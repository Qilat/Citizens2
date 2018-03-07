package fr.poudlardrp.citizens.api.ai;

import fr.poudlardrp.citizens.api.astar.pathfinder.MinecraftBlockExaminer;
import fr.poudlardrp.citizens.api.npc.NPC;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

public class TeleportStuckAction implements StuckAction {
    private static final int MAX_ITERATIONS = 10;
    private static final double RANGE = 10;
    public static TeleportStuckAction INSTANCE = new TeleportStuckAction();

    private TeleportStuckAction() {
        // singleton
    }

    private boolean canStand(Block block) {
        return MinecraftBlockExaminer.canStandIn(block.getType())
                && MinecraftBlockExaminer.canStandIn(block.getRelative(BlockFace.UP).getType());
    }

    @Override
    public boolean run(NPC npc, Navigator navigator) {
        if (!npc.isSpawned())
            return false;
        Location base = navigator.getTargetAsLocation();
        if (npc.getEntity().getWorld() == base.getWorld()
                && npc.getEntity().getLocation().distanceSquared(base) <= RANGE)
            return true;
        Block block = base.getBlock();
        int iterations = 0;
        while (!canStand(block)) {
            if (iterations++ >= MAX_ITERATIONS) {
                block = base.getBlock();
                break;
            }
            block = block.getRelative(BlockFace.UP);
        }
        npc.teleport(block.getLocation(), TeleportCause.PLUGIN);
        return false;
    }
}
