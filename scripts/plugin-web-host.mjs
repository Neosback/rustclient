// Node reference host for the browser-facing plugin ABI.
//
// The generated jco module imports these functions from `rustclient:plugin/host`. A real
// browser host would back storage with IndexedDB and run plugins in a worker; this harness
// keeps the same import surface so the cross-target proof exercises the production component.

import { readFileSync, writeFileSync } from 'node:fs';

const path =
  process.env.PLUGIN_SMOKE_STATE ?? '/tmp/rustclient-web-plugin-state.json';
let config = {};
try {
  config = JSON.parse(readFileSync(path, 'utf8'));
} catch {
  config = {};
}

const player = { x: 3224, y: 3218, plane: 0 };
export const state = { config, player, tick: 0n, logs: [] };

export function log(level, message) {
  state.logs.push({ level, message });
}

export function playerPosition() {
  return player;
}

export function tick() {
  return state.tick;
}

export function configGet(key) {
  return Object.hasOwn(config, key) ? config[key] : undefined;
}

export function configSet(key, value) {
  config[key] = value;
  writeFileSync(path, JSON.stringify(config));
}
