use super::*;
use rustclient_plugin_api::{
    ClientSnapshot, OverlayCommand, Rect, Skill, SkillLevel, VarbitDef, Vars, WidgetSnapshot,
};
use std::collections::BTreeMap;
use std::sync::Arc;

const FIXED_SIDE_PANELS: u32 = 0x0224_0050;
const RESIZED_BOTTOM_SIDE_BACKGROUND: u32 = 0x00A4_0046;

fn panel(id: u32, x: i32, y: i32, width: i32) -> WidgetSnapshot {
    let bounds = Rect {
        x,
        y,
        width,
        height: 261,
    };
    WidgetSnapshot {
        id,
        child_index: -1,
        bounds,
        clip: bounds,
        text: None,
        sprite: None,
        item: None,
        hidden: false,
        actions: Vec::new(),
    }
}

fn snapshot(widgets: Vec<WidgetSnapshot>) -> GameSnapshot {
    let mut skills = vec![SkillLevel::default(); 23];
    skills[Skill::Hitpoints.index()] = SkillLevel {
        real: 99,
        boosted: 50,
        experience: 0,
    };
    skills[Skill::Prayer.index()] = SkillLevel {
        real: 70,
        boosted: 70,
        experience: 0,
    };
    GameSnapshot {
        client: ClientSnapshot {
            run_energy: 4200,
            ..ClientSnapshot::default()
        },
        local_player: Some(1),
        players: vec![rustclient_plugin_api::PlayerSnapshot {
            actor: actor(1, None),
            name: "Me".into(),
            combat_level: 3,
            skill_level: 0,
            is_local: true,
            hidden: false,
        }],
        skills,
        widgets: widgets.into(),
        ..GameSnapshot::default()
    }
}

fn rects(output: &HostOutput) -> Vec<(i32, i32, i32, i32, u32, bool)> {
    output
        .overlay
        .iter()
        .filter_map(|item| match &item.command {
            OverlayCommand::Rect {
                x,
                y,
                width,
                height,
                argb,
                filled,
            } => Some((*x, *y, *width, *height, *argb, *filled)),
            _ => None,
        })
        .collect()
}

#[test]
fn nothing_is_drawn_without_a_side_panel() {
    let mut harness = Harness::new();
    harness.enable("statusbars", &[]);
    assert!(harness.output(&snapshot(Vec::new())).overlay.is_empty());
}

#[test]
fn fixed_layout_bars_sit_left_and_right_of_the_side_panel() {
    let mut harness = Harness::new();
    harness.enable(
        "statusbars",
        &[("enableSkillIcon", ConfigValue::Boolean(false))],
    );
    let output = harness.output(&snapshot(vec![panel(FIXED_SIDE_PANELS, 549, 205, 190)]));
    let rects = rects(&output);
    // Left bar: x = 549 - 20, y = 205 + 4; right bar: x = 549 - 0 + 190.
    assert_eq!((rects[0].0, rects[0].1), (529, 209));
    assert_eq!(rects[1], (529, 209, 20, 252, 0x9600_0000, true));
    // Hitpoints 50/99 of 252 rows is 127, drawn inside the border.
    assert_eq!(
        rects[2],
        (530, 209 + 1 + (252 - 127), 18, 127 - 2, 0x7DE1_2300, true)
    );
    assert_eq!(rects[3], (739, 209, 20, 252, 0x9600_0000, false));
    // Prayer is full and not active: the full height, in the idle prayer colour.
    assert_eq!(rects[5].3, 250);
    assert_eq!(rects[5].4, 0xAF32_C8C8);
}

#[test]
fn the_modern_layout_uses_the_configured_width_and_its_own_offsets() {
    let mut harness = Harness::new();
    harness.enable(
        "statusbars",
        &[
            ("barWidth", ConfigValue::Integer(10)),
            ("enableSkillIcon", ConfigValue::Boolean(false)),
        ],
    );
    let output = harness.output(&snapshot(vec![panel(
        RESIZED_BOTTOM_SIDE_BACKGROUND,
        500,
        300,
        250,
    )]));
    let rects = rects(&output);
    // extra = 10 - 20 = -10. Left x = 500 + 10 - 61 + 20 = 469, y = 300 - 12 + 12 = 300.
    assert_eq!(
        (rects[0].0, rects[0].1, rects[0].2, rects[0].3),
        (469, 300, 10, 272)
    );
    // Right x = 500 + 10 - 35 + 10 = 485.
    assert_eq!(rects[3].0, 485);
}

