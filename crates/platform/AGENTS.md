# Platform Contract Scope

`crates/platform` owns dependency-light capability contracts, not platform implementations.

- Shared contracts must not expose filesystem paths, browser objects, `winit`, `wgpu`, Tokio, or JavaScript values.
- Use semantic keys/values and stable error categories at the boundary.
- Native implementations belong in `platform-native`; browser implementations belong in `platform-web`.
- Target-specific `cfg` belongs in adapters/composition roots, not semantic/domain crates.
- Do not add a transport abstraction until the protocol/runtime requirements that consume it are concrete.
- Capabilities should be injectable and testable; hidden global service locators are forbidden.
- Browser limitations must fail explicitly rather than silently pretending native behavior exists.
