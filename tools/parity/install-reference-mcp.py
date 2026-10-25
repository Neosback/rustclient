#!/usr/bin/env python3
"""Register the repo-owned live reference MCP in Codex, without replacing another server."""
import json
from pathlib import Path
import shutil
import subprocess
import sys

ROOT = Path(__file__).resolve().parents[2]
NAME = "rustclient-reference"
command = [sys.executable, str(ROOT / "tools/parity/reference_mcp.py")]
codex = shutil.which("codex")
if not codex:
    raise SystemExit("Codex CLI is not on PATH")
existing = subprocess.run([codex, "mcp", "get", NAME, "--json"], capture_output=True, text=True)
if existing.returncode == 0:
    transport = json.loads(existing.stdout).get("transport", {})
    if transport.get("command") != command[0] or transport.get("args") != command[1:]:
        raise SystemExit(NAME + " already points elsewhere; refusing to replace it")
    print(NAME + " already registered for this checkout")
elif "No MCP server named" not in existing.stderr:
    raise SystemExit(existing.stderr or existing.stdout)
else:
    subprocess.run([codex, "mcp", "add", NAME, "--", *command], check=True)
