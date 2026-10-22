//! Assembly of NPC models from NPC definitions.

use crate::{
    osrs240::models::{adjusted_model, combine_models},
    world::{NpcDefinition, StaticModel},
};
use rustclient_model::ModelId;

/// Assemble an unposed NPC model from definition and loaded model meshes.
#[must_use]
pub fn build_npc_base<'m>(
    definition: &NpcDefinition,
    models: impl Fn(ModelId) -> Option<&'m StaticModel>,
) -> Option<StaticModel> {
    if definition.models.is_empty() {
        return None;
    }
    let mut part_models = Vec::new();
    for id in &definition.models {
        if let Some(source) = models(*id) {
            part_models.push(source);
        }
    }
    if part_models.is_empty() {
        return None;
    }
    let combined = combine_models(&part_models);
    Some(adjusted_model(
        &combined,
        &definition.recolors,
        &definition.retextures,
        0,
    ))
}
