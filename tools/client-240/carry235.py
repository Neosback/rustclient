#!/usr/bin/env python3
"""T3 proposals: carry readable names from the hand-named 235 client to the 240 semantic jar.

Usage: carry235.py DUMP240.tsv SOURCES235_DIR OUT_PROPOSALS.tsv [--report]

DUMP240.tsv comes from `Client240Tool dump-classes`. SOURCES235_DIR is
references/rustosrs/RuneLite-melxin/runescape-client/src/main/java (read-only).

Nothing here is a mapping. Output rows are proposals that a cluster agent must confirm against the
control flow before they enter a shard. Matching is structural: member order, modifiers and
normalized types inside already-paired classes, with class pairs growing by voting from the
types of matched members.
"""
import difflib
import os
import re
import sys
from collections import Counter, defaultdict

GENERIC = re.compile(r"(class[A-Z0-9]+|field\d+|method\d+)$")
PRIMITIVE = {"B": "byte", "C": "char", "D": "double", "F": "float", "I": "int", "J": "long",
             "S": "short", "Z": "boolean", "V": "void"}


# --------------------------------------------------------------------------- 240 side
def load240(path):
    classes = {}
    for line in open(path, encoding="utf-8"):
        p = line.rstrip("\n").split("\t")
        if p[0] == "C":
            classes[p[1]] = {"super": p[2], "ifaces": p[3].split(",") if p[3] else [], "access": int(p[4]),
                             "obf": p[5], "fields": [], "methods": [], "strings": set()}
        elif p[0] == "F":
            classes[p[1]]["fields"].append({"name": p[2], "desc": p[3], "access": int(p[4]), "obf": p[5]})
        elif p[0] == "M":
            if p[2].startswith("<"):
                continue
            classes[p[1]]["methods"].append({"name": p[2], "desc": p[3], "access": int(p[4]), "obf": p[5],
                                             "size": int(p[6]), "calls": int(p[7])})
        elif p[0] == "S":
            classes[p[1]]["strings"].add(p[2])
    return classes


def parse_desc(desc):
    """Descriptor -> list of type tokens ('int', 'int[]', ('L', internal), ...); return token last."""
    tokens, i = [], 0
    while i < len(desc):
        c = desc[i]
        if c in "()":
            i += 1
            continue
        dims = 0
        while desc[i] == "[":
            dims += 1
            i += 1
        if desc[i] == "L":
            j = desc.index(";", i)
            base = ("L", desc[i + 1:j])
            i = j + 1
        else:
            base = PRIMITIVE[desc[i]]
            i += 1
        tokens.append((base, dims))
    return tokens


# --------------------------------------------------------------------------- 235 side
ANNOT = re.compile(r'@(\w+)\((?:\s*(?:\w+\s*=\s*)?"([^"]*)")?')


def balanced(text):
    return text.count("(") <= text.count(")")


def parse235(root):
    classes = {}
    for name in sorted(os.listdir(root)):
        if not name.endswith(".java"):
            continue
        lines = open(os.path.join(root, name), encoding="utf-8", errors="replace").read().split("\n")
        info = {"file": name[:-5], "obf": None, "implements": None, "super": None, "fields": [], "methods": []}
        pending, depth_one, buf = [], None, ""
        header_seen = False
        i = 0
        while i < len(lines):
            line = lines[i]
            i += 1
            stripped = line.strip()
            if not header_seen:
                if stripped.startswith("@"):
                    text = stripped
                    while not balanced(text) and i < len(lines):
                        text += " " + lines[i].strip()
                        i += 1
                    pending.append(text)
                    continue
                m = re.match(r"(?:public |final |abstract |static )*(?:class|interface|enum)\s+(\w+)(?:<[^>]*>)?(?:\s+extends\s+([\w.]+))?", stripped)
                if m:
                    header_seen = True
                    info["super"] = m.group(2).split(".")[-1] if m.group(2) else None
                    for a in pending:
                        am = ANNOT.match(a)
                        if am and am.group(1) == "ObfuscatedName":
                            info["obf"] = am.group(2)
                        if am and am.group(1) == "Implements":
                            info["implements"] = am.group(2)
                    pending = []
                continue
            if not line.startswith("\t") or line.startswith("\t\t"):
                continue
            if stripped.startswith("@"):
                text = stripped
                while not balanced(text) and i < len(lines):
                    text += " " + lines[i].strip()
                    i += 1
                pending.append(text)
                continue
            if not stripped or stripped.startswith("//") or stripped.startswith("/*") or stripped == "}":
                pending = []
                continue
            decl = stripped
            while "(" in decl.split("=")[0] and not balanced(decl.split("{")[0]) and i < len(lines):
                decl += " " + lines[i].strip()
                i += 1
            annotations = {}
            for a in pending:
                am = ANNOT.match(a)
                if am:
                    annotations.setdefault(am.group(1), am.group(2))
            descriptor = None
            for a in pending:
                dm = re.search(r'descriptor\s*=\s*"([^"]*)"', a)
                if dm:
                    descriptor = dm.group(1)
            pending = []
            member = parse_member(decl)
            if member is None:
                continue
            member.update({"obf": annotations.get("ObfuscatedName"), "export": annotations.get("Export"),
                           "obf_desc": descriptor})
            (info["methods"] if member["kind"] == "method" else info["fields"]).append(member)
        classes[name[:-5]] = info
    return classes


