//! Source-neutral live interface changes and script values.
use crate::{InterfaceId, WidgetId};

#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum InterfaceMountKind {
    Modal,
    Overlay,
    Client,
}
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct InterfaceMount {
    pub parent: WidgetId,
    pub interface: InterfaceId,
    pub kind: InterfaceMountKind,
}
/// Definition or server-authored interaction permissions. Operation bits refer
/// to zero-based semantic action slots; packet masks are translated by adapters.
#[derive(Debug, Clone, Copy, PartialEq, Eq, Default)]
#[expect(
    clippy::struct_excessive_bools,
    reason = "independent authored interaction capabilities"
)]
pub struct WidgetPermissions {
    pub enabled_operations: u32,
    pub continue_enabled: bool,
    pub target_mask: u8,
    pub drag_depth: u8,
    pub accepts_drag: bool,
    pub accepts_target: bool,
    pub crm_target: bool,
    pub script_trigger: bool,
}
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct WidgetEventRange {
    pub widget: WidgetId,
    /// Inclusive dynamic child range; -1 denotes the base component.
    pub first: i32,
    pub last: i32,
    pub permissions: WidgetPermissions,
}
#[derive(Debug, Clone, PartialEq, Eq)]
pub enum WidgetProperty {
    Text(String),
    Hidden(bool),
    ScrollY(u32),
    Runtime {
        address: crate::WidgetAddress,
        value: Box<crate::WidgetRuntime>,
    },
}
#[derive(Debug, Clone, PartialEq, Eq)]
pub enum InterfaceUpdate {
    Setting {
        key: ClientSetting,
        value: bool,
    },
    CreateChild {
        parent: WidgetId,
        parent_child: Option<u32>,
        allow_sparse: bool,
        index: u32,
        body: crate::WidgetBody,
    },
    CloneChild {
        parent: WidgetId,
        source: u32,
        index: u32,
    },
    DeleteChild(crate::WidgetAddress),
    DeleteChildren(WidgetId),
    ClientVariable {
        id: u32,
        value: ScriptValue,
    },
    SetRoot(Option<InterfaceId>),
    Open(InterfaceMount),
    Close(WidgetId),
    Move {
        source: WidgetId,
        destination: WidgetId,
    },
    SetProperty {
        widget: WidgetId,
        property: WidgetProperty,
    },
    SetEvents(WidgetEventRange),
    Resync {
        root: Option<InterfaceId>,
        mounts: Vec<InterfaceMount>,
        events: Vec<WidgetEventRange>,
    },
}
#[derive(Debug, Clone, PartialEq, Eq)]
pub enum ScriptValue {
    Int(i32),
    Text(String),
    Long(i64),
    Integers(Vec<i32>),
    Strings(Vec<String>),
}

#[derive(Debug, Clone, PartialEq, Eq)]
pub struct ScriptRequest {
    pub id: crate::ScriptId,
    pub arguments: Vec<ScriptValue>,
}

/// Checked zero-based operation in a widget's server-controlled operation set.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct WidgetOperation(u8);
impl WidgetOperation {
    #[must_use]
    pub const fn new(index: u8) -> Option<Self> {
        if index < 32 { Some(Self(index)) } else { None }
    }
    #[must_use]
    pub const fn index(self) -> u8 {
        self.0
    }
}
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct WidgetAction {
    pub address: crate::WidgetAddress,
    pub operation: WidgetOperation,
    pub item: Option<crate::ItemDefinitionId>,
    pub interface_epoch: u64,
}

#[derive(Debug, Clone, Copy, PartialEq, Eq, PartialOrd, Ord)]
pub enum ClientSetting {
    ShowMouseOverText,
    ShowMouseCross,
    ShowLoadingMessages,
    ShiftClickDrop,
    TapToDrop,
    FollowerOpsLowPriority,
    MouseCamera,
}
impl ClientSetting {
    #[must_use]
    pub const fn tag(self) -> u8 {
        match self {
            Self::ShowMouseOverText => 0,
            Self::ShowMouseCross => 1,
            Self::ShowLoadingMessages => 2,
            Self::ShiftClickDrop => 3,
            Self::TapToDrop => 4,
            Self::FollowerOpsLowPriority => 5,
            Self::MouseCamera => 6,
        }
    }
}
