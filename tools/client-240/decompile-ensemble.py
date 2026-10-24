#!/usr/bin/env python3
"""Render selected revision-240 classes through Vineflower and CFR side by side.

Decompiler output is disposable presentation evidence. Original-bytecode facts remain
authoritative. This script never edits a jar or a source tree under references/.
"""
from __future__ import annotations

import argparse
import hashlib
import json
import os
from pathlib import Path
import re
import shutil
import subprocess
import sys
import zipfile

ROOT = Path(__file__).resolve().parents[2]
HERE = Path(__file__).resolve().parent
TOOLCHAIN = HERE / "toolchain.py"
WORKBENCH = HERE / "deob-v2.py"
INPUTS = HERE / "inputs.json"
CACHE = ROOT / ".cache/client-240/deob-v2"
VENDOR = Path(os.environ.get("CLIENT240_TOOLS", HERE / "vendor")).resolve()
VINEFLOWER = VENDOR / "vineflower-1.12.0.jar"
CFR = VENDOR / "cfr-0.152.jar"
CLASS_NAME = re.compile(r"^[A-Za-z_$][A-Za-z0-9_$]*$")
SHAPE_WORDS = ("if", "switch", "for", "while", "try", "catch", "break", "continue", "return", "throw")
SUSPICIOUS = ("$VF", "Couldn't be decompiled", "Could not be decompiled", "IllegalStateException")


def run(command: list[str], *, check: bool = True) -> subprocess.CompletedProcess[str]:
    return subprocess.run(
        command,
        cwd=ROOT,
        text=True,
        stdout=subprocess.PIPE,
        stderr=subprocess.PIPE,
        check=check,
    )


def runtime() -> str:
    completed = run([sys.executable, str(TOOLCHAIN), "--runtime"])
    lines = [line for line in completed.stdout.splitlines() if line.strip()]
    if len(lines) != 2:
        raise RuntimeError(f"unexpected runtime output: {completed.stdout!r}")
    return lines[0]


def toolchain_check() -> None:
    completed = run([sys.executable, str(TOOLCHAIN), "--check"], check=False)
    if completed.returncode:
        raise RuntimeError((completed.stdout + completed.stderr).strip())


def safe_dir(path: Path) -> Path:
    marker = path.resolve() / ".output-guard"
    completed = run([sys.executable, str(TOOLCHAIN), "--output", str(marker)])
    return Path(completed.stdout.strip()).parent


def input_paths() -> dict[str, Path]:
    manifest = json.loads(INPUTS.read_text(encoding="utf-8"))
    result: dict[str, Path] = {}
    for entry in manifest["artifacts"]:
        if entry["role"] in {"original", "semantic"}:
            result[entry["role"]] = (ROOT / entry["path"]).resolve()
    result["semantic"] = Path(os.environ.get("CLIENT240_SEMANTIC_JAR", result["semantic"])).resolve()
    result["original"] = Path(os.environ.get("CLIENT240_INPUT_JAR", result["original"])).resolve()
    return result


def ensure_indexes() -> tuple[Path, Path]:
    original = CACHE / "index-original.tsv"
    semantic = CACHE / "index-semantic.tsv"
    if original.is_file() and semantic.is_file():
        return original, semantic
    completed = run(
        [
            sys.executable,
            str(WORKBENCH),
            "--skip-toolchain-check",
            "index",
            "--jar",
            "both",
        ],
        check=False,
    )
    if completed.returncode:
        raise RuntimeError("failed to build identity indexes:\n" + completed.stdout + completed.stderr)
    if not original.is_file() or not semantic.is_file():
        raise RuntimeError("workbench reported success but identity indexes are missing")
    return original, semantic


