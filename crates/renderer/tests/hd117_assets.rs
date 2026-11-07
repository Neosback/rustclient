//! The vendored 117 HD tree must stay byte-identical to the recorded upstream copy.
use sha2::{Digest, Sha256};
use std::collections::BTreeMap;
use std::fmt::Write as _;
use std::fs;
use std::path::{Path, PathBuf};

fn root() -> PathBuf {
    Path::new(env!("CARGO_MANIFEST_DIR")).join("assets/hd117")
}

fn collect(dir: &Path, base: &Path, out: &mut Vec<String>) {
    let mut entries: Vec<_> = fs::read_dir(dir)
        .expect("read asset directory")
        .map(|entry| entry.expect("directory entry").path())
        .collect();
    entries.sort();
    for path in entries {
        if path.is_dir() {
            collect(&path, base, out);
        } else {
            let relative = path.strip_prefix(base).expect("under asset root");
            out.push(relative.to_string_lossy().replace('\\', "/"));
        }
    }
}

fn hex(bytes: &[u8]) -> String {
    bytes.iter().fold(String::new(), |mut text, byte| {
        let _ = write!(text, "{byte:02x}");
        text
    })
}

#[test]
fn vendored_assets_match_the_manifest() {
    let root = root();
    let manifest = fs::read_to_string(root.join("MANIFEST.sha256")).expect("manifest");
    let mut expected = BTreeMap::new();
    for line in manifest.lines().filter(|line| !line.is_empty()) {
        let (hash, path) = line.split_once("  ").expect("`<sha256>  <path>` line");
        assert!(
            expected.insert(path.to_owned(), hash.to_owned()).is_none(),
            "duplicate manifest entry {path}"
        );
    }
    let mut actual = Vec::new();
    for folder in ["upstream-resources", "upstream-schemas"] {
        collect(&root.join(folder), &root, &mut actual);
    }
    actual.sort();
    let expected_paths: Vec<String> = expected.keys().cloned().collect();
    assert_eq!(
        actual, expected_paths,
        "file set differs from MANIFEST.sha256"
    );
    for (path, hash) in &expected {
        let bytes = fs::read(root.join(path)).expect("read asset");
        assert_eq!(&hex(&Sha256::digest(&bytes)), hash, "{path} changed");
    }
}
