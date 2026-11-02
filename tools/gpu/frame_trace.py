#!/usr/bin/env python3
"""Score bounded native traces. Present calls measure the application, not scanout."""
import argparse
import json
import math
from pathlib import Path


def percentile(values, fraction):
    return sorted(values)[max(0, math.ceil(len(values) * fraction) - 1)] if values else None


def summarize(path, warmup, seconds, cap):
    frames, gpu, metadata, endings, completed, stages, events = [], {}, [], [], [], [], []
    all_frames, locations = [], []
    with path.open() as stream:
        for line in stream:
            row = json.loads(line)
            kind = row.get('type')
            if kind == 'frame':
                all_frames.append(row)
                if warmup * 1e9 <= row['at_ns'] < (warmup + seconds) * 1e9:
                    frames.append(row)
            elif kind == 'location':
                locations.append(row)
            elif kind == 'gpu':
                gpu[row['epoch'], row['frame']] = row['ns']
            elif kind == 'stage':
                stages.append(row)
            elif kind == 'event':
                events.append(row)
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
    target = (refresh / 1000 if refresh else 60) if cap == 0 else (min(cap, refresh / 1000) if refresh else cap)
    hz = len(shown) / seconds
    over25 = sum(value > 25 for value in gaps) / len(gaps) if gaps else None
    prior = [row['description'] for row in events if row['at_ns'] <= warmup * 1e9]
    focused = metadata[-1].get('focused') if metadata else None
    occluded = metadata[-1].get('occluded') if metadata else None
    for description in prior:
        if description.startswith('Focused('):
            focused = description == 'Focused(true)'
        elif description.startswith('Occluded('):
            occluded = description == 'Occluded(true)'
    events = [row for row in events if warmup * 1e9 <= row['at_ns'] < (warmup + seconds) * 1e9]
    changes = [row for row in events if row['description'].startswith((
        'Resized(', 'Moved(', 'ScaleFactorChanged', 'Occluded(true)', 'Focused(false)'))]
    valid = bool(focused is True and occluded is False and not changes and endings and endings[-1]['valid'] and endings[-1]['dropped'] == 0
                 and shown and shown[-1]['at_ns'] >= (warmup + seconds - .2) * 1e9)
    gpu_available = any(row.get('gpu_available', False) for row in shown)
    gpu_valid = bool(gpu_available and valid and completed and completed[-1]['dropped'] == 0
                     and len(times) == len(shown) and any(x > 0 for x in times)
                     and all(math.isfinite(x) and x >= 0 for x in times))
    # These are the 60 Hz acceptance gates, not a generic test for arbitrary caps.
    gates = dict(trace_complete=valid, rate=hz >= target * .99,
                 cpu_p99=bool(cpu and percentile(cpu, .99) <= 10),
                 gpu_p99=bool(gpu_valid and percentile(times, .99) <= 12),
                 gap_p99=bool(gaps and percentile(gaps, .99) <= 20),
                 gaps_over25=over25 is not None and over25 <= .001)
    statuses = {name: 'pass' if value else 'fail' for name, value in gates.items()}
    if not valid:
        statuses = {name: 'invalid' for name in gates}
    elif not gpu_available:
        statuses['gpu_p99'] = 'unavailable'
    elif not gpu_valid:
        statuses['gpu_p99'] = 'invalid'
    return dict(scope='Application present calls; display cadence and commit-to-visible latency unverified',
                trace=str(path), foreground_at_start=focused, occluded_at_start=occluded, lifecycle_changes_in_scored_window=changes, warmup_seconds=warmup, scored_seconds=seconds,
                requested_cap=cap, uncapped_characterization=cap == 0, target_hz=target, measured_refresh_millihertz=refresh,
                frames=len(frames), presentations=len(shown), presentation_hz=hz,
                instrumented_main_work_upper_p99_ms=percentile(cpu, .99),
                render_work_upper_p99_ms=percentile(render_cpu, .99),
                cpu_scope='Render wall minus acquire/present waits plus non-overlapping measured main-thread stages between redraws; worker CPU and OS thread CPU are separate',
                gpu_p99_ms=percentile(times, .99),
                gpu_scope='First executed world/UI render pass through final display pass; preceding queue uploads and scanout are outside this interval',
                gpu_samples=len(times), gpu_valid=gpu_valid, gap_p99_ms=percentile(gaps, .99),
                gap_max_ms=max(gaps, default=None), gaps_over25_fraction=over25,
                vertex_uploaded_bytes=sum(row['vertex_upload_bytes'] for row in frames),
                dynamic_uploaded_bytes=sum(row['dynamic_upload_bytes'] for row in frames),
                ui_raster_passes=sum(row['ui_raster_passes'] for row in frames),
                location_upload_bytes=sum(row.get('location_upload_bytes', 0) for row in frames),
                location_latency=location_latency(locations, all_frames, stages, warmup, seconds),
                renderer_metadata=metadata, gates=gates, gate_status=statuses, passed=all(gates.values()))


def location_latency(records, frames, stages, warmup, seconds):
    """Conservative group latency; includes coalesced observations, never scanout."""
    observations = [row for row in records if row['phase'] == 'post_cycle_observation'
                    and warmup*1e9 <= row['at_ns'] < (warmup+seconds)*1e9]
    publications = sorted((row for row in records if row['phase'] == 'published' and row['publication']),
                          key=lambda row: row['at_ns'])
    covered, samples = {}, []
    for publication in publications:
        scene = publication.get('epoch', 0), publication['scene_generation']
        candidates = [row for row in observations if (row.get('epoch', 0), row['scene_generation']) == scene
                      and covered.get(scene, -1) < row['source_generation'] <= publication['source_generation']
                      and row['at_ns'] <= publication['at_ns']]
        if not candidates:
            continue
        start = min(row['at_ns'] for row in candidates)
        visible = next((row for row in frames if row['presented']
                        and (row.get('epoch', 0), row.get('scene_generation')) == scene
                        and row.get('location_publication') == publication['publication']
                        and row['at_ns'] >= publication['at_ns']), None)
        if visible is None:
            continue
        commit_stages = [row for row in stages if row['stage'] == 7
                         and row['at_ns'] - row['ns'] <= start <= row['at_ns']]
        commit_start = min((row['at_ns']-row['ns'] for row in commit_stages), default=None)
        samples.append(dict(epoch=scene[0], scene_generation=scene[1], publication=publication['publication'],
                            observations=len(candidates), post_cycle_to_present_ms=(visible['at_ns']-start)/1e6,
                            commit_script_upper_ms=None if commit_start is None else (visible['at_ns']-commit_start)/1e6))
        covered[scene] = publication['source_generation']
    upper = [row['commit_script_upper_ms'] for row in samples if row['commit_script_upper_ms'] is not None]
    unresolved = sum(row['source_generation'] > covered.get((row.get('epoch', 0), row['scene_generation']), -1)
                     for row in observations)
    return dict(scope='Post-cycle observation to first application present carrying the installed group; '
                      'commit/script upper bound only when enclosing stage exists; packet arrival and scanout unavailable',
                samples=samples, post_cycle_p99_ms=percentile([row['post_cycle_to_present_ms'] for row in samples], .99),
                commit_script_upper_p99_ms=percentile(upper, .99),
                unresolved_observations=unresolved,
                gate='invalid' if unresolved else ('unavailable' if not upper or len(upper) != len(samples) else ('pass' if percentile(upper, .99) <= 50 else 'fail')))


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
