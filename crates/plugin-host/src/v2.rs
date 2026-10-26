//! API 0.2 mediation: capability-checked queries, event filtering and output validation.
//!
//! Plugins see [`GameSnapshot`] data only through [`PluginCall`] accessors, receive only the
//! events their grants cover, and return a [`PluginOutput`] that this module bounds and merges
//! before any owning subsystem sees it.

use crate::{HostView, LogSink, PluginCall, PluginSet, PluginStorage};
use rustclient_plugin_api::{
    CameraCommand, Capability, CapabilitySet, ChatLine, EntityVisibility, GameEvent, GameSnapshot,
    InfoBox, ItemStack, LayeredOverlay, LogLevel, MenuEdit, Notification, OverlayCommand,
    PlayerPosition, PlayerSnapshot, PluginId, PluginOutput, RoofArea, SceneFlags, SkillLevel,
    SpriteOverride, Stretch, WidgetSnapshot, limits,
};
use std::collections::BTreeSet;

/// A query or output item was refused because the capability is not granted.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct CapabilityDenied(pub Capability);

impl std::fmt::Display for CapabilityDenied {
    fn fmt(&self, formatter: &mut std::fmt::Formatter<'_>) -> std::fmt::Result {
        write!(formatter, "capability {} is not granted", self.0)
    }
}

impl std::error::Error for CapabilityDenied {}

/// A [`HostView`] over one shared [`GameSnapshot`].
#[derive(Debug, Clone, Copy)]
pub struct SnapshotView<'a>(pub &'a GameSnapshot);

impl HostView for SnapshotView<'_> {
    fn player_position(&self) -> Option<PlayerPosition> {
        let world = self.0.local_player()?.actor.world;
        Some(PlayerPosition {
            x: world.x,
            y: world.y,
            plane: world.plane,
        })
    }

    fn tick(&self) -> u64 {
        self.0.client.tick
    }

    fn game(&self) -> Option<&GameSnapshot> {
        Some(self.0)
    }
}

impl<'a> PluginCall<'a> {
    /// Fail unless `capability` is granted, reporting the first denial of each capability.
    fn require(&mut self, capability: Capability) -> Result<(), CapabilityDenied> {
        if self.capabilities.contains(capability) {
            return Ok(());
        }
        if self.denied.insert(capability) {
            self.logs.log(
                self.plugin,
                LogLevel::Warn,
                &format!("denied: capability {capability} is not granted"),
            );
        }
        Err(CapabilityDenied(capability))
    }

