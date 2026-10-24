#!/bin/sh
# Build the sample plugin for wasm32-wasip2 and place the component beside its manifest.
set -eu

root="$(cd "$(dirname "$0")/.." && pwd)"
plugin_dir="$root/plugins/hello-overlay"

cargo build --release --target wasm32-wasip2 --manifest-path "$plugin_dir/Cargo.toml"
cp "$plugin_dir/target/wasm32-wasip2/release/hello_overlay.wasm" "$plugin_dir/hello_overlay.wasm"
echo "packaged plugin package: $plugin_dir (plugin.json + hello_overlay.wasm)"
