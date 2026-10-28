//! Scene geometry generation for live player and NPC entities.

mod actor_animation;
mod emission;
use actor_animation::{DirectionalSequences, Playback, pose_model, select_movement};
pub(crate) use emission::{Placement, emit_model};

use crate::{
    ActorMotion, AnimationAssets, Result, SceneAsset, SceneGeometry, lighting::Light,
    motion::ActorPose,
};
use rustclient_cache::{
    osrs240::{
        entity_assets::{EntityAssets, EntityRequest},
        models::adjusted_model,
        npc_model::build_npc_base,
        player_model::{body_recolors, build_player_base},
    },
    world::{ItemDefinition, NpcDefinition, StaticModel, VarbitDefinition},
};
use rustclient_game_state::{NpcEntity, PlayerEntity, WorldEntitiesState};
use rustclient_model::{
    ItemDefinitionId, ModelId, ModelPoint, NpcDefinitionId, SequenceId, SlotContent, VarbitId,
    VarpId,
};
use std::collections::{BTreeMap, BTreeSet};

/// Grow-only entity dependencies layered over the immutable spawn window.
///
/// This is catalog data, not semantic state: it can be dropped and rebuilt from the cache
/// without affecting `GameState`.
#[derive(Debug, Default)]
pub struct EntityCatalog {
    npcs: BTreeMap<NpcDefinitionId, NpcDefinition>,
    items: BTreeMap<ItemDefinitionId, ItemDefinition>,
    models: BTreeMap<ModelId, StaticModel>,
    animations: std::sync::Arc<AnimationAssets>,
    varbits: BTreeMap<VarbitId, VarbitDefinition>,
    requested_npcs: BTreeSet<NpcDefinitionId>,
    requested_items: BTreeSet<ItemDefinitionId>,
    requested_sequences: BTreeSet<SequenceId>,
}

enum NpcResolution<'a> {
    Visible(&'a NpcDefinition),
    HiddenMorph,
    Unresolved,
}

impl EntityCatalog {
    /// Definitions that live entities reference and no earlier request covered.
    ///
    /// Each id is requested once; the loader never retries absent or undecodable assets.
    pub fn request_missing(
        &mut self,
        asset: &SceneAsset,
        entities: &WorldEntitiesState,
        scenery: &rustclient_game_state::SceneryState,
    ) -> Option<EntityRequest> {
        let mut request = EntityRequest::default();
        let mut want_sequence = |id: SequenceId, requested: &mut BTreeSet<SequenceId>| {
            if asset
                .sources()
                .sequences
                .get(&id)
                .is_none_or(|s| s.skeletal.is_some())
                && requested.insert(id)
            {
                request.sequences.insert(id);
            }
        };
        for id in scenery
            .locations()
            .values()
            .filter_map(|loc| loc.animation.and_then(|a| a.sequence))
            .chain(
                asset
                    .sources()
                    .sequences
                    .values()
                    .filter(|s| s.skeletal.is_some())
                    .map(|s| s.id),
            )
        {
            want_sequence(id, &mut self.requested_sequences);
        }
        for player in entities.players().values() {
            if let Some(sequence) = player.sequence.sequence {
                want_sequence(sequence, &mut self.requested_sequences);
            }
            let Some(appearance) = &player.appearance else {
                continue;
            };
            let movement = appearance.movement_sequences;
            for sequence in [
                movement.idle,
                movement.turn,
                movement.walk,
                movement.walk_back,
                movement.walk_left,
                movement.walk_right,
                movement.run,
            ]
            .into_iter()
            .flatten()
            {
                want_sequence(sequence, &mut self.requested_sequences);
            }
            for slot in &appearance.slots {
                if let SlotContent::Item(item) = slot
                    && self.requested_items.insert(*item)
                {
                    request.items.insert(*item);
                }
            }
        }
        for npc in entities.npcs().values() {
            if let Some(sequence) = npc.sequence.sequence {
                want_sequence(sequence, &mut self.requested_sequences);
            }
            if self.requested_npcs.insert(npc.definition_id) {
                request.npcs.insert(npc.definition_id);
            }
        }
        // Sequences referenced by NPC definitions are resolved by the loader itself.
        self.requested_sequences
            .extend(request.sequences.iter().copied());
        (!request.is_empty()).then_some(request)
    }

