# Bounded sidebar diagnostics

`RuneLiteSidebarMetrics.java` measures resolved FlatLaf metrics from the pinned shell's compiled
classes, not guessed sums of constants. Prepare `tools/parity/runelite240.py`'s isolated build first;
use its `classpath.txt` and verify `source-manifest.json` against the pinned source. Compile with
JDK 11 `javac -cp <classpath> -d target/sidebar-lifecycle tools/sidebar/RuneLiteSidebarMetrics.java`.
Run with the same classpath plus target/sidebar-lifecycle, `-Djava.awt.headless=true
-Dflatlaf.uiScale=1x`, an isolated target user.home, then `RuneLiteSidebarMetrics`.
Optional arguments are an output PNG path and an owned asset export directory. Asset export uses
the source ImageUtil/ColorScheme transformations and writes only the named output directory.
The PNG is a metric fixture, not the complete production configuration panel.

`cargo run -p rustclient-native --bin gpu_shell_probe` exercises the real shared panel and retained
GPU shell with bounded list/settings, unchanged/hidden and reconstruction workloads. It writes under
target/gpu-shell-probe. No golden image comparison is installed in CI (ADR-0004).

Browser harness: build `cargo build -p rustclient-web --target wasm32-unknown-unknown`, run
`wasm-bindgen --target web --out-dir target/sidebar-lifecycle/web/pkg
 target/wasm32-unknown-unknown/debug/rustclient_web.wasm`, copy `apps/web/index.html` alongside pkg,
and serve that target directory on loopback. Open `/?shell-harness`; F9 switches panel/rail and F10
shows/hides. A browser with WebGPU is required. `data-shell-passes` and `data-shell-width` on the canvas
are diagnostic observations, not gameplay metrics. This shell harness is not a playable browser client.
