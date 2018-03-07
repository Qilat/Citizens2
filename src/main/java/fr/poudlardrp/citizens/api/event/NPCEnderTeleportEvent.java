package fr.poudlardrp.citizens.api.event;

import net.citizensnpcs.api.npc.NPC;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

/**
 * Called when an NPC is teleported after using an ender pearl.
 */
public class NPCEnderTeleportEvent extends NPCEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;

    public NPCEnderTeleportEvent(NPC npc) {
        super(npc);
    }

    public static HandlerList getHandlerList() {
        return handlers;
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
    public void setCancelled(boolean arg0) {
        this.cancelled = arg0;
    }
}