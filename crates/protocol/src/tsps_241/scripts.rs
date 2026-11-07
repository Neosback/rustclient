//! TSPS script requests and ordered server-authored preconditions.
use super::{reader::Reader, updates::inventory_slot};
use crate::{ProtocolError, Result, ServerUpdate, VariableUpdate};
use rustclient_model::{
    InventoryId, InventoryUpdate, ScriptId, ScriptRequest, ScriptValue, VarpId,
};

pub(super) fn arguments(r: &mut Reader<'_>) -> Result<Vec<ScriptValue>> {
    let count = r.u8()?;
    let mut arguments = Vec::with_capacity(usize::from(count));
    for _ in 0..count {
        arguments.push(match r.u8()? {
            0 => ScriptValue::Text(r.string()?),
            1 => ScriptValue::Int(r.i32()?),
            2 => ScriptValue::Long(r.long()?),
            _ => return Err(ProtocolError::new("TSPS script", "unknown argument type")),
        });
    }
    Ok(arguments)
}

pub(super) fn decode(
    codec: &mut super::SessionCodec,
    opcode: u8,
    r: &mut Reader<'_>,
) -> Result<Vec<ServerUpdate>> {
    // RUN_CLIENT_SCRIPT has a short id; WIDGET_RUN_SCRIPT has an int id.
    let id = ScriptId::new(if opcode == 170 {
        u32::from(r.u16()?)
    } else {
        r.u32()?
    });
    let arguments = arguments(r)?;
    let mut out = Vec::new();
    if opcode == 110 {
        if r.remaining() > 0 {
            let count = r.u16()?;
            for _ in 0..count {
                let id = VarpId::new(r.u32()?);
                let value = r.i32()?;
                out.push(ServerUpdate::Variable(VariableUpdate::Set { id, value }));
            }
        }
        if r.remaining() > 0 {
            let count = r.u16()?;
            for _ in 0..count {
                let id = r.u32()?;
                let value = r.i32()?;
                out.push(codec.varbit(id, value)?);
            }
        }
        if r.remaining() > 0 {
            let count = r.u8()?;
            let mut total_slots = 0_usize;
            for _ in 0..count {
                let id = InventoryId::new(u32::from(r.u16()?));
                let capacity = usize::from(r.u16()?);
                total_slots = total_slots.checked_add(capacity).ok_or_else(|| {
                    ProtocolError::new("TSPS script inventory", "capacity overflow")
                })?;
                if total_slots > 16_384 {
                    return Err(ProtocolError::new(
                        "TSPS script inventory",
                        "packet slot budget exceeded",
                    ));
                }
                let count = r.u16()?;
                let mut slots = vec![None; capacity];
                let mut seen = vec![false; capacity];
                for _ in 0..count {
                    let (slot, stack) = inventory_slot(r)?;
                    let index = usize::from(slot);
                    if index >= capacity || seen[index] {
                        return Err(ProtocolError::new(
                            "TSPS script inventory",
                            "invalid or duplicate slot",
                        ));
                    }
                    slots[index] = stack;
                    seen[index] = true;
                }
                out.push(ServerUpdate::Inventory(InventoryUpdate::Replace {
                    id,
                    slots,
                }));
            }
        }
    }
    out.push(ServerUpdate::Script(ScriptRequest { id, arguments }));
    Ok(out)
}
