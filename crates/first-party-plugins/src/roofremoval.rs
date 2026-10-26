//! Port of `net.runelite.client.plugins.roofremoval.RoofRemovalPlugin` (BSD-2, see `NOTICE`).
//!
//! The source maps four booleans to `Scene.setRoofRemovalMode` flags and marks override areas
//! `TILE_FLAG_UNDER_ROOF` in the scene's tile settings before the roofs are built. Here the same
//! flags are requested through `SceneFlags::roofs` and the areas through `PluginOutput::roof_areas`;
//! the scene builds the roofs from them and hides the matching ones (`rustclient_scene::RoofView`).
//!
//! Not ported: instanced scenes (`WorldPoint.fromLocalInstance`), because the client does not load
//! instanced maps yet; the source's region lookup is the plain world region there.

use crate::NativePlugin;
use rustclient_plugin_api::{GameStateKind, PluginOutput, RoofArea, RoofModes, SceneFlags};
use rustclient_plugin_host::{PluginCall, PluginFault};
use serde::Deserialize;
use std::collections::BTreeMap;

/// `RoofRemovalConfigOverride.POH`: the player-owned-house regions.
const POH_REGIONS: [u32; 9] = [7257, 7534, 7535, 7790, 7791, 8046, 8047, 8302, 8303];
/// `Constants.REGION_SIZE`.
const REGION_SIZE: i32 = 64;
const MAX_PLANE: u8 = 3;

/// One `FlaggedArea` of `overrides.jsonc`, in tile coordinates inside its region.
#[derive(Debug, Clone, Copy, Deserialize)]
struct Flagged {
    rx1: i32,
    ry1: i32,
    rx2: i32,
    ry2: i32,
    z1: u8,
    z2: u8,
}

/// `overrides.jsonc` is JSON with `//` and `/* */` comments, which Gson tolerates.
fn strip_comments(text: &str) -> String {
    let mut out = String::with_capacity(text.len());
    let mut chars = text.chars().peekable();
    let (mut in_string, mut escaped) = (false, false);
    while let Some(c) = chars.next() {
        if in_string {
            out.push(c);
            match (escaped, c) {
                (true, _) => escaped = false,
                (false, '\\') => escaped = true,
                (false, '"') => in_string = false,
                _ => {}
            }
            continue;
        }
        match (c, chars.peek()) {
            ('"', _) => {
                in_string = true;
                out.push(c);
            }
            ('/', Some('/')) => {
                for next in chars.by_ref() {
                    if next == '\n' {
                        out.push('\n');
                        break;
                    }
                }
            }
            ('/', Some('*')) => {
                chars.next();
                let mut previous = ' ';
                for next in chars.by_ref() {
                    if previous == '*' && next == '/' {
                        break;
                    }
                    previous = next;
                }
            }
            _ => out.push(c),
        }
    }
    out
}

fn load_overrides() -> BTreeMap<u32, Vec<Flagged>> {
    let text = strip_comments(include_str!("../data/roofremoval/overrides.jsonc"));
    // The data ships with the crate; a parse failure is a build defect, caught by the tests.
    serde_json::from_str::<BTreeMap<String, Vec<Flagged>>>(&text)
        .unwrap_or_default()
        .into_iter()
        .filter_map(|(region, areas)| Some((region.parse().ok()?, areas)))
        .collect()
}

/// Regions whose tiles overlap the scene, as `Scene.getMapRegions` lists them.
fn scene_regions(base: (i32, i32), size: (u32, u32)) -> Vec<(i32, i32)> {
    let width = i32::try_from(size.0).unwrap_or(0);
    let height = i32::try_from(size.1).unwrap_or(0);
    if width == 0 || height == 0 {
        return Vec::new();
    }
    let mut regions = Vec::new();
    for rx in base.0.div_euclid(REGION_SIZE)..=(base.0 + width - 1).div_euclid(REGION_SIZE) {
        for ry in base.1.div_euclid(REGION_SIZE)..=(base.1 + height - 1).div_euclid(REGION_SIZE) {
            regions.push((rx, ry));
        }
    }
    regions
}

