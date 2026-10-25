#!/usr/bin/env python3
"""Unattended UI oracle recorder for the live revision-240 reference client (runtime evidence only).

  record_ui.py sweep NAME [--step 6]   hover every canvas pixel grid point, record the client's full menu entries
  record_ui.py regions NAME            merge a sweep into rectangles of identical menus -> TSV on stdout / --out
  record_ui.py dump NAME               write the full live widget tree (geometry, text, sprites, actions, listeners)
  record_ui.py tabs NAME               click every gameframe tab found by a sweep and dump the widgets of each

Everything goes through the token-protected loopback bridge (`reference_mcp.py`); the driver only delivers canvas
mouse/key events like a player. Outputs land in target/runelite-240-runtime/recordings/ (not committed); curated
summaries are copied into docs/reference/client-240/inputs/ by hand. The menu entries are the exact menu the real
client built for that pointer position, so they are the oracle for which options show, their order and their
widget/param identifiers (type 57 etc. carry the component id), not a re-derivation.
"""
import argparse
import json
import subprocess
import sys
import time
from collections import OrderedDict
from pathlib import Path

ROOT = Path(__file__).resolve().parents[2]
RECORDINGS = ROOT / "target/runelite-240-runtime/recordings"
MCP = ROOT / "tools/parity/reference_mcp.py"


def call(name, arguments=None):
    result = subprocess.run([sys.executable, str(MCP), "--query", name, "--arguments", json.dumps(arguments or {})],
                            capture_output=True, text=True)
    if result.returncode:
        raise SystemExit(f"{name} failed: {result.stderr[-400:]}")
    return json.loads(result.stdout)


def sweep(name, step):
    status = call("reference_status")
    if status["gameState"] != "LOGGED_IN":
        raise SystemExit("client is not logged in")
    started = call("reference_record_menu", {"name": name, "step": step})
    print(f"sweeping {started['points']} points -> {started['file']}", flush=True)
    while True:
        state = call("reference_record_status")
        print(f"\r{state['done']}/{state['total']}", end="", flush=True)
        if not state["running"]:
            print()
            if state.get("error"):
                raise SystemExit(state["error"])
            return
        time.sleep(5)


def key(entries):
    return tuple((e["option"], e["target"], e["type"], e["identifier"], e["param0"], e["param1"]) for e in entries)


def regions(name, step):
    rows = OrderedDict()
    for line in (RECORDINGS / f"{name}.jsonl").read_text().splitlines():
        record = json.loads(line)
        rows.setdefault(record["y"], []).append((record["x"], key(record["entries"])))
    runs = []  # (y, x0, x1, menu)
    for y, points in rows.items():
        start, menu = points[0]
        last = start
        for x, current in points[1:]:
            if current != menu:
                runs.append((y, start, last, menu))
                start, menu = x, current
            last = x
        runs.append((y, start, last, menu))
    merged = []  # rectangles; extend vertically when the run above has identical x-span and menu
    open_rects = {}
    for y, x0, x1, menu in runs:
        key_ = (x0, x1, menu)
        rect = open_rects.pop(key_, None)
        if rect and rect[3] == y - step:
            rect[3] = y
            open_rects[key_] = rect
        else:
            rect = [x0, y, x1, y, menu]
            rect = [x0, x1, menu, y, y]
            open_rects[key_] = rect
            merged.append(rect)
    lines = ["x0\ty0\tx1\ty1\tmenu (option|target|type|identifier|param0|param1; top entry last)"]
    for x0, x1, menu, y0, y1 in merged:
        text = " ; ".join("|".join(map(str, entry)) for entry in menu)
        lines.append(f"{x0}\t{y0}\t{x1 + step - 1}\t{y1 + step - 1}\t{text}")
    return "\n".join(lines)


def dump(name):
    return call("reference_dump_widgets", {"name": name})


def tabs(name, step):
    """Click each distinct widget-action tab found by a sweep (type 57 entries whose option names a tab) and dump."""
    seen = OrderedDict()
    for line in (RECORDINGS / f"{name}.jsonl").read_text().splitlines():
        record = json.loads(line)
        for entry in record["entries"]:
            if entry["type"] == 57 and entry["option"] and entry["option"] not in seen and record["x"] > 500:
                seen[entry["option"]] = (record["x"], record["y"], entry["param1"])
    for option, (x, y, widget) in seen.items():
        call("reference_input", {"kind": "click", "x": x, "y": y})
        time.sleep(1.5)
        slug = "".join(c if c.isalnum() else "-" for c in option.lower()).strip("-")[:30] or "tab"
        print(option, (x, y), dump(f"{name}-tab-{slug}"), flush=True)


def main():
    parser = argparse.ArgumentParser(description=__doc__, formatter_class=argparse.RawDescriptionHelpFormatter)
    parser.add_argument("command", choices=["sweep", "regions", "dump", "tabs"])
    parser.add_argument("name")
    parser.add_argument("--step", type=int, default=6)
    parser.add_argument("--out", type=Path)
    args = parser.parse_args()
    if args.command == "sweep":
        sweep(args.name, args.step)
    elif args.command == "regions":
        text = regions(args.name, args.step)
        if args.out:
            args.out.write_text(text + "\n")
        else:
            print(text)
    elif args.command == "dump":
        print(json.dumps(dump(args.name)))
    else:
        tabs(args.name, args.step)


if __name__ == "__main__":
    main()
