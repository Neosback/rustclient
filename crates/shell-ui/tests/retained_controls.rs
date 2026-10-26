#![cfg(not(target_arch = "wasm32"))]
use rustclient_plugin_host::{
    configuration::{ConfigValue, Configuration, Hotkey},
    required::schemas,
};
use rustclient_shell_ui::{PluginPanel, RetainedPanel, ShellAction, ShellChannel};

/// blitz-paint keeps process-global layer counters, so concurrent panels corrupt each other's painting;
/// the app owns one panel and these tests take turns.
static ONE_PANEL: std::sync::Mutex<()> = std::sync::Mutex::new(());

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
    let _turn = ONE_PANEL
        .lock()
        .unwrap_or_else(std::sync::PoisonError::into_inner);
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
    for _ in 0..3 {
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
    image::save_buffer(
        std::path::Path::new(env!("CARGO_MANIFEST_DIR")).join("../../target/shell-controls.png"),
        panel.pixels(),
        370,
        700,
        image::ColorType::Rgba8,
    )
    .unwrap();
    click(&mut panel, "[role='switch']");
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

/// Every control type renders and emits its typed value; PNGs of each view land in `target/` for review.
#[test]
#[expect(
    clippy::too_many_lines,
    reason = "one scenario walks every control type through a single retained panel"
)]
fn every_control_type_emits_typed_values_and_views_render() {
    let _turn = ONE_PANEL
        .lock()
        .unwrap_or_else(std::sync::PoisonError::into_inner);
    let channel = ShellChannel::default();
    for schema in schemas().unwrap() {
        let config = Configuration::new(schema, None).unwrap();
        channel
            .model
            .borrow_mut()
            .plugins
            .push(PluginPanel::project(&config, |key| {
                (key == "uiEnableCustomChrome")
                    .then(|| "Custom window chrome is not available in this build.".to_owned())
            }));
    }
    let out = std::path::Path::new(env!("CARGO_MANIFEST_DIR")).join("../../target");
    let save = |panel: &RetainedPanel, name: &str| {
        image::save_buffer(
            out.join(name),
            panel.pixels(),
            370,
            700,
            image::ColorType::Rgba8,
        )
        .unwrap();
    };
    let mut panel = RetainedPanel::new(channel.clone(), [370, 700], 1.).unwrap();
    panel.set_visible(true);
    panel.render().unwrap();
    save(&panel, "shell-list.png");
    // Embedded icons are decoded: the rail icon region contains non-background pixels.
    let [x, y, w, h] = panel.element_bounds("#plugins-nav img").unwrap();
    assert!(w > 0. && h > 0., "rail icon has no layout box");
    let distinct = panel
        .pixels()
        .as_chunks::<4>()
        .0
        .iter()
        .collect::<std::collections::HashSet<_>>()
        .len();
    assert!(distinct > 8, "icons did not rasterize ({x},{y})");
    click(
        &mut panel,
        "[data-plugin='dev.rustclient.runelite'] .card-open",
    );
    save(&panel, "shell-runelite.png");
    let scroll = panel.element_bounds("#settings-scroll").unwrap();
    let reveal = |panel: &mut RetainedPanel, selector: &str| {
        for _ in 0..40 {
            if let Some([_, y, _, h]) = panel.element_bounds(selector)
                && y > scroll[1]
                && y + h < scroll[1] + scroll[3]
            {
                return;
            }
            panel.scroll(scroll[0] + 20., scroll[1] + 20., [0., -120.]);
            panel.render();
        }
        panic!("{selector} never scrolled into view");
    };
    let configured = |channel: &ShellChannel, wanted: &str| {
        channel
            .drain()
            .into_iter()
            .find_map(|action| match action {
                ShellAction::Configure { key, value, .. } if key == wanted => Some(value),
                _ => None,
            })
            .unwrap_or_else(|| panic!("no change for {wanted}"))
    };
    let command = if cfg!(target_os = "macos") {
        keyboard_types::Modifiers::SUPER
    } else {
        keyboard_types::Modifiers::CONTROL
    };
    let replace_text = |panel: &mut RetainedPanel, selector: &str, text: &str| {
        reveal(panel, selector);
        click(panel, selector);
        panel.key(
            keyboard_types::Key::Character("a".into()),
            keyboard_types::Code::KeyA,
            command,
            true,
            None,
        );
        panel.ime_commit(text.into());
        panel.render();
    };
    let _ = channel.drain();

    reveal(&mut panel, ".choice-current");
    click(&mut panel, ".choice-current");
    click(&mut panel, ".choice-option:not(.selected)");
    assert!(
        matches!(
            configured(&channel, "automaticResizeType"),
            ConfigValue::Choice(_)
        ),
        "choice option did not emit"
    );

    replace_text(&mut panel, ".dim", "800");
    assert_eq!(
        configured(&channel, "gameSize"),
        ConfigValue::Size([800, 503])
    );
    replace_text(&mut panel, ".dim", "10");
    assert!(
        channel.drain().is_empty(),
        "out-of-range width must not be emitted"
    );

    reveal(&mut panel, ".hotkey-capture");
    click(&mut panel, ".hotkey-capture");
    panel.key(
        keyboard_types::Key::F5,
        keyboard_types::Code::F5,
        keyboard_types::Modifiers::CONTROL,
        true,
        None,
    );
    panel.render();
    assert_eq!(
        configured(&channel, "sidebarToggleKey"),
        ConfigValue::Hotkey(Hotkey {
            key: "F5".into(),
            modifiers: 2
        })
    );

    replace_text(&mut panel, ".hex", "#FF112233");
    assert_eq!(
        configured(&channel, "notificationFlashColor"),
        ConfigValue::Color(0xFF11_2233)
    );

    reveal(&mut panel, ".style-toggle");
    click(&mut panel, ".style-toggle");
    let ConfigValue::Font(font) = configured(&channel, "fontType") else {
        panic!("fontType emitted a non-font value");
    };
    assert!(font.bold && !font.italic);
    save(&panel, "shell-runelite-font.png");
}

