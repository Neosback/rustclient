# Revision 240 port matrix

Status: active triage. This is a navigation/index document, not a parity claim.

Read `README.md` in this directory first. Exact deep findings belong in domain
research documents and `SYMBOL-LEDGER.tsv`; existing checkpoint audits remain
canonical for behaviors they already cover.

## Priority legend

- **P0**: required for a correct connected world loop.
- **P1**: required for RuneLite-class normal gameplay parity.
- **P2**: important secondary client behavior, tooling or plugin observability.
- **P3**: optional/later feature for the first complete client target.

## Domain inventory

| Priority | Domain | Primary 240 evidence to inspect | RustClient owner | Current state | Research next step |
| --- | --- | --- | --- | --- | --- |
| P0 | Buffer primitives and transformed reads | `PacketBuffer.java`, its buffer superclass/generic helpers, packet handler call sites | `crates/protocol/src/openrune_240/` | partial/verified for implemented families | Produce one canonical table of byte/short/int endian + add/neg/sub + smart/varint + bit-mode primitives and map every decoder to it |
| P0 | ISAAC, packet framing and lengths | `client.java` game packet loop, `PacketBuffer.java`, exact RSProt 240 tables | `crates/protocol` | partial | Trace server packet dispatch order, variable lengths, cipher consumption and fragmented transport behavior |
| P0 | Login/bootstrap/session | `GameEngine.java`, `client.java`, login classes, initial player bit stream | `protocol`, `application`, `game-state::connection` | partial/verified for current OpenRune flow | Catalog every response/state transition, rebuild/reconnect path and region/bootstrap dependency |
| P0 | Player list synchronization | `classDF.java`, `classDC.java`, `Player.java` | `protocol::openrune_240::player_info`, `game-state::players/entities/player_movement` | partial with strong existing audit | Fill all extended masks that are currently only consumed, then finish lifecycle and scheduler cross-effects |
| P0 | NPC list synchronization | `classAS.java`, `NPC.java`, `NPCComposition.java`, related update helpers | `protocol::openrune_240::npc_info`, `game-state::npcs/entities` | partial with strong existing audit | Method-map every remaining NPC mask and lifecycle branch, especially cosmetic/effect state |
| P0 | Actor path scheduler | `Actor.java`, `classDC.java`, `classGT.java` | `game-state::entities`, `player_movement`, `npcs`; scene projection consumes state | partial | Implement the bounded oldest-first path queue, assignment backlog, per-cycle movement gates, catch-up and traversal-speed scheduling |
| P0 | Action/sequence scheduler | `classCU.java`, `Actor.java`, `Animation.java` | `cache::osrs240::sequence_policy`, `application::SequencePolicyResolver`, `model::ActorSequenceSlot`, `game-state::sequence_scheduler`, `scene` pose preparation | partial: revision-240 policy is resolved from cache before native GameState application; priority and same-ID restart semantics are authoritative and fingerprinted | Replace the temporary zero backlog with the actor path queue, then implement movement gating plus finite completion/loop semantics |
| P0 | Facing/turning/targets | `Actor.java`, `Player.java`, `NPC.java`, entity-target lookup helpers | `game-state::entities`; `scene` projection | partial | Trace target indexes, explicit face angles/tiles, turn rate, turn-left/right/walk selection and target disappearance |
| P0 | Player appearance | `Player.java`, `PlayerComposition.java`, update-mask handler | `game-state::players`, `cache`, `scene` | partial | Decode/retain complete appearance state, transforms/customization, sequence defaults and model-cache invalidation rules |
| P0 | NPC definitions and morphs | `NPCComposition.java`, `NPC.java` | `cache`, `scene`, `game-state` vars | partial/verified for important morph behavior | Finish override/customization fields, model dependencies and morph edge cases used by 240 cache |
| P0 | Region/base/world-view state | `WorldView.java`, `client.java`, region rebuild handlers | `game-state`, `scene`, `protocol` profile | partial | Document normal vs instanced rebuild, base shifts, plane rules, entity rebasing, dynamic regions and teardown order |
| P0 | Terrain and collision | `CollisionData.java`, region/terrain decode helpers | `cache`, `scene`, possibly semantic collision/query owner | partial | Trace tile flags, bridge/plane rules, clipping bit mutations, object clipping and navigation-facing queries |
| P0 | Scene graph and location placement | `Scene.java`, `Tile.java`, `GameObject.java`, `WallObject.java`, `DecorativeObject.java`, `GroundObject.java`, `DynamicObject.java` | `scene`, `game-state::scenery` | partial | Catalog placement shapes/orientations, tags/flags, replacement/removal order, bridges/roofs and dynamic-object lifecycle |
| P0 | JS5/cache containers | `IndexDataBase.java`, archive/container helpers, exact cache | `crates/cache` and protocol JS5 validation | partial | Build format matrix for index metadata, compression, groups/files, XTEA where required and revision-specific tolerances |
| P0 | Definition decoding | `Animation`, `ObjectComposition`, `ItemComposition`, `NPCComposition`, `VarbitComposition`, `EnumComposition`, `StructComposition`, spot/identity-kit/underlay/overlay helpers | `crates/cache` | partial | Inventory every definition family referenced by runtime, exact opcodes/defaults and which fields are semantically required |
| P0 | Model and ModelData | `Model.java`, `ModelData.java` | `cache`, `scene`, `renderer` | partial | Trace decode variants, merge/dedup, recolor/retexture, resize/translate/rotate/mirror, contour, normals and bounds |
| P0 | Legacy animation frames/skeletons | `Animation.java` plus generic sequence/frame/skeleton classes | `cache`, `scene` | partial | Map frame timing, loop rules, interleave masks, alpha transforms, bone transforms and model-copy rules |
| P0 | Cached-model/skeletal animation | cached-animation generic classes referenced by `Animation`/models | `cache`, `scene` | partial | Complete channel semantics, blending/masks and parity checks beyond successful decode/preparation |
| P0 | Rendering order and projection | `Scene.java`, `Model.java`, `Projection`/`IntProjection`/`FloatProjection`, rasterizer classes | `scene`, `renderer` | partial | Document visibility, occlusion, near clipping, face culling, depth behavior, priority buckets, alpha and texture/material ordering |
| P0 | Camera | `client.java`, camera helpers, projection classes | `game-state`/application camera semantics + `renderer` | partial | Trace camera modes, pitch/yaw clamps, focal point, shake, roofs, oculus/free camera and cutscene/script controls |
| P1 | Spot animations | `ActorSpotAnim.java`, actor update masks, graphics/spot definitions | `game-state`, `cache`, `scene` | incomplete semantics | Map actor slots, start cycles, height, replacement/removal and pose composition |
| P1 | Graphics objects | `GraphicsObject.java`, zone packet handlers | `game-state` or scene-runtime semantic owner + `scene` | partial | Trace creation, lifetime, sequence advancement, coordinates/height and removal |
| P1 | Projectiles | `Projectile.java`, zone packet handlers | semantic projectile owner + `scene` | incomplete | Trace source/target coordinates, target tracking, slope/height, cycle math, animation and removal |
| P1 | Hitsplats/headbars | `Actor.java`, `Hitsplat.java`, `HealthBarConfig.java`, player/NPC masks | `game-state`, `cache`, `game-ui`/renderer projection | incomplete | Specify slot replacement/priority, delays, expiry and healthbar ordering/interpolation |
| P1 | Overhead text/chat bubbles | actor fields and player/NPC update handlers | `game-state::chat/entities`, projection/UI | incomplete | Separate public chat decoding from actor overhead state, filters, color/effects and expiry |
| P1 | Actor tint/transparency/custom overrides | player/NPC masks, `ColorTextureOverride`, model application sites | `game-state`, `scene` | incomplete | Map timing/field meaning and model application order |
| P1 | Widgets/interfaces | `Widget.java`, `WidgetNode.java`, client interface handlers | `game-ui`, `game-state::interfaces`, `protocol::interfaces` | early/partial | Build widget field/decode/listener/layout/render/input matrix and interface parent lifecycle |
| P1 | CS2/client scripts | `Script.java`, `ScriptEvent.java`, `ScriptEventBuilder.java`, interpreter generic classes | `client-script`, `game-ui`, semantic query/command adapters | incomplete | Identify interpreter, stacks, locals, arrays, opcodes, event queue ordering, widget opcodes and var/inventory queries |
| P1 | Menus and interaction building | `Menu.java`, `client.java`, scene/widget menu builders | `game-ui`, application intents | partial | Trace entry ordering, opcode normalization, shift-click, walk/entity/loc/item/widget entries and cancel behavior |
| P1 | Input, picking and drag | `GameEngine.java`, `client.java`, widget/scene input helpers | `input`, `game-ui`, `scene`, application | partial | Map mouse/keyboard queues, viewport picking, click cross, drag thresholds, press/hold/release and focus rules |
| P1 | Outbound actions | menu dispatch/client packet writers + exact RSProt client packets | `protocol::actions`, application intents | partial | Cover all gameplay actions required by menus/widgets and prove field order/transforms against selected RSProt 240 |
| P1 | Item containers/inventory | `ItemContainer.java`, widget/inventory handlers | `game-state::inventory`, `protocol`, `game-ui` | partial | Trace partial/full container updates, widget binding, quantity semantics, invalidation and script notifications |
| P1 | Varps/varbits/varcs | `VarbitComposition.java`, client var arrays/change queues, script opcodes | `game-state` vars + `cache` definitions + `client-script` | partial | Catalog reset/update ordering, varbit extraction widths, changed-ring semantics and UI/script notifications |
| P1 | Chat | `ChatLineBuffer.java`, `MessageNode.java`, Huffman/text helpers, packet handlers | `protocol::chat/huffman`, `game-state::chat`, `game-ui` | partial | Finish message types, private/public/system paths, dedupe IDs, ignore filtering, formatting and script/plugin events |
| P1 | Social/friends/ignore/friends chat | `FriendContainer`, `Friend`, `Ignore`, `FriendsChatManager`, `FriendsChatMember`, `Nameable*` | semantic social owner, protocol/UI/plugins | largely unimplemented | Document update structures, sorting, world/rank flags, login updates and UI/script hooks |
| P1 | Clan settings/channels | `ClanSettings.java`, `ClanChannel.java`, `ClanChannelMember.java` | semantic clan owner + protocol/UI | largely unimplemented | Trace full/delta updates, member permissions, sorted views and script access |
| P1 | Gameframe/minimap | `Widget`, sprite/mask helpers, scene/minimap draw paths | `game-ui`, `renderer` | partial | Link existing gameframe audit to minimap projection, icons, hint arrows, destination and clipping masks |
| P1 | Audio and positional sound | `AmbientSoundEffect.java` plus PCM/MIDI/sound-effect generic classes | `audio`, cache, scene/application | early | Map mixer timing, effect queue, positional attenuation, object ambient transforms, music/jingle state and preferences |
| P2 | Preferences | `ClientPreferences.java` | application/settings/platform persistence | partial | Separate stock gameplay-affecting preferences from RustClient shell settings; map defaults and effects |
| P2 | World map | `WorldMapRegion`, `WorldMapRenderer`, `WorldMapData`, `WorldMapIcon`, `RenderOverview` | future world-map/game-ui owner | incomplete | Catalog cache inputs, coordinate transforms, icon filtering, flashing, zoom/pan and script opcodes |
| P2 | GE/trading helper state | `GrandExchangeOffer.java`, packet/script handlers | game-state/UI as needed | incomplete | Determine minimum state required for interfaces/scripts and plugin observability |
| P2 | DB/table/config data | `DBRowConfig.java` and related generic DB classes | `cache`, `client-script` | untriaged | Identify 240 script-facing DB/table opcodes and cache formats actually used by target interfaces |
| P2 | World list/title/login UI | `World.java`, `GameEngine.java`, client title helpers | application, shell/game UI, protocol bootstrap | partial | Separate official world-list behavior from private-server profile needs; retain reusable UI/session semantics only |
| P2 | Timing/randomness | engine cycle loop, actor/object animation random starts, client random helpers | application clocks + explicit semantic randomness | partial | Inventory every behavior where cycle boundary or randomness changes observable state and make source explicit |
| P2 | RuneLite injected API/accessors | API-implemented semantic classes, injected methods/fields, callbacks | `plugin-api`, `plugin-host`, semantic events/snapshots | evidence-found | Classify each injected surface as semantic query, committed event, validated command, overlay/UI capability, diagnostic, or not-needed |
| P2 | RuneLite callback/event ordering | injected callbacks around packet/state/menu/widget/draw operations | `game-state` commits, application event batching, plugin host | untriaged | Establish ordering only where RuneLite-class plugin behavior requires it; do not import callback-driven state ownership |
| P3 | MIDI/device/platform details | engine/audio/platform classes | `audio`, platform adapters | untriaged | Port only cross-platform semantics; keep device/browser specifics behind adapters |
| P3 | Java runtime/applet/browser legacy | `GameEngine` applet/AWT/network implementation details | none or platform adapters | not-needed except semantics | Record only timing/input/window semantics that survive into the Rust native+web architecture |

