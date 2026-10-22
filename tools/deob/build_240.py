#!/usr/bin/env python3
"""Build the pinned 235 deob tool in scratch space; never modify reference inputs."""
import argparse
import hashlib
import json
import os
from pathlib import Path
import shutil
import subprocess
import urllib.request

ROOT = Path(__file__).resolve().parents[2]
TOOLS = Path(__file__).resolve().parent
REFERENCE = ROOT / 'references/RuneLite-melxin'
EXPECTED_TREE = '6d8d2bdf749a74c1dc32dd67b6bc29a89c049113'


def digest(path):
    return hashlib.sha256(path.read_bytes()).hexdigest()


def run(command, cwd, log):
    print('Running:', command[0], '->', log.name, flush=True)
    with log.open('w') as stream:
        result = subprocess.run(list(map(str, command)), cwd=cwd, stdout=stream, stderr=subprocess.STDOUT)
    if result.returncode:
        raise RuntimeError(f'{log}: command failed ({result.returncode})\n{log.read_text()[-5000:]}')


def java_home(version):
    return Path(subprocess.check_output(['/usr/libexec/java_home', '-v', version], text=True).strip())


def argument_file(path, arguments):
    # javac argument files have their own quoting, independent of shell quoting.
    path.write_text('\n'.join('"' + str(a).replace('\\', '\\\\').replace('"', '\\"') + '"' for a in arguments) + '\n')


def dependency(entry, output):
    group, artifact, version = entry['coordinate'].split(':')
    name = f'{artifact}-{version}.jar'
    target = output / name
    if target.exists():
        if digest(target) != entry['sha256']:
            raise RuntimeError(f'Modified dependency: {target}')
        return target
    caches = Path.home() / '.gradle/caches/modules-2/files-2.1' / group / artifact / version
    candidates = list(caches.glob('*/' + name))
    candidates.append(Path.home() / '.m2/repository' / group.replace('.', '/') / artifact / version / name)
    source = next((p for p in candidates if p.is_file() and digest(p) == entry['sha256']), None)
    if source:
        shutil.copyfile(source, target)
    else:
        path = f'{group.replace(".", "/")}/{artifact}/{version}/{name}'
        base = 'https://raw.githubusercontent.com/melxin/hosting/master/' if artifact == 'fernflower' else 'https://repo.maven.apache.org/maven2/'
        with urllib.request.urlopen(base + path, timeout=60) as response:
            content = response.read()
        if hashlib.sha256(content).hexdigest() != entry['sha256']:
            raise RuntimeError(f'Dependency hash mismatch: {entry["coordinate"]}')
        target.write_bytes(content)
    return target


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--work', type=Path, default=ROOT / 'target/deob-240-rebuild')
    parser.add_argument('--java11', type=Path)
    args = parser.parse_args()
    work = args.work.resolve()
    if work == REFERENCE or REFERENCE in work.parents or work == ROOT or ROOT in work.parents and 'references' in work.relative_to(ROOT).parts:
        raise SystemExit('Build output must be scratch space outside references')
    if work.exists():
        raise SystemExit('Choose a fresh scratch directory; existing output is not overwritten')
    tree = subprocess.check_output(['git', 'rev-parse', 'HEAD:references/RuneLite-melxin'], cwd=ROOT, text=True).strip()
    if tree != EXPECTED_TREE:
        raise SystemExit('The 235 tool snapshot changed; review and repin before rebuilding')
    changes = subprocess.check_output(['git', 'status', '--porcelain', '--', 'references/RuneLite-melxin'], cwd=ROOT, text=True)
    if changes.strip():
        raise SystemExit('The reference tool tree has local changes')
    work.mkdir(parents=True)
    dependencies = work / 'dependencies'
    dependencies.mkdir()
    entries = json.loads((TOOLS / 'dependencies.json').read_text())
    jars = [dependency(entry, dependencies) for entry in entries]
    for module in ['deobfuscator', 'runescape-api', 'injection-annotations']:
        shutil.copytree(REFERENCE / module / 'src/main/java', work / 'source' / module)
    cache_source = work / 'source/cache/net/runelite/cache/script/Opcodes.java'
    cache_source.parent.mkdir(parents=True)
    shutil.copyfile(REFERENCE / 'cache/src/main/java/net/runelite/cache/script/Opcodes.java', cache_source)
    shutil.copytree(REFERENCE / 'runelite-api/build/classes/java/main', work / 'api-classes')
    run(['git', 'apply', '--unsafe-paths', '--directory=' + str(work / 'source'), str(TOOLS / 'injected-240-compat.patch')], ROOT, work / 'patch.log')
    classes = work / 'classes'
    classes.mkdir()
    cp = os.pathsep.join(map(str, [classes, work / 'api-classes'] + jars))
    java = (args.java11 or java_home('11')) / 'bin'
    sources = sorted((work / 'source').rglob('*.java'))
    sources.extend(TOOLS / name for name in ['AuditJar.java', 'NormalizeJar.java', 'SaveJar.java'])
    arguments = ['--release', '11', '-encoding', 'UTF-8', '-classpath', cp,
                 '-processorpath', str(dependencies / 'lombok-1.18.30.jar'), '-d', classes] + sources
    argument_file(work / 'compile.args', arguments)
    run([java / 'javac', '@' + str(work / 'compile.args')], work, work / 'compile.log')
    binary = ROOT / 'references/gamepacks/osrs-240/injected-client-1.12.39.jar'
    provenance = json.loads((ROOT / 'docs/reference/gamepack-240.json').read_text())
    if digest(binary) != provenance['sha256']:
        raise SystemExit('Input JAR hash differs from the verified reference')
    (classes / 'deob.properties').write_text(f'rs.version=240\nvanilla.jar={binary}\nrs.client={work / "baseline-235.jar"}\n')
    (work / 'runtime-classpath.txt').write_text(cp)
    (work / 'build-provenance.json').write_text(json.dumps({
        'tool_reference_git_tree': tree, 'input_sha256': digest(binary),
        'scratch_patch_sha256': digest(TOOLS / 'injected-240-compat.patch'),
        'dependency_artifacts': entries, 'java11': str(java),
        'source_count': len(sources),
        'profile': 'injected input: retain classes/members/parameters; preserve nests and bootstrap handles',
    }, indent=2) + '\n')
    print(f'Tool built in {work}. Run the explicit stages documented in tools/deob/README.md.', flush=True)


if __name__ == '__main__':
    main()
