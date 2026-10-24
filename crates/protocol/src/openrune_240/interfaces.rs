//! Selected 240 interface family. Packed addresses/transforms end here.
use crate::{ProtocolError, Result, wire::Reader};
use rustclient_model::{
    InterfaceId, InterfaceMount, InterfaceMountKind, InterfaceUpdate, WidgetEventRange, WidgetId,
    WidgetPermissions, WidgetProperty,
};
fn invalid(detail: &str) -> ProtocolError {
    ProtocolError::new("interface update", detail)
}
fn integer(r: &mut Reader<'_>, order: [usize; 4]) -> Result<u32> {
    let b = r.take(4)?;
    Ok(u32::from_be_bytes(order.map(|i| b[i])))
}
fn address(value: u32) -> Result<WidgetId> {
    if value >> 16 == 65535 || value & 65535 == 65535 {
        return Err(invalid("absent component address"));
    }
    Ok(WidgetId::new(InterfaceId::new(value >> 16), value & 65535))
}
fn group(value: u16) -> Option<InterfaceId> {
    (value != 65535).then(|| InterfaceId::new(u32::from(value)))
}
fn le_add(r: &mut Reader<'_>) -> Result<u16> {
    let lo = r.u8()?.wrapping_sub(128);
    let hi = r.u8()?;
    Ok(u16::from_le_bytes([lo, hi]))
}
fn kind(value: u8) -> Result<InterfaceMountKind> {
    match value {
        0 => Ok(InterfaceMountKind::Modal),
        1 => Ok(InterfaceMountKind::Overlay),
        2 => Ok(InterfaceMountKind::Client),
        _ => Err(invalid("unsupported subinterface kind")),
    }
}
fn child(value: u16) -> i32 {
    if value == 65535 { -1 } else { i32::from(value) }
}
fn permissions(primary: u32, operations: u32) -> Result<WidgetPermissions> {
    if primary & !0x00ff_ffff != 0 {
        return Err(invalid("unsupported interaction flags"));
    }
    Ok(WidgetPermissions {
        enabled_operations: operations,
        continue_enabled: primary & 1 != 0,
        target_mask: ((primary >> 11) & 63).to_le_bytes()[0],
        drag_depth: ((primary >> 17) & 7).to_le_bytes()[0],
        accepts_drag: primary & (1 << 20) != 0,
        accepts_target: primary & (1 << 21) != 0,
        crm_target: primary & (1 << 22) != 0,
        script_trigger: primary & (1 << 23) != 0,
    })
}
pub(super) fn decode(opcode: u16, payload: &[u8]) -> Result<InterfaceUpdate> {
    let mut r = Reader::new(payload, "interface update");
    let update = match opcode {
        35 => InterfaceUpdate::SetRoot(group(le_add(&mut r)?)),
        45 => {
            let kind = kind(r.u8()?)?;
            let interface =
                group(le_add(&mut r)?).ok_or_else(|| invalid("subinterface requires a group"))?;
            let parent = address(integer(&mut r, [1, 0, 3, 2])?)?;
            InterfaceUpdate::Open(InterfaceMount {
                parent,
                interface,
                kind,
            })
        }
        116 => InterfaceUpdate::Close(address(integer(&mut r, [0, 1, 2, 3])?)?),
        51 => InterfaceUpdate::Move {
            source: address(integer(&mut r, [1, 0, 3, 2])?)?,
            destination: address(integer(&mut r, [1, 0, 3, 2])?)?,
        },
        54 => {
            let hidden = 128_u8.wrapping_sub(r.u8()?);
            if hidden > 1 {
                return Err(invalid("invalid hidden flag"));
            }
            InterfaceUpdate::SetProperty {
                widget: address(integer(&mut r, [2, 3, 0, 1])?)?,
                property: WidgetProperty::Hidden(hidden != 0),
            }
        }
        11 => {
            let widget = address(integer(&mut r, [2, 3, 0, 1])?)?;
            let mut text = String::new();
            loop {
                let byte = r.u8()?;
                if byte == 0 {
                    break;
                }
                text.push(crate::wire::decode_cp1252(byte));
                if text.len() > 16_384 {
                    return Err(invalid("text limit exceeded"));
                }
            }
            InterfaceUpdate::SetProperty {
                widget,
                property: WidgetProperty::Text(text),
            }
        }
        78 => InterfaceUpdate::SetProperty {
            widget: address(integer(&mut r, [1, 0, 3, 2])?)?,
            property: WidgetProperty::ScrollY(u32::from(r.u16()?)),
        },
        106 => {
            let widget = address(integer(&mut r, [0, 1, 2, 3])?)?;
            let primary = integer(&mut r, [1, 0, 3, 2])?;
            let hi = r.u8()?;
            let lo = r.u8()?.wrapping_sub(128);
            let first = child(u16::from_be_bytes([hi, lo]));
            let operations = integer(&mut r, [3, 2, 1, 0])?;
            let lo = r.u8()?;
            let hi = r.u8()?;
            InterfaceUpdate::SetEvents(WidgetEventRange {
                widget,
                first,
                last: child(u16::from_le_bytes([lo, hi])),
                permissions: permissions(primary, operations)?,
            })
        }
        30 => resync(&mut r, payload.len())?,
        _ => return Err(invalid("unsupported interface opcode")),
    };
    if r.offset != payload.len() {
        return Err(invalid("trailing bytes"));
    }
    Ok(update)
}

