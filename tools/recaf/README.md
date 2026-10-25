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
