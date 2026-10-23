# SPEC-0018: Playable desktop — walk and bank

Status: accepted milestone; implementation in progress, not an acceptance claim.

Owners: model (typed actions), protocol (OpenRune 240.2 codecs), game-state
(entities, map selection, inventories and interfaces), application (validation
and orchestration), scene (collision and picking projections), game-ui (widgets
and menus), client-script (bounded VM), native (platform composition).

## Goal

Using the actual server LIVE cache, log in, walk/run across an ordinary map
rebuild, interact with a banker or bank booth, deposit/withdraw one or all through
server-driven interfaces, close/reopen the bank, and continue playing. Item counts
must survive another login. This milestone does not establish RuneLite parity.

Implementation status and exact validation: [playable desktop checkpoint](../plan/PLAYABLE-desktop.md).

## Invariants and interfaces

- GameState owns semantic entities, current map selection, inventory containers,
  interface state and script-visible variables. Existing owners are extended.
- Platform input becomes typed actions; application validates current session,
  coordinates, targets and interface permissions before profile encoding.
- Protocol owns opcodes, lengths, transforms, ciphers and profile limits. Verify
  them against the server-selected rsprot 240 sources, never nearby revisions.
- Movement requests do not change confirmed player position. Server updates do.
- Picking derives from the displayed projection. Collision/routing uses cache
  geometry and committed location/variable overrides, not independent game truth.
- Map changes invalidate old scene workers and interaction targets. World-space
  entity coordinates survive non-instanced map rebuilds; old GPU geometry is
  discarded. Scene installation is checked against the current map selection.
- Widgets are decoded from cache and driven by server operations and bounded CS2
  execution. A hand-built bank screen is not a compatible substitute.
- New semantic state, events and operations include lifecycle reset, replay,
  deterministic fingerprints and a fingerprint schema bump where required.
- Shared semantics remain Wasm-compatible; apps contain platform composition only.

## Failure behavior

Reject stale/out-of-scene actions before cipher advancement or network enqueue.
Use bounded outbound queues and report backpressure. Disconnect drops pending
actions. Malformed packets fail with profile/context; unsupported instanced maps
and required script opcodes fail explicitly rather than corrupting state.
Script execution has instruction, stack, call-depth and allocation limits.
Failed semantic operations leave state unchanged. Failed/stale asset workers do
not install a different map or revive a disconnected session.

## Acceptance

- Focused codec-family goldens cover movement, interactions, interface/container
  updates and malformed/truncated data with recorded rsprot source provenance.
- Semantic contracts cover map transitions, stale targets, container/widget
  lifecycle, script limits and deterministic replay.
- Live disposable-account proof completes the goal above with exact results.
- Run formatting, workspace/all-target check, strict Clippy, workspace tests and
  web Wasm compilation. Record commands and distinguish checks from live evidence.
- Render parity follows ADR-0004's separate manual acceptance gate.

## Non-goals

Instanced travel, combat, advanced banking, browser gameplay, plugin execution,
distribution and full visual acceptance are subsequent milestones.

## NPC and interaction continuation

The profile tracks ordered NPC wire indexes and the last NPC update-origin field;
these are decoder context, not an entity-position authority. It converts build-area
origins to typed offsets from the current map anchor. GameState resolves the world
origin and validates the complete NPC batch (duplicates, membership, limits and
coordinate arithmetic) before committing any part. Removals precede additions;
attributes then apply in source order. Unmodeled cosmetic fields may be consumed
only with a verified layout; unsupported customization masks fail explicitly.

NPC names, action visibility, locomotion and delayed action-sequence playback are
semantic state. Action sequences use the existing ActorSequenceSlot owner; base
locomotion playback remains separate. New observable fields participate in
fingerprint schema 8 and reset on session shutdown. ActionSlot is a checked index
into five definition-authored world actions; ActionMask represents visibility.
Menus are disposable views of current state/assets, and a selected entry must be
revalidated against its map, runtime target and current effective definition.

## Inventory continuation

GameState owns ordered slots in typed InventoryId containers. Replace clears old
contents, Patch applies entries in source order (last duplicate wins), Remove
forgets a container. Empty slots differ from item placeholders with quantity zero.
Operations require an active session and validate limits before mutation: at most
256 containers and 16,384 slots/changes per operation. A patch may establish a
previously unknown container; unmentioned slots remain unchanged or empty. Equal
final contents commit no event. Changed contents emit InventoryChanged and an
inventory-domain delta. Session boundaries clear containers. Schema 8 includes
sorted container IDs, ordered optional stacks and the inventory delta/event.

The 240 profile normalizes mirrored container IDs from negative combined IDs,
rejects obsolete nonnegative IF1 widget addresses, and bounds quantities to the
wire's nonnegative signed integer range. Widget rendering, script consumers and
item actions remain subsequent work; containers do not invent interface state.

