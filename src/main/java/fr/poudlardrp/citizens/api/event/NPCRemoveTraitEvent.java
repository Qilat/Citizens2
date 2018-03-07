package fr.poudlardrp.citizens.api.event;

import fr.poudlardrp.citizens.api.npc.NPC;
import fr.poudlardrp.citizens.api.trait.Trait;
import org.bukkit.event.HandlerList;

public class NPCRemoveTraitEvent extends NPCTraitEvent {
    private static final HandlerList handlers = new HandlerList();

    public NPCRemoveTraitEvent(NPC npc, Trait trait) {
        super(npc, trait);
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}