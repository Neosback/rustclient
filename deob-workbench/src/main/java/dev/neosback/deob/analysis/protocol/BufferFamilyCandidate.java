package dev.neosback.deob.analysis.protocol;

import dev.neosback.deob.analysis.packet.PacketReadCandidate;
import dev.neosback.deob.identity.ClassId;
import dev.neosback.deob.identity.FieldId;
import java.util.List;
import java.util.Set;

/** Ranked structural evidence that a class participates in a byte-buffer family. */
public record BufferFamilyCandidate(
        ClassId classId,
        List<PacketReadCandidate> readMethods,
        Set<FieldId> byteArrayFields,
        Set<FieldId> offsetFields,
        Set<ClassId> directSubtypes,
        Set<ClassId> descendantReadClasses,
        int evidenceScore,
        List<String> reasons) {

    public BufferFamilyCandidate {
        readMethods = List.copyOf(readMethods);
        byteArrayFields = Set.copyOf(byteArrayFields);
        offsetFields = Set.copyOf(offsetFields);
        directSubtypes = Set.copyOf(directSubtypes);
        descendantReadClasses = Set.copyOf(descendantReadClasses);
        reasons = List.copyOf(reasons);
    }
}
