//! Optional bounded GPU timestamps and renderer stage wall times.
use std::{sync::mpsc, time::Duration};

/// Profiling is an engine diagnostic, separate from GPU plugin configuration.
#[derive(Debug, Clone, Copy, Default, PartialEq, Eq)]
pub enum Instrumentation {
    #[default]
    Disabled,
    Timings,
}
impl Instrumentation {
    pub(crate) fn enabled(self) -> bool {
        self == Self::Timings
    }
}

#[derive(Debug, Clone, Copy)]
pub struct GpuTimingSample {
    pub frame: u64,
    /// Enclosing GPU command interval, excluding CPU waits and display presentation.
    pub nanoseconds: f64,
}

/// Latest attempted frame. These CPU spans are wall time, not OS thread CPU time.
#[derive(Debug, Clone, Copy, Default)]
pub struct FrameTimings {
    pub frame: u64,
    pub acquire: Duration,
    pub preparation: Duration,
    pub encoding_submit: Duration,
    pub present_call: Duration,
    pub gpu_available: bool,
    pub gpu_samples_dropped: u64,
}

pub(crate) struct CpuClock {
    #[cfg(not(target_arch = "wasm32"))]
    start: Option<std::time::Instant>,
}
impl CpuClock {
    pub fn start(enabled: bool) -> Self {
        #[cfg(target_arch = "wasm32")]
        let _ = enabled;
        Self {
            #[cfg(not(target_arch = "wasm32"))]
            start: enabled.then(std::time::Instant::now),
        }
    }
    pub fn elapsed(&self) -> Duration {
        #[cfg(not(target_arch = "wasm32"))]
        {
            self.start.map_or(Duration::ZERO, |start| start.elapsed())
        }
        #[cfg(target_arch = "wasm32")]
        {
            Duration::ZERO
        }
    }
}

pub(crate) const GPU_FEATURES: wgpu::Features =
    wgpu::Features::TIMESTAMP_QUERY.union(wgpu::Features::TIMESTAMP_QUERY_INSIDE_ENCODERS);

struct Slot {
    query: wgpu::QuerySet,
    resolved: wgpu::Buffer,
    readback: wgpu::Buffer,
    pending: Option<mpsc::Receiver<Result<(), wgpu::BufferAsyncError>>>,
    frame: u64,
}
pub(crate) struct GpuTimer {
    slots: [Slot; 3],
    period: f64,
    samples: [Option<GpuTimingSample>; 3],
    pub dropped: u64,
}
impl GpuTimer {
    pub fn new(device: &wgpu::Device, queue: &wgpu::Queue) -> Option<Self> {
        if !device.features().contains(GPU_FEATURES) {
            return None;
        }
        Some(Self {
            slots: std::array::from_fn(|_| Slot {
                query: device.create_query_set(&wgpu::QuerySetDescriptor {
                    label: Some("frame GPU timestamps"),
                    ty: wgpu::QueryType::Timestamp,
                    count: 2,
                }),
                resolved: device.create_buffer(&wgpu::BufferDescriptor {
                    label: Some("GPU timestamp resolve"),
                    size: wgpu::QUERY_RESOLVE_BUFFER_ALIGNMENT,
                    usage: wgpu::BufferUsages::QUERY_RESOLVE | wgpu::BufferUsages::COPY_SRC,
                    mapped_at_creation: false,
                }),
                readback: device.create_buffer(&wgpu::BufferDescriptor {
                    label: Some("asynchronous GPU timestamp readback"),
                    size: 16,
                    usage: wgpu::BufferUsages::COPY_DST | wgpu::BufferUsages::MAP_READ,
                    mapped_at_creation: false,
                }),
                pending: None,
                frame: 0,
            }),
            period: f64::from(queue.get_timestamp_period()),
            samples: [None; 3],
            dropped: 0,
        })
    }
    pub fn poll(&mut self, device: &wgpu::Device) {
        let _ = device.poll(wgpu::PollType::Poll);
        for (index, slot) in self.slots.iter_mut().enumerate() {
            let Some(receiver) = &slot.pending else {
                continue;
            };
            match receiver.try_recv() {
                Ok(Ok(())) => {
                    let Ok(bytes) = slot.readback.slice(..).get_mapped_range() else {
                        slot.readback.unmap();
                        slot.pending = None;
                        self.dropped += 1;
                        continue;
                    };
                    let mut timestamps = [0_u64; 2];
                    for (value, bytes) in timestamps.iter_mut().zip(bytes.as_chunks::<8>().0) {
                        *value = u64::from_le_bytes(*bytes);
                    }
                    drop(bytes);
                    slot.readback.unmap();
                    slot.pending = None;
                    if let Some(ticks) = timestamps[1].checked_sub(timestamps[0]) {
                        #[expect(
                            clippy::cast_precision_loss,
                            reason = "GPU frame interval, not the absolute timestamp counter"
                        )]
                        let nanoseconds = ticks as f64 * self.period;
                        if self.samples[index].is_some() {
                            self.dropped += 1;
                        }
                        self.samples[index] = Some(GpuTimingSample {
                            frame: slot.frame,
                            nanoseconds,
                        });
                    } else {
                        self.dropped += 1;
                    }
                }
                Err(mpsc::TryRecvError::Empty) => {}
                Ok(Err(_)) | Err(mpsc::TryRecvError::Disconnected) => {
                    slot.readback.unmap();
                    slot.pending = None;
                    self.dropped += 1;
                }
            }
        }
    }
    pub fn begin(&mut self, encoder: &mut wgpu::CommandEncoder, frame: u64) -> Option<usize> {
        let Some(index) = self.slots.iter().position(|slot| slot.pending.is_none()) else {
            self.dropped += 1;
            return None;
        };
        self.slots[index].frame = frame;
        encoder.write_timestamp(&self.slots[index].query, 0);
        Some(index)
    }
    pub fn end(&self, encoder: &mut wgpu::CommandEncoder, index: usize) {
        let slot = &self.slots[index];
        encoder.write_timestamp(&slot.query, 1);
        encoder.resolve_query_set(&slot.query, 0..2, &slot.resolved, 0);
        encoder.copy_buffer_to_buffer(&slot.resolved, 0, &slot.readback, 0, 16);
    }
    pub fn submitted(&mut self, index: usize) {
        let slot = &mut self.slots[index];
        let (sender, receiver) = mpsc::sync_channel(1);
        slot.pending = Some(receiver);
        slot.readback
            .slice(..)
            .map_async(wgpu::MapMode::Read, move |result| {
                let _ = sender.try_send(result);
            });
    }
    pub fn take_samples(&mut self) -> [Option<GpuTimingSample>; 3] {
        std::mem::take(&mut self.samples)
    }
    pub fn pending(&self) -> usize {
        self.slots
            .iter()
            .filter(|slot| slot.pending.is_some())
            .count()
    }
}
