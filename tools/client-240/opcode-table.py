#!/usr/bin/env python3
"""Join the revision-240 CS2 opcode evidence into one table (analysis evidence only).

Inputs: ScriptOpcodeScan output (bytecode handlers), RuneLite rev-240 opcode names, the profile adapter
table in script_opcodes.rs, and an optional `script_probe --find-opcode summary` tally of opcodes the
adapter does not name. Output columns: opcode, rust_name, runelite_name, status, live_uses, handlers.
The scan only proves that a handler branch exists; names are RuneLite's and never decide behaviour.
"""
import argparse
import re
from collections import defaultdict
from pathlib import Path

ROOT = Path(__file__).resolve().parents[2]
RL = ROOT / "references/open-runelite-rev-240/cache/src/main/java/net/runelite/cache/script"
CONSTANT = re.compile(r"int (\w+)\s*=\s*(\d+);")


def runelite():
    names = {}
    for name in ("Opcodes.java", "RuneLiteOpcodes.java"):
        for key, value in CONSTANT.findall((RL / name).read_text(encoding="utf-8")):
            names.setdefault(int(value), key)
    return names


def adapter(path):
    text = path.read_text(encoding="utf-8")
    body = text.split("const OPCODES", 1)[1]
    return {int(n): v for n, v in re.findall(r"\((\d+), Opcode::(\w+)\)", body)}


def scan(path):
    rows = defaultdict(list)
    for line in path.read_text(encoding="utf-8").splitlines()[1:]:
        cols = line.split("\t")
        rows[int(cols[0])].append(cols)
    return rows


def live(path):
    uses = {}
    if path and path.exists():
        for n, count in re.findall(r"Unsupported\((\d+)\): (\d+) uses", path.read_text(encoding="utf-8")):
            uses[int(n)] = int(count)
    return uses


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("--scan", default=ROOT / ".cache/client-240/opcodes/scan.tsv", type=Path)
    parser.add_argument("--live", default=ROOT / ".cache/client-240/opcodes/live-unsupported.txt", type=Path)
    parser.add_argument("--rust", default=ROOT / "crates/cache/src/osrs240/script_opcodes.rs", type=Path)
    parser.add_argument("--out", default=ROOT / "docs/reference/client-240/inputs/script-opcodes-240.tsv", type=Path)
    args = parser.parse_args()
    rl, rust, found, uses = runelite(), adapter(args.rust), scan(args.scan), live(args.live)
    numbers = sorted(set(rl) | set(rust) | set(uses) | {n for n in found if n >= 100})
    rows = []
    stats = defaultdict(int)
    for n in numbers:
        handlers = sorted({c[1].split("(")[0] for c in found.get(n, [])})
        if n in rust:
            status = "adapter" if (n in rl or handlers) else "adapter-unconfirmed"
        elif n in rl and handlers:
            status = "named-confirmed"
        elif n in rl:
            status = "named-only"
        elif handlers:
            status = "handler-only"
        else:
            status = "unknown"
        stats[status] += 1
        rows.append([n, rust.get(n, ""), rl.get(n, ""), status, uses.get(n, ""), ",".join(handlers)])
    args.out.parent.mkdir(parents=True, exist_ok=True)
    with args.out.open("w", encoding="utf-8") as out:
        out.write("opcode\trust_name\trunelite_name\tstatus\tlive_uses\thandlers\n")
        for row in rows:
            out.write("\t".join(map(str, row)) + "\n")
    for key in sorted(stats):
        print(f"{key}: {stats[key]}")
    named_live = [r for r in rows if r[4] != "" and r[2]]
    print(f"live-unsupported opcodes: {len(uses)}; with a RuneLite name: {len(named_live)}; "
          f"live uses covered by a name: {sum(r[4] for r in named_live)} of {sum(uses.values())}")


if __name__ == "__main__":
    main()
