package fr.poudlardrp.citizens.api.event;

import fr.poudlardrp.citizens.api.npc.NPC;
import org.bukkit.entity.Entity;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityCombustByEntityEvent;

public class NPCCombustByEntityEvent extends NPCCombustEvent {
    private static final HandlerList handlers = new HandlerList();
    private final EntityCombustByEntityEvent event;

    public NPCCombustByEntityEvent(EntityCombustByEntityEvent event, NPC npc) {
        super(event, npc);
        this.event = event;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    /**
     * The combuster can be a WeatherStorm a Blaze, or an Entity holding a FIRE_ASPECT enchanted item.
     *
     * @return the Entity that set the combustee alight.
     */
    public Entity getCombuster() {
        return event.getCombuster();
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
