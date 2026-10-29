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
}
