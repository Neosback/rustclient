# Revision 235 → 240: what carries over from the 235 tree and what changed

Status: research inventory, 2026-10-25. Evidence only; RustClient specs and code stay authoritative.

The readable, hand-named client source we have is **revision 235** (the OpenOSRS/RuneLite "melxin" tree,
see [the tree](#1-the-235-tree)). The client we target is **240** (RuneLite injected client 1.12.39,
OpenRune 240.2), which only exists as stable `classXX` names in `references/rl-suite-240/`. This page
lists every 235→240 change we could establish from independent evidence, so a port can start from the
235 source and look at 240 only where this list says Jagex or RuneLite changed something.

## How the list was built, and what it does not cover

| Source | What it gives | Where |
| --- | --- | --- |
| Upstream RuneLite history, `f2e65dce..2a0776b3` (471 commits) | API, cache-decoder and plugin changes with dates | [`rev-235-240/upstream-commits-235-240.tsv`](rev-235-240/upstream-commits-235-240.tsv) |
| rl-suite packet tables, revisions 235–240 | Every client/server packet, per revision | [`rev-235-240/packets-235-240.tsv`](rev-235-240/packets-235-240.tsv) |
| rl-suite semantic jars / `script-opcodes-240.tsv` | CS2 opcode numbering | `docs/reference/client-240/inputs/` |

Range ends: `f2e65dce` is upstream's first `-rev235` content commit (2026-01-14, "Update Legacy ID classes
to 2026-01-14-rev235") and `2a0776b3` its last `-rev240` content commit before "cache: rev 241"
(2026-09-23). 110 of the 464 non-merge commits are bot content updates (GameVals, item variations,
quests, widget ids, scripts) and are not listed individually.

**Not established here:** a class-by-class diff of the 235 and 240 *client* (`runescape-client`). RuneLite
does not publish the injected client sources, and obfuscated names are re-randomised every revision, so
that diff needs structural matching (`tools/client-240/carry235.py` finds only 66 confident class pairs).
A quick member-shape comparison of the two rl-suite decompilations was tried and discarded: injected-API
differences between builds swamp it. Treat engine behaviour that this page does not mention as
*unchanged only by default* — the rule below still requires a check against 240 before a port claims parity.

Dates below are when upstream RuneLite *supported* a revision (`api: update N`, `cache: update N`), which
can precede the live release (RuneLite works from betas). No upstream cache or API change is labelled 240;
revision 240 upstream is content updates only. Whether that means 239→240 changed no formats must be
confirmed against our actual 240 cache.

## 1. The 235 tree

`references/runelite-melxin-235/` is the vendored copy of the 235 tree; it was exported from the working copy
`/Users/tylercovalt/Documents/ChatGPT/RSPSi-resources/RuneLite-melxin` without build output
(`buildSrc/.../Dependencies.kt`: `rsversion = 235`, `rlVersion = 1.12.14`, `openosrsVersion = 4.31.2`).
It is OpenOSRS' injected RuneLite: `runescape-client` (624 readable `@Export`-named Java files with their
235 `@ObfuscatedName`s), `runescape-api`, `runelite-api`, `runelite-mixins`, `runelite-client`, `cache`,
`deobfuscatorRuneLite` and the injector. Its `runelite-api` lacks `Renderable.getRenderMode` and
`DecorativeObject.getXOffset2`, which upstream added in "api: update 236", so everything after the range
start should be treated as absent from it.

It differs from the older copy inside `references/rustosrs/RuneLite-melxin` by local edits:

- `cache/.../ObjectLoader.java`: opcodes 6/7 read 32-bit model ids (a 237-style change), and
  `ObjectManager` swallows per-object decode failures.
- `wiki-scraper`: `BuildingDumper`/`SceneryDumper` and `buildingData`/`sceneryData` commands (hard-coded
  `/Users/tylercovalt/Desktop/LIVE` cache path).
- `runelite-client/.../resources`: `building_data.json`, `scenery_data.json` (+ samples).
- `gradle.properties`: host-specific `org.gradle.java.home`.

