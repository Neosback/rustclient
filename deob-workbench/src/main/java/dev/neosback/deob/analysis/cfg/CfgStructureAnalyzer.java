package dev.neosback.deob.analysis.cfg;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/** Dominators and natural loops derived from an exact CFG without rewriting control flow. */
public final class CfgStructureAnalyzer {
    public CfgStructureReport analyze(ControlFlowGraph cfg) {
        if (cfg.blocks().isEmpty()) {
            return new CfgStructureReport(cfg.method(), List.of(), List.of());
        }

        Map<Integer, Set<Integer>> predecessors = predecessors(cfg, true);
        Map<Integer, Set<Integer>> normalPredecessors = predecessors(cfg, false);
        Set<Integer> allBlocks = new TreeSet<>();
        for (BasicBlock block : cfg.blocks()) {
            allBlocks.add(block.id());
        }

        Map<Integer, Set<Integer>> dominators = new HashMap<>();
        for (BasicBlock block : cfg.blocks()) {
            if (block.id() == 0) {
                dominators.put(block.id(), new TreeSet<>(Set.of(0)));
            } else {
                dominators.put(block.id(), new TreeSet<>(allBlocks));
            }
        }

        boolean changed;
        do {
            changed = false;
            for (BasicBlock block : cfg.blocks()) {
                if (block.id() == 0) {
                    continue;
                }
                Set<Integer> incoming = predecessors.getOrDefault(block.id(), Set.of());
                TreeSet<Integer> next = new TreeSet<>();
                if (incoming.isEmpty()) {
                    next.add(block.id());
                } else {
                    boolean first = true;
                    for (int predecessor : incoming) {
                        if (first) {
                            next.addAll(dominators.get(predecessor));
                            first = false;
                        } else {
                            next.retainAll(dominators.get(predecessor));
                        }
                    }
                    next.add(block.id());
                }
                if (!next.equals(dominators.get(block.id()))) {
                    dominators.put(block.id(), next);
                    changed = true;
                }
            }
        } while (changed);

        List<BlockDominance> dominance = new ArrayList<>();
        for (BasicBlock block : cfg.blocks()) {
            dominance.add(new BlockDominance(
                    block.id(),
                    dominators.get(block.id()),
                    immediateDominator(block.id(), dominators)));
        }

        List<NaturalLoop> loops = new ArrayList<>();
        for (ControlFlowEdge edge : cfg.edges()) {
            if (edge.kind() == EdgeKind.EXCEPTION || edge.kind() == EdgeKind.SUBROUTINE) {
                continue;
            }
            Set<Integer> tailDominators = dominators.get(edge.fromBlock());
            if (tailDominators == null || !tailDominators.contains(edge.toBlock())) {
                continue;
            }
            loops.add(new NaturalLoop(
                    edge.toBlock(),
                    edge.fromBlock(),
                    naturalLoop(edge.toBlock(), edge.fromBlock(), normalPredecessors)));
        }
        loops.sort((left, right) -> {
            int header = Integer.compare(left.headerBlock(), right.headerBlock());
            return header != 0 ? header : Integer.compare(left.backEdgeFromBlock(), right.backEdgeFromBlock());
        });
        return new CfgStructureReport(cfg.method(), dominance, loops);
    }

    private static Integer immediateDominator(
            int block,
            Map<Integer, Set<Integer>> dominators) {
        if (block == 0) {
            return null;
        }
        TreeSet<Integer> strict = new TreeSet<>(dominators.getOrDefault(block, Set.of()));
        strict.remove(block);
        for (int candidate : strict) {
            Set<Integer> candidateDominators = dominators.getOrDefault(candidate, Set.of());
            boolean containsEveryOtherStrict = true;
            for (int other : strict) {
                if (other != candidate && !candidateDominators.contains(other)) {
                    containsEveryOtherStrict = false;
                    break;
                }
            }
            if (containsEveryOtherStrict) {
                return candidate;
            }
        }
        return null;
    }

    private static Set<Integer> naturalLoop(
            int header,
            int tail,
            Map<Integer, Set<Integer>> predecessors) {
        TreeSet<Integer> loop = new TreeSet<>();
        loop.add(header);
        loop.add(tail);
        ArrayDeque<Integer> work = new ArrayDeque<>();
        if (tail != header) {
            work.add(tail);
        }
        while (!work.isEmpty()) {
            int current = work.removeFirst();
            for (int predecessor : predecessors.getOrDefault(current, Set.of())) {
                if (loop.add(predecessor) && predecessor != header) {
                    work.addLast(predecessor);
                }
            }
        }
        return loop;
    }

    private static Map<Integer, Set<Integer>> predecessors(
            ControlFlowGraph cfg,
            boolean includeExceptional) {
        Map<Integer, Set<Integer>> result = new HashMap<>();
        for (BasicBlock block : cfg.blocks()) {
            result.put(block.id(), new HashSet<>());
        }
        for (ControlFlowEdge edge : cfg.edges()) {
            if (!includeExceptional
                    && (edge.kind() == EdgeKind.EXCEPTION || edge.kind() == EdgeKind.SUBROUTINE)) {
                continue;
            }
            result.computeIfAbsent(edge.toBlock(), ignored -> new HashSet<>()).add(edge.fromBlock());
        }
        return result;
    }
}
