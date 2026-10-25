package dev.neosback.deob.analysis.protocol;

import java.util.List;

public record ActorBehaviorClusterReport(
        String corpusSha256,
        String packetRevision,
        List<ActorBehaviorClusterCandidate> candidates) {

    public ActorBehaviorClusterReport {
        candidates = List.copyOf(candidates);
    }
}
