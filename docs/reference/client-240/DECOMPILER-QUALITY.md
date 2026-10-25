# Decompiler quality experiment: revision 240

Measured on 2026-10-25. Status: **REVIEW_REQUIRED**.

Use **Vineflower 1.12 for the main export, Recaf for navigation and decompiler switching, CFR for checked whole-class alternatives, and the workbench for exact-bytecode evidence**. Chaining every tool in the Java Deob folder does not improve this client reliably.

## Result

| Check | Observed result |
| --- | --- |
| Original corpus | 735 classes; `25f42961c400bd9dfff1554402441c0ba6d1cffd011163cb9b0b4c42ae194f85` |
| Annotation reading view | 26,302 top-level Named annotations removed; all 18,365 Code attributes preserved byte for byte |
| Class identity / keyword view | Stable class identities plus explicit Java-keyword aliases; original member names otherwise retained |
| Reversible rename proof | 735/735 classes have identical normalized bytecode after forward/reverse rename |
| Raw-name original Java | 735 files; 117 files reported syntax errors after annotation cleanup |
| Named/keyword-safe primary Java | 735 files; six files still failed syntax reconstruction |
| Checked reading bundle | 735 Java files, 730 syntax-pass files; classTV supplied by CFR |
| Unresolved original code | Complete ASM views retained for the five remaining problem classes, with Code hashes and bootstrap/exception information |
| Current production naming shards | Validation failed with 179 stale/conflicting entries; no automatic promotion or modification |

For the small `aa` control class, raw Recaf output was 625,563 Java UTF-16 characters with Vineflower, 1,213,915 with CFR and 1,213,862 with Procyon. Removing annotation noise reduced those outputs to 584, 660 and 643 characters respectively. This is a readability measurement, not a semantic-equivalence score.

## Same-input sample comparison

59 rendering attempts covered `aa`, Actor, Player, NPC, Widget, PacketBuffer, Model and client, with original/metadata-clean and pinned semantic views where applicable. The raw `aa` control adds one rendering per Recaf engine. Source is checked with the JDK parser at a diagnostic limit of 100,000; counts below use that uncapped-within-this-corpus check, rather than the default first 100 errors.

| Input | Renderer | Attempts | Runtime failures/timeouts | Files with explicit failure markers | Files with syntax errors |
| --- | --- | ---: | ---: | ---: | ---: |
| original | Recaf-Vineflower | 9 | 0 | 0 | 4 |
| original | Recaf-CFR | 9 | 1 | 1 | 5 |
| original | Recaf-Procyon | 9 | 1 | 5 | 3 |
| semantic | Recaf-Vineflower | 7 | 1 | 0 | 1 |
| semantic | Recaf-CFR | 7 | 1 | 1 | 5 |
| semantic | Recaf-Procyon | 7 | 1 | 2 | 0 |
| original | Vineflower-1.12.0 | 8 | 0 | 0 | 4 |
| original | JADX-1.5.6-local | 3 | 0 | 0 | 3 |

The sample includes raw JVM keywords such as `do` and `if`; syntax failures at those names are presentation problems, not evidence that the original method body was absent. The final reading export repairs them through reversible mappings. Method-name differences are review flags because renderers can repair identifiers or represent synthetic methods as Java sugar.

CFR hit the 45-second Recaf class limit on the original client. Procyon failed on its bootstrap attribute with a ClassCastException. Recaf engines also timed out on the semantic client view. These are bounded-run observations; standalone renderers were given longer process limits. The whole-corpus primary export completed in about 128 seconds on this machine.

## Remaining defects

| File | Remaining Java issue |
| --- | --- |
| ClanSettings.java | constructor could not be resugared |
| ObjectComposition.java | constructor could not be resugared |
| Widget.java | constructor could not be resugared |
| classBF.java | constant-dynamic bootstrap pseudo-Java |
| client.java | constant-dynamic bootstrap pseudo-Java |

Whole-class CFR and JADX alternatives were tried on all six primary problem files, and Recaf Procyon was tried against the same reversible reading JAR. Only CFR classTV met the checked fallback criteria: successful exit, zero syntax/failure markers, and all non-synthetic field/method names represented. The legacy Vineflower exception/stack profile did not resolve the constructor cases. Alternatives with failed bodies, invalid syntax or nonzero exits were retained as evidence and rejected from selection.

