//! Stateful decoder for desktop revision-240 `PLAYER_INFO` packet (opcode 91).
//!
//! Revision 240 keeps a 2048-slot global-player-info table across packets. Active and
//! low-resolution player indices are decoded in four bitstream passes with variable skip counts;
//! only after those passes are complete do byte-aligned pending update masks begin.

use crate::{ProtocolError, Result, openrune_240::bit_reader::BitReader, wire::Reader};
use rustclient_model::{MoveSpeed, PlayerIndex, PlayerUpdate};
pub use rustclient_model::{
    ExactMove, Gender, ItemDefinitionId, KitId, MovementSequences, PlayerAppearance,
    PlayerInfoUpdate, PlayerMovement, SequenceId, SlotContent, WorldTile,
};

const PLAYER_CAPACITY: usize = 2048;
const INITIAL_GPI_BITS: usize = 30 + 2046 * 18;
pub(crate) const INITIAL_GPI_BYTES: usize = INITIAL_GPI_BITS.div_ceil(8);

#[derive(Debug, Clone, Copy, Default)]
struct PlayerCoord {
    plane: u8,
    x: i32,
    y: i32,
}

impl PlayerCoord {
    fn from_packed(packed: u32) -> Self {
        Self {
            plane: ((packed >> 28) & 3).to_le_bytes()[0],
            x: i32::try_from((packed >> 14) & 16_383).unwrap_or_default(),
            y: i32::try_from(packed & 16_383).unwrap_or_default(),
        }
    }

    fn region(self) -> u32 {
        pack_region(
            self.plane,
            u32::try_from(self.x >> 13).unwrap_or_default(),
            u32::try_from(self.y >> 13).unwrap_or_default(),
        )
    }
}

#[derive(Debug, Clone)]
struct PlayerSlot {
    active: bool,
    flags: u8,
    region: u32,
    coord: PlayerCoord,
    path_speed: i8,
    pending_movement: Option<PlayerMovement>,
    pending_speed: i8,
    appearance: Option<PlayerAppearance>,
}

impl Default for PlayerSlot {
    fn default() -> Self {
        Self {
            active: false,
            flags: 0,
            region: 0,
            coord: PlayerCoord::default(),
            path_speed: 1,
            pending_movement: None,
            pending_speed: 1,
            appearance: None,
        }
    }
}

/// Persistent revision-240 global-player-info decoder.
///
/// The decoder owns wire-only player-index bookkeeping. Authoritative gameplay state remains in
/// `GameState`; this type exists so packet decoding can mirror the client protocol exactly.
pub(crate) struct PlayerInfoDecoder {
    local_player: PlayerIndex,
    slots: Vec<PlayerSlot>,
    active_indices: Vec<usize>,
    empty_indices: Vec<usize>,
    pending_update_indices: Vec<usize>,
    initialized: bool,
}

impl PlayerInfoDecoder {
    #[must_use]
    pub(crate) fn new(local_player: PlayerIndex) -> Self {
        Self {
            local_player,
            slots: (0..PLAYER_CAPACITY)
                .map(|_| PlayerSlot::default())
                .collect(),
            active_indices: Vec::with_capacity(PLAYER_CAPACITY - 1),
            empty_indices: Vec::with_capacity(PLAYER_CAPACITY - 1),
            pending_update_indices: Vec::with_capacity(PLAYER_CAPACITY - 1),
            initialized: false,
        }
    }

