#!/usr/bin/env python3
"""Exercise the toolchain on committed mappings without rewriting active wave outputs."""
import argparse
import os
from pathlib import Path
import re
import subprocess
import uuid

ROOT = Path(__file__).resolve().parents[2]
DOCS = Path('docs/reference/client-240')


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('label', nargs='?', default='portable-setup')
    args = parser.parse_args()
    if not re.fullmatch(r'[A-Za-z0-9][A-Za-z0-9._-]*', args.label):
        parser.error('use a simple report label without path separators')
    work = ROOT / '.cache/client-240' / ('setup-' + uuid.uuid4().hex[:12])
    docs = work / 'docs'
    revision = subprocess.check_output(['git', 'rev-parse', 'HEAD'], cwd=ROOT, text=True).strip()
    files = subprocess.check_output(['git', 'ls-tree', '-r', '--name-only', revision, '--', str(DOCS)], cwd=ROOT, text=True).splitlines()
    copied = 0
    for name in files:
        relative = Path(name).relative_to(DOCS)
        if relative.parts[0] not in {'mappings', 'ledger'}:
            continue
        output = docs / relative
        output.parent.mkdir(parents=True, exist_ok=True)
        output.write_bytes(subprocess.check_output(['git', 'show', f'{revision}:{name}'], cwd=ROOT))
        copied += 1
    if not copied:
        parser.error('no committed mapping/ledger baseline found')
    env = os.environ.copy()
    env.update(CLIENT240_DOCS=str(docs), CLIENT240_CACHE=str(work / 'generated'))
    print(f'Verifying committed mapping baseline {revision}; active wave files stay untouched', flush=True)
    result = subprocess.run(['bash', str(ROOT / 'tools/client-240/verify.sh'), args.label], cwd=ROOT, env=env)
    report = docs / 'verification' / (args.label + '.md')
    if report.exists():
        destination = ROOT / DOCS / 'verification' / report.name
        content = report.read_text() + f'\nMapping baseline: `{revision}` (committed shards only).\nToolchain: current repository sources and bundled artifacts.\nUncommitted naming work was not merged or modified by this setup check.\n'
        destination.write_text(content.replace(str(ROOT) + '/', '<repo>/'))
        print(f'Setup report: {destination.relative_to(ROOT)}')
    return result.returncode


if __name__ == '__main__':
    raise SystemExit(main())
