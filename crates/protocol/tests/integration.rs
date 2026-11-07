//! Integration contract families share a harness to avoid redundant links and process launches.
#[path = "framing.rs"]
mod framing;
#[path = "js5.rs"]
mod js5;
#[path = "packet_groups.rs"]
mod packet_groups;
#[path = "tsps_241.rs"]
mod tsps_241;
