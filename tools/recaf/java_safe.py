#!/usr/bin/env python3
"""Build reversible class-identity and Java-keyword reading views without behavior cleanup."""
import json
import argparse
import os
from pathlib import Path
import subprocess
import sys
import zipfile

from metadata_view import ROOT, build_metadata_view, class_view, sha
sys.path.insert(0, str(ROOT / 'tools/client-240'))
from toolchain import check, runtime, output_path

KEYWORDS = set('abstract assert boolean break byte case catch char class const continue default do double else enum extends final finally float for goto if implements import instanceof int interface long native new package private protected public return short static strictfp super switch synchronized this throw throws transient try void volatile while true false null _'.split())


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--identity', action='store_true', help='reuse pinned stable class identities without semantic transforms')
    args = parser.parse_args()
    check()
    java, _ = runtime()
    original = ROOT / 'references/gamepacks/osrs-240/injected-client-1.12.39.jar'
    clean = ROOT / '.cache/client-240/recaf/views/original-metadata.jar'
    cleanup = build_metadata_view(original, clean)
    out = output_path(ROOT / '.cache/client-240/recaf/views/java-safe')
    out.mkdir(parents=True, exist_ok=True)
    inventory = {}
    names = set()
    with zipfile.ZipFile(clean) as archive:
        for entry in archive.namelist():
            if entry.endswith('.class'):
                owner = entry[:-6]
                _, structure, _ = class_view(archive.read(entry), clean=False)
                inventory[owner] = structure['members']
                names.add(owner)
                names.update(bytes.fromhex(member[2]).decode() for member in structure['members'])
    aliases = {}
    for name in sorted(KEYWORDS & names):
        candidate = 'bytecode_' + name
        while candidate in names:
            candidate += '_'
        names.add(candidate)
        aliases[name] = candidate
    desired_classes = {}
    identity_evidence = {}
    if args.identity:
        # Rebuild indexes from pinned inputs; never reuse potentially stale identity caches.
        subprocess.run([sys.executable, str(ROOT / 'tools/client-240/deob-v2.py'), 'index', '--jar', 'both'], check=True)
        index = ROOT / '.cache/client-240/deob-v2/index-semantic.tsv'
        identity_evidence['index_sha256'] = sha(index.read_bytes())
        for line in index.read_text().splitlines():
            parts = line.split('\t')
            if parts[0] == 'C':
                if parts[2] in desired_classes: raise ValueError('ambiguous original class identity')
                desired_classes[parts[2]] = parts[1]
        if set(desired_classes) != set(inventory):
            raise ValueError('stable identity view does not cover the exact original class set')
        identity_evidence['scope'] = 'class identities only; original member names retained except explicit keyword aliases'
    # Never reuse speculative or stale semantic member names: class/member renaming has separate proof gates.
    lines = ['tiny\t2\t0\tbytecode\tjava_safe']
    changes = []
    for owner, members in sorted(inventory.items()):
        desired = desired_classes.get(owner, owner)
        target = aliases.get(desired, desired)
        lines.append('c\t' + owner + '\t' + target)
        if target != owner:
            changes.append({'kind': 'class', 'owner': owner, 'from': owner, 'to': target})
        for kind, access, name_hex, desc_hex in members:
            name, desc = bytes.fromhex(name_hex).decode(), bytes.fromhex(desc_hex).decode()
            desired = name
            target_name = aliases.get(desired, desired)
            if target_name != name:
                lines.append('\t' + ('m' if kind == 'method' else 'f') + '\t' + desc + '\t' + name + '\t' + target_name)
                changes.append({'kind': kind, 'owner': owner, 'descriptor': desc, 'from': name, 'to': target_name})
    mapping = out / 'java-keywords.tiny'
    mapping.write_text('\n'.join(lines) + '\n')
    vendor = ROOT / 'tools/client-240/vendor'
    jars = [vendor / 'tiny-remapper-0.10.3.jar', vendor / 'mapping-io-0.6.0.jar', *sorted((vendor / 'tiny_deps').glob('*.jar'))]
    cp = os.pathsep.join(map(str, jars))
    readable = out / 'client-java-safe.jar'
    reversed_jar = out / 'roundtrip.jar'
    def remap(source, destination, before, after):
        subprocess.run([java, '-cp', cp, 'net.fabricmc.tinyremapper.Main', str(source), str(destination), str(mapping), before, after], check=True)
    remap(clean, readable, 'bytecode', 'java_safe')
    remap(readable, reversed_jar, 'java_safe', 'bytecode')
    compare = subprocess.run([java, '--source', '17', '-cp', cp, str(ROOT / 'tools/client-240/java/Client240Tool.java'),
                              'compare', str(clean), str(reversed_jar)], capture_output=True, text=True)
    if compare.returncode:
        (out / 'rejected.txt').write_text(compare.stdout + compare.stderr)
        readable.unlink(missing_ok=True)
        reversed_jar.unlink(missing_ok=True)
        (out / 'manifest.json').unlink(missing_ok=True)
        raise RuntimeError(compare.stdout + compare.stderr)
    record = {'input_sha256': cleanup['input_sha256'], 'metadata_sha256': cleanup['output_sha256'],
        'java_safe_sha256': sha(readable.read_bytes()), 'mapping_sha256': sha(mapping.read_bytes()),
        'changes': changes, 'identity_evidence': identity_evidence, 'roundtrip': compare.stdout.strip(),
        'generator_sha256': sha(Path(__file__).read_bytes()),
        'scope': 'reversible reading names and Java-keyword aliases; no control-flow cleanup, names inferred or production mapping shards changed'}
    (out / 'manifest.json').write_text(json.dumps(record, indent=2) + '\n')
    print(json.dumps({key: value for key, value in record.items() if key != 'changes'}, indent=2))
    print('aliases:', len(changes))


if __name__ == '__main__':
    main()
