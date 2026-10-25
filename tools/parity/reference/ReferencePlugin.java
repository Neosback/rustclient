package net.runelite.client.plugins.rustclientreference;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermissions;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.jar.JarFile;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.events.ClientTick;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.events.ScriptPreFired;
import net.runelite.api.events.WidgetLoaded;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

/** Local reference diagnostics, deliberately offering no game-state mutation operations. */
@PluginDescriptor(name = "RustClient Reference", configName = "rustclientreference",
    developerPlugin = true, enabledByDefault = true)
public final class ReferencePlugin extends Plugin {
    @Inject private Client client;
    @Inject private ClientThread clientThread;
    private final Gson gson = new GsonBuilder().serializeNulls().create();
    private final Set<String> classes = new HashSet<>();
    private final List<Object> samples = new ArrayList<>();
    private final List<Object> events = new ArrayList<>();
    private int captureRemaining;
    private int droppedEvents;
    private HttpServer server;
    private ExecutorService workers;
    private Path endpoint;
    private String token;

    @Override
    protected void startUp() throws Exception {
        try (JarFile jar = new JarFile(System.getProperty("rustclient.reference.jar"))) {
            jar.stream().map(e -> e.getName()).filter(n -> n.endsWith(".class"))
                .forEach(n -> classes.add(n.substring(0, n.length() - 6).replace('/', '.')));
        }
        byte[] bytes = new byte[32];
        new SecureRandom().nextBytes(bytes);
        token = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
        server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 8);
        workers = Executors.newFixedThreadPool(2, runnable -> {
            Thread thread = new Thread(runnable, "rustclient-reference-http");
            thread.setDaemon(true);
            return thread;
        });
        server.setExecutor(workers);
        server.createContext("/v1/query", this::handle);
        server.start();
        endpoint = Path.of(System.getProperty("rustclient.reference.endpoint"));
        Files.writeString(endpoint, gson.toJson(ReferenceSnapshots.map("schema", 1,
            "url", "http://127.0.0.1:" + server.getAddress().getPort() + "/v1/query",
            "token", token, "pid", ProcessHandle.current().pid())));
        if (endpoint.getFileSystem().supportedFileAttributeViews().contains("posix")) {
            Files.setPosixFilePermissions(endpoint, PosixFilePermissions.fromString("rw-------"));
        }
        System.err.println("RustClient Reference bridge ready: " + endpoint);
    }

    @Override
    protected void shutDown() throws Exception {
        if (server != null) server.stop(0);
        if (workers != null) workers.shutdownNow();
        if (endpoint != null && Files.exists(endpoint)
            && Files.readString(endpoint).contains(token)) Files.delete(endpoint);
    }

    private void handle(HttpExchange exchange) {
        try {
            String supplied = exchange.getRequestHeaders().getFirst("X-RustClient-Reference");
            if (supplied == null || !MessageDigest.isEqual(token.getBytes(StandardCharsets.UTF_8),
                    supplied.getBytes(StandardCharsets.UTF_8))) {
                reply(exchange, 403, ReferenceSnapshots.map("error", "Reference token required"));
                return;
            }
            if (!exchange.getRequestMethod().equals("GET") || exchange.getRequestURI().toString().length() > 2048) {
                reply(exchange, 400, ReferenceSnapshots.map("error", "Expected bounded GET request"));
                return;
            }
            Map<String, String> query = new HashMap<>();
            String raw = exchange.getRequestURI().getRawQuery();
            if (raw != null) for (String pair : raw.split("&")) {
                String[] parts = pair.split("=", 2);
                query.put(parts[0], parts.length < 2 ? "" : URLDecoder.decode(parts[1], StandardCharsets.UTF_8));
            }
            CompletableFuture<Object> result = new CompletableFuture<>();
            clientThread.invokeLater(() -> {
                if (result.isDone()) return;
                try { result.complete(query(query)); }
                catch (Exception error) { result.completeExceptionally(error); }
            });
            try { reply(exchange, 200, result.get(3, TimeUnit.SECONDS)); }
            finally { result.cancel(false); }
        } catch (Exception error) {
            try { reply(exchange, 400, ReferenceSnapshots.map("error", error.toString())); }
            catch (Exception ignored) { exchange.close(); }
        }
    }

    private static int number(Map<String, String> query, String key, int fallback, int min, int max) {
        int value = Integer.parseInt(query.getOrDefault(key, Integer.toString(fallback)));
        if (value < min || value > max) throw new IllegalArgumentException("Range: " + key);
        return value;
    }

    private Object query(Map<String, String> q) throws Exception {
        switch (q.getOrDefault("op", "snapshot")) {
            case "snapshot": return ReferenceSnapshots.snapshot(client);
            case "widget": {
                int group = number(q, "group", 162, 0, 65535);
                int component = number(q, "component", 0, 0, 65535);
                var widget = client.getWidget(group, component);
                return ReferenceSnapshots.map("cycle", client.getGameCycle(), "widget",
                    ReferenceSnapshots.widget(widget, number(q, "depth", 1, 0, 4), new int[]{0}));
            }
            case "tile": return ReferenceSnapshots.tile(client,
                number(q, "x", 0, 0, 255), number(q, "y", 0, 0, 255), number(q, "plane", 0, 0, 3));
            case "class": return ReferenceSnapshots.type(client, classes, q.getOrDefault("name", "client"));
            case "trace_start":
                captureRemaining = number(q, "cycles", 150, 1, 500);
                samples.clear(); events.clear(); droppedEvents = 0;
                return ReferenceSnapshots.map("cycles", captureRemaining, "cycle", client.getGameCycle());
            case "trace_stop": captureRemaining = 0; // fall through to return captured values
            case "trace": {
                int start = number(q, "sampleOffset", 0, 0, 500);
                int count = number(q, "sampleCount", 1, 0, 20);
                int eventStart = number(q, "eventOffset", 0, 0, 4096);
                int eventCount = number(q, "eventCount", 64, 0, 256);
                return ReferenceSnapshots.map("remaining", captureRemaining,
                    "sampleTotal", samples.size(), "eventTotal", events.size(),
                    "sampleOffset", start, "eventOffset", eventStart,
                    "samples", new ArrayList<>(samples.subList(Math.min(start, samples.size()), Math.min(start + count, samples.size()))),
                    "events", new ArrayList<>(events.subList(Math.min(eventStart, events.size()), Math.min(eventStart + eventCount, events.size()))),
                    "droppedEvents", droppedEvents);
            }
            default: throw new IllegalArgumentException("Unknown read-only reference operation");
        }
    }

    private void reply(HttpExchange exchange, int status, Object value) throws Exception {
        byte[] body = gson.toJson(value).getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
        exchange.getResponseHeaders().set("Cache-Control", "no-store");
        exchange.sendResponseHeaders(status, body.length);
        try (var out = exchange.getResponseBody()) { out.write(body); }
    }

    private void event(String kind, Object data) {
        if (captureRemaining == 0) return;
        if (events.size() < 4096) events.add(ReferenceSnapshots.map("cycle", client.getGameCycle(), "kind", kind, "data", data));
        else droppedEvents++;
    }

    @Subscribe public void onClientTick(ClientTick event) {
        if (captureRemaining > 0) {
            var sample = ReferenceSnapshots.snapshot(client, 64);
            sample.remove("widgetRoots");
            sample.remove("menu");
            samples.add(sample);
            captureRemaining--;
        }
    }
    @Subscribe public void onScriptPreFired(ScriptPreFired event) { event("script", event.getScriptId()); }
    @Subscribe public void onWidgetLoaded(WidgetLoaded event) { event("widgetLoaded", event.getGroupId()); }
    @Subscribe public void onMenuOptionClicked(MenuOptionClicked event) {
        event("menuClick", ReferenceSnapshots.map("type", event.getMenuAction().getId(),
            "param0", event.getParam0(), "param1", event.getParam1(), "id", event.getId()));
    }
}
