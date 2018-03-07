package fr.poudlardrp.citizens.trait.waypoint.triggers;

import fr.poudlardrp.citizens.trait.waypoint.WaypointProvider;
import fr.poudlardrp.citizens.trait.waypoint.Waypoints;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.persistence.Persist;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class DelayTrigger implements WaypointTrigger {
    @Persist
    private int delay = 0;

    public DelayTrigger() {
    }

    public DelayTrigger(int delay) {
        this.delay = delay;
    }

    @Override
    public String description() {
        return String.format("Delay for %d ticks", delay);
    }

    public int getDelay() {
        return delay;
    }

    @Override
    public void onWaypointReached(NPC npc, Location waypoint) {
        if (delay > 0) {
            scheduleTask(npc.getTrait(Waypoints.class).getCurrentProvider());
        }
    }

    private void scheduleTask(final WaypointProvider provider) {
        provider.setPaused(true);
        Bukkit.getScheduler().scheduleSyncDelayedTask(CitizensAPI.getPlugin(), new Runnable() {
            @Override
            public void run() {
                provider.setPaused(false);
            }
        }, delay);
    }
}
