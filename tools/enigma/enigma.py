#!/usr/bin/env python3
"""Build and launch the pinned Fabric Enigma MCP fork with isolated RustClient draft mappings."""
import argparse
import hashlib
import json
import os
from pathlib import Path
import shutil
import socket
import subprocess
import sys
import time
import urllib.request
import zipfile

ROOT = Path(__file__).resolve().parents[2]
HERE = Path(__file__).resolve().parent
sys.path.insert(0, str(ROOT / 'tools/client-240'))
sys.path.insert(0, str(ROOT / 'tools/recaf'))
from toolchain import output_path, runtime, check
from metadata_view import build_metadata_view
from integrate import integrate

LOCK = json.loads((HERE / 'enigma.lock.json').read_text())
CACHE = output_path(Path(os.environ.get('CLIENT240_CACHE', ROOT / '.cache/client-240')) / 'enigma')


def sha(path): return hashlib.sha256(path.read_bytes()).hexdigest()


def recipe():
    files = [HERE / 'enigma.lock.json', HERE / 'upstream-mcp.patch', HERE / 'integrate.py', *sorted((HERE / 'java').rglob('*.java'))]
    return hashlib.sha256(b''.join(str(p.relative_to(HERE)).encode() + b'\0' + p.read_bytes() for p in files)).hexdigest()


def location():
    source = CACHE / ('build-' + recipe()[:20]) / ('Enigma-' + LOCK['commit'])
    return source, source / 'enigma-swing/build/libs/enigma-swing-4.0.2+local-all.jar'


def build():
    check()
    java, _ = runtime()
    CACHE.mkdir(parents=True, exist_ok=True)
    if (CACHE / 'build.json').is_file():
        try:
            jar = check_build()
            print('Enigma MCP already built and verified:', jar)
            return
        except (ValueError, OSError, KeyError):
            pass
    archive = CACHE / 'source.zip'
    if not archive.exists():
        with urllib.request.urlopen(LOCK['source_url'], timeout=60) as response: content = response.read()
        if hashlib.sha256(content).hexdigest() != LOCK['source_sha256']: raise ValueError('upstream archive checksum mismatch')
        archive.write_bytes(content)
    if sha(archive) != LOCK['source_sha256'] or sha(HERE / 'upstream-mcp.patch') != LOCK['mcp_patch_sha256']:
        raise ValueError('pinned Enigma input changed')
    source, jar = location()
    source.parent.mkdir(parents=True, exist_ok=True)
    if source.exists(): shutil.rmtree(source)
    with zipfile.ZipFile(archive) as stream:
        for member in stream.infolist():
            if not (source.parent / member.filename).resolve().is_relative_to(source.parent.resolve()) or (member.external_attr >> 16) & 0o170000 == 0o120000:
                raise ValueError('unsafe upstream archive path')
        stream.extractall(source.parent)
    subprocess.run(['git', 'apply', '--unsafe-paths', '--directory=' + str(source), str(HERE / 'upstream-mcp.patch')], cwd=ROOT, check=True)
    integrate(source)
    for path in (HERE / 'java').rglob('*.java'):
        dest = source / 'enigma-swing/src/main/java' / path.relative_to(HERE / 'java')
        dest.parent.mkdir(parents=True, exist_ok=True); dest.write_bytes(path.read_bytes())
    properties = source / 'gradle/wrapper/gradle-wrapper.properties'
    with properties.open('a') as stream: stream.write('\ndistributionSha256Sum=' + LOCK['gradle_sha256'] + '\n')
    gradlew = source / 'gradlew'; gradlew.chmod(0o755)
    subprocess.run([str(gradlew), ':enigma-swing:shadowJar', '-x', 'test', '--no-daemon', '--console=plain'], cwd=source,
        env={**os.environ, 'JAVA_HOME': str(Path(java).parent.parent), 'GRADLE_USER_HOME': str(CACHE / 'gradle-home')}, check=True)
    record = {'recipe_sha256': recipe(), 'jar_sha256': sha(jar), 'source_commit': LOCK['commit'], 'jar': str(jar.relative_to(ROOT))}
    (CACHE / 'build.json').write_text(json.dumps(record, indent=2) + '\n')
    print('Built Enigma MCP:', jar)


