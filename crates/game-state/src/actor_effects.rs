//! Hitmarks, health bars and spot animations retained on one actor (SPEC-0022).
//!
//! The wire gives relative times; every entry is stamped with the client cycle it arrived on, so
//! lifetimes, fill interpolation and projection are functions of committed state and the
//! `ClientCycle`, never of wall time. Definition-driven display durations are fixed at the
//! reference defaults (deviations `HITSPLAT-DEFAULT-DURATION` and `HEADBAR-DEFAULT-DURATION`).
use rustclient_model::{ActorEffects, ClientCycle, SpotAnimationId};
use std::collections::BTreeMap;

/// `HitSplatDefinition` default `duration` (stock `classPW` field 70).
pub const HITMARK_DEFAULT_DURATION: u32 = 70;
/// `HealthBarDefinition.int5` default: how long a bar stays after its last update completes.
pub const HEADBAR_DEFAULT_DURATION: u32 = 70;
/// Most retained hitmarks per actor; the oldest is dropped first.
pub const MAX_HITMARKS: usize = 16;
/// Most distinct health bar types per actor.
pub const MAX_HEADBARS: usize = 16;
/// Most pending updates retained per health bar type.
pub const MAX_HEADBAR_UPDATES: usize = 8;
/// Spot animation slots per actor; larger slots are ignored.
pub const SPOT_ANIMATION_SLOTS: u8 = 16;

/// A hitmark stamped with its arrival cycle.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct AppliedHitmark {
    pub kind: u16,
    pub amount: u16,
    pub delay: u16,
    pub limit: u16,
    pub applied: ClientCycle,
}
impl AppliedHitmark {
    /// First cycle the splat is shown.
    #[must_use]
    pub fn shown_at(&self) -> ClientCycle {
        add(self.applied, u32::from(self.delay))
    }
    /// First cycle the splat is gone.
    #[must_use]
    pub fn expires_at(&self) -> ClientCycle {
        add(
            self.applied,
            u32::from(self.delay) + HITMARK_DEFAULT_DURATION,
        )
    }
}

/// One `HealthBarUpdate`: `cycle` is arrival plus start time; the fill moves over `duration`.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct HeadbarUpdate {
    pub cycle: ClientCycle,
    pub start_fill: u8,
    pub end_fill: u8,
    pub duration: u16,
}

/// The updates of one health bar type, oldest first.
#[derive(Debug, Clone, PartialEq, Eq)]
pub struct HeadbarTrack {
    pub kind: u16,
    pub updates: Vec<HeadbarUpdate>,
}
impl HeadbarTrack {
    /// Stock `HealthBarConfig.get`: drop updates superseded by a later one that has started, and
    /// return the current one while its display time has not run out.
    fn current(&mut self, now: ClientCycle) -> Option<HeadbarUpdate> {
        let started = self.updates.iter().take_while(|u| u.cycle <= now).count();
        if started == 0 {
            return None;
        }
        self.updates.drain(..started - 1);
        let update = self.updates[0];
        (add(
            update.cycle,
            HEADBAR_DEFAULT_DURATION + u32::from(update.duration),
        ) > now)
            .then_some(update)
    }
    /// The fill to draw at `now`, in the bar definition's width units (stock draw code with the
    /// default one-cycle step): linear from `start_fill` to `end_fill` over `duration`.
    #[must_use]
    pub fn fill_at(&self, now: ClientCycle) -> Option<u8> {
        let update = self.updates.iter().rev().find(|u| u.cycle <= now)?;
        let elapsed = now.get() - update.cycle.get();
        if elapsed >= u64::from(update.duration) {
            return Some(update.end_fill);
        }
        let (start, end) = (i64::from(update.start_fill), i64::from(update.end_fill));
        let moved =
            (end - start) * i64::try_from(elapsed).unwrap_or(0) / i64::from(update.duration);
        u8::try_from(start + moved).ok()
    }
}

/// A spot animation slot.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct AppliedSpotAnim {
    pub id: SpotAnimationId,
    pub height: u16,
    pub delay: u16,
    pub looping: bool,
    pub applied: ClientCycle,
}

