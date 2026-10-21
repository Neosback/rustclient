# Protocol Scope

This crate owns private-server wire formats and revision/profile adapters.

- Raw opcodes, masks, endian/transforms, ciphers and packet lengths stay here.
- Convert wire data to semantic operations before crossing into game state.
- Convert semantic outbound intents to bytes here.
- Never mutate `GameState` directly from a decoder.
- All reads are bounds-checked; malformed network data returns contextual errors, not panics.
- New revisions need fixtures and should not add `revision == X` branches outside profile modules.
