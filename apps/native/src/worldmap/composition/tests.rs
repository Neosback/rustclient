use super::*;
use rustclient_platform::{JobOutcome, JobRejected};
use std::sync::{Mutex, mpsc};

#[derive(Default)]
struct Executor(Mutex<Vec<CpuJob>>);
impl CpuJobExecutor for Executor {
    fn try_submit(&self, job: CpuJob) -> Result<(), JobRejected> {
        let mut pending = self.0.lock().unwrap();
        if pending.is_empty() {
            pending.push(job);
            Ok(())
        } else {
            Err(JobRejected(job))
        }
    }
}
impl Executor {
    fn run(&self) -> JobOutcome {
        self.0.lock().unwrap().pop().unwrap().run()
    }
}
#[test]
fn coalesces_latest_view_rejects_stale_results_and_keeps_unchanged_pixels() {
    let mut composer = MapComposer::default();
    let assets = MapView::default();
    let mut state = WorldMapState::new();
    let executor = Executor::default();
    let (sender, receiver) = mpsc::channel();
    let complete = move |identity, result| sender.send((identity, result)).unwrap();
    assert!(
        composer
            .request(
                &state,
                &assets,
                [32, 32],
                [0, 0],
                None,
                1,
                &executor,
                complete.clone()
            )
            .is_none()
    );
    for center in 1..=100 {
        state.center_tile = [center, center];
        assert!(
            composer
                .request(
                    &state,
                    &assets,
                    [32, 32],
                    [0, 0],
                    None,
                    1,
                    &executor,
                    complete.clone()
                )
                .is_none()
        );
    }
    assert_eq!(composer.submitted, 1);
    assert_eq!(executor.run(), JobOutcome::Completed);
    let (identity, result) = receiver.recv().unwrap();
    composer.receive(identity, result);
    assert!(
        composer
            .request(
                &state,
                &assets,
                [32, 32],
                [0, 0],
                None,
                1,
                &executor,
                complete.clone()
            )
            .is_none()
    );
    assert_eq!(composer.stale, 1);
    assert_eq!(executor.run(), JobOutcome::Completed);
    let (identity, result) = receiver.recv().unwrap();
    composer.receive(identity, result);
    let pixels = composer
        .request(
            &state,
            &assets,
            [32, 32],
            [0, 0],
            None,
            1,
            &executor,
            complete.clone(),
        )
        .unwrap();
    assert_eq!(pixels.viewport_size(), [32, 32]);
    assert_ne!(pixels.revision, 0);
    for _ in 0..100 {
        assert!(
            composer
                .request(
                    &state,
                    &assets,
                    [32, 32],
                    [0, 0],
                    None,
                    1,
                    &executor,
                    complete.clone()
                )
                .is_none()
        );
    }
    assert_eq!(composer.submitted, 2);
    assert_eq!(composer.published, 1);
}
#[test]
fn teardown_revokes_pending_work_and_reopening_cannot_accept_the_old_request() {
    let mut composer = MapComposer::default();
    let assets = MapView::default();
    let state = WorldMapState::new();
    let executor = Executor::default();
    let (sender, receiver) = mpsc::channel();
    let complete = move |identity, result| sender.send((identity, result)).unwrap();
    composer.request(
        &state,
        &assets,
        [32, 32],
        [0, 0],
        None,
        1,
        &executor,
        complete.clone(),
    );
    let old = composer.running.unwrap();
    composer.clear();
    assert_eq!(executor.run(), JobOutcome::Cancelled);
    assert!(receiver.try_recv().is_err());
    composer.request(
        &state,
        &assets,
        [32, 32],
        [0, 0],
        None,
        2,
        &executor,
        complete.clone(),
    );
    composer.receive(old, Err("obsolete fault".into()));
    assert!(composer.running.is_some());
    assert_eq!(executor.run(), JobOutcome::Completed);
    let (identity, result) = receiver.recv().unwrap();
    composer.receive(identity, result);
    assert!(
        composer
            .request(
                &state,
                &assets,
                [32, 32],
                [0, 0],
                None,
                2,
                &executor,
                complete
            )
            .is_some()
    );
}
