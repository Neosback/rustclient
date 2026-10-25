package dev.neosback.deob.analysis.semantic;

import java.util.List;

/** Versioned semantic vocabulary sourced from a pinned external API snapshot. */
public record SemanticVocabularyCatalog(
        String id,
        String sourceRepository,
        String sourceCommit,
        String sourceVersion,
        List<String> sourceFiles,
        List<SemanticVocabularyEntry> entries) {

    public SemanticVocabularyCatalog {
        sourceFiles = List.copyOf(sourceFiles);
        entries = List.copyOf(entries);
    }
}
