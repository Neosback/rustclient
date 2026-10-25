package dev.neosback.deob.analysis.protocol;

import java.util.List;

public record MaskLineageReport(
        String sourceSha256,
        String targetSha256,
        String sourcePacketRevision,
        String targetPacketRevision,
        List<MaskLineageMatch> matches,
        List<String> warnings) {

    public MaskLineageReport {
        matches = List.copyOf(matches);
        warnings = List.copyOf(warnings);
    }
}
