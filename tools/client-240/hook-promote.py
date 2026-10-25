#!/usr/bin/env python3
"""Promote unambiguous RuneLite hook anchors to a naming shard (T1: API-derived evidence).

Usage: hook-promote.py CLUSTER [--max-size N] [--dump dump-240.tsv] [--dry-run] [--force]
Eligible: a method whose only anchor is exactly one posted API event, still carrying its generic `methodN` name,
small enough (default 60 instructions) that `post<Event>` describes it, and not virtual (`vmethodN`: overrides
need family review). Same-class collisions on a name are skipped, never suffixed. The shard is verified with
check-shard.sh afterwards; this script only proposes names that the injector's own call sites justify.
"""
import argparse
import csv
import re
import sys
from collections import defaultdict
from pathlib import Path

ROOT = Path(__file__).resolve().parents[2]
DOCS = ROOT / "docs/reference/client-240"
GENERIC = re.compile(r"method\d+")


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("cluster")
    parser.add_argument("--max-size", type=int, default=60)
    parser.add_argument("--dump", type=Path, default=ROOT / ".cache/client-240/dump-240.tsv")
    parser.add_argument("--anchors", type=Path, default=DOCS / "inputs/hook-anchors-240.tsv")
    parser.add_argument("--dry-run", action="store_true")
    parser.add_argument("--force", action="store_true")
    args = parser.parse_args()
    events = defaultdict(list)
    for row in csv.DictReader(args.anchors.open(encoding="utf-8"), delimiter="\t"):
        if row["kind"] == "event":
            events[row["stable_symbol"]].append(row)
    members = {}  # symbol -> (desc, obf class, obf name)
    obf_class = {}
    with args.dump.open(encoding="utf-8") as handle:
        for raw in handle:
            parts = raw.rstrip("\n").split("\t")
            if parts[0] == "C":
                obf_class[parts[1]] = parts[5]
            elif parts[0] == "M":
                members[f"{parts[1]}.{parts[2]}"] = (parts[3], parts[1], parts[5])
    taken = set()
    for shard in (DOCS / "mappings/clusters").glob("*.tiny"):
        if shard.stem != args.cluster:
            taken.update(line.split("\t")[-1] for line in shard.read_text(encoding="utf-8").splitlines())
    # The validator allows one owning shard per class, so classes already declared elsewhere are skipped.
    seeded = set()
    for shard in (DOCS / "mappings/clusters").glob("*.tiny"):
        if shard.stem != args.cluster:
            seeded.update(line.split("\t")[1] for line in shard.read_text(encoding="utf-8").splitlines()
                          if line.startswith("c\t"))
    proposals = defaultdict(list)  # (class, readable, desc) -> symbols
    for symbol, rows in events.items():
        stable_class, _, name = symbol.rpartition(".")
        if len(rows) != 1 or rows[0]["readable"] or not GENERIC.fullmatch(name):
            continue
        if int(rows[0]["size"]) > args.max_size or symbol not in members:
            continue
        if stable_class in seeded:
            print(f"skip {symbol}: class already owned by another shard", file=sys.stderr)
            continue
        event = rows[0]["target"]
        readable = "post" + (event[4:] if event.startswith("Post") else event)
        proposals[(stable_class, readable)].append(symbol)
    chosen = []
    for (stable_class, readable), symbols in sorted(proposals.items()):
        if len(symbols) != 1:
            print(f"skip {stable_class}.{readable}: {len(symbols)} methods post the same event", file=sys.stderr)
            continue
        chosen.append((symbols[0], stable_class, readable))
    shard, ledger = ["tiny\t2\t0\tsemantic\treadable"], []
    by_class = defaultdict(list)
    for symbol, stable_class, readable in chosen:
        desc, _, obf = members[symbol]
        name = symbol.rpartition(".")[2]
        by_class[stable_class].append(f"\tm\t{desc}\t{name}\t{readable}")
        event = events[symbol][0]["target"]
        ledger.append([symbol, stable_class, obf, readable, "method", "T1", "B",
                       f"injector call site: this method constructs and posts net.runelite.api.events.{event} "
                       f"(inputs/hook-anchors-240.tsv)", "-", args.cluster])
    for stable_class in sorted(by_class):
        shard.append(f"c\t{stable_class}\t{stable_class}")
        shard.extend(by_class[stable_class])
    print(f"{len(chosen)} method rename(s) proposed")
    if args.dry_run:
        print("\n".join(shard))
        return
    paths = (DOCS / "mappings/clusters" / f"{args.cluster}.tiny", DOCS / "ledger/clusters" / f"{args.cluster}.tsv")
    if any(p.exists() for p in paths) and not args.force:
        sys.exit("shard exists; pass --force")
    paths[0].write_text("\n".join(shard) + "\n", encoding="utf-8")
    with paths[1].open("w", encoding="utf-8", newline="") as handle:
        csv.writer(handle, delimiter="\t", lineterminator="\n").writerows(sorted(ledger))
    print(f"next: tools/client-240/check-shard.sh {args.cluster}")


if __name__ == "__main__":
    main()
