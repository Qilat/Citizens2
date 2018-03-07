package fr.poudlardrp.citizens.api.ai.flocking;

import net.citizensnpcs.api.npc.NPC;

import java.util.Collection;

public interface NPCFlock {
    public Collection<NPC> getNearby(NPC npc);
}
