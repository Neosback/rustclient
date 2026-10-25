# Deob Workbench Agent Instructions

Scope: `deob-workbench/` only.

## Purpose

This is a standalone Java analysis project for reconstructing and explaining revision-specific OSRS client behavior. It is tooling and evidence infrastructure, not runtime RustClient code.

## Authority rules

1. The exact original gamepack bytecode is behavioral authority.
2. Structural/semantic deob jars, decompiler output, RuneLite APIs, historical mappings, and neighboring revisions are evidence only.
3. Analysis-normalized bytecode must never silently replace original bytecode in evidence records.
4. Every derived fact must retain provenance back to a source artifact and, where applicable, exact class/member/instruction identity.
5. Automated analysis may propose names and confidence, but must not write or bless production mapping shards automatically.
6. `references/` remains read-only. Never mutate or regenerate evidence into it.
7. Do not edit Rust crates as a side effect of deob work.

## Design rules

- Prefer immutable records/value objects at subsystem boundaries.
- Keep bytecode identity separate from semantic/readable names.
- Use stable original instruction indices for cross-view navigation.
- CFG, value-flow, decoder, packet, lineage, and semantic-oracle analyses are separate passes over one shared corpus model.
- A pass may enrich evidence but may not destructively rewrite the source corpus.
- Report ambiguity explicitly. Do not turn a weak match into a strong name.
- Revision-specific assumptions belong in revision profiles, not generic analyzers.
- Keep file formats deterministic and diffable.

## Validation claims

Do not claim compilation, tests, or behavioral validation unless they were actually run. The current branch may intentionally defer execution while architecture is being assembled.
