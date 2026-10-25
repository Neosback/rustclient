#!/usr/bin/env python3
"""Build pinned Recaf and launch a read-only stdio MCP workspace for revision 240."""
import hashlib
import json
import os
from pathlib import Path
import re
import subprocess
import sys
import tempfile
import urllib.request
import zipfile

ROOT = Path(__file__).resolve().parents[2]
sys.path.insert(0, str(ROOT / 'tools/client-240'))
from toolchain import output_path, runtime  # noqa: E402

HERE = Path(__file__).resolve().parent
LOCK = json.loads((HERE / 'recaf.lock.json').read_text())
CACHE = output_path(Path(os.environ.get('CLIENT240_CACHE', ROOT / '.cache/client-240')) / 'recaf')
SOURCE = CACHE / ('Recaf-' + LOCK['commit'])
RECAF_JAR = SOURCE / 'recaf-ui/build/libs/recaf-ui-4.0.0-SNAPSHOT-all.jar'
CLASSES = CACHE / 'bridge-classes'


def sha(path):
    return hashlib.sha256(path.read_bytes()).hexdigest()


def java_tools():
    java, _ = runtime()
    version = subprocess.check_output([java, '-version'], stderr=subprocess.STDOUT, text=True)
    match = re.search(r'version "(\d+)', version)
    if not match or int(match.group(1)) < LOCK['java_target']:
        raise ValueError(f'Recaf bridge requires JDK {LOCK["java_target"]}+; set CLIENT240_JAVA')
    return java, str(Path(java).with_name('javac'))


def build():
    java, javac = java_tools()
    CACHE.mkdir(parents=True, exist_ok=True)
    archive = CACHE / 'source.zip'
    if not archive.exists():
        with urllib.request.urlopen(LOCK['source_url'], timeout=60) as response:
            content = response.read()
        if hashlib.sha256(content).hexdigest() != LOCK['source_sha256']:
            raise ValueError('Recaf source download checksum mismatch')
        archive.write_bytes(content)
    if sha(archive) != LOCK['source_sha256']:
        raise ValueError('Recaf source archive changed')
    # Restore upstream files from the verified archive; generated outputs stay in cache.
    with zipfile.ZipFile(archive) as stream:
        for member in stream.infolist():
            destination = (CACHE / member.filename).resolve()
            if not destination.is_relative_to(CACHE) or (member.external_attr >> 16) & 0o170000 == 0o120000:
                raise ValueError('Unsafe path in Recaf source archive')
        stream.extractall(CACHE)
    gradle_properties = SOURCE / 'gradle/wrapper/gradle-wrapper.properties'
    with gradle_properties.open('a') as stream:
        stream.write('\ndistributionSha256Sum=' + LOCK['gradle_sha256'] + '\n')
    gradlew = SOURCE / 'gradlew'
    gradlew.chmod(0o755)
    env = os.environ.copy()
    env.update(JAVA_HOME=str(Path(java).parent.parent), TARGET_VERSION=str(LOCK['java_target']),
               GRADLE_USER_HOME=str(CACHE / 'gradle-home'))
    subprocess.run([str(gradlew), ':recaf-ui:shadowJar', '-x', 'test', '--no-daemon', '--console=plain'],
                   cwd=SOURCE, env=env, check=True)
    CLASSES.mkdir(parents=True, exist_ok=True)
    subprocess.run([javac, '--release', str(LOCK['java_target']), '-cp', str(RECAF_JAR), '-d', str(CLASSES),
                    str(HERE / 'src/RecafMcp.java')], check=True)
    record = {'recaf_commit': LOCK['commit'], 'recaf_jar_sha256': sha(RECAF_JAR),
              'bridge_source_sha256': sha(HERE / 'src/RecafMcp.java'),
              'bridge_classes': {str(path.relative_to(CLASSES)): sha(path) for path in sorted(CLASSES.rglob('*.class'))}}
    (CACHE / 'build.json').write_text(json.dumps(record, indent=2) + '\n')
    print('Recaf and MCP bridge built; metadata: ' + str(CACHE / 'build.json'))


def check_build():
    record = json.loads((CACHE / 'build.json').read_text())
    if record['recaf_commit'] != LOCK['commit'] or record['recaf_jar_sha256'] != sha(RECAF_JAR):
        raise ValueError('Recaf artifact changed; rebuild')
    if record['bridge_source_sha256'] != sha(HERE / 'src/RecafMcp.java'):
        raise ValueError('Bridge source changed; rebuild')
    actual = {str(path.relative_to(CLASSES)): sha(path) for path in sorted(CLASSES.rglob('*.class'))}
    if actual != record['bridge_classes']:
        raise ValueError('Bridge class files changed; rebuild')


def main():
    if sys.argv[1:] == ['--build']:
        build()
        return
    check_build()
    java, _ = java_tools()
    if sys.argv[1:] == ['--check']:
        print('Pinned Recaf and compiled MCP bridge checksums: OK')
        return
    if sys.argv[1:]:
        raise ValueError('Usage: recaf.py [--build|--check]')
    # The MCP workspace has one fixed input, copied byte-for-byte from the pinned corpus.
    manifest = json.loads((ROOT / 'tools/client-240/inputs.json').read_text())
    original = ROOT / 'references/gamepacks/osrs-240/injected-client-1.12.39.jar'
    expected = next(item['sha256'] for item in manifest['artifacts'] if (ROOT / item['path']) == original)
    if sha(original) != expected:
        raise ValueError('Pinned original client checksum mismatch')
    sessions = CACHE / 'sessions'
    sessions.mkdir(parents=True, exist_ok=True)
    # Each MCP process owns its Recaf configuration and workspace; concurrent clients do not share state.
    session = Path(tempfile.mkdtemp(prefix='mcp-', dir=sessions))
    artifact = session / 'client-analysis.jar'
    artifact.write_bytes(original.read_bytes())
    command = [java, '-cp', os.pathsep.join([str(RECAF_JAR), str(CLASSES)]), 'dev.neosback.recaf.RecafMcp',
               str(artifact), str(session / 'data')]
    os.execv(java, command)


if __name__ == '__main__':
    try:
        main()
    except (OSError, ValueError, KeyError, subprocess.SubprocessError) as error:
        print(f'Recaf MCP: {error}. Run python3 tools/recaf/recaf.py --build if needed.', file=sys.stderr)
        raise SystemExit(1)
