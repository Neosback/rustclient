# Cluster 01-buffer-framing (revision 240)

Scope: PORT-MATRIX P0 "Buffer primitives and transformed reads" and "ISAAC, packet framing and lengths".

Shard files:

- `docs/reference/client-240/mappings/clusters/01-buffer-framing.tiny`
- `docs/reference/client-240/ledger/clusters/01-buffer-framing.tsv` (165 rows: T2 = 85, T3 = 5, T4 = 75)

Naming vocabulary follows RSProt's `JagexByteBufExtensions` (g1/g2/g3/g4, `Alt1..3`, `gSmart*`, `gjstr2`) with RuneLite-style `Add`/`Neg`/`Sub`/`LE` suffixes where the transform is unambiguous. Where the exact byte permutation is unusual (medium/int middle-endian forms) the RSProt `AltN` suffix is kept so the name stays traceable to a published reference.

## 1. What the family does

`classXY` is the byte-array cursor ("Buffer") used for every wire and cache read/write in the client: network frames, login blocks, cache definitions, config files. `PacketBuffer` extends it with a bit cursor (player/NPC info bit streams) and an ISAAC-masked opcode/byte layer. `classYT` is the ISAAC stream cipher. `classJJ`/`classJS` are the inbound/outbound opcode+length tables. `classDJ` is the single game connection (`client.field796`): socket, inbound frame state machine fields, outbound packet queue and flush buffer. `classJL` is a pooled outbound packet node. `classKW` is not part of the family except for two displaced statics (the opcode table accessor and a chat-text helper).

## 2. Old to new mapping (classes)

| Stable symbol | @ObfuscatedName | Readable | Tier | Notes |
|---|---|---|---|---|
| classXY | xy | Buffer | T3 | byte[] + position cursor. Position is stored multiplied (`pos * -1095856699`, decoded by `702114061`); the Java source exposes this as `POSraw`/`pos`. Not a quirk of the wire, only of the obfuscator. |
| PacketBuffer | xs | PacketBuffer | n/a | Extends classXY, implements RuneLite `PacketBuffer`; left under its existing name, members named in the ledger. |
| classJJ | jj | ServerProt | T2 | 152 constants (opcode, length). Equal to `server-packets.tsv` and RSProt 240 sizes. |
| classJS | js | ClientProt | T2 | 117 constants (opcode, length). Equal to `client-packets.tsv`. |
| classYT | yt | IsaacCipher | T2 | Identical algorithm to RSProt `IsaacRandom`. |
| classDJ | dj | GameConnection | T4 | Singleton `client.field796`. |
| classJL | jl | OutboundPacket | T4 | Pooled node (pool of 300). |
| classKW | kw | (left generic) | n/a | World-entity enum with 4 values. Only two displaced statics were named: `allServerProts` (method6619) and `stripModIconPrefix` (method6620). |

Key members (full list in the ledger; stable symbol / obf):

| Stable symbol | Obf | Readable |
|---|---|---|
| PacketBuffer.field6925 | ae | bitIndex |
| PacketBuffer.field6927 | af | isaacCipher |
| PacketBuffer.method12973 | ax | readOpcode |
| PacketBuffer.method12969 | as | isTwoByteOpcodeAhead |
| PacketBuffer.method12976 | ac | readCipheredBytes |
| PacketBuffer.method12964 | ab | writeCipheredByte |
| PacketBuffer.method12981 / 12982 / 12987 | aa / aj / al | beginBitAccess / readBits / endBitAccess |
| PacketBuffer.method12991 | ay | bitsRemaining |
| classJJ.field3318 / field3319 | fc / fm | opcode / length |
| classJJ.field3295 | ec | UNHANDLED (opcode 129, length -2) |
| classJS.field3370 / field3369 | er / eo | opcode / length |
| classKW.method6619 | az | allServerProts |
| classYT.method13682 / 13674 / 13667 / 13670 | mi / ae / az / af | initialize / generate / nextInt / peekInt |
| classDJ.method3374 / 3371 | ae / af | queuePacket / flush |
| classDJ.field1518 / 1523 / 1524 | as / ax / ac | currentServerProt / payloadLength / needsFirstOpcodeByte |
| classDJ.field1522 / 1526 | aa / ao | idleCycles / cyclesSinceWrite |
| classDJ.field1527 / 1528 | al / aj | packetGroupRemaining / bytesRead |
| classDJ.field1533 | ab | outboundCipher |

## 3. Canonical primitive table

Convention: "stream b0,b1,.." are bytes in arrival order. Rust column is where `crates/protocol` currently implements (or does not implement) the same read. `Reader` = `crates/protocol/src/wire.rs`; outbound encoders live in `openrune_240`.

