package dev.neosback.deob.analysis.injected;

import dev.neosback.deob.identity.ClassId;
import dev.neosback.deob.identity.FieldId;
import dev.neosback.deob.identity.MethodId;
import java.util.List;

/** Aggregated semantic anchor for one exact field exposed through RuneLite-style injected APIs. */
public record InjectedFieldAnchor(
        FieldId field,
        String semanticTerm,
        List<ClassId> rsInterfaces,
        List<MethodId> getterMethods,
        List<MethodId> setterMethods,
        Long getterDecoder,
        Long setterEncoder,
        boolean modularInverseProven,
        String confidence,
        List<String> evidence) {

    public InjectedFieldAnchor {
        rsInterfaces = List.copyOf(rsInterfaces);
        getterMethods = List.copyOf(getterMethods);
        setterMethods = List.copyOf(setterMethods);
        evidence = List.copyOf(evidence);
    }
}
