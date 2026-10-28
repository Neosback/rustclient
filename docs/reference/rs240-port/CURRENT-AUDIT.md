# Current correctness and port audit

Reviewed: 2026-10-28. Start: `0a91c0237c5d2bb4825a549ffc89497dfe671208`
with pre-existing dirty work; source inspection refreshed at
`bd3aae10d4a37c46401aaf71be2fa415cbe623e8` after that work was committed during
review. [REVIEW-BASELINE.json](REVIEW-BASELINE.json) records file hashes, selected
RSProt entries and the pasted input hash. No Rust implementation changed in this
review. No live session, render acceptance or Cargo suite was run for it.

This is a domain/source audit and re-review of the supplied assessment, not an
exhaustive method-by-method proof. [SPEC-0019](../../specs/SPEC-0019-port-parity.md)
defines the parity contract; [the roadmap](../../plan/PORTING-ROADMAP.md) defines
execution order. The [matrix TSV](PORT-MATRIX.tsv) is the editable domain backlog;
its Markdown is generated. [Packet coverage](PACKET-COVERAGE.tsv) records dispatch
and encode reachability separately from semantic completion. The method ledger
retains exact source mappings. None produces a client completion percentage.

## Confirmed blockers and their failure paths

1. **Selected NPC masks can end a session.**
   [npc_extended.rs](../../../crates/protocol/src/openrune_240/npc_extended.rs)
   rejects bits outside SUPPORTED before consuming their bodies. The selected
   `NpcAvatarExtendedInfoDesktopWriter` emits BAS `0x200000`, head customization
   `0x80000` and body customization `0x2000000`, all rejected here. Stock
   `classAS.method661` has further legacy branches too. The error propagates through
   SessionCodec -> ConnectionDecoder -> the native network worker's Protocol failure.
   This is a session failure, not evidence of a Rust panic or process crash. Port
   payload order, typed retention, cache/model policy and consumers together;
   expanding SUPPORTED alone would corrupt packet alignment.
2. **World mutations are consumed or deferred.**
   [zone.rs](../../../crates/protocol/src/openrune_240/zone.rs) walks all indexed
   enclosed payloads but only emits LOC_ANIM (index 3). Other payload bodies are
   skipped, including ground-item and location changes. Direct location add/delete/
   merge and specific ground-item packets fall through the game dispatch. GameState
   has no ground-item collection. Static location placement, var-driven morphs, location animation and a
   GameState ChangeLocation/override owner already exist; reuse that owner when
   wiring server replacement/removal.
3. **Interaction/dialogue is a narrow subset.**
   ClientIntent has ten variants, but variants are not packet counts: NpcAction and
   LocationAction each encode five operations, and Chat encodes two packets. Walk,
   minimap walk, widgets, close, window, heartbeat and map completion also exist.
   Pause/count/name/string/item resume, player/ground-item actions, targeted
   operations and IF_BUTTOND are missing. Add validation, selection lifetime,
   dispatch and consumers, not only encoders. Selected RSProt deprecates U packets
   for item targeting in favor of T paths; packet-name existence is not proof that
   every listed legacy packet must be emitted by current gameplay.
4. **Instances fail explicitly; many other packets have no semantic consumer.**
   SessionCodec returns `instanced rebuild is not supported` for opcode 12. Scene
   chunk helpers are not an integrated instance lifecycle. Var groups/clan vars,
   scripted camera, audio, player options/hints, specific effects, social and many
   widget mutations are Deferred. ConnectionDecoder ignores Deferred. Some Deferred
   branches deliberately update codec context (NPC origin, zone headers), so the
   enum value alone cannot prove a packet was wholly ignored. See the per-packet
   inventory and inspect each downstream path before claiming support.
5. **Decoded actor effects do not survive as active semantic state.**
   Spots, hits/headbars, timed overrides, freeze and rich target-facing remain
   partial/consumed/rejected across the adapters. Entity structs retain overheads,
   appearance, path/exact movement, action cursors and ordinary rotation. They lack
   complete active effect/target containers and consumers. Reset, replacement,
   removal/index reuse, events/replay/fingerprints and projection are part of each
   port obligation.

## Re-review of every major pasted claim

