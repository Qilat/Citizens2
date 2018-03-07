package fr.poudlardrp.citizens.npc;

import fr.poudlardrp.citizens.api.npc.NPC;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

public interface EntityController {
    Entity getBukkitEntity();

    void remove();

    void setEntity(Entity entity);

    void spawn(Location at, NPC npc);
}
