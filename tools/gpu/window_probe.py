#!/usr/bin/env python3
"""Bounded native title-window FPS smoke check using isolated disposable preferences.

Build rustclient-native first. Requires a desktop session and a read-only cache path.
Never connects to a game server or modifies the user's application preferences.
"""
import argparse
import json
import os
from pathlib import Path
import re
import statistics
import subprocess
import tempfile
import time

ROOT = Path(__file__).resolve().parents[2]


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('cache', type=Path)
    parser.add_argument('--seconds', type=float, default=8)
    args = parser.parse_args()
    if not 5 <= args.seconds <= 30:
        parser.error('--seconds must be 5..30')
    binary = ROOT / 'target/debug/rustclient-native'
    if not binary.is_file():
        parser.error('build rustclient-native first')
    output = ROOT / 'target/gpu-window-probe'
    output.mkdir(parents=True, exist_ok=True)
    run = Path(tempfile.mkdtemp(prefix='run-', dir=output))
    schema = json.loads((ROOT / 'crates/plugin-host/src/builtin-schemas.json').read_text())
    version = next(row['version'] for row in schema if row['plugin'] == 'dev.rustclient.gpu')
    results = []
    for name, cap, unlock in [('target30', 30, True), ('locked50', 50, False), ('target60', 60, True)]:
        preferences = run / name / 'preferences'
        path = preferences / 'plugin-config/dev.rustclient.gpu/preferences-v1.json'
        path.parent.mkdir(parents=True)
        path.write_text(json.dumps({'version': version, 'values': {
            'unlockFps': {'type': 'boolean', 'value': unlock},
            'fpsTarget': {'type': 'integer', 'value': cap},
            'vsyncMode': {'type': 'choice', 'value': 'OFF'},
        }}))
        log = run / name / 'window.log'
        with log.open('w') as stream:
            process = subprocess.Popen([
                str(binary), '--cache-dir', str(args.cache.resolve()), '--cache-profile', 'openrune-240',
                '--preferences-dir', str(preferences), '--plugins-dir', str(run / name / 'plugins'),
            ], stdout=stream, stderr=subprocess.STDOUT,
                env={**os.environ, 'RUSTCLIENT_FRAME_TIMINGS': '1'})
            try:
                # Bounded, pollable subprocess lifetime; no UI interaction or account entry.
                end = time.monotonic() + args.seconds
                while process.poll() is None and time.monotonic() < end:
                    time.sleep(0.1)
                unexpected_exit = process.poll()
            finally:
                if process.poll() is None:
                    process.terminate()
                    try:
                        process.wait(timeout=5)
                    except subprocess.TimeoutExpired:
                        process.kill()
                        process.wait()
        text = log.read_text()
        samples = [float(value) for value in re.findall(r'frame timings: ([0-9.]+) FPS', text)]
        steady = samples[1:]  # Exclude the partial initial second that includes startup.
        passed = (unexpected_exit is None and len(steady) >= 2
                  and max(steady) <= cap + 1 and statistics.median(steady) >= cap * 0.6
                  and 'defaults active' not in text and 'rendering failed' not in text)
        results.append(dict(mode=name, requested=cap, unlock=unlock, samples=samples,
                            passed=passed, unexpectedExit=unexpected_exit, log=str(log.relative_to(ROOT))))
        print(f'{name}: {samples} FPS; passed={passed}', flush=True)
    report = dict(scope='Native offline title FPS smoke only; no live gameplay/vsync parity claim',
                  passed=all(row['passed'] for row in results), results=results)
    (output / 'report.json').write_text(json.dumps(report, indent=2) + '\n')
    if not report['passed']:
        raise SystemExit('Native window smoke failed; inspect target/gpu-window-probe/report.json')


if __name__ == '__main__':
    main()
