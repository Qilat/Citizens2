package fr.poudlardrp.citizens.trait.waypoint.triggers;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import fr.poudlardrp.citizens.util.PlayerAnimation;
import fr.poudlardrp.citizens.api.npc.NPC;
import fr.poudlardrp.citizens.api.persistence.Persist;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.List;

public class AnimationTrigger implements WaypointTrigger {
    @Persist(required = true)
    private List<PlayerAnimation> animations;

    public AnimationTrigger() {
    }

    public AnimationTrigger(Collection<PlayerAnimation> collection) {
        animations = Lists.newArrayList(collection);
    }

    @Override
    public String description() {
        return String.format("Animation Trigger [animating %s]", Joiner.on(", ").join(animations));
    }

    @Override
    public void onWaypointReached(NPC npc, Location waypoint) {
        if (npc.getEntity().getType() != EntityType.PLAYER)
            return;
        Player player = (Player) npc.getEntity();
        for (PlayerAnimation animation : animations) {
            animation.play(player);
        }
    }
}
