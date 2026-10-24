#!/usr/bin/env python3
"""Merge tiny-v2 cluster shards into one file, grouping entries by class.

Usage: merge-tiny.py OUT.tiny SHARD.tiny...
"""
import sys

out, shards = sys.argv[1], sys.argv[2:]
classes = {}
renames = {}
order = []
for shard in shards:
    owner = None
    with open(shard, encoding="utf-8") as handle:
        next(handle)
        for raw in handle:
            line = raw.rstrip("\n")
            if not line or line.startswith("#"):
                continue
            parts = line.split("\t")
            if parts[0] == "c":
                owner = parts[1]
                if owner not in classes:
                    classes[owner] = []
                    order.append(owner)
                if parts[1] != parts[2]:
                    renames[owner] = parts[2]
            else:
                classes[owner].append(line)
with open(out, "w", encoding="utf-8", newline="") as handle:
    handle.write("tiny\t2\t0\tsemantic\treadable\n")
    for owner in order:
        handle.write(f"c\t{owner}\t{renames.get(owner, owner)}\n")
        for line in classes[owner]:
            handle.write(line + "\n")
print(f"merged {len(shards)} shard(s), {len(order)} class block(s)")
