# CP0 Reference Infrastructure

## Purpose

Establish reproducible, read-only reference evidence before CP1 semantic-model implementation begins.

This checkpoint does not port gameplay behavior and does not implement networking. It makes the existing RustOSRS/RuneLite/OpenRune evidence easy for humans and coding agents to inspect without making it part of RustClient's dependency graph.

## Inputs

- RustClient `main` after the foundation merge.
- `Neosback/RustOSRS` pinned by Git submodule gitlink.
- `RuneLite-melxin/` and `OpenRune-Server-main/` contained inside the pinned RustOSRS snapshot.

## Work packages

### R0.1 Pin reference snapshot

Add `references/rustosrs` as a Git submodule.

Requirements:

- use an exact gitlink commit,
- never float to latest `main` during normal builds,
- record the upstream URL in `.gitmodules`,
- do not include the submodule in the Cargo workspace.

Evidence:

- `git ls-tree HEAD references/rustosrs` reports mode `160000`, type `commit` and an exact SHA.

### R0.2 Agent/reference governance

Add:

- `references/README.md`,
- `references/AGENTS.md`,
- root-agent guidance stating references are evidence only.

Rules:

- reference code is read-only during RustClient implementation,
- no RustClient crate may depend on reference crates,
- foreign architecture is not automatically RustClient architecture,
- material behavioral ports need focused RustClient tests/fixtures.

### R0.3 Reference index

Add `docs/reference/INDEX.md` mapping common research tasks to:

- RustOSRS Rust crates/editor,
- RuneLite snapshot,
- OpenRune server snapshot.

The index must make it clear which reference is useful for cache, scene, models, rendering, widgets, scripts, entities and later server/protocol work.

### R0.4 Reference tooling

Implement:

```text
cargo xtask references sync
cargo xtask references status
```

`sync` initializes/updates to the exact gitlink. `status` verifies:

- submodule is initialized,
- checkout SHA equals RustClient's recorded gitlink,
- origin points at the expected RustOSRS repository.

The commands must not advance the reference to upstream `main`.

### R0.5 Revision baseline amendment

Amend `SPEC-0005` so:

- revision `240` is the first implementation target,
- `Revision` is distinct from `ServerProfile`,
- no process-wide hard-coded revision becomes architectural truth,
- only implemented/validated revisions appear as supported choices,
- networking remains deferred,
- unsupported revisions fail explicitly rather than silently falling back.

## Validation

Required RustClient gates:

```text
cargo fmt --all --check
cargo check --workspace --all-targets
cargo clippy --workspace --all-targets -- -D warnings
cargo test --workspace
cargo check -p rustclient-web --target wasm32-unknown-unknown
```

Reference-specific checks:

```text
cargo xtask references sync
cargo xtask references status
```

The reference-specific commands require Git/network access for initial sync and should not be made part of ordinary CI until caching/availability policy is deliberately chosen.

## Exit criteria

CP0 reference infrastructure closes when:

1. RustOSRS is pinned by gitlink.
2. Reference trees are excluded from Cargo ownership.
3. Agent rules prevent accidental edits/dependencies.
4. The reference index is sufficient to route common research tasks.
5. `xtask references status` verifies the exact pin.
6. `SPEC-0005` records revision `240` as initial baseline with multi-revision architecture.
7. Normal RustClient CI is green on the exact checkpoint head.

## Next checkpoint

Proceed to CP1 semantic model, beginning with typed coordinate spaces, identities, revision value types and time/orientation primitives. CP1 may consult the pinned reference snapshot but must define RustClient-owned semantic types rather than import reference types.
