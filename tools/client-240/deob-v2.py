#!/usr/bin/env python3
"""Bytecode-first revision-240 deobfuscation research workbench.

This CLI orchestrates repository-pinned evidence tools. It does not modify jars,
mapping shards, ledgers, Java sources, or Rust code.
"""
from __future__ import annotations

import argparse
import csv
import json
import os
from pathlib import Path
import subprocess
import sys
from typing import Iterable

ROOT = Path(__file__).resolve().parents[2]
HERE = Path(__file__).resolve().parent
DOCS = ROOT / "docs/reference/client-240"
CACHE_DEFAULT = ROOT / ".cache/client-240/deob-v2"
TOOLCHAIN = HERE / "toolchain.py"
EVIDENCE_JAVA = HERE / "java/Client240Evidence.java"
INPUTS = HERE / "inputs.json"
PASS_MANIFEST = HERE / "deob-passes.json"
WORKBENCH_RENDERER = HERE / "render-workbench.py"
VENDOR = Path(os.environ.get("CLIENT240_TOOLS", HERE / "vendor")).resolve()
ASM = os.pathsep.join(
    str(VENDOR / f"tiny_deps/{name}")
    for name in (
        "asm-9.6.jar",
        "asm-tree-9.6.jar",
        "asm-commons-9.6.jar",
        "asm-util-9.6.jar",
        "asm-analysis-9.6.jar",
    )
)


def run(command: list[str], *, capture: bool = False) -> subprocess.CompletedProcess[str]:
    return subprocess.run(
        command,
        cwd=ROOT,
        text=True,
        check=True,
        stdout=subprocess.PIPE if capture else None,
        stderr=subprocess.PIPE if capture else None,
    )


def runtime() -> tuple[str, str]:
    result = run([sys.executable, str(TOOLCHAIN), "--runtime"], capture=True)
    lines = [line for line in result.stdout.splitlines() if line.strip()]
    if len(lines) != 2:
        raise RuntimeError(f"unexpected toolchain runtime output: {result.stdout!r}")
    return lines[0], lines[1]


def toolchain_check() -> None:
    run([sys.executable, str(TOOLCHAIN), "--check"])


def safe_output(path: Path) -> Path:
    result = run([sys.executable, str(TOOLCHAIN), "--output", str(path)], capture=True)
    return Path(result.stdout.strip())


def input_paths() -> dict[str, Path]:
    manifest = json.loads(INPUTS.read_text(encoding="utf-8"))
    paths: dict[str, Path] = {}
    for entry in manifest["artifacts"]:
        role = entry["role"]
        if role in {"original", "semantic"}:
            paths[role] = (ROOT / entry["path"]).resolve()
    if "CLIENT240_SEMANTIC_JAR" in os.environ:
        paths["semantic"] = Path(os.environ["CLIENT240_SEMANTIC_JAR"]).resolve()
    if "CLIENT240_INPUT_JAR" in os.environ:
        paths["original"] = Path(os.environ["CLIENT240_INPUT_JAR"]).resolve()
    return paths


def resolve_jar(value: str) -> Path:
    known = input_paths()
    if value in known:
        return known[value]
    path = Path(value).expanduser().resolve()
    if not path.is_file():
        raise FileNotFoundError(path)
    return path


def evidence_command(java: str, *args: str) -> list[str]:
    return [java, "--source", "17", "-cp", ASM, str(EVIDENCE_JAVA), *args]


def build_index(java: str, jar: Path, out: Path) -> Path:
    target = safe_output(out)
    target.parent.mkdir(parents=True, exist_ok=True)
    run(evidence_command(java, "index", str(jar), str(target)))
    return target


def build_audit(java: str, original: Path, semantic: Path, out: Path) -> Path:
    target = safe_output(out)
    target.parent.mkdir(parents=True, exist_ok=True)
    run(evidence_command(java, "audit", str(original), str(semantic), str(target)))
    return target


def command_index(args: argparse.Namespace, java: str, cache: Path) -> int:
    jars = input_paths()
    selected = ["original", "semantic"] if args.jar == "both" else [args.jar]
    for role in selected:
        jar = jars[role] if role in jars else resolve_jar(role)
        out = cache / f"index-{role}.tsv"
        built = build_index(java, jar, out)
        print(f"{role}: {built.relative_to(ROOT)}")
    return 0


