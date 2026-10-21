# Reference Provenance

The `references/rustosrs` Git submodule is intentionally pinned by RustClient's git tree. The gitlink itself is the canonical reference version; this document explains how to cite it.

For implementation notes, tests or ADRs that materially rely on reference behavior, record:

```text
Reference: references/rustosrs/<path>
RustOSRS pin: `git ls-tree HEAD references/rustosrs`
```

Do not duplicate the pinned SHA into multiple source files as configuration. Use the gitlink and `cargo xtask references status` as the authoritative version check.

When the pin is advanced, the change should explain:

- why a newer reference snapshot is needed,
- whether RuneLite/OpenRune bundled snapshots also changed,
- which RustClient specs/fixtures are affected,
- whether behavior changes are expected or only new evidence is being added.
