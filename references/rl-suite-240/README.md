# OSRS 240 — rl-suite output

Input: `injected-client-1.12.39.jar` (RuneLite injected client, revision 240)
Input SHA-256: `25f42961c400bd9dfff1554402441c0ba6d1cffd011163cb9b0b4c42ae194f85`

Tool: [rl-suite 1.3.1](https://github.com/) — built from
`/Users/tylercovalt/Documents/rl-suite-main` with JDK 17.

## Outputs

| Path | What it is |
| --- | --- |
| `injected-client-1.12.39-deob.jar` | Deobfuscated, deterministically renamed jar (735 classes) |
| `sources/` | Vineflower sources for the generic-name jar (735 files) |
| `injected-client-1.12.39-deob-semantic.jar` | Same jar with 93 API-derived semantic class names |
| `sources-semantic/` | Vineflower sources for the semantic-name jar |
| `injected-client-1.12.39-deob.audit.json` | Audit report for the generic run |
| `injected-client-1.12.39-deob-semantic.audit.json` | Audit report for the semantic run |
| `semantic-1.12.39.tsv` | Checksum-bound semantic map used for the second run |
| `build_semantic_map.py` | Script that derives semantic class names from the jar's API interfaces |

## What the tool did

- 735 classes, 696 classes renamed to stable `classXX` names.
- 26,298 malformed `@Named` annotations removed.
- 252 packet fields named from the `osrs-240` packet profile.
- 5 packet handlers matched; 164 try/catch ranges normalized.
- 0 archive verification errors.
- Every original class, field, and method name retained as
  `@ObfuscatedName` / `@ObfuscatedSignature`.
- Unused members marked with `@ToRemove` rather than deleted.

The semantic run additionally names 93 classes from the RuneLite API interfaces
they implement in this exact jar, for example:

`Player`, `NPC`, `Actor`, `GameEngine`, `Widget`, `WorldView`, `Menu`,
`ClientPreferences`, `AbstractFont`, `AccountType`, `ChatLineBuffer`,
`MessageNode`, `ItemLayer`, `GroundObject`, `GameObject`, `WallObject`,
`DecorativeObject`, `Scene`, `Tile`, `Model`, `SpritePixels`, `PacketBuffer`.

The remaining game classes keep `classXX` names; fields and methods keep
`fieldN` / `methodN` names. Use `@ObfuscatedName` to map back to the original
jar, and use the named packet fields directly.

## Important caveat

The output is **analysis-only**. rl-suite materializes three constant-dynamic
string-concatenation sites so decompilers can read them; this changes when
those constants are computed. Do not log in with the transformed jar. Keep the
original `injected-client-1.12.39.jar`.

## Commands

```sh
cd /Users/tylercovalt/Documents/rl-suite-main
export JAVA_HOME=/opt/homebrew/opt/openjdk@17/libexec/openjdk.jdk/Contents/Home
./gradlew :transform-jar:shadedJar

JAR=transform-jar/build/libs/rl-suite-1.3.1-all.jar
OUT="/Users/tylercovalt/Documents/Java Deob/decompiled/rl-suite-240"

"$JAVA_HOME/bin/java" -jar "$JAR" \
  --input "/Users/tylercovalt/Documents/Java Deob/OSRS_gamepack_240/injected-client-1.12.39.jar" \
  --output "$OUT/injected-client-1.12.39-deob.jar" \
  --report "$OUT/injected-client-1.12.39-deob.audit.json" \
  --packet-profile osrs-240

"$JAVA_HOME/bin/java" -jar "$JAR" --decompile \
  --input "$OUT/injected-client-1.12.39-deob.jar" \
  --source-output "$OUT/sources"

# Optional API-derived semantic class names
"$JAVA_HOME/bin/java" -jar "$JAR" \
  --input "/Users/tylercovalt/Documents/Java Deob/OSRS_gamepack_240/injected-client-1.12.39.jar" \
  --output "$OUT/injected-client-1.12.39-deob-semantic.jar" \
  --report "$OUT/injected-client-1.12.39-deob-semantic.audit.json" \
  --packet-profile osrs-240 \
  --semantic-map "$OUT/semantic-1.12.39.tsv"

"$JAVA_HOME/bin/java" -jar "$JAR" --decompile \
  --input "$OUT/injected-client-1.12.39-deob-semantic.jar" \
  --source-output "$OUT/sources-semantic"
```
