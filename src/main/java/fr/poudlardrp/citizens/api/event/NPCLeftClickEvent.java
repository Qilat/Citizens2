package fr.poudlardrp.citizens.api.event;

import fr.poudlardrp.citizens.api.npc.NPC;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

/**
 * Called when an NPC is left-clicked by a player.
 */
public class NPCLeftClickEvent extends NPCClickEvent {
    private static final HandlerList handlers = new HandlerList();

    public NPCLeftClickEvent(NPC npc, Player leftClicker) {
        super(npc, leftClicker);
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}