### Bytes

| Stable method | Readable | Semantics | RSProt | Rust |
|---|---|---|---|---|
| classXY.method13095 | readUnsignedByte | b0 | g1 | `Reader::u8` |
| classXY.method13097 | readByte | (i8) b0 | g1s | `Reader::u8` cast |
| classXY.method13218 | readUnsignedByteAdd | (b0 - 128) & 0xFF | g1Alt1 | `Reader::u8_add` |
| classXY.method13229 | readByteAdd | (i8)(b0 - 128) | g1sAlt1 | `Reader::i8_add` |
| classXY.method13224 | readUnsignedByteNeg | (0 - b0) & 0xFF | g1Alt2 | `Reader::u8_neg` |
| classXY.method13232 | readByteNeg | (i8)(0 - b0) | g1sAlt2 | `Reader::i8_neg` |
| classXY.method13225 | readUnsignedByteSub | (128 - b0) & 0xFF | g1Alt3 | `Reader::u8_sub` |
| classXY.method13235 | readByteSub | (i8)(128 - b0) | g1sAlt3 | `Reader::i8_sub` |
| classXY.method13029 | writeByte | | p1 | encoders |
| classXY.method13209 / 13211 / 13214 | writeByteAdd / Neg / Sub | v+128, -v, 128-v | p1Alt1..3 | encoders |

### Shorts

| Stable method | Readable | Semantics | RSProt | Rust |
|---|---|---|---|---|
| classXY.method13099 | readUnsignedShort | b0<<8 \| b1 | g2 | `Reader::u16` |
| classXY.method13104 | readShort | signed | g2s | `Reader::u16` cast |
| classXY.method13250 | readUnsignedShortLE | b1<<8 \| b0 | g2Alt1 | `Reader::u16_le` |
| classXY.method13258 | readShortLE | signed LE | g2sAlt1 | cast of `u16_le` |
| classXY.method13251 | readUnsignedShortAdd | b0<<8 \| (b1-128)&0xFF | g2Alt2 | `Reader::u16_add` |
| classXY.method13262 | readShortAdd | signed | g2sAlt2 | cast of `u16_add` |
| classXY.method13254 | readUnsignedShortLEAdd | b1<<8 \| (b0-128)&0xFF | g2Alt3 | `Reader::u16_add_le` |
| classXY.method13266 | readShortLEAdd | signed | g2sAlt3 | cast of `u16_add_le` |
| classXY.method13031 | writeShort | | p2 | encoders |
| classXY.method13239 / 13242 / 13244 | writeShortLE / Add / LEAdd | | p2Alt1..3 | encoders |

### Mediums

| Stable method | Readable | Semantics | RSProt | Rust |
|---|---|---|---|---|
| classXY.method13105 / 13107 | readUnsignedMedium / readMedium | b0<<16 \| b1<<8 \| b2 | g3 / g3s | none in Reader (gap) |
| classXY.method13269 / 13275 | readUnsignedMediumLE / readMediumLE | b2<<16 \| b1<<8 \| b0 | g3Alt1 | gap |
| classXY.method13278 | readMediumAlt2 | b0<<16 \| b2<<8 \| b1, signed | g3sAlt2 | gap |
| classXY.method13283 | readMediumAlt3 | b1<<16 \| b0<<8 \| b2, signed | g3sAlt3 | gap |
| classXY.method13032 | writeMedium | | p3 | encoders |

### Ints, longs, floats

| Stable method | Readable | Semantics | RSProt | Rust |
|---|---|---|---|---|
| classXY.method13113 | readInt | b0<<24..b3 | g4 | gap in Reader (inline elsewhere) |
| classXY.method13295 | readIntLE | b3<<24 \| b2<<16 \| b1<<8 \| b0 | g4Alt1 | gap |
| classXY.method13300 | readIntAlt2 | b0<<8 \| b1 \| b2<<24 \| b3<<16 | g4Alt2 (middle endian) | gap |
| classXY.method13303 | readIntAlt3 | b0<<16 \| b1<<24 \| b2 \| b3<<8 | g4Alt3 (middle endian) | gap |
| classXY.method13115 | readLong | two readInts, hi first | g8 | gap |
| classXY.method13119 | readFloat | Float.intBitsToFloat(readInt) | g4f | gap |
| classXY.method13037 / 13047 | writeInt / writeLong | | p4 / p8 | encoders |
| classXY.method13284 / 13287 / 13290 | writeIntLE / Alt2 / Alt3 | | p4Alt1..3 | encoders |
| classXY.method13039 | writeLong48 | low 6 bytes of a long, big endian | (none) | `openrune_240/login.rs` |

