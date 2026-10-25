#!/usr/bin/env python3
"""Compare pinned Recaf engines and bundled renderers on the same revision-240 classes."""
import argparse
from collections import Counter
import hashlib
import json
import os
from pathlib import Path
import subprocess
import sys
import time
import zipfile

from mcp_client import RecafClient
from metadata_view import build_metadata_view
from recaf import ROOT, CACHE, RECAF_JAR, CLASSES, java_tools, check_build
sys.path.insert(0, str(ROOT / 'tools/client-240'))
from toolchain import check, output_path

SAMPLES = ['aa', 'Actor', 'Player', 'NPC', 'Widget', 'PacketBuffer', 'Model', 'client']
ENGINES = ['Vineflower', 'CFR', 'Procyon']
MARKERS = ["Couldn't be decompiled", 'This method could not be decompiled',
           'Method not decompiled:', 'Could not decompile', 'Decompilation failed']


def facts(path):
    text = path.read_text()
    return {'path': str(path.relative_to(ROOT)), 'sha256': hashlib.sha256(path.read_bytes()).hexdigest(),
            'characters': len(text), 'lines': len(text.splitlines()),
            'failure_markers': {marker: text.count(marker) for marker in MARKERS if marker in text}}


def inventory(client, cls):
    first = client.call('class_outline', {'className': cls, 'limit': 500})['result']
    methods = first['methods']
    offset = 500
    while offset < first['methodCount']:
        methods.extend(client.call('class_outline', {'className': cls, 'limit': 500, 'methodsOffset': offset})['result']['methods'])
        offset += 500
    return methods


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--out', type=Path, default=ROOT / '.cache/client-240/decompiler-quality')
    parser.add_argument('--jadx', type=Path, help='optional local jadx executable to compare; not a runtime dependency')
    args = parser.parse_args()
    check()
    check_build()
    java, _ = java_tools()
    out = output_path(args.out)
    out.mkdir(parents=True, exist_ok=True)
    if (out / 'manifest.json').exists():
        raise ValueError('comparison output already exists; choose a fresh --out to avoid stale sources')
    semantic_names = {}
    name_map = ROOT / 'references/rl-suite-240/semantic-1.12.39.tsv'
    for line in name_map.read_text().splitlines():
        if line.startswith('class\t'):
            _, original, _, _, semantic = line.split('\t')
            semantic_names[semantic] = original
    paths = {item['role']: ROOT / item['path'] for item in json.loads((ROOT / 'tools/client-240/inputs.json').read_text())['artifacts']}
    clean = CACHE / 'views/original-metadata.jar'
    cleanup = build_metadata_view(paths['original'], clean)
    records = []
    inventories = {}
    versions = {}

    def save(record):
        records.append(record)
        (out / 'manifest.json').write_text(json.dumps({'schema': 1, 'records': records,
            'versions': versions, 'inventories': inventories, 'metadata_cleanup': {k: v for k, v in cleanup.items() if k != 'classes'},
            'name_map_sha256': hashlib.sha256(name_map.read_bytes()).hexdigest()}, indent=2) + '\n')
        print(record['input'], record['engine'], record['sample'], record.get('error', record.get('characters')), flush=True)

    for source_view in ['original', 'semantic']:
        with RecafClient(out / (source_view + '-recaf.log'), source_view) as client:
            versions[source_view] = client.call('workspace_status')['result']
            for sample in SAMPLES:
                if source_view == 'semantic' and sample == 'aa':
                    continue  # No API-derived semantic alias for this generic noise-control class.
                cls = semantic_names.get(sample, sample) if source_view == 'original' else sample
                inventories[source_view + '/' + sample] = inventory(client, cls)
                for engine in ENGINES:
                    # Raw noise control is intentionally limited to one small class.
                    views = ['original', 'metadata-clean'] if sample == 'aa' else ['metadata-clean']
                    for view in views:
                        record = {'input': source_view, 'bytecode_view': view, 'engine': 'Recaf-' + engine,
                                  'sample': sample, 'class_name': cls}
                        began = time.monotonic()
                        try:
                            text, provenance, result = client.source(cls, engine, view)
                            dest = out / 'sources' / source_view / view / engine / (cls + '.java')
                            dest.parent.mkdir(parents=True, exist_ok=True)
                            dest.write_text(text)
                            record.update(facts(dest))
                            record.update(provenance)
                            record['view_sha256'] = result['viewSha256']
                        except (ValueError, RuntimeError, TimeoutError) as error:
                            record['error'] = str(error)
                        record['seconds'] = round(time.monotonic() - began, 3)
                        save(record)

    # Newer standalone Vineflower uses the same class bytes, with the complete corpus as type context.
    for sample in SAMPLES:
        cls = semantic_names.get(sample, sample)
        dest = out / 'sources/original/metadata-clean/Vineflower-1.12.0'
        dest.mkdir(parents=True, exist_ok=True)
        class_file = out / 'classfiles' / (cls + '.class')
        class_file.parent.mkdir(parents=True, exist_ok=True)
        with zipfile.ZipFile(clean) as archive:
            class_file.write_bytes(archive.read(cls + '.class'))
        record = {'input': 'original', 'bytecode_view': 'metadata-clean', 'engine': 'Vineflower-1.12.0', 'sample': sample, 'class_name': cls}
        command = [java, '-Xmx2g', '-jar', str(ROOT / 'tools/client-240/vendor/vineflower-1.12.0.jar'),
                   '-log=ERROR', '-thr=2', '-e=' + str(clean), str(class_file), str(dest)]
        began = time.monotonic()
        try:
            completed = subprocess.run(command, capture_output=True, text=True, timeout=120)
            (out / ('vf-' + cls + '.log')).write_text(completed.stdout + completed.stderr)
            record['returncode'] = completed.returncode
            path = dest / (cls + '.java')
            if path.is_file(): record.update(facts(path))
            else: record['error'] = 'No source file produced'
        except subprocess.TimeoutExpired:
            record['error'] = '120-second process timeout'
        record['seconds'] = round(time.monotonic() - began, 3)
        save(record)

    if args.jadx:
        version = subprocess.check_output([str(args.jadx.resolve()), '--version'], text=True, timeout=20).strip()
        versions['optional_jadx'] = {'version': version, 'launcher_sha256': hashlib.sha256(args.jadx.read_bytes()).hexdigest()}
        for sample in ['Actor', 'Widget', 'PacketBuffer']:
            cls = semantic_names[sample]
            dest = out / 'sources/original/metadata-clean/JADX' / (cls + '.java')
            dest.parent.mkdir(parents=True, exist_ok=True)
            record = {'input': 'original', 'bytecode_view': 'metadata-clean', 'engine': 'JADX-1.5.6-local', 'sample': sample, 'class_name': cls}
            try:
                completed = subprocess.run([str(args.jadx.resolve()), '-r', '--single-class', cls, '--single-class-output', str(dest), str(clean)],
                    env={**os.environ, 'JAVA_HOME': str(Path(java).parent.parent)}, capture_output=True, text=True, timeout=120)
                record['returncode'] = completed.returncode
                (out / ('jadx-' + cls + '.log')).write_text(completed.stdout + completed.stderr)
                if dest.is_file(): record.update(facts(dest))
                else: record['error'] = 'No source file produced'
            except subprocess.TimeoutExpired:
                record['error'] = '120-second process timeout'
            save(record)

    syntax = out / 'syntax.json'
    subprocess.run([java, '-Xmx2g', '-Xss16m', '-cp', os.pathsep.join([str(RECAF_JAR), str(CLASSES)]),
                    'dev.neosback.recaf.SourceMetrics', str(out / 'sources'), str(syntax)], check=True)
    metrics = json.loads(syntax.read_text())
    for record in records:
        if 'path' not in record:
            continue
        relative = str((ROOT / record['path']).relative_to(out / 'sources'))
        record['syntax'] = metrics[relative]
        expected = Counter(method['name'] for method in inventories[record['input'] + '/' + record['sample']]
                           if not method['access'] & (0x1000 | 0x40) and method['name'] != '<clinit>')
        actual = Counter(record['syntax']['methodNames'])
        record['method_name_review_flags'] = dict(expected - actual)
    manifest = json.loads((out / 'manifest.json').read_text())
    manifest['records'] = records
    manifest['comparison_complete'] = True
    manifest['status'] = 'REVIEW_REQUIRED' if any(record.get('error') or record.get('failure_markers')
        or record.get('syntax', {}).get('syntaxErrors') for record in records) else 'RENDERED_NOT_BEHAVIOR_VERIFIED'
    manifest['limitations'] = ['Syntax parsing is not type checking or behavioral verification.',
        'Method-name differences can result from sugar, bridge handling or identifier repair; review, do not automatically call them missing bodies.',
        'No source from different decompilers is spliced together.', 'Current naming shards failed validation; semantic view uses only the pinned input.']
    (out / 'manifest.json').write_text(json.dumps(manifest, indent=2) + '\n')
    print('Finished:', out / 'manifest.json')


if __name__ == '__main__':
    main()
