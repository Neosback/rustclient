#!/usr/bin/env python3
"""Exercise the actual stdio MCP process and optionally its live client connection."""
import argparse
import json
from pathlib import Path
import subprocess
import sys

ROOT = Path(__file__).resolve().parents[2]
parser = argparse.ArgumentParser(description=__doc__)
parser.add_argument("--live", action="store_true")
args = parser.parse_args()
messages = [
    {"id": 1, "method": "initialize", "params": {"protocolVersion": "2025-11-25",
        "capabilities": {}, "clientInfo": {"name": "reference-verifier", "version": "1"}}},
    {"method": "notifications/initialized"},
    {"id": 2, "method": "tools/list"},
    {"id": 3, "method": "ping"},
    {"id": 4, "method": "tools/call", "params": {"name": "arbitrary_eval", "arguments": {}}},
    {"id": 5, "method": "tools/call", "params": {"name": "reference_widget",
        "arguments": {"group": -1, "component": 0}}},
]
if args.live:
    for index, (name, arguments) in enumerate([
        ("reference_status", {}), ("reference_snapshot", {}),
        ("reference_widget", {"group": 162, "component": 4, "depth": 1}),
        ("reference_class", {"name": "bq"}),
        ("reference_trace_start", {"cycles": 2}), ("reference_trace", {}),
        ("reference_trace_stop", {}), ("reference_class", {"name": "java.lang.System"}),
    ], start=10):
        messages.append({"id": index, "method": "tools/call", "params": {"name": name, "arguments": arguments}})
process = subprocess.run([sys.executable, str(ROOT / "tools/parity/reference_mcp.py")],
    input="".join(json.dumps(dict(message, jsonrpc="2.0")) + "\n" for message in messages),
    capture_output=True, text=True, timeout=50)
assert process.returncode == 0, process.stderr
responses = [json.loads(line) for line in process.stdout.splitlines()]
by_id = {response["id"]: response for response in responses}
assert len(responses) == len(messages) - 1  # notifications are silent
assert by_id[1]["result"]["protocolVersion"] == "2025-11-25"
names = {tool["name"] for tool in by_id[2]["result"]["tools"]}
assert len(names) == 8 and "reference_trace" in names
assert by_id[3]["result"] == {}
assert by_id[4]["error"]["code"] == -32602
assert by_id[5]["result"]["isError"]
if args.live:
    for index in range(10, 17):
        assert not by_id[index]["result"]["isError"], by_id[index]
    assert by_id[10]["result"]["structuredContent"]["revision"] == 240
    assert by_id[13]["result"]["structuredContent"]["name"] == "bq"
    assert by_id[17]["result"]["isError"]  # arbitrary JDK reflection is not exposed
print(json.dumps({"status": "PASS", "live": args.live, "tools": sorted(names),
                  "checks": ["initialize", "notifications", "catalog", "ping", "unknown tool",
                             "argument bounds", "EOF shutdown"]
                  + (["live API snapshots", "widget", "gamepack metadata", "trace controls", "reflection scope"] if args.live else [])}, indent=2))
