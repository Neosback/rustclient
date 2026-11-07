//! Source-produced wire fixtures; no TS/Java runtime or external reference tree required.
use rustclient_model::{Plane, WorldTile};
use rustclient_platform::{ByteStream, TransportError};
use rustclient_protocol::{
    ClientIntent, Credentials, ServerProfile, ServerUpdate, VariableUpdate, tsps_241,
};
use std::collections::VecDeque;

const SUCCESS: &[u8] = include_bytes!("fixtures/tsps-241/login-success.bin");
const HANDSHAKE: &[u8] = include_bytes!("fixtures/tsps-241/handshake.bin");
const PLACEMENT: &[u8] = include_bytes!("fixtures/tsps-241/initial-placement.bin");
struct Stream {
    input: VecDeque<u8>,
    sent: Vec<Vec<u8>>,
}
impl ByteStream for Stream {
    fn receive(&mut self, bytes: &mut [u8]) -> impl Future<Output = Result<usize, TransportError>> {
        let n = bytes.len().min(self.input.len()).min(3);
        for byte in &mut bytes[..n] {
            *byte = self.input.pop_front().unwrap();
        }
        std::future::ready(Ok(n))
    }
    fn send(&mut self, bytes: &[u8]) -> impl Future<Output = Result<(), TransportError>> {
        self.sent.push(bytes.to_vec());
        std::future::ready(Ok(()))
    }
}
fn login() -> tsps_241::LoginSession {
    let mut stream = Stream {
        input: SUCCESS.iter().chain(HANDSHAKE).copied().collect(),
        sent: Vec::new(),
    };
    pollster::block_on(tsps_241::authenticate(
        &mut stream,
        Credentials::new("Test".into(), "fixture".into()).unwrap(),
    ))
    .unwrap()
}
#[test]
fn password_login_matches_the_typescript_encoder_and_preserves_following_packets() {
    let mut stream = Stream {
        input: SUCCESS
            .iter()
            .chain(HANDSHAKE)
            .chain(PLACEMENT)
            .copied()
            .collect(),
        sent: Vec::new(),
    };
    let session = pollster::block_on(tsps_241::authenticate(
        &mut stream,
        Credentials::new("Test".into(), "fixture".into()).unwrap(),
    ))
    .unwrap();
    assert_eq!(session.player.get(), 1);
    assert_eq!(session.appearance.display_name, "Test");
    assert!(session.member);
    assert_eq!(
        stream.sent[1],
        include_bytes!("fixtures/tsps-241/login-request.bin")
    );
    assert_eq!(stream.input.iter().copied().collect::<Vec<_>>(), PLACEMENT);
}
#[test]
fn server_refusal_is_typed_and_does_not_send_a_handshake() {
    let mut stream = Stream {
        input: include_bytes!("fixtures/tsps-241/login-rejected.bin")
            .iter()
            .copied()
            .collect(),
        sent: Vec::new(),
    };
    let error = pollster::block_on(tsps_241::authenticate(
        &mut stream,
        Credentials::new("Test".into(), "fixture".into()).unwrap(),
    ))
    .err()
    .unwrap();
    assert_eq!(
        error.rejection,
        Some(rustclient_model::LoginRejection::InvalidCredentials)
    );
    assert_eq!(stream.sent.len(), 2);
    assert!(!error.to_string().contains("fixture"));
}
#[test]
fn fragmented_bootstrap_and_coalesced_updates_match_server_produced_fixtures() {
    let mut codec = tsps_241::SessionCodec::new(login());
    let mut updates = Vec::new();
    let mut input = include_bytes!("fixtures/tsps-241/animations.bin").to_vec();
    input.extend(PLACEMENT);
    for byte in input {
        codec
            .receive_batches(&[byte], |batch| updates.extend(batch.updates))
            .unwrap();
    }
    assert!(!codec.awaiting_initial_rebuild());
    assert!(updates.iter().any(|u| matches!(u, ServerUpdate::InitialPlacement(p) if p.position == WorldTile::new(3200,3200,Plane::MIN))));
    assert!(updates.iter().any(|u| matches!(u, ServerUpdate::PlayerInfo(p) if p.local_appearance.as_ref().is_some_and(|a| a.movement_sequences.walk.unwrap().get()==819))));
    let bytes: Vec<_> = include_bytes!("fixtures/tsps-241/varp-small.bin")
        .iter()
        .chain(include_bytes!("fixtures/tsps-241/varp-large.bin"))
        .chain(include_bytes!("fixtures/tsps-241/inventory.bin"))
        .copied()
        .collect();
    updates.clear();
    codec
        .receive_batches(&bytes, |batch| updates.extend(batch.updates))
        .unwrap();
    assert_eq!(updates.len(), 3);
    assert!(matches!(
        updates[0],
        ServerUpdate::Variable(VariableUpdate::Set { value: 1, .. })
    ));
    assert!(matches!(
        updates[1],
        ServerUpdate::Variable(VariableUpdate::Set { value: 123_456, .. })
    ));
    let ServerUpdate::Inventory(rustclient_model::InventoryUpdate::Replace { slots, .. }) =
        &updates[2]
    else {
        panic!("inventory fixture")
    };
    assert_eq!(slots[0].unwrap().amount, 100_000);
}
#[test]
fn walking_and_prompt_responses_match_the_typescript_encoder() {
    use rustclient_model::{
        InterfaceId, ItemDefinitionId, WidgetAction, WidgetAddress, WidgetId, WidgetOperation,
    };

    let mut codec = tsps_241::SessionCodec::new(login());
    assert_eq!(
        codec
            .encode(ClientIntent::WidgetAction(WidgetAction {
                address: WidgetAddress {
                    widget: WidgetId::new(InterfaceId::new(149), 0),
                    child: Some(3)
                },
                operation: WidgetOperation::new(0).unwrap(),
                item: Some(ItemDefinitionId::new(995)),
                interface_epoch: 0,
            }))
            .unwrap(),
        include_bytes!("fixtures/tsps-241/widget-request.bin")
    );
    assert_eq!(
        codec
            .encode(ClientIntent::Walk {
                destination: WorldTile::new(3201, 3202, Plane::MIN),
                invert_run: true
            })
            .unwrap(),
        include_bytes!("fixtures/tsps-241/walk.bin")
    );
    assert_eq!(
        codec
            .encode(ClientIntent::Resume(
                rustclient_protocol::ResumeIntent::Count(123)
            ))
            .unwrap(),
        include_bytes!("fixtures/tsps-241/count-request.bin")
    );
    let chat = rustclient_protocol::Huffman::from_sizes(&[8; 256]).unwrap();
    let message = chat.encode_public_chat("hello").unwrap();
    codec.set_chat_codec(chat);
    assert_eq!(
        codec
            .encode(ClientIntent::Chat(
                rustclient_protocol::ChatIntent::Public {
                    chat_type: 0,
                    message
                }
            ))
            .unwrap(),
        include_bytes!("fixtures/tsps-241/chat-request.bin")
    );
    let mut updates = Vec::new();
    codec
        .receive_batches(include_bytes!("fixtures/tsps-241/logout.bin"), |batch| {
            updates.extend(batch.updates);
        })
        .unwrap();
    assert_eq!(
        updates,
        [ServerUpdate::Control(
            rustclient_protocol::SessionControl::Logout
        )]
    );
}
#[test]
fn unsupported_required_packets_and_buffer_floods_never_publish_success() {
    let mut codec = tsps_241::SessionCodec::new(login());
    let mut published = false;
    // VARBIT is assigned, but requires cache-resolved semantic variable ownership.
    assert!(
        codec
            .receive_batches(&[42, 0, 1, 0, 0, 0, 1], |_| published = true)
            .is_err()
    );
    assert!(!published);
    let mut fresh = tsps_241::SessionCodec::new(login());
    assert!(
        fresh
            .receive_batches(&vec![0; 131_073], |_| published = true)
            .is_err()
    );
    assert!(!published);
    assert_ne!(
        ServerProfile::TSPS_241.id(),
        ServerProfile::OPENRUNE_240_2.id()
    );
    assert_eq!(ServerProfile::TSPS_241.revision().major(), 241);
    assert!(ServerProfile::parse("tsps-240").is_err());
}

