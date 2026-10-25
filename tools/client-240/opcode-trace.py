#!/usr/bin/env python3
"""Join a Trace240Agent aggregate to stable symbols and real stack effects (runtime evidence only).

Usage: opcode-trace.py TRACE.tsv [--dump dump-240.tsv] [--out script-opcode-trace-240.tsv]
TRACE.tsv comes from `tools/parity/runelite240.py --trace-opcodes OUT.tsv`. Raw deltas are in stored units; the
real entry count is raw * multiplier (mod 2^32, signed), the per-field inverse of the stored-size unit.
A delta outside +-64 is reported as unreliable (`?`) rather than guessed. The trace proves which handler returned
non-2 for an opcode during a real session; it is not a complete opcode census.
"""
import argparse
import csv
from collections import defaultdict
from pathlib import Path

ROOT = Path(__file__).resolve().parents[2]
MULTIPLIER = {"int": -324749371, "obj": 1375698635, "long": 18294437}


def real(raw, kind):
    value = (int(raw) * MULTIPLIER[kind]) & 0xFFFFFFFF
    value = value - (1 << 32) if value >= 1 << 31 else value
    return str(value) if abs(value) <= 64 else "?"


def stable_handlers(dump):
    classes, handlers = {}, defaultdict(list)
    with dump.open(encoding="utf-8") as handle:
        for line in handle:
            parts = line.rstrip("\n").split("\t")
            if parts[0] == "C":
                classes[parts[5]] = parts[1]
            elif parts[0] == "M" and parts[3].startswith("(ILScript;Z"):
                handlers[(parts[1], parts[5] if parts[5] != "-" else parts[2])].append(parts[2])
    return classes, handlers


def convert(trace, dump):
    classes, handlers = stable_handlers(dump)
    rows = []
    for row in csv.DictReader(trace.open(encoding="utf-8"), delimiter="\t"):
        owner, _, name = row["handler"].rpartition(".")
        stable_class = classes.get(owner)
        stable = sorted(set(handlers.get((stable_class, name), []))) if stable_class else []
        symbol = f"{stable_class}.{stable[0]}" if len(stable) == 1 else row["handler"]
        rows.append([row["opcode"], symbol, row["handler"], row["result"], real(row["raw_int_delta"], "int"),
                     real(row["raw_obj_delta"], "obj"), real(row["raw_long_delta"], "long"), row["count"]])
    rows.sort(key=lambda r: (int(r[0]), r[1], r[3]))
    return rows


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("trace", type=Path)
    parser.add_argument("--dump", type=Path, default=ROOT / ".cache/client-240/dump-240.tsv")
    parser.add_argument("--out", type=Path, default=ROOT / "docs/reference/client-240/inputs/script-opcode-trace-240.tsv")
    args = parser.parse_args()
    rows = convert(args.trace, args.dump)
    args.out.parent.mkdir(parents=True, exist_ok=True)
    with args.out.open("w", encoding="utf-8") as out:
        out.write("opcode\thandler\toriginal\tresult\tint_delta\tobj_delta\tlong_delta\tcount\n")
        for row in rows:
            out.write("\t".join(row) + "\n")
    opcodes = {r[0] for r in rows}
    print(f"{len(rows)} rows, {len(opcodes)} opcodes traced, "
          f"{sum(1 for r in rows if '?' in r[4:7])} rows with an unreliable delta")


if __name__ == "__main__":
    main()
