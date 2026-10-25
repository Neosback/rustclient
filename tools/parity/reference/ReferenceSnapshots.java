package net.runelite.client.plugins.rustclientreference;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.runelite.api.Actor;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.Player;
import net.runelite.api.WorldView;
import net.runelite.api.widgets.Widget;

/** Bounded, detached values only. Call every method on the RuneLite client thread. */
final class ReferenceSnapshots {
    private ReferenceSnapshots() { }

    static Map<String, Object> map(Object... entries) {
        Map<String, Object> result = new LinkedHashMap<>();
        for (int i = 0; i < entries.length; i += 2) result.put((String) entries[i], entries[i + 1]);
        return result;
    }

    static Map<String, Object> actor(Actor actor) {
        var local = actor.getLocalLocation();
        var world = actor.getWorldLocation();
        return map("kind", actor instanceof NPC ? "npc" : "player",
            "index", actor instanceof NPC ? ((NPC) actor).getIndex() : ((Player) actor).getId(),
            "definition", actor instanceof NPC ? ((NPC) actor).getId() : null,
            "local", local == null ? null : new int[]{local.getX(), local.getY()},
            "world", world == null ? null : new int[]{world.getX(), world.getY(), world.getPlane()},
            "orientation", actor.getOrientation(), "animation", actor.getAnimation(),
            "animationFrame", actor.getAnimationFrame(), "pose", actor.getPoseAnimation(),
            "poseFrame", actor.getPoseAnimationFrame(), "idle", actor.getIdlePoseAnimation(),
            "walk", actor.getWalkAnimation(), "run", actor.getRunAnimation(),
            "logicalHeight", actor.getLogicalHeight(), "heightOffset", actor.getAnimationHeightOffset(),
            "overheadCycles", actor.getOverheadCycle());
    }

    static Map<String, Object> snapshot(Client client) {
        return snapshot(client, 512);
    }

    static Map<String, Object> snapshot(Client client, int limit) {
        WorldView world = client.getTopLevelWorldView();
        List<Object> actors = new ArrayList<>();
        if (world != null) {
            for (Player p : world.players()) {
                if (p != null && actors.size() < limit) actors.add(actor(p));
            }
            for (NPC n : world.npcs()) {
                if (n != null && actors.size() < limit) actors.add(actor(n));
            }
        }
        List<Object> roots = new ArrayList<>();
        for (Widget widget : client.getWidgetRoots()) {
            if (widget != null) roots.add(widget(widget, 0, new int[]{0}));
        }
        List<Object> menu = new ArrayList<>();
        menu.addAll(menu(client));
        return map("schema", 1, "revision", client.getRevision(), "gameState", client.getGameState().name(),
            "cycle", client.getGameCycle(), "tick", client.getTickCount(), "resized", client.isResized(),
            "canvas", new int[]{client.getCanvasWidth(), client.getCanvasHeight()},
            "viewport", new int[]{client.getViewportXOffset(), client.getViewportYOffset(),
                client.getViewportWidth(), client.getViewportHeight()},
            "camera", new int[]{client.getCameraX(), client.getCameraY(), client.getCameraZ(),
                client.getCameraPitch(), client.getCameraYaw()},
            "base", world == null ? null : new int[]{world.getBaseX(), world.getBaseY(), world.getPlane()},
            "localPlayer", client.getLocalPlayer() == null ? null : actor(client.getLocalPlayer()),
            "actors", actors, "actorsTruncated", actors.size() == limit,
            "widgetRoots", roots, "menu", menu,
            "chatTab", client.getVarcIntValue(41));
    }

    /** Every entry of the menu the client built for the last processed mouse position, top entry last. */
    static List<Object> menu(Client client) {
        List<Object> menu = new ArrayList<>();
        for (var entry : client.getMenuEntries()) {
            if (menu.size() == 512) break;
            menu.add(map("option", entry.getOption(), "target", entry.getTarget(),
                "type", entry.getType().getId(), "identifier", entry.getIdentifier(),
                "param0", entry.getParam0(), "param1", entry.getParam1(),
                "worldView", entry.getWorldViewId(), "deprioritized", entry.isDeprioritized()));
        }
        return menu;
    }

