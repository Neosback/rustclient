# OSRS 240 / RuneLite porting roadmap

Status: canonical execution order, revised 2026-10-29 at `dbb2b500` after a deob-first review
of the stock client. Full client parity is open.
Contract: [SPEC-0019](../specs/SPEC-0019-port-parity.md). Source navigation:
[CLIENT-LOGIC-MAP](../reference/rs240-port/CLIENT-LOGIC-MAP.md). Domain status:
[PORT-MATRIX](../reference/rs240-port/PORT-MATRIX.md). Findings:
[CURRENT-AUDIT](../reference/rs240-port/CURRENT-AUDIT.md).

## Read and update these owners

| Question | Canonical document/data |
| --- | --- |
| What must match, and what can Rust improve? | SPEC-0019 and [deviations](PARITY-DEVIATIONS.tsv) |
| Where does the stock client do it? | [CLIENT-LOGIC-MAP](../reference/rs240-port/CLIENT-LOGIC-MAP.md), [SYMBOL-LEDGER.tsv](../reference/rs240-port/SYMBOL-LEDGER.tsv), [DEOB-PACKET-SITES.tsv](../reference/rs240-port/DEOB-PACKET-SITES.tsv) |
| What exists and what is missing by domain? | [PORT-MATRIX.tsv](../reference/rs240-port/PORT-MATRIX.tsv), generated Markdown |
| What packets/opcodes/plugins/events are covered? | [PACKET-COVERAGE](../reference/rs240-port/PACKET-COVERAGE.tsv), [CS2-HOST-COVERAGE](../reference/rs240-port/CS2-HOST-COVERAGE.tsv), [RUNELITE-PLUGINS](../reference/builtin-plugins/RUNELITE-PLUGINS.tsv), [RUNELITE-EVENTS](../reference/builtin-plugins/RUNELITE-EVENTS.tsv) |
| What was actually tested at a past milestone? | Its historical checkpoint; dates/commits delimit the claim |
| Which documents are active or historical? | [Documentation index](../README.md) |

## How every phase runs (spec-driven)

1. **Spec gate.** The phase's spec is written or amended and accepted before implementation.
   It cites the stock anchors below, states invariants, failure behavior and acceptance tests.
   Specs that only have a planned number below are written at the start of their phase. New
   phases do not append "continuation" sections to SPEC-0017/0018; those stay as history.
2. **Deob first.** Behavior comes from the 240 rl-suite deob (`references/rl-suite-240`). The
   readable 235 tree is for navigation, and every 235 rule is re-read in 240. RuneLite
   (`references/open-runelite-rev-240`) defines what plugins observe and how GPU features
   behave. It never defines stock rules. Wire layouts come from selected RSProt, and cache
   layouts from the actual cache.
3. **Owners, not mirrors.** Port semantics into the existing crate owners. Java class shapes,
   static state and injection are not copied.
4. **Exit.** The spec's acceptance tests pass. PORT-MATRIX, PACKET-COVERAGE, SYMBOL-LEDGER and
   the generated inventories are updated in the same change, along with a checkpoint that
   records exact commands and NOT-RUN gates.

## Phases in dependency order

Each later phase assumes phase 1. Phases 2-4 and 7 can overlap once phase 1 lands. Phase 0
research can run alongside any phase.

### Phase 0: evidence denominators (ongoing)

- **Spec:** SPEC-0019 (no new spec).
- **Work:** finish the inventories: RuneLite hooks/`Client` API disposition, per-payload stock
  mask branch tables, cache layout variants with actual-cache vectors, and an inventory of
  source tests and cases. Promote the S anchors in CLIENT-LOGIC-MAP through a
  [client-240 naming wave](../reference/client-240/README.md).
- **Anchors:** [hook-anchors-240](../reference/client-240/inputs/hook-anchors-240.tsv),
  `runelite-api/.../Client.java`, `classDF.method3050`, `classAS.method661`.
- **Exit:** `tools/parity_inventory.py --check` and `tools/port_audit.py --check` cover the new
  inventories.

