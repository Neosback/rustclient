#!/usr/bin/env python3
"""Check that source-quality diagnostics do not silently stop at javac's default 100 errors."""
import json
import os
from pathlib import Path
import subprocess
import tempfile
import unittest

from recaf import ROOT, RECAF_JAR, CLASSES, check_build, java_tools


class SourceMetricsTests(unittest.TestCase):
    def test_reports_more_than_one_hundred_bad_files_and_keeps_nested_methods_separate(self):
        check_build()
        java, _ = java_tools()
        scratch = ROOT / '.cache/client-240/recaf/tests'
        scratch.mkdir(parents=True, exist_ok=True)
        with tempfile.TemporaryDirectory(dir=scratch) as directory:
            root = Path(directory)
            sources = root / 'sources'
            sources.mkdir()
            for number in range(110):
                (sources / f'Bad{number}.java').write_text(f'class Bad{number} {{ void if() {{}} }}')
            (sources / 'Good.java').write_text('class Good { int field; static { int local = 1; } void outer(int arg) { int local = 2; } class Nested { int nested; void inner() {} } }')
            destination = root / 'metrics.json'
            subprocess.run([java, '-cp', os.pathsep.join([str(RECAF_JAR), str(CLASSES)]),
                'dev.neosback.recaf.SourceMetrics', str(sources), str(destination)], check=True,
                stdout=subprocess.DEVNULL, stderr=subprocess.DEVNULL)
            metrics = json.loads(destination.read_text())
            self.assertEqual(sum(bool(record['syntaxErrors']) for record in metrics.values()), 110)
            self.assertEqual(metrics['Good.java']['methodNames'], {'outer': 1})
            self.assertEqual(metrics['Good.java']['fieldNames'], {'field': 1})
            self.assertEqual(metrics['Good.java']['syntaxErrors'], [])


if __name__ == '__main__':
    unittest.main()
