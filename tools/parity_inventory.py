#!/usr/bin/env python3
"""Generate/check parity denominators that are derivable from pinned sources and Rust code.

Outputs (all are inventories, never completion percentages):

- docs/reference/rs240-port/CS2-HOST-COVERAGE.tsv: every revision-240 script opcode number known to
  the opcode evidence table, with the Rust adapter name and whether the application script host has
  a dispatch arm for it. A dispatch arm is a static code reference, not proof of correct behavior.
- docs/reference/builtin-plugins/RUNELITE-PLUGINS.tsv: every plugin directory in the pinned
  open-runelite 240 tree. Source-derived columns are regenerated; `category`, `blocking`,
  `disposition` and `note` are hand-owned review data and are preserved by --write.
- docs/reference/builtin-plugins/RUNELITE-EVENTS.tsv: every RuneLite API event class against the
  versioned RustClient plugin event enum and whether host code constructs it.
"""
import argparse
import csv
import io
import json
import re
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
RL = ROOT / 'references/open-runelite-rev-240'
PLUGINS = RL / 'runelite-client/src/main/java/net/runelite/client/plugins'
EVENTS = RL / 'runelite-api/src/main/java/net/runelite/api/events'
CS2_OUT = ROOT / 'docs/reference/rs240-port/CS2-HOST-COVERAGE.tsv'
PLUGIN_OUT = ROOT / 'docs/reference/builtin-plugins/RUNELITE-PLUGINS.tsv'
EVENT_OUT = ROOT / 'docs/reference/builtin-plugins/RUNELITE-EVENTS.tsv'
MATRIX = ROOT / 'docs/reference/rs240-port/PORT-MATRIX.tsv'

CATEGORIES = {'client-feature', 'content-helper', 'external-service', 'developer', 'fork-specific'}
DISPOSITIONS = {'proposed-applicable', 'needs-decision', 'required'}
# Events whose variant exists and is diffed from projected actor fields that the projector
# currently hard-codes empty (crates/plugin-bridge/src/project.rs), so they cannot fire.
SOURCE_ABSENT = {
    'GraphicChanged': 'ActorState.graphic is always None: no spot-animation state in GameState',
    'InteractingChanged': 'ActorState.interacting is always None: no target-facing state in GameState',
    'HitsplatApplied': 'ActorState.hitsplats is always empty: no hitsplat state in GameState',
}


def tsv(rows, header):
    out = io.StringIO()
    writer = csv.writer(out, delimiter='\t', lineterminator='\n')
    writer.writerow(header)
    writer.writerows(rows)
    return out.getvalue()


def rust_without_tests(path):
    return re.split(r'#\[cfg\(test\)\]', path.read_text())[0]


def cs2():
    adapter = [(int(number), name) for number, name in re.findall(
        r'\((\d+), Opcode::(\w+)\)', (ROOT / 'crates/cache/src/osrs240/script_opcodes.rs').read_text())]
    names = dict(adapter)
    folded = dict(re.findall(r'Self::(\w+) => \(Self::(\w+), true\)',
                             (ROOT / 'crates/cache/src/script.rs').read_text()))
    dispatched = set()
    hosts = sorted((ROOT / 'crates/client-script/src').glob('*.rs')) + sorted(
        p for p in (ROOT / 'crates/application/src/scripts').glob('*.rs') if 'test' not in p.name)
    for path in hosts:
        text = rust_without_tests(path)
        for block in re.findall(r'Opcode::\{([^}]*)\}', text):
            dispatched |= set(re.findall(r'\b([A-Z]\w*)\b', block))
        dispatched |= set(re.findall(r'Opcode::([A-Z]\w*)', text))
    with (ROOT / 'docs/reference/client-240/inputs/script-opcodes-240.tsv').open(newline='') as stream:
        evidence = {int(r['opcode']): r for r in csv.DictReader(stream, delimiter='\t')}
    rows = []
    for number in sorted(set(evidence) | set(names)):
        source = evidence.get(number, {})
        name = names.get(number, '')
        uses = source.get('live_uses') or ''
        if name:
            status = 'host-dispatched' if (name in dispatched or folded.get(name) in dispatched) \
                else 'named-unhandled'
        else:
            status = 'unnamed-cache-used' if uses not in ('', '0') else 'unnamed-unused'
        rows.append([number, name, source.get('runelite_name', ''), source.get('status', 'adapter'),
                     status, uses, source.get('handlers', '')])
    return tsv(rows, ['opcode', 'adapter_name', 'runelite_name', 'evidence_status', 'host_status',
                      'live_uses', 'scanned_handlers']), rows


def descriptor(files):
    for path in files:
        match = re.search(r'@PluginDescriptor\s*\((.*?)\)\s*(?:@\w+[^\n]*\s*)*public class',
                          path.read_text(errors='ignore'), re.S)
        if match:
            return match.group(1)
    return ''


