use super::{Result, ScriptHost};
use rustclient_cache::{osrs240::scripts::ScriptOption, script::Opcode};
use rustclient_client_script::Stacks;
use rustclient_game_state::SemanticOperation;
use rustclient_model::{
    ClientSetting, FontId, InterfaceUpdate, InventoryId, MinimapUpdate, ScriptValue, SkillId,
    VarpId,
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
        InvGetnum, InvGetobj, InvSize, InvTotal, KeyHeld, MapMembers, MapWorld, Mousecam, OcCert,
        OcIop, OcMembers, OcName, OcParam, OcStackable, OcUncert, OcUnplaceholder, OnMobile,
        Paraheight, Parawidth, Random, Randominc, RunenergyVisible, RunweightVisible, SetVarbit,
        SetVarcInt, SetVarcString, SetVarcStringOld, SetVarp, Setfolloweropslowpriority,
        Setshiftclickdrop, Setshowloadingmessages, Setshowmousecross, Setshowmouseovertext,
        Settaptodrop, Stat, StatBase, StatXp, StructParam, ViewportGeteffectivesize,
    };
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

        Opcode::DiscardMobileOffsets => {
            s.ints::<2>()?;
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
                h.set_client_variable(bit.varp, next.cast_signed())?;
                None
            }
        }
        Clientclock => Some(
            i32::try_from(h.state.scenery().cycle().get()).map_err(|_| "client cycle overflow")?,
        ),
        Clienttype | OnMobile | Opcode::ViewportGetx | Opcode::ViewportGety => Some(0),
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
        GetVarclansetting | GetVarclan => Some(-1),
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
mod tests {
    use super::{super::ClientScripts, *};
    use rustclient_game_state::GameState;
    use rustclient_platform::MemoryBlobs;

    use super::super::test_support::scripts;
    fn state() -> GameState {
        let mut state = GameState::new();
        state
            .apply_operation(SemanticOperation::BeginSession {
                revision: rustclient_model::Revision::with_subrevision(240, 2),
            })
            .unwrap();
        state
    }
    fn run(
        scripts: &mut ClientScripts<MemoryBlobs>,
        state: &mut GameState,
        op: Opcode,
        ints: &[i32],
    ) -> Result<Vec<i32>> {
        let mut stacks = Stacks::default();
        stacks.integers.extend_from_slice(ints);
        let mut host = ScriptHost {
            resources: scripts,
            state,
        };
        instruction(&mut host, op, 0, &mut stacks)?;
        Ok(stacks.integers)
    }

    #[test]
    fn cache_chat_history_walk_visits_all_rows_in_both_directions() {
        let (mut scripts, mut state) = (scripts(), state());
        for (channel, text) in [
            (2, "first"),
            (0, "game message"),
            (2, "second"),
            (2, "third"),
        ] {
            state
                .apply_operation(SemanticOperation::GameMessage(
                    rustclient_model::GameMessage {
                        channel: rustclient_model::ids::ChatChannelId::new(channel),
                        sender: "Test".into(),
                        text: text.into(),
                    },
                ))
                .unwrap();
        }
        assert_eq!(
            run(&mut scripts, &mut state, Opcode::ChatGethistorylength, &[2]).unwrap(),
            [3]
        );
        let mut uid = 3;
        let mut rows = Vec::new();
        while uid != -1 {
            rows.push(uid);
            uid = run(&mut scripts, &mut state, Opcode::ChatGetprevuid, &[uid]).unwrap()[0];
        }
        assert_eq!(rows, [3, 2, 1, 0]);
        assert_eq!(
            run(&mut scripts, &mut state, Opcode::ChatGetnextuid, &[0]).unwrap(),
            [1]
        );
        assert_eq!(
            run(&mut scripts, &mut state, Opcode::ChatGetnextuid, &[3]).unwrap(),
            [-1]
        );
        assert_eq!(
            run(&mut scripts, &mut state, Opcode::ChatGetprevuid, &[99]).unwrap(),
            [-1]
        );
    }