/// Everything an actor currently shows, by effect class.
#[derive(Debug, Clone, Default, PartialEq, Eq)]
pub struct ActorEffectsState {
    hitmarks: Vec<AppliedHitmark>,
    headbars: Vec<HeadbarTrack>,
    spotanims: BTreeMap<u8, AppliedSpotAnim>,
}
impl ActorEffectsState {
    /// Retained hitmarks, oldest first (including ones still waiting for their delay).
    #[must_use]
    pub fn hitmarks(&self) -> &[AppliedHitmark] {
        &self.hitmarks
    }
    /// Health bar tracks, in arrival order of the type.
    #[must_use]
    pub fn headbars(&self) -> &[HeadbarTrack] {
        &self.headbars
    }
    /// Spot animation slots.
    pub fn spotanims(&self) -> impl Iterator<Item = (u8, &AppliedSpotAnim)> {
        self.spotanims.iter().map(|(slot, anim)| (*slot, anim))
    }
    /// Whether nothing is retained.
    #[must_use]
    pub fn is_empty(&self) -> bool {
        self.hitmarks.is_empty() && self.headbars.is_empty() && self.spotanims.is_empty()
    }

    /// Apply the blocks of one update at `now`; returns whether anything changed.
    pub(crate) fn apply(&mut self, effects: &ActorEffects, now: ClientCycle) -> bool {
        let before = self.clone();
        for hit in &effects.hitmarks {
            if self.hitmarks.len() == MAX_HITMARKS {
                self.hitmarks.remove(0);
            }
            self.hitmarks.push(AppliedHitmark {
                kind: hit.kind,
                amount: hit.amount,
                delay: hit.delay,
                limit: hit.limit,
                applied: now,
            });
        }
        for bar in &effects.headbars {
            let Some(fill) = bar.fill else {
                self.headbars.retain(|track| track.kind != bar.kind);
                continue;
            };
            let update = HeadbarUpdate {
                cycle: add(now, u32::from(fill.start_time)),
                start_fill: fill.start_fill,
                end_fill: fill.end_fill,
                duration: fill.end_time,
            };
            if let Some(track) = self.headbars.iter_mut().find(|t| t.kind == bar.kind) {
                if track.updates.len() == MAX_HEADBAR_UPDATES {
                    track.updates.remove(0);
                }
                track.updates.push(update);
            } else if self.headbars.len() < MAX_HEADBARS {
                self.headbars.push(HeadbarTrack {
                    kind: bar.kind,
                    updates: vec![update],
                });
            }
        }
        for spot in &effects.spotanims {
            if spot.slot >= SPOT_ANIMATION_SLOTS {
                continue;
            }
            match spot.id {
                Some(id) => {
                    self.spotanims.insert(
                        spot.slot,
                        AppliedSpotAnim {
                            id,
                            height: spot.height,
                            delay: spot.delay,
                            looping: spot.looping,
                            applied: now,
                        },
                    );
                }
                None => {
                    self.spotanims.remove(&spot.slot);
                }
            }
        }
        *self != before
    }

    /// Drop what is no longer shown at `now`. Called once per client cycle.
    pub(crate) fn expire(&mut self, now: ClientCycle) {
        self.hitmarks.retain(|hit| hit.expires_at() > now);
        // A track stays while its current update is displayed or its first update has not started.
        self.headbars.retain_mut(|track| {
            track.current(now).is_some() || track.updates.first().is_some_and(|u| u.cycle > now)
        });
    }
}

fn add(cycle: ClientCycle, amount: u32) -> ClientCycle {
    cycle.checked_advance(amount).unwrap_or(cycle)
}

#[cfg(test)]
mod tests {
    use super::*;
    use rustclient_model::{ActorSpotAnim, Headbar, HeadbarFill, Hitmark};

    fn at(cycle: u64) -> ClientCycle {
        ClientCycle::new(cycle)
    }
    fn bar(kind: u16, start_time: u16, end_time: u16, start: u8, end: u8) -> Headbar {
        Headbar {
            kind,
            fill: Some(HeadbarFill {
                start_time,
                end_time,
                start_fill: start,
                end_fill: end,
            }),
        }
    }