Paths below are relative to the repository; exact reviewed hashes and RSProt ZIP
entry names are in REVIEW-BASELINE.json. The selected release remains
`1.0.0-ALPHA-20260825`; the locally cached September release was not substituted.

| Pasted claim | Assessment | Evidence and corrected scope |
| --- | --- | --- |
| Turning uses the correct strict `> 1024` threshold | Confirmed for that branch | `game-state/src/actor_turning.rs::advance` and `classKN.method6539` agree. This does not prove the whole facing/turning scheduler complete. |
| NPC has 18 supported bits versus 26 stock checks | Substantially correct, count needs interpretation | Stock dispatch has 23 payload checks plus 3 extension markers. Rust's 18 allowed bits include markers and skipped bodies. Eight stock payload bits are rejected; only the three listed above are emitted by the selected writer. Other missing stock branches remain parity backlog, not proven random traffic from this server. |
| Player `0x2`, `0x200`, `0x80000` are missing and ordinary selected traffic crashes | Stock gap confirmed; selected-server frequency claim unsupported | `classDF.method3050` includes old chat, three menu strings and old spot-animation branches. The selected writer labels CHAT_OLD, NAME_EXTRAS and SPOTANIM_OLD unused/legacy and does not emit them. Rust rejects them if received. Track full stock parity separately; these are not the same immediate priority as selected NPC writer failures. |
| Enclosed zone data other than LOC_ANIM is discarded | Confirmed | `zone.rs::enclosed` reads payload widths, emits only animation index 3. Ground items and dynamic scenery need full state/consumer chains. |
| Widget model bodies never rasterize | Confirmed | `game-ui/src/widget_visuals.rs` explicitly leaves containers/models/missing assets without primitives. The model type/cache decoding does not provide chathead/equipment-preview rendering. |
| Audio has no production consumer and grows without bound | First half confirmed; growth claim false | `take_sound_effects` is used by tests only; `audio/src/lib.rs` is an empty subsystem shell. The script queue has SOUND_EFFECT_LIMIT=50 and clears on reset, so requests saturate and further requests are dropped; no unbounded-growth claim. Platform playback/music/ambient handling remains missing. |
| DB Long causes script failure, e.g. GE timestamps/account attributes | Explicit errors confirmed; examples unproven | `application/src/scripts/database.rs` rejects long values/defaults. Exact 240 `Friend` / `classBB` opcode-7502 code explicitly has a long `-1L` path for row=-1, while other branches still use int casts/default handling. Trace the invoked handler/bytecode and actual cache cases before claiming a universal long-column contract. VM long-stack support already exists. A script error is not a process crash. |
| F1-F12 cannot reach widget listeners; F3/F5/F11 are dev shortcuts | Confirmed input gap | `model/src/widget.rs::WidgetKey`, native `widget_key`, native key handlers. Add shared key semantics and shortcut arbitration while retaining script ownership of tabs. |
| 90% of outbound interactions are missing | Percentage rejected | Ten enum variants expand into multiple packets, packets have different roles, and some U packets are deprecated. Many necessary action families are absent, but no weighted interaction denominator supports 90%. |
| Var groups, camera/audio, player options/hints and specific effects are Deferred | Confirmed at dispatch boundary | `protocol/src/openrune_240/game.rs::receive`, `application/src/connection.rs::receive_inputs`, selected packet IDs/lengths. No live consequence/frequency for particular bosses/quests was reproduced here. |
| Instance rebuild disconnects | Confirmed native error path when opcode 12 arrives | Explicit protocol error propagates out of the worker. No instance-entry live run was performed. |
| Ground-item state is absent | Confirmed | No authoritative ground-item owner/operation in game-state/model; scene/menu/minimap/outbound effects are consequently incomplete. |
| Entity combat level is absent | Incorrect | `PlayerEntity.appearance: Option<PlayerAppearance>` retains `combat_level` in `model/src/entity.rs`. Reuse it for menu policy; do not duplicate the semantic field. Missing actor effects and rich facing are separate real gaps. |
| Menus only target nearest NPC/location, lack players/items/stacking/target modes | Confirmed scope gap | `apps/native/src/interaction.rs`, `application/src/interaction.rs`: nearest target and NPC/location menus remain subsets. New clickbox work in bd3aae10 does not create complete stacked menus or player/item actions. |
| Minimap mapfunction/item/friend/team dots absent | Confirmed for the minimap pipeline | Cache map_icon is decoded; scene minimap uses map_scene scenery and native minimap_dots emits NPC frame 1/player frame 2 only. World-map element rendering is a separate path. |
| Compass click does not reset north | Incorrect | `apps/native/src/widgets.rs` handles WidgetContent::Compass with camera.set_yaw(0), already present at 0a91c023. Live click/layout acceptance remains separate. |
| World map should be partial; pan/zoom/overview still absent | Partial status correct; remaining list stale | Loaders, CS2 host, viewport AND overview composition, native wheel zoom and drag/pan were present at 0a91c023. bd3aae10 adds load/error/reset handling and decoder repairs. Hover/element menus, target/smoothing/flash semantics, correct scale/labels and native/browser acceptance remain open. |
| Fix mask crashes by adding bits to whitelist | Unsafe as an implementation plan | Decode every corresponding payload in exact order and retain/apply its semantics first. Otherwise the next field is read at the wrong offset. |

