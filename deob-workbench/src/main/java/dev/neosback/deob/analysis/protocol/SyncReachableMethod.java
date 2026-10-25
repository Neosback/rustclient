package dev.neosback.deob.analysis.protocol;

import dev.neosback.deob.identity.InstructionId;
import dev.neosback.deob.identity.MethodId;
import java.util.List;

/** A method reachable from one exact rev-specific sync dispatch root. */
public record SyncReachableMethod(
        MethodId root,
        MethodId method,
        SyncRole role,
        List<String> packetNames,
        int depth,
        List<InstructionId> callPath,
        int bitAccessCalls,
        int packetReadCalls,
        int fieldWrites,
        int branchCount,
        int downstreamCalls) {

    public SyncReachableMethod {
        packetNames = List.copyOf(packetNames);
        callPath = List.copyOf(callPath);
    }
}