    /// Fold a decoded batch into the catalog.
    ///
    /// Returns advisories for cached-model animations that could not be prepared for playback;
    /// those sequences are then drawn in rest pose.
    pub fn merge(&mut self, asset: &SceneAsset, mut assets: EntityAssets) -> Vec<String> {
        let failures = std::sync::Arc::make_mut(&mut self.animations).merge(asset, &mut assets);
        self.npcs.extend(assets.npcs);
        self.items.extend(assets.items);
        self.models.extend(assets.models);
        self.varbits.extend(assets.varbits);
        failures
    }

    /// Resolve an NPC's current definition through its variable-driven transformation.
    ///
    /// `None` means the transformation selected "not shown" or a dependency has not
    /// arrived yet; the caller simply skips the NPC until the catalog grows.
    #[must_use]
    pub fn resolve_npc<'a>(
        &'a self,
        asset: &SceneAsset,
        id: NpcDefinitionId,
        variables: &BTreeMap<VarpId, i32>,
    ) -> Option<&'a NpcDefinition> {
        match self.classify_npc(asset, id, variables) {
            NpcResolution::Visible(def) => Some(def),
            NpcResolution::HiddenMorph | NpcResolution::Unresolved => None,
        }
    }

    fn classify_npc<'a>(
        &'a self,
        asset: &SceneAsset,
        id: NpcDefinitionId,
        variables: &BTreeMap<VarpId, i32>,
    ) -> NpcResolution<'a> {
        let mut id = id;
        // A chain cannot visit more definitions than the catalog contains without cycling.
        for _ in 0..=self.npcs.len() {
            let Some(def) = self.npcs.get(&id) else {
                return NpcResolution::Unresolved;
            };
            let Some(morphs) = &def.morphs else {
                return NpcResolution::Visible(def);
            };
            let selector = if let Some(varbit) = morphs.varbit {
                let Some(bit) = self
                    .varbits
                    .get(&varbit)
                    .or_else(|| asset.sources().varbits.get(&varbit))
                else {
                    return NpcResolution::Unresolved;
                };
                let value = *variables.get(&bit.varp).unwrap_or(&0);
                let width = u32::from(bit.high - bit.low) + 1;
                let mask = if width == 32 {
                    u32::MAX
                } else {
                    (1_u32 << width) - 1
                };
                ((value.cast_unsigned() >> bit.low) & mask).cast_signed()
            } else {
                morphs
                    .varp
                    .map_or(-1, |varp| *variables.get(&varp).unwrap_or(&0))
            };
            let Some(last) = morphs.transforms.len().checked_sub(1) else {
                return NpcResolution::HiddenMorph;
            };
            let index = usize::try_from(selector)
                .ok()
                .filter(|index| *index < last)
                .unwrap_or(last);
            match morphs.transforms.get(index).copied().flatten() {
                Some(next) => id = next,
                None => return NpcResolution::HiddenMorph,
            }
        }
        NpcResolution::Unresolved
    }

    /// One-line account of why live actors are or are not drawable, for the dev diagnostics.
    ///
    /// It repeats the skip conditions of the geometry build, so a count that stays below the
    /// entity totals names the stage (appearance, definition, models) that drops them.
    #[must_use]
    pub fn report(
        &self,
        asset: &SceneAsset,
        entities: &WorldEntitiesState,
        variables: &BTreeMap<VarpId, i32>,
    ) -> String {
        let players = entities.players();
        let with_appearance = players
            .values()
            .filter(|player| player.appearance.is_some())
            .count();
        let player_bases = players
            .values()
            .filter_map(|player| player.appearance.as_ref())
            .filter(|appearance| {
                build_player_base(appearance, &asset.sources().kits, &self.items, |id| {
                    self.model(asset, id)
                })
                .is_some()
            })
            .count();
        let npcs = entities.npcs();
        let loaded = npcs
            .values()
            .filter(|npc| self.npcs.contains_key(&npc.definition_id))
            .count();
        let mut resolved = 0_usize;
        let mut hidden_morphs = 0_usize;
        let mut npc_bases = 0_usize;
        let mut missing_models = BTreeSet::new();
        for npc in npcs.values() {
            match self.classify_npc(asset, npc.definition_id, variables) {
                NpcResolution::Visible(def) => {
                    resolved += 1;
                    if build_npc_base(def, |id| self.model(asset, id)).is_some() {
                        npc_bases += 1;
                    } else {
                        missing_models.extend(def.models.iter().map(|id| id.get()));
                    }
                }
                NpcResolution::HiddenMorph => {
                    resolved += 1;
                    hidden_morphs += 1;
                }
                NpcResolution::Unresolved => {}
            }
        }
        format!(
            "players {}/{with_appearance} appearance/{player_bases} drawable; npcs {}/{loaded} \
             defined/{resolved} resolved ({hidden_morphs} hidden)/{npc_bases} drawable; kits {} \
             items {} models {}; undrawable npc model ids {:?}",
            players.len(),
            npcs.len(),
            asset.sources().kits.len(),
            self.items.len(),
            self.models.len(),
            missing_models.iter().take(8).collect::<Vec<_>>(),
        )
    }

    fn model<'a>(&'a self, asset: &'a SceneAsset, id: ModelId) -> Option<&'a StaticModel> {
        self.models
            .get(&id)
            .or_else(|| asset.sources().models.get(&id))
    }

    /// Immutable animation resources for the scenery worker.
    #[must_use]
    pub fn animation_assets(&self) -> std::sync::Arc<AnimationAssets> {
        std::sync::Arc::clone(&self.animations)
    }
}

