package dev.neosback.deob.analysis.protocol;

import dev.neosback.deob.identity.ClassId;
import dev.neosback.deob.identity.FieldId;
import java.util.List;

/** Structural infrastructure candidates only; semantic role names remain hypotheses. */
public record BufferInfrastructureReport(
    String corpusSha256,
    List<BufferFamilyCandidate> bufferFamilies,
    List<ClassId> packetBufferClassCandidates,
    List<FieldId> offsetFieldCandidates,
    List<FieldId> bitIndexFieldCandidates,
    List<FieldId> cipherStateFieldCandidates,
    List<String> warnings
) {
    public BufferInfrastructureReport {
        bufferFamilies = List.copyOf(bufferFamilies);
        packetBufferClassCandidates = List.copyOf(packetBufferClassCandidates);
        offsetFieldCandidates = List.copyOf(offsetFieldCandidates);
        bitIndexFieldCandidates = List.copyOf(bitIndexFieldCandidates);
        cipherStateFieldCandidates = List.copyOf(cipherStateFieldCandidates);
        warnings = List.copyOf(warnings);
    }
}
