package dev.neosback.deob.analysis.injected;

import java.util.List;

public record InjectedSemanticAnchorReport(
        String corpusSha256,
        List<InjectedFieldAnchor> fieldAnchors,
        List<InjectedMethodAnchor> methodAnchors,
        List<String> warnings) {

    public InjectedSemanticAnchorReport {
        fieldAnchors = List.copyOf(fieldAnchors);
        methodAnchors = List.copyOf(methodAnchors);
        warnings = List.copyOf(warnings);
    }
}
