package dev.neosback.deob.analysis.protocol;

import java.util.List;

public record Rev240SyncReport(
        String jarSha256,
        String packetProfileRevision,
        List<PacketDispatchEvidence> dispatchEvidence,
        List<SyncRoleCandidate> candidates) {

    public Rev240SyncReport {
        dispatchEvidence = List.copyOf(dispatchEvidence);
        candidates = List.copyOf(candidates);
    }
}
