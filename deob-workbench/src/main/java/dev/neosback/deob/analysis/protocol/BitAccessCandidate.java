package dev.neosback.deob.analysis.protocol;

import dev.neosback.deob.identity.FieldId;
import dev.neosback.deob.identity.MethodId;
import java.util.List;

/** Structural evidence that a method participates in bit-oriented buffer access. */
public record BitAccessCandidate(
        MethodId method,
        List<FieldId> byteArrayFields,
        List<FieldId> integerStateFields,
        int byteLoads,
        int shiftByThreeCount,
        int maskCount,
        int sevenEightConstantCount,
        int branchCount,
        boolean backwardBranch,
        int evidenceScore) {

    public BitAccessCandidate {
        byteArrayFields = List.copyOf(byteArrayFields);
        integerStateFields = List.copyOf(integerStateFields);
    }
}
