#!/usr/bin/env python3
"""Build the shared sidebar harness under target/, with content-stamped browser asset URLs."""
import argparse
import hashlib
import json
from pathlib import Path
import subprocess

ROOT = Path(__file__).resolve().parents[2]
OUTPUT = ROOT / 'target/sidebar-lifecycle/web'


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--skip-build', action='store_true', help='package the existing Cargo Wasm output')
    parser.add_argument('--target-dir', type=Path, default=ROOT / 'target', help='Cargo build cache (default: workspace target)')
    args = parser.parse_args()
    if not args.skip_build:
        subprocess.run(['cargo', 'build', '-p', 'rustclient-web', '--target',
                        'wasm32-unknown-unknown', '--target-dir', str(args.target_dir)], cwd=ROOT, check=True)
    OUTPUT.mkdir(parents=True, exist_ok=True)
    subprocess.run(['wasm-bindgen', '--target', 'web', '--out-dir', str(OUTPUT / 'pkg'),
                    str(args.target_dir / 'wasm32-unknown-unknown/debug/rustclient_web.wasm')],
                   cwd=ROOT, check=True)
    wasm = OUTPUT / 'pkg/rustclient_web_bg.wasm'
    digest = hashlib.sha256(wasm.read_bytes()).hexdigest()
    html = (ROOT / 'apps/web/index.html').read_text()
    assert html.count("'./pkg/rustclient_web.js'") == 1
    assert html.count('await init();') == 1
    html = html.replace("'./pkg/rustclient_web.js'", f"'./pkg/rustclient_web.js?v={digest}'")
    html = html.replace('await init();',
                        "await init({module_or_path: './pkg/rustclient_web_bg.wasm?v=" + digest + "'});")
    (OUTPUT / 'index.html').write_text(html)
    (OUTPUT / 'build.json').write_text(json.dumps({'wasm_sha256': digest}, indent=2) + '\n')
    print('Serve on loopback: python3 -m http.server 64729 --bind 127.0.0.1 --directory ' + str(OUTPUT))
    print('Open http://127.0.0.1:64729/?shell-harness')


if __name__ == '__main__':
    main()