### Smart and variable-length integers

| Stable method | Readable | Semantics | RSProt | Rust |
|---|---|---|---|---|
| classXY.method13150 | readSmart1or2 | peek<128 ? g1 : g2-32768 | gSmart1or2 | `Reader::u_short_smart` |
| classXY.method13146 | readSmart1or2Signed | peek<128 ? g1-64 : g2-49152 | gSmart1or2s | gap |
| classXY.method13152 | readSmart1or2Null | peek<128 ? g1-1 : g2-32769 (-1 = null) | gSmart1or2null | gap |
| classXY.method13156 | readSmart1or2Extended | sums 32767 per max smart, then adds tail | gSmart1or2extended | gap |
| classXY.method13164 | readSmart2or4 | signed first byte <0 ? g4 & 0x7FFFFFFF : g2 | gSmart2or4 | gap |
| classXY.method13169 | readSmart2or4Null | as above but short 32767 maps to -1 | gSmart2or4null | gap |
| classXY.method13158 | readUnsignedByteOrShort252 | b<252 ? b : ((b-252)<<8) + g1 | (none) | gap |
| classXY.method13172 | readMidiVarLen | 7-bit continuation | gMidiVarLen | gap |
| classXY.method13176 / 13177 | readVarInt / readVarIntZigZag | | gVarInt / zigzag | gap |
| classXY.method13084 | writeSmart1or2 | v<128 one byte; v<32768 short | pSmart1or2 | encoders |
| classXY.method13087 / 13090 / 13093 | writeMidiVarLen / VarInt / VarIntZigZag | | | encoders |
| classXY.method13173 | readIntByStorageType | switch on a storage-type enum (byte, ubyte, short, ushort, medium, umedium, int) | (none) | cache decoder helper |

### Strings, byte runs, bits, integrity

| Stable method | Readable | Notes |
|---|---|---|
| classXY.method13127 / 13125 | readString / readStringOrNull | gjstr (NUL-terminated) / gjstrnull |
| classXY.method13134 | readZeroPrefixedString | gjstr2: leading 0 byte required |
| classXY.method13137 / 13064 | read/writeZeroPrefixedUtf8String | static, MIDI var-len length + UTF-8; writer encodes UTF-8 by hand (1-3 bytes, no surrogate pairing) |
| classXY.method13140 / 13067 | readBytes / writeBytes | |
| classXY.method13307 / 13310 | readBytesReversed / readBytesReversedAdd | gdataAlt1 / gdataAlt3: fills destination backwards, Add form subtracts 128 per byte |
| classXY.method13074 / 13080 | writeLengthShort / writeLengthByte | back-patch a length placeholder behind the payload |
| classXY.method13182..13191 | xteaEncrypt / Decrypt (+Range) | `crypto.rs` xtea |
| classXY.method13195 | rsaEncrypt | BigInteger modPow, u16 length + bytes; used for the login block |
| classXY.method13202 / 13208 | writeCrc32 / checkTrailingCrc32 | standard CRC-32; also static CRC tables `field6955` (crc32) and `field6954` (crc64) |
| PacketBuffer.method12981 / 12982 / 12987 | beginBitAccess / readBits / endBitAccess | MSB-first bit reads using `bitMasks`; `endBitAccess` byte-aligns up: `offset = (bitIndex + 7) / 8`. Rust: `openrune_240/bit_reader.rs` `BitReader` (`read_bits`, `byte_align`). |

Remaining classXY members (see section 8) are decoys, no-arg bridge overloads, or static clones of the above.

## 4. ISAAC, framing and lengths

### ISAAC (classYT)

Algorithm identical to RSProt `IsaacRandom`: golden ratio 0x9E3779B9 mixed four times, two seed passes over results then memory, 256 word results/memory, the `a ^= a<<13; a ^= a>>>6; a ^= a<<2; a ^= a>>>16` step, results consumed from the top (`results[--remaining]`). `nextInt` refills on 0; `peekInt` refills identically but does not decrement, and is used only for the opcode peek. Rust: `crypto.rs` `Isaac` (`new(seed: [u32;4])`, `next_word`); no `peek` exists yet, the Rust framer handles the peek itself.

Seeding (client.method2301, login): four random login ints. The outbound cipher is `new IsaacCipher(keys)` (stored in `GameConnection.outboundCipher`); the inbound cipher in `PacketBuffer.isaacCipher` is seeded with each key + 50.

### Outbound