    /// Initialize the persistent 2048-slot GPI table from the prefix of the login rebuild.
    /// # Errors
    /// Rejects an invalid local-player index, truncated GPI prefix, or non-zero alignment padding.
    pub(crate) fn initialize_from_rebuild(&mut self, payload: &[u8]) -> Result<WorldTile> {
        if payload.len() < INITIAL_GPI_BYTES {
            return Err(ProtocolError::new(
                "initial player info",
                "truncated GPI prefix",
            ));
        }
        let local = self.local_index()?;
        if !(1..PLAYER_CAPACITY).contains(&local) {
            return Err(ProtocolError::new(
                "initial player info",
                "local player index outside 1..2047",
            ));
        }

        let prefix = &payload[..INITIAL_GPI_BYTES];
        let mut bits = BitReader::new(prefix, "initial player info");
        let packed = bits.read_bits(30)?;
        let local_coord = PlayerCoord::from_packed(packed);

        self.active_indices.clear();
        self.empty_indices.clear();
        self.pending_update_indices.clear();
        for slot in &mut self.slots {
            *slot = PlayerSlot::default();
        }

        self.slots[local].active = true;
        self.slots[local].coord = local_coord;
        self.slots[local].region = local_coord.region();
        self.active_indices.push(local);

        for index in 1..PLAYER_CAPACITY {
            if index == local {
                continue;
            }
            let packed_region = bits.read_bits(18)?;
            let plane = ((packed_region >> 16) & 3).to_le_bytes()[0];
            let region_x = (packed_region >> 8) & 255;
            let region_y = packed_region & 255;
            self.slots[index].region = pack_region(plane, region_x, region_y);
            self.empty_indices.push(index);
        }

        if bits.bits_remaining() != 6 || prefix[INITIAL_GPI_BYTES - 1] & 0x3f != 0 {
            return Err(ProtocolError::new(
                "initial player info",
                "invalid GPI alignment padding",
            ));
        }
        self.initialized = true;
        coord_tile(local_coord, "initial player info")
    }

    /// Decode one revision-240 `PLAYER_INFO` payload using persistent GPI state.
    /// # Errors
    /// Rejects malformed bit passes, impossible player transitions, invalid masks, or trailing data.
    pub(crate) fn decode(&mut self, payload: &[u8]) -> Result<PlayerInfoUpdate> {
        if !self.initialized {
            return Err(ProtocolError::new(
                "player info",
                "GPI state was not initialized by the login rebuild",
            ));
        }

        self.pending_update_indices.clear();
        let mut bits = BitReader::new(payload, "player info bitstream");
        let mut output = PlayerInfoUpdate::default();

        let active = self.active_indices.clone();
        self.decode_active_pass(&mut bits, &active, false, &mut output)?;
        self.decode_active_pass(&mut bits, &active, true, &mut output)?;

        let empty = self.empty_indices.clone();
        self.decode_empty_pass(&mut bits, &empty, true, &mut output)?;
        self.decode_empty_pass(&mut bits, &empty, false, &mut output)?;

        self.rebuild_index_tables();
        bits.byte_align();
        let mask_offset = bits.byte_offset();
        if mask_offset > payload.len() {
            return Err(ProtocolError::new(
                "player info",
                "pending update offset exceeds packet",
            ));
        }

        let mut reader = Reader::new(&payload[mask_offset..], "player info masks");
        let pending = self.pending_update_indices.clone();
        for index in pending {
            let mask = read_update_mask(&mut reader)?;
            self.decode_update_mask(&mut reader, index, mask, &mut output)?;
        }
        if reader.offset != reader.bytes.len() {
            return Err(ProtocolError::new(
                "player info",
                format!(
                    "packet length mismatch: consumed {}, expected {}",
                    mask_offset + reader.offset,
                    payload.len()
                ),
            ));
        }

        self.finalize_movements(&mut output)?;
        Ok(output)
    }

    fn decode_active_pass(
        &mut self,
        bits: &mut BitReader<'_>,
        indices: &[usize],
        flagged: bool,
        output: &mut PlayerInfoUpdate,
    ) -> Result<()> {
        let mut skip = 0usize;
        for &index in indices {
            if (self.slots[index].flags & 1 != 0) != flagged {
                continue;
            }
            if skip > 0 {
                skip -= 1;
                self.slots[index].flags |= 2;
                continue;
            }
            if bits.read_bits(1)? == 0 {
                skip = read_skip_count(bits)?;
                self.slots[index].flags |= 2;
            } else {
                self.read_active_player_update(bits, index, output)?;
            }
        }
        ensure_skip_exhausted(skip, "active-player")?;
        bits.byte_align();
        Ok(())
    }

    fn decode_empty_pass(
        &mut self,
        bits: &mut BitReader<'_>,
        indices: &[usize],
        flagged: bool,
        output: &mut PlayerInfoUpdate,
    ) -> Result<()> {
        let mut skip = 0usize;
        for &index in indices {
            if (self.slots[index].flags & 1 != 0) != flagged {
                continue;
            }
            if skip > 0 {
                skip -= 1;
                self.slots[index].flags |= 2;
                continue;
            }
            if bits.read_bits(1)? == 0 {
                skip = read_skip_count(bits)?;
                self.slots[index].flags |= 2;
            } else if self.read_external_player_update(bits, index, output)? {
                self.slots[index].flags |= 2;
            }
        }
        ensure_skip_exhausted(skip, "external-player")?;
        bits.byte_align();
        Ok(())
    }

