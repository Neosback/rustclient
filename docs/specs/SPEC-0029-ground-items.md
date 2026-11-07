# SPEC-0029: Ground items

**Status:** Accepted for the ground-item state milestone, 2026-11-05; pile rendering and live
acceptance remain open.
**Owners:** protocol (`openrune_240::ground_items`), model (`GroundItemUpdate`), GameState
(`GroundItemsState`), application (world actions), scene catalog (definitions), native
composition (menu, minimap), plugin bridge (projection, SPEC-0004).

## Scope and invariants

Ground items ("objs") are server-owned item stacks on world tiles. `GameState` holds them as
authoritative state; menus, the minimap, plugins and any future pile renderer are projections.
Revision 240 wire layouts stay in the protocol adapter. Higher layers see the source-neutral
`GroundItemUpdate`. Nothing predicts despawns or visibility changes: the server deletes items,
and the public and despawn ticks are retained facts.

Source anchors (pinned 240 deob, `references/rl-suite-240/sources-semantic`):

- Wire: `client` `OBJ_*_SPECIFIC` handlers and `classTI.method10892` enclosed zone bodies;
  rsprox 240 `zone/payload/Obj*Decoder.kt` and `specific/Obj*SpecificDecoder.kt` agree field
  for field with the stock buffer reads (`classXY.method13295/13300/13303` for `g4Alt1/2/3`).
- State: `classBU.method1249` (add, appended with `Deque2.method7946`), `classJB.method6387`
  (delete), `IntProjection.method4110` (count), `classCZ.method2851` (enabled ops),
  `WorldView.method3788` / `classAD.method325` (customise, uncustomise), the
  `UPDATE_ZONE_FULL_FOLLOWS` handler (zone reset) and the map rebuild.
- Menu: the 235 deob `class62` ground item branch (orange `colorStartTag(16748608)` names,
  ops 4 down to 0, default "Take" at op 3, `Hidden` removes an op).

## Behaviour

- Each tile holds its items oldest first. Add appends. Delete, count, customise and
  uncustomise act on the first item with the same id and quantity; enabling ops acts on the
  first item with the same id. An update with no match commits nothing.
- Zone bodies are relative to the map anchor like location updates. `*_SPECIFIC` packets carry
  an absolute `CoordGrid`. Either outside the -48..55 window is ignored, mirroring stock
  `method3747`.
- `UPDATE_ZONE_FULL_FOLLOWS` empties the zone's 8x8 tiles on its plane. A map rebuild that
  changes the window empties every pile; a same-window rebuild keeps them, as it keeps
  scenery. Session begin and end clear everything.
- The total is bounded (`GROUND_ITEM_LIMIT`); exceeding it fails the operation and leaves state
  unchanged.
- Commits emit `GroundItemsChanged { tile }` or `GroundItemAreaReset`, the `GROUND_ITEMS` delta
  and a per-tile projection change. Fingerprint schema 31 covers every retained field.
- World menu: entries list the definition's ground options that the item's op mask enables,
  as `"<op> <col=ff9040><name>"`. Noted items take the unnoted name. Validation re-checks that
  the item is still on the tile, then encodes `OPOBJ1..5` (sub-op 0).
- Minimap: a red dot (mapdots frame 0) at the centre of each tile with a pile on the local
  player's plane, drawn before NPC and player dots.

## Accepted deviations

`GROUND-ITEM-TILE-PICK` (the hovered tile stands in for pile model bounds) and
`GROUND-ITEM-PILES-UNDRAWN` (no scene models yet), in
[PARITY-DEVIATIONS.tsv](../plan/PARITY-DEVIATIONS.tsv).

## Non-goals of this milestone

Pile models (top three items by value, count variants, customisation), OPOBJ6 examine,
OPOBJT/OPOBJU use paths, item-on-item targeting and live drop/pickup acceptance.

## Acceptance

- Unit: protocol golden bytes for every body, truncated/oversized/negative input rejection,
  OPOBJ round trips against the reference decoder order.
- GameState: ordering and matching, reset and rebuild, replay fingerprint equality, inactive
  session rejection.
- Application: menu labels, op masking, validated intent and stale rejection.
- Live (NOT-RUN in the authoring pass): drop, pick up, stack count changes, zone reset on
  region entry, despawn.
