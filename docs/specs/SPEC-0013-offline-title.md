# SPEC-0013: Offline cache title milestone

**Status:** Implemented for the first desktop title milestone (2026-10-21)

Validation evidence: [offline title checkpoint](../plan/TITLE-offline-cache.md).

## Goal and owners

Open an explicitly selected `osrs-240` cache read-only and present its classic
welcome and credential screens. This narrow slice precedes CP5 networking and
the remainder of CP6/CP9. It does not claim general cache or revision compatibility.

- `platform` owns bounded read-only blob ranges; native supplies directory I/O.
- `cache` owns validated JS5 storage/container/table/file decoding and typed CPU
  images/fonts. Cache profile adapters own index mappings and named asset roles.
- `game-state` owns pre-session title panel/status through the existing operation
  gateway, events/deltas, replay and fingerprint schema v2. Title operations are
  rejected during an active game session. Session begin/end reset title state.
- `game-ui` owns credential drafts, focus, hover, local preferences, cosmetic
  animation and a platform-neutral ordered quad draw list. These are presentation
  state, not a second semantic session. Passwords are never persisted, logged,
  replayed, fingerprinted or included in debug output. Projection text contains
  only masked passwords. Cancel clears the password.
- `renderer` consumes the draw list and owns disposable textures/pipelines/buffers.
- `application` coordinates loading/preferences using injected capabilities.
- Native owns argument parsing, worker/event-loop scheduling, windows and input.

## Interfaces and invariants

`ReadOnlyBlobs` supplies blob length and exact bounded ranges by `StorageKey`.
It exposes no paths or mutable operations. An in-memory implementation permits
shared decoder tests and later browser composition without native I/O.

JS5 readers validate checked arithmetic, allocation/decompression limits, chain
identity/order/cycles, container boundaries, group CRC and version trailer. Absent
optional indexes are allowed; absent required assets fail with index/group/file
context. Protocol 5–7 reference tables preserve optional name/hash/size/Whirlpool
columns. Unsupported layouts/compression fail explicitly. No XTEA/region decoding
is implied. Snapshot identity hashes reference-table bytes in index order; it is
a metadata identity, not a hash or authenticity claim for every cache byte.

Cache profile selection is explicit. Filenames/table revision numbers do not
prove a client revision. The supplied LIVE snapshot is the user-selected 240
target. Named title assets are validated against it; unsupported profiles fail.

The title uses 765×503 logical pixels, horizontally centered and top-aligned,
with a shared DPI transform for draw and hit-test coordinates. Native enforces
that minimum logical window size. Static assets upload once per GPU lifetime;
flames update at explicit 20 ms steps with an injected seeded random source.
GPU loss rebuilds projection resources from retained CPU assets/state.

## Behavior and non-goals

Welcome offers New User (local information) and Existing User (credentials).
Typing, Backspace, Tab/Shift-Tab, Enter, Escape, mouse focus, password masking,
remember/hide username are supported. Printable CP1252 input is bounded to 320
username and 20 password characters. Text is literal, never interpreted as markup.
Empty submission reports the missing field; nonempty submission reports an
offline-preview status without beginning a session. Help is local information.
Remember defaults off; disabling it deletes the stored username. Hide defaults
off. Preferences persist separately from the read-only cache. Music is silent,
its control visibly muted/disabled, and world selection is unavailable.

Authentication, account services, title music, world selection, general widgets,
CS2, scene rendering and browser cache import are deferred. Web compilation and
shared cache/UI/render semantics remain required.

## Failure behavior and acceptance

Load on a native worker and report progress/errors without blocking the window
event thread. Never repair or write into the source cache. Corrupt preferences
fall back to defaults with a diagnostic; preference write failures do not crash
the title. Startup failures name configuration/profile/storage/decode causes.

Acceptance requires decoder fixtures (including malformed data), title semantic
replay/fingerprint v2 vectors, deterministic flame evidence, input/DPI/preference
tests, actual LIVE asset verification, native welcome/credential captures,
renderer recreation checks, format/check/strict Clippy/tests and wasm compilation.

## Behavioral evidence

- Pinned RustOSRS gitlink: `git ls-tree HEAD references/rustosrs`.
- `references/rustosrs/crates/osrs-cache/src/encode/{store,reference_table}.rs`
  and `decode/sprite.rs`: JS5 and sprite behavior.
- RuneLite 235: `runescape-client/.../{HttpResponse,HealthBarUpdate,class358,
  LoginScreenAnimation,AbstractFont}.java` in the user-supplied RuneLite-melxin
  checkout. Record the exact checkout SHA with generated fixture provenance.
- LIVE title asset hashes/metadata are recorded by the inspection command.

Reference source remains evidence, never a RustClient dependency or edit target.

### LIVE encoded-size metadata policy

The LIVE logo reports encoded size 12541 while its CRC-valid stored gzip
container is 13604 bytes; its decoded size is correctly 34092. Publisher encoded
size metadata is therefore retained as an advisory hint, never used for range
allocation or as a rejection criterion. Stored index/container lengths, CRC,
version and reported decoded length remain checked. This is an explicit format
policy, not a heuristic fallback to a different revision.
