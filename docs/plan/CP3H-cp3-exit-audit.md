# CP3H — CP3 Exit-Criteria and Architecture Audit

<!-- authority-notice -->
> Historical checkpoint and evidence record. Implementation status, “next” tasks, counts
> and validation below apply to the recorded snapshot. Current work follows
> [PORTING-ROADMAP.md](PORTING-ROADMAP.md) and the [domain matrix](../reference/rs240-port/PORT-MATRIX.md).

## Goal

Audit the implemented CP3A–CP3G state-runtime work against the original CP3 roadmap and current architecture specs without weakening exit criteria or adding placeholder runtime code solely to declare CP3 complete.

## Baseline

SPEC-0010 originally defines CP3 as:

- `GameState`,
- typed operations,
- ordered batches,
- events,
- deltas,
- deterministic clock/random providers,
- replay skeleton,
- exit evidence that identical inputs produce identical state/event hashes.

The audit uses the current merged implementation on `main` after CP3G as evidence.

## Audit matrix

| Roadmap requirement | Status after CP3G | Evidence / interpretation |
| --- | --- | --- |
| Authoritative `GameState` | Complete | CP3A established the non-`Clone`, private authoritative root and strict session lifecycle. |
| Deterministic semantic time | Complete | CP3B stores session-owned `GameTick` and advances it only from explicit checked `TickSpan` input. `GameState` does not read wall time. |
| Typed semantic operations | Complete | CP3C routes public mutation through `SemanticOperation` and `GameState::apply_operation`. |
| Ordered batches | Complete | CP3D defines exact slice ordering, first-failure stop, committed-prefix semantics, and exact failure index. |
| Events | Complete for modeled domains | CP3E emits committed facts only after successful mutation. |
| `StateDelta` | Complete for modeled domains | CP3E adds discardable projection metadata for the currently modeled lifecycle/tick domains. |
| Replay skeleton | Complete | CP3F replays operation/commit envelopes through the authoritative mutation gateway and detects exact first divergence. |
| Session compatibility identity in replay | Complete | CP3G makes canonical `Revision` session-owned and verifies it through begin-session operation/commit replay. |
| Deterministic clock provider | Satisfied by revised architecture | The semantic runtime uses explicit deterministic time input instead of allowing `GameState` to read a clock provider. Platform clocks remain outside domain transitions. Reintroducing an internal clock read would reduce determinism. |
| Deterministic randomness contract/provider | **Not complete** | The specs require randomness to enter explicitly through a deterministic source, but there is not yet a canonical runtime abstraction/input contract or test proving repeatability. |
| Identical state/event hashes | **Not complete** | Existing equality/replay tests prove modeled values and commit metadata match, but there is no canonical semantic state/event fingerprint/hash contract or exit test. |

## Decision

**CP3 remains open after CP3H.**

CP3A–CP3G provide the structural runtime, but closing CP3 now would silently drop two explicit roadmap requirements: deterministic randomness and canonical state/event hash/fingerprint evidence.

CP3H therefore does not mark CP3 complete and does not start CP4.

## Deterministic-time interpretation

The original roadmap phrase "deterministic clock/random providers" was intentionally broader than the implementation now needs for semantic time.

For game time, the stronger design is already implemented:

```text
runtime scheduler / replay
        │
        │ explicit SemanticOperation::AdvanceTick(TickSpan)
        ▼
     GameState
```

`GameState` never asks a clock what time it is. Identical ordered semantic inputs therefore do not depend on wall-clock timing.

This satisfies the deterministic-clock intent without introducing a second time authority or a fake domain clock object.

## Remaining CP3 closure work

### Proposed CP3I — deterministic randomness/input contract

Define the smallest canonical contract for randomness that can later be recorded/replayed and tested deterministically. It must:

- keep nondeterministic OS/browser entropy outside domain transitions,
- make deterministic seed/input state explicit,
- avoid tying `game-state` to platform RNG APIs,
- avoid creating a random semantic operation with no real semantic consumer merely to satisfy the roadmap,
- establish repeatability tests for the abstraction itself.

The exact ownership/API should be chosen only after inspecting existing model/platform ownership so the project does not create a duplicate RNG concept.

### Proposed CP3J — canonical semantic fingerprint + CP3 exit test

Define a canonical, stable fingerprint/hash surface for the modeled semantic state and committed event/commit stream, then prove:

```text
same initial semantic state
+ same deterministic semantic inputs
= same state fingerprint
+ same committed-event/commit fingerprint
```

The fingerprint must not depend on:

- Rust `HashMap` iteration order,
- randomized `Hasher` seeds,
- pointer/layout details,
- renderer/platform state,
- wall-clock values.

It should be extensible as `GameState` gains later domains without turning the fingerprint representation into a second authoritative state store.

## Non-blocking deferred work

These items remain intentionally outside CP3 and do not block closure:

- persistent replay file serialization/versioning,
- concrete `ServerProfile` and supported-profile registry,
- networking/login/protocol bytes,
- renderer/UI/plugin replay integration,
- player/NPC/inventory/widget/scene domains,
- replay checkpoints/seeking,
- projection rebuild-vs-incremental tests until projections exist.

The final SPEC-0002 projection acceptance criterion is explicitly conditional on projections existing and therefore is not evidence that must be fabricated during CP3.

## Validation

CP3H changes documentation only, but the repository must still pass the normal foundation gates:

- `cargo fmt --all --check`
- `cargo check --workspace --all-targets`
- `cargo clippy --workspace --all-targets -- -D warnings`
- `cargo test --workspace`
- `cargo check -p rustclient-web --target wasm32-unknown-unknown`

## Exit criteria

CP3H is complete when:

1. SPEC-0010 accurately records CP3 as still in progress,
2. the deterministic-time implementation is reconciled with the roadmap without adding a second clock authority,
3. deterministic randomness and canonical state/event fingerprinting are explicitly recorded as the only remaining CP3 closure gaps found by this audit,
4. deferred later-checkpoint work is not pulled into CP3,
5. the exact checkpoint head passes the standard CI gates.
