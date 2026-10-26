#!/usr/bin/env python3
"""Inventory the frozen GPU/core source surface without modifying reference trees."""
import hashlib,json,re
from pathlib import Path
ROOT=Path(__file__).resolve().parents[2]
SOURCE=ROOT/'references/open-runelite-rev-240/runelite-client/src'

def digest(p): return hashlib.sha256(p.read_bytes()).hexdigest()
def main():
    roots=[SOURCE/'main/java/net/runelite/client/plugins/gpu', SOURCE/'main/java/net/runelite/client/plugins/config', SOURCE/'main/java/net/runelite/client/ui/overlay']
    singles=['main/java/net/runelite/client/config/RuneLiteConfig.java','main/java/net/runelite/client/config/ChatColorConfig.java','main/java/net/runelite/client/config/ConfigManager.java','main/java/net/runelite/client/ui/ClientUI.java','main/java/net/runelite/client/ui/ClientToolbar.java']
    files=set(p for root in roots for p in root.rglob('*.java'))
    files.update(SOURCE/p for p in singles)
    rows=[];settings=[]
    for p in sorted(files):
        text=p.read_text();rel=str(p.relative_to(ROOT))
        rows.append({'path':rel,'sha256':digest(p),'status':'source-inventoried','methods':len(re.findall(r'(?m)^\s*(?:public|protected|private)\s+[^;={}]+\([^;{}]*\)\s*(?:throws[^{}]+)?\{',text))})
        for match in re.finditer(r'@ConfigItem\((.*?)\)\s*(?:@Range\([^)]*\)\s*)?(?:default\s+)?([\w<>]+)\s+(\w+)\(\)\s*(?:\{(.*?)\}|;)',text,re.S):
            attributes,kind,symbol,body=match.groups();key=re.search(r'keyName\s*=\s*"([^"]+)"',attributes)
            if key: settings.append({'path':rel,'symbol':symbol,'key':key[1],'javaType':kind,'defaultExpression':(re.search(r'return\s+(.*?);',body or '',re.S) or ['','abstract'])[1].strip(),'status':'unimplemented','rustOwner':None,'verification':'not-run'})
    resources=[{'path':str(p.relative_to(ROOT)),'sha256':digest(p)} for p in sorted((SOURCE/'main/resources/net/runelite/client/plugins/gpu').rglob('*')) if p.is_file()]
    tests=[{'path':str(p.relative_to(ROOT)),'sha256':digest(p),'cases':re.findall(r'@Test\s+(?:public\s+)?void\s+(\w+)',p.read_text()),'verification':'not-run'} for p in sorted((SOURCE/'test/java/net/runelite/client').rglob('*Test.java')) if any(s in str(p) for s in ['plugins/gpu/','plugins/config/','config/ConfigManager','config/Keybind','config/ChatColor','ui/overlay/'])]
    result={'schema':1,'baseline':'RustClient-vendored-source; file hashes define the snapshot','scope':'GPU and RuneLite configuration/core support; no HD or other gameplay plugins','sources':rows,'settings':settings,'resources':resources,'tests':tests}
    out=ROOT/'docs/reference/builtin-plugins/source-inventory.json'
    if out.exists():
        previous=json.loads(out.read_text());bykey={(r['path'],r['key']):r for r in previous['settings']}
        for row in settings:
            old=bykey.get((row['path'],row['key']))
            if old and old['defaultExpression']==row['defaultExpression']:
                for key in ('status','rustOwner','verification'): row[key]=old[key]
    out.write_text(json.dumps(result,indent=2)+'\n')
    print(json.dumps({'sources':len(rows),'settings':len(settings),'resources':len(resources),'sourceTests':len(tests)}))
if __name__=='__main__': main()
