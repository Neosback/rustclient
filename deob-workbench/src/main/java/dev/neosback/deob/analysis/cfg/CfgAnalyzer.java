package dev.neosback.deob.analysis.cfg;

import dev.neosback.deob.bytecode.InstructionIndex;
import dev.neosback.deob.identity.MethodId;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LookupSwitchInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TableSwitchInsnNode;
import org.objectweb.asm.tree.TryCatchBlockNode;

/**
 * Builds a non-destructive CFG directly from the original ASM method tree. Edges retain branch,
 * switch, and exception provenance. A `backward` edge is lexical (target instruction <= source
 * instruction), not a dominator-proven loop edge.
 */
public final class CfgAnalyzer {
    public ControlFlowGraph analyze(MethodId methodId, MethodNode method) {
        InstructionIndex instructions = InstructionIndex.of(method);
        if (instructions.size() == 0) {
            return new ControlFlowGraph(methodId, 0, List.of(), List.of());
        }

        TreeSet<Integer> leaders = discoverLeaders(method, instructions);
        List<BasicBlock> blocks = createBlocks(leaders, instructions.size());
        int[] blockByInstruction = mapInstructionsToBlocks(blocks, instructions.size());
        List<ControlFlowEdge> edges = new ArrayList<>();

        addNormalEdges(instructions, blocks, blockByInstruction, edges);
        addExceptionEdges(method, instructions, blocks, blockByInstruction, edges);

        return new ControlFlowGraph(methodId, instructions.size(), blocks, deduplicate(edges));
    }

    private TreeSet<Integer> discoverLeaders(MethodNode method, InstructionIndex instructions) {
        TreeSet<Integer> leaders = new TreeSet<>();
        leaders.add(0);

        for (int index = 0; index < instructions.size(); index++) {
            AbstractInsnNode instruction = instructions.instruction(index);
            if (instruction instanceof JumpInsnNode jump) {
                addTarget(leaders, instructions.targetIndex(jump.label), instructions.size());
                addFollowingLeader(leaders, index, instructions.size());
            } else if (instruction instanceof TableSwitchInsnNode tableSwitch) {
                addTarget(leaders, instructions.targetIndex(tableSwitch.dflt), instructions.size());
                for (var label : tableSwitch.labels) {
                    addTarget(leaders, instructions.targetIndex(label), instructions.size());
                }
                addFollowingLeader(leaders, index, instructions.size());
            } else if (instruction instanceof LookupSwitchInsnNode lookupSwitch) {
                addTarget(leaders, instructions.targetIndex(lookupSwitch.dflt), instructions.size());
                for (var label : lookupSwitch.labels) {
                    addTarget(leaders, instructions.targetIndex(label), instructions.size());
                }
                addFollowingLeader(leaders, index, instructions.size());
            } else if (isMethodExit(instruction.getOpcode())) {
                addFollowingLeader(leaders, index, instructions.size());
            }
        }

        if (method.tryCatchBlocks != null) {
            for (TryCatchBlockNode tryCatch : method.tryCatchBlocks) {
                addTarget(leaders, instructions.targetIndex(tryCatch.start), instructions.size());
                addTarget(leaders, instructions.targetIndex(tryCatch.end), instructions.size());
                addTarget(leaders, instructions.targetIndex(tryCatch.handler), instructions.size());
            }
        }
        return leaders;
    }

    private static void addFollowingLeader(Set<Integer> leaders, int index, int instructionCount) {
        if (index + 1 < instructionCount) {
            leaders.add(index + 1);
        }
    }

    private static void addTarget(Set<Integer> leaders, int target, int instructionCount) {
        if (target >= 0 && target < instructionCount) {
            leaders.add(target);
        }
    }

    private static List<BasicBlock> createBlocks(TreeSet<Integer> leaders, int instructionCount) {
        List<Integer> ordered = new ArrayList<>(leaders);
        List<BasicBlock> blocks = new ArrayList<>(ordered.size());
        for (int i = 0; i < ordered.size(); i++) {
            int start = ordered.get(i);
            int end = i + 1 < ordered.size() ? ordered.get(i + 1) : instructionCount;
            if (start < end) {
                blocks.add(new BasicBlock(blocks.size(), start, end));
            }
        }
        return blocks;
    }

    private static int[] mapInstructionsToBlocks(List<BasicBlock> blocks, int instructionCount) {
        int[] mapping = new int[instructionCount];
        for (BasicBlock block : blocks) {
            for (int index = block.startInstruction(); index < block.endInstructionExclusive(); index++) {
                mapping[index] = block.id();
            }
        }
        return mapping;
    }

