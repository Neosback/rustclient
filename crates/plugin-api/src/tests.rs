use super::*;

#[test]
fn api_versions_are_additive_within_a_major() {
    let host = PluginApiVersion::current();
    assert!(PluginApiVersion::new(0, 0).is_compatible_with(host));
    assert!(PluginApiVersion::new(0, 1).is_compatible_with(host));
    assert!(PluginApiVersion::new(0, 2).is_compatible_with(host));
    assert!(!PluginApiVersion::new(0, 3).is_compatible_with(host));
    assert!(!PluginApiVersion::new(1, 0).is_compatible_with(host));
    assert_eq!(PluginApiVersion::parse("0.2"), Ok(host));
    assert!(PluginApiVersion::parse("0").is_err());
}

#[test]
fn plugin_ids_reject_malformed_names() {
    assert!(PluginId::parse("dev.rustclient.hello-overlay").is_ok());
    assert!(PluginId::parse("hello").is_err());
    assert!(PluginId::parse("dev.RustClient").is_err());
    assert!(PluginId::parse("dev..overlay").is_err());
}

#[test]
fn capability_names_round_trip() {
    for capability in Capability::ALL {
        assert_eq!(Capability::parse(capability.as_str()), Ok(capability));
    }
    assert!(Capability::parse("game.write.players").is_err());
}

#[test]
fn capability_sets_are_deny_by_default() {
    let mut grants = CapabilitySet::none();
    assert!(!grants.contains(Capability::UiOverlay));
    grants.insert(Capability::UiOverlay);
    grants.insert(Capability::UiOverlay);
    assert!(grants.contains(Capability::UiOverlay));
    assert_eq!(grants.len(), 1);
}
