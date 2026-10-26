# Native shell compatibility dependency

`blitz-dom-0.2.4` is the crates.io source archive, SHA-256
`c81d0f520bad6798a54cc4360bcabd245dc0aba2ce5917217d1d82ac5ee6bfed`.
Its upstream MIT/Apache notices are retained. The dependency change is the
normalized Cargo.toml dependency on `image`: `=0.25.6` becomes `=0.25.10` to
share RustClient's existing decoder. Source behavior is unchanged. The original
declaration remains in Cargo.toml.orig for comparison. A redundant assignment in
src/layout/construct.rs is removed: the same value is assigned unconditionally
four lines later. This silences a Rust 1.99 warning without changing behavior.

Source: https://crates.io/crates/blitz-dom/0.2.4
This directory is an explicit compatibility dependency, separate from pinned
behavioral evidence under references/. Generated build output does not belong here.
