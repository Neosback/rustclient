package dev.neosback.deob.analysis.protocol;

import dev.neosback.deob.identity.MethodId;
import java.util.List;

/** Structural evidence for a packet-reader-shaped boolean method. */
public record PacketReaderGeometryCandidate(
    MethodId method,
    int tryCatchCount,
    int instructionCount,
    int branchCount,
    int score,
    List<String> reasons
) {
    public PacketReaderGeometryCandidate {
        reasons = List.copyOf(reasons);
    }
}
