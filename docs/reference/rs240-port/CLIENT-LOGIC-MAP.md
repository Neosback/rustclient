# Revision-240 stock client logic map

Status: research navigation, reviewed 2026-10-29 at `dbb2b500`. This map does not set the order of
work; [the roadmap](../../plan/PORTING-ROADMAP.md) does, and [PORT-MATRIX](PORT-MATRIX.md) owns
status. This page answers one question per subsystem: **where does the stock client do this,
and which Rust owner must reproduce it?**

## How to read the anchors

- **240** is the stable rl-suite symbol in `references/rl-suite-240/sources-semantic/`
  (`client.method2172` means `client.java`, method `method2172`). Line numbers are for that
  pinned tree. The original obfuscated name is in the method's `@ObfuscatedName`.
- **235** is the hand-named readable method in
  `references/runelite-melxin-235/runescape-client/src/main/java/`. Use it to navigate; confirm
  the behavior in 240 before porting it (SPEC-0019).
- **RL** is the RuneLite API surface in `references/open-runelite-rev-240/` (`runelite-api` and
  `runelite-client` plugins). It shows what plugins observe. It is never stock behavior on its own.
- Confidence: **T1** comes from a RuneLite hook/event anchor in
  [hook-anchors-240.tsv](../client-240/inputs/hook-anchors-240.tsv) and is deterministic.
  **S** is a static control-flow match against the 235 method found in this review: structure
  and call order agree, but no runtime trace was taken. Promote S anchors through the
  [client-240 naming workflow](../client-240/README.md) before naming them.
- Per-packet read and write sites are generated in [DEOB-PACKET-SITES.tsv](DEOB-PACKET-SITES.tsv).
  Script opcodes are in [CS2-HOST-COVERAGE.tsv](CS2-HOST-COVERAGE.tsv). Menu action to packet
  mapping is in [menu-actions-240.tsv](../client-240/inputs/menu-actions-240.tsv).

## 1. Engine loop and client cycle

The stock client runs a fixed 20 ms logic cycle (`doCycle`) and a separate, variable-rate draw
(`draw`). All game logic, packet handling, script events, menus and outbound packet creation
happen inside the logic cycle in a fixed order. The draw pass builds hover/menu candidates.
[SPEC-0020](../../specs/SPEC-0020-client-cycle.md) specifies the order.

| Stock behavior | 240 | 235 | Conf. | Rust owner today |
| --- | --- | --- | --- | --- |
| Cycle entry: `++cycle`, JS5, archive disk responses, mouse snapshot, game-state dispatch | `client.vmethod231` (1776) | `Client.doCycle` | S | `apps/native/src/main.rs::about_to_wait` (native-only) |
| JS5 request/response pump | `client.method2332` | `Client.doCycleJs5` | S | `crates/protocol/src/openrune_240/js5.rs` (login CRC check only) |
| Game-state switch (0/5/10/11/20/25/30/40/45/50) | `client.field829` (×-44590225) | `Client.gameState` | S | `crates/game-state/src/title.rs`, `connection.rs` |
| `updateGameState` | `classJB.method6386` | `WorldMapSectionType.updateGameState` | S | `crates/game-state/src/connection.rs` |
| Logged-in cycle | `client.method2172` (3231-4230) | `Client.doCycleLoggedIn` | S | split across `apps/native/src/main.rs`, `network.rs`, `crates/application` |
| Logged-out cycle (login, reconnect states 40/45) | `client.method2301` | `Client.doCycleLoggedOut` | S | `crates/application/src/title.rs`; reconnect absent |
| Packet loop: up to 100 packets per cycle, and the whole PACKET_GROUP_START group | `client.method2198` (4777) | `Client.method1357` | S | network worker decodes everything as bytes arrive (`apps/native/src/network.rs`) |
| Server packet dispatcher | `client.method2346` (4884-7409); copies at `method2199/2200/2201/2202` | `Client.method1434` | S | `crates/protocol/src/openrune_240/game.rs::receive` |
| Outbound packet node / flush at end of cycle | `classEF.method3863` / `classMU.method7762(classHS.method6136())` | `ReflectionCheck.getPacketBufferNode` / `packetWriter.flush` | S | encoded and sent immediately by the network worker |
| RuneLite tick / tickEnd / serverTick / frame hooks | `client.method2126` / `method2347` / `method2383` / `method2363` | — | T1 | `crates/plugin-bridge` diff after `AdvanceCycles` |
| Draw entry / logged-in draw | `client.vmethod237` (12987) / `client.method2187` | `Client.draw` / `Client.drawLoggedIn` | S | `apps/native/src/main.rs` redraw + `crates/renderer` |
| Loading/"Connection lost" messages in states 25/40/45 | `classLU.method7199` from `vmethod237` | `HttpMethod.drawLoadingMessage` | S | absent |
| 50 Hz timing, focus, frame callbacks | `GameEngine` (`method11156` focus, `method11153` draw) | `GameEngine` | T1 | `apps/native/src/frame_limit.rs` |

