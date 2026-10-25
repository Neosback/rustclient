package dev.neosback.deob.revision;

import java.util.List;
import java.util.Objects;

public record SourceManifest(
    String profile,
    String behavioralAuthority,
    List<EvidenceSource> sources
) {
    public SourceManifest {
        Objects.requireNonNull(profile, "profile");
        Objects.requireNonNull(behavioralAuthority, "behavioralAuthority");
        sources = List.copyOf(sources);
    }

    public EvidenceSource require(String id) {
        return sources.stream()
            .filter(source -> source.id().equals(id))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Unknown evidence source: " + id));
    }
}
