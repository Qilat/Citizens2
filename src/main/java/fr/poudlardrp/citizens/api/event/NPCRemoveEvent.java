package fr.poudlardrp.citizens.api.event;

import net.citizensnpcs.api.npc.NPC;
import org.bukkit.event.HandlerList;

public class NPCRemoveEvent extends NPCEvent {

    private static final HandlerList handlers = new HandlerList();

    public NPCRemoveEvent(NPC npc) {
        super(npc);
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
