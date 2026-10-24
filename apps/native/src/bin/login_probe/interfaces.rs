//! Disposable-account input checks through the actual 240 cache listeners.
use rustclient_application::{
    ConnectionDecoder,
    scripts::{ClientScripts, ListenerEvent},
};
use rustclient_game_state::GameState;
use rustclient_game_ui::{
    pointer::drag_position,
    widgets::{WidgetBounds, WidgetTree, resolve_widget},
};
use rustclient_model::{InterfaceId, WidgetAddress, WidgetId, WidgetListener};
use rustclient_platform::ByteStream;
use rustclient_platform_native::{DirectoryBlobs, NativeTcp};
type Result<T> = std::result::Result<T, Box<dyn std::error::Error>>;
#[derive(Default)]
pub struct Review {
    phase: usize,
    pub price_opened: bool,
    pub frame_only: bool,
    pub completed: bool,
}
impl Review {
    #[expect(
        clippy::too_many_lines,
        reason = "bounded diagnostic sequence through authored interfaces"
    )]
    pub fn step(
        &mut self,
        state: &mut GameState,
        scripts: &mut ClientScripts<DirectoryBlobs>,
        decoder: &mut ConnectionDecoder,
        stream: &mut NativeTcp,
    ) -> Result<()> {
        if state.current_tick().is_none_or(|tick| tick.get() < 3)
            || self.completed
            || (!self.frame_only && self.phase >= 10)
        {
            return Ok(());
        }
        let tree = WidgetTree::build(
            state.interfaces(),
            &scripts.interfaces,
            WidgetBounds::canvas(state.interfaces()),
        )?;
        if self.phase == 2 {
            let address = WidgetAddress::component(WidgetId::new(InterfaceId::new(116), 57));
            let Some(node) = tree.nodes.iter().find(|node| node.address == address) else {
                return Ok(());
            };
            let widget = resolve_widget(state.interfaces(), &scripts.interfaces, address)?;
            let parent_address = rustclient_game_ui::pointer::drag_parent(
                state.interfaces(),
                &scripts.interfaces,
                address,
            )?
            .ok_or("zoom drag parent absent")?;
            let parent = *tree
                .nodes
                .iter()
                .find(|node| node.address == parent_address)
                .ok_or("zoom drag parent not displayed")?;
            println!(
                "zoom parent {:?}, permissions {:?}",
                parent.address, widget.permissions
            );
            let mouse = drag_position(
                node.bounds,
                parent,
                [0, 0],
                [8, 8],
                [
                    parent.bounds.x + parent.bounds.width - 4,
                    parent.bounds.y + 8,
                ],
            );
            let before = state.interfaces().viewport().fov;
            for kind in [WidgetListener::Drag, WidgetListener::DragComplete] {
                let result = scripts
                    .dispatch_listener(
                        state,
                        address,
                        kind,
                        ListenerEvent {
                            mouse,
                            ..Default::default()
                        },
                    )?
                    .ok_or("zoom drag listener absent")?;
                if !result.errors.is_empty() {
                    return Err(result.errors.join("; ").into());
                }
                for intent in result.intents {
                    send(decoder, stream, intent)?;
                }
            }
            if state.interfaces().viewport().fov == before {
                return Err("zoom drag did not change viewport FOV".into());
            }
            println!(
                "zoom drag verified: {before:?} -> {:?}",
                state.interfaces().viewport().fov
            );
            click_dropdown(state, scripts, decoder, stream)?;
            self.phase = 3;
            return Ok(());
        }
        if self.phase == 3 || self.phase == 5 {
            let fixed = self.phase == 3;
            select_layout(
                state,
                scripts,
                decoder,
                stream,
                if fixed {
                    "Fixed - Classic layout"
                } else {
                    "Resizable - Classic layout"
                },
            )?;
            self.phase += 1;
            return Ok(());
        }
        if self.phase == 4 {
            if state.interfaces().window().mode != rustclient_model::WindowMode::Fixed
                || state.interfaces().root() != Some(InterfaceId::new(548))
            {
                return Ok(());
            }
            println!(
                "fixed mode committed with root {:?} and canvas {:?}",
                state.interfaces().root(),
                state.interfaces().window().canvas
            );
            click_dropdown(state, scripts, decoder, stream)?;
            self.phase = 5;
            return Ok(());
        }
        if self.phase == 6 {
            if state.interfaces().window().mode != rustclient_model::WindowMode::Resizable
                || state.interfaces().root() != Some(InterfaceId::new(161))
            {
                return Ok(());
            }
            println!("fixed and resizable choices verified through authored dropdown listeners");
            if self.frame_only {
                click_dropdown(state, scripts, decoder, stream)?;
                self.phase = 11;
                return Ok(());
            }
            self.phase = 7;
        }
        if self.phase == 11 {
            select_layout(state, scripts, decoder, stream, "Resizable - Modern layout")?;
            self.phase = 12;
            return Ok(());
        }
        if self.phase == 12 {
            if state.interfaces().root() != Some(InterfaceId::new(164)) {
                return Ok(());
            }
            let metrics = state.interfaces().window();
            if metrics.canvas != metrics.available {
                return Err("resizable gameframe did not use the host canvas".into());
            }
            let viewport = tree
                .nodes
                .iter()
                .find(|node| {
                    resolve_widget(state.interfaces(), &scripts.interfaces, node.address)
                        .is_ok_and(|w| w.content == rustclient_model::WidgetContent::WorldViewport)
                })
                .ok_or("gameframe viewport absent")?;
            println!(
                "modern resizable root 164, canvas {:?}, viewport {:?}",
                metrics.canvas, viewport.bounds
            );
            self.completed = true;
            return Ok(());
        }
        if self.phase == 9 {
            if state.interfaces().groups().contains(&InterfaceId::new(464)) {
                self.price_opened = true;
                self.phase = 10;
                println!(
                    "guide prices mounted; keeping it open to separate client input checks from the known server close failure"
                );
                if std::env::var_os("RUSTCLIENT_REVIEW_CLOSE_PRICES").is_some() {
                    send(
                        decoder,
                        stream,
                        rustclient_protocol::ClientIntent::CloseModal,
                    )?;
                }
            }
            return Ok(());
        }
        let (needle, group) = match self.phase {
            0 => (
                "Settings",
                state.interfaces().root().map_or(161, InterfaceId::get),
            ),
            1 => ("Display", 116),
            7 => (
                "Worn Equipment",
                state.interfaces().root().map_or(161, InterfaceId::get),
            ),
            _ => ("View guide prices", 387),
        };
        for node in tree.nodes {
            if node.id.interface.get() != group {
                continue;
            }
            for (label, action) in
                rustclient_application::widget_actions(state, &scripts.interfaces, node.address)?
            {
                if !label.contains(needle) {
                    continue;
                }
                println!("interface review action: {label} at {:?}", action.address);
                let (intents, errors) = scripts.perform_action(state, action)?;
                if !errors.is_empty() {
                    return Err(errors.join("; ").into());
                }
                for intent in intents {
                    send(decoder, stream, intent)?;
                }
                self.phase += 1;
                return Ok(());
            }
        }
        Ok(())
    }
}
fn send(
    decoder: &mut ConnectionDecoder,
    stream: &mut NativeTcp,
    intent: rustclient_protocol::ClientIntent,
) -> Result<()> {
    stream.deadline_after(std::time::Duration::from_secs(2));
    pollster::block_on(stream.send(&decoder.encode(intent)?))?;
    Ok(())
}

