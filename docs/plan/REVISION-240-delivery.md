# Revision-240 delivery and consolidation

Status: active implementation; not a complete client or rendering-parity claim.
Scope: OpenRune 240.2, classic OSRS behavior, shared desktop/browser Rust core,
and an ergonomic sandboxed plugin ecosystem. See the
[source guide](../reference/REVISION-240.md) for exact reference roles and limits.

## Current implementation

| Area | Present | Still required |
| --- | --- | --- |
| State/runtime | Typed operations, commits/deltas, replay, explicit tick/cycle/revision and fingerprints | Richer game domains and complete ongoing entity/action semantics |
| Networking | Native OpenRune bootstrap/login, framing/ciphers, initial placement and initial entity/location packet families | Complete client outbound actions and server update coverage, travel/reconnect behavior, browser-compatible transport |
| Cache/scene | JS5, terrain/models/textures, morph inputs, NPC/player base assets, legacy and cached-model animation paths | Complete required format coverage and independently verified transforms/materials/poses |
| Renderer | Shared wgpu, camera, world/UI pipelines, retained assets and rebuild paths | SPEC-0016 manual acceptance evidence; correct any measured differences |
| UI | Title, cache-authored IF3 gameframe/mount tree, CS2, menus/drag/chat/input, shared layout, font/formatting and wheel-listener paths | Complete widget kinds, scrollbar/interface edge cases, scaling/persistence, world-map content and desktop three-layout acceptance; see rs240-port/GAMEFRAME-INTERFACES.md |
| Browser | Canvas/renderer bootstrap and shared-crate Wasm compilation | Cache import/storage, shared application session, relay/WebSocket transport, controls and actual world/UI flow |
| Plugins | Accepted WIT/Component architecture and crate ownership | Real WIT interfaces/bindings, host mediation, both runtimes, SDK, sample plugin and authoring tooling |
| Distribution | Development native launch scripts | Reproducible desktop bundles and browser build/deployment with asset/transport setup |

## Verified 240 client reference (2026-10-22)

Downloaded RuneLite's official `injected-client-1.12.39.jar` to
`references/gamepacks/osrs-240/injected-client-1.12.39.jar`.
Its SHA-256 matches RuneLite's historical bootstrap manifest. Static inspection
traces revision 240 from `client.init` through the engine assignment to the
`getRevision` accessor; login serialization also contains revision 240.
[Provenance and limits](../reference/gamepack-240.json) are recorded.

This removes the missing-240-client-reference obstacle. It does not establish an
untouched vanilla gamepack, successful decompilation/mapping, OpenRune 240.2
compatibility or visual parity. The next reference step is isolated inspection
and decompilation of this injected artifact while preserving the 235 source tree.

## Branch reconciliation

The working line `Neosback/openrune-240-login` contains the native client slices
and direct RuneLite reference. `origin/main` contributed the complete additional
rsprox files; it was merged without changing reference source contents.
`claude/hopeful-zhukovsky-aefeb0` contributed the cached-model animation work. Its
original WIP label was accurate: formatting, strict Clippy and malformed-input
handling needed correction before integration into main.

Older remote branches are already represented by exact historical checkpoint
trees, verified with empty `git diff --stat` comparisons:

| Remote branch | Equivalent checkpoint in the working line |
| --- | --- |
| `docs/cp3h-exit-audit` | `021e095` |
| `feat/cp3i-deterministic-randomness` | `e80c184` |
| `feat/cp3j-semantic-fingerprints` | `2a164db` |

Their ancestry was recorded with content-preserving merges (`ccd329bc`,
`1d535b5a`, `73cdeabb`) because their entire snapshots equal those existing
ancestors. The resulting tree is byte-for-byte identical to `61c030d9`; old
patches did not revert later fingerprint/entity/profile changes. Main was
fast-forwarded to the consolidated working line before these ancestry merges.
Every local and fetched remote branch is now reachable from main. Branches and
the other worktree were retained; no branch deletion or worktree reset occurred.

## Integrated animation work

Revision-adapter decoding now preserves bone bind variants and cached animation
curves. Scene preparation bakes curves and resolves bone hierarchy matrices;
entity posing applies weighted skinning and alpha channels before NPC scale.
The public semantic state and renderer ownership remain unchanged.

Integration repairs reject invalid channel slots, negative/out-of-range/cyclic
parents, duplicate key times, non-finite inputs and excessive curve expansion.
Singular bind matrices produce preparation advisories. There is one new compact
codec-family test, including truncation and malformed-input cases. No pixel,
per-model or repeated render parity tests were added.

