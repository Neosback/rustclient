package dev.neosback.deob.bytecode;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodNode;

/**
 * Deterministic index over real JVM instructions. ASM labels, frames, and line-number nodes do not
 * consume an index. Branch labels resolve to the first executable instruction at or after the label.
 */
public final class InstructionIndex {
    private final List<AbstractInsnNode> instructions;
    private final Map<AbstractInsnNode, Integer> indexByNode;
    private final Map<LabelNode, Integer> targetByLabel;

    private InstructionIndex(
        List<AbstractInsnNode> instructions,
        Map<AbstractInsnNode, Integer> indexByNode,
        Map<LabelNode, Integer> targetByLabel
    ) {
        this.instructions = List.copyOf(instructions);
        this.indexByNode = indexByNode;
        this.targetByLabel = targetByLabel;
    }

    public static InstructionIndex of(MethodNode method) {
        List<AbstractInsnNode> executable = new ArrayList<>();
        Map<AbstractInsnNode, Integer> indexByNode = new IdentityHashMap<>();

        for (AbstractInsnNode node = method.instructions.getFirst(); node != null; node = node.getNext()) {
            if (node.getOpcode() >= 0) {
                indexByNode.put(node, executable.size());
                executable.add(node);
            }
        }

        Map<LabelNode, Integer> targetByLabel = new IdentityHashMap<>();
        for (AbstractInsnNode node = method.instructions.getFirst(); node != null; node = node.getNext()) {
            if (node instanceof LabelNode label) {
                targetByLabel.put(label, findNextExecutableIndex(label, indexByNode));
            }
        }

        return new InstructionIndex(executable, indexByNode, targetByLabel);
    }

    private static int findNextExecutableIndex(
        AbstractInsnNode start,
        Map<AbstractInsnNode, Integer> indexByNode
    ) {
        for (AbstractInsnNode node = start; node != null; node = node.getNext()) {
            Integer index = indexByNode.get(node);
            if (index != null) {
                return index;
            }
        }
        return -1;
    }

    public int size() {
        return instructions.size();
    }

    public List<AbstractInsnNode> instructions() {
        return instructions;
    }

    public AbstractInsnNode instruction(int index) {
        return instructions.get(index);
    }

    public int indexOf(AbstractInsnNode node) {
        Integer index = indexByNode.get(node);
        if (index == null) {
            throw new IllegalArgumentException("Node is not an executable instruction in this method");
        }
        return index;
    }

    public int targetIndex(LabelNode label) {
        Integer index = targetByLabel.get(label);
        if (index == null) {
            throw new IllegalArgumentException("Label does not belong to this method");
        }
        return index;
    }
}
