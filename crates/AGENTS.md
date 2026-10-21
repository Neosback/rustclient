# Shared Crates Scope

Each crate should have one clear semantic owner. Before adding a type or service, determine which crate owns its lifecycle and invariants.

- Do not solve Cargo cycles by creating a vague `common` crate.
- `model` is for stable dependency-light semantic primitives, not random helpers.
- Cross-crate communication should use typed domain values/events/commands.
- Platform APIs stay behind `platform`; external plugin ABI stays behind `plugin-api`/`wit`.
- Renderer and UI are consumers of state, never hidden mutation backdoors.
