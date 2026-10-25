package dev.neosback.deob.analysis.protocol;

import dev.neosback.deob.analysis.lineage.LineageConfidence;
import dev.neosback.deob.corpus.JarCorpus;
import dev.neosback.deob.identity.FieldId;
import dev.neosback.deob.identity.MethodId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Aligns update-mask behavior across builds without depending on obfuscated names or mask values.
 * Exact structural hashes are strongest evidence; constants and member descriptors are supporting
 * evidence only. No match is promoted into the mapping ledger automatically.
 */
public final class MaskLineageMatcher {
    public MaskLineageReport match(
            JarCorpus source,
            PacketTable sourcePackets,
            JarCorpus target,
            PacketTable targetPackets) {
        MaskFingerprintReport sourceReport = new MaskFingerprintAnalyzer().analyze(source, sourcePackets);
        MaskFingerprintReport targetReport = new MaskFingerprintAnalyzer().analyze(target, targetPackets);
        List<MaskBlockFingerprint> targets = targetReport.fingerprints();
        List<MaskLineageMatch> matches = new ArrayList<>();
        List<String> warnings = new ArrayList<>();

        for (MaskBlockFingerprint sourceMask : sourceReport.fingerprints()) {
            List<Scored> scored = new ArrayList<>();
            for (MaskBlockFingerprint targetMask : targets) {
                scored.add(score(sourceMask, targetMask));
            }
            scored.sort(Comparator
                    .comparingInt(Scored::score).reversed()
                    .thenComparing(value -> value.target().method())
                    .thenComparingInt(value -> value.target().branchInstruction().opcodeIndex()));

            if (scored.isEmpty() || scored.get(0).score() < 30) {
                matches.add(new MaskLineageMatch(
                        sourceMask,
                        null,
                        scored.isEmpty() ? 0 : scored.get(0).score(),
                        scored.size() > 1 ? scored.get(1).score() : 0,
                        LineageConfidence.UNMATCHED,
                        scored.isEmpty() ? List.of("no target mask fingerprints") : scored.get(0).evidence()));
                continue;
            }

            Scored best = scored.get(0);
            int runnerUp = scored.size() > 1 ? scored.get(1).score() : 0;
            LineageConfidence confidence = confidence(best.score(), runnerUp);
            matches.add(new MaskLineageMatch(
                    sourceMask,
                    best.target(),
                    best.score(),
                    runnerUp,
                    confidence,
                    best.evidence()));
        }

        long ambiguous = matches.stream().filter(match -> match.confidence() == LineageConfidence.AMBIGUOUS).count();
        long unmatched = matches.stream().filter(match -> match.confidence() == LineageConfidence.UNMATCHED).count();
        if (ambiguous > 0) warnings.add("ambiguous-mask-matches=" + ambiguous);
        if (unmatched > 0) warnings.add("unmatched-source-masks=" + unmatched);
        if (!sourcePackets.revision().equals(targetPackets.revision())) {
            warnings.add("packet revisions differ; require protocol-fingerprint evidence before carrying semantics");
        }

        matches.sort(Comparator
                .comparing((MaskLineageMatch match) -> match.source().method())
                .thenComparingInt(match -> match.source().branchInstruction().opcodeIndex()));
        return new MaskLineageReport(
                source.sha256(),
                target.sha256(),
                sourcePackets.revision(),
                targetPackets.revision(),
                matches,
                warnings);
    }

    private static Scored score(MaskBlockFingerprint source, MaskBlockFingerprint target) {
        int score = 0;
        List<String> evidence = new ArrayList<>();

        if (source.combinedStructuralHash().equals(target.combinedStructuralHash())) {
            score += 65;
            evidence.add("exact-combined-structural-hash +65");
        }
        if (source.role() == target.role()) {
            score += 10;
            evidence.add("same-sync-role +10");
        } else if (source.role() == SyncRole.MIXED || target.role() == SyncRole.MIXED) {
            score += 4;
            evidence.add("compatible-mixed-sync-role +4");
        }
        if (source.successors().size() == target.successors().size()) {
            score += 5;
            evidence.add("same-successor-count +5");
        }

        int sharedSuccessorHashes = sharedSuccessorHashes(source, target);
        if (sharedSuccessorHashes > 0) {
            int contribution = Math.min(20, sharedSuccessorHashes * 10);
            score += contribution;
            evidence.add("shared-successor-structural-hashes=" + sharedSuccessorHashes + " +" + contribution);
        }
        if (descriptorShape(source).equals(descriptorShape(target))) {
            score += 7;
            evidence.add("same-field-descriptor-shape +7");
        }
        if (readCountShape(source).equals(readCountShape(target))) {
            score += 5;
            evidence.add("same-packet-read-count-shape +5");
        }
        if (callDescriptorShape(source).equals(callDescriptorShape(target))) {
            score += 5;
            evidence.add("same-downstream-call-descriptor-shape +5");
        }
        if (source.maskConstant() == target.maskConstant()) {
            score += 3;
            evidence.add("same-mask-constant +3");
        } else {
            evidence.add("mask-constant-changed 0x"
                    + Integer.toHexString(source.maskConstant())
                    + "->0x"
                    + Integer.toHexString(target.maskConstant()));
        }

        return new Scored(target, Math.min(100, score), evidence);
    }

    private static int sharedSuccessorHashes(MaskBlockFingerprint source, MaskBlockFingerprint target) {
        List<String> remaining = new ArrayList<>(target.successors().stream()
                .map(MaskSuccessorFingerprint::structuralHash)
                .toList());
        int count = 0;
        for (MaskSuccessorFingerprint successor : source.successors()) {
            int index = remaining.indexOf(successor.structuralHash());
            if (index >= 0) {
                count++;
                remaining.remove(index);
            }
        }
        return count;
    }

    private static Map<String, Integer> descriptorShape(MaskBlockFingerprint fingerprint) {
        Map<String, Integer> shape = new TreeMap<>();
        for (MaskSuccessorFingerprint successor : fingerprint.successors()) {
            for (FieldId field : successor.fieldWrites()) {
                shape.merge(normalizeDescriptor(field.descriptor()), 1, Integer::sum);
            }
        }
        return shape;
    }

    private static List<Integer> readCountShape(MaskBlockFingerprint fingerprint) {
        return fingerprint.successors().stream()
                .map(successor -> successor.packetReadMethods().size())
                .sorted()
                .toList();
    }

    private static Map<String, Integer> callDescriptorShape(MaskBlockFingerprint fingerprint) {
        Map<String, Integer> shape = new TreeMap<>();
        for (MaskSuccessorFingerprint successor : fingerprint.successors()) {
            for (MethodId method : successor.downstreamCalls()) {
                shape.merge(normalizeDescriptor(method.descriptor()), 1, Integer::sum);
            }
        }
        return shape;
    }

    private static String normalizeDescriptor(String descriptor) {
        return descriptor.replaceAll("L[^;]+;", "L*;");
    }

    private static LineageConfidence confidence(int best, int runnerUp) {
        if (best - runnerUp <= 4) return LineageConfidence.AMBIGUOUS;
        if (best >= 85) return LineageConfidence.HIGH;
        if (best >= 65) return LineageConfidence.MEDIUM;
        if (best >= 30) return LineageConfidence.LOW;
        return LineageConfidence.UNMATCHED;
    }

    private record Scored(MaskBlockFingerprint target, int score, List<String> evidence) {
        private Scored {
            evidence = List.copyOf(evidence);
        }
    }
}
