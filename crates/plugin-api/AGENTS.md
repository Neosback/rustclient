# Public Plugin API Scope

Treat everything here as compatibility-sensitive.

- Do not expose internal `GameState`, renderer, cache decoder, protocol, or platform structs.
- Prefer stable semantic records/enums/IDs and explicit versioning.
- WIT is the intended external ABI source of truth once CP11 begins; Rust types must map deliberately.
- Design for batching rather than chatty per-object calls.
- Adding a field/method requires considering old plugins and web/native parity.
- Do not add privileged capabilities merely because a first-party plugin needs an easy shortcut.