#[test]
fn icons_and_counters_follow_their_switches() {
    let mut harness = Harness::new();
    harness.enable(
        "statusbars",
        &[("enableCounter", ConfigValue::Boolean(true))],
    );
    let output = harness.output(&snapshot(vec![panel(FIXED_SIDE_PANELS, 549, 205, 190)]));
    let sprites: Vec<_> = output
        .overlay
        .iter()
        .filter_map(|item| match item.command {
            OverlayCommand::Sprite { sprite, x, y, .. } => Some((sprite, x, y)),
            _ => None,
        })
        .collect();
    // Hitpoints orb on the left bar, prayer orb on the right, centred in each bar, 4 below the top.
    assert_eq!(sprites, [(1067, 539, 213), (1068, 749, 213)]);
    let counters: Vec<_> = output
        .overlay
        .iter()
        .filter_map(|item| match &item.command {
            OverlayCommand::Text {
                text,
                argb: 0xFFFF_FFFF,
                ..
            } => Some(text.clone()),
            _ => None,
        })
        .collect();
    assert_eq!(counters, ["50", "70"]);
}

#[test]
fn run_energy_and_special_attack_read_their_sources() {
    let mut harness = Harness::new();
    harness.enable(
        "statusbars",
        &[
            ("leftBarMode", ConfigValue::Choice("RUN_ENERGY".into())),
            ("rightBarMode", ConfigValue::Choice("SPECIAL_ATTACK".into())),
            ("enableCounter", ConfigValue::Boolean(true)),
            ("enableSkillIcon", ConfigValue::Boolean(false)),
        ],
    );
    let mut game = snapshot(vec![panel(FIXED_SIDE_PANELS, 549, 205, 190)]);
    game.vars.varps.insert(300, 735);
    let output = harness.output(&game);
    let counters: Vec<_> = output
        .overlay
        .iter()
        .filter_map(|item| match &item.command {
            OverlayCommand::Text {
                text,
                argb: 0xFFFF_FFFF,
                ..
            } => Some(text.clone()),
            _ => None,
        })
        .collect();
    // 4200 / 100 and 735 / 10, in whole percent.
    assert_eq!(counters, ["42", "73"]);
}

#[test]
fn poison_and_venom_recolour_the_hitpoints_bar_and_an_active_prayer_the_prayer_bar() {
    let mut harness = Harness::new();
    harness.enable(
        "statusbars",
        &[("enableSkillIcon", ConfigValue::Boolean(false))],
    );
    let mut game = snapshot(vec![panel(FIXED_SIDE_PANELS, 549, 205, 190)]);
    game.vars.varps.insert(102, 10);
    let poisoned = harness.output(&game);
    assert_eq!(rects(&poisoned)[2].4, 0x9600_9100);
    game.vars.varps.insert(102, 1_000_000);
    assert_eq!(rects(&harness.output(&game))[2].4, 0x9600_4100);
    // Protect from Melee (varbit 4129) lights the prayer bar.
    game.vars = Vars {
        varps: BTreeMap::from([(7, 1)]),
        varbits: Arc::new(BTreeMap::from([(
            4129,
            VarbitDef {
                varp: 7,
                lsb: 0,
                msb: 0,
            },
        )])),
    };
    assert_eq!(rects(&harness.output(&game))[5].4, 0xE139_FFBA);
}

#[test]
fn bars_hide_after_the_delay_once_combat_ends() {
    use rustclient_plugin_api::{ActorRef, NpcSnapshot};
    let mut harness = Harness::new();
    harness.enable(
        "statusbars",
        &[
            ("hideAfterCombatDelay", ConfigValue::Integer(10)),
            ("enableSkillIcon", ConfigValue::Boolean(false)),
        ],
    );
    let panel = vec![panel(FIXED_SIDE_PANELS, 549, 205, 190)];
    let mut fight = snapshot(panel.clone());
    fight.client.tick = 100;
    // Never fought: evaluated at once on enabling, 100 ticks since tick 0 is past the delay.
    assert!(harness.output(&fight).overlay.is_empty());
    // Fighting an attackable NPC shows the bars on the tick it starts.
    fight.npcs = vec![NpcSnapshot {
        actor: actor(7, None),
        id: 2,
        name: Some("Giant rat".into()),
        combat_level: 3,
        size: (1, 1),
        actions: vec![Some("Attack".into())],
    }];
    fight.players[0].actor.interacting = Some(ActorRef::Npc(7));
    let shown = harness.run(&fight, &[GameEvent::GameTick(100)]);
    assert!(!shown.overlay.is_empty());
    // Out of combat, they stay for the delay and go on the tick that reaches it.
    let mut over = snapshot(panel);
    over.client.tick = 109;
    assert!(
        !harness
            .run(&over, &[GameEvent::GameTick(109)])
            .overlay
            .is_empty()
    );
    over.client.tick = 110;
    assert!(
        harness
            .run(&over, &[GameEvent::GameTick(110)])
            .overlay
            .is_empty()
    );
}
