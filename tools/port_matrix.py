#!/usr/bin/env python3
"""Validate/render the current domain audit. This never infers implementation from file counts."""
import argparse
import csv
import hashlib
import json
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
DIRECTORY = ROOT / 'docs/reference/rs240-port'
SOURCE = DIRECTORY / 'PORT-MATRIX.tsv'
OUTPUT = DIRECTORY / 'PORT-MATRIX.md'
STATUSES = {'partial', 'implemented', 'not-implemented', 'incorrect'}


def render():
    with SOURCE.open(newline='') as stream:
        rows = list(csv.DictReader(stream, delimiter='\t'))
    seen = set()
    for line, row in enumerate(rows, 2):
        if None in row or any(value is None for value in row.values()):
            raise ValueError(f'line {line}: inconsistent columns')
        if row['id'] in seen or row['implementation'] not in STATUSES:
            raise ValueError(f'line {line}: duplicate id or invalid implementation status')
        seen.add(row['id'])
        for column in ['owners', 'evidence']:
            for value in filter(None, row[column].split('|')):
                path = (ROOT / value).resolve()
                if not path.is_relative_to(ROOT) or not path.exists():
                    raise ValueError(f'line {line}: missing/escaping {column} path {value}')
        if row['implementation'] != 'implemented' and not row['remaining']:
            raise ValueError(f'line {line}: incomplete behavior needs a concrete obligation')
    lines = [
        '# Revision 240 port matrix', '',
        'Audit checkpoint: 2026-10-26. This replaces the old research-only domain table.', '',
        'Canonical editable data: [PORT-MATRIX.tsv](PORT-MATRIX.tsv). Regenerate with',
        '`python3 tools/port_matrix.py --write`; validate with `--check`.', '',
        'This is a domain audit, **not an exhaustive method inventory or a completion percentage**.',
        'The [symbol ledger](SYMBOL-LEDGER.tsv) retains method evidence; source mapping is not implementation.',
        'Read [CURRENT-AUDIT.md](CURRENT-AUDIT.md) for confirmed defects, source restrictions and acceptance gaps.', '',
        'Implementation and verification are separate. `implemented` applies only to the explicitly named subset.',
        '`partial` means working behavior still lacks branches. `not-implemented` means the semantic runtime/effect is absent.',
        '`incorrect` identifies a confirmed remaining contradiction. Test paths below describe available contracts;',
        'they do not claim those tests ran at this checkpoint or that desktop/browser visuals passed.', '',
        'P0: connected-world correctness. P1: normal gameplay parity. P2: supporting client/product behavior.', '',
        '| Priority / ID | Behavior | Implementation | Verification boundary | Rust owners / evidence | Remaining or wrong |',
        '| --- | --- | --- | --- | --- | --- |',
    ]
    for row in rows:
        paths = row['owners'].split('|')
        links = ', '.join(f'[{Path(p).name}](../../../{p})' for p in paths)
        evidence = ', '.join(f'[{Path(p).name}](../../../{p})' for p in filter(None, row['evidence'].split('|')))
        lines.append(f"| {row['priority']} / {row['id']} | {row['behavior']} | {row['implementation']} | {row['verification']} | {links}<br>{evidence} | {row['remaining']} |")
    lines += ['', 'Priority order and cross-revision evidence are maintained in [CURRENT-AUDIT.md](CURRENT-AUDIT.md).', '']
    return '\n'.join(lines), len(rows)


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    group = parser.add_mutually_exclusive_group(required=True)
    group.add_argument('--write', action='store_true')
    group.add_argument('--check', action='store_true')
    parser.add_argument("--tsps-root", type=Path, help="optional read-only verification of the supplied TypeScript evidence tree")
    args = parser.parse_args()
    if args.tsps_root:
        manifest=json.loads((DIRECTORY / 'TSPS-237-EVIDENCE.json').read_text())
        for entry in manifest['files']:
            path=(args.tsps_root / entry['path']).resolve()
            if not path.is_relative_to(args.tsps_root.resolve()) or hashlib.sha256(path.read_bytes()).hexdigest()!=entry['sha256']:
                raise SystemExit(f"TypeScript evidence changed or missing: {entry['path']}")
        print(f"{len(manifest['files'])} TypeScript evidence hashes match; no source edits")
    expected, count = render()
    if args.write:
        OUTPUT.write_text(expected)
    elif not OUTPUT.exists() or OUTPUT.read_text() != expected:
        raise SystemExit('PORT-MATRIX.md differs from TSV; run tools/port_matrix.py --write')
    print(f'{count} domain rows validated; no semantic completion percentage inferred')


if __name__ == '__main__':
    main()
