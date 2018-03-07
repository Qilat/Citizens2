package fr.poudlardrp.citizens.nms.v1_10_R1.entity.nonliving;

import fr.poudlardrp.citizens.npc.AbstractEntityController;
import fr.poudlardrp.citizens.npc.CitizensNPC;
import fr.poudlardrp.citizens.npc.ai.NPCHolder;
import fr.poudlardrp.citizens.util.Util;
import fr.poudlardrp.citizens.api.event.NPCPushEvent;
import fr.poudlardrp.citizens.api.npc.NPC;
import net.minecraft.server.v1_10_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_10_R1.CraftServer;
import org.bukkit.craftbukkit.v1_10_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftItem;
import org.bukkit.craftbukkit.v1_10_R1.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.util.Vector;

public class ItemController extends AbstractEntityController {
    public ItemController() {
        super(EntityItemNPC.class);
    }

    @Override
    protected Entity createEntity(Location at, NPC npc) {
        WorldServer ws = ((CraftWorld) at.getWorld()).getHandle();
        Material id = Material.STONE;
        int data = npc.data().get(NPC.ITEM_DATA_METADATA, npc.data().get("falling-block-data", 0));
        if (npc.data().has(NPC.ITEM_ID_METADATA)) {
            id = Material.getMaterial(npc.data().<String>get(NPC.ITEM_ID_METADATA));
        }
        final EntityItemNPC handle = new EntityItemNPC(ws, npc, at.getX(), at.getY(), at.getZ(),
                CraftItemStack.asNMSCopy(new org.bukkit.inventory.ItemStack(id, 1, (short) data)));
        return handle.getBukkitEntity();
    }

    @Override
    public Item getBukkitEntity() {
        return (Item) super.getBukkitEntity();
    }

    public static class EntityItemNPC extends EntityItem implements NPCHolder {
        private final CitizensNPC npc;

        public EntityItemNPC(World world) {
            super(world);
            this.npc = null;
        }

        public EntityItemNPC(World world, NPC npc, double x, double y, double z, ItemStack stack) {
            super(world, x, y, z, stack);
            this.npc = (CitizensNPC) npc;
        }

        @Override
        public void collide(net.minecraft.server.v1_10_R1.Entity entity) {
            // this method is called by both the entities involved - cancelling
            // it will not stop the NPC from moving.
            super.collide(entity);
            if (npc != null) {
                Util.callCollisionEvent(npc, entity.getBukkitEntity());
            }
        }

        @Override
        public void d(EntityHuman entityhuman) {
            if (npc == null) {
                super.d(entityhuman);
            }
        }

        @Override
        public boolean d(NBTTagCompound save) {
            return npc == null ? super.d(save) : false;
        }

        @Override
        public void g(double x, double y, double z) {
            if (npc == null) {
                super.g(x, y, z);
                return;
            }
            if (NPCPushEvent.getHandlerList().getRegisteredListeners().length == 0) {
                if (!npc.data().get(NPC.DEFAULT_PROTECTED_METADATA, true))
                    super.g(x, y, z);
                return;
            }
            Vector vector = new Vector(x, y, z);
            NPCPushEvent event = Util.callPushEvent(npc, vector);
            if (!event.isCancelled()) {
                vector = event.getCollisionVector();
                super.g(vector.getX(), vector.getY(), vector.getZ());
            }
            // when another entity collides, this method is called to push the
            // NPC so we prevent it from doing anything if the event is
            // cancelled.
        }

        @Override
        public CraftEntity getBukkitEntity() {
            if (npc != null && !(bukkitEntity instanceof NPCHolder)) {
                bukkitEntity = new ItemNPC(this);
            }
            return super.getBukkitEntity();
        }

        @Override
        public NPC getNPC() {
            return npc;
        }

        @Override
        public void m() {
            if (npc != null) {
                npc.update();
            } else {
                super.m();
            }
        }
    }

    public static class ItemNPC extends CraftItem implements NPCHolder {
        private final CitizensNPC npc;

        public ItemNPC(EntityItemNPC entity) {
            super((CraftServer) Bukkit.getServer(), entity);
            this.npc = entity.npc;
        }

        @Override
        public NPC getNPC() {
            return npc;
        }

        public void setType(Material material, int data) {
            npc.data().setPersistent(NPC.ITEM_ID_METADATA, material.name());
            npc.data().setPersistent(NPC.ITEM_DATA_METADATA, data);
            if (npc.isSpawned()) {
                npc.despawn();
                npc.spawn(npc.getStoredLocation());
            }
        }
    }
}