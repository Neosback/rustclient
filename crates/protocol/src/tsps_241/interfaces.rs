//! Profile-owned subinterface and interaction-mask layouts; no widget state is retained here.
use super::{reader::Reader, updates::widget};
use crate::{ProtocolError, Result, ServerUpdate, VariableUpdate};
use rustclient_model::{
    InterfaceId, InterfaceMount, InterfaceMountKind, InterfaceUpdate, VarpId, WidgetEventRange,
    WidgetPermissions, WidgetProperty,
};

pub(super) fn open(
    codec: &mut super::SessionCodec,
    r: &mut Reader<'_>,
) -> Result<Vec<ServerUpdate>> {
    let parent = widget(r.u32()?)?;
    let group = r.u16()?;
    if group == u16::MAX {
        return Err(ProtocolError::new("TSPS interface", "absent group"));
    }
    let kind = match r.u8()? {
        0 => InterfaceMountKind::Modal,
        1 => InterfaceMountKind::Overlay,
        2 => InterfaceMountKind::Client,
        _ => {
            return Err(ProtocolError::new(
                "TSPS interface",
                "unsupported mount type",
            ));
        }
    };
    let count = r.u8()?;
    let mut out = Vec::new();
    for _ in 0..count {
        let id = VarpId::new(u32::from(r.u16()?));
        let value = r.i32()?;
        out.push(ServerUpdate::Variable(VariableUpdate::Set { id, value }));
    }
    let count = r.u8()?;
    for _ in 0..count {
        let id = u32::from(r.u16()?);
        let value = r.i32()?;
        out.push(codec.varbit(id, value)?);
    }
    let count = r.u8()?;
    let mut hidden = Vec::with_capacity(usize::from(count));
    for _ in 0..count {
        hidden.push(widget(r.u32()?)?);
    }
    // These scripts run with the target widget as context. Ordinary unscoped script requests
    // cannot represent that contract; never silently run them with a different context/order.
    if r.u8()? != 0 || r.u8()? != 0 {
        return Err(ProtocolError::new(
            "TSPS interface",
            "widget-scoped pre/post scripts require semantic support",
        ));
    }
    out.push(ServerUpdate::Interface(InterfaceUpdate::Open(
        InterfaceMount {
            parent,
            interface: InterfaceId::new(u32::from(group)),
            kind,
        },
    )));
    for widget in hidden {
        out.push(ServerUpdate::Interface(InterfaceUpdate::SetProperty {
            widget,
            property: WidgetProperty::Hidden(true),
        }));
    }
    Ok(out)
}

pub(super) fn events(opcode: u8, r: &mut Reader<'_>) -> Result<InterfaceUpdate> {
    let widget = widget(r.u32()?)?;
    let child = |n: u16| if n == u16::MAX { -1 } else { i32::from(n) };
    let (first, last) = if opcode == 111 {
        (-1, -1)
    } else {
        (child(r.u16()?), child(r.u16()?))
    };
    if first > last {
        return Err(ProtocolError::new(
            "TSPS interface events",
            "reversed child range",
        ));
    }
    let flags = r.u32()?;
    if flags & !0x00ff_ffff != 0 {
        return Err(ProtocolError::new(
            "TSPS interface events",
            "unsupported interaction flags",
        ));
    }
    let operations = if opcode == 112 {
        r.u32()?
    } else {
        flags >> 1 & 1023
    };
    Ok(InterfaceUpdate::SetEvents(WidgetEventRange {
        widget,
        first,
        last,
        permissions: WidgetPermissions {
            enabled_operations: operations,
            continue_enabled: flags & 1 != 0,
            target_mask: ((flags >> 11) & 63).to_le_bytes()[0],
            drag_depth: ((flags >> 17) & 7).to_le_bytes()[0],
            accepts_drag: flags & (1 << 20) != 0,
            accepts_target: flags & (1 << 21) != 0,
            crm_target: flags & (1 << 22) != 0,
            script_trigger: flags & (1 << 23) != 0,
        },
    }))
}
