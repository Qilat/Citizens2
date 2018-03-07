package fr.poudlardrp.citizens.api.event;

import fr.poudlardrp.citizens.api.npc.NPC;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

/**
 * Represents an event where an NPC was clicked by a player.
 */
public abstract class NPCClickEvent extends NPCEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private final Player clicker;
    private boolean cancelled = false;

    protected NPCClickEvent(NPC npc, Player clicker) {
        super(npc);
        this.clicker = clicker;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    /**
     * Gets the player that clicked the NPC.
     *
     * @return Player that clicked the NPC
     */
    public Player getClicker() {
        return clicker;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}