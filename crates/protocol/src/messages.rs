//! Shared decoded updates and outbound intents. Byte layouts remain profile-owned.
use crate::openrune_240::{
    loc_anim::LocAnimUpdate, npc_info::NpcInfoUpdate, player_info::PlayerInfoUpdate,
};
use rustclient_model::{WorldAreaId, WorldTile};

/// Resolves immutable cache definitions only; the adapter never reads live variable values.
pub type VarbitLookup = Box<
    dyn FnMut(rustclient_model::VarbitId) -> crate::Result<rustclient_model::VarpBitRange> + Send,
>;

/// Ordered decoded output for one ordinary packet or one complete packet group.
#[derive(Debug)]
pub struct PacketBatch {
    /// Includes the group marker, matching the stock packet-loop counter.
    pub packets: usize,
    pub wire_bytes: usize,
    pub updates: Vec<ServerUpdate>,
}
/// Source-neutral initial placement decoded from the login rebuild.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct Bootstrap {
    pub position: WorldTile,
    pub map_anchor: WorldTile,
    pub area: Option<WorldAreaId>,
}
/// A message entered in the chatbox: a public message or a `::` command.
#[derive(Debug, Clone, PartialEq, Eq)]
pub enum ChatIntent {
    /// Public message with parsed style and Huffman text from the selected cache codec.
    Public {
        chat_type: u8,
        message: crate::PublicChatMessage,
    },
    /// Developer/console command without its leading `::`.
    Command(String),
}

/// Narrow outbound semantic intents supported by this profile.
#[derive(Debug, Clone)]
pub enum ClientIntent {
    WindowStatus {
        mode: rustclient_model::WindowMode,
        width: u16,
        height: u16,
    },
    KeepAlive,
    /// Normal world-map selection. The server decides its effect and permissions.
    WorldMapClick(WorldTile),
    PingReply {
        first: i32,
        second: i32,
        fps: u32,
    },
    /// User-selected destination. Confirmed player position does not change.
    Walk {
        destination: WorldTile,
        invert_run: bool,
    },
    MinimapWalk {
        destination: WorldTile,
        invert_run: bool,
        click: crate::MinimapClick,
    },
    /// The selected map has finished loading.
    MapBuildComplete,
    WidgetAction(rustclient_model::WidgetAction),
    CloseModal,
    /// Chat typed in the main chatbox. `command` carries text after `::`.
    Chat(ChatIntent),
    /// A script prompt's answer (`RESUME_P_*`).
    Resume(crate::ResumeIntent),
    NpcAction {
        index: rustclient_model::NpcIndex,
        slot: rustclient_model::ActionSlot,
        control: bool,
    },
    LocationAction {
        tile: WorldTile,
        object: rustclient_model::ObjectDefinitionId,
        slot: rustclient_model::ActionSlot,
        control: bool,
    },
    /// A ground option (`OPOBJ1..5`) on an item lying on `tile`.
    GroundItemAction {
        tile: WorldTile,
        item: rustclient_model::ItemDefinitionId,
        slot: rustclient_model::ActionSlot,
        control: bool,
    },
    /// "Examine" on an NPC, object or ground item (`OPNPC6`, `OPLOC6`, `OPOBJ6`).
    Examine(ExamineTarget),
    /// A widget dropped on another (`IF_BUTTOND`).
    WidgetDrag(rustclient_model::WidgetDragDrop),
    /// An option on another player (`OPPLAYER1..8`).
    PlayerAction {
        index: rustclient_model::PlayerIndex,
        slot: rustclient_model::PlayerOpSlot,
        control: bool,
    },
}
/// What an Examine names: the server answers with the definition's examine text.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum ExamineTarget {
    Npc(rustclient_model::NpcDefinitionId),
    Location(rustclient_model::ObjectDefinitionId),
    GroundItem {
        tile: WorldTile,
        item: rustclient_model::ItemDefinitionId,
    },
}
/// Profile-decoded updates, without exposing wire IDs to application semantics.
#[derive(Debug, Clone, PartialEq, Eq)]
pub enum ServerUpdate {
    GameMessage(rustclient_model::GameMessage),
    ChatFilters([Option<u8>; 3]),
    Interface(rustclient_model::InterfaceUpdate),
    Script(rustclient_model::ScriptRequest),
    PlayerStatus(rustclient_model::PlayerStatusUpdate),
    Inventory(rustclient_model::InventoryUpdate),
    Variable(VariableUpdate),
    InitialPlacement(Bootstrap),
    /// New ordinary map anchor; the profile carries no player position here.
    RebuildMap {
        map_anchor: rustclient_model::MapAnchor,
    },
    /// Repeated absolute window selection; unchanged values must not rebuild the scene.
    SetMapAnchor(rustclient_model::MapAnchor),
    PlayerInfo(PlayerInfoUpdate),
    NpcInfo(NpcInfoUpdate),
    MapFlag(Option<rustclient_model::MapDestination>),
    ResetActorAnimations,
    LocationAnim(LocAnimUpdate),
    Location(crate::openrune_240::zone::LocationUpdate),
    LocationZoneReset(crate::openrune_240::zone::ZoneOrigin),
    /// A ground item ("obj") change, from a zone body or an `OBJ_*_SPECIFIC` packet.
    GroundItem(rustclient_model::GroundItemUpdate),
    Tick,
    LoggedOut,
    Control(crate::SessionControl),
    Deferred,
}
/// Server variable values, defaulting to zero on reset. Profile bytes stay here.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum VariableUpdate {
    SetBits {
        range: rustclient_model::VarpBitRange,
        value: i32,
    },
    Set {
        id: rustclient_model::VarpId,
        value: i32,
    },
    Reset,
    Sync,
}
