package fr.poudlardrp.citizens.api.ai.flocking;

import fr.poudlardrp.citizens.api.npc.NPC;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Flocker implements Runnable {
    public static double HIGH_INFLUENCE = 1.0 / 20.0;
    public static double LOW_INFLUENCE = 1.0 / 200.0;
    private final List<FlockBehavior> behaviors;
    private final NPCFlock flock;
    private final NPC npc;
    private double maxForce = 1.5;

    public Flocker(NPC npc, NPCFlock flock, FlockBehavior... behaviors) {
        this.npc = npc;
        this.flock = flock;
        this.behaviors = Arrays.asList(behaviors);
    }

    private static Vector clip(double max, Vector vector) {
        if (vector.length() > max) {
            return vector.normalize().multiply(max);
        }
        return vector;
    }

    @Override
    public void run() {
        Collection<NPC> nearby = flock.getNearby(npc);
        if (nearby.isEmpty())
            return;
        Vector base = new Vector(0, 0, 0);
        for (FlockBehavior behavior : behaviors) {
            base.add(behavior.getVector(npc, nearby));
        }
        base = clip(maxForce, base);
        npc.getEntity().setVelocity(npc.getEntity().getVelocity().add(base));
    }

    public void setMaxForce(double maxForce) {
        this.maxForce = maxForce;
    }
}