def command_audit(args: argparse.Namespace, java: str, cache: Path) -> int:
    jars = input_paths()
    out = Path(args.out).resolve() if args.out else cache / "original-vs-semantic.tsv"
    built = build_audit(java, jars["original"], jars["semantic"], out)
    counts = audit_counts(built)
    print(f"audit: {built.relative_to(ROOT)}")
    for name, count in counts.items():
        print(f"  {name}: {count}")
    return 0


def command_refs(args: argparse.Namespace, java: str, _cache: Path) -> int:
    jar = resolve_jar(args.jar)
    completed = run(evidence_command(java, "refs", str(jar), *args.classes), capture=True)
    sys.stdout.write(completed.stdout)
    if completed.stderr:
        sys.stderr.write(completed.stderr)
    return 0


def read_audit(path: Path) -> Iterable[dict[str, str]]:
    with path.open(encoding="utf-8", newline="") as handle:
        lines = (line for line in handle if not line.startswith("#"))
        yield from csv.DictReader(lines, delimiter="\t")


def audit_counts(path: Path) -> dict[str, int]:
    counts: dict[str, int] = {"changed-methods": 0}
    for row in read_audit(path):
        counts["changed-methods"] += 1
        for flag in row["flags"].split("|"):
            counts[flag] = counts.get(flag, 0) + 1
    return counts


def class_audit_rows(path: Path, classes: list[str]) -> list[dict[str, str]]:
    wanted = set(classes)
    rows = []
    for row in read_audit(path):
        identity_class = row["identity"].split(".", 1)[0]
        original_class = row["original"].split(".", 1)[0] if row["original"] != "-" else "-"
        semantic_class = row["semantic"].split(".", 1)[0] if row["semantic"] != "-" else "-"
        if wanted.intersection({identity_class, original_class, semantic_class}):
            rows.append(row)
    return rows


def write_drift_excerpt(path: Path, classes: list[str], out: Path) -> None:
    rows = class_audit_rows(path, classes)
    with out.open("w", encoding="utf-8", newline="") as handle:
        if not rows:
            handle.write("identity\toriginal\tsemantic\tflags\n")
            return
        writer = csv.DictWriter(handle, fieldnames=list(rows[0]), delimiter="\t", lineterminator="\n")
        writer.writeheader()
        writer.writerows(rows)


def command_dossier(args: argparse.Namespace, java: str, cache: Path) -> int:
    jars = input_paths()
    dossier_dir = safe_output(cache / "dossiers" / args.name)
    dossier_dir.mkdir(parents=True, exist_ok=True)

    audit_path = cache / "original-vs-semantic.tsv"
    if args.refresh_audit or not audit_path.is_file():
        build_audit(java, jars["original"], jars["semantic"], audit_path)

    refs_path = dossier_dir / "semantic-refs.tsv"
    refs = run(evidence_command(java, "refs", str(jars["semantic"]), *args.classes), capture=True)
    refs_path.write_text(refs.stdout, encoding="utf-8")
    write_drift_excerpt(audit_path, args.classes, dossier_dir / "drift.tsv")

    ensemble = HERE / "decompile-ensemble.py"
    if not ensemble.is_file():
        raise RuntimeError(f"decompiler ensemble not installed: {ensemble}")
    command = [sys.executable, str(ensemble), "--out", str(dossier_dir / "source"), *args.classes]
    if args.semantic_only:
        command.insert(2, "--semantic-only")
    run(command)

    manifest = {
        "schema": 1,
        "classes": args.classes,
        "behavioral_authority": str(jars["original"].relative_to(ROOT)),
        "identity_overlay": str(jars["semantic"].relative_to(ROOT)),
        "audit": str(audit_path.relative_to(ROOT)),
        "references": str(refs_path.relative_to(ROOT)),
        "drift_excerpt": str((dossier_dir / "drift.tsv").relative_to(ROOT)),
        "decompiler_manifest": str((dossier_dir / "source/manifest.json").relative_to(ROOT)),
    }
    (dossier_dir / "manifest.json").write_text(json.dumps(manifest, indent=2) + "\n", encoding="utf-8")
    print(f"dossier: {dossier_dir.relative_to(ROOT)}")
    return 0