`classEF.method3863` creates an `OutboundPacket` from a `ClientProt`, sizes the buffer from `ClientProt.length` (fixed <=18 -> 20 bytes, <=98 -> 100, else 260; -1 -> 260; -2 -> 10000), attaches the outbound cipher to the `PacketBuffer`, and writes the opcode with `writeCipheredByte` (opcode + isaac byte; always one byte). Length byte/short are back-patched by the builder for -1/-2 prots. `GameConnection.queuePacket` records the buffer offset as `queuedSize`, resets it to 0, and adds to `pendingBytes`. `GameConnection.flush` concatenates queued packets into a 5000 byte flush buffer while they fit, recycles each `OutboundPacket` into the 300-entry pool, then writes to the socket and zeroes `cyclesSinceWrite`.

### Inbound frame loop (client.method2346, driven from client.method2198)

1. `needsFirstOpcodeByte` set: read one byte from the socket into `inboundBuffer[0]`, clear the flag.
2. `isTwoByteOpcodeAhead` peeks the ISAAC-decoded first byte (`peekInt`, not consuming). If >= 128, read a second byte into `inboundBuffer[1]`.
3. `readOpcode`: decode first byte with ISAAC; if < 128 return it, else `((b - 128) << 8) + decoded second byte`.
4. Index `classKW.allServerProts()[opcode]` (152 entries; index == opcode). Out of range raises "Invalid ServerProt". Set `currentServerProt` and rotate the three `recentServerProt*` fields (kept for the unhandled/exception report).
5. Length class from `ServerProt.length`: fixed N, -1 reads one length byte, -2 reads a big-endian u16. Stored in `payloadLength`.
6. Wait for `payloadLength` bytes, read into `inboundBuffer` (offset reset to 0 per frame), then dispatch on `currentServerProt` comparisons; handlers clear `currentServerProt`.
7. A handled frame must leave the buffer fully consumed; unhandled prots hit the error path including the previous two prot ids.

Packet groups: `PACKET_GROUP_START` sets `packetGroupRemaining` from a `g2s`; the loop refuses to start another frame until that many bytes are buffered; `bytesRead` diffs allow subtraction as bytes are pulled.

Per-cycle driver (client.method2172): `idleCycles` incremented per logged-in cycle and the connection is dropped above 750; `cyclesSinceWrite` incremented and NO_TIMEOUT queued above 50; then `flush`. A frame count cap of 100 per cycle bounds the read loop.

### Length types (classJJ.length and classJS.length)

- `>= 0`: fixed payload length.
- `-1`: var-byte (one length byte after the opcode).
- `-2`: var-short (big-endian u16 after the opcode).

Rust cross-check: `openrune_240/lengths.rs` `LENGTHS` matches the 152 client lengths except opcode 129, where Rust has `-3 // unassigned` and the client has `UNHANDLED` with length -2.

### URL_OPEN

Only `URL_OPEN` (RSProt `UrlOpenEncoder`, encrypted payload) has an ISAAC-masked payload: the client reads it with `readCipheredBytes`. All other server payloads are plain bytes after the framed opcode. Login success flag 1 also reads 4 ISAAC-masked bytes via `readCipheredUnsignedByte`.

## 5. Quirks (documented, not fixed)

1. Position storage multiplier in classXY (`-1095856699` / `702114061`) and similar constants on `GameConnection`/`classJL`/`classYT` fields. Obfuscator output, not wire behaviour; the Rust port must not replicate it.
2. Massive decoy and clone methods on classXY. Call counts are inflated and decoy copies call the wrong siblings (for example `classDF` calls `method12991` with literal arguments; `Widget.method7220` calls with wrong junk arguments). Names were assigned only from verified bodies.
3. `readMediumAlt2/Alt3` and `readIntAlt2/Alt3` are middle-endian permutations with no relation to a simple LE/BE flip.
4. `readByteNeg`/`readUnsignedByteNeg` use `0 - b`, `readByteSub` uses `128 - b`; both are used on the wire, do not conflate.
5. Opcode 129 is `UNHANDLED` with length -2; the client has no comparison for it so it reaches the error path. Rust treats it as unassigned (-3).
6. The ISAAC peek has no matching decrement: `isTwoByteOpcodeAhead` peeks, then `readOpcode` consumes with `nextInt`.
7. Outbound opcodes are always one ISAAC byte; there is no two-byte client opcode.
8. `writeUtf8` hand-encodes 1-3 bytes, so supplementary code points become two surrogate encodings; `readZeroPrefixedUtf8String` uses a standard decoder. Round trip is not exact for supplementary characters.
9. `classKW.method6620` (strip "<img=ID>" prefix) is a displaced static unrelated to the enum.
10. Login block ordering: RSA is applied to the first block before XTEA; the 6-byte `writeLong48` is used for the random value inside the RSA block.

