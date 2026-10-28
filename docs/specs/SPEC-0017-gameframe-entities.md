# SPEC-0017: Gameframe HUD, Minimap, Live Entities and Location Animations

Status: approved implementation milestone.
Owners: model (typed entity identities, appearance, and orientation), cache (gameframe sprites, NPC/kit/item definitions, base model assembly), protocol (openrune-240 player info, NPC info, and location animation decoders), game-state (authoritative entity collections, location animation overrides, fingerprint v6), scene (minimap generation and posed entity meshing), game-ui (gameframe HUD presentation), renderer (world entity uploads and UI dynamic textures), application (connection orchestration), native (composition, camera face-north, tab interaction).

## Contract

1. **Authoritative Entities**:
   - `GameState` is the single authoritative owner of live player entities and NPC entities within the active session.
   - Entity state is transitioned strictly through typed `SemanticOperation::UpdatePlayers` and `SemanticOperation::UpdateNpcs` produced by the connection decoder.
   - Decoded player appearances, worn items, identity kits, orientations, and active animation sequences belong to semantic state and participate in canonical state fingerprinting (schema version 6) and replay.
   - Posed vertex buffers, GPU handles, and UI frames are disposable projections and never authoritative truth.

2. **Gameframe & Minimap Presentation**:
   - An authentic resizable-mode gameframe HUD presents:
     - Top-right minimap framed by sprite 1177, masked to a circular aperture via sprite 1178, rotated with camera yaw, with player location centered.
     - Top-right compass positioned inside frame housing, masked via sprite 1179, rotated with camera yaw; clicking the compass snaps camera yaw to 0 (Face North).
     - Status orbs (Hitpoints, Prayer, Run energy, and Special Attack) positioned along the minimap boundary with fill levels and text.
     - Bottom-right stone tab navigation bar (sprites 1173 & 1174) with authentic tab icons (sprites 774..=787) and active tab selection.
     - Bottom-left chatbox frame with channel tabs.
   - All HUD sprites are decoded from cache index 8 without external image dependencies.
   - Minimap scene imagery is generated from authoritative terrain underlay/overlay shapes, heights, and static wall/door placement at 4 pixels per tile.

3. **Protocol & Updating**:
   - `openrune-240.2` decodes:
     - `PLAYER_INFO` (opcode 91): bitstream movement, low-res/high-res transitions, and extended info blocks (appearance, sequence animation, spot animation, facing).
     - `NPC_INFO` (opcodes 150 small / 141 large / 111 origin): active NPC movement, new NPC additions with definition IDs, and extended info blocks.
     - `LOC_ANIM` (opcode 123) and `LOC_ANIM_SPECIFIC` (opcode 84): location sequence overrides.
   - Handled packets emit typed `ServerUpdate` variants without leaking raw network bytes or ciphers into higher layers.

4. **Model Assembly & Posing**:
   - Player composite models are assembled following `PlayerComposition`: kit models and worn items combined in slot order, tinted by body colors, and lit with player lighting constants.
   - NPC models are assembled from `NpcDefinition` body models, recolored/retextured, and lit with NPC lighting constants.
   - Dynamic entity posing evaluates sequence skeletal frames at the 50 Hz client cycle, projecting posed models into disposable scene vertex geometry.

## Interface load callback continuation, 2026-10-25

`application::ClientScripts` schedules immutable cache-authored group load listeners after a valid
interface operation commits to GameState. Asset residency is not an interface-open event: an explicit
root assignment or subinterface open runs that group's load listeners even when the same group is
already present. A resync runs root load listeners only when the root changes and mount load listeners
when a parent receives a different/new group; retaining a group at the same parent does not reopen it.
Moves, unrelated updates and repeated resource synchronization do not synthesize load callbacks.

Preserve root-before-mount and authored component order for explicitly opened groups. Invalid topology
must reject before scheduling any callbacks. Native and web use this same scheduler. The resource
cache/loaded-group set is disposable orchestration state, never a second owner of interface topology.

