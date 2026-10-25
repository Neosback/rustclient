#!/usr/bin/env python3
"""Repair known descriptor/ownership defects without inventing or changing readable names.

Default is a cache-only plan. --apply writes the reviewed deterministic repair; run verify.sh afterward.
"""
import argparse
from collections import defaultdict
import hashlib
import json
from pathlib import Path
import sys

ROOT=Path(__file__).resolve().parents[2]
sys.path.insert(0,str(ROOT/'tools/client-240'))
from toolchain import check, output_path


def sha(data): return hashlib.sha256(data).hexdigest()


def main():
    parser=argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--index',type=Path,default=ROOT/'.cache/client-240/mapping-repair/semantic-index.json')
    parser.add_argument('--apply',action='store_true')
    args=parser.parse_args()
    check()
    docs=ROOT/'docs/reference/client-240'
    index=json.loads(args.index.read_text())
    semantic=docs/'inputs/injected-client-1.12.39-deob-semantic.jar'
    if index['sha256']!=sha(semantic.read_bytes()): raise ValueError('Index does not describe pinned semantic input')
    descriptors=defaultdict(set)
    for cls in index['classes']:
        for kind,group in [('f','fields'),('m','methods')]:
            for entry in cls[group]:
                identity=entry['id']
                descriptors[(kind,identity['owner']['internalName'],identity['name'])].add(identity['descriptor'])
    destinations={'Actor':'03-actor-motion','Player':'03-actor-motion','NPC':'03-actor-motion',
        'WorldView':'06-world-region','classKY':'13-coord'}
    inputs={p:p.read_bytes() for folder,extension in [('mappings','*.tiny'),('ledger','*.tsv')]
        for p in sorted((docs/folder/'clusters').glob(extension))}
    classes={}
    order=defaultdict(list)
    changes=[]
    for path,data in inputs.items():
        if path.suffix!='.tiny': continue
        current=None
        for line in data.decode().splitlines()[1:]:
            if not line or line.startswith('#'): continue
            parts=line.split('\t')
            if parts[0]=='c':
                current=parts[1]; dest=destinations.get(current,path.stem)
                if current not in classes:
                    classes[current]={'destination':dest,'name':parts[2],'members':{}}
                    order[dest].append(current)
                elif classes[current]['name']!=parts[2] or classes[current]['destination']!=dest:
                    raise ValueError('Conflicting class rename/owner: '+current)
                if dest!=path.stem: changes.append({'class':current,'fromShard':path.stem,'toShard':dest})
            elif parts[0]=='' and parts[1] in ('f','m'):
                kind,desc,name,target=parts[1:]
                if path.stem=='03-actor-motion' and desc==kind:
                    choices=descriptors[(kind,current,name)]
                    if len(choices)!=1: raise ValueError('Descriptor is not unique: '+str((current,name,choices)))
                    desc=next(iter(choices)); changes.append({'symbol':current+'.'+name,'fromDescriptor':parts[2],'toDescriptor':desc})
                if desc not in descriptors[(kind,current,name)]: raise ValueError('Unresolved member: '+current+'.'+name)
                key=(kind,desc,name)
                previous=classes[current]['members'].setdefault(key,target)
                if previous!=target: raise ValueError('Conflicting member rename: '+current+'.'+name)
            else: raise ValueError('Unexpected mapping line: '+line)
    outputs={}
    for path in inputs:
        if path.suffix!='.tiny':continue
        lines=['tiny\t2\t0\tsemantic\treadable']
        for owner in order[path.stem]:
            cls=classes[owner]; lines.append('c\t'+owner+'\t'+cls['name'])
            for (kind,desc,name),target in cls['members'].items(): lines.append('\t'+'\t'.join((kind,desc,name,target)))
        outputs[path]=('\n'.join(lines)+'\n').encode()
    ledgers=defaultdict(dict)
    # Prefer the dedicated shard's existing row when an identical mapping was duplicated.
    ledger_inputs=sorted((p for p in inputs if p.suffix=='.tsv'),key=lambda p:p.stem in destinations.values(),reverse=True)
    for path in ledger_inputs:
        for line in inputs[path].decode().splitlines():
            if not line or line.startswith('#'):continue
            row=line.split('\t')
            if len(row)!=10:raise ValueError('Invalid ledger row: '+str(path))
            dest=destinations.get(row[1],path.stem); row[9]=dest
            existing=ledgers[dest].get(row[0])
            if existing:
                if existing[1:5]!=row[1:5]:raise ValueError('Conflicting ledger identity/name: '+row[0])
                if existing[7]!=row[7]:existing[7]+='; prior '+path.stem+' evidence: '+row[7]
            else:ledgers[dest][row[0]]=row
    for path in inputs:
        if path.suffix=='.tsv':outputs[path]=(''.join('\t'.join(row)+'\n' for row in ledgers[path.stem].values())).encode()
    manifest={'semanticSha256':index['sha256'],'indexSha256':sha(args.index.read_bytes()),'changes':changes,
        'files':{str(p.relative_to(ROOT)):{'before':sha(inputs[p]),'after':sha(data)} for p,data in outputs.items() if data!=inputs[p]},
        'policy':'No new readable names or confidence upgrades; descriptors resolved only by unique exact pinned member identity.',
        'applied':args.apply}
    plan=output_path(ROOT/'.cache/client-240/mapping-repair/repair-plan.json')
    plan.parent.mkdir(parents=True,exist_ok=True);plan.write_text(json.dumps(manifest,indent=2)+'\n')
    if args.apply:
        if any(p.read_bytes()!=data for p,data in inputs.items()):raise ValueError('Concurrent mapping/ledger edits detected')
        for path,data in outputs.items():
            if data!=inputs[path]:path.write_bytes(data)
    print('descriptor repairs:',sum('toDescriptor' in row for row in changes),'changed files:',len(manifest['files']),'applied:',args.apply)


if __name__=='__main__':main()