def read_class_identities(path: Path) -> tuple[dict[str, str], dict[str, str]]:
    current_to_canonical: dict[str, str] = {}
    canonical_to_current: dict[str, str] = {}
    with path.open(encoding="utf-8") as handle:
        for line in handle:
            if not line.startswith("C\t"):
                continue
            parts = line.rstrip("\n").split("\t")
            current, canonical = parts[1], parts[2]
            current_to_canonical[current] = canonical
            canonical_to_current.setdefault(canonical, current)
    return current_to_canonical, canonical_to_current


def extract_class_family(jar: Path, class_name: str, destination: Path) -> Path:
    destination.mkdir(parents=True, exist_ok=True)
    main_entry = class_name + ".class"
    prefix = class_name + "$"
    extracted: Path | None = None
    with zipfile.ZipFile(jar) as archive:
        names = set(archive.namelist())
        if main_entry not in names:
            raise RuntimeError(f"{class_name} is not present in {jar}")
        for name in sorted(names):
            if name == main_entry or (name.startswith(prefix) and name.endswith(".class")):
                target = destination / name
                target.parent.mkdir(parents=True, exist_ok=True)
                target.write_bytes(archive.read(name))
                if name == main_entry:
                    extracted = target
    if extracted is None:
        raise RuntimeError(f"failed to extract {main_entry}")
    return extracted


def sha256(path: Path) -> str:
    digest = hashlib.sha256()
    with path.open("rb") as handle:
        for block in iter(lambda: handle.read(1024 * 1024), b""):
            digest.update(block)
    return digest.hexdigest()


def find_source(directory: Path, class_name: str) -> Path | None:
    exact = list(directory.rglob(class_name + ".java"))
    if exact:
        return exact[0]
    candidates = list(directory.rglob("*.java"))
    return candidates[0] if len(candidates) == 1 else None


def source_facts(path: Path | None) -> dict[str, object]:
    if path is None or not path.is_file():
        return {"present": False}
    text = path.read_text(encoding="utf-8", errors="replace")
    shape = {word: len(re.findall(rf"\b{word}\b", text)) for word in SHAPE_WORDS}
    suspicious = {marker: text.count(marker) for marker in SUSPICIOUS if marker in text}
    return {
        "present": True,
        "path": str(path.relative_to(ROOT)),
        "sha256": sha256(path),
        "lines": text.count("\n") + (0 if text.endswith("\n") else 1),
        "shape": shape,
        "suspicious_markers": suspicious,
    }


def renderer_record(
    java: str,
    renderer: str,
    class_file: Path,
    class_name: str,
    destination: Path,
) -> dict[str, object]:
    destination.mkdir(parents=True, exist_ok=True)
    if renderer == "vineflower":
        command = [
            java,
            "-Xmx2g",
            "-jar",
            str(VINEFLOWER),
            "-log=ERROR",
            "-mpm=60",
            str(class_file),
            str(destination),
        ]
    elif renderer == "cfr":
        command = [
            java,
            "-Xmx2g",
            "-jar",
            str(CFR),
            str(class_file),
            "--outputdir",
            str(destination),
            "--silent",
            "true",
        ]
    else:
        raise ValueError(renderer)

    completed = run(command, check=False)
    source = find_source(destination, class_name)
    return {
        "renderer": renderer,
        "returncode": completed.returncode,
        "stdout": completed.stdout[-4000:],
        "stderr": completed.stderr[-4000:],
        "source": source_facts(source),
    }


def presentation_comparison(records: list[dict[str, object]]) -> dict[str, object]:
    by_name = {record["renderer"]: record for record in records}
    left = by_name.get("vineflower", {}).get("source", {})
    right = by_name.get("cfr", {}).get("source", {})
    if not isinstance(left, dict) or not isinstance(right, dict) or not left.get("present") or not right.get("present"):
        return {"comparable": False}
    left_shape = left.get("shape", {})
    right_shape = right.get("shape", {})
    differences = {
        key: [left_shape.get(key, 0), right_shape.get(key, 0)]
        for key in SHAPE_WORDS
        if left_shape.get(key, 0) != right_shape.get(key, 0)
    }
    return {
        "comparable": True,
        "text_identical": left.get("sha256") == right.get("sha256"),
        "presentation_shape_disagreement": differences,
        "note": "Source-shape disagreement is a review trigger, not proof that either decompiler is wrong.",
    }


