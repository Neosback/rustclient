package dev.neosback.deob.analysis.packet;

import dev.neosback.deob.identity.InstructionId;

/** One exact-bytecode event proven to depend on a packet-read result. */
public record PacketTimelineEvent(
        InstructionId instruction,
        PacketTimelineEventKind kind,
        String operation,
        int dependencyDepth) {

    public PacketTimelineEvent {
        if (dependencyDepth < 0) {
            throw new IllegalArgumentException("dependencyDepth must be >= 0");
        }
        operation = operation == null ? "" : operation;
    }
}
