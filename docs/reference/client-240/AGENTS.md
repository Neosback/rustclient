# Client-240 naming tree agent instructions

Scope: `docs/reference/client-240/` and `tools/client-240/`. Read [`README.md`](README.md), the
[`rs240-port` rules](../rs240-port/AGENTS.md) and the root `AGENTS.md` first.

## Hard rules

1. `references/` is read-only evidence. Never edit it, and never regenerate anything into it.
2. Never edit Java sources. The only inputs you write are your cluster's mapping shard, ledger shard and
   write-up. Everything else is generated.
3. Stable symbols (`classDF.method3015`) are the key. A readable name is added beside the key; do not
   remove, reorder or re-case existing citations elsewhere in the docs.
4. Name only what you can justify. Each row needs a tier and evidence that a reviewer can re-check in the
   cited method. Leave it generic rather than guess; T3 and T4 rows without cited evidence fail
   `ledger_check.py`.
5. Own your classes. A class belongs to one shard and you may name only members of classes in your
   assignment (the mapping applies globally, so other clusters' sources will show your names). Before
   naming a class or a member used outside your cluster, search the reference set (`grep -rlw` over
   `references/rl-suite-240/sources-semantic`, and check each `_Sub` class separately because `_` is a
   word character) and list the external referrers in your report. For a member of a class you do not own,
   write a handover line in your write-up instead of a mapping entry.
6. Do not run git writes. The wave coordinator commits.
7. Run `tools/client-240/verify.sh` sparingly (three or four times at most): it remaps and decompiles the
   whole jar.

## Writing a mapping entry

Shard header is exactly `tiny<TAB>2<TAB>0<TAB>semantic<TAB>readable`. Members use the descriptor from the
semantic jar:

```text
c	classDF	PlayerInfoDecoder
	f	I	field1269	skullIcon
	m	(Lclass1;I)V	method3015	readLocalPlayer
```

For a class you only own but do not rename, write `c<TAB>classXX<TAB>classXX`. Add one ledger row per
renamed symbol in `ledger/clusters/NN-name.tsv` (columns are defined in `ledger/clusters/00-seed-api.tsv`
and `tools/client-240/ledger_check.py`).

## Naming conventions

- Java identifiers in the client's own style: classes `UpperCamel`, members `lowerCamel`, constants
  `UPPER_SNAKE`. Prefer the name the revision-240 protocol or cache vocabulary already uses.
- Name the semantic role, not the implementation detail, and never encode a guess in the name.
- Do not use a name that shadows a RuneLite API member unless it implements it.

## Write-up (`clusters/NN-name.md`)

Per cluster: what the family does, the old→new table with stable symbol and `@ObfuscatedName`, key
algorithms, wire bytes/bits and transforms (cite RSProt 240 when available), cycle and ordering
semantics, side effects, the proposed RustClient owner (follow the rs240-port architecture
discipline), client quirks, unresolved questions, and what you deliberately left generic. Use the status
and confidence vocabularies from `../rs240-port/README.md`.

When Vineflower output is ambiguous (labelled breaks, collapsed switches, `$VF` markers) cross-check the
method with a second decompiler and say when they disagree.

## Port-irrelevant code

Do not spend effort on code RustClient will not port: AWT/applet/Swing glue, the software rasterizer,
mobile support, sound-platform backends, RuneLite-only injection shims and the launcher. List any class
you triage out in the write-up as `not-needed` so it is not revisited.
