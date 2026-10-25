# Revision-240 script opcode table (analysis evidence)

`inputs/script-opcodes-240.tsv` joins four sources per opcode number: the adapter table in
`crates/cache/src/osrs240/script_opcodes.rs`, RuneLite's rev-240 `Opcodes.java`/`RuneLiteOpcodes.java`, the
bytecode handler scan (`tools/client-240/java/ScriptOpcodeScan.java` over the rl-suite semantic jar) and a
live-use tally from the cache (`script_probe CACHE --find-opcode summary`, the `~/Desktop/LIVE` build-240 cache). Regenerate with
`tools/client-240/opcode-table.py`. It is evidence only; the adapter table stays the behavioural owner.

## Status counts

| status | meaning | count |
|---|---|---|
| adapter | in the adapter table, named by RuneLite or a handler | 586 |
| adapter-unconfirmed | in the adapter table, neither RuneLite nor a handler branch confirms it | 5 |
| handler-only | bytecode handler branch found, no name anywhere (mostly small control/compare numbers) | 566 |
| named-only | RuneLite names it, adapter and scan do not | 9 |
| unknown | nothing but a live use | 201 |

## Opcodes the cache uses that the adapter does not name

329 opcode numbers, 2809 uses. None has a RuneLite rev-240 name, and none is named in the 235 or
rustosrs references either, so the names have to come from reading handler behavior (Phase B scripts wave).
128 of them have a scanned handler branch; the other 201 have none. Top by use:

| opcode | uses | scanned handlers |
|---|---|---|
| 8024 | 145 | classBB.method924, classFW.method5091 |
| 8023 | 129 | classBB.method924, classFW.method5091 |
| 7016 | 109 | — |
| 4030 | 108 | classBB.method873, classMC.method7575 |
| 213 | 83 | classBB.method798, classGD.method5429 |
| 3228 | 78 | Renderable.method3851, classBB.method857, classBB.method858 |
| 8022 | 64 | classBB.method924, classFW.method5091 |
| 2929 | 63 | classBB.method837, classJG.method6408 |
| 8026 | 61 | classBB.method923, classBB.method924, classFW.method5091 |
| 6751 | 59 | — |
| 6752 | 59 | — |
| 204 | 58 | classBB.method798, classGD.method5429 |
| 216 | 56 | classBB.method798, classGD.method5429 |
| 103 | 53 | — |
| 202 | 51 | — |
| 7010 | 51 | — |
| 7035 | 40 | — |
| 7040 | 39 | — |
| 6802 | 37 | — |
| 7015 | 36 | — |
| 212 | 34 | classBB.method798, classGD.method5429 |
| 211 | 33 | classBB.method798, classGD.method5429 |
| 4124 | 30 | classBB.method874, classBB.method875, classBB.method876, classJF.method6405 |
| 7005 | 30 | — |
| 7000 | 29 | — |

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
symbols and `opcode-table.py` adds the `traced_*` columns. No live trace has been recorded yet, so those columns are empty.
