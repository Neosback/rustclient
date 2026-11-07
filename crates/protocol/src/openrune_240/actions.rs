//! Client world-action encodings verified against selected `RSProt` desktop V2 decoders.
use crate::{ProtocolError, Result};
use rustclient_model::{ActionSlot, NpcIndex, ObjectDefinitionId, WorldTile};
fn bounded(value: u32) -> Result<u16> {
    u16::try_from(value).map_err(|_| {
        ProtocolError::new(
            "world action encode",
            "identifier or coordinate exceeds profile range",
        )
    })
}
fn be_add(value: u16) -> [u8; 2] {
    let [hi, lo] = value.to_be_bytes();
    [hi, lo.wrapping_add(128)]
}
fn le_add(value: u16) -> [u8; 2] {
    let [lo, hi] = value.to_le_bytes();
    [lo.wrapping_add(128), hi]
}
pub(super) fn npc(index: NpcIndex, slot: ActionSlot, control: bool) -> Result<(u8, Vec<u8>)> {
    let index = bounded(index.get())?;
    if index == u16::MAX {
        return Err(ProtocolError::new("NPC action encode", "invalid NPC index"));
    }
    let mut out = Vec::with_capacity(4);
    let control = u8::from(control);
    let opcode = match slot.get() {
        0 => {
            out.extend_from_slice(&index.to_le_bytes());
            out.extend_from_slice(&[0, control.wrapping_add(128)]);
            59
        }
        1 => {
            out.extend_from_slice(&index.to_le_bytes());
            out.extend_from_slice(&[128, 128_u8.wrapping_sub(control)]);
            87
        }
        2 => {
            out.extend_from_slice(&index.to_le_bytes());
            out.extend_from_slice(&[128_u8.wrapping_sub(control), 0]);
            43
        }
        3 => {
            out.extend_from_slice(&[128_u8.wrapping_sub(control), 0]);
            out.extend_from_slice(&be_add(index));
            35
        }
        _ => {
            out.extend_from_slice(&[0, control.wrapping_neg()]);
            out.extend_from_slice(&index.to_be_bytes());
            73
        }
    };
    Ok((opcode, out))
}
pub(super) fn location(
    tile: WorldTile,
    object: ObjectDefinitionId,
    slot: ActionSlot,
    control: bool,
) -> Result<(u8, Vec<u8>)> {
    let coordinate = |value| {
        u32::try_from(value)
            .map_err(|_| ProtocolError::new("location action encode", "negative world coordinate"))
            .and_then(bounded)
    };
    let (x, y, id) = (
        coordinate(tile.x)?,
        coordinate(tile.y)?,
        bounded(object.get())?,
    );
    let control = u8::from(control);
    let mut out = Vec::with_capacity(8);
    let opcode = match slot.get() {
        0 => {
            out.push(128_u8.wrapping_sub(control));
            out.extend_from_slice(&le_add(y));
            out.push(0);
            out.extend_from_slice(&le_add(x));
            out.extend_from_slice(&be_add(id));
            74
        }
        1 => {
            out.extend_from_slice(&y.to_be_bytes());
            out.extend_from_slice(&[0, 128_u8.wrapping_sub(control)]);
            out.extend_from_slice(&id.to_be_bytes());
            out.extend_from_slice(&x.to_le_bytes());
            90
        }
        2 => {
            out.extend_from_slice(&[128, 128_u8.wrapping_sub(control)]);
            out.extend_from_slice(&be_add(y));
            out.extend_from_slice(&le_add(id));
            out.extend_from_slice(&x.to_be_bytes());
            8
        }
        3 => {
            out.push(control.wrapping_add(128));
            out.extend_from_slice(&id.to_le_bytes());
            out.extend_from_slice(&y.to_be_bytes());
            out.push(128);
            out.extend_from_slice(&x.to_be_bytes());
            69
        }
        _ => {
            out.extend_from_slice(&y.to_le_bytes());
            out.extend_from_slice(&x.to_be_bytes());
            out.extend_from_slice(&[128, 128_u8.wrapping_sub(control)]);
            out.extend_from_slice(&le_add(id));
            77
        }
    };
    Ok((opcode, out))
}

