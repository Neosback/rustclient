#!/usr/bin/env python3
"""Generate the documentation authority index; preserve historical evidence in place."""
import argparse
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
DOCS = ROOT / 'docs'
OUTPUT = DOCS / 'README.md'


def render():
    groups = {name: [] for name in [
        'Normative specifications', 'Architecture decisions',
        'Current execution and audit owners', 'Domain research and source ledgers',
        'Historical implementation checkpoints', 'Reference tooling and verification records',
        'Documentation and evidence rules',
    ]}
    active = {'plan/PORTING-ROADMAP.md', 'plan/MASTER_PLAN.md',
              'plan/REVISION-240-delivery.md', 'reference/INDEX.md',
              'reference/REVISION-240.md', 'reference/rs240-port/README.md',
              'reference/rs240-port/PORT-MATRIX.md', 'reference/rs240-port/CURRENT-AUDIT.md',
              'reference/builtin-plugins/FIRST-PARTY-LEDGER.md',
              'reference/builtin-plugins/GPU-AUDIT.md'}
    for path in sorted(DOCS.rglob('*.md')):
        if path == OUTPUT:
            continue
        name = str(path.relative_to(DOCS))
        if path.name == 'AGENTS.md':
            group = 'Documentation and evidence rules'
        elif name.startswith('specs/'):
            group = 'Normative specifications'
        elif name.startswith('adr/'):
            group = 'Architecture decisions'
        elif name in active:
            group = 'Current execution and audit owners'
        elif name.startswith('plan/') or (name.startswith('reference/builtin-plugins/')
                                           and path.name != 'README.md'):
            group = 'Historical implementation checkpoints'
        elif name.startswith('reference/client-240/') or '/verification/' in name:
            group = 'Reference tooling and verification records'
        else:
            group = 'Domain research and source ledgers'
        groups[group].append(name)
    lines = [
        '# RustClient documentation', '',
        'Start with [ARCHITECTURE.md](../ARCHITECTURE.md), '
        '[the parity contract](specs/SPEC-0019-port-parity.md), then '
        '[the porting roadmap](plan/PORTING-ROADMAP.md).', '',
        'The roadmap is the current execution order. '
        '[The matrix](reference/rs240-port/PORT-MATRIX.md) owns domain status; '
        '[the audit](reference/rs240-port/CURRENT-AUDIT.md) owns the latest review findings. '
        'Update these owners instead of appending another competing current plan.', '',
        'Specs and ADRs retain their declared decision/status scope. A mismatch between '
        'code and a ratified contract stays an open obligation. Source research and '
        'historical test logs do not silently amend that contract.', '',
        'Historical checkpoints remain at stable paths for source citations and exact '
        'validation records. Their “current”, “next”, counts and completion statements '
        'apply only to their recorded snapshot. They do not direct new work. '
        'The HD feature menu is optional research after classic parity.', '',
        'Generated index: `python3 tools/docs_index.py --write`; check with `--check`. '
        'This classifies document authority; it does not certify every factual sentence.', '',
        'Current machine-readable data: '
        '[domain matrix](reference/rs240-port/PORT-MATRIX.tsv), '
        '[packet coverage](reference/rs240-port/PACKET-COVERAGE.tsv), '
        '[method ledger](reference/rs240-port/SYMBOL-LEDGER.tsv), '
        '[review baseline](reference/rs240-port/REVIEW-BASELINE.json), '
        '[source pins](reference/source-manifest.json), '
        '[deviations](plan/PARITY-DEVIATIONS.tsv), '
        '[CS2 host coverage](reference/rs240-port/CS2-HOST-COVERAGE.tsv), '
        '[RuneLite plugins](reference/builtin-plugins/RUNELITE-PLUGINS.tsv) and '
        '[events](reference/builtin-plugins/RUNELITE-EVENTS.tsv) '
        '(`tools/parity_inventory.py`), GPU/first-party JSON/TSV ledgers. '
        'Source mappings, dispatch paths and test presence are distinct from acceptance.', '',
    ]
    for group, names in groups.items():
        lines += [f'## {group}', '']
        lines += [f'- [{name}]({name})' for name in names]
        lines.append('')
    return '\n'.join(lines)


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    group = parser.add_mutually_exclusive_group(required=True)
    group.add_argument('--write', action='store_true')
    group.add_argument('--check', action='store_true')
    args = parser.parse_args()
    expected = render()
    if args.write:
        OUTPUT.write_text(expected)
    elif not OUTPUT.exists() or OUTPUT.read_text() != expected:
        raise SystemExit('docs/README.md is stale; run tools/docs_index.py --write')
    print('Documentation index matches all Markdown under docs/; authority only, not behavioral acceptance')


if __name__ == '__main__':
    main()
