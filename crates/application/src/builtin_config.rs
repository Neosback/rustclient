//! Application ownership and two-phase publication of required-service configuration.
use rustclient_platform::{BlobStorage, StorageKey};
use rustclient_plugin_api::PluginId;
use rustclient_plugin_host::{
    configuration::{ConfigChanged, ConfigDocument, ConfigSchema, ConfigValue, Configuration},
    required::schemas,
};
use std::collections::BTreeMap;

pub struct BuiltinConfigurations {
    configurations: BTreeMap<PluginId, Configuration>,
}
impl BuiltinConfigurations {
    /// Recover invalid namespaces independently, preserving healthy namespaces and the original bytes.
    /// Recovery is visible to the caller; storage is only replaced after an explicit successful edit.
    /// # Errors
    /// Invalid embedded schemas cannot be recovered.
    pub fn load_recovering(
        storage: Option<&dyn BlobStorage>,
    ) -> Result<(Self, Vec<String>), String> {
        let mut configurations = BTreeMap::new();
        let mut diagnostics = Vec::new();
        for schema in schemas()? {
            let loaded = (|| {
                let key = Self::key(&schema.plugin)?;
                let bytes = storage
                    .map(|store| store.get(&key))
                    .transpose()
                    .map_err(|error| error.to_string())?
                    .flatten();
                Configuration::new(schema.clone(), bytes.as_deref())
            })();
            let configuration = match loaded {
                Ok(configuration) => configuration,
                Err(error) => {
                    diagnostics.push(format!(
                        "{}: {error}; defaults active, saved bytes retained until an explicit edit",
                        schema.plugin
                    ));
                    Configuration::new(schema, None)?
                }
            };
            configurations.insert(configuration.schema().plugin.clone(), configuration);
        }
        Ok((Self { configurations }, diagnostics))
    }
    /// Load all required namespaces, rejecting corrupt data rather than silently accepting a subset.
    /// # Errors
    /// Reports the namespace and storage/validation cause. The caller can offer explicit recovery.
    pub fn load(storage: Option<&dyn BlobStorage>) -> Result<Self, String> {
        let mut configurations = BTreeMap::new();
        for schema in schemas()? {
            let key = Self::key(&schema.plugin)?;
            let stored = storage
                .map(|storage| storage.get(&key))
                .transpose()
                .map_err(|error| format!("{}: {error}", schema.plugin))?
                .flatten();
            let configuration = Configuration::new(schema, stored.as_deref())?;
            configurations.insert(configuration.schema().plugin.clone(), configuration);
        }
        Ok(Self { configurations })
    }
    fn key(plugin: &PluginId) -> Result<StorageKey, String> {
        StorageKey::parse(format!("plugin-config/{plugin}/preferences-v1.json"))
            .map_err(|error| error.to_string())
    }
    pub fn iter(&self) -> impl Iterator<Item = &Configuration> {
        self.configurations.values()
    }
    #[must_use]
    pub fn get(&self, id: &str) -> Option<&Configuration> {
        self.configurations
            .iter()
            .find(|(plugin, _)| plugin.as_str() == id)
            .map(|(_, configuration)| configuration)
    }
    /// Prepare effects without publishing, atomically persist, then commit an infallible prepared effect.
    /// # Errors
    /// Invalid values, effect preparation and storage failures leave accepted preferences unchanged.
    pub fn change<P>(
        &mut self,
        plugin: &str,
        key: &str,
        value: ConfigValue,
        storage: &dyn BlobStorage,
        prepare: impl FnOnce(&ConfigSchema, &ConfigDocument) -> Result<P, String>,
        commit: impl FnOnce(P),
    ) -> Result<Option<ConfigChanged>, String> {
        let id = PluginId::parse(plugin).map_err(|error| error.to_string())?;
        let configuration = self
            .configurations
            .get_mut(&id)
            .ok_or_else(|| format!("unknown required service {id}"))?;
        let schema = configuration.schema().clone();
        let storage_key = Self::key(&id)?;
        configuration.change(key, value, |_, candidate| {
            let prepared = prepare(&schema, candidate)?;
            let bytes = serde_json::to_vec(candidate)
                .map_err(|error| format!("{id}: encode preferences: {error}"))?;
            storage
                .put(&storage_key, &bytes)
                .map_err(|error| format!("{id}: persist preferences: {error}"))?;
            commit(prepared);
            Ok(())
        })
    }
    /// Apply the same transaction to a complete reset.
    /// # Errors
    /// Any failed preparation/persistence preserves the accepted preferences.
    pub fn reset<P>(
        &mut self,
        plugin: &str,
        storage: &dyn BlobStorage,
        prepare: impl FnOnce(&ConfigSchema, &ConfigDocument) -> Result<P, String>,
        commit: impl FnOnce(P),
    ) -> Result<Option<ConfigChanged>, String> {
        let id = PluginId::parse(plugin).map_err(|error| error.to_string())?;
        let configuration = self
            .configurations
            .get_mut(&id)
            .ok_or_else(|| format!("unknown required service {id}"))?;
        let schema = configuration.schema().clone();
        let storage_key = Self::key(&id)?;
        configuration.reset(|_, candidate| {
            let prepared = prepare(&schema, candidate)?;
            let bytes = serde_json::to_vec(candidate).map_err(|error| error.to_string())?;
            storage
                .put(&storage_key, &bytes)
                .map_err(|error| format!("{id}: persist preferences: {error}"))?;
            commit(prepared);
            Ok(())
        })
    }
}

/// Read a candidate's effective value without creating a competing preference map.
#[must_use]
pub fn value<'a>(
    schema: &'a ConfigSchema,
    document: &'a ConfigDocument,
    key: &str,
) -> Option<&'a ConfigValue> {
    document.values.get(key).or_else(|| {
        schema
            .settings
            .iter()
            .find(|setting| setting.key == key)
            .map(|setting| &setting.default)
    })
}