MODS = {"public", "private", "protected", "static", "final", "abstract", "synchronized", "native",
        "volatile", "transient", "default", "strictfp"}


def parse_member(decl):
    head = decl.split("=")[0].split("{")[0].strip().rstrip(";").strip()
    words, rest = [], head
    while True:
        m = re.match(r"(\w+)\s+(.*)", rest)
        if m and m.group(1) in MODS:
            words.append(m.group(1))
            rest = m.group(2)
        else:
            break
    if "(" in rest:
        m = re.match(r"(?:<[^>]*>\s*)?([\w.<>,\[\]? ]+?)\s+(\w+)\s*\((.*)\)", rest)
        if not m:
            return None
        params = split_params(m.group(3))
        return {"kind": "method", "mods": set(words), "ret": norm_src(m.group(1)), "name": m.group(2),
                "params": [norm_src(p.rsplit(" ", 1)[0]) for p in params if p.strip()]}
    m = re.match(r"([\w.<>,\[\]? ]+?)\s+(\w+)$", rest)
    if not m:
        return None
    return {"kind": "field", "mods": set(words), "type": norm_src(m.group(1)), "name": m.group(2)}


def split_params(text):
    out, depth, cur = [], 0, ""
    for ch in text:
        if ch == "<":
            depth += 1
        elif ch == ">":
            depth -= 1
        if ch == "," and depth == 0:
            out.append(cur)
            cur = ""
        else:
            cur += ch
    if cur.strip():
        out.append(cur)
    return [p.strip() for p in out]


def norm_src(text):
    text = re.sub(r"<[^>]*>", "", text).strip()
    dims = text.count("[")
    base = text.replace("[]", "").strip().split(".")[-1].replace("final ", "").strip()
    if base in PRIMITIVE.values():
        return (base, dims)
    return (("L", base), dims)


