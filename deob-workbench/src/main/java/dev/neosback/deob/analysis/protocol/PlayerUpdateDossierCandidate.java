package dev.neosback.deob.analysis.protocol;

import dev.neosback.deob.analysis.packet.PacketReadShape;
import dev.neosback.deob.identity.InstructionId;
import dev.neosback.deob.identity.MethodId;
import java.util.List;
import java.util.Set;

/** Structural dossier for a method that may implement player synchronization/update semantics. */
public record PlayerUpdateDossierCandidate(
        MethodId method,
        int hypothesisScore,
        Set<MethodId> readMethods,
        Set<PacketReadShape> readShapes,
        List<InstructionId> fieldWriteSites,
        List<InstructionId> branchSites,
        List<InstructionId> callSites,
        int naturalLoopCount,
        List<String> notes) {

    public PlayerUpdateDossierCandidate {
        readMethods = Set.copyOf(readMethods);
        readShapes = Set.copyOf(readShapes);
        fieldWriteSites = List.copyOf(fieldWriteSites);
        branchSites = List.copyOf(branchSites);
        callSites = List.copyOf(callSites);
        notes = List.copyOf(notes);
    }
}
