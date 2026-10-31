//! Portable CPU-work admission and cancellation; no game state or GPU handles.
use std::sync::{
    Arc,
    atomic::{AtomicBool, Ordering},
};

/// Identifies a projection lifetime and one request inside it.
#[derive(Debug, Clone, Copy, Default, PartialEq, Eq)]
pub struct JobIdentity {
    pub session: u64,
    pub scene: Option<[u8; 32]>,
    pub resources: u64,
    pub request: u64,
}
/// Visible updates precede speculative work. Neither category contains semantic commits.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum JobPriority {
    Visible,
    Prefetch,
}

/// Explicitly owned cancellation shared by a service and its disposable tasks.
#[derive(Debug, Clone, Default)]
pub struct JobCancellation(Arc<AtomicBool>);
impl JobCancellation {
    pub fn cancel(&self) {
        self.0.store(true, Ordering::Release);
    }
    #[must_use]
    pub fn is_cancelled(&self) -> bool {
        self.0.load(Ordering::Acquire)
    }
}

type Work = Box<dyn FnOnce(&JobCancellation) + Send + 'static>;
/// Owned CPU work. Adapters decide when it runs; closures check cancellation between stages.
pub struct CpuJob {
    pub identity: JobIdentity,
    pub priority: JobPriority,
    cancellation: JobCancellation,
    work: Work,
    on_fault: Option<Box<dyn FnOnce() + Send + 'static>>,
}
/// Execution outcome, distinct from queue admission or semantic state.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum JobOutcome {
    Completed,
    Cancelled,
    Faulted,
}
impl CpuJob {
    pub fn new(
        identity: JobIdentity,
        priority: JobPriority,
        cancellation: JobCancellation,
        work: impl FnOnce(&JobCancellation) + Send + 'static,
    ) -> Self {
        Self {
            identity,
            priority,
            cancellation,
            work: Box::new(work),
            on_fault: None,
        }
    }
    /// Report an unwinding worker fault to the owning service so it can clear pending state.
    /// A browser adapter using aborting panics must provide its own worker/trap notification.
    #[must_use]
    pub fn with_fault_handler(mut self, handler: impl FnOnce() + Send + 'static) -> Self {
        self.on_fault = Some(Box::new(handler));
        self
    }
    #[must_use]
    pub fn is_cancelled(&self) -> bool {
        self.cancellation.is_cancelled()
    }
    /// Execute admitted work, notifying its owner on an unwinding fault.
    #[must_use]
    pub fn run(self) -> JobOutcome {
        if self.is_cancelled() {
            return JobOutcome::Cancelled;
        }
        let result = std::panic::catch_unwind(std::panic::AssertUnwindSafe(|| {
            (self.work)(&self.cancellation);
        }));
        if result.is_err() {
            if let Some(handler) = self.on_fault {
                handler();
            }
            JobOutcome::Faulted
        } else if self.cancellation.is_cancelled() {
            JobOutcome::Cancelled
        } else {
            JobOutcome::Completed
        }
    }
}

/// Admission failure returns ownership so the caller can retry without losing required work.
pub struct JobRejected(pub CpuJob);
impl std::fmt::Debug for JobRejected {
    fn fmt(&self, f: &mut std::fmt::Formatter<'_>) -> std::fmt::Result {
        f.debug_tuple("JobRejected")
            .field(&self.0.identity)
            .finish()
    }
}
/// Bounded admission shared by native and future cooperative browser scheduling.
pub trait CpuJobExecutor {
    /// Never blocks waiting for queue capacity. Caller retains rejected work.
    /// # Errors
    /// Returns the owned job when capacity or its lifetime prevents admission.
    fn try_submit(&self, job: CpuJob) -> Result<(), JobRejected>;
}
