package fr.poudlardrp.citizens.api.event;

import net.citizensnpcs.api.npc.NPC;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.util.Vector;

public class NPCPushEvent extends NPCEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;
    private Vector collisionVector;

    public NPCPushEvent(NPC npc, Vector vector) {
        super(npc);
        this.collisionVector = vector;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    /**
     * Return the collision {@link Vector} being applied to the NPC.
     *
     * @return The collision vector
     */
    public Vector getCollisionVector() {
        return collisionVector;
    }

    /**
     * Sets the collision {@link Vector} to be applied to the NPC.
     *
     * @param vector The new collision vector
     */
    public void setCollisionVector(Vector vector) {
        this.collisionVector = vector;
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
    public void setCancelled(boolean cancel) {
        cancelled = cancel;

    }

}
