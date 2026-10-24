//! Authoritative interface topology and mutable component properties.
use rustclient_model::{
    InterfaceId, InterfaceMount, InterfaceUpdate, WidgetEventRange, WidgetId, WidgetPermissions,
    WidgetProperty,
};
use std::collections::{BTreeMap, BTreeSet};

#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum InterfaceError {
    LimitExceeded,
    InvalidRange,
    CyclicMounts,
    InvalidPermissions,
}
impl core::fmt::Display for InterfaceError {
    fn fmt(&self, f: &mut core::fmt::Formatter<'_>) -> core::fmt::Result {
        f.write_str(match self {
            Self::LimitExceeded => "interface update exceeds allocation limits",
            Self::InvalidRange => "interface update has an invalid child range",
            Self::CyclicMounts => "interface update creates cyclic mounts",
            Self::InvalidPermissions => "interface update has invalid target or drag permissions",
        })
    }
}
impl std::error::Error for InterfaceError {}
#[derive(Debug, Default)]
pub struct InterfaceState {
    window: rustclient_model::WindowMetrics,
    default_window_mode: rustclient_model::WindowMode,
    viewport: rustclient_model::ViewportSettings,
    settings: BTreeMap<rustclient_model::ClientSetting, bool>,
    device_preferences: BTreeMap<u8, i32>,
    epoch: u64,
    revision: u64,
    root: Option<InterfaceId>,
    mounts: BTreeMap<WidgetId, InterfaceMount>,
    events: Vec<WidgetEventRange>,
    runtime: BTreeMap<rustclient_model::WidgetAddress, rustclient_model::WidgetRuntime>,
    client_variables: BTreeMap<u32, rustclient_model::ScriptValue>,
}
impl InterfaceState {
    #[must_use]
    pub const fn window(&self) -> rustclient_model::WindowMetrics {
        self.window
    }
    #[must_use]
    pub const fn default_window_mode(&self) -> rustclient_model::WindowMode {
        self.default_window_mode
    }
    #[must_use]
    pub const fn viewport(&self) -> rustclient_model::ViewportSettings {
        self.viewport
    }
    /// Disposable invalidation token, excluded from semantic fingerprints.
    #[must_use]
    pub const fn epoch(&self) -> u64 {
        self.epoch
    }
    /// Disposable change counter, advanced by every committed interface mutation and
    /// excluded from semantic fingerprints. Layout caches compare it to skip unchanged work.
    #[must_use]
    pub const fn revision(&self) -> u64 {
        self.revision
    }
    pub(crate) fn touch(&mut self) {
        self.revision = self.revision.wrapping_add(1);
    }
    pub(crate) fn invalidate(&mut self) {
        self.epoch = self.epoch.wrapping_add(1);
    }
    #[must_use]
    pub const fn root(&self) -> Option<InterfaceId> {
        self.root
    }
    #[must_use]
    pub const fn mounts(&self) -> &BTreeMap<WidgetId, InterfaceMount> {
        &self.mounts
    }
    #[must_use]
    pub fn event_ranges(&self) -> &[WidgetEventRange] {
        &self.events
    }
    #[must_use]
    pub fn permissions(&self, widget: WidgetId, child: i32) -> Option<WidgetPermissions> {
        self.events
            .iter()
            .find(|e| e.widget == widget && e.first <= child && child <= e.last)
            .map(|e| e.permissions)
    }
    /// All groups referenced by topology, including temporarily detached mounts.
    #[must_use]
    pub fn groups(&self) -> BTreeSet<InterfaceId> {
        groups(self.root, &self.mounts)
    }
    #[must_use]
    pub const fn runtime(
        &self,
    ) -> &BTreeMap<rustclient_model::WidgetAddress, rustclient_model::WidgetRuntime> {
        &self.runtime
    }
    #[must_use]
    pub const fn client_variables(&self) -> &BTreeMap<u32, rustclient_model::ScriptValue> {
        &self.client_variables
    }
    #[must_use]
    pub const fn device_preferences(&self) -> &BTreeMap<u8, i32> {
        &self.device_preferences
    }
    #[must_use]
    pub const fn settings(&self) -> &BTreeMap<rustclient_model::ClientSetting, bool> {
        &self.settings
    }
    #[expect(
        clippy::too_many_lines,
        reason = "bounded interface transition grammar validates each candidate before mutation"
    )]
    pub(crate) fn apply(&mut self, update: InterfaceUpdate) -> Result<bool, InterfaceError> {
        match update {
            InterfaceUpdate::Window(value) => {
                if [
                    value.available.width,
                    value.available.height,
                    value.canvas.width,
                    value.canvas.height,
                ]
                .into_iter()
                .any(|n| !(1..=16_384).contains(&n))
                    || value.canvas.width > value.available.width
                    || value.canvas.height > value.available.height
                {
                    return Err(InterfaceError::InvalidRange);
                }
                let changed = self.window != value;
                self.window = value;
                Ok(changed)
            }
            InterfaceUpdate::DefaultWindowMode(value) => {
                let changed = self.default_window_mode != value;
                self.default_window_mode = value;
                Ok(changed)
            }
            InterfaceUpdate::Viewport(value) => {
                if value.fov.contains(&0)
                    || value.zoom.contains(&0)
                    || value.clamp.contains(&0)
                    || value.clamp[0] > value.clamp[1]
                    || value.clamp[2] > value.clamp[3]
                {
                    return Err(InterfaceError::InvalidRange);
                }
                let changed = self.viewport != value;
                self.viewport = value;
                Ok(changed)
            }
            InterfaceUpdate::Setting { key, value } => {
                let changed = self.settings.get(&key) != Some(&value);
                self.settings.insert(key, value);
                Ok(changed)
            }
            InterfaceUpdate::DevicePreference { id, value } => {
                let changed = self.device_preferences.insert(id, value) != Some(value);
                Ok(changed)
            }
            InterfaceUpdate::ClientVariable { id, value } => {
                if self.client_variables.len() >= 16_384 && !self.client_variables.contains_key(&id)
                {
                    return Err(InterfaceError::LimitExceeded);
                }
                if !valid_value(&value) {
                    return Err(InterfaceError::LimitExceeded);
                }
                if self.client_variables.get(&id) == Some(&value) {
                    return Ok(false);
                }
                self.client_variables.insert(id, value);
                Ok(true)
            }
            InterfaceUpdate::CreateChild {
                parent,
                parent_child,
                reject_replace,
                index,
                body,
            } => {
                let address = rustclient_model::WidgetAddress {
                    widget: parent,
                    child: Some(index),
                };
                if index >= 16_384 || self.runtime.len() >= 16_384 {
                    return Err(InterfaceError::LimitExceeded);
                }
                // Reference cc_create grows the child array to any index; its fourth
                // operand only forbids replacing an occupied slot.
                if reject_replace && self.runtime.contains_key(&address) {
                    return Err(InterfaceError::InvalidRange);
                }
                if parent_child == Some(index)
                    || parent_child.is_some_and(|index| {
                        !matches!(
                            self.runtime
                                .get(&rustclient_model::WidgetAddress {
                                    widget: parent,
                                    child: Some(index)
                                })
                                .and_then(|v| v.body.as_ref()),
                            Some(rustclient_model::WidgetBody::Container { .. })
                        )
                    })
                {
                    return Err(InterfaceError::InvalidRange);
                }
                self.validate_parent(address, parent_child)?;
                let value = rustclient_model::WidgetRuntime {
                    parent_child,
                    body: Some(body),
                    layout: Some(rustclient_model::WidgetLayout {
                        offset: [0; 2],
                        size: [0; 2],
                        position_mode: [rustclient_model::PositionMode::Start; 2],
                        size_mode: [rustclient_model::SizeMode::Absolute; 2],
                    }),
                    ..Default::default()
                };
                let changed = self.runtime.get(&address) != Some(&value);
                self.runtime.insert(address, value);
                Ok(changed)
            }
            InterfaceUpdate::CloneChild {
                parent,
                source,
                index,
            } => {
                let old = rustclient_model::WidgetAddress {
                    widget: parent,
                    child: Some(source),
                };
                let address = rustclient_model::WidgetAddress {
                    widget: parent,
                    child: Some(index),
                };
                let value = self
                    .runtime
                    .get(&old)
                    .ok_or(InterfaceError::InvalidRange)?
                    .clone();
                if index >= 16_384
                    || value.parent_child == Some(index)
                    || self.runtime.len() >= 16_384
                {
                    return Err(InterfaceError::LimitExceeded);
                }
                self.validate_parent(address, value.parent_child)?;
                let changed = self.runtime.get(&address) != Some(&value);
                self.runtime.insert(address, value);
                Ok(changed)
            }
            InterfaceUpdate::DeleteChild(address) => {
                if address.child.is_none() {
                    return Err(InterfaceError::InvalidRange);
                }
                let mut removed = std::collections::BTreeSet::from([address
                    .child
                    .ok_or(InterfaceError::InvalidRange)?]);
                loop {
                    let before = removed.len();
                    for (key, value) in &self.runtime {
                        if key.widget == address.widget
                            && value.parent_child.is_some_and(|p| removed.contains(&p))
                            && let Some(child) = key.child
                        {
                            removed.insert(child);
                        }
                    }
                    if before == removed.len() {
                        break;
                    }
                }
                let before = self.runtime.len();
                self.runtime.retain(|key, _| {
                    key.widget != address.widget
                        || key.child.is_none_or(|child| !removed.contains(&child))
                });
                Ok(before != self.runtime.len())
            }
            InterfaceUpdate::DeleteChildren(parent) => {
                let before = self.runtime.len();
                self.runtime
                    .retain(|address, _| address.widget != parent || address.child.is_none());
                Ok(before != self.runtime.len())
            }
            InterfaceUpdate::SetProperty { widget, property } => self.property(widget, property),
            InterfaceUpdate::SetEvents(event) => {
                let mut events = self.events.clone();
                insert_event(&mut events, event)?;
                let changed = self.events != events;
                self.events = events;
                Ok(changed)
            }
            topology => self.topology(topology),
        }
    }
    fn property(
        &mut self,
        widget: WidgetId,
        property: WidgetProperty,
    ) -> Result<bool, InterfaceError> {
        use rustclient_model::{WidgetAddress, WidgetBody};
        let (address, mut value) = match property {
            WidgetProperty::Runtime { address, value } => {
                if address.widget != widget
                    || (address.child.is_some() && !self.runtime.contains_key(&address))
                {
                    return Err(InterfaceError::InvalidRange);
                }
                (address, *value)
            }
            property => {
                let address = WidgetAddress::component(widget);
                let mut value = self.runtime.get(&address).cloned().unwrap_or_default();
                match property {
                    WidgetProperty::Text(text) => value.text = Some(text),
                    WidgetProperty::Hidden(hidden) => value.hidden = Some(hidden),
                    WidgetProperty::ScrollY(y) => {
                        let mut scroll = value.scroll.unwrap_or([0; 2]);
                        scroll[1] = y;
                        value.scroll = Some(scroll);
                    }
                    WidgetProperty::Runtime { .. } => return Err(InterfaceError::InvalidRange),
                }
                (address, value)
            }
        };
        // Mutable text has one owner; the body override retains style only.
        if let Some(WidgetBody::Text { text, .. }) = &mut value.body {
            let text = std::mem::take(text);
            if value.text.is_none() {
                value.text = Some(text);
            }
        }
        if address.child.is_some_and(|c| c >= 16_384) {
            return Err(InterfaceError::InvalidRange);
        }
        self.validate_parent(address, value.parent_child)?;
        if !valid_runtime(&value)
            || (self.runtime.len() >= 16_384 && !self.runtime.contains_key(&address))
        {
            return Err(InterfaceError::LimitExceeded);
        }
        if self.runtime.get(&address) == Some(&value) {
            return Ok(false);
        }
        self.runtime.insert(address, value);
        Ok(true)
    }
    fn validate_parent(
        &self,
        address: rustclient_model::WidgetAddress,
        mut parent: Option<u32>,
    ) -> Result<(), InterfaceError> {
        if address.child.is_none() && parent.is_some() {
            return Err(InterfaceError::InvalidRange);
        }
        let mut visited = BTreeSet::new();
        while let Some(child) = parent {
            if address.child == Some(child) || !visited.insert(child) || visited.len() > 128 {
                return Err(InterfaceError::CyclicMounts);
            }
            let value = self
                .runtime
                .get(&rustclient_model::WidgetAddress {
                    widget: address.widget,
                    child: Some(child),
                })
                .ok_or(InterfaceError::InvalidRange)?;
            if !matches!(
                value.body,
                Some(rustclient_model::WidgetBody::Container { .. })
            ) {
                return Err(InterfaceError::InvalidRange);
            }
            parent = value.parent_child;
        }
        Ok(())
    }
    fn topology(&mut self, update: InterfaceUpdate) -> Result<bool, InterfaceError> {
        // Only small topology/range candidates are copied for atomic validation;
        // component text buffers stay in the authority throughout this transition.
        let mut root = self.root;
        let mut mounts = self.mounts.clone();
        let mut resync_events = None;
        match update {
            InterfaceUpdate::SetRoot(next) => root = next,
            InterfaceUpdate::Open(mount) => {
                mounts.insert(mount.parent, mount);
            }
            InterfaceUpdate::Close(parent) => {
                mounts.remove(&parent);
            }
            InterfaceUpdate::Move {
                source,
                destination,
            } if source != destination => {
                let mount = mounts.remove(&source);
                mounts.remove(&destination);
                if let Some(mut mount) = mount {
                    mount.parent = destination;
                    mounts.insert(destination, mount);
                }
            }
            InterfaceUpdate::Move { .. } => {}
            InterfaceUpdate::Resync {
                root: next,
                mounts: next_mounts,
                events,
            } => {
                if next_mounts.len() > 256 || events.len() > 4096 {
                    return Err(InterfaceError::LimitExceeded);
                }
                root = next;
                mounts.clear();
                for mount in next_mounts {
                    mounts.insert(mount.parent, mount);
                }
                let mut candidate = Vec::new();
                for event in events {
                    insert_event(&mut candidate, event)?;
                }
                resync_events = Some(candidate);
            }
            InterfaceUpdate::Viewport(_)
            | InterfaceUpdate::Window(_)
            | InterfaceUpdate::DefaultWindowMode(_)
            | InterfaceUpdate::Setting { .. }
            | InterfaceUpdate::DevicePreference { .. }
            | InterfaceUpdate::CloneChild { .. }
            | InterfaceUpdate::CreateChild { .. }
            | InterfaceUpdate::DeleteChild(_)
            | InterfaceUpdate::DeleteChildren(_)
            | InterfaceUpdate::ClientVariable { .. }
            | InterfaceUpdate::SetProperty { .. }
            | InterfaceUpdate::SetEvents(_) => {
                return Ok(false);
            }
        }
        validate_mounts(&mounts)?;
        let previous_groups = self.groups();
        let current_groups = groups(root, &mounts);
        let removed: BTreeSet<_> = previous_groups
            .difference(&current_groups)
            .copied()
            .collect();
        let mut changed = root != self.root || mounts != self.mounts;
        if let Some(events) = resync_events {
            changed |= events != self.events;
            self.events = events;
        } else {
            let count = self.events.len();
            self.events
                .retain(|e| !removed.contains(&e.widget.interface));
            changed |= count != self.events.len();
        }
        let runtime_count = self.runtime.len();
        self.runtime
            .retain(|address, _| !removed.contains(&address.widget.interface));
        changed |= runtime_count != self.runtime.len();
        self.root = root;
        self.mounts = mounts;
        Ok(changed)
    }
}
fn groups(
    root: Option<InterfaceId>,
    mounts: &BTreeMap<WidgetId, InterfaceMount>,
) -> BTreeSet<InterfaceId> {
    root.into_iter()
        .chain(mounts.values().map(|m| m.interface))
        .collect()
}
fn validate_mounts(mounts: &BTreeMap<WidgetId, InterfaceMount>) -> Result<(), InterfaceError> {
    if mounts.len() > 256 {
        return Err(InterfaceError::LimitExceeded);
    }
    let mut incoming = BTreeMap::<InterfaceId, usize>::new();
    for mount in mounts.values() {
        incoming.entry(mount.parent.interface).or_default();
        *incoming.entry(mount.interface).or_default() += 1;
    }
    let mut ready: Vec<_> = incoming
        .iter()
        .filter_map(|(id, n)| (*n == 0).then_some(*id))
        .collect();
    let mut visited = 0;
    while let Some(id) = ready.pop() {
        visited += 1;
        for mount in mounts.values().filter(|m| m.parent.interface == id) {
            if let Some(n) = incoming.get_mut(&mount.interface) {
                *n -= 1;
                if *n == 0 {
                    ready.push(mount.interface);
                }
            }
        }
    }
    if visited != incoming.len() {
        return Err(InterfaceError::CyclicMounts);
    }
    Ok(())
}
fn insert_event(
    events: &mut Vec<WidgetEventRange>,
    event: WidgetEventRange,
) -> Result<(), InterfaceError> {
    if event.first < -1 || event.last > 65_534 || event.first > event.last {
        return Err(InterfaceError::InvalidRange);
    }
    if event.permissions.target_mask > 63 || event.permissions.drag_depth > 7 {
        return Err(InterfaceError::InvalidPermissions);
    }
    let mut next = Vec::with_capacity(events.len() + 2);
    for old in events.iter().copied() {
        if old.widget != event.widget || old.last < event.first || old.first > event.last {
            next.push(old);
            continue;
        }
        if old.first < event.first {
            next.push(WidgetEventRange {
                last: event.first - 1,
                ..old
            });
        }
        if old.last > event.last {
            next.push(WidgetEventRange {
                first: event.last + 1,
                ..old
            });
        }
    }
    next.push(event);
    next.sort_by_key(|e| (e.widget, e.first));
    let mut merged: Vec<WidgetEventRange> = Vec::with_capacity(next.len());
    for interval in next {
        if let Some(last) = merged.last_mut()
            && last.widget == interval.widget
            && last.last + 1 == interval.first
            && last.permissions == interval.permissions
        {
            last.last = interval.last;
        } else {
            merged.push(interval);
        }
    }
    if merged.len() > 4096 {
        return Err(InterfaceError::LimitExceeded);
    }
    *events = merged;
    Ok(())
}

