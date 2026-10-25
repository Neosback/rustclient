package dev.neosback.deob.analysis.protocol;

import java.util.List;

public record BufferFamilyReport(
        String corpusSha256,
        List<BufferFamilyCandidate> candidates) {

    public BufferFamilyReport {
        candidates = List.copyOf(candidates);
    }
}
