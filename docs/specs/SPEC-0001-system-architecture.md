# SPEC-0001: System Architecture and Ownership

**Status:** Accepted for foundation work  
**Scope:** entire workspace

## Purpose

Define module ownership and dependency direction before implementation makes those boundaries expensive to change.

## Invariants

- There is one authoritative semantic game state.
- Bytes are not game state; decoded protocol operations are inputs to game state.
- GPU resources are not game state.
- Plugin snapshots are not game state.
- Revision-specific encodings do not leak into semantic domain APIs.
- Native/browser differences do not fork domain behavior.

## Workspace ownership

| Area | Owner | Must not own |
|---|---|---|
| shared semantic primitives | `model` | networking, GPU, OS handles |
| state transitions | `game-state` | sockets, cache byte parsing, GPU resources |
| runtime orchestration and deterministic runtime services | `application` | authoritative domain state, target-specific platform handles |
| packet encoding/decoding | `protocol` | renderer/UI behavior |
| archive/definition decoding | `cache` | live game state |
| loaded scene semantics | `scene` | platform/windowing |
| client script VM | `client-script` | native/browser APIs |
| GPU projection | `renderer` | authoritative entity state |
| in-game interfaces | `game-ui` | native shell configuration |
| application chrome | `shell-ui` | protocol logic |
| input semantics | `input` | revision packet encodings |
| platform contracts | `platform` | concrete OS/browser implementation |
| native platform | `platform-native` | semantic forks |
| browser platform | `platform-web` | semantic forks |
| public extension schema | `plugin-api` + `wit` | internal state structs |
| plugin policy/lifecycle | `plugin-host` | server protocol ownership |
| native plugin execution | `plugin-runtime-native` | plugin semantics |
| web plugin execution | `plugin-runtime-web` | plugin semantics |
| composition | `apps/*` | reusable domain logic |

## Command path

External stimuli are normalized into typed inputs. Domain validation occurs before committed transitions. State mutation is single-writer logically even if decoding/preparation is parallelized.

```text
bytes/input/time -> adapter -> DomainInput -> GameRuntime -> GameState transition
                                                   |
                                                   +-> StateDelta/EventBatch
```

Deterministic runtime services follow the same rule. For example, a random-dependent semantic transition receives explicit deterministic random input produced by application orchestration; it never reaches into OS/browser entropy or hidden global RNG state during the transition.

## Query path

Consumers query immutable views or snapshots appropriate to their boundary. Internal systems may use efficient borrowed views. Wasm plugins receive ABI-safe copied/serialized/component values.

## Dependency policy

A dependency-cycle between architectural layers is a design defect. Shared code is moved inward only when it is truly generic, not to silence Cargo cycles.

## Acceptance criteria

- Workspace crates exist with documented owners.
- Root/nested agent files reflect ownership.
- Native and web composition roots compile as empty shells.
- A future architecture checker has a documented edge allowlist.
- No crate introduces revision conditionals outside designated revision/profile modules without an approved exception.
