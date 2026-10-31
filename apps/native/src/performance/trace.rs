//! Bounded, nonblocking raw diagnostic output. Overflow makes a run invalid.
use std::{
    fs::File,
    io::{BufWriter, Write},
    sync::{
        Arc,
        atomic::{AtomicBool, AtomicU64, Ordering},
        mpsc,
    },
    thread::JoinHandle,
};

#[derive(Clone, Copy)]
pub(super) struct FrameRecord {
    pub epoch: u64,
    pub frame: u64,
    pub at_ns: u64,
    pub presented: bool,
    pub frame_wall_ns: u64,
    pub cpu_work_upper_ns: u64,
    pub acquire_ns: u64,
    pub preparation_ns: u64,
    pub encoding_submit_ns: u64,
    pub present_call_ns: u64,
    pub requested_ns: Option<u64>,
    pub redraw_late_ns: u64,
    pub wake_late_ns: u64,
    pub gap_ns: Option<u64>,
    pub vertex_upload_bytes: u32,
    pub dynamic_upload_bytes: u32,
    pub raster_passes: u32,
    pub gpu_available: bool,
    pub gpu_dropped: u64,
}
pub(super) enum Record {
    Frame(FrameRecord),
    Gpu { epoch: u64, frame: u64, ns: f64 },
    Metadata(serde_json::Value),
    Stage { at_ns: u64, stage: u8, ns: u64 },
}
pub(super) struct Trace {
    sender: Option<mpsc::SyncSender<Record>>,
    dropped: Arc<AtomicU64>,
    failed: Arc<AtomicBool>,
    worker: Option<JoinHandle<()>>,
}
impl Trace {
    pub fn open(path: &std::path::Path) -> std::io::Result<Self> {
        let file = File::create(path)?;
        let (sender, receiver) = mpsc::sync_channel(8192);
        let dropped = Arc::new(AtomicU64::new(0));
        let failed = Arc::new(AtomicBool::new(false));
        let worker_dropped = dropped.clone();
        let worker_failed = failed.clone();
        let worker = std::thread::Builder::new().name("frame-trace-writer".into()).spawn(move || {
            let mut file = BufWriter::new(file);
            let result = (|| -> std::io::Result<()> {
                let mut count = 0_u64;
                for record in receiver {
                    let row = match record {
                        Record::Metadata(row) => row,
                        Record::Stage { at_ns, stage, ns } => serde_json::json!({"type":"stage", "at_ns":at_ns,"stage":stage,"ns":ns}),
                        Record::Gpu { epoch, frame, ns } => serde_json::json!({"type":"gpu", "epoch":epoch, "frame":frame, "ns":ns}),
                        Record::Frame(f) => serde_json::json!({
                            "type":"frame", "epoch":f.epoch, "frame":f.frame, "at_ns":f.at_ns,
                            "presented":f.presented, "frame_wall_ns":f.frame_wall_ns,
                            "cpu_work_upper_ns":f.cpu_work_upper_ns, "acquire_ns":f.acquire_ns,
                            "preparation_ns":f.preparation_ns, "encoding_submit_ns":f.encoding_submit_ns,
                            "present_call_ns":f.present_call_ns, "requested_ns":f.requested_ns,
                            "redraw_late_ns":f.redraw_late_ns, "wake_late_ns":f.wake_late_ns, "gap_ns":f.gap_ns,
                            "vertex_upload_bytes":f.vertex_upload_bytes, "dynamic_upload_bytes":f.dynamic_upload_bytes,
                            "ui_raster_passes":f.raster_passes, "gpu_available":f.gpu_available, "gpu_dropped":f.gpu_dropped,
                        }),
                    };
                    serde_json::to_writer(&mut file, &row)?; file.write_all(b"\n")?;
                    count += 1;
                    if count.is_multiple_of(64) { file.flush()?; }
                }
                serde_json::to_writer(&mut file, &serde_json::json!({"type":"trace_end", "records":count,
                    "dropped":worker_dropped.load(Ordering::Relaxed), "valid":worker_dropped.load(Ordering::Relaxed)==0}))?;
                file.write_all(b"\n")?; file.flush()
            })();
            if let Err(error) = result { worker_failed.store(true, Ordering::Relaxed); eprintln!("frame trace write failed: {error}"); }
        })?;
        Ok(Self {
            sender: Some(sender),
            dropped,
            failed,
            worker: Some(worker),
        })
    }
    pub fn send(&self, record: Record) {
        if self.failed.load(Ordering::Relaxed)
            || self
                .sender
                .as_ref()
                .is_none_or(|sender| sender.try_send(record).is_err())
        {
            self.dropped.fetch_add(1, Ordering::Relaxed);
        }
    }
}
impl Drop for Trace {
    fn drop(&mut self) {
        self.sender.take();
        // Only process shutdown joins the IO writer; no render callback waits for it.
        if let Some(worker) = self.worker.take() {
            let _ = worker.join();
        }
    }
}
