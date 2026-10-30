import os
import json
import logging
import uuid
import shutil
import sys
import asyncio
import concurrent.futures
from datetime import datetime, timezone
from pathlib import Path
from typing import List, Optional, Dict, Any

from mcp.server.fastmcp import FastMCP

# --- MCP client imports (used to talk to awslabs.dynamodb-mcp-server) ---
from mcp import ClientSession
from mcp.client.stdio import stdio_client, StdioServerParameters

# Persistent storage (JSON Lines) - used as fallback
AWS_DEFAULT_REGION="us-east-1"
AWS_REGION="us-east-1"
TASKS_FILE = "tasks.jsonl"
TABLE = "TrainingSessions"

# MCP command / args for the AWSlabs DynamoDB MCP server
MCP_COMMAND = os.environ.get("MCP_CMD_PATH", "uvx")
MCP_ARGS = ["awslabs.dynamodb-mcp-server@1.0.9"]

# Ensure the DynamoDB MCP server will accept writes
ENV = os.environ.copy()
ENV.setdefault("FASTMCP_LOG_LEVEL", "ERROR")
ENV["DDB_MCP_READONLY"] = "false"

mcp = FastMCP("TaskManagementAssistant")

###############################################################################
# Helper functions: call the DynamoDB MCP server (via stdio_client + ClientSession)
###############################################################################
def _assert_uvx_available():
    if shutil.which(MCP_COMMAND) is None:
        raise FileNotFoundError(f"'{MCP_COMMAND}' not found on PATH. Install 'uvx' or set MCP_CMD_PATH env var.")

def generate_arg_variants(base_args: Dict[str, Any], variant_key_map: Dict[str, List[str]]):
    """
    Produce permutations of key names to try with the remote MCP tool.
    """
    from itertools import product

    keys = []
    candidates = []
    for k, v in base_args.items():
        keys.append(k)
        if k in variant_key_map:
            candidates.append(variant_key_map[k])
        else:
            candidates.append([k])

    for combo in product(*candidates):
        args = {}
        for logical_key, actual_key in zip(keys, combo):
            args[actual_key] = base_args[logical_key]
        yield args


async def try_call_with_variants_async(session: ClientSession, tool_name: str, base_args: Dict[str, Any],
                                       variant_key_map: Dict[str, List[str]], stop_on_success: bool = True):
    """
    Async helper to attempt a tool call with permutations of argument key names.
    Returns (success_bool, result_or_exception, used_args_or_tried_list)
    """
    debug = os.environ.get("MCP_DEBUG") == "1"
    last_err = None
    tried = []
    for args in generate_arg_variants(base_args, variant_key_map):
        tried.append(args)
        if debug:
            print(f"[MCP_DEBUG] calling tool={tool_name!r} args={args!r}", flush=True)
        try:
            # call_tool returns whatever the MCP returns; often it's a JSON-serializable object
            res = await session.call_tool(tool_name, args)
            if debug:
                print(f"[MCP_DEBUG] raw result for {tool_name!r} args={args!r}: {res!r}", flush=True)
            # Many MCP clients return a simple object or a wrapper; treat non-exceptions as success.
            is_error = getattr(res, "isError", False)
            if not is_error:
                return True, res, args
            last_err = res
        except Exception as exc:
            if debug:
                print(f"[MCP_DEBUG] exception for {tool_name!r} args={args!r}: {exc!r}", flush=True)
            last_err = exc
    return False, last_err, tried


async def _call_mcp_tool_once_async(tool_name: str, base_args: Dict[str, Any],
                                    variant_key_map: Dict[str, List[str]]):
    """
    Spawn the awslabs.dynamodb-mcp-server using uvx (stdio) and call a tool once.
    Returns the (success_bool, result_or_exception, used_args_or_tried_list)
    """
    # Validate uvx present
    _assert_uvx_available()

    server_params = StdioServerParameters(command=MCP_COMMAND, args=MCP_ARGS, env=ENV)

    async with stdio_client(server_params) as server_yield:
        # server_yield is typically (read_stream, write_stream, proc) or (read_stream, write_stream)
        try:
            read_stream, write_stream, *_ = server_yield
        except Exception:
            read_stream, write_stream = server_yield

        async with ClientSession(read_stream, write_stream) as session:
            await session.initialize()
            # Try the tool with argument key variants
            return await try_call_with_variants_async(session, tool_name, base_args, variant_key_map)


def _run_mcp_call(tool_name: str, base_args: Dict[str, Any], variant_key_map: Dict[str, List[str]]):
    """
    Sync wrapper around async MCP call. Runs in a dedicated thread with its own event loop,
    since this may be called from a context that already has an event loop running (e.g. when
    add_task/list_tasks are invoked as MCP tools from mcp_bridge.py's async server) — asyncio.run()
    cannot be nested inside an already-running loop on the same thread.
    Returns (success_bool, result_or_exception, used_args_or_tried_list)
    """
    try:
        with concurrent.futures.ThreadPoolExecutor(max_workers=1) as pool:
            return pool.submit(
                asyncio.run, _call_mcp_tool_once_async(tool_name, base_args, variant_key_map)
            ).result()
    except Exception as exc:
        return False, exc, None