fn click_dropdown(
    state: &mut GameState,
    scripts: &mut ClientScripts<DirectoryBlobs>,
    decoder: &mut ConnectionDecoder,
    stream: &mut NativeTcp,
) -> Result<()> {
    click(
        state,
        scripts,
        decoder,
        stream,
        WidgetAddress {
            widget: WidgetId::new(InterfaceId::new(116), 27),
            child: Some(0),
        },
    )
}
fn click(
    state: &mut GameState,
    scripts: &mut ClientScripts<DirectoryBlobs>,
    decoder: &mut ConnectionDecoder,
    stream: &mut NativeTcp,
    address: WidgetAddress,
) -> Result<()> {
    let outcome = scripts
        .dispatch_listener(
            state,
            address,
            WidgetListener::Click,
            ListenerEvent::default(),
        )?
        .ok_or("dropdown click listener absent")?;
    if !outcome.errors.is_empty() {
        return Err(outcome.errors.join("; ").into());
    }
    for intent in outcome.intents {
        send(decoder, stream, intent)?;
    }
    Ok(())
}
fn select_layout(
    state: &mut GameState,
    scripts: &mut ClientScripts<DirectoryBlobs>,
    decoder: &mut ConnectionDecoder,
    stream: &mut NativeTcp,
    label: &str,
) -> Result<()> {
    let tree = WidgetTree::build(
        state.interfaces(),
        &scripts.interfaces,
        WidgetBounds::canvas(state.interfaces()),
    )?;
    for node in tree.nodes.iter().rev() {
        if node.id.interface.get() != 116 || node.clip.width <= 0 || node.clip.height <= 0 {
            continue;
        }
        let widget = resolve_widget(state.interfaces(), &scripts.interfaces, node.address)?;
        let rustclient_model::WidgetBody::Text { text, .. } = &widget.body else {
            continue;
        };
        if !text.contains(label) {
            continue;
        }
        let mut address = node.address;
        for _ in 0..8 {
            let widget = resolve_widget(state.interfaces(), &scripts.interfaces, address)?;
            if let Some((_, action)) =
                rustclient_application::widget_actions(state, &scripts.interfaces, address)?
                    .into_iter()
                    .next()
            {
                println!("interface review layout choice: {label}");
                let (intents, errors) = scripts.perform_action(state, action)?;
                if !errors.is_empty() {
                    return Err(errors.join("; ").into());
                }
                for intent in intents {
                    send(decoder, stream, intent)?;
                }
                return Ok(());
            }
            if widget.listeners.contains_key(&WidgetListener::Click) {
                println!("interface review layout choice: {label}");
                return click(state, scripts, decoder, stream, address);
            }
            let Some(parent) =
                rustclient_game_ui::widgets::parent_address(state.interfaces(), address, &widget)
            else {
                break;
            };
            address = parent;
        }
    }
    Err(format!("authored dropdown choice {label} not found").into())
}
