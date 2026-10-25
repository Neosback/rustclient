#!/usr/bin/env python3
"""Extract the menu-action -> outbound packet table from the 240 menu click dispatcher (analysis evidence only).

Usage: menu-actions.py [--source readable-src] [--out menu-actions-240.tsv]
The dispatcher `classQP.method9746` (plus its delegates `classSW.method10742`, `classHU.method6158`) first folds ids >= 2000 into 0..1999 (var17 -= 2000), then branches on the action
id with plain `if (var17 == N)` blocks. Each block builds one outbound packet (`ClientProt.X`). This reads the readable
decompile of the semantic jar; it proves the action-to-packet mapping and the written field order, not the semantics of
every side effect. Cross-check with the live recorder (`tools/parity/record_ui.py`) which shows the action ids the real
client attaches to each menu entry.
"""
import argparse
import re
from pathlib import Path

ROOT = Path(__file__).resolve().parents[2]
# (source file, method, name of the action-id variable). The first is the entry dispatcher; the others are the
# delegates it calls with the folded action id (`classIK.method6263` is a pre-hook shared by all actions).
DISPATCHERS = [("classQP.java", "method9746", "var17"), ("classSW.java", "method10742", "var3"),
               ("classHU.java", "method6158", "var2")]


def blocks(text, var):
    equal = rf"(?:{var} == (\d+)|(\d+) == {var})"
    for match in re.finditer(r"\bif \(([^{}]*?)\) \{", text):
        condition = match.group(1)
        ids = re.findall(equal, condition)
        if not ids:
            continue
        stripped = re.sub(rf"(?:{var} == \d+|\d+ == {var})", "", condition)
        if re.sub(r"[\s|()]", "", stripped):
            continue  # compound condition mixing other state; skip rather than guess
        depth, i = 1, match.end()
        while i < len(text) and depth:
            depth += {"{": 1, "}": -1}.get(text[i], 0)
            i += 1
        yield sorted({int(a or b) for a, b in ids}), text[match.end():i]


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("--source", type=Path, default=ROOT / ".cache/client-240/readable-src")
    parser.add_argument("--out", type=Path, default=ROOT / "docs/reference/client-240/inputs/menu-actions-240.tsv")
    args = parser.parse_args()
    rows = {}
    for name, method, var in DISPATCHERS:
        text = (args.source / name).read_text(encoding="utf-8")
        start = text.index(f"static void {method}(") if f"static void {method}(" in text else text.index(method)
        for ids, body in blocks(text[start:], var):
            prots = list(dict.fromkeys(re.findall(r"ClientProt\.(\w+)", body)))
            writes = [m.group(1) for m in re.finditer(r"Buffer\.(write\w+)", body)]
            for action in ids:
                rows.setdefault(action, []).append((prots, writes, f"{name[:-5]}.{method}"))
    args.out.parent.mkdir(parents=True, exist_ok=True)
    with args.out.open("w", encoding="utf-8") as out:
        out.write("action\tclient_prot\tbuffer_writes\tnote\n")
        for action in sorted(rows):
            prots = list(dict.fromkeys(p for entry in rows[action] for p in entry[0]))
            writes = max((entry[1] for entry in rows[action]), key=len)
            sources = ",".join(dict.fromkeys(entry[2] for entry in rows[action]))
            note = sources if prots else f"{sources}: no packet in block (client-side or delegated)"
            out.write(f"{action}\t{','.join(prots)}\t{','.join(writes)}\t{note}\n")
    print(f"{len(rows)} actions, {sum(1 for r in rows.values() if any(p[0] for p in r))} with a ClientProt packet")


if __name__ == "__main__":
    main()