def _av_from_py(value):
    """
    Convert a simple Python value to DynamoDB AttributeValue JSON shape used by awslabs.dynamodb-mcp-server.
    Very simple converter: strings -> {"S": str}, ints/floats -> {"N": str(n)}, bool->{"BOOL":bool}
    None -> omitted
    """
    if value is None:
        return None
    if isinstance(value, bool):
        return {"BOOL": value}
    if isinstance(value, (int, float)):
        return {"N": str(value)}
    # fallback to string
    return {"S": str(value)}


def _item_to_ddb_attrs(item: Dict[str, Any]) -> Dict[str, Dict[str, str]]:
    """
    Convert a plain dict into DynamoDB AttributeValue-style dict.
    E.g., {"task_id": "abc", "seats": 25} -> {"task_id": {"S":"abc"}, "seats": {"N":"25"}}
    """
    out = {}
    for k, v in item.items():
        av = _av_from_py(v)
        if av is not None:
            out[k] = av
    return out


def _parse_mcp_result_to_json(mcp_result):
    """
    Attempt to extract a JSON-serializable Python object from the MCP result.
    MCP result may be:
      - already a list/dict (good)
      - an object with `.content` where .content[0].text contains JSON
      - a string
      - something else
    Returns parsed object on success, or raises ValueError.
    """
    # direct return if it's already a list/dict
    if isinstance(mcp_result, (list, dict)):
        return mcp_result

    # try to access .content[0].text (some MCP wrappers use that)
    try:
        content = getattr(mcp_result, "content", None)
        if content:
            # content may be list-like; try to find first text-looking element
            for c in content:
                try:
                    text = getattr(c, "text", None)
                    if text:
                        return json.loads(text)
                except Exception:
                    # try to interpret c as text
                    try:
                        return json.loads(c)
                    except Exception:
                        continue
    except Exception:
        pass

    # try to parse string representation
    try:
        return json.loads(str(mcp_result))
    except Exception:
        pass

    raise ValueError("Could not parse MCP result into JSON-like object.")


###############################################################################
# Tools exposed by this MCP server
###############################################################################
@mcp.tool()
def add_task(description: str, location: Optional[str] = None, scheduled_for: Optional[str] = None) -> str:
    """
    Add a structured task. scheduled_for (if provided) must be an ISO8601 datetime string.
    Attempts to write to DynamoDB (via the AWS-managed DynamoDB MCP server). On failure, falls back to local file.
    """
    # validate scheduled_for -> convert to ISO UTC string if provided
    dt_iso = None
    if scheduled_for:
        try:
            s = scheduled_for.rstrip("Z")
            dt = datetime.fromisoformat(s)
            if dt.tzinfo is None:
                dt = dt.replace(tzinfo=timezone.utc)
            dt_iso = dt.astimezone(timezone.utc).isoformat()
        except Exception:
            return "Error: scheduled_for must be ISO8601 (e.g. 2025-10-05T09:00:00 or ...Z)."

    task = {
        "task_id": uuid.uuid4().hex,  # use task_id as key in DynamoDB as requested
        "description": description,
        "location": location or "",
        "scheduled_for": dt_iso,
        "created_at": datetime.now(timezone.utc).isoformat(),
    }

    # Prepare DynamoDB AttributeValue JSON
    item = _item_to_ddb_attrs(task)

    # base args using logical names and candidate variants
    put_base = {"table": TABLE, "item": item}
    put_variants = {
        "table": ["table_name", "TableName", "table"],
        "item": ["item", "Item", "ItemAttributes", "ItemAttr"]
    }

    # Try to call the DynamoDB MCP server put_item (attempt variants)
    try:
        ok, result, used = _run_mcp_call("put_item", put_base, put_variants)
        if ok:
            # success: return a friendly message
            return f"Task added with id {task['task_id']} (stored in DynamoDB)."
        else:
            # failed: fall back to local storage and include reason
            fallback_reason = f"{type(result).__name__}: {result}" if result is not None else "unknown error"
    except FileNotFoundError as fnf:
        ok = False
        fallback_reason = str(fnf)
    except Exception as exc:
        ok = False
        fallback_reason = str(exc)

    # Fall back to local JSONL storage
    try:
        with open(TASKS_FILE, "a", encoding="utf-8") as f:
            f.write(json.dumps(task, ensure_ascii=False) + "\n")
        return f"Task added with id {task['task_id']} (local fallback). DynamoDB write failed: {fallback_reason}"
    except Exception as e:
        return f"An error occurred while adding the task: {e} (DynamoDB write failed: {fallback_reason})"


