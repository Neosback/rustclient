package dev.neosback.deob.analysis.semantic;

import dev.neosback.deob.analysis.lineage.LineageConfidence;
import dev.neosback.deob.analysis.protocol.ActorBehaviorClusterAnalyzer;
import dev.neosback.deob.analysis.protocol.ActorBehaviorClusterCandidate;
import dev.neosback.deob.analysis.protocol.ActorBehaviorClusterKind;
import dev.neosback.deob.analysis.protocol.MaskLineageMatch;
import dev.neosback.deob.analysis.protocol.MaskLineageMatcher;
import dev.neosback.deob.analysis.protocol.PacketTable;
import dev.neosback.deob.corpus.JarCorpus;
import dev.neosback.deob.identity.FieldId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Fuses target mask behavior, cross-build mask lineage, and a pinned semantic vocabulary.
 * Results are proposals only; the analyzer never writes mappings.
 */
public final class ActorSemanticDossierAnalyzer {
    public ActorSemanticDossierReport analyze(
            JarCorpus source,
            PacketTable sourcePackets,
            JarCorpus target,
            PacketTable targetPackets,
            SemanticVocabularyCatalog vocabulary) {
        var sourceClusters = new ActorBehaviorClusterAnalyzer().analyze(source, sourcePackets).candidates();
        var targetClusters = new ActorBehaviorClusterAnalyzer().analyze(target, targetPackets).candidates();
        var lineage = new MaskLineageMatcher().match(source, sourcePackets, target, targetPackets);

        Map<String, Set<ActorBehaviorClusterKind>> sourceKindsByHash = new HashMap<>();
        for (ActorBehaviorClusterCandidate cluster : sourceClusters) {
            sourceKindsByHash
                    .computeIfAbsent(cluster.maskStructuralHash(), ignored -> new HashSet<>())
                    .add(cluster.kind());
        }

        Map<String, LineageSupport> supportByTargetHash = new HashMap<>();
        for (MaskLineageMatch match : lineage.matches()) {
            if (match.target() == null || match.confidence() == LineageConfidence.UNMATCHED) continue;
            String targetHash = match.target().combinedStructuralHash();
            Set<ActorBehaviorClusterKind> sourceKinds = sourceKindsByHash.getOrDefault(
                    match.source().combinedStructuralHash(), Set.of());
            LineageSupport candidate = new LineageSupport(match.confidence(), sourceKinds, match.score());
            supportByTargetHash.merge(targetHash, candidate, ActorSemanticDossierAnalyzer::stronger);
        }

        Map<String, SemanticFieldProposal> best = new HashMap<>();
        for (ActorBehaviorClusterCandidate cluster : targetClusters) {
            Set<String> allowedCategories = categories(cluster.kind());
            if (allowedCategories.isEmpty()) continue;
            LineageSupport support = supportByTargetHash.get(cluster.maskStructuralHash());

            for (FieldId field : cluster.fieldWrites()) {
                for (SemanticVocabularyEntry term : vocabulary.entries()) {
                    if (!allowedCategories.contains(term.category())) continue;
                    if (term.descriptorHint() == null || term.descriptorHint().isBlank()) continue;
                    if (!term.descriptorHint().equals(field.descriptor())) continue;

                    List<String> evidence = new ArrayList<>();
                    int score = Math.min(45, cluster.evidenceScore() / 2);
                    evidence.add("target-behavior-cluster=" + cluster.kind() + " score=" + cluster.evidenceScore());
                    score += 20;
                    evidence.add("pinned-vocabulary-category=" + term.category() + " +20");
                    score += 15;
                    evidence.add("exact-descriptor-hint=" + field.descriptor() + " +15");

                    LineageConfidence lineageConfidence = LineageConfidence.UNMATCHED;
                    if (support != null) {
                        lineageConfidence = support.confidence();
                        int bonus = lineageBonus(support.confidence());
                        score += bonus;
                        evidence.add("mask-lineage=" + support.confidence()
                                + " score=" + support.score() + " +" + bonus);
                        if (support.sourceKinds().contains(cluster.kind())) {
                            score += 10;
                            evidence.add("same-behavior-cluster-on-source-mask +10");
                        }
                    } else {
                        evidence.add("no-cross-build-mask-lineage-support");
                    }
                    evidence.add("semantic-source=" + vocabulary.id() + "@" + vocabulary.sourceCommit());

                    SemanticFieldProposal proposal = new SemanticFieldProposal(
                            field,
                            term.term(),
                            term.category(),
                            cluster.kind(),
                            Math.min(100, score),
                            lineageConfidence,
                            evidence);
                    String key = field.stableKey() + "|" + term.term();
                    best.merge(key, proposal, (left, right) -> left.score() >= right.score() ? left : right);
                }
            }
        }

        List<SemanticFieldProposal> proposals = best.values().stream()
                .sorted(Comparator
                        .comparingInt(SemanticFieldProposal::score).reversed()
                        .thenComparing(SemanticFieldProposal::field)
                        .thenComparing(SemanticFieldProposal::term))
                .toList();
        List<String> warnings = new ArrayList<>(lineage.warnings());
        if (proposals.isEmpty()) warnings.add("no semantic field proposals satisfied descriptor/category evidence");
        if (proposals.stream().noneMatch(proposal -> proposal.lineageConfidence() == LineageConfidence.HIGH)) {
            warnings.add("no proposals currently have HIGH cross-build mask-lineage support");
        }
        return new ActorSemanticDossierReport(
                source.sha256(), target.sha256(), vocabulary.id(), proposals, warnings);
    }

    private static Set<String> categories(ActorBehaviorClusterKind kind) {
        return switch (kind) {
            case ANIMATION_FRAME_CYCLE -> Set.of(
                    "ANIMATION_PRIMARY",
                    "ANIMATION_FRAME",
                    "POSE_ANIMATION",
                    "POSE_FRAME",
                    "POSE_IDLE",
                    "POSE_WALK",
                    "POSE_RUN");
            case ORIENTATION_OR_TARGET -> Set.of("ORIENTATION_TARGET", "ORIENTATION_CURRENT");
            case OVERHEAD_TEXT -> Set.of("OVERHEAD_TEXT", "OVERHEAD_TEXT_CYCLE");
            case SPOT_ANIMATION -> Set.of("SPOT_ANIMATION", "ANIMATION_HEIGHT");
            case MOVEMENT_PATH, HEALTH_OR_HITSPLAT, UNKNOWN -> Set.of();
        };
    }

    private static int lineageBonus(LineageConfidence confidence) {
        return switch (confidence) {
            case HIGH -> 20;
            case MEDIUM -> 12;
            case LOW -> 5;
            case AMBIGUOUS, UNMATCHED -> 0;
        };
    }

    private static LineageSupport stronger(LineageSupport left, LineageSupport right) {
        int leftRank = rank(left.confidence());
        int rightRank = rank(right.confidence());
        if (leftRank != rightRank) return leftRank > rightRank ? left : right;
        return left.score() >= right.score() ? left : right;
    }

    private static int rank(LineageConfidence confidence) {
        return switch (confidence) {
            case HIGH -> 5;
            case MEDIUM -> 4;
            case LOW -> 3;
            case AMBIGUOUS -> 2;
            case UNMATCHED -> 1;
        };
    }

    private record LineageSupport(
            LineageConfidence confidence,
            Set<ActorBehaviorClusterKind> sourceKinds,
            int score) {
        private LineageSupport {
            sourceKinds = Set.copyOf(sourceKinds);
        }
    }
}
