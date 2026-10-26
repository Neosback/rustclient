# Current correctness and port audit

Checkpoint: 2026-10-26; inspected main through `7db36d6f`, plus the sidebar change in this checkpoint.
This is a domain-level source/code audit. It does not certify every method or repeat every historical test.

The [rebuilt matrix](PORT-MATRIX.md) separates implementation, verification boundaries, owners and actual
remaining obligations. Its [TSV](PORT-MATRIX.tsv) is canonical; `tools/port_matrix.py --check` checks paths,
status vocabulary and the generated Markdown. It cannot prove behavior. The existing
[method ledger](SYMBOL-LEDGER.tsv) and `tools/port_audit.py --check` retain exact 240 symbol evidence.
No percentage is inferred from declarations, files, plugin names or green workspace builds.

## Findings that need action first

1. **Game canvas and shell:** UI vertices previously used the reduced game width with a full-window
   raster viewport, hiding HUD content and shifting clicks. That draw-extent correction is now in
   `renderer::ui`. Asynchronous shell-driven window resizes have their own pending-size handling.
   Fixed, resizable classic/modern, HiDPI and stretched-mode live acceptance must still be repeated.
   The user's latest layout requirement overrides stock collapsed-rail behavior: the entire rail and
   panel disappear together, reserve zero width and do no retained DOM/painting work while hidden.
2. **Movement responsiveness:** the old worker waited up to 200 ms for inbound data before sending
   queued actions. It now polls available socket input and waits on the action channel, which clicks
   wake immediately. The native contract is covered; event-to-send-to-render latency has not been
   measured against the running server. Server tick delay is not client input-queue delay.
3. **Actor turns/poses:** ordinary movement heading now derives from the oldest queued target
   (`Actor.method3208`), not the newest network endpoint. Connected movement poses sample committed
   translation rather than running a second positional simulation. However full facing targets,
   apply policy, ordinary current-orientation mutation, NPC cache turn rates, freeze handling and
   turning slowdown are still incomplete. Keep position authority in GameState; do not add a visual
   collision clamp or speculative path reroute.
4. **Action lifetime:** legacy scheduling includes restart/priority/gates and frame-boundary behavior.
   `game-state::sequence_playback` still uses aggregate-cycle advancement for cached-model actions;
   their authoritative completion/loop lifetime is unfinished. Decode/pose success does not close it.
5. **Recognized actor data is still discarded:** spot slots, hits/headbars, tint/transparency,
   customization and rich facing need typed retention and actual consumers. See
   [PLAYER-EXTENDED-STATE.md](PLAYER-EXTENDED-STATE.md) and [WORLD-EFFECTS.md](WORLD-EFFECTS.md).
6. **Menus are a subset:** nearest-hit NPC/location actions and widget operations are useful, but
   stacked actors, ground items, players, item/spell targeting, conditional operations/sub-operations,
   attack priority, tall menus and submenus are not complete. Menu paint fixes do not complete entry
   construction or outbound action coverage.
7. **Chat:** history/overheads and outgoing standard color/effect/pattern prefixes are implemented.
   Inline crowns now use the graphics-default sprite group, including full canvas advance and trim
   offset. The live cache selects group 423 with 60 frames. Full social filtering, private/clan channels,
   account-crown visuals and every text macro remain open.
8. **World map and nested worlds:** the blank world-map window is still an implementation gap.
   Sailing/world-entity machinery is already present in 235, but RustClient has no complete semantic
   world-view registry, active-view routing or V7-info/V4-rebuild path. Working normal map loading
   proves neither feature.

## Reference policy

- Exact 240 client control flow, selected OpenRune RSProt 240 and live cache resolve compatibility.
- The readable vendored `references/runelite-melxin-235` is a research base, with matching source
  symbols checked against 240 before claiming behavior parity. The [235→240 change inventory](../REVISION-235-TO-240.md)
  is useful but explicitly not a complete client diff. **An unlisted change is not proof of equality.**
- `/Users/tylercovalt/Documents/tsps-main/client` is a supporting TypeScript implementation supplied as
  revision 237. We have not independently established that every module is an unchanged stock 237 port.
  Its tests/custom behavior are examples to inspect, not 240 authority.
- Reference sources are read-only. No Java/TypeScript architecture is copied mechanically into the
  GameState/adapter/projection ownership model.

## What the TypeScript reference contributes, and what must not carry across

