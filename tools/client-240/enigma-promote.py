#!/usr/bin/env python3
"""Promote reviewed Enigma draft renames into a client-240 cluster shard and ledger shard.

The Enigma session works in the ORIGINAL obfuscated namespace (class `aa`, member `ae`) and saves
`draft.tiny` there. Production shards are keyed by the stable semantic names of the pinned
rl-suite jar (`classKY`, `field4053`, `method6623`). This tool translates one into the other by
matching the semantic jar's `@ObfuscatedName` values, so a draft rename lands on exactly one
stable symbol or the promotion fails closed.

Nothing is guessed: an unmatched or ambiguous draft entry, a collision with an existing shard,
an illegal identifier, or a stable symbol that is already readable aborts the run. The output
shard still has to pass tools/client-240/check-shard.sh (and verify.sh) before it is committed.

Usage:
  enigma-promote.py --session SESSION_DIR --cluster NN-name --tier T4 --confidence B \
      --evidence "cited method and reason" [--rust-owner crate::module] \
      [--evidence-file ROWS.tsv] [--dump DUMP.tsv] [--docs DIR] [--force] [--dry-run]

ROWS.tsv (optional, tab separated, no header): stable_symbol, tier, confidence, evidence, rust_owner.
It overrides the command-line defaults for the listed symbols.
"""
import argparse
import csv
import os
from pathlib import Path
import re
import subprocess
import sys
import tempfile

ROOT = Path(__file__).resolve().parents[2]
STABLE_CLASS = re.compile(r"class[A-Z0-9]+")
STABLE_MEMBER = re.compile(r"(field|method)\d+")
IDENT = re.compile(r"[A-Za-z_][A-Za-z0-9_]*$")
KEYWORDS = set(
    "abstract assert boolean break byte case catch char class const continue default do double else enum "
    "extends final finally float for goto if implements import instanceof int interface long native new "
    "package private protected public return short static strictfp super switch synchronized this throw "
    "throws transient try void volatile while true false null var record yield _".split()
)
TIERS = {"T1", "T2", "T3", "T4"}
CONFIDENCE = {"A", "B", "C"}
LEDGER_COLUMNS = ["stable_symbol", "class", "original_obf", "readable", "kind", "tier", "confidence",
                  "evidence", "rust_owner", "cluster"]


class PromoteError(Exception):
    pass


def parse_tiny(path):
    """Yield ('c', obf, named) and ('f'|'m', owner_obf, desc, obf, named) from a tiny-v2 file."""
    owner = None
    with open(path, encoding="utf-8") as handle:
        header = next(handle).rstrip("\n").split("\t")
        if header[:2] != ["tiny", "2"]:
            raise PromoteError(f"{path}: not a tiny-v2 file")
        for raw in handle:
            line = raw.rstrip("\n")
            if not line or line.startswith("#"):
                continue
            parts = line.split("\t")
            if parts[0] == "c":
                owner = parts[1]
                yield ("c", parts[1], parts[2] if len(parts) > 2 else "")
            elif parts[0] == "" and len(parts) >= 5 and parts[1] in ("f", "m"):
                yield (parts[1], owner, parts[2], parts[3], parts[4])
            elif parts[0] == "" and len(parts) == 4 and parts[1] in ("f", "m"):
                yield (parts[1], owner, parts[2], parts[3], "")


def load_dump(path):
    """Index the semantic jar: classes by obf and semantic name, members by (class, obf, desc)."""
    classes_by_obf, classes_by_stable = {}, {}
    members = {}
    with open(path, encoding="utf-8") as handle:
        for raw in handle:
            parts = raw.rstrip("\n").split("\t")
            if parts[0] == "C":
                stable, obf = parts[1], parts[5]
                classes_by_stable[stable] = obf
                if obf != "-":
                    classes_by_obf[obf] = stable
                else:
                    classes_by_obf.setdefault(stable, stable)
            elif parts[0] in ("F", "M"):
                stable_class, name, desc, obf = parts[1], parts[2], parts[3], parts[5]
                kind = "field" if parts[0] == "F" else "method"
                members.setdefault((stable_class, obf if obf != "-" else name, desc), []).append((kind, name, obf))
    return classes_by_obf, classes_by_stable, members


def to_semantic_desc(desc, classes_by_obf):
    """Rewrite `Laaa;` references from the original namespace into semantic class names."""
    def swap(match):
        name = match.group(1)
        return "L" + classes_by_obf.get(name, name) + ";"
    return re.sub(r"L([^;]+);", swap, desc)


