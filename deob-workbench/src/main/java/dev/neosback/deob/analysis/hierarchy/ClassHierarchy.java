package dev.neosback.deob.analysis.hierarchy;

import dev.neosback.deob.corpus.ClassArtifact;
import dev.neosback.deob.corpus.JarCorpus;
import dev.neosback.deob.identity.ClassId;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import org.objectweb.asm.tree.ClassNode;

/** Immutable hierarchy index derived only from class headers in one exact corpus. */
public final class ClassHierarchy {
    private final NavigableMap<ClassId, ClassRelations> relations;

    private ClassHierarchy(NavigableMap<ClassId, ClassRelations> relations) {
        this.relations = Collections.unmodifiableNavigableMap(new TreeMap<>(relations));
    }

    public static ClassHierarchy analyze(JarCorpus corpus) {
        NavigableMap<ClassId, MutableRelations> mutable = new TreeMap<>();
        for (ClassArtifact artifact : corpus.classes().values()) {
            ClassNode node = artifact.readNode();
            ClassId id = new ClassId(node.name);
            ClassId superClass = node.superName == null ? null : new ClassId(node.superName);
            List<ClassId> interfaces = new ArrayList<>();
            for (String iface : node.interfaces) {
                interfaces.add(new ClassId(iface));
            }
            Collections.sort(interfaces);
            mutable.put(id, new MutableRelations(id, superClass, interfaces));
        }

        for (MutableRelations entry : mutable.values()) {
            if (entry.superClass != null) {
                MutableRelations parent = mutable.get(entry.superClass);
                if (parent != null) {
                    parent.directSubtypes.add(entry.id);
                }
            }
            for (ClassId iface : entry.interfaces) {
                MutableRelations parent = mutable.get(iface);
                if (parent != null) {
                    parent.directSubtypes.add(entry.id);
                }
            }
        }

        NavigableMap<ClassId, ClassRelations> frozen = new TreeMap<>();
        for (MutableRelations entry : mutable.values()) {
            frozen.put(entry.id, new ClassRelations(
                    entry.id,
                    entry.superClass,
                    entry.interfaces,
                    entry.directSubtypes));
        }
        return new ClassHierarchy(frozen);
    }

    public NavigableMap<ClassId, ClassRelations> relations() {
        return relations;
    }

    public ClassRelations require(ClassId id) {
        ClassRelations relation = relations.get(id);
        if (relation == null) {
            throw new IllegalArgumentException("Class is outside the loaded corpus: " + id);
        }
        return relation;
    }

    /** Returns in-corpus superclasses and interfaces in deterministic breadth-first order. */
    public List<ClassId> ancestors(ClassId id) {
        ClassRelations start = require(id);
        Set<ClassId> seen = new LinkedHashSet<>();
        Deque<ClassId> queue = new ArrayDeque<>();
        if (start.superClass() != null && relations.containsKey(start.superClass())) {
            queue.add(start.superClass());
        }
        for (ClassId iface : start.interfaces()) {
            if (relations.containsKey(iface)) {
                queue.add(iface);
            }
        }
        while (!queue.isEmpty()) {
            ClassId next = queue.removeFirst();
            if (!seen.add(next)) {
                continue;
            }
            ClassRelations relation = relations.get(next);
            if (relation == null) {
                continue;
            }
            if (relation.superClass() != null && relations.containsKey(relation.superClass())) {
                queue.addLast(relation.superClass());
            }
            for (ClassId iface : relation.interfaces()) {
                if (relations.containsKey(iface)) {
                    queue.addLast(iface);
                }
            }
        }
        return List.copyOf(seen);
    }

    public Set<ClassId> descendants(ClassId id) {
        require(id);
        Set<ClassId> seen = new TreeSet<>();
        Deque<ClassId> queue = new ArrayDeque<>();
        queue.add(id);
        while (!queue.isEmpty()) {
            ClassId current = queue.removeFirst();
            for (ClassId child : require(current).directSubtypes()) {
                if (seen.add(child)) {
                    queue.addLast(child);
                }
            }
        }
        return Collections.unmodifiableSet(seen);
    }

    private static final class MutableRelations {
        private final ClassId id;
        private final ClassId superClass;
        private final List<ClassId> interfaces;
        private final Set<ClassId> directSubtypes = new TreeSet<>();

        private MutableRelations(ClassId id, ClassId superClass, List<ClassId> interfaces) {
            this.id = id;
            this.superClass = superClass;
            this.interfaces = List.copyOf(interfaces);
        }
    }
}
