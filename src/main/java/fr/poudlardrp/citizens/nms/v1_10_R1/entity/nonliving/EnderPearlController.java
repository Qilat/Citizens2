package fr.poudlardrp.citizens.nms.v1_10_R1.entity.nonliving;

import fr.poudlardrp.citizens.nms.v1_10_R1.entity.MobEntityController;
import fr.poudlardrp.citizens.npc.CitizensNPC;
import fr.poudlardrp.citizens.npc.ai.NPCHolder;
import fr.poudlardrp.citizens.util.Util;
import fr.poudlardrp.citizens.api.event.NPCPushEvent;
import fr.poudlardrp.citizens.api.npc.NPC;
import net.minecraft.server.v1_10_R1.EntityEnderPearl;
import net.minecraft.server.v1_10_R1.NBTTagCompound;
import net.minecraft.server.v1_10_R1.World;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_10_R1.CraftServer;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftEnderPearl;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftEntity;
import org.bukkit.entity.EnderPearl;
import org.bukkit.util.Vector;

public class EnderPearlController extends MobEntityController {
    public EnderPearlController() {
        super(EntityEnderPearlNPC.class);
    }

    @Override
    public EnderPearl getBukkitEntity() {
        return (EnderPearl) super.getBukkitEntity();
    }

    public static class EnderPearlNPC extends CraftEnderPearl implements NPCHolder {
        private final CitizensNPC npc;

        public EnderPearlNPC(EntityEnderPearlNPC entity) {
            super((CraftServer) Bukkit.getServer(), entity);
            this.npc = entity.npc;
        }

        @Override
        public NPC getNPC() {
            return npc;
        }
    }

    public static class EntityEnderPearlNPC extends EntityEnderPearl implements NPCHolder {
        private final CitizensNPC npc;

        public EntityEnderPearlNPC(World world) {
            this(world, null);
        }

        public EntityEnderPearlNPC(World world, NPC npc) {
            super(world);
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
                bukkitEntity = new EnderPearlNPC(this);
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
                if (!npc.data().get(NPC.DEFAULT_PROTECTED_METADATA, true)) {
                    super.m();
                }
            } else {
                super.m();
            }
        }
    }
}