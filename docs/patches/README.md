# OpenRune guide-price close repair

The 2026-10-22 disposable-account reproduction opens equipment, selects **View
guide prices**, receives interface 464, then sends the valid CLOSE_MODAL request.
Opening the interface alone remains connected. Closing it with an empty temporary
inventory makes the running server throw in `GuidePriceScript.closeGuide` line
227: `Could not move tempInv into inv`, with `objs=[]`. The same stack appears
when disconnect cleanup closes the modal.

The companion patch returns immediately when the temporary inventory is empty.
It preserves the existing checked move for inventories that actually contain
items. The server already uses `Inventory.isEmpty()` in this script and recognizes
empty move results in the equipment/death scripts.

`openrune-guide-prices-empty-close.patch` is a reviewable patch for the actual
OpenRune server checkout recorded in the reference index. `git apply --check`
passed against that checkout. The patch has **not** been applied, compiled or
runtime-tested; the running server and read-only reference sources were preserved
as required by SPEC-0016. Server compilation and an empty/nonempty close/reopen
reproduction are required before calling the server-side repair verified.

The client diagnostic `login_probe CACHE PUBLIC_KEY --review-interfaces` verifies
settings/zoom, chat input and opening guide prices. Set
`RUSTCLIENT_REVIEW_CLOSE_PRICES=1` to include the known failing server close path.
The probe reads exactly two credential lines from stdin. No account secrets
belong in command arguments or committed evidence.

## Revision-240 scaling script arguments

`openrune-240-interface-scaling-arguments.patch` repairs the server helper that
calls script 2358 with one integer even though the selected LIVE cache script has
zero arguments and zero locals. The live fixed/classic/modern transition review
reproduced `event arguments exceed locals` on each switch. Keep strict client
argument validation; remove the obsolete argument at the server producer.

`git apply --check` passed against the server checkout. This patch is prepared
only: it has not been applied, compiled or runtime-tested. The selected server
and reference sources remain read-only under SPEC-0016. After applying and
rebuilding the server, repeat `login_probe CACHE PUBLIC_KEY --review-gameframe`
and confirm the script-2358 errors disappear alongside all three mode transitions.
