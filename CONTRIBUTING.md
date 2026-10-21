# Contributing

RustClient is spec-driven while foundational contracts are still being established.

Before a cross-cutting implementation change:

1. read `ARCHITECTURE.md` and nearest `AGENTS.md`,
2. find the owning spec/ADR,
3. search for an existing semantic owner,
4. update the spec first when behavior/boundaries change,
5. implement with focused tests/fixtures,
6. run the validation tier honestly and record what actually ran.

Do not add duplicate models/services to avoid understanding an existing subsystem. If ownership is wrong, refactor ownership explicitly.
