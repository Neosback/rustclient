#!/usr/bin/env python3
"""Render an offline, searchable client-240 deobfuscation research workbench.

The renderer is presentation-only. It reads generated evidence and the declarative
pass policy, then writes a self-contained HTML page. It never reads Java source as
behavioral truth and never mutates mappings, jars, references, or ledgers.
"""
from __future__ import annotations

import argparse
import csv
import html
import json
from collections import Counter, defaultdict
from pathlib import Path
from typing import Iterable

ROOT = Path(__file__).resolve().parents[2]
HERE = Path(__file__).resolve().parent
DEFAULT_CACHE = ROOT / ".cache/client-240/deob-v2"
DEFAULT_PASSES = HERE / "deob-passes.json"

INDEX_COLUMNS = {
    "C": ("kind", "current", "canonical", "super", "canonicalSuper", "access", "fields", "methods"),
    "F": ("kind", "owner", "name", "desc", "access", "canonicalOwner", "canonicalName", "canonicalDesc"),
    "M": (
        "kind", "owner", "name", "desc", "access", "identity", "insns", "branches", "switches",
        "tryCatches", "strictHash", "canonicalHash", "invokes", "fields", "constants",
    ),
    "R": ("kind", "source", "target", "reasons", "count"),
}


def esc(value: object) -> str:
    return html.escape(str(value), quote=True)


def read_index(path: Path) -> list[dict[str, str]]:
    if not path.is_file():
        return []
    rows: list[dict[str, str]] = []
    for raw in path.read_text(encoding="utf-8", errors="replace").splitlines():
        if not raw or raw.startswith("#"):
            continue
        parts = raw.split("\t")
        columns = INDEX_COLUMNS.get(parts[0])
        if columns is None:
            continue
        if len(parts) < len(columns):
            parts += [""] * (len(columns) - len(parts))
        rows.append(dict(zip(columns, parts[: len(columns)])))
    return rows


def read_tsv(path: Path) -> list[dict[str, str]]:
    if not path.is_file():
        return []
    with path.open(encoding="utf-8", newline="") as handle:
        lines = (line for line in handle if not line.startswith("#"))
        return list(csv.DictReader(lines, delimiter="\t"))


def class_rows(index: Iterable[dict[str, str]]) -> list[dict[str, str]]:
    return [row for row in index if row.get("kind") == "C"]


def method_rows(index: Iterable[dict[str, str]]) -> list[dict[str, str]]:
    return [row for row in index if row.get("kind") == "M"]


def reference_rows(index: Iterable[dict[str, str]]) -> list[dict[str, str]]:
    return [row for row in index if row.get("kind") == "R"]


def drift_by_class(audit: list[dict[str, str]]) -> Counter[str]:
    counts: Counter[str] = Counter()
    for row in audit:
        identity = row.get("identity", "")
        if "." in identity:
            counts[identity.split(".", 1)[0]] += 1
        original = row.get("original", "")
        if original and original != "-" and "." in original:
            counts[original.split(".", 1)[0]] += 0
        semantic = row.get("semantic", "")
        if semantic and semantic != "-" and "." in semantic:
            counts[semantic.split(".", 1)[0]] += 0
    return counts


def index_method_map(index: list[dict[str, str]]) -> dict[str, dict[str, str]]:
    return {row["identity"]: row for row in method_rows(index) if row.get("identity")}


def load_dossiers(cache: Path) -> list[dict[str, object]]:
    root = cache / "dossiers"
    if not root.is_dir():
        return []
    out: list[dict[str, object]] = []
    for manifest_path in sorted(root.glob("*/manifest.json")):
        try:
            data = json.loads(manifest_path.read_text(encoding="utf-8"))
        except (OSError, json.JSONDecodeError):
            continue
        data["_manifest"] = manifest_path.relative_to(cache).as_posix()
        out.append(data)
    return out


def badge(text: str, css: str = "") -> str:
    cls = "badge" + (" " + css if css else "")
    return f'<span class="{cls}">{esc(text)}</span>'