Acceptance: semantic host tests cover repeated root/subinterface opens, unchanged resync, a reused group
opened at another parent, callback order, invalid topology and repeated resource synchronization.
This continuation does not accept widget-model frame reset/advancement, root-close callback ordering,
all mutable widget unload behavior or rendering parity; those remain tracked separately.

## Destination and pointer continuation, 2026-10-25

GameState owns one optional plane-independent world-tile `MapDestination` for the minimap flag.
Accepted local movement/action requests and server map-flag updates enter through typed operations;
confirmed player position is never predicted from a click. Session teardown clears the destination.
Ordinary map rebases preserve its world coordinate. Client-cycle advancement clears it when the
local player's footprint-centred fine position enters the destination tile, or no local player exists.
Destination state, changes and replay participate in fingerprint schema 24; the gameframe delta marks
explicit changes and automatic arrival clearing.

Movement click validation rejects stale maps, wrong planes and out-of-map coordinates. It does not
reject an in-bounds request based on disposable local reachability: the server owns the requested
walk's resulting movement. The client BFS used for special actor traversal remains a distinct concern.
Minimap clicks use the displayed player's authoritative fine position and integer camera rotation,
and the revision adapter emits the dedicated minimap movement packet with its pointer/camera/fine
telemetry. World clicks emit ordinary movement packets. Minimap clicks do not start a yellow cross.

Cache-authored tabs and their sprites continue through widget/script operations. Pointer feedback
honours mouse-cross/mouseover preferences; cross animation uses four 100 ms frames and the reference
eight-pixel anchor with sprite trim offsets. Minimap flag frame zero comes from the cache `mapmarker`
group, stays upright while its position rotates, and is clipped by the displayed aperture.

Acceptance: focused semantic destination/reset/arrival/replay contracts, byte-level packet vectors,
minimap coordinate/rotation/mask tests, captured world-menu destinations, and existing interface tests.
Full instance/multi-world routing, transformed-player footprint sizes, special actor route expansion,
all minimap toggle modes, complete tab/interface content and desktop pixel parity remain open until
separately verified. A passing headless test does not establish perfect visual parity.

## Actor chat and projection continuation, 2026-10-25

Player public/forced speech is committed with the corresponding player update. GameState owns
actor text, presentation metadata and cycle lifetime independently of bounded chat history.
Public messages add one history entry on receipt, never on outbound submission. Simple player
speech strips a leading `~`; it enters history for the local player or when prefixed. Player
speech expires after 150 client cycles; NPC speech after 100. Actor removal/index reuse and
session teardown clear speech. New observable speech participates in fingerprint schema 25.

The profile decoder receives the selected cache Huffman codec explicitly. Transformed bytes,
account-type tables and CP1252 conversion stay in protocol adapters; missing codec or malformed
chat produces a contextual error rather than silently dropping a public message. Cache-script
chat listeners run after history commits, including messages carried by player updates.

Overhead presentation uses posed actor bounds, the world camera/viewport, cache bold font,
clipping, overlap resolution and received style metadata. Rendering does not own speech timers.
NPC spawn cursors are initialized before movement endpoints change. Projection observes the
same committed client cycle as minimap/camera consumers. Context menus do not consume camera
arrow press/release state; mouse selection, Enter and Escape retain their menu roles.

Acceptance: bounded chat vectors; history/speech lifecycle and replay contracts; NPC packet
bursts and ordered turns; camera held-key/menu cases; baseline Cargo gates. Visual acceptance
is recorded separately for fixed/classic/modern layouts, feet contact and NPC routes.
Broader actor effects, social lists and complete renderer parity are outside this continuation.

Speech quads occupy the world widget's painter boundary, beneath subsequently painted mounted
interfaces. Their timers continue while obscured. Context-menu labels share widget rich-text
parsing/measurement, so cache colour tags control styling rather than appearing as literal text.

