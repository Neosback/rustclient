#!/usr/bin/env python3
"""Check GPU source coverage and frozen hashes, without claiming behavioral parity.

--require-complete also fails for unresolved implementation/acceptance gates.
No reference or ledger files are written by this checker.
"""
import argparse
import hashlib
import json
from pathlib import Path
import sys

ROOT = Path(__file__).resolve().parents[2]
PREFIX = 'references/open-runelite-rev-240/runelite-client/src/'
JAVA = PREFIX + 'main/java/net/runelite/client/plugins/gpu/'
RESOURCES = PREFIX + 'main/resources/net/runelite/client/plugins/gpu/'
TESTS = PREFIX + 'test/java/net/runelite/client/plugins/gpu/'
LEDGER = ROOT / 'docs/reference/builtin-plugins/gpu-audit.json'


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--check', action='store_true', help='check only (the default)')
    parser.add_argument('--require-complete', action='store_true')
    args = parser.parse_args()
    ledger = json.loads(LEDGER.read_text())
    inventory = json.loads((LEDGER.parent / 'source-inventory.json').read_text())
    errors = []
    files = ledger['files']
    paths = [row['path'] for row in files]
    if len(paths) != len(set(paths)):
        errors.append('duplicate ledger source path')
    expected = set()
    for root, pattern in ((JAVA, '*.java'), (RESOURCES, '*'), (TESTS, '*Test.java'), ('runelite-gpu/', '*')):
        expected.update(str(path.relative_to(ROOT)) for path in (ROOT / root).rglob(pattern) if path.is_file())
    if set(paths) != expected:
        errors.append(f'source coverage changed: missing={sorted(expected-set(paths))}, stale={sorted(set(paths)-expected)}')
    statuses = {'implemented', 'partial', 'unimplemented', 'engine-adaptation', 'excluded', 'bookkeeping'}
    for row in files:
        path = ROOT / row['path']
        if not path.is_file() or hashlib.sha256(path.read_bytes()).hexdigest() != row['sha256']:
            errors.append(f"source hash changed: {row['path']}")
        if row['status'] not in statuses or not row['verification'] or not row['notes']:
            errors.append(f"invalid status/evidence: {row['path']}")
        for owner in row['owners']:
            if not (ROOT / owner).is_file():
                errors.append(f"missing Rust/tool owner: {owner}")
        if row.get('bundledCopy'):
            bundled = ROOT / row['bundledCopy']
            if not bundled.is_file() or path.read_bytes() != bundled.read_bytes():
                errors.append(f"bundled region resource differs: {row['bundledCopy']}")
        if row.get('matches'):
            other = ROOT / row['matches']
            if not path.is_file() or path.read_bytes() != other.read_bytes():
                errors.append(f"supplied resource differs: {row['path']}")
    source_settings = {row['key']: row for row in inventory['settings'] if '/plugins/gpu/' in row['path']}
    settings = {row['key']: row for row in ledger['settings']}
    if len(settings) != len(ledger['settings']) or settings.keys() != source_settings.keys():
        errors.append('GPU setting coverage changed')
    for key, source in source_settings.items():
        row = settings.get(key, {})
        if row.get('default') != source['defaultExpression'] or row.get('status') != source['status']:
            errors.append(f'setting inventory/ledger mismatch: {key}')
    if errors:
        print('\n'.join(errors), file=sys.stderr)
        return 1
    print(f"GPU inventory verified: {len(settings)} settings; {len(files)} pinned source/resource/test files.")
    print(f"Open gates: {len(ledger['openGates'])}; hashes/coverage are not parity or completion.")
    if args.require_complete and ledger['openGates']:
        for gate in ledger['openGates']:
            print(f"  {gate['id']}: {gate['requirement']}")
        return 2
    return 0


if __name__ == '__main__':
    sys.exit(main())
