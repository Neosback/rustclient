//! Integration contract families share a harness to avoid redundant links and process launches.
#[path = "action_playback.rs"]
mod action_playback;
#[path = "actor_effects.rs"]
mod actor_effects;
#[path = "actor_turning.rs"]
mod actor_turning;
#[path = "chat.rs"]
mod chat;
#[path = "connection.rs"]
mod connection;
#[path = "facing.rs"]
mod facing;
#[path = "ground_items.rs"]
mod ground_items;
#[path = "inventories.rs"]
mod inventories;
#[path = "npcs.rs"]
mod npcs;
#[path = "player_options.rs"]
mod player_options;
#[path = "players.rs"]
mod players;
#[path = "projection.rs"]
mod projection;
#[path = "scene.rs"]
mod scene;
#[path = "scenery.rs"]
mod scenery;
#[path = "speech.rs"]
mod speech;
#[path = "title.rs"]
mod title;
#[path = "widgets.rs"]
mod widgets;