Order inside `client.method2172` (S, relative line in the method):

1. Reboot and logout timers count down (3-8).
2. A network error goes to the error handler `classCN.method2546` (235 `class165.method4149`).
   Otherwise the client adds the Cancel entry if no menu is open (`classEY.method4149`), then
   runs the packet loop `method2198` (27).
3. Reflection-check replies (`REFLECTION_CHECK_REPLY`, 33-41), then `CONNECTION_TELEMETRY` (51).
4. Input telemetry: `EVENT_MOUSE_MOVE` from the mouse recorder (149), `EVENT_MOUSE_SCROLL` (319),
   `EVENT_KEYBOARD` (329), `EVENT_CAMERA_POSITION` (395) and `EVENT_APPLET_FOCUS` (413).
5. World map, health bar and hitsplat expiry, then timers and the queued sound effect list.
6. Connection watchdog: more than 750 cycles without input calls the error handler (reconnect path).
7. Per world view: each player runs `classKN.method6539(view, actor, 1)`, the per-cycle actor
   update (235 `class85.updateActorSequence`, which calls `Actor.method3291` and
   `Actor.method3289`). NPCs follow via `classCZ.method2849`, then world entities and their
   sequences (`classRD.method9980`).
8. Projectiles and graphics objects advance, then `++graphicsCycle` (235 `class287.method6521`;
   not yet located in 240 `method2172`).
9. Key event queue (`classFW.method5060/5073`), then root interface layout (235
   `PcmPlayer.updateRootInterface`). Then the script event queues
   drain in order, `scriptEvents2`, `scriptEvents3`, `scriptEvents` (235 names, confirm in 240).
   After that come widget drag, `menu()` input handling and menu sort (`method2307`,
   PostMenuSort).
10. Scene click to walk: `Scene.method4364` then `MOVE_GAMECLICK` (829-832). Then hover
    invalidation and the camera (`classHA.method6018`, `classMH.method7615`, oculus/follow).
11. Idle: mouse and keyboard both idle for more than 15000 cycles sends `IDLE` (933, 235 sets
    `logoutTimer = 250`). More than 50 cycles since the last write sends `NO_TIMEOUT` (980).
12. Flush the packet writer (990).

Today the Rust cycle is driven from `NativeApp::about_to_wait`. Packets are applied when winit
delivers network events, not at a fixed point in the cycle. There is no per-cycle packet budget
and no packet-group handling. KeepAlive is sent every 5 s, not after 50 idle write cycles. A
15 s receive gap ends the session instead of entering the reconnect state. These are cycle
ordering differences (matrix `client-cycle`).

## 2. Login, session and lifecycle

| Stock behavior | 240 | 235 | Conf. | Rust |
| --- | --- | --- | --- | --- |
| Login screen input/state machine | `classBB.method789` | `class358.loginScreen` | S | `crates/game-ui/src/controller.rs`, `crates/application/src/title.rs` |
| Pending login / response codes | `PendingLogin` | — | T1 class | `crates/protocol/src/openrune_240/login.rs` |
| GameStateChanged | `client.method2276` | `updateGameState` | T1 | `crates/game-state/src/connection.rs` |
| Logout / forced disconnect | — | `class30.logOut`, `class184.forceDisconnect` | 235 | `apps/native/src/network.rs` |
| Reasoned logout, world transfer | `client.method2346` LOGOUT_WITHREASON/LOGOUT_TRANSFER | `Client.method1434` | S | protocol error; transfer discarded (`session-control`) |
| World list | `World`, `client.method1946` WorldChanged, `World.method2517` WorldListLoad | `World` | T1 | absent (`world-list`) |

## 3. Map, regions and world views

