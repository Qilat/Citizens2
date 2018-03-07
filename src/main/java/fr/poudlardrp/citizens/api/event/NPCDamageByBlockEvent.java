package fr.poudlardrp.citizens.api.event;

import net.citizensnpcs.api.npc.NPC;
import org.bukkit.block.Block;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageByBlockEvent;

public class NPCDamageByBlockEvent extends NPCDamageEvent {
    private static final HandlerList handlers = new HandlerList();
    private final Block damager;

    public NPCDamageByBlockEvent(NPC npc, EntityDamageByBlockEvent event) {
        super(npc, event);
        damager = event.getDamager();
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Block getDamager() {
        return damager;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
