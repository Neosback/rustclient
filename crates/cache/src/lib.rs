//! Bounded JS5 cache decoding and revision-profile CPU assets. No filesystem or GPU access.
#![forbid(unsafe_code)]

mod assets;
mod container;
mod error;
mod profile;
mod reader;
mod sprite;
mod store;
mod table;

pub use assets::{BitmapFont, Image, Sprite};
pub use container::{MAX_CONTAINER_BYTES, decode_container};
pub use error::{CacheError, FileId, GroupId, IndexId, Source};
pub use profile::{AssetReport, CacheProfile, GameFrameSprites, TitleAssets};
pub use sprite::decode_sprites;
pub use store::{Cache, split_files};
pub use table::{Group, ReferenceTable, cp1252, name_hash};

pub mod osrs240;
pub mod widget;
pub mod world;

pub mod script;

impl world::SequenceAsset {
    /// Project revision-specific decoded sequence metadata into the shared scheduling vocabulary.
    #[must_use]
    pub const fn policy(&self) -> rustclient_model::SequencePolicy {
        rustclient_model::SequencePolicy {
            restart_mode: self.restart,
            replacement_priority: self.priority,
            movement_gate_with_backlog: self.movement_gate_with_backlog,
            movement_gate_without_backlog: self.movement_gate_without_backlog,
            max_loops: self.max_loops,
        }
    }
}

#[cfg(test)]
mod tests {
    use super::world::SequenceAsset;
    use rustclient_model::SequenceId;

    #[test]
    fn sequence_asset_projects_resolved_scheduling_policy() {
        let asset = SequenceAsset {
            id: SequenceId::new(42),
            frames: Vec::new(),
            delays: Vec::new(),
            frame_step: None,
            max_loops: 17,
            priority: 6,
            movement_gate_with_backlog: 2,
            movement_gate_without_backlog: 0,
            restart: 1,
            skeletal: None,
            range: None,
        };

        let policy = asset.policy();
        assert_eq!(policy.restart_mode, 1);
        assert_eq!(policy.replacement_priority, 6);
        assert_eq!(policy.movement_gate_with_backlog, 2);
        assert_eq!(policy.movement_gate_without_backlog, 0);
        assert_eq!(policy.max_loops, 17);
    }
}