/// `OPOBJ1_V2..OPOBJ5_V2`: per-op field order and transforms from the rsprox 240
/// `OpObj{1..5}V2Decoder`s. The sub-op byte is 0 (no sub-op), as for NPC and location ops.
pub(super) fn ground_item(
    tile: WorldTile,
    item: rustclient_model::ItemDefinitionId,
    slot: ActionSlot,
    control: bool,
) -> Result<(u8, Vec<u8>)> {
    let coordinate = |value| {
        u32::try_from(value)
            .map_err(|_| {
                ProtocolError::new("ground item action encode", "negative world coordinate")
            })
            .and_then(bounded)
    };
    let (x, y, id) = (
        coordinate(tile.x)?,
        coordinate(tile.y)?,
        bounded(item.get())?,
    );
    let control = u8::from(control);
    let mut out = Vec::with_capacity(8);
    let opcode = match slot.get() {
        0 => {
            out.push(0); // sub-op, g1Alt2
            out.extend_from_slice(&be_add(x));
            out.extend_from_slice(&le_add(y));
            out.push(128_u8.wrapping_sub(control));
            out.extend_from_slice(&id.to_be_bytes());
            112
        }
        1 => {
            out.push(0); // sub-op, g1Alt2
            out.extend_from_slice(&y.to_be_bytes());
            out.extend_from_slice(&be_add(id));
            out.extend_from_slice(&be_add(x));
            out.push(control.wrapping_add(128));
            113
        }
        2 => {
            out.extend_from_slice(&be_add(x));
            out.push(control.wrapping_neg());
            out.push(0); // sub-op, g1
            out.extend_from_slice(&id.to_le_bytes());
            out.extend_from_slice(&y.to_be_bytes());
            22
        }
        3 => {
            out.push(0); // sub-op, g1Alt2
            out.push(128_u8.wrapping_sub(control));
            out.extend_from_slice(&le_add(y));
            out.extend_from_slice(&be_add(id));
            out.extend_from_slice(&x.to_le_bytes());
            65
        }
        _ => {
            out.extend_from_slice(&le_add(id));
            out.push(0); // sub-op, g1
            out.extend_from_slice(&x.to_le_bytes());
            out.push(128_u8.wrapping_sub(control));
            out.extend_from_slice(&be_add(y));
            75
        }
    };
    Ok((opcode, out))
}

/// 240 widget button, indexed operation and continuation families.
pub(super) fn widget(action: rustclient_model::WidgetAction) -> Result<(u8, Vec<u8>)> {
    let widget = action.address.widget;
    if widget.interface.get() >= 65535
        || widget.component >= 65535
        || action.address.child.is_some_and(|c| c >= 65535)
        || action.item.is_some_and(|id| id.get() >= 65535)
    {
        return Err(ProtocolError::new(
            "widget action encode",
            "address or item outside profile range",
        ));
    }
    let packed = (widget.interface.get() << 16) | widget.component;
    if action.operation.is_button() {
        if action.address.child.is_some() || action.item.is_some() {
            return Err(ProtocolError::new(
                "legacy button encode",
                "authored button requires a base component and no item",
            ));
        }
        // classQP.method9746; selected RSProt If1ButtonDecoder.gCombinedId().
        return Ok((11, packed.to_be_bytes().to_vec()));
    }
    let child = action.address.child.map_or(Ok(65535), bounded)?;
    if action.operation.is_continue() {
        // classUL.method11465 -> classXY.method13037/13244: int BE, short LE-add.
        let mut out = packed.to_be_bytes().to_vec();
        out.extend([
            child.to_le_bytes()[0].wrapping_add(128),
            child.to_le_bytes()[1],
        ]);
        return Ok((82, out));
    }
    let item = action.item.map_or(Ok(65535), |id| bounded(id.get()))?;
    let mut out = Vec::with_capacity(9);
    out.extend(packed.to_be_bytes());
    out.extend(child.to_be_bytes());
    out.extend(item.to_be_bytes());
    out.push(
        action.operation.index().ok_or_else(|| {
            ProtocolError::new("widget action encode", "expected indexed operation")
        })? + 1,
    );
    Ok((1, out))
}

