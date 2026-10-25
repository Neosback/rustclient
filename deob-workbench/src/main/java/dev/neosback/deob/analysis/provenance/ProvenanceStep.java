package dev.neosback.deob.analysis.provenance;

import dev.neosback.deob.identity.InstructionId;
import java.util.List;

/** Data-flow inputs visible immediately before one executable instruction. */
public record ProvenanceStep(
        InstructionId instruction,
        int opcode,
        String operation,
        List<ValueOrigin> inputs,
        boolean completeInputs) {

    public ProvenanceStep {
        inputs = List.copyOf(inputs);
    }
}