    #[test]
    fn game_option_1_is_the_roof_toggle_owned_by_interface_settings() {
        let (mut scripts, mut state) = (scripts(), state());
        let get = |s: &mut ClientScripts<_>, st: &mut GameState| {
            run(s, st, Opcode::GetGameOption, &[1]).unwrap()
        };
        assert_eq!(get(&mut scripts, &mut state), [0]);
        assert!(
            run(&mut scripts, &mut state, Opcode::SetGameOption, &[1, 1])
                .unwrap()
                .is_empty()
        );
        assert_eq!(get(&mut scripts, &mut state), [1]);
        assert_eq!(
            state
                .interfaces()
                .settings()
                .get(&ClientSetting::RoofsHidden),
            Some(&true)
        );
        run(&mut scripts, &mut state, Opcode::SetGameOption, &[1, 0]).unwrap();
        assert_eq!(get(&mut scripts, &mut state), [0]);
    }

    #[test]
    fn device_option_6_is_a_clamped_numeric_scene_brightness() {
        let (mut scripts, mut state) = (scripts(), state());
        assert_eq!(
            run(&mut scripts, &mut state, Opcode::GetDeviceOption, &[6]).unwrap(),
            [40]
        );
        for (value, expected) in [(-1, 0), (35, 35), (80, 80), (150, 100)] {
            run(
                &mut scripts,
                &mut state,
                Opcode::SetDeviceOption,
                &[6, value],
            )
            .unwrap();
            assert_eq!(
                run(&mut scripts, &mut state, Opcode::GetDeviceOption, &[6]).unwrap(),
                [expected]
            );
            assert_eq!(
                i32::from(state.interfaces().brightness().percent()),
                expected
            );
        }
        run(&mut scripts, &mut state, Opcode::Setbrightness, &[25]).unwrap();
        assert_eq!(
            run(&mut scripts, &mut state, Opcode::GetDeviceOption, &[6]).unwrap(),
            [25]
        );
        assert_eq!(
            run(&mut scripts, &mut state, Opcode::Getbrightness, &[]).unwrap(),
            [25]
        );
    }

    #[test]
    fn numeric_device_preferences_default_then_round_trip_without_changing_other_options() {
        let (mut scripts, mut state) = (scripts(), state());
        let get = |s: &mut ClientScripts<_>, st: &mut GameState, id: i32| {
            run(s, st, Opcode::GetDeviceOption, &[id]).unwrap()
        };
        assert_eq!(get(&mut scripts, &mut state, 14), [25]);
        assert_eq!(get(&mut scripts, &mut state, 26), [0]);
        run(&mut scripts, &mut state, Opcode::SetDeviceOption, &[14, 70]).unwrap();
        run(&mut scripts, &mut state, Opcode::SetDeviceOption, &[26, -3]).unwrap();
        assert_eq!(get(&mut scripts, &mut state, 14), [70]);
        assert_eq!(get(&mut scripts, &mut state, 26), [-3]);
        assert_eq!(get(&mut scripts, &mut state, 5), [0]);
        assert_eq!(
            state.interfaces().device_preferences().get(&14),
            Some(&70),
            "preferences live in the interface state owner"
        );
    }

    #[test]
    fn minimap_zoom_ops_follow_the_zoomable_flag() {
        let (mut scripts, mut state) = (scripts(), state());
        let before = state.semantic_fingerprint();
        // Not zoomable yet: a script's zoom is ignored and the default reads back.
        run(&mut scripts, &mut state, Opcode::MinimapSetzoom, &[7]).unwrap();
        assert_eq!(
            run(&mut scripts, &mut state, Opcode::MinimapGetzoom, &[]).unwrap(),
            [4]
        );
        assert_eq!(state.semantic_fingerprint(), before);
        run(&mut scripts, &mut state, Opcode::MinimapSetzoomable, &[1]).unwrap();
        run(&mut scripts, &mut state, Opcode::MinimapSetzoom, &[7]).unwrap();
        assert_eq!(
            run(&mut scripts, &mut state, Opcode::MinimapGetzoom, &[]).unwrap(),
            [7]
        );
        run(&mut scripts, &mut state, Opcode::MinimapSetzoom, &[99]).unwrap();
        assert_eq!(
            run(&mut scripts, &mut state, Opcode::MinimapGetzoom, &[]).unwrap(),
            [8]
        );
        run(
            &mut scripts,
            &mut state,
            Opcode::MinimapSeticonzoomlimit,
            &[3],
        )
        .unwrap();
        assert_eq!(state.interfaces().minimap().icon_zoom_limit, 3);
        assert_ne!(state.semantic_fingerprint(), before);
        run(&mut scripts, &mut state, Opcode::MinimapSetzoomable, &[0]).unwrap();
        assert_eq!(
            run(&mut scripts, &mut state, Opcode::MinimapGetzoom, &[]).unwrap(),
            [4],
            "toggling zoomability restores the default scale"
        );
    }