/// Selected `RSProt` 240 `ClickWorldMapDecoder.g4Alt1`; stock `classXY.method13284`.
pub(super) fn map_click(tile: WorldTile) -> Result<(u8, Vec<u8>)> {
    let coordinate = |value| {
        u32::try_from(value)
            .ok()
            .filter(|value| *value < 16384)
            .ok_or_else(|| {
                ProtocolError::new("world map click", "coordinate exceeds packed 14-bit range")
            })
    };
    let packed =
        (u32::from(tile.plane.get()) << 28) | (coordinate(tile.x)? << 14) | coordinate(tile.y)?;
    Ok((78, packed.to_le_bytes().to_vec()))
}

#[cfg(test)]
mod tests {
    use super::*;
    #[test]
    fn authored_buttons_share_if1_packet_and_reject_dynamic_or_item_context() {
        use rustclient_model::{
            InterfaceId, ItemDefinitionId, WidgetAction, WidgetAddress, WidgetId, WidgetOperation,
        };
        for operation in [
            WidgetOperation::BUTTON_CLICK,
            WidgetOperation::BUTTON_TOGGLE,
            WidgetOperation::BUTTON_SELECT,
        ] {
            let action = WidgetAction {
                address: WidgetAddress::component(WidgetId::new(InterfaceId::new(0x1234), 0x5678)),
                operation,
                item: None,
                interface_epoch: 0,
            };
            assert_eq!(widget(action).unwrap(), (11, vec![0x12, 0x34, 0x56, 0x78]));
            for bad in [
                WidgetAction {
                    address: WidgetAddress {
                        child: Some(0),
                        ..action.address
                    },
                    ..action
                },
                WidgetAction {
                    item: Some(ItemDefinitionId::new(995)),
                    ..action
                },
            ] {
                let mut cipher = crate::Isaac::new([1, 2, 3, 4]);
                let mut fresh = crate::Isaac::new([1, 2, 3, 4]);
                assert!(
                    super::super::game::ClientIntent::WidgetAction(bad)
                        .encode(&mut cipher)
                        .is_err()
                );
                assert_eq!(cipher.next_word(), fresh.next_word());
            }
        }
    }
    #[test]
    fn widget_family_has_exact_fields_and_invalid_actions_preserve_cipher_state() {
        use rustclient_model::{
            InterfaceId, ItemDefinitionId, WidgetAction, WidgetAddress, WidgetId, WidgetOperation,
        };
        let action = WidgetAction {
            address: WidgetAddress {
                widget: WidgetId::new(InterfaceId::new(12), 13),
                child: Some(0x1234),
            },
            operation: WidgetOperation::new(31).unwrap(),
            item: Some(ItemDefinitionId::new(995)),
            interface_epoch: 7,
        };
        assert_eq!(
            widget(action).unwrap(),
            (1, vec![0, 12, 0, 13, 0x12, 0x34, 3, 0xe3, 32])
        );
        assert_eq!(
            widget(WidgetAction {
                operation: WidgetOperation::CONTINUE,
                ..action
            })
            .unwrap(),
            (82, vec![0, 12, 0, 13, 0xb4, 0x12])
        );
        let mut bad = action;
        bad.address.widget.component = 65535;
        let mut cipher = crate::Isaac::new([1, 2, 3, 4]);
        let mut fresh = crate::Isaac::new([1, 2, 3, 4]);
        assert!(
            super::super::game::ClientIntent::WidgetAction(bad)
                .encode(&mut cipher)
                .is_err()
        );
        assert_eq!(cipher.next_word(), fresh.next_word());
        let empty = WidgetAction {
            address: WidgetAddress::component(action.address.widget),
            item: None,
            ..action
        };
        assert_eq!(&widget(empty).unwrap().1[4..8], &[255; 4]);
    }
    #[test]
    fn action_family_matches_selected_v2_decoder_field_orders_and_transforms() {
        let npc_goldens = [
            (59, [0x34, 0x12, 0, 0x81]),
            (87, [0x34, 0x12, 0x80, 0x7f]),
            (43, [0x34, 0x12, 0x7f, 0]),
            (35, [0x7f, 0, 0x12, 0xb4]),
            (73, [0, 0xff, 0x12, 0x34]),
        ];
        let loc_goldens = [
            (74, [0x7f, 0x12, 0x0c, 0, 0x15, 0x0c, 0x12, 0xb4]),
            (90, [0x0c, 0x92, 0, 0x7f, 0x12, 0x34, 0x95, 0x0c]),
            (8, [0x80, 0x7f, 0x0c, 0x12, 0xb4, 0x12, 0x0c, 0x95]),
            (69, [0x81, 0x34, 0x12, 0x0c, 0x92, 0x80, 0x0c, 0x95]),
            (77, [0x92, 0x0c, 0x0c, 0x95, 0x80, 0x7f, 0xb4, 0x12]),
        ];
        for index in 0..5 {
            let slot = ActionSlot::new(u8::try_from(index).unwrap()).unwrap();
            let result = npc(NpcIndex::new(0x1234), slot, true).unwrap();
            assert_eq!(
                result,
                (npc_goldens[index].0, npc_goldens[index].1.to_vec())
            );
            let result = location(
                WorldTile::new(3221, 3218, rustclient_model::Plane::MIN),
                ObjectDefinitionId::new(0x1234),
                slot,
                true,
            )
            .unwrap();
            assert_eq!(
                result,
                (loc_goldens[index].0, loc_goldens[index].1.to_vec())
            );
        }
        let slot = ActionSlot::new(0).unwrap();
        assert!(npc(NpcIndex::new(65535), slot, false).is_err());
        assert!(
            location(
                WorldTile::new(-1, 0, rustclient_model::Plane::MIN),
                ObjectDefinitionId::new(1),
                slot,
                false
            )
            .is_err()
        );
    }
    #[test]
    fn world_map_click_uses_selected_240_little_endian_packed_coordinates() {
        use rustclient_model::Plane;
        assert_eq!(
            map_click(WorldTile::new(3200, 3201, Plane::new(1).unwrap())).unwrap(),
            (78, vec![0x81, 0x0c, 0x20, 0x13])
        );
        for value in [-1, 16384, i32::MAX] {
            assert!(map_click(WorldTile::new(value, 0, Plane::MIN)).is_err());
            assert!(map_click(WorldTile::new(0, value, Plane::MIN)).is_err());
        }
        assert_eq!(
            map_click(WorldTile::new(16383, 16383, Plane::MAX))
                .unwrap()
                .1,
            vec![0xff, 0xff, 0xff, 0x3f]
        );
    }

