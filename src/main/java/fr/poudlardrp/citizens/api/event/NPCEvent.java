package fr.poudlardrp.citizens.api.event;

import fr.poudlardrp.citizens.api.npc.NPC;

/**
 * Represents an event thrown by an NPC.
 */
public abstract class NPCEvent extends CitizensEvent {
    final NPC npc;

    protected NPCEvent(NPC npc) {
        super();
        this.npc = npc;
    }

    /**
     * Get the npc involved in the event.
     *
     * @return the npc involved in the event
     */
    public NPC getNPC() {
        return npc;
    }
}