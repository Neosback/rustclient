#!/usr/bin/env python3
"""Register the verified, running GUI-backed Enigma MCP review session."""
import json
import shutil
import subprocess
import urllib.request
from urllib.parse import urlsplit
from enigma import CACHE, check_build

NAME='rustclient-enigma'
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
codex=shutil.which('codex')
if not codex: raise SystemExit('Codex CLI is not on PATH')
existing=subprocess.run([codex,'mcp','get',NAME,'--json'],capture_output=True,text=True)
if existing.returncode==0:
    entry=json.loads(existing.stdout)
    if entry['transport'].get('url')!=url: raise SystemExit(NAME+' already points elsewhere; refusing to replace it')
    print(NAME+' already registered: '+url)
else:
    if 'No MCP server named' not in existing.stderr: raise SystemExit(existing.stderr)
    subprocess.run([codex,'mcp','add',NAME,'--url',url],check=True)