| Stock behavior | 240 | 235 | Conf. | Rust |
| --- | --- | --- | --- | --- |
| Normal/instance region load (game state 25) | `classFK.method4840` | `class383.loadRegion` | S | `crates/application/src/scene.rs`, `crates/scene/src/regions.rs` |
| Scene load / PreMapLoad | `rl4.method10135` | — | T1 | `crates/scene` |
| World view load/swap/unload | `client.method2174`, `client.method2294`, `classCL.method1620` | — | T1 | absent (`worldviews`) |
| World entity spawn/despawn | `client.method2273`, `classCL.method1622` | — | T1 | absent |
| Region/world-view write-up | [06-world-region](../client-240/clusters/06-world-region.md), [18-world-views](../client-240/clusters/18-world-views.md) | | | |

## 4. Player and NPC synchronization

| Stock behavior | 240 | 235 | Conf. | Rust |
| --- | --- | --- | --- | --- |
| Player info bit stream and masks | `classDF.method3050` (masks), `classDF.method3065` (despawn), `classDC.method2984/2987` | `PlayerUpdateManager.updatePlayers` | T1/ledger | `crates/protocol/src/openrune_240/player_info.rs`, `player_extended.rs` |
| NPC info and masks | `classAS.method661`; `NPC.method3510` spawn/change/despawn | `AttackOption.updateNpcs` | T1/ledger | `npc_info.rs`, `npc_extended.rs` |
| Appearance | `PlayerComposition`, `Player.method2661` | `Player.read` | T1 | `player_appearance.rs`, `crates/cache/src/osrs240/player_model.rs` |
| Write-ups | [04-player-info](../client-240/clusters/04-player-info.md), [05-npc-info](../client-240/clusters/05-npc-info.md), [12-player-appearance](../client-240/clusters/12-player-appearance.md), [PLAYER-EXTENDED-STATE](PLAYER-EXTENDED-STATE.md) | | | |

## 5. Actor runtime and effects

| Stock behavior | 240 | 235 | Conf. | Rust |
| --- | --- | --- | --- | --- |
| Per-cycle actor update (movement, turning, sequences) | `classKN.method6539`, `Actor.method3284/3289/3291` | `class85.updateActorSequence` | ledger | `crates/game-state/src/actor_*.rs`, `sequence_*.rs` |
| Spot animations | `Actor.method3278/3283`, `ActorSpotAnim` | `Actor.spotAnimations` | T1 | absent state (`actor-effects`) |
| Hitsplats / health bars | `Actor.method3290`, `Actor.method3277`, `Hitsplat`, `HealthBarConfig` | `Actor.addHitSplat`, `addHealthBar` | T1 | absent state; definitions not decoded (`cache-families`) |
| Animation / interacting change | `Actor.method3286`, `client.method2175` | `Actor.sequence`, `targetIndex` | T1 | animation present; interacting absent (`facing`) |
| Overhead text | `Actor.method3279` | `Actor.overheadText` | T1 | `crates/game-state/src/speech.rs` |
| Write-ups | [ACTOR-RUNTIME](ACTOR-RUNTIME.md), [SEQUENCE-SCHEDULER](SEQUENCE-SCHEDULER.md), [FACING-TURNING](FACING-TURNING.md), clusters [07](../client-240/clusters/07-animation-state.md)-[11](../client-240/clusters/11-overhead-overrides.md) | | | |

## 6. World effects and world mutations

| Stock behavior | 240 | 235 | Conf. | Rust |
| --- | --- | --- | --- | --- |
| Projectiles | `Projectile.method1228` | `Projectile.setDestination` | T1 | absent (`world-effects`) |
| Graphics objects | `GraphicsObject.method1105` | `GraphicsObject` | T1 | absent |
| Location spawn/despawn per type | `Scene.method4424/4449/4463/4465/4470/4473/4495/4504` | `Scene.newGameObject` etc. | T1 | `crates/game-state/src/scenery.rs` (static and var morphs only) |
| Pending location spawns (temporary changes) | — | `class150.addPendingSpawnToScene` | 235 | absent (`dynamic-scenery`) |
| Ground items | `WorldView.method3791`, `TileItem.method3423`, `client.method1736` | `Widget.addTileItemToGroundItems`, `class157.updateItemPile` | T1 | absent (`ground-items`) |
| Zone packet bodies | `client.method2346` UPDATE_ZONE_*, LOC_*, OBJ_* | `Client.method1434` | S | `crates/protocol/src/openrune_240/zone.rs` (LOC_ANIM only) |
| Write-ups | [WORLD-EFFECTS](WORLD-EFFECTS.md), [16-zone-updates](../client-240/clusters/16-zone-updates.md) | | | |

## 7. Scene, models and rendering

