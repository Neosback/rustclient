# 117 HD upstream assets

Verbatim copy of the 117 HD ("RLHD") plugin's resources, used as the source for the `hd117` render
profile ([SPEC-0030](../../../../docs/specs/SPEC-0030-render-profiles.md)).

- Upstream: <https://github.com/117HD/RLHD> (author 117), plugin version 1.5.2
  (`runelite-plugin.properties` of the copied tree).
- License: BSD 2-Clause, see [LICENSE](LICENSE). The local source tree did not ship a LICENSE file; the text was
  taken from the upstream repository's `LICENSE`.
- Local source of this copy: `~/Documents/RLHD-master` (not a git checkout, so no upstream commit id is known).
- `upstream-resources/` is `src/main/resources/rs117/hd/` and `upstream-schemas/` is `schemas/`. `.DS_Store`
  files were left out; nothing else was changed, renamed or reformatted.
- `MANIFEST.sha256` lists the SHA-256 of every file. `crates/renderer/tests/hd117_assets.rs` re-hashes the
  tree, so a changed, missing or extra file fails the test.

Do not edit files under `upstream-*`. Ported or modified shaders and data live outside these folders and cite
the upstream file and line they come from.
