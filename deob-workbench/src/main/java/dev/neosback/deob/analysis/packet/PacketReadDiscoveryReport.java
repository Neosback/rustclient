package dev.neosback.deob.analysis.packet;

import java.util.List;

public record PacketReadDiscoveryReport(String jarSha256, List<PacketReadCandidate> candidates) {
    public PacketReadDiscoveryReport {
        candidates = List.copyOf(candidates);
    }
}
