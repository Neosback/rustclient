//! Port of `net.runelite.client.plugins.statusbars` (BSD-2, see `NOTICE`).
//!
//! The source draws a vertical bar on each side of the side panel for hitpoints, prayer, run energy,
//! special attack or warmth, with an optional icon, counter and restore preview, and hides them after
//! a delay out of combat. The bars are returned as overlay rectangles, sprites and text anchored to the
//! side-panel widget the same way `StatusBarsOverlay.render` places them.
//!
//! Not ported:
//! - Restore bars (`enableRestorationBars`): they come from `ItemStatChangesService`, a table of the
//!   stat effect of every consumable, for the item under the menu cursor.
//! - Heart variants for poison, disease and venom, the prayer skill icon and the warmth skill icon are
//!   `RuneLite` resource images, not cache sprites; the hitpoints and prayer orb icons from the cache are
//!   used for the first three and the warmth bar has no icon.
//! - The in-combat test (`hideAfterCombatDelay`) reads the interact target, which the client does not
//!   retain yet, so with a delay set the bars hide after it and only reappear once that lands.

use crate::NativePlugin;
use rustclient_plugin_api::{
    ActorRef, HorizontalAlign, LayeredOverlay, OverlayCommand, OverlayLayer, PluginOutput, Rect,
    Skill, WidgetSnapshot,
};
use rustclient_plugin_host::{PluginCall, PluginFault};

const BACKGROUND: u32 = argb(150, 0, 0, 0);
const HEALTH: u32 = argb(125, 225, 35, 0);
const POISONED: u32 = argb(150, 0, 145, 0);
const VENOMED: u32 = argb(150, 0, 65, 0);
const DISEASE: u32 = argb(181, 255, 193, 75);
const PARASITE: u32 = argb(181, 196, 62, 109);
const PRAYER: u32 = argb(175, 50, 200, 200);
const ACTIVE_PRAYER: u32 = argb(225, 57, 255, 186);
const RUN_STAMINA: u32 = argb(255, 160, 124, 72);
const ENERGY: u32 = argb(220, 199, 174, 0);
const SPECIAL_ATTACK: u32 = argb(195, 3, 153, 0);
const WARMTH: u32 = argb(255, 244, 97, 0);
const WHITE: u32 = 0xFFFF_FFFF;
const BLACK: u32 = 0xFF00_0000;

/// `Color(r, g, b, a)` as packed `AARRGGBB`.
const fn argb(a: u32, r: u32, g: u32, b: u32) -> u32 {
    a << 24 | r << 16 | g << 8 | b
}

const HEIGHT: i32 = 252;
const RESIZED_BOTTOM_HEIGHT: i32 = 272;
const RESIZED_BOTTOM_OFFSET_Y: i32 = 12;
const RESIZED_BOTTOM_OFFSET_X: i32 = 10;
const DEFAULT_WIDTH: i32 = 20;
const BORDER_SIZE: i32 = 1;
const SKILL_ICON_HEIGHT: i32 = 35;
const COUNTER_ICON_HEIGHT: i32 = 18;
const MIN_ICON_AND_COUNTER_WIDTH: i32 = 16;
const MAX_REAL_LEVEL: i32 = 99;

/// `SpriteID.OrbIcon.{HITPOINTS, PRAYER, WALK, SPECIAL}`.
const ORB_HITPOINTS: u32 = 1067;
const ORB_PRAYER: u32 = 1068;
const ORB_WALK: u32 = 1069;
const ORB_SPECIAL: u32 = 1610;

/// `VarPlayerID.{POISON, DISEASE, SA_ENERGY}` and `VarbitID.{PARASITE, STAMINA_ACTIVE, WINT_WARMTH,
/// PVP_AREA_CLIENT}`.
const VARP_POISON: u32 = 102;
const VARP_DISEASE: u32 = 456;
const VARP_SPECIAL_ENERGY: u32 = 300;
const VARBIT_PARASITE: u32 = 10_151;
const VARBIT_STAMINA_ACTIVE: u32 = 25;
const VARBIT_WARMTH: u32 = 11_434;
const VARBIT_PVP_AREA: u32 = 8121;
/// `InterfaceID.BrOverlay.CONTENT`: present while in Last Man Standing.
const LMS_OVERLAY: u32 = 0x0148_0004;

