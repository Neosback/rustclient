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
