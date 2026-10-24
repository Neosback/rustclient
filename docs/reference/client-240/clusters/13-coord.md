# Cluster 13: packed coordinates

Status: evidence-found

## Scope

Owned class: `classKY` (`@ObfuscatedName("ky")`).

This cluster names the revision-240 packed plane/x/y coordinate primitive and the conversion helpers that are directly established by the source. It does not rename unrelated math helpers merely because they are called nearby.

## Old to new

| Stable symbol | Original | Readable name | Evidence |
| --- | --- | --- | --- |
| `classKY` | `ky` | `Coord` | T4: constructor and `method6623` are exact inverse packed-coordinate transforms |
| `classKY.field4053` | `af` | `x` | T4: packed bits 14..27 and string/world-coordinate use |
| `classKY.field4054` | `ae` | `y` | T4: packed bits 0..13 and string/world-coordinate use |
| `classKY.field4055` | `az` | `plane` | T4: packed high bits plus `-1` invalid sentinel |
| `classKY.method6623` | `az` | `pack` | T4: `plane << 28 | x << 14 | y` |
| `classKY.method6626` | `af` | `unpackPlane` | T4: `value >> 28` |
| `classKY.method6640` | `ab` | `unpackY` | T4: `value & 16383` |
| `classKY.method6649` | `ax` | `tileToLocalBase` | T4: `tile << 7` |
| `classKY.method6652` | `ac` | `localToTile` | T4: `local >> 7` |
| `classKY.method6656` | `aa` | `tileToLocalCenter` | T4: `64 + (tile << 7)` |
| `classKY.method6674` | `aq` | `isValid` | T4: tests `plane != -1` |
| `classKY.method6663` | `cz` | `packCoord` | T4: invalid returns `-1`, otherwise packs plane/x/y |

## Behavior

`Coord` carries three integer components. Its packed representation is:

```text
bits 28..29  plane
bits 14..27  x
bits  0..13  y
```

A packed value of `-1` constructs an invalid coordinate by setting `plane` to `-1`. `isValid` tests exactly that sentinel.

The local-coordinate helpers establish the stock 128-local-unit tile scale:

```text
tileToLocalBase(tile)   = tile << 7
localToTile(local)      = local >> 7
tileToLocalCenter(tile) = 64 + (tile << 7)
```

These helpers matter to the world-effect path. `Projectile` applies local-coordinate deltas through `classKY.method6649`, while `classNG.method7883` uses `method6656` when resolving a tile target to the center of a scene tile.

## Authority and Rust owner

The Java class should not be ported one-for-one merely because it exists. RustClient should retain typed coordinate domains and explicit conversions:

- semantic plane/tile/world coordinates: `game-state` or shared typed model ownership;
- revision-specific packed forms: protocol/profile adapters;
- local 128-unit scene conversions: a dependency-light typed coordinate helper consumed by `game-state` and `scene`.

The `-1` invalid packed sentinel is wire/reference behavior, not a reason to represent every Rust coordinate as an untyped integer.

## Known external referrers inspected

- `Actor.java`, which produces this coordinate-shaped actor position data;
- `classNG.java`, whose `method7883` resolves entity/tile targets and consumes the local-center conversion;
- `Projectile.java`, which consumes the local-unit conversion while moving a projectile target/source frame.

The exhaustive reference set was not regenerated in this checkpoint because the user explicitly prohibited the repository verification/gate workflow. A later verification pass should enumerate all referrers before merging generated artifacts.

## Client quirks

`method6634` contains a suspicious masked shift using `243900667` rather than the normal 14-bit mask. It is marked `@ToRemove(unused = "true")`; this cluster deliberately leaves it generic instead of laundering the oddity into a normal-looking name.

## Deliberately left generic

Several duplicate/unused extraction helpers and region-specific unpacking helpers remain unnamed. Their bodies are either redundant, marked unused, or require a broader region-coordinate audit before a semantic name would be safer than the stable symbol.

## Verification state

No `verify.sh`, tests, Cargo checks, Clippy, CI, decompiler regeneration, or generated merge update was run for this checkpoint, per user instruction.
