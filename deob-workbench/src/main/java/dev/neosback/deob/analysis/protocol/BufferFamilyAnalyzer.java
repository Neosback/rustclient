package dev.neosback.deob.analysis.protocol;

import dev.neosback.deob.analysis.hierarchy.ClassHierarchy;
import dev.neosback.deob.analysis.packet.PacketReadCandidate;
import dev.neosback.deob.analysis.packet.PacketReadDiscovery;
import dev.neosback.deob.corpus.JarCorpus;
import dev.neosback.deob.identity.ClassId;
import dev.neosback.deob.identity.FieldId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/** Groups structural packet-read evidence by class and exact inheritance relationships. */
public final class BufferFamilyAnalyzer {
    public BufferFamilyReport analyze(JarCorpus corpus) {
        var discovery = new PacketReadDiscovery().analyze(corpus);
        ClassHierarchy hierarchy = ClassHierarchy.analyze(corpus);
        Map<ClassId, List<PacketReadCandidate>> byOwner = new HashMap<>();
        for (PacketReadCandidate read : discovery.candidates()) {
            byOwner.computeIfAbsent(read.method().owner(), ignored -> new ArrayList<>()).add(read);
        }

        Set<ClassId> readOwners = new HashSet<>(byOwner.keySet());
        List<BufferFamilyCandidate> candidates = new ArrayList<>();
        for (Map.Entry<ClassId, List<PacketReadCandidate>> entry : byOwner.entrySet()) {
            ClassId owner = entry.getKey();
            List<PacketReadCandidate> reads = entry.getValue().stream()
                    .sorted(Comparator.comparing(PacketReadCandidate::method))
                    .toList();
            Set<FieldId> byteArrays = new TreeSet<>();
            Set<FieldId> offsets = new TreeSet<>();
            int readScore = 0;
            for (PacketReadCandidate read : reads) {
                byteArrays.addAll(read.byteArrayFields());
                offsets.addAll(read.offsetFieldCandidates());
                readScore += read.evidenceScore();
            }

            Set<ClassId> descendantReadClasses = new TreeSet<>();
            for (ClassId descendant : hierarchy.descendants(owner)) {
                if (readOwners.contains(descendant)) {
                    descendantReadClasses.add(descendant);
                }
            }

            var relation = hierarchy.require(owner);
            List<String> reasons = new ArrayList<>();
            reasons.add("structural-read-methods=" + reads.size());
            reasons.add("byte-array-fields=" + byteArrays.size());
            reasons.add("offset-field-candidates=" + offsets.size());
            if (!relation.directSubtypes().isEmpty()) {
                reasons.add("direct-subtypes=" + relation.directSubtypes().size());
            }
            if (!descendantReadClasses.isEmpty()) {
                reasons.add("descendant-read-classes=" + descendantReadClasses.size());
            }

            int score = Math.min(60, readScore * 2);
            score += Math.min(15, byteArrays.size() * 5);
            score += Math.min(15, offsets.size() * 5);
            score += Math.min(5, relation.directSubtypes().size() * 2);
            score += Math.min(5, descendantReadClasses.size() * 2);

            candidates.add(new BufferFamilyCandidate(
                    owner,
                    reads,
                    byteArrays,
                    offsets,
                    relation.directSubtypes(),
                    descendantReadClasses,
                    score,
                    reasons));
        }

        candidates.sort(Comparator
                .comparingInt(BufferFamilyCandidate::evidenceScore).reversed()
                .thenComparing(BufferFamilyCandidate::classId));
        return new BufferFamilyReport(corpus.sha256(), candidates);
    }
}
