#![cfg(not(target_arch = "wasm32"))]
#![allow(clippy::float_cmp)]
use keyboard_types::{Code, Key, Modifiers};
use rustclient_plugin_host::{
    configuration::{ConfigValue, Configuration},
    required::all_schemas,
};
use rustclient_shell_ui::{PluginPanel, RetainedPanel, ShellAction, ShellChannel, SidebarState};

fn click(panel: &mut RetainedPanel, selector: &str) {
    let [x, y, width, height] = panel
        .element_bounds(selector)
        .unwrap_or_else(|| panic!("missing {selector}"));
    panel.pointer(x + width / 2., y + height / 2., Some(true), true);
    panel.pointer(x + width / 2., y + height / 2., Some(false), false);
    panel.render();
}
fn enter(panel: &mut RetainedPanel) {
    panel.key(Key::Enter, Code::Enter, Modifiers::empty(), true, None);
    panel.render();
}
fn search(panel: &mut RetainedPanel, text: &str) {
    click(panel, "#plugin-search");
    panel.key(
        Key::Character("a".into()),
        Code::KeyA,
        if cfg!(target_os = "macos") {
            Modifiers::SUPER
        } else {
            Modifiers::CONTROL
        },
        true,
        None,
    );
    panel.ime_commit(text.into());
    panel.render();
}
fn state(panel: &mut RetainedPanel, channel: &ShellChannel, sidebar: SidebarState, scale: u32) {
    channel.model.borrow_mut().sidebar = sidebar;
    panel
        .resize(
            [sidebar.width().max(1) * scale, 800 * scale],
            f32::from(u16::try_from(scale).unwrap()),
        )
        .unwrap();
    panel.invalidate();
    panel.set_visible(sidebar != SidebarState::Hidden);
    panel.render();
}

