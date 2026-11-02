//! Port of `net.runelite.client.plugins.groundmarkers.GroundMarkerPlugin` and its overlay
//! (BSD-2, see `NOTICE`).
//!
//! Holding Shift over a tile and opening the menu offers `Mark` (or `Unmark` when the tile is
//! marked); choosing it toggles the tile. Marked tiles within 32 tiles of the player on the
//! player's plane are outlined and filled. Markers persist per region under `region_<id>`, in the
//! source's JSON: a list of `{regionId, regionX, regionY, z, color, label}` where `color` is the
//! serialized `java.awt.Color` (`{"value": <signed ARGB>, "falpha": 0.0}`).
//!
//! Not ported: the `Label` and `Color` menu entries (they need a text prompt and a color picker),
//! so labels stored by `RuneLite` are kept and written back but not drawn; the minimap outline
//! (`drawOnMinimap`); the import, export and clear options on the world map orb; the translation
//! of marked regions into instances; and the sub-worlds of boats.

use crate::NativePlugin;
use crate::npchighlight::{layered, stroke_width};
use rustclient_plugin_api::{
    Capability, EventInterest, GameEvent, GameEventKind, MenuKind, MenuOffer, OverlayCommand,
    PLUGIN_MENU_TYPE, PluginOutput, WorldPoint,
};
use rustclient_plugin_host::{PluginCall, PluginFault};
use serde_json::{Value, json};
use std::collections::BTreeMap;

/// `MAX_DRAW_DISTANCE`.
const MAX_DRAW_DISTANCE: i32 = 32;
const MARK: &str = "Mark";
const UNMARK: &str = "Unmark";
const TARGET: &str = "Tile";

/// One marked tile, as `GroundMarkerPoint`. Equality is the tile alone: the source excludes the
/// color and label from `equals`.
#[derive(Debug, Clone)]
struct Marker {
    x: u8,
    y: u8,
    plane: u8,
    /// Signed ARGB of the stored `Color`; `None` is an old marker that follows `markerColor`.
    color: Option<i32>,
    label: Option<String>,
}

impl Marker {
    fn is_at(&self, x: u8, y: u8, plane: u8) -> bool {
        (self.x, self.y, self.plane) == (x, y, plane)
    }
}

fn region_of(tile: WorldPoint) -> u32 {
    let (x, y) = (tile.x.max(0).cast_unsigned(), tile.y.max(0).cast_unsigned());
    ((x >> 6) << 8) | (y >> 6)
}

/// The tile inside its region, as `WorldPoint.getRegionX/Y`.
fn local_of(tile: WorldPoint) -> (u8, u8, u8) {
    (
        u8::try_from(tile.x & 63).unwrap_or(0),
        u8::try_from(tile.y & 63).unwrap_or(0),
        tile.plane,
    )
}

fn region_key(region: u32) -> String {
    format!("region_{region}")
}

/// `WorldPoint.fromRegion`.
fn world_of(region: u32, marker: &Marker) -> WorldPoint {
    WorldPoint::new(
        i32::try_from((region >> 8) << 6).unwrap_or(0) + i32::from(marker.x),
        i32::try_from((region & 0xFF) << 6).unwrap_or(0) + i32::from(marker.y),
        marker.plane,
    )
}

fn parse_region(text: &str) -> Vec<Marker> {
    let Ok(Value::Array(points)) = serde_json::from_str::<Value>(text) else {
        return Vec::new();
    };
    points
        .iter()
        .filter_map(|point| {
            let coordinate = |key: &str| {
                point
                    .get(key)
                    .and_then(Value::as_u64)
                    .and_then(|v| u8::try_from(v).ok())
            };
            Some(Marker {
                x: coordinate("regionX").filter(|v| *v < 64)?,
                y: coordinate("regionY").filter(|v| *v < 64)?,
                plane: coordinate("z").filter(|v| *v < 4)?,
                color: point
                    .get("color")
                    .and_then(|color| color.get("value"))
                    .and_then(Value::as_i64)
                    .and_then(|v| i32::try_from(v).ok()),
                label: point
                    .get("label")
                    .and_then(Value::as_str)
                    .map(str::to_owned),
            })
        })
        .collect()
}

fn write_region(region: u32, markers: &[Marker]) -> String {
    Value::Array(
        markers
            .iter()
            .map(|marker| {
                let mut point = json!({
                    "regionId": region,
                    "regionX": marker.x,
                    "regionY": marker.y,
                    "z": marker.plane,
                });
                if let Some(color) = marker.color {
                    point["color"] = json!({ "value": color, "falpha": 0.0 });
                }
                if let Some(label) = &marker.label {
                    point["label"] = Value::String(label.clone());
                }
                point
            })
            .collect(),
    )
    .to_string()
}

#[derive(Default)]
pub(crate) struct GroundMarkers {
    regions: BTreeMap<u32, Vec<Marker>>,
}

