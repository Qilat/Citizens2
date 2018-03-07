package fr.poudlardrp.citizens.npc.ai;

import com.google.common.collect.Lists;
import fr.poudlardrp.citizens.util.NMS;
import fr.poudlardrp.citizens.api.ai.AbstractPathStrategy;
import fr.poudlardrp.citizens.api.ai.NavigatorParameters;
import fr.poudlardrp.citizens.api.ai.TargetType;
import fr.poudlardrp.citizens.api.ai.event.CancelReason;
import fr.poudlardrp.citizens.api.npc.NPC;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import java.util.List;

public class MCNavigationStrategy extends AbstractPathStrategy {
    private static final Location HANDLE_LOCATION = new Location(null, 0, 0, 0);
    private final Entity handle;
    private final MCNavigator navigator;
    private final NavigatorParameters parameters;
    private final Location target;

    MCNavigationStrategy(final NPC npc, Iterable<Vector> path, NavigatorParameters params) {
        super(TargetType.LOCATION);
        List<Vector> list = Lists.newArrayList(path);
        this.target = list.get(list.size() - 1).toLocation(npc.getStoredLocation().getWorld());
        this.parameters = params;
        handle = npc.getEntity();
        this.navigator = NMS.getTargetNavigator(npc.getEntity(), list, params);
    }

    MCNavigationStrategy(final NPC npc, Location dest, NavigatorParameters params) {
        super(TargetType.LOCATION);
        this.target = dest;
        this.parameters = params;
        handle = npc.getEntity();
        this.navigator = NMS.getTargetNavigator(npc.getEntity(), dest, params);
    }

    private double distanceSquared() {
        return handle.getLocation(HANDLE_LOCATION).distanceSquared(target);
    }

    @Override
    public Iterable<Vector> getPath() {
        return navigator.getPath();
    }

    @Override
    public Location getTargetAsLocation() {
        return target;
    }

    @Override
    public TargetType getTargetType() {
        return TargetType.LOCATION;
    }

    @Override
    public void stop() {
        navigator.stop();
    }

    @Override
    public String toString() {
        return "MCNavigationStrategy [target=" + target + "]";
    }

    @Override
    public boolean update() {
        if (navigator.getCancelReason() != null) {
            setCancelReason(navigator.getCancelReason());
        }
        if (getCancelReason() != null)
            return true;
        boolean wasFinished = navigator.update();
        parameters.run();
        if (distanceSquared() < parameters.distanceMargin()) {
            stop();
            return true;
        }
        return wasFinished;
    }

    public static interface MCNavigator {
        CancelReason getCancelReason();

        Iterable<Vector> getPath();

        void stop();

        boolean update();
    }
}