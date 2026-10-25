package dev.neosback.deob.analysis.packet;

import dev.neosback.deob.identity.InstructionId;
import dev.neosback.deob.identity.MethodId;
import java.util.List;

/** One packet-read invocation and every reachable consumer proven by exact provenance. */
public record PacketTimeline(
        MethodId hostMethod,
        InstructionId readCall,
        MethodId readMethod,
        PacketReadShape readShape,
        List<PacketTimelineEvent> events) {

    public PacketTimeline {
        events = List.copyOf(events);
    }
}
