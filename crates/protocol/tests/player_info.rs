use rustclient_model::{ExactMove, MoveSpeed, PlayerIndex, SequenceId};
use rustclient_protocol::{Isaac, PlayerMovement, ServerUpdate, SessionCodec};

fn frame(cipher: &mut Isaac, opcode: u16, payload: &[u8]) -> Vec<u8> {
    let mut out = Vec::new();
    for byte in if opcode < 128 {
        vec![u8::try_from(opcode).unwrap()]
    } else {
        vec![
            u8::try_from((opcode >> 8) | 128).unwrap(),
            opcode.to_be_bytes()[1],
        ]
    } {
        out.push(byte.wrapping_add(cipher.next_word().to_le_bytes()[0]));
    }
    out.extend_from_slice(&u16::try_from(payload.len()).unwrap().to_be_bytes());
    out.extend_from_slice(payload);
    out
}

fn bootstrap() -> Vec<u8> {
    let mut bytes = vec![0; 4608];
    let packed = (3221_u32 << 14) | 0x0c92;
    bytes[..4].copy_from_slice(&(packed << 2).to_be_bytes());
    for value in [402_u16, 65_535, 402] {
        let [lo, hi] = value.to_le_bytes();
        bytes.extend_from_slice(&[lo.wrapping_add(128), hi]);
    }
    bytes
}

#[derive(Default)]
struct BitWriter {
    bytes: Vec<u8>,
    bit_offset: usize,
}

impl BitWriter {
    fn bits(&mut self, value: u32, width: usize) {
        for shift in (0..width).rev() {
            let byte_index = self.bit_offset >> 3;
            if byte_index == self.bytes.len() {
                self.bytes.push(0);
            }
            let bit_index = 7 - (self.bit_offset & 7);
            if value >> shift & 1 != 0 {
                self.bytes[byte_index] |= 1 << bit_index;
            }
            self.bit_offset += 1;
        }
    }

    fn align(&mut self) {
        self.bit_offset = (self.bit_offset + 7) & !7;
        self.bytes.resize(self.bit_offset >> 3, 0);
    }

    fn finish(mut self, masks: &[u8]) -> Vec<u8> {
        self.align();
        self.bytes.extend_from_slice(masks);
        self.bytes
    }
}

fn skip_all_external(writer: &mut BitWriter) {
    writer.bits(0, 1);
    writer.bits(3, 2);
    writer.bits(2045, 11);
    writer.align();
}

fn local_step(direction: u8) -> Vec<u8> {
    let mut writer = BitWriter::default();
    writer.bits(1, 1); // active player has an update
    writer.bits(0, 1); // no pending mask
    writer.bits(1, 2); // one-tile step
    writer.bits(u32::from(direction), 3);
    writer.align();
    skip_all_external(&mut writer);
    writer.finish(&[])
}

fn local_run(direction: u8) -> Vec<u8> {
    let mut writer = BitWriter::default();
    writer.bits(1, 1);
    writer.bits(0, 1);
    writer.bits(2, 2); // revision 240 run is one 4-bit vector
    writer.bits(u32::from(direction), 4);
    writer.align();
    skip_all_external(&mut writer);
    writer.finish(&[])
}

fn local_relative_teleport(plane_delta: u8, delta_x: i8, delta_y: i8) -> Vec<u8> {
    let mut writer = BitWriter::default();
    writer.bits(1, 1);
    writer.bits(0, 1);
    writer.bits(3, 2);
    writer.bits(0, 1); // 12-bit relative form
    let x = u32::from(delta_x.cast_unsigned() & 31);
    let y = u32::from(delta_y.cast_unsigned() & 31);
    let packed = (u32::from(plane_delta & 3) << 10) | (x << 5) | y;
    writer.bits(packed, 12);
    writer.align();
    skip_all_external(&mut writer);
    writer.finish(&[])
}

fn local_sequence_and_facing(sequence: u16, delay: u8, facing: u16) -> Vec<u8> {
    let mut writer = BitWriter::default();
    writer.bits(1, 1);
    writer.bits(1, 1); // pending update mask
    writer.bits(0, 2); // no movement
    writer.align();
    skip_all_external(&mut writer);

    let [sequence_lo, sequence_hi] = sequence.to_le_bytes();
    let [facing_hi, facing_lo] = facing.to_be_bytes();
    writer.finish(&[
        0x90, // facing 0x80 + sequence 0x10
        facing_hi,
        facing_lo,
        sequence_lo,
        sequence_hi,
        delay.wrapping_add(128),
    ])
}

fn local_step_with_exact_move(direction: u8) -> Vec<u8> {
    let mut writer = BitWriter::default();
    writer.bits(1, 1);
    writer.bits(1, 1); // pending update mask
    writer.bits(1, 2); // ordinary step, which exact move must suppress
    writer.bits(u32::from(direction), 3);
    writer.align();
    skip_all_external(&mut writer);

    writer.finish(&[
        0x01, 0x02, // extended mask 0x200
        129,  // readByteSub => -1
        0,    // readByte => 0
        2,    // readByte => 2
        129,  // readByteAdd => 1
        0, 133, // readUnsignedShortAdd => 5
        0, 10, // readUnsignedShort => 10
        128, 4, // readUnsignedShortAddLE => 1024
    ])
}

