"""Apply the small RustClient session/provenance integration to the supplied MCP fork."""
from pathlib import Path


def integrate(root):
    def edit(relative, before, after):
        path = root / relative
        text = path.read_text()
        if text.count(before) != 1:
            raise ValueError(f'integration anchor changed: {relative}: {before[:65]}')
        path.write_text(text.replace(before, after))

    edit('enigma/src/main/java/cuchaz/enigma/config/ConfigPaths.java',
         'public static Path getConfigPathRoot() {',
         'public static Path getConfigPathRoot() {\n\t\tString isolated = System.getProperty("rustclient.enigma.configDir");\n\t\tif (isolated != null) return Paths.get(isolated);')
    protocol = 'enigma-swing/src/main/java/cuchaz/enigma/gui/mcp/McpProtocol.java'
    edit(protocol, 'Object toolResult = dispatch(name, arguments);',
         'RustClientTools.guard(this.gui);\n\t\t\tObject toolResult = dispatch(name, arguments);')
    edit(protocol, 'return textContent(this.gson.toJson(toolResult), false);',
         'return textContent(this.gson.toJson(java.util.Map.of("originalSha256", System.getProperty("rustclient.enigma.originalSha256", "unspecified"), "analysisSha256", System.getProperty("rustclient.enigma.analysisSha256", "unspecified"), "session", System.getProperty("rustclient.enigma.session", "standalone"), "data", toolResult)), false);')
    edit(protocol, 'case "list_packages":\n\t\t\ttoolResult', '''case "workspace_status": return RustClientTools.status(this.gui, arguments);
        case "class_hierarchy": return RustClientTools.hierarchy(this.gui, arguments);
        case "method_family": return RustClientTools.family(this.gui, arguments);
        case "find_references": return RustClientTools.references(this.gui, arguments);
        case "validate_rename": return RustClientTools.validate(this.gui, arguments);
        case "list_packages":
\t\t\ttoolResult''')
    edit(protocol, 'JsonArray toolList = new JsonArray();', '''JsonArray toolList = new JsonArray();
        JsonObject empty = new JsonObject();
        toolList.add(rawTool("workspace_status", "Describe the pinned corpus and isolated draft mapping session", empty, new String[0]));
        JsonObject hierarchy = new JsonObject(); hierarchy.add("class", simple("string", "Original or mapped class name"));
        toolList.add(rawTool("class_hierarchy", "Enigma parents, children, ancestors and descendants", hierarchy, new String[]{"class"}));
        toolList.add(targetTool("method_family", "Inspect entries Enigma treats as equivalent virtual methods", null, null, new String[]{"class", "name", "desc"}));
        toolList.add(targetTool("find_references", "Find exact member/class references including type descriptors", null, null, new String[]{"kind", "class"}));
        toolList.add(targetTool("validate_rename", "Check a proposed name without changing any mapping", "newName", "Proposed readable name", new String[]{"kind", "class", "newName"}));''')
    edit(protocol, 'JsonObject schema = new JsonObject();\n\t\tschema.addProperty("type", "object");', '''JsonObject schema = new JsonObject();
        if (java.util.Set.of("list_classes", "list_members", "class_hierarchy", "method_family", "find_references").contains(name)) {
            properties.add("offset", simple("integer", "Nonnegative page offset"));
            properties.add("limit", simple("integer", "Page size, at most 500; default 100"));
        }
        if (name.equals("decompile_class")) {
            properties.add("offset", simple("integer", "UTF-16 source offset"));
            properties.add("maxChars", simple("integer", "Source page size, at most 100000; default 30000"));
        }
\t\tschema.addProperty("type", "object");''')
    edit(protocol, 'toolObject.add("inputSchema", schema);', '''toolObject.add("inputSchema", schema);
        boolean readOnly = !java.util.Set.of("rename", "mark_deobfuscated", "reset_obfuscated", "set_javadoc", "save_mappings").contains(name);
        JsonObject annotations = new JsonObject();
        annotations.addProperty("readOnlyHint", readOnly);
        annotations.addProperty("destructiveHint", false);
        annotations.addProperty("openWorldHint", false);
        toolObject.add("annotations", annotations);''')
    tools = 'enigma-swing/src/main/java/cuchaz/enigma/gui/mcp/McpTools.java'
    edit('enigma-swing/src/main/java/cuchaz/enigma/gui/mcp/McpEntryRef.java',
         'map.put("renamable", project.isRenamable(obf));',
         'map.put("renamable", !(obf instanceof MethodEntry method && method.isConstructor()) && project.isRenamable(obf));')
    edit(tools, 'String pkg = McpEntryRef.normalize(reqStr(args, "package"));',
         'String pkg = McpEntryRef.normalize(str(args, "package"));\n        if (pkg == null) pkg = "";')
    edit(tools, 'result.put("classes", classes);', 'classes.sort(java.util.Comparator.comparing(Object::toString));\n        result.put("total", classes.size());\n        result.put("classes", RustClientTools.page(classes, args));')
    edit(tools, 'result.put("fields", fields);\n\t\tresult.put("methods", methods);', '''fields.sort(java.util.Comparator.comparing(Object::toString));
        methods.sort(java.util.Comparator.comparing(Object::toString));
        result.put("fieldCount", fields.size()); result.put("methodCount", methods.size());
        result.put("fields", RustClientTools.page(fields, args));
        result.put("methods", RustClientTools.page(methods, args));''')
    edit(tools, 'result.put("source", source);', '''int start = Math.min(RustClientTools.integer(args, "offset", 0, Integer.MAX_VALUE), source.length());
        int end = (int)Math.min((long)source.length(), (long)start + RustClientTools.integer(args, "maxChars", 30000, 100000));
        if (end > start && end < source.length() && Character.isHighSurrogate(source.charAt(end - 1)) && Character.isLowSurrogate(source.charAt(end))) end += end - start == 1 ? 1 : -1;
        result.put("totalChars", source.length()); result.put("offset", start); result.put("nextOffset", end);
        result.put("source", source.substring(start, end));''')
    edit(tools, 'try {\n\t\t\tSwingUtilities.invokeAndWait(() -> this.gui.getController().saveMappings(selected.toPath()));', '''String session = System.getProperty("rustclient.enigma.session");
        if (session != null) {
            try {
                java.nio.file.Path path = selected.toPath().toAbsolutePath().normalize();
                if (!path.getParent().toRealPath().startsWith(java.nio.file.Path.of(session).toRealPath())
                    || (java.nio.file.Files.exists(path) && !path.toRealPath().startsWith(java.nio.file.Path.of(session).toRealPath())))
                    throw new McpException("MCP mapping saves must stay inside this draft session");
            } catch (java.io.IOException error) { throw new McpException("Cannot validate draft save path: " + error); }
        }
        try {
            final java.util.concurrent.CompletableFuture<?>[] save = new java.util.concurrent.CompletableFuture<?>[1];
            SwingUtilities.invokeAndWait(() -> save[0] = this.gui.getController().saveMappings(selected.toPath()));
            save[0].join();''')
    # The fork's save tool originally returned before the asynchronous write completed.