## Other codebase and documentation findings

**Appearance drops valid inputs.** `player_appearance.rs` reads but discards
the transformed-NPC ID, skull/overhead bytes and customization slots. The semantic
PlayerAppearance retains combat level, but lacks the complete transform/override
representation. Extend this owner and appearance invalidation/scene consumers;
do not mistake general NPC morph/model support for transformed-player parity.
[PLAYER-APPEARANCE.md](PLAYER-APPEARANCE.md) records the exact source contract.

**World-map state ownership/lifecycle needs an audit.** `ClientScripts::world_map`
contains immutable assets alongside mutable selection, target coordinates, filters,
flashing and script-visible values. Native drawing advances flash_cycle, and input
mutates the same state; target_tile is assigned without a complete tick-driven
consumer. Classify presentation-only state versus replayable semantic state under
SPEC-0002/0019, extend the existing owner and specify any boundary correction before
adding another controller. Existing drawing/overview/pan code deserves partial
credit without establishing a complete source-equivalent lifecycle.

**Browser remains bootstrap-only.** Its app creates the renderer/canvas; shared
Wasm compilation and the JS-host plugin proof do not compose cache/session/world,
input/audio, shell panels and plugin execution into a playable browser client.

**GPU shell has changed mechanisms.** bd3aae10 integrates on-demand egui/wgpu work
under ADR-0007. Earlier Dioxus/Blitz CPU-retention measurements remain dated evidence
for that backend. They cannot accept the new backend's IME/focus/clipboard/control
behavior or prove its performance. See per-setting GPU/core ledgers for actual
remaining runtime and visual obligations.

**Documentation had conflicting authorities.** The old delivery summary still
called the native WIT/host/sample nonexistent and recommended a new 240 deob run;
those slices already exist. It also quoted obsolete ledger counts. The reference
index instructed readers to trust 235 for changes not listed in a partial diff.
These statements were removed from active guidance. Old milestone files now carry
historical-scope notices; their local validation/results remain evidence for their
recorded snapshots. The speculative HD feature menu is optional research.

**Inventory remains incomplete.** The initial checker report had 153 ledger rows:
67 verification-unrecorded, 33 source-reviewed, 51 contract-tested, 2 live-tested.
The declaration scan found 18,433 declarations, 18,342 without method-level ledger
triage. Aliases/injection and heterogeneous scopes prevent a port percentage.
This review updates source rows and pins previously unlisted ledger source files;
that repairs provenance coverage, not behavior. Full source tests/cases/assets,
script host-opcode and RuneLite feature/API dispositions still need inventories.

## Validation boundary and next work

Documentation checks validate paths, schemas, generated data and source pins. They
are not semantic tests. This review ran the port tools and documentation/link/
provenance checks; exact final results are recorded in the delivery status. It did
not repeat historical Cargo, GPU, Java, live-login, performance or visual results.

Follow batches 0-3 of the canonical roadmap first: freeze packet/mask/source-case
coverage, close selected valid-traffic failures and map lifecycle, preserve world
mutations, then connect full interactions/dialogue. Continue actor/UI/audio/social,
render/plugin and browser acceptance in the same shared owners. No missing feature
is excused as a Rust improvement; use the explicit deviations register.
