#!/usr/bin/env python3
"""Exercise fail-closed setup behavior using isolated scratch files."""
import os
from pathlib import Path
import shutil
import subprocess
import sys
import tempfile

ROOT = Path(__file__).resolve().parents[2]
HERE = Path(__file__).resolve().parent


def main():
    base = ROOT / '.cache/client-240/setup-negative'
    base.mkdir(parents=True, exist_ok=True)
    with tempfile.TemporaryDirectory(dir=base) as temporary:
        scratch = Path(temporary)
        checked = []

        def run(args, *, ok=False, contains=None, env=None):
            result = subprocess.run(args, cwd=ROOT, env={**os.environ, **(env or {})}, capture_output=True, text=True)
            assert (result.returncode == 0) == ok, (args, result.returncode, result.stdout, result.stderr)
            if contains:
                assert contains in result.stdout + result.stderr, (args, result.stdout, result.stderr)
            return result

        python = sys.executable
        checker = [python, str(HERE / 'toolchain.py')]
        run([*checker, '--check'], ok=True, contains='JDK 17+: OK')
        checked.append('pinned bundle and host JDK')
        run([*checker, '--output', str(ROOT / 'references/generated')], contains='must be a child')
        run([*checker, '--output', str(ROOT / '.cache/client-240')], contains='must be a child')
        run([*checker, '--output', str(scratch / 'out')], env={'CLIENT240_CACHE': str(ROOT / 'references')}, contains='unsafe cache')
        checked.append('reference/cache-root output refusal')
        broken = scratch / 'broken.jar'
        broken.write_bytes(b'not the pinned client')
        run([*checker, '--check'], env={'CLIENT240_SEMANTIC_JAR': str(broken)}, contains='SHA-256 mismatch')
        run([*checker, '--check'], env={'CLIENT240_TOOLS': str(scratch / 'missing-tools')}, contains='missing pinned artifact')
        checked.append('corrupt and missing artifacts rejected')
        run(['bash', str(HERE / 'verify.sh'), '../escape'], contains='invalid verification label')
        checked.append('verification-label traversal refused')
        install = [python, str(HERE / 'install-skill.py'), '--dest', str(scratch / 'installed-skill')]
        run(install, ok=True)
        run(install, ok=True)
        occupied = scratch / 'occupied'
        (occupied / 'client-240-wave').mkdir(parents=True)
        sentinel = occupied / 'client-240-wave/keep.txt'
        sentinel.write_text('preserve')
        run([python, str(HERE / 'install-skill.py'), '--dest', str(occupied)], contains='already exists')
        assert sentinel.read_text() == 'preserve'
        checked.append('skill installer idempotence and non-overwrite')
        docs = scratch / 'docs'
        shutil.copytree(ROOT / 'docs/reference/client-240/mappings', docs / 'mappings')
        shutil.copytree(ROOT / 'docs/reference/client-240/ledger', docs / 'ledger')
        # A stale source symbol must stop verification before remap/decompilation.
        (docs / 'mappings/clusters/99-invalid-setup.tiny').write_text('tiny\t2\t0\tsemantic\treadable\nc\tNoSuchSetupClass\tInvalidSetupClass\n')
        run(['bash', str(HERE / 'verify.sh'), 'expected-failure'], env={
            'CLIENT240_DOCS': str(docs), 'CLIENT240_CACHE': str(scratch / 'cache')}, contains='FAIL')
        report = (docs / 'verification/expected-failure.md').read_text()
        assert 'Mapping shards valid, current and collision free: FAILED' in report
        assert 'Forward remap' not in report
        assert not (scratch / 'cache/readable.jar').exists()
        checked.append('stale shard aborts dependent remap/decompile gates')
        for script in sorted(HERE.glob('*.sh')):
            run(['bash', '-n', str(script)], ok=True)
        checked.append('all shell scripts parse')
        print('PASS: ' + '; '.join(checked))


if __name__ == '__main__':
    main()