| Stock behavior | 240 | 235 | Conf. | Rust |
| --- | --- | --- | --- | --- |
| Scene draw / alpha pass | `Scene.method4508`, `Scene.method4511`, `Scene.method4474` | `Scene.draw` | T1 | `crates/scene`, `crates/renderer/src/world*.rs` |
| Entity draw into scene | — | `class108.drawEntities` | 235 | `crates/scene/src/entity_mesh*` |
| Model / ModelData / Texture | `Model`, `ModelData`, `Texture`, `TextureProvider` | same | T1 class | `crates/cache/src/osrs240/models.rs`, `crates/scene`, `crates/renderer` |
| Projection | `IntProjection`, `FloatProjection`, `Projection.method5052/5053` | `Perspective` | T1 | `crates/renderer/src/camera.rs` |
| RuneLite GPU path | `runelite-client/.../plugins/gpu` | — | RL | [GPU-AUDIT](../builtin-plugins/GPU-AUDIT.md) |

## 8. Interfaces and client scripts

| Stock behavior | 240 | 235 | Conf. | Rust |
| --- | --- | --- | --- | --- |
| Open/close sub-interface, drag | `classPQ.method9202/9203/9206` | `openInterface`, `closeInterface` | T1 | `crates/game-state/src/interfaces.rs`, `crates/application/src/scripts/lifecycle.rs` |
| Root layout per cycle | `client.method2172` step 9 | `PcmPlayer.updateRootInterface` | S | `ClientScripts::resize_widgets` |
| Interface draw | `classGP.method5585`, `classGP.method5625` | `class40.drawInterface`, `class150.drawWidgets` | T1/S | `crates/game-ui/src/widgets.rs`, `widget_visuals.rs` |
| Widget invalidation | — | `class376.invalidateWidget` | 235 | `crates/game-ui` |
| Run script / script events | `client.method2371`, `client.method2381`, `ScriptEvent` | `LoginType.runScript`, `class141.runScriptEvent` | T1 | `crates/client-script`, `crates/application/src/scripts` |
| Instruction dispatcher | `classBB` (control-flow flattened; see [OPCODES](../client-240/OPCODES.md)) | `ScriptOpcodes` + handlers | S | 421 of 606 named opcodes dispatched ([CS2-HOST-COVERAGE](CS2-HOST-COVERAGE.tsv)) |
| Pause/dialog resume | — | `HealthBarDefinition.resumePauseWidget` | 235 | absent (`outbound-actions`) |
| Write-ups | [GAMEFRAME-INTERFACES](GAMEFRAME-INTERFACES.md), [LOGIN-REGION-INTERFACES](LOGIN-REGION-INTERFACES.md) | | | |

## 9. Input, picking and menus

[SPEC-0021](../../specs/SPEC-0021-input-menus.md) specifies these.

| Stock behavior | 240 | 235 | Conf. | Rust |
| --- | --- | --- | --- | --- |
| Mouse / key / wheel handlers | `classTZ`, `classTI`, `classTC` | `MouseHandler`, `KeyHandler`, `MouseWheelHandler` | T1 | `apps/native` (winit) |
| Menu entry insert | `Menu.method12757` | `Menu.insertMenuItem` | T1 | `crates/application/src/interaction.rs`, `apps/native/src/interaction.rs` |
| Cancel entry | `classEY.method4149` | `class33.addCancelMenuEntry` | S | `crates/game-ui/src/context_menu.rs` |
| Scene/NPC/player menu options | — | `class62.addSceneMenuOptions`, `class40.addNpcToMenu`, `AsyncRestClient.addPlayerToMenu` | 235 | nearest NPC/location only |
| Open / sort / left-click decision | `client.method1651`, `client.method2307`, `client.method2344` | `Client.openMenu`, `Client.menu` | T1 | partial |
| Menu action to packet | `classQP.method9746` | `class101.menuAction` | T1 | Rust encodes 5 of the 46 rows in [menu-actions-240](../client-240/inputs/menu-actions-240.tsv) (OPNPC1-5), plus OPLOC1-5, which that partial table omits |
| Walk click | `Scene.method4364` then MOVE_GAMECLICK | `Scene.shouldSendWalk` | S | `ClientIntent::Walk` |
| RL menu API | `MenuAction.java` (75 values), `MenuEntry`, `menuentryswapper` | | RL | `crates/plugin-api` menu events never emitted |

## 10. Variables, inventories, stats