impl GroundMarkers {
    fn toggle(&mut self, call: &mut PluginCall<'_>, tile: WorldPoint) {
        let region = region_of(tile);
        let (x, y, plane) = local_of(tile);
        let color = call.config().color("markerColor").cast_signed();
        let markers = self.regions.entry(region).or_default();
        if let Some(at) = markers.iter().position(|m| m.is_at(x, y, plane)) {
            markers.remove(at);
        } else {
            markers.push(Marker {
                x,
                y,
                plane,
                color: Some(color),
                label: None,
            });
        }
        let text = (!markers.is_empty()).then(|| write_region(region, markers));
        if markers.is_empty() {
            self.regions.remove(&region);
        }
        if !call.granted(Capability::StoragePlugin) {
            return;
        }
        let Ok(mut values) = call.load_storage() else {
            return;
        };
        match text {
            Some(text) => values.insert(region_key(region), text),
            None => values.remove(&region_key(region)),
        };
        // A failed write leaves the marker for this session only; the next toggle tries again.
        let _ = call.save_storage(&values);
    }
}

impl NativePlugin for GroundMarkers {
    fn game_event_interests(&self) -> EventInterest {
        EventInterest::one(GameEventKind::MenuOptionClicked)
    }
    fn init(&mut self, call: &mut PluginCall<'_>) -> Result<(), PluginFault> {
        self.regions.clear();
        if !call.granted(Capability::StoragePlugin) {
            return Ok(());
        }
        for (key, text) in call.load_storage().unwrap_or_default() {
            let Some(region) = key
                .strip_prefix("region_")
                .and_then(|id| id.parse::<u32>().ok())
            else {
                continue;
            };
            let markers = parse_region(&text);
            if !markers.is_empty() {
                self.regions.insert(region, markers);
            }
        }
        Ok(())
    }
    fn events(
        &mut self,
        call: &mut PluginCall<'_>,
        events: &[GameEvent],
    ) -> Result<(), PluginFault> {
        for event in events {
            let GameEvent::MenuOptionClicked(entry) = event else {
                continue;
            };
            if entry.kind != MenuKind::Other(PLUGIN_MENU_TYPE)
                || entry.target != TARGET
                || !matches!(entry.option.as_str(), MARK | UNMARK)
            {
                continue;
            }
            // The entry names the tile the menu was opened on; its plane is the player's, as the
            // offer's was.
            let Some(plane) = call
                .local_player()
                .ok()
                .flatten()
                .map(|player| player.actor.world.plane)
            else {
                continue;
            };
            let tile = WorldPoint::new(entry.param0, entry.param1, plane);
            self.toggle(call, tile);
        }
        Ok(())
    }
    fn output(&mut self, call: &mut PluginCall<'_>) -> Result<PluginOutput, PluginFault> {
        let mut output = PluginOutput::default();
        let hovered = call.client().ok().and_then(|client| client.hovered_tile);
        if let Some(tile) = hovered {
            let (x, y, plane) = local_of(tile);
            let marked = self
                .regions
                .get(&region_of(tile))
                .is_some_and(|markers| markers.iter().any(|m| m.is_at(x, y, plane)));
            output.menu_offers.push(MenuOffer {
                option: if marked { UNMARK } else { MARK }.to_owned(),
                target: TARGET.to_owned(),
                tile,
                shift: true,
            });
        }
        let Some(player) = call
            .local_player()
            .ok()
            .flatten()
            .map(|player| player.actor.world)
        else {
            return Ok(output);
        };
        let config = call.config();
        let default_color = config.color("markerColor");
        let fill = u32::try_from(config.integer("fillOpacity").clamp(0, 255)).unwrap_or(0) << 24;
        let width = stroke_width(config.decimal("borderWidth"));
        // The regions the 32 tile radius can reach: at most four.
        let regions = [
            (player.x - MAX_DRAW_DISTANCE, player.y - MAX_DRAW_DISTANCE),
            (player.x + MAX_DRAW_DISTANCE, player.y - MAX_DRAW_DISTANCE),
            (player.x - MAX_DRAW_DISTANCE, player.y + MAX_DRAW_DISTANCE),
            (player.x + MAX_DRAW_DISTANCE, player.y + MAX_DRAW_DISTANCE),
        ]
        .map(|(x, y)| region_of(WorldPoint::new(x, y, 0)));
        for (index, region) in regions.into_iter().enumerate() {
            if regions[..index].contains(&region) {
                continue;
            }
            for marker in self.regions.get(&region).into_iter().flatten() {
                let tile = world_of(region, marker);
                if tile.plane != player.plane
                    || (tile.x - player.x).abs().max((tile.y - player.y).abs()) >= MAX_DRAW_DISTANCE
                {
                    continue;
                }
                output.overlay.push(layered(OverlayCommand::Tile {
                    tile,
                    size: 1,
                    argb: marker.color.map_or(default_color, i32::cast_unsigned),
                    fill_argb: fill,
                    width,
                }));
            }
        }
        Ok(output)
    }
}
