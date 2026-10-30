//! Semantic geometry/input contracts for the single native/browser component; no pixel goldens.
use keyboard_types::{Code, Key, Modifiers};
use rustclient_plugin_host::{configuration::Configuration, required::all_schemas};
use rustclient_shell_ui::{
    EguiPanel, PluginPanel, ShellAction, ShellChannel, SidebarMetrics, SidebarState,
};
fn fixture(scale: f32) -> (EguiPanel, ShellChannel) {
    let channel = ShellChannel::default();
    channel.model.borrow_mut().sidebar = SidebarState::Panel;
    channel.model.borrow_mut().plugins = all_schemas()
        .unwrap()
        .into_iter()
        .map(|schema| PluginPanel::project(&Configuration::new(schema, None).unwrap(), |_| None))
        .collect();
    #[expect(
        clippy::cast_possible_truncation,
        clippy::cast_sign_loss,
        clippy::cast_precision_loss,
        reason = "five bounded DPI fixtures"
    )]
    let mut panel = EguiPanel::new(
        channel.clone(),
        [
            (SidebarMetrics::OPEN as f32 * scale).round() as u32,
            (503. * scale).round() as u32,
        ],
        scale,
    )
    .unwrap();
    panel.set_visible(true);
    for t in 0..4 {
        panel.render(f64::from(t) * 0.1).unwrap();
    }
    (panel, channel)
}
fn click(panel: &mut EguiPanel, target: &str, time: f64) {
    let [x, y, w, h] = panel
        .target_bounds(target)
        .unwrap_or_else(|| panic!("missing {target}"));
    panel.pointer(x + w / 2., y + h / 2., Some(true), true);
    panel.render(time).unwrap();
    panel.pointer(x + w / 2., y + h / 2., Some(false), false);
    panel.render(time + 0.01).unwrap();
    panel.pointer_leave();
    panel.render(time + 0.02).unwrap();
}
#[test]
fn source_metrics_and_hidpi_targets_share_one_extent_without_hidden_work() {
    assert_eq!(
        (
            SidebarMetrics::INNER_PANEL,
            SidebarMetrics::SCROLL_ALLOWANCE,
            SidebarMetrics::RAIL,
            SidebarMetrics::OPEN
        ),
        (225, 17, 31, 273)
    );
    for scale in [1., 1.25, 1.5, 1.75, 2.] {
        let (mut panel, channel) = fixture(scale);
        let header = panel.target_bounds("header:config_icon_lg").unwrap();
        assert!((header[0] - 10.).abs() < 0.01 && (header[1] - 10.).abs() < 0.01);
        assert!((header[2] - 69.).abs() <= 1. && (header[3] - 34.).abs() < 0.01);
        click(&mut panel, "header:profile_icon", 0.5);
        assert!(
            channel.drain().is_empty(),
            "unavailable header fabricated navigation"
        );
        let [x, _, w, h] = panel.target_bounds("toggle-panel").unwrap();
        assert!(
            x >= 242. && x + w <= 274. && (h - 26.).abs() < 0.01,
            "{scale}: {x} {w} {h}"
        );
        click(&mut panel, "toggle-panel", 1.);
        assert_eq!(channel.drain(), [ShellAction::TogglePanel]);
        panel.set_visible(false);
        let before = panel.stats();
        for n in 0..100 {
            panel.pointer(10., 10., Some(true), true);
            assert!(!panel.render(f64::from(n) + 2.).unwrap());
        }
        assert_eq!(panel.stats().passes, before.passes);
        assert!(channel.drain().is_empty());
        assert!(panel.target_bounds("toggle-panel").is_none());
    }
}
#[test]
fn required_badges_configuration_search_favorites_and_back_keep_their_owners() {
    let (mut panel, channel) = fixture(1.);
    assert!(
        panel
            .target_bounds("always-on:dev.rustclient.gpu")
            .is_some()
    );
    assert!(panel.target_bounds("enabled:dev.rustclient.gpu").is_none());
    click(&mut panel, "favorite:dev.rustclient.gpu", 1.);
    assert_eq!(
        channel.drain(),
        [ShellAction::ToggleFavorite {
            plugin: "dev.rustclient.gpu".into()
        }]
    );
    click(&mut panel, "search", 2.);
    panel.key(
        Key::Character("gpu".into()),
        Code::Unidentified,
        Modifiers::empty(),
        true,
        Some("gpu".into()),
    );
    panel.render(2.1).unwrap();
    assert!(panel.target_bounds("plugin:dev.rustclient.gpu").is_some());
    assert!(
        panel
            .target_bounds("plugin:dev.rustclient.camera")
            .is_none()
    );
    click(&mut panel, "configure:dev.rustclient.gpu", 3.);
    assert!(
        panel
            .target_bounds("always-on:dev.rustclient.gpu")
            .is_some()
    );
    assert!(panel.target_bounds("enabled:dev.rustclient.gpu").is_none());
    click(&mut panel, "back", 4.);
    assert!(
        panel
            .target_bounds("plugin:dev.rustclient.camera")
            .is_none()
    );
    click(&mut panel, "clear-search", 5.);
    assert!(
        panel
            .target_bounds("plugin:dev.rustclient.camera")
            .is_some()
    );
    assert!(channel.drain().is_empty());
}

#[test]
fn unavailable_activation_cannot_emit_an_effective_looking_enable_action() {
    let (mut panel, channel) = fixture(1.);
    let id = "dev.rustclient.animsmoothing";
    channel
        .model
        .borrow_mut()
        .plugins
        .iter_mut()
        .find(|p| p.id == id)
        .unwrap()
        .enablement_unavailable = Some("No gameplay composition in this harness".into());
    panel.invalidate();
    panel.render(0.5).unwrap();
    click(&mut panel, &format!("enabled:{id}"), 1.);
    assert!(channel.drain().is_empty());
    click(&mut panel, &format!("configure:{id}"), 2.);
    assert!(panel.target_bounds("back").is_some());
}

#[test]
fn favorite_reordering_keeps_keyboard_focus_on_the_same_plugin_identity() {
    let (mut panel, channel) = fixture(1.);
    let id = "dev.rustclient.gpu";
    click(&mut panel, &format!("favorite:{id}"), 1.);
    assert_eq!(
        channel.drain(),
        [ShellAction::ToggleFavorite { plugin: id.into() }]
    );
    channel.model.borrow_mut().pinned.insert(id.into());
    panel.invalidate();
    panel.render(1.5).unwrap();
    panel.key(Key::Enter, Code::Enter, Modifiers::empty(), true, None);
    panel.render(2.).unwrap();
    assert_eq!(
        channel.drain(),
        [ShellAction::ToggleFavorite { plugin: id.into() }]
    );
}
