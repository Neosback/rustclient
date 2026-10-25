package dev.neosback.deob.analysis.semantic;

import dev.neosback.deob.analysis.injected.InjectedFieldAnchor;
import dev.neosback.deob.analysis.injected.InjectedMethodAnchor;
import java.util.List;

public record ActorSemanticDossierReport(
        String sourceSha256,
        String targetSha256,
        String vocabularyId,
        List<SemanticFieldProposal> proposals,
        List<InjectedFieldAnchor> injectedFieldAnchors,
        List<InjectedMethodAnchor> injectedMethodAnchors,
        List<String> warnings) {

    public ActorSemanticDossierReport {
        proposals = List.copyOf(proposals);
        injectedFieldAnchors = List.copyOf(injectedFieldAnchors);
        injectedMethodAnchors = List.copyOf(injectedMethodAnchors);
        warnings = List.copyOf(warnings);
    }
}