#[test]
#[expect(
    clippy::too_many_lines,
    reason = "one native DOM serially exercises state and draft lifecycles"
)]
fn sidebar_restores_navigation_and_drafts_do_not_publish_while_interacting() {
    for scale in [1, 2] {
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
        let mut panel = RetainedPanel::new(
            channel.clone(),
            [278 * scale, 800 * scale],
            f32::from(u16::try_from(scale).unwrap()),
        )
        .unwrap();
        panel.set_visible(true);
        panel.render().unwrap();

        panel.pointer(50., 20., None, false);
        panel.render();
        let before = panel.stats();
        for index in 0_u16..600 {
            panel.pointer(50. + f32::from(index % 10), 20., None, false);
        }
        assert!(panel.render().is_none());
        assert_eq!(
            panel.stats(),
            before,
            "unchanged hover must do zero recurring work"
        );
        assert!(channel.drain().is_empty());

        channel
            .model
            .borrow_mut()
            .pinned
            .insert("dev.rustclient.roofremoval".into());
        panel.invalidate();
        panel.render();
        assert!(
            panel
                .element_bounds("[data-plugin='dev.rustclient.roofremoval']")
                .unwrap()[1]
                < panel
                    .element_bounds("[data-plugin='dev.rustclient.gpu']")
                    .unwrap()[1]
        );
        channel.model.borrow_mut().pinned.clear();
        panel.invalidate();
        panel.render();
        search(&mut panel, "GPU");
        click(&mut panel, ".pin-button");
        assert_eq!(
            channel.drain(),
            vec![ShellAction::ToggleFavorite {
                plugin: "dev.rustclient.gpu".into()
            }]
        );
        channel
            .model
            .borrow_mut()
            .pinned
            .insert("dev.rustclient.gpu".into());
        panel.invalidate();
        panel.render();
        click(&mut panel, "#clear-search");
        assert_eq!(panel.input_value("#plugin-search").as_deref(), Some(""));
        let favorite = panel
            .element_bounds("[data-plugin='dev.rustclient.gpu']")
            .unwrap();
        let other = panel
            .element_bounds("[data-plugin='dev.rustclient.animsmoothing']")
            .unwrap();
        assert!(favorite[1] < other[1], "favorites must sort first");

        search(&mut panel, "GPU");
        click(&mut panel, ".card-open");
        let [x, y, width, height] = panel.element_bounds("[role='slider']").unwrap();
        panel.pointer(x + width / 2., y + height / 2., Some(true), true);
        panel.render();
        assert!(channel.drain().is_empty(), "mousedown must not publish");
        panel.pointer(x + width + 100., y + height / 2., None, true);
        panel.render();
        assert!(channel.drain().is_empty(), "drag must not publish");
        assert_eq!(panel.input_value(".number-input").as_deref(), Some("184"));
        panel.pointer(x + width + 100., y + height / 2., Some(false), false);
        panel.render();
        assert_eq!(
            channel.drain(),
            vec![ShellAction::Configure {
                plugin: "dev.rustclient.gpu".into(),
                key: "drawDistance".into(),
                value: ConfigValue::Integer(184)
            }]
        );
        // Simulate a rejected transaction: the accepted model stays unchanged.
        channel.model.borrow_mut().error = Some("test storage failure".into());
        panel.invalidate();
        panel.render();
        assert_eq!(panel.input_value(".number-input").as_deref(), Some("50"));
        channel.model.borrow_mut().error = None;
        panel.invalidate();
        panel.render();

        let [x, y, width, height] = panel.element_bounds("[role='slider']").unwrap();
        panel.pointer(x + width / 2., y + height / 2., Some(true), true);
        panel.render();
        panel.key(Key::Escape, Code::Escape, Modifiers::empty(), true, None);
        panel.pointer(x + width / 2., y + height / 2., Some(false), false);
        panel.render();
        assert!(
            channel.drain().is_empty(),
            "Escape cancels the captured draft"
        );
        panel.pointer(x + width / 2., y + height / 2., Some(true), true);
        panel.clear_focus();
        panel.render();
        assert!(channel.drain().is_empty(), "capture loss cancels the draft");

        click(&mut panel, ".number-input");
        panel.key(
            Key::Character("a".into()),
            Code::KeyA,
            if cfg!(target_os = "macos") {
                Modifiers::SUPER
            } else {
                Modifiers::CONTROL
            },
            true,
            None,
        );
        panel.ime_commit("72".into());
        panel.render();
        assert!(channel.drain().is_empty(), "numeric typing is draft-only");
        enter(&mut panel);
        assert!(matches!(
            channel.drain().as_slice(),
            [ShellAction::Configure {
                value: ConfigValue::Integer(72),
                ..
            }]
        ));

        // Scrolling only changes paint, and hiding/restoring keeps the selected plugin and offset.
        let [sx, sy, _, _] = panel.element_bounds("#settings-scroll").unwrap();
        let layouts = panel.stats().layouts;
        panel.scroll(sx + 10., sy + 10., [0., -180.]);
        panel.render();
        assert_eq!(panel.stats().layouts, layouts);
        let before = panel.element_bounds("[role='slider']").unwrap()[1];
        click(&mut panel, "#plugins-nav");
        assert_eq!(channel.drain(), vec![ShellAction::Close]);
        state(&mut panel, &channel, SidebarState::Rail, scale);
        assert!(panel.element_bounds(".panel").is_none());
        assert_eq!(panel.element_bounds(".rail").unwrap()[2], 36.);
        state(&mut panel, &channel, SidebarState::Panel, scale);
        assert_eq!(panel.element_bounds("[role='slider']").unwrap()[1], before);
        state(&mut panel, &channel, SidebarState::Hidden, scale);
        let idle = panel.stats();
        panel.pointer(20., 20., None, false);
        panel.update();
        panel.render();
        assert_eq!(
            panel.stats(),
            idle,
            "hidden chrome must do no recurring work"
        );
        state(&mut panel, &channel, SidebarState::Panel, scale);
        assert!(panel.element_bounds("#settings-scroll").is_some());
        click(&mut panel, "[aria-label='Back to plugins']");
        assert_eq!(panel.input_value("#plugin-search").as_deref(), Some("GPU"));
        assert!(
            panel
                .element_bounds("[data-plugin='dev.rustclient.gpu']")
                .is_some()
        );

        // A native title tooltip is actual rendered DOM, not a no-op HTML title attribute.
        let [tx, ty, tw, th] = panel.element_bounds(".card-open").unwrap();
        panel.pointer(tx + tw / 2., ty + th / 2., None, false);
        panel.render();
        assert!(panel.element_bounds("[role='tooltip']").is_some());
        panel.pointer_leave();
        panel.render();
        assert!(panel.element_bounds("[role='tooltip']").is_none());
    }
}