    #[test]
    fn renderself_consumes_its_flag_without_changing_state() {
        let (mut scripts, mut state) = (scripts(), state());
        let before = state.semantic_fingerprint();
        assert!(
            run(&mut scripts, &mut state, Opcode::Renderself, &[1])
                .unwrap()
                .is_empty()
        );
        assert!(run(&mut scripts, &mut state, Opcode::Renderself, &[]).is_err());
        assert_eq!(state.semantic_fingerprint(), before);
    }

    #[test]
    fn camera_angles_come_from_the_host_and_commands_queue_one_intent() {
        let (mut scripts, mut state) = (scripts(), state());
        scripts.set_camera_angles(200, 1024);
        assert_eq!(
            run(&mut scripts, &mut state, Opcode::CamGetangleXa, &[]).unwrap(),
            [200]
        );
        assert_eq!(
            run(&mut scripts, &mut state, Opcode::CamGetangleYa, &[]).unwrap(),
            [1024]
        );
        let mut stacks = Stacks::default();
        stacks.push_object(ScriptValue::Text("tele 1 2".into()));
        let mut host = ScriptHost {
            resources: &mut scripts,
            state: &mut state,
        };
        instruction(&mut host, Opcode::Docheat, 0, &mut stacks).unwrap();
        assert_eq!(
            scripts.take_intents().len(),
            1,
            "a command becomes one outbound chat intent"
        );
    }

    /// Run one instruction against fresh stacks seeded with `ints` and `texts` (push order).
    fn run_with_text(
        scripts: &mut ClientScripts<MemoryBlobs>,
        state: &mut GameState,
        op: Opcode,
        ints: &[i32],
        texts: &[&str],
    ) -> Result<Stacks> {
        let mut stacks = Stacks::default();
        stacks.integers.extend_from_slice(ints);
        for text in texts {
            stacks.push_object(ScriptValue::Text((*text).into()));
        }
        let mut host = ScriptHost {
            resources: scripts,
            state,
        };
        instruction(&mut host, op, 0, &mut stacks)?;
        Ok(stacks)
    }

    #[test]
    fn settings_text_ops_follow_the_reference_stack_effects() {
        let (mut scripts, mut state) = (scripts(), state());
        let mut pick = |condition| {
            let mut out = run_with_text(
                &mut scripts,
                &mut state,
                Opcode::TextSwitch,
                &[condition],
                &["first", "second"],
            )
            .unwrap();
            out.text().unwrap()
        };
        assert_eq!(pick(1), "first");
        assert_eq!(pick(0), "second");
        let range = |scripts: &mut ClientScripts<MemoryBlobs>, state: &mut GameState, option| {
            run_with_text(scripts, state, Opcode::DeviceoptionGetrange, &[option], &[])
                .map(|out| out.integers)
        };
        assert_eq!(range(&mut scripts, &mut state, 14).unwrap(), [25, 90]);
        assert_eq!(range(&mut scripts, &mut state, 3).unwrap(), [0, 1]);
        assert_eq!(range(&mut scripts, &mut state, 20).unwrap(), [100, 400]);
        assert!(range(&mut scripts, &mut state, 99).is_err());
        assert!(
            run_with_text(
                &mut scripts,
                &mut state,
                Opcode::CcFieldConfig(1137),
                &[5],
                &[]
            )
            .unwrap()
            .integers
            .is_empty(),
            "field configuration consumes its operand and pushes nothing"
        );
    }

    #[test]
    fn absent_definitions_answer_defaults_like_the_reference_client() {
        let (mut scripts, mut state) = (scripts(), state());
        let mut run =
            |op, ints: &[i32]| run_with_text(&mut scripts, &mut state, op, ints, &[]).unwrap();
        // An enum that does not exist never matches the requested types: int outputs answer 0.
        assert_eq!(run(Opcode::Enum, &[73, 73, -1, 5]).integers, [0]);
        let mut text = run(Opcode::Enum, &[73, i32::from(b's'), -1, 5]);
        assert_eq!(text.text().unwrap(), "null");
        assert_eq!(run(Opcode::EnumGetoutputcount, &[-1]).integers, [0]);
        // An absent item keeps its id through the note ops and is not stackable.
        assert_eq!(run(Opcode::OcCert, &[-1]).integers, [-1]);
        assert_eq!(run(Opcode::OcUncert, &[-1]).integers, [-1]);
        assert_eq!(run(Opcode::OcStackable, &[-1]).integers, [0]);
    }

