package dev.neosback.deob.analysis.injected;

import dev.neosback.deob.identity.ClassId;
import dev.neosback.deob.identity.MethodId;
import java.util.List;

/** Exact single-call semantic anchor from an injected API method to one game method. */
public record InjectedMethodAnchor(
        MethodId targetMethod,
        String semanticTerm,
        MethodId injectedMethod,
        List<ClassId> rsInterfaces,
        boolean staticTarget,
        String confidence) {

    public InjectedMethodAnchor {
        rsInterfaces = List.copyOf(rsInterfaces);
    }
}