    /// Decode an `OPOBJ*_V2` body the way the rsprox 240 decoders read it:
    /// returns `(sub-op, x, y, control, id)`.
    fn decode_ground_item(opcode: u8, body: &[u8]) -> (u8, u16, u16, bool, u16) {
        let mut r = crate::wire::Reader::new(body, "test");
        let g1 = |r: &mut crate::wire::Reader<'_>| r.u8().unwrap();
        let g1_alt1 = |r: &mut crate::wire::Reader<'_>| r.u8_add().unwrap();
        let g1_alt2 = |r: &mut crate::wire::Reader<'_>| r.u8_neg().unwrap();
        let g1_alt3 = |r: &mut crate::wire::Reader<'_>| r.u8_sub().unwrap();
        let g2 = |r: &mut crate::wire::Reader<'_>| r.u16().unwrap();
        let g2_alt1 = |r: &mut crate::wire::Reader<'_>| r.u16_le().unwrap();
        let g2_alt2 = |r: &mut crate::wire::Reader<'_>| r.u16_add().unwrap();
        let g2_alt3 = |r: &mut crate::wire::Reader<'_>| r.u16_add_le().unwrap();
        let out = match opcode {
            112 => {
                let subop = g1_alt2(&mut r);
                let x = g2_alt2(&mut r);
                let y = g2_alt3(&mut r);
                let control = g1_alt3(&mut r) == 1;
                (subop, x, y, control, g2(&mut r))
            }
            113 => {
                let subop = g1_alt2(&mut r);
                let y = g2(&mut r);
                let id = g2_alt2(&mut r);
                let x = g2_alt2(&mut r);
                (subop, x, y, g1_alt1(&mut r) == 1, id)
            }
            22 => {
                let x = g2_alt2(&mut r);
                let control = g1_alt2(&mut r) == 1;
                let subop = g1(&mut r);
                let id = g2_alt1(&mut r);
                (subop, x, g2(&mut r), control, id)
            }
            65 => {
                let subop = g1_alt2(&mut r);
                let control = g1_alt3(&mut r) == 1;
                let y = g2_alt3(&mut r);
                let id = g2_alt2(&mut r);
                (subop, g2_alt1(&mut r), y, control, id)
            }
            75 => {
                let id = g2_alt3(&mut r);
                let subop = g1(&mut r);
                let x = g2_alt1(&mut r);
                let control = g1_alt3(&mut r) == 1;
                (subop, x, g2_alt2(&mut r), control, id)
            }
            _ => unreachable!(),
        };
        assert_eq!(r.offset, body.len(), "OPOBJ body is 8 bytes");
        out
    }

