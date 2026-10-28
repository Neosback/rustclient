//! Native process diagnostics. No game state or GPU readback is involved.
use std::time::{Duration, Instant};
use sysinfo::{Pid, ProcessRefreshKind, ProcessesToUpdate, System};
use winit::window::Window;

/// Bounded diagnostic samples, never an unbounded telemetry history.
#[derive(Default)]
struct Samples {
    values: Vec<Duration>,
    next: usize,
}
impl Samples {
    fn push(&mut self, value: Duration) {
        if self.values.len() < 256 {
            self.values.push(value);
        } else {
            self.values[self.next] = value;
            self.next = (self.next + 1) % 256;
        }
    }
    fn finish(&mut self) -> (f64, f64) {
        self.values.sort_unstable();
        let p95 = self
            .values
            .get(self.values.len().saturating_sub(1) * 95 / 100)
            .copied()
            .unwrap_or_default();
        let max = self.values.last().copied().unwrap_or_default();
        self.values.clear();
        self.next = 0;
        (p95.as_secs_f64() * 1000., max.as_secs_f64() * 1000.)
    }
}
#[derive(Clone, Copy)]
pub(super) enum Stage {
    Hover,
    EntityBuild,
    EntityUpload,
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
    stages: [Samples; 3],
    previous_present: Option<Instant>,
    cycle_debt: Duration,
}

impl Performance {
    pub(super) fn new() -> Self {
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
            log: std::env::var_os("RUSTCLIENT_FRAME_TIMINGS").is_some(),
            last_note: Instant::now(),
            frame_samples: Samples::default(),
            tick_samples: Samples::default(),
            gaps: Samples::default(),
            stages: std::array::from_fn(|_| Samples::default()),
            previous_present: None,
            cycle_debt: Duration::ZERO,
        }
    }

    /// Window/lifecycle breadcrumbs for correlating memory samples; silent unless
    /// `RUSTCLIENT_FRAME_TIMINGS` is set.
    pub(super) fn event(&self, describe: impl FnOnce() -> String) {
        if self.log {
            eprintln!("event: {}", describe());
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
            self.stages[stage as usize].push(started.elapsed());
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
            if presented {
                let now = Instant::now();
                if let Some(previous) = self.previous_present.replace(now) {
                    self.gaps.push(now.duration_since(previous));
                }
            } else {
                self.previous_present = None;
            }
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
            let (frame_p95, frame_max) = self.frame_samples.finish();
            let (tick_p95, tick_max) = self.tick_samples.finish();
            let (gap_p95, gap_max) = self.gaps.finish();
            let stages = self.stages.each_mut().map(Samples::finish);
            eprintln!(
                "latency ms: frame p95={frame_p95:.2} max={frame_max:.2}; tick p95={tick_p95:.2} max={tick_max:.2}; present gap p95={gap_p95:.2} max={gap_max:.2}; hover p95={:.2}; entity build p95={:.2}; entity upload p95={:.2}; discarded wall-clock debt={:.2}",
                stages[0].0,
                stages[1].0,
                stages[2].0,
                self.cycle_debt.as_secs_f64() * 1000.
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
