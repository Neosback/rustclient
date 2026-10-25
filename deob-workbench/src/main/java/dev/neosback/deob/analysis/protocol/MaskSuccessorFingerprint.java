package dev.neosback.deob.analysis.protocol;

import dev.neosback.deob.identity.FieldId;
import dev.neosback.deob.identity.MethodId;
import java.util.List;

/** Rename-independent structural evidence for one immediate mask successor block. */
public record MaskSuccessorFingerprint(
        int startInstruction,
        List<MethodId> packetReadMethods,
        List<MethodId> downstreamCalls,
        List<FieldId> fieldWrites,
        List<Integer> integerConstants,
        String structuralHash) {

    public MaskSuccessorFingerprint {
        packetReadMethods = List.copyOf(packetReadMethods);
        downstreamCalls = List.copyOf(downstreamCalls);
        fieldWrites = List.copyOf(fieldWrites);
        integerConstants = List.copyOf(integerConstants);
    }
}
