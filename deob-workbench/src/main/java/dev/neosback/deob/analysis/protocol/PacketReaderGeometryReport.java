package dev.neosback.deob.analysis.protocol;

import java.util.List;
import java.util.Map;

public record PacketReaderGeometryReport(
    String sourceSha256,
    int expectedReaderCount,
    Integer selectedTryCatchCount,
    Map<Integer, Integer> booleanMethodTryCatchHistogram,
    List<PacketReaderGeometryCandidate> candidates,
    List<String> warnings
) {
    public PacketReaderGeometryReport {
        booleanMethodTryCatchHistogram = Map.copyOf(booleanMethodTryCatchHistogram);
        candidates = List.copyOf(candidates);
        warnings = List.copyOf(warnings);
    }
}