def existing_names(docs, skip_cluster):
    """Readable names already taken by other shards, keyed by stable symbol and by class."""
    taken, readable_classes = {}, {}
    for shard in sorted((docs / "mappings" / "clusters").glob("*.tiny")):
        if shard.stem == skip_cluster:
            continue
        for entry in parse_tiny(shard):
            if entry[0] == "c":
                if entry[1] != entry[2]:
                    taken[entry[1]] = entry[2]
                    readable_classes[entry[2]] = entry[1]
            else:
                taken[f"{entry[1]}.{entry[3]}"] = entry[4]
    return taken, readable_classes


def collect(session, dump, docs, cluster):
    draft = Path(session) / "draft.tiny"
    if not draft.is_file():
        raise PromoteError(f"no draft.tiny in {session}")
    classes_by_obf, classes_by_stable, members = load_dump(dump)
    taken, readable_classes = existing_names(docs, cluster)
    renames = []  # (kind, stable_class, stable_symbol_member_or_None, obf, desc, readable)
    problems = []
    for entry in parse_tiny(draft):
        if entry[0] == "c":
            _, obf, named = entry
            stable = classes_by_obf.get(obf)
            if stable is None:
                problems.append(f"draft class {obf} is not in the semantic jar")
                continue
            if named in ("", obf, stable) or not STABLE_CLASS.fullmatch(stable):
                continue
            renames.append(("class", stable, None, obf, "", named))
            continue
        kind, owner_obf, desc, obf, named = entry
        if named in ("", obf) or named.startswith("bytecode_"):
            continue  # unnamed, or an automatic keyword-safe alias
        stable_class = classes_by_obf.get(owner_obf)
        if stable_class is None:
            problems.append(f"draft member {owner_obf}.{obf}: class not in the semantic jar")
            continue
        found = members.get((stable_class, obf, to_semantic_desc(desc, classes_by_obf)), [])
        found = [m for m in found if m[0] == ("field" if kind == "f" else "method")]
        if len(found) != 1:
            problems.append(f"draft member {owner_obf}.{obf} {desc}: {len(found)} semantic matches")
            continue
        member_kind, stable_name, member_obf = found[0]
        if not STABLE_MEMBER.fullmatch(stable_name):
            problems.append(f"{stable_class}.{stable_name}: already API/semantically named; not renamable")
            continue
        renames.append((member_kind, stable_class, stable_name, member_obf, to_semantic_desc(desc, classes_by_obf), named))
    seen_names = {}
    for kind, stable_class, stable_name, obf, desc, readable in renames:
        symbol = stable_class if kind == "class" else f"{stable_class}.{stable_name}"
        if not IDENT.match(readable) or readable in KEYWORDS:
            problems.append(f"{symbol}: '{readable}' is not a legal Java identifier")
        if symbol in taken and taken[symbol] != readable:
            problems.append(f"{symbol}: already mapped to '{taken[symbol]}' by another shard")
        if kind == "class" and readable in readable_classes and readable_classes[readable] != symbol:
            problems.append(f"{symbol}: readable class name '{readable}' already used by {readable_classes[readable]}")
        scope = (kind, stable_class if kind != "class" else "", readable, desc if kind == "method" else "")
        if scope in seen_names:
            problems.append(f"{symbol}: duplicate readable name '{readable}' with {seen_names[scope]}")
        seen_names[scope] = symbol
    return renames, problems


def evidence_rows(path):
    rows = {}
    if not path:
        return rows
    with open(path, encoding="utf-8") as handle:
        for line in csv.reader(handle, delimiter="\t"):
            if not line or line[0].startswith("#"):
                continue
            if len(line) < 4:
                raise PromoteError(f"{path}: row needs stable_symbol, tier, confidence, evidence")
            rows[line[0]] = (line[1], line[2], line[3], line[4] if len(line) > 4 else "-")
    return rows


