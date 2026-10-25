"""Arithmetic/identity contracts for the optional cross-analyzer comparison."""
import importlib.util
from pathlib import Path
import unittest

spec = importlib.util.spec_from_file_location('hybrid_review', Path(__file__).with_name('hybrid-review.py'))
review = importlib.util.module_from_spec(spec)
spec.loader.exec_module(review)


def pair(read, write, bits=32, desc='I'):
    return {'bits': bits, 'field': {'owner': {'internalName': 'a'}, 'name': 'x', 'descriptor': desc},
        'readMultiplier': read, 'writeMultiplier': write}


def candidate(value, desc='I', confidence='STATED'):
    return {'field': 'a.x', 'descriptor': desc, 'multiplier': str(value), 'confidence': confidence}


class ComparisonContracts(unittest.TestCase):
    def outcome(self, pairs, value):
        return review.compare({'provenPairs': pairs}, [value])['rows'][0]['outcome']

    def test_signed_int_and_unsigned_candidate_are_same_residue(self):
        self.assertEqual(self.outcome([pair(-1, -1)], candidate(4294967295)), 'agrees-with-pair')

    def test_long_width_is_not_truncated(self):
        self.assertEqual(self.outcome([pair(-1, -1, 64, 'J')], candidate(18446744073709551615, 'J')), 'agrees-with-pair')
        self.assertEqual(self.outcome([pair(-1, -1, 64, 'J')], candidate(4294967295, 'J')), 'different-candidate')

    def test_same_owner_name_with_different_descriptor_does_not_match(self):
        self.assertEqual(self.outcome([pair(1, 1)], candidate(1, 'J')), 'no-workbench-pair')
        self.assertEqual(self.outcome([pair(1, 1)], candidate(1, 'AMBIGUOUS')), 'ambiguous-descriptor')

    def test_multiple_pairs_remain_ambiguous(self):
        self.assertEqual(self.outcome([pair(1, 1), pair(-1, -1)], candidate(1)), 'agrees-with-one-of-multiple-pairs')

    def test_disagreement_does_not_overwrite_stated_confidence(self):
        report = review.compare({'provenPairs': [pair(1, 1)]}, [candidate(-1)])
        self.assertEqual(report['rows'][0]['outcome'], 'different-candidate')
        self.assertEqual(report['rows'][0]['confidence'], 'STATED')

    def test_invalid_inverse_evidence_fails(self):
        with self.assertRaises(ValueError):
            review.compare({'provenPairs': [pair(3, 3)]}, [candidate(3)])


if __name__ == '__main__':
    unittest.main()
