package dev.neosback.deob.identity;

import java.util.Objects;

/** Stable index over opcode-bearing instructions only; labels/debug/frame nodes are excluded. */
public record InstructionId(MethodId method, int opcodeIndex) {
    public InstructionId {
        Objects.requireNonNull(method, "method");
        if (opcodeIndex < 0) {
            throw new IllegalArgumentException("opcodeIndex must be >= 0");
        }
    }

    @Override
    public String toString() {
        return method.stableKey() + "#" + opcodeIndex;
    }
}