def plugins():
    hand = {}
    if PLUGIN_OUT.exists():
        with PLUGIN_OUT.open(newline='') as stream:
            hand = {r['plugin']: r for r in csv.DictReader(stream, delimiter='\t')}
    schemas = {s['name'] for s in json.loads(
        (ROOT / 'crates/plugin-host/src/first-party-schemas.json').read_text())}
    modules = {p.stem for p in (ROOT / 'crates/first-party-plugins/src').glob('*.rs')}
    matrix_ids = {r['id'] for r in csv.DictReader(MATRIX.open(newline=''), delimiter='\t')}
    rows = []
    for directory in sorted(p for p in PLUGINS.iterdir() if p.is_dir()):
        files = sorted(directory.rglob('*.java'))
        text = descriptor(files)
        name = re.search(r'name\s*=\s*"([^"]*)"', text)
        name = name.group(1) if name else ''
        default = 'false' if re.search(r'enabledByDefault\s*=\s*false', text) else 'true'
        flags = '|'.join(f for f, pattern in [('hidden', r'hidden\s*=\s*true'),
                                               ('developer', r'developerPlugin\s*=\s*true')]
                         if re.search(pattern, text))
        network = any(re.search(r'OkHttpClient|HttpUrl|okhttp3|WebSocket', f.read_text(errors='ignore'))
                      for f in files)
        lines = sum(f.read_text(errors='ignore').count('\n') for f in files)
        if directory.name == 'gpu':
            status = 'required-service'
        elif directory.name in modules:
            status = 'partial-module'
        elif name in schemas:
            status = 'schema-only'
        else:
            status = 'not-started'
        own = hand.get(directory.name)
        if own is None:
            raise ValueError(f'{directory.name}: new plugin needs hand-owned category/blocking/disposition')
        if own['category'] not in CATEGORIES or own['disposition'] not in DISPOSITIONS:
            raise ValueError(f'{directory.name}: invalid category or disposition')
        unknown = set(filter(None, own['blocking'].split('|'))) - matrix_ids
        if unknown or not own['blocking']:
            raise ValueError(f'{directory.name}: blocking ids must be PORT-MATRIX ids, got {unknown}')
        rows.append([directory.name, name, default, flags, len(files), lines,
                     'yes' if network else 'no', status, own['category'], own['blocking'],
                     own['disposition'], own['note']])
    stale = set(hand) - {r[0] for r in rows}
    if stale:
        raise ValueError(f'plugin rows without a pinned source directory: {sorted(stale)}')
    return tsv(rows, ['plugin', 'name', 'enabled_by_default', 'flags', 'java_files', 'java_lines',
                      'network', 'rustclient_status', 'category', 'blocking', 'disposition',
                      'note']), rows


def events():
    source = (ROOT / 'crates/plugin-api/src/events.rs').read_text()
    body = source.split('pub enum GameEvent', 1)[1].split('\n}', 1)[0]
    declared = set(re.findall(r'^    ([A-Z]\w+)', body, re.M))
    constructed = set()
    for crate in ['plugin-bridge', 'plugin-host', 'application']:
        for path in (ROOT / 'crates' / crate / 'src').rglob('*.rs'):
            if 'test' not in path.name:
                constructed |= set(re.findall(r'GameEvent::([A-Z]\w+)', rust_without_tests(path)))
    for path in (ROOT / 'apps/native/src').rglob('*.rs'):
        constructed |= set(re.findall(r'GameEvent::([A-Z]\w+)', rust_without_tests(path)))
    rows = []
    names = sorted(p.stem for p in EVENTS.glob('*.java'))
    for name in names:
        if name not in declared:
            status, note = 'absent', ''
        elif name in SOURCE_ABSENT:
            status, note = 'declared-source-absent', SOURCE_ABSENT[name]
        elif name in constructed:
            status, note = 'emitted', ''
        else:
            status, note = 'declared-never-emitted', 'no host/bridge construction outside tests'
        rows.append([name, name if name in declared else '', status, note])
    for name in sorted(declared - set(names)):
        rows.append(['', name, 'rustclient-only' if name in constructed else 'declared-never-emitted',
                     'no RuneLite API event class of this name'])
    return tsv(rows, ['runelite_event', 'plugin_api_event', 'status', 'note']), rows


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    group = parser.add_mutually_exclusive_group(required=True)
    group.add_argument('--write', action='store_true')
    group.add_argument('--check', action='store_true')
    args = parser.parse_args()
    summaries = []
    for path, build, column in [(CS2_OUT, cs2, 4), (PLUGIN_OUT, plugins, 7), (EVENT_OUT, events, 2)]:
        expected, rows = build()
        if args.write:
            path.write_text(expected)
        elif not path.exists() or path.read_text() != expected:
            raise SystemExit(f'{path.relative_to(ROOT)} is stale; run tools/parity_inventory.py --write')
        counts = {}
        for row in rows:
            counts[row[column]] = counts.get(row[column], 0) + 1
        summaries.append(f'{path.name}: {len(rows)} rows {dict(sorted(counts.items()))}')
    print('\n'.join(summaries))
    print('Inventories only: a dispatch arm, module or declared event is not behavioral parity')


if __name__ == '__main__':
    main()
