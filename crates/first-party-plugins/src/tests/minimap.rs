use super::*;
use rustclient_plugin_api::{ClientSnapshot, MinimapRequest};
use rustclient_plugin_host::PluginStorage;

fn id() -> PluginId {
    PluginId::parse("dev.rustclient.minimap").unwrap()
}

fn saved(harness: &Harness) -> Option<String> {
    harness.storage.load(&id()).get("zoomLevel").cloned()
}

fn at(cycle: u64, zoom: f64) -> GameSnapshot {
    GameSnapshot {
        client: ClientSnapshot {
            cycle,
            minimap_zoom: zoom,
            ..ClientSnapshot::default()
        },
        ..GameSnapshot::default()
    }
}

#[test]
fn by_default_the_wheel_zooms_and_nothing_is_recolored_or_hidden() {
    let mut harness = Harness::new();
    harness.enable("minimap", &[]);
    assert_eq!(
        harness.output(&at(0, 4.)).minimap,
        MinimapRequest {
            wheel_zoom: true,
            zoom: None,
            hidden: false,
            dot_colors: [None; MinimapRequest::DOT_KINDS],
        }
    );
}

#[test]
fn dot_colors_follow_the_source_frame_order_and_alpha_zero_keeps_the_stock_dot() {
    let mut harness = Harness::new();
    harness.enable(
        "minimap",
        &[
            ("item", ConfigValue::Color(0xFF01_0203)),
            ("clan", ConfigValue::Color(0xFF04_0506)),
            ("clanchat", ConfigValue::Color(0xFF07_0809)),
            ("hideMinimap", ConfigValue::Boolean(true)),
        ],
    );
    let output = harness.output(&at(0, 4.)).minimap;
    assert_eq!(
        output.dot_colors,
        [
            Some(0xFF01_0203),
            None,
            None,
            None,
            None,
            Some(0xFF04_0506),
            Some(0xFF07_0809)
        ]
    );
    assert!(output.hidden);
}

#[test]
fn the_saved_zoom_is_applied_once_when_the_wheel_is_on() {
    let mut harness = Harness::new();
    harness.storage.save(
        &id(),
        &std::collections::BTreeMap::from([("zoomLevel".to_owned(), "6.5".to_owned())]),
    );
    harness.enable("minimap", &[]);
    assert_eq!(harness.output(&at(0, 4.)).minimap.zoom, Some(6.5));
    assert_eq!(harness.output(&at(1, 6.5)).minimap.zoom, None);

    let mut off = Harness::new();
    off.storage.save(
        &id(),
        &std::collections::BTreeMap::from([("zoomLevel".to_owned(), "6.5".to_owned())]),
    );
    off.enable("minimap", &[("zoom", ConfigValue::Boolean(false))]);
    assert_eq!(off.output(&at(0, 4.)).minimap.zoom, None);
    assert!(!off.output(&at(1, 4.)).minimap.wheel_zoom);
}

#[test]
fn the_zoom_is_saved_every_eleven_seconds_and_only_when_it_moved() {
    let mut harness = Harness::new();
    harness.enable("minimap", &[]);
    harness.output(&at(100, 4.));
    harness.output(&at(649, 5.25));
    assert_eq!(saved(&harness), None, "eleven seconds have not passed");
    harness.output(&at(650, 5.25));
    assert_eq!(saved(&harness).as_deref(), Some("5.25"));
    // Unchanged zoom writes nothing; a corrupt stored value is ignored on the next start.
    harness.storage.save(
        &id(),
        &std::collections::BTreeMap::from([("zoomLevel".to_owned(), "x".to_owned())]),
    );
    harness.output(&at(1500, 5.25));
    assert_eq!(saved(&harness).as_deref(), Some("x"));
}