    /** Full live widget record: geometry, flags, text, sprite/model/item content and numeric listener arguments. */
    static Map<String, Object> widgetFull(Widget widget, int depth, int[] budget) {
        if (widget == null || budget[0]++ >= 40000) return null;
        var bounds = widget.getBounds();
        var location = widget.getCanvasLocation();
        var result = map("id", widget.getId(), "group", widget.getId() >>> 16,
            "component", widget.getId() & 65535, "child", widget.getIndex(), "parent", widget.getParentId(),
            "type", widget.getType(), "contentType", widget.getContentType(), "if3", widget.isIf3(),
            "hidden", widget.isHidden(), "selfHidden", widget.isSelfHidden(),
            "bounds", new int[]{bounds.x, bounds.y, bounds.width, bounds.height},
            "canvas", location == null ? null : new int[]{location.getX(), location.getY()},
            "relative", new int[]{widget.getRelativeX(), widget.getRelativeY(), widget.getWidth(), widget.getHeight()},
            "original", new int[]{widget.getOriginalX(), widget.getOriginalY(),
                widget.getOriginalWidth(), widget.getOriginalHeight()},
            "modes", new int[]{widget.getXPositionMode(), widget.getYPositionMode(),
                widget.getWidthMode(), widget.getHeightMode()},
            "scroll", new int[]{widget.getScrollX(), widget.getScrollY(),
                widget.getScrollWidth(), widget.getScrollHeight()},
            "clickMask", widget.getClickMask(), "noClickThrough", widget.getNoClickThrough(),
            "noScrollThrough", widget.getNoScrollThrough(), "targetVerb", widget.getTargetVerb(),
            "targetPriority", widget.getTargetPriority(), "name", widget.getName(),
            "text", widget.getText(), "textColor", widget.getTextColor(), "font", widget.getFontId(),
            "textShadowed", widget.getTextShadowed(), "textAlign",
            new int[]{widget.getXTextAlignment(), widget.getYTextAlignment(), widget.getLineHeight()},
            "opacity", widget.getOpacity(), "filled", widget.isFilled(), "borderType", widget.getBorderType(),
            "sprite", widget.getSpriteId(), "spriteTiling", widget.getSpriteTiling(),
            "flipped", new boolean[]{widget.isFlippedHorizontally(), widget.isFlippedVertically()},
            "model", widget.getModelId(), "modelType", widget.getModelType(),
            "modelView", new int[]{widget.getRotationX(), widget.getRotationY(), widget.getRotationZ(),
                widget.getModelZoom(), widget.getAnimationId()},
            "item", new int[]{widget.getItemId(), widget.getItemQuantity(), widget.getItemQuantityMode()},
            "drag", new int[]{widget.getDragDeadZone(), widget.getDragDeadTime()},
            "actions", widget.getActions(), "hasListener", widget.hasListener(),
            "onOp", listener(widget.getOnOpListener()), "onLoad", listener(widget.getOnLoadListener()),
            "onKey", listener(widget.getOnKeyListener()), "onInv", listener(widget.getOnInvTransmitListener()),
            "onVar", listener(widget.getOnVarTransmitListener()));
        if (depth > 0) {
            List<Object> children = new ArrayList<>();
            for (Widget[] group : new Widget[][]{widget.getStaticChildren(),
                    widget.getDynamicChildren(), widget.getNestedChildren()}) {
                if (group == null) continue;
                for (Widget child : group) {
                    if (child == null || budget[0] >= 40000) continue;
                    children.add(widgetFull(child, depth - 1, budget));
                }
            }
            result.put("children", children);
        }
        return result;
    }

    private static Object[] listener(Object[] listener) {
        if (listener == null) return null;
        // IDs and numeric event sentinels establish dispatch; omit arbitrary string arguments.
        return Arrays.stream(listener).limit(64)
            .map(v -> v instanceof Number ? v : "<" + v.getClass().getSimpleName() + ">")
            .toArray();
    }