    #[test]
    fn ground_item_ops_round_trip_through_the_reference_decoders() {
        let tile = WorldTile::new(3222, 3218, rustclient_model::Plane::MIN);
        let item = rustclient_model::ItemDefinitionId::new(995);
        for (slot, opcode) in [(0, 112), (1, 113), (2, 22), (3, 65), (4, 75)] {
            for control in [false, true] {
                let (encoded, body) =
                    ground_item(tile, item, ActionSlot::new(slot).unwrap(), control).unwrap();
                assert_eq!(encoded, opcode);
                assert_eq!(body.len(), 8);
                assert_eq!(
                    decode_ground_item(opcode, &body),
                    (0, 3222, 3218, control, 995),
                    "op {}",
                    slot + 1
                );
            }
        }
        assert!(
            ground_item(
                WorldTile::new(-1, 0, rustclient_model::Plane::MIN),
                item,
                ActionSlot::new(0).unwrap(),
                false
            )
            .is_err()
        );
    }
}

/// `OPNPC6` (101): the NPC definition id, `g2Alt2`.
pub(super) fn examine_npc(definition: rustclient_model::NpcDefinitionId) -> Result<(u8, Vec<u8>)> {
    Ok((101, be_add(bounded(definition.get())?).to_vec()))
}
/// `OPLOC6` (105): the object definition id, `g2Alt1`.
pub(super) fn examine_location(object: ObjectDefinitionId) -> Result<(u8, Vec<u8>)> {
    Ok((105, bounded(object.get())?.to_le_bytes().to_vec()))
}
/// `OPOBJ6` (9): item id `g2Alt1`, tile y `g2Alt3`, tile x `g2`.
pub(super) fn examine_ground_item(
    tile: WorldTile,
    item: rustclient_model::ItemDefinitionId,
) -> Result<(u8, Vec<u8>)> {
    let coordinate = |value| {
        u32::try_from(value)
            .map_err(|_| ProtocolError::new("examine encode", "negative world coordinate"))
            .and_then(bounded)
    };
    let mut out = Vec::with_capacity(6);
    out.extend_from_slice(&bounded(item.get())?.to_le_bytes());
    out.extend_from_slice(&le_add(coordinate(tile.y)?));
    out.extend_from_slice(&coordinate(tile.x)?.to_be_bytes());
    Ok((9, out))
}