def table(headers: list[str], rows: Iterable[list[object]], *, searchable: bool = True) -> str:
    body = []
    for values in rows:
        rendered = [str(value) for value in values]
        search = " ".join(rendered).lower()
        attr = f' data-search="{esc(search)}"' if searchable else ""
        body.append("<tr" + attr + ">" + "".join(f"<td>{value}</td>" for value in rendered) + "</tr>")
    return (
        '<div class="table-wrap"><table>'
        + "<thead><tr>"
        + "".join(f"<th>{esc(header)}</th>" for header in headers)
        + "</tr></thead><tbody>"
        + "".join(body)
        + "</tbody></table></div>"
    )


def render(cache: Path, passes_path: Path, output: Path) -> None:
    policy = json.loads(passes_path.read_text(encoding="utf-8"))
    original_index = read_index(cache / "index-original.tsv")
    semantic_index = read_index(cache / "index-semantic.tsv")
    audit = read_tsv(cache / "original-vs-semantic.tsv")
    dossiers = load_dossiers(cache)

    original_classes = class_rows(original_index)
    semantic_classes = class_rows(semantic_index)
    original_methods = method_rows(original_index)
    semantic_methods = method_rows(semantic_index)
    refs = reference_rows(original_index)
    drift_counts = drift_by_class(audit)
    original_method_by_identity = index_method_map(original_index)
    semantic_method_by_identity = index_method_map(semantic_index)

    flags: Counter[str] = Counter()
    for row in audit:
        for flag in row.get("flags", "").split("|"):
            if flag:
                flags[flag] += 1

    generated = {
        "original index": (cache / "index-original.tsv").is_file(),
        "semantic index": (cache / "index-semantic.tsv").is_file(),
        "drift audit": (cache / "original-vs-semantic.tsv").is_file(),
        "dossiers": bool(dossiers),
    }

    summary_cards = [
        ("Original classes", len(original_classes)),
        ("Original methods", len(original_methods)),
        ("Reference edges", len(refs)),
        ("Drifted methods", len(audit)),
        ("Semantic classes", len(semantic_classes)),
        ("Dossiers", len(dossiers)),
    ]

    class_table_rows = []
    semantic_by_canonical = {row.get("canonical", ""): row for row in semantic_classes}
    for row in original_classes:
        canonical = row.get("canonical", "")
        sem = semantic_by_canonical.get(canonical, {})
        class_table_rows.append([
            f"<code>{esc(row.get('current', ''))}</code>",
            f"<code>{esc(canonical)}</code>",
            f"<code>{esc(sem.get('current', '-'))}</code>",
            esc(row.get("canonicalSuper", "-")),
            esc(row.get("fields", "0")),
            esc(row.get("methods", "0")),
            badge(str(drift_counts.get(canonical, 0)), "warn" if drift_counts.get(canonical, 0) else "ok"),
        ])

    audit_rows = []
    for row in audit:
        identity = row.get("identity", "")
        orig = original_method_by_identity.get(identity, {})
        sem = semantic_method_by_identity.get(identity, {})
        row_flags = [flag for flag in row.get("flags", "").split("|") if flag]
        audit_rows.append([
            f"<code>{esc(identity)}</code>",
            f"<code>{esc(row.get('original', '-'))}</code>",
            f"<code>{esc(row.get('semantic', '-'))}</code>",
            " ".join(badge(flag, "danger" if flag in {"try-catch-changed", "branch-shape-changed", "missing-original", "missing-semantic"} else "warn") for flag in row_flags),
            esc(row.get("origInsn", orig.get("insns", "-"))),
            esc(row.get("semInsn", sem.get("insns", "-"))),
            esc(row.get("origTryCatch", orig.get("tryCatches", "-"))),
            esc(row.get("semTryCatch", sem.get("tryCatches", "-"))),
        ])

    reference_table_rows = [
        [
            f"<code>{esc(row.get('source', ''))}</code>",
            f"<code>{esc(row.get('target', ''))}</code>",
            esc(row.get("reasons", "")),
            esc(row.get("count", "")),
        ]
        for row in refs
    ]

    pass_rows = []
    for item in policy.get("passes", []):
        safety = item.get("safety", "")
        safety_css = {
            "evidence-only": "ok",
            "identity-safe": "identity",
            "analysis-equivalent": "warn",
            "analysis-only": "danger",
            "excluded-destructive": "blocked",
        }.get(safety, "")
        pass_rows.append([
            f"<code>{esc(item.get('id', ''))}</code>",
            esc(item.get("stage", "")),
            badge(safety, safety_css),
            badge(item.get("status", "")),
            "yes" if item.get("default") else "no",
            esc(item.get("input_view", "")),
            esc(item.get("output", "")),
            esc(item.get("inspiration", "")),
        ])

    view_rows = [
        [
            f"<code>{esc(view.get('id', ''))}</code>",
            esc(view.get("title", "")),
            "YES" if view.get("behavioral_authority") else "no",
            esc(view.get("purpose", "")),
            ", ".join(esc(x) for x in view.get("allowed_safety", [])),
        ]
        for view in policy.get("views", [])
    ]

    dossier_rows = []
    for item in dossiers:
        dossier_rows.append([
            esc(item.get("_manifest", "")),
            ", ".join(f"<code>{esc(x)}</code>" for x in item.get("classes", [])),
            esc(item.get("behavioral_authority", "")),
            esc(item.get("identity_overlay", "")),
        ])

    source_rows = []
    for name, source in policy.get("sources", {}).items():
        source_rows.append([
            f"<code>{esc(name)}</code>",
            esc(source.get("repository", "")),
            esc(source.get("license", "license not recorded/clarified")),
            esc(source.get("role", "")),
            "<br>".join(esc(x) for x in source.get("use", [])),
        ])

    missing = [name for name, exists in generated.items() if not exists]
    command_hint = "python3 tools/client-240/deob-v2.py index && python3 tools/client-240/deob-v2.py audit"

    style = """
:root { color-scheme: dark; font-family: ui-sans-serif, system-ui, sans-serif; }
body { margin: 0; background: #0d1117; color: #e6edf3; }
main { max-width: 1600px; margin: auto; padding: 24px; }
h1, h2 { margin-top: 0; }
section { margin: 22px 0; padding: 18px; border: 1px solid #30363d; border-radius: 10px; background: #161b22; }
.warning { border-left: 5px solid #d29922; padding: 12px 14px; background: #2b2111; }
.grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(170px, 1fr)); gap: 10px; }
.card { padding: 14px; border: 1px solid #30363d; border-radius: 8px; background: #0d1117; }
.card strong { display: block; font-size: 1.6rem; }
input { width: 100%; box-sizing: border-box; padding: 11px; margin: 12px 0; background: #0d1117; color: #e6edf3; border: 1px solid #30363d; border-radius: 7px; }
.table-wrap { overflow: auto; max-height: 70vh; border: 1px solid #30363d; border-radius: 7px; }
table { width: 100%; border-collapse: collapse; font-size: 13px; }
th { position: sticky; top: 0; background: #21262d; z-index: 1; text-align: left; }
th, td { padding: 7px 9px; border-bottom: 1px solid #30363d; vertical-align: top; }
code { font-family: ui-monospace, SFMono-Regular, Menlo, monospace; white-space: nowrap; }
.badge { display: inline-block; padding: 2px 6px; margin: 1px; border: 1px solid #484f58; border-radius: 999px; font-size: 11px; }
.badge.ok { border-color: #238636; }
.badge.identity { border-color: #1f6feb; }
.badge.warn { border-color: #d29922; }
.badge.danger { border-color: #f85149; }
.badge.blocked { border-color: #8b949e; opacity: .75; }
.muted { color: #8b949e; }
nav a { margin-right: 14px; color: #58a6ff; }
.hidden { display: none; }
"""

    cards_html = "".join(f'<div class="card"><span class="muted">{esc(name)}</span><strong>{esc(value)}</strong></div>' for name, value in summary_cards)
    flag_html = " ".join(badge(f"{name}: {count}", "warn") for name, count in flags.most_common()) or '<span class="muted">No drift audit loaded.</span>'
    missing_html = ""
    if missing:
        missing_html = (
            '<div class="warning"><strong>Evidence is incomplete.</strong> Missing: '
            + esc(", ".join(missing))
            + f'. Generate evidence with <code>{esc(command_hint)}</code>, then render the workbench again.</div>'
        )

    page = f"""<!doctype html>
<html lang="en"><head><meta charset="utf-8"><meta name="viewport" content="width=device-width,initial-scale=1">
<title>Client-240 deob workbench</title><style>{style}</style></head>
<body><main>
<h1>Client-240 deobfuscation workbench</h1>
<p class="warning"><strong>Behavioral authority:</strong> only the original-exact gamepack view. Semantic transforms, normalized bytecode, readable names, Vineflower, CFR, graphs, and this page are research aids and must remain traceable to original bytecode.</p>
{missing_html}
<nav><a href="#views">Views</a><a href="#classes">Classes</a><a href="#drift">Drift</a><a href="#refs">References</a><a href="#passes">Passes</a><a href="#dossiers">Dossiers</a><a href="#sources">Sources</a></nav>
<section><h2>Corpus summary</h2><div class="grid">{cards_html}</div><p>{flag_html}</p></section>
<section id="views"><h2>Evidence views</h2>{table(["View", "Title", "Behavior authority", "Purpose", "Allowed safety classes"], view_rows)}</section>
<section id="classes"><h2>Class explorer</h2><input class="filter" data-target="classes-table" placeholder="Filter class, canonical identity, superclass, semantic name..."><div id="classes-table">{table(["Original", "Canonical/obfuscated identity", "Semantic", "Superclass", "Fields", "Methods", "Drifted methods"], class_table_rows)}</div></section>
<section id="drift"><h2>Original vs semantic method drift</h2><input class="filter" data-target="drift-table" placeholder="Filter method identity or drift flag..."><div id="drift-table">{table(["Identity", "Original", "Semantic", "Flags", "Original insns", "Semantic insns", "Original try/catch", "Semantic try/catch"], audit_rows)}</div></section>
<section id="refs"><h2>Original bytecode reference graph</h2><input class="filter" data-target="refs-table" placeholder="Filter source, target, or edge reason..."><div id="refs-table">{table(["Source", "Target", "Reasons", "Count"], reference_table_rows)}</div></section>
<section id="passes"><h2>Pass registry</h2><input class="filter" data-target="passes-table" placeholder="Filter pass, stage, safety, status, or inspiration..."><div id="passes-table">{table(["Pass", "Stage", "Safety", "Status", "Default", "Input view", "Output", "Inspiration"], pass_rows)}</div></section>
<section id="dossiers"><h2>Research dossiers</h2>{table(["Manifest", "Classes", "Behavioral authority", "Identity overlay"], dossier_rows) if dossier_rows else '<p class="muted">No dossiers generated yet.</p>'}</section>
<section id="sources"><h2>Upstream design sources and licensing boundary</h2>{table(["Source", "Repository", "License status", "Role", "What we use"], source_rows)}</section>
<script>
document.querySelectorAll('.filter').forEach(input => {{
  const root = document.getElementById(input.dataset.target);
  input.addEventListener('input', () => {{
    const q = input.value.toLowerCase().trim();
    root.querySelectorAll('tbody tr').forEach(row => {{
      row.classList.toggle('hidden', q && !row.dataset.search.includes(q));
    }});
  }});
}});
</script>
</main></body></html>"""
    output.parent.mkdir(parents=True, exist_ok=True)
    output.write_text(page, encoding="utf-8")
    print(f"workbench: {output}")


def parser() -> argparse.ArgumentParser:
    cli = argparse.ArgumentParser(description=__doc__)
    cli.add_argument("--cache", type=Path, default=DEFAULT_CACHE)
    cli.add_argument("--passes", type=Path, default=DEFAULT_PASSES)
    cli.add_argument("--out", type=Path)
    return cli


def main() -> int:
    args = parser().parse_args()
    cache = args.cache.expanduser().resolve()
    passes_path = args.passes.expanduser().resolve()
    output = args.out.expanduser().resolve() if args.out else cache / "workbench/index.html"
    render(cache, passes_path, output)
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
