#!/usr/bin/env python3
"""Check the metadata reading view against every original class, including its opaque Code bytes."""
import hashlib
import json
from pathlib import Path
import tempfile
import unittest
import zipfile

from metadata_view import ROOT, build_metadata_view, class_view


class MetadataViewTests(unittest.TestCase):
    def test_all_members_code_and_unrelated_metadata_are_preserved(self):
        source = ROOT / 'references/gamepacks/osrs-240/injected-client-1.12.39.jar'
        original_hash = hashlib.sha256(source.read_bytes()).hexdigest()
        scratch = ROOT / '.cache/client-240/recaf/tests'
        scratch.mkdir(parents=True, exist_ok=True)
        with tempfile.TemporaryDirectory(dir=scratch) as directory:
            target = Path(directory) / 'view.jar'
            report = build_metadata_view(source, target)
            self.assertEqual(len(report['classes']), 735)
            self.assertEqual(report['removed_annotations'], 26302)
            self.assertEqual(report['method_code_attributes_preserved'], 18365)
            with zipfile.ZipFile(source) as original, zipfile.ZipFile(target) as cleaned:
                self.assertEqual(original.namelist(), cleaned.namelist())
                for name in original.namelist():
                    before, after = original.read(name), cleaned.read(name)
                    if name.endswith('.class'):
                        transformed, protected_before, _ = class_view(before)
                        _, protected_after, remaining = class_view(after)
                        self.assertEqual(transformed, after, name)
                        self.assertEqual(protected_before, protected_after, name)
                        self.assertEqual(remaining, [], name)
                    else:
                        self.assertEqual(before, after, name)
            # A corrupt cached artifact is rejected and rebuilt, not silently consumed.
            target.write_bytes(b'changed')
            rebuilt = build_metadata_view(source, target)
            self.assertEqual(rebuilt['output_sha256'], report['output_sha256'])
            self.assertEqual(hashlib.sha256(source.read_bytes()).hexdigest(), original_hash)

    def test_invalid_or_truncated_classfiles_fail_closed(self):
        source = ROOT / 'references/gamepacks/osrs-240/injected-client-1.12.39.jar'
        with zipfile.ZipFile(source) as archive:
            cls = archive.read('aa.class')
        for size in [0, 3, 8, len(cls) // 2, len(cls) - 1]:
            with self.subTest(size=size), self.assertRaises(ValueError):
                class_view(cls[:size])
        with self.assertRaises(ValueError):
            class_view(b'bad!' + cls[4:])


if __name__ == '__main__':
    unittest.main()
