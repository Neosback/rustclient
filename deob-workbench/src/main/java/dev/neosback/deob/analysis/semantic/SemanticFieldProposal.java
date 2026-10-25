package dev.neosback.deob.analysis.semantic;

import dev.neosback.deob.analysis.lineage.LineageConfidence;
import dev.neosback.deob.analysis.protocol.ActorBehaviorClusterKind;
import dev.neosback.deob.identity.FieldId;
import java.util.List;

/** Ranked semantic-name proposal for one exact target field. Never an automatic mapping. */
public record SemanticFieldProposal(
        FieldId field,
        String term,
        String category,
        ActorBehaviorClusterKind clusterKind,
        int score,
        LineageConfidence lineageConfidence,
        List<String> evidence) {

    public SemanticFieldProposal {
        evidence = List.copyOf(evidence);
    }
}
