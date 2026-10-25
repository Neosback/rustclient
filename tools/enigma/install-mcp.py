#!/usr/bin/env python3
"""Register the verified, running GUI-backed Enigma MCP review session."""
import argparse
import json
import shutil
import subprocess
import urllib.request
from urllib.parse import urlsplit
from enigma import CACHE, check_build

NAME='rustclient-enigma'
parser=argparse.ArgumentParser(description=__doc__)
parser.add_argument('--client',choices=('codex','claude','all'),default='codex')
args=parser.parse_args()
check_build()
active=json.loads((CACHE/'active.json').read_text())
url=active['url']; parsed=urlsplit(url)
if parsed.scheme!='http' or parsed.hostname!='127.0.0.1' or parsed.path!='/mcp':
    raise SystemExit('Expected this checkout’s loopback MCP session')
body=json.dumps({'jsonrpc':'2.0','id':1,'method':'tools/call','params':{'name':'workspace_status','arguments':{}}}).encode()
reply=json.load(urllib.request.urlopen(urllib.request.Request(url,data=body,headers={'Content-Type':'application/json'}),timeout=20))
if 'error' in reply: raise SystemExit(str(reply['error']))
status=json.loads(reply['result']['content'][0]['text'])
if status['originalSha256']!=active['original_sha256'] or not status['data']['draftOnly']:
    raise SystemExit('Unexpected Enigma workspace')
for client in ('codex','claude') if args.client=='all' else (args.client,):
    executable=shutil.which(client)
    if not executable: raise SystemExit(client+' CLI is not on PATH')
    command=[executable,'mcp','get',NAME]
    if client=='codex': command.append('--json')
    existing=subprocess.run(command,capture_output=True,text=True)
    if existing.returncode==0:
        if client=='codex': found=json.loads(existing.stdout)['transport'].get('url')
        else:
            found=next((line.partition('URL:')[2].strip() for line in existing.stdout.splitlines() if line.strip().startswith('URL:')),None)
        if found!=url: raise SystemExit(client+': '+NAME+' already points elsewhere; refusing to replace it')
        print(client+': '+NAME+' already registered: '+url)
    else:
        if 'No MCP server named' not in existing.stderr+existing.stdout: raise SystemExit(existing.stderr+existing.stdout)
        if client=='codex': command=[executable,'mcp','add',NAME,'--url',url]
        else: command=[executable,'mcp','add','--transport','http','--scope','user',NAME,url]
        subprocess.run(command,check=True)
