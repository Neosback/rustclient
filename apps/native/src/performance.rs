//! Native process diagnostics. No game state or GPU readback is involved.
use std::time::{Duration, Instant};
use sysinfo::{Pid, ProcessRefreshKind, ProcessesToUpdate, System};
use winit::window::Window;

pub(super) struct Performance {
    system: System,
    pid: Pid,
    sample_start: Instant,
    presented: u32,
    redraws: u32,
    frame_time: Duration,
    tick_time: Duration,
    ticks: u32,
    log: bool,
    last_note: Instant,
}

impl Performance {
    pub(super) fn new() -> Self {
        Self {
            system: System::new(),
            pid: Pid::from_u32(std::process::id()),
            sample_start: Instant::now(),
            presented: 0,
            redraws: 0,
            frame_time: Duration::ZERO,
            tick_time: Duration::ZERO,
            ticks: 0,
            log: std::env::var_os("RUSTCLIENT_FRAME_TIMINGS").is_some(),
            last_note: Instant::now(),
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

    pub(super) fn tick(&mut self, start: Instant) {
        self.ticks += 1;
        self.tick_time += start.elapsed();
    }

    pub(super) fn frame(&mut self, window: &Window, start: Instant, presented: bool) {
        self.presented += u32::from(presented);
        self.redraws += 1;
        self.frame_time += start.elapsed();
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
        let title = window.title();
        let title = title.split(" | ").next().unwrap_or("RustClient");
        window.set_title(&format!(
            "{title} | {:.0} FPS | {memory}",
            f64::from(self.presented) / elapsed.as_secs_f64(),
        ));
        if self.log {
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