fn initialized_codec(server: &mut Isaac) -> SessionCodec {
    let mut codec = SessionCodec::new(Isaac::new([0; 4]), Isaac::new([0; 4]), PlayerIndex::new(1));
    let rebuild = frame(server, 39, &bootstrap());
    let mut updates = Vec::new();
    codec
        .receive(&rebuild, |update| updates.push(update))
        .unwrap();
    assert!(matches!(
        updates.as_slice(),
        [ServerUpdate::InitialPlacement(_)]
    ));
    codec
}

fn player_update(codec: &mut SessionCodec, server: &mut Isaac, payload: &[u8]) -> ServerUpdate {
    let packet = frame(server, 91, payload);
    let mut updates = Vec::new();
    codec
        .receive(&packet, |update| updates.push(update))
        .unwrap();
    assert_eq!(updates.len(), 1);
    updates.pop().unwrap()
}

fn local(update: &rustclient_model::PlayerInfoUpdate) -> &rustclient_model::PlayerUpdate {
    update
        .player(PlayerIndex::new(1))
        .expect("local player update")
}

#[test]
fn player_info_keeps_four_pass_state_across_step_and_run_ticks() {
    let mut server = Isaac::new([0; 4]);
    let mut codec = initialized_codec(&mut server);

    let ServerUpdate::PlayerInfo(step) = player_update(&mut codec, &mut server, &local_step(7))
    else {
        panic!("expected player info");
    };
    let step = local(&step);
    assert_eq!(step.movement, Some(PlayerMovement::Step { direction: 7 }));
    assert_eq!(step.movement_speed, Some(MoveSpeed::Walk));
    assert_eq!(
        (step.tile.x, step.tile.y, step.tile.plane.get()),
        (3222, 3219, 0)
    );

    let ServerUpdate::PlayerInfo(run) = player_update(&mut codec, &mut server, &local_run(13))
    else {
        panic!("expected player info");
    };
    let run = local(&run);
    assert_eq!(
        run.movement,
        Some(PlayerMovement::Run {
            delta_x: 0,
            delta_y: 2,
        })
    );
    assert_eq!(run.movement_speed, Some(MoveSpeed::Walk));
    assert_eq!(
        (run.tile.x, run.tile.y, run.tile.plane.get()),
        (3222, 3221, 0)
    );
}

#[test]
fn player_info_decodes_signed_relative_teleport_and_plane_delta() {
    let mut server = Isaac::new([0; 4]);
    let mut codec = initialized_codec(&mut server);

    let payload = local_relative_teleport(2, -7, 11);
    let ServerUpdate::PlayerInfo(update) = player_update(&mut codec, &mut server, &payload) else {
        panic!("expected player info");
    };
    let update = local(&update);
    assert_eq!(
        update.movement,
        Some(PlayerMovement::Teleport {
            plane_delta: 2,
            delta_x: -7,
            delta_y: 11,
        })
    );
    assert_eq!(
        (update.tile.x, update.tile.y, update.tile.plane.get()),
        (3214, 3229, 2)
    );
}

#[test]
fn pending_mask_begins_only_after_all_four_bit_passes() {
    let mut server = Isaac::new([0; 4]);
    let mut codec = initialized_codec(&mut server);

    let payload = local_sequence_and_facing(1234, 3, 768);
    let ServerUpdate::PlayerInfo(update) = player_update(&mut codec, &mut server, &payload) else {
        panic!("expected player info");
    };
    let update = local(&update);
    assert_eq!(update.facing, Some(768));
    assert_eq!(update.sequence, Some(Some(SequenceId::new(1234))));
    assert_eq!(update.sequence_delay, Some(3));
    assert_eq!(update.movement, None);
}

#[test]
fn sequence_mask_preserves_explicit_65535_clear() {
    let mut server = Isaac::new([0; 4]);
    let mut codec = initialized_codec(&mut server);

    let payload = local_sequence_and_facing(65_535, 0, 1024);
    let ServerUpdate::PlayerInfo(update) = player_update(&mut codec, &mut server, &payload) else {
        panic!("expected player info");
    };
    let update = local(&update);
    assert_eq!(update.sequence, Some(None));
    assert_eq!(update.sequence_delay, Some(0));
}

#[test]
fn exact_move_mask_suppresses_deferred_ordinary_path_movement() {
    let mut server = Isaac::new([0; 4]);
    let mut codec = initialized_codec(&mut server);

    let payload = local_step_with_exact_move(4);
    let ServerUpdate::PlayerInfo(update) = player_update(&mut codec, &mut server, &payload) else {
        panic!("expected player info");
    };
    let update = local(&update);
    assert_eq!(update.movement, None);
    assert_eq!(
        update.exact_move,
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
    assert_eq!((update.tile.x, update.tile.y), (3222, 3218));
}