#[test]
fn script_ids_and_preconditions_match_source_bytes_in_commit_order() {
    use rustclient_model::{InventoryUpdate, ScriptValue};
    let mut codec = tsps_241::SessionCodec::new(login());
    let mut updates = Vec::new();
    codec
        .receive_batches(
            include_bytes!("fixtures/tsps-241/client-script.bin"),
            |batch| {
                updates.extend(batch.updates);
            },
        )
        .unwrap();
    let ServerUpdate::Script(script) = &updates[0] else {
        panic!("script fixture")
    };
    assert_eq!(script.id.get(), 1234);
    assert_eq!(
        script.arguments,
        [ScriptValue::Text("hello".into()), ScriptValue::Int(42)]
    );
    updates.clear();
    codec
        .receive_batches(
            include_bytes!("fixtures/tsps-241/widget-script.bin"),
            |batch| {
                updates.extend(batch.updates);
            },
        )
        .unwrap();
    assert_eq!(updates.len(), 3);
    assert!(matches!(
        updates[0],
        ServerUpdate::Variable(VariableUpdate::Set { value: 1, .. })
    ));
    let ServerUpdate::Inventory(InventoryUpdate::Replace { id, slots }) = &updates[1] else {
        panic!("inventory precondition")
    };
    assert_eq!(id.get(), 93);
    assert_eq!(slots.len(), 28);
    assert_eq!(slots[0].unwrap().amount, 100_000);
    let ServerUpdate::Script(script) = &updates[2] else {
        panic!("script after prerequisites")
    };
    assert_eq!(script.id.get(), 1234);
    assert_eq!(script.arguments[2], ScriptValue::Long(-1_234_567_890_123));
}

