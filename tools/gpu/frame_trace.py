#!/usr/bin/env python3
"""Score bounded native traces. Present calls measure the application, not scanout."""
import argparse
import json
import math
from pathlib import Path


def percentile(values, fraction):
    return sorted(values)[max(0, math.ceil(len(values) * fraction) - 1)] if values else None


def summarize(path, warmup, seconds, cap):
    frames, gpu, metadata, endings, completed, stages = [], {}, [], [], [], []
    with path.open() as stream:
        for line in stream:
            row = json.loads(line)
            kind = row.get('type')
            if kind == 'frame' and warmup * 1e9 <= row['at_ns'] < (warmup + seconds) * 1e9:
                frames.append(row)
            elif kind == 'gpu':
                gpu[row['epoch'], row['frame']] = row['ns']
            elif kind == 'stage':
                stages.append(row)
            elif kind == 'renderer':
                metadata.append(row)
            elif kind == 'trace_end':
                endings.append(row)
            elif kind == 'gpu_complete':
                completed.append(row)
    shown = [row for row in frames if row['presented']]
    gaps = [row['gap_ns'] / 1e6 for row in shown if row['gap_ns'] is not None]
    render_cpu = [row['cpu_work_upper_ns'] / 1e6 for row in frames]
    # Account for measured event/cycle work between redraws, without counting nested
    # stages or work already inside a render callback twice. These are wall-time
    # upper estimates for instrumented main-thread work, not OS CPU or worker time.
    intervals = sorted((row['at_ns'] - row['ns'], row['at_ns']) for row in stages)
    merged = []
    for start, end in intervals:
        if merged and start <= merged[-1][1]:
            merged[-1] = (merged[-1][0], max(merged[-1][1], end))
        else:
            merged.append((start, end))
    cpu, cursor = [], 0
    previous = warmup * 1e9
    for row in frames:
        render_start = row['at_ns'] - row['frame_wall_ns']
        outside = 0
        while cursor < len(merged) and merged[cursor][1] <= previous:
            cursor += 1
        index = cursor
        while index < len(merged) and merged[index][0] < render_start:
            start, end = merged[index]
            outside += max(0, min(end, render_start) - max(start, previous))
            index += 1
        cpu.append((row['cpu_work_upper_ns'] + outside) / 1e6)
        previous = row['at_ns']
    times = [gpu[row['epoch'], row['frame']] / 1e6 for row in shown
             if (row['epoch'], row['frame']) in gpu]
    refresh = metadata[-1].get('refresh_millihertz') if metadata else None
    target = min(cap, refresh / 1000) if refresh else cap
    hz = len(shown) / seconds
    over25 = sum(value > 25 for value in gaps) / len(gaps) if gaps else None
    valid = bool(endings and endings[-1]['valid'] and endings[-1]['dropped'] == 0
                 and frames and shown[-1]['at_ns'] >= (warmup + seconds - .2) * 1e9)
    gpu_valid = bool(valid and completed and completed[-1]['dropped'] == 0
                     and len(times) == len(shown) and all(math.isfinite(x) and x >= 0 for x in times))
    # These are the 60 Hz acceptance gates, not a generic test for arbitrary caps.
    gates = dict(trace_complete=valid, rate=hz >= target * .99,
                 cpu_p99=bool(cpu and percentile(cpu, .99) <= 10),
                 gpu_p99=bool(gpu_valid and percentile(times, .99) <= 12),
                 gap_p99=bool(gaps and percentile(gaps, .99) <= 20),
                 gaps_over25=over25 is not None and over25 <= .001)
    return dict(scope='Application present calls; display cadence and commit-to-visible latency unverified',
                trace=str(path), warmup_seconds=warmup, scored_seconds=seconds,
                requested_cap=cap, target_hz=target, measured_refresh_millihertz=refresh,
                frames=len(frames), presentations=len(shown), presentation_hz=hz,
                instrumented_main_work_upper_p99_ms=percentile(cpu, .99),
                render_work_upper_p99_ms=percentile(render_cpu, .99),
                cpu_scope='Render wall minus acquire/present waits plus non-overlapping measured main-thread stages between redraws; worker CPU and OS thread CPU are separate',
                gpu_p99_ms=percentile(times, .99),
                gpu_samples=len(times), gpu_valid=gpu_valid, gap_p99_ms=percentile(gaps, .99),
                gap_max_ms=max(gaps, default=None), gaps_over25_fraction=over25,
                vertex_uploaded_bytes=sum(row['vertex_upload_bytes'] for row in frames),
                dynamic_uploaded_bytes=sum(row['dynamic_upload_bytes'] for row in frames),
                ui_raster_passes=sum(row['ui_raster_passes'] for row in frames),
                renderer_metadata=metadata, gates=gates, passed=all(gates.values()))


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('trace', type=Path)
    parser.add_argument('--warmup', type=float, default=30)
    parser.add_argument('--seconds', type=float, default=300)
    parser.add_argument('--cap', type=float, default=60)
    parser.add_argument('--output', type=Path)
    args = parser.parse_args()
    report = summarize(args.trace, args.warmup, args.seconds, args.cap)
    text = json.dumps(report, indent=2) + '\n'
    if args.output:
        args.output.write_text(text)
    print(text)
    raise SystemExit(0 if report['passed'] else 1)


if __name__ == '__main__':
    main()
