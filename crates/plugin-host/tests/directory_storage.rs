//! Plugin configuration files: atomic saves, kept corrupt files and surfaced failures.
#![cfg(not(target_arch = "wasm32"))]
use rustclient_plugin_api::PluginId;
use rustclient_plugin_host::{DirectoryStorage, PluginStorage};
use std::{collections::BTreeMap, path::PathBuf};

fn scratch(name: &str) -> PathBuf {
    let dir = std::env::temp_dir().join(format!(
        "rustclient-directory-storage-{name}-{}",
        std::process::id()
    ));
    let _ = std::fs::remove_dir_all(&dir);
    dir
}

fn values(pairs: &[(&str, &str)]) -> BTreeMap<String, String> {
    pairs
        .iter()
        .map(|(k, v)| ((*k).to_owned(), (*v).to_owned()))
        .collect()
}

#[test]
fn saves_round_trip_and_leave_no_staging_file() {
    let root = scratch("round-trip");
    let id = PluginId::parse("test.storage").unwrap();
    let mut storage = DirectoryStorage::new(root.clone());
    storage.save(&id, &values(&[("a", "1")]));
    storage.save(&id, &values(&[("a", "2"), ("b", "3")]));
    assert_eq!(storage.load(&id), values(&[("a", "2"), ("b", "3")]));
    let names: Vec<_> = std::fs::read_dir(root.join("test.storage"))
        .unwrap()
        .map(|entry| entry.unwrap().file_name().to_string_lossy().into_owned())
        .collect();
    assert_eq!(names, ["config.json"]);
    assert!(storage.take_errors().is_empty());
    let _ = std::fs::remove_dir_all(root);
}

#[test]
fn a_corrupt_file_is_kept_and_reported_instead_of_silently_reset() {
    let root = scratch("corrupt");
    let id = PluginId::parse("test.storage").unwrap();
    let dir = root.join("test.storage");
    std::fs::create_dir_all(&dir).unwrap();
    std::fs::write(dir.join("config.json"), "{ not json").unwrap();
    let storage = DirectoryStorage::new(root.clone());
    assert!(storage.load(&id).is_empty());
    assert_eq!(
        std::fs::read_to_string(dir.join("config.json.corrupt")).unwrap(),
        "{ not json"
    );
    let errors = storage.take_errors();
    assert_eq!(errors.len(), 1);
    assert!(errors[0].contains("test.storage") && errors[0].contains("not valid"));
    assert!(storage.take_errors().is_empty());
    let _ = std::fs::remove_dir_all(root);
}

#[test]
fn a_save_that_cannot_happen_is_reported() {
    let root = scratch("blocked");
    std::fs::create_dir_all(&root).unwrap();
    // A file where the plugin folder must be makes the save impossible.
    std::fs::write(root.join("test.storage"), "in the way").unwrap();
    let id = PluginId::parse("test.storage").unwrap();
    let mut storage = DirectoryStorage::new(root.clone());
    storage.save(&id, &values(&[("a", "1")]));
    assert_eq!(storage.take_errors().len(), 1);
    let _ = std::fs::remove_dir_all(root);
}
