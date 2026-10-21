# ADR-0003: Platform Capabilities Behind Shared Contracts

**Status:** Accepted

## Decision

Native/browser facilities are implemented behind capability-oriented interfaces. Domain/protocol/cache/game-state semantics cannot depend directly on OS/browser frameworks.

## Consequences

The browser is a first-class target rather than a later port. Some adapters differ substantially, especially transport/storage/threading, while shared client semantics remain one source.
