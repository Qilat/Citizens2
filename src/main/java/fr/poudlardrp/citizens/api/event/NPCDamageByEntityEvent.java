package fr.poudlardrp.citizens.api.event;

import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.Entity;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class NPCDamageByEntityEvent extends NPCDamageEvent {
    private static final HandlerList handlers = new HandlerList();
    private final Entity damager;

    public NPCDamageByEntityEvent(NPC npc, EntityDamageByEntityEvent event) {
        super(npc, event);
        damager = event.getDamager();
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Entity getDamager() {
        return damager;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
