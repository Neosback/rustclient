package dev.neosback.deob.analysis.protocol;

import java.util.List;

public record BitAccessReport(
        String jarSha256,
        List<BitAccessCandidate> candidates) {

    public BitAccessReport {
        candidates = List.copyOf(candidates);
    }
}
