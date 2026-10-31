//! Persistent bounded CPU workers; transport and semantic operations never enter this pool.
use rustclient_platform::{CpuJob, CpuJobExecutor, JobOutcome, JobPriority, JobRejected};
use std::{
    collections::VecDeque,
    sync::{Arc, Condvar, Mutex},
    thread::JoinHandle,
};

const MAX_QUEUED: usize = 64;

#[derive(Default)]
struct Queue {
    visible: VecDeque<CpuJob>,
    prefetch: VecDeque<CpuJob>,
    stopping: bool,
    running: usize,
    completed: u64,
    cancelled: u64,
    failed: u64,
}
#[derive(Default)]
struct Shared {
    queue: Mutex<Queue>,
    ready: Condvar,
}

/// Bounded worker/accounting snapshot, separate from process or GPU memory estimates.
#[derive(Debug, Clone, Copy)]
pub struct JobPoolStats {
    pub workers: usize,
    pub queued: usize,
    pub running: usize,
    pub completed: u64,
    pub cancelled: u64,
    pub failed: u64,
}

/// Pool lifetime belongs to the composition root. Dropping it revokes queued work.
pub struct NativeJobPool {
    shared: Arc<Shared>,
    workers: Vec<JoinHandle<()>>,
}
impl NativeJobPool {
    /// Start logical-CPU-count minus two workers, clamped to one through four.
    /// # Errors
    /// Returns thread creation failure; any workers already created are stopped.
    pub fn new() -> std::io::Result<Self> {
        let count = std::thread::available_parallelism()
            .map_or(1, usize::from)
            .saturating_sub(2)
            .clamp(1, 4);
        Self::with_workers(count)
    }
    fn with_workers(count: usize) -> std::io::Result<Self> {
        let mut pool = Self {
            shared: Arc::default(),
            workers: Vec::with_capacity(count),
        };
        for index in 0..count {
            let shared = Arc::clone(&pool.shared);
            pool.workers.push(
                std::thread::Builder::new()
                    .name(format!("projection-{index}"))
                    .spawn(move || worker(&shared))?,
            );
        }
        Ok(pool)
    }
    #[must_use]
    pub fn stats(&self) -> JobPoolStats {
        let q = self
            .shared
            .queue
            .lock()
            .unwrap_or_else(std::sync::PoisonError::into_inner);
        JobPoolStats {
            workers: self.workers.len(),
            queued: q.visible.len() + q.prefetch.len(),
            running: q.running,
            completed: q.completed,
            cancelled: q.cancelled,
            failed: q.failed,
        }
    }
}
impl CpuJobExecutor for NativeJobPool {
    fn try_submit(&self, job: CpuJob) -> Result<(), JobRejected> {
        let mut q = self
            .shared
            .queue
            .lock()
            .unwrap_or_else(std::sync::PoisonError::into_inner);
        if q.stopping || job.is_cancelled() || q.visible.len() + q.prefetch.len() >= MAX_QUEUED {
            return Err(JobRejected(job));
        }
        match job.priority {
            JobPriority::Visible => &mut q.visible,
            JobPriority::Prefetch => &mut q.prefetch,
        }
        .push_back(job);
        drop(q);
        self.shared.ready.notify_one();
        Ok(())
    }
}
fn worker(shared: &Shared) {
    loop {
        let job = {
            let mut q = shared
                .queue
                .lock()
                .unwrap_or_else(std::sync::PoisonError::into_inner);
            loop {
                if q.stopping {
                    return;
                }
                if let Some(job) = q.visible.pop_front().or_else(|| q.prefetch.pop_front()) {
                    q.running += 1;
                    break job;
                }
                q = shared
                    .ready
                    .wait(q)
                    .unwrap_or_else(std::sync::PoisonError::into_inner);
            }
        };
        // Fault isolation is at the job boundary, never around a semantic transition.
        let identity = job.identity;
        let result = std::panic::catch_unwind(std::panic::AssertUnwindSafe(|| job.run()));
        let mut q = shared
            .queue
            .lock()
            .unwrap_or_else(std::sync::PoisonError::into_inner);
        q.running -= 1;
        match result {
            Ok(JobOutcome::Completed) => q.completed += 1,
            Ok(JobOutcome::Cancelled) => q.cancelled += 1,
            Ok(JobOutcome::Faulted) | Err(_) => {
                q.failed += 1;
                eprintln!("projection job {identity:?} panicked");
            }
        }
    }
}

