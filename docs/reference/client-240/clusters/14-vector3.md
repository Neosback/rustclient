# Cluster 14: pooled three-component vector

Status: evidence-found

## Scope

Owned class: `classUZ` (`@ObfuscatedName("uz")`).

This cluster names the mutable float-vector primitive used by projectile/world-position resolution and by broader transform math. The source establishes the vector operations directly, so the names do not depend on a nearby-revision guess.

## Old to new

| Stable symbol | Original | Readable name | Evidence |
| --- | --- | --- | --- |
| `classUZ` | `uz` | `Vector3f` | T4: constructors plus set/add/subtract/dot/cross/length/normalize behavior |
| `classUZ.field6425` | `al` | `x` | T4: first component; projectile path consumes it as horizontal x |
| `classUZ.field6423` | `aa` | `y` | T4: second component; projectile path consumes it as vertical height |
| `classUZ.field6424` | `ao` | `z` | T4: third component; projectile path consumes it as the second horizontal axis |
| `classUZ.method11802` | `lq` | `set` | T4: canonical three-component assignment |
| `classUZ.method11844` | `ak` | `length` | T4: Euclidean magnitude |
| `classUZ.method11817` | `aq` | `normalize` | T4: reciprocal-length scaling |
| `classUZ.method11822` | `ad` | `add` | T4: component-wise addition |
| `classUZ.method11834` | `aw` | `subtract` | T4: component-wise subtraction |
| `classUZ.method11835` | `sv` | `dot` | T4: standard dot product |
| `classUZ.method11839` | `ai` | `cross` | T4: standard cross product written back into this vector |
| `classUZ.method11846` | `av` | `scale` | T4: scalar multiplication |
| `classUZ.method11827` | `au` | `addScaled` | T4: this += other * scalar |
| `classUZ.method11816` | `ay` | `isNearlyZero` | T4: per-component epsilon test |
| `classUZ.method11797` | `ac` | `releaseToPool` | T4: bounded static-pool return path |

## Projectile semantics exposed by this primitive

`classNG.method7883` returns a temporary `Vector3f` describing a resolved target/source position. `Projectile.method1228` reads it as:

```text
x = vector.x
horizontal_z = vector.z
height = vector.y - encoded_height_offset
```

The projectile then releases the temporary vector through `releaseToPool`.

That axis ordering is important. In the client's 3D math, the second vector component is the vertical axis while the first and third components are horizontal. A Rust port should preserve meaning through typed names rather than infer that the Java field order is x/y-ground-plane.

## Math semantics

The directly named operations are conventional:

```text
length(v) = sqrt(x² + y² + z²)
normalize(v) = v / length(v)
dot(a,b) = ax*bx + ay*by + az*bz
cross(a,b) = standard right-handed three-component cross product
```

`isNearlyZero` uses the stock epsilon `9.765625E-4F` independently on every component.

## Pooling

The class owns a bounded static vector pool. `releaseToPool` appends the current object while capacity remains. Other factory/copy helpers pull from the same pool.

This pooling behavior is an implementation/performance detail, not semantic GameState authority. RustClient may use value types or a different allocation strategy as long as observable math and hot-path cost remain appropriate. The pool itself should not leak into gameplay state types.

## Rust owner

Recommended ownership:

- general dependency-light math type: shared model/math layer;
- semantic projectile position: semantic world-effect owner in `game-state` or a dedicated runtime state owner;
- scene conversion and rendering: `scene` consumes resolved positions;
- renderer remains projection-only.

Do not create separate vector representations in protocol, GameState, scene and renderer merely to mirror Java classes.

## Known external referrers inspected

- `classNG.java`: constructs/resolves temporary positions;
- `Projectile.java`: reads resolved x/y/z, then releases the temporary vector;
- quaternion/matrix helpers such as `classUL` and `classUU`: broader transform consumers visible in `classUZ` itself.

A later verification pass should enumerate the exhaustive reference set before generated mappings are merged.

## Client quirks

The class contains several duplicate helper bodies and multiple pool/factory variants, some marked `@ToRemove`. This cluster names one clear canonical operation for each semantic role instead of assigning misleading unique names to every duplicated transform artifact.

`normalize` does not visibly guard against zero length in the inspected method body. The port should not silently add a compatibility-changing branch without first checking callers and the broader math contract.

## Deliberately left generic

Duplicate reset/copy/add/subtract/cross/normalize methods, quaternion transform helpers and unused pool factories remain generic. They require either caller-specific evidence or are deob/injection duplicates that do not improve the port vocabulary.

## Verification state

No `verify.sh`, tests, Cargo checks, Clippy, CI, decompiler regeneration, or generated merge update was run for this checkpoint, per user instruction.