/// Every `Prayer` varbit (`Prayer.getVarbit`), which reads 1 while the prayer is active.
const PRAYER_VARBITS: [u32; 55] = [
    4104, 4105, 4106, 4122, 4123, 4107, 4108, 4109, 4110, 4111, 4112, 4124, 4125, 4113, 4114, 4115,
    4116, 4117, 4118, 4126, 4127, 4119, 4120, 4121, 4128, 16090, 16091, 4129, 5466, 5464, 5465,
    14840, 14829, 14830, 14831, 14966, 14841, 14964, 14963, 14962, 14832, 14844, 14839, 14843,
    14850, 14851, 14833, 14834, 14835, 14845, 14847, 14846, 14848, 14842, 14965,
];

/// `Viewport`: the side-panel widget each layout draws the bars against and where the bars sit
/// relative to it.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
enum Viewport {
    ResizedBox,
    ResizedBottom,
    Fixed,
    FixedBank,
}

impl Viewport {
    /// In the source's priority order.
    const ALL: [Self; 4] = [
        Self::ResizedBox,
        Self::ResizedBottom,
        Self::Fixed,
        Self::FixedBank,
    ];

    const fn widget(self) -> u32 {
        match self {
            Self::ResizedBox => 0x00A1_0049,
            Self::ResizedBottom => 0x00A4_0046,
            Self::Fixed => 0x0224_0050,
            Self::FixedBank => 0x000F_0003,
        }
    }

    /// `(offsetLeft, offsetRight)`.
    const fn offsets(self) -> ((i32, i32), (i32, i32)) {
        match self {
            Self::ResizedBottom => ((61, -12), (35, -12)),
            _ => ((20, -4), (0, -4)),
        }
    }
}

#[derive(Debug, Clone, Copy, PartialEq, Eq)]
enum BarMode {
    Disabled,
    Hitpoints,
    Prayer,
    RunEnergy,
    SpecialAttack,
    Warmth,
}

impl BarMode {
    fn parse(name: &str) -> Self {
        match name {
            "HITPOINTS" => Self::Hitpoints,
            "PRAYER" => Self::Prayer,
            "RUN_ENERGY" => Self::RunEnergy,
            "SPECIAL_ATTACK" => Self::SpecialAttack,
            "WARMTH" => Self::Warmth,
            _ => Self::Disabled,
        }
    }
}

/// What one bar shows.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
struct Bar {
    max: i32,
    current: i32,
    color: u32,
    icon: Option<u32>,
}

/// `BarRenderer.getBarHeight`, including `Math.round` rounding half up and `NaN` as zero.
#[expect(
    clippy::cast_possible_truncation,
    reason = "the rounded ratio is bounded by the bar height"
)]
fn bar_height(base: i32, current: i32, size: i32) -> i32 {
    let ratio = f64::from(current) / f64::from(base);
    if ratio >= 1.0 {
        return size;
    }
    (ratio * f64::from(size) + 0.5).floor() as i32
}

fn rect(command: Rect, argb: u32, filled: bool) -> LayeredOverlay {
    LayeredOverlay {
        layer: OverlayLayer::Top,
        command: OverlayCommand::Rect {
            x: command.x,
            y: command.y,
            width: command.width,
            height: command.height,
            argb,
            filled,
        },
    }
}

