package dev.neosback.deob.analysis.cfg;

/** Inclusive start, exclusive end over stable opcode instruction indices. */
public record BasicBlock(int id, int startInstruction, int endInstructionExclusive) {
    public BasicBlock {
        if (id < 0 || startInstruction < 0 || endInstructionExclusive <= startInstruction) {
            throw new IllegalArgumentException("Invalid basic block range");
        }
    }

    public boolean contains(int instructionIndex) {
        return instructionIndex >= startInstruction && instructionIndex < endInstructionExclusive;
    }

    public boolean intersects(int startInclusive, int endExclusive) {
        return startInstruction < endExclusive && endInstructionExclusive > startInclusive;
    }
}
