# Enigma mapping review MCP

This optional desktop tool reuses the supplied `Enigma-MCP-master` fork of Fabric Enigma 4.0.2.
The Fabric checkout matched the pinned official commit byte-for-byte; its MCP fork is captured as
`upstream-mcp.patch`. The Quilt checkout uses a different API/package layout. Fabric was selected
because it is the base of the existing MCP implementation, not because its version proves better
decompilation. Recaf remains the source/bytecode browser; the workbench owns reusable exact-bytecode
analysis; Enigma adds interactive hierarchy/reference navigation and draft naming validation.

## Build and use

Run from the repository root with Python 3.9+, Git, a JDK 17+ and a graphical desktop. This setup was
built and exercised with JDK 24. The first build downloads checksum-pinned upstream source and Gradle;
Gradle resolves versioned transitive dependencies. This is not a fully offline/hermetic build.

```bash
python3 tools/enigma/enigma.py --build
python3 tools/enigma/enigma.py --check
python3 tools/enigma/enigma.py --start
```

Keep **RustClient — Enigma Mapping Review** open. When the session log contains
`RUSTCLIENT_ENIGMA_READY`, register the running endpoint:

```bash
python3 tools/enigma/install-mcp.py
# Register the same verified endpoint in Claude Code user configuration:
python3 tools/enigma/install-mcp.py --client claude
# Or verify/register both clients:
python3 tools/enigma/install-mcp.py --client all
```

The installer verifies the live corpus and draft-only status, then uses `codex mcp add` to register
`rustclient-enigma`. It refuses to replace a different endpoint with the same name. Reload MCP
connections to expose newly registered tools in an existing agent session. The default endpoint is
`http://127.0.0.1:32412/mcp`; `--port` selects another port. Closing the window stops its server.

`--start` creates a new session under `.cache/client-240/enigma/sessions/`; it never overwrites a saved
draft. `.cache/client-240/enigma/active.json` records the PID, session, endpoint and hashes. Save draft
work before closing. Retain its session directory; there is currently no automatic resume/merge command.
MCP saves are restricted to that session, including resolved symlink targets. Manual GUI operations
remain ordinary Enigma operations: explicitly opening another JAR makes the guarded MCP tools reject
the workspace. The draft must undergo human evidence review and the repository naming verifier before
any changes are promoted to production mapping shards.

## Inputs and provenance

The launcher checks the repository-pinned original injected revision-240 artifact. A derived input
removes malformed top-level `javax/jakarta.inject.Named` annotations using the existing metadata-view
tool. Its manifest verifies byte-identical Code attributes, constant pools, members, headers and all
other attributes. Annotation reflection changes; the JAR is for reading only.

The initial draft copies the checked reading bundle's class/keyword map, or generates the same kind
of identity view when that bundle is absent. It does not import the production mapping set; the
[subsequent baseline repair](../../docs/reference/client-240/MAPPING-BASELINE.md) is verified separately.
Each successful tool response includes the original SHA-256, analysis SHA-256 and session.
Enigma can compute analysis frames internally; its instruction representation is not the original
bytecode authority. External RuneLite API dependency JARs are not loaded, so external method-family
completeness is not guaranteed. Use the workbench's original instruction IDs for evidence citations.

Enigma's mapped/deobfuscated flags describe draft mapping state. They do not mean an evidence-ledger
name is approved or a method has been ported to Rust. Enigma's decompiler does not fix the five unresolved
files in the existing best-reading bundle; see the [quality report](../../docs/reference/client-240/DECOMPILER-QUALITY.md).

## Tools and verification

The fork exposes eleven existing tools: `list_packages`, `list_classes`, `list_members`, `search`,
`decompile_class`, `rename`, `mark_deobfuscated`, `reset_obfuscated`, `get_javadoc`, `set_javadoc`,
`save_mappings`. The integration adds `workspace_status`, `class_hierarchy`, `method_family`,
`find_references`, `validate_rename`.

Lists/graphs accept bounded pagination. Source output accepts bounded character pages. Non-mutating
rename validation uses Enigma's own validation and reports virtual families. Constructor entries are
not advertised as renamable. The integration also fixes empty default-package queries and waits for
asynchronous mapping writes before returning a successful save.

```bash
python3 tools/enigma/verify.py
```

This is an explicit **live draft mutation smoke test**, not just a connectivity check. It exercises all
16 tools, restores temporary name/javadoc/mark edits, saves the draft and checks original/production
mapping hashes. Run it in a fresh setup session without concurrent user edits. The successful 2026-10-25
run is recorded in [the setup report](../../docs/reference/client-240/verification/enigma-hybrid-20261025.md).
Upstream Enigma's complete test suite was not run by this integration build.

## Source ownership and licenses

`enigma.lock.json` pins the Fabric archive, Gradle distribution and complete supplied MCP delta.
`integrate.py` applies guarded source edits; `java/` contains the integration helpers. A content-addressed
build recipe and built-JAR hash are checked before launch. No external Enigma checkout is required after
cloning this repository, and none of the supplied source trees is modified by setup.

See [NOTICE](NOTICE), [LGPL license](LICENSE) and [incorporated GPL text](GPL-3.0.txt). The original remote
of the user-supplied MCP fork was not available; its provenance is stated rather than invented.

## Promoting a reviewed draft

`save_mappings` writes `draft.tiny` in the original obfuscated namespace. `tools/client-240/enigma-promote.py`
translates the renames in it to stable semantic keys (`classKY`, `field4053`, `method6623`) by matching the
semantic jar's `@ObfuscatedName` values, then writes a cluster shard and ledger shard:

```bash
python3 tools/client-240/enigma-promote.py --session .cache/client-240/enigma/sessions/<session> \
  --cluster 19-widgets --tier T4 --confidence B --evidence "cited method and reason" --dry-run
tools/client-240/check-shard.sh 19-widgets
```

It fails closed on unmatched or ambiguous entries, symbols that are API-named, collisions with other shards,
illegal identifiers and missing evidence. Per-symbol evidence can be supplied with `--evidence-file`.
`bytecode_*` keyword aliases and unrenamed entries are ignored. Parameter renames are not promoted yet.
`tools/client-240/test-promote.py` covers the translation and every refusal offline.
