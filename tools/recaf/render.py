#!/usr/bin/env python3
"""Render one exact-code reading class with bundled Vineflower 1.12 and full type context."""
import argparse
import json
from pathlib import Path
import re
import subprocess
import sys
import zipfile

from metadata_view import ROOT, build_metadata_view, sha
sys.path.insert(0, str(ROOT / 'tools/client-240'))
from toolchain import check, output_path, runtime


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('class_name', help='exact original JVM internal class name')
    parser.add_argument('--out', type=Path)
    parser.add_argument('--timeout', type=int, default=180)
    args = parser.parse_args()
    if not re.fullmatch(r'[A-Za-z_$][A-Za-z0-9_$/]*', args.class_name):
        parser.error('invalid JVM class name')
    if args.timeout < 1 or args.timeout > 600:
        parser.error('--timeout must be between 1 and 600 seconds')
    check()
    java, _ = runtime()
    original = ROOT / 'references/gamepacks/osrs-240/injected-client-1.12.39.jar'
    clean = ROOT / '.cache/client-240/recaf/views/original-metadata.jar'
    metadata = build_metadata_view(original, clean)
    request = args.class_name.encode()
    key = sha(request)[:16]
    out = output_path(args.out or ROOT / '.cache/client-240/recaf/render' / key)
    out.mkdir(parents=True, exist_ok=True)
    cls = out / (args.class_name + '.class')
    cls.parent.mkdir(parents=True, exist_ok=True)
    with zipfile.ZipFile(clean) as archive:
        bytecode = archive.read(args.class_name + '.class')
        cls.write_bytes(bytecode)
    tool = ROOT / 'tools/client-240/vendor/vineflower-1.12.0.jar'
    source = out / (args.class_name + '.java')
    source.unlink(missing_ok=True)
    (out / 'render.json').unlink(missing_ok=True)
    command = [java, '-Xmx2g', '-jar', str(tool), '-log=ERROR', '-thr=2', '-e=' + str(clean)]
    command.extend([str(cls), str(out)])
    with (out / 'renderer.log').open('w') as log:
        result = subprocess.run(command, stdout=log, stderr=subprocess.STDOUT, timeout=args.timeout)
    if result.returncode or not source.is_file():
        raise RuntimeError(f'render failed; see {out / "renderer.log"}')
    text = source.read_text()
    markers = [marker for marker in ["Couldn't be decompiled", 'Could not be decompiled'] if marker in text]
    record = {'input_sha256': metadata['input_sha256'], 'metadata_view_sha256': metadata['output_sha256'],
        'class_name': args.class_name, 'scope': 'one class',
        'renderer_sha256': sha(tool.read_bytes()), 'source_sha256': sha(source.read_bytes()),
        'failure_markers': markers, 'status': 'REVIEW_REQUIRED' if markers else 'RENDERED_NOT_BEHAVIOR_VERIFIED'}
    (out / 'render.json').write_text(json.dumps(record, indent=2) + '\n')
    print(source)
    if markers:
        raise SystemExit('Renderer reported a failed body; inspect renderer.log and exact bytecode')


if __name__ == '__main__':
    main()
