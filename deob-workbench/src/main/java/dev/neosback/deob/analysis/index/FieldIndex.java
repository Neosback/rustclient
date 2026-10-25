package dev.neosback.deob.analysis.index;

import dev.neosback.deob.identity.FieldId;

public record FieldIndex(FieldId id, int access, Object constantValue) {}
