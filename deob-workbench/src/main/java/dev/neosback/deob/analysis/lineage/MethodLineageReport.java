package dev.neosback.deob.analysis.lineage;

import java.util.List;

public record MethodLineageReport(
        String sourceJarSha256,
        String targetJarSha256,
        int sourceMethodCount,
        int targetMethodCount,
        List<MethodLineageDecision> decisions) {

    public MethodLineageReport {
        decisions = List.copyOf(decisions);
    }
}
