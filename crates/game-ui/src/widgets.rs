//! Bounded shared layout of immutable widgets and committed interface state.
use crate::Rect;
use rustclient_cache::widget::{
    InterfaceDefinition, PositionMode, SizeMode, WidgetBody, WidgetDefinition,
};
use rustclient_game_state::InterfaceState;
use rustclient_model::{InterfaceId, WidgetAddress, WidgetId, WidgetPermissions};
use std::collections::BTreeMap;
const LIMIT: usize = 16_384;
const EXTENT: i64 = 16_777_216;
#[derive(Debug, Clone, PartialEq, Eq)]
pub enum WidgetLayoutError {
    MissingInterface(InterfaceId),
    MissingWidget(WidgetId),
    LimitExceeded,
    InvalidViewport,
    CoordinateOverflow,
}
impl core::fmt::Display for WidgetLayoutError {
    fn fmt(&self, f: &mut core::fmt::Formatter<'_>) -> core::fmt::Result {
        write!(f, "interface layout: {self:?}")
    }
}
impl std::error::Error for WidgetLayoutError {}
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct WidgetBounds {
    pub x: i32,
    pub y: i32,
    pub width: i32,
    pub height: i32,
}
impl WidgetBounds {
    #[must_use]
    pub fn canvas(state: &InterfaceState) -> Self {
        let size = state.window().canvas;
        Self {
            x: 0,
            y: 0,
            width: size.width.cast_signed(),
            height: size.height.cast_signed(),
        }
    }
    #[must_use]
    #[expect(
        clippy::cast_precision_loss,
        reason = "layout bounds are explicitly limited to f32 integer precision"
    )]
    pub fn rect(self) -> Rect {
        Rect::new(
            self.x as f32,
            self.y as f32,
            self.width as f32,
            self.height as f32,
        )
    }
    fn intersection(self, other: Self) -> Self {
        let x = self.x.max(other.x);
        let y = self.y.max(other.y);
        Self {
            x,
            y,
            width: (self.x + self.width)
                .min(other.x + other.width)
                .saturating_sub(x)
                .max(0),
            height: (self.y + self.height)
                .min(other.y + other.height)
                .saturating_sub(y)
                .max(0),
        }
    }
}
#[derive(Debug, Clone, Copy)]
pub struct PlacedWidget {
    pub id: WidgetId,
    pub address: WidgetAddress,
    pub bounds: WidgetBounds,
    pub clip: WidgetBounds,
    pub scroll_y: u32,
}
#[derive(Debug, Default)]
pub struct WidgetTree {
    pub nodes: Vec<PlacedWidget>,
}
struct Task {
    address: WidgetAddress,
    origin: [i32; 2],
    parent_size: [i32; 2],
    clip: WidgetBounds,
    depth: u16,
}
#[derive(Clone, Copy, PartialEq, Eq)]
enum Visibility {
    Painted,
    Attached,
}
impl WidgetTree {
    /// Rebuild a disposable layout from the selected root and mounted groups.
    /// Hidden ancestors prune their descendants. No listeners run here.
    /// # Errors
    /// Rejects missing assets, excessive depth/nodes, invalid extents or overflow.
    pub fn build(
        state: &InterfaceState,
        assets: &BTreeMap<InterfaceId, InterfaceDefinition>,
        viewport: WidgetBounds,
    ) -> Result<Self, WidgetLayoutError> {
        Self::build_with_drag(state, assets, viewport, None)
    }
    /// Build the same layout with a disposable drag origin inherited by descendants.
    /// # Errors
    /// Uses the ordinary layout bounds and overflow checks.
    pub fn build_with_drag(
        state: &InterfaceState,
        assets: &BTreeMap<InterfaceId, InterfaceDefinition>,
        viewport: WidgetBounds,
        dragged: Option<crate::pointer::DraggedWidget>,
    ) -> Result<Self, WidgetLayoutError> {
        Self::layout(state, assets, viewport, dragged, Visibility::Painted)
    }
    /// Reflow attached components, including hidden descendants, without painting them.
    /// # Errors
    /// Uses the same bounded layout validation as the paint projection.
    pub fn attached(
        state: &InterfaceState,
        assets: &BTreeMap<InterfaceId, InterfaceDefinition>,
        viewport: WidgetBounds,
    ) -> Result<Self, WidgetLayoutError> {
        Self::layout(state, assets, viewport, None, Visibility::Attached)
    }
    #[expect(
        clippy::too_many_lines,
        reason = "single-pass layout validation and construction"
    )]
    fn layout(
        state: &InterfaceState,
        assets: &BTreeMap<InterfaceId, InterfaceDefinition>,
        viewport: WidgetBounds,
        dragged: Option<crate::pointer::DraggedWidget>,
        visibility: Visibility,
    ) -> Result<Self, WidgetLayoutError> {
        if viewport.width < 0
            || viewport.height < 0
            || [viewport.x, viewport.y, viewport.width, viewport.height]
                .iter()
                .any(|n| i64::from(*n).abs() > EXTENT / 2)
        {
            return Err(WidgetLayoutError::InvalidViewport);
        }
        let Some(root) = state.root() else {
            return Ok(Self::default());
        };
        let mut tasks = Vec::new();
        push_group(
            &mut tasks,
            assets,
            root,
            [viewport.x, viewport.y],
            [viewport.width, viewport.height],
            viewport,
            0,
        )?;
        let mut dynamic_children = BTreeMap::<WidgetAddress, Vec<WidgetAddress>>::new();
        for (address, runtime) in state.runtime() {
            if address.child.is_some() {
                dynamic_children
                    .entry(WidgetAddress {
                        widget: address.widget,
                        child: runtime.parent_child,
                    })
                    .or_default()
                    .push(*address);
            }
        }
        let mut nodes = Vec::new();
        while let Some(task) = tasks.pop() {
            if nodes.len() >= LIMIT || task.depth >= 128 {
                return Err(WidgetLayoutError::LimitExceeded);
            }
            let widget = resolve_widget(state, assets, task.address)?;
            let group = assets
                .get(&widget.id.interface)
                .ok_or(WidgetLayoutError::MissingInterface(widget.id.interface))?;
            let overrides = state.runtime().get(&task.address);
            if widget.hidden && visibility == Visibility::Painted {
                continue;
            }
            let mut bounds = place(&widget, task.origin, task.parent_size)?;
            if let Some(dragged) = dragged.filter(|dragged| dragged.address == task.address) {
                bounds.x = checked(i64::from(dragged.origin[0]))?;
                bounds.y = checked(i64::from(dragged.origin[1]))?;
            }
            let clip = task.clip.intersection(bounds);
            let (child_size, scroll) =
                if let WidgetBody::Container { scroll_size, .. } = widget.body {
                    let effective = [
                        if scroll_size[0] == 0 {
                            bounds.width
                        } else {
                            checked(i64::from(scroll_size[0]))?
                        },
                        if scroll_size[1] == 0 {
                            bounds.height
                        } else {
                            checked(i64::from(scroll_size[1]))?
                        },
                    ];
                    let maximum = [effective[0] - bounds.width, effective[1] - bounds.height]
                        .map(|n| n.max(0).cast_unsigned());
                    let current = overrides.and_then(|v| v.scroll).unwrap_or([0; 2]);
                    (
                        effective,
                        [current[0].min(maximum[0]), current[1].min(maximum[1])],
                    )
                } else {
                    ([bounds.width, bounds.height], [0; 2])
                };
            nodes.push(PlacedWidget {
                id: widget.id,
                address: task.address,
                bounds,
                clip,
                scroll_y: scroll[1],
            });
            let depth = task.depth + 1;
            // Mounted roots follow the owning component's normal children.
            if task.address.child.is_none()
                && let Some(mount) = state.mounts().get(&widget.id)
            {
                push_group(
                    &mut tasks,
                    assets,
                    mount.interface,
                    [bounds.x, bounds.y],
                    child_size,
                    clip,
                    depth,
                )?;
            }
            if matches!(widget.body, WidgetBody::Container { .. }) {
                let origin = [
                    checked(i64::from(bounds.x) - i64::from(scroll[0]))?,
                    checked(i64::from(bounds.y) - i64::from(scroll[1]))?,
                ];
                {
                    for address in dynamic_children
                        .get(&task.address)
                        .into_iter()
                        .flatten()
                        .rev()
                    {
                        tasks.push(Task {
                            address: *address,
                            origin,
                            parent_size: child_size,
                            clip,
                            depth,
                        });
                    }
                    if task.address.child.is_none() {
                        for child in group.children(Some(widget.id)).iter().rev() {
                            tasks.push(Task {
                                address: WidgetAddress::component(*child),
                                origin,
                                parent_size: child_size,
                                clip,
                                depth,
                            });
                        }
                    }
                }
            }
            if tasks.len() > LIMIT {
                return Err(WidgetLayoutError::LimitExceeded);
            }
        }
        Ok(Self { nodes })
    }
}
/// Resolve widget-local aligned geometry, including hidden/detached components.
/// Script geometry queries inspect the widget, not only visible paint nodes.
/// # Errors
/// Rejects invalid/missing definitions, cyclic ancestry or coordinate overflow.
pub fn widget_bounds(
    state: &InterfaceState,
    assets: &BTreeMap<InterfaceId, InterfaceDefinition>,
    address: WidgetAddress,
    viewport: WidgetBounds,
) -> Result<WidgetBounds, WidgetLayoutError> {
    let mut ancestry = Vec::new();
    let mut next = Some(address);
    while let Some(address) = next {
        if ancestry.len() >= 128 || ancestry.iter().any(|(a, _)| *a == address) {
            return Err(WidgetLayoutError::LimitExceeded);
        }
        let widget = resolve_widget(state, assets, address)?;
        next = parent_address(state, address, &widget);
        ancestry.push((address, widget));
    }
    let mut parent = [viewport.width, viewport.height];
    let mut bounds = viewport;
    for (_, widget) in ancestry.iter().rev() {
        bounds = place(widget, [0, 0], parent)?;
        parent = [bounds.width, bounds.height];
        if let WidgetBody::Container { scroll_size, .. } = widget.body {
            for axis in 0..2 {
                if scroll_size[axis] != 0 {
                    parent[axis] = checked(i64::from(scroll_size[axis]))?;
                }
            }
        }
    }
    Ok(bounds)
}

