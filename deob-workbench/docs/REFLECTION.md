# Reflection and Runtime Hooks

Java reflection is a runtime access mechanism, not a semantic discovery mechanism.

## Boundary

The workbench derives candidate hooks from exact target bytecode. A future launcher may use `Class.forName`, `getDeclaredField` / `getDeclaredMethod`, and controlled accessibility to reach private client state, but only from a reviewed manifest containing the exact owner, member name, and descriptor.

Do not scan fields at runtime and choose one because its type or current value looks plausible. Obfuscated names are revision-local and can change meaning between builds.

## Current flow

```text
exact target jar
    -> BufferInfrastructureAnalyzer
    -> ReflectionHookDeriver
    -> CANDIDATE manifest
    -> control/descriptor verification (required before promotion)
    -> human review
    -> APPROVED manifest
    -> runtime reflection in a separate launcher/runtime layer
```

`reflection-hooks` currently emits candidates for buffer infrastructure such as the leading Buffer class, offset-like fields, bit-access owners, bit-index-like integer state, and object-state fields that could later prove to be cipher state. A `cipherState` logical label is explicitly not proof of ISAAC.

## Runtime rule

Reflection must never make the analysis jar behavioral authority and must never mutate the original gamepack as part of deobfuscation. Prefer reads for diagnostics/hooks. Any write-capable runtime tooling should be a separate, explicit capability with its own safety and revision checks.

## Control validation

Before carrying a structural hook rule to a new build, reproduce a known-good hook on a control build first. Exact class/member existence and descriptor checks are necessary but not sufficient; the structural derivation rule itself must also reproduce the known answer. If the control disagrees, stop instead of returning a plausible candidate.
