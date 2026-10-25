# Injected API Bridges

RuneLite's `injected-client` contains a second kind of evidence in addition to the original game methods: API bridges injected onto obfuscated game classes. These bridges are useful semantic anchors because they often expose a clean API concept through a very small bytecode wrapper around one exact field or method.

## Authority boundary

The target jar remains the bytecode artifact being analyzed. Original game-method instructions define game behavior. RuneLite-injected getters, setters, and invokers are treated as semantic/identity evidence about exact members inside that artifact; they do not redefine the behavior of unrelated game methods.

A cleaned/decompiled copy remains presentation only.

## Exact bridge extraction

`InjectedApiBridgeAnalyzer` scans classes implementing or inheriting `net/runelite/rs/api/*` interfaces and considers public semantic-looking methods. It only emits deliberately small shapes:

- one exact field read followed by return;
- one exact integer/long field read, one multiplier, then return;
- one exact field write from an injected setter;
- one exact integer/long field write with one multiplier;
- one exact downstream method call with no field accesses.

Complex wrappers, mixin-derived behavior, multi-field methods, and other ambiguous methods are left unclassified rather than guessed.

## Multiplier evidence

For integer and long fields, an injected getter can expose the decoder applied to raw obfuscated storage. A setter can expose the encoder. `InjectedSemanticAnchorAnalyzer` pairs getter/setter bridges by exact target field and semantic term.

When both constants exist, it proves:

```text
getterDecoder * setterEncoder == 1 mod 2^32
```

for `int`, or modulo `2^64` for `long`.

The resulting confidence tiers distinguish exact API linkage from arithmetic proof:

- `EXACT_GETTER_BRIDGE`
- `EXACT_SETTER_BRIDGE`
- `EXACT_GETTER_SETTER_BRIDGE`
- `INJECTOR_MULTIPLIER_PROVEN`
- method invokers use `EXACT_SINGLE_CALL_BRIDGE`

These labels are evidence confidence, not permission to auto-write mapping shards.

## CLI

```bash
java -jar target/client-deob-workbench-0.1.0-SNAPSHOT.jar injected-bridges \
  --jar ../references/gamepacks/osrs-240/injected-client-1.12.39.jar \
  --out .cache/rev240/injected-bridges.json

java -jar target/client-deob-workbench-0.1.0-SNAPSHOT.jar injected-anchors \
  --jar ../references/gamepacks/osrs-240/injected-client-1.12.39.jar \
  --out .cache/rev240/injected-anchors.json
```

## Semantic dossier fusion

`actor-semantic-dossier` now includes the exact injected field/method anchors in its output. If a pinned RuneLite vocabulary term matches the same exact field and semantic term, the injected bridge contributes more weight than a broad behavior-cluster heuristic. A proven getter/setter inverse pair contributes the strongest injected-field bonus.

The intended chain is:

```text
exact PLAYER_INFO / NPC_INFO path
    -> mask-controlled exact field write
    -> behavior cluster + mask lineage
    -> injected getter/setter for the same exact field
    -> pinned historical RuneLite vocabulary
    -> reviewed semantic mapping
```

## Recaf and decompilers

Recaf remains useful as an interactive researcher UI for browsing ASM, searching symbols, and prototyping transforms, but it is not a required runtime dependency of this workbench. Any useful scripted analysis should be promoted into deterministic Java/ASM passes here.

Vineflower/CFR output is for human reading. Evidence extraction uses exact bytecode identities instead of decompiler line numbers or renamed local variables.

## Cleanup warning

Do not delete synthetic/bridge/injected methods before this pass runs: those wrappers are evidence. Likewise, destructive dead-code or unused-member cleanup must never replace the exact target corpus used for behavior analysis.
