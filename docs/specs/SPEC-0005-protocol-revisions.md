# SPEC-0005: Private-Server Protocol and Revision Profiles

**Status:** Draft

## Goal

Support multiple OSRS-style private-server revisions without revision logic spreading throughout the client.

The first implemented compatibility baseline is revision `240`. The architecture must support adding more revisions later without turning revision `240` into a global assumption.

## Revision identity

`Revision` is distinct from server identity. It represents the protocol/cache compatibility era that a profile targets.

Conceptually:

```text
Revision
├── major: 240
└── subrevision: optional
```

The concrete semantic type may evolve during CP1, but callers must not rely on a process-wide `CLIENT_REVISION = 240` constant.

Only revisions with an implemented and validated profile appear in the supported-revision registry. RustClient does not claim broad historical compatibility merely because the registry can hold multiple revisions.

## Server profile concept

A `ServerProfile` selects compatible protocol/cache/client behavior for one server or server family. It references a `Revision` plus explicit codecs/tables/capabilities rather than becoming a bag of global flags.

Potential profile dimensions:

- profile ID and display name,
- protocol revision/family,
- login handshake/auth scheme,
- opcode tables,
- packet framing/length rules,
- cipher/ISAAC-like state where applicable,
- coordinate/region encoding,
- entity update masks,
- widget/script protocol extensions,
- cache revision/schema,
- custom server capabilities/extensions.

Two servers may target the same base revision while using different server profiles because of cache, extension or protocol differences.

## Revision selection

The runtime architecture must allow a user/application to select among supported revisions/profiles, similar in spirit to revision-targeted proxy tooling, without exposing unsupported revisions as working choices.

Initial implementation scope:

```text
SupportedRevisionRegistry
└── 240
```

Later:

```text
SupportedRevisionRegistry
├── 228
├── 229
├── ...
├── 240
└── newer validated revisions
```

Adding a revision is an implementation/testing event, not a data-entry exercise.

## Decode boundary

Network bytes -> framed packet -> revision decoder -> semantic `ServerOperation`.

Higher layers consume `ServerOperation` and semantic IDs/value objects. They do not inspect packet opcodes.

## Encode boundary

Semantic/local interaction -> validated outbound intent -> revision encoder -> framed bytes.

The domain should not construct packet buffers.

## Networking sequencing

Networking/login is intentionally deferred until semantic model, cache, scene, entities, UI and interaction contracts are sufficiently stable.

Before live networking exists, revision-specific behavior can be developed and tested with:

- pinned reference sources,
- cache fixtures,
- recorded state/replay fixtures,
- synthetic `GameState`,
- golden codec fixtures when protocol work begins.

This keeps networking from becoming the architecture owner of game semantics.

## Version modules

Prefer explicit modules/data tables for protocol families. Shared primitives can be factored only when byte semantics are truly identical.

Avoid inheritance-style revision chains where revision N mutates revision N-1 through dozens of overrides; they make provenance and testing difficult.

Revision-specific behavior must not leak into renderer, plugin API, generic game state or unrelated UI code.

## Extensions

Private servers may expose negotiated extensions. Extension IDs/namespaces must be explicit and capability-negotiated. Unknown required extensions fail connection clearly; unknown optional extensions are ignored safely.

## Reference evidence

Pinned source under `references/rustosrs/` may be used to research revision behavior, RuneLite semantics and OpenRune server expectations. Reference source is evidence only; RustClient specs and tests remain authoritative.

When behavior is ported from a reference, the implementation should capture the semantic rule in focused tests/fixtures rather than relying on continued source-code resemblance.

## Testing

Each supported profile requires golden fixtures for login/framing and high-value packet families once networking/protocol implementation begins. Encode/decode round trips are used where semantics are reversible. Fuzz/property tests target framing, lengths, masks, smart values, strings and malformed data.

Revision-registry tests must prove that unsupported revisions fail explicitly rather than silently falling back to `240`.

## Acceptance criteria

- Revision `240` can be the only initial implementation without becoming a global hard-coded assumption.
- `Revision` and `ServerProfile` remain separate concepts.
- Supported revision/profile selection is explicit and discoverable.
- Adding a new profile does not require renderer, plugin API or generic game-state conditionals for opcode differences.
- Unsupported revisions fail clearly instead of being coerced to the nearest known revision.
