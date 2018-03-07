package fr.poudlardrp.citizens.api.astar.pathfinder;

import net.citizensnpcs.api.npc.NPC;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

import java.util.ListIterator;

public interface PathPoint {
    void addCallback(PathCallback callback);

    PathPoint createAtOffset(Vector vector);

    Vector getGoal();

    PathPoint getParentPoint();

    Vector getVector();

    void setVector(Vector vector);

    public static interface PathCallback {
        void run(NPC npc, Block point, ListIterator<Block> path);
    }
}
