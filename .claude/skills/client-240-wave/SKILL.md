---
name: client-240-wave
description: "Run a parallel naming wave over the revision-240 client evidence. Picks clusters of unnamed classes with disjoint reference sets, gives each to its own subagent that writes an evidence-backed tiny-v2 mapping shard, ledger rows and a behavior write-up, verifies rename-only with tools/client-240/verify.sh, and commits one cluster per commit. Use for 'client wave', 'name more of the 240 client', 'do another deob wave', 'map the 240 client to readable names'. Takes a cluster count, an optional token ceiling and an area."
metadata:
  version: "1.1.0"
---

# Revision-240 Client Naming Wave

Run N independent research agents over N clusters of unnamed 240-client classes, then verify and commit.
The deliverable is **names + behavior docs**, never edited Java and never Rust. Porting a behavior into a
RustClient crate is a separate spec-gated checkpoint.

Read first: `docs/reference/client-240/README.md`, `docs/reference/client-240/AGENTS.md`,
`docs/reference/rs240-port/{README,AGENTS,PORT-MATRIX}.md`, ADR-0005 and the root `AGENTS.md`.

Run commands from the RustClient repository root, resolved with `git rev-parse --show-toplevel`.
Install this canonical skill for Codex with `python3 tools/client-240/install-skill.py`.

## Arguments

`$ARGUMENTS` is optional and free-form.

| Form | Meaning | Default |
|---|---|---|
| a bare number (`4`) | how many clusters to do | 4 |
| `--budget 800k` / `--budget 2M` | stop launching new clusters once cumulative subagent tokens pass this | none |
| `--area <protocol\|cache\|scene\|ui\|scripts\|entities\|login\|camera>` | restrict candidates to a `PORT-MATRIX.md` domain | P0 domains first |
| `--dry-run` | survey and propose clusters, then stop without spawning | off |

Read a bare number as clusters, never as tokens or minutes.

## Why clusters, not tokens or time

Cluster count is the only dial that is both controllable and meaningful, because one cluster is also one
commit. Calibrate from the template this skill was adapted from: one cluster costs roughly 200k subagent
tokens and 15 minutes, and a wave runs in the time of its slowest agent. Four is a comfortable default.
If the user gives a budget anyway, convert it at 200k tokens per cluster, say you did, and proceed.

## Procedure

### 1. Baseline

Confirm a clean working tree for the files you will touch (other sessions may have unrelated edits; leave
them alone), then run the toolchain on the committed state so a later failure is attributable:

```bash
git status --porcelain -- docs/reference/client-240 tools/client-240 references
bash tools/client-240/verify.sh baseline-$(date +%Y%m%d)
```

Stop if the baseline does not pass. Run `python3 tools/client-240/toolchain.py --check`
first: the tool JARs and pinned inputs ship in the repository. Only a host JDK 17+, Python 3.9+,
Git and Bash are prerequisites. Optional CLIENT240 overrides are for explicit alternate inputs.
When asked to verify installation/setup only, stop after toolchain/baseline verification; do not
start a naming wave or commit other sessions' pending shards.

### 2. Pick clusters with disjoint class sets

This step makes or breaks the wave. A class belongs to exactly one shard, so **every cluster must own a
class set no other cluster touches**.

Start from the priority order in `docs/reference/rs240-port/PORT-MATRIX.md` (P0 first: buffer primitives,
ISAAC and framing, login/bootstrap, player and NPC sync, actor path, widgets and scripts, scene and
model, camera). Candidates are unnamed classes in `references/rl-suite-240/sources-semantic/` that are
not yet in `mappings/clusters/*.tiny`. Rank by size and by how many port-matrix rows they unblock:

```bash
cd references/rl-suite-240/sources-semantic
for f in class*.java; do echo "$(wc -l < $f) $f"; done | sort -rn | head -40
```

Compute each candidate's full reference set and check the sets do not intersect:

```bash
grep -rlw <ClassName> references/rl-suite-240/sources-semantic
```

Traps from the template, all still true:

- `grep -w classAB` does **not** match `classAB_Sub1`; grep each subclass separately.
- A family that looks self-contained is often constructed from a manager or a registry; that file joins the
  cluster.
- If two clusters both need one shared file, merge them or drop one. Never split a class between agents.

A good cluster is an inheritance family plus whatever constructs it, 5 to 15 classes.

