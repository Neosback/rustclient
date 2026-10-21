# SPEC-0005: Private-Server Protocol and Revision Profiles

**Status:** Draft

## Goal

Support multiple OSRS-style private-server protocols without revision logic spreading throughout the client.

## Profile concept

A `ServerProfile` selects compatible protocol/cache/client behavior. It includes stable metadata and references to codecs/tables rather than becoming a bag of global flags.

Potential profile dimensions:

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

## Decode boundary

Network bytes -> framed packet -> revision decoder -> semantic `ServerOperation`.

Higher layers consume `ServerOperation` and semantic IDs/value objects. They do not inspect packet opcodes.

## Encode boundary

Semantic/local interaction -> validated outbound intent -> revision encoder -> framed bytes.

The domain should not construct packet buffers.

## Version modules

Prefer explicit modules/data tables for protocol families. Shared primitives can be factored only when byte semantics are truly identical.

Avoid inheritance-style revision chains where revision N mutates revision N-1 through dozens of overrides; they make provenance and testing difficult.

## Extensions

Private servers may expose negotiated extensions. Extension IDs/namespaces must be explicit and capability-negotiated. Unknown required extensions fail connection clearly; unknown optional extensions are ignored safely.

## Testing

Each supported profile requires golden fixtures for login/framing and high-value packet families. Encode/decode round trips are used where semantics are reversible. Fuzz/property tests target framing, lengths, masks, smart values, strings, and malformed data.

## Acceptance criteria

Adding a new profile must not require renderer, plugin API, or generic game-state conditionals for opcode differences.
