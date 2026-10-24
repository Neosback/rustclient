# Revision 240 research instructions

Scope: `docs/reference/rs240-port/` only.

Read `README.md`, `PORT-MATRIX.md`, the applicable existing `docs/plan/*240*.md`
audit, and the root/docs `AGENTS.md` files before extending this corpus.

## Evidence discipline

- Treat `references/` as read-only pinned evidence.
- Use `references/rl-suite-240/sources-semantic/` as the primary revision-240
  client control-flow source.
- Preserve stable source symbols such as `classDF.method3015`; do not invent a
  semantic rename until behavior is established.
- Use the canonical `references/rl-suite-240/semantic-1.12.39.tsv` for the complete
  API-derived class map instead of copying it here.
- Protocol-sensitive claims need the exact selected RSProt 240 evidence whenever
  available. Nearby rsprox revisions are supporting evidence, not opcode authority.
- Cache-sensitive claims need actual 240 cache evidence when layouts/defaults are
  ambiguous.
- Explicitly distinguish stock client behavior from RuneLite injection/API behavior.

## Documentation discipline

- Add or update `SYMBOL-LEDGER.tsv` when a source symbol becomes relevant.
- Keep one semantic fact canonical. Link existing player/NPC/gameframe audits
  rather than cloning their exact tables.
- Record the Rust owner for every port-relevant behavior.
- Use the status and confidence vocabularies defined in `README.md`.
- Mark uncertainty. Never turn a plausible interpretation into a compatibility
  claim just to make a row look complete.
- Research docs describe evidence and ownership. Normative public/architecture
  behavior still requires the owning spec/ADR when root documentation requires it.

## Architecture discipline

- Never recommend one-to-one Java class translation as the default.
- `GameState` remains the sole authoritative semantic state.
- Raw revision-specific bytes/opcodes/masks/transforms stay in protocol adapters.
- Renderer/scene projections are not a second semantic authority.
- RuneLite injected hooks/accessors should be classified into semantic queries,
  committed events, validated commands, UI/overlay capabilities, diagnostics or
  not-needed behavior before any plugin API is designed around them.

## Research branch validation

This corpus is documentation/inspection work. Do not run workspace-wide Cargo
checks/tests/Clippy, release gates, parity runs or long CI tasks merely for these
Markdown/TSV edits. Never claim a gate was run when it was not. Use narrow syntax
or link checks only if a research edit actually requires them.