Reviewed files are hash-bound in [TSPS-237-EVIDENCE.json](TSPS-237-EVIDENCE.json). Recheck a supplied
working tree using `python3 tools/port_matrix.py --check --tsps-root /path/to/tsps-main/client`.

| Area / supporting files | Useful structure | Compatibility restriction |
| --- | --- | --- |
| `runelite/client/ui/ClientToolbar.ts`, `game/sidebar/Sidebar*.tsx`, `SidebarStore.ts` | Navigation registration/removal, selection, adjacent rail/panel composition, config panel separation | Only plugins with actual tool panels belong on the tool rail. All configuration entries are not navigation buttons. User requires no rail in the closed state. |
| `game/movement/NpcClientTick.ts` | Small movement/turn helpers and explicit tests | `computeMovementStep` normalizes the delta by Euclidean distance. The stock actor scheduler independently steps each axis. Its 180-degree turn helper uses `>= 1024`, whereas inspected 240 smooth-turn direction uses `> 1024`. Do not copy these helpers as stock parity. Trace callers before judging the entire TS client. |
| `game/worldview/WorldViewManager.ts`, `WorldView.ts`, `WorldEntity.ts` | Registries, view/entity lifecycle, coordinate snapshots and routing | TS root uses `-1`; exact 240 root is `0`. Info/rebuild packet versions and transforms must come from selected 240 writers/consumers. |
| `game/actor/ActorAnimation.ts` | Explicit frame, frame-tick and loop counters | Comments say OSRS-like. Slot-specific clearing vs restarting, readiness, delay, forced-movement gates and cached-model completion must follow exact 240 controllers. |
| `game/chat/ChatTextMetrics.ts` | Shared markup measurement and icon advances | It looks up a named `mod_icons` group and has fallback widths. Our selected cache graphics defaults choose the sprite group; do not substitute guessed widths or suppress missing-source errors. |
| `game/worldMap/WorldMapController.ts`, `rs/map/WorldMapArchiveRenderer.ts` | Controller/input/repaint separation and archive renderer organization | 238 changed unnamed world-map indices and square/zone group/file metadata. Use 240 cache evidence before implementing our loader. |

Other essential deltas: 237+ long stacks/parameters/model IDs, 240 entity ops and sub-ops,
239+ float/14-bit camera API values (distinct from 2048-unit actor orientation), 240 unencrypted map
storage, modern render modes/second wall-decoration offsets and GPU alpha-priority policy.
Do not import 235/237 packet IDs, masks, transforms or cache opcode tables.

## Plugin and browser honesty

[The first-party ledger](../builtin-plugins/FIRST-PARTY-LEDGER.md) records the actual scope of recently
added modules. Its broad word “ported” must be read with each row's omissions; no row has live visual
acceptance unless explicitly recorded. Menu entry swapper and developer tools are still pending.
The GPU/core ledger is also unfinished. Core window bounds, notifications, fonts, tooltips and
infobox behavior are not completed merely by storing their settings. Sidebar navigation services
are distinct from plugin configuration descriptors.

Browser composition is still a renderer bootstrap. Dioxus Web dependency presence and a Wasm check
do not establish browser panels, login, a playable world, or external-plugin parity.

## Acceptance and ordering

First close canvas/input/shell acceptance and normal walk/run/corner trajectories. Then port rich
facing/rotation and complete action/actor-effect lifecycles; expand menus and channel/social state.
World views need one specified semantic registry plus exact 240 protocol/cache adapters before boats
can render or route input correctly. The world-map asset/controller/runtime is a separate delivery.

Every new semantic field needs reset, removal/index reuse, events/deltas, replay and fingerprint
implications. Reuse existing owners. Continue the required GPU/core work against the per-setting
ledger rather than adding inert controls or declaring all default gameplay plugins complete.

Automated build/test results and desktop observations belong in the implementation checkpoint;
this source audit alone does not create visual acceptance. The user-reported minimap/click defects
remain acceptance obligations until demonstrated in the new executable.


## Tracking gaps in the existing method ledger

The current `tools/port_audit.py --check` report contains 130 symbol rows: 67 have verification
unrecorded, 36 source-reviewed, 25 contract-tested and 2 live-tested; none is visual-accepted.
These are mixed symbol records, not equally sized features or a completion percentage. Historical
“verified” statuses with unrecorded verification must not be promoted into release acceptance.
The tool also reports ledger sources absent from its partial source-manifest pin list. That does
not show modification, but it is an evidence-custody gap to close before stronger parity claims.
The original injected-jar pin and generated per-source hashes remain distinct provenance records.
