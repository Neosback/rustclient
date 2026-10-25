package dev.neosback.deob.analysis.decoder;

import java.util.List;

public record DecoderEvidenceReport(
        List<MultiplierSite> sites,
        List<DecoderPair> provenPairs,
        List<String> warnings) {

    public DecoderEvidenceReport {
        sites = List.copyOf(sites);
        provenPairs = List.copyOf(provenPairs);
        warnings = List.copyOf(warnings);
    }
}
