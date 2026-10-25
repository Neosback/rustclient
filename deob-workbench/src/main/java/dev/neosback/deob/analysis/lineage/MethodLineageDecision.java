package dev.neosback.deob.analysis.lineage;

import dev.neosback.deob.identity.MethodId;
import java.util.List;

/** Best explainable target for one source method, with runner-up context for ambiguity. */
public record MethodLineageDecision(
        MethodId source,
        MethodId target,
        int score,
        Integer runnerUpScore,
        LineageConfidence confidence,
        List<LineageEvidence> evidence) {

    public MethodLineageDecision {
        evidence = List.copyOf(evidence);
    }
}
