package fr.poudlardrp.citizens.api.event;

import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.Entity;
import org.bukkit.event.HandlerList;

public class NPCCollisionEvent extends NPCEvent {
    private static final HandlerList handlers = new HandlerList();
    private final Entity entity;

    public NPCCollisionEvent(NPC npc, Entity entity) {
        super(npc);
        this.entity = entity;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    /**
     * Returns the {@link Entity} that collided with the {@link NPC}.
     *
     * @return The collided entity
     */
    public Entity getCollidedWith() {
        return entity;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
