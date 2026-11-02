use super::*;

/// Twelve offers with an option text past the 64 byte bound.
fn offers() -> Vec<rustclient_plugin_api::MenuOffer> {
    vec![
        rustclient_plugin_api::MenuOffer {
            option: "o".repeat(100),
            target: "Tile".into(),
            tile: rustclient_plugin_api::WorldPoint::new(3222, 3218, 0),
            shift: true,
        };
        12
    ]
}

fn full_output() -> PluginOutput {
    PluginOutput {
        panels: Vec::new(),
        overlay: vec![LayeredOverlay {
            layer: OverlayLayer::Top,
            command: OverlayCommand::Text {
                text: "x".repeat(1000),
                x: 1,
                y: 2,
                argb: 0xFFFF_FFFF,
                align: rustclient_plugin_api::HorizontalAlign::Left,
            },
        }],
        notifications: vec![
            Notification {
                title: "t".into(),
                body: "b".into(),
                kind: NotificationKind::Standard,
            };
            9
        ],
        info_boxes: vec![InfoBox {
            id: "i".into(),
            text: "123456789012345678".into(),
            argb: 0,
            tooltip: String::new(),
        }],
        menu_edits: vec![MenuEdit::MoveToTop { index: 0 }],
        menu_offers: offers(),
        entities: Some(EntityVisibility {
            hidden: vec![ActorRef::Npc(5)],
            hidden_overlays: vec![ActorRef::Npc(6)],
            hide_projectiles: true,
            hide_graphics: false,
        }),
        sprite_overrides: vec![
            SpriteOverride {
                sprite: 1,
                width: 2,
                height: 2,
                rgba: Arc::from(vec![0u8; 16]),
            },
            SpriteOverride {
                sprite: 2,
                width: 2,
                height: 2,
                rgba: Arc::from(vec![0u8; 3]),
            },
            SpriteOverride {
                sprite: 3,
                width: 2000,
                height: 1,
                rgba: Arc::from(vec![0u8; 8000]),
            },
        ],
        camera: vec![
            CameraCommand::YawTarget(-1),
            CameraCommand::PitchTarget(9999),
            CameraCommand::Speed(f32::NAN),
            CameraCommand::ZoomLimits { min: 5, max: 1 },
        ],
        scene: Some(SceneFlags {
            roofs: RoofModes::new(RoofModes::POSITION),
            smoothing: rustclient_plugin_api::AnimationSmoothing::new(
                rustclient_plugin_api::AnimationSmoothing::NPCS,
            ),
        }),
        roof_areas: vec![
            RoofArea {
                min: (10, 10),
                max: (12, 12),
                planes: (0, 1),
            },
            // Inverted bounds and a plane beyond the top are rejected.
            RoofArea {
                min: (5, 5),
                max: (1, 1),
                planes: (0, 0),
            },
            RoofArea {
                min: (1, 1),
                max: (2, 2),
                planes: (0, 4),
            },
        ],
        stretch: Some(Stretch {
            keep_aspect_ratio: true,
            integer_scaling: false,
            scale_percent: 5000,
            scaling: StretchScaling::Linear,
        }),
        smoothing_denied: (0..5000).collect(),
        frame_limit: Some(100_000),
        minimap: None,
    }
}

#[test]
fn outputs_without_grants_are_stripped_and_reported() {
    let game = snapshot();
    let mut harness = Harness::new();
    harness.add("dev.rustclient.none", &[], full_output(), false);
    let merged = harness.output(&game);
    assert_eq!(merged, rustclient_plugin_host::HostOutput::default());
    let denied: Vec<_> = harness
        .logs
        .records
        .iter()
        .filter(|record| record.message.contains("ungranted capability"))
        .map(|record| record.message.clone())
        .collect();
    for capability in [
        Capability::UiOverlay,
        Capability::ClientNotify,
        Capability::UiInfobox,
        Capability::UiMenuSwap,
        Capability::UiMenuDecorate,
        Capability::UiEntitiesHide,
        Capability::UiSpriteOverride,
        Capability::CameraControl,
        Capability::SceneControl,
        Capability::UiViewport,
        Capability::ClientFrameLimit,
    ] {
        assert!(
            denied
                .iter()
                .any(|message| message.contains(capability.as_str())),
            "no denial reported for {capability}: {denied:?}"
        );
    }
}

