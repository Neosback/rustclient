# Checkpoint Close Template

Use this for every completed checkpoint.

## Identity

- Checkpoint:
- Branch:
- Base SHA:
- Final head SHA:

## Scope completed

Summarize only what is actually implemented.

## Specs / ADRs

- Added:
- Amended:
- Superseded:

## Implementation

List the owned crates/modules changed and the semantic responsibility added.

## Tests added

List focused tests, fixtures, replay cases, property/fuzz coverage, and integration tests.

## Validation evidence

Record exact commands/checks and results. Separate local validation from CI. Do not write "all green" unless every required gate ran.

## Deliberately skipped

List any full-suite, browser, fixture-server, GPU, benchmark, or platform validation that was not run and why.

## Known limitations

State deferred behavior explicitly.

## Architecture audit

Confirm:

- no duplicate semantic owner introduced,
- no revision-specific logic leaked outward,
- no renderer/plugin/platform backdoor into `GameState`,
- native/web semantic behavior remains shared,
- public plugin API changes are versioned/documented.

## Next checkpoint

List concrete dependencies now unblocked.
