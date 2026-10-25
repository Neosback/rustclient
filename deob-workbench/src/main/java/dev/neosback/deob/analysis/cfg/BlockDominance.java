package dev.neosback.deob.analysis.cfg;

import java.util.Set;
import java.util.TreeSet;

public record BlockDominance(
        int blockId,
        Set<Integer> dominators,
        Integer immediateDominator) {

    public BlockDominance {
        dominators = Set.copyOf(new TreeSet<>(dominators));
    }
}
