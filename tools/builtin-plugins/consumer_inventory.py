"""Pinned bundled-plugin source inputs and syntactic consumer candidates, never parity claims."""
import csv
import hashlib
import re
from pathlib import Path


def sha(path):
    return hashlib.sha256(path.read_bytes()).hexdigest()


def file_record(root, path):
    return dict(path=str(path.relative_to(root)), sha256=sha(path))


def build(root):
    source = root / 'references/open-runelite-rev-240/runelite-client/src'
    java = source / 'main/java/net/runelite/client/plugins'
    resources = source / 'main/resources/net/runelite/client/plugins'
    tests = source / 'test/java/net/runelite/client/plugins'
    fixtures = source / 'test/resources/net/runelite/client/plugins'
    with (root / 'docs/reference/builtin-plugins/RUNELITE-PLUGINS.tsv').open() as stream:
        catalog = list(csv.DictReader(stream, delimiter='\t'))
    registry = (root / 'crates/first-party-plugins/src/lib.rs').read_text()
    registered = set(re.findall(r'name:\s*"([a-z]+)"', registry))
    required_path = root / 'crates/plugin-host/src/required.rs'
    required = set(re.findall(r'"dev\.rustclient\.([a-z]+)"', required_path.read_text()))
    result = []
    for row in catalog:
        name = row['plugin']
        files, subscriptions, calls, keys, deps = [], [], [], [], set()
        for path in sorted((java / name).rglob('*.java')):
            text = path.read_text()
            # Keep candidates explicitly distinct from a language-aware semantic audit.
            clean = re.sub(r'"(?:\\.|[^"\\])*"|\'(?:\\.|[^\'\\])*\'|/\*.*?\*/|//[^\n]*',
                           lambda match: '\n'*match[0].count('\n') if match[0].startswith(('/*','//')) else match[0], text, flags=re.S)
            record = file_record(root, path)
            files.append(record)
            for match in re.finditer(r'@Subscribe(?:\(([^)]*)\))?\s+(?:public|protected|private)\s+void\s+(\w+)\s*\((?:final\s+)?([\w.]+)\s+\w+\)', clean):
                subscriptions.append(dict(path=record['path'], method=match[2], event=match[3], annotation=match[1] or '', line=clean.count('\n', 0, match.start())+1))
            for match in re.finditer(r'\b(client|itemManager|overlayManager|clientThread|configManager)\.(\w+)\s*\(', clean):
                calls.append(dict(path=record['path'], receiver=match[1], method=match[2]))
            keys += [dict(path=record['path'], key=key) for key in re.findall(r'keyName\s*=\s*"([^"]+)"',clean)]
            deps.update(re.findall(r'@PluginDependency\((\w+)\.class\)',clean))
        source_tests = []
        for path in sorted(set((tests / name).rglob('*')) | set((fixtures / name).rglob('*'))):
            if not path.is_file():
                continue
            record = file_record(root,path)
            if path.suffix == '.java':
                record['declared_test_candidates'] = re.findall(r'@(?:Test|ParameterizedTest|TestFactory)(?:\([^)]*\))?\s+(?:public\s+)?void\s+(\w+)',path.read_text())
                record['case_expansion'] = 'not-audited; parameterized/dynamic cases require separate source-case records'
            source_tests.append(record)
        result.append(dict(plugin=name, category=row['category'], registered_in_rust=name in registered or name in required,
                           registration_kind='required-service' if name in required else 'first-party-entry' if name in registered else 'no-registration-found',
                           disposition=row['disposition'], source_files=files,
                           resources=[file_record(root,p) for p in sorted((resources/name).rglob('*')) if p.is_file()],
                           tests_and_fixtures=source_tests, dependency_candidates=sorted(deps),
                           subscription_candidates=subscriptions, call_candidates=calls, config_keys=keys,
                           verification='source-inventory-only; no reachability, execution or visual acceptance implied'))
    return dict(scope='All catalog slots; feature-level local/service dispositions still require source audit',
                extraction='Syntactic candidates plus complete per-folder file hashes; not an AST/member/case-completeness claim',
                registration_sources=[file_record(root, root / 'crates/first-party-plugins/src/lib.rs'), file_record(root, required_path)],
                plugins=result)
