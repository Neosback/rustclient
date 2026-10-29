//! Revision 240 script footer, including long locals/arguments (not the 235 footer).
use crate::{
    Cache, CacheProfile, FileId, GroupId, IndexId, Source,
    error::Result,
    reader::Reader,
    script::{Instruction, Script},
};
use rustclient_model::ScriptId;
use rustclient_platform::ReadOnlyBlobs;
use std::collections::BTreeMap;
/// Semantic owner of a script-visible game or device option.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum ScriptOption {
    /// Numeric scene brightness percentage (live scripts 3961/3966; classBY.method1359).
    Brightness,
    /// A percentage stored in the existing client-variable owner.
    Volume(rustclient_model::VarpId),
    /// An integer device preference stored in `GameState`'s interface state; `default` applies
    /// until first set. The client has no engine feature behind it yet, so scripts only read
    /// back what they stored.
    Number { id: u8, default: i32 },
    /// A boolean stored in `GameState`'s interface settings; `default` applies until first set.
    Flag {
        setting: rustclient_model::ClientSetting,
        default: bool,
    },
}
impl CacheProfile {
    /// Cache 240's settings search suppresses chat's key listener. Restore it
    /// through its authored script when that group closes (TSPS input lifecycle).
    #[must_use]
    pub fn interface_close_script(
        self,
        group: rustclient_model::InterfaceId,
    ) -> Option<(
        rustclient_model::WidgetAddress,
        rustclient_model::ScriptRequest,
    )> {
        use rustclient_model::{InterfaceId, ScriptRequest, WidgetAddress, WidgetId};
        match (self, group.get()) {
            (Self::OpenRune240, 134) => Some((
                WidgetAddress::component(WidgetId::new(InterfaceId::new(162), 0)),
                ScriptRequest {
                    id: ScriptId::new(2158),
                    arguments: Vec::new(),
                },
            )),
            _ => None,
        }
    }
    /// Revision-240 key codes consumed by CS2 key listeners.
    #[must_use]
    pub fn script_key(self, key: rustclient_model::WidgetKey) -> i32 {
        use rustclient_model::WidgetKey;
        match key {
            WidgetKey::Enter => 84,
            WidgetKey::Backspace => 85,
            WidgetKey::Tab => 80,
            WidgetKey::Escape => 13,
            WidgetKey::Left => 96,
            WidgetKey::Right => 97,
            WidgetKey::Up => 98,
            WidgetKey::Down => 99,
            WidgetKey::Delete => 101,
            WidgetKey::Home => 102,
            WidgetKey::End => 103,
            WidgetKey::PageUp => 104,
            WidgetKey::PageDown => 105,
        }
    }
    /// Volume aliases verified against cache scripts 2475, 3643, 3644 and 9685;
    /// option 1 is the roof toggle read by script 73 and written by 3647/4583/6025/6026.
    /// # Errors
    /// Rejects game options without an implemented semantic owner.
    pub fn game_option(
        self,
        option: i32,
        read: impl Fn(rustclient_model::VarpId) -> i32,
    ) -> std::result::Result<ScriptOption, String> {
        use rustclient_model::{ClientSetting, VarpId};
        match (self, option) {
            (Self::OpenRune240, 1) => Ok(ScriptOption::Flag {
                setting: ClientSetting::RoofsHidden,
                default: false,
            }),
            (Self::OpenRune240, 7) => Ok(ScriptOption::Volume(VarpId::new(168))),
            (Self::OpenRune240, 8) => Ok(ScriptOption::Volume(VarpId::new(169))),
            (Self::OpenRune240, 9) => Ok(ScriptOption::Volume(VarpId::new(
                if read(VarpId::new(5588)) == 1 {
                    5589
                } else {
                    872
                },
            ))),
            _ => Err(format!("unsupported game option {option}")),
        }
    }
    /// Load a bounded immutable script from the profile's script archive.
    /// # Errors
    /// Rejects missing files, corrupt footers, excessive counts and invalid operands.
    pub fn load_script<S: ReadOnlyBlobs>(self, cache: &Cache<S>, id: ScriptId) -> Result<Script> {
        let source = Source {
            index: IndexId(12),
            group: Some(GroupId(id.get())),
            file: Some(FileId(0)),
        };
        let files = cache.group_files(source.index, GroupId(id.get()))?;
        let bytes = files
            .iter()
            .find(|(id, _)| id.0 == 0)
            .ok_or_else(|| source.error("script", "missing file"))?;
        decode(&bytes.1, id, source)
    }
}
/// Decode the selected 240 format with exact byte boundaries.
/// # Errors
/// Returns source context on malformed programs; unsupported instructions retain identity for VM errors.
pub fn decode(bytes: &[u8], id: ScriptId, source: Source) -> Result<Script> {
    let invalid = || source.error("script", "invalid footer or allocation limit");
    if bytes.len() < 19 || bytes.len() > 1_048_576 {
        return Err(invalid());
    }
    let n = bytes.len();
    let footer = usize::from(u16::from_be_bytes([bytes[n - 2], bytes[n - 1]]));
    let start = n
        .checked_sub(footer + 18)
        .filter(|s| *s > 0)
        .ok_or_else(invalid)?;
    let mut reader = Reader::new(&bytes[start..n - 2], source);
    let count = usize::try_from(reader.u32()?).map_err(|_| invalid())?;
    let locals = [
        usize::from(reader.u16()?),
        usize::from(reader.u16()?),
        usize::from(reader.u16()?),
    ];
    let arguments = [
        usize::from(reader.u16()?),
        usize::from(reader.u16()?),
        usize::from(reader.u16()?),
    ];
    if count > 65_536
        || locals.iter().any(|v| *v > 4096)
        || arguments.iter().zip(locals).any(|(a, l)| *a > l)
    {
        return Err(invalid());
    }
    let switch_count = reader.u8()?;
    let mut switches = Vec::new();
    let mut total_cases = 0;
    for _ in 0..switch_count {
        let cases = usize::from(reader.u16()?);
        total_cases += cases;
        if total_cases > 16_384 {
            return Err(invalid());
        }
        let mut table = BTreeMap::new();
        for _ in 0..cases {
            table.insert(reader.u32()?.cast_signed(), reader.u32()?.cast_signed());
        }
        switches.push(table);
    }
    reader.finish()?;
    let mut reader = Reader::new(&bytes[..start], source);
    let name = reader.string_value()?;
    let mut instructions = Vec::with_capacity(count);
    while reader.remaining() > 0 {
        if instructions.len() >= count {
            return Err(invalid());
        }
        let value = reader.u16()?;
        let mut instruction = Instruction {
            array_default: None,
            opcode: super::script_opcodes::opcode(value),
            integer: 0,
            text: None,
            long: 0,
        };
        match value {
            3 => instruction.text = Some(reader.string_value()?),
            44 => {
                let raw = reader.u32()?;
                instruction.integer = (raw >> 16).cast_signed();
                instruction.array_default = Some(match raw & 65535 {
                    115 => crate::script::ArrayInitialValue::Text,
                    105 | 49 => crate::script::ArrayInitialValue::Integer(0),
                    _ => crate::script::ArrayInitialValue::Integer(-1),
                });
            }
            61 => {
                let b = reader.take(8)?;
                instruction.long = i64::from_be_bytes(b.try_into().map_err(|_| invalid())?);
            }
            21 | 38 | 39 | 62 | 63 => instruction.integer = i32::from(reader.u8()?),
            0..=99 => instruction.integer = reader.u32()?.cast_signed(),
            _ => instruction.integer = i32::from(reader.u8()?),
        }
        instructions.push(instruction);
    }
    if instructions.len() != count {
        return Err(invalid());
    }
    Ok(Script {
        id,
        name,
        locals,
        arguments,
        instructions,
        switches,
    })
}

