package fr.poudlardrp.citizens.npc;

import fr.poudlardrp.citizens.api.npc.NPC;
import fr.poudlardrp.citizens.util.NMS;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

public abstract class AbstractEntityController implements EntityController {
    private Entity bukkitEntity;

    public AbstractEntityController() {
    }

    public AbstractEntityController(Class<?> clazz) {
        NMS.registerEntityClass(clazz);
    }

    protected abstract Entity createEntity(Location at, NPC npc);

    @Override
    public Entity getBukkitEntity() {
        return bukkitEntity;
    }

    @Override
    public void setEntity(Entity entity) {
        this.bukkitEntity = entity;
    }

    @Override
    public void remove() {
        if (bukkitEntity == null)
            return;
        bukkitEntity.remove();
        bukkitEntity = null;
    }

    @Override
    public void spawn(Location at, NPC npc) {
        bukkitEntity = createEntity(at, npc);
    }
}