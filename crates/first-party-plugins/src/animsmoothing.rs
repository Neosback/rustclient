//! Port of `net.runelite.client.plugins.animsmoothing.AnimationSmoothingPlugin` (BSD-2, see `NOTICE`).
//!
//! The source installs `Client.setAnimationInterpolationFilter`, a predicate over animation ids;
//! the injected client then blends player, NPC, object and widget animations between frames except
//! for the ids the predicate rejects. Here the same predicate is the class mask in
//! `SceneFlags::smoothing` plus the rejected ids in `PluginOutput::smoothing_denied`, applied by
//! `rustclient_scene::AnimationInterpolation` while entities and scenery are posed.
//!
//! The source has no settings besides the plugin switch, and neither does this port.

use crate::NativePlugin;
use rustclient_plugin_api::{AnimationSmoothing, PluginOutput, SceneFlags};
use rustclient_plugin_host::{PluginCall, PluginFault};

/// The animations `isAnimationInterpolatable` rejects, as `AnimationID` values, in source order.
pub(crate) const DENIED: [u32; 23] = [
    6566,  // DOG_UPDATE_JACKAL_DEFEND
    8266,  // WYRM_WALK
    8267,  // WYRM_READY
    8270,  // WYRM_ATTACK_MELEE
    8271,  // WYRM_ATTACK_MAGIC
    4652,  // GIANT_UPDATE_BASIC_ATTACK
    5530,  // GHOST_UPDATE_NORMAL_READY
    5531,  // GHOST_UPDATE_NORMAL_WALK
    5583,  // ZOMBIE_UPDATE_SIGN_WALK
    6818,  // DUCK_REWORK_UNDERWATER_READY_AND_EXTRA
    244,   // WILD_CAVE_CHAINMACE_READY
    1051,  // ELEMENTAL_WHEEL_SPIN
    5857,  // BRAIN_MILL_SAIL_ROTATE
    6495,  // FAI_VARROCK_MILLSAIL
    7898,  // DS2_LITHKREN_GENERATOR_WHEEL
    9450,  // WATERWHEEL_CHUTE
    8977,  // HUMAN_SPELLCAST_DEMONBANE
    367,   // POH_NEXUS
    3558,  // BURGH_BUCKET_DRIP
    4519,  // KEYKEEPER
    9493,  // TOA_SOT_CAST_B
    8499,  // BASILISK_KNIGHT_MELEE
    11291, // WGS_HEROIC_CLIMB_SUCCESS_TWO
];

#[derive(Default)]
pub(crate) struct AnimationSmoothingPlugin;

impl NativePlugin for AnimationSmoothingPlugin {
    fn output(&mut self, _call: &mut PluginCall<'_>) -> Result<PluginOutput, PluginFault> {
        Ok(PluginOutput {
            scene: Some(SceneFlags {
                smoothing: AnimationSmoothing::new(
                    AnimationSmoothing::PLAYERS
                        | AnimationSmoothing::NPCS
                        | AnimationSmoothing::OBJECTS
                        | AnimationSmoothing::WIDGETS,
                ),
                ..SceneFlags::default()
            }),
            smoothing_denied: DENIED.to_vec(),
            ..PluginOutput::default()
        })
    }
}
