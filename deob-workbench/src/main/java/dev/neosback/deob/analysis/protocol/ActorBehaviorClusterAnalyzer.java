package dev.neosback.deob.analysis.protocol;

import dev.neosback.deob.corpus.JarCorpus;
import dev.neosback.deob.identity.FieldId;
import dev.neosback.deob.identity.MethodId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Scores deliberately broad actor-behavior clusters from exact mask-block evidence. These labels
 * are semantic hypotheses only and are never promoted to mappings automatically.
 */
public final class ActorBehaviorClusterAnalyzer {
    public ActorBehaviorClusterReport analyze(JarCorpus corpus, PacketTable serverPackets) {
        MaskFingerprintReport masks = new MaskFingerprintAnalyzer().analyze(corpus, serverPackets);
        List<ActorBehaviorClusterCandidate> result = new ArrayList<>();

        for (MaskBlockFingerprint mask : masks.fingerprints()) {
            Set<MethodId> reads = new TreeSet<>();
            Set<MethodId> calls = new TreeSet<>();
            Set<FieldId> writes = new TreeSet<>();
            List<Integer> constants = new ArrayList<>();
            for (MaskSuccessorFingerprint successor : mask.successors()) {
                reads.addAll(successor.packetReadMethods());
                calls.addAll(successor.downstreamCalls());
                writes.addAll(successor.fieldWrites());
                constants.addAll(successor.integerConstants());
            }

            int intWrites = countDescriptor(writes, "I");
            int intArrayWrites = countDescriptor(writes, "[I");
            int stringWrites = countDescriptor(writes, "Ljava/lang/String;");
            boolean resetsZero = constants.contains(0);
            boolean sentinelMinusOne = constants.contains(-1) || constants.contains(65535);

            add(result, mask, ActorBehaviorClusterKind.OVERHEAD_TEXT,
                    stringWrites > 0 ? 75 + Math.min(15, reads.size() * 5) : 0,
                    reads, writes, calls,
                    stringWrites > 0 ? List.of("writes String state under a sync mask") : List.of());

            List<String> animationReasons = new ArrayList<>();
            int animationScore = 0;
            if (intWrites >= 3 && !reads.isEmpty()) {
                animationScore += 35 + Math.min(20, intWrites * 4) + Math.min(15, reads.size() * 5);
                animationReasons.add("multiple integer state writes follow packet reads");
            }
            if (resetsZero) {
                animationScore += 10;
                animationReasons.add("successor contains zero-reset constants consistent with frame/cycle reset");
            }
            if (sentinelMinusOne) {
                animationScore += 5;
                animationReasons.add("successor contains a common absent-sequence sentinel");
            }
            add(result, mask, ActorBehaviorClusterKind.ANIMATION_FRAME_CYCLE,
                    animationScore, reads, writes, calls, animationReasons);

            List<String> movementReasons = new ArrayList<>();
            int movementScore = 0;
            if (intArrayWrites > 0) {
                movementScore += 70;
                movementReasons.add("writes int-array actor state under a sync mask");
            }
            if (intWrites >= 4 && reads.size() >= 2) {
                movementScore += 20;
                movementReasons.add("dense integer update with multiple packet reads");
            }
            add(result, mask, ActorBehaviorClusterKind.MOVEMENT_PATH,
                    movementScore, reads, writes, calls, movementReasons);

            List<String> facingReasons = new ArrayList<>();
            int facingScore = 0;
            if (intWrites >= 1 && intWrites <= 2 && !reads.isEmpty()) {
                facingScore = 45 + Math.min(15, reads.size() * 5);
                facingReasons.add("small integer-state update fed by packet reads");
                if (sentinelMinusOne) {
                    facingScore += 5;
                    facingReasons.add("contains target-like absent sentinel");
                }
            }
            add(result, mask, ActorBehaviorClusterKind.ORIENTATION_OR_TARGET,
                    facingScore, reads, writes, calls, facingReasons);

            List<String> spotReasons = new ArrayList<>();
            int spotScore = 0;
            if (intWrites >= 2 && reads.size() >= 2) {
                spotScore = 35 + Math.min(20, reads.size() * 4);
                spotReasons.add("multiple packet reads feed multiple integer actor fields");
            }
            add(result, mask, ActorBehaviorClusterKind.SPOT_ANIMATION,
                    spotScore, reads, writes, calls, spotReasons);

            List<String> healthReasons = new ArrayList<>();
            int healthScore = 0;
            if (reads.size() >= 2 && calls.size() >= 2) {
                healthScore = 30 + Math.min(20, calls.size() * 3);
                healthReasons.add("multi-read mask block fans into multiple helper calls");
            }
            add(result, mask, ActorBehaviorClusterKind.HEALTH_OR_HITSPLAT,
                    healthScore, reads, writes, calls, healthReasons);
        }

        result.sort(Comparator
                .comparingInt(ActorBehaviorClusterCandidate::evidenceScore).reversed()
                .thenComparing(ActorBehaviorClusterCandidate::method)
                .thenComparing(candidate -> candidate.kind().name()));
        return new ActorBehaviorClusterReport(corpus.sha256(), serverPackets.revision(), result);
    }

    private static int countDescriptor(Set<FieldId> fields, String descriptor) {
        return (int) fields.stream().filter(field -> descriptor.equals(field.descriptor())).count();
    }

    private static void add(
            List<ActorBehaviorClusterCandidate> target,
            MaskBlockFingerprint mask,
            ActorBehaviorClusterKind kind,
            int score,
            Set<MethodId> reads,
            Set<FieldId> writes,
            Set<MethodId> calls,
            List<String> reasons) {
        if (score < 30) return;
        target.add(new ActorBehaviorClusterCandidate(
                mask.method(),
                mask.role(),
                mask.maskConstant(),
                mask.combinedStructuralHash(),
                kind,
                Math.min(100, score),
                List.copyOf(reads),
                List.copyOf(writes),
                List.copyOf(calls),
                reasons));
    }
}
