# Plugin SDK Scope

Optimize for plugin-author ergonomics without widening host privilege.

- SDK wrappers may be convenient, but cannot become a second competing API contract.
- Generate/adapt from canonical WIT/public API definitions where practical.
- Examples must compile and demonstrate supported patterns rather than internal shortcuts.
- Plugin test helpers should prefer replay/synthetic event inputs over requiring a live server.
