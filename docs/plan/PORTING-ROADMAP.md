# RustClient: TSPS migration and classic behavior

## Current priority: TSPS migration

Primary target: TSPS at `ac7b52a21df137cbd6c011c73fd2f173cbc274d6`, configured
cache `osrs-241_2026-10-20`. OpenRune 240.2 stays available. The checkout inspected
for this continuation is clean `d4ea8072`; the earlier merge conflicts are already resolved.
[SPEC-0031](../specs/SPEC-0031-tsps-profile.md) owns the approved migration contract.

1. **Build/repository cleanup:** diagnostics opt-in; optimized `play` separate from fast dev;
   inactive shell vendor removed with recovery provenance; full source research is explicit.
2. **Protocol/cache adapter:** independent TSPS framing/login, shared intent/update facade,
   independently pinned 241 script map and source-produced fixtures. **Experimental, partial.**
3. **Playable acceptance:** finish required packets and semantic owners, validate the installed
   cache, then run native login/walk/actors/interfaces/inventory/chat/ground-items/logout/restart.
   **Not established.** OpenRS2 cache 2746 title assets pass CRC/version/decoded-size inspection; its supplied XTEA-key set is empty. The decoder inventory covers 240,949 declared config/interface/script files: 200,724 decoded, 877 unsupported, one failed (script 0), 39,347 unchecked. This is structural evidence; script execution, map and visual acceptance remain open. Current explicit gaps include NPC updates, complete skills/run-energy,
   actor target fallback/effect duration and appearance extensions, widget mutations/scoped mount scripts, ground-stack identities, social,
   world views and custom movement. No silent payload skipping closes these gaps.
4. **Correctness:** extend existing shared menu/input/lifecycle owners; close classic manual
   rendering acceptance before retiring its source evidence. The source decoration snap is off;
   earlier reports saying it is on are stale.
5. **Later:** playable web, continuous movement and optional HD/plugin rendering services.

The domain matrix remains the detailed status owner for the existing 240 implementation;
the [earlier execution record](../archive/2026-foundation/plan/PORTING-ROADMAP-2026-11-05.md) retains its dated scope. Neither registered profiles,
source mapping counts nor fixtures certify complete 241 cache or gameplay behavior.

### Commands

- `cargo play --cache-dir PATH`: optimized play, plugins available.
- `cargo edit` / `cargo quickcheck`: fast native development; diagnostics skipped.
- `bash scripts/check.sh fast|full|research`: bounded edit checks, complete validation, or explicit source research.
- `cargo check --workspace --all-targets --all-features`: includes diagnostics.
- Source research regeneration: `python3 tools/port_audit.py --check` and
  `python3 tools/parity_inventory.py --check` (requires pinned research trees).
- TS-generated fixtures: `npx --yes --package=tsx tsx tools/tsps/export-fixtures.mts /path/to/tsps`;
  output is isolated under `target/tsps-fixtures` and compared with committed fixture hashes.

### Saved development connection

`<preferences-dir>/connection.json` supplies profile/endpoint/cache defaults; explicit arguments
can override them. It never contains credentials. Example:

```json
{"server_profile":"tsps-241","server_url":"ws://127.0.0.1:43594/","cache_profile":"tsps-241","cache_dir":"/path/to/osrs-241_2026-10-20"}
```

Use `--server-profile openrune-240.2 --cache-profile openrune-240 --rsa-public-key PATH`
for the retained server, or `--offline` for local inspection. No machine-specific OpenRune
paths are searched. TSPS live/visual acceptance and runtime performance measurements remain NOT-RUN.

## Execution and acceptance order

1. Freeze this cleanup and run the commands above. Record exact failures and unavailable live
   cases in the [current audit](../reference/rs240-port/CURRENT-AUDIT.md). Compare identical
   clean, warm and incremental workloads before claiming build savings.
2. Complete TSPS semantic packet coverage in the existing adapter and GameState owners:
   player/NPC lifecycle and effects, skills/run energy, widget mutations and scoped interface scripts, ground items, social/chat and required outbound interactions. Generate
   fixtures through pinned server/client encoders. Unsupported required effects remain errors.
3. Prove map/cache and script compatibility, then exercise native login, visible scene/actors,
   walking, object/NPC interactions, inventory, dialogues, ground items, chat, logout and restart.
   Capture the build/cache/source identities and session resets; codec tests alone do not pass.
4. Observe input ownership at viewport edges, scrolling/capture, fractional DPI, Retina and
   small windows; add shared semantic regressions for proven failures. Run Windows/Linux CI
   and label absent live hardware cases unverified.
5. Measure unchanged-quality frame-time distributions, CPU/GPU work, uploads, allocation and
   retained/peak memory. Optimize demonstrated costs through existing owners and recheck semantics.
6. Finish classic rendering acceptance below. Retire evidence only after every dependent
   domain row has a reproducible replacement, source pin and applicable license.
7. Continue browser gameplay and optional HD/plugin renderer services after the native milestone.
   Native and browser retain shared semantics and automatic capability negotiation.

### Phase 11: classic GPU completion

This heading retains incoming spec/research links. Use the bounded manual acceptance workflow
in ADR-0004 and the renderer/domain rows: decoration placement, depth/face ordering,
transparency, animation and picking need matched stock evidence. Compilation and the existing
Rust implementation are not the oracle. Preserve classic quality and draw distance while
allowing renderer-owned, capability-mediated plugin enhancements. No visual gate is closed by
this cleanup; deob/RuneLite source evidence stays pinned until its dependent gates pass.
