package fr.poudlardrp.citizens.trait.waypoint;

import fr.poudlardrp.citizens.api.event.CitizensEvent;
import org.bukkit.event.HandlerList;

import java.util.Iterator;

public class LinearWaypointsCompleteEvent extends CitizensEvent {
    private static final HandlerList handlers = new HandlerList();
    private final WaypointProvider provider;
    private Iterator<Waypoint> next;

    public LinearWaypointsCompleteEvent(WaypointProvider provider, Iterator<Waypoint> next) {
        this.next = next;
        this.provider = provider;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public Iterator<Waypoint> getNextWaypoints() {
        return next;
    }

    public void setNextWaypoints(Iterator<Waypoint> waypoints) {
        this.next = waypoints;
    }

    public WaypointProvider getWaypointProvider() {
        return provider;
    }
}
