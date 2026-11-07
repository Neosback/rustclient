use super::{Result, ScriptHost};
mod variables;
use rustclient_cache::{osrs240::scripts::ScriptOption, script::Opcode};
use rustclient_client_script::Stacks;
use rustclient_game_state::SemanticOperation;
use rustclient_model::{
    ClientSetting, FontId, InterfaceUpdate, InventoryId, MinimapUpdate, ScriptValue, SkillId,
};
use rustclient_platform::ReadOnlyBlobs;
fn script_option<S: ReadOnlyBlobs>(
    h: &ScriptHost<'_, S>,
    device: bool,
    option: i32,
) -> Result<ScriptOption> {
    Ok(if device {
        h.resources.profile.device_option(option)?
    } else {
        h.resources
            .profile
            .game_option(option, |id| h.state.scenery().variable(id))?
    })
}
fn set_brightness<S: ReadOnlyBlobs>(h: &mut ScriptHost<'_, S>, value: i32) -> Result<()> {
    let percent = u8::try_from(value.clamp(0, 100)).map_err(|_| "brightness range")?;
    let brightness = rustclient_model::ScreenBrightness::new(percent).ok_or("brightness range")?;
    h.apply(InterfaceUpdate::ScreenBrightness(brightness))
}
/// Most string lists a script may keep, and entries in each.
const STRING_VECTORS: usize = 64;
const STRING_VECTOR_ENTRIES: usize = 4096;
fn vector_mut<'a, S: ReadOnlyBlobs>(
    h: &'a mut ScriptHost<'_, S>,
    vector: i32,
) -> Result<&'a mut Vec<String>> {
    let vectors = &mut h.resources.string_vectors;
    if !vectors.contains_key(&vector) && vectors.len() >= STRING_VECTORS {
        return Err("string vector limit".into());
    }
    let values = vectors.entry(vector).or_default();
    if values.len() >= STRING_VECTOR_ENTRIES {
        return Err("string vector entry limit".into());
    }
    Ok(values)
}
fn same_text(a: &str, b: &str, ignore_case: bool) -> bool {
    if ignore_case {
        a.to_lowercase() == b.to_lowercase()
    } else {
        a == b
    }
}
fn vector_contains(values: &[String], value: &str, ignore_case: bool) -> bool {
    values
        .iter()
        .any(|held| same_text(held, value, ignore_case))
}
/// The inventory an `INV_*` or `INVOTHER_*` operand names. `INVOTHER_*` adds 32768, the offset
/// the protocol adapter gives inventories of another player (235 `class101`).
fn inventory(op: Opcode, container: i32) -> Result<InventoryId> {
    let container = if matches!(
        op,
        Opcode::InvotherGetobj | Opcode::InvotherGetnum | Opcode::InvotherTotal
    ) {
        container
            .checked_add(32_768)
            .ok_or("inventory id overflow")?
    } else {
        container
    };
    Ok(InventoryId::new(id(container)?))
}
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
        EnumString, GetVarclan, GetVarclansetting, Gettaptodrop, Getwindowmode, IfClose, IfGettop,
        IfHassub, InvGetnum, InvGetobj, InvSize, InvTotal, KeyHeld, MapMembers, MapWorld, Mousecam,
        OcCert, OcIop, OcMembers, OcName, OcParam, OcStackable, OcUncert, OcUnplaceholder,
        OnMobile, Paraheight, Parawidth, Random, Randominc, RunenergyVisible, RunweightVisible,
        Setfolloweropslowpriority, Setshiftclickdrop, Setshowloadingmessages, Setshowmousecross,
        Setshowmouseovertext, Settaptodrop, Stat, StatBase, StatXp, StructParam,
        ViewportGeteffectivesize,
    };
    if variables::instruction(h, op, n, s)? {
        return Ok(true);
    }
    let number = match op {
        Opcode::Setbrightness => {
            let value = s.int()?;
            set_brightness(h, value)?;
            None
        }
        Opcode::Getbrightness => Some(i32::from(h.state.interfaces().brightness().percent())),
        Opcode::ClientBuild => {
            // The 240 client pushes these two literals (traced live: stack +2 integers, no objects).
            s.integers.extend([240, 1]);
            None
        }
        Opcode::SetDeviceOption | Opcode::SetGameOption => {
            let [option, value] = s.ints()?;
            match script_option(h, op == Opcode::SetDeviceOption, option)? {
                ScriptOption::Brightness => set_brightness(h, value)?,
                ScriptOption::Volume(variable) => {
                    if !(0..=100).contains(&value) {
                        return Err("master volume percentage range".into());
                    }
                    h.set_client_variable(variable, value)?;
                }
                ScriptOption::Number { id, .. } => {
                    h.apply(InterfaceUpdate::DevicePreference { id, value })?;
                }
                ScriptOption::Flag { setting, .. } => h.apply(InterfaceUpdate::Setting {
                    key: setting,
                    value: value == 1,
                })?,
            }
            None
        }
        Opcode::GetDeviceOption | Opcode::GetGameOption => {
            let option = s.int()?;
            Some(
                match script_option(h, op == Opcode::GetDeviceOption, option)? {
                    ScriptOption::Brightness => {
                        i32::from(h.state.interfaces().brightness().percent())
                    }
                    ScriptOption::Volume(variable) => h.state.scenery().variable(variable),
                    ScriptOption::Number { id, default } => h
                        .state
                        .interfaces()
                        .device_preferences()
                        .get(&id)
                        .copied()
                        .unwrap_or(default),
                    ScriptOption::Flag { setting, default } => i32::from(
                        h.state
                            .interfaces()
                            .settings()
                            .get(&setting)
                            .copied()
                            .unwrap_or(default),
                    ),
                },
            )
        }
        Opcode::SoundSynth => {
            let [id, loops, delay] = s.ints()?;
            // Reference `queueSoundEffect`: zero loops is a no-op and the queue is bounded.
            if loops != 0 && h.resources.sound_effects.len() < super::SOUND_EFFECT_LIMIT {
                h.resources.sound_effects.push(super::SoundEffectRequest {
                    id: u32::try_from(id).map_err(|_| "negative sound effect id")?,
                    loops: u32::try_from(loops).map_err(|_| "negative sound effect loops")?,
                    delay: u32::try_from(delay).map_err(|_| "negative sound effect delay")?,
                });
            }
            None
        }
        // The friend system is never populated by this client, which the reference reports as
        // status 0 (not loaded), pushed as -2.
        Opcode::FriendCount => Some(-2),
        // No clan settings are ever received, so no listened or affined clan is selectable.
        Opcode::ActiveclansettingsFindListened | Opcode::ActiveclansettingsFindAffined => {
            if op == Opcode::ActiveclansettingsFindAffined {
                s.int()?;
            }
            Some(0)
        }
        Opcode::Reboottimer => Some(h.state.entities().reboot_cycles().cast_signed()),
        Opcode::Staffmodlevel => Some(h.state.authenticated_player().map_or(0, |p| {
            if p.staff_level >= 2 {
                i32::from(p.staff_level)
            } else {
                0
            }
        })),
        Opcode::ChatSetmessagefilter => {
            let filter = s.text()?.trim().to_lowercase();
            h.state
                .apply_operation(SemanticOperation::ChatFilterText(filter))
                .map_err(|e| e.to_string())?;
            None
        }
        Opcode::ChatGetmessagefilter => {
            s.push_object(ScriptValue::Text(h.state.chat().filter_text().to_owned()));
            None
        }
        Opcode::ChatGetfilterPublic => Some(i32::from(h.state.chat().filters()[0])),
        Opcode::ChatGetfilterPrivate => Some(i32::from(h.state.chat().filters()[1])),
        Opcode::ChatGetfilterTrade => Some(i32::from(h.state.chat().filters()[2])),
        Opcode::SetkeyscopeWidget
        | Opcode::SetkeyscopeInterface
        | Opcode::SetkeyscopeAll
        | Opcode::SetkeyscopeNone => {
            let scope = match op {
                Opcode::SetkeyscopeWidget => rustclient_model::WidgetKeyScope::Widget(
                    h.resources.profile.script_widget(s.int()?)?,
                ),
                Opcode::SetkeyscopeInterface => rustclient_model::WidgetKeyScope::Interface(
                    rustclient_model::InterfaceId::new(id(s.int()?)?),
                ),
                Opcode::SetkeyscopeAll => rustclient_model::WidgetKeyScope::All,
                _ => rustclient_model::WidgetKeyScope::None,
            };
            h.apply(InterfaceUpdate::KeyScope(scope))?;
            None
        }
        Opcode::CamSetfollowheight => {
            let mut viewport = h.state.interfaces().viewport();
            viewport.follow_height = s.int()?.max(0).cast_unsigned();
            h.apply(InterfaceUpdate::Viewport(viewport))?;
            None
        }
        Opcode::CamGetfollowheight => {
            Some(h.state.interfaces().viewport().follow_height.cast_signed())
        }
        Opcode::ViewportSetfov | Opcode::ViewportSetzoom | Opcode::ViewportClampfov => {
            let mut viewport = h.state.interfaces().viewport();
            let profile = h.resources.profile;
            match op {
                Opcode::ViewportClampfov => {
                    let values = s.ints::<4>()?;
                    viewport.clamp = std::array::from_fn(|i| {
                        profile.script_viewport_factor(
                            values[i],
                            false,
                            if i % 2 == 0 { 1 } else { 32767 },
                        )
                    });
                    viewport.clamp[1] = viewport.clamp[1].max(viewport.clamp[0]);
                    viewport.clamp[3] = viewport.clamp[3].max(viewport.clamp[2]);
                }
                Opcode::ViewportSetfov => {
                    viewport.fov = s
                        .ints::<2>()?
                        .map(|n| profile.script_viewport_factor(n, true, 256));
                }
                _ => {
                    let [height, width] = s.ints()?;
                    viewport.zoom = [
                        profile.script_viewport_factor(height, false, 256),
                        profile.script_viewport_factor(width, false, 320),
                    ];
                }
            }
            h.apply(InterfaceUpdate::Viewport(viewport))?;
            None
        }
        Opcode::ViewportGetfov | Opcode::ViewportGetzoom => {
            let viewport = h.state.interfaces().viewport();
            s.integers.extend(if op == Opcode::ViewportGetfov {
                viewport
                    .fov
                    .map(|n| h.resources.profile.script_viewport_units(n))
            } else {
                viewport.zoom.map(i32::from)
            });
            None
        }
        Opcode::ChatPlayername => {
            let name = h
                .state
                .entities()
                .local_player()
                .and_then(|p| p.appearance.as_ref())
                .map_or("", |p| p.display_name.as_str());
            s.push_object(ScriptValue::Text(name.to_owned()));
            None
        }
        // `Mes` (3100) files its text as a game message with no sender; the settings pages use it
        // to say why a setting cannot change.
        Opcode::Mes => {
            let text = s.text()?;
            h.state
                .apply_operation(SemanticOperation::GameMessage(
                    rustclient_model::GameMessage {
                        channel: rustclient_model::ids::ChatChannelId::new(0),
                        sender: String::new(),
                        text,
                    },
                ))
                .map_err(|e| e.to_string())?;
            None
        }
        // `TEXT_SWITCH` (4110, `classBB` 4110): the first string when the condition is 1.
        Opcode::TextSwitch => {
            let second = s.text()?;
            let first = s.text()?;
            let chosen = if s.int()? == 1 { first } else { second };
            s.push_object(ScriptValue::Text(chosen));
            None
        }
        // `DEVICEOPTION_GETRANGE` (3217): the pinned jar's ranges by option kind. Option 20 is
        // newer than the jar; its scaling range follows the TSPS 241 client (`DEVICE-OPTION-RANGE`).
        Opcode::DeviceoptionGetrange => {
            let (min, max) = match s.int()? {
                2..=4 => (0, 1),
                5 => (0, i32::MAX),
                6 => (0, 100),
                14 => (25, 90),
                20 => (100, 400),
                other => return Err(format!("unrecognized device option {other}")),
            };
            s.integers.extend([min, max]);
            None
        }
        Opcode::StringvectorAdd => {
            let value = s.text()?;
            let vector = s.int()?;
            vector_mut(h, vector)?.push(value);
            None
        }
        Opcode::StringvectorAddunique => {
            let [vector, ignore_case] = s.ints()?;
            let value = s.text()?;
            let values = vector_mut(h, vector)?;
            if !vector_contains(values, &value, ignore_case != 0) {
                values.push(value);
            }
            None
        }
        Opcode::StringvectorRemove => {
            let [vector, ignore_case] = s.ints()?;
            let value = s.text()?;
            vector_mut(h, vector)?.retain(|held| !same_text(held, &value, ignore_case != 0));
            None
        }
        Opcode::StringvectorGet => {
            let [vector, index] = s.ints()?;
            let value = usize::try_from(index)
                .ok()
                .and_then(|index| h.resources.string_vectors.get(&vector)?.get(index))
                .cloned()
                .unwrap_or_default();
            s.push_object(ScriptValue::Text(value));
            None
        }
        Opcode::StringvectorSize => {
            let vector = s.int()?;
            Some(
                h.resources
                    .string_vectors
                    .get(&vector)
                    .map_or(0, |values| i32::try_from(values.len()).unwrap_or(i32::MAX)),
            )
        }
        Opcode::StringvectorContains => {
            let [vector, ignore_case, alternate] = s.ints()?;
            let value = s.text()?;
            let found = h
                .resources
                .string_vectors
                .get(&vector)
                .is_some_and(|values| {
                    vector_contains(values, &value, ignore_case != 0 || alternate != 0)
                });
            Some(i32::from(found))
        }
        Opcode::StringvectorClear => {
            let vector = s.int()?;
            h.resources.string_vectors.remove(&vector);
            None
        }
        Opcode::ChatGethistorylength => {
            let channel = rustclient_model::ids::ChatChannelId::new(id(s.int()?)?);
            Some(
                i32::try_from(h.state.chat().channel(channel).count())
                    .map_err(|_| "chat length")?,
            )
        }
        Opcode::ChatGethistoryBytypeandline
        | Opcode::ChatGethistoryexBytypeandline
        | Opcode::ChatGethistoryByuid
        | Opcode::ChatGethistoryexByuid => {
            let entry = if matches!(
                op,
                Opcode::ChatGethistoryBytypeandline | Opcode::ChatGethistoryexBytypeandline
            ) {
                let [channel, line] = s.ints()?;
                let channel = rustclient_model::ids::ChatChannelId::new(id(channel)?);
                usize::try_from(line)
                    .ok()
                    .and_then(|line| h.state.chat().channel(channel).nth(line))
            } else {
                let uid = s.int()?.cast_unsigned();
                h.state
                    .chat()
                    .entries()
                    .iter()
                    .find(|entry| entry.id == uid)
            };
            s.integers.push(entry.map_or(-1, |entry| {
                if matches!(
                    op,
                    Opcode::ChatGethistoryByuid | Opcode::ChatGethistoryexByuid
                ) {
                    entry.message.channel.get().cast_signed()
                } else {
                    entry.id.cast_signed()
                }
            }));
            s.integers.push(entry.map_or(0, |entry| {
                (entry.cycle.get() & u64::from(u32::MAX))
                    .try_into()
                    .unwrap_or(0_u32)
                    .cast_signed()
            }));
            for text in [
                entry.map_or("", |e| e.message.sender.as_str()),
                "",
                entry.map_or("", |e| e.message.text.as_str()),
            ] {
                s.push_object(ScriptValue::Text(text.to_owned()));
            }
            s.integers.push(0); // Social relationship is absent until a social-list update is modeled.
            if matches!(
                op,
                Opcode::ChatGethistoryexBytypeandline | Opcode::ChatGethistoryexByuid
            ) {
                s.push_object(ScriptValue::Text(String::new()));
                s.integers.push(0);
            }
            None
        }
        Opcode::ChatGetnextuid | Opcode::ChatGetprevuid => {
            let uid = s.int()?.cast_unsigned();
            // classVX.method12332 / classIW.method6366: NEXT is newer, PREV is older.
            let entry = if op == Opcode::ChatGetnextuid {
                h.state.chat().newer_uid(uid)
            } else {
                h.state.chat().older_uid(uid)
            };
            Some(entry.map_or(-1, u32::cast_signed))
        }

        // `DiscardMobileOffsets` drops its pair outright. `CamSetkeypanspeeds` (`classKU.method6609`)
        // stores the pair as the arrow-key camera pan speeds, plain and with the modifier held
        // (`client.field842`/`field952`, read by `classZJ`); no camera pan consumes them here yet.
        Opcode::DiscardMobileOffsets | Opcode::CamSetkeypanspeeds => {
            s.ints::<2>()?;
            None
        }
        // `classOY.method8858`: the definition's name, `"null"` when it has none or is absent.
        Opcode::NcName => {
            let id = s.int()?;
            let name = h
                .resources
                .profile
                .script_npc_name(&h.resources.cache, id)
                .map_err(|e| e.to_string())?;
            s.push_object(ScriptValue::Text(name));
            None
        }
        // Deob classTK.method10981 only sets the disconnect-vs-reconnect hint.
        // RustClient currently ends every failed session (SPEC-0014), so there
        // is no reconnect branch to suppress. Keep the session alive until the
        // validated button request is sent and the server replies with logout.
        Opcode::Logout => None,
        // `Renderself` only drives a debug outline of the hovered actor in the reference (also
        // reachable through `::renderself`); nothing is drawn here, so its flag is dropped.
        Opcode::MinimapSetzoomable => {
            let zoomable = s.int()? != 0;
            h.apply(InterfaceUpdate::Minimap(MinimapUpdate::Zoomable(zoomable)))?;
            None
        }
        Opcode::MinimapSetzoom => {
            // Scripts pass whole zoom steps; the wheel adds quarter steps.
            let zoom = s.int()?;
            h.apply(InterfaceUpdate::Minimap(MinimapUpdate::ZoomQuarters(
                zoom.saturating_mul(4),
            )))?;
            None
        }
        Opcode::MinimapGetzoom => Some(h.state.interfaces().minimap().zoom()),
        Opcode::MinimapSeticonzoomlimit => {
            let limit = s.int()?;
            h.apply(InterfaceUpdate::Minimap(MinimapUpdate::IconZoomLimit(
                limit,
            )))?;
            None
        }
        // `CcFieldConfig` is text-input field configuration (1133-1146): fields are not
        // interactive yet, so the operand is consumed and ignored (`TEXT-FIELD-WIDGET`).
        Opcode::DiscardMobileFlag | Opcode::Renderself | Opcode::CcFieldConfig(_) => {
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
            if h.resources.font_icons.is_none() {
                h.resources.font_icons = Some(
                    h.resources
                        .profile
                        .font_icons(&h.resources.cache)
                        .map_err(|error| error.to_string())?,
                );
            }
            let icons = h.resources.font_icons.as_deref().unwrap_or_default();
            let font = &h.resources.fonts[&id];
            let lines = rustclient_game_ui::widget_text::wrap_with_icons(&text, font, width, icons);
            Some(if op == Paraheight {
                i32::try_from(lines.len()).map_err(|_| "text line count")?
            } else {
                lines
                    .iter()
                    .map(|line| {
                        rustclient_game_ui::widget_text::width_with_icons(line, font, icons)
                    })
                    .max()
                    .unwrap_or(0)
            })
        }
        Stat | StatBase | StatXp => {
            let id = SkillId::new(id(s.int()?)?);
            let skill = h.state.entities().skills().get(&id);
            Some(match op {
                Stat => skill.map_or(1, |v| i32::from(v.current)),
                StatXp => skill.map_or(0, |v| v.experience.cast_signed()),
                _ => skill.map_or(1, |v| {
                    i32::from(h.resources.profile.real_skill_level(v.experience))
                }),
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
        Opcode::Movecoord => {
            let [base, delta_x, delta_plane, delta_y] = s.ints()?;
            Some(
                h.resources
                    .profile
                    .script_move_coord(base, delta_x, delta_plane, delta_y),
            )
        }
        Coordx | Coordy | Coordz => {
            let p = s.int()?.cast_unsigned();
            Some(match op {
                Coordx => (p >> 14 & 16383).cast_signed(),
                Coordy => (p & 16383).cast_signed(),
                _ => (p >> 28).cast_signed(),
            })
        }
        MapWorld => Some(255),
        Opcode::CamGetangleXa => Some(h.resources.camera_angles[0]),
        Opcode::CamGetangleYa => Some(h.resources.camera_angles[1]),
        Opcode::ChatSendpublic => {
            let chat_type = u8::try_from(s.int()?).map_err(|_| "chat type out of range")?;
            let text = s.text()?;
            let message = h.resources.encode_public_chat(&text)?;
            h.resources
                .queue_chat(rustclient_protocol::ChatIntent::Public { chat_type, message })?;
            None
        }
        // Prompt answers (`classKU` 3104-3106, 3115): the text is read from the script stack and
        // sent as is; the script itself closes the prompt afterwards.
        Opcode::ResumeCountdialog => {
            let text = s.text()?;
            h.resources
                .queue_resume(rustclient_protocol::ResumeIntent::count_from_text(&text))?;
            None
        }
        Opcode::ResumeNamedialog => {
            let text = s.text()?;
            h.resources
                .queue_resume(rustclient_protocol::ResumeIntent::Name(text))?;
            None
        }
        Opcode::ResumeStringdialog => {
            let text = s.text()?;
            h.resources
                .queue_resume(rustclient_protocol::ResumeIntent::Text(text))?;
            None
        }
        Opcode::ResumeObjdialog => {
            let item = s.int()?;
            // The stock writer keeps the low sixteen bits of the item id.
            let item = u16::try_from(item & 0xffff).map_err(|_| "item id out of range")?;
            h.resources
                .queue_resume(rustclient_protocol::ResumeIntent::Item(item))?;
            None
        }
        Opcode::Docheat => {
            let text = s.text()?;
            h.resources
                .queue_chat(rustclient_protocol::ChatIntent::Command(text))?;
            None
        }
        IfClose => {
            if h.resources.effects.len() >= 32 {
                return Err("script effect limit".into());
            }
            h.resources
                .effects
                .push(rustclient_protocol::ClientIntent::CloseModal);
            None
        }
        Clientclock => Some(
            i32::try_from(h.state.scenery().cycle().get()).map_err(|_| "client cycle overflow")?,
        ),
        Clienttype | OnMobile | Opcode::ViewportGetx | Opcode::ViewportGety => Some(0),
        // `classSU.method10737`: the desktop client reports wifi and charging as the literal 1.
        Opcode::MobileWifiavailable | Opcode::MobileBatterycharging => Some(1),
        Getwindowmode => Some(
            h.resources
                .profile
                .script_window_mode_value(h.state.interfaces().window().mode),
        ),
        Opcode::Getdefaultwindowmode => Some(
            h.resources
                .profile
                .script_window_mode_value(h.state.interfaces().default_window_mode()),
        ),
        Opcode::Setwindowmode | Opcode::Setdefaultwindowmode => {
            if let Ok(mode) = h.resources.profile.script_window_mode(s.int()?) {
                if op == Opcode::Setdefaultwindowmode {
                    h.apply(InterfaceUpdate::DefaultWindowMode(mode))?;
                } else {
                    let window = h
                        .resources
                        .profile
                        .window_metrics(mode, h.state.interfaces().window().available);
                    h.apply(InterfaceUpdate::Window(window))?;
                }
            }
            None
        }
        Opcode::Getcanvassize => {
            let canvas = h.state.interfaces().window().canvas;
            s.integers
                .extend([canvas.width.cast_signed(), canvas.height.cast_signed()]);
            None
        }
        Opcode::Getcanvaswidth => Some(h.state.interfaces().window().canvas.width.cast_signed()),
        Opcode::Getcanvasheight => Some(h.state.interfaces().window().canvas.height.cast_signed()),
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
        InvGetobj | InvGetnum | Opcode::InvotherGetobj | Opcode::InvotherGetnum => {
            let [container, slot] = s.ints()?;
            let stack = h
                .state
                .inventories()
                .get(inventory(op, container)?)
                .and_then(|a| usize::try_from(slot).ok().and_then(|i| a.get(i)))
                .copied()
                .flatten();
            Some(if matches!(op, InvGetobj | Opcode::InvotherGetobj) {
                stack.map_or(-1, |i| i.item.get().cast_signed())
            } else {
                stack.map_or(0, |i| i.amount.cast_signed())
            })
        }
        InvTotal | Opcode::InvotherTotal => {
            let [container, item] = s.ints()?;
            let total = h
                .state
                .inventories()
                .get(inventory(op, container)?)
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
        Random | Randominc => {
            let bound = i64::from(s.int()?) + i64::from(op == Randominc);
            Some(match u64::try_from(bound) {
                Ok(bound) if bound > 0 => {
                    use crate::DeterministicRandomSource as _;
                    i32::try_from(h.resources.random.next_sample().get() % bound).unwrap_or(0)
                }
                _ => 0,
            })
        }
        Enum | EnumString | EnumGetoutputcount => {
            let (raw_enum, key, output) = if op == Enum {
                let [_input, output, enum_id, key] = s.ints()?;
                (enum_id, Some(key), output)
            } else if op == EnumString {
                let [enum_id, key] = s.ints()?;
                (enum_id, Some(key), i32::from(b's'))
            } else {
                (s.int()?, None, 0)
            };
            // The reference client loads an enum that does not exist as an empty one whose types
            // never match, so the lookup answers its default instead of failing the script.
            if let Ok(enum_id) = u32::try_from(raw_enum) {
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
            } else if output == i32::from(b's') {
                s.push_object(ScriptValue::Text("null".into()));
                None
            } else {
                Some(0)
            }
        }
        StructParam => {
            let [structure, param] = s.ints()?;
            // An absent struct is an empty one in the reference client, so every parameter answers
            // its default.
            let values = match u32::try_from(structure) {
                Ok(structure) => h
                    .resources
                    .profile
                    .script_struct(&h.resources.cache, structure)
                    .map_err(|e| e.to_string())?,
                Err(_) => std::collections::BTreeMap::default(),
            };
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
        OcName | OcIop | OcMembers | OcParam | OcUnplaceholder | OcStackable | OcCert
        | OcUncert => {
            let param = if op == OcIop || op == OcParam {
                Some(s.int()?)
            } else {
                None
            };
            let raw_item = s.int()?;
            let item = h.resources.profile.script_item(raw_item)?;
            let Some(item) = item else {
                match op {
                    OcName | OcIop => s.push_object(ScriptValue::Text(if op == OcName {
                        "null".into()
                    } else {
                        String::new()
                    })),
                    OcUnplaceholder => s.integers.push(-1),
                    OcMembers | OcStackable => s.integers.push(0),
                    OcCert | OcUncert => s.integers.push(raw_item),
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
                OcStackable => Some(i32::from(def.stackable)),
                // A noted item points at its plain form and the other way round; otherwise the id stays.
                OcCert => Some(match (def.note_template, def.note) {
                    (None, Some(note)) => note.get().cast_signed(),
                    _ => raw_item,
                }),
                OcUncert => Some(match (def.note_template, def.note) {
                    (Some(_), Some(note)) => note.get().cast_signed(),
                    _ => raw_item,
                }),
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
        // No clan setting is received, and `classSU.method10737` reports no battery level: -1.
        GetVarclansetting | GetVarclan | Opcode::MobileBatterylevel => Some(-1),
        ViewportGeteffectivesize => {
            let viewport = h
                .resources
                .interfaces
                .values()
                .flat_map(|g| g.widgets().values())
                .find(|w| {
                    w.content == rustclient_model::WidgetContent::WorldViewport
                        && h.state.interfaces().groups().contains(&w.id.interface)
                })
                .map(|w| {
                    rustclient_game_ui::widgets::widget_bounds(
                        h.state.interfaces(),
                        &h.resources.interfaces,
                        rustclient_model::WidgetAddress::component(w.id),
                        rustclient_game_ui::widgets::WidgetBounds::canvas(h.state.interfaces()),
                    )
                })
                .transpose()
                .map_err(|e| e.to_string())?;
            let size = viewport.map_or([-1, -1], |bounds| {
                rustclient_game_ui::viewport::viewport_shape(
                    bounds.width,
                    bounds.height,
                    h.state.interfaces().viewport(),
                )
                .size
            });
            s.integers.extend(size);
            None
        }
        _ => return Ok(false),
    };
    if let Some(n) = number {
        s.integers.push(n);
    }
    Ok(true)
}

#[cfg(test)]
mod tests;
