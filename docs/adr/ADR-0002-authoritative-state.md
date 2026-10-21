# ADR-0002: Single Authoritative Semantic GameState

**Status:** Accepted

## Decision

Live semantic truth is owned by `game-state`. Renderer, UI and plugins hold projections/snapshots, not competing mutable models.

## Consequences

- State transitions become testable/replayable.
- GPU/device loss can rebuild render state.
- Plugins can be isolated safely.
- We accept deliberate projection/update machinery instead of convenient cross-subsystem mutation.