### Phase 1: client cycle and session runtime (foundation)

- **Spec:** [SPEC-0020](../specs/SPEC-0020-client-cycle.md) (draft; accept first).
- **Why first:** every later behavior depends on where in the 20 ms cycle it runs. Today
  packets apply whenever window events deliver them, outbound packets bypass the cycle, and
  the driver lives in `apps/native`.
- **Work:** an application-owned cycle driver used by native and browser; the inbound queue
  with a 100-packet budget and atomic PACKET_GROUP_START; outbound creation and end-of-cycle
  flush; NO_TIMEOUT after 50 idle write cycles; IDLE at 15000; the 750-cycle watchdog and the
  reconnect state; typed LOGOUT_WITHREASON/LOGOUT_TRANSFER outcomes; SEND_PING_REPLY; and a
  reflection-check policy under SPEC-0009.
- **240 anchors:** `client.vmethod231` (doCycle), `client.method2172` (doCycleLoggedIn),
  `client.method2198` (packet loop), `client.method2346` (dispatcher; PACKET_GROUP_START at
  6987, SERVER_TICK_END at 6027, SEND_PING at 6993), `client.method2301` (logged-out cycle),
  `classCN.method2546` (network error), `classJB.method6386` (updateGameState), `GameEngine`.
- **235:** `Client.doCycle`, `doCycleLoggedIn`, `method1357`, `method1434`, `doCycleLoggedOut`.
- **RuneLite:** `GameStateChanged`, `GameTick`, `ClientTick`, and `Callbacks.tick/tickEnd/serverTick`.
- **Matrix rows:** `client-cycle`, `session-control`, `input-queue`, `login` (reconnect).

### Phase 2: valid inbound actor traffic

- **Spec:** new SPEC-0022 "Actor synchronization and effects" (planned; supersedes the actor
  continuations in SPEC-0017).
- **Work:** selected NPC masks BAS `0x200000`, head `0x80000` and body `0x2000000`, decoded in
  order and retained; the rest of the stock NPC and player mask branches; full appearance
  retention (transformed NPC, skull/overheads, slot overrides). Never widen a mask whitelist
  without decoding the payload.
- **240 anchors:** `classAS.method661`, `classDF.method3050/3065`, `classDC.method2984/2987`,
  `NPC.method3510`, `Player.method2661`, `PlayerComposition`.
- **235:** `PlayerUpdateManager.updatePlayers`, `AttackOption.updateNpcs`, `Player.read`.
- **Write-ups:** clusters [04](../reference/client-240/clusters/04-player-info.md),
  [05](../reference/client-240/clusters/05-npc-info.md),
  [12](../reference/client-240/clusters/12-player-appearance.md),
  [PLAYER-EXTENDED-STATE](../reference/rs240-port/PLAYER-EXTENDED-STATE.md),
  [PLAYER-APPEARANCE](../reference/rs240-port/PLAYER-APPEARANCE.md).
- **Matrix rows:** `npc-masks`, `player-masks`, `appearance`, `npc-defs`.

### Phase 3: map lifecycle and world views

- **Spec:** new SPEC-0023 "Map lifecycle, instances and world views" (planned; extends SPEC-0015/0016 scene contracts).
- **Work:** REBUILD_REGION_V2 instances with chunk rotation and collision; normal travel and
  loading state 25; teardown and asset cancellation; world views and world entities
  (REBUILD_WORLDENTITY_V4, WORLDENTITY_INFO_V7, SET_ACTIVE_WORLD_V2) with 240 root id 0.
- **240 anchors:** `classFK.method4840` (loadRegion), `rl4.method10135` (PreMapLoad/loadScene),
  `client.method2174/2294` (world view load and swap), `classCL.method1620/1622`,
  `client.method2273`, `WorldView`, `WorldEntity`, `CollisionData`.
- **235:** `class383.loadRegion`.
- **RuneLite:** `WorldView`, `InstanceTemplates`, `PreMapLoad`, `WorldViewLoaded/Unloaded`, and
  `DrawCallbacks.loadScene/swapScene`.
