# Plugin Host Scope

This crate owns plugin lifecycle, compatibility, capabilities, event/query mediation, command validation, budgets and fault isolation.

- Runtime-specific execution belongs in `plugin-runtime-native` / `plugin-runtime-web`.
- Deny external capabilities by default.
- Validate every plugin-originated command before it reaches core systems.
- One plugin failure must not corrupt or stall authoritative state.
- Keep plugin-local storage/config namespaced by stable plugin ID.
- Record actionable diagnostics for compatibility failures, traps, budget overruns and denied capabilities.
