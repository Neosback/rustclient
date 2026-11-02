#!/usr/bin/env python3
"""Native animated-title measurement with isolated preferences and complete traces.

Build the native binary first; keep builds/profilers inactive during scored runs.
No account entry or authentication. Use --seconds 300 --warmup 30 --repeats 3
for acceptance. The default short run is a smoke check, not performance acceptance.
"""
import argparse
import hashlib
import json
import math
import os
import plistlib
import re
import shutil
import signal
import sys
from pathlib import Path
import subprocess
import tempfile
import time
from frame_trace import summarize

ROOT = Path(__file__).resolve().parents[2]


def sha256_file(path):
    digest = hashlib.sha256()
    with path.open('rb') as stream:
        while chunk := stream.read(1024 * 1024):
            digest.update(chunk)
    return digest.hexdigest()


def measurement_binary(binary, run):
    if sys.platform != 'darwin':
        return binary.resolve(), None
    app = run / 'RustClient Measurement.app'
    executable = app / 'Contents/MacOS/RustClient-measurement'
    executable.parent.mkdir(parents=True)
    shutil.copy2(binary, executable)
    with (app / 'Contents/Info.plist').open('wb') as stream:
        plistlib.dump(dict(CFBundleExecutable=executable.name,
                          CFBundleIdentifier='dev.rustclient.measurement',
                          CFBundleName='RustClient Measurement', CFBundleVersion='1',
                          CFBundlePackageType='APPL', NSHighResolutionCapable=True), stream)
    return executable.resolve(), app.resolve()


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('cache', type=Path)
    parser.add_argument('--binary', type=Path, default=ROOT / 'target/debug/rustclient-native')
    parser.add_argument('--seconds', type=float, default=12)
    parser.add_argument('--warmup', type=float, default=3)
    parser.add_argument('--repeats', type=int, default=1)
    parser.add_argument('--cap', type=int, default=60)
    parser.add_argument('--direct', action='store_true',
                        help='Launch the executable directly (recorded separately from macOS bundle runs)')
    parser.add_argument('--output', type=Path, default=ROOT / 'target/gpu-window-probe')
    args = parser.parse_args()
    if args.seconds < 5 or args.warmup < 0 or not 1 <= args.repeats <= 10 or args.cap < 0:
        parser.error('seconds >= 5, warmup >= 0, repeats 1..10 and cap >= 0 (uncapped) required')
    if not args.binary.is_file() or not args.cache.is_dir():
        parser.error('build the native binary and supply a cache directory')
    # LaunchServices does not inherit the caller's working directory. Every path
    # passed to the app, including stdout and the trace environment, must be absolute.
    args.output = args.output.resolve()
    args.output.mkdir(parents=True, exist_ok=True)
    run = Path(tempfile.mkdtemp(prefix='run-', dir=args.output))
    schema = json.loads((ROOT / 'crates/plugin-host/src/builtin-schemas.json').read_text())
    version = next(row['version'] for row in schema if row['plugin'] == 'dev.rustclient.gpu')
    cache_files = {}
    for file in sorted(args.cache.glob('main_file_cache.*')):
        stat = file.stat()
        cache_files[file.name] = dict(bytes=stat.st_size, mtime_ns=stat.st_mtime_ns)
        cache_files[file.name]['sha256'] = sha256_file(file)
    environment = dict(cache_files=cache_files, cpu_count=os.cpu_count(),
                       os=subprocess.check_output(['sw_vers'], text=True).strip(),
                       power=subprocess.check_output(['pmset', '-g', 'batt'], text=True).strip(),
                       preferences=184, concurrent_workload='See per-run process inventory; server presence is not assumed')
    (run / 'environment.json').write_text(json.dumps(environment, indent=2) + '\n')
    results = []
    executable, app = (args.binary.resolve(), None) if args.direct else measurement_binary(args.binary, run)
    digest = sha256_file(executable)
    for repeat in range(args.repeats):
        inventory = subprocess.check_output(['ps', '-Ao', 'pid,ppid,pcpu,rss,comm'], text=True)
        (run / f'processes-before-{repeat + 1}.txt').write_text(inventory)
        names = [Path(line.strip()).name for line in subprocess.check_output(['ps', '-Ao', 'comm='], text=True).splitlines()]
        blockers = sorted({name for name in names if name in {
            'cargo', 'cargo-clippy', 'rustc', 'clippy-driver', 'samply', 'sample',
            'rustclient-native', 'RustClient-measurement', 'interface_probe', 'gpu_probe', 'scene_probe', 'script_probe'}})
        if blockers:
            raise SystemExit(f'Measurement requires builds/profilers/other clients inactive: {blockers}')
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
            client_args = ['--cache-dir', str(args.cache.resolve()),
                           '--cache-profile', 'openrune-240', '--preferences-dir', str(preferences),
                           '--plugins-dir', str(folder / 'plugins')]
            if app:
                command = ['open', '-W', '-n', '-a', str(app), '--stdout', str(log), '--stderr', str(log)]
                for name in ['RUSTCLIENT_FRAME_TIMINGS', 'RUSTCLIENT_FRAME_TRACE', 'RUSTCLIENT_RUN_SECONDS']:
                    command += ['--env', f'{name}={env[name]}']
                command += ['--args', *client_args]
            else:
                command = [str(executable), *client_args]
            process = subprocess.Popen(command, stdout=stream, stderr=subprocess.STDOUT, env=env)
            native_pid = None if app else process.pid
            start = time.monotonic()
            deadline = start + duration + 15
            next_sample = start
            forced = False
            try:
                while process.poll() is None and time.monotonic() < deadline:
                    now = time.monotonic()
                    if native_pid is None:
                        found = subprocess.run(['pgrep', '-f', '^' + re.escape(str(executable))],
                                               capture_output=True, text=True)
                        if found.stdout.strip():
                            native_pid = int(found.stdout.splitlines()[0])
                    if native_pid and now >= next_sample:
                        sample = subprocess.run(['ps', '-p', str(native_pid), '-o', 'rss='],
                                                capture_output=True, text=True)
                        if sample.stdout.strip():
                            rss.write(json.dumps(dict(at_seconds=now-start, rss_kib=int(sample.stdout)))+'\n')
                            rss.flush()
                        next_sample = now + 1
                    time.sleep(.1)
            finally:
                if process.poll() is None:
                    forced = True
                    if app and native_pid:
                        try:
                            os.kill(native_pid, signal.SIGTERM)
                        except ProcessLookupError:
                            pass
                    else:
                        process.terminate()
                    try:
                        process.wait(timeout=5)
                    except subprocess.TimeoutExpired:
                        process.kill()
                        process.wait()
        report = summarize(trace, args.warmup, args.seconds, args.cap) if trace.is_file() else {'passed': False}
        report.update(repeat=repeat + 1, exit_code=process.returncode, forced_termination=forced,
                      log=str(log), rss=str(memory), native_pid=native_pid, invocation=command)
        workload_changed = 'game session started' in log.read_text()
        preferences_changed = json.loads(path.read_text()) != {'version': version, 'values': values}
        report.update(game_session_started=workload_changed, preferences_changed=preferences_changed)
        report['passed'] = report['passed'] and process.returncode == 0 and not forced and not workload_changed and not preferences_changed
        results.append(report)
        (folder / 'report.json').write_text(json.dumps(report, indent=2) + '\n')
        print(f"Run {repeat + 1}: {report.get('presentation_hz')} presents/s; gates={report.get('gates')}", flush=True)
    report = dict(scope='Animated title only; no gameplay, map, scanout or visual acceptance',
                  acceptance_duration=args.seconds >= 300 and args.warmup >= 30 and args.repeats >= 3 and args.cap == 60,
                  launch_mode='direct' if args.direct else 'platform-default',
                  binary_sha256=digest, binary=str(executable), source_binary=str(args.binary.resolve()), cache=str(args.cache.resolve()),
                  passed=all(row['passed'] for row in results), results=results)
    (run / 'report.json').write_text(json.dumps(report, indent=2) + '\n')
    print(run / 'report.json', flush=True)
    raise SystemExit(0 if report['passed'] else 1)


if __name__ == '__main__':
    main()
