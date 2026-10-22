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
impl CacheProfile {
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
            opcode: super::script_opcodes::opcode(value),
            integer: 0,
            text: None,
            long: 0,
        };
        match value {
            3 => instruction.text = Some(reader.string_value()?),
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