fn resync(r: &mut Reader<'_>, length: usize) -> Result<InterfaceUpdate> {
    let root = group(r.u16()?);
    let count = usize::from(r.u16()?);
    if count > 256 {
        return Err(invalid("mount limit exceeded"));
    }
    let mut mounts = Vec::with_capacity(count);
    for _ in 0..count {
        let parent = address(integer(r, [0, 1, 2, 3])?)?;
        let interface = group(r.u16()?).ok_or_else(|| invalid("subinterface requires a group"))?;
        mounts.push(InterfaceMount {
            parent,
            interface,
            kind: kind(r.u8()?)?,
        });
    }
    let mut events = Vec::new();
    while r.offset < length {
        if events.len() >= 4096 {
            return Err(invalid("event range limit exceeded"));
        }
        let widget = address(integer(r, [0, 1, 2, 3])?)?;
        let first = child(r.u16()?);
        let last = child(r.u16()?);
        let primary = integer(r, [0, 1, 2, 3])?;
        let operations = integer(r, [0, 1, 2, 3])?;
        events.push(WidgetEventRange {
            widget,
            first,
            last,
            permissions: permissions(primary, operations)?,
        });
    }
    Ok(InterfaceUpdate::Resync {
        root,
        mounts,
        events,
    })
}

#[cfg(test)]
mod tests {
    use super::*;
    #[test]
    #[expect(
        clippy::too_many_lines,
        reason = "one focused semantic/codec family contract"
    )]
    fn interface_family_matches_selected_transforms_sentinels_and_extended_button_permissions() {
        let widget = WidgetId::new(InterfaceId::new(548), 7);
        let mount = InterfaceMount {
            parent: widget,
            interface: InterfaceId::new(12),
            kind: InterfaceMountKind::Modal,
        };
        let event = WidgetEventRange {
            widget,
            first: 2,
            last: 4,
            permissions: WidgetPermissions {
                enabled_operations: 0x8000_0001,
                continue_enabled: true,
                target_mask: 3,
                drag_depth: 1,
                script_trigger: true,
                ..Default::default()
            },
        };
        let cases = vec![
            (
                35,
                vec![0xa4, 2],
                InterfaceUpdate::SetRoot(Some(InterfaceId::new(548))),
            ),
            (35, vec![127, 255], InterfaceUpdate::SetRoot(None)),
            (
                45,
                vec![0, 0x8c, 0, 0x24, 2, 7, 0],
                InterfaceUpdate::Open(mount),
            ),
            (116, vec![2, 0x24, 0, 7], InterfaceUpdate::Close(widget)),
            (
                51,
                vec![0x24, 2, 7, 0, 0x24, 2, 8, 0],
                InterfaceUpdate::Move {
                    source: widget,
                    destination: WidgetId::new(InterfaceId::new(548), 8),
                },
            ),
            (
                54,
                vec![127, 0, 7, 2, 0x24],
                InterfaceUpdate::SetProperty {
                    widget,
                    property: WidgetProperty::Hidden(true),
                },
            ),
            (
                11,
                vec![0, 7, 2, 0x24, 128, 0],
                InterfaceUpdate::SetProperty {
                    widget,
                    property: WidgetProperty::Text("€".into()),
                },
            ),
            (
                78,
                vec![0x24, 2, 7, 0, 0, 123],
                InterfaceUpdate::SetProperty {
                    widget,
                    property: WidgetProperty::ScrollY(123),
                },
            ),
            (
                106,
                vec![2, 0x24, 0, 7, 0x82, 0, 1, 0x18, 0, 130, 1, 0, 0, 128, 4, 0],
                InterfaceUpdate::SetEvents(event),
            ),
            (
                30,
                vec![
                    2, 0x24, 0, 1, 2, 0x24, 0, 7, 0, 12, 0, 2, 0x24, 0, 7, 0, 2, 0, 4, 0, 0x82,
                    0x18, 1, 128, 0, 0, 1,
                ],
                InterfaceUpdate::Resync {
                    root: Some(InterfaceId::new(548)),
                    mounts: vec![mount],
                    events: vec![event],
                },
            ),
        ];
        for (opcode, bytes, expected) in cases {
            assert_eq!(decode(opcode, &bytes).unwrap(), expected);
            for length in 0..bytes.len() {
                // Resync has no event count: its topology-only prefix is valid.
                if opcode == 30 && length == 11 {
                    continue;
                }
                assert!(
                    decode(opcode, &bytes[..length]).is_err(),
                    "opcode {opcode}, length {length}"
                );
            }
            let mut extra = bytes;
            extra.push(1);
            assert!(decode(opcode, &extra).is_err());
        }
        assert!(decode(54, &[126, 0, 7, 2, 0x24]).is_err());
        assert!(decode(45, &[3, 0x8c, 0, 0x24, 2, 7, 0]).is_err());
        assert!(decode(30, &[0, 1, 255, 255]).is_err());
        assert_eq!(child(65535), -1);
        assert!(permissions(0x0100_0000, 0).is_err());
    }
}