An experiment that reused the semantic artifact’s entire member-renaming scheme failed the reverse-rename comparison in 48 classes and was rejected. The exported reading view uses verified class identities and keyword aliases only. It does not claim that every semantic member name can be safely reused on the original artifact.

## How the tools fit together

- **Recaf:** useful local workspace, source navigation, field/method outlines, bytecode and alternate renderers. Its embedded Vineflower is 1.11.2; the standalone primary is the repo-pinned 1.12.0. Both were measured.
- **Vineflower:** primary renderer, with full corpus type context. [Upstream documentation](https://github.com/Vineflower/vineflower).
- **CFR:** secondary whole-class reading choice when its diagnostics and member inventory pass. [Upstream documentation](https://www.benf.org/other/cfr/).
- **Procyon / JADX:** useful independent evidence, but neither replaced the primary in this experiment. JADX was an optional local 1.5.6 comparison, not a new production dependency. [JADX documentation](https://github.com/skylot/jadx).
- **Tiny Remapper / mapping-io:** reversible names and traceable aliases; the Java Deob copies of these and CFR/Vineflower match the repo-vendored bytes exactly.
- **deob-workbench:** exact API anchors, arithmetic, CFG, provenance and packet/sync evidence. Its reports remain proposals rather than automatic names.
- **Existing rl-suite semantic JAR:** useful stable identity/naming evidence; its control-flow, exception and constant-dynamic transforms remain explicitly separate from the original code.
- **ProGuard:** a shrinker/optimizer/obfuscator; no evidence supports placing it blindly in this readability path. [Official manual](https://www.guardsquare.com/manual/home).
- **Byte Buddy:** runtime code generation/instrumentation; no role was needed in the measured renderer recipe. [Official project](https://bytebuddy.net/).
- **Enigma:** optional manual mapping UI; it does not recover method bodies by itself.
- **Local older OSRS/RuneLite deobfuscators:** inspected as algorithm references. Their default unused-member, exception, argument and block removal passes were not applied. The local OSRS updater also has an unspecified license and a 2024 snapshot; nothing from it was copied into this implementation.

## Use and reproduce

See [the Recaf tooling README](../../../tools/recaf/README.md) for build, comparison, export, fallback and assembly commands. The best current local output is `.cache/client-240/recaf/best-reading/sources/`; `manifest.json` records every source hash and selected renderer, and `original-bytecode.json` links unresolved Java files back to exact original Code hashes. Generated sources are ignored artifacts; adapters, manifests, tests and repeatable commands are repo-owned.

Quality pipeline: original → metadata-only noise view → reversible class/keyword names → primary renderer → syntax/failure/inventory checks → checked whole-class alternative → explicit unresolved list plus original bytecode.

## Limits and validation

730 syntax-pass files is not 730 behavior-proven or fully type-checked files. Full Java dependency compilation and per-method behavioral differential tests were not run. Original Code coverage stays complete even when Java cannot reconstruct a body. Original member names and uncertain meanings remain visible; this task does not recover every original developer name or certify all naming shards.

The annotation view changes reflection metadata, so it is never a runtime replacement client. The semantic artifact’s three constant-dynamic rewrites must not be used to claim original initialization timing. No Java output was spliced or recompiled into the authority corpus.
The annotation reader follows the separate annotation and Code structures in the [JVM classfile specification](https://docs.oracle.com/javase/specs/jvms/se24/html/jvms-4.html#jvms-4.7.16); preservation claims additionally come from the executed byte comparisons.

Validation executed: both metadata tests over all 735 original classes; the diagnostic-limit/nested-member regression test; registered MCP smoke including raw and clean controls; the 735-class rename roundtrip; 59 initial renderer attempts plus targeted alternatives and a legacy profile; whole-corpus syntax checks; source hashes and original reference checksum preservation. No Rust files changed and no Cargo gates were run.

[Current naming baseline failure report](verification/decompiler-quality-baseline-20261025.md).
