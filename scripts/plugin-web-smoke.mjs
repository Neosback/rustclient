// Cross-host smoke: drives the same component that runs under Wasmtime through jco's JS
// bindings. `PLUGIN_SMOKE_EXPECT` selects the fresh or persisted-config run.

import { guest } from './hello_overlay.js';
import { state } from './host.mjs';

const expected = process.env.PLUGIN_SMOKE_EXPECT ?? 'fresh';
guest.init();
guest.onEvent([{ tag: 'started' }]);
for (let tick = 1n; tick <= 120n; tick += 1n) {
  state.tick = tick;
  guest.onEvent([{ tag: 'tick', val: tick }]);
}
guest.onEvent([{ tag: 'stopped' }]);
const lines = guest.overlay();

const text = 'hello-overlay · tick 100 · 3224,3218 plane 0';
if (lines.length !== 1 || lines[0].text !== text) {
  throw new Error(`unexpected overlay: ${JSON.stringify(lines)}`);
}
const [line] = lines;
if (line.x !== 8 || line.y !== 8 || line.argb !== 0xffffff00) {
  throw new Error(`unexpected overlay style: ${JSON.stringify(line)}`);
}
if (state.config.last_tick !== '100') {
  throw new Error(`unexpected config: ${JSON.stringify(state.config)}`);
}
const restored = expected === 'restored';
const ready = `hello-overlay ready (last persisted tick ${restored ? 100 : 0})`;
for (const message of [ready, 'session started', 'session stopped']) {
  if (!state.logs.some((log) => log.message === message)) {
    throw new Error(`missing log ${JSON.stringify(message)}: ${JSON.stringify(state.logs)}`);
  }
}

console.log(`web plugin ${expected} run passed: ${line.text}`);
