//! Native diagnostics; asynchronous GPU samples are supplied by the renderer.
mod trace;
use std::time::{Duration, Instant};
use sysinfo::{Pid, ProcessRefreshKind, ProcessesToUpdate, System};
use winit::window::Window;

/// Bounded timing histogram, including every sample. Quantiles round up to 0.1 ms.
/// Overflow uses the observed maximum, so a slow tail is never hidden by truncation.
#[derive(Default)]
struct Samples {
    bins: Vec<u64>,
    count: u64,
    maximum: Duration,
    over_25: u64,
}
#[derive(Default)]
struct Summary {
    p95: f64,
    max: f64,
    p99: f64,
    count: u64,
    over_25: u64,
}
impl Samples {
    fn push(&mut self, value: Duration) {
        if self.bins.is_empty() {
            self.bins.resize(1001, 0);
        }
        let index = usize::try_from(value.as_micros().div_ceil(100))
            .unwrap_or(1000)
            .min(1000);
        self.bins[index] += 1;
        self.count += 1;
        self.maximum = self.maximum.max(value);
        self.over_25 += u64::from(value > Duration::from_millis(25));
    }
    fn percentile(&self, percent: u64) -> f64 {
        let rank = (self.count.saturating_mul(percent).div_ceil(100)).max(1);
        let mut count = 0;
        for (index, bin) in self.bins.iter().enumerate() {
            count += bin;
            if count >= rank {
                return if index == 1000 {
                    self.maximum.as_secs_f64() * 1000.
                } else {
                    Duration::from_micros(u64::try_from(index).unwrap_or(0) * 100).as_secs_f64()
                        * 1000.
                };
            }
        }
        0.
    }
    fn summary(&self) -> Summary {
        Summary {
            p95: self.percentile(95),
            p99: self.percentile(99),
            max: self.maximum.as_secs_f64() * 1000.,
            count: self.count,
            over_25: self.over_25,
        }
    }
    fn finish(&mut self) -> Summary {
        let summary = self.summary();
        self.bins.fill(0);
        self.count = 0;
        self.maximum = Duration::ZERO;
        self.over_25 = 0;
        summary
    }
}
#[derive(Clone, Copy)]
pub(super) enum Stage {
    Hover,
    EntityBuild,
    EntityUpload,
    SceneUpload,
    MinimapBuild,
    WidgetProjection,
    WidgetAssets,
    CommitAndScripts,
}

pub(super) struct Performance {
    system: System,
    pid: Pid,
    sample_start: Instant,
    presented: u32,
    fps: u32,
    redraws: u32,
    frame_time: Duration,
    tick_time: Duration,
    ticks: u32,
    log: bool,
    last_note: Instant,
    frame_samples: Samples,
    tick_samples: Samples,
    gaps: Samples,
    stages: [Samples; 8],
    frame_total: Samples,
    gap_total: Samples,
    previous_present: Option<Instant>,
    cycle_debt: Duration,
    origin: Instant,
    run_seconds: Option<Duration>,
    trace: Option<trace::Trace>,
    epoch: u64,
    scene_generation: u64,
    requested: Option<Instant>,
    wake_late: Duration,
    cpu_work: Samples,
    gpu_work: Samples,
    drain_deadline: Option<Instant>,
}

