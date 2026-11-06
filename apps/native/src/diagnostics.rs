//! Process-level diagnostics: a bounded log ring and a panic hook that writes a crash report.
//!
//! Every `eprintln!` in this binary is also recorded in the ring (see the macro below), so a crash
//! report carries what the client was saying just before it died. Both the ring and the report
//! directory are process-wide because a panic hook has no other way to reach them; they hold only
//! diagnostic text, never game state, and are set up once at startup.
use std::{
    collections::VecDeque,
    path::{Path, PathBuf},
    sync::{Mutex, OnceLock},
};

/// Log lines kept for crash reports.
const RING_LINES: usize = 200;
/// Longest log line kept, in bytes; longer lines are cut at a character boundary.
const MAX_LINE: usize = 512;

/// `eprintln!` that also feeds the crash-report ring. Shadows the std macro for the binary's
/// modules so existing log sites need no change.
macro_rules! eprintln {
    () => {{
        $crate::diagnostics::record("");
        ::std::eprintln!();
    }};
    ($($arg:tt)*) => {{
        let line = ::std::format!($($arg)*);
        $crate::diagnostics::record(&line);
        ::std::eprintln!("{line}");
    }};
}

static RING: Mutex<VecDeque<String>> = Mutex::new(VecDeque::new());
static REPORT_DIR: OnceLock<PathBuf> = OnceLock::new();

/// Remember `line` for the next crash report.
pub fn record(line: &str) {
    let Ok(mut ring) = RING.lock() else {
        return;
    };
    push_bounded(&mut ring, line);
}

fn push_bounded(ring: &mut VecDeque<String>, line: &str) {
    let mut end = line.len().min(MAX_LINE);
    while !line.is_char_boundary(end) {
        end -= 1;
    }
    if ring.len() == RING_LINES {
        ring.pop_front();
    }
    ring.push_back(line[..end].to_owned());
}

/// Install the panic hook, writing reports under `directory`. The default hook still runs first,
/// so the message reaches the terminal as before.
pub fn install(directory: PathBuf) {
    if REPORT_DIR.set(directory).is_err() {
        return;
    }
    let previous = std::panic::take_hook();
    std::panic::set_hook(Box::new(move |info| {
        previous(info);
        let Some(directory) = REPORT_DIR.get() else {
            return;
        };
        let location = info
            .location()
            .map_or_else(|| "unknown location".to_owned(), ToString::to_string);
        let message = info
            .payload()
            .downcast_ref::<&str>()
            .map(|text| (*text).to_owned())
            .or_else(|| info.payload().downcast_ref::<String>().cloned())
            .unwrap_or_else(|| "non-text panic payload".to_owned());
        let lines: Vec<String> = RING
            .lock()
            .map(|ring| ring.iter().cloned().collect())
            .unwrap_or_default();
        let backtrace = std::backtrace::Backtrace::force_capture().to_string();
        let thread = std::thread::current();
        let report = render_report(
            &message,
            &location,
            thread.name().unwrap_or("unnamed"),
            &backtrace,
            &lines,
        );
        match write_report(directory, &report) {
            Ok(path) => ::std::eprintln!("crash report written to {}", path.display()),
            Err(error) => ::std::eprintln!("crash report could not be written: {error}"),
        }
    }));
}

/// The text of a crash report.
fn render_report(
    message: &str,
    location: &str,
    thread: &str,
    backtrace: &str,
    lines: &[String],
) -> String {
    let mut report = format!(
        "RustClient crash report\nversion: {}\nos: {} {}\nthread: {thread}\nlocation: {location}\n\
         message: {message}\n\nbacktrace:\n{backtrace}\n\nlast {} log lines:\n",
        env!("CARGO_PKG_VERSION"),
        std::env::consts::OS,
        std::env::consts::ARCH,
        lines.len(),
    );
    for line in lines {
        report.push_str(line);
        report.push('\n');
    }
    report
}

/// Write `report` to a new timestamped file in `directory`, keeping only the newest few.
fn write_report(directory: &Path, report: &str) -> std::io::Result<PathBuf> {
    /// Reports kept; older ones are removed so crashes cannot fill the disk.
    const KEEP: usize = 10;
    std::fs::create_dir_all(directory)?;
    let stamp = std::time::SystemTime::now()
        .duration_since(std::time::UNIX_EPOCH)
        .map_or(0, |elapsed| elapsed.as_secs());
    let mut path = directory.join(format!("crash-{stamp}.txt"));
    let mut suffix = 1;
    while path.exists() {
        path = directory.join(format!("crash-{stamp}-{suffix}.txt"));
        suffix += 1;
    }
    std::fs::write(&path, report)?;
    let mut reports: Vec<PathBuf> = std::fs::read_dir(directory)?
        .filter_map(Result::ok)
        .map(|entry| entry.path())
        .filter(|path| {
            path.extension().is_some_and(|ext| ext == "txt")
                && path
                    .file_name()
                    .and_then(|name| name.to_str())
                    .is_some_and(|name| name.starts_with("crash-"))
        })
        .collect();
    reports.sort();
    let excess = reports.len().saturating_sub(KEEP);
    for old in &reports[..excess] {
        let _ = std::fs::remove_file(old);
    }
    Ok(path)
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn the_ring_keeps_the_newest_lines_and_cuts_long_ones_on_a_character_boundary() {
        let mut ring = VecDeque::new();
        for n in 0..RING_LINES + 5 {
            push_bounded(&mut ring, &format!("line {n}"));
        }
        assert_eq!(ring.len(), RING_LINES);
        assert_eq!(ring.front().unwrap(), "line 5");
        let long = "é".repeat(MAX_LINE);
        push_bounded(&mut ring, &long);
        let kept = ring.back().unwrap();
        assert!(kept.len() <= MAX_LINE && kept.chars().all(|c| c == 'é'));
    }

    #[test]
    fn reports_carry_the_panic_and_the_recent_log_and_old_ones_are_pruned() {
        let directory =
            std::env::temp_dir().join(format!("rustclient-crash-test-{}", std::process::id()));
        let _ = std::fs::remove_dir_all(&directory);
        let lines = vec!["connecting".to_owned(), "scene load failed".to_owned()];
        let report = render_report("boom", "src/x.rs:1:1", "main", "frames", &lines);
        assert!(report.contains("message: boom") && report.contains("scene load failed"));
        for _ in 0..13 {
            write_report(&directory, &report).unwrap();
        }
        let count = std::fs::read_dir(&directory).unwrap().count();
        assert_eq!(count, 10);
        let _ = std::fs::remove_dir_all(&directory);
    }
}
