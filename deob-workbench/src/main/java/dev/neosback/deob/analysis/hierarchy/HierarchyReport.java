package dev.neosback.deob.analysis.hierarchy;

import java.util.List;

public record HierarchyReport(
        List<ClassRelations> classes,
        List<VirtualMethodFamily> virtualFamilies) {

    public HierarchyReport {
        classes = List.copyOf(classes);
        virtualFamilies = List.copyOf(virtualFamilies);
    }
}
