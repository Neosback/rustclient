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