    fn read_active_player_update(
        &mut self,
        bits: &mut BitReader<'_>,
        index: usize,
        output: &mut PlayerInfoUpdate,
    ) -> Result<()> {
        let has_mask = bits.read_bits(1)? == 1;
        if has_mask {
            self.pending_update_indices.push(index);
        }

        match bits.read_bits(2)? {
            0 => self.read_remove_or_mask_only(bits, index, has_mask, output)?,
            1 => {
                let direction = bits.read_bits_u8(3)?;
                self.apply_move(index, PlayerMovement::Step { direction });
            }
            2 => {
                let direction = bits.read_bits_u8(4)?;
                let (delta_x, delta_y) = run_delta(direction);
                self.apply_move(index, PlayerMovement::Run { delta_x, delta_y });
            }
            3 => self.read_teleport(bits, index)?,
            _ => unreachable!(),
        }
        Ok(())
    }

    fn read_remove_or_mask_only(
        &mut self,
        bits: &mut BitReader<'_>,
        index: usize,
        has_mask: bool,
        output: &mut PlayerInfoUpdate,
    ) -> Result<()> {
        if has_mask {
            self.slots[index].pending_movement = None;
            return Ok(());
        }
        if index == self.local_index()? {
            return Err(ProtocolError::new(
                "player info bitstream",
                "local player cannot leave high resolution",
            ));
        }

        self.slots[index].region = self.slots[index].coord.region();
        self.slots[index].active = false;
        self.slots[index].pending_movement = None;
        output.removals.push(model_index(index)?);
        if bits.read_bits(1)? != 0 {
            let _ = self.read_external_player_update(bits, index, output)?;
        }
        Ok(())
    }

    fn read_teleport(&mut self, bits: &mut BitReader<'_>, index: usize) -> Result<()> {
        if bits.read_bits(1)? == 0 {
            let packed = bits.read_bits(12)?;
            let plane_delta = ((packed >> 10) & 3).to_le_bytes()[0];
            let mut delta_x = i32::try_from((packed >> 5) & 31).unwrap_or_default();
            let mut delta_y = i32::try_from(packed & 31).unwrap_or_default();
            if delta_x > 15 {
                delta_x -= 32;
            }
            if delta_y > 15 {
                delta_y -= 32;
            }
            let slot = &mut self.slots[index];
            slot.coord.plane = slot.coord.plane.wrapping_add(plane_delta) & 3;
            slot.coord.x += delta_x;
            slot.coord.y += delta_y;
            slot.pending_movement = Some(PlayerMovement::Teleport {
                plane_delta,
                delta_x,
                delta_y,
            });
            slot.pending_speed = slot.path_speed;
        } else {
            let packed = bits.read_bits(30)?;
            let plane_delta = ((packed >> 28) & 3).to_le_bytes()[0];
            let delta_x = i32::try_from((packed >> 14) & 16_383).unwrap_or_default();
            let delta_y = i32::try_from(packed & 16_383).unwrap_or_default();
            let slot = &mut self.slots[index];
            slot.coord.plane = slot.coord.plane.wrapping_add(plane_delta) & 3;
            slot.coord.x = (slot.coord.x + delta_x) & 16_383;
            slot.coord.y = (slot.coord.y + delta_y) & 16_383;
            slot.pending_movement = Some(PlayerMovement::Teleport {
                plane_delta,
                delta_x,
                delta_y,
            });
            slot.pending_speed = slot.path_speed;
        }
        Ok(())
    }

