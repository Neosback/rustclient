#!/usr/bin/env python3
"""Native animated-title measurement with isolated preferences and complete traces.

Build the native binary first; keep builds/profilers inactive during scored runs.
No account entry or game-server connection. Use --seconds 300 --warmup 30 --repeats 3
for acceptance. The default short run is a smoke check, not performance acceptance.
"""
import argparse
import hashlib
import json
import math
import os
from pathlib import Path
import subprocess
import tempfile
import time
from frame_trace import summarize

ROOT = Path(__file__).resolve().parents[2]


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('cache', type=Path)
    parser.add_argument('--binary', type=Path, default=ROOT / 'target/debug/rustclient-native')
    parser.add_argument('--seconds', type=float, default=12)
    parser.add_argument('--warmup', type=float, default=3)
    parser.add_argument('--repeats', type=int, default=1)
    parser.add_argument('--cap', type=int, default=60)
    parser.add_argument('--output', type=Path, default=ROOT / 'target/gpu-window-probe')
    args = parser.parse_args()
    if args.seconds < 5 or args.warmup < 0 or not 1 <= args.repeats <= 10 or args.cap < 1:
        parser.error('seconds >= 5, warmup >= 0, repeats 1..10 and cap >= 1 required')
    if not args.binary.is_file() or not args.cache.is_dir():
        parser.error('build the native binary and supply a cache directory')
    args.output.mkdir(parents=True, exist_ok=True)
    run = Path(tempfile.mkdtemp(prefix='run-', dir=args.output))
    schema = json.loads((ROOT / 'crates/plugin-host/src/builtin-schemas.json').read_text())
    version = next(row['version'] for row in schema if row['plugin'] == 'dev.rustclient.gpu')
    results = []
    digest = hashlib.sha256(args.binary.read_bytes()).hexdigest()
    for repeat in range(args.repeats):
        folder = run / str(repeat + 1)
        preferences = folder / 'preferences'
        path = preferences / 'plugin-config/dev.rustclient.gpu/preferences-v1.json'
        path.parent.mkdir(parents=True)
        values = {'unlockFps': {'type': 'boolean', 'value': True},
                  'fpsTarget': {'type': 'integer', 'value': args.cap},
                  'vsyncMode': {'type': 'choice', 'value': 'OFF'},
                  'drawDistance': {'type': 'integer', 'value': 184}}
        path.write_text(json.dumps({'version': version, 'values': values}))
        log, trace, memory = folder / 'window.log', folder / 'frames.jsonl', folder / 'rss.jsonl'
        duration = args.warmup + args.seconds + 1
        env = {**os.environ, 'RUSTCLIENT_FRAME_TIMINGS': '1',
               'RUSTCLIENT_FRAME_TRACE': str(trace), 'RUSTCLIENT_RUN_SECONDS': str(math.ceil(duration))}
        with log.open('w') as stream, memory.open('w') as rss:
            process = subprocess.Popen([
                str(args.binary.resolve()), '--cache-dir', str(args.cache.resolve()),
                '--cache-profile', 'openrune-240', '--preferences-dir', str(preferences),
                '--plugins-dir', str(folder / 'plugins'),
            ], stdout=stream, stderr=subprocess.STDOUT, env=env)
            start = time.monotonic()
            deadline = start + duration + 15
            next_sample = start
            forced = False
            try:
                while process.poll() is None and time.monotonic() < deadline:
                    now = time.monotonic()
                    if now >= next_sample:
                        sample = subprocess.run(['ps', '-p', str(process.pid), '-o', 'rss='],
                                                capture_output=True, text=True)
                        if sample.stdout.strip():
                            rss.write(json.dumps(dict(at_seconds=now-start, rss_kib=int(sample.stdout)))+'\n')
                            rss.flush()
                        next_sample = now + 1
                    time.sleep(.1)
            finally:
                if process.poll() is None:
                    forced = True
                    process.terminate()
                    try:
                        process.wait(timeout=5)
                    except subprocess.TimeoutExpired:
                        process.kill()
                        process.wait()
        report = summarize(trace, args.warmup, args.seconds, args.cap) if trace.is_file() else {'passed': False}
        report.update(repeat=repeat + 1, exit_code=process.returncode, forced_termination=forced,
                      log=str(log), rss=str(memory))
        report['passed'] = report['passed'] and process.returncode == 0 and not forced
        results.append(report)
        (folder / 'report.json').write_text(json.dumps(report, indent=2) + '\n')
        print(f"Run {repeat + 1}: {report.get('presentation_hz')} presents/s; gates={report.get('gates')}", flush=True)
    report = dict(scope='Animated title only; no gameplay, map, scanout or visual acceptance',
                  acceptance_duration=args.seconds >= 300 and args.warmup >= 30 and args.repeats >= 3,
                  binary_sha256=digest, binary=str(args.binary.resolve()), cache=str(args.cache.resolve()),
                  passed=all(row['passed'] for row in results), results=results)
    (run / 'report.json').write_text(json.dumps(report, indent=2) + '\n')
    print(run / 'report.json', flush=True)
    raise SystemExit(0 if report['passed'] else 1)


if __name__ == '__main__':
    main()
