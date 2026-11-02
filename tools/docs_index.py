#!/usr/bin/env python3
"""Generate compact current navigation and the historical checkpoint index."""
import argparse
import json
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
DOCS = ROOT / 'docs'


def render():
    current = '''# RustClient documentation

Start with [architecture](../ARCHITECTURE.md), the [parity contract](specs/SPEC-0019-port-parity.md)
and the [current roadmap](plan/PORTING-ROADMAP.md). Reviewed 2026-11-01; implementation
and live acceptance remain separate. No whole-client completion percentage is claimed.

## Current owners

| Question | Canonical owner |
| --- | --- |
| What is implemented, and what remains? | [Current audit](reference/rs240-port/CURRENT-AUDIT.md) and [domain matrix](reference/rs240-port/PORT-MATRIX.md) |
| What should we implement next? | [One execution roadmap](plan/PORTING-ROADMAP.md) |
| What behavior is required? | [Spec registry](specs/README.md), [architecture](../ARCHITECTURE.md) and ADRs below |
| What sources establish that behavior? | [Source map](reference/INDEX.md), [revision guide](reference/REVISION-240.md), [stock logic map](reference/rs240-port/CLIENT-LOGIC-MAP.md) |
| What update/performance evidence exists? | [Update audit and scoped measurements](reference/UPDATE-OPTIMIZATION-2026-10-30.md) |
| What is left in classic GPU? | [GPU audit](reference/builtin-plugins/GPU-AUDIT.md) and [gate ledger](reference/builtin-plugins/gpu-audit.json) |
| What is left for RuneLite features? | [First-party ledger](reference/builtin-plugins/FIRST-PARTY-LEDGER.md), [catalog](reference/builtin-plugins/RUNELITE-PLUGINS.tsv), [hooks/API ownership](reference/builtin-plugins/HOOK-API-OWNERS.tsv) |
| Which TSPS ideas still transfer? | [Reconciled shortlist](reference/TSPS-GIT-SHORTLIST-2026-10-29.md#current-reconciliation--2026-10-31); stock 240 remains authoritative |
| What did an older checkpoint prove? | [Historical archive](archive/README.md), its [path/hash manifest](archive/MANIFEST.json) and dated reference records |

Update these owners instead of creating another current plan. Specs/ADRs keep their
declared decision scope; missing implementation does not silently weaken a contract.
Dated source research and diagnostics retain their original evidence, not current
execution authority. Optional HD research is outside classic completion.

## Architecture decisions

'''
    for path in sorted((DOCS / 'adr').glob('*.md')):
        current += f'- [{path.stem}](adr/{path.name})\n'
    current += '''
## Coverage and provenance

- [Domain TSV](reference/rs240-port/PORT-MATRIX.tsv) and [review baseline](reference/rs240-port/REVIEW-BASELINE.json)
- [Source pins](reference/source-manifest.json) and [research corpus/navigation](reference/rs240-port/README.md)
- [Method ledger](reference/rs240-port/SYMBOL-LEDGER.tsv), [packet boundaries](reference/rs240-port/PACKET-COVERAGE.tsv) and [CS2 host inventory](reference/rs240-port/CS2-HOST-COVERAGE.tsv)
- [Plugin event inventory](reference/builtin-plugins/RUNELITE-EVENTS.tsv), [consumer inventory](reference/builtin-plugins/source-inventory.json) and [scoped API verification](reference/builtin-plugins/API-CONTINUATION-EVIDENCE.json)
- [Accepted/proposed deviations](plan/PARITY-DEVIATIONS.tsv) and [checkpoint evidence template](templates/CHECKPOINT_TEMPLATE.md)
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
'''
    manifest = json.loads((DOCS / 'archive/MANIFEST.json').read_text())
    archive = '''# Historical documentation archive

The [current roadmap](../plan/PORTING-ROADMAP.md), [audit](../reference/rs240-port/CURRENT-AUDIT.md)
and [domain matrix](../reference/rs240-port/PORT-MATRIX.md) direct new work. These
records preserve foundation, delivery and implementation evidence for their original
snapshots. Their “current”, “next”, counts and acceptance claims are historical.
Accepted specs and ADRs remain in their normal locations and keep their authority.

Archived 2026-11-01. No unique checkpoint was discarded. The old master/delivery
plans and superseded roadmap/audit are retained here; the reusable template moved
to [templates](../templates/CHECKPOINT_TEMPLATE.md). [MANIFEST.json](MANIFEST.json)
records original paths/hashes and relocated hashes. Relocation adds authority notices
and repairs links; it does not rerun or extend validation. Frozen JSON evidence keeps
its original paths and can be resolved through that mapping. Dated reference/source
research stays under `docs/reference/` because it remains useful evidence.

## Foundation and implementation checkpoints

| Original name | Disposition / retained document |
| --- | --- |
'''
    for entry in sorted(manifest['entries'], key=lambda e: e['path']):
        path = ROOT / entry['path']
        relative = path.relative_to(DOCS)
        archive += f"| `{entry['original']}` | [{entry['kind']}: {path.name}](../{relative}) |\n"
    archive += '\nGenerated with `python3 tools/docs_index.py --write`; verify with `--check`.\n'
    return {DOCS / 'README.md': current, DOCS / 'archive/README.md': archive}


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    group = parser.add_mutually_exclusive_group(required=True)
    group.add_argument('--write', action='store_true')
    group.add_argument('--check', action='store_true')
    args = parser.parse_args()
    for path, expected in render().items():
        if args.write:
            path.write_text(expected)
        elif not path.exists() or path.read_text() != expected:
            raise SystemExit(f'{path.relative_to(ROOT)} is stale; run tools/docs_index.py --write')
    print('Current navigation and archive index match; no behavioral acceptance inferred')


if __name__ == '__main__':
    main()
