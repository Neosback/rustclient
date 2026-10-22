//! Stateful desktop-240 GPI list/skip decoding. Only wire tracking lives here;
//! confirmed entity positions remain in `GameState`. Remote presentation is deferred.
use super::{bit_reader::BitReader, player_extended::decode_extended};
use crate::{ProtocolError, Result, wire::Reader};
pub use rustclient_model::{PlayerInfoUpdate, PlayerMovement};

pub struct PlayerInfoDecoder {
    local: usize,
    high: [bool; 2048],
    flags: [u8; 2048],
}
impl PlayerInfoDecoder {
    pub fn new(local: rustclient_model::PlayerIndex) -> Self {
        let local = usize::try_from(local.get()).unwrap_or(0);
        let mut high = [false; 2048];
        if local < high.len() {
            high[local] = true;
        }
        Self {
            local,
            high,
            flags: [0; 2048],
        }
    }
    /// Decode all four independently aligned GPI passes and extended blocks.
    /// # Errors
    /// Rejects truncated/invalid skips, local removal and unsupported masks.
    pub fn decode(&mut self, payload: &[u8]) -> Result<PlayerInfoUpdate> {
        if self.local == 0 || self.local >= 2048 {
            return Err(error("invalid local player index"));
        }
        let mut bits = BitReader::new(payload, "player info");
        let mut update = PlayerInfoUpdate::default();
        let mut extended = Vec::new();
        let old_high = self.high;
        for (high, flag) in [(true, 0), (true, 1), (false, 1), (false, 0)] {
            let mut skips = 0;
            for (index, was_high) in old_high.iter().copied().enumerate().skip(1) {
                if was_high != high || self.flags[index] & 1 != flag {
                    continue;
                }
                if skips > 0 {
                    skips -= 1;
                    self.flags[index] |= 2;
                    continue;
                }
                if bits.read_bits(1)? == 0 {
                    skips = match bits.read_bits(2)? {
                        0 => 0,
                        1 => bits.read_bits(5)?,
                        2 => bits.read_bits(8)?,
                        _ => bits.read_bits(11)?,
                    };
                    self.flags[index] |= 2;
                } else if high {
                    let has_extended = bits.read_bits(1)? == 1;
                    if has_extended {
                        extended.push(index);
                    }
                    let kind = bits.read_bits(2)?;
                    if kind == 0 && !has_extended {
                        if index == self.local {
                            return Err(error("local player removed from high resolution"));
                        }
                        self.high[index] = false;
                        if bits.read_bits(1)? != 0 {
                            self.low(&mut bits, index, &mut extended, 0)?;
                        }
                    } else {
                        let movement = movement(&mut bits, kind)?;
                        if index == self.local {
                            update.local_movement = movement;
                        }
                    }
                } else if self.low(&mut bits, index, &mut extended, 0)? {
                    self.flags[index] |= 2;
                }
            }
            if skips != 0 {
                return Err(error("skip exceeds player list"));
            }
            bits.byte_align();
        }
        for flags in &mut self.flags {
            *flags >>= 1;
        }
        let mut reader = Reader::new(bits.remaining_bytes(), "player extended info");
        for index in extended {
            let values = decode_extended(&mut reader)?;
            if index == self.local {
                update.local_appearance = values.appearance;
                update.local_sequence = values.sequence;
                update.local_facing = values.facing;
            }
        }
        if reader.offset != reader.bytes.len() {
            return Err(error("trailing player-info bytes"));
        }
        Ok(update)
    }
    fn low(
        &mut self,
        bits: &mut BitReader<'_>,
        index: usize,
        extended: &mut Vec<usize>,
        depth: u8,
    ) -> Result<bool> {
        if depth > 8 {
            return Err(error("low-resolution recursion limit"));
        }
        match bits.read_bits(2)? {
            0 => {
                if bits.read_bits(1)? != 0 {
                    self.low(bits, index, extended, depth + 1)?;
                }
                bits.read_bits(13)?;
                bits.read_bits(13)?;
                if bits.read_bits(1)? != 0 {
                    extended.push(index);
                }
                if self.high[index] {
                    return Err(error("duplicate high-resolution promotion"));
                }
                self.high[index] = true;
                Ok(true)
            }
            1 => {
                bits.read_bits(2)?;
                Ok(false)
            }
            2 => {
                bits.read_bits(5)?;
                Ok(false)
            }
            _ => {
                bits.read_bits(18)?;
                Ok(false)
            }
        }
    }
}
fn error(detail: &'static str) -> ProtocolError {
    ProtocolError::new("player info", detail)
}
fn signed(value: i32, width: u8) -> i32 {
    if value & (1 << (width - 1)) == 0 {
        value
    } else {
        value - (1 << width)
    }
}
fn movement(bits: &mut BitReader<'_>, kind: u32) -> Result<Option<PlayerMovement>> {
    Ok(match kind {
        0 => None,
        1 => Some(PlayerMovement::Step {
            direction: bits.read_bits_u8(3)?,
        }),
        2 => {
            const RUN: [(i32, i32); 16] = [
                (-2, -2),
                (-1, -2),
                (0, -2),
                (1, -2),
                (2, -2),
                (-2, -1),
                (2, -1),
                (-2, 0),
                (2, 0),
                (-2, 1),
                (2, 1),
                (-2, 2),
                (-1, 2),
                (0, 2),
                (1, 2),
                (2, 2),
            ];
            let (x, y) = RUN[usize::from(bits.read_bits_u8(4)?)];
            let (a, b) = (x.signum(), y.signum());
            let direction = |x, y| match (x, y) {
                (-1, -1) => 0,
                (0, -1) => 1,
                (1, -1) => 2,
                (-1, 0) => 3,
                (1, 0) => 4,
                (-1, 1) => 5,
                (0, 1) => 6,
                _ => 7,
            };
            Some(PlayerMovement::Run {
                direction1: direction(a, b),
                direction2: direction(x - a, y - b),
            })
        }
        _ => {
            let width = if bits.read_bits(1)? == 0 { 5 } else { 14 };
            let plane_delta = bits.read_bits_u8(2)?;
            let delta_x = signed(bits.read_bits_i32(width.into())?, width);
            let delta_y = signed(bits.read_bits_i32(width.into())?, width);
            Some(PlayerMovement::Teleport {
                delta_x,
                delta_y,
                plane_delta,
            })
        }
    })
}

