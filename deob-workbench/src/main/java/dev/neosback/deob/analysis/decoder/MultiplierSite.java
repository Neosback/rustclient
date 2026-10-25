package dev.neosback.deob.analysis.decoder;

import dev.neosback.deob.identity.FieldId;
import dev.neosback.deob.identity.InstructionId;

/** One exact multiply site associated with a field read or field write. */
public record MultiplierSite(
        FieldId field,
        DecoderDirection direction,
        int bits,
        long multiplier,
        InstructionId multiplyInstruction,
        InstructionId fieldInstruction,
        DecoderConfidence confidence) {
}
