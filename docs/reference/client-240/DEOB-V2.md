# Client-240 deob v2: exact-bytecode deobfuscation workbench

Status: implementation branch. This design extends the existing mapping and verification pipeline. It does not replace tiny-v2 mapping shards, the evidence ledger, or the rename-only proof.

The goal is not merely to produce Java that looks cleaner. The goal is to make the revision-240 client **fully inspectable** while preserving a permanent route from every readable view back to the exact shipped bytecode.

The core rule is simple:

> **Original bytecode defines behavior. Semantic names identify it. Analysis passes explain it. Decompiled Java presents it. Human-reviewed mappings name it.**

No later layer is allowed to silently replace an earlier authority.

## Why the current pipeline needs another layer

The existing client-240 workflow is strong at proving that a rename is mechanically safe, but weaker at proving that the evidence used to choose a name or describe behavior is faithful to the shipped gamepack.

The older research flow is effectively:

```text
original injected jar
        |
        | rl-suite semantic transforms
        v
semantic jar
        |
        +--> Vineflower source ----------------------> human interpretation
        |
        +--> tiny-v2 readable overlay --> Vineflower -> readable source
        |
        `--> CFR only when a researcher notices suspicious source
```

That flow has four important failure modes.

### 1. The semantic jar is not the exact original control flow

The pinned semantic audit records deliberate transformations before a naming wave starts. For revision 240 it reports, among other changes:

- 329 packet-handler instructions removed;
- 165 packet return jumps flattened;
- packet-handler try/catch regions reduced from 1,640 to 10;
- three ConstantDynamic string-concatenation sites materialized;
- decoder normalization and unused-member marking;
- structural symbol renaming.

These are useful analysis transforms, but they make the semantic jar unsuitable as the sole behavioral authority for protocol framing, player/NPC update masks, movement cycles, animation behavior, exception-sensitive behavior, or any client quirk that depends on exact control flow.

**v2 rule:** the original gamepack is permanent behavioral ground truth. The semantic jar is an identity/readability input. Any transformed method is surfaced before a researcher treats its reconstructed source as behavioral evidence.

### 2. Decompiled Java is too close to being the data model

Source `grep`, reconstructed labels, inferred expressions, and decompiler-generated source structure should not decide reference ownership or bytecode identity.

Source text can obscure:

- descriptor-only dependencies;
- inheritance and interface edges;
- bootstrap handles and invokedynamic references;
- annotations and signatures;
- exception edges;
- subclass families;
- references hidden by expression reconstruction;
- references in code a decompiler chooses to inline or restructure.

**v2 rule:** reference sets, ownership boundaries, member identity, call edges, field edges, control-flow metrics, fingerprints, and future value-flow evidence come from bytecode analysis. Java source is presentation evidence.

### 3. Decompiler disagreement is discovered too late

Vineflower is currently the normal renderer and CFR is used when a researcher notices suspicious output. That detects obvious failures, not silent differences.

**v2 rule:** difficult classes can be rendered through both pinned decompilers, from both exact and transformed views, and shown beside bytecode evidence. Neither renderer is declared correct merely because its output looks cleaner.

### 4. Cross-revision carry-forward needs stronger structure

`carry235.py` already learned that field/method order is unreliable and moved toward unique normalized signatures. Descriptors, access, instruction count, and call count are still not enough entropy for difficult obfuscated methods.

**v2 rule:** cross-revision candidates can consume rename-invariant bytecode fingerprints, CFG shape, value-flow signatures, constants, member-reference identities, decoder evidence, packet evidence, and virtual-family structure. These are proposal features only. No automated match may directly write a readable mapping.

---

# Upstream design review

Two existing projects contain valuable work that should inform this system:

- `Tonic-Box/rl-suite`
- `melxin/devious-client/deobfuscator`

The correct approach is **selective adaptation**, not replacing this repository's toolchain with either project wholesale.

## What to take from `Tonic-Box/rl-suite`

`rl-suite` is especially useful because the pinned semantic artifact already comes from that lineage and its transform pipeline is close to the problems we are solving.

### Adopt the architecture ideas

1. **Analyze first, apply later**
   - A pass first inspects untouched input and produces a plan.
   - Mutation is a separate phase.
   - This prevents one readability transform from contaminating the evidence another pass uses.

2. **Decoder confidence**
   - Preserve a distinction equivalent to `STATED`, `PROVEN`, and `INFERRED`.
   - A read/write modular-inverse pair can be proven.
   - A one-sided arithmetic pattern can be useful evidence without being treated as fact.
   - Conflicting candidates should remain unresolved rather than being guessed.

3. **Hierarchy-aware remapping**
   - A rename must understand inherited members, owner resolution, collisions, and descriptors.
   - Renaming must not be a string replacement over decompiled Java.

4. **Virtual method families**
   - One virtual method identity can span an interface/base/subclass override family.
   - The family should be named coherently and reviewed as one behavioral contract.

5. **Revision packet profiles**
   - Packet identity and lengths are revision data, not timeless assumptions.
   - Revision-specific packet tables should be first-class evidence alongside bytecode discovery.

6. **Audit reporting**
   - Every analysis transform should say what it inspected, what it changed, and why.
   - The workbench should expose this per method, not hide it in build logs.

### Keep these isolated from behavioral truth

`rl-suite` also demonstrates why v2 needs separate views. Readability passes such as packet-handler normalization and ConstantDynamic materialization are useful, but they can alter control-flow shape, exception layout, or initialization timing.

Those passes belong only in `analysis-normalized`. They never replace `original-exact`.

### Code reuse boundary

At the time this design was reviewed, a root license was not found in the `Tonic-Box/rl-suite` repository listing. Until licensing is explicitly clarified, this project treats `rl-suite` as a design and behavior reference rather than copying implementation code into RustClient.

## What to take from `melxin/devious-client/deobfuscator`

`devious-client` is the stronger source for deeper deobfuscation algorithms. Its deobfuscator contains mature ideas around control flow, symbolic execution, arithmetic, packets, and cross-revision mapping.

Its repository carries a BSD 2-Clause license, so selective code reuse is possible when the license and attribution requirements are retained. Even so, direct copying is not automatically the best architecture.

### Adapt these algorithms

1. **Control-flow graph construction**
   - basic blocks;
   - normal branch edges;
   - switch edges;
   - exception edges;
   - loop/back-edge analysis;
   - future dominator/post-dominator information.

2. **Symbolic execution and value provenance**
   - model operand-stack and local-variable flow;
   - trace values into method arguments, fields, comparisons, packet reads, and arithmetic;
   - expose provenance as evidence instead of relying on decompiler expression shape.

3. **Modular arithmetic analysis**
   - detect field encoders/decoders;
   - prove inverse pairs where possible;
   - distinguish proven from inferred multipliers;
   - optionally render a decoded analysis view while keeping original arithmetic visible.

4. **Packet discovery**
   - packet type discovery;
   - packet length evidence;
   - packet read ordering;
   - handler association;
   - C2S opcode/framing discovery;
   - ISAAC-related call-path evidence.

5. **Cross-revision mapping**
   - class/member mapping from multiple independent features;
   - method execution/call-shape matching;
   - static-initializer structure;
   - descriptor and signature compatibility;
   - ambiguity reporting instead of forced one-to-one guesses.

6. **Constant-parameter and expression analysis**
   - identify parameters that are effectively constants at all relevant call sites;
   - normalize expression evidence for comparison without claiming the normalized expression is original syntax.

### Do not import the entire Devious bytecode model

The Devious deobfuscator has its own classfile/instruction/execution abstraction. RustClient already pins ASM 9.6 and the v2 evidence layer is ASM-native.

Bringing in the entire Devious IR would create two competing bytecode representations and duplicate:

- class/member identity;
- descriptors;
- instructions;
- CFG state;
- value-flow state;
- hierarchy resolution.

Instead, port the useful algorithms onto ASM tree nodes and ASM analysis primitives. If a specific BSD-licensed implementation is copied rather than reimplemented, preserve attribution in the source and repository license documentation.

### Do not make destructive cleanup part of authoritative research

Passes that remove unused classes, unused fields, unused methods, runtime exceptions, or unreachable structures can make a jar nicer to inspect, but they also destroy evidence.

For this project:

- detect unused/reachable state;
- annotate it;
- filter it in presentation if useful;
- do not delete it from the authoritative evidence corpus.

## Comparison and decision matrix

| Capability | Existing v2 | `rl-suite` | Devious deobfuscator | RustClient decision |
| --- | --- | --- | --- | --- |
| Exact original-bytecode authority | yes | semantic transform output is primary product | deob pipeline mutates a working model | keep v2 original-exact authority |
| ASM-native model | yes | yes | custom bytecode model | stay ASM-native |
| Analyze-before-transform pass plans | partial | strong | pass-oriented but different model | adopt explicitly |
| Decoder confidence/evidence | not yet | strong | strong arithmetic analysis | combine concepts on original bytecode |
| Hierarchy-aware rename | current remapper + planned v2 layer | strong | strong | add explicit hierarchy/virtual-family evidence |
| Revision packet profiles | pinned TSVs | strong | packet discovery logic | use both profile data and bytecode discovery |
| CFG evidence | metrics only | limited for our purpose | strong | port concept to ASM |
| Symbolic value flow | not yet | limited | strong | port concept to ASM |
| Modular arithmetic analysis | semantic jar contains normalization | DecoderTable | ModArith/multiplication passes | detect on exact view; normalize only in optional view |
| Packet handler discovery | packet tables + manual tracing | revision normalization | strong packet finders | build evidence-only discovery |
| Cross-revision mapper | canonical fingerprints + carry235 | structural rename support | strong updater/mappers | build ranked multi-feature mapper |
| Dual decompiler | yes | not the central model | source output is downstream | keep Vineflower + CFR ensemble |
| Original-vs-transformed drift | yes | audit transform counts | not our current invariant | keep and expand v2 drift audit |
| Searchable research UI | now specified/implemented | no equivalent required | no equivalent required | keep local static workbench |

---

# Five-view architecture

The rebuilt system has five explicit views. Every artifact and pass declares which view it consumes and produces.

```text
                         READ-ONLY EVIDENCE

              +----------------------------------+
              | 1. original-exact                |
              | shipped gamepack bytecode        |
              | ONLY behavioral authority        |
              +----------------+-----------------+
                               |
                identity links | ASM evidence
                               v
              +----------------------------------+
              | 2. identity                      |
              | obfuscated identity              |
              | classXX / fieldN / methodN       |
              | readable tiny-v2 overlay         |
              +----------------+-----------------+
                               |
                 optional only |
                               v
              +----------------------------------+
              | 3. analysis-normalized           |
              | proven decoder presentation      |
              | optional CFG cleanup              |
              | packet/condy transforms if needed|
              | NEVER behavioral authority       |
              +----------------+-----------------+
                               |
                               v
              +----------------------------------+
              | 4. readable                      |
              | evidence-backed names            |
              | owner/family-aware symbol view   |
              +----------------+-----------------+
                               |
                               v
              +----------------------------------+
              | 5. presentation                  |
              | Vineflower | CFR | CFG | tables  |
              | static HTML workbench            |
              +----------------------------------+