#[test]
fn configuration_is_a_compact_panel_left_of_the_tools_and_collapses_without_a_hud() {
    let _turn = ONE_PANEL
        .lock()
        .unwrap_or_else(std::sync::PoisonError::into_inner);
    for scale in [1_u32, 2] {
        let channel = ShellChannel::default();
        for schema in schemas().unwrap() {
            let configuration = Configuration::new(schema, None).unwrap();
            channel
                .model
                .borrow_mut()
                .plugins
                .push(PluginPanel::project(&configuration, |_| None));
        }
        let mut panel = RetainedPanel::new(
            channel.clone(),
            [278 * scale, 600 * scale],
            f32::from(u16::try_from(scale).unwrap()),
        )
        .unwrap();
        panel.set_visible(true);
        panel.render().unwrap();
        let rail = panel.element_bounds(".rail").unwrap();
        let body = panel.element_bounds(".panel").unwrap();
        assert_eq!(rail, [242., 0., 36., 600.]);
        assert_eq!(body, [0., 0., 242., 600.]);
        assert!(
            panel.element_bounds(".card-open .glyph").is_none(),
            "plugin configuration is a flat list, not a grid of icon cards"
        );
        assert_eq!(
            panel.pixels().last(),
            Some(&255),
            "application chrome must fill the entire window height"
        );
        if scale == 1 {
            image::save_buffer(
                std::path::Path::new(env!("CARGO_MANIFEST_DIR"))
                    .join("../../target/sidebar-configuration.png"),
                panel.pixels(),
                278,
                600,
                image::ColorType::Rgba8,
            )
            .unwrap();
        }
        click(&mut panel, "#plugins-nav");
        assert!(channel.drain().contains(&ShellAction::Close));
        channel.model.borrow_mut().panel_visible = false;
        panel
            .resize(
                [36 * scale, 600 * scale],
                f32::from(u16::try_from(scale).unwrap()),
            )
            .unwrap();
        panel.invalidate();
        panel.render().unwrap();
        assert!(panel.element_bounds(".panel").is_none());
        assert_eq!(panel.element_bounds(".rail").unwrap(), [0., 0., 36., 600.]);
        click(&mut panel, "#plugins-nav");
        assert!(channel.drain().contains(&ShellAction::Open));
    }
}