#[cfg(test)]
mod tests {
    use super::*;
    use rustclient_model::{PlayerIndex, SequenceChange, SequenceId};
    #[test]
    fn gpi_family_preserves_pass_alignment_movement_and_sequence_clearing() {
        // RSProt PlayerInfo.pHighRes/pStationary. Local index 1, 2046 low-res
        // entries: 0|11|11111111101 skips 2045 after the first entry.
        let mut decoder = PlayerInfoDecoder::new(PlayerIndex::new(1));
        let step = decoder.decode(&[0x98, 0x7f, 0xf4]).unwrap();
        assert_eq!(
            step.local_movement,
            Some(PlayerMovement::Step { direction: 4 })
        );
        assert_eq!(
            decoder.decode(&[0, 0x7f, 0xf4]).unwrap().local_movement,
            None
        );
        // After a stationary tick the local index moves to pass two, low-res
        // indices to pass three. Each pass still starts on its own byte.
        let sequence = decoder
            .decode(&[0xc0, 0x7f, 0xf4, 0x10, 0x8a, 0, 0x80])
            .unwrap();
        assert_eq!(
            sequence.local_sequence,
            SequenceChange::Set(Some(SequenceId::new(10)))
        );
        let clear = decoder
            .decode(&[0xc0, 0x7f, 0xf4, 0x10, 0x7f, 0xff, 0x80])
            .unwrap();
        assert_eq!(clear.local_sequence, SequenceChange::Set(None));
        let run = PlayerInfoDecoder::new(PlayerIndex::new(1))
            .decode(&[0xaf, 0x7f, 0xf4])
            .unwrap();
        assert_eq!(
            run.local_movement,
            Some(PlayerMovement::Run {
                direction1: 7,
                direction2: 7
            })
        );
        let teleport = PlayerInfoDecoder::new(PlayerIndex::new(1))
            .decode(&[0xb3, 0xf1, 0, 0x7f, 0xf4])
            .unwrap();
        assert_eq!(
            teleport.local_movement,
            Some(PlayerMovement::Teleport {
                delta_x: -1,
                delta_y: 2,
                plane_delta: 1
            })
        );
        for bytes in [
            &[][..],
            &[0x98],
            &[0x98, 0x7f],
            &[0x7f, 0xfc],
            &[0xc0, 0x7f, 0xf4, 2],
        ] {
            assert!(
                PlayerInfoDecoder::new(PlayerIndex::new(1))
                    .decode(bytes)
                    .is_err()
            );
        }
    }
}
