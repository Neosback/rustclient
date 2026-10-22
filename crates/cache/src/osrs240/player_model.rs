//! Assembly of composite player models from kit and worn item definitions.

use crate::{
    osrs240::models::{adjusted_model, combine_models},
    world::{ItemDefinition, KitDefinition, StaticModel},
};
use rustclient_model::{Gender, ItemDefinitionId, KitId, ModelId, PlayerAppearance, SlotContent};
use std::collections::BTreeMap;

const RECOLOR_FROM: [u16; 5] = [6798, 8741, 25238, 4626, 4550];
const RECOLOR_TO: [&[u16]; 5] = [
    &[
        6798, 107, 10283, 16, 4797, 7744, 5799, 4634, 33697, 22433, 2983, 54193, 8, 5281, 10438,
        3650, 38214, 43691, 200, 571, 908, 21830, 28946, 49835, 51526, 43414, 937, 8130, 52114,
        30385,
    ],
    &[
        8741, 12, 64030, 43162, 7735, 8404, 1701, 38430, 24094, 10153, 56621, 4783, 1341, 16578,
        35003, 25239, 8, 5281, 10438, 3650, 38214, 43691, 200, 571, 908, 21830, 28946, 49835,
        51526,
    ],
    &[
        25238, 8742, 12, 64030, 43162, 7735, 8404, 1701, 38430, 24094, 10153, 56621, 4783, 1341,
        16578, 35003, 8, 5281, 10438, 3650, 38214, 43691, 200, 571, 908, 21830, 28946, 49835,
        51526,
    ],
    &[4626, 11146, 6439, 12, 4758, 10270],
    &[
        4550, 4537, 5681, 5673, 5790, 6806, 8076, 4574, 17050, 0, 127, 33715, 47545,
    ],
];
const TINT_FROM: [u16; 5] = [55232, 9104, 0, 0, 0];
const TINT_TO: [&[u16]; 5] = [
    &[
        6554, 115, 10304, 28, 5702, 7756, 5681, 4510, 33701, 22437, 2859, 54197, 16, 5157, 10446,
        3658, 38222, 43571, 472, 580, 784, 21966, 28950, 49839, 51534, 43420, 945, 8144, 52122,
        30389,
    ],
    &[
        9104, 10275, 7595, 3610, 7975, 8526, 918, 38802, 24466, 10145, 58654, 5027, 1457, 16565,
        34991, 25486, 24, 5392, 10429, 3673, 38201, 43579, 192, 687, 412, 21821, 28835, 50076,
        51517,
    ],
    &[],
    &[],
    &[],
];

#[must_use]
pub fn body_recolors(colors: [u8; 5]) -> Vec<(u16, u16)> {
    let mut out = Vec::new();
    for slot in 0..5 {
        let choice = usize::from(colors[slot]);
        if let Some(to) = RECOLOR_TO[slot].get(choice) {
            out.push((RECOLOR_FROM[slot], *to));
        }
        if slot < 2
            && let Some(to) = TINT_TO[slot].get(choice)
        {
            out.push((TINT_FROM[slot], *to));
        }
    }
    out
}

/// Assemble an unposed player base model from appearance slots, kit defs, and item defs.
#[must_use]
pub fn build_player_base(
    appearance: &PlayerAppearance,
    kits: &BTreeMap<KitId, KitDefinition>,
    items: &BTreeMap<ItemDefinitionId, ItemDefinition>,
    models: &BTreeMap<ModelId, StaticModel>,
) -> Option<StaticModel> {
    if appearance.hidden {
        return None;
    }
    let mut part_models = Vec::new();
    for slot in &appearance.slots {
        match slot {
            SlotContent::Empty => {}
            SlotContent::Kit(kit_id) => {
                if let Some(kit_def) = kits.get(kit_id) {
                    for m_id in &kit_def.models {
                        if let Some(source) = models.get(m_id) {
                            part_models.push(adjusted_model(
                                source,
                                &kit_def.recolors,
                                &kit_def.retextures,
                                0,
                            ));
                        }
                    }
                }
            }
            SlotContent::Item(item_id) => {
                if let Some(item_def) = items.get(item_id) {
                    let wear = match appearance.gender {
                        Gender::Male => &item_def.male,
                        Gender::Female => &item_def.female,
                    };
                    for m_id in wear.models.iter().flatten() {
                        if let Some(source) = models.get(m_id) {
                            part_models.push(adjusted_model(
                                source,
                                &item_def.recolors,
                                &item_def.retextures,
                                i32::from(wear.offset),
                            ));
                        }
                    }
                }
            }
        }
    }
    if part_models.is_empty() {
        return None;
    }
    let refs: Vec<&StaticModel> = part_models.iter().collect();
    let combined = combine_models(&refs);
    let recolors = body_recolors(appearance.colors);
    Some(adjusted_model(&combined, &recolors, &[], 0))
}
