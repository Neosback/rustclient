# Revision-240 script opcode table (analysis evidence)

`inputs/script-opcodes-240.tsv` joins four sources per opcode number: the adapter table in
`crates/cache/src/osrs240/script_opcodes.rs`, RuneLite's rev-240 `Opcodes.java`/`RuneLiteOpcodes.java`, the
bytecode handler scan (`tools/client-240/java/ScriptOpcodeScan.java` over the rl-suite semantic jar) and a
live-use tally from the cache (`script_probe CACHE --find-opcode summary`, the `~/Desktop/LIVE` build-240 cache). Regenerate with
`tools/client-240/opcode-table.py`. It is evidence only; the adapter table stays the behavioural owner.

## Status counts

| status | meaning | count |
|---|---|---|
| adapter | in the adapter table, named by RuneLite or a handler | 600 |
| adapter-unconfirmed | in the adapter table, neither RuneLite nor a handler branch confirms it | 5 |
| handler-only | bytecode handler branch found, no name anywhere (mostly small control/compare numbers) | 552 |
| named-only | RuneLite names it, adapter and scan do not | 9 |
| unknown | nothing but a live use | 201 |

## Opcodes the cache uses that the adapter does not name

319 opcode numbers, 2391 uses (329 and 2809 before the 2026-10-25 port of 6531 and 8002/8020-8029, see the end of this file). None has a RuneLite rev-240 name, and none is named in the 235 or
rustosrs references either, so the names have to come from reading handler behavior (Phase B scripts wave).
118 of them have a scanned handler branch; the other 201 have none. Top by use:

| opcode | uses | scanned handlers |
|---|---|---|
| 7016 | 109 | — |
| 4030 | 108 | classBB.method873, classMC.method7575 |
| 213 | 83 | classBB.method798, classGD.method5429 |
| 2929 | 63 | classBB.method837, classJG.method6408 |
| 204 | 58 | classBB.method798, classGD.method5429 |
| 6751 | 58 | — |
| 6752 | 58 | — |
| 216 | 56 | classBB.method798, classGD.method5429 |
| 103 | 51 | — |
| 7010 | 51 | — |
| 202 | 49 | — |
| 7035 | 40 | — |
| 7040 | 39 | — |
| 7015 | 36 | — |
| 212 | 34 | classBB.method798, classGD.method5429 |
| 6802 | 34 | — |
| 211 | 33 | classBB.method798, classGD.method5429 |
| 4124 | 30 | classBB.method874, classBB.method875, classBB.method876, classJF.method6405 |
| 7005 | 30 | — |
| 7000 | 29 | — |
| 6801 | 23 | — |
| 6852 | 22 | — |
| 7011 | 22 | — |
| 7020 | 22 | — |
| 3700 | 21 | — |

## Limits of the scan

- The central dispatcher in `classBB` routes opcode ranges through control-flow-flattened jumps with opaque
  int thresholds (for example `if (op >= 1495873716) goto ...; goto ...`). The decompiled if/else chain of range
  checks is therefore not a faithful routing table, and opcode ranges cannot be attributed to handler classes
  from it. Several range handlers (`classDI.method3365`, `GameConnection.method3398`, `classNF.method7882`)
  decompile to a bare `return 2`; whether the flattened flow reaches them for 69xx-70xx numbers is unproven.
- Per-opcode stack counts are not emitted: the stack-size multipliers differ per site, and the arithmetic only
  holds for part of the sites. Read the handler instead.
- Some opcodes appear in several handler methods; the table lists all of them.
- Handler bytecode comes from the rl-suite semantic jar. Timing and control-flow claims for protocol work must be
  checked against the original gamepack (`references/gamepacks/osrs-240`).

## rl-suite `data/rev-240`

The upstream folder holds `mappings/{client,server}-packets.tsv` (byte-identical to our `inputs/` copies) and
older deob jars (1.12.34 to 1.12.36). It has no opcode table. The older jars could feed the cross-revision mapper later.

## Runtime trace

`tools/parity/Trace240Agent.java` (attached with `runelite240.py --trace-opcodes`) records which handler really executes each
opcode and its stack effect in a live session of the pinned client. `tools/client-240/opcode-trace.py` joins it to stable
symbols and `opcode-table.py` adds the `traced_*` columns. The agent captures the opcode on entry because handlers rewrite it (an IF opcode folds into its CC number after popping the widget id, so 2100 and 1100 pop 3 and 2 ints). A handler that delegates to another appears as two rows with the same stack effect. The first login and interface load traced 200 opcodes, all already named by the adapter; the 329 unnamed opcodes need sessions that open the interfaces that use them (world map, quests, social lists, settings, stock market).

### Router frames and ported array opcodes (2026-10-25)

`traced_handler` is the method that returned the result, which can be a range router rather than the code that does the work: 6531 traces to
`classQR.method9824`, but the body is `classSU.method10737`, which pushes the two literals `240` and `1` (client build and sub-build).
Read the deobfuscated body, not only the traced frame. Opcodes 8000-8029 are the script array family (`classFW.method5091`,
array object `classTE`: size, initial value, growable/read-only flag, 5000-element limit). Ported into `crates/client-script` and
`crates/application/src/scripts/arrays.rs` with tests in `crates/client-script/tests/arrays.rs`: 6531 (`ClientBuild`), 8002 (`ArrayIsNull`),
8020/8021 (enum keys/values as read-only arrays), 8022-8029 (new, resize, push, insert, remove, append-all, insert-all, remove-range).
8001, 8009-8012, 8014, 8015, 8018 and 8019 (the cursor/iterator part of the family) are still unsupported; the cache uses them 37 times in total. The hot unnamed opcodes that remain are 7016, 4030, 213 and 2929.

