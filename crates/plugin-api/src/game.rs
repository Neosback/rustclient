//! Immutable gameplay records delivered to plugins (API 0.2).
//!
//! Every type here is a projection built by the host from authoritative state. Plugins hold
//! copies; none of these values can mutate the client, and none exposes an internal struct,
//! protocol packet or renderer handle. Names follow the `RuneLite` API where a counterpart
//! exists so ported plugin code reads naturally.

use std::collections::BTreeMap;
use std::sync::Arc;

/// A world tile coordinate.
#[derive(Debug, Clone, Copy, PartialEq, Eq, PartialOrd, Ord, Hash, Default)]
pub struct WorldPoint {
    /// World tile x.
    pub x: i32,
    /// World tile y.
    pub y: i32,
    /// World plane, 0..=3.
    pub plane: u8,
}

impl WorldPoint {
    /// Construct a point.
    #[must_use]
    pub const fn new(x: i32, y: i32, plane: u8) -> Self {
        Self { x, y, plane }
    }

    /// Chebyshev distance in tiles, or `None` across planes.
    #[must_use]
    pub fn distance_to(self, other: Self) -> Option<u32> {
        (self.plane == other.plane).then(|| self.x.abs_diff(other.x).max(self.y.abs_diff(other.y)))
    }
}

/// An integer rectangle in canvas pixels.
#[derive(Debug, Clone, Copy, PartialEq, Eq, Default)]
pub struct Rect {
    /// Left edge.
    pub x: i32,
    /// Top edge.
    pub y: i32,
    /// Width in pixels.
    pub width: i32,
    /// Height in pixels.
    pub height: i32,
}

/// Reference to an actor in the current scene.
#[derive(Debug, Clone, Copy, PartialEq, Eq, PartialOrd, Ord, Hash)]
pub enum ActorRef {
    /// A player by scene index.
    Player(u16),
    /// An NPC by scene index.
    Npc(u16),
}

/// One hitsplat applied to an actor.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct Hitsplat {
    /// Hitsplat type id from the cache definition.
    pub kind: u16,
    /// Displayed amount.
    pub amount: i32,
    /// Client cycle the splat was applied.
    pub cycle: u32,
}

/// One health bar update applied to an actor.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct Healthbar {
    /// Health bar definition id.
    pub kind: u16,
    /// Current width in the bar's own units, or `None` when removed.
    pub amount: Option<u16>,
}

/// State shared by players and NPCs.
#[derive(Debug, Clone, PartialEq, Eq)]
pub struct ActorState {
    /// Scene index.
    pub index: u16,
    /// Current tile.
    pub world: WorldPoint,
    /// Orientation in 0..2048 angle units.
    pub orientation: u16,
    /// Action animation id, if one is playing.
    pub animation: Option<u32>,
    /// Movement/pose animation id, if resolved.
    pub pose: Option<u32>,
    /// Spot animation id, if one is playing and the client retains it.
    pub graphic: Option<u32>,
    /// Overhead chat text, if any.
    pub overhead_text: Option<String>,
    /// Actor this one is facing/interacting with, if the client retains it.
    pub interacting: Option<ActorRef>,
    /// Hitsplats applied during the last retained window, oldest first.
    pub hitsplats: Vec<Hitsplat>,
    /// Retained health bars.
    pub healthbars: Vec<Healthbar>,
}

/// One player in the loaded scene.
#[derive(Debug, Clone, PartialEq, Eq)]
pub struct PlayerSnapshot {
    /// Shared actor state.
    pub actor: ActorState,
    /// Display name, empty before the appearance block arrives.
    pub name: String,
    /// Combat level from the appearance block.
    pub combat_level: u8,
    /// Total level shown on the player (skill-level worlds), 0 otherwise.
    pub skill_level: u16,
    /// Whether this is the local player.
    pub is_local: bool,
    /// Whether the server marked this player's appearance hidden.
    pub hidden: bool,
}

