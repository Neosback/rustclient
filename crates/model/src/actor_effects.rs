//! Server-authored visual effects on an actor: hitmarks, health bars and spot animations.
//!
//! These are the semantic facts of the player/NPC extended-info blocks `HITMARKS`, `HEADBARS`
//! and `SPOTANIM` (stock `Actor.addHitSplat`, `addHealthBar`, `updateSpotAnimation`). Lifetimes and
//! interpolation belong to `GameState`, which stamps them with the client cycle.
use crate::{PlayerIndex, SpotAnimationId};

/// One hitmark as written: `type`, `value`, `delay`, `limit` (four smarts).
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct Hitmark {
    /// Hitsplat type id.
    pub kind: u16,
    /// Displayed amount.
    pub amount: u16,
    /// Client cycles before the splat appears.
    pub delay: u16,
    /// Most splats of this class the stock client keeps at once (0 means unlimited).
    pub limit: u16,
}

/// How a health bar's fill moves: stock `HealthBarUpdate(cycle + start_time, start_fill,
/// end_fill, cycle_offset = end_time)`.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct HeadbarFill {
    /// Client cycles before the bar starts moving.
    pub start_time: u16,
    /// Client cycles the fill takes to move from `start_fill` to `end_fill`.
    pub end_time: u16,
    /// Fill at the start, in the bar definition's width units.
    pub start_fill: u8,
    /// Fill at the end, in the bar definition's width units.
    pub end_fill: u8,
}

/// A health bar update, or the removal of the bar of this type (`fill` is `None`).
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct Headbar {
    /// Health bar definition id.
    pub kind: u16,
    /// New fill movement; `None` removes every bar of this type (`end_time` 32767 on the wire).
    pub fill: Option<HeadbarFill>,
}

/// A spot animation assigned to one of the actor's slots.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct ActorSpotAnim {
    /// Slot, so several effects can play at once.
    pub slot: u8,
    /// Graphic, or `None` for 65535, which clears the slot.
    pub id: Option<SpotAnimationId>,
    /// Height above the actor's feet, in local units.
    pub height: u16,
    /// Client cycles before it starts.
    pub delay: u16,
    /// Whether the animation repeats.
    pub looping: bool,
}

/// Every effect block decoded for one actor in one update, in packet order.
#[derive(Debug, Clone, Default, PartialEq, Eq)]
pub struct ActorEffects {
    pub hitmarks: Vec<Hitmark>,
    pub headbars: Vec<Headbar>,
    pub spotanims: Vec<ActorSpotAnim>,
}
impl ActorEffects {
    /// Whether no block was present.
    #[must_use]
    pub fn is_empty(&self) -> bool {
        self.hitmarks.is_empty() && self.headbars.is_empty() && self.spotanims.is_empty()
    }
}

/// Effects for one visible player (local or remote).
#[derive(Debug, Clone, PartialEq, Eq)]
pub struct PlayerEffects {
    pub index: PlayerIndex,
    pub effects: ActorEffects,
}
