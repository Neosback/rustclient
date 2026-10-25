package dev.neosback.deob.analysis.packet;

import java.util.List;

public record PacketTimelineReport(
        String jarSha256,
        int packetReadCandidateCount,
        List<PacketTimeline> timelines,
        List<String> skippedMethods) {

    public PacketTimelineReport {
        timelines = List.copyOf(timelines);
        skippedMethods = List.copyOf(skippedMethods);
    }
}
