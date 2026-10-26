#![cfg(not(target_arch = "wasm32"))]
use rustclient_plugin_host::{
    configuration::{ConfigValue, Configuration},
    required::schemas,
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

#[test]
#[expect(
    clippy::too_many_lines,
    reason = "one end-to-end native DOM scenario shares a single retained panel"
)]
fn native_dom_emits_typed_changes_and_idle_world_frames_reuse_pixels() {
    struct Clipboard(std::cell::RefCell<String>);
    impl blitz_traits::shell::ShellProvider for Clipboard {
        fn get_clipboard_text(&self) -> Result<String, blitz_traits::shell::ClipboardError> {
            Ok(self.0.borrow().clone())
        }
        fn set_clipboard_text(
            &self,
            value: String,
        ) -> Result<(), blitz_traits::shell::ClipboardError> {
            *self.0.borrow_mut() = value;
            Ok(())
        }
    }
    let channel = ShellChannel::default();
    for schema in schemas().unwrap() {
        let config = Configuration::new(schema, None).unwrap();
        channel
            .model
            .borrow_mut()
            .plugins
            .push(PluginPanel::project(&config, |_| None));
    }
    let mut panel = RetainedPanel::new(channel.clone(), [370, 700], 1.).unwrap();
    #[expect(
        clippy::arc_with_non_send_sync,
        reason = "Blitz requires Arc; native DOM and clipboard callbacks stay on the UI thread"
    )]
    let clipboard = std::sync::Arc::new(Clipboard(std::cell::RefCell::new("GPU".into())));
    panel.set_native_services(clipboard.clone());
    assert!(panel.render().is_none(), "hidden panel performed work");
    panel.set_visible(true);
    let pixels = panel.render().unwrap();
    assert_eq!(pixels.len(), 370 * 700 * 4);
    assert!(pixels.as_chunks::<4>().0.iter().any(|pixel| pixel[3] != 0));
    let before = panel.stats();
    for _ in 0..120 {
        assert!(panel.render().is_none());
    }
    assert_eq!(
        panel.stats(),
        before,
        "static panel reran during world frames"
    );
    click(&mut panel, "#plugin-search");
    panel.key(
        keyboard_types::Key::Character("R".into()),
        keyboard_types::Code::KeyR,
        keyboard_types::Modifiers::empty(),
        true,
        Some("R".into()),
    );
    panel.ime_commit("une".into());
    panel.render();
    assert_eq!(panel.input_value("#plugin-search").as_deref(), Some("Rune"));
    assert!(
        panel
            .element_bounds("[data-plugin='dev.rustclient.gpu']")
            .is_none()
    );
    assert!(
        panel
            .element_bounds("[data-plugin='dev.rustclient.runelite']")
            .is_some()
    );
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
    panel.key(
        keyboard_types::Key::Character("v".into()),
        keyboard_types::Code::KeyV,
        command,
        true,
        None,
    );
    panel.render();
    assert_eq!(panel.input_value("#plugin-search").as_deref(), Some("GPU"));
    panel.key(
        keyboard_types::Key::Character("a".into()),
        keyboard_types::Code::KeyA,
        command,
        true,
        None,
    );
    panel.key(
        keyboard_types::Key::Character("c".into()),
        keyboard_types::Code::KeyC,
        command,
        true,
        None,
    );
    assert_eq!(clipboard.0.borrow().as_str(), "GPU");
    panel.clear_focus();
    for _ in 0..2 {
        panel.key(
            keyboard_types::Key::Tab,
            keyboard_types::Code::Tab,
            keyboard_types::Modifiers::empty(),
            true,
            None,
        );
    }
    panel.key(
        keyboard_types::Key::Enter,
        keyboard_types::Code::Enter,
        keyboard_types::Modifiers::empty(),
        true,
        None,
    );
    panel.render();
    eprintln!(
        "checkbox bounds {:?}",
        panel.element_bounds("input[type='checkbox']")
    );
    image::save_buffer(
        std::path::Path::new(env!("CARGO_MANIFEST_DIR")).join("../../target/shell-controls.png"),
        panel.pixels(),
        370,
        700,
        image::ColorType::Rgba8,
    )
    .unwrap();
    click(&mut panel, "input[type='checkbox']");
    let checkbox_actions = channel.drain();
    assert!(checkbox_actions.iter().any(|action| matches!(action,ShellAction::Configure { key,value:ConfigValue::Boolean(false),.. } if key=="hideUnrelatedMaps")), "checkbox actions: {checkbox_actions:?}");
    let [x, y, width, height] = panel.element_bounds("[role='slider']").unwrap();
    panel.pointer(x + width / 2., y + height / 2., Some(true), true);
    panel.pointer(x + width + 200., y + height / 2., None, true);
    panel.pointer(x + width + 200., y + height / 2., Some(false), false);
    let values: Vec<_> = channel
        .drain()
        .into_iter()
        .filter_map(|action| match action {
            ShellAction::Configure {
                key,
                value: ConfigValue::Integer(value),
                ..
            } if key == "drawDistance" => Some(value),
            _ => None,
        })
        .collect();
    assert!(
        values
            .first()
            .is_some_and(|value| (80..=100).contains(value)),
        "slider emitted {values:?}"
    );
    assert_eq!(
        values.last(),
        Some(&184),
        "slider capture did not reach its endpoint: {values:?}"
    );
    panel.render();
    panel.set_visible(false);
    let before = panel.stats();
    panel.invalidate();
    assert!(panel.render().is_none());
    assert_eq!(panel.stats(), before);
}