/// Rotate a model point around the Y axis by an OSRS orientation angle (0..2047).
#[must_use]
pub fn rotate_y(p: ModelPoint, angle: u16) -> ModelPoint {
    let theta = f64::from(angle & 2047) * std::f64::consts::TAU / 2048.0;
    let (sin, cos) = theta.sin_cos();
    #[expect(
        clippy::cast_possible_truncation,
        reason = "coordinate bounds clamp inside i32"
    )]
    let x = (f64::from(p.x) * cos + f64::from(p.z) * sin).round() as i32;
    #[expect(
        clippy::cast_possible_truncation,
        reason = "coordinate bounds clamp inside i32"
    )]
    let z = (-f64::from(p.x) * sin + f64::from(p.z) * cos).round() as i32;
    ModelPoint::new(x, p.y, z)
}

/// Players and NPCs a presentation filter removes (`Hooks.RenderableDrawListener` returning false).
#[derive(Debug, Clone, Default, PartialEq, Eq)]
pub struct HiddenActors {
    players: std::collections::BTreeSet<u32>,
    npcs: std::collections::BTreeSet<u32>,
}

impl HiddenActors {
    /// Hide the given players and NPCs by scene index.
    #[must_use]
    pub fn new(
        players: impl IntoIterator<Item = u32>,
        npcs: impl IntoIterator<Item = u32>,
    ) -> Self {
        Self {
            players: players.into_iter().collect(),
            npcs: npcs.into_iter().collect(),
        }
    }

    /// Whether the player at `index` is hidden.
    #[must_use]
    pub fn player(&self, index: u32) -> bool {
        self.players.contains(&index)
    }

    /// Whether the NPC at `index` is hidden.
    #[must_use]
    pub fn npc(&self, index: u32) -> bool {
        self.npcs.contains(&index)
    }
}

/// Presentation choices applied while entities are built; none of them changes game state.
#[derive(Debug, Clone, Copy)]
pub struct EntityPresentation<'a> {
    /// Which animations are blended between frames.
    pub interpolation: &'a crate::AnimationInterpolation,
    /// Actors that are not drawn.
    pub hidden: &'a HiddenActors,
}

/// Append live entity geometry (players and NPCs) into `geometry`.
/// # Errors
/// Returns error on vertex allocation limit or pose failures.
pub fn build_entities_geometry(
    asset: &SceneAsset,
    catalog: &EntityCatalog,
    entities: &WorldEntitiesState,
    motion: &ActorMotion,
    variables: &BTreeMap<VarpId, i32>,
    presentation: EntityPresentation<'_>,
    geometry: &mut SceneGeometry,
) -> Result<()> {
    let EntityPresentation {
        interpolation,
        hidden,
    } = presentation;
    for player in entities.players().values() {
        if hidden.player(player.index.get()) {
            continue;
        }
        let position = motion
            .player(player.index)
            .unwrap_or_else(|| corner(player.tile));
        build_player(
            asset,
            catalog,
            player,
            position,
            motion.player_pose(player.index),
            interpolation.blends(interpolation.players, player.sequence.sequence),
            geometry,
        )?;
    }
    for npc in entities.npcs().values() {
        if hidden.npc(npc.index.get()) {
            continue;
        }
        let position = motion.npc(npc.index).unwrap_or_else(|| corner(npc.tile));
        build_npc(
            asset,
            catalog,
            npc,
            position,
            motion.npc_pose(npc.index),
            interpolation.blends(interpolation.npcs, npc.sequence.sequence),
            variables,
            geometry,
        )?;
    }
    Ok(())
}

/// Footprint corner of a tile in world units.
fn corner(tile: rustclient_model::WorldTile) -> [i32; 2] {
    [tile.x * 128, tile.y * 128]
}

