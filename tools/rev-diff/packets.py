#!/usr/bin/env python3
"""Per-packet comparison of the revision 235 and 240 protocol tables.

Usage: packets.py RL_SUITE_DATA_DIR OUT.tsv

RL_SUITE_DATA_DIR is rl-suite's `data/` directory (`/Users/tylercovalt/Documents/rl-suite-main/data`
locally); the tables are `rev-NNN/mappings/{client,server}-packets.tsv`. Packets are joined by *name*:
numeric ids and byte encodings are re-scrambled by the obfuscator every revision, so they are never
evidence that two packets are the same message. Obfuscated buffer-method names are stripped before
comparing field layouts.

Status values:
  identical   same field layout (types, order and byte encodings)
  reencoded   same field types in the same order, different byte encodings
  reordered   same multiset of field types in a different order
  structural  different field types/count
  added       named in 240 only
  removed     named in 235 only
`first_step` is the first revision step (235->236 ... 239->240) at which the name appeared, disappeared
or its layout first differed from the previous revision.
"""
import csv
import re
import sys
from collections import Counter

REVS = list(range(235, 241))
OBF = re.compile(r"\([a-zA-Z0-9_$]+\)")


def load(data, rev, kind):
    rows = {}
    with open(f"{data}/rev-{rev}/mappings/{kind}-packets.tsv", encoding="utf-8") as fh:
        for line in fh:
            if line.startswith("#") or not line.strip():
                continue
            p = line.rstrip("\n").split("\t")
            if p[1] == "-":
                continue  # unnamed table slot
            rows[p[1]] = {"id": p[0], "len": p[2], "fields": OBF.sub("", p[4]) if len(p) > 4 else ""}
    return rows


def types(fields):
    out = []
    for token in re.split(r",(?![^\[]*\])", fields):
        token = token.strip()
        if token and token != "-":
            out.append(re.sub(r"\[.*\]", "", token))
    return out


def status(a, b):
    if a["fields"] == b["fields"]:
        return "identical"
    ta, tb = types(a["fields"]), types(b["fields"])
    if ta == tb:
        return "reencoded"
    if Counter(ta) == Counter(tb):
        return "reordered"
    return "structural"


def main(data, out):
    header = ["direction", "name", "status", "first_step", "id235", "id240", "len235", "len240", "fields235", "fields240"]
    counts = Counter()
    with open(out, "w", newline="", encoding="utf-8") as fh:
        writer = csv.writer(fh, delimiter="\t")
        writer.writerow(header)
        for kind in ("client", "server"):
            tables = {rev: load(data, rev, kind) for rev in REVS}
            a, b = tables[235], tables[240]
            for name in sorted(set(a) | set(b)):
                if name in a and name in b:
                    st = status(a[name], b[name])
                elif name in b:
                    st = "added"
                else:
                    st = "removed"
                step = ""
                if st != "identical":
                    for prev, nxt in zip(REVS, REVS[1:]):
                        p, n = tables[prev].get(name), tables[nxt].get(name)
                        if (p is None) != (n is None) or (p and n and p["fields"] != n["fields"]):
                            step = f"{prev}->{nxt}"
                            break
                counts[(kind, st)] += 1
                ra, rb = a.get(name), b.get(name)
                writer.writerow([
                    kind, name, st, step,
                    ra["id"] if ra else "", rb["id"] if rb else "",
                    ra["len"] if ra else "", rb["len"] if rb else "",
                    ra["fields"] if ra else "", rb["fields"] if rb else "",
                ])
    for (kind, st), n in sorted(counts.items()):
        print(f"{kind:7s} {st:11s} {n}")


if __name__ == "__main__":
    if len(sys.argv) != 3:
        sys.exit(__doc__)
    main(sys.argv[1], sys.argv[2])
