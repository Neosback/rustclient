//! Integration contract families share a harness to avoid redundant links and process launches.
#[path = "builtin_configuration.rs"]
mod builtin_configuration;
#[path = "client_cycle.rs"]
mod client_cycle;
#[path = "flames.rs"]
mod flames;
#[path = "widget_actions.rs"]
mod widget_actions;
#[path = "world_actions.rs"]
mod world_actions;
