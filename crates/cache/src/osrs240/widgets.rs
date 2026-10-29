//! IF3 layout verified against the pinned client and selected LIVE cache.
use crate::{
    Cache, CacheProfile, GroupId, IndexId, Source,
    error::Result,
    reader::Reader,
    widget::{
        InterfaceDefinition, PositionMode, SizeMode, WidgetBody, WidgetContent, WidgetDefinition,
        WidgetLayout, WidgetListener,
    },
};
use rustclient_model::{
    FontId, InterfaceId, ModelId, ScriptValue, SequenceId, SpriteId, WidgetId, WidgetPermissions,
};
use rustclient_platform::ReadOnlyBlobs;
use sha2::{Digest, Sha256};
use std::collections::BTreeMap;
mod legacy;
pub use legacy::decode_legacy;
impl CacheProfile {
    /// Decode one interface's immutable component graph from the selected cache.
    /// # Errors
    /// Returns exact source context for missing, malformed or unsupported widgets.
    pub fn load_interface<S: ReadOnlyBlobs>(
        self,
        cache: &Cache<S>,
        id: InterfaceId,
    ) -> Result<InterfaceDefinition> {
        let source = Source::group(IndexId(3), GroupId(id.get()));
        if id.get() >= 65535 {
            return Err(source.error("interface", "group address exceeds profile range"));
        }
        let files = cache.group_files(IndexId(3), GroupId(id.get()))?;
        if files.len() > 8192 {
            return Err(source.error("interface", "component count limit"));
        }
        let mut widgets = BTreeMap::new();
        let mut reports = Vec::new();
        let mut legacy = BTreeMap::new();
        for (file, bytes) in files {
            let source = Source {
                file: Some(file),
                ..source
            };
            if file.0 >= 65535 {
                return Err(source.error("interface", "component address exceeds profile range"));
            }
            let address = WidgetId::new(id, file.0);
            let widget = if bytes.first().copied() == Some(255) {
                decode(&bytes, address, source)?
            } else {
                let (widget, presentation) = decode_legacy(&bytes, address, source)?;
                legacy.insert(address, presentation);
                widget
            };
            reports.push(crate::AssetReport {
                name: format!("interface {}:{}", id.get(), file.0),
                source,
                bytes: bytes.len(),
                sha256: format!("{:x}", Sha256::digest(&bytes)),
            });
            widgets.insert(widget.id, widget);
        }
        let mut definition =
            InterfaceDefinition::from_widgets(id, widgets.into_values().collect())?;
        definition.reports = reports;
        definition.with_legacy(legacy)
    }
}
fn optional(value: u16) -> Option<u32> {
    (value != 65535).then(|| u32::from(value))
}
fn size_mode(value: i8, source: Source) -> Result<SizeMode> {
    match value {
        0 => Ok(SizeMode::Absolute),
        1 => Ok(SizeMode::ParentMinus),
        2 => Ok(SizeMode::Proportional),
        4 => Ok(SizeMode::Aspect),
        _ => Err(source.error("widget", "unsupported sizing mode")),
    }
}
fn position_mode(value: i8, source: Source) -> Result<PositionMode> {
    match value {
        0 => Ok(PositionMode::Start),
        1 => Ok(PositionMode::Center),
        2 => Ok(PositionMode::End),
        3 => Ok(PositionMode::ProportionalStart),
        4 => Ok(PositionMode::ProportionalCenter),
        5 => Ok(PositionMode::ProportionalEnd),
        _ => Err(source.error("widget", "unsupported position mode")),
    }
}
/// Decode one revision-owned component, retaining listeners for later VM execution.
/// # Errors
/// Rejects unknown types/modes/listener values, truncation and trailing bytes.
pub fn decode(bytes: &[u8], id: WidgetId, source: Source) -> Result<WidgetDefinition> {
    if bytes.len() > 1_048_576 {
        return Err(source.error("widget", "component byte limit"));
    }
    let mut r = Reader::new(bytes, source);
    if r.u8()? != 255 {
        return decode_legacy(bytes, id, source).map(|(widget, _)| widget);
    }
    let kind = r.u8()?;
    let content = match r.u16()? {
        0 => WidgetContent::None,
        1337 => WidgetContent::WorldViewport,
        1338 => WidgetContent::Minimap,
        1339 => WidgetContent::Compass,
        1400 => WidgetContent::WorldMap,
        1401 => WidgetContent::WorldMapOverview,
        n => WidgetContent::Other(n),
    };
    let offset = [i32::from(r.i16()?), i32::from(r.i16()?)];
    let size = [
        i32::from(r.u16()?),
        if kind == 9 {
            i32::from(r.i16()?)
        } else {
            i32::from(r.u16()?)
        },
    ];
    let raw_modes = [r.i8()?, r.i8()?];
    let size_mode = [
        size_mode(raw_modes[0], source)?,
        size_mode(raw_modes[1], source)?,
    ];
    let position_mode = [
        position_mode(r.i8()?, source)?,
        position_mode(r.i8()?, source)?,
    ];
    let parent = optional(r.u16()?).map(|component| WidgetId::new(id.interface, component));
    let hidden = r.u8()? == 1;
    let body = body(&mut r, kind, raw_modes, source)?;
    let flags = r.u24()?;
    let permissions = WidgetPermissions {
        enabled_operations: (flags >> 1) & 1023,
        continue_enabled: flags & 1 != 0,
        target_mask: ((flags >> 11) & 63).to_le_bytes()[0],
        drag_depth: ((flags >> 17) & 7).to_le_bytes()[0],
        accepts_drag: flags & (1 << 20) != 0,
        accepts_target: flags & (1 << 21) != 0,
        crm_target: flags & (1 << 22) != 0,
        script_trigger: flags & (1 << 23) != 0,
    };
    let data_text = r.string_value()?;
    let count = r.u8()?;
    if count > 32 {
        return Err(source.error("widget", "action count limit"));
    }
    let actions = (0..count)
        .map(|_| r.string_value())
        .collect::<Result<Vec<_>>>()?;
    let drag_zone = r.u8()?;
    let drag_threshold = r.u8()?;
    let scrollbar = r.u8()? == 1;
    let target_verb = r.string_value()?;
    let listeners = listeners(&mut r, source)?;
    let variable_triggers = triggers(&mut r)?;
    let inventory_triggers = triggers(&mut r)?;
    let stat_triggers = triggers(&mut r)?;
    r.finish()?;
    Ok(WidgetDefinition {
        id,
        parent,
        content,
        layout: WidgetLayout {
            offset,
            size,
            size_mode,
            position_mode,
        },
        hidden,
        body,
        permissions,
        data_text,
        actions,
        drag_zone,
        drag_threshold,
        scrollbar,
        target_verb,
        listeners,
        variable_triggers,
        inventory_triggers,
        stat_triggers,
    })
}
fn listeners(
    r: &mut Reader<'_>,
    source: Source,
) -> Result<BTreeMap<WidgetListener, Vec<ScriptValue>>> {
    let mut listeners = BTreeMap::new();
    for listener in [
        WidgetListener::Load,
        WidgetListener::MouseOver,
        WidgetListener::MouseLeave,
        WidgetListener::TargetLeave,
        WidgetListener::TargetEnter,
        WidgetListener::Variable,
        WidgetListener::Inventory,
        WidgetListener::Stat,
        WidgetListener::Timer,
        WidgetListener::Operation,
        WidgetListener::MouseRepeat,
        WidgetListener::Click,
        WidgetListener::ClickRepeat,
        WidgetListener::Release,
        WidgetListener::Hold,
        WidgetListener::Drag,
        WidgetListener::DragComplete,
        WidgetListener::Scroll,
    ] {
        let count = r.u8()?;
        if count == 0 {
            continue;
        }
        let mut args = Vec::with_capacity(usize::from(count));
        for _ in 0..count {
            args.push(match r.u8()? {
                0 => ScriptValue::Int(r.u32()?.cast_signed()),
                1 => ScriptValue::Text(r.string_value()?),
                _ => return Err(source.error("widget", "unknown listener argument type")),
            });
        }
        listeners.insert(listener, args);
    }
    Ok(listeners)
}
fn triggers(r: &mut Reader<'_>) -> Result<Vec<i32>> {
    let count = r.u8()?;
    (0..count).map(|_| r.u32().map(u32::cast_signed)).collect()
}
fn body(r: &mut Reader<'_>, kind: u8, modes: [i8; 2], source: Source) -> Result<WidgetBody> {
    Ok(match kind {
        0 => WidgetBody::Container {
            scroll_size: [u32::from(r.u16()?), u32::from(r.u16()?)],
            no_click_through: r.u8()? == 1,
        },
        3 => WidgetBody::Rectangle {
            color: r.u32()?,
            filled: r.u8()? == 1,
            transparency: r.u8()?,
        },
        4 => WidgetBody::Text {
            font: optional(r.u16()?).map(FontId::new),
            text: r.string_value()?,
            line_height: r.u8()?,
            alignment: [r.u8()?, r.u8()?],
            shadowed: r.u8()? == 1,
            color: r.u32()?,
        },
        5 => WidgetBody::Sprite {
            sprite: match r.u32()? {
                u32::MAX => None,
                id => Some(SpriteId::new(id)),
            },
            angle: r.u16()?,
            tiled: r.u8()? == 1,
            transparency: r.u8()?,
            outline: r.u8()?,
            shadow: r.u32()?,
            flip: {
                let v = r.u8()? == 1;
                let h = r.u8()? == 1;
                [h, v]
            },
        },
        6 => WidgetBody::Model {
            model: match r.u32()? {
                u32::MAX => None,
                id => Some(ModelId::new(id)),
            },
            offset: [i32::from(r.i16()?), i32::from(r.i16()?)],
            angles: [r.u16()?, r.u16()?, r.u16()?],
            zoom: r.u16()?,
            sequence: optional(r.u16()?).map(SequenceId::new),
            orthographic: r.u8()? == 1,
            sizing: [
                r.u16()?,
                if modes[0] != 0 { r.u16()? } else { 0 },
                if modes[1] != 0 { r.u16()? } else { 0 },
            ],
        },
        9 => WidgetBody::Line {
            width: r.u8()?,
            color: r.u32()?,
            reversed: r.u8()? == 1,
        },
        _ => return Err(source.error("widget", format!("unsupported IF3 component type {kind}"))),
    })
}

