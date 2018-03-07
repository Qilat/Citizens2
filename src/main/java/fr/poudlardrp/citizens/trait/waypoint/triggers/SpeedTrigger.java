package fr.poudlardrp.citizens.trait.waypoint.triggers;

import fr.poudlardrp.citizens.api.npc.NPC;
import fr.poudlardrp.citizens.api.persistence.Persist;
import org.bukkit.Location;

public class SpeedTrigger implements WaypointTrigger {
    @Persist
    private float speed = 1F;

    public SpeedTrigger() {
    }

    public SpeedTrigger(float speed) {
        this.speed = speed;
    }

    @Override
    public String description() {
        return String.format("Speed change to %f", speed);
    }

    public float getSpeed() {
        return speed;
    }

    @Override
    public void onWaypointReached(NPC npc, Location waypoint) {
        npc.getNavigator().getDefaultParameters().speedModifier(speed);
    }
}