    fn read_external_player_update(
        &mut self,
        bits: &mut BitReader<'_>,
        index: usize,
        output: &mut PlayerInfoUpdate,
    ) -> Result<bool> {
        match bits.read_bits(2)? {
            0 => self.read_external_spawn(bits, index, output),
            1 => {
                let plane_delta = bits.read_bits_u8(2)?;
                let region = self.slots[index].region;
                let plane = (((region >> 28) & 3) + u32::from(plane_delta)) & 3;
                self.slots[index].region = (region & 0x0fff_ffff) | (plane << 28);
                Ok(false)
            }
            2 => {
                let packed = bits.read_bits_u8(5)?;
                let plane_delta = packed >> 3;
                let direction = packed & 7;
                let region = self.slots[index].region;
                let plane = (((region >> 28) & 3) + u32::from(plane_delta)) & 3;
                let mut x = i32::try_from((region >> 14) & 255).unwrap_or_default();
                let mut y = i32::try_from(region & 255).unwrap_or_default();
                let (delta_x, delta_y) = step_delta(direction);
                x = (x + delta_x).rem_euclid(256);
                y = (y + delta_y).rem_euclid(256);
                self.slots[index].region = pack_region(
                    plane.to_le_bytes()[0],
                    u32::try_from(x).unwrap_or_default(),
                    u32::try_from(y).unwrap_or_default(),
                );
                Ok(false)
            }
            3 => {
                let packed = bits.read_bits(18)?;
                let plane_delta = (packed >> 16) & 3;
                let delta_x = (packed >> 8) & 255;
                let delta_y = packed & 255;
                let region = self.slots[index].region;
                let plane = (((region >> 28) & 3) + plane_delta) & 3;
                let x = (((region >> 14) & 255) + delta_x) & 255;
                let y = ((region & 255) + delta_y) & 255;
                self.slots[index].region = pack_region(plane.to_le_bytes()[0], x, y);
                Ok(false)
            }
            _ => unreachable!(),
        }
    }

    fn read_external_spawn(
        &mut self,
        bits: &mut BitReader<'_>,
        index: usize,
        output: &mut PlayerInfoUpdate,
    ) -> Result<bool> {
        if bits.read_bits(1)? != 0 {
            let _ = self.read_external_player_update(bits, index, output)?;
        }
        let local_x = bits.read_bits(13)?;
        let local_y = bits.read_bits(13)?;
        if bits.read_bits(1)? == 1 {
            self.pending_update_indices.push(index);
        }
        if self.slots[index].active {
            return Err(ProtocolError::new(
                "player info bitstream",
                "external spawn targeted an active player",
            ));
        }

        let region = self.slots[index].region;
        let region_x = (region >> 14) & 255;
        let region_y = region & 255;
        self.slots[index].coord = PlayerCoord {
            plane: ((region >> 28) & 3).to_le_bytes()[0],
            x: i32::try_from((region_x << 13) + local_x).unwrap_or_default(),
            y: i32::try_from((region_y << 13) + local_y).unwrap_or_default(),
        };
        self.slots[index].active = true;
        self.slots[index].pending_movement = None;

        let appearance = self.slots[index].appearance.clone();
        let speed = move_speed(self.slots[index].path_speed)?;
        let change = self.ensure_change(output, index)?;
        change.spawned = true;
        change.appearance = appearance;
        change.movement_speed = Some(speed);
        Ok(true)
    }

    fn apply_move(&mut self, index: usize, movement: PlayerMovement) {
        let slot = &mut self.slots[index];
        match movement {
            PlayerMovement::Step { direction } => {
                let (delta_x, delta_y) = step_delta(direction);
                slot.coord.x += delta_x;
                slot.coord.y += delta_y;
            }
            PlayerMovement::Run { delta_x, delta_y } => {
                slot.coord.x += i32::from(delta_x);
                slot.coord.y += i32::from(delta_y);
            }
            PlayerMovement::Teleport { .. } => unreachable!(),
        }
        slot.pending_movement = Some(movement);
        slot.pending_speed = slot.path_speed;
    }

    fn rebuild_index_tables(&mut self) {
        self.active_indices.clear();
        self.empty_indices.clear();
        for index in 1..PLAYER_CAPACITY {
            self.slots[index].flags >>= 1;
            if self.slots[index].active {
                self.active_indices.push(index);
            } else {
                self.empty_indices.push(index);
            }
        }
    }

    fn finalize_movements(&mut self, output: &mut PlayerInfoUpdate) -> Result<()> {
        for index in 1..PLAYER_CAPACITY {
            let Some(movement) = self.slots[index].pending_movement.take() else {
                continue;
            };
            let speed = move_speed(self.slots[index].pending_speed)?;
            let change = self.ensure_change(output, index)?;
            change.movement = Some(movement);
            change.movement_speed = Some(speed);
        }
        Ok(())
    }

