//! Stateful desktop-240 GPI list/skip decoding. Only wire tracking lives here;
//! confirmed visible and out-of-view positions remain in `GameState`.
use super::{bit_reader::BitReader, player_extended::decode_extended};
use crate::{ProtocolError, Result, wire::Reader};
use rustclient_model::{
    MoveSpeed, Plane, PlayerIndex, PlayerTrackingGrid, RemotePlayerUpdate, TileOffset, WorldTile,
};
pub use rustclient_model::{PlayerInfoUpdate, PlayerMovement};

const PLAYER_CAPACITY: usize = 2048;

pub struct PlayerInfoDecoder {
    local: usize,
    high: [bool; PLAYER_CAPACITY],
    flags: [u8; PLAYER_CAPACITY],
    path_speed: [MoveSpeed; PLAYER_CAPACITY],
}
impl PlayerInfoDecoder {
    /// Translate the login GPI region prefix without retaining a position cache.
    pub fn bootstrap(&mut self, payload: &[u8]) -> Result<PlayerInfoUpdate> {
        let mut bits = BitReader::new(payload, "player tracking bootstrap");
        bits.read_bits(30)?;
        let grid =
            PlayerTrackingGrid::new(8192, 256).ok_or_else(|| error("tracking grid dimensions"))?;
        let mut update = PlayerInfoUpdate::default();
        for index in 1..2048_u32 {
            if usize::try_from(index).ok() == Some(self.local) {
                continue;
            }
            let region = bits.read_bits(18)?;
            let plane = Plane::new((region >> 16).to_le_bytes()[0])
                .ok_or_else(|| error("tracking plane"))?;
            let x = i32::try_from((region >> 8) & 255).map_err(|_| error("tracking x"))? * 8192;
            let y = i32::try_from(region & 255).map_err(|_| error("tracking y"))? * 8192;
            update.remote.push(RemotePlayerUpdate::Track {
                index: PlayerIndex::new(index),
                grid,
                region: WorldTile::new(x, y, plane),
            });
        }
        Ok(update)
    }
    pub fn new(local: rustclient_model::PlayerIndex) -> Self {
        let local = usize::try_from(local.get()).unwrap_or(0);
        let mut high = [false; PLAYER_CAPACITY];
        if local < high.len() {
            high[local] = true;
        }
        Self {
            local,
            high,
            flags: [0; PLAYER_CAPACITY],
            path_speed: [MoveSpeed::Walk; PLAYER_CAPACITY],
        }
    }
    /// Decode all four independently aligned GPI passes and extended blocks.
    /// # Errors
    /// Rejects truncated/invalid skips, local removal and unsupported masks.
    pub fn decode(&mut self, payload: &[u8]) -> Result<PlayerInfoUpdate> {
        if self.local == 0 || self.local >= PLAYER_CAPACITY {
            return Err(error("invalid local player index"));
        }
        let mut bits = BitReader::new(payload, "player info");
        let mut update = PlayerInfoUpdate::default();
        let mut extended = Vec::new();
        let mut pending_moves = Vec::new();
        let mut has_extended = [false; PLAYER_CAPACITY];
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
                    if high && index != self.local {
                        update.remote.push(RemotePlayerUpdate::Move {
                            index: player(index)?,
                            movement: None,
                        });
                    }
                    continue;
                }
                if bits.read_bits(1)? == 0 {
                    skips = read_skip_count(&mut bits)?;
                    self.flags[index] |= 2;
                    if high && index != self.local {
                        update.remote.push(RemotePlayerUpdate::Move {
                            index: player(index)?,
                            movement: None,
                        });
                    }
                } else if high {
                    let pending_extended = bits.read_bits(1)? == 1;
                    if pending_extended {
                        extended.push(index);
                        has_extended[index] = true;
                    }
                    let kind = bits.read_bits(2)?;
                    if kind == 0 && !pending_extended {
                        if index == self.local {
                            return Err(error("local player removed from high resolution"));
                        }
                        self.high[index] = false;
                        update.remote.push(RemotePlayerUpdate::Leave {
                            index: player(index)?,
                        });
                        if bits.read_bits(1)? != 0 {
                            self.low(
                                &mut bits,
                                index,
                                &mut extended,
                                &mut has_extended,
                                &mut update.remote,
                                0,
                            )?;
                        }
                    } else {
                        pending_moves.push((index, movement(&mut bits, kind)?));
                    }
                } else if self.low(
                    &mut bits,
                    index,
                    &mut extended,
                    &mut has_extended,
                    &mut update.remote,
                    0,
                )? {
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

        let mut effective_speed = [None; PLAYER_CAPACITY];
        let mut reader = Reader::new(bits.remaining_bytes(), "player extended info");
        for index in extended {
            let values = decode_extended(&mut reader)?;
            let previous_speed = self.path_speed[index];
            if let Some(speed) = values.persistent_speed {
                self.path_speed[index] = speed;
            }
            let speed = values
                .temporary_speed
                .or(values.persistent_speed)
                .unwrap_or(previous_speed);
            effective_speed[index] = Some(speed);

            if index == self.local {
                update.local_appearance = values.appearance;
                update.local_sequence = values.sequence;
                update.local_sequence_delay = values.sequence_delay;
                update.local_facing = values.facing;
                update.local_movement_speed = Some(speed);
                update.local_exact_move = values.exact_move;
            } else {
                update.remote.push(RemotePlayerUpdate::Attributes {
                    index: player(index)?,
                    appearance: values.appearance,
                    sequence: values.sequence,
                    delay: values.sequence_delay,
                    facing: values.facing,
                    movement_speed: Some(speed),
                    exact_move: values.exact_move,
                });
            }
        }
        if reader.offset != reader.bytes.len() {
            return Err(error("trailing player-info bytes"));
        }

        // Revision 240 applies ordinary path movement only after all pending masks are decoded.
        // Exact movement remains separate semantic metadata; the confirmed network coordinate still
        // advances by the movement bits and actor simulation can use `exact_move` to override pathing.
        for (index, movement) in pending_moves {
            let speed = effective_speed[index].unwrap_or(self.path_speed[index]);
            if index == self.local {
                update.local_movement = movement;
                update.local_movement_speed = Some(speed);
            } else {
                update.remote.push(RemotePlayerUpdate::Move {
                    index: player(index)?,
                    movement,
                });
                if !has_extended[index] {
                    update.remote.push(RemotePlayerUpdate::Attributes {
                        index: player(index)?,
                        appearance: None,
                        sequence: rustclient_model::SequenceChange::Unchanged,
                        delay: 0,
                        facing: None,
                        movement_speed: Some(speed),
                        exact_move: None,
                    });
                }
            }
        }
        Ok(update)
    }
    fn low(
        &mut self,
        bits: &mut BitReader<'_>,
        index: usize,
        extended: &mut Vec<usize>,
        has_extended: &mut [bool; PLAYER_CAPACITY],
        remote: &mut Vec<RemotePlayerUpdate>,
        depth: u8,
    ) -> Result<bool> {
        if depth > 8 {
            return Err(error("low-resolution recursion limit"));
        }
        match bits.read_bits(2)? {
            0 => {
                if bits.read_bits(1)? != 0 {
                    self.low(
                        bits,
                        index,
                        extended,
                        has_extended,
                        remote,
                        depth + 1,
                    )?;
                }
                let x = bits.read_bits_i32(13)?;
                let y = bits.read_bits_i32(13)?;
                let pending_extended = bits.read_bits(1)? != 0;
                if pending_extended {
                    extended.push(index);
                    has_extended[index] = true;
                }
                if self.high[index] {
                    return Err(error("duplicate high-resolution promotion"));
                }
                self.high[index] = true;
                remote.push(RemotePlayerUpdate::Enter {
                    index: player(index)?,
                    offset: TileOffset { x, y },
                });
                if !pending_extended {
                    remote.push(RemotePlayerUpdate::Attributes {
                        index: player(index)?,
                        appearance: None,
                        sequence: rustclient_model::SequenceChange::Unchanged,
                        delay: 0,
                        facing: None,
                        movement_speed: Some(self.path_speed[index]),
                        exact_move: None,
                    });
                }
                Ok(true)
            }
            1 => {
                remote.push(RemotePlayerUpdate::Shift {
                    index: player(index)?,
                    regions: TileOffset { x: 0, y: 0 },
                    plane_delta: bits.read_bits_u8(2)?,
                });
                Ok(false)
            }
            2 => {
                let value = bits.read_bits_u8(5)?;
                let (x, y) = match value & 7 {
                    0 => (-1, -1),
                    1 => (0, -1),
                    2 => (1, -1),
                    3 => (-1, 0),
                    4 => (1, 0),
                    5 => (-1, 1),
                    6 => (0, 1),
                    _ => (1, 1),
                };
                remote.push(RemotePlayerUpdate::Shift {
                    index: player(index)?,
                    regions: TileOffset { x, y },
                    plane_delta: value >> 3,
                });
                Ok(false)
            }
            _ => {
                let value = bits.read_bits(18)?;
                remote.push(RemotePlayerUpdate::Shift {
                    index: player(index)?,
                    regions: TileOffset {
                        x: i32::try_from((value >> 8) & 255)
                            .map_err(|_| error("tracking x delta"))?,
                        y: i32::try_from(value & 255).map_err(|_| error("tracking y delta"))?,
                    },
                    plane_delta: (value >> 16).to_le_bytes()[0],
                });
                Ok(false)
            }
        }
    }
}
fn player(index: usize) -> Result<PlayerIndex> {
    Ok(PlayerIndex::new(
        u32::try_from(index).map_err(|_| error("player index overflow"))?,
    ))
}
fn error(detail: &'static str) -> ProtocolError {
    ProtocolError::new("player info", detail)
}
fn read_skip_count(bits: &mut BitReader<'_>) -> Result<usize> {
    usize::try_from(match bits.read_bits(2)? {
        0 => 0,
        1 => bits.read_bits(5)?,
        2 => bits.read_bits(8)?,
        _ => bits.read_bits(11)?,
    })
    .map_err(|_| error("skip count overflow"))
}
fn signed_five(value: u32) -> i32 {
    let value = i32::try_from(value & 31).unwrap_or_default();
    if value > 15 { value - 32 } else { value }
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
        _ if bits.read_bits(1)? == 0 => {
            let packed = bits.read_bits(12)?;
            Some(PlayerMovement::Teleport {
                plane_delta: ((packed >> 10) & 3).to_le_bytes()[0],
                delta_x: signed_five((packed >> 5) & 31),
                delta_y: signed_five(packed & 31),
            })
        }
        _ => {
            let packed = bits.read_bits(30)?;
            Some(PlayerMovement::Teleport {
                plane_delta: ((packed >> 28) & 3).to_le_bytes()[0],
                delta_x: i32::try_from((packed >> 14) & 16_383)
                    .map_err(|_| error("teleport x delta"))?,
                delta_y: i32::try_from(packed & 16_383)
                    .map_err(|_| error("teleport y delta"))?,
            })
        }
    })
}