/// One NPC in the loaded scene.
#[derive(Debug, Clone, PartialEq, Eq)]
pub struct NpcSnapshot {
    /// Shared actor state.
    pub actor: ActorState,
    /// Current composition (definition) id after transforms.
    pub id: u32,
    /// Composition name, if any.
    pub name: Option<String>,
    /// Combat level from the definition, 0 when not attackable.
    pub combat_level: u8,
    /// Footprint in tiles, `(width, height)`.
    pub size: (u8, u8),
    /// Menu actions from the definition, in slot order.
    pub actions: Vec<Option<String>>,
}

/// The named skills in client order.
#[derive(Debug, Clone, Copy, PartialEq, Eq, PartialOrd, Ord, Hash)]
pub enum Skill {
    /// Attack.
    Attack,
    /// Defence.
    Defence,
    /// Strength.
    Strength,
    /// Hitpoints.
    Hitpoints,
    /// Ranged.
    Ranged,
    /// Prayer.
    Prayer,
    /// Magic.
    Magic,
    /// Cooking.
    Cooking,
    /// Woodcutting.
    Woodcutting,
    /// Fletching.
    Fletching,
    /// Fishing.
    Fishing,
    /// Firemaking.
    Firemaking,
    /// Crafting.
    Crafting,
    /// Smithing.
    Smithing,
    /// Mining.
    Mining,
    /// Herblore.
    Herblore,
    /// Agility.
    Agility,
    /// Thieving.
    Thieving,
    /// Slayer.
    Slayer,
    /// Farming.
    Farming,
    /// Runecraft.
    Runecraft,
    /// Hunter.
    Hunter,
    /// Construction.
    Construction,
}

impl Skill {
    /// Every skill in client index order.
    pub const ALL: [Self; 23] = [
        Self::Attack,
        Self::Defence,
        Self::Strength,
        Self::Hitpoints,
        Self::Ranged,
        Self::Prayer,
        Self::Magic,
        Self::Cooking,
        Self::Woodcutting,
        Self::Fletching,
        Self::Fishing,
        Self::Firemaking,
        Self::Crafting,
        Self::Smithing,
        Self::Mining,
        Self::Herblore,
        Self::Agility,
        Self::Thieving,
        Self::Slayer,
        Self::Farming,
        Self::Runecraft,
        Self::Hunter,
        Self::Construction,
    ];

    /// Client skill index.
    #[must_use]
    pub const fn index(self) -> usize {
        self as usize
    }

    /// Skill for a client index.
    #[must_use]
    pub fn from_index(index: usize) -> Option<Self> {
        Self::ALL.get(index).copied()
    }
}

/// Level and experience for one skill.
#[derive(Debug, Clone, Copy, PartialEq, Eq, Default)]
pub struct SkillLevel {
    /// Level without boosts.
    pub real: u8,
    /// Level including boosts and drains.
    pub boosted: u8,
    /// Experience points.
    pub experience: i32,
}

/// One inventory-like item stack.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct ItemStack {
    /// Item definition id.
    pub id: u32,
    /// Stack quantity.
    pub quantity: u32,
}

/// One loaded widget with resolved canvas bounds.
#[derive(Debug, Clone, PartialEq, Eq)]
pub struct WidgetSnapshot {
    /// Packed `group << 16 | child` id.
    pub id: u32,
    /// Index within a dynamic child array, or `-1` for a static component.
    pub child_index: i32,
    /// Resolved bounds on the canvas.
    pub bounds: Rect,
    /// Visible clip of the bounds.
    pub clip: Rect,
    /// Text after script/runtime overrides, if any.
    pub text: Option<String>,
    /// Sprite id, if the widget paints a sprite.
    pub sprite: Option<u32>,
    /// Item shown by the widget, `(id, quantity)`.
    pub item: Option<(u32, u32)>,
    /// Whether the widget or an ancestor is hidden.
    pub hidden: bool,
    /// Action strings, in slot order.
    pub actions: Vec<String>,
}

impl WidgetSnapshot {
    /// The interface group part of the packed id.
    #[must_use]
    pub const fn group(&self) -> u16 {
        (self.id >> 16) as u16
    }

    /// The component part of the packed id.
    #[must_use]
    pub const fn child(&self) -> u16 {
        (self.id & 0xFFFF) as u16
    }
}