/// `BarRenderer.renderBar` without the restore preview.
fn render_bar(
    out: &mut Vec<LayeredOverlay>,
    bar: Bar,
    (x, y): (i32, i32),
    (width, height): (i32, i32),
    icons: bool,
    counter: bool,
) {
    let filled = bar_height(bar.max, bar.current, height);
    let area = Rect {
        x,
        y,
        width,
        height,
    };
    // The source outlines and then fills the same area, so the border is drawn twice.
    out.push(rect(area, BACKGROUND, false));
    out.push(rect(area, BACKGROUND, true));
    let fill_height = filled - BORDER_SIZE * 2;
    if fill_height > 0 && width - BORDER_SIZE * 2 > 0 {
        out.push(rect(
            Rect {
                x: x + BORDER_SIZE,
                y: y + BORDER_SIZE + (height - filled),
                width: width - BORDER_SIZE * 2,
                height: fill_height,
            },
            bar.color,
            true,
        ));
    }
    // Icons and counters would overlap a narrow bar.
    if (!icons && !counter) || width < MIN_ICON_AND_COUNTER_WIDTH {
        return;
    }
    let middle = x + width / 2;
    if icons && let Some(sprite) = bar.icon {
        out.push(LayeredOverlay {
            layer: OverlayLayer::Top,
            command: OverlayCommand::Sprite {
                sprite,
                x: middle,
                y: y + 4,
                align: HorizontalAlign::Center,
            },
        });
    }
    if counter {
        let baseline = y + if icons {
            SKILL_ICON_HEIGHT
        } else {
            COUNTER_ICON_HEIGHT
        };
        let text = bar.current.to_string();
        // `TextComponent`: a black shadow one pixel down and right of the white text.
        for (offset, argb) in [(1, BLACK), (0, WHITE)] {
            out.push(LayeredOverlay {
                layer: OverlayLayer::Top,
                command: OverlayCommand::Text {
                    text: text.clone(),
                    x: middle + offset,
                    y: baseline + offset,
                    argb,
                    align: HorizontalAlign::Center,
                },
            });
        }
    }
}

#[derive(Default)]
pub(crate) struct StatusBars {
    displayed: bool,
    last_combat_tick: u64,
    /// The delay `displayed` was last evaluated with, so a change re-evaluates immediately.
    checked_delay: Option<i32>,
}

impl StatusBars {
    /// `checkStatusBars`.
    fn check(&mut self, call: &mut PluginCall<'_>) {
        let delay = call.config.integer("hideAfterCombatDelay");
        self.checked_delay = Some(delay);
        let Some(local) = call.local_player().ok().flatten() else {
            return;
        };
        let tick = call.client().map_or(0, |client| client.tick);
        if delay == 0 {
            self.displayed = true;
            return;
        }
        let fighting = match local.actor.interacting {
            Some(ActorRef::Npc(index)) => call.npcs().is_ok_and(|npcs| {
                npcs.binary_search_by_key(&index, |npc| npc.actor.index)
                    .ok()
                    .and_then(|at| npcs.get(at))
                    .is_some_and(|npc| npc.actions.iter().flatten().any(|a| a == "Attack"))
            }),
            Some(ActorRef::Player(_)) => call.varbit(VARBIT_PVP_AREA).ok().flatten() == Some(1),
            None => false,
        };
        if fighting {
            self.last_combat_tick = tick;
            self.displayed = true;
        } else if i64::try_from(tick.saturating_sub(self.last_combat_tick)).unwrap_or(i64::MAX)
            >= i64::from(delay)
        {
            self.displayed = false;
        }
    }
}