impl Performance {
    pub(super) fn new() -> Self {
        let trace = std::env::var_os("RUSTCLIENT_FRAME_TRACE").and_then(|path| {
            match trace::Trace::open(std::path::Path::new(&path)) {
                Ok(trace) => Some(trace),
                Err(error) => {
                    eprintln!("frame trace unavailable: {error}");
                    None
                }
            }
        });
        let log = trace.is_some() || std::env::var_os("RUSTCLIENT_FRAME_TIMINGS").is_some();
        Self {
            system: System::new(),
            pid: Pid::from_u32(std::process::id()),
            sample_start: Instant::now(),
            presented: 0,
            fps: 0,
            redraws: 0,
            frame_time: Duration::ZERO,
            tick_time: Duration::ZERO,
            ticks: 0,
            log,
            last_note: Instant::now(),
            frame_samples: Samples::default(),
            tick_samples: Samples::default(),
            gaps: Samples::default(),
            stages: std::array::from_fn(|_| Samples::default()),
            frame_total: Samples::default(),
            gap_total: Samples::default(),
            previous_present: None,
            cycle_debt: Duration::ZERO,
            origin: Instant::now(),
            run_seconds: std::env::var("RUSTCLIENT_RUN_SECONDS")
                .ok()
                .and_then(|value| value.parse::<u64>().ok())
                .filter(|seconds| *seconds > 0)
                .map(Duration::from_secs),
            trace,
            epoch: 0,
            scene_generation: 0,
            requested: None,
            wake_late: Duration::ZERO,
            cpu_work: Samples::default(),
            gpu_work: Samples::default(),
            drain_deadline: None,
        }
    }

