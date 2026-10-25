# Recaf MCP for client research

This repository owns a small **stdio MCP server backed by Recaf's real headless services**.
It creates a fresh workspace from a byte-identical copy of the pinned revision-240 injected client.
The original JAR and reference trees are never exported over or modified.

## Setup

Prerequisites: Python 3.9+, Git, network access for the initial build, and JDK 24+.
The naming tools only need JDK 17; this optional Recaf build deliberately targets the installed
JDK 24 rather than requiring an additional JDK 22 toolchain.

From the repository root:

```bash
python3 tools/recaf/recaf.py --build
python3 tools/recaf/recaf.py --check
python3 tools/recaf/verify.py
python3 tools/recaf/install-mcp.py
python3 tools/recaf/verify.py --registered
```

The installer adds `rustclient-recaf` to the current Codex configuration using the
[official `codex mcp add` interface](https://learn.chatgpt.com/docs/extend/mcp?surface=cli).
It resolves this checkout, Python and the verified host JDK at installation time, so no developer's
absolute paths are committed. It refuses to replace an entry pointing at another server.
Start a new Codex session or reload MCP connections after registration. Registration alone does not
prove an already-running chat has loaded the tools; `verify.py` independently tests the actual server.

To use another JDK, set `CLIENT240_JAVA` to its `bin/java`, rebuild if necessary, then rerun the installer.
To remove the registration: `codex mcp remove rustclient-recaf`.

## Available tools

| Tool | Purpose |
| --- | --- |
| `workspace_status` | Corpus size and available Recaf decompilers |
| `list_classes` | Sorted class names with prefix and pagination |
| `class_outline` | Exact hierarchy, fields, methods and descriptors |
| `decompile_class` | A paginated Java reading view; optional decompiler selection |
| `method_bytecode` | Exact method disassembly, selected by name and descriptor |
| `search_strings` | String constants with class/member and ASM tree instruction index |

Every successful tool response includes the loaded artifact's SHA-256. Search `asmIndex` counts ASM
nodes including labels/debug/frame nodes; workbench `opcodeIndex` counts opcode-bearing instructions
only. Do not interchange these coordinate systems.

Decompilation now defaults to **Vineflower** and a `metadata-clean` view. The view removes top-level
`javax.inject.Named`/`jakarta.inject.Named` annotations while preserving every opaque JVM `Code`
attribute, constant pool, member identity, other attribute and non-Named annotation. `bytecodeView:
"original"` explicitly restores the noisy original view. Responses identify both the input JAR and
the selected reading JAR hashes. All outlines, string searches and method disassembly still use the
original workspace. Annotation reflection changes in the derived view; it is analysis-only.

After updating/rebuilding the bridge, reload its MCP connection to replace an already-running older
process. `verify.py --registered` tests a fresh process using the registered command.

## Compare and export readable code

```bash
# A new output directory is required for each comparison.
python3 tools/recaf/compare.py --out .cache/client-240/quality-comparison-new
# Optional local experiment; JADX is not a required production dependency.
python3 tools/recaf/compare.py --out .cache/client-240/quality-with-jadx \
  --jadx "/path/to/Java Deob/jadx/bin/jadx"

# Build class-identity/Java-keyword aliases and prove the rename roundtrip.
python3 tools/recaf/java_safe.py --identity
# Export all classes and run uncapped syntax diagnostics; fresh output only.
python3 tools/recaf/export.py --out .cache/client-240/reading-export-new
# Inspect whole-class alternatives only for files flagged by that export.
python3 tools/recaf/fallbacks.py --reading .cache/client-240/reading-export-new
python3 tools/recaf/fallbacks.py --reading .cache/client-240/reading-export-new --procyon-only
python3 tools/recaf/assemble.py --reading .cache/client-240/reading-export-new \
  --out .cache/client-240/reading-bundle-new

# Render one original class with bundled Vineflower 1.12 and complete type context.
python3 tools/recaf/render.py xs
```

`compare.py` benchmarks Recaf Vineflower 1.11.2, CFR 0.152 and Procyon 0.6.0, then bundled Vineflower
1.12.0. `SourceMetrics.java` uses the JDK parser without type attribution. It sets a high diagnostic
limit so one broken file cannot silently hide errors in later files. Parse success is not type checking
or behavioral equivalence. Timeouts, failure markers and method-name review flags remain in manifests;
source from different renderers is never spliced together.

`java_safe.py --identity` reuses only exact class identities from the pinned semantic index. It retains
original member names apart from documented Java-keyword aliases, and proves the full forward/reverse
rename against the metadata view across 735 classes. The complete tiny mapping and original-to-stable
member index accompany exports. This avoids inheriting control-flow/exception/constant-dynamic changes
from the semantic JAR. Production naming shards remain a separate reviewed layer.
Each export and assembled bundle snapshots its reading JAR, mapping, source hashes and identity index,
so a later export cannot silently replace the bytecode used for an earlier reading session.

`export.py` returns `REVIEW_REQUIRED` if any source is missing, fails syntax parsing, contains a failed
body, an unresugared constructor or constant-dynamic pseudo-Java. Such an export remains useful evidence;
its status must not be presented as compilable or behavior-verified code. It has a 15-minute process cap;
class rendering and fallback tools have shorter caps.
`export.py` and `assemble.py` deliberately exit with code 1 for `REVIEW_REQUIRED`; inspect their manifests
and proceed with fallback research rather than treating the files as approved source.

`assemble.py` chooses only whole-class alternatives with successful exit, zero syntax/failure markers,
and represented non-synthetic field/method names. It leaves unresolved files flagged and attaches
complete original-code ASM dumps through `dump_unresolved.py`. These checks help choose a reading view;
they do not prove that the Java has identical behavior or compiles against all dependencies.

Run focused tooling checks with `test_metadata.py`, `test_source_metrics.py`, and `verify.py --registered`.
See [the measured comparison](../../docs/reference/client-240/DECOMPILER-QUALITY.md) for results and
remaining limitations.

The bridge exposes inspection only. Mapping promotion remains in tiny-v2 shards and the reviewed ledger.
There is no arbitrary script execution, client-method evaluation, workspace mutation or export tool.
Recaf is used headlessly here; this server does not control a separately opened GUI window.

## Reproducibility and ownership

- `src/RecafMcp.java`: the complete adapter, using Recaf workspace/decompiler services and ASM.
- `recaf.py`: builds, verifies and launches it using Python's standard library.
- `recaf.lock.json`: upstream source commit/archive hash, Gradle distribution hash and Java target.
- `Recaf-LICENSE`: upstream MIT license; original project [Col-E/Recaf](https://github.com/Col-E/Recaf).
- `verify.py`: exercises MCP initialize, notifications, catalog, all six tools, errors, EOF shutdown
  and original checksum preservation.
- `install-mcp.py`: idempotent Codex registration for this checkout.

The source API was checked against `/Users/tylercovalt/Documents/Recaf-master`; that checkout is not a
runtime prerequisite. Build inputs come from the pinned official source archive, not an unversioned
external folder. Recaf's README pointed at a missing MCP plugin, so this adapter avoids an unreviewed
third-party plugin dependency.

Recaf source, generated distribution, Gradle dependencies and isolated sessions live in
`.cache/client-240/recaf/` by default. They are **not** committed binaries. First builds need network;
the source/Gradle bootstraps have checksum verification, while transitive build dependencies use upstream
Gradle version declarations. This is a traceable build, not a claim of a fully hermetic dependency closure.
`build.json` records the generated Recaf JAR, bridge source and classfile hashes and is checked at startup.
Session folders remain for inspection; remove them after their MCP processes have exited.

The MCP entry launches its own Recaf process and reserves stdout for newline-delimited JSON-RPC.
Recaf diagnostics go to stderr. EOF terminates that child and its owned executor pools. No network
listener or persistent background service is required.
