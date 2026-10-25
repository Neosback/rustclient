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
