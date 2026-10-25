package dev.neosback.deob.analysis.cfg;

import java.util.Objects;

public record ControlFlowEdge(
    int fromBlock,
    int toBlock,
    EdgeKind kind,
    String detail,
    boolean backward
) {
    public ControlFlowEdge {
        if (fromBlock < 0 || toBlock < 0) {
            throw new IllegalArgumentException("Block ids must be >= 0");
        }
        Objects.requireNonNull(kind, "kind");
        detail = detail == null ? "" : detail;
    }
}
