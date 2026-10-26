#![forbid(unsafe_code)]

use std::path::Path;
use std::process::{Command, ExitCode};

mod cache;

const REFERENCE_PATH: &str = "references/rustosrs";
const EXPECTED_ORIGIN: &str = "https://github.com/Neosback/RustOSRS";

fn main() -> ExitCode {
    match run() {
        Ok(()) => ExitCode::SUCCESS,
        Err(error) => {
            eprintln!("{error}");
            ExitCode::FAILURE
        }
    }
}

fn run() -> Result<(), String> {
    let mut args = std::env::args().skip(1);
    let command = args.next();
    if command.as_deref() == Some("cache") {
        return cache::run(args.collect());
    }
    let action = args.next();
    let extra = args.next();

    match (command.as_deref(), action.as_deref(), extra.as_deref()) {
        (None | Some("help"), None, None) => {
            print_help();
            Ok(())
        }
        (Some("references"), Some("sync"), None) => references_sync(),
        (Some("references"), Some("status"), None) => references_status(),
        (Some("references"), None, None) => Err(
            "missing references action; use `cargo xtask references status` or `cargo xtask references sync`"
                .to_owned(),
        ),
        _ => Err("unknown xtask command; use `cargo xtask help`".to_owned()),
    }
}

fn print_help() {
    println!(
        "RustClient xtask\n\n\
         Commands:\n\
           references status  verify pinned submodule and vendored reference trees\n\
           references sync    initialize/update pinned reference checkout\n\n\
           cache inspect --cache-dir PATH --cache-profile osrs-240  validate title assets\n\n\
         Planned commands: architecture, check, plugin, fixtures"
    );
}

fn references_sync() -> Result<(), String> {
    run_git(&["submodule", "sync", "--", REFERENCE_PATH])?;
    run_git(&[
        "submodule",
        "update",
        "--init",
        "--recursive",
        "--",
        REFERENCE_PATH,
    ])?;
    references_status()
}

fn references_status() -> Result<(), String> {
    let expected = expected_reference_sha()?;
    let git_marker = Path::new(REFERENCE_PATH).join(".git");
    if !git_marker.exists() {
        return Err(format!(
            "{REFERENCE_PATH} is not initialized; run `cargo xtask references sync`"
        ));
    }

    let actual = git_stdout(&["-C", REFERENCE_PATH, "rev-parse", "HEAD"])?;
    let origin = git_stdout(&["-C", REFERENCE_PATH, "remote", "get-url", "origin"])?;

    if normalize_repo_url(&origin) != normalize_repo_url(EXPECTED_ORIGIN) {
        return Err(format!(
            "unexpected reference origin: expected {EXPECTED_ORIGIN}, found {origin}"
        ));
    }

    if actual != expected {
        return Err(format!(
            "reference checkout is out of sync: expected {expected}, found {actual}; run `cargo xtask references sync`"
        ));
    }
    let changes = git_stdout(&[
        "-C",
        REFERENCE_PATH,
        "status",
        "--porcelain",
        "--untracked-files=normal",
    ])?;
    if !changes.is_empty() {
        return Err(format!(
            "{REFERENCE_PATH} has local reference changes: {changes}"
        ));
    }

    println!("RustOSRS reference OK");
    println!("  path:     {REFERENCE_PATH}");
    println!("  commit:   {actual}");
    println!("  origin:   {origin}");
    for (path, description) in [
        (
            "references/open-runelite-rev-240",
            "open-runelite revision 240 (runelite-api, client, GPU plugin)",
        ),
        (
            "references/rl-suite-240",
            "rl-suite deobfuscated 240 client sources",
        ),
        (
            "references/rsprox-master",
            "rsprox client/server packet evidence through OSRS 235",
        ),
        (
            "references/runelite-melxin-235",
            "RuneLite 235 readable client tree (revision 235, not 240)",
        ),
    ] {
        let entry = git_stdout(&["ls-tree", "HEAD", "--", path])?;
        let fields: Vec<_> = entry.split_whitespace().collect();
        if fields.len() != 4 || fields[0] != "040000" || fields[1] != "tree" {
            return Err(format!(
                "{path} is not a vendored tree in the current commit"
            ));
        }
        let changes = git_stdout(&[
            "status",
            "--porcelain",
            "--untracked-files=normal",
            "--",
            path,
        ])?;
        if !changes.is_empty() {
            return Err(format!(
                "{path} differs from the committed reference snapshot: {changes}"
            ));
        }
        println!("{description} reference OK");
        println!("  path:     {path}");
        println!("  git tree: {}", fields[2]);
    }
    Ok(())
}

fn expected_reference_sha() -> Result<String, String> {
    let entry = git_stdout(&["ls-tree", "HEAD", "--", REFERENCE_PATH])?;
    let mut fields = entry.split_whitespace();
    let mode = fields.next();
    let kind = fields.next();
    let sha = fields.next();

    match (mode, kind, sha) {
        (Some("160000"), Some("commit"), Some(sha)) => Ok(sha.to_owned()),
        _ => Err(format!(
            "{REFERENCE_PATH} is not recorded as a Git submodule in the current commit"
        )),
    }
}

fn run_git(args: &[&str]) -> Result<(), String> {
    let status = Command::new("git")
        .args(args)
        .status()
        .map_err(|error| format!("failed to run git {args:?}: {error}"))?;

    if status.success() {
        Ok(())
    } else {
        Err(format!("git {args:?} failed with {status}"))
    }
}

fn git_stdout(args: &[&str]) -> Result<String, String> {
    let output = Command::new("git")
        .args(args)
        .output()
        .map_err(|error| format!("failed to run git {args:?}: {error}"))?;

    if !output.status.success() {
        let stderr = String::from_utf8_lossy(&output.stderr);
        return Err(format!(
            "git {args:?} failed with {}: {}",
            output.status,
            stderr.trim()
        ));
    }

    String::from_utf8(output.stdout)
        .map(|value| value.trim().to_owned())
        .map_err(|error| format!("git {args:?} returned non-UTF-8 output: {error}"))
}

fn normalize_repo_url(url: &str) -> &str {
    let trimmed = url.trim().trim_end_matches('/');
    trimmed.strip_suffix(".git").unwrap_or(trimmed)
}

#[cfg(test)]
mod tests {
    use super::normalize_repo_url;

    #[test]
    fn normalizes_optional_git_suffix_and_trailing_slash() {
        assert_eq!(
            normalize_repo_url("https://github.com/Neosback/RustOSRS.git/"),
            "https://github.com/Neosback/RustOSRS"
        );
    }
}
