#!/usr/bin/env python3
"""Merge ledger shards into NAMES.tsv and cross-check them against the mapping shards.

Usage: ledger_check.py DOCS_DIR [--write] [--only CLUSTER]
Exit status is non-zero when a mapping entry lacks evidence or a ledger row lacks a mapping entry.
"""
import csv
import glob
import os
import re
import sys

COLUMNS = ["stable_symbol", "class", "original_obf", "readable", "kind", "tier", "confidence",
           "evidence", "rust_owner", "cluster"]
TIERS = {"T1", "T2", "T3", "T4"}
CONFIDENCE = {"A", "B", "C"}


def mapping_entries(path):
    entries = {}
    owner = None
    with open(path, encoding="utf-8") as handle:
        next(handle)
        for raw in handle:
            line = raw.rstrip("\n")
            if not line or line.startswith("#"):
                continue
            parts = line.split("\t")
            if parts[0] == "c":
                owner = parts[1]
                if parts[1] != parts[2]:
                    entries[owner] = ("class", parts[2])
            elif parts[0] == "" and parts[1] in ("f", "m"):
                entries[f"{owner}.{parts[3]}"] = ("field" if parts[1] == "f" else "method", parts[4])
    return entries


def main():
    docs = sys.argv[1]
    write = "--write" in sys.argv
    only = sys.argv[sys.argv.index("--only") + 1] if "--only" in sys.argv else None
    problems = []
    rows = {}
    for shard in sorted(glob.glob(os.path.join(docs, "ledger", "clusters", "*.tsv"))):
        if only and os.path.basename(shard) != only + ".tsv":
            continue
        with open(shard, encoding="utf-8") as handle:
            for number, row in enumerate(csv.reader(handle, delimiter="\t"), 1):
                if not row or row[0].startswith("#"):
                    continue
                where = f"{os.path.basename(shard)}:{number}"
                if len(row) != len(COLUMNS):
                    problems.append(f"{where}: expected {len(COLUMNS)} columns, found {len(row)}")
                    continue
                record = dict(zip(COLUMNS, row))
                if record["tier"] not in TIERS:
                    problems.append(f"{where}: bad tier {record['tier']}")
                if record["confidence"] not in CONFIDENCE:
                    problems.append(f"{where}: bad confidence {record['confidence']}")
                if record["tier"] in ("T3", "T4") and (not record["evidence"] or record["evidence"] == "-"):
                    problems.append(f"{where}: {record['tier']} entries need cited evidence")
                if record["stable_symbol"] in rows:
                    problems.append(f"{where}: duplicate ledger row {record['stable_symbol']}")
                rows[record["stable_symbol"]] = (record, where)
    mapped = {}
    for shard in sorted(glob.glob(os.path.join(docs, "mappings", "clusters", "*.tiny"))):
        if only and os.path.basename(shard) != only + ".tiny":
            continue
        for key, value in mapping_entries(shard).items():
            if key in mapped:
                problems.append(f"{os.path.basename(shard)}: {key} already mapped by another shard")
            mapped[key] = (value, os.path.basename(shard))
    for key, ((kind, readable), shard) in mapped.items():
        if key not in rows:
            problems.append(f"{shard}: {key} -> {readable} has no ledger row")
        else:
            record, where = rows[key]
            if record["readable"] != readable or record["kind"] != kind:
                problems.append(f"{where}: ledger says {record['kind']} {record['readable']}, mapping says {kind} {readable}")
    for key, (record, where) in rows.items():
        if key not in mapped:
            problems.append(f"{where}: ledger row {key} has no mapping entry")
    if write and not only:
        out = os.path.join(docs, "ledger", "NAMES.tsv")
        with open(out, "w", encoding="utf-8", newline="") as handle:
            writer = csv.writer(handle, delimiter="\t", lineterminator="\n")
            writer.writerow(COLUMNS)
            for key in sorted(rows):
                writer.writerow([rows[key][0][c] for c in COLUMNS])
    by_tier = {}
    for record, _ in rows.values():
        by_tier[record["tier"]] = by_tier.get(record["tier"], 0) + 1
    print(f"ledger: {len(rows)} rows ({', '.join(f'{k}={v}' for k, v in sorted(by_tier.items()))}), "
          f"{len(mapped)} mapping entries")
    if problems:
        print("\n".join(problems))
        print(f"ledger: FAILED, {len(problems)} problem(s)")
        sys.exit(1)
    print("ledger: OK")


if __name__ == "__main__":
    main()
