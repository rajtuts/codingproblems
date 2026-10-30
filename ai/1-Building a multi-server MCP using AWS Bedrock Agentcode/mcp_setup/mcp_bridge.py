import logging
import os
from typing import Optional, List, Dict

from mcp.server.fastmcp import FastMCP

import task_server
import weather_server

mcp = FastMCP("CloudLabAssistant", host="0.0.0.0", port=8000)


@mcp.tool()
def add_task(description: str, location: Optional[str] = None, scheduled_for: Optional[str] = None) -> str:
    """
    Add a structured task. scheduled_for (if provided) must be an ISO8601 datetime string.
    """
    return task_server.add_task(description, location, scheduled_for)


@mcp.tool()
def list_tasks(filter_date: Optional[str] = None) -> List[Dict]:
    """
    List tasks. Optional filter_date (YYYY-MM-DD) to filter scheduled tasks.
    """
    return task_server.list_tasks(filter_date)


@mcp.tool()
def get_weather(location: str) -> dict:
    """
    Lookup mock weather. Location may be "City" or "City,country".
    """
    return weather_server.get_weather(location)


if __name__ == "__main__":
    debug = os.environ.get("MCP_DEBUG") == "1"
    log_level = logging.DEBUG if debug else logging.WARNING
    logging.basicConfig(level=log_level, format="%(asctime)s %(name)s %(levelname)s %(message)s")
    logging.getLogger("mcp").setLevel(log_level)
    if debug:
        # boto3/botocore's own DEBUG logging traces credential resolution and every API call/response,
        # which is what actually shows why a call is silently misbehaving (vs. just "isError=False").
        logging.getLogger("boto3").setLevel(logging.DEBUG)
        logging.getLogger("botocore").setLevel(logging.DEBUG)
    mcp.run(transport="streamable-http")
