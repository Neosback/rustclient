# Native shell compatibility dependency

`blitz-dom-0.2.4` is the crates.io source archive, SHA-256
`c81d0f520bad6798a54cc4360bcabd245dc0aba2ce5917217d1d82ac5ee6bfed`.
Its upstream MIT/Apache notices are retained. The dependency change is the
normalized Cargo.toml dependency on `image`: `=0.25.6` becomes `=0.25.10` to
share RustClient's existing decoder. The decoder dependency change preserves behavior. The original
declaration remains in Cargo.toml.orig for comparison. A redundant assignment in
src/layout/construct.rs is removed: the same value is assigned unconditionally
four lines later. This silences a Rust 1.99 warning without changing behavior.

Source: https://crates.io/crates/blitz-dom/0.2.4
This directory is an explicit compatibility dependency, separate from pinned
behavioral evidence under references/. Generated build output does not belong here.

Sidebar compatibility fix (2026-10-27): `src/mutator.rs` detaches existing children
before inserting them at their new positions. Previously a keyed row moved within
the same parent was inserted and then removed again, corrupting retained lists.
Inserting a node before itself is a no-op. Repository-owned shell sidebar contracts
exercise favorites reordering and subsequent search/navigation against the real DOM.

`dioxus-native-dom-0.7.10` is the crates.io source archive, SHA-256
`0979a659aca5fe1ed94bc64ffba519b1d7d3d17a80967a4e927fc82a4fe3ba39`.
Its original MIT/Apache notices are retained. The only behavior patch is in
`src/mutation_writer.rs`: clear every ElementId mapping into a detached subtree
before reclaiming it. Blitz uses reusable slab indexes; stale descendant mappings
could otherwise point at a newly allocated template and free its children during
search, favorites reordering and subsequent settings navigation. The real native
DOM sidebar contracts exercise this lifecycle, including repeated reconstruction.
Source: https://crates.io/crates/dioxus-native-dom/0.7.10

`blitz-dom/src/node/node.rs` also applies overflow clipping before scroll offsets
in hit-testing and honors `pointer-events: none` for the hit element. This keeps
offscreen settings from intercepting fixed header controls and slider fill/tooltip
children from taking pointer capture. Sidebar contracts cover header clicks after
scrolling and captured slider input.
Removal also clears hover/focus/active/press references into detached subtrees,
so recycled slab indexes cannot masquerade as the previous hovered/focused control.
