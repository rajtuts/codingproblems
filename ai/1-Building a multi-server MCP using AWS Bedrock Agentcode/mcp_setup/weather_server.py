import csv
import logging
from pathlib import Path
from typing import Optional, Iterable

from mcp.server.fastmcp import FastMCP

mcp = FastMCP("WeatherAssistant")

@mcp.tool()
def get_weather(location: str) -> dict:
    """
    Lookup weather from mock_weather.csv. Location may be "City" or "City,country".
    Returns a dict with fields: location, weather, temperature_celsius, feels_like_celsius, humidity, wind_speed_mps
    """
    csv_path = Path("mock_weather.csv")
    if not csv_path.exists():
        return {"error": "mock_weather.csv file not found on the server."}

    def get_field(row: dict, candidates: Iterable[str]) -> Optional[str]:
        for c in candidates:
            v = row.get(c)
            if v not in (None, ""):
                return v
        return None

    def normalize(s: Optional[str]) -> Optional[str]:
        return s.strip().lower() if s and isinstance(s, str) else None

    parts = [p.strip() for p in location.split(",", 1)]
    query_city = normalize(parts[0])
    query_country = normalize(parts[1]) if len(parts) > 1 else None

    try:
        with csv_path.open(newline="", encoding="utf-8") as fh:
            reader = csv.DictReader(fh)
            matches = []
            for row in reader:
                row_city = normalize(get_field(row, ("city", "name", "location")))
                if not row_city:
                    continue
                if row_city != query_city:
                    continue
                if query_country:
                    row_country = normalize(get_field(row, ("country", "country_code", "countryCode")))
                    if row_country and row_country != query_country:
                        continue
                matches.append(row)

            if not matches:
                return {"error": f"Could not find weather data for '{location}'."}

            row = matches[0]
            weather = get_field(row, ("weather", "description", "condition")) or "N/A"
            temp = get_field(row, ("temp_c", "temperature_celsius", "temp", "temperature")) or "N/A"
            feels_like = get_field(row, ("feels_like_c", "feels_like", "feels_like_celsius")) or "N/A"
            humidity = get_field(row, ("humidity",)) or "N/A"
            wind = get_field(row, ("wind_mps", "wind_speed", "wind")) or "N/A"
            location_name = get_field(row, ("name", "city", "location")) or location

            def fmt_temp(value: str) -> str:
                v = value.strip()
                try:
                    fv = float(v)
                    return f"{fv}°C"
                except Exception:
                    return v

            def fmt_percent(value: str) -> str:
                v = value.strip()
                if v.endswith("%"):
                    return v
                try:
                    fv = float(v)
                    return f"{fv}%"
                except Exception:
                    return v

            def fmt_wind(value: str) -> str:
                v = value.strip()
                if v.endswith("m/s") or v.endswith("mph"):
                    return v
                try:
                    fv = float(v)
                    return f"{fv} m/s"
                except Exception:
                    return v

            return {
                "location": location_name,
                "weather": weather,
                "temperature_celsius": fmt_temp(temp),
                "feels_like_celsius": fmt_temp(feels_like),
                "humidity": fmt_percent(humidity),
                "wind_speed_mps": fmt_wind(wind)
            }
    except Exception as e:
        return {"error": f"Error reading mock CSV: {e}"}

if __name__ == "__main__":
    logging.getLogger("mcp").setLevel(logging.WARNING)
    mcp.run(transport="stdio")