#[test]
fn granted_outputs_are_bounded_and_clamped() {
    let game = snapshot();
    let mut harness = Harness::new();
    harness.add(
        "dev.rustclient.all",
        &[
            Capability::UiOverlay,
            Capability::ClientNotify,
            Capability::UiInfobox,
            Capability::UiMenuSwap,
            Capability::UiMenuDecorate,
            Capability::UiEntitiesHide,
            Capability::UiSpriteOverride,
            Capability::CameraControl,
            Capability::SceneControl,
            Capability::UiViewport,
            Capability::ClientFrameLimit,
        ],
        full_output(),
        false,
    );
    let merged = harness.output(&game);
    assert!(
        harness.logs.records.is_empty(),
        "{:?}",
        harness.logs.records
    );
    let OverlayCommand::Text { text, .. } = &merged.overlay[0].command else {
        panic!("text overlay expected");
    };
    assert_eq!(text.len(), 256);
    assert_eq!(merged.notifications.len(), 4);
    assert_eq!(merged.info_boxes[0].1.text.len(), 16);
    assert_eq!(merged.menu_edits, [MenuEdit::MoveToTop { index: 0 }]);
    assert_eq!(merged.menu_offers.len(), 8);
    assert_eq!(merged.menu_offers[0].1.option.len(), 64);
    assert_eq!(merged.menu_offers[0].1.tile.x, 3222);
    assert_eq!(merged.entities.hidden, [ActorRef::Npc(5)]);
    assert_eq!(merged.entities.hidden_overlays, [ActorRef::Npc(6)]);
    assert!(merged.entities.hide_projectiles);
    assert_eq!(
        merged
            .sprite_overrides
            .iter()
            .map(|sprite| sprite.sprite)
            .collect::<Vec<_>>(),
        [1],
        "malformed or oversized sprites must be rejected"
    );
    assert_eq!(
        merged.camera,
        [
            CameraCommand::YawTarget(2047),
            CameraCommand::PitchTarget(383)
        ]
    );
    assert_eq!(merged.scene.roofs, RoofModes::new(RoofModes::POSITION));
    assert_eq!(
        merged.roof_areas,
        [RoofArea {
            min: (10, 10),
            max: (12, 12),
            planes: (0, 1)
        }],
        "malformed roof areas must be rejected"
    );
    assert_eq!(
        merged.stretch.map(|stretch| stretch.scale_percent),
        Some(800)
    );
    assert_eq!(
        merged.smoothing_denied.len(),
        rustclient_plugin_api::limits::SMOOTHING_DENIED
    );
    assert_eq!(
        merged.frame_limit,
        Some(rustclient_plugin_api::limits::MAX_FRAME_LIMIT)
    );
}

#[test]
fn the_lowest_frame_limit_among_plugins_applies() {
    let game = snapshot();
    let mut harness = Harness::new();
    let limit = |fps| PluginOutput {
        frame_limit: Some(fps),
        ..PluginOutput::default()
    };
    harness.add(
        "dev.rustclient.a",
        &[Capability::ClientFrameLimit],
        limit(60),
        false,
    );
    harness.add(
        "dev.rustclient.b",
        &[Capability::ClientFrameLimit],
        limit(0),
        false,
    );
    harness.add("dev.rustclient.c", &[], limit(1), false);
    // The second asks for zero, which clamps up to one frame per second; the third has no grant.
    assert_eq!(harness.output(&game).frame_limit, Some(1));
}

#[test]
fn minimap_requests_need_their_grant_clamp_the_zoom_and_merge_per_slot() {
    let game = snapshot();
    let mut harness = Harness::new();
    let request =
        |wheel_zoom, zoom, hidden, first: Option<u32>, second: Option<u32>| PluginOutput {
            minimap: Some(MinimapRequest {
                wheel_zoom,
                zoom,
                hidden,
                dot_colors: [first, second, None, None, None, None, None],
            }),
            ..PluginOutput::default()
        };
    harness.add(
        "dev.rustclient.a",
        &[Capability::UiMinimap],
        request(
            true,
            Some(100.),
            false,
            Some(0xFF11_1111),
            Some(0xFF22_2222),
        ),
        false,
    );
    harness.add(
        "dev.rustclient.b",
        &[Capability::UiMinimap],
        request(false, Some(f64::NAN), true, Some(0xFF33_3333), None),
        false,
    );
    harness.add(
        "dev.rustclient.c",
        &[],
        request(true, Some(3.), true, Some(0xFF44_4444), Some(0xFF55_5555)),
        false,
    );
    let merged = harness.output(&game).minimap;
    // The zoom is clamped to what the minimap can show; a non-finite one is dropped, so the
    // first plugin's request stands; the plugin without the grant contributes nothing.
    assert_eq!(merged.zoom, Some(MinimapRequest::MAX_ZOOM));
    assert!(merged.wheel_zoom && merged.hidden);
    assert_eq!(
        merged.dot_colors,
        [
            Some(0xFF33_3333),
            Some(0xFF22_2222),
            None,
            None,
            None,
            None,
            None
        ]
    );
}

