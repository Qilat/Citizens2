package fr.poudlardrp.citizens.nms.v1_10_R1.entity;

import com.google.common.collect.Maps;
import fr.poudlardrp.citizens.npc.AbstractEntityController;
import fr.poudlardrp.citizens.api.npc.NPC;
import net.minecraft.server.v1_10_R1.World;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_10_R1.CraftWorld;
import org.bukkit.entity.Entity;

import java.lang.reflect.Constructor;
import java.util.Map;

public abstract class MobEntityController extends AbstractEntityController {
    private static final Map<Class<?>, Constructor<?>> CONSTRUCTOR_CACHE = Maps.newHashMap();
    private final Constructor<?> constructor;

    protected MobEntityController(Class<?> clazz) {
        super(clazz);
        this.constructor = getConstructor(clazz);
    }

    private static Constructor<?> getConstructor(Class<?> clazz) {
        Constructor<?> constructor = CONSTRUCTOR_CACHE.get(clazz);
        if (constructor != null)
            return constructor;
        try {
            return clazz.getConstructor(World.class, NPC.class);
        } catch (Exception ex) {
            throw new IllegalStateException("unable to find an entity constructor");
        }
    }

    @Override
    protected Entity createEntity(Location at, NPC npc) {
        net.minecraft.server.v1_10_R1.Entity entity = createEntityFromClass(((CraftWorld) at.getWorld()).getHandle(),
                npc);
        entity.setPositionRotation(at.getX(), at.getY(), at.getZ(), at.getYaw(), at.getPitch());

        // entity.onGround isn't updated right away - we approximate here so
        // that things like pathfinding still work *immediately* after spawn.
        org.bukkit.Material beneath = at.getBlock().getRelative(BlockFace.DOWN).getType();
        if (beneath.isBlock()) {
            entity.onGround = true;
        }
        return entity.getBukkitEntity();
    }

    private net.minecraft.server.v1_10_R1.Entity createEntityFromClass(Object... args) {
        try {
            return (net.minecraft.server.v1_10_R1.Entity) constructor.newInstance(args);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}