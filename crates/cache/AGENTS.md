# Cache Scope

This crate owns archive/container decoding and semantic asset/definition decoding.

- No GPU resource creation.
- No live entity/game-state ownership.
- Preserve typed source identity and useful decode context.
- Separate revision-specific binary layouts from semantic definitions.
- Custom private-server formats must be explicit profile extensions, not silent heuristics.
- Add small golden fixtures for every new decoder family.
