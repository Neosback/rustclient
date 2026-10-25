#!/usr/bin/env python3
"""Compose existing exact-bytecode evidence and optional local rl-suite cross-checks; apply no transforms."""
import argparse
from collections import Counter, defaultdict
import csv
import hashlib
import json
import os
from pathlib import Path
import subprocess
import sys

ROOT = Path(__file__).resolve().parents[2]
HERE = Path(__file__).resolve().parent
sys.path.insert(0, str(ROOT / 'tools/client-240'))
from toolchain import check, output_path, runtime
from workbench import snapshot


def sha(path):
    return hashlib.sha256(path.read_bytes()).hexdigest()


def compare(decoders, candidates):
    pairs = defaultdict(set)
    for pair in decoders['provenPairs']:
        field = pair['field']
        key = (field['owner']['internalName'] + '.' + field['name'], field['descriptor'])
        modulus = 1 << pair['bits']
        if (pair['readMultiplier'] * pair['writeMultiplier']) % modulus != 1:
            raise ValueError('Invalid workbench inverse pair: ' + str(key))
        pairs[key].add(pair['readMultiplier'] % modulus)
    rows = []
    for candidate in candidates:
        descriptor = candidate['descriptor']
        key = (candidate['field'], descriptor)
        row = dict(candidate)
        reads = pairs.get(key, set())
        if descriptor not in ('I', 'J'):
            outcome = 'ambiguous-descriptor'
        elif not reads:
            outcome = 'no-workbench-pair'
        elif int(candidate['multiplier']) % (1 << (32 if descriptor == 'I' else 64)) in reads:
            outcome = 'agrees-with-pair' if len(reads) == 1 else 'agrees-with-one-of-multiple-pairs'
        else:
            outcome = 'different-candidate'
        row['outcome'] = outcome
        row['workbenchReadMultipliersUnsigned'] = [str(value) for value in sorted(reads)]
        rows.append(row)
    return {'workbenchPairs': len(decoders['provenPairs']), 'workbenchFields': len(pairs),
        'suiteCandidates': len(candidates), 'suiteConfidence': dict(Counter(r['confidence'] for r in rows)),
        'outcomes': dict(Counter(r['outcome'] for r in rows)),
        'byConfidence': {confidence: dict(Counter(r['outcome'] for r in rows if r['confidence'] == confidence))
            for confidence in sorted({r['confidence'] for r in rows})},
        'workbenchFieldsWithoutSuiteCandidate': len(set(pairs) - {(r['field'], r['descriptor']) for r in rows}),
        'rows': rows}


