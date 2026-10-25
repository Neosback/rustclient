#!/usr/bin/env python3
"""Offline tests for enigma-promote.py using a synthetic draft and semantic-jar dump."""
import importlib.util
from pathlib import Path
import tempfile

HERE = Path(__file__).resolve().parent
spec = importlib.util.spec_from_file_location("promote", HERE / "enigma-promote.py")
promote = importlib.util.module_from_spec(spec)
spec.loader.exec_module(promote)

DUMP = "\n".join([
    "C\tclassAA\tjava/lang/Object\t\t33\taa",
    "F\tclassAA\tfield1\tI\t0\tae",
    "F\tclassAA\tfield2\tLclassAB;\t0\taf",
    "M\tclassAA\tmethod9\t(LclassAB;)I\t0\tag\t4\t0",
    "M\tclassAA\tmethod10\t(I)I\t0\tag\t4\t0",
    "M\tclassAA\tgetName\t()I\t1\t-\t4\t0",
    "C\tclassAB\tjava/lang/Object\t\t33\tab",
]) + "\n"


def draft(lines):
    return "tiny\t2\t0\tintermediary\tnamed\n" + "\n".join(lines) + "\n"


def collect(draft_text, shards=()):
    with tempfile.TemporaryDirectory() as temporary:
        base = Path(temporary)
        session = base / "session"
        session.mkdir()
        (session / "draft.tiny").write_text(draft_text, encoding="utf-8")
        docs = base / "docs"
        (docs / "mappings" / "clusters").mkdir(parents=True)
        for name, text in shards:
            (docs / "mappings" / "clusters" / name).write_text(text, encoding="utf-8")
        dump = base / "dump.tsv"
        dump.write_text(DUMP, encoding="utf-8")
        return promote.collect(session, dump, docs, "99-test")


def main():
    ok, problems = collect(draft([
        "c\taa\tclassAA", "\tf\tI\tae\tcounter", "\tf\tLab;\taf\towner",
        "\tm\t(Lab;)I\tag\tresolve", "\tm\t(I)I\tag\tscale",
        "c\tab\tTarget", "\tm\t()V\tif\tbytecode_if",
    ]))
    assert problems == [], problems
    got = {(kind, cls, name, readable) for kind, cls, name, _, _, readable in ok}
    assert got == {
        ("field", "classAA", "field1", "counter"), ("field", "classAA", "field2", "owner"),
        ("method", "classAA", "method9", "resolve"), ("method", "classAA", "method10", "scale"),
        ("class", "classAB", None, "Target"),
    }, got
    desc = {(name, d) for _, _, name, _, d, _ in ok if name == "field2"}
    assert desc == {("field2", "LclassAB;")}, desc  # descriptors are rewritten to semantic names

    _, problems = collect(draft(["c\taa\tclassAA", "\tf\tI\tzz\tmissing"]))
    assert any("0 semantic matches" in p for p in problems), problems
    _, problems = collect(draft(["c\taa\tclassAA", "\tm\t()I\tgetName\tnamed"]))
    assert any("0 semantic matches" in p or "not renamable" in p for p in problems), problems
    _, problems = collect(draft(["c\tzz\tclassZZ"]))
    assert any("not in the semantic jar" in p for p in problems), problems
    _, problems = collect(draft(["c\taa\tclassAA", "\tf\tI\tae\tclass"]))
    assert any("legal Java identifier" in p for p in problems), problems
    _, problems = collect(draft(["c\taa\tclassAA", "\tf\tI\tae\tcounter", "\tf\tLab;\taf\tcounter"]))
    assert any("duplicate readable name" in p for p in problems), problems
    taken = "tiny\t2\t0\tsemantic\treadable\nc\tclassAA\tclassAA\n\tf\tI\tfield1\tother\n"
    _, problems = collect(draft(["c\taa\tclassAA", "\tf\tI\tae\tcounter"]), [("01-x.tiny", taken)])
    assert any("already mapped" in p for p in problems), problems
    _, problems = collect(draft(["c\taa\tShared"]), [("01-x.tiny", "tiny\t2\t0\tsemantic\treadable\nc\tclassAB\tShared\n")])
    assert any("already used" in p for p in problems), problems
    print("test-promote: OK")


if __name__ == "__main__":
    main()
