#!/usr/bin/env python3
"""Serve only the GPU probe and bundled shader sources on loopback (never the repository)."""
import argparse
from http.server import BaseHTTPRequestHandler, HTTPServer
import json
from pathlib import Path

ROOT = Path(__file__).resolve().parents[2]
SHADERS = ROOT / 'crates/renderer/src/shaders'
NAMES = ('Nearest', 'Linear', 'Mitchell', 'CatmullRom', 'Xbr', 'Hybrid')


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--port', type=int, default=8842)
    parser.add_argument('--output', type=Path, default=ROOT / 'target/gpu-browser-probe.json')
    args = parser.parse_args()

    class Handler(BaseHTTPRequestHandler):
        def do_GET(self):
            if self.path in ('/', '/probe'):
                content = Path(__file__).with_name('probe.html').read_bytes()
                kind = 'text/html; charset=utf-8'
            elif self.path == '/shader/ui':
                content = '\n'.join((SHADERS / name).read_text() for name in
                    ('colorblind.wgsl', 'ui_scale.wgsl', 'ui_xbr.wgsl')).encode()
                kind = 'text/plain; charset=utf-8'
            elif self.path == '/probe/depth':
                content = Path(__file__).with_name('depth_probe.js').read_bytes()
                kind = 'text/javascript; charset=utf-8'
            elif self.path == '/shader/world':
                content = '\n'.join((SHADERS / name).read_text() for name in
                    ('colorblind.wgsl', 'world.wgsl')).encode()
                kind = 'text/plain; charset=utf-8'
            elif self.path in {f'/native/{name}.png' for name in NAMES}:
                path = ROOT / 'target/gpu-probe' / self.path.rsplit('/', 1)[1]
                if not path.is_file():
                    self.send_error(404)
                    return
                content = path.read_bytes()
                kind = 'image/png'
            else:
                self.send_error(404)
                return
            self.send_response(200)
            self.send_header('Content-Type', kind)
            self.send_header('Content-Length', str(len(content)))
            self.send_header('Cache-Control', 'no-store')
            self.end_headers()
            self.wfile.write(content)

        def do_POST(self):
            if self.path != '/result' or self.headers.get('Origin') != f'http://127.0.0.1:{args.port}':
                self.send_error(403)
                return
            length = int(self.headers.get('Content-Length', '0'))
            if not 0 < length <= 262144:
                self.send_error(413)
                return
            try:
                value = json.loads(self.rfile.read(length))
            except ValueError:
                self.send_error(400)
                return
            args.output.parent.mkdir(parents=True, exist_ok=True)
            args.output.write_text(json.dumps(value, indent=2) + '\n')
            self.send_response(204)
            self.end_headers()

    server = HTTPServer(('127.0.0.1', args.port), Handler)
    print(f'GPU probe: http://127.0.0.1:{args.port}/probe', flush=True)
    server.serve_forever()


if __name__ == '__main__':
    main()