#[cfg(test)]
mod examine_tests {
    use super::*;
    use rustclient_model::{ItemDefinitionId, NpcDefinitionId, Plane};

    #[test]
    fn examine_packets_use_the_240_field_transforms() {
        assert_eq!(
            examine_npc(NpcDefinitionId::new(0x0102)).unwrap(),
            (101, vec![0x01, 0x02 + 128])
        );
        assert_eq!(
            examine_location(ObjectDefinitionId::new(0x0102)).unwrap(),
            (105, vec![0x02, 0x01])
        );
        // Item 0x0102, y 0x0304 (g2Alt3: low byte + 128 first), x 0x0506 (big-endian).
        assert_eq!(
            examine_ground_item(
                WorldTile::new(0x0506, 0x0304, Plane::MIN),
                ItemDefinitionId::new(0x0102)
            )
            .unwrap(),
            (9, vec![0x02, 0x01, 0x04 + 128, 0x03, 0x05, 0x06])
        );
        assert!(examine_npc(NpcDefinitionId::new(70_000)).is_err());
    }
}

/// `OPPLAYER1..8`: field order and transforms from the rsprox 240 `OpPlayer{1..8}Decoder`s.
/// Opcodes 57, 21, 36, 64, 52, 4, 50 and 41.
pub(super) fn player(
    index: rustclient_model::PlayerIndex,
    slot: rustclient_model::PlayerOpSlot,
    control: bool,
) -> Result<(u8, Vec<u8>)> {
    let index = bounded(index.get())?;
    let control = u8::from(control);
    let mut out = Vec::with_capacity(3);
    let opcode = match slot.get() {
        1 => {
            out.push(control.wrapping_neg()); // g1Alt2
            out.extend_from_slice(&index.to_be_bytes());
            57
        }
        2 => {
            out.push(128_u8.wrapping_sub(control)); // g1Alt3
            out.extend_from_slice(&index.to_le_bytes());
            21
        }
        3 => {
            out.extend_from_slice(&be_add(index)); // g2Alt2
            out.push(control.wrapping_add(128)); // g1Alt1
            36
        }
        4 => {
            out.push(128_u8.wrapping_sub(control));
            out.extend_from_slice(&index.to_be_bytes());
            64
        }
        5 => {
            out.extend_from_slice(&be_add(index));
            out.push(control.wrapping_add(128));
            52
        }
        6 => {
            out.push(control.wrapping_add(128));
            out.extend_from_slice(&index.to_le_bytes());
            4
        }
        7 => {
            out.push(control.wrapping_add(128));
            out.extend_from_slice(&index.to_le_bytes());
            50
        }
        _ => {
            out.extend_from_slice(&be_add(index));
            out.push(control); // g1
            41
        }
    };
    Ok((opcode, out))
}

#[cfg(test)]
mod player_tests {
    use super::*;
    use rustclient_model::{PlayerIndex, PlayerOpSlot};

    #[test]
    fn every_player_option_uses_its_240_opcode_and_field_transforms() {
        let index = PlayerIndex::new(0x0102);
        let encode =
            |slot, control| player(index, PlayerOpSlot::new(slot).unwrap(), control).unwrap();
        // Control off, then on, for each slot.
        assert_eq!(encode(1, false), (57, vec![0, 0x01, 0x02]));
        assert_eq!(encode(1, true), (57, vec![255, 0x01, 0x02]));
        assert_eq!(encode(2, true), (21, vec![127, 0x02, 0x01]));
        assert_eq!(encode(3, true), (36, vec![0x01, 0x02 + 128, 129]));
        assert_eq!(encode(4, false), (64, vec![128, 0x01, 0x02]));
        assert_eq!(encode(5, false), (52, vec![0x01, 0x02 + 128, 128]));
        assert_eq!(encode(6, true), (4, vec![129, 0x02, 0x01]));
        assert_eq!(encode(7, false), (50, vec![128, 0x02, 0x01]));
        assert_eq!(encode(8, true), (41, vec![0x01, 0x02 + 128, 1]));
        assert!(PlayerOpSlot::new(0).is_none() && PlayerOpSlot::new(9).is_none());
        assert!(
            player(
                PlayerIndex::new(70_000),
                PlayerOpSlot::new(1).unwrap(),
                false
            )
            .is_err()
        );
    }
}

