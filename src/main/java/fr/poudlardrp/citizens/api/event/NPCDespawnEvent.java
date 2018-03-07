package fr.poudlardrp.citizens.api.event;

import fr.poudlardrp.citizens.api.npc.NPC;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

/**
 * Called when an NPC despawns.
 */
public class NPCDespawnEvent extends NPCEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private final DespawnReason reason;
    private boolean cancelled;

    public NPCDespawnEvent(NPC npc, DespawnReason reason) {
        super(npc);
        this.reason = reason;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public DespawnReason getReason() {
        return reason;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }
}