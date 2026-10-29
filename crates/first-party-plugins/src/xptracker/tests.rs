use super::*;
use crate::tests::Harness;
use rustclient_plugin_api::{ClientSnapshot, GameSnapshot, SkillLevel};

#[test]
fn rates_action_history_goals_and_rate_only_reset_follow_source_arithmetic() {
    let mut skill = SkillTracker::new(1000);
    skill.set_goal(1000, 2000).unwrap();
    assert!(skill.update(1100));
    let summary = skill.summary();
    assert_eq!(summary.gained, 100);
    assert_eq!(summary.xp_per_hour, 6000);
    assert_eq!(summary.actions_remaining, Some(9));
    assert_eq!(summary.seconds_remaining, Some(540));
    skill.advance(120_000, PausePolicy::default());
    assert_eq!(skill.summary().xp_per_hour, 3000);
    for xp in (1120..=1320).step_by(20) {
        assert!(skill.update(xp));
    }
    // All ten samples are 20 now; ceil((2000-1320)/20)=34.
    assert_eq!(skill.summary().actions_remaining, Some(34));
    assert_eq!(skill.summary().actions, 12);
    skill.reset_rate();
    assert_eq!(skill.summary().gained, 320);
    assert_eq!(skill.summary().xp_per_hour, 0);
    assert!(skill.update(1340));
    assert_eq!(skill.summary().gained, 340);
    assert_eq!(skill.summary().xp_per_hour, 1200);
    assert!(!skill.update(500));
    assert_eq!(skill.summary().gained, 0);
    assert_eq!(skill.summary().actions, 0);
}
#[test]
fn idle_logout_manual_pause_gain_resume_and_auto_rate_reset_are_explicit() {
    let mut skill = SkillTracker::new(0);
    skill.update(100);
    skill.advance(
        120_000,
        PausePolicy {
            pause_after_ms: 60_000,
            ..Default::default()
        },
    );
    assert_eq!(skill.summary().xp_per_hour, 6000);
    skill.advance(
        300_000,
        PausePolicy {
            logged_out: true,
            ..Default::default()
        },
    );
    assert_eq!(skill.summary().xp_per_hour, 6000);
    skill.pause();
    skill.advance(300_000, PausePolicy::default());
    assert_eq!(skill.summary().xp_per_hour, 6000);
    skill.update(200);
    skill.advance(60_000, PausePolicy::default());
    assert_eq!(skill.summary().xp_per_hour, 6000);
    skill.advance(
        60_000,
        PausePolicy {
            reset_rate_after_ms: 60_000,
            ..Default::default()
        },
    );
    assert_eq!(skill.summary().gained, 200);
    assert_eq!(skill.summary().xp_per_hour, 0);
}
#[test]
fn persistence_requires_exact_account_and_snapshot_and_rejects_corruption() {
    let mut skill = SkillTracker::new(100);
    skill.update(200);
    skill.set_goal(100, 500).unwrap();
    let save = skill.save("server/account-mode").unwrap();
    assert_eq!(
        SkillTracker::restore(&save, "server/account-mode", 200)
            .unwrap()
            .summary(),
        skill.summary()
    );
    assert!(SkillTracker::restore(&save, "other/account", 200).is_err());
    assert!(SkillTracker::restore(&save, "server/account-mode", 300).is_err());
    assert!(skill.save("").is_err());
    assert!(SkillTracker::restore("{}", "account", 0).is_err());
    let corrupt = save.replace("\"history_cursor\":1", "\"history_cursor\":10");
    assert!(SkillTracker::restore(&corrupt, "server/account-mode", 200).is_err());
}
#[test]
fn live_plugin_suppresses_login_sync_then_emits_gain_overlay_and_resets_on_xp_loss() {
    let mut harness = Harness::new();
    harness.enable("xptracker", &[]);
    let mut snapshot = GameSnapshot {
        client: ClientSnapshot {
            state: GameStateKind::LoggedIn,
            viewport: rustclient_plugin_api::Rect {
                x: 0,
                y: 0,
                width: 512,
                height: 334,
            },
            ..Default::default()
        },
        skills: vec![
            SkillLevel {
                real: 1,
                boosted: 1,
                experience: 100
            };
            23
        ],
        ..Default::default()
    };
    let changed = |xp| GameEvent::StatChanged {
        skill: Skill::Attack,
        level: SkillLevel {
            real: 1,
            boosted: 1,
            experience: xp,
        },
    };
    assert!(
        harness
            .run(&snapshot, &[changed(100), GameEvent::GameTick(1)])
            .overlay
            .is_empty()
    );
    assert!(
        harness
            .run(&snapshot, &[GameEvent::GameTick(2)])
            .overlay
            .is_empty()
    );
    snapshot.skills[0].experience = 150;
    snapshot.client.cycle = 30;
    let output = harness.run(&snapshot, &[changed(150)]);
    assert_eq!(output.overlay.len(), 1);
    assert!(
        matches!(&output.overlay[0].command,OverlayCommand::Text{text,..} if text.contains("+50 XP") && text.contains("3000/hr"))
    );
    // A normal map load is still the same logged-in session, not a new login sync gate.
    snapshot.client.state = GameStateKind::Loading;
    snapshot.skills[0].experience = 175;
    assert!(harness.run(&snapshot, &[changed(175)]).overlay.is_empty());
    snapshot.client.state = GameStateKind::LoggedIn;
    let resumed = harness.run(&snapshot, &[GameEvent::ClientTick]);
    assert!(
        matches!(&resumed.overlay[0].command,OverlayCommand::Text{text,..} if text.contains("+75 XP"))
    );
    snapshot.skills[0].experience = 10;
    assert!(harness.run(&snapshot, &[changed(10)]).overlay.is_empty());
}
