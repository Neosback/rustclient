package dev.neosback.deob.analysis.protocol;

import dev.neosback.deob.corpus.ClassArtifact;
import dev.neosback.deob.corpus.JarCorpus;
import dev.neosback.deob.identity.ClassId;
import dev.neosback.deob.identity.FieldId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;

/** Correlates byte-buffer, bit-index, and object-state evidence without promoting semantic names. */
public final class BufferInfrastructureAnalyzer {
    public BufferInfrastructureReport analyze(JarCorpus corpus) {
        BufferFamilyReport families = new BufferFamilyAnalyzer().analyze(corpus);
        BitAccessReport bits = new BitAccessAnalyzer().analyze(corpus);

        TreeSet<ClassId> packetBuffers = new TreeSet<>();
        TreeSet<FieldId> offsets = new TreeSet<>();
        TreeSet<FieldId> bitIndexes = new TreeSet<>();
        for (BufferFamilyCandidate family : families.candidates()) offsets.addAll(family.offsetFields());
        for (BitAccessCandidate candidate : bits.candidates()) {
            packetBuffers.add(candidate.method().owner());
            bitIndexes.addAll(candidate.integerStateFields());
        }

        TreeSet<FieldId> cipherState = new TreeSet<>();
        for (ClassId owner : packetBuffers) {
            ClassArtifact artifact = corpus.classes().get(owner);
            if (artifact == null) continue;
            ClassNode node = artifact.readNode();
            for (FieldNode field : node.fields) {
                if ((field.access & Opcodes.ACC_STATIC) != 0) continue;
                if (!field.desc.startsWith("L") && !field.desc.startsWith("[L")) continue;
                cipherState.add(new FieldId(owner, field.name, field.desc));
            }
        }

        List<String> warnings = new ArrayList<>();
        if (packetBuffers.isEmpty()) warnings.add("No bit-access owners found; PacketBuffer role remains unresolved.");
        if (cipherState.isEmpty()) warnings.add("No object-state fields found on bit-access owners; do not infer an ISAAC field.");

        List<BufferFamilyCandidate> ranked = families.candidates().stream()
            .sorted(Comparator.comparingInt(BufferFamilyCandidate::evidenceScore).reversed())
            .toList();
        return new BufferInfrastructureReport(
            corpus.sha256(), ranked, List.copyOf(packetBuffers), List.copyOf(offsets),
            List.copyOf(bitIndexes), List.copyOf(cipherState), warnings);
    }
}
