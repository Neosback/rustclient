//! Integration contract families share a harness to avoid redundant links and process launches.
#[path = "api_v2.rs"]
mod api_v2;
#[path = "directory_storage.rs"]
mod directory_storage;
#[path = "event_routing.rs"]
mod event_routing;
#[path = "first_party_schemas.rs"]
mod first_party_schemas;
#[path = "lifecycle.rs"]
mod lifecycle;
#[path = "menu_stage.rs"]
mod menu_stage;
#[path = "panels.rs"]
mod panels;
#[path = "required_configuration.rs"]
mod required_configuration;
#[path = "storage_mediation.rs"]
mod storage_mediation;