fn region_id((rx, ry): (i32, i32)) -> Option<u32> {
    Some(u32::try_from(rx).ok()? << 8 | u32::try_from(ry).ok()?)
}

/// The areas computed for one scene base, scene size and POH setting.
struct CachedAreas {
    base: (i32, i32),
    size: (u32, u32),
    poh: bool,
    areas: Vec<RoofArea>,
}

#[derive(Default)]
pub(crate) struct RoofRemoval {
    overrides: BTreeMap<u32, Vec<Flagged>>,
    /// Recomputed only when the scene or the POH setting changes.
    cached: Option<CachedAreas>,
}

impl RoofRemoval {
    pub(crate) fn new() -> Self {
        Self {
            overrides: load_overrides(),
            cached: None,
        }
    }

    fn areas(&mut self, base: (i32, i32), size: (u32, u32), poh: bool) -> Vec<RoofArea> {
        if let Some(cached) = &self.cached
            && (cached.base, cached.size, cached.poh) == (base, size, poh)
        {
            return cached.areas.clone();
        }
        let mut areas = Vec::new();
        for region in scene_regions(base, size) {
            let Some(id) = region_id(region) else {
                continue;
            };
            let (origin_x, origin_y) = (region.0 * REGION_SIZE, region.1 * REGION_SIZE);
            if poh && POH_REGIONS.contains(&id) {
                // `configOverrideRegions`: every tile of the region on every plane.
                areas.push(RoofArea {
                    min: (origin_x, origin_y),
                    max: (origin_x + REGION_SIZE - 1, origin_y + REGION_SIZE - 1),
                    planes: (0, MAX_PLANE),
                });
            } else if let Some(flagged) = self.overrides.get(&id) {
                // Tiles in the config-override region win over the data, as in the source's
                // `if / else if`, so an overridden region contributes only its whole-region area.
                areas.extend(
                    flagged
                        .iter()
                        .filter(|area| {
                            area.rx1 <= area.rx2 && area.ry1 <= area.ry2 && area.z1 <= area.z2
                        })
                        .map(|area| RoofArea {
                            min: (origin_x + area.rx1, origin_y + area.ry1),
                            max: (origin_x + area.rx2, origin_y + area.ry2),
                            planes: (area.z1, area.z2.min(MAX_PLANE)),
                        }),
                );
            }
        }
        self.cached = Some(CachedAreas {
            base,
            size,
            poh,
            areas: areas.clone(),
        });
        areas
    }
}

impl NativePlugin for RoofRemoval {
    fn init(&mut self, _call: &mut PluginCall<'_>) -> Result<(), PluginFault> {
        if self.overrides.is_empty() {
            self.overrides = load_overrides();
        }
        self.cached = None;
        Ok(())
    }

