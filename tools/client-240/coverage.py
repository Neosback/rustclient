#!/usr/bin/env python3
"""Count how much of the semantic jar's generic naming a mapping covers.

Usage: coverage.py SEMANTIC_SOURCES_DIR MERGED_TINY
"""
import glob
import os
import re
import sys

sources, merged = sys.argv[1], sys.argv[2]
generic = {"class": set(), "field": set(), "method": set()}
for path in glob.glob(os.path.join(sources, "*.java")):
    text = open(path, encoding="utf-8", errors="replace").read()
    for kind, pattern in (("field", r"\bfield\d+\b"), ("method", r"\bmethod\d+\b")):
        for match in re.findall(pattern, text):
            generic[kind].add(match)
    stem = os.path.basename(path)[:-5]
    if re.fullmatch(r"class[A-Z0-9]+", stem):
        generic["class"].add(stem)
named = {"class": 0, "field": 0, "method": 0}
with open(merged, encoding="utf-8") as handle:
    next(handle)
    for line in handle:
        parts = line.rstrip("\n").split("\t")
        if parts[0] == "c" and parts[1] != parts[2]:
            named["class"] += 1
        elif parts[0] == "" and parts[1] == "f":
            named["field"] += 1
        elif parts[0] == "" and parts[1] == "m":
            named["method"] += 1
for kind in ("class", "field", "method"):
    total = len(generic[kind])
    print(f"coverage {kind}: {named[kind]} named of {total} generic ({100 * named[kind] / max(total, 1):.1f}%)")
