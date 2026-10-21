# SPEC-0009: Security and Capability Model

**Status:** Draft

## Threat model

RustClient may load third-party plugins and connect to private servers that are buggy or hostile. Network/cache/plugin inputs are untrusted.

## Principles

- parse defensively with explicit bounds/limits,
- deny plugin capabilities by default,
- isolate plugin storage by stable plugin ID,
- never expose account credentials/tokens to plugins unless an explicit future capability requires it,
- no raw packet/socket API for ordinary plugins,
- cap allocations/message sizes/decompression expansion where practical,
- redact secrets from logs,
- fail closed on malformed manifests or incompatible ABI versions.

## Plugin execution

Native Wasm runtime should use interruption/fuel/epoch/resource limits where practical. Browser plugins should run in worker isolation where feasible, with host-controlled termination/restart.

## Network extensions

HTTP access is an explicit high-trust capability. Private-server transport access is not automatically inherited by plugins.

## Interaction policy

Because private servers may intentionally allow automation or custom controls, interaction capabilities are profile/policy controlled rather than hard-coded to one public-game policy. The architecture still distinguishes observation from game-affecting interaction so server/client owners can enforce policy cleanly.

## Unsafe Rust

Workspace default denies unsafe code. A future need for unsafe code requires an ADR naming the exact crate/module, invariant, test strategy and audit boundary.