def check_build():
    record = json.loads((CACHE / 'build.json').read_text()); _, jar = location()
    if record['recipe_sha256'] != recipe() or sha(jar) != record['jar_sha256']: raise ValueError('Enigma recipe/artifact changed; rebuild')
    return jar


def prepare_session():
    check()
    original = ROOT / 'references/gamepacks/osrs-240/injected-client-1.12.39.jar'
    metadata = CACHE / 'metadata-clean.jar'
    evidence = build_metadata_view(original, metadata)
    # Import the reading bundle's class/keyword map into a separate draft session.
    mapping = ROOT / '.cache/client-240/recaf/best-reading/class-names-and-keywords.tiny'
    if not mapping.is_file():
        subprocess.run([sys.executable, str(ROOT / 'tools/recaf/java_safe.py'), '--identity'], cwd=ROOT, check=True)
        mapping = ROOT / '.cache/client-240/recaf/views/java-safe/java-keywords.tiny'
    import tempfile
    sessions = CACHE / 'sessions'; sessions.mkdir(parents=True, exist_ok=True)
    session = Path(tempfile.mkdtemp(prefix='review-', dir=sessions))
    shutil.copyfile(metadata, session / 'client-analysis.jar')
    shutil.copyfile(mapping, session / 'draft.tiny')
    return session, evidence


def start(port):
    if not 1 <= port <= 65535: raise ValueError('port must be between 1 and 65535')
    jar = check_build(); java, _ = runtime()
    with socket.socket() as probe:
        probe.bind(('127.0.0.1', port))
    session, evidence = prepare_session()
    command = [java, '-Xmx3g', '-Drustclient.enigma.configDir=' + str(session / 'config'),
        '-Drustclient.enigma.session=' + str(session), '-Drustclient.enigma.input=' + str(session / 'client-analysis.jar'),
        '-Drustclient.enigma.originalSha256=' + evidence['input_sha256'], '-Drustclient.enigma.analysisSha256=' + evidence['output_sha256'],
        '-cp', str(jar), 'dev.neosback.enigma.LaunchReview', str(session / 'client-analysis.jar'), str(session / 'draft.tiny'), str(port)]
    with (session / 'enigma.log').open('w') as log:
        child = subprocess.Popen(command, cwd=ROOT, stdout=log, stderr=subprocess.STDOUT, start_new_session=True)
    record = {'pid': child.pid, 'url': f'http://127.0.0.1:{port}/mcp', 'session': str(session.relative_to(ROOT)),
        'original_sha256': evidence['input_sha256'], 'analysis_sha256': evidence['output_sha256'], 'initial_mapping_sha256': sha(session / 'draft.tiny')}
    (session / 'session.json').write_text(json.dumps(record, indent=2) + '\n')
    (CACHE / 'active.json').write_text(json.dumps(record, indent=2) + '\n')
    print(json.dumps(record, indent=2), flush=True)


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    group = parser.add_mutually_exclusive_group(required=True)
    group.add_argument('--build', action='store_true'); group.add_argument('--check', action='store_true'); group.add_argument('--start', action='store_true')
    parser.add_argument('--port', type=int, default=32412)
    args = parser.parse_args()
    if args.build: build()
    elif args.check: print('Enigma MCP recipe and built JAR: OK', check_build())
    else: start(args.port)


if __name__ == '__main__':
    try: main()
    except (OSError, ValueError, KeyError, subprocess.SubprocessError) as error:
        print('Enigma MCP:', error, file=sys.stderr); raise SystemExit(1)