/// How one varbit maps onto its varp.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct VarbitDef {
    /// Backing varp id.
    pub varp: u32,
    /// Least-significant bit.
    pub lsb: u8,
    /// Most-significant bit, inclusive.
    pub msb: u8,
}

impl VarbitDef {
    /// Extract this varbit from its varp value.
    #[must_use]
    pub const fn extract(self, varp_value: i32) -> i32 {
        let width = (self.msb - self.lsb + 1) as u32;
        let mask = if width >= 32 {
            u32::MAX
        } else {
            (1u32 << width) - 1
        };
        ((varp_value.cast_unsigned() >> self.lsb) & mask).cast_signed()
    }
}

/// Varp values plus the varbit layout needed to read them.
#[derive(Debug, Clone, Default)]
pub struct Vars {
    /// Current varp values; absent ids read as zero like the client.
    pub varps: BTreeMap<u32, i32>,
    /// Shared varbit layout table.
    pub varbits: Arc<BTreeMap<u32, VarbitDef>>,
}

impl Vars {
    /// Value of one varp.
    #[must_use]
    pub fn varp(&self, id: u32) -> i32 {
        self.varps.get(&id).copied().unwrap_or(0)
    }

    /// Value of one varbit, or `None` for an unknown varbit id.
    #[must_use]
    pub fn varbit(&self, id: u32) -> Option<i32> {
        let def = self.varbits.get(&id)?;
        Some(def.extract(self.varp(def.varp)))
    }
}

/// Chat message class, following `RuneLite`'s `ChatMessageType` where one exists.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum ChatKind {
    /// Game message.
    Game,
    /// Public chat.
    Public,
    /// Private message received.
    Private,
    /// Clan/channel chat.
    Channel,
    /// Anything else, by raw client type id.
    Other(u16),
}

/// One chat line.
#[derive(Debug, Clone, PartialEq, Eq)]
pub struct ChatLine {
    /// Monotonic message id within the session.
    pub id: u32,
    /// Message class.
    pub kind: ChatKind,
    /// Sender, empty for game messages.
    pub name: String,
    /// Message text.
    pub text: String,
}

/// Coarse client lifecycle, following `RuneLite`'s `GameState`.
#[derive(Debug, Clone, Copy, PartialEq, Eq, Default)]
pub enum GameStateKind {
    /// Title screen.
    #[default]
    LoginScreen,
    /// Credentials submitted.
    LoggingIn,
    /// Scene is loading.
    Loading,
    /// In game.
    LoggedIn,
    /// Connection lost; the client is reconnecting.
    ConnectionLost,
}

/// Camera state in client units.
#[derive(Debug, Clone, Copy, PartialEq, Default)]
pub struct CameraSnapshot {
    /// Camera x (east) in local units, as `RuneLite`'s `getCameraX`.
    pub x: i32,
    /// Camera y (height) in local units, as `RuneLite`'s `getCameraY`.
    pub y: i32,
    /// Camera z (north) in local units, as `RuneLite`'s `getCameraZ`.
    pub z: i32,
    /// Pitch in 0..2048 angle units.
    pub pitch: i32,
    /// Yaw in 0..2048 angle units.
    pub yaw: i32,
    /// Zoom scale as shown by the client.
    pub zoom: i32,
}

/// Client-level facts that are not part of authoritative game semantics.
#[derive(Debug, Clone, Copy, PartialEq, Default)]
pub struct ClientSnapshot {
    /// Lifecycle state.
    pub state: GameStateKind,
    /// Committed gameplay tick.
    pub tick: u64,
    /// Client cycle (20 ms units).
    pub cycle: u64,
    /// Measured frames per second.
    pub fps: u32,
    /// Canvas size in pixels.
    pub canvas: (u32, u32),
    /// Game viewport on the canvas.
    pub viewport: Rect,
    /// Whether the gameframe is resizable.
    pub resizable: bool,
    /// Whether the window has focus.
    pub focused: bool,
    /// Camera.
    pub camera: CameraSnapshot,
    /// Scene base tile of the loaded map.
    pub scene_base: (i32, i32),
    /// Local run energy in tenths of a percent, 0..=1000.
    pub run_energy: u16,
    /// Local weight in kilograms.
    pub weight: i16,
}

