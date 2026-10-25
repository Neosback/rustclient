package dev.neosback.deob.revision;

import java.util.Objects;

public record EvidenceSource(
    String id,
    SourceRole role,
    SourceStatus status,
    String revision,
    String version,
    String locator,
    String license,
    String notes
) {
    public EvidenceSource {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(role, "role");
        Objects.requireNonNull(status, "status");
        Objects.requireNonNull(locator, "locator");
        revision = revision == null ? "" : revision;
        version = version == null ? "" : version;
        license = license == null ? "" : license;
        notes = notes == null ? "" : notes;
    }
}
