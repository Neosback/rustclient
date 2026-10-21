# WIT Interface Scope

This directory will become the canonical external plugin ABI definition.

- Interfaces are versioned contracts, not mirrors of internal Rust structs.
- Use semantic names and bounded/batched operations.
- Capabilities are split into least-privilege interfaces.
- Breaking changes require a major interface version or an explicit migration plan.
- Do not expose raw pointers, GPU handles, sockets, filesystem paths, or revision-specific packet structures to ordinary plugins.
