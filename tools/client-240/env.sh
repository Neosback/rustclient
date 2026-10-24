# Shared settings for the client-240 research toolchain. Sourced, never executed.
# Reference trees are read-only: every output goes to docs/reference/client-240 or .cache/client-240.
set -euo pipefail

CLIENT240_REPO="$(cd "$(dirname "${BASH_SOURCE[0]}")/../.." && pwd)"
CLIENT240_DOCS="$CLIENT240_REPO/docs/reference/client-240"
CLIENT240_CACHE="${CLIENT240_CACHE:-$CLIENT240_REPO/.cache/client-240}"

# Local research tools (Vineflower, tiny-remapper, mapping-io, ASM) and the rl-suite output jar.
CLIENT240_TOOLS="${CLIENT240_TOOLS:-$HOME/Documents/Java Deob}"
CLIENT240_JAVA="${CLIENT240_JAVA:-/opt/homebrew/opt/openjdk@17/bin/java}"
CLIENT240_SEMANTIC_JAR="${CLIENT240_SEMANTIC_JAR:-$CLIENT240_TOOLS/decompiled/rl-suite-240/injected-client-1.12.39-deob-semantic.jar}"

# Pinned identities. The semantic jar hash is the output.sha256 recorded in
# references/rl-suite-240/injected-client-1.12.39-deob-semantic.audit.json.
CLIENT240_INPUT_JAR="$CLIENT240_REPO/references/gamepacks/osrs-240/injected-client-1.12.39.jar"
CLIENT240_INPUT_SHA="25f42961c400bd9dfff1554402441c0ba6d1cffd011163cb9b0b4c42ae194f85"
CLIENT240_SEMANTIC_SHA="7ef51916eb8f47ce12d8787981d7d1dc96314e04c1e339f7fa57b94ad52116dc"

CLIENT240_ASM="$CLIENT240_TOOLS/tiny_deps/asm-9.6.jar:$CLIENT240_TOOLS/tiny_deps/asm-tree-9.6.jar:$CLIENT240_TOOLS/tiny_deps/asm-commons-9.6.jar:$CLIENT240_TOOLS/tiny_deps/asm-util-9.6.jar:$CLIENT240_TOOLS/tiny_deps/asm-analysis-9.6.jar"
CLIENT240_REMAPPER="$CLIENT240_TOOLS/tiny-remapper-0.10.3.jar:$CLIENT240_TOOLS/mapping-io-0.6.0.jar:$CLIENT240_ASM"
CLIENT240_VINEFLOWER="$CLIENT240_TOOLS/vineflower-1.12.0.jar"
CLIENT240_TOOL_SRC="$CLIENT240_REPO/tools/client-240/java/Client240Tool.java"

client240_sha() { shasum -a 256 "$1" | cut -d' ' -f1; }

client240_require_inputs() {
  local actual
  [[ -f "$CLIENT240_SEMANTIC_JAR" ]] || { echo "missing semantic jar: $CLIENT240_SEMANTIC_JAR (set CLIENT240_SEMANTIC_JAR)" >&2; return 1; }
  actual="$(client240_sha "$CLIENT240_SEMANTIC_JAR")"
  [[ "$actual" == "$CLIENT240_SEMANTIC_SHA" ]] || { echo "semantic jar hash mismatch: $actual" >&2; return 1; }
  actual="$(client240_sha "$CLIENT240_INPUT_JAR")"
  [[ "$actual" == "$CLIENT240_INPUT_SHA" ]] || { echo "original gamepack hash mismatch: $actual" >&2; return 1; }
  for jar in "$CLIENT240_VINEFLOWER" "$CLIENT240_TOOLS/tiny-remapper-0.10.3.jar" "$CLIENT240_TOOLS/mapping-io-0.6.0.jar"; do
    [[ -f "$jar" ]] || { echo "missing tool: $jar (set CLIENT240_TOOLS)" >&2; return 1; }
  done
  [[ -x "$CLIENT240_JAVA" ]] || { echo "missing java: $CLIENT240_JAVA (set CLIENT240_JAVA)" >&2; return 1; }
}

client240_tool() { "$CLIENT240_JAVA" -cp "$CLIENT240_ASM" "$CLIENT240_TOOL_SRC" "$@"; }
