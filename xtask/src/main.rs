#![forbid(unsafe_code)]

use std::process::ExitCode;

fn main() -> ExitCode {
    let command = std::env::args().nth(1);
    match command.as_deref() {
        Some("help") | None => {
            println!("RustClient xtask foundation\n\nPlanned commands: architecture, check, plugin, fixtures");
            ExitCode::SUCCESS
        }
        Some(other) => {
            eprintln!("unknown xtask command: {other}");
            ExitCode::FAILURE
        }
    }
}
