//! Revision-owned enum, parameter, inventory and item definitions for CS2.
use crate::{Cache, CacheProfile, FileId, GroupId, IndexId, Source, error::Result, reader::Reader};
use rustclient_model::{ItemDefinitionId, ScriptValue};
use rustclient_platform::ReadOnlyBlobs;
use std::collections::BTreeMap;
#[derive(Debug)]
pub struct ScriptEnum {
    pub input: u8,
    pub output: u8,
    pub default: ScriptValue,
    pub entries: BTreeMap<i32, ScriptValue>,
}
impl CacheProfile {
    /// # Errors
    /// Rejects missing or malformed definition files.
    pub fn script_definition<S: ReadOnlyBlobs>(
        self,
        cache: &Cache<S>,
        kind: DefinitionKind,
        id: u32,
    ) -> Result<Vec<u8>> {
        let group = match kind {
            DefinitionKind::Enum => 8,
            DefinitionKind::Struct => 34,
            DefinitionKind::Parameter => 11,
            DefinitionKind::Inventory => 5,
            DefinitionKind::Varbit => 14,
            DefinitionKind::Item => 10,
        };
        cache.config_file(
            group,
            id,
            self == CacheProfile::OpenRune240 && matches!(group, 5 | 10 | 11 | 14),
        )
    }
    /// # Errors
    /// Rejects unsupported enum fields and excessive entry counts.
    pub fn load_script_enum<S: ReadOnlyBlobs>(
        self,
        cache: &Cache<S>,
        id: u32,
    ) -> Result<ScriptEnum> {
        let bytes = self.script_definition(cache, DefinitionKind::Enum, id)?;
        let source = Source {
            index: IndexId(2),
            group: Some(GroupId(8)),
            file: Some(FileId(id)),
        };
        let mut r = Reader::new(&bytes, source);
        let mut out = ScriptEnum {
            input: 0,
            output: 0,
            default: ScriptValue::Int(0),
            entries: BTreeMap::new(),
        };
        loop {
            match r.u8()? {
                0 => break,
                1 => out.input = r.u8()?,
                2 => {
                    out.output = r.u8()?;
                    if out.output == 115 {
                        out.default = ScriptValue::Text("null".into());
                    }
                }
                3 => out.default = ScriptValue::Text(r.string_value()?),
                4 => out.default = ScriptValue::Int(r.u32()?.cast_signed()),
                kind @ (5 | 6) => {
                    let n = r.u16()?;
                    if n > 16_384 {
                        return Err(source.error("enum", "entry limit"));
                    }
                    for _ in 0..n {
                        let k = r.u32()?.cast_signed();
                        let v = if kind == 5 {
                            ScriptValue::Text(r.string_value()?)
                        } else {
                            ScriptValue::Int(r.u32()?.cast_signed())
                        };
                        out.entries.insert(k, v);
                    }
                }
                kind => return Err(source.error("enum", format!("unsupported field {kind}"))),
            }
        }
        r.finish()?;
        Ok(out)
    }
    /// # Errors
    /// Rejects malformed item definitions with exact source context.
    pub fn load_item<S: ReadOnlyBlobs>(
        self,
        cache: &Cache<S>,
        id: ItemDefinitionId,
    ) -> Result<crate::world::ItemDefinition> {
        let bytes = self.script_definition(cache, DefinitionKind::Item, id.get())?;
        super::definitions::item(
            &bytes,
            id,
            Source {
                index: IndexId(2),
                group: Some(GroupId(10)),
                file: Some(FileId(id.get())),
            },
        )
    }
}
#[derive(Debug, Clone, Copy, PartialEq, Eq, PartialOrd, Ord)]
pub enum DefinitionKind {
    Enum,
    Struct,
    Parameter,
    Inventory,
    Varbit,
    Item,
}
impl CacheProfile {
    /// # Errors
    /// Rejects malformed inventory definitions.
    pub fn inventory_capacity<S: ReadOnlyBlobs>(self, cache: &Cache<S>, id: u32) -> Result<u16> {
        let bytes = self.script_definition(cache, DefinitionKind::Inventory, id)?;
        let source = Source {
            index: IndexId(2),
            group: Some(GroupId(5)),
            file: Some(FileId(id)),
        };
        let mut r = Reader::new(&bytes, source);
        let mut capacity = 0;
        loop {
            match r.u8()? {
                0 => break,
                2 => capacity = r.u16()?,
                op => {
                    return Err(
                        source.error("inventory definition", format!("unsupported field {op}"))
                    );
                }
            }
        }
        r.finish()?;
        Ok(capacity)
    }
    /// # Errors
    /// Rejects invalid bit ranges.
    pub fn script_varbit<S: ReadOnlyBlobs>(
        self,
        cache: &Cache<S>,
        id: u32,
    ) -> Result<crate::world::VarbitDefinition> {
        let bytes = self.script_definition(cache, DefinitionKind::Varbit, id)?;
        super::animation::varbit(
            &bytes,
            Source {
                index: IndexId(2),
                group: Some(GroupId(14)),
                file: Some(FileId(id)),
            },
        )
    }
    /// Parameter defaults are authored cache data, never invented by a script consumer.
    /// # Errors
    /// Rejects unknown parameter fields.
    pub fn parameter_default<S: ReadOnlyBlobs>(
        self,
        cache: &Cache<S>,
        id: u32,
    ) -> Result<ScriptValue> {
        let bytes = self.script_definition(cache, DefinitionKind::Parameter, id)?;
        let source = Source {
            index: IndexId(2),
            group: Some(GroupId(11)),
            file: Some(FileId(id)),
        };
        decode_parameter_default(&bytes, source)
    }

