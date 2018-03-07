package fr.poudlardrp.citizens.npc.ai;

import com.google.common.collect.Lists;
import fr.poudlardrp.citizens.util.NMS;
import fr.poudlardrp.citizens.api.ai.AbstractPathStrategy;
import fr.poudlardrp.citizens.api.ai.NavigatorParameters;
import fr.poudlardrp.citizens.api.ai.TargetType;
import fr.poudlardrp.citizens.api.ai.event.CancelReason;
import fr.poudlardrp.citizens.api.astar.AStarMachine;
import fr.poudlardrp.citizens.api.astar.pathfinder.ChunkBlockSource;
import fr.poudlardrp.citizens.api.astar.pathfinder.Path;
import fr.poudlardrp.citizens.api.astar.pathfinder.VectorGoal;
import fr.poudlardrp.citizens.api.astar.pathfinder.VectorNode;
import fr.poudlardrp.citizens.api.npc.NPC;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.List;

public class AStarNavigationStrategy extends AbstractPathStrategy {
    private static final AStarMachine<VectorNode, Path> ASTAR = AStarMachine.createWithDefaultStorage();
    private static final Location NPC_LOCATION = new Location(null, 0, 0, 0);
    private final Location destination;
    private final NPC npc;
    private final NavigatorParameters params;
    private Path plan;
    private boolean planned = false;
    private Vector vector;

    public AStarNavigationStrategy(NPC npc, Iterable<Vector> path, NavigatorParameters params) {
        super(TargetType.LOCATION);
        List<Vector> list = Lists.newArrayList(path);
        this.params = params;
        this.destination = list.get(list.size() - 1).toLocation(npc.getStoredLocation().getWorld());
        this.npc = npc;
        setPlan(new Path(list));
    }

    public AStarNavigationStrategy(NPC npc, Location dest, NavigatorParameters params) {
        super(TargetType.LOCATION);
        this.params = params;
        this.destination = dest;
        this.npc = npc;
    }

    @Override
    public Iterable<Vector> getPath() {
        return plan == null ? null : plan.getPath();
    }

    @Override
    public Location getTargetAsLocation() {
        return destination;
    }

    public void setPlan(Path path) {
        this.plan = path;
        this.planned = true;
        if (plan == null || plan.isComplete()) {
            setCancelReason(CancelReason.STUCK);
        } else {
            vector = plan.getCurrentVector();
            if (params.debug()) {
                plan.debug();
            }
        }
    }

    @Override
    public void stop() {
        if (plan != null && params.debug()) {
            plan.debugEnd();
        }
        plan = null;
    }

    @Override
    public boolean update() {
        if (!planned) {
            Location location = npc.getEntity().getLocation();
            VectorGoal goal = new VectorGoal(destination, (float) params.pathDistanceMargin());
            setPlan(ASTAR.runFully(goal,
                    new VectorNode(goal, location, new ChunkBlockSource(location, params.range()), params.examiners()),
                    50000));
        }
        if (getCancelReason() != null || plan == null || plan.isComplete()) {
            return true;
        }
        Location currLoc = npc.getEntity().getLocation(NPC_LOCATION);
        if (currLoc.toVector().distanceSquared(vector) <= params.distanceMargin()) {
            plan.update(npc);
            if (plan.isComplete()) {
                return true;
            }
            vector = plan.getCurrentVector();
        }
        double dX = vector.getBlockX() - currLoc.getX();
        double dZ = vector.getBlockZ() - currLoc.getZ();
        double dY = vector.getY() - currLoc.getY();
        double xzDistance = dX * dX + dZ * dZ;
        double distance = xzDistance + dY * dY;
        if (params.debug()) {
            npc.getEntity().getWorld().playEffect(vector.toLocation(npc.getEntity().getWorld()), Effect.ENDER_SIGNAL,
                    0);
        }
        if (distance > 0 && dY > NMS.getStepHeight(npc.getEntity()) && xzDistance <= 2.75) {
            NMS.setShouldJump(npc.getEntity());
        }
        double destX = vector.getX() + 0.5, destZ = vector.getZ() + 0.5;
        NMS.setDestination(npc.getEntity(), destX, vector.getY(), destZ, params.speed());
        params.run();
        plan.run(npc);
        return false;
    }
}
