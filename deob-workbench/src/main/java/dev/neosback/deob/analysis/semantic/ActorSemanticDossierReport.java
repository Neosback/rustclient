package dev.neosback.deob.analysis.semantic;

import java.util.List;

public record ActorSemanticDossierReport(
        String sourceSha256,
        String targetSha256,
        String vocabularyId,
        List<SemanticFieldProposal> proposals,
        List<String> warnings) {

    public ActorSemanticDossierReport {
        proposals = List.copyOf(proposals);
        warnings = List.copyOf(warnings);
    }
}
