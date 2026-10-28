#!/usr/bin/env python3
"""Execute the pinned injected-240 overlay arithmetic in an isolated Java harness.

This diagnostic does not execute the gamepack, touch references, or establish visual parity.
Its first three vectors correspond to the bounded Rust overlay contract; the fourth checks the
source's lowest-opacity byte result independently.
"""
import argparse
import hashlib
import json
import os
from pathlib import Path
import shutil
import subprocess

ROOT = Path(__file__).resolve().parents[2]
SOURCE = ROOT / 'references/rl-suite-240/sources-semantic/classGP.java'


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--java-home', type=Path,
                        default=Path(os.environ['JAVA_HOME']) if os.environ.get('JAVA_HOME') else None)
    parser.add_argument('--output', type=Path, default=ROOT / 'target/gpu-overlay-oracle')
    args = parser.parse_args()
    def java_tool(name):
        path = args.java_home / 'bin' / name if args.java_home else shutil.which(name)
        if not path or not Path(path).is_file():
            raise SystemExit(f'{name} unavailable; select a JDK with --java-home or JAVA_HOME')
        return str(path)
    source = SOURCE.read_text()
    digest = hashlib.sha256(SOURCE.read_bytes()).hexdigest()
    manifest = json.loads((ROOT / 'docs/reference/source-manifest.json').read_text())
    baseline = next(s for s in manifest['sources'] if s['id'] == 'rl-suite-240')
    expected_hash = next(f['sha256'] for f in baseline['files']
                         if f['path'] == 'sources-semantic/classGP.java')
    if digest != expected_hash:
        raise SystemExit('Pinned overlay source hash mismatch')
    method = source.index('public void method5624(')
    start = source.index('int var18 = var15.getTextColor();', method)
    end = source.index('field2648 = var24 << 24 | var22 + var23;', start)
    block = source[start:end + len('field2648 = var24 << 24 | var22 + var23;')]
    # Fixture scaffolding only; the arithmetic block itself is taken verbatim from pinned evidence.
    program = '''public class ViewportOverlayOracle {
      static int field2648;
      static class ColorWidget {
        final int color, opacity;
        ColorWidget(int c, int a) { color=c; opacity=a; }
        int getTextColor() { return color; }
        int getOpacity() { return opacity; }
      }
      static int blend(int previous, int color, int transparency) {
        field2648=previous;
        ColorWidget var15=new ColorWidget(color, transparency);
        BLOCK
        return field2648;
      }
      public static void main(String[] args) {
        int red=blend(0,0xff0000,128);
        int blue=blend(red,0x0000ff,128);
        int[] results={red,blue,blend(0,0xffffff,1),blend(0,0xffffff,255)};
        for(int value:results) {
          System.out.printf("%d %d %d %d%n", (value>>>16)&255,(value>>>8)&255,value&255,value>>>24);
        }
      }
    }'''.replace('BLOCK', block)
    args.output.mkdir(parents=True, exist_ok=True)
    harness = args.output / 'ViewportOverlayOracle.java'
    harness.write_text(program)
    subprocess.run([java_tool('javac'), '-d', str(args.output), str(harness)],
                   check=True, timeout=30, capture_output=True)
    output = subprocess.check_output(
        [java_tool('java'), '-cp', str(args.output), 'ViewportOverlayOracle'],
        text=True, timeout=30)
    actual = [[int(value) for value in row.split()] for row in output.splitlines()]
    expected = [[127, 0, 0, 128], [63, 0, 127, 191], [254, 254, 254, 255], [0, 0, 0, 1]]
    report = {'source': str(SOURCE.relative_to(ROOT)), 'sourceSha256': digest,
              'blockSha256': hashlib.sha256(block.encode()).hexdigest(),
              'actualRgba': actual, 'expectedRgba': expected, 'matched': actual == expected,
              'limits': 'Literal arithmetic only; widget selection and runtime/visual parity not established.'}
    (args.output / 'result.json').write_text(json.dumps(report, indent=2) + '\n')
    print(json.dumps(report, indent=2))
    if not report['matched']:
        raise SystemExit(1)


if __name__ == '__main__':
    main()