- **Write-ups:** clusters [06](../reference/client-240/clusters/06-world-region.md),
  [18](../reference/client-240/clusters/18-world-views.md).
- **Matrix rows:** `instances`, `worldviews`, `normal-maps`.

### Phase 4: zone updates, dynamic objects, ground items, world effects

- **Spec:** new SPEC-0024 "Zone updates and world objects" (planned).
- **Work:** every UPDATE_ZONE_* enclosed payload in order; LOC_ADD_CHANGE/LOC_DEL/LOC_MERGE with
  temporary-spawn lifetime; OBJ_* ground items (count, visibility, customization, enabled ops);
  MAP_ANIM and PROJANIM graphics objects and projectiles; collision and minimap invalidation.
  All of this becomes GameState-owned, extending the existing scenery owner.
- **240 anchors:** dispatcher sites in DEOB-PACKET-SITES (UPDATE_ZONE_*, LOC_*, OBJ_*, MAP_ANIM*,
  PROJANIM_SPECIFIC_V4), `Scene.method4424/4449/4463/4465/4470/4473/4495/4504`,
  `WorldView.method3791`, `TileItem.method3423`, `client.method1736`, `Projectile.method1228`,
  `GraphicsObject.method1105`.
- **235:** `class150.addPendingSpawnToScene`, `class157.updateItemPile`,
  `Widget.addTileItemToGroundItems`, `Projectile.setDestination`, `Scene.newGameObject`.
- **RuneLite:** `TileItem`, `ItemSpawned/Despawned/QuantityChanged`, `GameObjectSpawned…`,
  `ProjectileMoved`, `GraphicsObjectCreated`, and the `grounditems`/`objectindicators` plugins.
- **Write-ups:** [WORLD-EFFECTS](../reference/rs240-port/WORLD-EFFECTS.md), cluster
  [16](../reference/client-240/clusters/16-zone-updates.md).
- **Matrix rows:** `ground-items`, `dynamic-scenery`, `world-effects`, `objects`, `minimap` (dots).

### Phase 5: input, picking, menus and every outbound action

- **Spec:** [SPEC-0021](../specs/SPEC-0021-input-menus.md) (draft; accept first).
- **Work:** move gameplay input semantics out of `apps/native` into `crates/input` and
  `application`. Build the stock menu model: construction order, sort, the 2000 deprioritization
  and left-click rule. Add item/spell selection modes, every menu-action packet, RESUME_P_* dialogs,
  IF_BUTTOND drag, player options, hidden ops and hint arrows, F-keys and shortcut arbitration.
  Emit the menu plugin events.
- **240 anchors:** `Menu.method12757/12755`, `classEY.method4149`, `client.method1651/2307/2344`,
  `classQP.method9746`, `Scene.method4364`, `classTZ`, `classTI`, `classTC`,
  [menu-actions-240](../reference/client-240/inputs/menu-actions-240.tsv).
- **235:** `Menu.insertMenuItem`, `Client.menu`, `Client.openMenu`, `class101.menuAction`,
  `class62.addSceneMenuOptions`, `class40.addNpcToMenu`, `AsyncRestClient.addPlayerToMenu`,
  `HealthBarDefinition.resumePauseWidget`.
- **RuneLite:** `MenuAction` (75 values), `MenuEntry`, and the `menuentryswapper`,
  `mousehighlight` and `keyremapping` plugins.
- **Matrix rows:** `shared-input`, `menus`, `menu-paint`, `outbound-actions`, `widget-input`,
  `server-interaction-state`.

### Phase 6: actor effects and facing

- **Spec:** SPEC-0022 (from phase 2).
- **Work:** decode SPOTANIM, HITSPLAT and HEALTHBAR definitions. Add GameState slots for spot
  animations, hitsplats and health bars with stock lifetimes and expiry (SPEC-0020 S7). Port
  timed tint/transparency/model overrides, freeze, rich target facing and interaction slowdown,
  and the remaining action/idle lifecycle. Then the scene/game-ui projections and plugin events.
