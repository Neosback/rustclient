//! Shared fixed-cycle scheduling and bounded, ordered ingress. No transport or game-state copy.
use core::{fmt, time::Duration};
use rustclient_platform::MonotonicTime;
use std::collections::VecDeque;

pub const CLIENT_CYCLE_DURATION: Duration = Duration::from_millis(20);
const MAX_CATCH_UP: u32 = 10;
const PACKET_BUDGET: usize = 100;
const QUEUED_PACKETS: usize = 65_536;
const QUEUED_BYTES: usize = 262_144;

#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum CycleError {
    ClockOverflow,
    InboundLimit,
    OutboundLimit,
    InvalidBatch,
}
impl fmt::Display for CycleError {
    fn fmt(&self, f: &mut fmt::Formatter<'_>) -> fmt::Result {
        f.write_str(match self {
            Self::ClockOverflow => "client cycle deadline overflow",
            Self::OutboundLimit => "client cycle outbound queue limit exceeded",
            Self::InboundLimit => "client cycle inbound queue limit exceeded",
            Self::InvalidBatch => "client cycle requires a nonempty packet boundary",
        })
    }
}
impl std::error::Error for CycleError {}

/// Timing policy from the selected nanosecond clock; supplied timestamps are replayable inputs.
#[derive(Debug, Default)]
pub struct CycleClock {
    next: Duration,
}
impl CycleClock {
    #[must_use]
    pub const fn new(now: MonotonicTime) -> Self {
        Self {
            next: now.as_duration(),
        }
    }
    #[must_use]
    pub const fn deadline(&self) -> MonotonicTime {
        MonotonicTime::from_duration(self.next)
    }
    /// Return due cycles, capped at ten, dropping excess debt as in `classMG.method7611`.
    /// # Errors
    /// Rejects duration overflow before changing the clock.
    pub fn due(&mut self, now: MonotonicTime) -> Result<u32, CycleError> {
        let now = now.as_duration();
        if now < self.next {
            return Ok(0);
        }
        let mut next = self.next;
        let mut cycles = 0;
        loop {
            next = next
                .checked_add(CLIENT_CYCLE_DURATION)
                .ok_or(CycleError::ClockOverflow)?;
            cycles += 1;
            if cycles == MAX_CATCH_UP || next >= now {
                break;
            }
        }
        self.next = next.max(now);
        Ok(cycles)
    }
}

/// All outputs of one packet, or a complete group. Splitting this envelope is forbidden.
#[derive(Debug)]
pub struct InboundBatch<T> {
    pub packets: usize,
    pub wire_bytes: usize,
    pub payload: T,
}
/// Ordered pending work; this contains inputs only, never a second semantic state.
#[derive(Debug)]
pub struct CycleIngress<T> {
    queue: VecDeque<InboundBatch<T>>,
    packets: usize,
    bytes: usize,
    applied: usize,
}
impl<T> Default for CycleIngress<T> {
    fn default() -> Self {
        Self {
            queue: VecDeque::new(),
            packets: 0,
            bytes: 0,
            applied: 0,
        }
    }
}
impl<T> CycleIngress<T> {
    /// # Errors
    /// Rejects empty or excessive work before enqueuing any part of it.
    pub fn enqueue(&mut self, batch: InboundBatch<T>) -> Result<(), CycleError> {
        if batch.packets == 0 || batch.wire_bytes == 0 {
            return Err(CycleError::InvalidBatch);
        }
        let packets = self
            .packets
            .checked_add(batch.packets)
            .filter(|n| *n <= QUEUED_PACKETS)
            .ok_or(CycleError::InboundLimit)?;
        let bytes = self
            .bytes
            .checked_add(batch.wire_bytes)
            .filter(|n| *n <= QUEUED_BYTES)
            .ok_or(CycleError::InboundLimit)?;
        self.queue.push_back(batch);
        self.packets = packets;
        self.bytes = bytes;
        Ok(())
    }
    /// Begin S4. A new budget is available only at an explicit cycle boundary.
    pub fn begin_cycle(&mut self) {
        self.applied = 0;
    }
    /// Take one whole envelope while the ordinary budget remains. A group may exceed it.
    pub fn next_batch(&mut self) -> Option<T> {
        if self.applied >= PACKET_BUDGET {
            return None;
        }
        let batch = self.queue.pop_front()?;
        self.applied += batch.packets;
        self.packets -= batch.packets;
        self.bytes -= batch.wire_bytes;
        Some(batch.payload)
    }
    pub fn clear(&mut self) {
        self.queue.clear();
        self.packets = 0;
        self.bytes = 0;
        self.applied = 0;
    }
}

/// Shared session counters. Successful writes, not queued intents, reset keepalive timing.
#[derive(Debug, Default)]
pub struct SessionCounters {
    silent: u32,
    unwritten: u32,
}
impl SessionCounters {
    pub fn received(&mut self) {
        self.silent = 0;
    }
    pub fn written(&mut self) {
        self.unwritten = 0;
    }
    pub fn advance(&mut self) {
        self.silent = self.silent.saturating_add(1);
        self.unwritten = self.unwritten.saturating_add(1);
    }
    #[must_use]
    pub const fn keepalive_due(&self) -> bool {
        self.unwritten > 50
    }
    #[must_use]
    pub const fn reconnect_due(&self) -> bool {
        self.silent > 750
    }
}

/// Outbound intents are retained until the explicit S14 flush, in creation order.
#[derive(Debug)]
pub struct CycleEgress<T> {
    pending: std::cell::RefCell<Vec<T>>,
}
impl<T> Default for CycleEgress<T> {
    fn default() -> Self {
        Self {
            pending: std::cell::RefCell::new(Vec::new()),
        }
    }
}
impl<T> CycleEgress<T> {
    /// # Errors
    /// Rejects excess local intents without changing prior accepted work.
    pub fn enqueue(&self, intent: T) -> Result<(), CycleError> {
        let mut pending = self.pending.borrow_mut();
        if pending.len() >= 256 {
            return Err(CycleError::OutboundLimit);
        }
        pending.push(intent);
        Ok(())
    }
    /// Offer the entire ordered batch to a nonblocking transport handoff.
    /// A rejected handoff returns ownership and preserves every intent for the next cycle.
    /// # Errors
    /// Returns the handoff error without dropping accepted work.
    pub fn flush<E>(
        &self,
        handoff: impl FnOnce(Vec<T>) -> Result<(), (Vec<T>, E)>,
    ) -> Result<(), E> {
        let mut pending = self.pending.borrow_mut();
        if pending.is_empty() {
            return Ok(());
        }
        let batch = std::mem::take(&mut *pending);
        if let Err((batch, error)) = handoff(batch) {
            *pending = batch;
            return Err(error);
        }
        Ok(())
    }
}
