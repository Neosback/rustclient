//! Bit-level packet reading for player and NPC info streams.

use crate::{ProtocolError, Result};

pub struct BitReader<'a> {
    bytes: &'a [u8],
    bit_offset: usize,
    stage: &'static str,
}

impl<'a> BitReader<'a> {
    #[must_use]
    pub const fn new(bytes: &'a [u8], stage: &'static str) -> Self {
        Self {
            bytes,
            bit_offset: 0,
            stage,
        }
    }

    /// Read up to 32 bits from the current bit position.
    /// # Errors
    /// Returns error if bit stream is truncated or bit count is invalid.
    pub fn read_bits(&mut self, count: usize) -> Result<u32> {
        if count == 0 || count > 32 {
            return Err(ProtocolError::new(self.stage, "invalid bit read width"));
        }
        let total_bits = self.bytes.len() * 8;
        if self.bit_offset + count > total_bits {
            return Err(ProtocolError::new(self.stage, "bit stream truncated"));
        }

        let mut byte_idx = self.bit_offset >> 3;
        let mut bit_in_byte = 8 - (self.bit_offset & 7);
        self.bit_offset += count;

        let mut value = 0u32;
        let mut remaining = count;
        while remaining > 0 {
            let bits_to_read = remaining.min(bit_in_byte);
            let b = u32::from(self.bytes[byte_idx]);
            let shift = bit_in_byte - bits_to_read;
            let mask = ((1 << bits_to_read) - 1) << shift;
            value = (value << bits_to_read) | ((b & mask) >> shift);

            remaining -= bits_to_read;
            byte_idx += 1;
            bit_in_byte = 8;
        }
        Ok(value)
    }

    /// Read up to 8 bits as a byte.
    /// # Errors
    /// Returns error if the stream is truncated or `count` exceeds 8.
    pub fn read_bits_u8(&mut self, count: usize) -> Result<u8> {
        let value = self.read_bits(count)?;
        u8::try_from(value).map_err(|_| ProtocolError::new(self.stage, "bit field exceeds a byte"))
    }

    /// Read up to 31 bits as a non-negative `i32`.
    /// # Errors
    /// Returns error if the stream is truncated or the value does not fit.
    pub fn read_bits_i32(&mut self, count: usize) -> Result<i32> {
        let value = self.read_bits(count)?;
        i32::try_from(value).map_err(|_| ProtocolError::new(self.stage, "bit field exceeds i32"))
    }

    /// Advance to the next whole byte boundary.
    pub fn byte_align(&mut self) {
        self.bit_offset = (self.bit_offset + 7) & !7;
    }

    /// Byte offset within the slice after byte alignment.
    #[must_use]
    pub const fn byte_offset(&self) -> usize {
        (self.bit_offset + 7) >> 3
    }

    /// Remaining bits in the payload.
    #[must_use]
    pub const fn bits_remaining(&self) -> usize {
        (self.bytes.len() * 8).saturating_sub(self.bit_offset)
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn bit_reader_reads_exact_bit_sequences() {
        let bytes = [0b1011_0010, 0b1100_0101];
        let mut r = BitReader::new(&bytes, "test");
        assert_eq!(r.read_bits(1).unwrap(), 1);
        assert_eq!(r.read_bits(2).unwrap(), 0b01);
        assert_eq!(r.read_bits(1).unwrap(), 1);
        assert_eq!(r.read_bits(4).unwrap(), 0b0010);
        assert_eq!(r.read_bits(4).unwrap(), 0b1100);
        assert_eq!(r.read_bits(4).unwrap(), 0b0101);
        assert!(r.read_bits(1).is_err());
    }
}