def command_plan(_args: argparse.Namespace, _java: str, _cache: Path) -> int:
    policy = json.loads(PASS_MANIFEST.read_text(encoding="utf-8"))
    print(f"behavioral authority: {policy['behavioral_authority']}")
    print("\nviews:")
    for view in policy["views"]:
        authority = " [BEHAVIORAL AUTHORITY]" if view.get("behavioral_authority") else ""
        print(f"  {view['id']}{authority}: {view['purpose']}")
    print("\npasses:")
    grouped: dict[str, list[dict[str, object]]] = {}
    for item in policy["passes"]:
        grouped.setdefault(str(item["stage"]), []).append(item)
    for stage, items in grouped.items():
        print(f"  [{stage}]")
        for item in items:
            default = "default" if item.get("default") else "opt-in"
            print(
                f"    {item['id']}: {item['safety']}; {item['status']}; {default}; "
                f"input={item['input_view']}"
            )
    return 0


def command_workbench(args: argparse.Namespace, _java: str, cache: Path) -> int:
    if not WORKBENCH_RENDERER.is_file():
        raise RuntimeError(f"workbench renderer not installed: {WORKBENCH_RENDERER}")
    output = safe_output(Path(args.out).resolve()) if args.out else safe_output(cache / "workbench/index.html")
    run(
        [
            sys.executable,
            str(WORKBENCH_RENDERER),
            "--cache",
            str(cache),
            "--passes",
            str(PASS_MANIFEST),
            "--out",
            str(output),
        ]
    )
    return 0


def parser() -> argparse.ArgumentParser:
    cli = argparse.ArgumentParser(description=__doc__)
    cli.add_argument(
        "--cache",
        type=Path,
        default=CACHE_DEFAULT,
        help="generated-output root; must pass the existing client-240 safe-output policy",
    )
    cli.add_argument(
        "--skip-toolchain-check",
        action="store_true",
        help="skip pinned hash/tool presence check; runtime discovery still requires JDK 17+ for bytecode commands",
    )
    sub = cli.add_subparsers(dest="command", required=True)

    plan = sub.add_parser("plan", help="show evidence views, pass safety, status, and default policy")
    plan.set_defaults(func=command_plan, needs_runtime=False)

    index = sub.add_parser("index", help="build bytecode evidence index without decompiling")
    index.add_argument("--jar", choices=("original", "semantic", "both"), default="both")
    index.set_defaults(func=command_index, needs_runtime=True)

    audit = sub.add_parser("audit", help="compare original and semantic methods by obfuscated identity")
    audit.add_argument("--out", help="output TSV below the client-240 cache root")
    audit.set_defaults(func=command_audit, needs_runtime=True)

    refs = sub.add_parser("refs", help="query exact bytecode class reference sets")
    refs.add_argument("classes", nargs="+")
    refs.add_argument("--jar", default="semantic", help="original, semantic, or a jar path")
    refs.set_defaults(func=command_refs, needs_runtime=True)

    dossier = sub.add_parser("dossier", help="assemble drift, references, and dual-decompiler evidence")
    dossier.add_argument("classes", nargs="+")
    dossier.add_argument("--name", default="adhoc")
    dossier.add_argument("--refresh-audit", action="store_true")
    dossier.add_argument(
        "--semantic-only",
        action="store_true",
        help="do not render the original jar; bytecode drift evidence is still retained",
    )
    dossier.set_defaults(func=command_dossier, needs_runtime=True)

    workbench = sub.add_parser("workbench", help="render offline searchable HTML from generated evidence")
    workbench.add_argument("--out", help="HTML output path below the client-240 cache root")
    workbench.set_defaults(func=command_workbench, needs_runtime=False)
    return cli


def main() -> int:
    args = parser().parse_args()
    try:
        cache = safe_output(Path(args.cache).resolve() / "root.marker").parent
        java = ""
        if args.needs_runtime:
            if not args.skip_toolchain_check:
                toolchain_check()
            java, _javap = runtime()
        return args.func(args, java, cache)
    except (OSError, RuntimeError, subprocess.CalledProcessError, KeyError, ValueError) as error:
        print(f"client-240 deob-v2: {error}", file=sys.stderr)
        return 1


if __name__ == "__main__":
    raise SystemExit(main())