```

## 1. `original-exact`

The immutable shipped gamepack is the answer to:

- what instructions execute;
- what branches exist;
- what exceptions can occur;
- what order packet reads occur in;
- what arithmetic actually happens;
- what initialization timing exists;
- what quirks the real client contains.

Only evidence-only passes may operate on this view.

## 2. `identity`

This view answers:

- which original obfuscated class/member corresponds to a stable research symbol;
- which virtual family a method belongs to;
- what readable name has been accepted;
- what evidence ledger row supports that name.

Renames may change names, never instruction behavior.

## 3. `analysis-normalized`

This is intentionally expendable. It may contain easier-to-read forms such as:

- proven multiplier decoding;
- optional CFG cleanup;
- packet-handler normalization;
- ConstantDynamic materialization.

Every changed method must retain a route to the original method identity and a transform record.

## 4. `readable`

Readable names are applied as an overlay, not as a new behavioral source. A Java class is still not assumed to map one-for-one to a Rust type.

## 5. `presentation`

Presentation can include:

- Vineflower source;
- CFR source;
- bytecode instruction facts;
- CFG diagrams;
- reference graphs;
- value-flow traces;
- packet read sequences;
- decoder evidence;
- cross-revision candidates;
- transform warnings;
- mapping evidence;
- static HTML navigation.

Presentation can be regenerated at any time.

---

# Pass safety model

The machine-readable registry is:

`tools/client-240/deob-passes.json`

Every pass has one of five safety classes.

| Safety | Meaning | May define behavior? |
| --- | --- | --- |
| `evidence-only` | reads bytecode and emits facts | yes, when reading `original-exact` |
| `identity-safe` | changes names/metadata presentation only | no independent behavior claim |
| `analysis-equivalent` | intended semantics-preserving rewrite that changes instruction representation | no, verify against original |
| `analysis-only` | readability transform that may affect behaviorally relevant representation | no |
| `excluded-destructive` | removes or irreversibly hides evidence | never used in authoritative research |

This gives us something the previous workflow lacked: an aggressive deob can exist without asking researchers to remember which pieces are safe.

## Planned evidence passes

### `decoder-evidence`

Use the best ideas from `rl-suite` decoder confidence and Devious modular arithmetic.

For every numeric encoded field, retain:

- field identity;
- read multiplier candidates;
- write multiplier candidates;
- modular inverse proof where it exists;
- every cited instruction site;
- confidence (`stated`, `proven`, `inferred`, `conflict`);
- whether optional normalization is permitted.

Default policy: only proven inverse pairs may be rewritten in `analysis-normalized`. Inferred values remain annotations.

### `cfg-evidence`

For each original method emit:

- instruction-indexed basic blocks;
- predecessors/successors;
- conditional edges;
- fallthrough edges;
- switch edges and keys;
- exception-handler edges;
- loop/back edges;
- unreachable blocks without deleting them;
- future dominator/post-dominator information.

The CFG is more reliable than a decompiler's labelled-break syntax for understanding player/NPC update loops.

### `symbolic-flow-evidence`

Build a lightweight ASM-based value provenance engine, influenced by the useful ideas in Devious `Execution`, `Frame`, and `InstructionContext`.

It should answer questions such as:

- Which packet-buffer read produced this mask bit?
- Which field receives this decoded value?
- Which comparison controls this animation reset?
- Which constants and arguments feed this coordinate calculation?
- Does a value pass through an encoder/decoder before storage?
- Which call sites supply a constant parameter?

The output is a graph/trace, not rewritten Java.

### `packet-discovery`

Combine three independent evidence sources:

1. revision-240 packet tables;
2. original exact bytecode discovery;
3. known protocol evidence such as RSProt where selected by the research tree.

The output should record:

- packet identity;
- direction;
- opcode/framing path;
- ISAAC relationship;
- declared length where known;
- actual buffer read sequence;
- handler method;
- update-mask dispatch;
- cycle/world-view routing;
- conflicts between tables and bytecode.

A conflict is surfaced, not silently reconciled.

### `cross-revision-mapper`

Replace single-feature carry-forward with a ranked, explainable matcher.

Candidate features can include:

- obfuscated identity where available;
- descriptor compatibility;
- superclass/interface neighborhood;
- virtual family structure;
- canonical bytecode fingerprint;
- CFG fingerprint;
- constant/string signature;
- field-use signature;
- call-neighborhood signature;
- decoder signature;
- packet role;
- static-initializer evidence;
- value-flow signature.

The mapper outputs candidates and independent scores. It never edits a mapping shard.

---

# Existing v2 tooling

## `Client240Evidence.java`

This repository-local ASM program is deliberately separate from the existing verified rename path.

Commands:

```text
index JAR OUT.tsv
    Build a bytecode-first class/member/reference index.

