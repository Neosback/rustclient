//! Exact 240 IF1 (`Widget.method7215`), including 32-bit model selectors.
use super::{
    BTreeMap, FontId, ModelId, PositionMode, Reader, Result, SequenceId, SizeMode, Source,
    SpriteId, WidgetBody, WidgetContent, WidgetDefinition, WidgetId, WidgetLayout,
    WidgetPermissions, optional,
};
use crate::widget::{LegacyButton, LegacyPresentation};

fn model(r: &mut Reader<'_>) -> Result<Option<ModelId>> {
    Ok(match r.u32()? {
        u32::MAX => None,
        id => Some(ModelId::new(id)),
    })
}
fn empty() -> WidgetBody {
    WidgetBody::Text {
        font: None,
        text: String::new(),
        line_height: 0,
        alignment: [0; 2],
        shadowed: false,
        color: 0,
    }
}

/// Decode both the ordinary widget and its authored conditional/tooltip metadata.
/// # Errors
/// Rejects unknown selectors, excessive arrays, truncation and trailing bytes with source context.
#[expect(
    clippy::too_many_lines,
    reason = "source-ordered legacy component grammar"
)]
pub fn decode_legacy(
    bytes: &[u8],
    id: WidgetId,
    source: Source,
) -> Result<(WidgetDefinition, LegacyPresentation)> {
    if bytes.len() > 1_048_576 {
        return Err(source.error("IF1", "component byte limit"));
    }
    let mut r = Reader::new(bytes, source);
    let kind = r.u8()?;
    if kind > 8 {
        return Err(source.error("IF1", format!("unsupported component type {kind}")));
    }
    let button = match r.u8()? {
        0 => LegacyButton::None,
        1 => LegacyButton::Click,
        2 => LegacyButton::Target,
        3 => LegacyButton::Close,
        4 => LegacyButton::Toggle,
        5 => LegacyButton::Select,
        6 => LegacyButton::Continue,
        value => return Err(source.error("IF1", format!("invalid button {value}"))),
    };
    let content = match r.u16()? {
        0 => WidgetContent::None,
        1337 => WidgetContent::WorldViewport,
        1338 => WidgetContent::Minimap,
        1339 => WidgetContent::Compass,
        1400 => WidgetContent::WorldMap,
        1401 => WidgetContent::WorldMapOverview,
        value => WidgetContent::Other(value),
    };
    let offset = [i32::from(r.i16()?), i32::from(r.i16()?)];
    let size = [i32::from(r.u16()?), i32::from(r.u16()?)];
    let transparency = r.u8()?;
    let parent = optional(r.u16()?).map(|part| WidgetId::new(id.interface, part));
    let hover_redirect = optional(r.u16()?).map(|part| WidgetId::new(id.interface, part));
    let count = r.u8()?;
    let comparisons = (0..count)
        .map(|_| Ok((r.u8()?, i32::from(r.u16()?))))
        .collect::<Result<Vec<_>>>()?;
    let count = r.u8()?;
    let mut expressions = Vec::new();
    let mut total = 0;
    for _ in 0..count {
        let length = usize::from(r.u16()?);
        total += length;
        if total > 65_536 {
            return Err(source.error("IF1", "expression word limit"));
        }
        let words = (0..length)
            .map(|_| r.u16().map(|v| if v == 65535 { -1 } else { i32::from(v) }))
            .collect::<Result<Vec<_>>>()?;
        expressions.push(words);
    }
    let mut hidden = false;
    let mut body = empty();
    let mut alternate = None;
    let mut hover_colors = [0; 2];
    let mut tooltip = None;
    if kind == 0 {
        let height = u32::from(r.u16()?);
        hidden = r.u8()? == 1;
        body = WidgetBody::Container {
            scroll_size: [0, height],
            no_click_through: false,
        };
    }
    if kind == 1 {
        r.u16()?;
        r.u8()?;
    }
    let filled = kind == 3 && r.u8()? == 1;
    let (mut font, mut alignment, mut line_height, mut shadowed) = (None, [0; 2], 0, false);
    if matches!(kind, 1 | 4) {
        alignment = [r.u8()?, r.u8()?];
        line_height = r.u8()?;
        font = optional(r.u16()?).map(FontId::new);
        shadowed = r.u8()? == 1;
    }
    let (mut text, mut text2) = (String::new(), String::new());
    if kind == 4 {
        text = r.string_value()?;
        text2 = r.string_value()?;
    }
    let color = if matches!(kind, 1 | 3 | 4) {
        r.u32()?
    } else {
        0
    };
    if matches!(kind, 3 | 4) {
        let alternate_color = r.u32()?;
        hover_colors = [r.u32()?, r.u32()?];
        if kind == 3 {
            body = WidgetBody::Rectangle {
                color,
                filled,
                transparency,
            };
            alternate = Some(WidgetBody::Rectangle {
                color: alternate_color,
                filled,
                transparency,
            });
        } else {
            body = WidgetBody::Text {
                font,
                text,
                line_height,
                alignment,
                shadowed,
                color,
            };
            alternate = Some(WidgetBody::Text {
                font,
                text: text2,
                line_height,
                alignment,
                shadowed,
                color: alternate_color,
            });
        }
    }
    if kind == 5 {
        let sprite = |id| (id != u32::MAX).then_some(SpriteId::new(id));
        body = WidgetBody::Sprite {
            sprite: sprite(r.u32()?),
            angle: 0,
            tiled: false,
            transparency: 0,
            outline: 0,
            shadow: 0,
            flip: [false; 2],
        };
        alternate = Some(WidgetBody::Sprite {
            sprite: sprite(r.u32()?),
            angle: 0,
            tiled: false,
            transparency: 0,
            outline: 0,
            shadow: 0,
            flip: [false; 2],
        });
    }
    if kind == 6 {
        let main = model(&mut r)?;
        let secondary = model(&mut r)?;
        let sequence = optional(r.u16()?).map(SequenceId::new);
        let secondary_sequence = optional(r.u16()?).map(SequenceId::new);
        let zoom = r.u16()?;
        let angles = [r.u16()?, r.u16()?, 0];
        body = WidgetBody::Model {
            model: main,
            offset: [0; 2],
            angles,
            zoom,
            sequence,
            orthographic: false,
            sizing: [0; 3],
        };
        alternate = Some(WidgetBody::Model {
            model: secondary,
            offset: [0; 2],
            angles,
            zoom,
            sequence: secondary_sequence,
            orthographic: false,
            sizing: [0; 3],
        });
    }
    if kind == 8 {
        tooltip = Some(r.string_value()?);
    }
    let mut permissions = WidgetPermissions::default();
    let mut target_verb = String::new();
    let mut target_text = String::new();
    if button == LegacyButton::Target {
        target_verb = r.string_value()?;
        target_text = r.string_value()?;
        permissions.target_mask =
            u8::try_from(r.u16()? & 63).map_err(|_| source.error("IF1", "target mask"))?;
    }
    let mut button_text = String::new();
    if matches!(
        button,
        LegacyButton::Click | LegacyButton::Toggle | LegacyButton::Select | LegacyButton::Continue
    ) {
        button_text = r.string_value()?;
        if button_text.is_empty() {
            button_text = match button {
                LegacyButton::Click => "Ok",
                LegacyButton::Continue => "Continue",
                _ => "Select",
            }
            .into();
        }
    }
    permissions.crm_target = matches!(
        button,
        LegacyButton::Click | LegacyButton::Toggle | LegacyButton::Select
    );
    permissions.continue_enabled = button == LegacyButton::Continue;
    r.finish()?;
    let presentation = LegacyPresentation {
        button,
        button_text,
        target_text,
        hover_redirect,
        comparisons,
        expressions,
        alternate,
        hover_colors,
        tooltip,
    };
    Ok((
        WidgetDefinition {
            id,
            parent,
            content,
            layout: WidgetLayout {
                offset,
                size,
                size_mode: [SizeMode::Absolute; 2],
                position_mode: [PositionMode::Start; 2],
            },
            hidden,
            body,
            permissions,
            data_text: String::new(),
            actions: Vec::new(),
            drag_zone: 0,
            drag_threshold: 0,
            scrollbar: false,
            target_verb,
            listeners: BTreeMap::new(),
            variable_triggers: Vec::new(),
            inventory_triggers: Vec::new(),
            stat_triggers: Vec::new(),
        },
        presentation,
    ))
}
