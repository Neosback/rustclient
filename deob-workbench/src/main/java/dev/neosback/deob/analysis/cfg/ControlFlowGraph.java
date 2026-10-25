package dev.neosback.deob.analysis.cfg;

import dev.neosback.deob.identity.MethodId;
import java.util.List;
import java.util.Objects;

public record ControlFlowGraph(
    MethodId method,
    int instructionCount,
    List<BasicBlock> blocks,
    List<ControlFlowEdge> edges
) {
    public ControlFlowGraph {
        Objects.requireNonNull(method, "method");
        if (instructionCount < 0) {
            throw new IllegalArgumentException("instructionCount must be >= 0");
        }
        blocks = List.copyOf(blocks);
        edges = List.copyOf(edges);
    }
}
