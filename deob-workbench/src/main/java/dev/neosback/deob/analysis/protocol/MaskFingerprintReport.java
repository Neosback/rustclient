package dev.neosback.deob.analysis.protocol;

import java.util.List;

public record MaskFingerprintReport(
        String corpusSha256,
        String packetRevision,
        List<MaskBlockFingerprint> fingerprints) {

    public MaskFingerprintReport {
        fingerprints = List.copyOf(fingerprints);
    }
}
