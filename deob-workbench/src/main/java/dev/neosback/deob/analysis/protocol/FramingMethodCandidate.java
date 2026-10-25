package dev.neosback.deob.analysis.protocol;

import dev.neosback.deob.identity.FieldId;
import dev.neosback.deob.identity.MethodId;
import java.util.List;
import java.util.Set;

/** Structural evidence for a buffer method that may participate in opcode/cipher framing. */
public record FramingMethodCandidate(
        MethodId method,
        Set<MethodId> packetReadsCalled,
        Set<MethodId> externalIntCalls,
        Set<FieldId> objectStateFields,
        int byteArrayAccesses,
        int arithmeticOperations,
        int evidenceScore,
        List<String> reasons) {

    public FramingMethodCandidate {
        packetReadsCalled = Set.copyOf(packetReadsCalled);
        externalIntCalls = Set.copyOf(externalIntCalls);
        objectStateFields = Set.copyOf(objectStateFields);
        reasons = List.copyOf(reasons);
    }
}