## Screen brightness correction, 2026-10-25

Live cache scripts 3961/3966 read/write device option 6 as a 0..100 brightness percentage.
The earlier SoundEffectQuality mapping was incorrect and is superseded by this evidence.
The 240 classBY.method1359 clamps that percentage, inverts it, then computes gamma as
`0.5 + (100 - percent) / 200`. GameState's interface preferences own one typed brightness
value, default 40 (gamma 0.8); profile ids and aliases stay in cache/script adapters.
The frame projects that value into the world camera uniform for both textures and untextured
HSL. Changing brightness must invalidate a cached uniform even with a stationary camera.
Session reset follows existing interface-preference lifecycle; disk persistence is separate.
Brightness participates in fingerprint schema 26 and existing interface events/replay.

## Actor turning continuation, 2026-10-26

GameState advances current body rotation after movement and before action playback on each
committed client cycle. Actors retain their turn rate, signed consecutive turn count and the
last cycle's pre-turn orientation/idle turn request, so projections can reconstruct body rotation
without a second turn scheduler. NPC definition changes replace resolved movement policy;
a zero rate resets rotation only on spawn, not on later definition replacement. Missing NPC
policy remains explicit. Player turn rates also arrive through profile enrichment rather than
GameState revision defaults. Session/reset/removal/index reuse discard the actor's turning state.
These fields participate in semantic fingerprint schema 27 and existing entity deltas/replay.

The ordinary step follows exact 240 `classKN.method6539`: a 1024-unit tie turns positively,
snap comparisons are strict, rate zero still counts nonaligned cycles, and the signed turn
counter wraps as Java int arithmetic does. Idle turn animation requests require positive rate
and either a partial step or more than 25 turning cycles. Sequence fallback stays in the
cache-backed pose projection. Rich target-facing, animation freeze flags, movement slowdown
for target interaction and complete movement-sequence lifetime remain separate incomplete work.
Acceptance requires boundary/rate, forced-phase, policy replacement, reset/replay and projection
reconstruction tests; automated checks do not establish logged-in visual parity.

## Action playback continuation, 2026-10-26

Resolved actor actions advance after movement/turning through GameState. Legacy sequences
carry their within-frame remainder across strict `>` boundaries and rewind by the authored
frame step. Cached-model actions advance their frame/time cursor once per cycle without
changing the legacy within-frame counter. Both count end crossings and clear the action on
terminal loop limits or invalid rewind. The source's positive-rewind legacy total-duration
modulo applies even for a one-cycle advance. Invalid/degenerate resolved timing must terminate
without an unbounded zero-duration loop. Unresolved timing keeps its explicit compatibility
fallback; it is not evidence of completed playback.

The action/path hold runs after movement: an active action at delay <= 1 with the authored
hold policy and remaining pre-assignment path work stays at delay one after forced movement
has ended. A positive delay consumed to zero does not advance playback in that same cycle.
Resolved action projection uses committed frame/time and within-frame counters; it must not
reconstruct lifetime or loop count from an elapsed timer. Movement-pose lifetime, readiness
fallback, freeze windows, spot effects and animation callbacks remain separate incomplete work.

Cached-model timing now retains its loop rewind; fingerprint schema 28 includes that field.
Existing action reset/removal/index reuse, entity cycle deltas and semantic replay cover these
transitions. Acceptance includes legacy carry/zero boundaries, cached ranges/rewinds/loop
limits, delayed starts/path holds, local/remote/NPC operation replay and committed pose sampling.

### NPC compass decoding correction — 2026-10-28

Revision-240 NPC compass codes are NW, N, NE, W, E, SW, S, SE, with north increasing
world Y (`NPC.method3466`). They differ from player movement's code table. Preserve
both run steps in that order, and apply the same NPC table to walk, crawl and run.
Tests cover each wire code and all ordered run pairs. Never compensate for a decode
error with client collision clamps, mirrored models or a second positional scheduler.
