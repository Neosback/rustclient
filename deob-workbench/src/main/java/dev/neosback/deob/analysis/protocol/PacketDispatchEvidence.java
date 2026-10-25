package dev.neosback.deob.analysis.protocol;

import dev.neosback.deob.identity.InstructionId;
import dev.neosback.deob.identity.MethodId;
import java.util.List;

/** One packet-table entry correlated to an exact switch case and its direct block calls. */
public record PacketDispatchEvidence(
        MethodId dispatcher,
        InstructionId switchInstruction,
        int packetId,
        String packetName,
        int packetLength,
        int targetInstruction,
        List<MethodId> directCalls) {

    public PacketDispatchEvidence {
        directCalls = List.copyOf(directCalls);
    }
}
