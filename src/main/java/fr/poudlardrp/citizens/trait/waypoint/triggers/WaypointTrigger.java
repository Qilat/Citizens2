package fr.poudlardrp.citizens.trait.waypoint.triggers;

import fr.poudlardrp.citizens.api.npc.NPC;
import org.bukkit.Location;

public interface WaypointTrigger {
    String description();

    void onWaypointReached(NPC npc, Location waypoint);
}
