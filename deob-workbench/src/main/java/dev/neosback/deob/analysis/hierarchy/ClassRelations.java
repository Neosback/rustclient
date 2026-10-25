package dev.neosback.deob.analysis.hierarchy;

import dev.neosback.deob.identity.ClassId;
import java.util.List;
import java.util.Set;

/** Direct hierarchy facts for one class in the loaded corpus. */
public record ClassRelations(
        ClassId id,
        ClassId superClass,
        List<ClassId> interfaces,
        Set<ClassId> directSubtypes) {

    public ClassRelations {
        interfaces = List.copyOf(interfaces);
        directSubtypes = Set.copyOf(directSubtypes);
    }
}
