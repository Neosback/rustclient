#!/usr/bin/env python3
"""Attach complete original-Code ASM views for classes with unresolved Java reconstruction."""
import argparse
import json
import os
from pathlib import Path
import subprocess

from recaf import ROOT, RECAF_JAR, CLASSES, java_tools, check_build
from metadata_view import build_metadata_view, sha
from toolchain import output_path


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--reading', type=Path, default=ROOT / '.cache/client-240/recaf/best-reading')
    args = parser.parse_args()
    check_build()
    java, _ = java_tools()
    reading = output_path(args.reading)
    manifest = json.loads((reading / 'manifest.json').read_text())
    inverse = {}
    for line in (reading / 'class-names-and-keywords.tiny').read_text().splitlines():
        if line.startswith('c\t'):
            _, original, readable = line.split('\t')
            inverse[readable] = original
    unresolved = set(manifest['remaining_syntax_errors']) | set(manifest['remaining_failure_markers'])
    classes = {name[:-5]: inverse[name[:-5]] for name in sorted(unresolved)}
    original = ROOT / 'references/gamepacks/osrs-240/injected-client-1.12.39.jar'
    metadata = ROOT / '.cache/client-240/recaf/views/original-metadata.jar'
    proof = build_metadata_view(original, metadata)
    output = reading / 'original-bytecode'
    subprocess.run([java, '-cp', os.pathsep.join([str(RECAF_JAR), str(CLASSES)]),
        'dev.neosback.recaf.BytecodeDump', str(metadata), str(output), *classes.values()], check=True)
    records = {}
    for readable, original_name in classes.items():
        source = output / (original_name + '.txt')
        records[readable] = {'original_class': original_name, 'path': str(source.relative_to(reading)),
            'sha256': sha(source.read_bytes()), 'method_code_attributes': proof['classes'][original_name + '.class']['code_attributes']}
    (reading / 'original-bytecode.json').write_text(json.dumps({'original_sha256': proof['input_sha256'],
        'metadata_view_sha256': proof['output_sha256'], 'classes': records,
        'scope': 'complete original instruction/exception/bootstrap views; annotation noise excluded, original Code attributes unchanged'}, indent=2) + '\n')
    print('Complete original bytecode attached for', len(records), 'unresolved Java classes')


if __name__ == '__main__':
    main()