    fn snapshot(&self) -> Option<&'a GameSnapshot> {
        self.view.game()
    }

    /// Client facts. Requires `game.read.client`.
    ///
    /// # Errors
    /// [`CapabilityDenied`] when the grant is missing.
    pub fn client(&mut self) -> Result<rustclient_plugin_api::ClientSnapshot, CapabilityDenied> {
        self.require(Capability::GameReadClient)?;
        Ok(self.snapshot().map(|game| game.client).unwrap_or_default())
    }

    /// All players in the scene. Requires `game.read.players`.
    ///
    /// # Errors
    /// [`CapabilityDenied`] when the grant is missing.
    pub fn players(&mut self) -> Result<&'a [PlayerSnapshot], CapabilityDenied> {
        self.require(Capability::GameReadPlayers)?;
        Ok(self.snapshot().map_or(&[][..], |game| &game.players))
    }

    /// The local player. Requires `game.read.players`.
    ///
    /// # Errors
    /// [`CapabilityDenied`] when the grant is missing.
    pub fn local_player(&mut self) -> Result<Option<&'a PlayerSnapshot>, CapabilityDenied> {
        self.require(Capability::GameReadPlayers)?;
        Ok(self.snapshot().and_then(GameSnapshot::local_player))
    }

    /// All NPCs in the scene. Requires `game.read.npcs`.
    ///
    /// # Errors
    /// [`CapabilityDenied`] when the grant is missing.
    pub fn npcs(&mut self) -> Result<&'a [rustclient_plugin_api::NpcSnapshot], CapabilityDenied> {
        self.require(Capability::GameReadNpcs)?;
        Ok(self.snapshot().map_or(&[][..], |game| &game.npcs))
    }

    /// Skill levels in client index order. Requires `game.read.skills`.
    ///
    /// # Errors
    /// [`CapabilityDenied`] when the grant is missing.
    pub fn skills(&mut self) -> Result<&'a [SkillLevel], CapabilityDenied> {
        self.require(Capability::GameReadSkills)?;
        Ok(self.snapshot().map_or(&[][..], |game| &game.skills))
    }

    /// One varp value. Requires `game.read.vars`.
    ///
    /// # Errors
    /// [`CapabilityDenied`] when the grant is missing.
    pub fn varp(&mut self, id: u32) -> Result<i32, CapabilityDenied> {
        self.require(Capability::GameReadVars)?;
        Ok(self.snapshot().map_or(0, |game| game.vars.varp(id)))
    }

    /// One varbit value, `None` when the id is unknown. Requires `game.read.vars`.
    ///
    /// # Errors
    /// [`CapabilityDenied`] when the grant is missing.
    pub fn varbit(&mut self, id: u32) -> Result<Option<i32>, CapabilityDenied> {
        self.require(Capability::GameReadVars)?;
        Ok(self.snapshot().and_then(|game| game.vars.varbit(id)))
    }

    /// One inventory-like container. Requires `game.read.inventory`.
    ///
    /// # Errors
    /// [`CapabilityDenied`] when the grant is missing.
    pub fn container(
        &mut self,
        id: u32,
    ) -> Result<Option<&'a [Option<ItemStack>]>, CapabilityDenied> {
        self.require(Capability::GameReadInventory)?;
        Ok(self
            .snapshot()
            .and_then(|game| game.containers.get(&id))
            .map(Vec::as_slice))
    }

    /// Loaded widgets. Requires `game.read.widgets`.
    ///
    /// # Errors
    /// [`CapabilityDenied`] when the grant is missing.
    pub fn widgets(&mut self) -> Result<&'a [WidgetSnapshot], CapabilityDenied> {
        self.require(Capability::GameReadWidgets)?;
        Ok(self.snapshot().map_or(&[][..], |game| &game.widgets))
    }

    /// Retained chat lines. Requires `game.read.chat`.
    ///
    /// # Errors
    /// [`CapabilityDenied`] when the grant is missing.
    pub fn chat(&mut self) -> Result<&'a [ChatLine], CapabilityDenied> {
        self.require(Capability::GameReadChat)?;
        Ok(self.snapshot().map_or(&[][..], |game| &game.chat))
    }
}

/// Validated, merged output of every enabled plugin for one update.
#[derive(Debug, Clone, Default, PartialEq)]
pub struct HostOutput {
    /// Overlay primitives ordered by layer, then plugin load order.
    pub overlay: Vec<LayeredOverlay>,
    /// Notifications in plugin load order.
    pub notifications: Vec<(PluginId, Notification)>,
    /// Info boxes in plugin load order.
    pub info_boxes: Vec<(PluginId, InfoBox)>,
    /// Menu edits in plugin load order.
    pub menu_edits: Vec<MenuEdit>,
    /// Union of every plugin's hidden actors and effects.
    pub entities: EntityVisibility,
    /// Sprite replacements; a later plugin wins for the same sprite.
    pub sprite_overrides: Vec<SpriteOverride>,
    /// Camera requests in plugin load order.
    pub camera: Vec<CameraCommand>,
    /// Merged scene flags.
    pub scene: SceneFlags,
    /// Union of every plugin's forced-roof areas.
    pub roof_areas: Vec<RoofArea>,
    /// Stretch request; the last plugin that sets one wins.
    pub stretch: Option<Stretch>,
    /// Animation ids no plugin wants smoothed; sorted and unique.
    pub smoothing_denied: Vec<u32>,
}

impl HostOutput {
    fn merge(&mut self, plugin: &PluginId, output: PluginOutput) {
        self.overlay.extend(output.overlay);
        self.notifications.extend(
            output
                .notifications
                .into_iter()
                .map(|n| (plugin.clone(), n)),
        );
        self.info_boxes
            .extend(output.info_boxes.into_iter().map(|b| (plugin.clone(), b)));
        self.menu_edits.extend(output.menu_edits);
        if let Some(entities) = output.entities {
            for actor in entities.hidden {
                if !self.entities.hidden.contains(&actor) {
                    self.entities.hidden.push(actor);
                }
            }
            for actor in entities.hidden_overlays {
                if !self.entities.hidden_overlays.contains(&actor) {
                    self.entities.hidden_overlays.push(actor);
                }
            }
            self.entities.hide_projectiles |= entities.hide_projectiles;
            self.entities.hide_graphics |= entities.hide_graphics;
        }
        for sprite in output.sprite_overrides {
            self.sprite_overrides
                .retain(|have| have.sprite != sprite.sprite);
            self.sprite_overrides.push(sprite);
        }
        self.camera.extend(output.camera);
        if let Some(scene) = output.scene {
            self.scene.roofs = self.scene.roofs.union(scene.roofs);
            self.scene.smoothing = self.scene.smoothing.union(scene.smoothing);
        }
        self.roof_areas.extend(output.roof_areas);
        self.smoothing_denied.extend(output.smoothing_denied);
        if output.stretch.is_some() {
            self.stretch = output.stretch;
        }
    }