    #[test]
    fn random_ops_stay_inside_their_bound_and_follow_the_seed() {
        let (mut scripts, mut state) = (scripts(), state());
        let mut draw = |scripts: &mut ClientScripts<MemoryBlobs>, op, bound| {
            run_with_text(scripts, &mut state, op, &[bound], &[])
                .unwrap()
                .integers[0]
        };
        let first: Vec<i32> = (0..64)
            .map(|_| draw(&mut scripts, Opcode::Random, 6))
            .collect();
        assert!(first.iter().all(|n| (0..6).contains(n)));
        assert!(
            first
                .iter()
                .collect::<std::collections::BTreeSet<_>>()
                .len()
                > 1
        );
        assert!((0..16).all(|_| draw(&mut scripts, Opcode::Randominc, 2) <= 2));
        assert_eq!(
            draw(&mut scripts, Opcode::Random, 0),
            0,
            "an empty range is 0"
        );
        scripts.seed_random(crate::RandomSeed::new(7));
        let seeded: Vec<i32> = (0..8)
            .map(|_| draw(&mut scripts, Opcode::Random, 1000))
            .collect();
        scripts.seed_random(crate::RandomSeed::new(7));
        let again: Vec<i32> = (0..8)
            .map(|_| draw(&mut scripts, Opcode::Random, 1000))
            .collect();
        assert_eq!(seeded, again);
    }

    #[test]
    fn string_vectors_add_dedupe_remove_and_clear() {
        let (mut scripts, mut state) = (scripts(), state());
        let mut call = |op, ints: &[i32], texts: &[&str]| {
            run_with_text(&mut scripts, &mut state, op, ints, texts).unwrap()
        };
        call(Opcode::StringvectorAdd, &[7], &["Alpha"]);
        call(Opcode::StringvectorAddunique, &[7, 1], &["alpha"]);
        call(Opcode::StringvectorAddunique, &[7, 0], &["alpha"]);
        assert_eq!(call(Opcode::StringvectorSize, &[7], &[]).integers, [2]);
        assert_eq!(
            call(Opcode::StringvectorGet, &[7, 1], &[]).text().unwrap(),
            "alpha"
        );
        assert_eq!(
            call(Opcode::StringvectorGet, &[7, 9], &[]).text().unwrap(),
            "",
            "an index past the end reads as the empty string"
        );
        assert_eq!(
            call(Opcode::StringvectorContains, &[7, 1, 0], &["ALPHA"]).integers,
            [1]
        );
        assert_eq!(
            call(Opcode::StringvectorContains, &[7, 0, 0], &["ALPHA"]).integers,
            [0]
        );
        call(Opcode::StringvectorRemove, &[7, 1], &["alpha"]);
        assert_eq!(call(Opcode::StringvectorSize, &[7], &[]).integers, [0]);
        call(Opcode::StringvectorAdd, &[7], &["x"]);
        call(Opcode::StringvectorClear, &[7], &[]);
        assert_eq!(call(Opcode::StringvectorSize, &[7], &[]).integers, [0]);
    }

    #[test]
    fn mes_files_a_senderless_game_message() {
        let (mut scripts, mut state) = (scripts(), state());
        let mut stacks = Stacks::default();
        stacks.push_object(ScriptValue::Text("That setting is locked.".into()));
        let mut host = ScriptHost {
            resources: &mut scripts,
            state: &mut state,
        };
        instruction(&mut host, Opcode::Mes, 0, &mut stacks).unwrap();
        let entries = state.chat().entries();
        assert_eq!(entries.len(), 1);
        assert_eq!(entries[0].message.text, "That setting is locked.");
    }

