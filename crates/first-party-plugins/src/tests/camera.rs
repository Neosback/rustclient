use super::*;
use rustclient_plugin_api::CameraCommand;

#[test]
fn the_defaults_send_the_unmodified_camera() {
    let mut harness = Harness::new();
    harness.enable("camera", &[]);
    let output = harness.output(&GameSnapshot::default());
    assert_eq!(
        output.camera,
        [
            CameraCommand::Speed(1.),
            CameraCommand::InvertYaw(false),
            CameraCommand::InvertPitch(false),
            CameraCommand::RelaxPitch(false),
        ]
    );
}

#[test]
fn settings_reach_the_camera_and_speed_is_clamped_as_the_client_does() {
    let mut harness = Harness::new();
    harness.enable(
        "camera",
        &[
            ("cameraSpeed", ConfigValue::Decimal(9_000)),
            ("invertYaw", ConfigValue::Boolean(true)),
            ("relaxCameraPitch", ConfigValue::Boolean(true)),
        ],
    );
    let output = harness.output(&GameSnapshot::default());
    assert_eq!(
        output.camera,
        [
            CameraCommand::Speed(5.),
            CameraCommand::InvertYaw(true),
            CameraCommand::InvertPitch(false),
            CameraCommand::RelaxPitch(true),
        ]
    );
}