#[test]
fn subinterfaces_and_permission_ranges_preserve_semantic_effects() {
    use rustclient_model::{InterfaceMountKind, InterfaceUpdate, WidgetProperty};
    let mut codec = tsps_241::SessionCodec::new(login());
    let mut updates = Vec::new();
    codec
        .receive_batches(
            include_bytes!("fixtures/tsps-241/interface-open.bin"),
            |batch| {
                updates.extend(batch.updates);
            },
        )
        .unwrap();
    assert_eq!(updates.len(), 3);
    assert!(matches!(
        updates[0],
        ServerUpdate::Variable(VariableUpdate::Set { value: 1, .. })
    ));
    let ServerUpdate::Interface(InterfaceUpdate::Open(mount)) = updates[1] else {
        panic!("mount fixture")
    };
    assert_eq!(mount.parent.interface.get(), 548);
    assert_eq!(mount.parent.component, 20);
    assert_eq!(mount.interface.get(), 149);
    assert_eq!(mount.kind, InterfaceMountKind::Overlay);
    assert!(matches!(
        updates[2],
        ServerUpdate::Interface(InterfaceUpdate::SetProperty {
            property: WidgetProperty::Hidden(true),
            ..
        })
    ));
    for (bytes, first, last, operations, drag) in [
        (
            include_bytes!("fixtures/tsps-241/interface-flags.bin").as_slice(),
            -1,
            -1,
            1,
            false,
        ),
        (
            include_bytes!("fixtures/tsps-241/interface-range.bin").as_slice(),
            0,
            27,
            1,
            true,
        ),
        (
            include_bytes!("fixtures/tsps-241/interface-range-v2.bin").as_slice(),
            -1,
            27,
            0x8000_0001,
            false,
        ),
    ] {
        updates.clear();
        codec
            .receive_batches(bytes, |batch| {
                updates.extend(batch.updates);
            })
            .unwrap();
        let ServerUpdate::Interface(InterfaceUpdate::SetEvents(range)) = updates[0] else {
            panic!("event fixture")
        };
        assert_eq!((range.first, range.last), (first, last));
        assert_eq!(range.permissions.enabled_operations, operations);
        assert_eq!(range.permissions.accepts_drag, drag);
    }
}

#[test]
fn unrepresented_script_context_and_preconditions_never_publish_a_partial_packet() {
    for bytes in [
        include_bytes!("fixtures/tsps-241/widget-script-varbit.bin").as_slice(),
        include_bytes!("fixtures/tsps-241/interface-scoped-script.bin").as_slice(),
        include_bytes!("fixtures/tsps-241/script-inventory-limit.bin").as_slice(),
        include_bytes!("fixtures/tsps-241/logout-reason.bin").as_slice(),
    ] {
        let mut codec = tsps_241::SessionCodec::new(login());
        let mut published = false;
        assert!(codec.receive_batches(bytes, |_| published = true).is_err());
        assert!(!published);
    }
}

#[test]
fn varbits_resolve_definition_ranges_without_mirroring_live_variable_state() {
    use rustclient_model::{VarpBitRange, VarpId};
    let range = VarpBitRange::new(VarpId::new(42), 4, 7).unwrap();
    let mut codec = tsps_241::SessionCodec::new(login());
    codec.set_varbit_lookup(Box::new(move |id| {
        assert_eq!(id.get(), 1);
        Ok(range)
    }));
    for (bytes, count) in [
        (include_bytes!("fixtures/tsps-241/varbit.bin").as_slice(), 1),
        (
            include_bytes!("fixtures/tsps-241/widget-script-varbit.bin").as_slice(),
            2,
        ),
        (
            include_bytes!("fixtures/tsps-241/interface-varbit.bin").as_slice(),
            2,
        ),
    ] {
        let mut updates = Vec::new();
        codec
            .receive_batches(bytes, |batch| {
                updates.extend(batch.updates);
            })
            .unwrap();
        assert_eq!(updates.len(), count);
        assert_eq!(
            updates[0],
            ServerUpdate::Variable(VariableUpdate::SetBits { range, value: 1 })
        );
    }
}

#[test]
fn every_source_player_frame_preserves_its_authoritative_map_header() {
    use rustclient_model::MapAnchor;
    let mut codec = tsps_241::SessionCodec::new(login());
    codec.receive_batches(PLACEMENT, |_| {}).unwrap();
    for (bytes, anchor) in [
        (
            include_bytes!("fixtures/tsps-241/player-idle.bin").as_slice(),
            MapAnchor { x: 3200, y: 3200 },
        ),
        (
            include_bytes!("fixtures/tsps-241/player-map-shift.bin").as_slice(),
            MapAnchor { x: 3208, y: 3200 },
        ),
        (
            include_bytes!("fixtures/tsps-241/player-map-repeat.bin").as_slice(),
            MapAnchor { x: 3208, y: 3200 },
        ),
    ] {
        let mut updates = Vec::new();
        codec
            .receive_batches(bytes, |batch| {
                updates.extend(batch.updates);
            })
            .unwrap();
        assert_eq!(updates.len(), 2);
        assert_eq!(updates[0], ServerUpdate::SetMapAnchor(anchor));
        assert!(matches!(updates[1], ServerUpdate::PlayerInfo(_)));
    }
}
