package fr.poudlardrp.citizens.trait.waypoint;

import com.google.common.collect.Lists;
import fr.poudlardrp.citizens.trait.waypoint.triggers.DelayTrigger;
import fr.poudlardrp.citizens.trait.waypoint.triggers.WaypointTrigger;
import fr.poudlardrp.citizens.api.CitizensAPI;
import fr.poudlardrp.citizens.api.npc.NPC;
import fr.poudlardrp.citizens.api.persistence.Persist;
import fr.poudlardrp.citizens.api.persistence.PersistenceLoader;
import fr.poudlardrp.citizens.trait.waypoint.triggers.WaypointTriggerRegistry;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.Collections;
import java.util.List;

public class Waypoint {
    static {
        PersistenceLoader.registerPersistDelegate(WaypointTrigger.class, WaypointTriggerRegistry.class);
    }

    @Persist(required = true)
    private Location location;
    @Persist
    private List<WaypointTrigger> triggers;

    public Waypoint() {
    }

    public Waypoint(Location at) {
        location = at;
    }

    public void addTrigger(WaypointTrigger trigger) {
        if (triggers == null)
            triggers = Lists.newArrayList();
        triggers.add(trigger);
    }

    public double distance(Waypoint dest) {
        return location.distance(dest.location);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Waypoint other = (Waypoint) obj;
        if (location == null) {
            if (other.location != null) {
                return false;
            }
        } else if (!location.equals(other.location)) {
            return false;
        }
        if (triggers == null) {
            if (other.triggers != null) {
                return false;
            }
        } else if (!triggers.equals(other.triggers)) {
            return false;
        }
        return true;
    }

    public Location getLocation() {
        return location;
    }

    public List<WaypointTrigger> getTriggers() {
        return triggers == null ? Collections.<WaypointTrigger>emptyList() : triggers;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = prime + ((location == null) ? 0 : location.hashCode());
        return prime * result + ((triggers == null) ? 0 : triggers.hashCode());
    }

    public void onReach(NPC npc) {
        if (triggers == null)
            return;
        runTriggers(npc, 0);
    }

    private void runTriggers(final NPC npc, int start) {
        for (int i = start; i < triggers.size(); i++) {
            WaypointTrigger trigger = triggers.get(i);
            trigger.onWaypointReached(npc, location);
            if (!(trigger instanceof DelayTrigger))
                continue;
            int delay = ((DelayTrigger) trigger).getDelay();
            if (delay <= 0)
                continue;
            final int newStart = i + 1;
            Bukkit.getScheduler().scheduleSyncDelayedTask(CitizensAPI.getPlugin(), new Runnable() {
                @Override
                public void run() {
                    runTriggers(npc, newStart);
                }
            }, delay);
            break;
        }
    }
}