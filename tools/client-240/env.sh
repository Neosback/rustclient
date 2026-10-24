# Shared repository-local client-240 toolchain. Sourced, never executed.
set -euo pipefail
CLIENT240_REPO="$(cd "$(dirname "${BASH_SOURCE[0]}")/../.." && pwd)"
CLIENT240_DOCS="${CLIENT240_DOCS:-$CLIENT240_REPO/docs/reference/client-240}"
CLIENT240_CACHE="${CLIENT240_CACHE:-$CLIENT240_REPO/.cache/client-240}"
CLIENT240_TOOLS="${CLIENT240_TOOLS:-$CLIENT240_REPO/tools/client-240/vendor}"
CLIENT240_SEMANTIC_JAR="${CLIENT240_SEMANTIC_JAR:-$CLIENT240_REPO/docs/reference/client-240/inputs/injected-client-1.12.39-deob-semantic.jar}"
CLIENT240_INPUT_JAR="$CLIENT240_REPO/references/gamepacks/osrs-240/injected-client-1.12.39.jar"
CLIENT240_INPUT_SHA="25f42961c400bd9dfff1554402441c0ba6d1cffd011163cb9b0b4c42ae194f85"
CLIENT240_SEMANTIC_SHA="7ef51916eb8f47ce12d8787981d7d1dc96314e04c1e339f7fa57b94ad52116dc"
export CLIENT240_CACHE CLIENT240_DOCS CLIENT240_TOOLS CLIENT240_SEMANTIC_JAR
client240_python="$CLIENT240_REPO/tools/client-240/toolchain.py"
client240_runtime="$(python3 "$client240_python" --runtime)" || { return 1 2>/dev/null || exit 1; }
CLIENT240_JAVA="${client240_runtime%%$'\n'*}"
CLIENT240_JAVAP="${client240_runtime#*$'\n'}"
export CLIENT240_JAVA CLIENT240_JAVAP
CLIENT240_DOCS="$(python3 "$client240_python" --docs "$CLIENT240_DOCS")"
CLIENT240_ASM="$CLIENT240_TOOLS/tiny_deps/asm-9.6.jar:$CLIENT240_TOOLS/tiny_deps/asm-tree-9.6.jar:$CLIENT240_TOOLS/tiny_deps/asm-commons-9.6.jar:$CLIENT240_TOOLS/tiny_deps/asm-util-9.6.jar:$CLIENT240_TOOLS/tiny_deps/asm-analysis-9.6.jar"
CLIENT240_REMAPPER="$CLIENT240_TOOLS/tiny-remapper-0.10.3.jar:$CLIENT240_TOOLS/mapping-io-0.6.0.jar:$CLIENT240_ASM"
CLIENT240_VINEFLOWER="$CLIENT240_TOOLS/vineflower-1.12.0.jar"
CLIENT240_TOOL_SRC="$CLIENT240_REPO/tools/client-240/java/Client240Tool.java"
client240_sha() { python3 "$client240_python" --sha "$1"; }
client240_require_inputs() { python3 "$client240_python" --check; }
client240_output() { python3 "$client240_python" --output "$1"; }
client240_tool() { "$CLIENT240_JAVA" --source 17 -cp "$CLIENT240_ASM" "$CLIENT240_TOOL_SRC" "$@"; }
