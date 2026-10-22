#!/usr/bin/env bash
# Transpile the native sample component with jco and run it against a Node host.
# Proves the WIT ABI and packaged component are not tied to Wasmtime or the native client.
set -euo pipefail

root="$(cd -- "$(dirname -- "$0")/.." && pwd)"
cd -- "$root"

scripts/build-sample-plugin.sh

out="$root/target/plugin-web/hello-overlay"
rm -rf -- "$out"
npx --yes @bytecodealliance/jco transpile \
  plugins/hello-overlay/hello_overlay.wasm \
  -o "$out" \
  -M 'rustclient:plugin/host=./host.mjs' \
  -q
cp scripts/plugin-web-host.mjs "$out/host.mjs"
cp scripts/plugin-web-smoke.mjs "$out/smoke.mjs"

export PLUGIN_SMOKE_STATE="$root/target/plugin-web/state.json"
rm -f -- "$PLUGIN_SMOKE_STATE"
PLUGIN_SMOKE_EXPECT=fresh node "$out/smoke.mjs"
PLUGIN_SMOKE_EXPECT=restored node "$out/smoke.mjs"
