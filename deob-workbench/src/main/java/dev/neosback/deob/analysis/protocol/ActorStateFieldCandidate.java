package dev.neosback.deob.analysis.protocol;

import dev.neosback.deob.identity.FieldId;
import java.util.List;

/** Low-confidence semantic vocabulary attached to a mask-controlled actor-state field. */
public record ActorStateFieldCandidate(
        FieldId field,
        List<SyncRole> roles,
        int maskControlledWriteEvidence,
        List<String> candidateSemantics,
        List<String> reasons) {

    public ActorStateFieldCandidate {
        roles = List.copyOf(roles);
        candidateSemantics = List.copyOf(candidateSemantics);
        reasons = List.copyOf(reasons);
    }
}
