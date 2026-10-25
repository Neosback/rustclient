package dev.neosback.deob.analysis.protocol;

import dev.neosback.deob.identity.FieldId;
import dev.neosback.deob.identity.InstructionId;
import dev.neosback.deob.identity.MethodId;
import java.util.List;

/** Exact mask-test evidence plus writes visible in both immediate CFG successors. */
public record UpdateMaskBitEvidence(
        MethodId method,
        SyncRole role,
        int maskConstant,
        InstructionId andInstruction,
        InstructionId branchInstruction,
        List<FieldId> branchTargetWrites,
        List<FieldId> fallthroughWrites) {

    public UpdateMaskBitEvidence {
        branchTargetWrites = List.copyOf(branchTargetWrites);
        fallthroughWrites = List.copyOf(fallthroughWrites);
    }
}