/// `IF_BUTTOND` (39): a widget dropped on another. Stock `classTM.method10984` writes, in this
/// order and matching the rsprox 240 `IfButtonDDecoder`: the dragged child (`g2Alt2`), the target's
/// packed component id (`g4`), the target child (`g2`), the dragged packed id (`g4`), the target
/// item (`g2Alt2`) and the dragged item (`g2Alt3`). A missing child or item is 65535.
pub(super) fn widget_drag(drag: rustclient_model::WidgetDragDrop) -> Result<(u8, Vec<u8>)> {
    let field = |end: &rustclient_model::WidgetDragEnd| -> Result<(u32, u16, u16)> {
        let widget = end.address.widget;
        let child = end.address.child.map_or(Ok(65535), bounded)?;
        let item = end.item.map_or(Ok(65535), |id| bounded(id.get()))?;
        if widget.interface.get() >= 65535 || widget.component >= 65535 {
            return Err(ProtocolError::new(
                "widget drag encode",
                "address outside profile range",
            ));
        }
        Ok((
            (widget.interface.get() << 16) | widget.component,
            child,
            item,
        ))
    };
    let (selected_id, selected_child, selected_item) = field(&drag.selected)?;
    let (target_id, target_child, target_item) = field(&drag.target)?;
    let mut out = Vec::with_capacity(16);
    out.extend(be_add(selected_child));
    out.extend(target_id.to_be_bytes());
    out.extend(target_child.to_be_bytes());
    out.extend(selected_id.to_be_bytes());
    out.extend(be_add(target_item));
    out.extend(le_add(selected_item));
    Ok((39, out))
}

#[cfg(test)]
mod drag_tests {
    use super::*;
    use rustclient_model::{
        InterfaceId, ItemDefinitionId, WidgetAddress, WidgetDragDrop, WidgetDragEnd, WidgetId,
    };

    fn end(interface: u32, component: u32, child: Option<u32>, item: Option<u32>) -> WidgetDragEnd {
        WidgetDragEnd {
            address: WidgetAddress {
                widget: WidgetId::new(InterfaceId::new(interface), component),
                child,
            },
            item: item.map(ItemDefinitionId::new),
        }
    }

    #[test]
    fn drag_encodes_the_240_field_order_and_transforms() {
        // Dragged 149:0 child 3 holding item 0x0102; dropped on 149:0 child 5 holding nothing.
        let drag = WidgetDragDrop {
            selected: end(149, 0, Some(3), Some(0x0102)),
            target: end(149, 0, Some(5), None),
        };
        let packed = (149_u32 << 16).to_be_bytes();
        let mut expected = vec![0, 3 + 128]; // selected child, g2Alt2
        expected.extend(packed); // target packed id
        expected.extend([0, 5]); // target child
        expected.extend(packed); // selected packed id
        expected.extend([0xff, 0xff_u8.wrapping_add(128)]); // target item 65535, g2Alt2
        expected.extend([0x02_u8.wrapping_add(128), 0x01]); // selected item, g2Alt3
        assert_eq!(widget_drag(drag).unwrap(), (39, expected));
        // Whole-component ends use child 65535.
        let plain = WidgetDragDrop {
            selected: end(1, 2, None, None),
            target: end(3, 4, None, None),
        };
        let (_, bytes) = widget_drag(plain).unwrap();
        assert_eq!(&bytes[..2], &[0xff, 0xff_u8.wrapping_add(128)]);
        assert_eq!(bytes.len(), 16);
        let too_big = WidgetDragDrop {
            selected: end(1, 2, Some(70_000), None),
            target: end(3, 4, None, None),
        };
        assert!(widget_drag(too_big).is_err());
    }
}
