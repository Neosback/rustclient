//! Script scheduling and semantic host bindings; never an alternate state store.
mod arrays;
mod database;
#[cfg(test)]
mod gameframe_tests;
#[cfg(test)]
mod input_tests;
mod lifecycle;
#[cfg(test)]
mod lifecycle_tests;
mod queries;
#[cfg(test)]
mod test_support;
mod visuals;
mod wheel;
mod widgets;
use rustclient_cache::{Cache, CacheProfile, script::Script, widget::InterfaceDefinition};
use rustclient_client_script::{Context, Host, Limits, Stacks, execute};
use rustclient_game_state::{GameState, SemanticOperation};
use rustclient_model::{
    FontId, InterfaceId, InterfaceUpdate, ItemDefinitionId, PlayerStatusUpdate, ScriptId,
    ScriptRequest, ScriptValue, WidgetAction, WidgetAddress, WidgetListener,
};
use rustclient_platform::ReadOnlyBlobs;
use std::{
    collections::{BTreeMap, BTreeSet, VecDeque},
    sync::Arc,
};
pub use visuals::{WidgetAssetRequest, load_widget_visuals};
/// Reference `queueSoundEffect` capacity.
const SOUND_EFFECT_LIMIT: usize = 50;
/// A synthesized sound effect requested by `SOUND_SYNTH`, for the audio adapter to mix.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct SoundEffectRequest {
    pub id: u32,
    pub loops: u32,
    pub delay: u32,
}
type Result<T> = std::result::Result<T, String>;
pub struct ClientScripts<S> {
    pub cache: Cache<S>,
    pub profile: CacheProfile,
    pub interfaces: BTreeMap<InterfaceId, InterfaceDefinition>,
    scripts: BTreeMap<ScriptId, Arc<Script>>,
    enums: BTreeMap<u32, rustclient_cache::osrs240::script_tables::ScriptEnum>,
    items: BTreeMap<ItemDefinitionId, rustclient_cache::world::ItemDefinition>,
    loaded_groups: BTreeSet<InterfaceId>,
    layout_sizes: BTreeMap<WidgetAddress, [i32; 2]>,
    /// Interface revision the sizes above were computed from; a match means nothing moved.
    layout_revision: Option<u64>,
    pub modifiers: [bool; 3],
    fonts: BTreeMap<FontId, rustclient_cache::BitmapFont>,
    effects: Vec<rustclient_protocol::ClientIntent>,
    sound_effects: Vec<SoundEffectRequest>,
    db: database::DbState,
    huffman: Option<rustclient_protocol::Huffman>,
    /// Renderer-owned camera pitch and yaw in script units, supplied each frame by the host.
    camera_angles: [i32; 2],
    queue: VecDeque<(ScriptRequest, Option<WidgetAddress>)>,
}
impl<S: ReadOnlyBlobs> ClientScripts<S> {
    #[must_use]
    pub fn new(cache: Cache<S>, profile: CacheProfile) -> Self {
        Self {
            cache,
            profile,
            interfaces: BTreeMap::new(),
            scripts: BTreeMap::new(),
            enums: BTreeMap::new(),
            items: BTreeMap::new(),
            loaded_groups: BTreeSet::new(),
            layout_sizes: BTreeMap::new(),
            layout_revision: None,
            modifiers: [false; 3],
            fonts: BTreeMap::new(),
            effects: Vec::new(),
            sound_effects: Vec::new(),
            db: database::DbState::default(),
            huffman: None,
            camera_angles: [0; 2],
            queue: VecDeque::new(),
        }
    }
    /// Commit network semantic input before scheduling its listeners.
    /// # Errors
    /// Returns state/cache errors; script failures are explicit diagnostics in the result.
    pub fn commit(&mut self, state: &mut GameState, op: SemanticOperation) -> Result<Vec<String>> {
        let notification = match &op {
            SemanticOperation::Interface(
                InterfaceUpdate::Open(_)
                | InterfaceUpdate::Close(_)
                | InterfaceUpdate::Move { .. }
                | InterfaceUpdate::Resync { .. },
            ) => Some((WidgetListener::SubChange, None)),
            SemanticOperation::GameMessage(_) | SemanticOperation::ChatFilters(_) => {
                Some((WidgetListener::Chat, None))
            }
            SemanticOperation::Scenery(rustclient_game_state::SceneryOperation::AdvanceCycles(
                _,
            )) => Some((WidgetListener::Timer, None)),
            SemanticOperation::PlayerStatus(PlayerStatusUpdate::Skill { id, .. }) => {
                Some((WidgetListener::Stat, Some(id.get().cast_signed())))
            }
            SemanticOperation::Scenery(
                rustclient_game_state::SceneryOperation::SynchronizeVariables
                | rustclient_game_state::SceneryOperation::ResetVariables,
            ) => Some((WidgetListener::Variable, None)),
            SemanticOperation::Inventory(v) => {
                Some((WidgetListener::Inventory, Some(v.id().get().cast_signed())))
            }
            SemanticOperation::Scenery(rustclient_game_state::SceneryOperation::SetVariable {
                id,
                ..
            }) => Some((WidgetListener::Variable, Some(id.get().cast_signed()))),
            _ => None,
        };
        let previous_groups = matches!(
            &op,
            SemanticOperation::Interface(
                InterfaceUpdate::Open(_)
                    | InterfaceUpdate::Close(_)
                    | InterfaceUpdate::Move { .. }
                    | InterfaceUpdate::SetRoot(_)
                    | InterfaceUpdate::Resync { .. }
            )
        )
        .then(|| state.interfaces().groups());
        let load_groups = Self::interface_load_groups(state, &op);
        let commit = state.apply_operation(op).map_err(|e| e.to_string())?;
        if state.lifecycle() != rustclient_game_state::SessionLifecycle::Active {
            self.reset();
            return Ok(Vec::new());
        }
        self.synchronize_with_loads(state, &load_groups)?;
        if let Some(previous_groups) = previous_groups {
            let current = state.interfaces().groups();
            for group in previous_groups.difference(&current) {
                if let Some((widget, request)) = self.profile.interface_close_script(*group) {
                    self.enqueue(request, Some(widget))?;
                }
            }
        }

        if !commit.delta().is_empty()
            && let Some((kind, id)) = notification
        {
            self.notify(state, kind, id)?;
        }
        Ok(self.drain(state))
    }
    /// Validate a selection, execute its listener, then check operation permissions.
    /// # Errors
    /// Rejects stale or forbidden actions before producing any network command.
    pub fn perform_action(
        &mut self,
        state: &mut GameState,
        action: WidgetAction,
    ) -> Result<(Vec<rustclient_protocol::ClientIntent>, Vec<String>)> {
        crate::validate_widget_action(state, &self.interfaces, action)?;
        let widget = rustclient_game_ui::widgets::resolve_widget(
            state.interfaces(),
            &self.interfaces,
            action.address,
        )
        .map_err(|e| e.to_string())?;
        if let Some(args) = widget.listeners.get(&WidgetListener::Operation)
            && let Some(mut request) = request(args.clone())?
        {
            for arg in &mut request.arguments {
                if let ScriptValue::Int(n) = arg {
                    *n = self.profile.listener_argument(
                        *n,
                        Some(action.address),
                        i32::from(action.operation.index()) + 1,
                    );
                }
            }
            self.enqueue(request, Some(action.address))?;
        }
        let errors = self.drain(state);
        if !errors.is_empty() {
            self.effects.clear();
            return Err(format!(
                "widget operation listener failed: {}",
                errors.join("; ")
            ));
        }
        let mut intents = std::mem::take(&mut self.effects);
        // classEA.method3815 retains the selected widget across its callback. A
        // dropdown may delete its own rows; that must not cancel this validated
        // button. Permissions may still change during the callback.
        let permissions = state
            .interfaces()
            .permissions(
                action.address.widget,
                action.address.child.map_or(-1, u32::cast_signed),
            )
            .unwrap_or(widget.permissions);
        if state.authenticated_player().is_some()
            && permissions.enabled_operations & (1_u32 << action.operation.index()) != 0
        {
            intents.push(rustclient_protocol::ClientIntent::WidgetAction(action));
        }
        Ok((intents, errors))
    }
    /// Run one widget listener for a pointer or wheel event.
    ///
    /// `None` when the widget has no such listener, so the caller can try the next widget under
    /// the pointer. Script errors come back with the dispatch result like other listeners.
    /// # Errors
    /// Rejects unresolved widgets and malformed listener definitions.
    pub fn dispatch_listener(
        &mut self,
        state: &mut GameState,
        address: WidgetAddress,
        kind: WidgetListener,
        event: ListenerEvent,
    ) -> Result<Option<ListenerOutcome>> {
        if !self.queue_listener(state, address, kind, event)? {
            return Ok(None);
        }
        let errors = self.drain(state);
        let intents = std::mem::take(&mut self.effects);
        Ok(Some(ListenerOutcome { intents, errors }))
    }
    fn queue_listener(
        &mut self,
        state: &GameState,
        address: WidgetAddress,
        kind: WidgetListener,
        event: ListenerEvent,
    ) -> Result<bool> {
        let widget = rustclient_game_ui::widgets::resolve_widget(
            state.interfaces(),
            &self.interfaces,
            address,
        )
        .map_err(|e| e.to_string())?;
        let Some(args) = widget.listeners.get(&kind) else {
            return Ok(false);
        };
        let Some(mut request) = request(args.clone())? else {
            return Ok(false);
        };
        for arg in &mut request.arguments {
            if let ScriptValue::Int(n) = arg {
                *n = event_argument(self.profile, *n, address, event);
            }
        }
        self.enqueue(request, Some(address))?;
        Ok(true)
    }
    /// The widget's scroll-wheel listener, run with the wheel rotation (negative scrolls up,
    /// like Java's `getWheelRotation`).
    /// # Errors
    /// Rejects unresolved widgets and malformed listener definitions.
    pub fn scroll_wheel(
        &mut self,
        state: &mut GameState,
        address: WidgetAddress,
        rotation: i32,
    ) -> Result<Option<ListenerOutcome>> {
        self.dispatch_listener(
            state,
            address,
            WidgetListener::Scroll,
            ListenerEvent {
                mouse: [0, 0],
                wheel: Some(rotation),
                target: None,
                ..ListenerEvent::default()
            },
        )
    }
    /// Deliver a key or committed character to visible cache-authored listeners.
    /// # Errors
    /// Rejects invalid layout or malformed listeners with their ordinary context.
    pub fn keyboard(
        &mut self,
        state: &mut GameState,
        key: Option<rustclient_model::WidgetKey>,
        character: Option<char>,
        pressed: bool,
    ) -> Result<ListenerOutcome> {
        let tree = rustclient_game_ui::widgets::WidgetTree::build(
            state.interfaces(),
            &self.interfaces,
            rustclient_game_ui::widgets::WidgetBounds::canvas(state.interfaces()),
        )
        .map_err(|e| e.to_string())?;
        let code = key.map_or(-1, |key| self.profile.script_key(key));
        let event = ListenerEvent {
            key: Some([
                code,
                character.map_or(0, |ch| i32::try_from(u32::from(ch)).unwrap_or(0)),
            ]),
            ..ListenerEvent::default()
        };
        let mut result = ListenerOutcome::default();
        for node in tree.nodes {
            let kinds: &[WidgetListener] = if pressed {
                if key.is_some() {
                    &[WidgetListener::Key, WidgetListener::KeyPress]
                } else {
                    &[WidgetListener::Key]
                }
            } else {
                &[WidgetListener::KeyRelease]
            };
            for kind in kinds {
                if let Some(outcome) = self.dispatch_listener(state, node.address, *kind, event)? {
                    result.errors.extend(outcome.errors);
                    result.intents.extend(outcome.intents);
                }
            }
        }
        Ok(result)
    }
    /// Publish the camera's pitch and yaw for `CAM_GETANGLE_*`. The camera stays renderer-owned;
    /// this is a disposable projection of it.
    pub fn set_camera_angles(&mut self, pitch: i32, yaw: i32) {
        self.camera_angles = [pitch, yaw];
    }
    /// Queue a chatbox message or `::` command for the server.
    /// # Errors
    /// Rejects text the chat code cannot represent and an exhausted effect budget.
    pub(crate) fn queue_chat(&mut self, chat: rustclient_protocol::ChatIntent) -> Result<()> {
        if self.effects.len() >= 32 {
            return Err("script effect limit".into());
        }
        self.effects
            .push(rustclient_protocol::ClientIntent::Chat(chat));
        Ok(())
    }
    /// Frame a public chat message with the cache's Huffman table, loading it on first use.
    /// # Errors
    /// Fails when the cache has no table or the text cannot be coded.
    pub(crate) fn encode_public_chat(&mut self, text: &str) -> Result<Vec<u8>> {
        if self.huffman.is_none() {
            let table = self
                .cache
                .huffman_sizes()
                .map_err(|e| e.to_string())?
                .ok_or("cache has no chat huffman table")?;
            self.huffman =
                Some(rustclient_protocol::Huffman::from_sizes(&table).map_err(|e| e.to_string())?);
        }
        self.huffman
            .as_ref()
            .ok_or("chat huffman table unavailable")?
            .encode_message(text)
            .map_err(|e| e.to_string())
    }
    pub fn reset(&mut self) {
        self.loaded_groups.clear();
        self.layout_sizes.clear();
        self.layout_revision = None;
        self.queue.clear();
        self.effects.clear();
        self.sound_effects.clear();
        self.db.reset();
    }
    /// Sound effects requested since the last call, in script order.
    pub fn take_sound_effects(&mut self) -> Vec<SoundEffectRequest> {
        std::mem::take(&mut self.sound_effects)
    }
    /// Drain validated outbound effects produced by load/timer/server scripts.
    pub fn take_intents(&mut self) -> Vec<rustclient_protocol::ClientIntent> {
        std::mem::take(&mut self.effects)
    }
    /// Deliver deferred resize listeners after this cycle's network and load scripts.
    /// The size cache is disposable layout metadata, never widget state.
    /// # Errors
    /// Rejects incomplete layouts or malformed resize listeners.
    pub fn resize_widgets(&mut self, state: &mut GameState) -> Result<Vec<String>> {
        // Layout is a pure function of interface state: skip it while nothing has changed,
        // but still deliver queued listener work.
        let revision = state.interfaces().revision();
        if self.layout_revision == Some(revision) {
            return Ok(self.drain(state));
        }
        self.layout_revision = None;
        let tree = rustclient_game_ui::widgets::WidgetTree::attached(
            state.interfaces(),
            &self.interfaces,
            rustclient_game_ui::widgets::WidgetBounds::canvas(state.interfaces()),
        )
        .map_err(|e| e.to_string())?;
        let mut sizes = BTreeMap::new();
        for node in tree.nodes {
            let size = [node.bounds.width, node.bounds.height];
            sizes.insert(node.address, size);
            let widget = rustclient_game_ui::widgets::resolve_widget(
                state.interfaces(),
                &self.interfaces,
                node.address,
            )
            .map_err(|e| e.to_string())?;
            if let rustclient_model::WidgetBody::Container { scroll_size, .. } = widget.body
                && let Some(runtime) = state.interfaces().runtime().get(&node.address)
                && let Some(scroll) = runtime.scroll
            {
                let clamped = [0, 1].map(|axis| {
                    scroll[axis]
                        .min(scroll_size[axis].saturating_sub(size[axis].max(0).cast_unsigned()))
                });
                if clamped != scroll {
                    let mut runtime = runtime.clone();
                    runtime.scroll = Some(clamped);
                    state
                        .apply_operation(SemanticOperation::Interface(
                            InterfaceUpdate::SetProperty {
                                widget: node.id,
                                property: rustclient_model::WidgetProperty::Runtime {
                                    address: node.address,
                                    value: Box::new(runtime),
                                },
                            },
                        ))
                        .map_err(|e| e.to_string())?;
                }
            }
            if self.layout_sizes.get(&node.address) == Some(&size) {
                continue;
            }
            if let Some(args) = widget.listeners.get(&WidgetListener::Resize)
                && let Some(request) = request(args.clone())?
            {
                self.enqueue(request, Some(node.address))?;
            }
        }
        self.layout_sizes = sizes;
        self.layout_revision = Some(revision);
        Ok(self.drain(state))
    }
    /// Schedule server calls and widget listeners in arrival order.
    /// # Errors
    /// Rejects an excessive event queue before allocation.
    pub fn enqueue(&mut self, request: ScriptRequest, widget: Option<WidgetAddress>) -> Result<()> {
        if self.queue.len() >= 4096 {
            return Err("script event queue limit".into());
        }
        self.queue.push_back((request, widget));
        Ok(())
    }
    /// Schedule a committed variable/container notification. Trigger filtering follows cache listeners.
    /// # Errors
    /// Propagates bounded queue or malformed listener failures.
    pub fn notify(
        &mut self,
        state: &GameState,
        kind: WidgetListener,
        changed: Option<i32>,
    ) -> Result<()> {
        let groups = state.interfaces().groups();
        let mut addresses = std::collections::BTreeSet::new();
        for group in &groups {
            if let Some(assets) = self.interfaces.get(group) {
                addresses.extend(
                    assets
                        .widgets()
                        .keys()
                        .copied()
                        .map(WidgetAddress::component),
                );
            }
        }
        addresses.extend(
            state
                .interfaces()
                .runtime()
                .keys()
                .filter(|address| {
                    address.child.is_some() && groups.contains(&address.widget.interface)
                })
                .copied(),
        );
        let mut pending = Vec::new();
        for address in addresses {
            let runtime = state.interfaces().runtime().get(&address);
            let base = self
                .interfaces
                .get(&address.widget.interface)
                .and_then(|group| group.widgets().get(&address.widget));
            let args = runtime.and_then(|r| r.listeners.get(&kind)).map_or_else(
                || {
                    if address.child.is_none() {
                        base.and_then(|w| w.listeners.get(&kind))
                    } else {
                        None
                    }
                },
                Option::as_ref,
            );
            if let Some(args) = args {
                let triggers = runtime.and_then(|r| r.triggers.get(&kind)).map_or_else(
                    || {
                        if address.child.is_none() {
                            base.map_or(&[][..], |w| match kind {
                                WidgetListener::Inventory => w.inventory_triggers.as_slice(),
                                WidgetListener::Variable => w.variable_triggers.as_slice(),
                                WidgetListener::Stat => w.stat_triggers.as_slice(),
                                _ => &[],
                            })
                        } else {
                            &[]
                        }
                    },
                    Vec::as_slice,
                );
                if triggers.is_empty() || changed.is_none_or(|c| triggers.contains(&c)) {
                    pending.push((address, args.clone()));
                }
            }
        }
        for (widget, args) in pending {
            if let Some(request) = request(args)? {
                self.enqueue(request, Some(widget))?;
            }
        }
        Ok(())
    }
    /// Run a bounded event batch. Failures stop that invocation and are returned explicitly.
    pub fn drain(&mut self, state: &mut GameState) -> Vec<String> {
        let mut errors = Vec::new();
        for _ in 0..4096 {
            let Some((mut request, widget)) = self.queue.pop_front() else {
                break;
            };
            if widget.is_some_and(|w| !state.interfaces().groups().contains(&w.widget.interface)) {
                continue;
            }
            if widget.is_some() {
                for arg in &mut request.arguments {
                    if let ScriptValue::Int(n) = arg {
                        *n = self.profile.listener_argument(*n, widget, 0);
                    }
                }
            }
            let mut host = ScriptHost {
                resources: self,
                state,
            };
            if let Err(e) = execute(
                &mut host,
                request,
                Context {
                    active: [widget; 2],
                },
                Limits::default(),
            ) {
                errors.push(e.to_string());
            }
        }
        errors
    }
}
/// What a listener run produced: network intents from the script and any script errors.
#[derive(Debug, Default)]
pub struct ListenerOutcome {
    pub intents: Vec<rustclient_protocol::ClientIntent>,
    pub errors: Vec<String>,
}
/// The pointer state a widget listener sees. Coordinates are relative to the widget the
/// reference measures them from: the widget itself, or its parent for drag listeners.
#[derive(Debug, Clone, Copy, Default)]
pub struct ListenerEvent {
    /// Profile key code and Unicode character, replacing the key event placeholders.
    pub key: Option<[i32; 2]>,
    pub mouse: [i32; 2],
    /// Wheel rotation, which replaces the second pointer coordinate for scroll listeners.
    pub wheel: Option<i32>,
    /// The widget a drag was released over.
    pub target: Option<WidgetAddress>,
}
/// Resolve one listener argument placeholder for an event: `-2147483647` is the pointer x,
/// `-2147483646` the pointer y (or the wheel rotation), `-2147483642/-2147483641` the drop target.
fn event_argument(
    profile: rustclient_cache::CacheProfile,
    argument: i32,
    address: WidgetAddress,
    event: ListenerEvent,
) -> i32 {
    match argument {
        -2_147_483_647 => event.mouse[0],
        -2_147_483_646 => event.wheel.unwrap_or(event.mouse[1]),
        -2_147_483_642 => event
            .target
            .map_or(-1, |t| profile.script_widget_number(t.widget)),
        -2_147_483_641 => event
            .target
            .and_then(|t| t.child)
            .map_or(-1, u32::cast_signed),
        -2_147_483_640 => event.key.map_or(0, |key| key[0]),
        -2_147_483_639 => event.key.map_or(0, |key| key[1]),
        n => profile.listener_argument(n, Some(address), 0),
    }
}
fn request(mut args: Vec<ScriptValue>) -> Result<Option<ScriptRequest>> {
    if args.is_empty() {
        return Ok(None);
    }
    match args.remove(0) {
        ScriptValue::Int(id) if id >= 0 => Ok(Some(ScriptRequest {
            id: ScriptId::new(id.cast_unsigned()),
            arguments: args,
        })),
        ScriptValue::Int(-1) => Ok(None),
        _ => Err("listener has invalid script identity".into()),
    }
}
struct ScriptHost<'a, S> {
    resources: &'a mut ClientScripts<S>,
    state: &'a mut GameState,
}
impl<S: ReadOnlyBlobs> Host for ScriptHost<'_, S> {
    fn script(&mut self, id: ScriptId) -> Result<Arc<Script>> {
        if let Some(script) = self.resources.scripts.get(&id) {
            return Ok(script.clone());
        }
        if self.resources.scripts.len() >= 16_384 {
            return Err("script asset limit".into());
        }
        let script = Arc::new(
            self.resources
                .profile
                .load_script(&self.resources.cache, id)
                .map_err(|e| e.to_string())?,
        );
        self.resources.scripts.insert(id, script.clone());
        Ok(script)
    }
    fn instruction(
        &mut self,
        op: rustclient_cache::script::Opcode,
        n: i32,
        s: &mut Stacks,
        context: &mut Context,
    ) -> Result<()> {
        if widgets::instruction(self, op, n, s, context)?
            || arrays::instruction(self, op, s)?
            || database::instruction(self, op, s)?
            || queries::instruction(self, op, n, s)?
        {
            return Ok(());
        }
        Err(format!("unsupported required instruction {op:?}"))
    }
}
impl<S: ReadOnlyBlobs> ScriptHost<'_, S> {
    fn set_client_variable(&mut self, id: rustclient_model::VarpId, value: i32) -> Result<()> {
        let commit = self
            .state
            .apply_operation(SemanticOperation::Scenery(
                rustclient_game_state::SceneryOperation::SetClientVariable { id, value },
            ))
            .map_err(|e| e.to_string())?;
        if !commit.delta().is_empty() {
            self.resources.notify(
                self.state,
                WidgetListener::Variable,
                Some(id.get().cast_signed()),
            )?;
        }
        Ok(())
    }
    fn apply(&mut self, update: InterfaceUpdate) -> Result<()> {
        self.state
            .apply_operation(SemanticOperation::Interface(update))
            .map(|_| ())
            .map_err(|e| e.to_string())
    }
}

