package fr.poudlardrp.citizens.api.event;

import fr.poudlardrp.citizens.api.npc.NPC;
import org.bukkit.event.HandlerList;

public class NPCCreateEvent extends NPCEvent {
    private static final HandlerList handlers = new HandlerList();

    public NPCCreateEvent(NPC npc) {
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
