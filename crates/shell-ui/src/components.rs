use crate::{SettingView, ShellAction, ShellChannel};
use dioxus::prelude::*;
use rustclient_plugin_host::configuration::{ConfigValue, Control};

pub(crate) const CSS: &str = include_str!("shell.css");

#[component]
pub fn Shell(channel: ShellChannel) -> Element {
    let mut query = use_signal(String::new);
    let mut selected = use_signal(|| None::<String>);
    let snapshot = channel.model.borrow().clone();
    let current = selected
        .read()
        .as_ref()
        .and_then(|id| snapshot.plugins.iter().find(|plugin| plugin.id == *id));
    let close = channel.clone();
    let open_list = channel.clone();
    rsx! {
        div { id: "shell", class: "shell",
            nav { class: "rail",
                button { id: "plugins-nav", title: "Plugin configuration", onclick: move |_| { selected.set(None); open_list.send(ShellAction::Open); }, "CFG" }
                for plugin in &snapshot.plugins {
                    { let id = plugin.id.clone(); let open = channel.clone(); rsx! { button { title: "{plugin.name}", onclick: move |_| { selected.set(Some(id.clone())); open.send(ShellAction::Open); }, "{plugin.name}" } } }
                }
                button { id: "close-shell", title: "Close sidebar", onclick: move |_| close.send(ShellAction::Close), "×" }
            }
            if snapshot.panel_visible { main { class: "panel",
                if let Some(error) = &snapshot.error { p { class: "error", "{error}" } }
                if let Some(plugin) = current {
                    header { button { onclick: move |_| selected.set(None), "‹" } h1 { "{plugin.name}" } span { class: "badge", "Always on" } }
                    { let channel = channel.clone(); let id = plugin.id.clone(); rsx! {
                        button { class: "reset", onclick: move |_| channel.send(ShellAction::Reset { plugin: id.clone() }), "Reset settings" }
                    } }
                    div { class: "settings", id: "settings-scroll",
                        for section in plugin.settings.iter().map(|setting| &setting.descriptor.section).collect::<std::collections::BTreeSet<_>>() {
                            Section { key: "{section}", title: section.clone(),
                                rows: plugin.settings.iter().filter(|setting| setting.descriptor.section == *section).cloned().collect(),
                                plugin: plugin.id.clone(), channel: channel.clone() }
                        }
                    }
                } else {
                    header { h1 { "Plugins" } }
                    input { id: "plugin-search", placeholder: "Search plugins…", value: "{query}", oninput: move |event| query.set(event.value()) }
                    for plugin in snapshot.plugins.iter().filter(|plugin| plugin.name.to_lowercase().contains(&query.read().to_lowercase())) {
                        { let id = plugin.id.clone(); rsx! {
                            button { class: "plugin-card", "data-plugin": "{plugin.id}", onclick: move |_| selected.set(Some(id.clone())),
                                strong { "{plugin.name}" } span { class: "badge", "Always on" }
                            }
                        } }
                    }
                }
            } }
        }
    }
}

#[component]
fn Section(
    title: String,
    rows: Vec<SettingView>,
    plugin: String,
    channel: ShellChannel,
) -> Element {
    let mut expanded = use_signal(|| true);
    rsx! {
        section {
            button { class: "section", onclick: move |_| { let value = !*expanded.read(); expanded.set(value); }, "{title}" }
            if *expanded.read() {
                for row in rows { SettingControl { key: "{row.descriptor.key}", row, plugin: plugin.clone(), channel: channel.clone() } }
            }
        }
    }
}