## Interface and widget continuation

GameState owns an optional root, parent-component mounts, widget overrides and
inclusive child permission ranges. WidgetId is an interface/component address;
packed IDs stay in adapters. Root changes preserve mounts for the server's later
move operations. Open replaces a mount at its parent, Close removes that mount,
and Move relocates it while replacing the destination. Closing a group clears
its overrides/permissions when no other mount or root uses that group. Resync
reconciles mounts and replaces permission ranges atomically; surviving groups
retain widget overrides. Unloaded-widget updates are retained to respect packet
ordering. Cyclic mounts are rejected before mutation. Session shutdown clears all
interface state. Changed operations emit InterfacesChanged and delta bit 1024;
no-ops emit neither. Fingerprint schema 9 includes every new semantic field.

The domain bounds mounts to 256, widget overrides to 16,384, event intervals to
4,096, text to 16,384 UTF-8 bytes, and child indexes to -1..65,534. Permission
ranges are canonical disjoint sorted intervals; later changes override overlapping
children and adjacent equal permissions merge. The profile translates the 240
V2 32-button permission mask into semantic operation bits, with legacy ten-button
cache permissions translated separately. Unknown required flags/layouts fail
explicitly. Widget definitions and listeners remain immutable cache assets.

The cache adapter decodes IF3 geometry, containers, rectangles, text, sprites,
models, lines, actions, typed listeners and trigger lists with exact source errors
and bounded allocations. Unsupported IF1/new custom formats fail explicitly.
Layout is shared and bounded; it derives from those definitions and committed
overrides. Rendering support and CS2 coverage must be reported separately from
successful definition decoding. No handwritten bank layout substitutes for cache.

## Script execution continuation

Cache profiles translate script binary instructions into named semantic instructions.
The 240 format includes integer, object/string and long local/argument counts.
Application schedules server requests and widget listeners in stream order; the VM
receives immutable assets and the existing GameState mutation gateway. VM stacks,
call frames and arrays are invocation-local execution machinery, not another live
widget or variable authority. Dynamic children, mutable properties, listeners and
client variables belong to GameState and clear on session/group shutdown.
Execution is bounded and errors identify script, instruction and cause. Successful
prefix semantic operations remain committed if a later instruction fails, matching
the existing nontransactional operation-batch contract; the failed instruction
leaves state unchanged and the application stops the affected script chain.

Widget actions carry the interface lifetime, component, dynamic child, operation
and expected item. Application validates current topology and permissions before
profile encoding. Script-created children participate in layout/hit testing and
replay exactly like other committed interface state.

Runtime widget overrides now share one map for server and script properties.
Nested dynamic children have a flat sibling index plus an optional dynamic parent;
cycles, missing parents and allocation limits reject before mutation. Client
variables/settings and server skill/energy/weight updates participate in schema
10 and reset with the session. The topology epoch is a disposable invalidation
token, like the existing scenery generation; it is excluded from fingerprints.

The explicit OpenRune cache adapter treats stale decoded-size hints for config
archives 5 (inventories), 10 (items), 11 (parameters) and 14 (varbits) as advisory, as it already
does for the object repack. CRC, stored-version and bounded decompression checks
remain required. This policy does not apply to the ordinary osrs-240 profile.

Live ordinary travel also encountered the OpenRune repack's stale decoded-size
hint for animation frame archives in index 0: archive 4699 reported 2744, decoded
2696; archive 8483 reported 281, decoded 233. The selected repack profile reports
frame-size hints as advisory after normal CRC/version checks. Other profiles
retain strict size checks.

Script writes use SceneryState's effective client variables; server values are
retained by the same owner as the synchronization baseline. Server packets update
both; VARP_SYNC restores effective values, and reset/session shutdown clear both.
The baseline participates in schema 10 because it affects future observable state.
Synchronization and baseline-only changes produce explicit committed events.

## Gameframe review continuation

### Desktop input and transition corrections

The existing interface state owner retains script-authored drag parents, dead
zones, delays and scrollbar behavior. These properties use the ordinary runtime
mutation, group/session reset, interface delta and replay paths; fingerprint schema
13 includes them. Pointer capture is disposable, bound to the interface lifetime,
and cancels on focus loss or shutdown. Drag coordinates are the clamped widget
origin relative to its drag parent, including parent scroll and the original grab
offset. Platform key/text events reach visible cache-authored key listeners with
profile-translated key/character arguments. Gameplay text never edits credentials.
Map asset loading preserves the authenticated gameframe, with a loading overlay;
it must never present the title or credential form for an active session.

Evidence: revision-240 `sources-semantic/classQI.java` (1301–1304),
`classPQ.java` (drag limits/coordinates), and `classBB.java` (event arguments).
Acceptance requires focused drag/event/reset contracts plus the workspace checks
and a separately recorded live input review. World-map drawing and remote-player
updating remain compatibility gaps until their complete cache/semantic paths and
live evidence exist; a black content widget is not successful world-map support.