fn build_player(
    asset: &SceneAsset,
    catalog: &EntityCatalog,
    player: &PlayerEntity,
    position: [i32; 2],
    drawn: Option<ActorPose>,
    blend: bool,
    geometry: &mut SceneGeometry,
) -> Result<()> {
    let orientation = drawn.map_or(player.orientation, |drawn| drawn.rotation);
    let Some(appearance) = &player.appearance else {
        return Ok(());
    };
    if appearance.hidden {
        return Ok(());
    }

    let Some(base_model) =
        build_player_base(appearance, &asset.sources().kits, &catalog.items, |id| {
            catalog.model(asset, id)
        })
    else {
        return Ok(());
    };

    let recolors = body_recolors(appearance.colors);
    let mut model = adjusted_model(&base_model, &recolors, &[], 0);
    let movement = appearance.movement_sequences;
    let (base, base_cycle) = match drawn {
        Some(drawn) => (
            select_movement(
                drawn.pose,
                &DirectionalSequences {
                    idle: movement.idle,
                    turn_left: movement.turn,
                    turn_right: movement.turn,
                    walk: [
                        movement.walk,
                        movement.walk_back,
                        movement.walk_left,
                        movement.walk_right,
                    ],
                    run: [movement.run, None, None, None],
                    crawl: [None; 4],
                },
            ),
            drawn.cycle,
        ),
        None => (
            match player.locomotion {
                rustclient_game_state::Locomotion::Idle => movement.idle,
                rustclient_game_state::Locomotion::Walk => movement.walk.or(movement.idle),
                rustclient_game_state::Locomotion::Run => {
                    movement.run.or(movement.walk).or(movement.idle)
                }
                rustclient_game_state::Locomotion::Turn => movement.turn.or(movement.idle),
            },
            player.animation_cycle,
        ),
    };
    let playback = Playback::for_actor(
        player.sequence,
        player.sequence_timing_resolved,
        base,
        base_cycle,
    );
    let posed_action = pose_model(asset, catalog, &mut model, playback, blend)?;
    if !posed_action {
        pose_model(
            asset,
            catalog,
            &mut model,
            Playback::movement(base, base_cycle),
            blend,
        )?;
    }

    let first_vertex = geometry.vertices.len();
    emit_model(
        asset,
        &model,
        Placement {
            position,
            plane: player.tile.plane,
            depth_policy: rustclient_model::ModelDepthPolicy::SortedDeferredWrite,
            size: 1,
            footprint: default_footprint(1),
            orientation,
            height_offset: sequence_height_offset(
                asset,
                catalog,
                if posed_action {
                    playback.sequence()
                } else {
                    base
                },
            ),
        },
        Light::entity(64, 850),
        geometry,
    )?;
    label_anchor(
        geometry,
        first_vertex,
        crate::ActorLabelId::Player(player.index, player.instance),
        asset,
        position,
        1,
    );
    Ok(())
}

