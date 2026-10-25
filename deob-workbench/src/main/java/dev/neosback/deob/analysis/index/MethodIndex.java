package dev.neosback.deob.analysis.index;

import dev.neosback.deob.identity.MethodId;

public record MethodIndex(
    MethodId id,
    int access,
    int instructionCount,
    int branchCount,
    int switchCount,
    int tryCatchCount,
    int invokeCount,
    int fieldAccessCount,
    String exactSha256,
    String structuralSha256
) {}