`entity_probe` now submits bounded requests covering the supplied ranges instead
of silently validating only the first capped batch. It meshes bounded groups,
reuses projection buffers and returns failure for decode/preparation problems.
A requested ID range is not a count of existing definitions: absent cache IDs
remain absent, and variable-controlled invisible morphs may emit no geometry.
This diagnostic exercises asset/pose execution, not visual parity.

## Playable desktop work in progress

[SPEC-0018](../specs/SPEC-0018-playable-desktop.md) defines the selected walk-and-bank
milestone. [The implementation checkpoint](PLAYABLE-desktop.md) records movement,
normal map rebuild and local-player decoder groundwork, exact validation and the
unfinished NPC/action/widget/CS2/banking work. It does not establish a playable loop.

## Next delivery slices

1. **Close classic rendering behavior.** Audit missing SPEC-0016/0017 behavior
   against deob, actual cache and RustOSRS Reference GPU: normals/merge eligibility,
   offsets/transform order, contours, textures/materials/priority, roofs/bridges,
   legacy/skeletal timing and transforms. Keep unresolved cases explicit. Once
   the intended feature set is complete, perform the single manual acceptance
   pass with reference/actual/difference artifacts. No screenshot CI suite.
2. **Complete one playable 240 loop.** Use rsprox client packet info as well as
   server packets, cross-check with selected rsprot 240 sources, then implement
   picking/menu intent -> validated outbound action -> decoded server result ->
   committed state. Add region travel and correct movement/animation selection.
   Implement widgets and required CS2 for a representative server-driven interface.
3. **Run that shared loop in the browser.** Supply imported/served cache blobs,
   browser storage, WebSocket transport or explicit TCP relay, and compose the
   existing application/scene/UI/renderer services. Demonstrate login, world,
   input, disconnect and reconstruction; a clear-frame canvas is insufficient.
4. **Ship the first useful plugin.** Define minimal versioned WIT interfaces,
   generate SDK bindings, implement host grants/query/event/command validation
   and both execution adapters. Run one plugin from the same source on desktop
   and web, with settings storage and an overlay. Then add create/build/load,
   hot reload, typed configuration, diagnostics and replay debugging.
5. **Package both targets.** Produce documented native bundles and browser build
   output; include cache/transport/profile configuration and plugin packaging.
   Measure performance with representative scenes before claiming improvement
   over RuneLite. "Better" must mean usable authoring, parity and measured costs.

These slices share owners and can be developed incrementally; they do not require
forking gameplay by platform or freezing a speculative plugin ABI prematurely.

## Validation for this consolidation

Animation/client code checkpoint: `1a84c31d` (including the merged animation work).
Reference-status tooling and documentation checkpoint: `61c030d9`. Main's ancestry
merges preserve that exact source tree; this final record changes documentation
only.

Passed on the integrated code:

```sh
cargo fmt --all --check
cargo check --workspace --all-targets
cargo clippy --workspace --all-targets -- -D warnings
cargo test --workspace
cargo check -p rustclient-web --target wasm32-unknown-unknown
cargo check -p rustclient-application --target wasm32-unknown-unknown
cargo run -p xtask -- references status
```

After the final reference-status tooling edit, the xtask all-target check, strict
Clippy and tests were rerun and passed. Selected source hashes and changed Markdown
file links were also checked. No upstream reference sources were changed.

The manual diagnostic passed against the read-only OpenRune server LIVE cache:

```sh
cargo run -p rustclient-native --bin entity_probe -- \
  "/Users/tylercovalt/Desktop/OpenRune Project/OpenRune-Server/.data/cache/LIVE" \
  0-16000 i0-32000
```

Requested 16,001 NPC IDs and 32,001 item IDs. Decoded 16,020 NPC definitions
(including transformation dependencies), 31,919 existing item definitions,
11,164 models, 2,151 sequences, 25,281 legacy frames, 632 skeletons and 191 cached
animations. All decode/preparation failure counts were zero. Bounded geometry
extraction at cycles 0 and 10 emitted 26,452,552 triangles in aggregate; this is
two samples across requested definitions, not one live scene or an FPS claim.
Local execution log: `target/entity-consolidation-probe.log` (not a fixture or CI
input). The superseded single-batch probe had not covered the requested ranges.

Rendering acceptance, a 240 deob run, live server login and browser gameplay
were **not run**. No visual parity, complete plugin runtime, or playable browser
claim follows from these checks.

### Playable desktop continuation

Stock NPC list/origin/movement/attribute updates, validated NPC/location actions,
posed-geometry picking and native menus, plus authoritative inventory packet
synchronization are implemented. Schema 8 covers their semantic state/replay.
Widget/interface rendering and CS2, bank transactions and live acceptance remain
incomplete. See the [current checkpoint](PLAYABLE-desktop.md) for exact limits and
validation; this does not establish RuneLite parity or working banking.
