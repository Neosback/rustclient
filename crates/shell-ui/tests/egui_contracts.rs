//! Input/configuration contracts through the same egui component used by the desktop.
use keyboard_types::{Code, Key, Modifiers};
use rustclient_plugin_host::configuration::{ConfigValue, Control, Hotkey, Setting};
use rustclient_shell_ui::{
    EguiPanel, PluginPanel, SettingView, ShellAction, ShellChannel, SidebarState,
};
fn fixture(control: Control, value: ConfigValue) -> (EguiPanel, ShellChannel) {
    let channel = ShellChannel::default();
    channel.model.borrow_mut().sidebar = SidebarState::Panel;
    channel.model.borrow_mut().plugins.push(PluginPanel {
        id: "dev.test".into(),
        name: "Sample".into(),
        description: "Test panel".into(),
        tags: vec![],
        required: true,
        enabled: true,
        settings: vec![SettingView {
            descriptor: Setting {
                key: "value".into(),
                label: "Value".into(),
                description: "Fixture".into(),
                section: "General".into(),
                control,
                default: value.clone(),
                source: "synthetic UI contract".into(),
            },
            value,
            unavailable: None,
            note: None,
        }],
    });
    let mut panel = EguiPanel::new(channel.clone(), [556, 1006], 2.).unwrap();
    panel.set_visible(true);
    panel.render(0.).unwrap();
    panel.render(0.1).unwrap();
    (panel, channel)
}
fn click(panel: &mut EguiPanel, target: &str, time: f64) {
    let [x, y, w, h] = panel.target_bounds(target).expect("displayed target");
    panel.pointer(x + w / 2., y + h / 2., Some(true), true);
    panel.render(time).unwrap();
    panel.pointer(x + w / 2., y + h / 2., Some(false), false);
    panel.render(time + 0.02).unwrap();
    panel.render(time + 0.05).unwrap();
}
fn open(panel: &mut EguiPanel) {
    click(panel, "plugin:dev.test", 1.);
}
fn key(panel: &mut EguiPanel, key: Key, mods: Modifiers, text: Option<String>, time: f64) {
    panel.key(key, Code::Unidentified, mods, true, text);
    panel.render(time).unwrap();
}
#[test]
fn unchanged_and_hidden_world_frames_do_no_component_or_tessellation_work() {
    let (mut panel, _) = fixture(Control::Boolean, ConfigValue::Boolean(false));
    for index in 1..12 {
        panel.render(f64::from(index) * 0.2).unwrap();
    }
    let before = panel.stats();
    for index in 0..600 {
        panel.render(3. + f64::from(index) / 60.).unwrap();
    }
    assert_eq!(panel.stats().passes, before.passes);
    assert_eq!(panel.stats().tessellations, before.tessellations);
    panel.set_visible(false);
    let before = panel.stats();
    for index in 0..600 {
        panel.pointer(50., 50., None, false);
        panel.render(15. + f64::from(index) / 60.).unwrap();
    }
    assert_eq!(panel.stats().passes, before.passes);
}
#[test]
fn boolean_and_required_enforcement_emit_only_typed_configuration_actions() {
    let (mut panel, channel) = fixture(Control::Boolean, ConfigValue::Boolean(false));
    open(&mut panel);
    click(&mut panel, "setting:dev.test:value", 2.);
    assert_eq!(
        channel.drain(),
        [ShellAction::Configure {
            plugin: "dev.test".into(),
            key: "value".into(),
            value: ConfigValue::Boolean(true)
        }]
    );
    // Required state is display-only; no enabled control is generated.
    assert!(panel.target_bounds("setting:dev.test:enabled").is_none());
    channel.model.borrow_mut().plugins[0].settings[0].value = ConfigValue::Boolean(false);
    channel.model.borrow_mut().error = Some("write failed; accepted value retained".into());
    panel.invalidate();
    panel.render(3.).unwrap();
    assert!(channel.drain().is_empty());
}
#[test]
fn slider_drag_commits_once_on_release_and_focus_loss_cancels_a_draft() {
    let (mut panel, channel) = fixture(
        Control::Integer { min: 0, max: 100 },
        ConfigValue::Integer(0),
    );
    open(&mut panel);
    let [x, y, w, h] = panel.target_bounds("setting:dev.test:value").unwrap();
    panel.pointer(x + 5., y + h / 2., Some(true), true);
    panel.render(2.).unwrap();
    for index in 1_u16..20 {
        panel.pointer(
            x + 5. + w * 0.6 * f32::from(index) / 20.,
            y + h / 2.,
            None,
            true,
        );
        panel.render(2. + f64::from(index) / 60.).unwrap();
    }
    assert!(
        channel.drain().is_empty(),
        "draft performed persistence-worthy actions"
    );
    panel.pointer(x + w * 0.6, y + h / 2., Some(false), false);
    panel.render(3.).unwrap();
    let actions = channel.drain();
    assert_eq!(actions.len(), 1);
    assert!(matches!(
        actions[0],
        ShellAction::Configure {
            value: ConfigValue::Integer(1..=100),
            ..
        }
    ));
    panel.pointer(x + w * 0.8, y + h / 2., Some(true), true);
    panel.render(4.).unwrap();
    panel.clear_focus();
    panel.render(4.1).unwrap();
    assert!(channel.drain().is_empty());
}
#[test]
fn text_editing_ime_backspace_enter_and_hotkey_capture_use_shared_values() {
    let (mut panel, channel) = fixture(
        Control::Text { max_len: 64 },
        ConfigValue::Text(String::new()),
    );
    open(&mut panel);
    click(&mut panel, "setting:dev.test:value", 2.);
    panel.ime_preedit("hello".into(), Some((0, 5)));
    panel.render(2.1).unwrap();
    panel.ime_commit("hello".into());
    panel.render(2.2).unwrap();
    key(&mut panel, Key::Backspace, Modifiers::empty(), None, 2.3);
    key(&mut panel, Key::Enter, Modifiers::empty(), None, 2.4);
    assert!(channel.drain().iter().any(|action|matches!(action,ShellAction::Configure{value:ConfigValue::Text(text),..} if text=="hell")));
    let (mut panel, channel) = fixture(
        Control::Hotkey,
        ConfigValue::Hotkey(Hotkey {
            key: String::new(),
            modifiers: 0,
        }),
    );
    open(&mut panel);
    click(&mut panel, "setting:dev.test:value", 2.);
    key(&mut panel, Key::F8, Modifiers::CONTROL, None, 2.2);
    assert_eq!(
        channel.drain(),
        [ShellAction::Configure {
            plugin: "dev.test".into(),
            key: "value".into(),
            value: ConfigValue::Hotkey(Hotkey {
                key: "F8".into(),
                modifiers: 2
            })
        }]
    );
}

#[test]
fn desktop_panel_ships_fonts_for_plugin_labels_and_controls() {
    // egui 0.37 has empty defaults unless its font feature is selected explicitly.
    let definitions = egui::FontDefinitions::default();
    assert!(!definitions.font_data.is_empty());
    assert!(!definitions.families[&egui::FontFamily::Proportional].is_empty());
    let (panel, _) = fixture(Control::Boolean, ConfigValue::Boolean(false));
    let [x, y, width, height] = panel.target_bounds("toggle-panel").unwrap();
    assert!(x >= 0. && y >= 0. && x + width <= 278. && y + height <= 503.);
}
