//! Browser GPU handles stay on their owning thread until the submitted queue completes.
use std::{
    any::Any,
    sync::{
        Arc,
        atomic::{AtomicBool, Ordering},
    },
};
pub(super) struct WebRetirement {
    complete: Arc<AtomicBool>,
    _resources: Box<dyn Any>,
}
impl WebRetirement {
    pub fn new(queue: &wgpu::Queue, resources: Box<dyn Any>) -> Self {
        let complete = Arc::new(AtomicBool::new(false));
        let signal = Arc::clone(&complete);
        // The Send callback captures no JavaScript-backed GPU handles.
        queue.on_submitted_work_done(move || signal.store(true, Ordering::Release));
        Self {
            complete,
            _resources: resources,
        }
    }
    pub fn pending(&self) -> bool {
        !self.complete.load(Ordering::Acquire)
    }
}
