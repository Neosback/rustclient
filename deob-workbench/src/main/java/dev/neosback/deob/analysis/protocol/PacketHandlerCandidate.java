package dev.neosback.deob.analysis.protocol;

import dev.neosback.deob.identity.MethodId;
import java.util.List;
import java.util.Set;

/** Ranked evidence that a method behaves like a packet-dispatch/update handler. */
public record PacketHandlerCandidate(
        MethodId method,
        int packetReadCalls,
        Set<MethodId> distinctReadMethods,
        int transformEvents,
        int fieldWriteEvents,
        int branchEvents,
        int callEvents,
        int naturalLoopCount,
        int evidenceScore,
        List<String> reasons) {

    public PacketHandlerCandidate {
        distinctReadMethods = Set.copyOf(distinctReadMethods);
        reasons = List.copyOf(reasons);
    }
}
