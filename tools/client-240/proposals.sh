#!/usr/bin/env bash
# proposals.sh : regenerate proposals/235-carry.tsv (T3 candidates, never part of the mapping).
source "$(dirname "$0")/env.sh"
client240_require_inputs
mkdir -p "$CLIENT240_CACHE"
client240_tool dump-classes "$CLIENT240_SEMANTIC_JAR" "$CLIENT240_CACHE/dump-240.tsv"
python3 "$(dirname "$0")/carry235.py" "$CLIENT240_CACHE/dump-240.tsv" \
  "$CLIENT240_REPO/references/rustosrs/RuneLite-melxin/runescape-client/src/main/java" \
  "$CLIENT240_DOCS/proposals/235-carry.tsv" --report