impl Drop for NativeJobPool {
    fn drop(&mut self) {
        let (visible, prefetch) = {
            let mut q = self
                .shared
                .queue
                .lock()
                .unwrap_or_else(std::sync::PoisonError::into_inner);
            q.stopping = true;
            (
                std::mem::take(&mut q.visible),
                std::mem::take(&mut q.prefetch),
            )
        };
        self.shared.ready.notify_all();
        // Drop potentially large captured assets outside the scheduler lock. Running jobs finish
        // cooperatively; the UI never waits for cache IO or mesh preparation during teardown.
        drop((visible, prefetch));
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    use rustclient_platform::{JobCancellation, JobIdentity};
    use std::{sync::mpsc, time::Duration};

    #[test]
    fn admission_is_bounded_prioritized_and_cancelled_work_never_runs() {
        let pool = NativeJobPool::with_workers(1).unwrap();
        let (started, wait) = mpsc::channel();
        let (release, blocked) = mpsc::channel();
        pool.try_submit(CpuJob::new(
            JobIdentity::default(),
            JobPriority::Visible,
            JobCancellation::default(),
            move |_| {
                started.send(()).unwrap();
                blocked.recv().unwrap();
            },
        ))
        .unwrap();
        wait.recv_timeout(Duration::from_secs(5)).unwrap();
        let (send, receive) = mpsc::channel();
        let scope = JobCancellation::default();
        for n in 0..MAX_QUEUED {
            let send = send.clone();
            let priority = if n == MAX_QUEUED - 1 {
                JobPriority::Visible
            } else {
                JobPriority::Prefetch
            };
            pool.try_submit(CpuJob::new(
                JobIdentity::default(),
                priority,
                if n == 0 {
                    scope.clone()
                } else {
                    JobCancellation::default()
                },
                move |_| {
                    send.send(n).unwrap();
                },
            ))
            .unwrap();
        }
        let overflow = CpuJob::new(
            JobIdentity::default(),
            JobPriority::Visible,
            JobCancellation::default(),
            |_| panic!("rejected work must not execute"),
        );
        assert!(pool.try_submit(overflow).is_err());
        assert_eq!(pool.stats().queued, MAX_QUEUED);
        scope.cancel();
        release.send(()).unwrap();
        assert_eq!(
            receive.recv_timeout(Duration::from_secs(5)).unwrap(),
            MAX_QUEUED - 1
        );
        for expected in 1..MAX_QUEUED - 1 {
            assert_eq!(
                receive.recv_timeout(Duration::from_secs(5)).unwrap(),
                expected
            );
        }
        let (faulted, fault) = mpsc::channel();
        pool.try_submit(
            CpuJob::new(
                JobIdentity::default(),
                JobPriority::Visible,
                JobCancellation::default(),
                |_| panic!("simulated projection failure"),
            )
            .with_fault_handler(move || {
                faulted.send(()).unwrap();
            }),
        )
        .unwrap();
        fault.recv_timeout(Duration::from_secs(5)).unwrap();
        let (healthy, resumed) = mpsc::channel();
        pool.try_submit(CpuJob::new(
            JobIdentity::default(),
            JobPriority::Visible,
            JobCancellation::default(),
            move |_| {
                healthy.send(()).unwrap();
            },
        ))
        .unwrap();
        resumed.recv_timeout(Duration::from_secs(5)).unwrap();
        assert_eq!(pool.stats().failed, 1);
        drop(pool);
        drop(send);
        assert!(receive.recv_timeout(Duration::from_secs(5)).is_err());
    }
}