impl CacheProfile {
    /// # Errors
    /// Rejects unsupported negative item identities.
    pub fn script_item(
        self,
        n: i32,
    ) -> std::result::Result<Option<rustclient_model::ItemDefinitionId>, String> {
        if n == -1 {
            Ok(None)
        } else {
            u32::try_from(n)
                .map(|id| Some(rustclient_model::ItemDefinitionId::new(id)))
                .map_err(|_| "negative script item identity".into())
        }
    }
    /// Selected script key identities: control, shift and alt.
    /// # Errors
    /// Rejects keys not yet supplied by the platform input adapter.
    pub fn script_modifier(self, key: i32) -> std::result::Result<usize, String> {
        match key {
            82 => Ok(0),
            81 => Ok(1),
            86 => Ok(2),
            _ => Err(format!("unsupported script key {key}")),
        }
    }
    /// Decode the component operand used by this profile's script language.
    /// # Errors
    /// Rejects absent addresses.
    pub fn script_widget(self, n: i32) -> std::result::Result<rustclient_model::WidgetId, String> {
        let n = n.cast_unsigned();
        if n >> 16 == 65535 || n & 65535 == 65535 {
            return Err("absent script widget address".into());
        }
        Ok(rustclient_model::WidgetId::new(
            rustclient_model::InterfaceId::new(n >> 16),
            n & 65535,
        ))
    }
    #[must_use]
    pub fn script_widget_number(self, id: rustclient_model::WidgetId) -> i32 {
        ((id.interface.get() << 16) | id.component).cast_signed()
    }
    #[must_use]
    pub fn listener_argument(
        self,
        n: i32,
        widget: Option<rustclient_model::WidgetAddress>,
        operation: i32,
    ) -> i32 {
        match n {
            -2_147_483_645 => widget.map_or(-1, |w| self.script_widget_number(w.widget)),
            -2_147_483_643 => widget.and_then(|w| w.child).map_or(-1, u32::cast_signed),
            -2_147_483_644 => operation,
            -2_147_483_647 | -2_147_483_646 => 0,
            -2_147_483_642 | -2_147_483_641 => -1,
            -2_147_483_640 | -2_147_483_639 => 0,
            n => n,
        }
    }
}

