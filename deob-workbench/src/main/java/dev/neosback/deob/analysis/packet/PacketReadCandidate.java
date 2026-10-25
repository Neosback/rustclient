package dev.neosback.deob.analysis.packet;

import dev.neosback.deob.identity.FieldId;
import dev.neosback.deob.identity.MethodId;
import java.util.List;

/** Evidence that a method structurally behaves like a byte-buffer read. */
public record PacketReadCandidate(
        MethodId method,
        PacketReadShape shape,
        String returnDescriptor,
        int byteArrayLoads,
        List<FieldId> byteArrayFields,
        List<FieldId> offsetFieldCandidates,
        int mask255Count,
        int shiftCount,
        int branchCount,
        boolean backwardBranch,
        int evidenceScore) {

    public PacketReadCandidate {
        byteArrayFields = List.copyOf(byteArrayFields);
        offsetFieldCandidates = List.copyOf(offsetFieldCandidates);
    }
}
