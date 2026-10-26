use rustclient_application::builtin_config::{BuiltinConfigurations, value};
use rustclient_platform::{BlobStorage, StorageError, StorageErrorKind, StorageKey};
use rustclient_plugin_host::configuration::ConfigValue;
use std::{
    cell::{Cell, RefCell},
    collections::BTreeMap,
};

#[derive(Default)]
struct Store {
    bytes: RefCell<BTreeMap<String, Vec<u8>>>,
    fail: Cell<bool>,
}
impl BlobStorage for Store {
    fn get(&self, key: &StorageKey) -> Result<Option<Vec<u8>>, StorageError> {
        Ok(self.bytes.borrow().get(key.as_str()).cloned())
    }
    fn put(&self, key: &StorageKey, bytes: &[u8]) -> Result<(), StorageError> {
        if self.fail.get() {
            return Err(StorageError::new(StorageErrorKind::Capacity, "disk full"));
        }
        self.bytes
            .borrow_mut()
            .insert(key.as_str().into(), bytes.to_vec());
        Ok(())
    }
    fn delete(&self, key: &StorageKey) -> Result<(), StorageError> {
        self.bytes.borrow_mut().remove(key.as_str());
        Ok(())
    }
}

#[test]
fn preparation_and_persistence_failures_never_publish_effect_or_configuration() {
    let store = Store::default();
    let mut settings = BuiltinConfigurations::load(Some(&store)).unwrap();
    let applied = Cell::new(true);
    settings
        .change(
            "dev.rustclient.gpu",
            "smoothBanding",
            ConfigValue::Boolean(false),
            &store,
            |schema, document| Ok(value(schema, document, "smoothBanding").cloned().unwrap()),
            |prepared| applied.set(prepared == ConfigValue::Boolean(true)),
        )
        .unwrap();
    assert!(!applied.get());
    store.fail.set(true);
    assert!(
        settings
            .change(
                "dev.rustclient.gpu",
                "smoothBanding",
                ConfigValue::Boolean(true),
                &store,
                |_, _| Ok(true),
                |prepared| applied.set(prepared)
            )
            .is_err()
    );
    assert!(!applied.get());
    assert_eq!(
        settings
            .get("dev.rustclient.gpu")
            .unwrap()
            .value("smoothBanding"),
        Some(&ConfigValue::Boolean(false))
    );
    let restored = BuiltinConfigurations::load(Some(&store)).unwrap();
    assert_eq!(
        restored
            .get("dev.rustclient.gpu")
            .unwrap()
            .value("smoothBanding"),
        Some(&ConfigValue::Boolean(false))
    );
    store.fail.set(false);
    assert!(
        settings
            .change(
                "dev.rustclient.gpu",
                "brightTextures",
                ConfigValue::Boolean(true),
                &store,
                |_, _| Err::<(), _>("effect unavailable".into()),
                |()| panic!("failed preparation committed")
            )
            .is_err()
    );
    assert_eq!(settings.get("dev.rustclient.gpu").unwrap().generation(), 1);
}
