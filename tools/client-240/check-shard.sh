#!/usr/bin/env bash
# check-shard.sh NN-name : fast, isolated check of ONE cluster's shard (safe to run concurrently).
# Validates the shard against the semantic jar, cross-checks its ledger rows, and proves rename-only by
# remapping with just this shard forward and back in a private directory. Does not decompile and does
# not touch merged.tiny, NAMES.tsv or other clusters' files.
source "$(dirname "$0")/env.sh"
set +e
here="$(cd "$(dirname "$0")" && pwd)"
cluster="${1:?cluster name such as 01-buffer-framing}"
[[ "$cluster" =~ ^[0-9][0-9]+-[a-z0-9]+(-[a-z0-9]+)*$ ]] || { echo "invalid cluster name" >&2; exit 2; }
shard="$CLIENT240_DOCS/mappings/clusters/$cluster.tiny"
[[ -f "$shard" ]] || { echo "missing shard $shard" >&2; exit 2; }
client240_require_inputs || exit 1
work="$(client240_output "$CLIENT240_CACHE/shards/$cluster")"
rm -rf "$work"; mkdir -p "$work"
status=0
seed="$CLIENT240_DOCS/mappings/clusters/00-seed-api.tiny"
checkset=("$shard")
[[ "$cluster" == 00-* ]] || checkset+=("$seed")   # also catches double-naming of seeded fields
echo "== validate"; client240_tool validate "$CLIENT240_SEMANTIC_JAR" "${checkset[@]}" || status=1
echo "== ledger"; python3 "$here/ledger_check.py" "$CLIENT240_DOCS" --only "$cluster" || status=1
echo "== rename-only proof"
CLIENT240_MAP="$shard" "$here/remap.sh" forward "$work/readable.jar" >"$work/forward.log" 2>&1 || { echo "forward remap failed"; tail -5 "$work/forward.log"; status=1; }
CLIENT240_MAP="$shard" "$here/remap.sh" roundtrip "$work/roundtrip.jar" >"$work/roundtrip.log" 2>&1 || { echo "roundtrip remap failed"; tail -5 "$work/roundtrip.log"; status=1; }
if [[ -f "$work/roundtrip.jar" ]]; then
  client240_tool compare "$CLIENT240_SEMANTIC_JAR" "$work/roundtrip.jar" || status=1
  client240_tool annotations "$CLIENT240_SEMANTIC_JAR" | sort > "$work/base.txt"
  client240_tool annotations "$work/readable.jar" | sort > "$work/readable.txt"
  [[ -s "$work/base.txt" ]] || { echo "empty annotation evidence"; status=1; }
  cmp -s "$work/base.txt" "$work/readable.txt" && echo "annotations identical ($(wc -l < "$work/base.txt" | tr -d ' ') lines)" || { echo "ANNOTATION MISMATCH"; status=1; }
fi
rm -f "$work"/*.jar
[[ $status -eq 0 ]] && echo "check-shard $cluster: PASS" || echo "check-shard $cluster: FAIL"
exit $status
