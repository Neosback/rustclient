# SPEC-0021: Input, picking and menus

**Status:** Draft, 2026-10-29. The "Input arbitration" section was accepted on 2026-10-29
at the user's direction and may be implemented. The unified menu model, selection modes,
sort/priority and picking sections remain draft; implementation of those must not start until accepted.
**Owners:** `input` (platform-neutral input events and key codes), `application` (menu model,
entry construction order, selection modes, action dispatch to intents), `scene` (pick
candidates), `game-ui` (menu paint and hit bands), `protocol` (encoders). `apps/*` translate
winit/browser events into `input` events and nothing more.
**Evidence:** [client logic map §9](../reference/rs240-port/CLIENT-LOGIC-MAP.md#9-input-picking-and-menus),
[menu-actions-240.tsv](../reference/client-240/inputs/menu-actions-240.tsv),
[DEOB-PACKET-SITES.tsv](../reference/rs240-port/DEOB-PACKET-SITES.tsv). Contract:
[SPEC-0019](SPEC-0019-port-parity.md). Cycle placement: [SPEC-0020](SPEC-0020-client-cycle.md).

## Purpose

Every in-game action starts as a menu entry: left-click runs the top entry and right-click opens
the menu. Stock 240 builds entries with `Menu.method12757` (235 `Menu.insertMenuItem`), sorts
them (`client.method2307`; 235 `Client.menu`), decides left-click (`client.method2344`) and
dispatches the chosen entry in `classQP.method9746` (235 `class101.menuAction`), which writes
the client packet. RustClient today builds nearest-NPC/location menus inside `NativeApp`. This
contradicts SPEC-0001 ownership (matrix `shared-input`, `incorrect`) and covers 5 of the 46
recorded action-to-packet rows. This spec defines the shared model.

## Invariants

1. One menu model in `application`, shared by native and browser. Renderer, game-ui and plugins
   read it. Plugins change it only through validated commands (ADR-0006).
2. Entry order, sort, left-click choice and the encoded packet must match stock for the same
   committed state and pointer position.
   Until that model exists in `application`, `NativeApp` builds the world menu and appends the
   entries plugins offered (`PluginOutput::menu_offers`, SPEC-0004) for the tile the menu opened
   on: after "Walk here", only on a right-click, and a chosen entry becomes a `MenuOptionClicked`
   for the plugin at its next update. Plugin entries never encode a packet.
3. Raw menu opcodes stay in the 240 profile. The shared model uses a typed action enum with a
   profile mapping (the same rule as packet opcodes).
4. Platform adapters produce semantic input events only. No game decision is made in `apps/*`.

## Normative behavior

### World-map click dispatch (accepted 2026-10-31)

The application world-map owner handles press/drag/release using logical canvas
coordinates and explicit monotonic time. Stock 240 RenderOverview.method12459 /
method12464 define press ordering, truncation and the 500 ms / 25-pixel confirmation
guard. Active-area inverse section mapping supplies the source coordinate. A typed
map-click intent is encoded only by the selected protocol adapter (240 CLICKWORLDMAP,
opcode 78, four-byte packed coordinate; pinned decoder g4Alt1). TSPS packet IDs and
its release-click policy are not target behavior. Occluding UI/menu arbitration,
map closure, focus loss and session replacement revoke unfinished gestures. Normal
clicks do not teleport local GameState: the OpenRune administrator handler decides
whether to telejump and authoritative server updates establish the new position.
The separate stock staff modifier shortcut is not replaced with a normal map click.

### Input events (`crates/input`)

- Mouse: button press/release with button identity, position in logical canvas pixels, motion,
  wheel delta, enter/leave. Key: stock key code space (240 `classTI`, 235 `KeyHandler` code
  table), press/release/typed character, focus gained/lost.
- The idle counters required by SPEC-0020 S13 (mouse and keyboard idle cycles) live with these
  events, not in `apps/native/src/input_activity.rs`.
- All F1-F12, Escape and modifier keys reach the semantic layer. RustClient developer shortcuts
  are opt-in, documented, and never shadow a key that a stock widget listener or keybinding
  setting can use.

### Menu entry model

Each entry carries option text, target text, typed action (stock opcode), identifier, argument 1,
argument 2, item id, world-view id, shift-click flag and an optional submenu (235
`Menu.insertMenuItem` fields). Capacity is the stock array length; read it from the 240 `Menu`
constructor before acceptance. Insert beyond capacity is ignored, as in stock.

### Construction order

At SPEC-0020 S3 the menu resets to Cancel unless it is open. Entries are then added in stock
order:

1. Interface entries for the hovered widget tree (widget ops, IF_SETEVENTS masks, target verbs,
   selected item/spell "Use"/"Cast" forms).
2. Scene entries for pick candidates in stock candidate order (235 `class62.addSceneMenuOptions`,
   `class40.addNpcToMenu`, `AsyncRestClient.addPlayerToMenu`): locations, NPCs, players and
   ground-item piles per tile, including Examine (1002-1004) and Walk here.
3. Minimap and world-map entries where their widgets apply.

Player options come from SET_PLAYER_OP state. Hidden ops come from HIDENPCOPS/HIDELOCOPS/
HIDEOBJOPS (matrix `server-interaction-state`). Cache op arrays come from 240 `EntityOps`
(matrix `definitions`).

### Sort and priority

- Stock sort: repeated adjacent passes move every entry with opcode above 1000 below entries
  with opcode below 1000 (240 `client.method2307`, client.java:35374; 235 `Client.menu`,
  Client.java:6348-6390). Before sorting, 240 compares the current top entry with a retained
  entry (`classON.field4991`), apparently the previous top entry. Confirm its meaning and port
  that rule with the sort. Entries are
  displayed from the highest index down.
- Deprioritized entries add 2000 to their opcode. `menuAction` subtracts 2000 before dispatch
  (240 classQP.java:114-119; 235 class101.java:492-493). Which entries are deprioritized (attack option settings, combat
  level comparison using `PlayerAppearance.combat_level`, NPC attack option setting) follows the
  240 construction code and its varps.
- Left-click runs the top entry unless the stock left-click rule (`client.method2344`
  MenuShouldLeftClick, shift-click flags) opens the menu instead.

### Plugin menu stage (accepted 2026-11-05)

After the client builds the world menu and before it shows, sorts or consults it, the host runs
one synchronous plugin stage (`PluginSet::menu_stage`). It delivers `MenuEntryAdded` for each
entry, `MenuOpened` only when the menu is being shown, then `PostMenuSort`. It then collects the
`menu_edits` of every running `PostMenuSort` subscriber that holds `ui.menu.swap`, in
subscriber priority order, and applies them in order to the delivered list (`apply_menu_edits`).
The edited list is the one the left click runs, the mouse-over text shows and the opened menu
paints. The stage is skipped when no running plugin subscribes to a menu hook.

Rows project to the plugin `MenuEntry` with `RuneLite` identifiers (`application::menu`): the
NPC index for NPC options, the object id and scene tile for location options, the item id and
scene tile for ground item options, and the scene tile for "Walk here". Choosing any entry,
stock or plugin-offered, delivers `MenuOptionClicked` at the next plugin update; consuming the
click is not offered yet.

Widget menus (inventory, equipment, bank and every interface with actions) run the same stage.
Their rows project with `RuneLite` `CC_OP` conventions (`application::menu::widget_menu_entry`):
`MenuKind::WidgetOption(op)` with the one-based operation index as identifier, the child index (-1
for the widget itself) in `param0` and the packed widget id in `param1`. Operations beyond ten, continue
and button clicks map to `MenuKind::Other`. The mouse-over text, the left click (first row) and the
opened menu use the edited list, and choosing a row delivers `MenuOptionClicked`. A widget menu
whose rows are all removed swallows the click.

### Selection modes

Item-selected and spell-selected modes are semantic state with a lifetime. They are set by the
widget op, cleared after dispatch or on cancel, and invalidated by interface close or epoch
change. Their target masks come from IF_SETEVENTS. They produce the T/U packet forms the
selected profile actually sends: follow DEOB-PACKET-SITES and selected RSProt, not packet-name
existence.

### Dispatch

The chosen entry maps to one intent through the typed action. The profile maps the intent to the
row in `menu-actions-240.tsv` (completed from `classQP.method9746` for the rows that table
omits, such as OPLOC1-5, IF_BUTTONX and walk). Dispatch also sets the click cross (yellow for
walk, red for interaction) and, for walk-like actions, the destination flag request. Validation
(stale epoch, removed target, index reuse) rejects before encoding, as today.

### Picking

Stock collects scene candidates while drawing models (viewport mouse tags). RustClient may keep
ray picking against posed geometry only if, for the same frame, it produces the same candidate
set and order. Until a reference comparison shows that, ray picking is a candidate entry in
PARITY-DEVIATIONS, not an accepted improvement.

### Menu paint

Paint and hit bands follow the stock `Menu` drawing (15-pixel rows, header "Choose Option",
submenus) and the existing game-ui contracts. Tall menus and submenus remain open.

### Input arbitration (accepted)

Evidence: 240 `Menu.method12762` (pointer test with a 10-pixel margin; inside it the menu owns the
pointer), `Menu.method12725` (dispatch through `classQP.method9746`), `Menu.method12757` (reset).
The 235 `Client.doCycleLoggedIn` menu branch is the navigation aid; it was not re-read for this
slice, so the "outside press closes without acting" rule below is the long-standing client
behavior and carries a re-read obligation against `classPQ.method9204/9191` before the unified
model lands. Mapping to Rust owners: `game-ui::arbitration` (pure decisions), `apps/native`
(feeds it winit-derived facts and nothing else).

1. **Modal menu.** While a menu is open, a press (either button) is consumed by the menu: inside
   it selects the row under the pointer, outside its 10-pixel margin closes it. Nothing beneath
   (shell chrome, sidebar toggle, widgets, world, walk) receives that press or the matching release.
2. **Suppression.** While open, hover text, widget hold/drag/repeat, wheel zoom/scroll and middle-drag
   camera are suppressed. Opening a menu cancels any hold, drag and held camera input already in
   progress, without firing its release/drag-complete listeners.
3. **Topmost paint.** The menu is painted after every other canvas layer (widgets, overlays, plugin
   overlays, click cross, shell toggle). Painting order and hit order agree.
4. **Shell chrome never outranks a menu.** Hit zones that sit on the canvas (the sidebar toggle
   button) are active only while the button is painted and no menu is open. The shell panel is a
   separate surface: a press or pointer move over it closes an open canvas menu, as leaving the menu's
   10-pixel margin does.
5. **Keys.** Escape, Enter and navigation keys reach an open menu whatever shell focus is. Shell
   keyboard focus swallows other keys only when the shell reports that it wants them.
6. **Lifetime.** A menu closes on: loss of the interface root, scene change, disconnect, logout,
   reconnect, window-metric change, focus loss and the cursor leaving the canvas. The interface epoch
   advances on every interface or inventory operation, so it does not close a menu by itself;
   instead every selection is revalidated against its captured epoch and identity when chosen.
   A selection that fails revalidation is a visible no-op with a diagnostic, never silent.
7. **Walk here** is offered only when a walk could be sent now (not loading, not a diagnostics view,
   connected), the same gate as the left-click walk.

Blocking summary (what each layer may take from the pointer): open menu > shell chrome on the
canvas > widget with click handlers / no-click-through containers > world viewport. A layer that
declines a press passes it to the next; a layer that takes it ends dispatch.

## Failure behavior

An unknown or unsupported action is a visible no-op with a diagnostic. It is never a silent
fallback to another action. A missing cache definition for a target drops that entry, as stock
does when it has no definition, and records a diagnostic.

## Acceptance criteria

1. `crates/input` owns the event and key-code types. `apps/native` only translates winit events,
   and `apps/web` compiles against the same types.
2. Menu construction vectors: for recorded state plus pointer fixtures, entry lists (order,
   texts, actions, arguments) equal stock. Capture the reference lists with the pinned
   RuneLite reference bridge (`tools/parity`, MenuOpened/MenuEntryAdded) on the same server
   state.
3. Every row of `menu-actions-240.tsv`, plus the omitted rows above, has an encoder vector
   checked against selected RSProt or marked not-needed with evidence.
4. Sort, deprioritization and left-click tests, including Examine, Cancel, Walk here and
   attack-option settings.
5. Selection-mode lifetime tests: set, dispatch, cancel, interface close, stale epoch.
6. Plugin events MenuEntryAdded/MenuOpened/PostMenuSort/MenuOptionClicked are emitted from this
   model (RUNELITE-EVENTS rows change from never-emitted).
7. Matrix rows `shared-input`, `menus`, `outbound-actions`, `widget-input` are updated.

## Non-goals

Java AWT key semantics beyond the stock key code table. Mobile/touch menus (stock mobile opcodes
stay not-needed unless a profile requires them).

## Open questions

- 240 `Menu` capacity and submenu rules.
- The exact 240 deprioritization branches (they reference varps for attack options).
- Whether ray picking can be shown equivalent, or must be replaced by tag collection during
  scene draw.
