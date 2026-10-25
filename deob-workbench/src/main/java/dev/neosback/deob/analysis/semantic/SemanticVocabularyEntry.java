package dev.neosback.deob.analysis.semantic;

import java.util.List;

/** One semantic term imported from a pinned API oracle. */
public record SemanticVocabularyEntry(
        String term,
        String category,
        String apiMember,
        String descriptorHint,
        List<String> notes) {

    public SemanticVocabularyEntry {
        notes = List.copyOf(notes);
    }
}
