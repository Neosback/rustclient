# Protocol evidence

OpenRune commit abc80a8d76d68912f96819680f8aae279a586514.
Exact Gradle-cached RSProt source release 1.0.0-ALPHA-20260825; no runtime dependency on these sources.

- `crypto` sources SHA-256 `9778befdb99eb5d708ef93d266578566f05e744ed1c4c5d5c307250125efd8e8`
- `osrs-240-desktop` sources SHA-256 `e68db452c6de344e0520d349ae69a2b238ffef620adb462f56b400e81b241740`
- `osrs-240-api` sources SHA-256 `685fdf6b43987beef76245356f9c1a3f1899d7079e6b941ed63fcb4af661d10e`
- `osrs-240-shared` sources SHA-256 `f2a33f33a29b76ba1bb26e2c2f263c5ed28634aaf41c5c20953b64d9e5c29ffc`
- `osrs-240-model` sources SHA-256 `350a02ab0a61cd41ee80177f32a86ff05508fff421c17561a10afbf6c54bdce3`

Relevant entries: GameServerProt, GameServerProtId, LoginBlockDecoder, DesktopLoginCrcDecoder, GameLoginResponseHandler, OkLoginResponseEncoder, RebuildNormalV2Encoder, RebuildLoginV2, OutgoingMessageEncoder, Sha256ChallengeVerifier, IsaacRandom.

Fixtures must be synthetic or independently generated public test vectors; server credentials/private keys are never fixture data.

`public.key` is a synthetic OpenSSL-generated 1024-bit public key; its private half was discarded. `login-request.bin` is an independent Python struct/pow/XTEA encoding for public dummy input fixture/example, seed [1,2,3,4], UUID bytes 0..23, session 0x0102030405060708 and CRC[i]=0x10203040+i. ISAAC vectors were evaluated directly using the exact cached RSProt binary via a temporary Java harness, over 512 big-endian words. XTEA uses the standard zero key/zero plaintext vector.

Signed primary-world area evidence: OpenRune RspCycle.worldId returns -1; Rsprox StaticRebuildV2Decoder uses g2sAlt3. Adapter tests translate -1 to None and reject other negative values.
- `StaticRebuildV2Decoder.kt` SHA-256 `3e18afc1948e69056a82108427cdd4371b8d4fd264abb3b244d872f62049c29b`
- `RspCycle.kt` SHA-256 `378dec662e86a441f6a06dcdc1538556f837c1ea2a756f8cbb92f697a9243c3e`

## SPEC-0018 movement and local-player fixtures

Same selected RSProt release, `1.0.0-ALPHA-20260825`. Synthetic codec-family
vectors are embedded beside the movement/GPI decoder tests. They express the
five-byte movement body, transformed coordinates/modifier, four aligned GPI
passes, skip counts and sequence assignment/clear. No live credentials are used.

Selected desktop source-entry SHA-256:

- GameClientProtId.kt: `f75fdca3c0547904241f2a09c6394b4251f1e573a780283d3a9fb23c3c2d218b`
- MoveGameClickDecoder.kt: `07df8f3cbb6d6b57f76bb2b596da457f295e74279cf73ce0eb6ecd6a1ad0e1a4`
- RebuildNormalV2Encoder.kt: `c1577ae5fe0a56cbda0db2e89a5648dd5371f548a4547cdc81cdf4025d095430`
- PlayerAvatarExtendedInfoDesktopWriter.kt: `a7bf0bc728e8faa3a46eb7673ca5ba21298609d317ff0d944195f93b0c074cc8`
- PlayerAppearanceEncoder.kt: `0c49b2c70153d8441149536025b173366cea051efdb436ea2f435b1b9d732f03`

PlayerInfo's four passes and movement bitcodes were checked against the selected
model source artifact and the pinned 235 PlayerUpdateManager as corroboration.
OpenRune's MoveGameClickHandler and ctrlMoveSpeed define Ctrl's walk/run inversion.
These fixtures are codec evidence, not live-session or render-parity acceptance.

## NPC, world actions and inventory continuation

Same selected 240 source artifacts listed above. The NPC binary fixtures were
independently bit-packed with Python from the source field layout: index 42,
small type 12 / large type 70000, signed deltas, optional spawn clock, stock name,
visibility, sequence and facing extensions. Action and inventory family goldens
are hand-authored fields embedded alongside their codecs. Normal V2 actions use
subop zero. Inventory model documentation confirms negative IF3 addresses, mirror
selection below -70000, replacement semantics, zero-count placeholders and removal
on stop-transmit. These are synthetic codec proofs, not recorded gameplay.

Selected source-entry and fixture SHA-256:

