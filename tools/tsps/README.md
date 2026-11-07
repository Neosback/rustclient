# TSPS wire evidence

Ordinary Rust tests consume the recorded bytes; they require no TSPS checkout, Java or Node.
Optional source regeneration runs the actual pinned TypeScript encoders:

```sh
npx --yes --package=tsx tsx tools/tsps/export-fixtures.mts /path/to/tsps
cargo test -p rustclient-protocol --test integration tsps_241::
```

Run from the RustClient root with Node/npm available. The exporter verifies the complete
commit and inspected source hashes in the [manifest](../../crates/protocol/tests/fixtures/tsps-241/sources.json),
writes only `target/tsps-fixtures`, and fails if its manifest or bytes differ from recorded
fixtures. It does not install dependencies into or modify TSPS. Review any changed source
identity, semantics, license and bytes before deliberately replacing evidence.

The [profile spec](../../docs/specs/SPEC-0031-tsps-profile.md) defines scope. These fixtures
establish the listed codec cases; they do not establish live gameplay or complete cache support.
