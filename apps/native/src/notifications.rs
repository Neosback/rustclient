//! Delivery of plugin notifications on the desktop.
//!
//! `RuneLite`'s `Notifier` drops a notification while the window is focused unless "send when focused"
//! is on, then raises a tray message and optionally flashes the taskbar. The shell does the same with
//! what the platform offers without extra dependencies: the taskbar attention request for
//! [`NotificationKind::Flash`], and the operating system's own notification command where one exists.
//! The text is passed as program arguments, never through a shell or script source.

use rustclient_plugin_api::{Notification, NotificationKind};
use std::process::{Command, Stdio};
use winit::window::{UserAttentionType, Window};

/// The command line that shows one desktop notification, if this platform has a standard one.
fn command(title: &str, body: &str) -> Option<Command> {
    if cfg!(target_os = "macos") {
        let mut command = Command::new("osascript");
        // The strings are script arguments (`item n of argv`), so no quoting of them is needed.
        command.args([
            "-e",
            "on run argv",
            "-e",
            "display notification (item 1 of argv) with title (item 2 of argv)",
            "-e",
            "end run",
            "--",
            body,
            title,
        ]);
        Some(command)
    } else if cfg!(target_os = "linux") {
        let mut command = Command::new("notify-send");
        command.args(["--app-name=RustClient", "--", title, body]);
        Some(command)
    } else {
        None
    }
}

/// Raise `notifications` for a window that does or does not have focus.
pub(super) fn deliver(window: &Window, focused: bool, notifications: &[Notification]) {
    if focused {
        return;
    }
    for notification in notifications {
        if notification.kind == NotificationKind::Flash {
            window.request_user_attention(Some(UserAttentionType::Informational));
        }
        let Some(mut command) = command(&notification.title, &notification.body) else {
            continue;
        };
        command
            .stdin(Stdio::null())
            .stdout(Stdio::null())
            .stderr(Stdio::null());
        // A missing helper (no `notify-send`) just means no desktop notification.
        if let Ok(mut child) = command.spawn() {
            // Reap the helper without blocking the frame.
            std::thread::spawn(move || {
                let _ = child.wait();
            });
        }
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn text_travels_as_arguments_not_script_source() {
        let Some(command) = command("RustClient - \"Me\"", "'; do shell script \"x\"") else {
            return;
        };
        let args: Vec<String> = command
            .get_args()
            .map(|arg| arg.to_string_lossy().into_owned())
            .collect();
        assert!(args.iter().any(|arg| arg == "'; do shell script \"x\""));
        // The fixed program text never embeds the message.
        assert!(
            !args
                .iter()
                .filter(|arg| arg.starts_with("display") || arg.starts_with("on run"))
                .any(|arg| arg.contains("shell script"))
        );
    }
}