/// The values behind one bar mode, or `None` for a disabled one.
fn bar_for(call: &mut PluginCall<'_>, mode: BarMode, lms: bool) -> Option<Bar> {
    let level = |call: &mut PluginCall<'_>, skill: Skill| {
        call.skills()
            .ok()
            .and_then(|skills| skills.get(skill.index()))
            .map_or((0, 0), |level| {
                (i32::from(level.real), i32::from(level.boosted))
            })
    };
    match mode {
        BarMode::Disabled => None,
        BarMode::Hitpoints => {
            let (real, boosted) = level(call, Skill::Hitpoints);
            let poison = call.varp(VARP_POISON).unwrap_or(0);
            let color = if poison >= 1_000_000 {
                VENOMED
            } else if poison > 0 {
                POISONED
            } else if call.varp(VARP_DISEASE).unwrap_or(0) > 0 {
                DISEASE
            } else if call.varbit(VARBIT_PARASITE).ok().flatten().unwrap_or(0) >= 1 {
                PARASITE
            } else {
                HEALTH
            };
            Some(Bar {
                max: if lms { MAX_REAL_LEVEL } else { real },
                current: boosted,
                color,
                icon: Some(ORB_HITPOINTS),
            })
        }
        BarMode::Prayer => {
            let (real, boosted) = level(call, Skill::Prayer);
            let active = PRAYER_VARBITS
                .iter()
                .any(|&varbit| call.varbit(varbit).ok().flatten() == Some(1));
            Some(Bar {
                max: if lms { MAX_REAL_LEVEL } else { real },
                current: boosted,
                color: if active { ACTIVE_PRAYER } else { PRAYER },
                icon: Some(ORB_PRAYER),
            })
        }
        BarMode::RunEnergy => {
            let energy = call.client().map_or(0, |client| client.run_energy / 100);
            let stamina = call
                .varbit(VARBIT_STAMINA_ACTIVE)
                .ok()
                .flatten()
                .unwrap_or(0)
                != 0;
            Some(Bar {
                max: 100,
                current: i32::from(energy),
                color: if stamina { RUN_STAMINA } else { ENERGY },
                icon: Some(ORB_WALK),
            })
        }
        BarMode::SpecialAttack => Some(Bar {
            max: 100,
            current: call.varp(VARP_SPECIAL_ENERGY).unwrap_or(0) / 10,
            color: SPECIAL_ATTACK,
            icon: Some(ORB_SPECIAL),
        }),
        BarMode::Warmth => Some(Bar {
            max: 100,
            current: call.varbit(VARBIT_WARMTH).ok().flatten().unwrap_or(0) / 10,
            color: WARMTH,
            icon: None,
        }),
    }
}

/// The first side-panel widget that is shown, with the layout it belongs to.
fn current_viewport(widgets: &[WidgetSnapshot]) -> Option<(Viewport, &WidgetSnapshot)> {
    Viewport::ALL.into_iter().find_map(|viewport| {
        widgets
            .iter()
            .find(|widget| widget.id == viewport.widget() && !widget.hidden)
            .map(|widget| (viewport, widget))
    })
}

impl NativePlugin for StatusBars {
    fn init(&mut self, _call: &mut PluginCall<'_>) -> Result<(), PluginFault> {
        *self = Self::default();
        Ok(())
    }

    fn events(
        &mut self,
        call: &mut PluginCall<'_>,
        events: &[rustclient_plugin_api::GameEvent],
    ) -> Result<(), PluginFault> {
        if events
            .iter()
            .any(|event| matches!(event, rustclient_plugin_api::GameEvent::GameTick(_)))
        {
            self.check(call);
        }
        Ok(())
    }