- `net/rsprot/protocol/game/incoming/codec/locs/OpLoc1V2Decoder.kt`: `6a138c43b9bcf58a0f71ce166000a4aee4d162b2aa6cfa5b39df61932adbfb5c`
- `net/rsprot/protocol/game/incoming/codec/locs/OpLoc2V2Decoder.kt`: `7d802b416bf7148f2382270192d068ca78a29dd8c12c32b0ac5bfc7982666eb4`
- `net/rsprot/protocol/game/incoming/codec/locs/OpLoc3V2Decoder.kt`: `7b76955523a07719cbea0ab97494ddb942471b731eea36292f5259d3be1190bc`
- `net/rsprot/protocol/game/incoming/codec/locs/OpLoc4V2Decoder.kt`: `093a185348af44c9e7c7cb75e8c24549ec5b556edbd260ca7454225d21afe3a8`
- `net/rsprot/protocol/game/incoming/codec/locs/OpLoc5V2Decoder.kt`: `cda1e7bc3ed60f5765c60f3b34824c7b6eda31f21c98b2f13c8a38b444a56219`
- `net/rsprot/protocol/game/incoming/codec/npcs/OpNpc1V2Decoder.kt`: `f819b3ca3e53d0957ac3d598a7f484bcbf5ab337aa86568d9c1c383ddf210523`
- `net/rsprot/protocol/game/incoming/codec/npcs/OpNpc2V2Decoder.kt`: `6ce51321d12177668ea9d02c695320f403f5b09e9a80d188608775bdedc05dfc`
- `net/rsprot/protocol/game/incoming/codec/npcs/OpNpc3V2Decoder.kt`: `c44fb937986eb0844632dc0fc2c6182c563cdbbace42577ac7f7d3569ef7f495`
- `net/rsprot/protocol/game/incoming/codec/npcs/OpNpc4V2Decoder.kt`: `c45dc65815caf42bb8678e20024b9092c4d481fc42bf4faabf0f1ded4c64b503`
- `net/rsprot/protocol/game/incoming/codec/npcs/OpNpc5V2Decoder.kt`: `1695796dd7eb6fa2015b5ac836d003259feb897b1825bfc8a389a61671fb8dbf`
- `net/rsprot/protocol/game/outgoing/codec/inv/UpdateInvFullEncoder.kt`: `9a96c27b4b4fb5661cf02153b343bd19389d31ad5df55dae812ccbb2c8e2313f`
- `net/rsprot/protocol/game/outgoing/codec/inv/UpdateInvPartialEncoder.kt`: `66586b23d9f080f4afc96ea86ed0b1d27f8bd727fd0d62fa8f57f67654664ff9`
- `net/rsprot/protocol/game/outgoing/codec/inv/UpdateInvStopTransmitEncoder.kt`: `779817b18122fa612ed841fabb431c422a7b0064dbebe89f1a55ad577c4d7bcd`
- `net/rsprot/protocol/game/outgoing/codec/npcinfo/NpcInfoLargeV6Encoder.kt`: `def306137639d48a3731d46a056a7fc955f7d2b51632d447741f28781d57b811`
- `net/rsprot/protocol/game/outgoing/codec/npcinfo/NpcInfoSmallV6Encoder.kt`: `168a4d5a379e7299ae3e76c13a5ce8f5f3df857e267c117e295e1824fbf43cc0`
- `net/rsprot/protocol/game/outgoing/codec/npcinfo/extendedinfo/writer/NpcAvatarExtendedInfoDesktopWriter.kt`: `93251866ab2a057ebb36d66d61468a4496a62e9ebd7010e3b75fa88cca54213c`
- `net/rsprot/protocol/game/outgoing/prot/GameServerProtId.kt`: `b2f57b28fcd3c05bd76e6b6361c3ba7b20b8a5260d71a7137ead9127946b5d49`
- `net/rsprot/protocol/game/outgoing/info/npcinfo/NpcInfoProtocol.kt`: `554d9564ce2405057d8eeeceeb41beda0c74df7686ce3e4946c30b4b1f03dded`
- `npc-stock-small.bin`: `da4c799f0e62c7a2019ec5028bbbb201b5c0768a5030d121ad647b53f24eb67b`
- `npc-stock-large.bin`: `d2cd814d19941307f1357be93685b56d9762e903c33dd0880ac67969a9f96785`

- `net/rsprot/protocol/game/outgoing/inv/UpdateInvFull.kt`: `3e4dc4eaf81db2d1e4442751b2e3481d09c5d7c98a75a9583aada40438836631`
- `net/rsprot/protocol/game/outgoing/inv/UpdateInvPartial.kt`: `95b3b2e80b2760d28a151bc446473bf62d90dc957241a1d8afcafc424c47c99a`
- `net/rsprot/protocol/game/outgoing/inv/UpdateInvStopTransmit.kt`: `7005228bbd4a1bbc74c48b20d201a8179a9b1b25bc6177edc287560bc7699257`

### Script and widget continuation

Synthetic RUNCLIENTSCRIPT/IF_BUTTONX/CLOSE_MODAL and player status family vectors
use the pinned rsprox `protocol/osrs-240` decoders (misc/player RunClientScript,
UpdateStatV2, UpdateRunEnergy, UpdateRunWeight; buttons IfButtonX) and its
GameClientProtId table. Script container grammar was checked against the local
240 deob `class57.method1304` / `class328.method8684`, including six footer
counts and long operands. The cache script fixture is synthetic, not a cache dump.
