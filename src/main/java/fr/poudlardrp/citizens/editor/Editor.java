package fr.poudlardrp.citizens.editor;

import fr.poudlardrp.citizens.util.Messages;
import fr.poudlardrp.citizens.api.util.Messaging;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public abstract class Editor implements Listener {
    private static final Map<String, Editor> EDITING = new HashMap<String, Editor>();

    private static void enter(Player player, Editor editor) {
        editor.begin();
        player.getServer().getPluginManager().registerEvents(editor,
                player.getServer().getPluginManager().getPlugin("Citizens"));
        EDITING.put(player.getName(), editor);
    }

    public static void enterOrLeave(Player player, Editor editor) {
        if (editor == null)
            return;
        Editor edit = EDITING.get(player.getName());
        if (edit == null) {
            enter(player, editor);
        } else if (edit.getClass() == editor.getClass()) {
            leave(player);
        } else {
            Messaging.sendErrorTr(player, Messages.ALREADY_IN_EDITOR);
        }
    }

    public static boolean hasEditor(Player player) {
        return EDITING.containsKey(player.getName());
    }

    public static void leave(Player player) {
        if (!hasEditor(player))
            return;
        Editor editor = EDITING.remove(player.getName());
        HandlerList.unregisterAll(editor);
        editor.end();
    }

    public static void leaveAll() {
        for (Entry<String, Editor> entry : EDITING.entrySet()) {
            entry.getValue().end();
            HandlerList.unregisterAll(entry.getValue());
        }
        EDITING.clear();
    }

    public abstract void begin();

    public abstract void end();
}