/// Logical ancestry includes dynamic children and mounted interface roots.
#[must_use]
pub fn parent_address(
    state: &InterfaceState,
    address: WidgetAddress,
    widget: &WidgetDefinition,
) -> Option<WidgetAddress> {
    if address.child.is_some() {
        Some(WidgetAddress {
            widget: address.widget,
            child: state
                .runtime()
                .get(&address)
                .and_then(|runtime| runtime.parent_child),
        })
    } else if let Some(parent) = widget.parent {
        Some(WidgetAddress::component(parent))
    } else {
        state
            .mounts()
            .iter()
            .find(|(_, mount)| mount.interface == address.widget.interface)
            .map(|(parent, _)| WidgetAddress::component(*parent))
    }
}

fn push_group(
    tasks: &mut Vec<Task>,
    assets: &BTreeMap<InterfaceId, InterfaceDefinition>,
    id: InterfaceId,
    origin: [i32; 2],
    parent_size: [i32; 2],
    clip: WidgetBounds,
    depth: u16,
) -> Result<(), WidgetLayoutError> {
    let group = assets
        .get(&id)
        .ok_or(WidgetLayoutError::MissingInterface(id))?;
    for child in group.children(None).iter().rev() {
        tasks.push(Task {
            address: WidgetAddress::component(*child),
            origin,
            parent_size,
            clip,
            depth,
        });
    }
    Ok(())
}
fn checked(value: i64) -> Result<i32, WidgetLayoutError> {
    if value.abs() > EXTENT {
        return Err(WidgetLayoutError::CoordinateOverflow);
    }
    i32::try_from(value).map_err(|_| WidgetLayoutError::CoordinateOverflow)
}
fn place(
    widget: &WidgetDefinition,
    origin: [i32; 2],
    parent: [i32; 2],
) -> Result<WidgetBounds, WidgetLayoutError> {
    let mut size = [0_i64; 2];
    for axis in 0..2 {
        let raw = i64::from(widget.layout.size[axis]);
        let parent = i64::from(parent[axis]);
        size[axis] = match widget.layout.size_mode[axis] {
            SizeMode::Absolute => raw,
            SizeMode::ParentMinus => parent - raw,
            SizeMode::Proportional => (raw * parent) >> 14,
            SizeMode::Aspect => 0,
        };
    }
    // Reference components default to a 1:1 aspect ratio; scripts may override
    // that ratio once the VM supplies authoritative runtime geometry properties.
    if widget.layout.size_mode[0] == SizeMode::Aspect {
        size[0] = size[1];
    }
    if widget.layout.size_mode[1] == SizeMode::Aspect {
        size[1] = size[0];
    }
    let mut position = [0; 2];
    for axis in 0..2 {
        let raw = i64::from(widget.layout.offset[axis]);
        let parent = i64::from(parent[axis]);
        let value = match widget.layout.position_mode[axis] {
            PositionMode::Start => raw,
            PositionMode::Center => raw + (parent - size[axis]) / 2,
            PositionMode::End => parent - size[axis] - raw,
            PositionMode::ProportionalStart => (raw * parent) >> 14,
            PositionMode::ProportionalCenter => ((raw * parent) >> 14) + (parent - size[axis]) / 2,
            PositionMode::ProportionalEnd => parent - size[axis] - ((raw * parent) >> 14),
        };
        position[axis] = checked(i64::from(origin[axis]) + value)?;
    }
    Ok(WidgetBounds {
        x: position[0],
        y: position[1],
        width: checked(size[0])?,
        height: checked(size[1])?,
    })
}

