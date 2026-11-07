//! A cache-resolved bit field in one authoritative integer variable.
use crate::VarpId;

#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct VarpBitRange {
    varp: VarpId,
    low: u8,
    high: u8,
}
impl VarpBitRange {
    /// Inclusive bit positions; invalid ranges cannot enter semantic operations.
    #[must_use]
    pub const fn new(varp: VarpId, low: u8, high: u8) -> Option<Self> {
        if low > high || high >= 32 {
            None
        } else {
            Some(Self { varp, low, high })
        }
    }
    #[must_use]
    pub const fn varp(self) -> VarpId {
        self.varp
    }
    const fn mask(self) -> u32 {
        u32::MAX >> (31 - (self.high - self.low))
    }
    #[must_use]
    pub const fn extract(self, value: i32) -> i32 {
        ((value.cast_unsigned() >> self.low) & self.mask()).cast_signed()
    }
    /// Authored out-of-range/negative values clear the field, preserving all other bits.
    #[must_use]
    pub const fn replace(self, previous: i32, value: i32) -> i32 {
        let mask = self.mask();
        let value = if value < 0 || value.cast_unsigned() > mask {
            0
        } else {
            value.cast_unsigned()
        };
        ((previous.cast_unsigned() & !(mask << self.low)) | (value << self.low)).cast_signed()
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    #[test]
    fn checked_ranges_preserve_neighbors_and_cover_full_width_without_shift_overflow() {
        let id = VarpId::new(42);
        assert!(VarpBitRange::new(id, 4, 3).is_none());
        assert!(VarpBitRange::new(id, 0, 32).is_none());
        let field = VarpBitRange::new(id, 4, 7).unwrap();
        assert_eq!(field.replace(0x1234, 9), 0x1294);
        assert_eq!(field.extract(0x1294), 9);
        assert_eq!(field.replace(0x1234, 16), 0x1204);
        assert_eq!(field.replace(0x1234, -1), 0x1204);
        let high = VarpBitRange::new(id, 31, 31).unwrap();
        assert_eq!(high.replace(7, 1), i32::MIN + 7);
        assert_eq!(high.extract(i32::MIN), 1);
        let full = VarpBitRange::new(id, 0, 31).unwrap();
        assert_eq!(full.replace(-1, i32::MAX), i32::MAX);
        assert_eq!(full.extract(-1), -1);
    }
}
