package dev.neosback.deob.analysis.protocol;

import java.util.List;

public record UpdateMaskReport(
        String jarSha256,
        String packetProfileRevision,
        List<UpdateMaskBitEvidence> maskTests,
        List<ActorStateFieldCandidate> actorStateFields) {

    public UpdateMaskReport {
        maskTests = List.copyOf(maskTests);
        actorStateFields = List.copyOf(actorStateFields);
    }
}
