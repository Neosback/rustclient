# Reference Tree Agent Instructions

Everything under `references/` is reference-only evidence.

## Do not

- modify vendored/submodule reference source while implementing RustClient,
- make RustClient Cargo crates depend on reference crates,
- expose reference structs/classes as RustClient public API types,
- copy large implementations without first identifying the RustClient semantic owner,
- treat RuneLite, OpenRune or RustOSRS architecture as RustClient architecture,
- fix reference-source bugs as part of unrelated RustClient work,
- silently advance a reference submodule to a newer commit.

## Do

- inspect reference source to establish behavior or provenance,
- record the exact reference path when it materially informs a port or fixture,
- translate behavior into the correct RustClient semantic owner,
- add focused tests/fixtures for behavior we depend on,
- document deliberate deviations from reference behavior when they affect compatibility,
- update the gitlink only in a dedicated reviewed reference-snapshot change.

## Authority order

1. Accepted RustClient specs and ADRs.
2. RustClient semantic owners and tests.
3. Pinned reference source as behavioral/provenance evidence.

A reference implementation can be wrong, outdated, server-specific or architecturally unsuitable. Never bypass RustClient boundaries merely to mirror a reference implementation literally.
