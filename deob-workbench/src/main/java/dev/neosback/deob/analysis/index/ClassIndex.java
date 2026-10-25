package dev.neosback.deob.analysis.index;

import dev.neosback.deob.identity.ClassId;
import java.util.List;

public record ClassIndex(
    ClassId id,
    int access,
    String superName,
    List<String> interfaces,
    List<FieldIndex> fields,
    List<MethodIndex> methods
) {
    public ClassIndex {
        interfaces = List.copyOf(interfaces);
        fields = List.copyOf(fields);
        methods = List.copyOf(methods);
    }
}
