#![cfg(feature = "legacy-native")]
#![cfg(not(target_arch = "wasm32"))]
//! Run explicitly in release mode. Timings are diagnostic; normal tests enforce work contracts.
use keyboard_types::{Code, Key, Modifiers};
use rustclient_plugin_host::{configuration::Configuration, required::all_schemas};
use rustclient_shell_ui::{PluginPanel, RetainedPanel, ShellChannel};
use std::time::Instant;

fn click(panel: &mut RetainedPanel, selector: &str) {
    let [x, y, w, h] = panel.element_bounds(selector).unwrap();
    panel.pointer(x + w / 2., y + h / 2., Some(true), true);
    panel.pointer(x + w / 2., y + h / 2., Some(false), false);
    panel.render();
}
fn trace(
    panel: &mut RetainedPanel,
    label: &str,
    count: usize,
    mut event: impl FnMut(&mut RetainedPanel, usize),
) {
    let before = panel.stats();
    let mut samples = Vec::new();
    for index in 0..count {
        let start = Instant::now();
        event(panel, index);
        panel.render();
        samples.push(start.elapsed().as_secs_f64() * 1000.);
    }
    samples.sort_by(f64::total_cmp);
    println!(
        "{label}: p50={:.4}ms p95={:.4}ms rasterizations={} layouts={}",
        samples[count / 2],
        samples[count * 95 / 100],
        panel.stats().rasterizations - before.rasterizations,
        panel.stats().layouts - before.layouts
    );
}
#[test]
#[ignore = "manual release-mode performance trace"]
#[expect(
    clippy::cast_precision_loss,
    reason = "bounded diagnostic scales and pointer offsets"
)]
fn interaction_trace() {
    for scale in [1_u32, 2] {
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
        let mut panel =
            RetainedPanel::new(channel.clone(), [278 * scale, 600 * scale], scale as f32).unwrap();
        panel.set_visible(true);
        panel.render().unwrap();
        println!("scale={scale}");
        trace(&mut panel, "same-target hover", 600, |panel, index| {
            panel.pointer(50. + (index % 20) as f32, 52., None, false);
        });
        trace(&mut panel, "cross-row hover", 120, |panel, index| {
            panel.pointer(60., if index % 2 == 0 { 55. } else { 85. }, None, false);
        });
        click(&mut panel, "#plugin-search");
        let command = if cfg!(target_os = "macos") {
            Modifiers::SUPER
        } else {
            Modifiers::CONTROL
        };
        // Keep one result mounted while editing, avoiding benchmark setup masquerading as typing.
        trace(&mut panel, "search typing", 40, |panel, index| {
            panel.key(Key::Character("a".into()), Code::KeyA, command, true, None);
            panel.ime_commit(if index % 2 == 0 { "GPU" } else { "GP" }.into());
        });
        click(&mut panel, ".card-open");
        let [x, y, w, h] = panel.element_bounds("[role='slider']").unwrap();
        panel.pointer(x + 5., y + h / 2., Some(true), true);
        panel.render();
        trace(&mut panel, "slider drafts", 120, |panel, index| {
            panel.pointer(x + 1. + (index % 145) as f32, y + h / 2., None, true);
        });
        panel.pointer(x + w, y + h / 2., Some(false), false);
        panel.render();
        println!("slider configure actions={}", channel.drain().len());
        let [sx, sy, _, _] = panel.element_bounds("#settings-scroll").unwrap();
        trace(&mut panel, "settings scrolling", 120, |panel, index| {
            panel.scroll(
                sx + 10.,
                sy + 20.,
                [0., if index % 2 == 0 { -45. } else { 45. }],
            );
        });
    }
}
