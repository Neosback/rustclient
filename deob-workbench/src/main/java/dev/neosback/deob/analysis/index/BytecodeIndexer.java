package dev.neosback.deob.analysis.index;

import dev.neosback.deob.bytecode.InstructionIndex;
import dev.neosback.deob.corpus.ClassArtifact;
import dev.neosback.deob.corpus.JarCorpus;
import dev.neosback.deob.identity.ClassId;
import dev.neosback.deob.identity.FieldId;
import dev.neosback.deob.identity.MethodId;
import java.util.ArrayList;
import java.util.List;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InvokeDynamicInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LookupSwitchInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TableSwitchInsnNode;

public final class BytecodeIndexer {
    public CorpusIndex index(JarCorpus corpus) {
        List<ClassIndex> classes = new ArrayList<>();
        for (ClassArtifact artifact : corpus.classes().values()) {
            classes.add(indexClass(artifact));
        }
        return new CorpusIndex(corpus.source().toString(), corpus.sha256(), classes);
    }

    private ClassIndex indexClass(ClassArtifact artifact) {
        ClassNode node = artifact.readNode();
        ClassId classId = artifact.id();

        List<FieldIndex> fields = new ArrayList<>();
        for (FieldNode field : node.fields) {
            fields.add(new FieldIndex(
                new FieldId(classId, field.name, field.desc),
                field.access,
                field.value
            ));
        }

        List<MethodIndex> methods = new ArrayList<>();
        for (MethodNode method : node.methods) {
            methods.add(indexMethod(classId, method));
        }

        return new ClassIndex(
            classId,
            node.access,
            node.superName,
            node.interfaces == null ? List.of() : List.copyOf(node.interfaces),
            fields,
            methods
        );
    }

    private MethodIndex indexMethod(ClassId owner, MethodNode method) {
        InstructionIndex instructionIndex = InstructionIndex.of(method);
        int branches = 0;
        int switches = 0;
        int invokes = 0;
        int fieldAccesses = 0;

        for (AbstractInsnNode instruction : instructionIndex.instructions()) {
            if (instruction instanceof JumpInsnNode) {
                branches++;
            }
            if (instruction instanceof TableSwitchInsnNode || instruction instanceof LookupSwitchInsnNode) {
                switches++;
            }
            if (instruction instanceof MethodInsnNode || instruction instanceof InvokeDynamicInsnNode) {
                invokes++;
            }
            if (instruction instanceof FieldInsnNode) {
                fieldAccesses++;
            }
        }

        MethodFingerprints.Result fingerprints = MethodFingerprints.compute(method);
        return new MethodIndex(
            new MethodId(owner, method.name, method.desc),
            method.access,
            instructionIndex.size(),
            branches,
            switches,
            method.tryCatchBlocks == null ? 0 : method.tryCatchBlocks.size(),
            invokes,
            fieldAccesses,
            fingerprints.exactSha256(),
            fingerprints.structuralSha256()
        );
    }
}