    pub(super) fn scene_generation(&mut self, generation: u64) {
        self.scene_generation = generation;
    }
    pub(super) fn location_event(
        &self,
        phase: &str,
        generation: u64,
        cycle: u64,
        publication: u64,
        details: impl FnOnce() -> serde_json::Value,
    ) {
        if let Some(trace) = &self.trace {
            trace.send(trace::Record::Metadata(serde_json::json!({"type":"location","epoch":self.epoch,"phase":phase,"scene_generation":self.scene_generation,"source_generation":generation,"cycle":cycle,"publication":publication,"at_ns":u64::try_from(self.origin.elapsed().as_nanos()).unwrap_or(u64::MAX),"details":details()})));
        }
    }
    pub(super) fn instrumentation(&self) -> rustclient_renderer::Instrumentation {
        if self.log {
            rustclient_renderer::Instrumentation::Timings
        } else {
            rustclient_renderer::Instrumentation::Disabled
        }
    }
    pub(super) fn run_complete(&self) -> bool {
        self.run_seconds
            .is_some_and(|limit| self.origin.elapsed() >= limit)
    }
    pub(super) fn wake(&mut self, cause: winit::event::StartCause) {
        self.wake_late = match cause {
            winit::event::StartCause::ResumeTimeReached {
                requested_resume, ..
            } => Instant::now().saturating_duration_since(requested_resume),
            _ => Duration::ZERO,
        };
    }
    pub(super) fn presentation_deadline(&mut self, requested: Option<Instant>) {
        self.requested = requested;
    }
    fn collect_gpu(&mut self, renderer: &mut rustclient_renderer::SurfaceRenderer) {
        for sample in renderer.take_gpu_timings().into_iter().flatten() {
            if sample.nanoseconds.is_finite()
                && sample.nanoseconds >= 0.
                && let Ok(duration) = Duration::try_from_secs_f64(sample.nanoseconds / 1e9)
            {
                self.gpu_work.push(duration);
            }
            if let Some(trace) = &self.trace {
                trace.send(trace::Record::Gpu {
                    epoch: self.epoch,
                    frame: sample.frame,
                    ns: sample.nanoseconds,
                });
            }
        }
    }
    pub(super) fn finish_run(
        &mut self,
        renderer: &mut rustclient_renderer::SurfaceRenderer,
    ) -> bool {
        let deadline = *self.drain_deadline.get_or_insert_with(|| {
            let now = Instant::now();
            if let Some(trace) = &self.trace { trace.send(trace::Record::Metadata(serde_json::json!({"type":"benchmark_end", "at_ns":u64::try_from(now.saturating_duration_since(self.origin).as_nanos()).unwrap_or(u64::MAX)}))); }
            now + Duration::from_secs(2)
        });
        let pending = renderer.poll_gpu_timings();
        self.collect_gpu(renderer);
        if pending == 0 {
            if let Some(trace) = &self.trace {
                trace.send(trace::Record::Metadata(serde_json::json!({"type":"gpu_complete", "dropped":renderer.frame_timings().gpu_samples_dropped})));
            }
            return true;
        }
        if Instant::now() >= deadline {
            if let Some(trace) = &self.trace {
                trace.send(trace::Record::Metadata(
                    serde_json::json!({"type":"gpu_drain_timeout", "pending":pending}),
                ));
            }
            return true;
        }
        false
    }
    pub(super) fn renderer_created(
        &mut self,
        window: &Window,
        renderer: &rustclient_renderer::SurfaceRenderer,
    ) {
        self.epoch = self.epoch.wrapping_add(1);
        if let Some(trace) = &self.trace {
            let size = window.inner_size();
            trace.send(trace::Record::Metadata(serde_json::json!({
                "type":"renderer", "schema":2, "epoch":self.epoch, "physical":[size.width,size.height],
                "focused":window.has_focus(), "dpi":window.scale_factor(), "refresh_millihertz":window.current_monitor().and_then(|m| m.refresh_rate_millihertz()),
                "effective":format!("{:?}", renderer.effective_gpu()), "backend":format!("{:?}",renderer.gpu_capabilities().backend),
                "features":format!("{:?}",renderer.gpu_capabilities().enabled_features),
                "supported_features":format!("{:?}",renderer.gpu_capabilities().supported_features),
                "settings":format!("{:?}",renderer.gpu_settings()),
                "stage_names":["hover","entity_build","entity_upload","scene_upload","minimap_build","widget_projection","widget_assets","commit_scripts"],
                "timing_scope":"CPU stage wall times; GPU enclosing command interval; present calls are not display timestamps"
            })));
        }
    }
    pub(super) fn render_metrics(
        &mut self,
        start: Instant,
        presented: bool,
        renderer: &mut rustclient_renderer::SurfaceRenderer,
    ) {
        if !self.log {
            return;
        }
        let now = Instant::now();
        let timing = renderer.frame_timings();
        let ui = renderer.ui_stats();
        let wall = now.saturating_duration_since(start);
        let cpu = wall
            .saturating_sub(timing.acquire)
            .saturating_sub(timing.present_call);
        self.cpu_work.push(cpu);
        self.collect_gpu(renderer);
        if let Some(trace) = &self.trace {
            let nanos = |duration: Duration| u64::try_from(duration.as_nanos()).unwrap_or(u64::MAX);
            trace.send(trace::Record::Frame(trace::FrameRecord {
                scene_generation: self.scene_generation,
                location_publication: timing.location_publication,
                location_upload_bytes: timing.location_upload_bytes,
                epoch: self.epoch,
                frame: timing.frame,
                at_ns: nanos(now.saturating_duration_since(self.origin)),
                presented,
                frame_wall_ns: nanos(wall),
                cpu_work_upper_ns: nanos(cpu),
                acquire_ns: nanos(timing.acquire),
                preparation_ns: nanos(timing.preparation),
                encoding_submit_ns: nanos(timing.encoding_submit),
                present_call_ns: nanos(timing.present_call),
                requested_ns: self
                    .requested
                    .map(|at| nanos(at.saturating_duration_since(self.origin))),
                redraw_late_ns: self
                    .requested
                    .map_or(0, |at| nanos(start.saturating_duration_since(at))),
                wake_late_ns: nanos(self.wake_late),
                gap_ns: if presented {
                    self.previous_present
                        .map(|at| nanos(now.saturating_duration_since(at)))
                } else {
                    None
                },
                vertex_upload_bytes: ui.vertex_upload_bytes,
                dynamic_upload_bytes: ui.dynamic_upload_bytes,
                raster_passes: ui.raster_passes,
                gpu_available: timing.gpu_available,
                gpu_dropped: timing.gpu_samples_dropped,
            }));
        }
    }
    /// Window/lifecycle breadcrumbs for correlating memory samples; silent unless
    /// `RUSTCLIENT_FRAME_TIMINGS` is set.
    pub(super) fn event(&self, describe: impl FnOnce() -> String) {
        if self.log {
            let description = describe();
            eprintln!("event: {description}");
            if let Some(trace) = &self.trace {
                trace.send(trace::Record::Metadata(serde_json::json!({
                    "type":"event", "at_ns":u64::try_from(self.origin.elapsed().as_nanos()).unwrap_or(u64::MAX),
                    "description":description,
                })));
            }
        }
    }

