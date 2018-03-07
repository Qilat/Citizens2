package fr.poudlardrp.citizens.api.event;

import fr.poudlardrp.citizens.api.npc.NPC;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class NPCDeathEvent extends NPCEvent {
    private static final HandlerList handlers = new HandlerList();
    private final EntityDeathEvent event;

    public NPCDeathEvent(NPC npc, EntityDeathEvent event) {
        super(npc);
        this.event = event;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public int getDroppedExp() {
        return event.getDroppedExp();
    }

    public void setDroppedExp(int exp) {
        event.setDroppedExp(exp);
    }

    public List<ItemStack> getDrops() {
        return event.getDrops();
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}