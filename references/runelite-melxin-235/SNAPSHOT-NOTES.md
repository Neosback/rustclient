# Revision 235 reference snapshot

This is a read-only reference copy of the OpenOSRS "melxin" injected-RuneLite tree at **OSRS revision 235**,
kept as the readable (`@Export`-named) source for the RustClient port of the revision-240 client.
It is evidence, not a product. Do not run it against a live account.

| Fact | Value |
| --- | --- |
| Game revision | 235 (`buildSrc/src/main/kotlin/Dependencies.kt`: `rsversion = 235`) |
| RuneLite base | `rlVersion = 1.12.14` |
| OpenOSRS injector | `openosrsVersion = 4.31.2` |
| Client sources | `runescape-client/src/main/java` — readable names via `@Export`, 235 `@ObfuscatedName`s |
| Licence | BSD 2-clause, see the headers in each file; Jagex's client code belongs to Jagex |

## Local differences from the upstream snapshot

The older copy bundled in `RustOSRS/RuneLite-melxin` is the closest upstream-shaped reference. Differences here:

- `cache/.../definitions/loaders/ObjectLoader.java`: opcodes 6/7 read 32-bit model ids (a 237-style change);
  `cache/.../ObjectManager.java` skips objects that fail to decode.
- `wiki-scraper`: `BuildingDumper`, `SceneryDumper` and the `buildingData` / `sceneryData` / `fullScrape`
  commands, with hard-coded local paths (`/Users/tylercovalt/Desktop/LIVE`, a RustOSRS examine file).
- `runelite-client/src/main/resources`: `building_data.json`, `scenery_data.json` and sample files.
- `gradle.properties`: host-specific `org.gradle.java.home`.
- Build output (`build/`, `.gradle/`) and `.DS_Store` files are not included.
- Some upstream resources present in the older copy are absent here (`item_stats.json`, `npc_stats.json`,
  `item_variations.json`, `ge_limits.json`, `skill_icons*`, `logback.xml`, `openosrs.properties`).

## What changed between 235 and 240

See `docs/reference/REVISION-235-TO-240.md` in the RustClient repository for the change inventory
(packets, cache decoders, XTEA, world map, CS2 long type, camera, GPU plugin, sailing) and the rule for
when to prefer this tree and when to read the 240 evidence.
