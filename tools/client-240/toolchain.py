#!/usr/bin/env python3
"""Check repository-pinned research inputs/tools and resolve the host JDK."""
import argparse
import hashlib
import json
import os
from pathlib import Path
import re
import shutil
import subprocess
import sys

ROOT = Path(__file__).resolve().parents[2]
HERE = Path(__file__).resolve().parent


def executable(value):
    found = shutil.which(str(value))
    if not found:
        raise ValueError(f'missing executable: {value}; install JDK 17+ or set CLIENT240_JAVA/CLIENT240_JAVAP')
    return str(Path(found).resolve())


def runtime():
    java_home = os.environ.get('JAVA_HOME')
    default = str(Path(java_home) / 'bin/java') if java_home else 'java'
    java = executable(os.environ.get('CLIENT240_JAVA', default))
    sibling = Path(java).with_name('javap.exe' if java.endswith('.exe') else 'javap')
    javap = executable(os.environ.get('CLIENT240_JAVAP', str(sibling) if sibling.is_file() else 'javap'))
    for tool in [java, javap]:
        result = subprocess.run([tool, '-version'], text=True, capture_output=True, timeout=20)
        match = re.search(r'(?:version\s+)?"?(\d+)(?:\.(\d+))?', result.stdout + result.stderr)
        if result.returncode or not match:
            raise ValueError(f'cannot determine JDK version: {tool}')
        major = int(match[2]) if match[1] == '1' else int(match[1])
        if major < 17:
            raise ValueError(f'JDK 17+ required: {tool} reports {major}')
    return java, javap


def digest(path):
    return hashlib.sha256(path.read_bytes()).hexdigest()


def checked_path(relative):
    path = (ROOT / relative).resolve()
    if not path.is_relative_to(ROOT):
        raise ValueError(f'manifest path escapes repository: {relative}')
    return path


def check():
    dependencies = json.loads((HERE / 'dependencies.json').read_text())
    deps = dependencies['artifacts'] + dependencies.get('sources', [])
    inputs = json.loads((HERE / 'inputs.json').read_text())['artifacts']
    vendor = Path(os.environ.get('CLIENT240_TOOLS', HERE / 'vendor')).resolve()
    for entry in deps + inputs:
        path = checked_path(entry['path'])
        if entry in deps:
            path = vendor / path.relative_to(HERE / 'vendor')
            if not checked_path(entry['license_path']).is_file():
                raise ValueError(f'missing upstream license: {entry["license_path"]}')
        if entry.get('role') == 'semantic':
            path = Path(os.environ.get('CLIENT240_SEMANTIC_JAR', path)).resolve()
        if not path.is_file():
            raise ValueError(f'missing pinned artifact: {path}')
        actual = digest(path)
        if actual != entry['sha256']:
            raise ValueError(f'SHA-256 mismatch: {path}; expected {entry["sha256"]}, found {actual}')
    runtime()
    print(f'pinned tools: {len(dependencies["artifacts"])}; source archives: {len(dependencies.get("sources", []))}; pinned inputs: {len(inputs)}; JDK 17+: OK')


def output_path(value):
    cache = Path(os.environ.get('CLIENT240_CACHE', ROOT / '.cache/client-240')).resolve()
    path = Path(value).resolve()
    if cache in (ROOT, ROOT / 'references', Path.home().resolve(), Path('/')) or cache.is_relative_to(ROOT / 'references'):
        raise ValueError(f'unsafe cache directory: {cache}')
    if path == cache or not path.is_relative_to(cache):
        raise ValueError(f'generated output must be a child of CLIENT240_CACHE ({cache}): {path}')
    if path.is_relative_to(ROOT / 'references'):
        raise ValueError('reference trees are read-only')
    return path


def docs_path(value):
    path = Path(value).resolve()
    allowed = (ROOT / 'docs/reference/client-240', ROOT / '.cache', ROOT / 'target')
    if not any(path == base or path.is_relative_to(base) for base in allowed):
        raise ValueError(f'CLIENT240_DOCS must be the research tree or repository scratch space: {path}')
    return path


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    modes = parser.add_mutually_exclusive_group(required=True)
    modes.add_argument('--check', action='store_true')
    modes.add_argument('--runtime', action='store_true')
    modes.add_argument('--output')
    modes.add_argument('--docs')
    modes.add_argument('--sha')
    args = parser.parse_args()
    try:
        if args.check:
            check()
        elif args.runtime:
            print('\n'.join(runtime()))
        elif args.output:
            print(output_path(args.output))
        elif args.docs:
            print(docs_path(args.docs))
        else:
            print(digest(Path(args.sha)))
    except (ValueError, OSError, subprocess.SubprocessError, KeyError) as error:
        print(f'client-240 toolchain: {error}', file=sys.stderr)
        return 1
    return 0


if __name__ == '__main__':
    raise SystemExit(main())
