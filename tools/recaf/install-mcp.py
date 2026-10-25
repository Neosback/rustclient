#!/usr/bin/env python3
"""Register this checkout's verified Recaf stdio server without replacing another entry."""
import json
from pathlib import Path
import shutil
import subprocess
import sys

ROOT = Path(__file__).resolve().parents[2]
sys.path.insert(0, str(ROOT / 'tools/client-240'))
from toolchain import runtime

NAME = 'rustclient-recaf'
command = [sys.executable, str(ROOT / 'tools/recaf/recaf.py')]
subprocess.run([*command, '--check'], check=True)
java, _ = runtime()
codex = shutil.which('codex')
if not codex:
    raise SystemExit('Codex CLI is not on PATH')
existing = subprocess.run([codex, 'mcp', 'get', NAME, '--json'], capture_output=True, text=True)
if existing.returncode == 0:
    data = json.loads(existing.stdout)
    transport = data.get('transport', {})
    if transport.get('command') != command[0] or transport.get('args') != command[1:]:
        raise SystemExit(f'{NAME} already points elsewhere; refusing to replace it')
    if (transport.get('env') or {}).get('CLIENT240_JAVA') == java:
        print(f'{NAME} already registered for this checkout')
        raise SystemExit(0)
else:
    # Distinguish a missing entry from an unreadable/broken Codex configuration.
    if 'No MCP server named' not in existing.stderr:
        raise SystemExit(existing.stderr or existing.stdout)
subprocess.run([codex, 'mcp', 'add', NAME, '--env', 'CLIENT240_JAVA=' + java, '--', *command], check=True)