    /// Like [`Self::event`] but at most once per second, for per-frame state.
    pub(super) fn note(&mut self, describe: impl FnOnce() -> String) {
        if self.log && self.last_note.elapsed() >= Duration::from_secs(1) {
            self.last_note = Instant::now();
            eprintln!("note: {}", describe());
        }
    }

    /// Presented frames per second over the last completed one second sample.
    pub(super) const fn fps(&self) -> u32 {
        self.fps
    }

    pub(super) fn tick(&mut self, start: Instant) {
        self.ticks += 1;
        let elapsed = start.elapsed();
        self.tick_time += elapsed;
        if self.log {
            self.tick_samples.push(elapsed);
        }
    }

    pub(super) fn stage(&mut self, stage: Stage, started: Instant) {
        if self.log {
            let duration = started.elapsed();
            self.stages[stage as usize].push(duration);
            if let Some(trace) = &self.trace {
                trace.send(trace::Record::Stage {
                    at_ns: u64::try_from(self.origin.elapsed().as_nanos()).unwrap_or(u64::MAX),
                    stage: stage as u8,
                    ns: u64::try_from(duration.as_nanos()).unwrap_or(u64::MAX),
                });
            }
        }
    }
    pub(super) fn suspend_presentation(&mut self) {
        self.previous_present = None;
    }
    fn presentation(&mut self, now: Instant, presented: bool) {
        // A skipped/reconfigured frame must remain part of the next real presentation gap.
        // Only explicit suspension/occlusion breaks the active presentation clock.
        if !self.log || !presented {
            return;
        }
        if let Some(previous) = self.previous_present.replace(now) {
            let gap = now.duration_since(previous);
            self.gaps.push(gap);
            self.gap_total.push(gap);
        }
    }
    pub(super) fn job_stats(
        &self,
        sample: impl FnOnce() -> rustclient_platform_native::JobPoolStats,
    ) {
        if self.log && self.sample_start.elapsed() >= Duration::from_secs(1) {
            let stats = sample();
            eprintln!("projection workers: {stats:?}");
            if let Some(trace) = &self.trace {
                trace.send(trace::Record::Metadata(serde_json::json!({"type":"jobs", "at_ns":u64::try_from(self.origin.elapsed().as_nanos()).unwrap_or(u64::MAX), "stats":format!("{stats:?}")})));
            }
        }
    }
    pub(super) fn cycle_debt(&mut self, debt: Duration) {
        if self.log {
            self.cycle_debt += debt;
        }
    }
    pub(super) fn frame(&mut self, window: &Window, start: Instant, presented: bool) {
        self.presented += u32::from(presented);
        self.redraws += 1;
        let duration = start.elapsed();
        self.frame_time += duration;
        if self.log {
            self.frame_samples.push(duration);
            self.frame_total.push(duration);
            self.presentation(Instant::now(), presented);
        }
        let elapsed = self.sample_start.elapsed();
        if elapsed < Duration::from_secs(1) {
            return;
        }
        self.system.refresh_processes_specifics(
            ProcessesToUpdate::Some(&[self.pid]),
            true,
            ProcessRefreshKind::nothing().with_memory().without_tasks(),
        );
        let memory = self.system.process(self.pid).map_or_else(
            || "memory unavailable".into(),
            |p| format!("{} MiB RSS", p.memory() / (1024 * 1024)),
        );
        let millis = u32::try_from(elapsed.as_millis())
            .unwrap_or(u32::MAX)
            .max(1);
        self.fps = self.presented.saturating_mul(1000) / millis;
        let title = window.title();
        let title = title.split(" | ").next().unwrap_or("RustClient");
        window.set_title(&format!(
            "{title} | {:.0} FPS | {memory}",
            f64::from(self.presented) / elapsed.as_secs_f64(),
        ));
        if self.log {
            let frame = self.frame_samples.finish();
            let tick = self.tick_samples.finish();
            let gap = self.gaps.finish();
            let (frame_p95, frame_max) = (frame.p95, frame.max);
            let (tick_p95, tick_max) = (tick.p95, tick.max);
            let (gap_p95, gap_max) = (gap.p95, gap.max);
            let stages = self.stages.each_mut().map(Samples::finish);
            eprintln!(
                "latency ms: frame p95={frame_p95:.2} max={frame_max:.2}; tick p95={tick_p95:.2} max={tick_max:.2}; present gap p95={gap_p95:.2} max={gap_max:.2}; hover p95={:.2}; entity build p95={:.2}; entity upload p95={:.2}; scene upload p95={:.2} max={:.2}; minimap build p95={:.2} max={:.2}; widget projection p95={:.2}; widget assets p95={:.2}; discarded wall-clock debt={:.2}",
                stages[0].p95,
                stages[1].p95,
                stages[2].p95,
                stages[3].p95,
                stages[3].max,
                stages[4].p95,
                stages[4].max,
                stages[5].p95,
                stages[6].p95,
                self.cycle_debt.as_secs_f64() * 1000.
            );
            let total = self.frame_total.summary();
            let gaps = self.gap_total.summary();
            let cpu = self.cpu_work.finish();
            let gpu = self.gpu_work.finish();
            let gpu_description = if gpu.count == 0 {
                "unavailable (no completed samples)".into()
            } else {
                format!("p99={:.3} n={}", gpu.p99, gpu.count)
            };
            eprintln!(
                "render diagnostics ms: CPU work upper p99={:.3}; GPU interval {gpu_description}",
                cpu.p99
            );
            eprintln!(
                "tail ms (0.1 ms upper buckets): frame wall p99={:.2}; tick p99={:.2}; gap p99={:.2}; commit+scripts p99={:.2}; lifetime frame wall p99={:.2} n={}; lifetime gap p99={:.2} >25ms={}/{}; GPU timestamps reported separately",
                frame.p99,
                tick.p99,
                gap.p99,
                stages[7].p99,
                total.p99,
                total.count,
                gaps.p99,
                gaps.over_25,
                gaps.count
            );
            self.cycle_debt = Duration::ZERO;
            eprintln!(
                "frame timings: {:.1} FPS, redraw {:.2} ms, tick {:.2} ms, {memory}",
                f64::from(self.presented) / elapsed.as_secs_f64(),
                self.frame_time.as_secs_f64() * 1000. / f64::from(self.redraws.max(1)),
                self.tick_time.as_secs_f64() * 1000. / f64::from(self.ticks.max(1)),
            );
        }
        self.sample_start = Instant::now();
        self.presented = 0;
        self.redraws = 0;
        self.frame_time = Duration::ZERO;
        self.tick_time = Duration::ZERO;
        self.ticks = 0;
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    #[test]
    fn dropped_frames_count_toward_gaps_but_suspension_does_not() {
        let mut metrics = Performance::new();
        metrics.log = true;
        let start = Instant::now();
        metrics.presentation(start, true);
        metrics.presentation(start + Duration::from_millis(16), false);
        metrics.presentation(start + Duration::from_millis(33), true);
        assert_eq!(metrics.gap_total.summary().count, 1);
        assert_eq!(metrics.gap_total.summary().over_25, 1);
        assert!((metrics.gap_total.summary().max - 33.).abs() < 1e-6);
        metrics.suspend_presentation();
        metrics.presentation(start + Duration::from_secs(10), true);
        assert_eq!(metrics.gap_total.summary().count, 1);
        let mut histogram = Samples::default();
        for _ in 0..989 {
            histogram.push(Duration::from_millis(1));
        }
        for _ in 0..11 {
            histogram.push(Duration::from_millis(200));
        }
        assert!((histogram.summary().p99 - 200.).abs() < 1e-6);
        assert_eq!(histogram.summary().over_25, 11);
        assert_eq!(histogram.summary().count, 1000);
    }
}