#[component]
fn SettingControl(row: SettingView, plugin: String, channel: ShellChannel) -> Element {
    let key = row.descriptor.key.clone();
    let disabled = row.unavailable.is_some();
    let pointer_channel = channel.clone();
    let emit = move |value| {
        channel.send(ShellAction::Configure {
            plugin: plugin.clone(),
            key: key.clone(),
            value,
        });
    };
    rsx! {
        div { class: "setting", title: "{row.descriptor.description}",
            label { "{row.descriptor.label}" }
            match (&row.descriptor.control, &row.value) {
                (Control::Boolean, ConfigValue::Boolean(value)) => rsx! {
                    input { r#type: "checkbox", checked: *value, disabled: disabled.then_some(true), oninput: move |event| emit(ConfigValue::Boolean(event.checked())) }
                },
                (Control::Integer { min, max }, ConfigValue::Integer(value)) => rsx! {
                    IntegerControl { min: *min, max: *max, value: *value, disabled, channel: pointer_channel, onchange: move |value| emit(ConfigValue::Integer(value)) }
                },
                (Control::Choice { options }, ConfigValue::Choice(value)) => rsx! {
                    ChoiceControl { options: options.clone(), value: value.clone(), disabled, onchange: move |value| emit(ConfigValue::Choice(value)) }
                },
                _ => rsx! { span { class: "value", "{row.value:?}" } },
            }
            if let Some(reason) = &row.unavailable { small { class: "unavailable", "{reason}" } }
        }
    }
}

#[component]
fn IntegerControl(
    min: i32,
    max: i32,
    value: i32,
    disabled: bool,
    channel: ShellChannel,
    onchange: EventHandler<i32>,
) -> Element {
    let update = EventHandler::new(move |event: MouseEvent| {
        if disabled {
            return;
        }
        #[cfg(not(target_arch = "wasm32"))]
        let element_x = event.client_coordinates().x - channel.pointer_origin_x.get().unwrap_or(0.);
        #[cfg(target_arch = "wasm32")]
        let element_x = event.element_coordinates().x;
        // The track's one-pixel borders must not make either endpoint unreachable.
        let fraction = ((element_x - 1.) / 128.).clamp(0., 1.);
        let value = f64::from(min) + fraction * (f64::from(max) - f64::from(min));
        #[expect(
            clippy::cast_possible_truncation,
            reason = "clamped to the i32 descriptor range"
        )]
        onchange.call(value.round() as i32);
    });
    let percent =
        100. * (f64::from(value) - f64::from(min)) / (f64::from(max) - f64::from(min)).max(1.);
    rsx! {
        div { class: "number",
            div { class: "slider", role: "slider", tabindex: if disabled { "-1" } else { "0" },
                "aria-valuemin": "{min}", "aria-valuemax": "{max}", "aria-valuenow": "{value}",
                onmousedown: update,
                onmousemove: move |event| { if !event.held_buttons().is_empty() { update.call(event); } },
                onkeydown: move |event| { if !disabled { match event.key() {
                    Key::ArrowLeft | Key::ArrowDown => onchange.call(value.saturating_sub(1).max(min)),
                    Key::ArrowRight | Key::ArrowUp => onchange.call(value.saturating_add(1).min(max)),
                    Key::Home => onchange.call(min), Key::End => onchange.call(max), _ => {}
                } } },
                div { class: "slider-fill", style: "width:{percent}%" }
            }
            input { class: "number-input", r#type: "text", value: "{value}", disabled: disabled.then_some(true),
                oninput: move |event| { if let Ok(value) = event.value().parse::<i32>() { onchange.call(value.clamp(min,max)); } } }
        }
    }
}

#[component]
fn ChoiceControl(
    options: Vec<String>,
    value: String,
    disabled: bool,
    onchange: EventHandler<String>,
) -> Element {
    let mut expanded = use_signal(|| false);
    rsx! {
        div { class: "choice",
            button { disabled: disabled.then_some(true), onclick: move |_| { let value = !*expanded.read(); expanded.set(value); }, "{value} ▾" }
            if *expanded.read() {
                for option in options { button { class: "choice-option", onclick: move |_| { onchange.call(option.clone()); expanded.set(false); }, "{option}" } }
            }
        }
    }
}
