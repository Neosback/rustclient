//! Integration contract families share a harness to avoid redundant links and process launches.
#[path = "cs1.rs"]
mod cs1;
#[path = "decoders.rs"]
mod decoders;
#[path = "fonts.rs"]
mod fonts;
#[path = "item_search.rs"]
mod item_search;
#[path = "legacy_widgets.rs"]
mod legacy_widgets;
#[path = "npc_animations.rs"]
mod npc_animations;
#[path = "scripts.rs"]
mod scripts;
#[path = "store.rs"]
mod store;
#[path = "widgets.rs"]
mod widgets;