audit ORIGINAL.jar SEMANTIC.jar OUT.tsv
    Match methods through preserved obfuscated identity and report drift.

refs JAR CLASS [CLASS...]
    Print exact incoming/outgoing class references from bytecode evidence.
```

The index contains strict and canonical method fingerprints. Canonical references use preserved obfuscated identities where possible so a pure rename does not manufacture a structural difference.

## `decompile-ensemble.py`

A class-focused source renderer using both pinned decompilers.

A dossier can retain:

```text
semantic / Vineflower
semantic / CFR
original / Vineflower
original / CFR
bytecode drift
reference evidence
command/tool manifest
```

Decompiler disagreement is evidence to inspect bytecode, not a vote where two tools outnumber the original jar.

## `deob-v2.py`

The orchestration CLI now exposes both evidence production and the architecture itself:

```text
python3 tools/client-240/deob-v2.py plan
python3 tools/client-240/deob-v2.py index
python3 tools/client-240/deob-v2.py audit
python3 tools/client-240/deob-v2.py refs classDF classDG
python3 tools/client-240/deob-v2.py dossier classDF --name player-sync
python3 tools/client-240/deob-v2.py workbench
```

`plan` prints the declared views, safety classes, pass status, and defaults.

`index`, `audit`, and `refs` stay bytecode-first.

`dossier` explicitly opts into source presentation.

`workbench` renders generated evidence to an offline HTML artifact.

## `render-workbench.py`

The workbench is the answer to “let us fully read and see everything” without building another source of truth.

It is a static, dependency-free HTML renderer over generated evidence. The first version exposes:

- corpus counts;
- original class identity;
- semantic class identity;
- original-vs-semantic method drift;
- exact original reference edges;
- pass safety/status/defaults;
- generated dossiers;
- upstream design provenance and license boundary;
- global table filtering/search.

Future evidence panels should add:

- method instruction view with stable instruction indices;
- CFG visualization;
- symbolic value-flow traces;
- decoder/multiplier sites;
- packet read timeline;
- virtual-family browser;
- cross-revision candidate comparison;
- tiny-v2/ledger linkage;
- side-by-side Vineflower/CFR source;
- original versus normalized instruction diff;
- direct stable-symbol to original-obfuscated identity navigation.

The workbench itself is disposable. Its evidence files remain the machine-readable source.

---

# Method fingerprints

Fingerprints are research accelerators, not naming tiers.

Each indexed method gets:

- `strict_hash`: opcode and operand stream as represented in that jar;
- `canonical_hash`: identity-normalized instruction stream using preserved obfuscated identities where possible.

The canonical stream includes:

- opcode;
- primitive immediates and stable literals;
- field/method reference kind, canonical owner, canonical member identity, normalized descriptor;
- canonical type references;
- invokedynamic name/descriptor and bootstrap identity;
- branch target as real-instruction index;
- lookup/table switch keys and destinations;
- multi-array dimensions;
- exception table ranges, handlers, and caught type.

It excludes:

- line numbers;
- local-variable names;
- stack-map frames;
- source file names;
- constant-pool slot numbers;
- decompiler syntax.

Future versions should add independent CFG and value-flow hashes rather than making `canonical_hash` an overloaded universal similarity score.

---

# Original-versus-semantic drift

The existing v2 audit classifies method drift with flags such as:

- `instruction-count-changed`
- `branch-shape-changed`
- `switch-shape-changed`
- `try-catch-changed`
- `invoke-set-changed`
- `field-set-changed`
- `constant-set-changed`
- `canonical-bytecode-changed`
- `missing-original`
- `missing-semantic`

For protocol and entity-sync research, any control-flow, exception, call-set, field-set, or constant-set difference is a reason to inspect the original method before specifying behavior.

The long-term workbench should also show the **specific original instruction regions** affected by each normalized transform.

---

# Naming policy

The existing T1-T4 evidence tiers remain. v2 changes the screening and research environment around them.

1. T1/T2 evidence remains strongest.
2. T3 carry-forward should require multiple compatible structural features, not one score.
3. T4 behavior names cite exact original method identity when the semantic method drifted.
4. Decompiler disagreement lowers confidence until reconciled against bytecode.
5. Ownership/reference sets come from the bytecode graph, not source `grep`.
6. Virtual-family names are reviewed across the whole override family.
7. Decoder normalization never creates a readable name by itself.
8. Cross-revision mapping emits proposals only.
9. No automated score writes tiny-v2 mapping shards.
10. A readable name must always remain traceable to the stable symbol and original obfuscated identity.

---

# Target research workflow

For a difficult subsystem such as player updating:

```text
1. select subsystem / candidate classes
              |
              v
