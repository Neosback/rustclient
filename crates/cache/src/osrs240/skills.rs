//! Revision-240 classKJ experience thresholds and real-level semantics.
use crate::CacheProfile;
const EXPERIENCE: [u32; 99] = [
    83, 174, 276, 388, 512, 650, 801, 969, 1154, 1358, 1584, 1833, 2107, 2411, 2746, 3115, 3523,
    3973, 4470, 5018, 5624, 6291, 7028, 7842, 8740, 9730, 10_824, 12_031, 13_363, 14_833, 16_456,
    18_247, 20_224, 22_406, 24_815, 27_473, 30_408, 33_648, 37_224, 41_171, 45_529, 50_339, 55_649,
    61_512, 67_983, 75_127, 83_014, 91_721, 101_333, 111_945, 123_660, 136_594, 150_872, 166_636,
    184_040, 203_254, 224_466, 247_886, 273_742, 302_288, 333_804, 368_599, 407_015, 449_428,
    496_254, 547_953, 605_032, 668_051, 737_627, 814_445, 899_257, 992_895, 1_096_278, 1_210_421,
    1_336_443, 1_475_581, 1_629_200, 1_798_808, 1_986_068, 2_192_818, 2_421_087, 2_673_114,
    2_951_373, 3_258_594, 3_597_792, 3_972_294, 4_385_776, 4_842_295, 5_346_332, 5_902_831,
    6_517_253, 7_195_629, 7_944_614, 8_771_558, 9_684_577, 10_692_629, 11_805_606, 13_034_431,
    14_391_160,
];
impl CacheProfile {
    /// Level from transmitted XP, capped at the source's real level 99.
    #[must_use]
    pub fn real_skill_level(self, experience: u32) -> u8 {
        match self {
            Self::Osrs240 | Self::OpenRune240 => u8::try_from(
                EXPERIENCE[..98].partition_point(|threshold| experience >= *threshold) + 1,
            )
            .unwrap_or(99),
        }
    }
}
pub(super) fn cs1_threshold(level: i32) -> Option<i32> {
    usize::try_from(level.checked_sub(1)?)
        .ok()
        .and_then(|n| EXPERIENCE.get(n))
        .and_then(|n| i32::try_from(*n).ok())
}
#[cfg(test)]
mod tests {
    use super::*;
    #[test]
    fn source_thresholds_and_real_level_cap_are_shared() {
        let profile = CacheProfile::Osrs240;
        for (xp, level) in [
            (0, 1),
            (82, 1),
            (83, 2),
            (174, 3),
            (1154, 10),
            (13_034_430, 98),
            (13_034_431, 99),
            (200_000_000, 99),
            (u32::MAX, 99),
        ] {
            assert_eq!(profile.real_skill_level(xp), level);
        }
        assert_eq!(cs1_threshold(1), Some(83));
        assert_eq!(cs1_threshold(99), Some(14_391_160));
        assert_eq!(cs1_threshold(0), None);
        assert_eq!(cs1_threshold(100), None);
    }
}
