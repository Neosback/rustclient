# Plugins

Plugins are WebAssembly components built against the versioned WIT contract in
[`wit/rustclient/plugin/world.wit`](../wit/rustclient/plugin/world.wit). They never receive
`GameState`, renderer handles, sockets or filesystem access. The host grants only the
capabilities declared in `plugin.json`.

## Package layout

```text
my-plugin/
  plugin.json          manifest (id, version, plugin-api, capabilities, entry)
  my_plugin.wasm       WebAssembly component
  Cargo.toml           optional authoring crate
  src/lib.rs
```

## Authoring

The repository sample, [`hello-overlay`](hello-overlay), is the reference implementation:

- subscribe to `tick` events via `on-event`,
- query the committed local player via `player-position`,
- persist plugin-local state with `config-get`/`config-set`,
- return text overlay commands from `overlay`.

Build it:

```text
rustup target add wasm32-wasip2
scripts/build-sample-plugin.sh
```

The resulting `plugins/hello-overlay` directory is a complete package.

## Running

Headless probe:

```text
cargo run -p rustclient-native --bin plugin_probe -- \
  --package plugins/hello-overlay --storage /tmp/rustclient-plugin-storage --ticks 120
```

Desktop client:

```text
scripts/run-openrune.sh /path/to/OpenRune-Server --plugins-dir plugins
```

Each child directory of `--plugins-dir` is one package. Packages that fail manifest validation,
API compatibility or instantiation are logged and skipped; they cannot prevent the client from
starting. A plugin is disabled after three consecutive faults.

## Status

Native component execution is implemented. Browser transpilation (`jco`), the ergonomic Rust SDK,
interactive capability grants, menus/panels and signed distribution are tracked in
[CP11](../docs/plan/CP11-plugin-slice.md).
