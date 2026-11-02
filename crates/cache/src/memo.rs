//! Decoded definitions shared by every window load of one open cache.
//!
//! A cache is read-only and CRC-validated, so a decoded texture, model, kit or floor is the same
//! on every load. Each entry carries the digest of the source bytes it came from, which the
//! window's content hash composes instead of re-hashing the bytes on every load.
use crate::world::{FloorMaterial, KitDefinition, StaticModel, TextureAsset};
use rustclient_model::{FloorOverlayId, FloorUnderlayId, KitId, ModelId, TextureId};
use std::{
    collections::BTreeMap,
    sync::{Arc, Mutex, PoisonError, RwLock},
};

/// Decoded models kept across loads; reaching it drops the whole set so memory stays bounded.
const MODEL_LIMIT: usize = 4096;

/// A lazily filled, shareable result. Failures are not stored, so a later load retries.
///
/// Loading is single flight: a caller that finds another load in progress waits for it and takes
/// its result, so a background warm-up and a scene load never decode the same set twice.
pub(crate) struct Slot<T> {
    held: RwLock<Option<Arc<T>>>,
    loading: Mutex<()>,
}

impl<T> Default for Slot<T> {
    fn default() -> Self {
        Self {
            held: RwLock::new(None),
            loading: Mutex::new(()),
        }
    }
}

impl<T> Slot<T> {
    pub(crate) fn get_or_load<E>(&self, load: impl FnOnce() -> Result<T, E>) -> Result<Arc<T>, E> {
        if let Some(held) = self.peek() {
            return Ok(held);
        }
        let _loading = self.loading.lock().unwrap_or_else(PoisonError::into_inner);
        if let Some(held) = self.peek() {
            return Ok(held);
        }
        let loaded = Arc::new(load()?);
        *self.held.write().unwrap_or_else(PoisonError::into_inner) = Some(Arc::clone(&loaded));
        Ok(loaded)
    }

    fn peek(&self) -> Option<Arc<T>> {
        self.held
            .read()
            .unwrap_or_else(PoisonError::into_inner)
            .clone()
    }
}

pub(crate) struct TextureSet {
    pub colors: BTreeMap<TextureId, u16>,
    pub textures: Arc<BTreeMap<TextureId, TextureAsset>>,
    pub digest: [u8; 32],
}

pub(crate) struct FloorSet {
    pub underlays: BTreeMap<FloorUnderlayId, FloorMaterial>,
    pub overlays: BTreeMap<FloorOverlayId, FloorMaterial>,
    pub digest: [u8; 32],
}

pub(crate) struct KitSet {
    pub kits: BTreeMap<KitId, KitDefinition>,
    pub models: std::collections::BTreeSet<ModelId>,
    pub digest: [u8; 32],
}

pub(crate) struct ModelEntry {
    pub model: StaticModel,
    pub digest: [u8; 32],
}

#[derive(Default)]
pub(crate) struct Memo {
    pub textures: Slot<TextureSet>,
    pub floors: Slot<FloorSet>,
    pub kits: Slot<KitSet>,
    models: RwLock<BTreeMap<ModelId, Arc<ModelEntry>>>,
}

impl Memo {
    pub(crate) fn model<E>(
        &self,
        id: ModelId,
        load: impl FnOnce() -> Result<ModelEntry, E>,
    ) -> Result<Arc<ModelEntry>, E> {
        if let Some(held) = self
            .models
            .read()
            .unwrap_or_else(PoisonError::into_inner)
            .get(&id)
        {
            return Ok(Arc::clone(held));
        }
        let loaded = Arc::new(load()?);
        let mut models = self.models.write().unwrap_or_else(PoisonError::into_inner);
        if models.len() >= MODEL_LIMIT {
            models.clear();
        }
        models.insert(id, Arc::clone(&loaded));
        Ok(loaded)
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn slot_loads_once_and_retries_after_a_failure() {
        let slot = Slot::<u32>::default();
        assert_eq!(
            slot.get_or_load(|| Err::<u32, &str>("unreadable")),
            Err("unreadable")
        );
        let first = slot.get_or_load(|| Ok::<_, &str>(7)).unwrap();
        let second = slot
            .get_or_load(|| Err::<u32, _>("must not reload a held value"))
            .unwrap();
        assert!(Arc::ptr_eq(&first, &second));
    }

    #[test]
    fn concurrent_callers_share_one_load() {
        let slot = Slot::<u32>::default();
        let loads = std::sync::atomic::AtomicU32::new(0);
        std::thread::scope(|scope| {
            for _ in 0..4 {
                scope.spawn(|| {
                    let value = slot
                        .get_or_load(|| {
                            std::thread::sleep(std::time::Duration::from_millis(20));
                            loads.fetch_add(1, std::sync::atomic::Ordering::SeqCst);
                            Ok::<_, &str>(9)
                        })
                        .unwrap();
                    assert_eq!(*value, 9);
                });
            }
        });
        assert_eq!(loads.load(std::sync::atomic::Ordering::SeqCst), 1);
    }
}
