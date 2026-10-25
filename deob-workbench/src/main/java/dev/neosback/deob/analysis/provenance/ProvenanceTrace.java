package dev.neosback.deob.analysis.provenance;

import dev.neosback.deob.identity.MethodId;
import java.util.List;

/** Read-only data-flow trace for one exact method. */
public record ProvenanceTrace(MethodId method, List<ProvenanceStep> steps) {
    public ProvenanceTrace {
        steps = List.copyOf(steps);
    }
}