    fn finish(&mut self) {
        self.overlay.sort_by_key(|item| item.layer);
        self.roof_areas.sort_unstable();
        self.roof_areas.dedup();
        self.smoothing_denied.sort_unstable();
        self.smoothing_denied.dedup();
    }
}

fn truncate(text: &mut String, max: usize) {
    if text.len() > max {
        let mut end = max;
        while !text.is_char_boundary(end) {
            end -= 1;
        }
        text.truncate(end);
    }
}

/// Clamp camera requests to what the camera can do and drop the ones that make no sense.
fn bound_camera(commands: Vec<CameraCommand>) -> Vec<CameraCommand> {
    commands
        .into_iter()
        .take(limits::CAMERA_COMMANDS)
        .filter_map(|command| match command {
            CameraCommand::YawTarget(yaw) => Some(CameraCommand::YawTarget(yaw.rem_euclid(2048))),
            CameraCommand::PitchTarget(pitch) => {
                Some(CameraCommand::PitchTarget(pitch.clamp(128, 383)))
            }
            CameraCommand::Speed(speed) => speed
                .is_finite()
                .then(|| CameraCommand::Speed(speed.clamp(0.1, 10.0))),
            CameraCommand::ZoomLimits { min, max } => {
                (min <= max).then_some(CameraCommand::ZoomLimits { min, max })
            }
            other => Some(other),
        })
        .collect()
}

/// Bound one plugin's output and strip everything its grants do not cover.
///
/// `deny` is called once per ungranted capability that the output tried to use.
pub(crate) fn validate(
    capabilities: &rustclient_plugin_api::CapabilitySet,
    mut output: PluginOutput,
    mut deny: impl FnMut(Capability),
) -> PluginOutput {
    let mut refuse = |capability: Capability, present: bool| -> bool {
        if present && !capabilities.contains(capability) {
            deny(capability);
            return true;
        }
        false
    };
    if refuse(Capability::UiOverlay, !output.overlay.is_empty()) {
        output.overlay.clear();
    }
    output.overlay.truncate(limits::OVERLAY_COMMANDS);
    output.overlay.retain_mut(|item| match &mut item.command {
        OverlayCommand::Text { text, .. } | OverlayCommand::ActorText { text, .. } => {
            truncate(text, limits::TEXT_BYTES);
            true
        }
        OverlayCommand::Rect { width, height, .. } => *width >= 0 && *height >= 0,
        _ => true,
    });

    if refuse(Capability::ClientNotify, !output.notifications.is_empty()) {
        output.notifications.clear();
    }
    output.notifications.truncate(limits::NOTIFICATIONS);
    for notification in &mut output.notifications {
        truncate(&mut notification.title, 64);
        truncate(&mut notification.body, limits::TEXT_BYTES);
    }

    if refuse(Capability::UiInfobox, !output.info_boxes.is_empty()) {
        output.info_boxes.clear();
    }
    output.info_boxes.truncate(limits::INFO_BOXES);
    for info in &mut output.info_boxes {
        truncate(&mut info.id, 64);
        truncate(&mut info.text, 16);
        truncate(&mut info.tooltip, limits::TEXT_BYTES);
    }

    if refuse(Capability::UiMenuSwap, !output.menu_edits.is_empty()) {
        output.menu_edits.clear();
    }
    output.menu_edits.truncate(limits::MENU_EDITS);
    for edit in &mut output.menu_edits {
        if let MenuEdit::SetOption { option, .. } = edit {
            truncate(option, 64);
        }
    }

    if refuse(Capability::UiEntitiesHide, output.entities.is_some()) {
        output.entities = None;
    }
    if let Some(entities) = &mut output.entities {
        entities.hidden.truncate(limits::HIDDEN_ACTORS);
        entities.hidden_overlays.truncate(limits::HIDDEN_ACTORS);
    }

    if refuse(
        Capability::UiSpriteOverride,
        !output.sprite_overrides.is_empty(),
    ) {
        output.sprite_overrides.clear();
    }
    output.sprite_overrides.truncate(limits::SPRITE_OVERRIDES);
    output.sprite_overrides.retain(|sprite| {
        sprite.width > 0
            && sprite.height > 0
            && sprite.width <= limits::SPRITE_EDGE
            && sprite.height <= limits::SPRITE_EDGE
            && sprite.rgba.len() == usize::from(sprite.width) * usize::from(sprite.height) * 4
    });

    if refuse(Capability::CameraControl, !output.camera.is_empty()) {
        output.camera.clear();
    }
    output.camera = bound_camera(output.camera);

    if refuse(Capability::SceneControl, output.scene.is_some()) {
        output.scene = None;
    }
    if refuse(Capability::SceneControl, !output.roof_areas.is_empty()) {
        output.roof_areas.clear();
    }
    if refuse(
        Capability::SceneControl,
        !output.smoothing_denied.is_empty(),
    ) {
        output.smoothing_denied.clear();
    }
    output.smoothing_denied.truncate(limits::SMOOTHING_DENIED);
    output.roof_areas.truncate(limits::ROOF_AREAS);
    output.roof_areas.retain(|area| {
        area.min.0 <= area.max.0
            && area.min.1 <= area.max.1
            && area.planes.0 <= area.planes.1
            && area.planes.1 <= 3
    });
    if refuse(Capability::UiViewport, output.stretch.is_some()) {
        output.stretch = None;
    }
    if let Some(stretch) = &mut output.stretch {
        stretch.scale_percent = stretch
            .scale_percent
            .clamp(Stretch::MIN_SCALE_PERCENT, Stretch::MAX_SCALE_PERCENT);
    }
    output
}