def source_inventory(root):
    files = {str(p.relative_to(root)): sha(p) for p in sorted(root.rglob('*'))
        if p.is_file() and not any(part in {'build', 'target', '.gradle', '.git'} for part in p.relative_to(root).parts)
        and (p.suffix == '.java' or p.name in {'build.gradle.kts', 'build.gradle', 'LICENSE', 'LICENSE.txt', 'pom.xml'})}
    if not files:
        raise ValueError('No source files found: ' + str(root))
    return {'treeSha256': hashlib.sha256(json.dumps(files, sort_keys=True).encode()).hexdigest(), 'files': files}


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--out', type=Path, default=ROOT / '.cache/client-240/hybrid-review')
    parser.add_argument('--suite-jar', type=Path, help='Optional caller-supplied rl-suite all.jar; invoked only for DecoderTable.extract')
    for label in ('agent', 'vanilla', 'suite'):
        parser.add_argument('--' + label + '-source', type=Path, help='Optional read-only source inventory')
    args = parser.parse_args()
    check()
    java, _ = runtime()
    out = output_path(args.out)
    out.mkdir(parents=True, exist_ok=True)
    original = ROOT / 'references/gamepacks/osrs-240/injected-client-1.12.39.jar'
    original_sha = sha(original)
    protected = [p for group in ('mappings', 'ledger') for p in (ROOT / 'docs/reference/client-240' / group).rglob('*') if p.is_file()]
    before = {p: sha(p) for p in protected}
    refs_before = subprocess.check_output(['git', 'status', '--porcelain', '--', 'references'], cwd=ROOT)
    _, tree, _ = snapshot()
    cache = Path(os.environ.get('CLIENT240_CACHE', ROOT / '.cache/client-240')).resolve()
    work = cache / 'workbench' / tree
    build = json.loads((work / 'build.json').read_text())
    work_jar = work / 'source/target/client-deob-workbench-0.1.0-SNAPSHOT.jar'
    if sha(work_jar) != build['jar_sha256']:
        raise ValueError('Workbench changed; rebuild with tools/client-240/workbench.py --build')
    for command in ('decoders', 'injected-anchors'):
        subprocess.run([java, '-jar', str(work_jar), command, '--jar', str(original), '--out', str(out / (command + '.json'))], check=True, cwd=ROOT)
    subprocess.run([java, '--source', '17', '-cp', str(work_jar), str(HERE / 'AgentPatternProbe.java'), str(original), str(out / 'agent-patterns.json')], check=True)
    report = {'originalSha256': original_sha, 'workbenchSourceSha256': tree, 'workbenchJarSha256': sha(work_jar),
        'policyRegistrySha256': sha(ROOT / 'tools/client-240/deob-passes.json'),
        'probeSources': {p.name: sha(p) for p in (Path(__file__), HERE / 'AgentPatternProbe.java', HERE / 'RlSuiteDecoderProbe.java')},
        'sources': {}, 'transformsApplied': [], 'automaticMappingsWritten': False}
    for label in ('agent', 'vanilla', 'suite'):
        root = getattr(args, label + '_source')
        if root:
            report['sources'][label] = source_inventory(root.resolve())
    if args.suite_jar:
        suite = args.suite_jar.resolve()
        suite_before = sha(suite)
        subprocess.run([java, '--source', '17', '-cp', str(suite), str(HERE / 'RlSuiteDecoderProbe.java'), str(original), str(out / 'rl-suite-decoders.tsv')], check=True)
        if sha(suite) != suite_before:
            raise ValueError('Caller-supplied binary changed')
        report['suiteJarSha256'] = suite_before
        report['suiteSourceCorrespondence'] = 'NOT_VERIFIED: source inventory and prebuilt binary are separate evidence'
        with (out / 'rl-suite-decoders.tsv').open() as stream:
            comparison = compare(json.loads((out / 'decoders.json').read_text()), list(csv.DictReader(stream, delimiter='\t')))
        (out / 'decoder-comparison.json').write_text(json.dumps(comparison, indent=2) + '\n')
        report['decoderComparison'] = {key: value for key, value in comparison.items() if key != 'rows'}
    patterns = json.loads((out / 'agent-patterns.json').read_text())
    report['agentPatterns'] = dict(Counter(hit['pattern'] for hit in patterns['hits']))
    for name in ('agent-patterns.json', 'injected-anchors.json'):
        if json.loads((out / name).read_text())['corpusSha256'] != original_sha:
            raise ValueError('Evidence corpus identity mismatch: ' + name)
    if sha(original) != original_sha or any(sha(p) != digest for p, digest in before.items()):
        raise ValueError('Protected original/mapping/ledger changed during review')
    if subprocess.check_output(['git', 'status', '--porcelain', '--', 'references'], cwd=ROOT) != refs_before:
        raise ValueError('Reference tree status changed during review')
    report['protectedInputsUnchanged'] = True
    report['outputSha256'] = {p.name: sha(p) for p in sorted(out.iterdir()) if p.is_file() and p.name != 'review.json'}
    (out / 'review.json').write_text(json.dumps(report, indent=2) + '\n')
    print(json.dumps({key: value for key, value in report.items() if key not in {'sources', 'outputSha256'}}, indent=2))


if __name__ == '__main__':
    main()