def write_outputs(args, renames, docs):
    overrides = evidence_rows(args.evidence_file)
    defaults = (args.tier, args.confidence, args.evidence, args.rust_owner)
    ledger = []
    by_class = {}
    for kind, stable_class, stable_name, obf, desc, readable in renames:
        symbol = stable_class if kind == "class" else f"{stable_class}.{stable_name}"
        tier, confidence, evidence, owner = overrides.get(symbol, defaults)
        if tier not in TIERS or confidence not in CONFIDENCE:
            raise PromoteError(f"{symbol}: tier/confidence must be one of {sorted(TIERS)}/{sorted(CONFIDENCE)}")
        if not evidence or evidence == "-":
            raise PromoteError(f"{symbol}: evidence is required")
        ledger.append([symbol, stable_class, obf, readable, kind, tier, confidence, evidence, owner or "-", args.cluster])
        entry = by_class.setdefault(stable_class, {"class": None, "lines": []})
        if kind == "class":
            entry["class"] = readable
        else:
            entry["lines"].append(f"\t{'f' if kind == 'field' else 'm'}\t{desc}\t{stable_name}\t{readable}")
    unknown = set(overrides) - {row[0] for row in ledger}
    if unknown:
        raise PromoteError(f"evidence rows for symbols with no draft rename: {sorted(unknown)}")
    shard = ["tiny\t2\t0\tsemantic\treadable"]
    for stable_class in sorted(by_class):
        entry = by_class[stable_class]
        shard.append(f"c\t{stable_class}\t{entry['class'] or stable_class}")
        shard.extend(entry["lines"])
    shard_text = "\n".join(shard) + "\n"
    ledger.sort(key=lambda row: row[0])
    shard_path = docs / "mappings" / "clusters" / f"{args.cluster}.tiny"
    ledger_path = docs / "ledger" / "clusters" / f"{args.cluster}.tsv"
    if args.dry_run:
        print(shard_text, end="")
        print(f"dry run: {len(ledger)} rename(s); nothing written")
        return
    for path in (shard_path, ledger_path):
        if path.exists() and not args.force:
            raise PromoteError(f"{path} exists; pass --force to replace it")
    shard_path.write_text(shard_text, encoding="utf-8")
    with open(ledger_path, "w", encoding="utf-8", newline="") as handle:
        writer = csv.writer(handle, delimiter="\t", lineterminator="\n")
        writer.writerows(ledger)  # cluster ledger shards carry no header row
    print(f"wrote {shard_path.relative_to(ROOT)} and {ledger_path.relative_to(ROOT)}: {len(ledger)} rename(s)")
    print(f"next: tools/client-240/check-shard.sh {args.cluster}")


def build_dump(out):
    script = (f'source "{ROOT}/tools/client-240/env.sh" && '
              f'client240_tool dump-classes "$CLIENT240_SEMANTIC_JAR" "{out}"')
    result = subprocess.run(["bash", "-c", script], capture_output=True, text=True)
    if result.returncode != 0:
        raise PromoteError("dump-classes failed:\n" + result.stdout + result.stderr)


def main():
    parser = argparse.ArgumentParser(description=__doc__, formatter_class=argparse.RawDescriptionHelpFormatter)
    parser.add_argument("--session", required=True, help="Enigma session directory containing draft.tiny")
    parser.add_argument("--cluster", required=True, help="cluster name, e.g. 19-widgets")
    parser.add_argument("--tier", default="T4")
    parser.add_argument("--confidence", default="B")
    parser.add_argument("--evidence", default="")
    parser.add_argument("--rust-owner", default="-")
    parser.add_argument("--evidence-file")
    parser.add_argument("--dump", help="precomputed dump-classes TSV of the semantic jar")
    parser.add_argument("--docs", default=str(ROOT / "docs/reference/client-240"))
    parser.add_argument("--force", action="store_true")
    parser.add_argument("--dry-run", action="store_true")
    args = parser.parse_args()
    if not re.fullmatch(r"[0-9][0-9]+-[a-z0-9]+(-[a-z0-9]+)*", args.cluster):
        parser.error("invalid cluster name")
    docs = Path(args.docs)
    try:
        with tempfile.TemporaryDirectory(dir=ROOT / ".cache" / "client-240") as scratch:
            dump = args.dump or os.path.join(scratch, "dump.tsv")
            if not args.dump:
                build_dump(dump)
            renames, problems = collect(args.session, dump, docs, args.cluster)
            if problems:
                print("\n".join(problems))
                raise PromoteError(f"{len(problems)} problem(s); nothing written")
            if not renames:
                raise PromoteError("the draft contains no reviewed renames")
            write_outputs(args, renames, docs)
    except PromoteError as error:
        print(f"enigma-promote: {error}", file=sys.stderr)
        sys.exit(1)


if __name__ == "__main__":
    main()
