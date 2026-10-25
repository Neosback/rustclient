package dev.neosback.deob.analysis.injected;

import java.util.List;

public record InjectedApiBridgeReport(
        String corpusSha256,
        List<InjectedApiBridge> bridges,
        int unclassifiedSemanticMethods,
        List<String> warnings) {

    public InjectedApiBridgeReport {
        bridges = List.copyOf(bridges);
        warnings = List.copyOf(warnings);
    }
}
