package dev.neosback.deob.analysis.protocol;

import dev.neosback.deob.identity.FieldId;
import dev.neosback.deob.identity.MethodId;
import java.util.List;

/** Broad semantic hypothesis for one mask-controlled actor-state block. */
public record ActorBehaviorClusterCandidate(
        MethodId method,
        SyncRole role,
        int maskConstant,
        String maskStructuralHash,
        ActorBehaviorClusterKind kind,
        int evidenceScore,
        List<MethodId> packetReadMethods,
        List<FieldId> fieldWrites,
        List<MethodId> downstreamCalls,
        List<String> reasons) {

    public ActorBehaviorClusterCandidate {
        packetReadMethods = List.copyOf(packetReadMethods);
        fieldWrites = List.copyOf(fieldWrites);
        downstreamCalls = List.copyOf(downstreamCalls);
        reasons = List.copyOf(reasons);
    }
}