## Cross-cutting questions every domain must answer

For each domain above, the detailed audit must explicitly resolve:

1. **Authority**: Is the value wire state, cache definition data, semantic runtime
   state, derived projection data, or injected/plugin-only state?
2. **Lifecycle**: What creates it, resets it, reuses indexes, shifts it on region
   changes, and destroys it?
3. **Timing**: Is the behavior packet-time, game-tick, client-cycle, animation
   frame, render-frame, or wall-clock driven?
4. **Ordering**: Which updates must apply before others within one packet/cycle?
5. **Sentinels**: Which encoded values mean absent, reset, infinite, default or
   unchanged?
6. **Bounds/failure**: What malformed data is rejected, ignored or clipped?
7. **Projection**: Which parts are authoritative versus recomputed for drawing?
8. **Revision ownership**: Which rules are 240-specific and must remain in a
   profile/decoder rather than leaking into domain types?
9. **Plugin visibility**: What does a RuneLite-class plugin actually need to
   observe or command, and at what committed ordering point?
10. **Evidence strength**: Is the conclusion exact-240, independently confirmed,
    nearby-revision only, or still inferred?

## Current high-value gaps

The existing 2026-10-23 actor audits already remove substantial uncertainty from
player/NPC framing and movement. The most valuable next research is therefore not
to rediscover those packet loops. It is to close the runtime semantics that those
packets feed:

1. implement the bounded actor path queue and capture the real assignment-time backlog;
2. apply the two resolved action movement gates during per-cycle path scheduling;
3. finish finite action completion/looping semantics;
4. target-following, turn rate and directional turn/walk/run selection;
5. spot animations, hits/headbars, overhead state, tint/transparency and other
   currently consumed-but-not-retained update blocks;
6. player appearance details and model invalidation;
7. projectile/graphics-object lifetime and pose behavior;
8. region rebuild/base-shift semantics; then
9. widgets + CS2 + complete menu/input action flow.

Those gaps connect already-decoded network state to the visible/client-script
behavior that makes the Rust client feel correct.
