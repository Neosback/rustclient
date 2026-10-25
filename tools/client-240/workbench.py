#!/usr/bin/env python3
"""Build/run a content-addressed workbench snapshot with a pinned Maven bootstrap."""
import hashlib
import json
import os
from pathlib import Path
import subprocess
import sys
import tarfile
import urllib.request

from toolchain import ROOT, check, output_path, runtime


def maven(cache):
    lock = json.loads((ROOT / 'tools/client-240/workbench-build.json').read_text())
    directory = cache / 'build-tools'
    directory.mkdir(parents=True, exist_ok=True)
    archive = directory / ('apache-maven-' + lock['maven_version'] + '-bin.tar.gz')
    if not archive.is_file():
        with urllib.request.urlopen(lock['maven_url'], timeout=60) as response:
            content = response.read()
        if hashlib.sha512(content).hexdigest() != lock['maven_sha512']:
            raise ValueError('Maven distribution checksum mismatch')
        archive.write_bytes(content)
    if hashlib.sha512(archive.read_bytes()).hexdigest() != lock['maven_sha512']:
        raise ValueError(f'changed Maven distribution: {archive}')
    home = directory / ('apache-maven-' + lock['maven_version'])
    if not (home / 'bin/mvn').is_file():
        with tarfile.open(archive) as stream:
            for member in stream.getmembers():
                if not (directory / member.name).resolve().is_relative_to(directory.resolve()) or member.issym() or member.islnk():
                    raise ValueError('unsafe path in Maven archive')
            stream.extractall(directory)
    return home / 'bin/mvn'


def git(*args):
    return subprocess.check_output(['git', *args], cwd=ROOT)


def snapshot():
    revision = git('rev-parse', 'HEAD').decode().strip()
    names = git('ls-files', '-z', '--cached', '--others', '--exclude-standard', '--', 'deob-workbench').decode().split('\0')
    files = {}
    digest = hashlib.sha256()
    for name in sorted(set(filter(None, names))):
        path = ROOT / name
        if not path.exists():
            continue
        if path.is_symlink():
            raise ValueError(f'symlinks are not supported in workbench snapshots: {name}')
        content = path.read_bytes()
        files[str(Path(name).relative_to('deob-workbench'))] = content
        digest.update(name.encode() + b'\0' + hashlib.sha256(content).digest())
    if 'pom.xml' not in files:
        raise ValueError('workbench source is missing pom.xml')
    return revision, digest.hexdigest(), files


def main():
    check()
    java, _ = runtime()
    cache = Path(os.environ.get('CLIENT240_CACHE', ROOT / '.cache/client-240')).resolve()
    # Validate the cache before downloading, copying or running a build.
    output_path(cache / 'workbench')
    revision, tree, files = snapshot()
    work = cache / 'workbench' / tree
    jar = work / 'source/target/client-deob-workbench-0.1.0-SNAPSHOT.jar'
    args = sys.argv[1:]
    if args == ['--build']:
        source = work / 'source'
        for name, content in files.items():
            destination = source / name
            destination.parent.mkdir(parents=True, exist_ok=True)
            destination.write_bytes(content)
        env = os.environ.copy()
        env['JAVA_HOME'] = str(Path(java).parent.parent)
        print(f'Building workbench snapshot {tree} (base commit {revision})', flush=True)
        subprocess.run([str(maven(cache)), '-B', '-ntp', '-f', str(source / 'pom.xml'), 'package'], cwd=ROOT, env=env, check=True)
        if not jar.is_file():
            raise ValueError('Maven completed without the expected CLI artifact')
        (work / 'build.json').write_text(json.dumps({'base_commit': revision, 'source_sha256': tree,
            'files': {name: hashlib.sha256(content).hexdigest() for name, content in files.items()},
            'jar_sha256': hashlib.sha256(jar.read_bytes()).hexdigest()}, indent=2) + '\n')
        print(f'Workbench CLI: {jar}')
        return 0
    if not jar.is_file():
        raise ValueError('build this workbench snapshot first: python3 tools/client-240/workbench.py --build')
    evidence = json.loads((work / 'build.json').read_text())
    if hashlib.sha256(jar.read_bytes()).hexdigest() != evidence['jar_sha256']:
        raise ValueError('built workbench JAR changed; rebuild before analysis')
    for index, arg in enumerate(args):
        if arg == '--out':
            if index + 1 >= len(args):
                raise ValueError('--out requires a path')
            args[index + 1] = str(output_path(args[index + 1]))
        elif arg.startswith('--out='):
            args[index] = '--out=' + str(output_path(arg.split('=', 1)[1]))
    print(f'Workbench source tree: {tree}', flush=True)
    return subprocess.run([java, '-jar', str(jar), *args], cwd=ROOT).returncode


if __name__ == '__main__':
    try:
        raise SystemExit(main())
    except (ValueError, OSError, subprocess.SubprocessError) as error:
        print(f'client-240 workbench: {error}', file=sys.stderr)
        raise SystemExit(1)
