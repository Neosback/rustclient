package dev.neosback.deob.analysis.protocol;

import dev.neosback.deob.analysis.lineage.LineageConfidence;
import java.util.List;

/** Explainable source-to-target alignment for one update-mask behavior block. */
public record MaskLineageMatch(
        MaskBlockFingerprint source,
        MaskBlockFingerprint target,
        int score,
        int runnerUpScore,
        LineageConfidence confidence,
        List<String> evidence) {

    public MaskLineageMatch {
        evidence = List.copyOf(evidence);
    }
}