    #[test]
    fn prompt_answers_queue_one_resume_intent_each() {
        use rustclient_protocol::{ClientIntent, ResumeIntent};
        let (mut scripts, mut state) = (scripts(), state());
        for (op, text, expected) in [
            (Opcode::ResumeCountdialog, "1234", ResumeIntent::Count(1234)),
            (Opcode::ResumeCountdialog, "12k", ResumeIntent::Count(0)),
            (
                Opcode::ResumeNamedialog,
                "Zezima",
                ResumeIntent::Name("Zezima".into()),
            ),
            (
                Opcode::ResumeStringdialog,
                "hello",
                ResumeIntent::Text("hello".into()),
            ),
        ] {
            let mut stacks = Stacks::default();
            stacks.push_object(ScriptValue::Text(text.into()));
            let mut host = ScriptHost {
                resources: &mut scripts,
                state: &mut state,
            };
            instruction(&mut host, op, 0, &mut stacks).unwrap();
            let intents = scripts.take_intents();
            assert!(matches!(
                intents.as_slice(),
                [ClientIntent::Resume(resume)] if *resume == expected
            ));
        }
        assert!(
            run(
                &mut scripts,
                &mut state,
                Opcode::ResumeObjdialog,
                &[0x1_0005]
            )
            .unwrap()
            .is_empty()
        );
        assert!(matches!(
            scripts.take_intents().as_slice(),
            [ClientIntent::Resume(ResumeIntent::Item(5))]
        ));
    }

    #[test]
    fn public_chat_needs_a_valid_type_and_the_cache_code_table() {
        let (mut scripts, mut state) = (scripts(), state());
        let mut stacks = Stacks::default();
        stacks.push_object(ScriptValue::Text("hello".into()));
        stacks.integers.push(300);
        let mut host = ScriptHost {
            resources: &mut scripts,
            state: &mut state,
        };
        assert!(instruction(&mut host, Opcode::ChatSendpublic, 0, &mut stacks).is_err());
        assert!(scripts.take_intents().is_empty());
    }

    #[test]
    fn volume_options_keep_their_range_and_unknown_options_fail() {
        let (mut scripts, mut state) = (scripts(), state());
        run(&mut scripts, &mut state, Opcode::SetGameOption, &[7, 40]).unwrap();
        assert_eq!(
            run(&mut scripts, &mut state, Opcode::GetGameOption, &[7]).unwrap(),
            [40]
        );
        assert!(run(&mut scripts, &mut state, Opcode::SetGameOption, &[7, 101]).is_err());
        assert!(run(&mut scripts, &mut state, Opcode::SetDeviceOption, &[19, -1]).is_err());
        assert!(run(&mut scripts, &mut state, Opcode::GetGameOption, &[2]).is_err());
        assert!(run(&mut scripts, &mut state, Opcode::GetDeviceOption, &[99]).is_err());
    }

    #[test]
    fn sound_synth_queues_bounded_requests_in_order() {
        let (mut scripts, mut state) = (scripts(), state());
        run(&mut scripts, &mut state, Opcode::SoundSynth, &[2266, 1, 0]).unwrap();
        run(&mut scripts, &mut state, Opcode::SoundSynth, &[10, 0, 0]).unwrap();
        run(&mut scripts, &mut state, Opcode::SoundSynth, &[3809, 2, 30]).unwrap();
        assert_eq!(
            scripts.take_sound_effects(),
            [
                super::super::SoundEffectRequest {
                    id: 2266,
                    loops: 1,
                    delay: 0
                },
                super::super::SoundEffectRequest {
                    id: 3809,
                    loops: 2,
                    delay: 30
                },
            ]
        );
        assert!(scripts.take_sound_effects().is_empty());
        for _ in 0..60 {
            run(&mut scripts, &mut state, Opcode::SoundSynth, &[1, 1, 0]).unwrap();
        }
        assert_eq!(scripts.take_sound_effects().len(), 50);
        run(&mut scripts, &mut state, Opcode::SoundSynth, &[1, 1, 0]).unwrap();
        scripts.reset();
        assert!(scripts.take_sound_effects().is_empty());
        assert!(run(&mut scripts, &mut state, Opcode::SoundSynth, &[-1, 1, 0]).is_err());
    }

    #[test]
    fn friend_and_clan_queries_report_the_unloaded_social_state() {
        let (mut scripts, mut state) = (scripts(), state());
        assert_eq!(
            run(&mut scripts, &mut state, Opcode::FriendCount, &[]).unwrap(),
            [-2]
        );
        assert_eq!(
            run(
                &mut scripts,
                &mut state,
                Opcode::ActiveclansettingsFindAffined,
                &[0]
            )
            .unwrap(),
            [0]
        );
        assert_eq!(
            run(
                &mut scripts,
                &mut state,
                Opcode::ActiveclansettingsFindListened,
                &[]
            )
            .unwrap(),
            [0]
        );
        assert!(
            run(
                &mut scripts,
                &mut state,
                Opcode::ActiveclansettingsFindAffined,
                &[]
            )
            .is_err()
        );
    }
}
