package dev.neosback.deob.identity;

import java.util.Objects;

public record ClassId(String internalName) implements Comparable<ClassId> {
    public ClassId {
        Objects.requireNonNull(internalName, "internalName");
        if (internalName.isBlank()) {
            throw new IllegalArgumentException("internalName must not be blank");
        }
    }

    @Override
    public int compareTo(ClassId other) {
        return internalName.compareTo(other.internalName);
    }

    @Override
    public String toString() {
        return internalName;
    }
}
