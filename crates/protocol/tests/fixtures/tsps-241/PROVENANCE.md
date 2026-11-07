# TSPS 241 protocol evidence

Source checkout: `/Users/tylercovalt/Documents/tsps`, commit
`ac7b52a21df137cbd6c011c73fd2f173cbc274d6`. `sources.json` pins inspected files.
The adjacent LICENSE retains TSPS BSD-2-Clause attribution for derived tables and layouts.

The configured cache is `osrs-241_2026-10-20`. OpenRS2 cache 2746 was downloaded and its archive hash pinned separately in sources.json; cache-wide acceptance remains incomplete.
This is a custom TSPS binary protocol, not stock OSRS-241 wire compatibility.
Unassigned or unsupported required packets fail explicitly. The 31 .bin cases and generated.json were exported by running the pinned TS server/client encoders through tools/tsps/export-fixtures.mts. They are interoperability evidence for those cases, not whole-client parity. Unknown/malformed cases remain Rust-native contracts.

The pinned TSPS cache selector and current OpenRS2 index both select 2746 for the exact configured revision/date; the selected record and index digest are recorded in sources.json. No cache is installed in the inspected TSPS checkout itself. This establishes the intended independently pinned artifact, not a live server/cache acceptance result.
