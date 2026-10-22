//! Shared widget values used by immutable cache definitions and live overrides.
use crate::{FontId, ModelId, ScriptValue, SequenceId, SpriteId, WidgetId, WidgetPermissions};
use std::collections::BTreeMap;
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum SizeMode {
    Absolute,
    ParentMinus,
    Proportional,
    Aspect,
}
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum PositionMode {
    Start,
    Center,
    End,
    ProportionalStart,
    ProportionalCenter,
    ProportionalEnd,
}
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct WidgetLayout {
    pub offset: [i32; 2],
    pub size: [i32; 2],
    pub size_mode: [SizeMode; 2],
    pub position_mode: [PositionMode; 2],
}
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum WidgetContent {
    None,
    WorldViewport,
    Minimap,
    Compass,
    Other(u16),
}
#[derive(Debug, Clone, PartialEq, Eq)]
pub enum WidgetBody {
    Container {
        scroll_size: [u32; 2],
        no_click_through: bool,
    },
    Rectangle {
        color: u32,
        filled: bool,
        transparency: u8,
    },
    Text {
        font: Option<FontId>,
        text: String,
        line_height: u8,
        alignment: [u8; 2],
        shadowed: bool,
        color: u32,
    },
    Sprite {
        sprite: Option<SpriteId>,
        angle: u16,
        tiled: bool,
        transparency: u8,
        outline: u8,
        shadow: u32,
        flip: [bool; 2],
    },
    Model {
        model: Option<ModelId>,
        offset: [i32; 2],
        angles: [u16; 3],
        zoom: u16,
        sequence: Option<SequenceId>,
        orthographic: bool,
        sizing: [u16; 3],
    },
    Line {
        width: u8,
        color: u32,
        reversed: bool,
    },
}
#[derive(Debug, Clone, Copy, PartialEq, Eq, PartialOrd, Ord)]
pub enum WidgetListener {
    Key,
    Resize,
    SubChange,
    Misc,
    Chat,
    Friend,
    Clan,
    DialogAbort,
    Stock,
    ClanSettings,
    ClanChannel,
    Load,
    MouseOver,
    MouseLeave,
    TargetLeave,
    TargetEnter,
    Variable,
    Inventory,
    Stat,
    Timer,
    Operation,
    MouseRepeat,
    Click,
    ClickRepeat,
    Release,
    Hold,
    Drag,
    DragComplete,
    Scroll,
}
#[derive(Debug, Clone, PartialEq, Eq)]
pub struct WidgetDefinition {
    pub id: WidgetId,
    pub parent: Option<WidgetId>,
    pub content: WidgetContent,
    pub layout: WidgetLayout,
    pub hidden: bool,
    pub body: WidgetBody,
    pub permissions: WidgetPermissions,
    pub data_text: String,
    pub actions: Vec<String>,
    pub drag_zone: u8,
    pub drag_threshold: u8,
    pub scrollbar: bool,
    pub target_verb: String,
    pub listeners: BTreeMap<WidgetListener, Vec<ScriptValue>>,
    pub variable_triggers: Vec<i32>,
    pub inventory_triggers: Vec<i32>,
    pub stat_triggers: Vec<i32>,
}

#[derive(Debug, Clone, Copy, PartialEq, Eq, PartialOrd, Ord, Hash)]
pub struct WidgetAddress {
    pub widget: WidgetId,
    pub child: Option<u32>,
}
impl WidgetAddress {
    #[must_use]
    pub const fn component(widget: WidgetId) -> Self {
        Self {
            widget,
            child: None,
        }
    }
}
/// Only mutable overrides; immutable defaults remain in the cache definition.
#[derive(Debug, Clone, PartialEq, Eq, Default)]
pub struct WidgetRuntime {
    pub target_priority: Option<crate::WidgetOperation>,
    pub text: Option<String>,
    pub parent_child: Option<u32>,
    pub parameters: BTreeMap<u32, ScriptValue>,
    pub hidden: Option<bool>,
    pub scroll: Option<[u32; 2]>,
    pub layout: Option<WidgetLayout>,
    pub body: Option<WidgetBody>,
    pub actions: Option<Vec<String>>,
    pub data_text: Option<String>,
    pub listeners: BTreeMap<WidgetListener, Option<Vec<ScriptValue>>>,
    pub triggers: BTreeMap<WidgetListener, Vec<i32>>,
    pub item: Option<(crate::ItemDefinitionId, u32)>,
}
