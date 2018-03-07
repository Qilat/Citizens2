package fr.poudlardrp.citizens.api.ai.goals;

import fr.poudlardrp.citizens.api.CitizensAPI;
import fr.poudlardrp.citizens.api.ai.event.NavigationCompleteEvent;
import fr.poudlardrp.citizens.api.ai.tree.BehaviorGoalAdapter;
import fr.poudlardrp.citizens.api.ai.tree.BehaviorStatus;
import fr.poudlardrp.citizens.api.astar.pathfinder.MinecraftBlockExaminer;
import fr.poudlardrp.citizens.api.npc.NPC;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import java.util.Random;

public class WanderGoal extends BehaviorGoalAdapter implements Listener {
    private final NPC npc;
    private final Random random = new Random();
    private final int xrange;
    private final int yrange;
    private boolean forceFinish;

    private WanderGoal(NPC npc, int xrange, int yrange) {
        this.npc = npc;
        this.xrange = xrange;
        this.yrange = yrange;
    }

    public static WanderGoal createWithNPC(NPC npc) {
        return createWithNPCAndRange(npc, 10, 2);
    }

    public static WanderGoal createWithNPCAndRange(NPC npc, int xrange, int yrange) {
        return new WanderGoal(npc, xrange, yrange);
    }

    private Location findRandomPosition() {
        Location base = npc.getEntity().getLocation();
        Location found = null;
        for (int i = 0; i < 10; i++) {
            int x = base.getBlockX() + random.nextInt(2 * xrange) - xrange;
            int y = base.getBlockY() + random.nextInt(2 * yrange) - yrange;
            int z = base.getBlockZ() + random.nextInt(2 * xrange) - xrange;
            Block block = base.getWorld().getBlockAt(x, y, z);
            if (MinecraftBlockExaminer.canStandOn(block)
                    && MinecraftBlockExaminer.canStandIn(block.getRelative(BlockFace.UP).getType())) {
                found = block.getLocation().add(0, 1, 0);
                break;
            }
        }
        return found;
    }

    @EventHandler
    public void onFinish(NavigationCompleteEvent event) {
        forceFinish = true;
    }

    @Override
    public void reset() {
        forceFinish = false;
        HandlerList.unregisterAll(this);
    }

    @Override
    public BehaviorStatus run() {
        if (!npc.getNavigator().isNavigating() || forceFinish)
            return BehaviorStatus.SUCCESS;
        return BehaviorStatus.RUNNING;
    }

    @Override
    public boolean shouldExecute() {
        if (!npc.isSpawned() || npc.getNavigator().isNavigating())
            return false;
        Location dest = findRandomPosition();
        if (dest == null)
            return false;
        npc.getNavigator().setTarget(dest);
        CitizensAPI.registerEvents(this);
        return true;
    }
}