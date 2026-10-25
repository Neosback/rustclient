package dev.neosback.deob.analysis.hierarchy;

import dev.neosback.deob.corpus.ClassArtifact;
import dev.neosback.deob.corpus.JarCorpus;
import dev.neosback.deob.identity.ClassId;
import dev.neosback.deob.identity.MethodId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

/** Builds virtual dispatch families without renaming or mutating any bytecode. */
public final class VirtualMethodFamilies {
    private VirtualMethodFamilies() {
    }

    public static List<VirtualMethodFamily> analyze(JarCorpus corpus, ClassHierarchy hierarchy) {
        Map<MethodId, MethodId> parent = new HashMap<>();
        Map<String, MethodId> declarations = new HashMap<>();

        for (ClassArtifact artifact : corpus.classes().values()) {
            ClassNode node = artifact.readNode();
            ClassId owner = new ClassId(node.name);
            for (MethodNode method : node.methods) {
                if (!eligible(method)) {
                    continue;
                }
                MethodId id = new MethodId(owner, method.name, method.desc);
                parent.put(id, id);
                declarations.put(signatureKey(owner, method.name, method.desc), id);
            }
        }

        for (MethodId method : List.copyOf(parent.keySet())) {
            for (ClassId ancestor : hierarchy.ancestors(method.owner())) {
                MethodId overridden = declarations.get(signatureKey(
                        ancestor,
                        method.name(),
                        method.descriptor()));
                if (overridden != null) {
                    union(parent, method, overridden);
                }
            }
        }

        Map<MethodId, List<MethodId>> grouped = new TreeMap<>();
        for (MethodId method : parent.keySet()) {
            MethodId root = find(parent, method);
            grouped.computeIfAbsent(root, ignored -> new ArrayList<>()).add(method);
        }

        List<VirtualMethodFamily> result = new ArrayList<>();
        for (List<MethodId> members : grouped.values()) {
            Collections.sort(members);
            MethodId first = members.get(0);
            result.add(new VirtualMethodFamily(first.stableKey(), members));
        }
        result.sort((left, right) -> left.familyKey().compareTo(right.familyKey()));
        return List.copyOf(result);
    }

    private static boolean eligible(MethodNode method) {
        if (method.name.startsWith("<")) {
            return false;
        }
        return (method.access & (Opcodes.ACC_STATIC | Opcodes.ACC_PRIVATE)) == 0;
    }

    private static String signatureKey(ClassId owner, String name, String descriptor) {
        return owner.internalName() + '\0' + name + '\0' + descriptor;
    }

    private static MethodId find(Map<MethodId, MethodId> parent, MethodId value) {
        MethodId current = value;
        while (!parent.get(current).equals(current)) {
            current = parent.get(current);
        }
        MethodId root = current;
        current = value;
        while (!parent.get(current).equals(current)) {
            MethodId next = parent.get(current);
            parent.put(current, root);
            current = next;
        }
        return root;
    }

    private static void union(Map<MethodId, MethodId> parent, MethodId left, MethodId right) {
        MethodId leftRoot = find(parent, left);
        MethodId rightRoot = find(parent, right);
        if (leftRoot.equals(rightRoot)) {
            return;
        }
        MethodId winner = leftRoot.compareTo(rightRoot) <= 0 ? leftRoot : rightRoot;
        MethodId loser = winner.equals(leftRoot) ? rightRoot : leftRoot;
        parent.put(loser, winner);
    }
}
