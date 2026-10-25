#!/usr/bin/env python3
"""Join a live packet capture (tools/parity/Packet240Agent) with the static packet tables.

Reads the capture TSV plus `inputs/server-packets.tsv` and `inputs/client-packets.tsv` and writes
`inputs/packet-trace-240.tsv`: one row per (direction, opcode) with the table name and length, the length the
client actually used, observed payload sizes, and (inbound) whether the handler consumed exactly the payload.
Runtime evidence only: it confirms or contradicts the static tables for the packets seen, nothing more.
"""
import argparse
from collections import defaultdict
from pathlib import Path

ROOT = Path(__file__).resolve().parents[2]
INPUTS = ROOT / "docs/reference/client-240/inputs"
COLUMNS = ["direction", "opcode", "name", "table_length", "client_length", "length_ok", "seen", "payload_min",
           "payload_max", "short_reads", "callers", "status"]


def table(path):
    rows = {}
    for line in path.read_text(encoding="utf-8").splitlines():
        if line and not line.startswith("#"):
            parts = line.split("\t")
            rows[int(parts[0])] = (parts[1], int(parts[2]))
    return rows


def header_bytes(declared):
    """Bytes before the payload in an outbound packet: opcode byte plus the length prefix."""
    return 1 + {-1: 1, -2: 2}.get(declared, 0)


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("capture", type=Path, nargs="+")
    parser.add_argument("--output", type=Path, default=INPUTS / "packet-trace-240.tsv")
    args = parser.parse_args()
    tables = {"in": table(INPUTS / "server-packets.tsv"), "out": table(INPUTS / "client-packets.tsv")}
    seen = defaultdict(lambda: {"count": 0, "min": None, "max": 0, "short": 0, "callers": set(), "declared": set()})
    for path in args.capture:
        lines = path.read_text(encoding="utf-8").splitlines()
        columns = lines[0].split("\t")
        for line in lines[1:]:
            fields = dict(zip(columns, line.split("\t")))
            if fields["direction"] not in tables:
                continue
            opcode = int(fields["opcode"])
            entry = seen[(fields["direction"], opcode)]
            count = int(fields["count"])
            actual = int(fields["actual_length"])
            if fields["direction"] == "out":
                actual -= header_bytes(int(fields["declared_length"]))
            entry["count"] += count
            entry["min"] = actual if entry["min"] is None else min(entry["min"], actual)
            entry["max"] = max(entry["max"], actual)
            entry["declared"].add(int(fields["declared_length"]))
            if "consumed_mismatches" in fields:
                entry["short"] += int(fields["consumed_mismatches"])
            else:  # older captures only carry sampled cursors
                entry["short"] += sum(1 for sample in fields.get("samples", "").split(",")
                                      if sample.startswith("consumed=")
                                      and int(sample.split("=")[1].split(":")[0]) != int(fields["actual_length"]))
            entry["callers"].update(c for c in fields.get("callers", "").split(",") if c)
    rows, problems = [], 0
    for (direction, opcode), entry in sorted(seen.items()):
        name, expected = tables[direction].get(opcode, ("UNKNOWN", None))
        client = ",".join(str(v) for v in sorted(entry["declared"]))
        ok = expected is not None and entry["declared"] == {expected}
        if expected is not None and expected >= 0:
            ok = ok and entry["min"] == entry["max"] == expected
        status = "ok"
        if not ok:
            status = "length-mismatch"
        elif entry["short"]:
            status = "short-read"
        problems += status != "ok"
        rows.append([direction, opcode, name, "" if expected is None else expected, client, "yes" if ok else "no",
                     entry["count"], entry["min"], entry["max"], entry["short"],
                     ",".join(sorted(entry["callers"])), status])
    args.output.write_text("\t".join(COLUMNS) + "\n" + "".join("\t".join(map(str, row)) + "\n" for row in rows),
                           encoding="utf-8")
    inbound = sum(1 for row in rows if row[0] == "in")
    print(f"{len(rows)} opcodes seen ({inbound} inbound, {len(rows) - inbound} outbound), {problems} flagged: {args.output}")
    for row in rows:
        if row[-1] != "ok":
            print("  ", *row)


if __name__ == "__main__":
    main()
