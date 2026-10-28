#!/usr/bin/env python3
"""Validate the 240 symbol ledger and generate a declaration inventory without editing evidence.

Declaration counts describe this decompiler output (including aliases/injection), never
semantic implementation percentages. Only an explicit method row counts as mapped.
"""
import argparse
import collections
import csv
import hashlib
import json
import re
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
LEDGER = ROOT / 'docs/reference/rs240-port/SYMBOL-LEDGER.tsv'
STATUSES = {'untriaged', 'evidence-found', 'specified', 'partial', 'implemented',
            'verified', 'not-needed', 'injection-only', 'blocked'}
VERIFICATIONS = {'unrecorded', 'source-reviewed', 'contract-tested', 'live-tested',
                 'visual-accepted', 'not-applicable'}
# Vineflower uses three spaces; the pinned RuneLite Java helpers use one tab. Keep both
# at member indentation so calls nested inside method bodies are not declarations.
DECLARATION = re.compile(r'^(?:   (?![ @])|\t(?![ \t@]))(?P<header>[^\n;{}]+\([^{};]*?\)\s*(?:throws\s+[^{};]+)?)[{;]', re.M)
NON_CODE = re.compile(r'"(?:\\.|[^"\\])*"|\'(?:\\.|[^\'\\])*\'|//[^\n]*|/\*[\s\S]*?\*/')


def declarations(path):
    source = path.read_text(errors='replace')
    clean = NON_CODE.sub(lambda m: ''.join('\n' if c == '\n' else ' ' for c in m[0]), source)
    for match in DECLARATION.finditer(clean):
        header = match['header']
        prefix = header.split('(', 1)[0]
        if '=' in prefix or prefix.strip().startswith(('return ', 'new ', 'if ', 'for ', 'while ', 'switch ')):
            continue
        name = re.search(r'([\w$]+)\s*$', prefix)
        if not name:
            continue
        yield name[1], source.count('\n', 0, match.start()) + 1, ' '.join(source[match.start():match.end()-1].split())


def inside(path):
    candidate = (ROOT / path).resolve()
    if not candidate.is_relative_to(ROOT):
        raise ValueError(f'path escapes workspace: {path}')
    return candidate


def audit():
    with LEDGER.open(newline='') as file:
        rows = list(csv.DictReader(file, delimiter='\t'))
    errors, seen, contents, methods = [], set(), {}, {}
    for line, row in enumerate(rows, 2):
        label = f'{LEDGER.relative_to(ROOT)}:{line}'
        if None in row or any(value is None for value in row.values()):
            errors.append(f'{label}: inconsistent TSV columns')
            continue
        identity = row['source_path'], row['symbol'], row['kind']
        if identity in seen:
            errors.append(f'{label}: duplicate symbol identity {row["symbol"]}')
        seen.add(identity)
        if row['status'] not in STATUSES or row['confidence'] not in {'A', 'B', 'C', 'D'}:
            errors.append(f'{label}: unknown status/confidence')
        if row['verification'] not in VERIFICATIONS:
            errors.append(f'{label}: unknown verification level')
        if row['verification'] in {'contract-tested', 'live-tested', 'visual-accepted'} and row['evidence'] in {'', '-'}:
            errors.append(f'{label}: verification needs named evidence')
        try:
            path = inside(row['source_path'])
            if not path.is_file():
                errors.append(f'{label}: missing source {row["source_path"]}')
                continue
            if row['kind'] in {'method', 'field'}:
                symbol = row['symbol'].rsplit('.', 1)[-1]
                if row['kind'] == 'method':
                    names = methods.setdefault(path, {name for name, _, _ in declarations(path)})
                    found = symbol in names
                else:
                    text = contents.setdefault(path, path.read_text(errors='replace'))
                    found = bool(re.search(r'\b' + re.escape(symbol) + r'\b', text))
                if not found:
                    errors.append(f'{label}: source declaration missing for {row["symbol"]}')
            for owner in row['rust_paths'].split('|'):
                if owner != '-' and not inside(owner).is_file():
                    errors.append(f'{label}: missing Rust owner {owner}')
        except ValueError as error:
            errors.append(f'{label}: {error}')
    manifest = json.loads((ROOT / 'docs/reference/source-manifest.json').read_text())
    # The method ledger also cites pinned RuneLite plugin/helper sources. Checking only
    # rl-suite-240 incorrectly reported those manifest-backed entries as unpinned.
    pins = {}
    for source in manifest['sources']:
        for entry in source.get('files', []):
            name = str(Path(source['root']) / entry['path'])
            if name in pins and pins[name] != entry['sha256']:
                errors.append(f'conflicting source pins: {name}')
            pins[name] = entry['sha256']
    used = {row['source_path'] for row in rows}
    for name in sorted(used):
        if name in pins and inside(name).is_file() and hashlib.sha256(inside(name).read_bytes()).hexdigest() != pins[name]:
            errors.append(f'changed pinned source: {name}')
    return rows, errors, used - pins.keys()


def inventory(rows):
    mapping = {(row['source_path'], row['symbol'].rsplit('.', 1)[-1]): row for row in rows if row['kind'] == 'method'}
    for path in sorted((ROOT / 'references/rl-suite-240/sources-semantic').glob('*.java')):
        relative = str(path.relative_to(ROOT))
        digest = hashlib.sha256(path.read_bytes()).hexdigest()
        for name, line, signature in declarations(path):
            row = mapping.get((relative, name))
            yield {'source_path': relative, 'method': name, 'line': line, 'source_sha256': digest,
                   'mapping_status': row['status'] if row else 'untriaged',
                   'verification': row['verification'] if row else 'unrecorded', 'declaration': signature}


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--check', action='store_true', help='validate the ledger (also runs with report/inventory)')
    parser.add_argument('--report', type=Path, help='write a JSON summary to a generated output path')
    parser.add_argument('--inventory', type=Path, help='write all top-level method/constructor declarations as TSV')
    parser.add_argument('--domain', help='restrict the displayed/JSON summary to one ledger domain')
    args = parser.parse_args()
    for output in [args.report, args.inventory]:
        if output and not output.resolve().is_relative_to(ROOT / 'target'):
            parser.error('generated reports must be under the workspace target/ directory')
    rows, errors, unpinned = audit()
    for error in errors:
        print(f'ERROR: {error}')
    if errors:
        return 1
    selected = [row for row in rows if not args.domain or row['domain'] == args.domain]
    summary = {'ledger_rows': len(rows), 'selected_rows': len(selected),
               'statuses': dict(collections.Counter(row['status'] for row in selected)),
               'verification': dict(collections.Counter(row['verification'] for row in selected)),
               'unpinned_ledger_sources': sorted(unpinned),
               'limits': 'Declaration/source mapping is not semantic completion, passing tests, or visual parity.'}
    if args.inventory:
        entries = list(inventory(rows))
        args.inventory.parent.mkdir(parents=True, exist_ok=True)
        with args.inventory.open('w', newline='') as file:
            writer = csv.DictWriter(file, fieldnames=list(entries[0]), delimiter='\t')
            writer.writeheader()
            writer.writerows(entries)
        summary['declarations'] = len(entries)
        summary['untriaged_declarations'] = sum(row['mapping_status'] == 'untriaged' for row in entries)
    if args.report:
        args.report.parent.mkdir(parents=True, exist_ok=True)
        args.report.write_text(json.dumps(summary, indent=2) + '\n')
    print(json.dumps(summary, indent=2))
    return 0


if __name__ == '__main__':
    raise SystemExit(main())
