package dev.neosback.deob.identity;

import java.util.Objects;

public record MethodId(ClassId owner, String name, String descriptor) implements Comparable<MethodId> {
    public MethodId {
        Objects.requireNonNull(owner, "owner");
        Objects.requireNonNull(name, "name");
        Objects.requireNonNull(descriptor, "descriptor");
    }

    public String stableKey() {
        return owner.internalName() + "." + name + descriptor;
    }

    @Override
    public int compareTo(MethodId other) {
        return stableKey().compareTo(other.stableKey());
    }

    @Override
    public String toString() {
        return stableKey();
    }
}
