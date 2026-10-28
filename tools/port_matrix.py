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


def check_packets():
    """Validate the manual dispatch audit against the complete pinned ID tables.

    This checks inventory consistency, never derives semantic support from code.
    """
    expected = {}
    for direction, name in [('client-to-server', 'client'), ('server-to-client', 'server')]:
        path = ROOT / f'docs/reference/client-240/inputs/{name}-packets.tsv'
        for line in path.read_text().splitlines():
            if not line.strip() or line.startswith('#'):
                continue
            opcode, packet, length, *_ = line.split('\t')
            expected[direction, opcode] = packet, length
    with (DIRECTORY / 'PACKET-COVERAGE.tsv').open(newline='') as stream:
        rows = list(csv.DictReader(stream, delimiter='\t'))
    seen = set()
    allowed = {'encoded-subset', 'missing-encoder', 'semantic-subset', 'partial',
               'codec-context', 'framed-unhandled', 'rejected', 'unassigned'}
    for line, row in enumerate(rows, 2):
        if None in row or any(value is None for value in row.values()):
            raise ValueError(f'packet line {line}: inconsistent columns')
        key = row['direction'], row['opcode']
        if key in seen or expected.get(key) != (row['name'], row['length']):
            raise ValueError(f'packet line {line}: duplicate or changed source identity')
        seen.add(key)
        if row['status'] not in allowed or not row['remaining']:
            raise ValueError(f'packet line {line}: invalid status or missing obligation')
        owner = (ROOT / row['owner']).resolve()
        if not owner.is_relative_to(ROOT) or not owner.is_file():
            raise ValueError(f'packet line {line}: missing/escaping owner')
    if seen != expected.keys():
        raise ValueError('packet audit does not cover the full pinned ID denominator')
    return len(rows)


def render():
    baseline = json.loads((DIRECTORY / 'REVIEW-BASELINE.json').read_text())
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
        f"Audit checkpoint: {baseline['review_date']}; inspected Rust `{baseline['reviewed_rust_commit'][:8]}`.", '',
        'Contract: [SPEC-0019](../../specs/SPEC-0019-port-parity.md). '
        'Execution order: [PORTING-ROADMAP.md](../../plan/PORTING-ROADMAP.md).', '',
        'Canonical editable data: [PORT-MATRIX.tsv](PORT-MATRIX.tsv). Regenerate with',
        '`python3 tools/port_matrix.py --write`; validate with `--check`.', '',
        'This is a domain audit, **not an exhaustive method inventory or a completion percentage**.',
        'The [symbol ledger](SYMBOL-LEDGER.tsv) retains method evidence; source mapping is not implementation.',
        'Read [CURRENT-AUDIT.md](CURRENT-AUDIT.md) for confirmed defects, source restrictions and acceptance gaps.', '',
        '[PACKET-COVERAGE.tsv](PACKET-COVERAGE.tsv) inventories packet dispatch/encode boundaries; '
        '[REVIEW-BASELINE.json](REVIEW-BASELINE.json) binds this review to inspected files.', '',
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
    packet_count = check_packets()
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
    print(f'{packet_count} packet identities validated; dispatch audit is not end-to-end support')


if __name__ == '__main__':
    main()