## 6. Rust owner proposal

- `crates/protocol/src/wire.rs`: widen `Reader` to the full canonical table above (mediums, ints, Alt forms, longs, floats, smarts, var-int, strings, reversed byte runs) with names matching the readable column. Add a symmetrical `Writer` for the outbound encoders so `openrune_240` encoders stop open-coding byte permutations.
- `crates/protocol/src/crypto.rs`: `Isaac` gains `peek_word`; inbound/outbound ciphers constructed from login keys (+50 for inbound) via a typed `SessionKeys`.
- `crates/protocol/src/openrune_240/game.rs`: `GameFramer` owns steps 1-6 of the inbound loop and the packet-group counter; `SessionCodec` owns the outbound queue, size classes and the 5000-byte flush batching. Idle (750) and keepalive (50) counters belong to `game-state::connection`, not the codec, because they are cycle-driven.
- `crates/protocol/src/openrune_240/lengths.rs`: keep `LENGTHS`; add the 129/UNHANDLED decision as a documented table entry.
- `bit_reader.rs`: already matches `beginBitAccess`/`readBits`/`endBitAccess`; add `bits_remaining` if the NPC info decoder needs the bounds check.

Gaps listed from the cross-check: no 107, no PACKET_GROUP_START, no SEND_PING/NO_TIMEOUT handling in the Rust framer, opcode 129 length mismatch, and the missing Reader primitives noted in section 3.

## 7. Unresolved questions

- `classDJ.method3381` (`closeSocket`) and `getSocket`/`setSocket` are T4/B: usage is clear but the high-level lifecycle is owned by login/logout code outside this cluster.
- `classJL.field3331`/`field3332` pool names are structural (B); no semantic difference from a plain free list was proven.
- Why `readIntByStorageType` switches on a storage-type enum from `classXF`; the enum itself was not named here.
- Whether any caller still depends on the crc64 table (`field6954`); only the table contents identify it.
- Exact reason opcode 129 has length -2 in the client table (RSProt shares this).

## 8. Left generic, and why

- Remaining classXY members with callers, all decoy clones or duplicates: method13096, 13101, 13112, 13200/13201, 13071/13073, 13257, 13276, 13315, `vmethod649..652`, and similar. Each is a copy of an already-named primitive or throws/short-circuits; naming them would create a second representation of the same concept.
- classXY members with no callers (dead clones).
- classKW enum body (4 world-entity values); handled by the actor/world clusters if needed.
- Most classDJ/classJL constructor and dead overloads.
- No-arg bridge overloads were named (T4/B) only where a caller uses them: `method13317/13320/13324/13326` (readSmart1or2Signed/readSmart1or2/readSmart1or2Extended/readFloat) plus a few static clones documented in the ledger.

## 9. Not-needed classes

- `classKW` enum body (see above).
- Helper classes encountered but owned elsewhere or irrelevant to naming here: `classUK` (socket byte-stream wrapper), `classNV` (node queue), `classPY` (CRC-32 routine), `classBF` (UTF-8 decode), `classAC` (UTF-8 length), `classTO` (byte-array pool), `classXF` (storage-type enum), `classKL`/`classDG` (mod icon helpers used only by `stripModIconPrefix`).

## 10. Extras claimed beyond the base list

`classYT` (IsaacCipher), `classDJ` (GameConnection), `classJL` (OutboundPacket), `classJS` (ClientProt). Each was checked with `grep -rlw`: users outside this cluster are the login/frame loop in `client`, `classEF.method3863`, and the NPC/player info decoders for bit access, none of which treat these as owned.

## 11. Handovers

- player-npc-sync (classDF/DC/AS): use `PacketBuffer.beginBitAccess/readBits/endBitAccess/bitsRemaining` and `classXY.read*`. Do not rename those helpers; `classAS` NPC info uses `bitsRemaining` against `GameConnection.payloadLength`. `classDF` calls of `method12991` with literals are decoys.
- actor-motion: no direct dependency; Add/Neg/Sub reads occur in their handlers and should reference the canonical names above.
- world-region: `readSmart1or2*` and `readIntByStorageType` are used by definition decoders (ObjectComposition, NPCComposition via `classXM`).
- External referrers of note: `client.method2301` (login), `client.method2346` (frame pump), `client.method2198` (io loop), `client.method2172` (per cycle), `classEF.method3863` (outbound packet builder), `classCL`/`classME` (disk metadata strings), `classSN`/`classMD`/`classSG`/`classVP`/`rl4` (no-arg bridge overloads), `MessageNode`/`classHU`/`classBB` (chat builders, `stripModIconPrefix`).
