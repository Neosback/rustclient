#!/usr/bin/env bash
# Cross-check one semantic class with the repo-pinned CFR decompiler.
source "$(dirname "$0")/env.sh"
client240_require_inputs
class="${1:?semantic class name}"
[[ "$class" =~ ^[A-Za-z_][A-Za-z0-9_\$]*$ ]] || { echo "invalid semantic class name" >&2; exit 2; }
out="$(client240_output "${2:-$CLIENT240_CACHE/cfr/$class}")"
mkdir -p "$out"
"$CLIENT240_JAVA" -jar "$CLIENT240_TOOLS/cfr-0.152.jar" "$CLIENT240_SEMANTIC_JAR" --jarfilter "^${class}$" --outputdir "$out" --silent true
[[ -s "$out/$class.java" ]] || { echo "CFR produced no source for $class" >&2; exit 1; }
echo "CFR source: $out/$class.java"
