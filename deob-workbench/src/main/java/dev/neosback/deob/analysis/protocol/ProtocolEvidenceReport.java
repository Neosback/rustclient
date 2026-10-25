package dev.neosback.deob.analysis.protocol;

import java.util.List;

/** Combined protocol-entry evidence. All roles are hypotheses unless separately proven. */
public record ProtocolEvidenceReport(
        String corpusSha256,
        BufferFamilyReport bufferFamilies,
        List<FramingMethodCandidate> framingCandidates,
        List<PacketHandlerCandidate> packetHandlers,
        List<PlayerUpdateDossierCandidate> playerUpdateCandidates,
        List<String> warnings) {

    public ProtocolEvidenceReport {
        framingCandidates = List.copyOf(framingCandidates);
        packetHandlers = List.copyOf(packetHandlers);
        playerUpdateCandidates = List.copyOf(playerUpdateCandidates);
        warnings = List.copyOf(warnings);
    }
}
