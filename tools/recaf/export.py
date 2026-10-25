#!/usr/bin/env python3
"""Export the reversible class-name/keyword reading view with explicit corpus quality checks."""
import argparse
import json
import os
import shutil
from pathlib import Path
import subprocess
import sys
import time

from recaf import ROOT, HERE, RECAF_JAR, CLASSES, check_build, java_tools
from metadata_view import sha
sys.path.insert(0, str(ROOT / 'tools/client-240'))
from toolchain import output_path

MARKERS = ["Couldn't be decompiled", 'This method could not be decompiled',
           'Unable to resugar constructor', 'ConstantBootstraps.invoke<']


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--out', type=Path, default=ROOT / '.cache/client-240/recaf/readable-java')
    args = parser.parse_args()
    check_build()
    java, _ = java_tools()
    out = output_path(args.out)
    if out.exists() and any(out.iterdir()):
        raise ValueError('choose a fresh output directory; existing evidence is not overwritten')
    out.mkdir(parents=True, exist_ok=True)
    subprocess.run([sys.executable, str(HERE / 'java_safe.py'), '--identity'], cwd=ROOT, check=True)
    view = ROOT / '.cache/client-240/recaf/views/java-safe'
    provenance = json.loads((view / 'manifest.json').read_text())
    jar = view / 'client-java-safe.jar'
    if sha(jar.read_bytes()) != provenance['java_safe_sha256']:
        raise ValueError('reading JAR changed after its roundtrip verification')
    snapshot = out / 'reading.jar'
    shutil.copyfile(jar, snapshot)
    if sha(snapshot.read_bytes()) != provenance['java_safe_sha256']:
        raise ValueError('reading JAR changed while snapshotting')
    jar = snapshot
    sources = out / 'sources'
    sources.mkdir()
    began = time.monotonic()
    with (out / 'vineflower.log').open('w') as log:
        subprocess.run([java, '-Xmx3g', '-jar', str(ROOT / 'tools/client-240/vendor/vineflower-1.12.0.jar'),
                        '-log=WARN', '-thr=4', '-mpm=60', str(jar), str(sources)],
                        cwd=ROOT, stdout=log, stderr=subprocess.STDOUT, check=True, timeout=900)
    syntax = out / 'syntax.json'
    subprocess.run([java, '-Xmx3g', '-Xss16m', '-cp', os.pathsep.join([str(RECAF_JAR), str(CLASSES)]),
                    'dev.neosback.recaf.SourceMetrics', str(sources), str(syntax)], check=True)
    metrics = json.loads(syntax.read_text())
    syntax_errors = {name: len(info['syntaxErrors']) for name, info in metrics.items() if info['syntaxErrors']}
    markers = {}
    source_files = {}
    for source in sorted(sources.rglob('*.java')):
        text = source.read_text()
        found = {marker: text.count(marker) for marker in MARKERS if marker in text}
        name = str(source.relative_to(sources))
        source_files[name] = sha(source.read_bytes())
        if found: markers[name] = found
    (out / 'class-names-and-keywords.tiny').write_bytes((view / 'java-keywords.tiny').read_bytes())
    (out / 'identities.tsv').write_bytes((ROOT / '.cache/client-240/deob-v2/index-semantic.tsv').read_bytes())
    record = {'schema': 1, 'status': 'PARSED_NOT_TYPECHECKED' if len(source_files) == 735 and not syntax_errors and not markers else 'REVIEW_REQUIRED',
        'source_classes': len(source_files), 'syntax_error_files': syntax_errors, 'failure_marker_files': markers,
        'source_sha256': source_files, 'provenance': provenance,
        'renderer_sha256': sha((ROOT / 'tools/client-240/vendor/vineflower-1.12.0.jar').read_bytes()),
        'seconds': round(time.monotonic() - began, 3),
        'limits': ['Java syntax parsing only; dependencies/types not compiled and behavior not differentially tested.',
            'Class names use pinned identities; member names remain original apart from documented keyword aliases.',
            'Synthetic/bridge/lambda bodies can be represented by Java sugar; original-bytecode inventory remains complete.',
            'All current semantic naming shards are not certified by this export.']}
    (out / 'manifest.json').write_text(json.dumps(record, indent=2) + '\n')
    print(json.dumps({key: value for key, value in record.items() if key not in {'source_sha256', 'provenance'}}, indent=2))
    print('Sources:', sources)
    if record['status'] == 'REVIEW_REQUIRED':
        raise SystemExit(1)


if __name__ == '__main__':
    main()