### Remote player tracking

GameState owns visible players and out-of-view tracking records. Exactly one record
owns an index's position, appearance and remembered facing at a time. Promotions
move cached appearance into a visible actor; demotions retain appearance/facing
while replacing exact position with the coarse tracking region. The profile
supplies region/grid dimensions and translated offsets; domain code performs
checked movement and wrapping without embedding a revision's bit layout. Initial
tracking, coarse shifts, promotions, demotions, movement and properties are ordered
semantic updates. Validate the whole batch before mutation. New visible incarnations
have typed instance identity so interpolation cannot reuse a departed actor's path.
All tracking fields and action playback participate in fingerprint schema 13,
replay, entity deltas and session reset. Low-resolution wire flags/list order stay
in the decoder; it does not keep a second authoritative position/appearance cache.

TSPS `client/game/sync/PlayerSyncManager.ts` and widget input controllers provide
additional lifecycle evidence; selected rsprot 240 codecs remain authoritative for
wire layout. RustOSRS provides rendering evidence. These repositories are read-only
references, never Cargo/runtime dependencies. Full combat/spot effects, instanced
world views and the complete world map remain separately tracked compatibility work.

The normal desktop gameframe has one presentation path: cache-authored widgets
and scripts. Handwritten chat, tabs and minimap chrome must not be composed over
it or receive invisible hit targets. Script geometry queries use local aligned
widget bounds even for hidden widgets; visible layout indexes dynamic children
once per build. Native FPS counts presented frames and memory reports process
RSS, sampled once per second outside GameState.

GameState owns bounded incoming game-message history (100 per channel, 4096
total), local message identity and arrival cycle. Server MESSAGE_GAME packets
become typed chat operations; script history queries and notifications read that
owner. Player appearance retains the server display name. Session boundaries
clear both. Invalid messages reject atomically. These fields and the chat event
and delta participate in fingerprint schema 11 and ordinary semantic replay.
Private/public player chat, social lists and outgoing chat remain later coverage.

Revision adapters normalize array operands, widget type/alignment operands and
parameter stack types before shared execution. The OpenRune adapter maps the
selected-tab client variable to the existing GameState tab owner, and the master
volume device option to the existing master-volume variable. No competing live
copy of those settings is introduced. Unsupported other device options fail with
context. Audio playback is outside this checkpoint.
### Window and game canvas contract

The interface owner retains typed fixed/resizable mode, host content extent and
the drawable game canvas extent. Platform resize inputs and script mode changes
commit through interface updates. The profile selects the canvas caps; layout,
script geometry, asset discovery, hit testing and action validation use the same
committed canvas. Fixed mode is top aligned and horizontally centered; resizable
mode uses available content within profile caps. DPI converts logical pixels to
physical pixels once, without stretching game sprites. Fullscreen changes host
content extent through the platform adapter and preserves the selected game mode.
Canvas changes schedule resize listeners; window status packets report the same
mode and extent. Session cleanup resets these fields and native composition
reasserts current host metrics. The title form keeps its own centered 765×503
coordinate space. Evidence: RuneLite 235 GameEngine.resizeCanvas,
MouseHandler.setWindowedMode, Client.resizeGame and class70.alignWidgetSize.



### Widget input/lifecycle continuation

One topmost interactive widget owns a primary press; underlying tracks or dismiss
layers do not receive that same click. Drag thresholds count client cycles.
Server drag-depth permissions select the ancestor coordinate space before a
script's explicit parent; absent both, the drag is free. Clamp the top-left origin
using the original grab offset, then include parent scroll for script coordinates.
Temporary drag origins affect the widget and its descendants in the shared
paint/hit-test layout without committing a second widget position.

Resize callbacks are deferred until after the current batch's load/server scripts,
on initial layout and changed extents. This initializes the script-owned camera
settings. Committed characters and named keys reach visible authored listeners;
title credential input does not consume gameplay typing. The selected cache
profile supplies the All Settings close hook that restores chat's listener.
Menus clamp to the committed game canvas. Hover text and red/yellow click crosses
are disposable feedback and cannot intercept pointer input.

Authenticated map loading keeps the gameframe visible, with a loading message,
while scene resources rebuild. It must not project the login form for a plane
change. World-map content and complete actor scheduling/blending are still
compatibility work; these input/lifecycle changes do not establish their completion.


The logout script instruction sets the reference client's disconnect/reconnect
hint. With RustClient's current always-end-on-transport-failure policy, that hint
has no additional observable state. It must allow the authored operation listener
to finish, followed by the validated logout button request; server confirmation
then ends the session. A future automatic reconnect policy must model that hint
explicitly before changing this behavior.
