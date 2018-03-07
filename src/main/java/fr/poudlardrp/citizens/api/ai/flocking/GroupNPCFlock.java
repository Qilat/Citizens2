package fr.poudlardrp.citizens.api.ai.flocking;

import net.citizensnpcs.api.npc.NPC;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class GroupNPCFlock implements NPCFlock {
    private final List<NPC> npcs;
    private final double radius;

    public GroupNPCFlock(Iterable<NPC> npcs, double radius) {
        this.npcs = new ArrayList<NPC>();
        this.radius = radius;
    }

    public static GroupNPCFlock create(Iterable<NPC> npcs) {
        return new GroupNPCFlock(npcs, -1);
    }

    public static GroupNPCFlock createWithRadius(Iterable<NPC> npcs, double radius) {
        return new GroupNPCFlock(npcs, radius);
    }

    @Override
    public Collection<NPC> getNearby(final NPC npc) {
        if (radius < 0)
            return npcs;
        return npcs.stream().filter(new Predicate<NPC>() {
            @Override
            public boolean test(NPC input) {
                return input.getStoredLocation().distance(npc.getStoredLocation()) < radius;
            }
        }).collect(Collectors.<NPC>toList());
    }
}
