#!/usr/bin/env bash
# remap.sh [forward|roundtrip] OUT.jar : apply merged.tiny to the semantic jar.
#   forward   semantic -> readable  (the clean-name jar)
#   roundtrip forward then readable -> semantic (must equal the input; see verify.sh)
source "$(dirname "$0")/env.sh"
client240_require_inputs
mode="${1:?mode forward|roundtrip}"; out="$(client240_output "${2:?output jar}")"
map="${CLIENT240_MAP:-$CLIENT240_DOCS/mappings/merged.tiny}"
mkdir -p "$CLIENT240_CACHE" "$(dirname "$out")"
run() { "$CLIENT240_JAVA" -cp "$CLIENT240_REMAPPER" net.fabricmc.tinyremapper.Main "$@"; }
case "$mode" in
  forward) run "$CLIENT240_SEMANTIC_JAR" "$out" "$map" semantic readable ;;
  roundtrip)
    forward="$(dirname "$out")/forward.jar"
    run "$CLIENT240_SEMANTIC_JAR" "$forward" "$map" semantic readable
    run "$forward" "$out" "$map" readable semantic ;;
  *) echo "unknown mode $mode" >&2; exit 2 ;;
esac