    static Map<String, Object> widget(Widget widget, int depth, int[] budget) {
        if (widget == null || budget[0]++ >= 2048) return null;
        var bounds = widget.getBounds();
        var result = map("id", widget.getId(), "group", widget.getId() >>> 16,
            "component", widget.getId() & 65535, "child", widget.getIndex(),
            "type", widget.getType(), "contentType", widget.getContentType(),
            "parent", widget.getParentId(), "hidden", widget.isHidden(),
            "bounds", new int[]{bounds.x, bounds.y, bounds.width, bounds.height},
            "scroll", new int[]{widget.getScrollX(), widget.getScrollY(),
                widget.getScrollWidth(), widget.getScrollHeight()},
            "actions", widget.getActions(), "onOp", listener(widget.getOnOpListener()),
            "onLoad", listener(widget.getOnLoadListener()), "onKey", listener(widget.getOnKeyListener()));
        if (depth > 0) {
            List<Object> children = new ArrayList<>();
            for (Widget[] group : new Widget[][]{widget.getStaticChildren(),
                    widget.getDynamicChildren(), widget.getNestedChildren()}) {
                if (group == null) continue;
                for (Widget child : group) {
                    if (child == null || budget[0] >= 2048) continue;
                    children.add(widget(child, depth - 1, budget));
                }
            }
            result.put("children", children);
        }
        return result;
    }

    static Map<String, Object> tile(Client client, int x, int y, int plane) {
        WorldView world = client.getTopLevelWorldView();
        if (world == null || plane < 0 || plane > 3 || x < 0 || y < 0
            || x >= world.getSizeX() || y >= world.getSizeY()) {
            throw new IllegalArgumentException("Tile outside current world view");
        }
        var scene = world.getScene();
        var tile = scene.getTiles()[plane][x][y];
        var wall = tile == null ? null : tile.getWallObject();
        var decoration = tile == null ? null : tile.getDecorativeObject();
        var ground = tile == null ? null : tile.getGroundObject();
        List<Object> objects = new ArrayList<>();
        if (tile != null) for (var object : tile.getGameObjects()) {
            if (object != null) objects.add(map("id", object.getId(), "orientation", object.getOrientation(),
                "x", object.getLocalLocation().getX(), "y", object.getLocalLocation().getY()));
        }
        var roofs = scene.getRoofs();
        return map("x", x, "y", y, "plane", plane,
            "flags", Byte.toUnsignedInt(world.getTileSettings()[plane][x][y]),
            "height", world.getTileHeights()[plane][x][y],
            "roof", roofs == null ? null : roofs[plane][x][y],
            "roofRemovalMode", scene.getRoofRemovalMode(),
            "wall", wall == null ? null : map("id", wall.getId(), "a", wall.getOrientationA(), "b", wall.getOrientationB()),
            "decoration", decoration == null ? null : map("id", decoration.getId(),
                "xOffset", decoration.getXOffset(), "yOffset", decoration.getYOffset()),
            "ground", ground == null ? null : ground.getId(), "objects", objects);
    }

    static Map<String, Object> type(Client client, Set<String> allowed, String name) throws Exception {
        if (!allowed.contains(name)) throw new IllegalArgumentException("Class is not in the pinned gamepack");
        Class<?> type = Class.forName(name, false, client.getClass().getClassLoader());
        List<Object> fields = new ArrayList<>();
        for (var field : type.getDeclaredFields()) fields.add(map("name", field.getName(),
            "type", field.getType().getTypeName(), "modifiers", Modifier.toString(field.getModifiers())));
        List<Object> methods = new ArrayList<>();
        for (var method : type.getDeclaredMethods()) methods.add(map("name", method.getName(),
            "returns", method.getReturnType().getTypeName(), "parameters",
            Arrays.stream(method.getParameterTypes()).map(Class::getTypeName).toArray(),
            "modifiers", Modifier.toString(method.getModifiers())));
        return map("name", name, "superclass", type.getSuperclass() == null ? null : type.getSuperclass().getName(),
            "interfaces", Arrays.stream(type.getInterfaces()).map(Class::getName).toArray(),
            "fields", fields, "methods", methods,
            "policy", "Metadata only; no field reads, accessibility changes or method invocation");
    }
}