impl PluginSet {
    /// Union of the grants of every enabled plugin: what the projector must build.
    #[must_use]
    pub fn granted(&self) -> CapabilitySet {
        self.plugins
            .iter()
            .filter(|plugin| plugin.enabled)
            .flat_map(|plugin| plugin.manifest.capabilities.iter())
            .collect()
    }

    /// Deliver typed events to every enabled plugin, filtered to each plugin's grants.
    pub fn dispatch_game(
        &mut self,
        events: &[GameEvent],
        view: &dyn HostView,
        storage: &mut dyn PluginStorage,
        logs: &mut dyn LogSink,
    ) {
        for index in 0..self.plugins.len() {
            let fault_limit = self.fault_limit;
            let plugin = &mut self.plugins[index];
            let granted = &plugin.manifest.capabilities;
            let visible: Vec<GameEvent> = events
                .iter()
                .filter(|event| {
                    event
                        .required_capability()
                        .is_none_or(|capability| granted.contains(capability))
                })
                .cloned()
                .collect();
            if visible.is_empty() {
                continue;
            }
            let _ = Self::invoke(
                plugin,
                view,
                storage,
                logs,
                fault_limit,
                |instance, call| instance.on_game_events(call, &visible),
            );
        }
    }

    /// Collect, validate and merge every enabled plugin's declarative output.
    pub fn collect_output(
        &mut self,
        view: &dyn HostView,
        storage: &mut dyn PluginStorage,
        logs: &mut dyn LogSink,
    ) -> HostOutput {
        let mut merged = HostOutput::default();
        for index in 0..self.plugins.len() {
            let fault_limit = self.fault_limit;
            let plugin = &mut self.plugins[index];
            let Some(produced) = Self::invoke(
                plugin,
                view,
                storage,
                logs,
                fault_limit,
                |instance, call| instance.output(call),
            ) else {
                continue;
            };
            if produced.is_empty() {
                continue;
            }
            let capabilities = plugin.manifest.capabilities.clone();
            let mut newly_denied: BTreeSet<Capability> = BTreeSet::new();
            let checked = validate(&capabilities, produced, |capability| {
                newly_denied.insert(capability);
            });
            for capability in newly_denied {
                if plugin.denied.insert(capability) {
                    logs.log(
                        &plugin.manifest.id,
                        LogLevel::Warn,
                        &format!("denied: output requires ungranted capability {capability}"),
                    );
                }
            }
            merged.merge(&plugin.manifest.id.clone(), checked);
        }
        merged.finish();
        merged
    }
}
