//! Integer orientation/angle value types.
//!
//! These are semantic game-space values. Renderer Euler angles, quaternions,
//! matrices, and floating-point conventions stay in renderer-owned code.

use crate::coords::ModelPoint;

/// Number of Jagex angular units in one complete turn.
pub const JAU_FULL_TURN: u16 = 2048;
/// One quarter turn in Jagex angular units.
pub const JAU_QUARTER_TURN: u16 = 512;

/// Ordinary object/location orientation (`0..=3`).
#[derive(Debug, Clone, Copy, PartialEq, Eq, PartialOrd, Ord, Hash)]
pub struct LocOrientation(u8);

impl LocOrientation {
    /// Construct a validated location orientation.
    #[must_use]
    pub const fn new(value: u8) -> Option<Self> {
        if value <= 3 { Some(Self(value)) } else { None }
    }

    /// Return the encoded orientation value.
    #[must_use]
    pub const fn get(self) -> u8 {
        self.0
    }

    /// Convert to an exact quarter turn.
    #[must_use]
    pub const fn quarter_turn(self) -> QuarterTurn {
        match self.0 {
            0 => QuarterTurn::Turn0,
            1 => QuarterTurn::Turn1,
            2 => QuarterTurn::Turn2,
            _ => QuarterTurn::Turn3,
        }
    }
}

/// Dedicated quarter-turn operation.
#[derive(Debug, Clone, Copy, PartialEq, Eq, PartialOrd, Ord, Hash)]
#[repr(u8)]
pub enum QuarterTurn {
    /// No rotation.
    Turn0 = 0,
    /// One quarter turn.
    Turn1 = 1,
    /// One half turn.
    Turn2 = 2,
    /// Three quarter turns.
    Turn3 = 3,
}

impl QuarterTurn {
    /// Construct from `0..=3`.
    #[must_use]
    pub const fn new(value: u8) -> Option<Self> {
        match value {
            0 => Some(Self::Turn0),
            1 => Some(Self::Turn1),
            2 => Some(Self::Turn2),
            3 => Some(Self::Turn3),
            _ => None,
        }
    }

    /// Return the quarter-turn index.
    #[must_use]
    pub const fn index(self) -> u8 {
        self as u8
    }

    /// Rotate an asset-local model point around the model Y axis.
    ///
    /// Wrapping negation intentionally matches Java two's-complement integer
    /// behavior at `i32::MIN`.
    #[must_use]
    pub const fn rotate_model_point(self, point: ModelPoint) -> ModelPoint {
        match self {
            Self::Turn0 => point,
            Self::Turn1 => ModelPoint::new(point.z, point.y, point.x.wrapping_neg()),
            Self::Turn2 => ModelPoint::new(point.x.wrapping_neg(), point.y, point.z.wrapping_neg()),
            Self::Turn3 => ModelPoint::new(point.z.wrapping_neg(), point.y, point.x),
        }
    }
}

/// Wrapped Jagex angular-unit value in `0..=2047`.
#[derive(Debug, Clone, Copy, PartialEq, Eq, PartialOrd, Ord, Hash)]
pub struct JauAngle(u16);

impl JauAngle {
    /// Zero angle.
    pub const ZERO: Self = Self(0);
    /// One quarter turn.
    pub const QUARTER: Self = Self(JAU_QUARTER_TURN);

    /// Construct and wrap into the canonical 2048-unit domain.
    #[must_use]
    pub const fn new_wrapped(value: u16) -> Self {
        Self(value & (JAU_FULL_TURN - 1))
    }

    /// Return the wrapped angular-unit value.
    #[must_use]
    pub const fn get(self) -> u16 {
        self.0
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn location_orientation_has_exact_range() {
        assert_eq!(LocOrientation::new(3).map(LocOrientation::get), Some(3));
        assert_eq!(LocOrientation::new(4), None);
    }

    #[test]
    fn quarter_turns_match_integer_model_rotation() {
        let point = ModelPoint::new(10, 20, 30);

        assert_eq!(
            QuarterTurn::Turn0.rotate_model_point(point),
            ModelPoint::new(10, 20, 30)
        );
        assert_eq!(
            QuarterTurn::Turn1.rotate_model_point(point),
            ModelPoint::new(30, 20, -10)
        );
        assert_eq!(
            QuarterTurn::Turn2.rotate_model_point(point),
            ModelPoint::new(-10, 20, -30)
        );
        assert_eq!(
            QuarterTurn::Turn3.rotate_model_point(point),
            ModelPoint::new(-30, 20, 10)
        );
    }

    #[test]
    fn four_quarter_turns_return_original_point() {
        let mut point = ModelPoint::new(-123, 456, 789);
        for _ in 0..4 {
            point = QuarterTurn::Turn1.rotate_model_point(point);
        }
        assert_eq!(point, ModelPoint::new(-123, 456, 789));
    }

    #[test]
    fn angular_units_wrap_exactly() {
        assert_eq!(JauAngle::new_wrapped(2047).get(), 2047);
        assert_eq!(JauAngle::new_wrapped(2048).get(), 0);
        assert_eq!(JauAngle::new_wrapped(2560).get(), 512);
    }
}
