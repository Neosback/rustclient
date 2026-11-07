//! Integer/bit and session-script variables share the authoritative owners.
use super::{Result, ScriptHost, id};
use rustclient_cache::script::Opcode;
use rustclient_client_script::Stacks;
use rustclient_game_state::SemanticOperation;
use rustclient_model::{InterfaceUpdate, ScriptValue, VarpId};
use rustclient_platform::ReadOnlyBlobs;
pub(super) fn instruction<S: ReadOnlyBlobs>(
    h: &mut ScriptHost<'_, S>,
    op: Opcode,
    n: i32,
    s: &mut Stacks,
) -> Result<bool> {
    use Opcode::{
        GetVarbit, GetVarcInt, GetVarcString, GetVarcStringOld, GetVarp, SetVarbit, SetVarcInt,
        SetVarcString, SetVarcStringOld, SetVarp,
    };
    let number = match op {
        GetVarp => Some(h.state.scenery().variable(VarpId::new(id(n)?))),
        SetVarp => {
            let value = s.int()?;
            h.set_client_variable(VarpId::new(id(n)?), value)?;
            None
        }
        GetVarcInt => Some(if h.resources.profile.is_gameframe_tab_variable(id(n)?) {
            h.state
                .selected_tab()
                .and_then(|tab| i32::try_from(tab).ok())
                .unwrap_or(-1)
        } else {
            match h.state.interfaces().client_variables().get(&id(n)?) {
                Some(ScriptValue::Int(n)) => *n,
                _ => -1,
            }
        }),
        GetVarcString | GetVarcStringOld => {
            s.push_object(
                h.state
                    .interfaces()
                    .client_variables()
                    .get(&id(n)?)
                    .cloned()
                    .unwrap_or_else(|| ScriptValue::Text(String::new())),
            );
            None
        }
        SetVarcInt => {
            let value = s.int()?;
            if h.resources.profile.is_gameframe_tab_variable(id(n)?) {
                h.state
                    .apply_operation(SemanticOperation::SelectGameFrameTab(
                        usize::try_from(value).ok(),
                    ))
                    .map_err(|e| e.to_string())?;
            } else {
                h.apply(InterfaceUpdate::ClientVariable {
                    id: id(n)?,
                    value: ScriptValue::Int(value),
                })?;
            }
            None
        }
        SetVarcString | SetVarcStringOld => {
            let value = ScriptValue::Text(s.text()?);
            h.apply(InterfaceUpdate::ClientVariable { id: id(n)?, value })?;
            None
        }
        GetVarbit | SetVarbit => {
            let bit = h
                .resources
                .profile
                .script_varbit(&h.resources.cache, id(n)?)
                .map_err(|e| e.to_string())?;
            let current = h.state.scenery().variable(bit.varp);
            let range = rustclient_model::VarpBitRange::new(bit.varp, bit.low, bit.high)
                .ok_or("invalid varbit range")?;
            if op == GetVarbit {
                Some(range.extract(current))
            } else {
                let value = s.int()?;
                h.set_client_variable(bit.varp, range.replace(current, value))?;
                None
            }
        }
        _ => return Ok(false),
    };
    if let Some(number) = number {
        s.integers.push(number);
    }
    Ok(true)
}
