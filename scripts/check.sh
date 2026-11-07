#!/usr/bin/env bash
set -euo pipefail
cd -- "$(dirname -- "$0")/.."
case "${1:-fast}" in
  fast)
    cargo fmt --all --check
    cargo quickcheck
    ;;
  full)
    python3 tools/port_matrix.py --check
    python3 tools/docs_index.py --check
    python3 tools/docs_check.py
    cargo fmt --all --check
    cargo check --workspace --all-targets --all-features
    cargo clippy --workspace --all-targets --all-features -- -D warnings
    # Diagnostic bins are checked above; none contain test cases.
    cargo test --workspace
    cargo check -p rustclient-web --target wasm32-unknown-unknown
    cargo check -p rustclient-application --target wasm32-unknown-unknown
    ;;
  research)
    python3 tools/port_audit.py --check
    python3 tools/parity_inventory.py --check
    ;;
  *)
    echo 'Usage: bash scripts/check.sh [fast|full|research]' >&2
    exit 2
    ;;
esac
