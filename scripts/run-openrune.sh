#!/usr/bin/env bash
set -euo pipefail
if [[ $# -lt 1 ]]; then
  echo 'Usage: scripts/run-openrune.sh SERVER_DIRECTORY [extra client arguments]' >&2
  exit 2
fi
server_dir="$(cd -- "$1" && pwd)"
shift
workspace_dir="$(cd -- "$(dirname -- "$0")/.." && pwd)"
cd -- "$workspace_dir"
exec cargo run -p rustclient-native --bin rustclient-native -- \
  --cache-dir "$server_dir/.data/cache/LIVE" \
  --cache-profile openrune-240 \
  --server-profile openrune-240.2 \
  --rsa-public-key "$server_dir/.data/client.key" \
  "$@"