    fn output(&mut self, call: &mut PluginCall<'_>) -> Result<PluginOutput, PluginFault> {
        // `startUp` and a changed delay evaluate straight away instead of waiting for a tick.
        if self.checked_delay != Some(call.config.integer("hideAfterCombatDelay")) {
            self.check(call);
        }
        if !self.displayed {
            return Ok(PluginOutput::default());
        }
        let Ok(widgets) = call.widgets() else {
            return Ok(PluginOutput::default());
        };
        let Some((viewport, anchor)) = current_viewport(widgets) else {
            return Ok(PluginOutput::default());
        };
        let config = call.config;
        let (left_offset, right_offset) = viewport.offsets();
        let (location_x, location_y) = (anchor.bounds.x, anchor.bounds.y);
        let (width, height, left, right) = if viewport == Viewport::ResizedBottom {
            let width = config.integer("barWidth").clamp(3, 40);
            let extra = width - DEFAULT_WIDTH;
            (
                width,
                RESIZED_BOTTOM_HEIGHT,
                (
                    location_x + RESIZED_BOTTOM_OFFSET_X - left_offset.0 - 2 * extra,
                    location_y - RESIZED_BOTTOM_OFFSET_Y - left_offset.1,
                ),
                (
                    location_x + RESIZED_BOTTOM_OFFSET_X - right_offset.0 - extra,
                    location_y - RESIZED_BOTTOM_OFFSET_Y - right_offset.1,
                ),
            )
        } else {
            (
                DEFAULT_WIDTH,
                HEIGHT,
                (location_x - left_offset.0, location_y - left_offset.1),
                (
                    location_x - right_offset.0 + anchor.bounds.width,
                    location_y - right_offset.1,
                ),
            )
        };
        let lms = widgets.iter().any(|widget| widget.id == LMS_OVERLAY);
        let icons = config.boolean("enableSkillIcon");
        let counter = config.boolean("enableCounter");
        let mut output = PluginOutput::default();
        for (mode, origin) in [
            (config.choice("leftBarMode"), left),
            (config.choice("rightBarMode"), right),
        ] {
            if let Some(bar) = bar_for(call, BarMode::parse(mode), lms) {
                render_bar(
                    &mut output.overlay,
                    bar,
                    origin,
                    (width, height),
                    icons,
                    counter,
                );
            }
        }
        Ok(output)
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn bar_height_rounds_half_up_and_saturates() {
        assert_eq!(bar_height(99, 99, 252), 252);
        assert_eq!(bar_height(99, 120, 252), 252);
        assert_eq!(bar_height(10, 5, 252), 126);
        // 1/4 of 10 is 2.5, which `Math.round` takes to 3.
        assert_eq!(bar_height(4, 1, 10), 3);
        assert_eq!(bar_height(99, 0, 252), 0);
        // An empty maximum is `NaN` for 0/0 (drawn as zero) and infinite otherwise (drawn full).
        assert_eq!(bar_height(0, 0, 252), 0);
        assert_eq!(bar_height(0, 5, 252), 252);
    }

    #[test]
    fn colours_pack_as_argb() {
        assert_eq!(HEALTH, 0x7DE1_2300);
        assert_eq!(BACKGROUND, 0x9600_0000);
    }

    #[test]
    fn a_full_bar_has_background_border_fill_icon_and_counter_with_shadow() {
        let mut out = Vec::new();
        let bar = Bar {
            max: 99,
            current: 99,
            color: HEALTH,
            icon: Some(ORB_HITPOINTS),
        };
        render_bar(&mut out, bar, (10, 20), (20, 252), true, true);
        let commands: Vec<_> = out.iter().map(|item| &item.command).collect();
        assert!(matches!(
            commands[0],
            OverlayCommand::Rect {
                filled: false,
                width: 20,
                height: 252,
                ..
            }
        ));
        assert!(matches!(
            commands[1],
            OverlayCommand::Rect {
                filled: true,
                width: 20,
                height: 252,
                ..
            }
        ));
        // Inside the 1 pixel border on every side.
        assert!(matches!(
            commands[2],
            OverlayCommand::Rect {
                x: 11,
                y: 21,
                width: 18,
                height: 250,
                argb: HEALTH,
                ..
            }
        ));
        assert!(matches!(
            commands[3],
            OverlayCommand::Sprite {
                sprite: ORB_HITPOINTS,
                x: 20,
                y: 24,
                align: HorizontalAlign::Center
            }
        ));
        // Icon shown: the counter sits below it at the icon height.
        assert!(matches!(
            commands[4],
            OverlayCommand::Text {
                y: 56,
                argb: BLACK,
                ..
            }
        ));
        assert!(matches!(
            commands[5],
            OverlayCommand::Text {
                y: 55,
                argb: WHITE,
                ..
            }
        ));
    }

    #[test]
    fn narrow_bars_draw_neither_icon_nor_counter() {
        let mut out = Vec::new();
        let bar = Bar {
            max: 10,
            current: 5,
            color: HEALTH,
            icon: Some(ORB_HITPOINTS),
        };
        render_bar(&mut out, bar, (0, 0), (15, 100), true, true);
        assert_eq!(out.len(), 3);
    }

    #[test]
    fn an_empty_bar_has_no_fill() {
        let mut out = Vec::new();
        let bar = Bar {
            max: 10,
            current: 0,
            color: HEALTH,
            icon: None,
        };
        render_bar(&mut out, bar, (0, 0), (20, 100), false, false);
        assert_eq!(out.len(), 2);
    }

    #[test]
    fn modes_parse_from_the_schema_names() {
        assert_eq!(BarMode::parse("HITPOINTS"), BarMode::Hitpoints);
        assert_eq!(BarMode::parse("RUN_ENERGY"), BarMode::RunEnergy);
        assert_eq!(BarMode::parse("DISABLED"), BarMode::Disabled);
        assert_eq!(BarMode::parse("nonsense"), BarMode::Disabled);
    }
}
