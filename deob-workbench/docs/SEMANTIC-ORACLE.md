# Historical RuneLite Semantic Oracle

RuneLite is used here as a vocabulary source, not as behavioral authority for the target gamepack.

## Pinned snapshot

The rev-240 semantic oracle is the official RuneLite **1.12.36 release** commit:

```text
57b8d715707c0db4b3aac6e857790fcf325c9ce8
```

This commit is pinned in `config/rev240-sources.json`. Curated Actor vocabulary is stored separately in `config/runelite-1.12.36-actor-vocabulary.json` so later analysis can name the exact external source and commit that contributed a term.

The initial vocabulary includes concepts such as server-side/world location, client/local location, target and current orientation, primary animation, pose animation, animation frames, walk/run/idle pose animation, spot animations, overhead text/cycles, death state, footprint size, and animation height offset.

## Authority boundary

A RuneLite API name can answer **what vocabulary exists at this historical point**. It cannot prove that a particular obfuscated field in 1.12.39 has that meaning.

A field proposal therefore requires target-bytecode evidence first. `actor-semantic-dossier` currently fuses:

1. exact target update-mask behavior;
2. an Actor behavior-cluster hypothesis;
3. JVM descriptor compatibility with a pinned vocabulary term;
4. cross-build mask lineage where available;
5. the exact semantic source id and commit.

The output remains a proposal. It never writes mapping shards automatically.

## Why 1.12.36

Revision 240 is represented by RuneLite builds around 1.12.34 through 1.12.36-SNAPSHOT in the structural evidence already used by this project. The final official 1.12.36 release is a stable semantic snapshot close to that protocol family and is preferable to current master, where API concepts may have changed.

## Promotion rule

Do not promote a proposal merely because it scores highly. A final readable mapping should also be consistent with neighboring-revision lineage, packet read/value flow, surrounding calls, decoder evidence where relevant, and human review of the exact target instructions.