#[expect(
    clippy::too_many_lines,
    reason = "one ordered NPC model construction and pose path"
)]
#[expect(
    clippy::too_many_arguments,
    reason = "immutable catalog, actor, drawn pose and smoothing inputs"
)]
fn build_npc(
    asset: &SceneAsset,
    catalog: &EntityCatalog,
    npc: &NpcEntity,
    position: [i32; 2],
    drawn: Option<ActorPose>,
    blend: bool,
    variables: &BTreeMap<VarpId, i32>,
    geometry: &mut SceneGeometry,
) -> Result<()> {
    let orientation = drawn.map_or(npc.orientation, |drawn| drawn.rotation);
    let Some(def) = catalog.resolve_npc(asset, npc.definition_id, variables) else {
        return Ok(());
    };
    let Some(mut model) = build_npc_base(def, |id| catalog.model(asset, id)) else {
        return Ok(());
    };
    let (base, base_cycle) = match drawn {
        Some(drawn) => (
            select_movement(
                drawn.pose,
                &DirectionalSequences {
                    idle: def.idle_sequence,
                    turn_left: def.turn_left_sequence,
                    turn_right: def.turn_right_sequence,
                    walk: [
                        def.walk_sequence,
                        def.walk_back_sequence,
                        def.walk_left_sequence,
                        def.walk_right_sequence,
                    ],
                    run: def.run_sequences,
                    crawl: def.crawl_sequences,
                },
            ),
            drawn.cycle,
        ),
        None => (
            match npc.locomotion {
                rustclient_game_state::Locomotion::Run => def.run_sequences[0]
                    .or(def.walk_sequence)
                    .or(def.idle_sequence),
                rustclient_game_state::Locomotion::Walk => npc
                    .path
                    .filter(|p| p.speed == rustclient_model::MoveSpeed::Crawl)
                    .and(def.crawl_sequences[0])
                    .or(def.walk_sequence)
                    .or(def.idle_sequence),
                rustclient_game_state::Locomotion::Turn => {
                    def.turn_left_sequence.or(def.idle_sequence)
                }
                rustclient_game_state::Locomotion::Idle => def.idle_sequence,
            },
            npc.animation_cycle,
        ),
    };
    let playback =
        Playback::for_actor(npc.sequence, npc.sequence_timing_resolved, base, base_cycle);
    let posed_action = pose_model(asset, catalog, &mut model, playback, blend)?;
    if !posed_action {
        pose_model(
            asset,
            catalog,
            &mut model,
            Playback::movement(base, base_cycle),
            blend,
        )?;
    }
    // The reference scales the animated model, so bone translations stay in unscaled units.
    if def.scale_width != 128 || def.scale_height != 128 {
        let (width, height) = (i32::from(def.scale_width), i32::from(def.scale_height));
        for p in &mut model.vertices {
            *p = ModelPoint::new(p.x * width / 128, p.y * height / 128, p.z * width / 128);
        }
    }

    let first_vertex = geometry.vertices.len();
    let pick_bounds=emit_model(
        asset,
        &model,
        Placement {
            position,
            plane: npc.tile.plane,
            depth_policy: catalog
                .npcs
                .get(&npc.definition_id)
                .map_or(rustclient_model::ModelDepthPolicy::DepthWrite, |base| {
                    base.depth_policy
                }),
            size: def.size.max(1),
            footprint: def
                .footprint
                .map_or_else(|| default_footprint(def.size.max(1)), i32::from),
            orientation,
            height_offset: sequence_height_offset(
                asset,
                catalog,
                if posed_action {
                    playback.sequence()
                } else {
                    base
                },
            ),
        },
        Light::entity(
            i32::from(def.ambient) + 64,
            i32::from(def.contrast) * 5 + 850,
        ),
        geometry,
    )?;
    label_anchor(
        geometry,
        first_vertex,
        crate::ActorLabelId::Npc(npc.index, npc.instance),
        asset,
        position,
        def.size.max(1),
    );
    if geometry.vertices.len() > first_vertex {
        let bounding=catalog.npcs.get(&npc.definition_id).is_some_and(|base|base.size==1);
        geometry.picks.push(crate::PickRange {
            bounds:Some(pick_bounds),
            precision:if bounding{crate::PickPrecision::BoundsOnly}else{crate::PickPrecision::ProjectedFaces},
            vertices: first_vertex..geometry.vertices.len(),
            target: rustclient_model::WorldTarget::Npc {
                index: npc.index,
                instance: npc.instance,
                definition: npc.definition_id,
                resolved: def.id,
            },
        });
    }
    Ok(())
}

/// Use posed bounds from the same CPU vertices submitted for drawing, with no GPU readback.
fn label_anchor(
    geometry: &mut SceneGeometry,
    first: usize,
    actor: crate::ActorLabelId,
    asset: &SceneAsset,
    position: [i32; 2],
    size: u8,
) {
    let points = &geometry.vertices[first..];
    let Some(vertex) = points.first() else {
        return;
    };
    let height = points
        .iter()
        .map(|v| i32::from(v.position[1]))
        .min()
        .unwrap_or(0);
    let vertices = first..geometry.vertices.len();
    geometry.actor_labels.push(crate::ActorLabelAnchor {
        actor,
        position: [
            position[0] - asset.descriptor().origin().x * 128 + i32::from(size) * 64,
            height,
            position[1] - asset.descriptor().origin().y * 128 + i32::from(size) * 64,
        ],
        level: vertex.render_level(),
        vertices,
    });
}

/// The client's default actor footprint, `(int)(0.4F * (size * 128))`.
#[expect(
    clippy::cast_possible_truncation,
    reason = "reference footprint is a float cast to int"
)]
fn default_footprint(size: u8) -> i32 {
    (0.4_f32 * (f32::from(size) * 128.)) as i32
}

/// Animation height offset of `sequence`, in fine units (`Renderable.getAnimationHeightOffset`).
fn sequence_height_offset(
    asset: &SceneAsset,
    catalog: &EntityCatalog,
    sequence: Option<SequenceId>,
) -> i32 {
    sequence
        .and_then(|id| catalog.animations.sequence(asset, id))
        .map_or(0, |sequence| i32::from(sequence.height_offset))
}