    fn ensure_change<'a>(
        &self,
        output: &'a mut PlayerInfoUpdate,
        index: usize,
    ) -> Result<&'a mut PlayerUpdate> {
        let player_index = model_index(index)?;
        let tile = coord_tile(self.slots[index].coord, "player info")?;
        if let Some(position) = output
            .players
            .iter()
            .position(|change| change.index == player_index)
        {
            output.players[position].tile = tile;
            return Ok(&mut output.players[position]);
        }
        let position = output.players.len();
        output.players.push(PlayerUpdate::new(player_index, tile));
        Ok(&mut output.players[position])
    }

    fn local_index(&self) -> Result<usize> {
        usize::try_from(self.local_player.get())
            .map_err(|_| ProtocolError::new("player info", "invalid local player index"))
    }

    #[allow(
        clippy::too_many_lines,
        reason = "mask order intentionally mirrors PlayerUpdateManager.method2946"
    )]
    fn decode_update_mask(
        &mut self,
        reader: &mut Reader<'_>,
        index: usize,
        mask: u32,
        output: &mut PlayerInfoUpdate,
    ) -> Result<()> {
        let mut temporary_speed = None;

        if mask & 4 != 0 {
            let length = usize::from(reader.u8_add()?);
            let wire = reader.take(length)?;
            let appearance_bytes: Vec<u8> = wire
                .iter()
                .rev()
                .map(|byte| byte.wrapping_sub(128))
                .collect();
            let appearance = decode_appearance(&appearance_bytes)?;
            self.slots[index].appearance = Some(appearance.clone());
            self.ensure_change(output, index)?.appearance = Some(appearance);
        }

        if mask & 16_384 != 0 {
            let raw = reader.i8_neg()?;
            let speed = if raw == 127 { -1 } else { raw };
            let _ = move_speed(speed)?;
            temporary_speed = Some(speed);
            self.slots[index].pending_speed = speed;
        }

        if mask & 8192 != 0 {
            let _ = reader.u16_add()?;
            let _ = reader.u16_add()?;
            let _ = reader.i8_sub()?;
            let _ = reader.i8_add()?;
            let _ = reader.i8_sub()?;
            let _ = reader.u8_add()?;
        }

        if mask & 128 != 0 {
            let facing = reader.u16()?;
            self.ensure_change(output, index)?.facing = Some(facing);
        }

        if mask & 16 != 0 {
            let sequence = match reader.u16_le()? {
                65_535 => None,
                id => Some(SequenceId::new(u32::from(id))),
            };
            let delay = reader.u8_add()?;
            let change = self.ensure_change(output, index)?;
            change.sequence = Some(sequence);
            change.sequence_delay = Some(delay);
        }

        if mask & 32 != 0 {
            skip_cstring(reader)?;
        }

        if mask & 4096 != 0 {
            let style = reader.u16_add()?;
            let _ = reader.u8_neg()?;
            let _ = reader.u8_neg()?;
            let length = usize::from(reader.u8_sub()?);
            let _ = reader.take(length)?;
            let icon_count = match style >> 8 {
                13..=20 => usize::from((style >> 8) - 12),
                _ => 0,
            };
            for _ in 0..icon_count {
                let _ = reader.i8_add()?;
            }
        }

        if mask & 64 != 0 {
            skip_hitsplats_and_healthbars(reader)?;
        }

        if mask & 2048 != 0 {
            let speed = reader.i8_add()?;
            let semantic_speed = move_speed(speed)?;
            self.slots[index].path_speed = speed;
            if temporary_speed.is_none() {
                self.slots[index].pending_speed = speed;
            }
            self.ensure_change(output, index)?.movement_speed = Some(semantic_speed);
        }

        if mask & 2 != 0 {
            let _ = reader.u16_le()?;
            let _ = reader.u8_neg()?;
            let _ = reader.u8()?;
            let length = usize::from(reader.u8_sub()?);
            let _ = reader.take(length)?;
        }

        if mask & 65_536 != 0 {
            let count = usize::from(reader.u8()?);
            for _ in 0..count {
                let _ = reader.u8()?;
                let _ = reader.u16_add_le()?;
                let _ = reader.u32_me()?;
            }
        }

        if mask & 1024 != 0 {
            for _ in 0..3 {
                skip_cstring(reader)?;
            }
        }

        if mask & 512 != 0 {
            let exact_move = ExactMove {
                start_delta_x: reader.i8_sub()?,
                start_delta_y: reader.u8()?.cast_signed(),
                end_delta_x: reader.u8()?.cast_signed(),
                end_delta_y: reader.i8_add()?,
                start_delay: reader.u16_add()?,
                end_delay: reader.u16()?,
                direction: reader.u16_add_le()?,
            };
            self.slots[index].pending_movement = None;
            self.ensure_change(output, index)?.exact_move = Some(exact_move);
        }

        if mask & 8 != 0 {
            let low = u32::from(reader.u16()?);
            let high = u32::from(reader.u8_sub()?);
            let _target = match low | (high << 16) {
                16_777_215 => None,
                value => Some(value),
            };
        }

        Ok(())
    }
}