fn valid_value(value: &rustclient_model::ScriptValue) -> bool {
    use rustclient_model::ScriptValue;
    match value {
        ScriptValue::Text(s) => s.len() <= 16_384,
        ScriptValue::Integers(v) => v.len() <= 16_384,
        ScriptValue::Strings(v) => {
            v.len() <= 4096 && v.iter().map(String::len).sum::<usize>() <= 65_536
        }
        ScriptValue::Int(_) | ScriptValue::Long(_) => true,
    }
}
fn valid_runtime(value: &rustclient_model::WidgetRuntime) -> bool {
    value.drag.is_none_or(|drag| {
        drag.parent.is_none_or(|parent| {
            parent.widget.interface.get() < 65535
                && parent.widget.component < 65535
                && parent.child.is_none_or(|child| child < 65535)
        })
    }) && value.text.as_ref().is_none_or(|s| s.len() <= 16_384)
        && value.parameters.len() <= 4096
        && value.parameters.values().all(valid_value)
        && value
            .actions
            .as_ref()
            .is_none_or(|a| a.len() <= 32 && a.iter().all(|s| s.len() <= 4096))
        && value.data_text.as_ref().is_none_or(|s| s.len() <= 16_384)
        && value.listeners.len() <= 64
        && value
            .listeners
            .values()
            .flatten()
            .all(|v| v.len() <= 128 && v.iter().all(valid_value))
        && value.triggers.len() <= 64
        && value.triggers.values().all(|v| v.len() <= 4096)
        && value.op_keys.len() <= 11
        && value.op_keys.iter().all(|(slot, keys)| {
            *slot <= rustclient_model::WidgetOpKeys::TARGET_SLOT && keys.keys.len() <= 5
        })
        && match &value.body {
            Some(rustclient_model::WidgetBody::Text { text, .. }) => text.len() <= 16_384,
            _ => true,
        }
        && value.layout.as_ref().is_none_or(|l| {
            l.offset
                .iter()
                .chain(l.size.iter())
                .all(|v| v.unsigned_abs() <= 16_777_216)
        })
}
