# Reflection and Runtime Hooks

Java reflection is a runtime access mechanism, not a semantic discovery mechanism.

## Boundary

The workbench derives candidate hooks from exact target bytecode. A future launcher may use `Class.forName`, `getDeclaredField` / `getDeclaredMethod`, and controlled accessibility to reach private client state, but only from a reviewed manifest containing the exact owner, member name, and descriptor.

Do not scan fields at runtime and choose one because its type or current value looks plausible. Obfuscated names are revision-local and can change meaning between builds.

## Current flow

```text
known control jar + known-good hook manifest
        -> rerun structural derivation
        -> require unique reproduction of every supplied hook
        -> failure: withhold all target hooks
        -> success: derive target candidates
        -> CONTROL_VERIFIED manifest
        -> human review
        -> APPROVED manifest
        -> runtime reflection in a separate launcher/runtime layer
```

`reflection-hooks` emits unverified `CANDIDATE` hooks for buffer infrastructure such as the leading Buffer class, offset-like fields, bit-access owners, bit-index-like integer state, and object-state fields that could later prove to be cipher state. A `cipherState` logical label is explicitly not proof of ISAAC.

`reflection-hooks-controlled` accepts a known control JAR and a known-good control manifest. The same derivation rules must reproduce every supplied control hook uniquely. A SHA mismatch, missing hook, ambiguous logical hook, or wrong exact identity fails closed and produces no target hooks. If the control passes, target candidates are marked `CONTROL_VERIFIED`; that is still not equivalent to human semantic approval.

```bash
java -jar target/client-deob-workbench-0.1.0-SNAPSHOT.jar reflection-hooks-controlled \
  --control-jar /path/to/known-build.jar \
  --control-hooks /path/to/known-good-hooks.json \
  --jar ../references/gamepacks/osrs-240/injected-client-1.12.39.jar \
  --out .cache/rev240/reflection-hooks-controlled.json
```

## Runtime rule

Reflection must never make the analysis jar behavioral authority and must never mutate the original gamepack as part of deobfuscation. Prefer reads for diagnostics/hooks. Any write-capable runtime tooling should be a separate, explicit capability with its own safety and revision checks.

A runtime probe should also verify the exact target JAR hash before resolving hooks. A hook manifest approved for one build must not be reused just because another build has the same obfuscated member names.