    private void addNormalEdges(
        InstructionIndex instructions,
        List<BasicBlock> blocks,
        int[] blockByInstruction,
        List<ControlFlowEdge> edges
    ) {
        for (BasicBlock block : blocks) {
            int lastIndex = block.endInstructionExclusive() - 1;
            AbstractInsnNode terminal = instructions.instruction(lastIndex);

            if (terminal instanceof JumpInsnNode jump) {
                int target = instructions.targetIndex(jump.label);
                if (target >= 0) {
                    EdgeKind kind = switch (terminal.getOpcode()) {
                        case Opcodes.GOTO -> EdgeKind.GOTO;
                        case Opcodes.JSR -> EdgeKind.SUBROUTINE;
                        default -> EdgeKind.CONDITIONAL_BRANCH;
                    };
                    addEdge(block, blockByInstruction[target], kind, "", blocks, edges);
                }
                if (terminal.getOpcode() != Opcodes.GOTO && terminal.getOpcode() != Opcodes.JSR) {
                    addFallthrough(block, blocks, edges);
                }
                continue;
            }

            if (terminal instanceof TableSwitchInsnNode tableSwitch) {
                int defaultTarget = instructions.targetIndex(tableSwitch.dflt);
                if (defaultTarget >= 0) {
                    addEdge(block, blockByInstruction[defaultTarget], EdgeKind.SWITCH_DEFAULT, "default", blocks, edges);
                }
                for (int i = 0; i < tableSwitch.labels.size(); i++) {
                    int target = instructions.targetIndex(tableSwitch.labels.get(i));
                    if (target >= 0) {
                        int key = tableSwitch.min + i;
                        addEdge(block, blockByInstruction[target], EdgeKind.SWITCH_CASE, Integer.toString(key), blocks, edges);
                    }
                }
                continue;
            }

            if (terminal instanceof LookupSwitchInsnNode lookupSwitch) {
                int defaultTarget = instructions.targetIndex(lookupSwitch.dflt);
                if (defaultTarget >= 0) {
                    addEdge(block, blockByInstruction[defaultTarget], EdgeKind.SWITCH_DEFAULT, "default", blocks, edges);
                }
                for (int i = 0; i < lookupSwitch.labels.size(); i++) {
                    int target = instructions.targetIndex(lookupSwitch.labels.get(i));
                    if (target >= 0) {
                        addEdge(
                            block,
                            blockByInstruction[target],
                            EdgeKind.SWITCH_CASE,
                            Integer.toString(lookupSwitch.keys.get(i)),
                            blocks,
                            edges
                        );
                    }
                }
                continue;
            }

            if (!isMethodExit(terminal.getOpcode()) && terminal.getOpcode() != Opcodes.RET) {
                addFallthrough(block, blocks, edges);
            }
        }
    }

    private void addExceptionEdges(
        MethodNode method,
        InstructionIndex instructions,
        List<BasicBlock> blocks,
        int[] blockByInstruction,
        List<ControlFlowEdge> edges
    ) {
        if (method.tryCatchBlocks == null) {
            return;
        }
        for (TryCatchBlockNode tryCatch : method.tryCatchBlocks) {
            int start = instructions.targetIndex(tryCatch.start);
            int end = instructions.targetIndex(tryCatch.end);
            int handler = instructions.targetIndex(tryCatch.handler);
            if (start < 0 || handler < 0) {
                continue;
            }
            if (end < 0) {
                end = instructions.size();
            }
            String catchType = tryCatch.type == null ? "<any>" : tryCatch.type;
            int handlerBlock = blockByInstruction[handler];
            for (BasicBlock block : blocks) {
                if (block.intersects(start, end)) {
                    addEdge(block, handlerBlock, EdgeKind.EXCEPTION, catchType, blocks, edges);
                }
            }
        }
    }

    private static void addFallthrough(
        BasicBlock block,
        List<BasicBlock> blocks,
        List<ControlFlowEdge> edges
    ) {
        int nextId = block.id() + 1;
        if (nextId < blocks.size()) {
            addEdge(block, nextId, EdgeKind.FALLTHROUGH, "", blocks, edges);
        }
    }

    private static void addEdge(
        BasicBlock from,
        int toBlock,
        EdgeKind kind,
        String detail,
        List<BasicBlock> blocks,
        List<ControlFlowEdge> edges
    ) {
        BasicBlock to = blocks.get(toBlock);
        boolean backward = to.startInstruction() <= from.startInstruction();
        edges.add(new ControlFlowEdge(from.id(), toBlock, kind, detail, backward));
    }

    private static boolean isMethodExit(int opcode) {
        return opcode == Opcodes.IRETURN
            || opcode == Opcodes.LRETURN
            || opcode == Opcodes.FRETURN
            || opcode == Opcodes.DRETURN
            || opcode == Opcodes.ARETURN
            || opcode == Opcodes.RETURN
            || opcode == Opcodes.ATHROW;
    }

    private static List<ControlFlowEdge> deduplicate(List<ControlFlowEdge> edges) {
        return List.copyOf(new LinkedHashSet<>(edges));
    }
}
