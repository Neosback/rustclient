package dev.neosback.deob.analysis.injected;

import dev.neosback.deob.identity.ClassId;
import dev.neosback.deob.identity.FieldId;
import dev.neosback.deob.identity.InstructionId;
import dev.neosback.deob.identity.MethodId;
import java.util.List;

/** Exact small-shape bridge from a RuneLite-style injected API method to one game member. */
public record InjectedApiBridge(
        MethodId injectedMethod,
        List<ClassId> rsInterfaces,
        String semanticName,
        InjectedBridgeKind kind,
        FieldId targetField,
        MethodId targetMethod,
        Long arithmeticConstant,
        boolean staticTarget,
        List<InstructionId> evidenceInstructions) {

    public InjectedApiBridge {
        rsInterfaces = List.copyOf(rsInterfaces);
        evidenceInstructions = List.copyOf(evidenceInstructions);
    }
}
