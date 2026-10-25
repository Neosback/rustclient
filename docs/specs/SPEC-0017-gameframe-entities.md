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
