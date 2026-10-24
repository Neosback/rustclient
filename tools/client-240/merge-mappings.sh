#!/usr/bin/env bash
# Concatenate cluster shards into the generated merged.tiny. Never hand-edit merged.tiny.
source "$(dirname "$0")/env.sh"
out="$CLIENT240_DOCS/mappings/merged.tiny"
shards=("$CLIENT240_DOCS"/mappings/clusters/*.tiny)
temporary="$(mktemp "$CLIENT240_DOCS/mappings/merged.XXXXXX")"
trap 'rm -f "$temporary"' EXIT
python3 "$(dirname "$0")/merge-tiny.py" "$temporary" "${shards[@]}"
mv "$temporary" "$out"
echo "wrote ${out#$CLIENT240_REPO/}"