    /// # Errors
    /// Rejects unsupported struct fields or parameter types.
    pub fn script_struct<S: ReadOnlyBlobs>(
        self,
        cache: &Cache<S>,
        id: u32,
    ) -> Result<BTreeMap<u32, ScriptValue>> {
        let bytes = self.script_definition(cache, DefinitionKind::Struct, id)?;
        let source = Source {
            index: IndexId(2),
            group: Some(GroupId(34)),
            file: Some(FileId(id)),
        };
        let mut r = Reader::new(&bytes, source);
        let mut values = BTreeMap::new();
        loop {
            match r.u8()? {
                0 => break,
                249 => {
                    for _ in 0..r.u8()? {
                        let kind = r.u8()?;
                        let key = r.u24()?;
                        let value = match kind {
                            0 => ScriptValue::Int(r.u32()?.cast_signed()),
                            1 => ScriptValue::Text(r.string_value()?),
                            _ => return Err(source.error("struct", "parameter type")),
                        };
                        values.insert(key, value);
                    }
                }
                op => return Err(source.error("struct", format!("unsupported field {op}"))),
            }
        }
        r.finish()?;
        Ok(values)
    }
}
impl CacheProfile {
    /// # Errors
    /// Rejects absent or malformed cache font masks/metrics.
    pub fn load_widget_font<S: ReadOnlyBlobs>(
        self,
        cache: &Cache<S>,
        id: rustclient_model::FontId,
    ) -> Result<crate::BitmapFont> {
        let source = Source::group(IndexId(13), GroupId(id.get()));
        let first = |index| {
            cache
                .group_files(IndexId(index), GroupId(id.get()))
                .and_then(|files| {
                    files
                        .into_iter()
                        .find(|(f, _)| f.0 == 0)
                        .map(|(_, b)| b)
                        .ok_or_else(|| source.error("font", "missing file"))
                })
        };
        let masks = first(8)?;
        let metrics = first(13)?;
        crate::BitmapFont::decode(&metrics, crate::decode_sprites(&masks, source)?, source)
    }
    /// # Errors
    /// Rejects absent/malformed model resources.
    pub fn load_widget_model<S: ReadOnlyBlobs>(
        self,
        cache: &Cache<S>,
        id: rustclient_model::ModelId,
    ) -> Result<crate::world::StaticModel> {
        let source = Source::group(IndexId(7), GroupId(id.get()));
        let files = cache.group_files(IndexId(7), GroupId(id.get()))?;
        let (_, bytes) = files
            .iter()
            .find(|(f, _)| f.0 == 0)
            .ok_or_else(|| source.error("model", "missing file"))?;
        super::models::decode(bytes, source)
    }
}

/// Decode the selected 240 parameter definition grammar.
/// # Errors
/// Rejects unsupported script variable types, fields and truncated defaults.
pub fn decode_parameter_default(bytes: &[u8], source: Source) -> Result<ScriptValue> {
    let mut reader = Reader::new(bytes, source);
    let mut kind = 0_u8;
    let mut number = 0;
    let mut text = String::new();
    let mut long = 0_i64;
    loop {
        match reader.u8()? {
            0 => break,
            1 => kind = if reader.u8()? == b's' { 36 } else { 0 },
            2 => number = reader.u32()?.cast_signed(),
            4 => {}
            5 => text = reader.string_value()?,
            7 => {
                long = i64::from_be_bytes(
                    reader
                        .take(8)?
                        .try_into()
                        .map_err(|_| source.error("parameter", "long default"))?,
                );
            }
            8 => kind = reader.u8()?,
            other => return Err(source.error("parameter", format!("unsupported field {other}"))),
        }
    }
    reader.finish()?;
    match kind {
        36 => Ok(ScriptValue::Text(text)),
        49 | 56 | 71 | 110 | 115 | 116 | 213 => Ok(ScriptValue::Long(long)),
        50 | 123..=208 | 210..=211 | 215..=255 => {
            Err(source.error("parameter", "unsupported script variable type"))
        }
        _ => Ok(ScriptValue::Int(number)),
    }
}
