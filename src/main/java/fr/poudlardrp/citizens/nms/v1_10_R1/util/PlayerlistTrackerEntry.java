package fr.poudlardrp.citizens.nms.v1_10_R1.util;

import fr.poudlardrp.citizens.nms.v1_10_R1.entity.EntityHumanNPC;
import fr.poudlardrp.citizens.npc.skin.SkinnableEntity;
import fr.poudlardrp.citizens.util.NMS;
import net.minecraft.server.v1_10_R1.Entity;
import net.minecraft.server.v1_10_R1.EntityPlayer;
import net.minecraft.server.v1_10_R1.EntityTrackerEntry;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

public class PlayerlistTrackerEntry extends EntityTrackerEntry {
    private static Field E = NMS.getField(EntityTrackerEntry.class, "e");
    private static Field F = NMS.getField(EntityTrackerEntry.class, "f");
    private static Field G = NMS.getField(EntityTrackerEntry.class, "g");
    private static Field TRACKER = NMS.getField(EntityTrackerEntry.class, "tracker");
    private static Field U = NMS.getField(EntityTrackerEntry.class, "u");

    public PlayerlistTrackerEntry(Entity entity, int i, int j, int k, boolean flag) {
        super(entity, i, j, k, flag);
    }

    public PlayerlistTrackerEntry(EntityTrackerEntry entry) {
        this(getTracker(entry), getE(entry), getF(entry), getG(entry), getU(entry));
    }

    private static int getE(EntityTrackerEntry entry) {
        try {
            return (Integer) E.get(entry);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private static int getF(EntityTrackerEntry entry) {
        try {
            return (Integer) F.get(entry);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private static int getG(EntityTrackerEntry entry) {
        try {
            return (Integer) G.get(entry);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private static Entity getTracker(EntityTrackerEntry entry) {
        try {
            return (Entity) TRACKER.get(entry);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static boolean getU(EntityTrackerEntry entry) {
        try {
            return (Boolean) U.get(entry);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void updatePlayer(final EntityPlayer entityplayer) {
        // prevent updates to NPC "viewers"
        if (entityplayer instanceof EntityHumanNPC)
            return;
        Entity tracker = getTracker(this);
        if (entityplayer != tracker && c(entityplayer)) {
            if (!this.trackedPlayers.contains(entityplayer)
                    && ((entityplayer.x().getPlayerChunkMap().a(entityplayer, tracker.ac, tracker.ae))
                    || (tracker.attachedToPlayer))) {
                if ((tracker instanceof SkinnableEntity)) {

                    SkinnableEntity skinnable = (SkinnableEntity) tracker;

                    Player player = skinnable.getBukkitEntity();
                    if (!entityplayer.getBukkitEntity().canSee(player))
                        return;

                    skinnable.getSkinTracker().updateViewer(entityplayer.getBukkitEntity());
                }
            }
        }
        super.updatePlayer(entityplayer);
    }
}
