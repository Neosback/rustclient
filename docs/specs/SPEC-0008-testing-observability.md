# SPEC-0008: Testing, Replay, and Observability

**Status:** Draft

## Test pyramid

- unit tests for binary primitives, coordinates, value objects, state transitions,
- property/fuzz tests for parsers/framing and deterministic transformations,
- golden fixtures for protocol/cache/script/render semantics,
- subsystem integration tests,
- deterministic replay tests,
- native/web parity tests,
- end-to-end private-server fixture tests only after lower layers are stable.

## Fixtures

Fixtures are immutable evidence. Each fixture records provenance/profile/revision and expected interpretation. Avoid opaque giant dumps when a minimal targeted fixture can prove the same behavior.

## Replay

Replays are first-class debugging inputs. Plugin tests should eventually run against replay streams without a live server.

## Observability

Use structured tracing with subsystem/span context. High-value metrics include tick/frame timings, decoder errors, transport queue depth, asset decode/upload time, draw counts, GPU memory estimates where available, plugin CPU/fuel/faults, event batch size and state-delta size.

## Diagnostics

Developer builds should support inspectors for state, packets after decode, scene entities, widgets/scripts, assets, render passes, plugin events/capabilities and profile selection.

## CI gates

Foundation target gates:

- format,
- workspace check,
- strict Clippy,
- workspace tests,
- web target check,
- architecture dependency validation once implemented.

No CI badge/status should imply a tier that did not execute.
