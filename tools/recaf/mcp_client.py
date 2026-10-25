"""Small local MCP client for repeatable Recaf research scripts."""
import json
import os
from pathlib import Path
import selectors
import subprocess
import sys

ROOT = Path(__file__).resolve().parents[2]


class RecafClient:
    def __init__(self, log, input_view='original'):
        self.log = log.open('w')
        self.child = subprocess.Popen([sys.executable, str(ROOT / 'tools/recaf/recaf.py')],
            cwd=ROOT, env={**os.environ, 'CLIENT240_RECAF_INPUT_VIEW': input_view},
            stdin=subprocess.PIPE, stdout=subprocess.PIPE, stderr=self.log, text=True, bufsize=1)
        self.selector = selectors.DefaultSelector()
        self.selector.register(self.child.stdout, selectors.EVENT_READ)
        self.request_id = 0
        self.rpc('initialize', {'protocolVersion': '2024-11-05', 'capabilities': {},
                               'clientInfo': {'name': 'quality-comparison', 'version': '1'}})
        self.child.stdin.write('{"jsonrpc":"2.0","method":"notifications/initialized"}\n')
        self.child.stdin.flush()

    def rpc(self, method, params):
        self.request_id += 1
        self.child.stdin.write(json.dumps({'jsonrpc': '2.0', 'id': self.request_id,
                                          'method': method, 'params': params}) + '\n')
        self.child.stdin.flush()
        if not self.selector.select(timeout=65):
            raise TimeoutError(f'Recaf MCP timeout: {method}')
        line = self.child.stdout.readline()
        if not line:
            raise RuntimeError(f'Recaf MCP exited {self.child.poll()}')
        reply = json.loads(line)
        if reply.get('id') != self.request_id or 'error' in reply:
            raise RuntimeError(str(reply))
        return reply['result']

    def call(self, name, args=None):
        reply = self.rpc('tools/call', {'name': name, 'arguments': args or {}})
        if reply.get('isError'):
            raise RuntimeError(reply['content'][0]['text'])
        return json.loads(reply['content'][0]['text'])

    def source(self, class_name, decompiler, bytecode_view='metadata-clean'):
        args = {'className': class_name, 'decompiler': decompiler,
                'bytecodeView': bytecode_view, 'maxChars': 100000}
        parts = []
        while True:
            reply = self.call('decompile_class', args)
            result = reply['result']
            page = result['view']
            if page['offset'] != args.get('offset', 0):
                raise ValueError('noncontiguous source page')
            parts.append(page['text'])
            if page['nextOffset'] >= page['totalChars']:
                return ''.join(parts), {key: value for key, value in reply.items() if key != 'result'}, result
            args['offset'] = page['nextOffset']

    def close(self):
        self.selector.close()
        self.child.stdin.close()
        try:
            code = self.child.wait(timeout=10)
        except subprocess.TimeoutExpired:
            self.child.kill()
            self.child.wait()
            raise RuntimeError('Recaf MCP did not shut down on EOF')
        finally:
            self.log.close()
        if code:
            raise RuntimeError(f'Recaf MCP exit {code}')

    def __enter__(self):
        return self

    def __exit__(self, *_):
        self.close()
