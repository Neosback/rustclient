package dev.neosback.deob.analysis.hooks;

import dev.neosback.deob.analysis.protocol.BufferFamilyCandidate;
import dev.neosback.deob.analysis.protocol.BufferInfrastructureAnalyzer;
import dev.neosback.deob.analysis.protocol.BufferInfrastructureReport;
import dev.neosback.deob.corpus.JarCorpus;
import dev.neosback.deob.identity.ClassId;
import dev.neosback.deob.identity.FieldId;
import java.util.ArrayList;
import java.util.List;

/** Produces candidate exact-name hooks for a future runtime reflection layer. */
public final class ReflectionHookDeriver {
    public ReflectionHookManifest deriveBufferHooks(JarCorpus corpus) {
        BufferInfrastructureReport infra = new BufferInfrastructureAnalyzer().analyze(corpus);
        List<ReflectionHookSpec> hooks = new ArrayList<>();
        List<String> warnings = new ArrayList<>(infra.warnings());

        if (!infra.bufferFamilies().isEmpty()) {
            BufferFamilyCandidate family = infra.bufferFamilies().get(0);
            hooks.add(classHook("Buffer.class", family.classId(), List.of("top structural buffer-family candidate")));
            for (FieldId field : family.offsetFields()) {
                hooks.add(fieldHook("Buffer.offset", field, List.of("offset-like mutable field in top buffer family")));
            }
        }
        for (ClassId owner : infra.packetBufferClassCandidates()) {
            hooks.add(classHook("PacketBuffer.class", owner, List.of("owner of bit-oriented buffer access")));
        }
        for (FieldId field : infra.bitIndexFieldCandidates()) {
            hooks.add(fieldHook("PacketBuffer.bitIndex", field, List.of("integer state used by bit-oriented access")));
        }
        for (FieldId field : infra.cipherStateFieldCandidates()) {
            hooks.add(fieldHook("PacketBuffer.cipherState", field, List.of("object state on bit-access owner; ISAAC role unproven")));
        }
        if (hooks.isEmpty()) warnings.add("No reflection hooks derived; runtime reflection must not guess members.");
        return new ReflectionHookManifest(corpus.sha256(), null, false, hooks, warnings);
    }

    private static ReflectionHookSpec classHook(String logical, ClassId id, List<String> evidence) {
        return new ReflectionHookSpec(logical, ReflectionHookKind.CLASS, id.internalName(), "", "", false, false, "CANDIDATE", evidence);
    }

    private static ReflectionHookSpec fieldHook(String logical, FieldId id, List<String> evidence) {
        return new ReflectionHookSpec(logical, ReflectionHookKind.FIELD, id.owner().internalName(), id.name(), id.descriptor(), false, true, "CANDIDATE", evidence);
    }
}