#[cfg(test)]
mod wheel_tests {
    use super::*;
    use rustclient_model::{InterfaceId, WidgetId};

    fn address() -> WidgetAddress {
        WidgetAddress::component(WidgetId {
            interface: InterfaceId::new(161),
            component: 9,
        })
    }

    #[test]
    fn the_wheel_placeholder_carries_the_rotation_and_others_resolve_normally() {
        let profile = rustclient_cache::CacheProfile::OpenRune240;
        let widget = ((161_u32 << 16) | 9).cast_signed();
        for rotation in [-3, 0, 5] {
            let event = ListenerEvent {
                wheel: Some(rotation),
                ..ListenerEvent::default()
            };
            assert_eq!(
                event_argument(profile, -2_147_483_646, address(), event),
                rotation
            );
        }
        let event = ListenerEvent::default();
        assert_eq!(
            event_argument(profile, -2_147_483_645, address(), event),
            widget
        );
        assert_eq!(event_argument(profile, 7, address(), event), 7);
    }

    #[test]
    fn pointer_placeholders_carry_the_widget_relative_position() {
        let profile = rustclient_cache::CacheProfile::OpenRune240;
        let event = ListenerEvent {
            mouse: [31, 7],
            ..ListenerEvent::default()
        };
        assert_eq!(
            event_argument(profile, -2_147_483_647, address(), event),
            31
        );
        assert_eq!(event_argument(profile, -2_147_483_646, address(), event), 7);
        assert_eq!(
            event_argument(profile, -2_147_483_642, address(), event),
            -1
        );
    }
}
