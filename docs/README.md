# RustClient documentation

Start with [architecture](../ARCHITECTURE.md), the [parity contract](specs/SPEC-0019-port-parity.md)
and the [current roadmap](plan/PORTING-ROADMAP.md). TSPS is the primary development target; the adapter is experimental. Implementation
and live acceptance remain separate. No whole-client completion percentage is claimed.

## Current owners

| Question | Canonical owner |
| --- | --- |
| What is implemented, and what remains? | [Current audit](reference/rs240-port/CURRENT-AUDIT.md) and [domain matrix](reference/rs240-port/PORT-MATRIX.md) |
| What should we implement next? | [One execution roadmap](plan/PORTING-ROADMAP.md) |
| What behavior is required? | [Spec registry](specs/README.md), [architecture](../ARCHITECTURE.md) and ADRs below |
| What sources establish that behavior? | [Source map](reference/INDEX.md), [revision guide](reference/REVISION-240.md), [stock logic map](reference/rs240-port/CLIENT-LOGIC-MAP.md) |
| What update/performance evidence exists? | [Update audit and scoped measurements](reference/UPDATE-OPTIMIZATION-2026-10-30.md) |
| What did the loading, prompt, interface, minimap and memory pass establish? | [Pass record](reference/CLIENT-PASS-2026-11-02.md) and [interface audit baseline](reference/rs240-port/INTERFACE-AUDIT.tsv) |
| What did the plugin-hook, ground-item and efficiency pass establish? | [Pass record](reference/CLIENT-PASS-2026-11-05.md), [ground items spec](specs/SPEC-0029-ground-items.md) |
| What is left in classic GPU? | [GPU audit](reference/builtin-plugins/GPU-AUDIT.md) and [gate ledger](reference/builtin-plugins/gpu-audit.json) |
| What is left for RuneLite features? | [First-party ledger](reference/builtin-plugins/FIRST-PARTY-LEDGER.md), [catalog](reference/builtin-plugins/RUNELITE-PLUGINS.tsv), [hooks/API ownership](reference/builtin-plugins/HOOK-API-OWNERS.tsv) |
| What defines the TSPS migration? | [SPEC-0031](specs/SPEC-0031-tsps-profile.md), the roadmap and pinned protocol fixtures; stock evidence remains authoritative for classic behavior |
| What did an older checkpoint prove? | [Historical archive](archive/README.md), its [path/hash manifest](archive/MANIFEST.json) and dated reference records |

Update these owners instead of creating another current plan. Specs/ADRs keep their
declared decision scope; missing implementation does not silently weaken a contract.
Dated source research and diagnostics retain their original evidence, not current
execution authority. Optional HD research is outside classic completion.

## Architecture decisions

- [ADR-0001-plugin-abi](adr/ADR-0001-plugin-abi.md)
- [ADR-0002-authoritative-state](adr/ADR-0002-authoritative-state.md)
- [ADR-0003-platform-boundary](adr/ADR-0003-platform-boundary.md)
- [ADR-0004-verification-policy](adr/ADR-0004-verification-policy.md)
- [ADR-0005-readable-client-names](adr/ADR-0005-readable-client-names.md)
- [ADR-0006-plugin-api-0-2](adr/ADR-0006-plugin-api-0-2.md)
- [ADR-0007-on-demand-gpu-shell](adr/ADR-0007-on-demand-gpu-shell.md)
- [ADR-0008-login-reconnect-and-world-transfer](adr/ADR-0008-login-reconnect-and-world-transfer.md)
- [ADR-0009-render-profiles](adr/ADR-0009-render-profiles.md)
- [README](adr/README.md)

## Coverage and provenance

- [Domain TSV](reference/rs240-port/PORT-MATRIX.tsv) and [review baseline](reference/rs240-port/REVIEW-BASELINE.json)
- [Source pins](reference/source-manifest.json) and [research corpus/navigation](reference/rs240-port/README.md)
- [Method ledger](reference/rs240-port/SYMBOL-LEDGER.tsv), [packet boundaries](reference/rs240-port/PACKET-COVERAGE.tsv) and [CS2 host inventory](reference/rs240-port/CS2-HOST-COVERAGE.tsv)
- [Plugin event inventory](reference/builtin-plugins/RUNELITE-EVENTS.tsv), [consumer inventory](reference/builtin-plugins/source-inventory.json) and [scoped API verification](reference/builtin-plugins/API-CONTINUATION-EVIDENCE.json)
- [Accepted/proposed deviations](plan/PARITY-DEVIATIONS.tsv) and [checkpoint evidence template](templates/CHECKPOINT_TEMPLATE.md)
- [Retired shell removal provenance](archive/retired-shell/README.md) and [hash inventory](archive/retired-shell/removal.json)
- [Readable-name evidence and workflow](reference/client-240/README.md)

Counts, source mappings, dispatch arms, registered modules and successful builds are
not behavioral acceptance. Historical raw logs under ignored `target/` are local
artifacts, not guaranteed files in a fresh checkout. Frozen JSON paths may predate
reorganization; resolve them through the archive manifest without rewriting provenance.

## Maintaining these docs

Documentation rules: [root instructions](../AGENTS.md), [docs instructions](AGENTS.md)
and [240 research instructions](reference/rs240-port/AGENTS.md). Markdown/TSV-only
work uses narrow documentation/ledger checks; it does not justify rerunning Cargo.

Run `python3 tools/docs_index.py --write` after changing navigation, then
`python3 tools/docs_check.py` and `python3 tools/port_matrix.py --check`.
The index is generated; the roadmap, audit, specs and editable ledgers remain authored.