#[cfg(test)]
mod tests {
    use super::*;
    use crate::{GroupId, IndexId};
    use rustclient_model::InterfaceId;

    #[test]
    fn if3_sprite_decodes_vertical_then_horizontal_flip_into_horizontal_vertical_pair() {
        // IF3 header: 0xFF, type=5 (sprite), content=0, x=0, y=0, w=12, h=30,
        // w_mode=0, h_mode=0, x_mode=0, y_mode=0, parent=0xFFFF, hidden=0,
        // sprite_id=1906, angle=0, tiled=0, transparency=0, outline=0, shadow=0,
        // spriteFlipV=0, spriteFlipH=1, no ops/listeners.
        let mut bytes = vec![
            0xFF, 5, 0, 0, 0, 0, 0, 0, 0, 12, 0, 30, 0, 0, 0, 0, 0xFF, 0xFF, 0, 0, 0, 0x07, 0x72,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
        ];
        // flags(3), data_text(1), action_count(1), drag(3), target_verb(1), listeners(18), triggers(3)
        bytes.extend_from_slice(&[0; 30]);
        let def = decode(
            &bytes,
            WidgetId::new(InterfaceId::new(116), 64),
            Source::group(IndexId(3), GroupId(116)),
        )
        .unwrap();
        assert_eq!(
            def.body,
            WidgetBody::Sprite {
                sprite: Some(SpriteId::new(1906)),
                angle: 0,
                tiled: false,
                transparency: 0,
                outline: 0,
                shadow: 0,
                flip: [true, false],
            }
        );
    }
}
