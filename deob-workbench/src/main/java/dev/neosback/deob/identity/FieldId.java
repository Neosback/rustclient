package dev.neosback.deob.identity;

import java.util.Objects;

public record FieldId(ClassId owner, String name, String descriptor) implements Comparable<FieldId> {
    public FieldId {
        Objects.requireNonNull(owner, "owner");
        Objects.requireNonNull(name, "name");
        Objects.requireNonNull(descriptor, "descriptor");
    }

    public String stableKey() {
        return owner.internalName() + "." + name + ":" + descriptor;
    }

    @Override
    public int compareTo(FieldId other) {
        return stableKey().compareTo(other.stableKey());
    }

    @Override
    public String toString() {
        return stableKey();
    }
}