2. exact bytecode reference graph
              |
              v
3. original-versus-semantic drift
              |
              +--------------------+
              |                    |
              v                    v
4. exact CFG/value flow      5. readable identity
              |                    |
              +---------+----------+
                        v
6. packet + decoder + virtual-family evidence
                        |
                        v
7. Vineflower/CFR dossier if human source helps
                        |
                        v
8. workbench inspection
                        |
                        v
9. human evidence decision
                        |
                        v
10. tiny-v2 shard + ledger + behavior write-up
```

This is intentionally evidence-first. Decompilation happens late enough that reconstructed syntax cannot silently define the model.

---

# Roadmap

## Phase A: foundation - current branch

Implemented or specified:

- exact original/semantic bytecode indexes;
- canonical identities and fingerprints;
- exact reference graph;
- original-versus-semantic drift audit;
- dual-decompiler dossiers;
- pass/view safety registry;
- offline searchable workbench;
- upstream adoption policy.

## Phase B: semantics

Build on ASM 9.6:

- decoder evidence with proof/confidence;
- exact CFG extraction;
- symbolic stack/local value provenance;
- virtual-family index;
- richer per-method instruction evidence.

## Phase C: protocol and entity updating

Add:

- packet discovery;
- packet read timelines;
- ISAAC/framing call-path evidence;
- update-mask discovery;
- player/NPC synchronization dossiers;
- animation/movement cycle data-flow tracing.

## Phase D: cross-revision automation

Add an explainable matcher using multiple independent features:

- 235 -> 240 calibration against known names;
- ambiguity/conflict reporting;
- virtual-family mapping;
- packet-role matching;
- CFG/value-flow fingerprints;
- no automatic promotion to mappings.

## Phase E: research UX

Expand the workbench into the primary navigation surface for generated evidence while keeping it static and reproducible:

- class browser;
- method browser;
- bytecode/CFG/value-flow tabs;
- packet browser;
- decoder browser;
- source ensemble;
- mapping evidence;
- transform lineage;
- revision comparison.

---

# Non-goals

This project does **not** need to:

- invent a new Java decompiler;
- execute or log in with analysis jars;
- remove evidence just to make the jar smaller;
- mutate files under `references/`;
- automatically bless human-readable names;
- make a Java class map one-for-one to a Rust type;
- port behavior into Rust as a side effect of deobfuscation;
- treat a normalized/decompiled representation as behavioral truth.

The repository already pins two mature decompilers. The higher-value work is exact evidence, control/data-flow analysis, explainable mapping, transform lineage, and a research UI that makes all of those visible together.

---

# Compatibility with the existing pipeline

v2 remains additive:

- mapping shards stay tiny-v2;
- ledger rows stay the readable-name authority;
- `remap.sh` stays the rename mechanism;
- `verify.sh` stays the rename-only verification path when explicitly run;
- generated indexes, source, dossiers, and HTML stay under cache paths;
- `references/` stays read-only;
- no Rust implementation is generated;
- `Client240Tool.java` and the current mapping verifier remain untouched while v2 matures.

The desired end state is not a single “deobfuscated jar.” It is a **traceable deobfuscation system** where a researcher can move from readable class names and Java all the way down to the exact original instruction, packet read, field flow, branch, decoder, and revision mapping that justified the interpretation.