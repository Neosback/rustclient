package dev.neosback.deob.analysis.cfg;

import dev.neosback.deob.identity.MethodId;
import java.util.List;

public record CfgStructureReport(
        MethodId method,
        List<BlockDominance> dominance,
        List<NaturalLoop> naturalLoops) {

    public CfgStructureReport {
        dominance = List.copyOf(dominance);
        naturalLoops = List.copyOf(naturalLoops);
    }
}
