#!/usr/bin/env bash
# Concatenate cluster shards into the generated merged.tiny. Never hand-edit merged.tiny.
source "$(dirname "$0")/env.sh"
out="$CLIENT240_DOCS/mappings/merged.tiny"
shards=("$CLIENT240_DOCS"/mappings/clusters/*.tiny)
python3 "$(dirname "$0")/merge-tiny.py" "$out" "${shards[@]}"
echo "wrote ${out#$CLIENT240_REPO/}"