#[cfg(test)]
mod tests {
    use super::*;
    use rustclient_model::{ExactMove, MoveSpeed, PlayerIndex, SequenceChange, SequenceId};
    #[test]
    fn remote_gpi_promotion_attributes_movement_and_removal_survive_all_four_passes() {
        let mut decoder = PlayerInfoDecoder::new(PlayerIndex::new(1));
        let tracking = decoder.bootstrap(&vec![0; 4608]).unwrap();
        assert_eq!(tracking.remote.len(), 2046);
        let promotion = decoder
            .decode(&[
                0x00, 0x86, 0x40, 0x32, 0x06, 0xff, 0xe0, 0x10, 0x0a, 0x00, 0x83,
            ])
            .unwrap();
        assert_eq!(
            promotion.remote,
            [
                RemotePlayerUpdate::Enter {
                    index: PlayerIndex::new(2),
                    offset: TileOffset { x: 3200, y: 3201 }
                },
                RemotePlayerUpdate::Attributes {
                    index: PlayerIndex::new(2),
                    appearance: None,
                    sequence: SequenceChange::Set(Some(SequenceId::new(10))),
                    delay: 3,
                    facing: None,
                    movement_speed: Some(MoveSpeed::Walk),
                    exact_move: None,
                },
            ]
        );
        assert_eq!(
            decoder.decode(&[0x13, 0x00, 0x7f, 0xf0]).unwrap().remote,
            [
                RemotePlayerUpdate::Move {
                    index: PlayerIndex::new(2),
                    movement: Some(PlayerMovement::Step { direction: 4 })
                },
                RemotePlayerUpdate::Attributes {
                    index: PlayerIndex::new(2),
                    appearance: None,
                    sequence: SequenceChange::Unchanged,
                    delay: 0,
                    facing: None,
                    movement_speed: Some(MoveSpeed::Walk),
                    exact_move: None,
                },
            ]
        );
        assert_eq!(
            decoder.decode(&[0x80, 0x00, 0x7f, 0xf0]).unwrap().remote,
            [RemotePlayerUpdate::Leave {
                index: PlayerIndex::new(2)
            }]
        );
    }
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
        assert_eq!(step.local_movement_speed, Some(MoveSpeed::Walk));
        assert_eq!(
            decoder.decode(&[0, 0x7f, 0xf4]).unwrap().local_movement,
            None
        );
        // After a stationary tick the local index moves to pass two, low-res
        // indices to pass three. Each pass still starts on its own byte.
        let sequence = decoder
            .decode(&[0xc0, 0x7f, 0xf4, 0x10, 0x0a, 0, 0x80])
            .unwrap();
        assert_eq!(
            sequence.local_sequence,
            SequenceChange::Set(Some(SequenceId::new(10)))
        );
        let clear = decoder
            .decode(&[0xc0, 0x7f, 0xf4, 0x10, 0xff, 0xff, 0x80])
            .unwrap();
        assert_eq!(clear.local_sequence, SequenceChange::Set(None));
        let delayed = decoder
            .decode(&[0xc0, 0x7f, 0xf4, 0x10, 0x0a, 0, 0x83])
            .unwrap();
        assert_eq!(delayed.local_sequence_delay, 3);
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

    #[test]
    fn exact_move_and_speed_metadata_survive_pending_mask_decode() {
        let update = PlayerInfoDecoder::new(PlayerIndex::new(1))
            .decode(&[
                0xd8, 0x7f, 0xf4, // local step with pending extended block
                0x01, 0x02, // mask extension + exact move 0x200
                129, 0, 2, 129, 0, 133, 0, 10, 128, 4,
            ])
            .unwrap();
        assert_eq!(update.local_movement, Some(PlayerMovement::Step { direction: 4 }));
        assert_eq!(update.local_movement_speed, Some(MoveSpeed::Walk));
        assert_eq!(
            update.local_exact_move,
            Some(ExactMove {
                start_delta_x: -1,
                start_delta_y: 0,
                end_delta_x: 2,
                end_delta_y: 1,
                start_delay: 5,
                end_delay: 10,
                direction: 1024,
            })
        );
    }
}