def render_side(
    java: str,
    side: str,
    jar: Path,
    current_name: str,
    canonical_name: str,
    root: Path,
) -> dict[str, object]:
    raw = root / "classes" / side / current_name
    if raw.exists():
        shutil.rmtree(raw)
    class_file = extract_class_family(jar, current_name, raw)
    records = []
    for renderer in ("vineflower", "cfr"):
        destination = root / side / renderer / current_name
        if destination.exists():
            shutil.rmtree(destination)
        records.append(renderer_record(java, renderer, class_file, current_name, destination))
    return {
        "side": side,
        "jar": str(jar.relative_to(ROOT)),
        "current_name": current_name,
        "canonical_name": canonical_name,
        "class_sha256": sha256(class_file),
        "renderers": records,
        "comparison": presentation_comparison(records),
    }


def parser() -> argparse.ArgumentParser:
    cli = argparse.ArgumentParser(description=__doc__)
    cli.add_argument("classes", nargs="+", help="semantic/stable class names, for example classDF")
    cli.add_argument("--out", type=Path, default=CACHE / "ensemble")
    cli.add_argument("--semantic-only", action="store_true")
    cli.add_argument("--skip-toolchain-check", action="store_true")
    return cli


def main() -> int:
    args = parser().parse_args()
    try:
        for name in args.classes:
            if not CLASS_NAME.fullmatch(name):
                raise ValueError(f"invalid class name: {name}")
        if not args.skip_toolchain_check:
            toolchain_check()
        java = runtime()
        out = safe_dir(args.out)
        out.mkdir(parents=True, exist_ok=True)

        original_index, semantic_index = ensure_indexes()
        original_current, original_by_canonical = read_class_identities(original_index)
        semantic_current, _semantic_by_canonical = read_class_identities(semantic_index)
        jars = input_paths()

        manifest: dict[str, object] = {
            "schema": 1,
            "authority": "original bytecode; decompiler output is presentation evidence only",
            "tools": {
                "vineflower": {"path": str(VINEFLOWER.relative_to(ROOT)), "sha256": sha256(VINEFLOWER)},
                "cfr": {"path": str(CFR.relative_to(ROOT)), "sha256": sha256(CFR)},
            },
            "classes": [],
        }

        class_records: list[dict[str, object]] = []
        for semantic_name in args.classes:
            canonical = semantic_current.get(semantic_name)
            if canonical is None:
                raise RuntimeError(f"semantic class not found in evidence index: {semantic_name}")
            record: dict[str, object] = {
                "requested": semantic_name,
                "canonical_obfuscated_identity": canonical,
                "semantic": render_side(java, "semantic", jars["semantic"], semantic_name, canonical, out),
            }
            if not args.semantic_only:
                original_name = original_by_canonical.get(canonical)
                if original_name is None:
                    record["original"] = {
                        "present": False,
                        "reason": "no original class matched the preserved obfuscated identity",
                    }
                else:
                    record["original"] = render_side(java, "original", jars["original"], original_name, canonical, out)
            class_records.append(record)

        manifest["classes"] = class_records
        manifest_path = out / "manifest.json"
        manifest_path.write_text(json.dumps(manifest, indent=2) + "\n", encoding="utf-8")
        print(f"ensemble: {manifest_path.relative_to(ROOT)}")
        return 0
    except (OSError, ValueError, RuntimeError, subprocess.SubprocessError, KeyError, zipfile.BadZipFile) as error:
        print(f"client-240 decompiler ensemble: {error}", file=sys.stderr)
        return 1


if __name__ == "__main__":
    raise SystemExit(main())
