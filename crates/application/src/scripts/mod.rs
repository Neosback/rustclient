//! Script scheduling and semantic host bindings; never an alternate state store.
mod queries;
mod visuals;
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
type Result<T> = std::result::Result<T, String>;
pub struct ClientScripts<S> {
    pub cache: Cache<S>,
    pub profile: CacheProfile,
    pub interfaces: BTreeMap<InterfaceId, InterfaceDefinition>,
    scripts: BTreeMap<ScriptId, Arc<Script>>,
    enums: BTreeMap<u32, rustclient_cache::osrs240::script_tables::ScriptEnum>,
    items: BTreeMap<ItemDefinitionId, rustclient_cache::world::ItemDefinition>,
    loaded_groups: BTreeSet<InterfaceId>,
    pub modifiers: [bool; 3],
    fonts: BTreeMap<FontId, rustclient_cache::BitmapFont>,
    effects: Vec<rustclient_protocol::ClientIntent>,
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
            modifiers: [false; 3],
            fonts: BTreeMap::new(),
            effects: Vec::new(),
            queue: VecDeque::new(),
        }
    }
    /// Commit network semantic input before scheduling its listeners.
    /// # Errors
    /// Returns state/cache errors; script failures are explicit diagnostics in the result.
    pub fn commit(&mut self, state: &mut GameState, op: SemanticOperation) -> Result<Vec<String>> {
        let notification = match &op {
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
        let commit = state.apply_operation(op).map_err(|e| e.to_string())?;
        if state.lifecycle() != rustclient_game_state::SessionLifecycle::Active {
            self.reset();
            return Ok(Vec::new());
        }
        self.synchronize(state)?;
        if !commit.delta().is_empty()
            && let Some((kind, id)) = notification
        {
            self.notify(state, kind, id)?;
        }
        Ok(self.drain(state))
    }
    /// Execute an operation listener before revalidating any server button request.
    /// # Errors
    /// Rejects stale or forbidden actions before producing any network command.
    pub fn perform_action(
        &mut self,
        state: &mut GameState,
        action: WidgetAction,
    ) -> Result<(Vec<rustclient_protocol::ClientIntent>, Vec<String>)> {
        let intent = crate::validate_widget_action(state, &self.interfaces, action)?;
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
        if intent.is_some()
            && let Ok(Some(intent)) = crate::validate_widget_action(state, &self.interfaces, action)
        {
            intents.push(intent);
        }
        Ok((intents, errors))
    }
    pub fn reset(&mut self) {
        self.loaded_groups.clear();
        self.queue.clear();
        self.effects.clear();
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
    /// # Errors
    /// Propagates missing required definitions and listener queue limits.
    pub fn synchronize(&mut self, state: &GameState) -> Result<()> {
        let current = state.interfaces().groups();
        self.loaded_groups.retain(|g| current.contains(g));
        for group in current
            .difference(&self.loaded_groups)
            .copied()
            .collect::<Vec<_>>()
        {
            if !self.interfaces.contains_key(&group) {
                self.interfaces.insert(
                    group,
                    self.profile
                        .load_interface(&self.cache, group)
                        .map_err(|e| e.to_string())?,
                );
            }
            let listeners: Vec<_> = self.interfaces[&group]
                .widgets()
                .values()
                .filter_map(|w| {
                    w.listeners
                        .get(&WidgetListener::Load)
                        .map(|a| (w.id, a.clone()))
                })
                .collect();
            for (widget, args) in listeners {
                if let Some(request) = request(args)? {
                    self.enqueue(request, Some(WidgetAddress::component(widget)))?;
                }
            }
            self.loaded_groups.insert(group);
        }
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
            let effective = rustclient_game_ui::widgets::resolve_widget(
                state.interfaces(),
                &self.interfaces,
                address,
            )
            .map_err(|e| e.to_string())?;
            if let Some(args) = effective.listeners.get(&kind) {
                let triggers = state
                    .interfaces()
                    .runtime()
                    .get(&address)
                    .and_then(|r| r.triggers.get(&kind))
                    .map_or_else(
                        || match kind {
                            WidgetListener::Inventory => effective.inventory_triggers.as_slice(),
                            WidgetListener::Variable => effective.variable_triggers.as_slice(),
                            WidgetListener::Stat => effective.stat_triggers.as_slice(),
                            _ => &[],
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
        if widgets::instruction(self, op, n, s, context)? || queries::instruction(self, op, n, s)? {
            return Ok(());
        }
        Err(format!("unsupported required instruction {op:?}"))
    }
}
impl<S: ReadOnlyBlobs> ScriptHost<'_, S> {
    fn apply(&mut self, update: InterfaceUpdate) -> Result<()> {
        self.state
            .apply_operation(SemanticOperation::Interface(update))
            .map(|_| ())
            .map_err(|e| e.to_string())
    }
}
