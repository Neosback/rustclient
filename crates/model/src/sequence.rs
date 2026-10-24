//! Revision-neutral sequence scheduling policy shared by cache adapters and actor runtime.

/// Runtime scheduling policy attached to an action sequence.
///
/// Revision adapters resolve cache-format defaults before constructing this value. The actor
/// scheduler consumes the resolved policy without knowing which revision or cache opcode produced
/// it. Numeric modes remain explicit until their complete cross-revision semantics are proven.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct SequencePolicy {
    /// Same-sequence reassignment behavior.
    pub restart_mode: u8,
    /// A different action sequence may replace the current one only when its priority is at least
    /// the active sequence's priority.
    pub replacement_priority: u8,
    /// Movement gate used while path entries that predate the action assignment remain.
    pub movement_gate_with_backlog: u8,
    /// Movement gate used after the assignment-time path backlog has been exhausted.
    pub movement_gate_without_backlog: u8,
    /// Maximum loop count carried by the sequence definition.
    pub max_loops: u16,
}
