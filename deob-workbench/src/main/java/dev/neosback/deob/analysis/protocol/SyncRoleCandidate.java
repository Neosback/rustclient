package dev.neosback.deob.analysis.protocol;

import dev.neosback.deob.identity.MethodId;
import java.util.List;

/** Ranked role hypothesis derived from rev-240 dispatch-table and exact-bytecode evidence. */
public record SyncRoleCandidate(
        MethodId method,
        SyncRole role,
        List<String> packetNames,
        int dispatchEvidenceCount,
        int bitAccessCalls,
        int packetReadCalls,
        int fieldWrites,
        int branchCount,
        int downstreamCalls,
        int evidenceScore) {

    public SyncRoleCandidate {
        packetNames = List.copyOf(packetNames);
    }
}
