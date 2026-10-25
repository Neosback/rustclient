#!/usr/bin/env python3
"""Join HookAnchorScan rows (original obfuscated symbols) to stable symbols (analysis evidence only).

Usage: hook-anchors.py [--scan anchors-original.tsv] [--dump dump-240.tsv] [--out hook-anchors-240.tsv]
Each row says a stable method calls a RuneLite hook or posts an API event, so its behavior includes that action.
Rows whose method is already named in the ledger carry the readable name. Nothing here renames anything.
"""
import argparse
import csv
import importlib.util
from pathlib import Path

ROOT = Path(__file__).resolve().parents[2]
spec = importlib.util.spec_from_file_location("promote", Path(__file__).with_name("enigma-promote.py"))
promote = importlib.util.module_from_spec(spec)
spec.loader.exec_module(promote)


def named(docs):
    names = {}
    path = docs / "ledger" / "NAMES.tsv"
    if path.is_file():
        for row in csv.reader(path.open(encoding="utf-8"), delimiter="\t"):
            if row and row[0] != "stable_symbol" and len(row) > 3:
                names[row[0]] = row[3]
    return names


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("--scan", type=Path, default=ROOT / ".cache/client-240/hooks/anchors-original.tsv")
    parser.add_argument("--dump", type=Path, default=ROOT / ".cache/client-240/dump-240.tsv")
    parser.add_argument("--docs", type=Path, default=ROOT / "docs/reference/client-240")
    parser.add_argument("--out", type=Path, default=ROOT / "docs/reference/client-240/inputs/hook-anchors-240.tsv")
    args = parser.parse_args()
    classes_by_obf, _, members = promote.load_dump(args.dump)
    names = named(args.docs)
    rows, missing = [], []
    for row in csv.DictReader(args.scan.open(encoding="utf-8"), delimiter="\t"):
        stable_class = classes_by_obf.get(row["class"])
        if stable_class is None:
            missing.append(f"{row['class']}.{row['method']}: class not in the semantic jar")
            continue
        desc = promote.to_semantic_desc(row["desc"], classes_by_obf)
        found = [m for m in members.get((stable_class, row["method"], desc), []) if m[0] == "method"]
        if len(found) != 1:
            missing.append(f"{row['class']}.{row['method']}{row['desc']}: {len(found)} semantic matches")
            continue
        symbol = f"{stable_class}.{found[0][1]}"
        rows.append([symbol, row["class"] + "." + row["method"], row["kind"], row["target"], row["count"],
                     names.get(symbol, ""), row["size"]])
    rows.sort()
    args.out.parent.mkdir(parents=True, exist_ok=True)
    with args.out.open("w", encoding="utf-8") as out:
        out.write("stable_symbol\toriginal\tkind\ttarget\tcount\treadable\tsize\n")
        for row in rows:
            out.write("\t".join(row) + "\n")
    print(f"{len(rows)} anchors joined, {len(missing)} unmatched")
    for line in missing[:20]:
        print("  unmatched:", line)


if __name__ == "__main__":
    main()
