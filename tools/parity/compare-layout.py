#!/usr/bin/env python3
"""Diff the Rust client's replayed widget layout against a live widget dump from the pinned reference client.

RUST.tsv comes from `reference_replay`; DUMP.json from `record_ui.py dump` / `reference_dump_widgets` taken from the
same session. Widgets are keyed (group, component, child index; -1 for static components). Only widgets the live
client reports as visible (not hidden by themselves or an ancestor) are compared; the Rust tree already prunes hidden
ones. Prints counts and the first differences per category.
"""
import argparse
import collections
import json
from pathlib import Path


def live(path):
    nodes = {}

    def walk(node, clipped):
        key = (node["group"], node["component"], node["child"])
        # (-1, -1) = never laid out by the reference; children of a zero-area container are clipped away.
        if not node["hidden"] and node["bounds"][:2] != [-1, -1] and not clipped:
            nodes.setdefault(key, tuple(node["bounds"]))
        empty = node["type"] == 0 and (node["bounds"][2] <= 0 or node["bounds"][3] <= 0)
        for child in node.get("children", []):
            walk(child, clipped or empty)

    for root in json.loads(Path(path).read_text())["roots"]:
        walk(root, False)
    return nodes


def rust(path):
    nodes = {}
    for line in Path(path).read_text().splitlines()[1:]:
        g, c, ch, x, y, w, h, *clip = map(int, line.split("\t"))
        if clip and (clip[0] <= 0 or clip[1] <= 0):  # clipped away entirely: the reference never lays these out
            continue
        nodes.setdefault((g, c, ch), (x, y, w, h))
    return nodes


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("rust")
    parser.add_argument("dump")
    parser.add_argument("--show", type=int, default=12)
    args = parser.parse_args()
    a, b = live(args.dump), rust(args.rust)
    # RuneLite reports (-1, -1) for widgets with no painted extent; compare only widgets with area on both sides.
    a = {k: v for k, v in a.items() if v[2] > 0 and v[3] > 0}
    b = {k: v for k, v in b.items() if v[2] > 0 and v[3] > 0}
    only_live = sorted(set(a) - set(b))
    only_rust = sorted(set(b) - set(a))
    both = sorted(set(a) & set(b))
    wrong = [k for k in both if a[k] != b[k]]
    print(f"live visible {len(a)}, rust painted {len(b)}, matched {len(both)}, exact bounds {len(both) - len(wrong)}")
    print(f"only live {len(only_live)}, only rust {len(only_rust)}, bounds differ {len(wrong)}")
    by_group = collections.Counter(k[0] for k in only_live)
    print("only-live by group:", dict(by_group.most_common(12)))
    by_group = collections.Counter(k[0] for k in only_rust)
    print("only-rust by group:", dict(by_group.most_common(12)))
    by_group = collections.Counter(k[0] for k in wrong)
    print("bounds-differ by group:", dict(by_group.most_common(12)))
    for title, keys in (("only live", only_live), ("only rust", only_rust)):
        print(title)
        for k in keys[: args.show]:
            print("  ", k, (a if title == "only live" else b)[k])
    print("bounds differ (live -> rust)")
    for k in wrong[: args.show]:
        print("  ", k, a[k], "->", b[k])


if __name__ == "__main__":
    main()