- **240 anchors:** `Actor.method3277/3278/3279/3283/3286/3290`, `ActorSpotAnim`, `Hitsplat`,
  `HealthBarConfig.method9419`, `classKN.method6539`, `Actor.method3284/3289/3291`,
  `client.method2175`.
- **235:** `Actor.addHitSplat`, `Actor.addHealthBar`, `Actor.spotAnimations`,
  `class85.updateActorSequence`.
- **Write-ups:** [ACTOR-RUNTIME](../reference/rs240-port/ACTOR-RUNTIME.md),
  [SEQUENCE-SCHEDULER](../reference/rs240-port/SEQUENCE-SCHEDULER.md),
  [FACING-TURNING](../reference/rs240-port/FACING-TURNING.md), clusters 07-11.
- **RuneLite:** `HitsplatApplied`, `GraphicChanged`, `InteractingChanged`, `ActorDeath`, and the
  `opponentinfo`, `statusbars` and `idlenotifier` plugins.
- **Matrix rows:** `actor-effects`, `actor-overrides`, `facing`, `turns`, `action-legacy`,
  `action-cached`, `cache-families` (actor groups).

### Phase 7: interfaces and client-script host

- **Spec:** new SPEC-0025 "Interface and client-script host" (planned; consolidates the
  SPEC-0018 widget and script continuations).
- **Work:** server IF_* mutations; model, NPC-head and player-head widgets; the 185
  named-unhandled and 318 unnamed CS2 opcodes (CS2-HOST-COVERAGE); stock script event queues
  and their order (SPEC-0020 S11); DB long branches; close/reset/unload order; varc
  definitions and persistence.
- **240 anchors:** `classPQ.method9202/9203/9206`, `classGP.method5585/5625`,
  `client.method2371/2381`, the `classBB` dispatcher ([OPCODES](../reference/client-240/OPCODES.md)),
  `classDS.method3606/3607`, IF_* dispatcher sites.
- **235:** `PcmPlayer.updateRootInterface`, `class40.drawInterface`, `class150.drawWidgets`,
  `class141.runScriptEvent`, `LoginType.runScript`, `class376.invalidateWidget`.
- **RuneLite:** `Widget`, `ScriptID`, `ScriptPreFired/PostFired`, `VarClientInt/StrChanged`, and
  the `devtools`, `bank` and `banktags` plugins.
- **Matrix rows:** `widgets`, `widget-mutations`, `scripts`, `db`, `fonts`, `var-domains` (varc).

### Phase 8: variables, chat, social and Grand Exchange

- **Spec:** new SPEC-0026 "Variables, chat, social and trading state" (planned).
- **Work:** VarGroups and clan vars; private, friends-chat and clan chat; friend and ignore
  lists; clan settings and channels; stock market and trading post; chat filter settings
  round-trip.
- **240 anchors:** `client.method2241`, `classFM.method4865`, `client.addChatMessage`,
  `MessageNode`, `ChatLineBuffer`, `FriendContainer`, `NameableContainer`, `classBW.method1339/1340`,
  `FriendsChatManager`, `ClanChannel.method6235/6236`, `ClanSettings`, `client.method2076/2365`,
  `GrandExchangeOffer`, `client.method2288`.
- **235:** `class222.addChatMessage`, `class356.addGameMessage`, `FriendSystem`.
- **RuneLite:** `friendlist`, `friendnotes`, `chatchannel`, `chatfilter`, `grandexchange`, and the
  `ChatMessageType` enum.
- **Matrix rows:** `public-chat`, `private-clan-chat`, `var-domains`, `stockmarket`.

### Phase 9: audio

- **Spec:** new SPEC-0027 "Audio" (planned), plus a SPEC-0003 audio capability amendment.
- **Work:** decode sound effect, music, jingle, sample and patch indices; synth sound effects;
  area and ambient sounds; MIDI music and jingles; volume preferences; the queued-sound stage in
  the cycle; native and browser audio adapters.
- **240 anchors:** `classSM.method10549`, `AmbientSoundEffect`, `WorldView.method3795`,
  `MidiRequest`, `ClientPreferences.method2836/2837/2839`, `client.method2172` S7.
