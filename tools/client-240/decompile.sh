#!/usr/bin/env bash
# decompile.sh IN.jar OUT_DIR : regenerate readable sources with the pinned Vineflower.
source "$(dirname "$0")/env.sh"
client240_require_inputs
in="${1:?input jar}"; out="${2:?output directory}"
rm -rf "$out"; mkdir -p "$out"
threads="${CLIENT240_THREADS:-$(sysctl -n hw.ncpu 2>/dev/null || nproc 2>/dev/null || echo 4)}"
# -mpm bounds pathological methods; -thr parallelizes classes.
"$CLIENT240_JAVA" -Xmx3g -jar "$CLIENT240_VINEFLOWER" -log=ERROR -thr="$threads" -mpm=60 "$in" "$out"
echo "decompiled $(find "$out" -name '*.java' | wc -l | tr -d ' ') class(es) into $out"
