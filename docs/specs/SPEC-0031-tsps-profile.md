# SPEC-0031: TSPS 241 migration

**Status:** Accepted for implementation, 2026-11-06; gameplay acceptance incomplete.
**Owners:** protocol/cache (profiles), application/game-state (shared semantics), platform adapters (transport).

## Goal and authority

TSPS is the primary development target; OpenRune 240.2 remains available during migration.
The TSPS baseline is `ac7b52a21df137cbd6c011c73fd2f173cbc274d6`, with configured cache
`osrs-241_2026-10-20`. Cache identity and wire compatibility are independent evidence.
TSPS defines its own wire contract and extensions. Stock evidence defines classic behavior;
revision-240 evidence does not establish an unexamined revision-241 difference.

## Invariants and interfaces

- `ServerProfile` selects authentication, framing and codecs explicitly; `CacheProfile` selects
  compatible assets. This migration snapshot validates cache 2746 reference metadata before title/login; modified caches require an evidenced profile update. Never infer either from a directory name or silently fall back to 240.
- TSPS binary packets use the existing WebSocket endpoint. Platform transports deliver ordered
  bytes; profile adapters own packet boundaries and translate to shared semantic updates/intents.
- Only the shared cycle driver commits gameplay state. Transport and cache workers carry explicit
  session/scene identities; cancellation and stale completion cannot resurrect retired state.
- Open menus own canvas input outside the world viewport. Rendering and hit-testing use the same
  coordinate transform; foreground input never leaks to world or camera actions.
- Unsupported valid packets/operations remain explicit gaps. No neutral script handlers, guessed
  actor lifetimes, discarded required fields, or presentation nudges establish compatibility.
- Plugin records remain versioned immutable projections. No engine, socket or GPU handles escape.
- Native and browser select available rendering capabilities automatically. Optional enhancements
  cannot silently reduce classic draw distance, change authored geometry or alter interaction rules.

## Failure behavior

Bound packet sizes and buffered data before allocation. Reject malformed frames, invalid field
ranges, incompatible caches and unsupported required behavior with profile/stage context and no
credentials or raw payloads. Rejection, cancellation, disconnect and process failure remain distinct.
Process a complete packet before publishing any of its semantic updates.

Varbit updates carry cache-resolved `VarpBitRange` inputs to GameState, which merges live
and server mirrors independently. The protocol owns no live variable mirror. Script and mount
preconditions precede their script/mount effects in packet order; unavailable definitions or
unrepresented widget-scoped script context fail before publishing any part of that packet.
Script inventory prerequisites have a combined 16,384-slot budget per packet, checked before
allocation; compact wire payloads cannot expand into hundreds of maximum-sized containers.
Exceeding the profile budget fails explicitly, without publishing prerequisite effects.

## Build and verification policy

Normal play retains optimized gameplay and external plugins. The default development profile
prioritizes rebuild speed; diagnostics require an explicit feature and participate in full checks.
Small semantic/codec tests run without source-research trees. Full source regeneration and manual
visual acceptance are separate, reproducible operations; no passing build implies visual parity.

## Acceptance

First native milestone: login/rejection, scene and actors, walking, object/NPC interactions,
inventory actions, interfaces/dialogues, ground items, chat, logout and clean restart. Test packet
fragmentation/coalescing, malformed data, session reset, actor index reuse, widget replacement,
floating menus, scaled coordinates and cancellation. Retain OpenRune codec/regression contracts.
Record actual source/cache identities, exact commands and PASS/FAIL/NOT-RUN results. Measure builds
and unchanged-quality runtime workloads separately; unrun live/visual cases remain unverified.

## Non-goals

Playable browser gameplay, continuous movement, HD implementations and full RuneLite feature parity
are later milestones. Existing render-profile negotiation is their extension boundary. This spec
does not import TSPS decoration corrections, priority-to-depth mappings, effect-height lifts,
neutral script stubs or mutable engine injection as classic-client behavior.

Fast development commands disable the optional external Wasm executor (`--no-default-features`); built-in plugins remain available. Normal `cargo play` and default builds retain external Wasm plugins.