/// One consistent view of the committed game, built once per update and shared by all plugins.
///
/// Fields a plugin cannot read without the matching capability are still present here; the
/// host's mediated accessors enforce grants.
#[derive(Debug, Clone, Default)]
pub struct GameSnapshot {
    /// Client facts.
    pub client: ClientSnapshot,
    /// Local player's scene index, if a session is active.
    pub local_player: Option<u16>,
    /// Players in the scene, ordered by index.
    pub players: Vec<PlayerSnapshot>,
    /// NPCs in the scene, ordered by index.
    pub npcs: Vec<NpcSnapshot>,
    /// Skills in client index order.
    pub skills: Vec<SkillLevel>,
    /// Varps and varbits.
    pub vars: Vars,
    /// Inventory-like containers by id.
    pub containers: BTreeMap<u32, Vec<Option<ItemStack>>>,
    /// Interface groups currently mounted, ascending.
    pub widget_groups: Vec<u16>,
    /// Loaded widgets; shared between snapshots while the interface layout is unchanged.
    pub widgets: Arc<[WidgetSnapshot]>,
    /// Chat lines retained by the client, oldest first.
    pub chat: Vec<ChatLine>,
}

impl GameSnapshot {
    /// The local player, if present.
    #[must_use]
    pub fn local_player(&self) -> Option<&PlayerSnapshot> {
        let index = self.local_player?;
        self.player(index)
    }

    /// A player by scene index.
    #[must_use]
    pub fn player(&self, index: u16) -> Option<&PlayerSnapshot> {
        self.players
            .binary_search_by_key(&index, |player| player.actor.index)
            .ok()
            .map(|at| &self.players[at])
    }

    /// An NPC by scene index.
    #[must_use]
    pub fn npc(&self, index: u16) -> Option<&NpcSnapshot> {
        self.npcs
            .binary_search_by_key(&index, |npc| npc.actor.index)
            .ok()
            .map(|at| &self.npcs[at])
    }

    /// A widget by packed id.
    #[must_use]
    pub fn widget(&self, id: u32) -> Option<&WidgetSnapshot> {
        self.widgets.iter().find(|widget| widget.id == id)
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn varbit_extraction_masks_the_declared_bit_range() {
        let def = VarbitDef {
            varp: 5,
            lsb: 4,
            msb: 7,
        };
        assert_eq!(def.extract(0b1010_0101_0000), 0b0101);
        let whole = VarbitDef {
            varp: 5,
            lsb: 0,
            msb: 31,
        };
        assert_eq!(whole.extract(-1), -1);
    }

    #[test]
    fn vars_read_zero_for_missing_varps_and_none_for_unknown_varbits() {
        let mut vars = Vars::default();
        assert_eq!(vars.varp(9), 0);
        assert_eq!(vars.varbit(1), None);
        vars.varps.insert(9, 0b110);
        vars.varbits = Arc::new(BTreeMap::from([(
            1,
            VarbitDef {
                varp: 9,
                lsb: 1,
                msb: 2,
            },
        )]));
        assert_eq!(vars.varbit(1), Some(3));
    }

    #[test]
    fn skills_round_trip_through_their_client_index() {
        for skill in Skill::ALL {
            assert_eq!(Skill::from_index(skill.index()), Some(skill));
        }
        assert_eq!(Skill::from_index(23), None);
    }

    #[test]
    fn widget_ids_split_into_group_and_child() {
        let widget = WidgetSnapshot {
            id: 0x00A1_005D,
            child_index: -1,
            bounds: Rect::default(),
            clip: Rect::default(),
            text: None,
            sprite: None,
            item: None,
            hidden: false,
            actions: Vec::new(),
        };
        assert_eq!((widget.group(), widget.child()), (161, 93));
    }

    #[test]
    fn world_distance_is_chebyshev_within_a_plane() {
        let a = WorldPoint::new(10, 10, 0);
        assert_eq!(a.distance_to(WorldPoint::new(13, 11, 0)), Some(3));
        assert_eq!(a.distance_to(WorldPoint::new(10, 10, 1)), None);
    }
}
