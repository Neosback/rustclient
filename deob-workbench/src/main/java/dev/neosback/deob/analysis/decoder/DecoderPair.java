package dev.neosback.deob.analysis.decoder;

import dev.neosback.deob.identity.FieldId;

/** A read/write multiplier pair proven inverse modulo the field width. */
public record DecoderPair(
        FieldId field,
        int bits,
        long readMultiplier,
        long writeMultiplier,
        String readMultiplierUnsigned,
        String writeMultiplierUnsigned) {
}
