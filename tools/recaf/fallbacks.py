#!/usr/bin/env python3
"""Compare whole-class fallback renderings only for explicitly flagged reading-view files."""
import argparse
from concurrent.futures import ThreadPoolExecutor
import json
import os
from pathlib import Path
import subprocess
import sys
import zipfile

from recaf import ROOT, RECAF_JAR, CLASSES, java_tools, check_build
from metadata_view import sha
from mcp_client import RecafClient
sys.path.insert(0, str(ROOT / 'tools/client-240'))
from toolchain import output_path


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--reading', type=Path, default=ROOT / '.cache/client-240/recaf/readable-java')
    parser.add_argument('--jadx', type=Path)
    parser.add_argument('--legacy-vineflower-only', action='store_true', help='try a separate legacy exception-rendering profile on constructor failures')
    parser.add_argument('--procyon-only', action='store_true', help='compare Recaf Procyon on the verified reading JAR')
    parser.add_argument('--complex-condy-only', action='store_true', help='separate analysis-only non-lazy constant-dynamic presentation; never selected by assemble.py')
    args = parser.parse_args()
    check_build()
    java, _ = java_tools()
    reading = output_path(args.reading)
    primary = json.loads((reading / 'manifest.json').read_text())
    jar = reading / 'reading.jar'
    if sha(jar.read_bytes()) != primary['provenance']['java_safe_sha256']:
        raise ValueError('reading JAR differs from the primary export')
    if sum((args.procyon_only, args.legacy_vineflower_only, args.complex_condy_only)) > 1: parser.error('choose one optional profile')
    out = reading / ('analysis-condy' if args.complex_condy_only else ('procyon' if args.procyon_only else ('legacy-vineflower' if args.legacy_vineflower_only else 'fallbacks')))
    if out.exists(): raise ValueError('fallback evidence already exists; do not overwrite it')
    out.mkdir()
    flagged = sorted(set(primary['syntax_error_files']) | set(primary['failure_marker_files']))
    if args.legacy_vineflower_only:
        flagged = [name for name in flagged if 'Unable to resugar constructor' in primary['failure_marker_files'].get(name, {})]
    if args.complex_condy_only:
        flagged = [name for name in flagged if 'ConstantBootstraps.invoke<' in primary['failure_marker_files'].get(name, {})]
    engines = ['Vineflower-condy'] if args.complex_condy_only else (['Vineflower-legacy'] if args.legacy_vineflower_only else (['CFR', 'JADX'] if args.jadx else ['CFR']))
    tasks = [(name[:-5], engine) for name in flagged for engine in engines]

    def render(task):
        cls, engine = task
        dest = out / engine
        dest.mkdir(exist_ok=True)
        source = dest / (cls + '.java')
        record = {'class_name': cls, 'engine': engine}
        if engine in {'CFR', 'Vineflower-legacy', 'Vineflower-condy'}:
            bytecode = out / (cls + '.class')
            with zipfile.ZipFile(jar) as archive: bytecode.write_bytes(archive.read(cls + '.class'))
            if engine == 'CFR':
                command = [java, '-Xmx2g', '-jar', str(ROOT / 'tools/client-240/vendor/cfr-0.152.jar'),
                           str(bytecode), '--extraclasspath', str(jar), '--outputdir', str(dest), '--silent', 'true']
            else:
                options = ['--decompile-complex-constant-dynamic=true'] if engine == 'Vineflower-condy' else ['--old-try-dedup=true', '--simplify-stack=false', '--inline-simple-lambdas=false']
                record['options'] = options
                if engine == 'Vineflower-condy':
                    record['safety'] = 'analysis-only'
                    record['limit'] = 'Non-lazy presentation can change constant initialization/exception timing. Not original-equivalent Java.'
                command = [java, '-Xmx2g', '-jar', str(ROOT / 'tools/client-240/vendor/vineflower-1.12.0.jar'),
                           '-log=ERROR', '-thr=2', '-e=' + str(jar), *options, str(bytecode), str(dest)]
        else:
            command = [str(args.jadx.resolve()), '-r', '--single-class', cls, '--single-class-output', str(source), str(jar)]
        try:
            with (out / (engine + '-' + cls + '.log')).open('w') as log:
                result = subprocess.run(command, env={**os.environ, 'JAVA_HOME': str(Path(java).parent.parent)},
                                        stdout=log, stderr=subprocess.STDOUT, timeout=180)
            record['returncode'] = result.returncode
            if source.is_file():
                text = source.read_text()
                record.update(path=str(source.relative_to(reading)), source_sha256=sha(source.read_bytes()),
                    failure_markers={marker:text.count(marker) for marker in ["Couldn't be decompiled", 'This method could not be decompiled',
                        'Method not decompiled:', 'Unable to resugar constructor', 'ConstantBootstraps.invoke<'] if marker in text})
            else: record['error'] = 'No source file produced'
        except subprocess.TimeoutExpired:
            record['error'] = '180-second process timeout'
        print(cls, engine, record.get('error', record.get('returncode')), flush=True)
        return record

    if args.procyon_only:
        current = ROOT / '.cache/client-240/recaf/views/java-safe/client-java-safe.jar'
        if sha(current.read_bytes()) != primary['provenance']['java_safe_sha256']:
            raise ValueError('current Recaf reading view differs from this export; do not mix snapshots')
        records = []
        with RecafClient(out / 'recaf.log', 'reading') as client:
            for name in flagged:
                cls = name[:-5]
                record = {'class_name': cls, 'engine': 'Recaf-Procyon'}
                try:
                    text, provenance, result = client.source(cls, 'Procyon')
                    source = out / 'Procyon' / name
                    source.parent.mkdir(exist_ok=True)
                    source.write_text(text)
                    record.update(returncode=0, path=str(source.relative_to(reading)), source_sha256=sha(source.read_bytes()),
                        failure_markers={marker:text.count(marker) for marker in ["Couldn't be decompiled", 'This method could not be decompiled', 'Method not decompiled:'] if marker in text})
                except (RuntimeError, TimeoutError, ValueError) as error:
                    record['error'] = str(error)
                records.append(record)
                print(cls, record.get('error', record.get('failure_markers')), flush=True)
    else:
        with ThreadPoolExecutor(max_workers=2) as pool:
            records = list(pool.map(render, tasks))
    subprocess.run([java, '-Xmx3g', '-Xss16m', '-cp', os.pathsep.join([str(RECAF_JAR), str(CLASSES)]),
                    'dev.neosback.recaf.SourceMetrics', str(out), str(out / 'syntax.json')], check=True)
    syntax = json.loads((out / 'syntax.json').read_text())
    for record in records:
        if 'path' in record:
            record['syntax'] = syntax[str(Path(record['path']).relative_to(out.name))]
    (out / 'manifest.json').write_text(json.dumps({'records': records, 'reading_jar_sha256': primary['provenance']['java_safe_sha256'],
        'scope': 'analysis-only non-lazy constant-dynamic presentation; excluded from assembly' if args.complex_condy_only else 'whole-class alternatives; no source splicing or automatic semantic promotion'}, indent=2) + '\n')


if __name__ == '__main__':
    main()
