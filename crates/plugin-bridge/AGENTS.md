# Plugin Bridge Scope

This crate projects authoritative `GameState` (plus immutable cache definitions) into the public plugin API 0.2
snapshots and derives change events by diffing consecutive projections (ADR-0006).

- It owns no semantic state. The only retained value is the previous projection used for diffing, discarded on session end.
- Never mutate `GameState`; never expose internal structs through `rustclient-plugin-api` types.
- A fact the client does not retain yet is absent from the snapshot (`None`/empty), never guessed.
- Project only the categories the enabled plugins are granted.
