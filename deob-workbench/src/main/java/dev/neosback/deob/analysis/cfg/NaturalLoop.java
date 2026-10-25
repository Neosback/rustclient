package dev.neosback.deob.analysis.cfg;

import java.util.Set;
import java.util.TreeSet;

/** A natural loop proven by a back edge whose target dominates its source. */
public record NaturalLoop(
        int headerBlock,
        int backEdgeFromBlock,
        Set<Integer> blocks) {

    public NaturalLoop {
        blocks = Set.copyOf(new TreeSet<>(blocks));
    }
}
