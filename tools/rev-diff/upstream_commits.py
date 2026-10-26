#!/usr/bin/env python3
"""Classified list of upstream RuneLite commits between two revisions.

Usage: upstream_commits.py UPSTREAM_CLONE RANGE OUT.tsv

UPSTREAM_CLONE is a (blobless is enough) clone of https://github.com/runelite/runelite and RANGE a git
revision range, for example `f2e65dce..2a0776b3e`:

  f2e65dce  2026-01-14  "Update Legacy ID classes to 2026-01-14-rev235"  (first -rev235 content commit)
  2a0776b3  2026-09-23  "Update GameVals to 2026-09-23-c1-rev240"        (last -rev240 content commit
                                                                         before "cache: rev 241")

Bot content commits (GameVals, Legacy ID classes, Item variations, Quests, Widget IDs, Scripts, Script
arguments) are counted but not listed. `upstream_support_window` is the latest "api: update N" /
"cache: update N" marker commit at or before the commit date; it says when RuneLite *supported* a
revision, not when Jagex released it. `area` comes from the paths a commit touches.
"""
import csv
import re
import subprocess
import sys

BOT = re.compile(r"^Update (GameVals|Legacy ID classes|Item variations|Quests|Widget IDs|Scripts|Script arguments)")
MARKERS = [
    ("2026-01-23", "api-236"), ("2026-02-01", "cache-236"),
    ("2026-03-12", "api-237"), ("2026-03-15", "cache-237"),
    ("2026-04-23", "api-238"), ("2026-05-02", "cache-238"),
    ("2026-06-12", "api-239"), ("2026-06-24", "cache-239"),
]


def window(date):
    current = "before api-236"
    for marker_date, name in MARKERS:
        if date >= marker_date:
            current = f"after {name}"
    return current


def area(files):
    kinds = set()
    for f in files:
        if f.startswith("cache/"):
            kinds.add("cache")
        elif f.startswith("runelite-api/"):
            kinds.add("api")
        elif "/plugins/gpu/" in f or "/plugins/skybox/" in f:
            kinds.add("plugin:gpu")
        else:
            m = re.search(r"/plugins/([a-z0-9]+)/", f)
            if m:
                kinds.add("plugin:" + m.group(1))
            elif f.startswith("runelite-client/src/main/java"):
                kinds.add("rl-client")
            elif f.startswith("runelite-client/src/main/resources"):
                kinds.add("rl-client-resources")
            elif f.endswith((".kts", ".toml", ".properties", ".xml")) or f.startswith(("gradle", ".github")):
                kinds.add("build")
            else:
                kinds.add("other")
    return ",".join(sorted(kinds)) or "none"


def main(repo, rng, out):
    log = subprocess.check_output(
        ["git", "-C", repo, "log", rng, "--no-merges", "--reverse",
         "--format=@@%h\t%ad\t%s", "--date=short", "--name-only"], text=True)
    commits, current = [], None
    for line in log.split("\n"):
        if line.startswith("@@"):
            sha, date, subject = line[2:].split("\t", 2)
            current = {"sha": sha, "date": date, "subject": subject, "files": []}
            commits.append(current)
        elif line.strip() and current:
            current["files"].append(line.strip())
    bots = 0
    with open(out, "w", newline="", encoding="utf-8") as fh:
        writer = csv.writer(fh, delimiter="\t")
        writer.writerow(["sha", "author_date", "upstream_support_window", "area", "subject"])
        for c in commits:
            if BOT.match(c["subject"]):
                bots += 1
                continue
            writer.writerow([c["sha"], c["date"], window(c["date"]), area(c["files"]), c["subject"]])
    print(f"{len(commits)} non-merge commits, {bots} bot content updates not listed, {len(commits) - bots} listed")


if __name__ == "__main__":
    if len(sys.argv) != 4:
        sys.exit(__doc__)
    main(*sys.argv[1:])
