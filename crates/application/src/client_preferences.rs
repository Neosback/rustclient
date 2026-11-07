//! Application-owned client preferences that belong to this machine rather than to the account:
//! where the window was left. Brightness, volumes and chat filters are server variables in the
//! stock client (they are saved with the account), so they are deliberately not kept here.
use rustclient_platform::{BlobStorage, StorageKey};
use serde::{Deserialize, Serialize};

/// Largest window extent accepted from a preferences file, in physical pixels.
const MAX_EXTENT: u32 = 16_384;
/// Window title-bar strip that must stay reachable on some monitor, in physical pixels.
const GRAB_HEIGHT: i64 = 32;
const GRAB_WIDTH: i64 = 96;

/// A monitor's desktop rectangle in physical pixels.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct MonitorRect {
    pub x: i32,
    pub y: i32,
    pub width: u32,
    pub height: u32,
}

/// Where the window was when the client last closed.
#[derive(Debug, Clone, Copy, PartialEq, Eq, Serialize, Deserialize)]
pub struct WindowPlacement {
    /// Outer position of the restored (non-maximized) window.
    pub x: i32,
    pub y: i32,
    /// Inner size of the restored (non-maximized) window.
    pub width: u32,
    pub height: u32,
    pub maximized: bool,
}
impl WindowPlacement {
    /// The placement with its size clamped to `min..=16384`, or `None` when it makes no sense.
    #[must_use]
    pub fn sanitized(self, min: (u32, u32)) -> Option<Self> {
        if self.width == 0 || self.height == 0 {
            return None;
        }
        Some(Self {
            width: self.width.clamp(min.0, MAX_EXTENT.max(min.0)),
            height: self.height.clamp(min.1, MAX_EXTENT.max(min.1)),
            ..self
        })
    }
    /// Whether enough of the title bar lies on a monitor for the player to grab the window. A
    /// disconnected monitor would otherwise restore the window out of reach.
    #[must_use]
    pub fn reachable(&self, monitors: &[MonitorRect]) -> bool {
        let left = i64::from(self.x);
        let top = i64::from(self.y);
        let right = left + i64::from(self.width);
        let bottom = top + GRAB_HEIGHT;
        monitors.iter().any(|monitor| {
            let m_left = i64::from(monitor.x);
            let m_top = i64::from(monitor.y);
            let m_right = m_left + i64::from(monitor.width);
            let m_bottom = m_top + i64::from(monitor.height);
            right.min(m_right) - left.max(m_left) >= GRAB_WIDTH
                && bottom.min(m_bottom) - top.max(m_top) >= GRAB_HEIGHT / 2
        })
    }
}

#[derive(Debug, Default, Clone, PartialEq, Eq, Serialize, Deserialize)]
pub struct ClientPreferences {
    #[serde(default)]
    pub window: Option<WindowPlacement>,
}
impl ClientPreferences {
    fn key() -> Result<StorageKey, String> {
        StorageKey::parse("application/client-v1.json").map_err(|error| error.to_string())
    }
    /// Missing preferences use defaults; unreadable ones are reported and defaults used.
    /// # Errors
    /// Reports storage failures and malformed JSON.
    pub fn load(storage: &dyn BlobStorage) -> Result<Self, String> {
        let bytes = storage
            .get(&Self::key()?)
            .map_err(|error| error.to_string())?;
        match bytes {
            None => Ok(Self::default()),
            Some(bytes) if bytes.len() > 4096 => {
                Err("Client preferences exceed the size limit".into())
            }
            Some(bytes) => serde_json::from_slice(&bytes)
                .map_err(|error| format!("Client preferences: {error}")),
        }
    }
    /// # Errors
    /// Reports encoding and storage failures; the previous file stays intact.
    pub fn save(&self, storage: &dyn BlobStorage) -> Result<(), String> {
        let bytes = serde_json::to_vec(self).map_err(|error| error.to_string())?;
        storage
            .put(&Self::key()?, &bytes)
            .map_err(|error| format!("Save client preferences: {error}"))
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    use rustclient_platform::StorageError;
    use std::cell::RefCell;

    #[derive(Default)]
    struct Storage(RefCell<Option<Vec<u8>>>);
    impl BlobStorage for Storage {
        fn get(&self, _: &StorageKey) -> Result<Option<Vec<u8>>, StorageError> {
            Ok(self.0.borrow().clone())
        }
        fn put(&self, _: &StorageKey, bytes: &[u8]) -> Result<(), StorageError> {
            *self.0.borrow_mut() = Some(bytes.to_vec());
            Ok(())
        }
        fn delete(&self, _: &StorageKey) -> Result<(), StorageError> {
            *self.0.borrow_mut() = None;
            Ok(())
        }
    }
    const SCREEN: MonitorRect = MonitorRect {
        x: 0,
        y: 0,
        width: 1920,
        height: 1080,
    };
    fn placement(x: i32, y: i32) -> WindowPlacement {
        WindowPlacement {
            x,
            y,
            width: 1000,
            height: 600,
            maximized: false,
        }
    }

    #[test]
    fn preferences_round_trip_and_default_when_missing() {
        let storage = Storage::default();
        assert_eq!(
            ClientPreferences::load(&storage).unwrap(),
            ClientPreferences::default()
        );
        let saved = ClientPreferences {
            window: Some(placement(40, 50)),
        };
        saved.save(&storage).unwrap();
        assert_eq!(ClientPreferences::load(&storage).unwrap(), saved);
        *storage.0.borrow_mut() = Some(b"{broken".to_vec());
        assert!(ClientPreferences::load(&storage).is_err());
    }

    #[test]
    fn sizes_are_clamped_and_degenerate_windows_dropped() {
        let min = (765, 503);
        let tiny = WindowPlacement {
            width: 10,
            height: 10,
            ..placement(0, 0)
        };
        let clamped = tiny.sanitized(min).unwrap();
        assert_eq!((clamped.width, clamped.height), min);
        let huge = WindowPlacement {
            width: u32::MAX,
            height: u32::MAX,
            ..placement(0, 0)
        };
        assert_eq!(huge.sanitized(min).unwrap().width, MAX_EXTENT);
        assert!(
            WindowPlacement {
                width: 0,
                ..placement(0, 0)
            }
            .sanitized(min)
            .is_none()
        );
    }

    #[test]
    fn a_window_left_on_a_missing_monitor_is_not_restored_out_of_reach() {
        assert!(placement(100, 100).reachable(&[SCREEN]));
        // Mostly off the left edge but the title bar still has room to grab.
        assert!(placement(-900, 10).reachable(&[SCREEN]));
        // On a monitor that is no longer attached.
        assert!(!placement(2500, 100).reachable(&[SCREEN]));
        assert!(!placement(100, 1200).reachable(&[SCREEN]));
        assert!(!placement(100, 100).reachable(&[]));
    }
}