The older copy has resources this one lacks (`item_stats.json`, `npc_stats.json`, `item_variations.json`,
`ge_limits.json`, `skill_icons*`, `logback.xml`, `openosrs.properties`). Locations are listed in
[INDEX.md](INDEX.md#the-235-tree).

## 2. What to use for what

| Area | Take from | Why |
| --- | --- | --- |
| Rendering rules: models, lighting, animation posing, scene assembly, placement, priorities, depth | **235 tree** | No Jagex renderer change was established in this window (see the limit above); the listed changes are RuneLite GPU plugin or API (3.8) |
| Interface/widget engine, CS2 interpreter, menu logic, actor movement/update rules | **235 tree**, then verify the 240 deltas below | Opcode numbers and names are unchanged; only long-typed operations were added |
| Plugin behaviour (the 13 ports) | **235 tree** + the per-plugin deltas in 3.12 | The 235→240 diff for those plugins is a handful of commits |
| Packets: ids, lengths, field encodings, message set | **240 tables only** | Layouts were re-scrambled at 236 and message versions added; 235 ids/encodings are wrong for 240 |
| Cache definition decoders | **240 cache + 3.2/3.3 below** | Many opcodes were added in 237–239 |
| Map region loading | **240 cache** | Map/loc files are no longer XTEA-encrypted or name-keyed (3.3) |
| Camera maths | **240**, then 235 for shape | Angles became 14-bit and camera position `float` in 239 (3.7) |
| World map data | **240 cache** | Per-square/zone group/file ids removed in 238 (3.4) |
| Content: items, NPCs, quests, islands, drop tables | **240 cache** | Data, not code |

## 3. Changes by area

### 3.1 Network

Packet tables (names joined across revisions; ids and encodings are never evidence of identity):

| Direction | 235 | 240 | Identical | Same fields, re-encoded | Same fields, reordered | Structural | Added | Removed |
| --- | ---: | ---: | ---: | ---: | ---: | ---: | ---: | ---: |
| Client → server | 101 | 101 named (117 table slots) | 35 | 18 | 26 | 6 | 16 | 16 |
| Server → client | 141 | 151 named (152 slots) | 97 | 0 | 23 | 2 | 29 | 19 |

Per-packet verdicts and the first revision step where each changed are in `packets-235-240.tsv`.

- **Re-scramble at 236.** All 44 client layout-only changes (18 re-encoded, 26 reordered), 17 of the 23
  server reorders and 7 of the 8 structural changes first differ between 235 and 236. Later steps move only
  six server layouts (`IF_SETPOSITION`, `OBJ_CUSTOMISE_SPECIFIC` 237; `IF_SETTEXT`, `LOC_ANIM_SPECIFIC` 238;
  `NPC_ANIM_SPECIFIC`, `OBJ_UNCUSTOMISE_SPECIFIC` 239) and one client one (`CLIENT_CHEAT`, 240). Every
  numeric id differs (2 of 85 common client names and 1 of 122 common server names kept their id). Never
  copy a 235 opcode, length or byte transform.
- **Message set changes, by step** (names only):
  - 236: server `OBJ_ADD/COUNT/CUSTOMISE/DEL/ENABLED_OPS/UNCUSTOMISE` removed (the `*_SPECIFIC` variants
    remain; assumed superseded by zone-partial updates, unverified), `WORLDENTITY_INFO_V5` removed, `ZBUF`
    added (and removed again at 239).
  - 237: client `EVENT_NATIVE_MOUSE_CLICK` replaced by `_V1/_V2`; server camera packets versioned
    (`CAM_*_V1/V2`), `SET_MAP_FLAG`, `UPDATE_REBOOT_TIMER` versioned, `WORLDENTITY_INFO_V6` → `V7`.
  - 238: client `OPLOC1-5`, `OPNPC1-5`, `OPOBJ1-5` become `_V1/_V2` (V1 dropped again at 239, leaving `_V2`);
    `RESUME_P_COUNTDIALOG_LONG` added; server `REBUILD_NORMAL`/`REBUILD_REGION`/`IF_SETMODEL` versioned,
    `REBUILD_WORLDENTITY_V4` added (`V3` dropped at 239).
  - 239: server `CAM_*_V3`, `CAM_TARGET_V4`, `CAM_SKYBOX`, `CAM_UNLOCK`, `CAM_LOOKAT_CYCLES`,
    `GROUP_FULL/VAR/VAR_INT/VAR_LONG`, `AMBIENCE_START/STOP`; client `EVENT_MOUSE_CLICK_V2` restored.
  - 240: `AMBIENCE_*` renamed `AMBIENTSOUND_START/STOP`, `NPC_INFO_LARGE_V6/SMALL_V6` added next to V5
    (which one the target server speaks comes from the selected rsprot 240 sources, not these tables).
- **Structural changes worth knowing:** `CLIENT_CHEAT` gained a trailing byte (240); at 236 `MOVE_MINIMAPCLICK`
  gained fields, `OPLOCU`/`OPOBJT`/`OPOBJU` changed field types, `HINT_ARROW` has no decoded fields in 240 and
  `RUNCLIENTSCRIPT` gained a long argument (`J0`) — CS2 now has a long stack (3.5). The table extraction reads
  field order from bytecode, so a layout difference is a wire difference, but each structural row should be
  re-read against the rsprot 240 sources before use.

Use `docs/reference/REVISION-240.md` for the 240 packet workflow (rsprot sources, OpenRune server).

### 3.2 Cache and definition decoders

From upstream `cache` module changes (`cache: update 236/237/238/239`). "Landed" is the upstream commit.

| Definition | Change | Landed |
| --- | --- | --- |
| NPC | opcode 130 `idleAnimRestart`, 147 `zbuf = false` | 236 |
| Object | opcode 96 `raise` (ubyte) | 236 |
| Object | opcodes 6/7 read **32-bit model ids** | 237 |
| NPC | opcodes 61/62 32-bit models / chatheads | 237 |
| Item | opcodes 44–54 32-bit inventory/wear/head models (`maleOffset`/`femaleOffset` unsigned byte on 45/48) | 237 |
| Kit | opcode 5 and 70–79 32-bit models / chatheads | 237 |
| SpotAnim | opcode 3 32-bit model id | 237 |
| Interface | model ids are `int` (`rev237` flag) for model-type widgets | 237 |
| Item, NPC, Object | **entity ops**: item ground ops (30–34) and NPC/object ops (30–34) go through `EntityOpsLoader` (a `"Hidden"` string still means no op); sub-ops, conditional ops (varp, varbit, min, max) and conditional sub-ops at item 200–202, NPC 251–253, object 100–102 | 237 |
| Enum | opcode 7 (long values), 8 (default long) | 237 |
| Param | opcode 7 default long, 8 explicit type | 237 |
| Inventory | opcode 249 params | 237 |
| All param tables | `readParams`: value tag 0 int, 1 string, **2 long** | 237 |
| Script | 16-byte trailer (adds long local/argument counts) and `LCONST` 8-byte operand | 237 (`rev > 1773753883`) |
| Item | opcode 15 `tradeable = false`; opcode 65 now `geTradeable` | 238 |
| Item | opcode 160 `stackable = 2` | 239 |

Skeletal/cached-model animation, 32-bit model ids and the `MAX_REGION` change all point the same way:
revision 237 is the big decoder revision. The 235 definition loaders are correct for 235 only.

### 3.3 Map regions and XTEA

- Upstream cache tooling stops requiring map XTEA keys in 237 (`cache: update 237`): `RegionLoader` reads
  an **unnamed** map index by region id (archive `i`, file 0 = terrain, file 1 = locations, no key) and
  `MAX_REGION` drops from 32768 to 25287. The same-day `api: update 237` makes the RuneLite XTEA plugin
  tolerate `getXteaKeys() == null`.
- `api: update 238` (2026-04-23) removes `getXteaKeys()` from `Client` and `WorldView` and deletes the
  RuneLite XTEA plugin and client. `REBUILD_NORMAL/REGION/WORLDENTITY` gain `_V1/_V2/_V4` forms in the
  238 packet tables.
- The login-block XTEA/RSA is **not** part of this and is unchanged by anything above.
- Our 240 cache and the OpenRune server decide the real behaviour; confirm the index naming on the actual cache.

### 3.4 World map

238: `WorldMapData` map squares and zones no longer carry `groupId`/`fileId` (`rev238`, gated on index
revision `> 425`); the world-map index is unnamed — `details` is archive 0 and `compositemap` archive 1.
Also `cache: 238 world map support` and `use id for compositemap and details when necessary` (06-18).

### 3.5 CS2 scripts and variables

- **Opcode numbers and names are unchanged.** All 551 opcodes named by the 235 `Instructions.java` have the
  same number and name in the 240 table; none was removed.
- **New in 237:** a third (long) value stack and 12 opcodes — `GET_VARC_LONG 51`, `SET_VARC_LONG 52`,
  `LCONST 61`, `POP_LONG 62`, `LLOAD 66`, `LSTORE 67`, `IF_LCMPNE/EQ/LT/GT/LE/GE 68–73`.
  Scripts carry long local and argument counts; `ScriptArguments` has a `Long()` count.
- Long-typed data: enum long values, param long values, `GROUP_VAR_LONG`, `RUNCLIENTSCRIPT` long args.
- RuneLite-only `RUNELITE_EXECUTE 6599` is not a Jagex opcode.
- 600+ numbers in 240 have no RuneLite name (see `client-240/OPCODES.md`); this tree names none of them.
- 237: `ScriptEvent` setters moved to a builder (`createScriptEventBuilder` → `ScriptEventBuilder.build()`).

### 3.6 Interfaces and widgets

Model-type widgets carry 32-bit model ids (237, 3.2). Widget menu sub-ops (`getSubOps`, `setSubOp`,
`setAction(int, String)`) were added on 2026-08-06 (`api: add widget subops`). In this window upstream also
removed `Client.getWidgetPositionsX/Y` and renamed `DraggingWidgetChanged` to `WidgetDrag`.
`ChatMessageType.LEVELUPMESSAGE(118)` ("Check the skill guide") appeared on 2026-03-03. Chat text gains the
`<at>` and `<nbh>` tags (08-06) and `Client.macroExpand` (`@mes_hl_blu@` etc., 08-30).

### 3.7 Camera

`api: update 239` (2026-06-12): camera position/focal point and pitch/yaw become **`float`**, with angles in
**14-bit units** (`Perspective.UNIT14`, `SINE14/COSINE14` tables, yaw masks `& 0x3FFF` instead of `& 0x7FF`).
Matching packets: `CAM_TARGET_V4`, `CAM_*_V3`, `CAM_UNLOCK`, `CAM_SKYBOX` (239). `Client.getMapAngle` removed.
This is the one numeric-convention change that can silently break a 235-derived camera or minimap rotation.
The camera plugin's script constants were updated on 2026-02-11 and in `api: update 237`.

### 3.8 Rendering (the RuneLite GPU plugin, not the Jagex renderer)

Changes in `plugins/gpu` and the API, by date (all RuneLite-side; the vendored `open-runelite-rev-240`
carries them):

- 2026-01-23: **face-priority sorting removed from alpha models**; 01-30 zones with incompatible roofs are
  not reused; 04-29 priority sort also disabled for streamed transparent models.
- 2026-01-23 (`api: update 236`): `Renderable.getRenderMode()` and `RENDERMODE_*`, `DecorativeObject`
  `getXOffset2/getYOffset2`; 02-16 colour-blind correction intensity; 02-27 `Actor.getFootprintSize`;
  `ActorSpotAnim` becomes a `Renderable`.
- 2026-05-05: `Perspective.getFootprintTileHeight` fixed for `mapLevel` and bridges.
- 2026-05-26/30: `Scene.getSkybox()`, GPU skybox, `DrawCallbacks.PRE_PASS_ALPHA` and a `preSceneDraw` overload
  with an entity projection; 06-21 skybox transparency fix.
- 2026-06-30 / 07-02 / 07-16: render modes for RuneLite objects, **model transparency**
  (`Model.getTransparency`), GPU render threads.
- 2026-08-20: UI scaling `targetDimensions` fixed in stretched mode.

Consequence for RustClient depth work: RuneLite's GPU plugin no longer face-priority-sorts alpha models, so a
RustOSRS-style per-face alpha sort is an addition, not what the 240 GPU plugin does.

### 3.9 Sailing and world entities

Evidence the machinery is **already in 235**: `Skill.SAILING`, `WorldEntity`, `WorldEntityConfig`, `WorldView`
and the `Perspective` world-view paths in the 235 `runelite-api`; client packets `OPWORLDENTITY1-6/T/U` and
server `REBUILD_WORLDENTITY_V3`, `WORLDENTITY_INFO_V5/V6`, `SET_ACTIVE_WORLD_V2` in the 235 tables.

Changed in this window:

- Packets: `WORLDENTITY_INFO_V5` removed (236), `V6` → `V7` (237); `REBUILD_WORLDENTITY_V4` added (238) and `V3`
  dropped (239); `OPWORLDENTITY*` layouts re-scrambled (236).
- API: `WorldView.TOPLEVEL` changes from `-1` to **`0`** and `LocalPoint`/`Perspective` use it (237, the
  top-level world-view id is now 0); `WorldView.getTileHeight(x, y, mapLevel)` (236); `localToCanvas` fixed on
  boats (236, 02-02); footprint tile height fixed on bridges (238).
- Plugin/content only: sailing islands, mooring points, salvage spots, sea fishing, skill-calculator and
  agility data, Wyrmscraig/Red Rock. These come from the 240 cache, not from code.

So the 235 world-entity source is the right base; verify the V7 info/V4 rebuild layouts, the top-level id,
and boat height/footprint maths against 240. This is inferred from RuneLite and rl-suite evidence, not from
diffing the clients.

### 3.10 Entity ops and menus

New `EntityOps` API (`getNumOps`, `getOp`, `getSubOps`) for NPC and object compositions (237, exported 03-31);
Menu Entry Swapper gained object sub-op support and fixes for entities with fewer than five ops (03-24/31).
The 235 `actions[]` model (null for "Hidden") is wrong for 240 definitions: ops can be conditional on a varp or
varbit range and have sub-ops.

### 3.11 Actors, models, animation, items (API deltas)

`Hitsplat` becomes an interface (`api: update 237`); `Animation.getLeftHandItem/getRightHandItem` (07-04);
`Model.getTransparency` (07-02); `PlayerComposition` colour/texture override API (02-19) and `setHash`
semantics; `Actor.getFootprintSize` moved up from `Player` (02-27); `ItemComposition.isGeTradeable`
(`api: update 238`); `ParamHolder` long values (237); `AmbientSoundEffect` loses min/max positions and
`Skill` gains `isMembers` in this window. `Quest` enum content changes are data.

### 3.12 RuneLite client and the plugins we are porting

Upstream commits in the window that touch a plugin on our port list (everything else for those plugins is
unchanged from 235):

| Plugin | 235→240 upstream changes |
| --- | --- |
| roofremoval | handle escene tiles (01-16); update POH regions (03-12); fix last-light lighthouse roof (06-20) |
| animsmoothing | none |
| stretchedmode | none (GPU UI-scaling fix in stretched mode, 08-20) |
| fps, statusbars, playerindicators | none |
| interfacestyles | 2010 glow icons (03-08); combat and sailing stone sprite offsets (05-18); actor scrubbed from condensed player option submenu (05-20) |
| devtools | script-inspector highlight error handling (02-26); `zsort` command (05-30); null `localPlayer` fixes (08-13) |
| entityhider | Leagues 6 thrall overrides (06-10) |
| npchighlight | null `localPlayer` fixes (08-13) |
| idlenotifier | animation additions only: moth mixing, huntingbolts, dragonstone/onyx cutting, bloodwood sap, golem crafting |
| camera | script updates (02-11, 237) |
| menuentryswapper | object sub-ops (03-31), < 5 ops fixes (03-24), bankmain migration (01-28), knock-out/lure block removed (07-08) |
| gpu | see 3.8; also 26 area/option commits |

Other client changes (not ported by us yet): custom font support (02-20), overlay snap corners and overlay
menus on `PostMenuSort`/`MenuOpened` (04–07), config panel profile limit 64, world hopper TCP/RTT ping,
`LinkBrowser` scheme checks, user-agent prefix requirement, and the client no longer references `Applet`
(`ClientConfiguration`, `GameEngine.setConfiguration/initialize`).

### 3.13 Content-only updates (ignore for porting)

Quests, item stats, clues, slayer, agility, hiscores, boss timers, world map locations, bank tags,
loot tracker, skill calculator and the bot-generated `gameval` classes. They change data we read from the
240 cache or are RuneLite-product features.

## 4. Working rule

1. Port behaviour from the 235 tree. Cite the 235 file and its `@ObfuscatedName`.
2. Before claiming 240 parity, check the topic against sections 3.1–3.12; if it is listed, read the 240
   evidence for that item (rl-suite `classXX`, rsprot 240 sources, the actual cache) and port the 240 form.
3. If it is not listed, the 235 behaviour is assumed unchanged, with the limit stated in the first section.
4. Never copy 235 packet ids, byte transforms, or definition opcode tables into the 240 profile.

## 5. Reproducing

```bash
git clone --filter=blob:none --no-checkout https://github.com/runelite/runelite.git runelite-upstream
python3 tools/rev-diff/upstream_commits.py runelite-upstream f2e65dce..2a0776b3e \
  docs/reference/rev-235-240/upstream-commits-235-240.tsv
python3 tools/rev-diff/packets.py /Users/tylercovalt/Documents/rl-suite-main/data \
  docs/reference/rev-235-240/packets-235-240.tsv
```

The diffs quoted in section 3 come from `git diff f2e65dce 2a0776b3 -- runelite-api cache` and
`git log -S<symbol>` on the same range.
