package fr.poudlardrp.citizens.api.event;

import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Location;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

/**
 * Called when an NPC teleports.
 */
public class NPCTeleportEvent extends NPCEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private final Location to;
    private boolean cancelled;

    public NPCTeleportEvent(NPC npc, Location to) {
        super(npc);
        this.to = to;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Location getFrom() {
        return npc.getStoredLocation();
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public Location getTo() {
        return to;
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