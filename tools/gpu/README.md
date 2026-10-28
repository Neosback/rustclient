# Classic GPU audit and portable probes

These tools are repository-owned; runtime rendering never reads references/ or invokes Java tools.
No account/server is needed. Run from the repository root. Current checkpoint used Rust 1.99.0.

```sh
python3 tools/gpu/audit.py --check
# Optional literal 240 overlay-byte oracle; needs a JDK on PATH/JAVA_HOME or --java-home.
python3 tools/gpu/overlay_oracle.py
cargo run -p rustclient-native --bin gpu_probe -- target/gpu-probe
# Optional second argument: a local cache directory, read only. Adds full title UI/HiDPI captures.
cargo run -p rustclient-native --bin gpu_probe -- target/gpu-probe /path/to/cache
# Optional real underground boundary case: accepts undeclared void squares; declared corruption fails.
cargo run -p rustclient-native --bin gpu_probe -- target/gpu-probe /path/to/cache --regions
cargo run -p rustclient-native --bin scene_probe -- /path/to/cache target/gpu-world-probe
# Optional exact-picker comparison and actual GPU geometry byte counters; no account/server.
cargo run -p rustclient-native --bin scene_probe -- /path/to/cache --performance
# Optional native desktop smoke: opens/closes isolated offline title windows at 30/50/60 FPS.
cargo build -p rustclient-native --bin rustclient-native
python3 tools/gpu/window_probe.py /path/to/cache
python3 tools/gpu/probe_server.py
```

Open http://127.0.0.1:8842/probe in a WebGPU-capable browser. It compiles the shared world/UI WGSL,
executes six canvas sampling modes and the shared world zone/filter predicate on a core-feature device,
compares edge pixels with the native
PNGs (maximum channel tolerance 2), checks encoded-space filtering/color correction, and posts its
bounded JSON report to target/gpu-browser-probe.json. Stop the server with Ctrl-C. It serves only
known probe/shader/native-fixture routes on loopback, never the repository directory. It does not
retain a device or run an animation loop after the probe completes.

The native and browser probes also check full-viewport widget alpha overlay beneath opaque,
translucent and transparent UI, all six scaling modes and color correction. The current browser
run has 35 checks. [The overlay checkpoint](../../docs/reference/builtin-plugins/GPU-OVERLAY-2026-10-27.md)
records its source selection/arithmetic and runtime-acceptance limits.

Four further browser checks exercise sorted opaque color without depth writes, depth-only replay,
later-surface occlusion and existing-wall depth testing through the shared world shader. The native
probe additionally executes the Rust face sorter and checks fresh GPU reconstruction. These use
explicitly synthetic geometry; see [the actor-depth checkpoint](../../docs/reference/builtin-plugins/GPU-ACTOR-DEPTH-2026-10-27.md).

Native images must be in target/gpu-probe for the browser comparison. Reports/PNGs are generated
artifacts, not tracked fixtures. Changing shader behavior requires regenerating native images before
running the browser comparison. A compile-only browser check does not pass pixel assertions.

`audit.py` checks source coverage, frozen SHA-256 values, owner paths, resource identity and setting
status/default agreement with the core inventory. It never modifies sources or promotes status.
`--require-complete` additionally fails while any open gates remain. Both structural success and a
failing completion gate are expected at the current partial checkpoint.

See [GPU-AUDIT](../../docs/reference/builtin-plugins/GPU-AUDIT.md) for results, intentional source
differences, WebGPU constraints and the outstanding 117HD gates. Offscreen native/browser agreement
is not exact Java runtime parity, playable browser acceptance, live window timing or performance proof.

The default native probe also hides/restores an explicitly synthetic unrelated quad through the actual
world vertex/uniform/raster pipeline. The scene probe captures a zero-distance frame and requires it
to differ from the default live-cache frame. Those checks do not claim real region-boundary appearance.
The optional `--regions` case tries bounded real underground boundary windows. The LIVE cache now
passes after correcting the distinction between undeclared void squares and corrupt declared groups.
The selected `(2832,4352)` window has two absent squares and real mixed-group geometry; toggling the
filter changes 53,583 channel bytes and reconstruction is byte-identical. Its diagnostic camera has
a black filtered frame and a narrow visible unfiltered platform; this is admission evidence, not
Java/gameplay visual acceptance. See
[the sparse-map checkpoint](../../docs/reference/builtin-plugins/GPU-SPARSE-MAPS-2026-10-27.md).