    fn output(&mut self, call: &mut PluginCall<'_>) -> Result<PluginOutput, PluginFault> {
        let config = call.config;
        let mut modes = 0;
        for (key, flag) in [
            ("removePosition", RoofModes::POSITION),
            ("removeHovered", RoofModes::HOVERED),
            ("removeDestination", RoofModes::DESTINATION),
            ("removeBetween", RoofModes::BETWEEN),
        ] {
            if config.boolean(key) {
                modes |= flag;
            }
        }
        let poh = config.boolean("overridePOH");
        let client = call.client().ok();
        let roof_areas = client
            .filter(|client| client.state != GameStateKind::LoginScreen)
            .map_or_else(Vec::new, |client| {
                self.areas(client.scene_base, client.scene_size, poh)
            });
        Ok(PluginOutput {
            scene: Some(SceneFlags {
                roofs: RoofModes::new(modes),
                ..SceneFlags::default()
            }),
            roof_areas,
            ..PluginOutput::default()
        })
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn comments_are_stripped_without_touching_strings() {
        let text = "{ \"a\": \"x // y\", // note\n \"b\": [1, /* two */ 2] }";
        let value: serde_json::Value = serde_json::from_str(&strip_comments(text)).unwrap();
        assert_eq!(value["a"], "x // y");
        assert_eq!(value["b"][1], 2);
    }

    #[test]
    fn the_shipped_override_data_parses_completely() {
        let overrides = load_overrides();
        assert_eq!(overrides.len(), 131, "regions in overrides.jsonc");
        assert_eq!(overrides.values().map(Vec::len).sum::<usize>(), 527);
        assert!(overrides.values().flatten().all(|area| {
            [area.rx1, area.rx2, area.ry1, area.ry2]
                .iter()
                .all(|v| (0..REGION_SIZE).contains(v))
                && area.z1 <= area.z2
                && area.z2 <= MAX_PLANE
        }));
        // Region 13871 carries one area with its y bounds reversed; the source's loops visit no
        // tile for it, and `areas` drops it the same way.
        let inverted: Vec<_> = overrides
            .iter()
            .flat_map(|(region, areas)| areas.iter().map(move |area| (*region, area)))
            .filter(|(_, area)| area.rx1 > area.rx2 || area.ry1 > area.ry2)
            .collect();
        assert_eq!(inverted.len(), 1);
        assert_eq!(inverted[0].0, 13871);
        let mut plugin = RoofRemoval::new();
        let (rx, ry) = (13871 / 256, 13871 % 256);
        let base = (rx * REGION_SIZE, ry * REGION_SIZE);
        assert_eq!(
            plugin.areas(base, (64, 64), false).len(),
            overrides[&13871].len() - 1
        );
        // First entry of the source file: the Smoke Dungeon.
        assert_eq!(overrides[&12946].len(), 1);
    }

    #[test]
    fn scene_regions_cover_the_104_tile_window_across_region_borders() {
        // A scene starting at world (3168, 3168) spans regions x 49..=51 and y 49..=51.
        let regions = scene_regions((3168, 3168), (104, 104));
        assert_eq!(regions.len(), 9);
        assert!(regions.contains(&(49, 49)) && regions.contains(&(51, 51)));
        assert!(scene_regions((0, 0), (0, 0)).is_empty());
        assert_eq!(region_id((50, 50)), Some(12850));
    }

    #[test]
    fn areas_follow_the_loaded_regions_and_the_poh_switch() {
        let mut plugin = RoofRemoval::new();
        // Smoke Dungeon (12946 = region 50, 146 is outside our scene) -> choose a scene around it.
        let (rx, ry) = (12946 / 256, 12946 % 256);
        let base = (rx * REGION_SIZE, ry * REGION_SIZE);
        let areas = plugin.areas(base, (64, 64), false);
        assert_eq!(areas.len(), 1);
        assert_eq!(areas[0].min, base);
        assert_eq!(areas[0].max, (base.0 + 35, base.1 + 63));
        // A scene far from every override has none.
        assert!(plugin.areas((0, 0), (64, 64), false).is_empty());
        // The POH switch forces the whole region on every plane and only then.
        let poh_base = ((7257 / 256) * REGION_SIZE, (7257 % 256) * REGION_SIZE);
        assert!(plugin.areas(poh_base, (64, 64), false).is_empty());
        let poh = plugin.areas(poh_base, (64, 64), true);
        assert_eq!(
            poh,
            [RoofArea {
                min: poh_base,
                max: (poh_base.0 + 63, poh_base.1 + 63),
                planes: (0, 3),
            }]
        );
        // Results are cached per scene and setting, and refreshed when either changes.
        assert_eq!(plugin.areas(poh_base, (64, 64), true), poh);
        assert!(plugin.areas(poh_base, (64, 64), false).is_empty());
    }
}
