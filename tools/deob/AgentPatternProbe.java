import dev.neosback.deob.bytecode.InstructionIndex;
import dev.neosback.deob.corpus.JarCorpus;
import dev.neosback.deob.io.JsonSupport;
import java.nio.file.Path;
import java.util.*;
import org.objectweb.asm.Handle;
import org.objectweb.asm.tree.*;

/** Read-only search inspired by the supplied runelite-agent. Never loads or transforms client classes. */
public class AgentPatternProbe {
    private static final String PLATFORM = "(IZIIIIIZIIIILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IIIILjava/lang/String;Ljava/lang/String;[IILjava/lang/String;Ljava/lang/String;Ljava/lang/String;)V";

    public static void main(String[] args) throws Exception {
        var corpus = JarCorpus.load(Path.of(args[0]));
        List<Map<String, Object>> hits = new ArrayList<>();
        for (var artifact : corpus.classes().values()) {
            var cls = artifact.readNode();
            for (var method : cls.methods) {
                var index = InstructionIndex.of(method);
                boolean actorGetter = cls.interfaces.contains("net/runelite/api/Actor")
                    && method.name.equals("getAnimation") && method.desc.equals("()I");
                boolean menuBridge = cls.name.equals("client") && Set.of("menuAction", "openWorldHopper", "hopToWorld").contains(method.name);
                if (actorGetter) add(hits, cls, method, -1, "actor-animation-api", "API accessor; inspect every field and branch before naming");
                if (menuBridge) add(hits, cls, method, -1, "menu-api-entry", "API entry; calls below are candidates, not automatic menu-handler names");
                if (method.name.equals("<init>") && method.desc.equals(PLATFORM))
                    add(hits, cls, method, -1, "platform-constructor-signature", "Exact supplied-agent descriptor");
                if (method.name.equals("focusLost")) add(hits, cls, method, -1, "focus-name", "Name match only");
                for (int i = 0; i < index.size(); i++) {
                    var insn = index.instruction(i);
                    if (actorGetter && insn instanceof FieldInsnNode field)
                        add(hits, cls, method, i, "actor-animation-field", field.owner + "." + field.name + ":" + field.desc);
                    if (actorGetter && insn instanceof LdcInsnNode ldc && ldc.cst instanceof Number)
                        add(hits, cls, method, i, "actor-animation-numeric-literal", ldc.cst.toString());
                    if (insn instanceof MethodInsnNode call) {
                        String target = call.owner + "." + call.name + call.desc;
                        if (menuBridge) add(hits, cls, method, i, "menu-api-call", target);
                        if (call.owner.equals("java/lang/management/RuntimeMXBean") && call.name.equals("getInputArguments"))
                            add(hits, cls, method, i, "runtime-arguments-call", target);
                        if (call.name.equals("initRLICN")) add(hits, cls, method, i, "mouse-init-call", target);
                    }
                    if (insn instanceof InvokeDynamicInsnNode dynamic) {
                        for (Object value : dynamic.bsmArgs) {
                            if (value instanceof Handle handle && handle.getDesc().equals("(Ljava/awt/event/HierarchyEvent;)V"))
                                add(hits, cls, method, i, "hierarchy-event-handle", handle.getOwner() + "." + handle.getName() + handle.getDesc());
                        }
                    }
                    if (insn instanceof LdcInsnNode ldc && ldc.cst instanceof String value) {
                        if (Set.of("wmic csproduct get UUID", "system_profiler SPHardwareDataType | awk '/UUID/ { print $3; }'", "cat /etc/machine-id").contains(value))
                            add(hits, cls, method, i, "device-command-literal", value);
                        if (value.equals(".java:") || value.equals("clienterror.ws?cv="))
                            add(hits, cls, method, i, "error-report-literal", value);
                    }
                }
            }
        }
        JsonSupport.write(Path.of(args[1]), Map.of("corpusSha256", corpus.sha256(), "hits", hits,
            "policy", "Read-only pattern candidates. No agent execution, transformation, automatic mapping, or vanilla reconstruction."));
        System.out.println("agent-inspired exact-bytecode hits: " + hits.size());
    }

    private static void add(List<Map<String, Object>> hits, ClassNode cls, MethodNode method, int index, String pattern, String evidence) {
        hits.add(Map.of("owner", cls.name, "name", method.name, "descriptor", method.desc,
            "opcodeIndex", index, "pattern", pattern, "evidence", evidence));
    }
}
