#!/usr/bin/env python3
"""Exercise MCP framing and real Recaf analysis without mutating the pinned client."""
import hashlib
import json
import os
from pathlib import Path
import selectors
import subprocess
import sys

ROOT = Path(__file__).resolve().parents[2]
ORIGINAL = ROOT / 'references/gamepacks/osrs-240/injected-client-1.12.39.jar'


def main():
    original_hash = hashlib.sha256(ORIGINAL.read_bytes()).hexdigest()
    log_path = ROOT / 'target/recaf-mcp-smoke.log'
    log_path.parent.mkdir(exist_ok=True)
    command = [sys.executable, str(ROOT / 'tools/recaf/recaf.py')]
    env = os.environ.copy()
    if sys.argv[1:] == ['--registered']:
        registration = json.loads(subprocess.check_output(['codex', 'mcp', 'get', 'rustclient-recaf', '--json'], text=True))
        transport = registration['transport']
        assert registration['enabled'] and transport['type'] == 'stdio'
        command = [transport['command'], *transport['args']]
        env.update(transport.get('env') or {})
    elif sys.argv[1:]:
        raise SystemExit('Usage: verify.py [--registered]')
    with log_path.open('w') as log:
        child = subprocess.Popen(command, cwd=ROOT, env=env, stdin=subprocess.PIPE,
                                 stdout=subprocess.PIPE, stderr=log, text=True, bufsize=1)
        selector = selectors.DefaultSelector()
        selector.register(child.stdout, selectors.EVENT_READ)
        request_id = 0

        def rpc(method, params):
            nonlocal request_id
            request_id += 1
            child.stdin.write(json.dumps({'jsonrpc': '2.0', 'id': request_id, 'method': method, 'params': params}) + '\n')
            child.stdin.flush()
            if not selector.select(timeout=60):
                raise AssertionError(f'MCP response timed out: {method}; see {log_path}')
            line = child.stdout.readline()
            if not line:
                raise AssertionError(f'MCP exited early: {child.poll()}; see {log_path}')
            result = json.loads(line)
            assert result['id'] == request_id, result
            assert 'error' not in result, result
            return result['result']

        def call(name, args=None):
            result = rpc('tools/call', {'name': name, 'arguments': args or {}})
            assert not result.get('isError'), result
            result = json.loads(result['content'][0]['text'])
            assert result['artifactSha256'] == original_hash
            return result['result']

        try:
            initialized = rpc('initialize', {'protocolVersion': '2024-11-05', 'capabilities': {}, 'clientInfo': {'name': 'repo-smoke', 'version': '1'}})
            assert initialized['serverInfo']['name'] == 'rustclient-recaf'
            child.stdin.write('{"jsonrpc":"2.0","method":"notifications/initialized"}\n')
            child.stdin.flush()
            catalog = rpc('tools/list', {})['tools']
            assert len(catalog) == 6
            assert all(tool['annotations']['readOnlyHint'] for tool in catalog)
            status = call('workspace_status')
            assert status['classCount'] == 735, status
            classes = call('list_classes', {'limit': 5})
            assert classes['total'] == 735 and len(classes['classes']) == 5
            outline = call('class_outline', {'className': 'aa'})
            assert outline['className'] == 'aa'
            page = call('class_outline', {'className': 'client', 'limit': 1, 'fieldsOffset': 1, 'methodsOffset': 1})
            assert len(page['fields']) == len(page['methods']) == 1
            assert page['fieldCount'] > 1 and page['methodCount'] > 1
            method = outline['methods'][0]
            bytecode = call('method_bytecode', {'className': 'aa', 'methodName': method['name'], 'descriptor': method['descriptor']})
            assert bytecode['totalChars'] > 0
            source = call('decompile_class', {'className': 'aa'})
            assert source['view']['totalChars'] > 0
            assert source['bytecodeView'] == 'metadata-clean'
            assert source['viewSha256'] == status['metadataSha256']
            assert source['view']['totalChars'] < 1000
            assert '@Named' not in source['view']['text']
            raw = call('decompile_class', {'className': 'aa', 'bytecodeView': 'original', 'maxChars': 1500})
            assert raw['view']['totalChars'] > 500000
            assert raw['viewSha256'] == original_hash
            search = call('search_strings', {'query': 'RuneScape', 'limit': 3})
            assert search['total'] > 0, search
            missing = rpc('tools/call', {'name': 'class_outline', 'arguments': {'className': 'missing/NoSuchClass'}})
            assert missing['isError']
            mutation = rpc('tools/call', {'name': 'rename_symbol', 'arguments': {}})
            assert mutation['isError']
            assert hashlib.sha256(ORIGINAL.read_bytes()).hexdigest() == original_hash
            print(json.dumps({'status': 'PASS', 'tools': len(catalog), 'classes': status['classCount'],
                'decompiler': source['decompiler'], 'artifactSha256': original_hash,
                'checks': ['initialize', 'initialized notification', 'tools/list', 'status', 'class pagination',
                    'class outline', 'outline pagination', 'method disassembly', 'metadata-clean decompilation',
                    'original decompilation control', 'string search', 'missing-class error',
                    'mutation unavailable', 'original checksum unchanged']}, indent=2))
        finally:
            selector.close()
            child.stdin.close()
            try:
                code = child.wait(timeout=10)
            except subprocess.TimeoutExpired:
                child.kill()
                child.wait()
                raise AssertionError('MCP child did not exit on EOF')
            if code:
                raise AssertionError(f'MCP exited {code}; see {log_path}')


if __name__ == '__main__':
    main()
