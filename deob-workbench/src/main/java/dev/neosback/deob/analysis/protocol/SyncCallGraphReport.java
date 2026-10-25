package dev.neosback.deob.analysis.protocol;

import java.util.List;

public record SyncCallGraphReport(
        String corpusSha256,
        String packetRevision,
        int maxDepth,
        List<SyncReachableMethod> reachableMethods,
        List<String> warnings) {

    public SyncCallGraphReport {
        reachableMethods = List.copyOf(reachableMethods);
        warnings = List.copyOf(warnings);
    }
}