/// Resolve a disposable effective widget from immutable defaults plus committed overrides.
/// # Errors
/// Rejects absent assets or deleted dynamic children.
pub fn resolve_widget(
    state: &InterfaceState,
    assets: &BTreeMap<InterfaceId, InterfaceDefinition>,
    address: WidgetAddress,
) -> Result<WidgetDefinition, WidgetLayoutError> {
    let base = assets
        .get(&address.widget.interface)
        .ok_or(WidgetLayoutError::MissingInterface(
            address.widget.interface,
        ))?
        .widgets()
        .get(&address.widget)
        .ok_or(WidgetLayoutError::MissingWidget(address.widget))?;
    let mut value = base.clone();
    if address.child.is_some() {
        if !state.runtime().contains_key(&address) {
            return Err(WidgetLayoutError::MissingWidget(address.widget));
        }
        value.parent = Some(address.widget);
        value.content = rustclient_model::WidgetContent::None;
        value.hidden = false;
        value.actions.clear();
        value.listeners.clear();
        value.permissions = WidgetPermissions::default();
        value.data_text.clear();
    }
    if let Some(overrides) = state.runtime().get(&address) {
        if let Some(layout) = overrides.layout {
            value.layout = layout;
        }
        if let Some(body) = &overrides.body {
            let default_text = if let WidgetBody::Text { text, .. } = &value.body {
                text.clone()
            } else {
                String::new()
            };
            value.body = body.clone();
            if let WidgetBody::Text { text, .. } = &mut value.body {
                *text = default_text;
            }
        }
        if let (Some(text), WidgetBody::Text { text: target, .. }) =
            (&overrides.text, &mut value.body)
        {
            target.clone_from(text);
        }
        if let Some(hidden) = overrides.hidden {
            value.hidden = hidden;
        }
        if let Some(actions) = &overrides.actions {
            value.actions.clone_from(actions);
        }
        if let Some(text) = &overrides.data_text {
            value.data_text.clone_from(text);
        }
        for (listener, args) in &overrides.listeners {
            if let Some(args) = args {
                value.listeners.insert(*listener, args.clone());
            } else {
                value.listeners.remove(listener);
            }
        }
    }
    Ok(value)
}
