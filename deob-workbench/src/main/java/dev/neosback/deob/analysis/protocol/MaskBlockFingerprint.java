package dev.neosback.deob.analysis.protocol;

import dev.neosback.deob.identity.InstructionId;
import dev.neosback.deob.identity.MethodId;
import java.util.List;

/** Canonical structural fingerprint for one sync update-mask test. */
public record MaskBlockFingerprint(
        MethodId method,
        SyncRole role,
        int maskConstant,
        InstructionId branchInstruction,
        List<MaskSuccessorFingerprint> successors,
        String combinedStructuralHash) {

    public MaskBlockFingerprint {
        successors = List.copyOf(successors);
    }
}