#[test]
fn merging_unions_hidden_actors_and_orders_overlay_by_layer() {
    let game = snapshot();
    let mut harness = Harness::new();
    let hide = |actor| PluginOutput {
        entities: Some(EntityVisibility {
            hidden: vec![actor],
            ..EntityVisibility::default()
        }),
        overlay: vec![LayeredOverlay {
            layer: if matches!(actor, ActorRef::Npc(_)) {
                OverlayLayer::Top
            } else {
                OverlayLayer::Scene
            },
            command: OverlayCommand::Line {
                from: (0, 0),
                to: (1, 1),
                argb: 1,
            },
        }],
        scene: Some(SceneFlags {
            roofs: if matches!(actor, ActorRef::Npc(_)) {
                RoofModes::new(RoofModes::BETWEEN)
            } else {
                RoofModes::default()
            },
            ..SceneFlags::default()
        }),
        ..PluginOutput::default()
    };
    let caps = [
        Capability::UiEntitiesHide,
        Capability::UiOverlay,
        Capability::SceneControl,
    ];
    harness.add("dev.rustclient.first", &caps, hide(ActorRef::Npc(5)), false);
    harness.add(
        "dev.rustclient.second",
        &caps,
        hide(ActorRef::Player(2)),
        false,
    );
    let merged = harness.output(&game);
    assert_eq!(
        merged.entities.hidden,
        [ActorRef::Npc(5), ActorRef::Player(2)]
    );
    assert_eq!(merged.overlay[0].layer, OverlayLayer::Scene);
    assert_eq!(merged.overlay[1].layer, OverlayLayer::Top);
    assert_eq!(merged.scene.roofs, RoofModes::new(RoofModes::BETWEEN));
}

#[test]
fn faults_in_a_probe_disable_it_but_not_other_plugins() {
    let game = snapshot();
    let mut harness = Harness::new();
    harness.set = PluginSet::with_fault_limit(2);
    let seen = Rc::new(RefCell::new(Seen::default()));
    harness
        .set
        .register(
            manifest("dev.rustclient.bad", &[]),
            Box::new(Probe {
                seen: Rc::clone(&seen),
                output: PluginOutput::default(),
                query: false,
                fail: true,
            }),
        )
        .unwrap();
    let good = harness.add("dev.rustclient.good", &[], PluginOutput::default(), false);
    for tick in 0..3 {
        harness.set.dispatch_game(
            &[GameEvent::GameTick(tick)],
            &SnapshotView(&game),
            &mut harness.storage,
            &mut harness.logs,
        );
    }
    assert_eq!(seen.borrow().events.len(), 2, "disabled after two faults");
    assert_eq!(good.borrow().events.len(), 3);
}

#[test]
fn tile_and_hull_outlines_are_bounded() {
    use rustclient_plugin_api::{ActorRef, OverlayCommand, WorldPoint};
    let tile = |size, width| LayeredOverlay {
        layer: OverlayLayer::AboveScene,
        command: OverlayCommand::Tile {
            tile: WorldPoint {
                x: 1,
                y: 2,
                plane: 0,
            },
            size,
            argb: 0xFFFF_FFFF,
            fill_argb: 0,
            width,
        },
    };
    let output = PluginOutput {
        overlay: vec![
            tile(0, 2),
            tile(200, 0),
            LayeredOverlay {
                layer: OverlayLayer::AboveScene,
                command: OverlayCommand::ActorHull {
                    actor: ActorRef::Npc(1),
                    argb: 0,
                    fill_argb: 0,
                    width: 99,
                },
            },
        ],
        ..PluginOutput::default()
    };
    let mut harness = Harness::new();
    harness.add("dev.rustclient.a", &[Capability::UiOverlay], output, false);
    let merged = harness.output(&snapshot());
    let shapes: Vec<(u8, u8)> = merged
        .overlay
        .iter()
        .map(|item| match item.command {
            OverlayCommand::Tile { size, width, .. } => (size, width),
            OverlayCommand::ActorHull { width, .. } => (0, width),
            _ => (u8::MAX, u8::MAX),
        })
        .collect();
    // An empty area is dropped; oversized areas and widths clamp to the limits.
    assert_eq!(shapes, [(8, 1), (0, 8)]);
}