- **235:** `class27.queueSoundEffect`, `KitDefinition.playSong`, and the sound loop in
  `Client.doCycleLoggedIn`.
- **RuneLite:** `SoundEffectPlayed`, `AreaSoundEffectPlayed`, `VolumeChanged`, and the `music` plugin.
- **Matrix rows:** `audio`, `cache-families` (audio indices).

### Phase 10: camera, world map, preferences and world list

- **Spec:** amend SPEC-0007 (camera) and write new SPEC-0028 "Preferences, world map and world
  list" (planned).
- **Work:** CAM_* scripted camera packets and the camera stage; world map completion (hover,
  menus, flash, scale, state ownership); the stock preferences file (ClientPreferences) and its
  effects; world list and world hopping.
- **240 anchors:** `classHA.method6018`, `classMH.method7615`, CAM_* dispatcher sites,
  `RenderOverview`, `WorldMapRenderer`, `WorldMapData`, `WorldMapIcon`, `ClientPreferences`,
  `World.method2517`, `client.method1946`.
- **235:** `Player.savePreferences`, `WorldMap`.
- **RuneLite:** `worldmap`, `worldhopper`, `defaultworld` and `camera` plugins.
- **Matrix rows:** `camera`, `world-map`, `persistence`, `world-list`, `minimap`.

### Phase 11: classic rendering parity

- **Spec:** SPEC-0007/0016/0017, with ADR-0004 for the manual acceptance pass.
- **Work:** close the per-setting GPU and core obligations, depth/priority/alpha, materials,
  fog, render modes and widget model rendering. Then run the one manual reference comparison.
- **240 anchors:** `Scene.method4508/4511/4474`, `Model`, `ModelData`, `Texture`,
  `IntProjection`/`FloatProjection`, and the `DrawCallbacks` sites.
- **RuneLite:** the `gpu` plugin and its shaders ([GPU-AUDIT](../reference/builtin-plugins/GPU-AUDIT.md)).
- **Matrix rows:** `gpu`, `depth`, `materials`, `roofs`, `canvas`.

### Phase 12: RuneLite feature layer

- **Spec:** SPEC-0004/0011 and ADR-0006, plus the plugin API version bump the new events need.
- **Work:** emit plugin events from the stock owners ported above. Decide the dispositions in
  RUNELITE-PLUGINS (external services, fork-only). Port applicable plugins in order of their
  blocking rows, and finish the SDK and the browser plugin runtime.
- **Matrix rows:** `plugin-api`, `first-party`, `required-plugins`.

### Phase 13: browser, packaging and final acceptance

- **Spec:** SPEC-0003, plus the browser sections of each phase spec.
- **Work:** the browser cache, transport, storage and audio adapters on the shared cycle driver
  and input layer; packaging; the full acceptance run on both targets.
- **Matrix rows:** `browser`, `sidebar`, and all remaining rows.

## Where Rust may do better (selected, limited)

These are the only standing permissions to differ in mechanism. Each must keep the same
observable result, and anything that changes timing, order, bytes or visuals needs a
[deviation](PARITY-DEVIATIONS.tsv) entry with evidence:

- Decoding and framing off the cycle thread, as long as application happens at SPEC-0020 S4.
- Bounded queues and explicit errors in place of unbounded Java collections and silent drops.
- Typed state, replay and fingerprints in place of static mutable fields.
- Parallel asset decoding and caching that never changes which asset is used.
- A GPU renderer in place of the software rasterizer (non-goal per SPEC-0019), held to the
  classic visual acceptance.
- Ray picking only after it is shown to produce the stock candidate set and order (SPEC-0021).

## Completion and document maintenance

An implemented subset can be retained as such without declaring its domain done. Missing
branches, unresolved source behavior, absent consumers, unaccounted source cases and NOT-RUN
platform/visual gates all remain open. Historical validation is never carried forward to a new
worktree. Validate the documentation tools after edits; their success proves schema, path and
provenance consistency only.
