#![cfg(feature="legacy-native")]
#![cfg(not(target_arch = "wasm32"))]
use rustclient_plugin_host::{
    configuration::{ConfigValue, Configuration},
    required::all_schemas,
};
use rustclient_shell_ui::{PluginPanel, RetainedPanel, ShellAction, ShellChannel};

fn click(panel: &mut RetainedPanel, selector: &str) {
    let [x, y, width, height] = panel
        .element_bounds(selector)
        .unwrap_or_else(|| panic!("missing {selector}"));
    panel.pointer(x + width / 2., y + height / 2., Some(true), true);
    panel.pointer(x + width / 2., y + height / 2., Some(false), false);
    panel.render();
}

fn type_into(panel: &mut RetainedPanel, selector: &str, text: &str) {
    click(panel, selector);
    let command = if cfg!(target_os = "macos") {
        keyboard_types::Modifiers::SUPER
    } else {
        keyboard_types::Modifiers::CONTROL
    };
    panel.key(
        keyboard_types::Key::Character("a".into()),
        keyboard_types::Code::KeyA,
        command,
        true,
        None,
    );
    panel.ime_commit(text.into());
    panel.key(
        keyboard_types::Key::Enter,
        keyboard_types::Code::Enter,
        keyboard_types::Modifiers::empty(),
        true,
        None,
    );
    panel.render();
}

#[test]
fn optional_plugins_switch_on_and_edit_text_and_decimal_settings() {
    let channel = ShellChannel::default();
    channel.model.borrow_mut().sidebar = rustclient_shell_ui::SidebarState::Panel;
    for schema in all_schemas().unwrap() {
        let config = Configuration::new(schema, None).unwrap();
        channel
            .model
            .borrow_mut()
            .plugins
            .push(PluginPanel::project(&config, |_| None));
    }
    let mut panel = RetainedPanel::new(channel.clone(), [370, 2400], 1.).unwrap();
    panel.set_visible(true);
    panel.render().unwrap();

    // Required services keep the locked switch; optional plugins get a live one.
    assert!(
        panel
            .element_bounds("[data-plugin='dev.rustclient.gpu'] .switch.locked")
            .is_some()
    );
    assert!(
        panel
            .element_bounds("[data-plugin='dev.rustclient.gpu'] button[role='switch']")
            .is_none()
    );
    click(
        &mut panel,
        "[data-plugin='dev.rustclient.roofremoval'] button[role='switch']",
    );
    assert!(channel.drain().iter().any(|action| matches!(
        action,
        ShellAction::Configure { plugin, key, value: ConfigValue::Boolean(true) }
            if plugin == "dev.rustclient.roofremoval" && key == "enabled"
    )));

    // Search matches the source tags, not only the name.
    click(&mut panel, "#plugin-search");
    panel.ime_commit("npcs".into());
    panel.render();
    assert!(
        panel
            .element_bounds("[data-plugin='dev.rustclient.animsmoothing']")
            .is_some()
    );
    assert!(
        panel
            .element_bounds("[data-plugin='dev.rustclient.roofremoval']")
            .is_none()
    );

    // A decimal setting emits thousandths; a malformed draft emits nothing.
    panel.set_visible(false);
    drop(panel);
    let mut panel = RetainedPanel::new(channel.clone(), [370, 2400], 1.).unwrap();
    panel.set_visible(true);
    panel.render().unwrap();
    click(
        &mut panel,
        "[data-plugin='dev.rustclient.camera'] .card-open",
    );
    type_into(&mut panel, ".decimal .number-input", "2.5");
    let actions = channel.drain();
    assert!(
        actions.iter().any(|action| matches!(
            action,
            ShellAction::Configure { plugin, key, value: ConfigValue::Decimal(2500) }
                if plugin == "dev.rustclient.camera" && key == "cameraSpeed"
        )),
        "decimal edit emitted {actions:?}"
    );
    type_into(&mut panel, ".decimal .number-input", "fast");
    assert!(
        channel.drain().is_empty(),
        "an unparseable decimal must not be applied"
    );
    assert_eq!(
        panel.input_value(".decimal .number-input").as_deref(),
        Some("fast")
    );

    // A text setting emits the entered text.
    click(&mut panel, "[aria-label='Back to plugins']");
    click(
        &mut panel,
        "[data-plugin='dev.rustclient.npchighlight'] .card-open",
    );
    type_into(&mut panel, ".text .text-input", "Goblin, Cow");
    assert!(channel.drain().iter().any(|action| matches!(
        action,
        ShellAction::Configure { plugin, key, value: ConfigValue::Text(text) }
            if plugin == "dev.rustclient.npchighlight" && key == "npcToHighlight" && text == "Goblin, Cow"
    )));
}
