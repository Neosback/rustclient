//! Deterministic runtime randomness contracts.
//!
//! This module intentionally separates repeatable pseudo-random generation from
//! platform entropy. Callers provide an explicit seed; implementations must not
//! consult wall time, operating-system entropy, browser randomness, or hidden
//! global state while producing samples.

/// Explicit seed used to initialize a deterministic random source.
#[derive(Debug, Clone, Copy, PartialEq, Eq, PartialOrd, Ord, Hash)]
pub struct RandomSeed(u64);

impl RandomSeed {
    /// Creates a seed from its exact 64-bit value.
    #[must_use]
    pub const fn new(value: u64) -> Self {
        Self(value)
    }

    /// Returns the exact seed value.
    #[must_use]
    pub const fn get(self) -> u64 {
        self.0
    }
}

/// One raw deterministic random sample.
///
/// The sample intentionally has no range or probability-distribution semantics.
/// A future semantic consumer owns the deterministic mapping from raw samples to
/// its domain-specific choice.
#[derive(Debug, Clone, Copy, PartialEq, Eq, PartialOrd, Ord, Hash)]
pub struct RandomSample(u64);

impl RandomSample {
    /// Creates a sample from an exact recorded value.
    ///
    /// This supports fixtures and replay-oriented inputs without requiring a
    /// pseudo-random generator to be rerun.
    #[must_use]
    pub const fn new(value: u64) -> Self {
        Self(value)
    }

    /// Returns the exact raw sample value.
    #[must_use]
    pub const fn get(self) -> u64 {
        self.0
    }
}

/// Injectable source of deterministic random samples.
///
/// For equivalent initial source state and identical call order, implementations
/// must produce the same sample sequence. Implementations must not read hidden
/// nondeterministic inputs while generating samples.
pub trait DeterministicRandomSource {
    /// Produces the next deterministic sample in this source's sequence.
    #[must_use]
    fn next_sample(&mut self) -> RandomSample;
}

/// SplitMix64 deterministic random source initialized from an explicit seed.
///
/// SplitMix64 is used here because it is compact, allocation-free, and defined
/// entirely in terms of fixed-width integer operations. The exact output vectors
/// are regression-tested. This source is runtime infrastructure, not
/// authoritative semantic game state.
#[derive(Debug)]
pub struct SplitMix64Random {
    state: u64,
}

impl SplitMix64Random {
    /// Creates a new source at the beginning of the sequence for `seed`.
    #[must_use]
    pub const fn new(seed: RandomSeed) -> Self {
        Self { state: seed.get() }
    }
}

impl DeterministicRandomSource for SplitMix64Random {
    fn next_sample(&mut self) -> RandomSample {
        const GAMMA: u64 = 0x9E37_79B9_7F4A_7C15;
        const MIX_1: u64 = 0xBF58_476D_1CE4_E5B9;
        const MIX_2: u64 = 0x94D0_49BB_1331_11EB;

        // Wrapping arithmetic is part of the SplitMix64 algorithm.
        self.state = self.state.wrapping_add(GAMMA);

        let mut value = self.state;
        value = (value ^ (value >> 30)).wrapping_mul(MIX_1);
        value = (value ^ (value >> 27)).wrapping_mul(MIX_2);
        value ^= value >> 31;

        RandomSample::new(value)
    }
}

#[cfg(test)]
mod tests {
    use super::{DeterministicRandomSource, RandomSample, RandomSeed, SplitMix64Random};

    #[test]
    fn seed_and_sample_values_are_explicit_and_lossless() {
        let seed = RandomSeed::new(u64::MAX);
        let sample = RandomSample::new(0x0123_4567_89AB_CDEF);

        assert_eq!(seed.get(), u64::MAX);
        assert_eq!(sample.get(), 0x0123_4567_89AB_CDEF);
    }

    #[test]
    fn identical_seed_and_call_order_produce_identical_sequences() {
        let seed = RandomSeed::new(0xDEAD_BEEF_CAFE_BABE);
        let mut left = SplitMix64Random::new(seed);
        let mut right = SplitMix64Random::new(seed);

        for _ in 0..64 {
            assert_eq!(left.next_sample(), right.next_sample());
        }
    }

    #[test]
    fn splitmix64_zero_seed_matches_canonical_regression_vector() {
        const EXPECTED: [u64; 5] = [
            0xE220_A839_7B1D_CDAF,
            0x6E78_9E6A_A1B9_65F4,
            0x06C4_5D18_8009_454F,
            0xF88B_B8A8_724C_81EC,
            0x1B39_896A_51A8_749B,
        ];

        let mut source = SplitMix64Random::new(RandomSeed::new(0));

        for expected in EXPECTED {
            assert_eq!(source.next_sample().get(), expected);
        }
    }
}