@mcp.tool()
def list_tasks(filter_date: Optional[str] = None) -> List[Dict]:
    """
    Return tasks as list of dicts. If filter_date provided (YYYY-MM-DD), only tasks scheduled that date (UTC) are returned.
    This function attempts to read from DynamoDB via the awslabs DynamoDB MCP server.
    If the remote read fails, it falls back to reading local TASKS_FILE.
    """
    # If the table read via MCP succeeds we parse and return items; else fallback to local file.

    # Build base args for a scan-like operation
    # We'll try a few tool names via MCP (put_item was asserted earlier). The standard MCP has 'scan' or 'scan_table' or 'query'
    scan_base = {"table": TABLE}
    scan_variants = {
        "table": ["table_name", "TableName", "table"],
    }

    # Try a set of candidate tool names in order
    candidate_tools = ["scan", "scan_table", "scan_items", "query", "list_items", "list_items_table"]

    for tool in candidate_tools:
        try:
            ok, result, used = _run_mcp_call(tool, scan_base, scan_variants)
        except FileNotFoundError as fnf:
            ok = False
            result = fnf
        except Exception as exc:
            ok = False
            result = exc

        if ok:
            # Try to parse result to JSON-like object
            try:
                parsed = _parse_mcp_result_to_json(result)
            except Exception:
                # if parsing fails, fallback to next tool name
                parsed = None

            if parsed is None:
                continue

            # Expect parsed to have Items or similar; try multiple shapes:
            items = None
            if isinstance(parsed, dict):
                # Many DynamoDB tools return {"Items": [...]} or {"items":[...]}
                if "Items" in parsed and isinstance(parsed["Items"], list):
                    items = parsed["Items"]
                elif "items" in parsed and isinstance(parsed["items"], list):
                    items = parsed["items"]
                elif "Results" in parsed and isinstance(parsed["Results"], list):
                    items = parsed["Results"]
                # or maybe parsed itself is list-like in a dict wrapper
            elif isinstance(parsed, list):
                items = parsed

            if items is None:
                # try to interpret parsed as list in string or nested location
                try:
                    items = parsed.get("Items") or parsed.get("items") or parsed.get("Results")
                except Exception:
                    items = None

            if not items:
                # no items returned -> empty list
                items = []

            # Convert DynamoDB AttributeValue shape to simple dicts
            def _from_av(av):
                if not isinstance(av, dict):
                    return av
                # choose common types
                if "S" in av:
                    return av["S"]
                if "N" in av:
                    try:
                        # convert ints when possible
                        n = av["N"]
                        if "." in n:
                            return float(n)
                        return int(n)
                    except Exception:
                        return av["N"]
                if "BOOL" in av:
                    return av["BOOL"]
                if "NULL" in av:
                    return None
                return av  # unknown shape

            plain_items = []
            for it in items:
                if isinstance(it, dict):
                    plain = {}
                    for kk, vv in it.items():
                        plain[kk] = _from_av(vv)
                    plain_items.append(plain)
                else:
                    # not dict-shaped; append raw
                    plain_items.append(it)

            # If filter_date provided, filter by scheduled_for date (UTC)
            if filter_date:
                try:
                    filter_dt = datetime.fromisoformat(filter_date).date()
                except Exception:
                    filter_dt = None

                if filter_dt:
                    filtered = []
                    for p in plain_items:
                        sf = p.get("scheduled_for") or p.get("date") or p.get("scheduled")
                        if not sf:
                            continue
                        try:
                            tf = datetime.fromisoformat(str(sf).replace("Z", "+00:00")).date()
                            if tf == filter_dt:
                                filtered.append(p)
                        except Exception:
                            continue
                    return filtered
            return plain_items

    # If we reach here, remote MCP read failed; fallback to local file reading
    if not os.path.exists(TASKS_FILE):
        return []

    filter_dt = None
    if filter_date:
        try:
            filter_dt = datetime.fromisoformat(filter_date).date()
        except Exception:
            filter_dt = None

    tasks: List[Dict] = []
    try:
        with open(TASKS_FILE, "r", encoding="utf-8") as f:
            for line in f:
                line = line.strip()
                if not line:
                    continue
                try:
                    t = json.loads(line)
                except Exception:
                    continue
                if filter_dt and t.get("scheduled_for"):
                    try:
                        tf = datetime.fromisoformat(t["scheduled_for"].replace("Z", "+00:00")).date()
                        if tf != filter_dt:
                            continue
                    except Exception:
                        continue
                tasks.append(t)
        return tasks
    except Exception:
        return []


if __name__ == "__main__":
    logging.getLogger("mcp").setLevel(logging.WARNING)
    mcp.run(transport="stdio")