fn ensure_skip_exhausted(skip: usize, kind: &str) -> Result<()> {
    if skip == 0 {
        Ok(())
    } else {
        Err(ProtocolError::new(
            "player info bitstream",
            format!("{kind} skip count crossed pass boundary"),
        ))
    }
}

fn model_index(index: usize) -> Result<PlayerIndex> {
    let index = u32::try_from(index)
        .map_err(|_| ProtocolError::new("player info", "player index overflow"))?;
    Ok(PlayerIndex::new(index))
}

fn coord_tile(coord: PlayerCoord, stage: &'static str) -> Result<WorldTile> {
    let plane = rustclient_model::Plane::new(coord.plane)
        .ok_or_else(|| ProtocolError::new(stage, "invalid player plane"))?;
    Ok(WorldTile::new(coord.x, coord.y, plane))
}

fn move_speed(value: i8) -> Result<MoveSpeed> {
    MoveSpeed::from_wire(value)
        .ok_or_else(|| ProtocolError::new("player info masks", "invalid movement speed"))
}

fn pack_region(plane: u8, x: u32, y: u32) -> u32 {
    (u32::from(plane & 3) << 28) | ((x & 255) << 14) | (y & 255)
}

fn read_skip_count(bits: &mut BitReader<'_>) -> Result<usize> {
    let count = match bits.read_bits(2)? {
        0 => 0,
        1 => bits.read_bits(5)?,
        2 => bits.read_bits(8)?,
        3 => bits.read_bits(11)?,
        _ => unreachable!(),
    };
    usize::try_from(count)
        .map_err(|_| ProtocolError::new("player info bitstream", "skip count overflow"))
}

fn read_update_mask(reader: &mut Reader<'_>) -> Result<u32> {
    let mut mask = u32::from(reader.u8()?);
    if mask & 1 != 0 {
        mask |= u32::from(reader.u8()?) << 8;
    }
    if mask & 256 != 0 {
        mask |= u32::from(reader.u8()?) << 16;
    }
    Ok(mask)
}

const fn step_delta(direction: u8) -> (i32, i32) {
    match direction {
        0 => (-1, -1),
        1 => (0, -1),
        2 => (1, -1),
        3 => (-1, 0),
        4 => (1, 0),
        5 => (-1, 1),
        6 => (0, 1),
        7 => (1, 1),
        _ => (0, 0),
    }
}

const fn run_delta(direction: u8) -> (i8, i8) {
    match direction {
        0 => (-2, -2),
        1 => (-1, -2),
        2 => (0, -2),
        3 => (1, -2),
        4 => (2, -2),
        5 => (-2, -1),
        6 => (2, -1),
        7 => (-2, 0),
        8 => (2, 0),
        9 => (-2, 1),
        10 => (2, 1),
        11 => (-2, 2),
        12 => (-1, 2),
        13 => (0, 2),
        14 => (1, 2),
        15 => (2, 2),
        _ => (0, 0),
    }
}

fn skip_cstring(reader: &mut Reader<'_>) -> Result<()> {
    loop {
        if reader.u8()? == 0 {
            return Ok(());
        }
    }
}

