package fr.poudlardrp.citizens.api.ai.flocking;

import fr.poudlardrp.citizens.api.npc.NPC;
import org.bukkit.util.Vector;

import java.util.Collection;

public interface FlockBehavior {
    Vector getVector(NPC npc, Collection<NPC> nearby);
}