| Stock behavior | 240 | 235 | Conf. | Rust |
| --- | --- | --- | --- | --- |
| Varp/varbit change notify; client-setting side effects of a varp | `client.method2241` | `Huffman.changeGameOptions` | T1 | `crates/game-state/src/scenery.rs` variables |
| Varc int/string change | `classDS.method3607/3606` | `VarcInt`/`VarcStr` | T1 | script-local only; not persisted |
| Inventory / stat change, client tick | `classPV.method9369/9370/9368` | — | T1 | `crates/game-state/src/inventory.rs`, `players.rs` |
| Definitions | `VarbitComposition`, `EnumComposition`, `StructComposition`, `ItemComposition.method8748` | | T1 | `crates/cache/src/osrs240` |

## 11. Chat and social

| Stock behavior | 240 | 235 | Conf. | Rust |
| --- | --- | --- | --- | --- |
| Add chat message | `client.addChatMessage`, `classFM.method4865` | `class222.addChatMessage`, `class356.addGameMessage` | T1 | `crates/game-state/src/chat.rs` |
| Chat buffers | `ChatLineBuffer`, `MessageNode` | same | T1 class | `crates/game-state/src/chat.rs` |
| Friends / ignore | `FriendContainer`, `NameableContainer`, `classBW.method1339/1340` | `FriendSystem` | T1 | absent (`private-clan-chat`) |
| Friends chat / clan | `FriendsChatManager`, `ClanChannel.method6235/6236`, `ClanSettings`, `client.method2076/2365` | | T1 | absent |

## 12. Audio

| Stock behavior | 240 | 235 | Conf. | Rust |
| --- | --- | --- | --- | --- |
| Sound effect / area sound play | `classSM.method10549` | `class27.queueSoundEffect` | T1 | bounded script queue only |
| Queued sound processing in the cycle | `client.method2172` step 5 | `Client.doCycleLoggedIn` (soundEffectCount loop) | S | absent |
| Ambient sounds | `AmbientSoundEffect`, `WorldView.method3795` | | T1 | absent |
| Music / jingles | `MidiRequest` | `KitDefinition.playSong` | T1 class | absent; audio indices not read |
| Volume preferences | `ClientPreferences.method2836/2837/2839` | | T1 | absent |

## 13. Camera, world map, preferences, stock market, cache

| Stock behavior | 240 | 235 | Conf. | Rust |
| --- | --- | --- | --- | --- |
| Camera follow/oculus/scripted moves | `client.method2172` step 10; `classHA.method6018`, `classMH.method7615`; CAM_* in `method2346` | `Client.doCycleLoggedIn` camera block | S | `crates/renderer/src/camera.rs`, `apps/native/src/camera_input.rs` |
| World map | `RenderOverview`, `WorldMapRenderer`, `WorldMapData`, `WorldMapRegion`, `WorldMapIcon` | `WorldMap` | T1 class | `crates/application/src/scripts/worldmap.rs`, `crates/scene/src/world_map.rs` |
| Preferences file | `ClientPreferences` (155 untriaged methods) | `Player.savePreferences` | T1 class | `crates/application/src/shell_preferences.rs` (shell only) |
| Grand Exchange | `GrandExchangeOffer`, `client.method2288`, `classOG.method8468` | | T1 | absent (`stockmarket`) |
| Cache archives / JS5 | `IndexDataBase`, `NodeCache`, `client.method2332` | `AbstractArchive`, `doCycleJs5` | T1/S | `crates/cache` (read-only local cache; no JS5 download) |

## 14. RuneLite-only layer

`Callbacks.*` and `DrawCallbacks.*` hooks and the `runelite-api` interfaces are injection, not
stock behavior. They define what plugins can observe ([RUNELITE-EVENTS](../builtin-plugins/RUNELITE-EVENTS.tsv),
[RUNELITE-PLUGINS](../builtin-plugins/RUNELITE-PLUGINS.tsv)). Port the stock behavior first,
then expose it through committed events and snapshots (ADR-0006).

## Open research

- Confirm the three script event queues and their drain order in 240 (step 9). Only the 235
  readable names were traced.
- Name the S anchors above through a client-240 naming wave so that ledgers cite readable names.
- `SOUND_AREA` and `HISCORE_REPLY` have no dispatcher comparison site. Locate their handler
  in bytecode before porting them.
- The stock client creates no `EVENT_MOUSE_CLICK_V1`, `EVENT_NATIVE_MOUSE_MOVE`, `RSEVEN_STATUS`,
  `MEMBERSHIP_PROMOTION_ELIGIBILITY` or `HISCORE_REQUEST` packets in the deob
  (DEOB-PACKET-SITES). Confirm this in bytecode, then classify them as not-needed rather than
  missing.
