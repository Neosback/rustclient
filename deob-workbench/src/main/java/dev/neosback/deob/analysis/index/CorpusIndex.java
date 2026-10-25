package dev.neosback.deob.analysis.index;

import java.util.List;

public record CorpusIndex(String source, String sha256, List<ClassIndex> classes) {
    public CorpusIndex {
        classes = List.copyOf(classes);
    }
}