impl CacheProfile {
    /// 240 `BufferProvider`'s `MOVECOORD` packs the offset with OR, then adds the base.
    #[must_use]
    pub const fn script_move_coord(self, base: i32, x: i32, plane: i32, y: i32) -> i32 {
        base.wrapping_add((plane << 28) | (x << 14) | y)
    }
    /// Selected cache helper 626 calls `CAMERA_SET_ZOOM_LIMITS` (605) with its
    /// authored defaults. Run before the first gameframe so absent saved varcs
    /// cannot be mistaken for a valid zoom range by its resize script.
    #[must_use]
    pub const fn session_setup_script(self) -> ScriptId {
        ScriptId::new(626)
    }
    /// Java short narrowing and exponential script FOV units.
    #[must_use]
    #[expect(
        clippy::cast_possible_truncation,
        clippy::cast_precision_loss,
        reason = "reference Java short narrowing and FOV conversion"
    )]
    pub fn script_viewport_factor(self, value: i32, exponential: bool, fallback: u16) -> u16 {
        let value = if exponential {
            f64::from((value as f32) / 256. + 7.).exp2() as i32
        } else {
            value
        };
        let value = (value as i16).max(0).cast_unsigned();
        if value == 0 { fallback } else { value }
    }
    /// Inverse of the script's exponential FOV units.
    #[must_use]
    #[expect(
        clippy::cast_possible_truncation,
        reason = "reference truncates logarithmic FOV conversion"
    )]
    pub fn script_viewport_units(self, factor: u16) -> i32 {
        ((f64::from(factor).log2() - 7.) * 256.) as i32
    }
}

#[derive(Debug, Clone, Copy)]
pub enum ScriptValueType {
    Integer,
    Long,
    Text,
}
impl CacheProfile {
    /// # Errors
    /// Rejects unsupported script base-value types; absence consumes no stack value.
    pub fn script_value_type(
        self,
        value: i32,
    ) -> std::result::Result<Option<ScriptValueType>, String> {
        match value {
            -1 => Ok(None),
            0 => Ok(Some(ScriptValueType::Integer)),
            1 => Ok(Some(ScriptValueType::Long)),
            2 => Ok(Some(ScriptValueType::Text)),
            _ => Err(format!("unsupported script value type {value}")),
        }
    }
}

impl CacheProfile {
    /// The profile aliases this client variable to `GameState`'s existing selected-tab owner.
    #[must_use]
    pub const fn is_gameframe_tab_variable(self, id: u32) -> bool {
        id == 171
    }
}

impl CacheProfile {
    /// `OpenRune` script 7109 copies its master-volume varp into desktop device option 19;
    /// option 6 is the boolean sound-effect preference (reference default on) read by script 3961.
    /// Every other preference slot the cache scripts use is a stored integer.
    /// # Errors
    /// Rejects option ids outside the script-visible preference range.
    pub fn device_option(self, option: i32) -> std::result::Result<ScriptOption, String> {
        use rustclient_model::VarpId;
        match (self, option) {
            (Self::OpenRune240, 6) => Ok(ScriptOption::Brightness),
            (Self::OpenRune240, 19) => Ok(ScriptOption::Volume(VarpId::new(3796))),
            // Option 14 defaults to 25 in the 240 client's device preferences; the other ids
            // are the preference slots the cache scripts read and write (settings, graphics and
            // audio panels) and start at zero.
            (Self::OpenRune240, 14) => Ok(ScriptOption::Number {
                id: 14,
                default: 25,
            }),
            (Self::OpenRune240, 0..=63) => Ok(ScriptOption::Number {
                id: u8::try_from(option).map_err(|e| e.to_string())?,
                default: 0,
            }),
            _ => Err(format!("unsupported device option {option}")),
        }
    }
}

#[cfg(test)]
mod script_units {
    use super::*;
    #[test]
    fn coordinate_offsets_use_the_selected_or_pack_and_wrapping_addition() {
        let profile = CacheProfile::OpenRune240;
        assert_eq!(profile.script_move_coord(0, 1, 2, 3), 0x2000_4003);
        assert_eq!(profile.script_move_coord(0, 1, 0, -1), -1);
        assert_eq!(profile.script_move_coord(i32::MAX, 0, 0, 1), i32::MIN);
    }
}
