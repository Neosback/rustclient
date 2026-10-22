# Specification System

RustClient is developed spec-first for cross-cutting behavior.

## Status values

- `Draft`: design may change freely.
- `Accepted`: implementation target; incompatible changes require amendment/ADR.
- `Implemented`: acceptance criteria are verified.
- `Superseded`: replaced by another spec/ADR.

## Required spec sections

Every substantive spec should contain: purpose, scope, terminology, invariants, normative behavior, errors/failure modes, performance constraints where relevant, test/acceptance criteria, non-goals, and open questions.

## Change process

1. Identify the semantic owner and affected specs.
2. Update/create the spec before large implementation work.
3. Add tests/fixtures that express the contract.
4. Implement within crate ownership rules.
5. Run narrow tests, then workspace gates appropriate to the change.
6. Mark criteria complete only with evidence.

## Initial specs

- `SPEC-0001`: system architecture and crate ownership
- `SPEC-0002`: authoritative state, events, commands, replay
- `SPEC-0003`: native/web platform abstraction
- `SPEC-0004`: plugin platform and ABI
- `SPEC-0005`: private-server protocol/revision profiles
- `SPEC-0006`: cache/assets/definitions
- `SPEC-0007`: rendering and UI projections
- `SPEC-0008`: testing, observability, determinism
- `SPEC-0009`: security and capability model
- `SPEC-0010`: implementation roadmap/checkpoints
- `SPEC-0011`: plugin authoring experience and lightweight scripting direction
- `SPEC-0012`: dependency-light semantic coordinates, IDs, revision, time, and orientation
- `SPEC-0013`: offline cache-backed desktop title milestone
- `SPEC-0014`: OpenRune 240.2 authenticated desktop bootstrap

For execution-sized tasks and dependency ordering, see `docs/plan/MASTER_PLAN.md`.

- [SPEC-0015: Cache-backed spawn scene preview](SPEC-0015-spawn-scene.md) — static terrain/location geometry after authenticated placement.

- [SPEC-0016: Reference GPU scenery and gameplay camera](SPEC-0016-reference-scenery-parity.md)
- [SPEC-0017: Gameframe, entities and location animations](SPEC-0017-gameframe-entities.md)

Current reference/revision decisions: [revision-240 integration guide](../reference/REVISION-240.md).
Current delivery gaps: [revision-240 delivery plan](../plan/REVISION-240-delivery.md).
