#!/usr/bin/env python3
"""Check local Markdown file links, archive integrity and generated navigation.

Does not validate remote URLs, prose claims, runtime behavior or ignored
raw artifacts. Frozen JSON provenance paths are historical, resolved by MANIFEST.json.
"""
import hashlib
import json
import re
import unicodedata
from collections import Counter
from pathlib import Path
from urllib.parse import unquote

from docs_index import render

ROOT = Path(__file__).resolve().parents[1]
LINK = re.compile(r'!?\[[^\]\n]*\]\((<[^>]+>|[^\s)]+)(?:\s+"[^"\n]*")?\)')
FENCE = re.compile(r'^```.*?^```[^\n]*', re.MULTILINE | re.DOTALL)


def heading_anchors(path):
    source = FENCE.sub('', path.read_text())
    anchors = set(re.findall(r'<a\s+(?:name|id)=["\']([^"\']+)', source))
    duplicates = Counter()
    for line in source.splitlines():
        heading = re.match(r'^#{1,6}\s+(.+?)\s*#*$', line)
        if not heading:
            continue
        title = re.sub(r'\[([^\]]+)\]\([^)]*\)', r'\1', heading[1])
        title = re.sub(r'<[^>]*>', '', title).lower()
        slug = ''.join(c for c in title if c in '-_ ' or unicodedata.category(c)[0] in 'LN')
        slug = slug.replace(' ', '-')
        count = duplicates[slug]
        duplicates[slug] += 1
        anchors.add(slug + (f'-{count}' if count else ''))
    return anchors


def missing_links(path):
    errors = []
    source = FENCE.sub('', path.read_text())
    for match in LINK.finditer(source):
        target = match[1].strip('<>')
        if re.match(r'[\w+.-]+:', target) or '$' in target:
            continue
        local, _, fragment = target.partition('#')
        local = unquote(local)
        destination = path.parent / local if local else path
        if local and not destination.exists():
            errors.append(f'{path.relative_to(ROOT)}: missing {local}')
        elif fragment and destination.is_file() and destination.suffix == '.md':
            if unquote(fragment) not in heading_anchors(destination):
                errors.append(f'{path.relative_to(ROOT)}: missing heading {target}')
    return errors


def check():
    errors = []
    paths = [ROOT / 'README.md', ROOT / 'ARCHITECTURE.md', *(ROOT / 'docs').rglob('*.md')]
    for path in paths:
        errors.extend(missing_links(path))
    manifest = json.loads((ROOT / 'docs/archive/MANIFEST.json').read_text())
    seen = set()
    for entry in manifest['entries']:
        path = (ROOT / entry['path']).resolve()
        if entry['path'] in seen or not path.is_relative_to(ROOT) or not path.is_file():
            errors.append(f"archive: duplicate/missing/escaping {entry['path']}")
            continue
        seen.add(entry['path'])
        if hashlib.sha256(path.read_bytes()).hexdigest() != entry['archived_sha256']:
            errors.append(f"archive: content changed after relocation: {entry['path']}")
        if not re.fullmatch(r'[a-f0-9]{64}', entry['original_sha256']):
            errors.append(f"archive: invalid original identity: {entry['original']}")
        if entry['kind'] == 'relocated' and (ROOT / entry['original']).exists():
            errors.append(f"archive: retired original still present: {entry['original']}")
    archived = {str(p.relative_to(ROOT)) for p in (ROOT / 'docs/archive/2026-foundation').rglob('*.md')}
    if archived != {e['path'] for e in manifest['entries'] if '/archive/' in e['path']}:
        errors.append('archive: historical Markdown and manifest membership differ')
    for path, expected in render().items():
        if not path.exists() or path.read_text() != expected:
            errors.append(f'{path.relative_to(ROOT)}: stale generated navigation')
    json_paths = list((ROOT / 'docs').rglob('*.json'))
    for path in json_paths:
        try:
            json.loads(path.read_text())
        except (ValueError, UnicodeError) as error:
            errors.append(f'{path.relative_to(ROOT)}: invalid JSON: {error}')
    for path in (ROOT / 'docs/plan').glob('*.md'):
        if path.name != 'PORTING-ROADMAP.md':
            errors.append(f'{path.relative_to(ROOT)}: classify checkpoint in archive or update current owner')
    if errors:
        raise SystemExit('\n'.join(errors))
    print(f'{len(paths)} Markdown files: local file links valid; {len(seen)} relocation/snapshot records intact')
    print(f'Heading links, {len(json_paths)} JSON files and navigation valid; no remote, runtime or parity acceptance inferred')


if __name__ == '__main__':
    check()