fn skip_hitsplats_and_healthbars(reader: &mut Reader<'_>) -> Result<()> {
    let hit_count = usize::from(reader.u8_neg()?);
    for _ in 0..hit_count {
        let hit_type = reader.u_short_smart()?;
        if hit_type == 32_767 {
            for _ in 0..4 {
                let _ = reader.u_short_smart()?;
            }
        } else if hit_type != 32_766 {
            let _ = reader.u_short_smart()?;
        }
        let _ = reader.u_short_smart()?;
    }

    let health_count = usize::from(reader.u8_neg()?);
    for _ in 0..health_count {
        let _ = reader.u_short_smart()?;
        let value = reader.u_short_smart()?;
        if value != 32_767 {
            let _ = reader.u_short_smart()?;
            let _ = reader.u8()?;
            if value > 0 {
                let _ = reader.u8_add()?;
            }
        }
    }
    Ok(())
}

fn decode_appearance(bytes: &[u8]) -> Result<PlayerAppearance> {
    let mut reader = Reader::new(bytes, "appearance payload");
    let gender = if reader.u8()? == 0 {
        Gender::Male
    } else {
        Gender::Female
    };
    let _ = reader.u8()?;
    let _ = reader.u8()?;

    let mut slots = [SlotContent::Empty; 12];
    for (slot_index, slot) in slots.iter_mut().enumerate() {
        let first = reader.u8()?;
        if first == 0 {
            continue;
        }
        let second = reader.u8()?;
        let value = (u16::from(first) << 8) | u16::from(second);
        if slot_index == 0 && value == 65_535 {
            let _ = reader.u16()?;
            break;
        }
        if value >= 0x800 {
            *slot = SlotContent::Item(ItemDefinitionId::new(u32::from(value - 0x800)));
        } else if value >= 0x100 {
            *slot = SlotContent::Kit(KitId::new(u32::from(value - 0x100)));
        }
    }

    for _ in 0..12 {
        if reader.u8()? != 0 {
            let _ = reader.u8()?;
        }
    }

    let mut colors = [0u8; 5];
    for color in &mut colors {
        *color = reader.u8()?;
    }

    let movement_sequences = MovementSequences {
        idle: read_sequence(&mut reader)?,
        turn: read_sequence(&mut reader)?,
        walk: read_sequence(&mut reader)?,
        walk_back: read_sequence(&mut reader)?,
        walk_left: read_sequence(&mut reader)?,
        walk_right: read_sequence(&mut reader)?,
        run: read_sequence(&mut reader)?,
    };

    skip_cstring(&mut reader)?;
    let combat_level = reader.u8()?;
    let skill_level = reader.u16()?;
    let hidden = reader.u8()? == 1;

    Ok(PlayerAppearance {
        gender,
        slots,
        colors,
        movement_sequences,
        combat_level,
        skill_level,
        hidden,
    })
}

fn read_sequence(reader: &mut Reader<'_>) -> Result<Option<SequenceId>> {
    Ok(match reader.u16()? {
        65_535 => None,
        id => Some(SequenceId::new(u32::from(id))),
    })
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn revision_240_run_directions_are_single_four_bit_vectors() {
        assert_eq!(run_delta(0), (-2, -2));
        assert_eq!(run_delta(2), (0, -2));
        assert_eq!(run_delta(7), (-2, 0));
        assert_eq!(run_delta(15), (2, 2));
    }

    #[test]
    fn revision_240_mask_extensions_use_bits_zero_and_eight() {
        let mut reader = Reader::new(&[0x01, 0x01, 0x01], "test");
        assert_eq!(read_update_mask(&mut reader).unwrap(), 0x01_0101);
    }

    #[test]
    fn initial_gpi_size_matches_revision_240_layout() {
        assert_eq!(INITIAL_GPI_BITS, 36_858);
        assert_eq!(INITIAL_GPI_BYTES, 4608);
    }

    #[test]
    fn movement_speed_ids_match_revision_240_enum() {
        assert_eq!(move_speed(-1).unwrap(), MoveSpeed::Stationary);
        assert_eq!(move_speed(0).unwrap(), MoveSpeed::Crawl);
        assert_eq!(move_speed(1).unwrap(), MoveSpeed::Walk);
        assert_eq!(move_speed(2).unwrap(), MoveSpeed::Run);
        assert!(move_speed(127).is_err());
    }
}
