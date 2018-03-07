package fr.poudlardrp.citizens.api.event;

import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

/**
 * Called when an NPC is right-clicked by a player.
 */
public class NPCRightClickEvent extends NPCClickEvent {
    private static final HandlerList handlers = new HandlerList();

    public NPCRightClickEvent(NPC npc, Player rightClicker) {
        super(npc, rightClicker);
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}