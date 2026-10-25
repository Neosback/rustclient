#!/usr/bin/env python3
"""Exercise the actual GUI-backed HTTP MCP server and restore every test edit."""
import hashlib
import json
from pathlib import Path
import subprocess
import time
import urllib.request

from enigma import ROOT, CACHE, check_build, sha


def main():
    check_build()
    active = json.loads((CACHE / 'active.json').read_text())
    url = active['url']
    request_id = 0

    def rpc(method, params=None, expect_error=False):
        nonlocal request_id
        request_id += 1
        data = json.dumps({'jsonrpc': '2.0', 'id': request_id, 'method': method, 'params': params or {}}).encode()
        request = urllib.request.Request(url, data=data, headers={'Content-Type': 'application/json', 'Accept': 'application/json, text/event-stream'})
        with urllib.request.urlopen(request, timeout=40) as response:
            result = json.load(response)
        assert result['id'] == request_id, result
        assert ('error' in result) == expect_error, result
        return result.get('error') if expect_error else result['result']

    def call(name, arguments=None, expect_error=False):
        result = rpc('tools/call', {'name': name, 'arguments': arguments or {}}, expect_error)
        if expect_error: return result
        assert not result.get('isError'), result
        envelope = json.loads(result['content'][0]['text'])
        assert envelope['originalSha256'] == active['original_sha256']
        assert envelope['analysisSha256'] == active['analysis_sha256']
        return envelope['data']

    original = ROOT / 'references/gamepacks/osrs-240/injected-client-1.12.39.jar'
    original_sha = sha(original)
    refs_before = subprocess.check_output(['git', 'status', '--porcelain', '--', 'references'], cwd=ROOT)
    production = ROOT / 'docs/reference/client-240'
    production_before = {str(p.relative_to(production)): sha(p) for part in ['mappings', 'ledger'] for p in (production / part).rglob('*') if p.is_file()}
    initialized = rpc('initialize', {'protocolVersion': '2024-11-05', 'capabilities': {}, 'clientInfo': {'name': 'rustclient-smoke', 'version': '1'}})
    assert initialized['serverInfo']['name'] == 'enigma-mcp'
    notification = urllib.request.Request(url, data=b'{"jsonrpc":"2.0","method":"notifications/initialized"}', headers={'Content-Type':'application/json'})
    with urllib.request.urlopen(notification, timeout=10) as response: assert response.status == 202
    assert rpc('ping') == {}
    catalog = rpc('tools/list')['tools']
    assert len(catalog) == 16, [t['name'] for t in catalog]
    names = {t['name'] for t in catalog}
    assert {'class_hierarchy', 'find_references', 'validate_rename', 'method_family', 'save_mappings'} <= names
    status = call('workspace_status')
    assert status['classes'] == 735 and status['draftOnly']
    assert 'packages' in call('list_packages')
    classes = call('list_classes', {'package': '', 'limit': 5})
    assert len(classes['classes']) == 5 and classes['total'] > 700
    members = call('list_members', {'class': 'aa', 'limit': 50})
    before_name = members['class']['deobfuscated']
    assert all(not method['renamable'] for method in members['methods'] if method.get('constructor'))
    limited = call('list_members', {'class': 'client', 'limit': 2})
    assert len(limited['fields']) == len(limited['methods']) == 2 and limited['methodCount'] > 1000
    assert call('search', {'query': 'Actor', 'limit': 3})['count'] > 0
    hierarchy = call('class_hierarchy', {'class': 'Actor'})
    assert {child['mapped'] for child in hierarchy['children']} == {'NPC', 'Player'}
    references = call('find_references', {'kind': 'class', 'class': 'PacketBuffer', 'limit': 5})
    assert references['total'] > 0 and len(references['references']) <= 5
    method = next(method for method in members['methods'] if not method.get('constructor'))
    family = call('method_family', {'class':'aa', 'name':method['obfuscated'], 'desc':method['descriptor']})
    assert family['total'] >= 1
    assert call('validate_rename', {'kind':'class', 'class':'aa', 'newName':'McpVerificationProbe'})['canProceed']
    assert not call('validate_rename', {'kind':'class', 'class':'aa', 'newName':'client'})['canProceed']
    assert not call('validate_rename', {'kind':'class', 'class':'aa', 'newName':'1invalid'})['canProceed']
    constructor = next(method for method in members['methods'] if method.get('constructor'))
    call('rename', {'kind':'method','class':'aa','name':'<init>','desc':constructor['descriptor'],'newName':'IllegalConstructor'}, expect_error=True)
    field = next(field for field in members['fields'] if field['status'] == 'obfuscated' and field['renamable'])
    target = {'kind':'field','class':'aa','name':field['obfuscated'],'desc':field['descriptor']}
    previous_doc = call('get_javadoc', {'kind':'class','class':'aa'})
    try:
        call('rename', {'kind':'class','class':'aa','newName':'McpVerificationProbe'})
        assert call('list_members', {'class':'aa','limit':1})['class']['deobfuscated'] == 'McpVerificationProbe'
        call('set_javadoc', {'kind':'class','class':'aa','javadoc':'MCP setup verification only'})
        assert 'MCP setup verification only' in str(call('get_javadoc', {'kind':'class','class':'aa'}))
        call('mark_deobfuscated', target)
        call('reset_obfuscated', target)
    finally:
        call('rename', {'kind':'class','class':'aa','newName':before_name})
        old_doc = previous_doc.get('javadoc') if isinstance(previous_doc, dict) else previous_doc
        call('set_javadoc', {'kind':'class','class':'aa','javadoc':old_doc or ''})
        call('reset_obfuscated', target)
    assert call('list_members', {'class':'aa','limit':1})['class']['deobfuscated'] == before_name
    source = call('decompile_class', {'class':'aa','decompiler':'VINEFLOWER','maxChars':250})
    assert 0 < len(source['source']) <= 250 and source['totalChars'] > 250
    saved = call('save_mappings')
    saved_path = Path(saved['saved']).resolve()
    assert saved_path.is_relative_to((ROOT / active['session']).resolve()) and saved_path.is_file()
    call('list_members', {'class':'aa','limit':-1}, expect_error=True)
    call('list_members', {'class':'does/not/Exist'}, expect_error=True)
    assert sha(original) == original_sha
    assert refs_before == subprocess.check_output(['git', 'status', '--porcelain', '--', 'references'], cwd=ROOT)
    assert production_before == {str(p.relative_to(production)): sha(p) for part in ['mappings', 'ledger'] for p in (production / part).rglob('*') if p.is_file()}
    print(json.dumps({'status':'PASS','tools':len(catalog),'classes':status['classes'],'fields':status['fields'],'methods':status['methods'],
        'checks':['initialize/notification/ping','all 16 tools','default-package and member pagination','hierarchy','references','method family',
            'valid/invalid/colliding rename checks','constructor rejection','reversible sandbox rename/javadoc/mark/reset',
            'paginated decompilation','completed draft save','invalid request errors','original references and production mappings unchanged'],
        'url':url,'session':active['session'],'original_sha256':original_sha},indent=2))


if __name__ == '__main__': main()
