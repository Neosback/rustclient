package dev.neosback.deob.analysis.lineage;

/** One explainable contribution to a cross-revision candidate score. */
public record LineageEvidence(String feature, int points, String detail) {
    public LineageEvidence {
        feature = feature == null ? "" : feature;
        detail = detail == null ? "" : detail;
    }
}
