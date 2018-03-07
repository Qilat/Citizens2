package fr.poudlardrp.citizens.api.event;

import fr.poudlardrp.citizens.api.npc.NPC;
import fr.poudlardrp.citizens.api.trait.Trait;

public abstract class NPCTraitEvent extends NPCEvent {
    private final Trait trait;

    protected NPCTraitEvent(NPC npc, Trait trait) {
        super(npc);
        this.trait = trait;
    }

    public Trait getTrait() {
        return trait;
    }
}
