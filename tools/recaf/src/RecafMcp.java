package dev.neosback.recaf;

import com.google.gson.*;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.*;
import org.objectweb.asm.util.Textifier;
import org.objectweb.asm.util.TraceMethodVisitor;
import software.coley.recaf.Bootstrap;
import software.coley.recaf.Main;
import software.coley.recaf.info.JvmClassInfo;
import software.coley.recaf.services.decompile.DecompilerManager;
import software.coley.recaf.services.workspace.WorkspaceManager;
import software.coley.recaf.workspace.model.Workspace;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.security.MessageDigest;
import java.util.*;
import java.util.concurrent.TimeUnit;

/** Read-only MCP over Recaf's actual headless workspace and decompiler services. */
public final class RecafMcp {
    private static final Gson JSON = new GsonBuilder().disableHtmlEscaping().create();
    private final Workspace workspace;
    private final DecompilerManager decompilers;
    private final String artifactSha256;

    private RecafMcp(String[] args) throws Exception {
        Path artifact = Path.of(args[0]);
        artifactSha256 = HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(Files.readAllBytes(artifact)));
        Main.main(new String[]{"--headless", "--datadir", args[1], "--input", artifact.toString()});
        workspace = Bootstrap.get().get(WorkspaceManager.class).getCurrent();
        decompilers = Bootstrap.get().get(DecompilerManager.class);
        if (workspace.jvmClassesStream(false).findAny().isEmpty())
            throw new IllegalStateException("Recaf did not load the selected analysis JAR");
    }

    public static void main(String[] args) throws Exception {
        // Reserve the original stdout descriptor for newline-delimited MCP JSON only.
        PrintWriter protocol = new PrintWriter(new OutputStreamWriter(new FileOutputStream(FileDescriptor.out), StandardCharsets.UTF_8), true);
        System.setOut(System.err);
        if (args.length != 2) throw new IllegalArgumentException("expected analysis JAR and isolated data directory");
        RecafMcp server = new RecafMcp(args);
        try (BufferedReader input = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8))) {
            for (String line; (line = input.readLine()) != null;) {
                JsonObject request;
                try {
                    request = JsonParser.parseString(line).getAsJsonObject();
                } catch (RuntimeException error) {
                    protocol.println(JSON.toJson(error(JsonNull.INSTANCE, -32700, "Invalid JSON request")));
                    continue;
                }
                if (!request.has("id")) continue;
                JsonElement id = request.get("id");
                try {
                    if (!request.has("jsonrpc") || !request.get("jsonrpc").getAsString().equals("2.0"))
                        throw new IllegalArgumentException("expected JSON-RPC 2.0");
                    Object result = server.dispatch(request.get("method").getAsString(), request.has("params") ? request.getAsJsonObject("params") : new JsonObject());
                    protocol.println(JSON.toJson(Map.of("jsonrpc", "2.0", "id", id, "result", result)));
                } catch (NoSuchMethodException error) {
                    protocol.println(JSON.toJson(error(id, -32601, error.getMessage())));
                } catch (Exception error) {
                    protocol.println(JSON.toJson(error(id, -32602, error.getMessage() == null ? error.getClass().getSimpleName() : error.getMessage())));
                }
            }
        } finally {
            // Recaf owns executor pools; the MCP child must terminate on client EOF.
            System.exit(0);
        }
    }

    private static Map<String, Object> error(JsonElement id, int code, String message) {
        return Map.of("jsonrpc", "2.0", "id", id, "error", Map.of("code", code, "message", message));
    }

    private Object dispatch(String method, JsonObject params) throws Exception {
        return switch (method) {
            case "initialize" -> Map.of("protocolVersion", "2024-11-05", "capabilities", Map.of("tools", Map.of("listChanged", false)),
                    "serverInfo", Map.of("name", "rustclient-recaf", "version", "0.1.0"));
            case "ping" -> Map.of();
            case "tools/list" -> Map.of("tools", tools());
            case "tools/call" -> {
                String name = params.get("name").getAsString();
                JsonObject args = params.has("arguments") ? params.getAsJsonObject("arguments") : new JsonObject();
                try {
                    Object value = call(name, args);
                    yield Map.of("content", List.of(Map.of("type", "text", "text", JSON.toJson(Map.of("artifactSha256", artifactSha256, "result", value)))), "isError", false);
                } catch (Exception error) {
                    yield Map.of("content", List.of(Map.of("type", "text", "text", error.getMessage() == null ? error.getClass().getSimpleName() : error.getMessage())), "isError", true);
                }
            }
            default -> throw new NoSuchMethodException("Unknown MCP method: " + method);
        };
    }

    private List<Map<String, Object>> tools() {
        Map<String, Object> className = Map.of("type", "string", "description", "Exact JVM internal class name from list_classes");
        Map<String, Object> string = Map.of("type", "string");
        Map<String, Object> nonnegative = Map.of("type", "integer", "minimum", 0);
        Map<String, Object> limit = Map.of("type", "integer", "minimum", 1, "maximum", 500);
        Map<String, Object> chars = Map.of("type", "integer", "minimum", 1, "maximum", 100000);
        return List.of(
            tool("workspace_status", "Describe the fixed Recaf analysis workspace and available decompilers", Map.of(), List.of()),
            tool("list_classes", "Page through exact class names", Map.of("prefix", string, "offset", nonnegative, "limit", limit), List.of()),
            tool("class_outline", "Read class hierarchy and paginated field/method descriptors from bytecode", Map.of("className", className, "fieldsOffset", nonnegative, "methodsOffset", nonnegative, "limit", limit), List.of("className")),
            tool("decompile_class", "Read a derived Java view; compare with bytecode before inferring behavior", Map.of("className", className, "decompiler", string, "offset", nonnegative, "maxChars", chars), List.of("className")),
            tool("method_bytecode", "Read ASM method disassembly from the loaded class bytes", Map.of("className", className, "methodName", string, "descriptor", string, "offset", nonnegative, "maxChars", chars), List.of("className", "methodName", "descriptor")),
            tool("search_strings", "Find bytecode LDC string constants; return exact method and ASM instruction index", Map.of("query", string, "offset", nonnegative, "limit", limit), List.of("query"))
        );
    }

    private static Map<String, Object> tool(String name, String description, Map<String, Object> properties, List<String> required) {
        return Map.of("name", name, "description", description, "inputSchema", Map.of("type", "object", "properties", properties, "required", required, "additionalProperties", false),
            "annotations", Map.of("readOnlyHint", true, "destructiveHint", false, "idempotentHint", true, "openWorldHint", false));
    }

    private JvmClassInfo info(JsonObject args) {
        var node = workspace.findJvmClass(false, required(args, "className"));
        if (node == null) throw new IllegalArgumentException("Class not found");
        return node.getValue().asJvmClass();
    }

    private static ClassNode node(JvmClassInfo info) {
        ClassNode node = new ClassNode();
        new ClassReader(info.getBytecode()).accept(node, 0);
        return node;
    }

    private Object call(String name, JsonObject args) throws Exception {
        return switch (name) {
            case "workspace_status" -> Map.of("classCount", workspace.jvmClassesStream(false).count(), "mode", "read-only derived copy",
                "decompilers", decompilers.getJvmDecompilers().stream().map(d -> Map.of("name", d.getName(), "version", d.getVersion())).toList());
            case "list_classes" -> {
                var names = workspace.jvmClassesStream(false).map(p -> p.getValue().getName()).filter(n -> n.startsWith(optional(args, "prefix", ""))).sorted().toList();
                int offset = integer(args, "offset", 0, 0, Integer.MAX_VALUE);
                yield Map.of("total", names.size(), "offset", offset, "classes", names.stream().skip(offset).limit(integer(args, "limit", 100, 1, 500)).toList());
            }
            case "class_outline" -> {
                ClassNode cls = node(info(args));
                int limit = integer(args, "limit", 100, 1, 500);
                int fieldsOffset = integer(args, "fieldsOffset", 0, 0, Integer.MAX_VALUE);
                int methodsOffset = integer(args, "methodsOffset", 0, 0, Integer.MAX_VALUE);
                yield Map.of("className", cls.name, "access", cls.access, "superName", cls.superName == null ? "" : cls.superName,
                    "interfaces", cls.interfaces,
                    "fieldCount", cls.fields.size(), "methodCount", cls.methods.size(),
                    "fields", cls.fields.stream().skip(fieldsOffset).limit(limit).map(f -> Map.of("name", f.name, "descriptor", f.desc, "access", f.access)).toList(),
                    "methods", cls.methods.stream().skip(methodsOffset).limit(limit).map(m -> Map.of("name", m.name, "descriptor", m.desc, "access", m.access)).toList());
            }
            case "decompile_class" -> {
                var decompiler = args.has("decompiler") ? decompilers.getJvmDecompiler(required(args, "decompiler")) : decompilers.getTargetJvmDecompiler();
                if (decompiler == null) throw new IllegalArgumentException("Unknown decompiler; see workspace_status");
                var future = decompilers.decompile(decompiler, workspace, info(args));
                software.coley.recaf.services.decompile.DecompileResult result;
                try { result = future.get(45, TimeUnit.SECONDS); }
                catch (Exception error) { future.cancel(true); throw error; }
                if (result.getText() == null) throw new IllegalStateException("Decompile failed: " + result.getType());
                yield Map.of("decompiler", decompiler.getName(), "view", page(result.getText(), args));
            }
            case "method_bytecode" -> {
                ClassNode cls = node(info(args));
                MethodNode method = cls.methods.stream().filter(m -> m.name.equals(required(args, "methodName")) && m.desc.equals(required(args, "descriptor"))).findFirst().orElseThrow(() -> new IllegalArgumentException("Method not found"));
                Textifier printer = new Textifier();
                method.accept(new TraceMethodVisitor(printer));
                StringWriter out = new StringWriter();
                printer.print(new PrintWriter(out));
                yield page(out.toString(), args);
            }
            case "search_strings" -> {
                String query = required(args, "query");
                int offset = integer(args, "offset", 0, 0, Integer.MAX_VALUE);
                int limit = integer(args, "limit", 100, 1, 500);
                List<Object> hits = new ArrayList<>();
                int total = 0;
                for (var path : workspace.jvmClassesStream(false).sorted(Comparator.comparing(p -> p.getValue().getName())).toList()) {
                    ClassNode cls = node(path.getValue().asJvmClass());
                    for (MethodNode method : cls.methods) {
                        for (int i = 0; i < method.instructions.size(); i++) {
                            if (method.instructions.get(i) instanceof LdcInsnNode ldc && ldc.cst instanceof String value && value.contains(query)) {
                                if (total >= offset && hits.size() < limit) hits.add(Map.of("className", cls.name, "methodName", method.name, "descriptor", method.desc, "asmIndex", i, "value", value.length() > 2000 ? value.substring(0, 2000) : value));
                                total++;
                            }
                        }
                    }
                }
                yield Map.of("total", total, "offset", offset, "hits", hits);
            }
            default -> throw new IllegalArgumentException("Unknown tool: " + name);
        };
    }

    private static Object page(String value, JsonObject args) {
        int offset = integer(args, "offset", 0, 0, Integer.MAX_VALUE);
        int start = Math.min(offset, value.length());
        int end = (int) Math.min((long) value.length(), (long) start + integer(args, "maxChars", 30000, 1, 100000));
        return Map.of("totalChars", value.length(), "offset", start, "nextOffset", end, "text", value.substring(start, end));
    }

    private static String required(JsonObject args, String key) {
        if (!args.has(key) || !args.get(key).isJsonPrimitive() || !args.getAsJsonPrimitive(key).isString())
            throw new IllegalArgumentException(key + " must be a string");
        return args.get(key).getAsString();
    }

    private static String optional(JsonObject args, String key, String fallback) {
        return args.has(key) ? required(args, key) : fallback;
    }

    private static int integer(JsonObject args, String key, int fallback, int min, int max) {
        if (!args.has(key)) return fallback;
        int value;
        try { value = args.get(key).getAsBigDecimal().intValueExact(); }
        catch (RuntimeException error) { throw new IllegalArgumentException(key + " must be an integer"); }
        if (value < min || value > max) throw new IllegalArgumentException(key + " out of range");
        return value;
    }
}
