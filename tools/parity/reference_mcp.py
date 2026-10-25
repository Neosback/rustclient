#!/usr/bin/env python3
"""Read-only MCP/CLI access to the running revision-240 reference plugin."""
import argparse
import json
import os
from pathlib import Path
import sys
from urllib.error import HTTPError
from urllib.parse import urlencode, urlsplit
from urllib.request import Request, urlopen

ROOT = Path(__file__).resolve().parents[2]
ENDPOINT = Path(os.environ.get("RUSTCLIENT_REFERENCE_ENDPOINT",
                              str(ROOT / "target/runelite-240-runtime/endpoint.json")))
PIN = "25f42961c400bd9dfff1554402441c0ba6d1cffd011163cb9b0b4c42ae194f85"


def integer(minimum, maximum, default=None):
    result = {"type": "integer", "minimum": minimum, "maximum": maximum}
    if default is not None:
        result["default"] = default
    return result


def tool(name, description, properties=None, required=None, readonly=True):
    return {"name": name, "description": description,
            "inputSchema": {"type": "object", "properties": properties or {},
                            "required": required or [], "additionalProperties": False},
            "annotations": {"readOnlyHint": readonly, "destructiveHint": False,
                            "idempotentHint": readonly, "openWorldHint": False}}


TOOLS = [
    tool("reference_status", "Connection, provenance and current client cycle/game state."),
    tool("reference_snapshot", "Detached actor poses, camera, viewport, widget roots, menu and chat-tab state."),
    tool("reference_widget", "Live widget bounds, actions, numeric listener arguments and child tree (max 2048 nodes).",
         {"group": integer(0, 65535), "component": integer(0, 65535), "depth": integer(0, 4, 1)},
         ["group", "component"]),
    tool("reference_tile", "Scene-local tile flags, height, roof ID, walls, decoration offsets and objects.",
         {"x": integer(0, 255), "y": integer(0, 255), "plane": integer(0, 3)}, ["x", "y", "plane"]),
    tool("reference_class", "Runtime class hierarchy and member signatures from the pinned gamepack. No field values or invocation.",
         {"name": {"type": "string", "minLength": 1, "maxLength": 160}}, ["name"]),
    tool("reference_trace_start", "Capture up to 500 client cycles and 4096 script/menu/widget events; replaces only diagnostic trace data.",
         {"cycles": integer(1, 500, 150)}, readonly=False),
    tool("reference_trace", "Page captured actor/camera samples and script/menu/widget events. Records indicate truncation.",
         {"sampleOffset": integer(0, 500, 0), "sampleCount": integer(0, 20, 1),
          "eventOffset": integer(0, 4096, 0), "eventCount": integer(0, 256, 64)}),
    tool("reference_trace_stop", "Stop diagnostic capture and read its first page; does not change game state.", readonly=False),
]
CATALOG = {entry["name"]: entry for entry in TOOLS}


def arguments(name, values):
    schema = CATALOG[name]["inputSchema"]
    if not isinstance(values, dict) or set(values) - set(schema["properties"]):
        raise ValueError("Unknown tool arguments")
    if set(schema["required"]) - set(values):
        raise ValueError("Missing required arguments")
    for key, value in values.items():
        spec = schema["properties"][key]
        if spec["type"] == "integer":
            if type(value) is not int or not spec["minimum"] <= value <= spec["maximum"]:
                raise ValueError("Integer range: " + key)
        elif not isinstance(value, str) or not spec["minLength"] <= len(value) <= spec["maxLength"]:
            raise ValueError("String length: " + key)
    return values


def query(name, values):
    values = arguments(name, values)
    config = json.loads(ENDPOINT.read_text())
    url = urlsplit(config["url"])
    if (url.scheme != "http" or url.hostname != "127.0.0.1" or not url.port
            or url.path != "/v1/query" or url.query or url.fragment or url.username):
        raise ValueError("Reference endpoint must be loopback /v1/query")
    operation = "snapshot" if name == "reference_status" else name.removeprefix("reference_")
    request = Request(config["url"] + "?" + urlencode(dict(values, op=operation)),
                      headers={"X-RustClient-Reference": config["token"]})
    try:
        with urlopen(request, timeout=5) as response:
            body = response.read(16 * 1024 * 1024 + 1)
    except HTTPError as error:
        body = error.read(4096).decode(errors="replace")
        raise RuntimeError("Reference request failed: " + body) from None
    if len(body) > 16 * 1024 * 1024:
        raise ValueError("Reference response budget exceeded; request a smaller page")
    result = json.loads(body)
    if name == "reference_status":
        result = {key: result[key] for key in ("schema", "revision", "gameState", "cycle", "tick")}
        result.update({"connected": True, "pid": config["pid"], "injectedJarSha256": PIN,
                       "policy": "API snapshots and metadata only; public RSA connection adapter"})
    return result


def content(value, error=False):
    return {"content": [{"type": "text", "text": json.dumps(value, ensure_ascii=False)}],
            "structuredContent": value, "isError": error}


def serve():
    initialized = False
    while True:
        raw = sys.stdin.buffer.readline(1024 * 1024 + 1)
        if not raw:
            return
        request = None
        try:
            if len(raw) > 1024 * 1024:
                raise ValueError("Request exceeds 1 MiB")
            request = json.loads(raw)
            if not isinstance(request, dict) or request.get("jsonrpc") != "2.0":
                raise ValueError("Expected JSON-RPC 2.0 object")
            if "id" not in request:  # initialized/cancelled notifications have no response
                continue
            method = request.get("method")
            params = request.get("params", {})
            if method == "initialize":
                initialized = True
                requested = params.get("protocolVersion")
                version = requested if requested in {"2024-11-05", "2025-03-26", "2025-06-18", "2025-11-25"} else "2025-11-25"
                result = {"protocolVersion": version, "capabilities": {"tools": {}},
                          "serverInfo": {"name": "rustclient-reference", "version": "1.0.0"},
                          "instructions": "Inspect only. Link runtime observations to pinned bytecode before porting; snapshots do not establish complete behavior."}
            elif not initialized:
                raise ValueError("Initialize first")
            elif method == "ping":
                result = {}
            elif method == "tools/list":
                result = {"tools": TOOLS}
            elif method == "tools/call":
                name = params.get("name")
                if name not in CATALOG:
                    response = {"jsonrpc": "2.0", "id": request["id"],
                                "error": {"code": -32602, "message": "Unknown reference tool"}}
                    print(json.dumps(response), flush=True)
                    continue
                try:
                    result = content(query(name, params.get("arguments", {})))
                except Exception as error:
                    result = content({"error": str(error), "hint": "Run tools/parity/runelite240.py and enable RustClient Reference."}, True)
            else:
                response = {"jsonrpc": "2.0", "id": request["id"],
                            "error": {"code": -32601, "message": "Method not found"}}
                print(json.dumps(response), flush=True)
                continue
            response = {"jsonrpc": "2.0", "id": request["id"], "result": result}
        except Exception as error:
            response = {"jsonrpc": "2.0", "id": request.get("id") if isinstance(request, dict) else None,
                        "error": {"code": -32700, "message": str(error)}}
        print(json.dumps(response, ensure_ascii=False), flush=True)


if __name__ == "__main__":
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--query", choices=CATALOG)
    parser.add_argument("--arguments", default="{}")
    args = parser.parse_args()
    if args.query:
        print(json.dumps(query(args.query, json.loads(args.arguments)), indent=2))
    else:
        serve()