**Port-irrelevant exclusion** (replaces the template's "no hardware renderers"). Do not spend a cluster on
code RustClient will not port: AWT/applet/Swing glue, the software rasterizer, Jagex Mobile support,
sound-platform backends, RuneLite-only injection shims and the launcher. Record them once as `not-needed`
in `docs/reference/client-240/clusters/EXCLUDED.md` so nobody re-triages them. Renderer-agnostic code
(protocol, cache definitions, model and scene data, UI, scripts, pathfinding, movement) stays in scope.

For `--dry-run`, print each cluster name, its classes, its reference-set size and the matrix rows it
serves, then stop.

Seed material for agents: `proposals/235-carry.tsv` (T3 candidates by unique signature) and the packet
tables at `docs/reference/client-240/inputs/{client,server}-packets.tsv`.

### 3. Brief the agents

Launch all agents in a single message so they run concurrently. Name each agent after the subsystem, not
the class number. Every prompt must carry:

1. **The exclusive file list**: its mapping shard `mappings/clusters/NN-name.tiny`, ledger shard
   `ledger/clusters/NN-name.tsv`, and write-up `clusters/NN-name.md`. Editing anything else is forbidden.
   State that other agents are working concurrently on other clusters.
2. **Read-only references and no Java edits.** `references/` is evidence (root `AGENTS.md` rule 11).
   Output is mapping entries, ledger rows and docs only.
3. **Stable symbols are the key** (`classDF.method3015`). A readable name is added beside it; existing
   citations keep working.
4. **The evidence tiers** (T1 API getter, T2 packet/protocol table or RSProt 240, T3 235 carry-forward only
   when structure matches, T4 behavior with a cited method). Leave a symbol generic rather than guess; T3 and T4
   rows without cited evidence fail the ledger check.
5. **The ownership rule**: the agent may name members (and the class itself) only for classes in its exclusive
   list; the mapping applies globally, so a name also appears in other clusters' sources. Before naming,
   grep the reference set (`grep -rlw`) and list external referrers in the report so their cluster can
   reconcile. For a member of a class it does not own, or a class nobody owns, it writes a handover line
   instead of a mapping entry. `client` and `rl5` are owned by nobody in a wave unless explicitly assigned.
6. **Cross-check ambiguous decompilation** (labelled breaks, collapsed switches, `$VF` markers) with a second
   decompiler bundled in the repository (`tools/client-240/secondary-decompile.sh CLASS`, using CFR) and
   record disagreement instead of picking silently.
7. **Client quirks stay**: document any odd or buggy original behavior as a `client quirk` with its method
   reference; never correct it in wording. A Rust port must reproduce it when OpenRune depends on it.
8. **Separate stock behavior from RuneLite injection** (`injection-only` status in the write-up).
9. **No git writes.** Read-only git only; this session commits.
10. **Run `verify.sh` at most 3 or 4 times**; it remaps and decompiles the whole jar.
11. **Hot-path and Rust-owner guidance**: the write-up proposes a RustClient owner (`protocol`, `cache`,
    `game-state`, `scene`, `game-ui`, `client-script`, `application`) per the rs240-port architecture
    discipline. A Java class does not map to a Rust type one-for-one.
12. **What to report**: old to new mapping, one line on what the family does, wire/cycle semantics found,
    proposed Rust owner, items left generic and why, any handover to another cluster, and suspected original
    quirks.

The naming and write-up conventions live in `docs/reference/client-240/AGENTS.md`; do not paste them
beyond pointing at the file.

### 4. Verify before believing

Agents report their own success. Check independently:

```bash
bash tools/client-240/verify.sh wave-<N>
```

This proves: shards are valid tiny-v2 and current (no stale symbols), no target collides, mapping and
ledger agree, **remap forward then back reproduces the semantic jar's bytecode exactly** (rename-only
proof), every `@ObfuscatedName`/`@ObfuscatedSignature` is byte-identical (the mapped-to-original
evidence), nothing under `references/` changed, and the readable jar decompiles. Read the report at
`docs/reference/client-240/verification/wave-<N>.md`.

Then **read every T3 and T4 entry by hand** against its cited method. The tooling cannot judge whether a
name is right. Check at least:

- a T3 carry-forward whose descriptors or call shape do not actually match the 235 member;
- a name that encodes a guess or contradicts the method body or the wire/cache evidence;
- a name that shadows an unrelated RuneLite API member;
- a write-up claim that is unsupported by the cited method or by RSProt 240 / cache evidence;
- a quirk quietly "fixed" in wording.

If an agent left a handover (a rename crossing into another cluster), apply it yourself after both
agents finish. `sed -E` on macOS does not support `\b`; use `perl -pi -e` for word-boundary edits.

### 5. Commit

One commit per cluster: its mapping shard, ledger shard and write-up. After the last cluster, regenerate
`mappings/merged.tiny`, `ledger/NAMES.tsv` and the verification report (all produced by `verify.sh`) and
commit them once as a wave commit. If two clusters share a file and cannot be separated, squash them.
Verify bisectability for a risky cluster by running `verify.sh` in a clean worktree at that commit.

Commit message: a plain sentence title, then a body listing what the family is, the old to new mapping
(or its count and the ledger path for long lists) and what was deliberately left generic. End with the
attribution line the session configures. Do not push. Stay on `main` unless the user says otherwise.

Update `docs/reference/rs240-port/PORT-MATRIX.md` and `SYMBOL-LEDGER.tsv` rows only where a cluster
changes their status, using the corpus vocabularies.

## Reporting back

Give the user, in this order: what each cluster became, the verification table, anything left generic on
purpose with the reason, and the coverage line from the verification report (named vs generic classes,
fields and methods) so they can judge the next wave. Also state which P0/P1 matrix rows now have a
`specified` write-up.
