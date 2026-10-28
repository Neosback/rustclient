# Revision-240 delivery status

Reviewed 2026-10-28 at `bd3aae10d4a37c46401aaf71be2fa415cbe623e8`; follow-up at
`b0c57e072fe9365c44142e719bea30a45e93ebd4` (identical Rust tree).
Target: nearly 1:1 OSRS 240 behavior and applicable pinned RuneLite feature effects,
initially OpenRune 240.2, with shared desktop/browser semantics. Full parity is open.

[The roadmap](PORTING-ROADMAP.md) owns execution order;
[SPEC-0019](../specs/SPEC-0019-port-parity.md) owns the contract;
[the matrix](../reference/rs240-port/PORT-MATRIX.md) owns domain status;
[the audit](../reference/rs240-port/CURRENT-AUDIT.md) owns current findings.
This page is a compact entry point, not another independent backlog.

## Present and incomplete

| Area | Present | Open boundary |
| --- | --- | --- |
| Core | Typed state/operations, ordered commits/events/deltas, explicit clocks, replay/fingerprints | Every new retained field/lifecycle must join the same contracts |
| Native session/world | Cache-backed title/login, normal maps, selected player/NPC updates, movement, scenery/animation projections | Valid selected NPC masks rejected; instance/nested-world lifecycle incomplete; server ground-item/scenery mutations and broad packet consumers missing; ping/reflection replies, idle telemetry, reasoned logout and world transfer absent |
| Interaction/UI | NPC/location/widget subsets, menus/chat, inventory sync, substantial widget/CS2 host | Dialogue/player/item/target/drag paths, rich menus, model widgets, remaining opcodes/DB branches and input parity |
| World map | Cache assets/controller/CS2, viewport/overview drawing, native pan/zoom, newer load/reset/error repairs | Exact controller/lifecycle/ownership, labels/hover/menus and native/browser acceptance |
| Renderer/shell | Shared wgpu projections/reconstruction, GPU settings subsets; egui shell integrated at bd3aae10 | GPU/core ledger obligations, replacement-shell controls/performance and classic visual acceptance |
| Plugins | Native WIT component/host/overlay slice, 0.2 compiled plugin services, first-party subsets, JS-host transpilation proof, plugin/event inventories | Dispositions for 131 plugins, hook/`Client` API inventory, events that cannot fire, empty SDK/web-runtime crates, live acceptance |
| Audio/social/camera | Bounded script sound requests, public-chat subset and local camera controls | Playback/music/ambient consumers, social/clan/var-domain and scripted-camera behavior |
| Browser/distribution | Renderer/canvas bootstrap, shared Wasm compilation, development launch tooling | Integrated shared gameplay/cache/transport/UI/audio/plugin loop and packaged acceptance |

The exact-240 semantic deob and readable-name overlay already exist. No new deob
run is required merely to resume implementation. Use [the source map](../reference/INDEX.md)
and exact selected RSProt/cache evidence. Older readable 235/other-client behavior
needs confirmation in 240, even when no known difference is listed.

## Documentation review validation

This change edits documents, TSV/JSON provenance and Python documentation tools.
It does not fix the runtime gaps. Checks run for this review:

- `python3 tools/port_matrix.py --check`: generated 49-domain matrix and full
  269-slot packet identity/obligation inventory agree with canonical input data.
- `python3 tools/port_audit.py --check --report target/document-review/port-review-after.json --inventory target/document-review/method-inventory.tsv`:
  154 symbol rows valid; no ledger source lacks a manifest pin. 18,433 declarations,
  18,341 without method-level triage. These are research counts, not feature counts.
- `python3 tools/docs_index.py --check`: every Markdown document under docs is
  classified once by authority/scope.
- Local Markdown destination checks, TSV/JSON schema/path/provenance checks and
  `git diff --check`; changed files' Python syntax checked without workspace builds.

No Cargo checks/tests/Clippy, Java execution, live login, GPU/performance runs or
visual/native/browser gameplay acceptance were repeated for this documentation
change. Historical results remain in their dated checkpoints and do not certify
this baseline. [The review baseline](../reference/rs240-port/REVIEW-BASELINE.json)
pins inspected files and selected archive entries; [the audit](../reference/rs240-port/CURRENT-AUDIT.md)
records corrections to the pasted assessment.

### Follow-up review validation

The follow-up at `b0c57e07` added `tools/parity_inventory.py` and its three generated
TSVs, new matrix rows, packet-row corrections and documentation fixes. These checks ran
and passed: `tools/port_matrix.py --check` (56 domains, 269 packet slots),
`tools/port_audit.py --check` (154 rows, 18,341 untriaged declarations),
`tools/parity_inventory.py --check`, `tools/docs_index.py --check`, a Markdown
destination scan and `git diff --check`. The one Rust edit is a doc comment in
`crates/plugin-runtime-web`. For that crate only, `cargo fmt --check` and `cargo check`
passed. No workspace Cargo, Clippy, test, Java, live, GPU or visual check ran.

## Maintaining a single current path

Update the roadmap/matrix/ledger with implementation and exact evidence in the
same checkpoint. Keep old checkpoint results at their stable paths with historical
notices; remove stale active summaries instead of adding contradictory appendices.
[The documentation index](../README.md) distinguishes those roles. Deliberate
observable differences belong in [the deviations register](PARITY-DEVIATIONS.tsv)
with evidence and decision status. Missing work is never an accepted deviation.
