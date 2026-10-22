use super::{Result, ScriptHost};
use rustclient_cache::script::Opcode;
use rustclient_client_script::Stacks;
use rustclient_game_state::{SceneryOperation, SemanticOperation};
use rustclient_model::{
    ClientSetting, FontId, InterfaceUpdate, InventoryId, ScriptValue, SkillId, VarpId,
};
use rustclient_platform::ReadOnlyBlobs;
fn id(n: i32) -> Result<u32> {
    u32::try_from(n).map_err(|_| "negative definition ID".into())
}
#[expect(
    clippy::too_many_lines,
    reason = "named script query grammar with explicit stack effects"
)]
pub(super) fn instruction<S: ReadOnlyBlobs>(
    h: &mut ScriptHost<'_, S>,
    op: Opcode,
    n: i32,
    s: &mut Stacks,
) -> Result<bool> {
    use Opcode::{
        Clientclock, Clienttype, Coord, Coordx, Coordy, Coordz, Enum, EnumGetoutputcount,
        EnumString, GetVarbit, GetVarcInt, GetVarcString, GetVarcStringOld, GetVarclan,
        GetVarclansetting, GetVarp, Gettaptodrop, Getwindowmode, IfClose, IfGettop, IfHassub,
        InvGetnum, InvGetobj, InvSize, InvTotal, KeyHeld, MapMembers, MapWorld, Mousecam, OcIop,
        OcMembers, OcName, OcParam, OcUnplaceholder, OnMobile, Paraheight, Parawidth,
        RunenergyVisible, RunweightVisible, SetVarbit, SetVarcInt, SetVarcString, SetVarcStringOld,
        SetVarp, Setfolloweropslowpriority, Setshiftclickdrop, Setshowloadingmessages,
        Setshowmousecross, Setshowmouseovertext, Settaptodrop, Stat, StatBase, StatXp, StructParam,
        ViewportGeteffectivesize,
    };
    let number = match op {
        Opcode::DiscardMobileOffsets => {
            s.ints::<2>()?;
            None
        }
        Opcode::DiscardMobileFlag => {
            s.int()?;
            None
        }
        Setshowmouseovertext
        | Setshowmousecross
        | Setshowloadingmessages
        | Setshiftclickdrop
        | Settaptodrop
        | Setfolloweropslowpriority
        | Mousecam => {
            let value = s.int()? == 1;
            let key = match op {
                Setshowmouseovertext => ClientSetting::ShowMouseOverText,
                Setshowmousecross => ClientSetting::ShowMouseCross,
                Setshowloadingmessages => ClientSetting::ShowLoadingMessages,
                Setshiftclickdrop => ClientSetting::ShiftClickDrop,
                Settaptodrop => ClientSetting::TapToDrop,
                Setfolloweropslowpriority => ClientSetting::FollowerOpsLowPriority,
                _ => ClientSetting::MouseCamera,
            };
            h.apply(InterfaceUpdate::Setting { key, value })?;
            None
        }
        Gettaptodrop => Some(i32::from(
            *h.state
                .interfaces()
                .settings()
                .get(&ClientSetting::TapToDrop)
                .unwrap_or(&false),
        )),
        KeyHeld => {
            let key = h.resources.profile.script_modifier(s.int()?)?;
            Some(i32::from(h.resources.modifiers[key]))
        }
        Paraheight | Parawidth => {
            let [width, font] = s.ints()?;
            let text = s.text()?;
            let id = FontId::new(id(font)?);
            if !h.resources.fonts.contains_key(&id) {
                h.resources.fonts.insert(
                    id,
                    h.resources
                        .profile
                        .load_widget_font(&h.resources.cache, id)
                        .map_err(|e| e.to_string())?,
                );
            }
            let font = &h.resources.fonts[&id];
            let lines = rustclient_game_ui::widget_visuals::wrap(&text, font, width);
            Some(if op == Paraheight {
                i32::try_from(lines.len()).map_err(|_| "text line count")?
            } else {
                lines.iter().map(|line| font.width(line)).max().unwrap_or(0)
            })
        }
        Stat | StatBase | StatXp => {
            let id = SkillId::new(id(s.int()?)?);
            let skill = h.state.entities().skills().get(&id);
            Some(match op {
                Stat => skill.map_or(1, |v| i32::from(v.current)),
                StatXp => skill.map_or(0, |v| v.experience.cast_signed()),
                _ => skill.map_or(1, |v| base_level(v.experience)),
            })
        }
        RunweightVisible => Some(i32::from(h.state.entities().run_weight())),
        RunenergyVisible => Some(i32::from(h.state.entities().run_energy() / 100)),
        Coord => Some(h.state.entities().local_player().map_or(0, |p| {
            ((u32::from(p.tile.plane.get()) << 28)
                | (p.tile.x.cast_unsigned() << 14)
                | p.tile.y.cast_unsigned())
            .cast_signed()
        })),
        Coordx | Coordy | Coordz => {
            let p = s.int()?.cast_unsigned();
            Some(match op {
                Coordx => (p >> 14 & 16383).cast_signed(),
                Coordy => (p & 16383).cast_signed(),
                _ => (p >> 28).cast_signed(),
            })
        }
        MapWorld => Some(255),
        IfClose => {
            if h.resources.effects.len() >= 32 {
                return Err("script effect limit".into());
            }
            h.resources
                .effects
                .push(rustclient_protocol::ClientIntent::CloseModal);
            None
        }
        GetVarp => Some(h.state.scenery().variable(VarpId::new(id(n)?))),
        SetVarp => {
            let value = s.int()?;
            h.state
                .apply_operation(SemanticOperation::Scenery(
                    SceneryOperation::SetClientVariable {
                        id: VarpId::new(id(n)?),
                        value,
                    },
                ))
                .map_err(|e| e.to_string())?;
            None
        }
        GetVarcInt => Some(match h.state.interfaces().client_variables().get(&id(n)?) {
            Some(ScriptValue::Int(n)) => *n,
            _ => -1,
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
            let value = ScriptValue::Int(s.int()?);
            h.apply(InterfaceUpdate::ClientVariable { id: id(n)?, value })?;
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
            let mask = u32::MAX >> (32 - u32::from(bit.high - bit.low + 1));
            if op == GetVarbit {
                Some(((current.cast_unsigned() >> bit.low) & mask).cast_signed())
            } else {
                let value = s.int()?;
                let value = if value < 0 || value.cast_unsigned() > mask {
                    0
                } else {
                    value.cast_unsigned()
                };
                let next = (current.cast_unsigned() & !(mask << bit.low)) | (value << bit.low);
                h.state
                    .apply_operation(SemanticOperation::Scenery(
                        SceneryOperation::SetClientVariable {
                            id: bit.varp,
                            value: next.cast_signed(),
                        },
                    ))
                    .map_err(|e| e.to_string())?;
                None
            }
        }
        Clientclock => Some(
            i32::try_from(h.state.scenery().cycle().get()).map_err(|_| "client cycle overflow")?,
        ),
        Clienttype | OnMobile => Some(0),
        Getwindowmode => Some(2),
        MapMembers => Some(i32::from(
            h.state.authenticated_player().is_some_and(|p| p.member),
        )),
        IfGettop => Some(
            h.state
                .interfaces()
                .root()
                .map_or(-1, |id| id.get().cast_signed()),
        ),
        IfHassub => {
            let widget = h.resources.profile.script_widget(s.int()?)?;
            Some(i32::from(
                h.state.interfaces().mounts().contains_key(&widget),
            ))
        }
        InvGetobj | InvGetnum => {
            let [container, slot] = s.ints()?;
            let stack = h
                .state
                .inventories()
                .get(InventoryId::new(id(container)?))
                .and_then(|a| usize::try_from(slot).ok().and_then(|i| a.get(i)))
                .copied()
                .flatten();
            Some(if op == InvGetobj {
                stack.map_or(-1, |i| i.item.get().cast_signed())
            } else {
                stack.map_or(0, |i| i.amount.cast_signed())
            })
        }
        InvTotal => {
            let [container, item] = s.ints()?;
            let total = h
                .state
                .inventories()
                .get(InventoryId::new(id(container)?))
                .unwrap_or(&[])
                .iter()
                .flatten()
                .filter(|i| i.item.get() == item.cast_unsigned())
                .fold(0_i32, |n, i| n.wrapping_add(i.amount.cast_signed()));
            Some(total)
        }
        InvSize => {
            let container = id(s.int()?)?;
            Some(i32::from(
                h.resources
                    .profile
                    .inventory_capacity(&h.resources.cache, container)
                    .map_err(|e| e.to_string())?,
            ))
        }
        Enum | EnumString | EnumGetoutputcount => {
            let (enum_id, key) = if op == Enum {
                let [_input, _output, enum_id, key] = s.ints()?;
                (id(enum_id)?, Some(key))
            } else if op == EnumString {
                let [enum_id, key] = s.ints()?;
                (id(enum_id)?, Some(key))
            } else {
                (id(s.int()?)?, None)
            };
            if !h.resources.enums.contains_key(&enum_id) {
                h.resources.enums.insert(
                    enum_id,
                    h.resources
                        .profile
                        .load_script_enum(&h.resources.cache, enum_id)
                        .map_err(|e| e.to_string())?,
                );
            }
            let table = &h.resources.enums[&enum_id];
            if let Some(key) = key {
                let value = table.entries.get(&key).unwrap_or(&table.default).clone();
                match value {
                    ScriptValue::Int(v) => Some(v),
                    other => {
                        s.push_object(other);
                        None
                    }
                }
            } else {
                Some(i32::try_from(table.entries.len()).map_err(|_| "enum count")?)
            }
        }
        StructParam => {
            let [structure, param] = s.ints()?;
            let values = h
                .resources
                .profile
                .script_struct(&h.resources.cache, id(structure)?)
                .map_err(|e| e.to_string())?;
            let value = if let Some(v) = values.get(&id(param)?) {
                v.clone()
            } else {
                h.resources
                    .profile
                    .parameter_default(&h.resources.cache, id(param)?)
                    .map_err(|e| e.to_string())?
            };
            match value {
                ScriptValue::Int(v) => Some(v),
                v => {
                    s.push_object(v);
                    None
                }
            }
        }
        OcName | OcIop | OcMembers | OcParam | OcUnplaceholder => {
            let param = if op == OcIop || op == OcParam {
                Some(s.int()?)
            } else {
                None
            };
            let item = h.resources.profile.script_item(s.int()?)?;
            let Some(item) = item else {
                match op {
                    OcName | OcIop => s.push_object(ScriptValue::Text(if op == OcName {
                        "null".into()
                    } else {
                        String::new()
                    })),
                    OcUnplaceholder => s.integers.push(-1),
                    OcMembers => s.integers.push(0),
                    OcParam => {
                        let value = h
                            .resources
                            .profile
                            .parameter_default(
                                &h.resources.cache,
                                id(param.ok_or("missing parameter")?)?,
                            )
                            .map_err(|e| e.to_string())?;
                        match value {
                            ScriptValue::Int(n) => s.integers.push(n),
                            value => s.push_object(value),
                        }
                    }
                    _ => return Ok(false),
                }
                return Ok(true);
            };
            if !h.resources.items.contains_key(&item) {
                h.resources.items.insert(
                    item,
                    h.resources
                        .profile
                        .load_item(&h.resources.cache, item)
                        .map_err(|e| e.to_string())?,
                );
            }
            let def = &h.resources.items[&item];
            match op {
                OcName => {
                    s.push_object(ScriptValue::Text(
                        def.name.clone().unwrap_or_else(|| "null".into()),
                    ));
                    None
                }
                OcIop => {
                    s.push_object(ScriptValue::Text(
                        param
                            .and_then(|n| usize::try_from(n - 1).ok())
                            .and_then(|i| def.inventory_actions.get(i))
                            .cloned()
                            .flatten()
                            .unwrap_or_default(),
                    ));
                    None
                }
                OcMembers => Some(i32::from(def.members)),
                OcUnplaceholder => Some(def.unplaceholder().get().cast_signed()),
                OcParam => {
                    let param = id(param.ok_or("missing parameter")?)?;
                    let value = if let Some(value) = def.parameters.get(&param) {
                        value.clone()
                    } else {
                        h.resources
                            .profile
                            .parameter_default(&h.resources.cache, param)
                            .map_err(|e| e.to_string())?
                    };
                    match value {
                        ScriptValue::Int(n) => Some(n),
                        v => {
                            s.push_object(v);
                            None
                        }
                    }
                }
                _ => return Ok(false),
            }
        }
        GetVarclansetting | GetVarclan => Some(-1),
        ViewportGeteffectivesize => {
            s.integers.extend([765, 503]);
            None
        }
        _ => return Ok(false),
    };
    if let Some(n) = number {
        s.integers.push(n);
    }
    Ok(true)
}

#[expect(
    clippy::cast_possible_truncation,
    clippy::cast_sign_loss,
    reason = "bounded positive experience threshold formula"
)]
fn base_level(experience: u32) -> i32 {
    let mut total = 0;
    for level in 1..=125 {
        total += (f64::from(level) + 300.0 * 2_f64.powf(f64::from(level) / 7.0)) as u32;
        if experience < total / 4 {
            return level;
        }
    }
    126
}
