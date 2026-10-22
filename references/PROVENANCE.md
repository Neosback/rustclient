# Reference Provenance

The `references/rustosrs` Git submodule is intentionally pinned by RustClient's git tree. The gitlink itself is the canonical reference version; this document explains how to cite it.

For implementation notes, tests or ADRs that materially rely on reference behavior, record:

```text
Reference: references/rustosrs/<path>
RustOSRS pin: `git ls-tree HEAD references/rustosrs`
```

Do not duplicate the pinned SHA into multiple source files as configuration. Use the gitlink and `cargo run -p xtask -- references status` as the authoritative version check.

When the pin is advanced, the change should explain:

- why a newer reference snapshot is needed,
- whether RuneLite/OpenRune bundled snapshots also changed,
- which RustClient specs/fixtures are affected,
- whether behavior changes are expected or only new evidence is being added.

## Vendored and external evidence

The direct RuneLite and rsprox snapshots are pinned by their Git tree identities:

```sh
git ls-tree HEAD references/RuneLite-melxin references/rsprox-master
```

Use the RustClient commit plus a relative source path when citing these copies.
`cargo run -p xtask -- references status` reports their tree IDs and rejects local
changes. This check does not fetch a new revision or establish protocol parity.

[`docs/reference/source-manifest.json`](../docs/reference/source-manifest.json)
records selected SHA-256 values for those sources, the actual development server,
OpenRune FileStore and the server-selected rsprot 240 source artifacts. External
roots are local research locations. Hashes cover named files/artifacts only;
external trees have not been imported wholesale. Advance provenance deliberately
when a new source materially informs a port. Preserve upstream notices.