# --------------------------------------------------------------------------- matching
def main():
    dump, src, out = sys.argv[1:4]
    report = "--report" in sys.argv
    c240 = load240(dump)
    c235 = parse235(src)
    by_implements = {(info["implements"] or name): name for name, info in c235.items()}
    # Initial class pairs: 240 classes already carrying a real (non classXX) name that 235 also has.
    pair = {name: by_implements[name] for name in c240 if not GENERIC.match(name) and name in by_implements}
    reverse = {v: k for k, v in pair.items()}

    def canon240(token):
        base, dims = token
        if isinstance(base, tuple):
            internal = base[1]
            if internal in c240:
                return (("L", pair.get(internal, "?")), dims)
            return (("L", internal.split("/")[-1]), dims)
        return (base, dims)

    def canon235(token):
        base, dims = token
        if isinstance(base, tuple):
            name = by_implements.get(base[1], base[1])
            if name in c235:
                return (("L", name if name in reverse else "?"), dims)
            return (("L", base[1]), dims)
        return (base, dims)

    def field_key240(f):
        return (bool(f["access"] & 8), canon240(parse_desc(f["desc"])[0]))

    def field_key235(f):
        return ("static" in f["mods"], canon235(f["type"]))

    def method_key240(m):
        tokens = parse_desc(m["desc"])
        return (bool(m["access"] & 8), tuple(canon240(t) for t in tokens[:-1]), canon240(tokens[-1]))

    def method_key235(m):
        return ("static" in m["mods"], tuple(canon235(t) for t in m["params"]), canon235(m["ret"]))

    proposals = {}
    for _ in range(8):
        votes = defaultdict(Counter)
        new_props = {}
        for n240, n235 in list(pair.items()):
            k240, k235 = c240[n240], c235[n235]
            seq240 = [field_key240(f) for f in k240["fields"]]
            seq235 = [field_key235(f) for f in k235["fields"]]
            for i240, i235, run in matched_pairs(seq240, seq235):
                f240, f235 = k240["fields"][i240], k235["fields"][i235]
                vote(votes, c240, c235, pair, by_implements, [parse_desc(f240["desc"])[0]], [f235["type"]])
                if f235["export"] and GENERIC.match(f240["name"]):
                    new_props[("field", n240, f240["name"], f240["desc"])] = (n235, f235["export"], run)
            seq240 = [method_key240(m) for m in k240["methods"]]
            seq235 = [method_key235(m) for m in k235["methods"]]
            for i240, i235, run in matched_pairs(seq240, seq235):
                m240, m235 = k240["methods"][i240], k235["methods"][i235]
                vote(votes, c240, c235, pair, by_implements, parse_desc(m240["desc"]), list(m235["params"]) + [m235["ret"]])
                if m235["export"] and GENERIC.match(m240["name"]):
                    new_props[("method", n240, m240["name"], m240["desc"])] = (n235, m235["export"], run)
        proposals = new_props
        grew = False
        for internal, counter in votes.items():
            if internal in pair or not GENERIC.match(internal):
                continue
            ranked = counter.most_common(2)
            best, count = ranked[0]
            runner = ranked[1][1] if len(ranked) > 1 else 0
            if count >= 2 and count > 2 * runner and best not in reverse:
                pair[internal] = best
                reverse[best] = internal
                grew = True
        if not grew:
            break

    rows = []
    for internal, name in sorted(pair.items()):
        if GENERIC.match(internal):
            rows.append(("class", internal, internal, "-", name, c235[name]["implements"] or name, "vote"))
    for (kind, owner, member, desc), (n235, export, run) in sorted(proposals.items()):
        rows.append((kind, owner, member, desc, n235, export, "unique-signature"))
    with open(out, "w", encoding="utf-8") as handle:
        handle.write("kind\tclass240\tmember240\tdesc240\tclass235\tname235\tbasis\n")
        for row in rows:
            handle.write("\t".join(row) + "\n")
    classes = sum(1 for r in rows if r[0] == "class")
    print(f"carry235: {len(pair)} class pairs ({len(pair) - classes} pre-named), "
          f"{classes} new class proposals, {len(rows) - classes} member proposals")
    if report:
        validate(rows, os.path.join(os.path.dirname(out) or ".", "..", "ledger", "clusters", "00-seed-api.tsv"))


def vote(votes, c240, c235, pair, by_implements, tokens240, tokens235):
    """Position-wise class votes from two aligned type lists."""
    if len(tokens240) != len(tokens235):
        return
    for t240, t235 in zip(tokens240, tokens235):
        base240, dims240 = t240
        base235, dims235 = t235
        if dims240 != dims235 or not isinstance(base240, tuple) or not isinstance(base235, tuple):
            continue
        internal = base240[1]
        name = by_implements.get(base235[1], base235[1])
        if internal in c240 and internal not in pair and name in c235:
            votes[internal][name] += 1


def matched_pairs(seq240, seq235):
    """Members whose normalized signature is unique, and equal, on both sides of a paired class.

    Order is deliberately ignored: the obfuscator reorders members, so positional alignment of
    same-typed fields proved unreliable (a 23% agreement with the deterministic T1 names).
    The third element is the group size, always 1 here, kept for the proposal format.
    """
    results = []
    for a, key in enumerate(seq240):
        if seq240.count(key) == 1 and seq235.count(key) == 1:
            results.append((a, seq235.index(key), 1))
    return results


def validate(rows, ledger_path):
    """Agreement with the deterministic T1 names, as a precision estimate."""
    if not os.path.exists(ledger_path):
        return
    t1 = {}
    for line in open(ledger_path, encoding="utf-8"):
        p = line.rstrip("\n").split("\t")
        if len(p) >= 4:
            owner, field = p[0].split(".")
            t1[(owner, field)] = p[3]
    agree = disagree = 0
    for row in rows:
        if row[0] == "field" and (row[1], row[2]) in t1:
            if t1[(row[1], row[2])].lower() == row[5].lower():
                agree += 1
            else:
                disagree += 1
    total = agree + disagree
    if total:
        print(f"carry235 precision check vs T1 names: {agree}/{total} agree ({100 * agree / total:.1f}%)")
    else:
        print("carry235 precision check: no overlap with T1 names")


if __name__ == "__main__":
    main()
