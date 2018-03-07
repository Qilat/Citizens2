package fr.poudlardrp.citizens;

import fr.poudlardrp.citizens.api.event.NPCEvent;
import fr.poudlardrp.citizens.api.npc.NPC;
import org.bukkit.Location;
import org.bukkit.event.HandlerList;

public class NPCNeedsRespawnEvent extends NPCEvent {
    private static final HandlerList handlers = new HandlerList();
    private final Location spawn;

    public NPCNeedsRespawnEvent(NPC npc, Location at) {
        super(npc);
        this.spawn = at;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public Location getSpawnLocation() {
        return spawn;
    }
}
