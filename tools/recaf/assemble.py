#!/usr/bin/env python3
"""Assemble whole-class reading choices while keeping unresolved source defects explicit."""
import argparse
from collections import Counter
import json
import os
from pathlib import Path
import shutil
import subprocess
import sys
import zipfile

from recaf import ROOT, RECAF_JAR, CLASSES, java_tools, check_build
from metadata_view import class_view, sha
sys.path.insert(0, str(ROOT / 'tools/client-240'))
from toolchain import output_path


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--reading', type=Path, default=ROOT / '.cache/client-240/recaf/readable-java')
    parser.add_argument('--out', type=Path, default=ROOT / '.cache/client-240/recaf/best-reading')
    args = parser.parse_args()
    check_build()
    java, _ = java_tools()
    reading, out = output_path(args.reading), output_path(args.out)
    if out.exists(): raise ValueError('choose a fresh output directory')
    primary = json.loads((reading / 'manifest.json').read_text())
    jar = reading / 'reading.jar'
    if sha(jar.read_bytes()) != primary['provenance']['java_safe_sha256']:
        raise ValueError('reading JAR changed')
    for name, expected in primary['source_sha256'].items():
        if sha((reading / 'sources' / name).read_bytes()) != expected:
            raise ValueError('primary source changed: ' + name)
    out.mkdir(parents=True)
    shutil.copyfile(jar, out / 'reading.jar')
    shutil.copytree(reading / 'sources', out / 'sources')
    for name in ['class-names-and-keywords.tiny', 'identities.tsv']:
        shutil.copyfile(reading / name, out / name)
    selected, rejected = {}, []
    syntax_errors = primary['syntax_error_files'].copy()
    failure_markers = primary['failure_marker_files'].copy()
    records = []
    for group in ['fallbacks', 'procyon', 'legacy-vineflower']:
        path = reading / group / 'manifest.json'
        if path.is_file(): records.extend(json.loads(path.read_text())['records'])
    with zipfile.ZipFile(jar) as archive:
        for record in records:
            if record.get('returncode') != 0 or record.get('error') or record.get('failure_markers') or record.get('syntax', {}).get('syntaxErrors'):
                continue
            name = record['class_name'] + '.java'
            if name in selected: continue
            source = reading / record['path']
            if sha(source.read_bytes()) != record['source_sha256']:
                raise ValueError('fallback source changed')
            _, structure, _ = class_view(archive.read(record['class_name'] + '.class'), clean=False)
            check_dir = out / 'checks' / record['class_name']
            check_dir.mkdir(parents=True)
            shutil.copyfile(source, check_dir / name)
            metrics_file = check_dir / 'syntax.json'
            subprocess.run([java, '-Xmx1g', '-cp', os.pathsep.join([str(RECAF_JAR), str(CLASSES)]),
                'dev.neosback.recaf.SourceMetrics', str(check_dir), str(metrics_file)], check=True)
            metrics = json.loads(metrics_file.read_text())[name]
            expected_methods, expected_fields = Counter(), Counter()
            for kind, access, symbol, descriptor in structure['members']:
                symbol = bytes.fromhex(symbol).decode()
                if access & 0x1000: continue
                if kind == 'method' and not access & 0x40 and symbol != '<clinit>': expected_methods[symbol] += 1
                if kind == 'field': expected_fields[symbol] += 1
            missing_methods = dict(expected_methods - Counter(metrics['methodNames']))
            missing_fields = dict(expected_fields - Counter(metrics['fieldNames']))
            if metrics['syntaxErrors'] or missing_methods or missing_fields:
                rejected.append({'class_name': record['class_name'], 'engine': record['engine'],
                    'missing_methods': missing_methods, 'missing_fields': missing_fields})
                continue
            shutil.copyfile(source, out / 'sources' / name)
            selected[name] = {'engine': record['engine'], 'source_sha256': record['source_sha256'],
                'checks': 'syntax parses; all non-synthetic field/method names represented; behavior remains unverified'}
            syntax_errors.pop(name, None)
            failure_markers.pop(name, None)
    source_hashes = {str(path.relative_to(out / 'sources')): sha(path.read_bytes()) for path in sorted((out / 'sources').rglob('*.java'))}
    result = {'status': 'REVIEW_REQUIRED' if syntax_errors or failure_markers else 'PARSED_NOT_TYPECHECKED',
        'classes': len(source_hashes), 'syntax_pass_files': len(source_hashes) - len(syntax_errors),
        'remaining_syntax_errors': syntax_errors, 'remaining_failure_markers': failure_markers,
        'whole_class_alternatives': selected, 'rejected_candidates': rejected,
        'source_sha256': source_hashes, 'original_sha256': primary['provenance']['input_sha256'],
        'reading_jar_sha256': primary['provenance']['java_safe_sha256'],
        'limitations': 'Source syntax/name checks only. No full dependency typecheck or per-method behavioral equivalence claim. No source splicing.'}
    (out / 'manifest.json').write_text(json.dumps(result, indent=2) + '\n')
    subprocess.run([sys.executable, str(ROOT / 'tools/recaf/dump_unresolved.py'), '--reading', str(out)], check=True)
    print(json.dumps({key:value for key,value in result.items() if key != 'source_sha256'}, indent=2))
    print('Sources:', out / 'sources')
    return int(result['status'] == 'REVIEW_REQUIRED')


if __name__ == '__main__':
    raise SystemExit(main())