    #[test]
    fn hitmarks_wait_for_their_delay_then_expire_after_the_default_duration() {
        let mut state = ActorEffectsState::default();
        let effects = ActorEffects {
            hitmarks: vec![Hitmark {
                kind: 12,
                amount: 5,
                delay: 3,
                limit: 0,
            }],
            ..ActorEffects::default()
        };
        assert!(state.apply(&effects, at(100)));
        let hit = state.hitmarks()[0];
        assert_eq!((hit.shown_at(), hit.expires_at()), (at(103), at(173)));
        state.expire(at(172));
        assert_eq!(state.hitmarks().len(), 1);
        state.expire(at(173));
        assert!(state.is_empty());
        // Bounded: the oldest of 17 splats is dropped.
        let many = ActorEffects {
            hitmarks: (0..17)
                .map(|n| Hitmark {
                    kind: 12,
                    amount: n,
                    delay: 0,
                    limit: 0,
                })
                .collect(),
            ..ActorEffects::default()
        };
        state.apply(&many, at(0));
        assert_eq!(
            (state.hitmarks().len(), state.hitmarks()[0].amount),
            (16, 1)
        );
    }

    #[test]
    fn health_bars_interpolate_supersede_expire_and_remove() {
        let mut state = ActorEffectsState::default();
        state.apply(
            &ActorEffects {
                headbars: vec![bar(2, 0, 20, 30, 10)],
                ..ActorEffects::default()
            },
            at(50),
        );
        let fill = |state: &ActorEffectsState, cycle| state.headbars()[0].fill_at(at(cycle));
        assert_eq!(fill(&state, 50), Some(30));
        assert_eq!(fill(&state, 60), Some(20));
        assert_eq!(fill(&state, 70), Some(10));
        assert_eq!(fill(&state, 200), Some(10));
        // A later update that has started supersedes the first.
        state.apply(
            &ActorEffects {
                headbars: vec![bar(2, 5, 0, 8, 8)],
                ..ActorEffects::default()
            },
            at(80),
        );
        assert_eq!(fill(&state, 84), Some(10), "not started yet");
        assert_eq!(fill(&state, 85), Some(8));
        state.expire(at(85));
        assert_eq!(state.headbars()[0].updates.len(), 1);
        // 85 + 70 default display time + 0 duration.
        state.expire(at(154));
        assert_eq!(state.headbars().len(), 1);
        state.expire(at(155));
        assert!(state.headbars().is_empty());
        state.apply(
            &ActorEffects {
                headbars: vec![bar(2, 0, 0, 5, 5)],
                ..ActorEffects::default()
            },
            at(0),
        );
        state.apply(
            &ActorEffects {
                headbars: vec![Headbar {
                    kind: 2,
                    fill: None,
                }],
                ..ActorEffects::default()
            },
            at(1),
        );
        assert!(state.headbars().is_empty());
    }

    #[test]
    fn spot_animation_slots_replace_clear_and_reject_out_of_range() {
        let mut state = ActorEffectsState::default();
        let anim = |slot, id: Option<u32>| ActorSpotAnim {
            slot,
            id: id.map(SpotAnimationId::new),
            height: 92,
            delay: 4,
            looping: false,
        };
        let put = |state: &mut ActorEffectsState, spots| {
            state.apply(
                &ActorEffects {
                    spotanims: spots,
                    ..ActorEffects::default()
                },
                at(9),
            )
        };
        assert!(put(&mut state, vec![anim(1, Some(246)), anim(16, Some(5))]));
        assert_eq!(state.spotanims().count(), 1);
        assert!(
            !put(&mut state, vec![anim(1, Some(246))]),
            "same slot, same data, same cycle"
        );
        assert!(put(&mut state, vec![anim(1, Some(247))]));
        assert!(put(&mut state, vec![anim(1, None)]));
        assert!(state.is_empty());
    }
}
