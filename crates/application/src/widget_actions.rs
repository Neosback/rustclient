//! Validate menu selections against the current displayed widget projection and permissions.
use rustclient_cache::widget::{InterfaceDefinition, LegacyButton};
use rustclient_game_state::GameState;
use rustclient_game_ui::widgets::{WidgetTree, resolve_widget};
use rustclient_model::{InterfaceId, WidgetAction, WidgetAddress, WidgetOperation};
use rustclient_protocol::ClientIntent;
use std::collections::BTreeMap;
/// Current actions for one displayed component/child. Labels remain definition/script authored.
/// # Errors
/// Rejects missing assets and invalid layouts.
pub fn widget_actions(
    state: &GameState,
    assets: &BTreeMap<InterfaceId, InterfaceDefinition>,
    address: WidgetAddress,
) -> Result<Vec<(String, WidgetAction)>, String> {
    let widget = resolve_widget(state.interfaces(), assets, address).map_err(|e| e.to_string())?;
    if widget.hidden || state.authenticated_player().is_none() {
        return Ok(Vec::new());
    }
    let child = address.child.map_or(-1, u32::cast_signed);
    let permissions = state
        .interfaces()
        .permissions(address.widget, child)
        .unwrap_or(widget.permissions);
    if address.child.is_none()
        && let Some(legacy) = assets
            .get(&address.widget.interface)
            .and_then(|group| group.legacy(address.widget))
    {
        let operation = match legacy.button {
            LegacyButton::Click => Some(WidgetOperation::BUTTON_CLICK),
            LegacyButton::Toggle => Some(WidgetOperation::BUTTON_TOGGLE),
            LegacyButton::Select => Some(WidgetOperation::BUTTON_SELECT),
            LegacyButton::Continue => Some(WidgetOperation::CONTINUE),
            // Target selection and close-dialog lifecycle need their own semantic owners.
            LegacyButton::None | LegacyButton::Target | LegacyButton::Close => None,
        };
        return Ok(operation
            .map(|operation| {
                (
                    legacy.button_text.clone(),
                    WidgetAction {
                        address,
                        operation,
                        item: None,
                        interface_epoch: state.interfaces().epoch(),
                    },
                )
            })
            .into_iter()
            .collect());
    }
    let item = state
        .interfaces()
        .runtime()
        .get(&address)
        .and_then(|r| r.item)
        .map(|(id, _)| id);
    let mut actions: Vec<_> = widget
        .actions
        .iter()
        .enumerate()
        .filter(|(i, label)| {
            *i < 32
                && !label.is_empty()
                && (permissions.enabled_operations & (1_u32 << i) != 0
                    || widget
                        .listeners
                        .contains_key(&rustclient_model::WidgetListener::Operation))
        })
        .filter_map(|(i, label)| {
            Some((
                label.clone(),
                WidgetAction {
                    address,
                    operation: WidgetOperation::new(u8::try_from(i).ok()?)?,
                    item,
                    interface_epoch: state.interfaces().epoch(),
                },
            ))
        })
        .collect();
    if permissions.continue_enabled {
        actions.push((
            "Continue".into(),
            WidgetAction {
                address,
                operation: WidgetOperation::CONTINUE,
                item,
                interface_epoch: state.interfaces().epoch(),
            },
        ));
    }
    Ok(actions)
}
/// # Errors
/// Rejects stale topology, removed children, changed items, hidden widgets and revoked operations.
pub fn validate_widget_action(
    state: &GameState,
    assets: &BTreeMap<InterfaceId, InterfaceDefinition>,
    action: WidgetAction,
) -> Result<Option<ClientIntent>, String> {
    if state.interfaces().epoch() != action.interface_epoch {
        return Err("widget selection belongs to an obsolete interface".into());
    }
    let tree = WidgetTree::build(
        state.interfaces(),
        assets,
        rustclient_game_ui::widgets::WidgetBounds::canvas(state.interfaces()),
    )
    .map_err(|e| e.to_string())?;
    if !tree
        .nodes
        .iter()
        .any(|w| w.address == action.address && w.clip.width > 0 && w.clip.height > 0)
    {
        return Err("widget is no longer displayed".into());
    }
    if !widget_actions(state, assets, action.address)?
        .iter()
        .any(|(_, current)| *current == action)
    {
        return Err("widget action or item changed".into());
    }
    let widget =
        resolve_widget(state.interfaces(), assets, action.address).map_err(|e| e.to_string())?;
    let permissions = state
        .interfaces()
        .permissions(
            action.address.widget,
            action.address.child.map_or(-1, u32::cast_signed),
        )
        .unwrap_or(widget.permissions);
    let permitted = if action.operation.is_button() {
        // Authored IF1 buttons do not consult ordinary operation bits (classQP.method9746).
        true
    } else if action.operation.is_continue() {
        permissions.continue_enabled
    } else {
        action
            .operation
            .index()
            .is_some_and(|index| permissions.enabled_operations & (1_u32 << index) != 0)
    };
    Ok(permitted.then_some(ClientIntent::WidgetAction(action)))
}
