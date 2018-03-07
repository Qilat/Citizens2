package fr.poudlardrp.citizens.util;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.GameProfileRepository;
import net.citizensnpcs.api.ai.NavigatorParameters;
import net.citizensnpcs.api.command.CommandManager;
import net.citizensnpcs.api.command.exception.CommandException;
import net.citizensnpcs.api.npc.BlockBreaker;
import net.citizensnpcs.api.npc.BlockBreaker.BlockBreakerConfiguration;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.citizensnpcs.api.util.Messaging;
import net.poudlardcitizens.npc.ai.MCNavigationStrategy.MCNavigator;
import net.poudlardcitizens.npc.ai.MCTargetStrategy.TargetNavigator;
import net.poudlardcitizens.npc.skin.SkinnableEntity;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.*;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.util.Vector;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.List;

public class NMS {
    private static NMSBridge BRIDGE;
    private static Field MODIFIERS_FIELD = NMS.getField(Field.class, "modifiers");

    private NMS() {
        // util class
    }

    public static boolean addEntityToWorld(Entity entity, SpawnReason custom) {
        return BRIDGE.addEntityToWorld(entity, custom);
    }

    public static void addOrRemoveFromPlayerList(Entity entity, boolean remove) {
        BRIDGE.addOrRemoveFromPlayerList(entity, remove);
    }

    public static void attack(LivingEntity attacker, LivingEntity bukkitTarget) {
        BRIDGE.attack(attacker, bukkitTarget);
    }

    /*
     * Yggdrasil's default implementation of this method silently fails instead of throwing
     * an Exception like it should.
     */
    public static GameProfile fillProfileProperties(GameProfile profile, boolean requireSecure) throws Exception {
        return BRIDGE.fillProfileProperties(profile, requireSecure);
    }

    public static BlockBreaker getBlockBreaker(Entity entity, Block targetBlock, BlockBreakerConfiguration config) {
        return BRIDGE.getBlockBreaker(entity, targetBlock, config);
    }

    public static BossBar getBossBar(Entity entity) {
        return BRIDGE.getBossBar(entity);
    }

    public static BoundingBox getBoundingBox(Entity handle) {
        return BRIDGE.getBoundingBox(handle);
    }

    public static Field getField(Class<?> clazz, String field) {
        if (clazz == null)
            return null;
        Field f = null;
        try {
            f = clazz.getDeclaredField(field);
            f.setAccessible(true);
        } catch (Exception e) {
            Messaging.logTr(Messages.ERROR_GETTING_FIELD, field, e.getLocalizedMessage());
        }
        return f;
    }

    public static Field getFinalField(Class<?> clazz, String field) {
        Field f = getField(clazz, field);
        if (f == null) {
            return null;
        }
        try {
            MODIFIERS_FIELD.setInt(f, f.getModifiers() & ~Modifier.FINAL);
        } catch (Exception e) {
            Messaging.logTr(Messages.ERROR_GETTING_FIELD, field, e.getLocalizedMessage());
            return null;
        }
        return f;
    }

    public static GameProfileRepository getGameProfileRepository() {
        return BRIDGE.getGameProfileRepository();
    }

    public static float getHeadYaw(Entity entity) {
        return BRIDGE.getHeadYaw(entity);
    }

    public static float getHorizontalMovement(Entity bukkitEntity) {
        return BRIDGE.getHorizontalMovement(bukkitEntity);
    }

    public static NPC getNPC(Entity entity) {
        return BRIDGE.getNPC(entity);
    }

    public static List<Entity> getPassengers(Entity entity) {
        return BRIDGE.getPassengers(entity);
    }

    public static GameProfile getProfile(SkullMeta meta) {
        return BRIDGE.getProfile(meta);
    }

    public static String getSound(String flag) throws CommandException {
        return BRIDGE.getSound(flag);
    }

    public static float getSpeedFor(NPC npc) {
        return BRIDGE.getSpeedFor(npc);
    }

    public static float getStepHeight(Entity entity) {
        return BRIDGE.getStepHeight(entity);
    }

    public static MCNavigator getTargetNavigator(Entity entity, Iterable<Vector> dest, NavigatorParameters params) {
        return BRIDGE.getTargetNavigator(entity, dest, params);
    }

    public static MCNavigator getTargetNavigator(Entity entity, Location dest, NavigatorParameters params) {
        return BRIDGE.getTargetNavigator(entity, dest, params);
    }

    public static TargetNavigator getTargetNavigator(Entity entity, Entity target,
                                                     NavigatorParameters parameters) {
        return BRIDGE.getTargetNavigator(entity, target, parameters);
    }

    public static Entity getVehicle(Entity entity) {
        return BRIDGE.getVehicle(entity);
    }

    public static float getVerticalMovement(Entity bukkitEntity) {
        return BRIDGE.getVerticalMovement(bukkitEntity);
    }

    public static boolean isOnGround(Entity entity) {
        return BRIDGE.isOnGround(entity);
    }

    public static void load(CommandManager commands) {
        BRIDGE.load(commands);
    }

    public static void loadBridge(String rev) throws Exception {
        BRIDGE = (NMSBridge) Class.forName("net.citizensnpcs.nms.v" + rev + ".util.NMSImpl").getConstructor()
                .newInstance();
    }

    public static void loadPlugins() {
        BRIDGE.loadPlugins();
    }

    public static void look(Entity entity, float yaw, float pitch) {
        BRIDGE.look(entity, yaw, pitch);
    }

    public static void look(Entity entity, Location to, boolean headOnly, boolean immediate) {
        BRIDGE.look(entity, to, headOnly, immediate);
    }

    public static void look(Entity bhandle, Entity btarget) {
        BRIDGE.look(bhandle, btarget);
    }

    public static void mount(Entity entity, Entity passenger) {
        BRIDGE.mount(entity, passenger);
    }

    public static void openHorseScreen(Tameable horse, Player equipper) {
        BRIDGE.openHorseScreen(horse, equipper);
    }

    public static void playAnimation(PlayerAnimation animation, Player player, int radius) {
        BRIDGE.playAnimation(animation, player, radius);
    }

    public static void registerEntityClass(Class<?> clazz) {
        BRIDGE.registerEntityClass(clazz);
    }

    public static void removeFromServerPlayerList(Player player) {
        BRIDGE.removeFromServerPlayerList(player);
    }

    public static void removeFromWorld(Entity entity) {
        BRIDGE.removeFromWorld(entity);
    }

    public static void removeHookIfNecessary(NPCRegistry npcRegistry, FishHook entity) {
        BRIDGE.removeHookIfNecessary(npcRegistry, entity);
    }

    public static void replaceTrackerEntry(Player player) {
        BRIDGE.replaceTrackerEntry(player);
    }

    public static void sendPositionUpdate(Player excluding, Entity from, Location storedLocation) {
        BRIDGE.sendPositionUpdate(excluding, from, storedLocation);
    }

    public static void sendTabListAdd(Player recipient, Player listPlayer) {
        BRIDGE.sendTabListAdd(recipient, listPlayer);
    }

    public static void sendTabListRemove(Player recipient, Collection<? extends SkinnableEntity> skinnableNPCs) {
        BRIDGE.sendTabListRemove(recipient, skinnableNPCs);
    }

    public static void sendTabListRemove(Player recipient, Player listPlayer) {
        BRIDGE.sendTabListRemove(recipient, listPlayer);
    }

    public static void setDestination(Entity entity, double x, double y, double z, float speed) {
        BRIDGE.setDestination(entity, x, y, z, speed);
    }

    public static void setDummyAdvancement(Player entity) {
        BRIDGE.setDummyAdvancement(entity);
    }

    public static void setHeadYaw(Entity entity, float yaw) {
        BRIDGE.setHeadYaw(entity, yaw);
    }

    public static void setKnockbackResistance(LivingEntity entity, double d) {
        BRIDGE.setKnockbackResistance(entity, d);
    }

    public static void setNavigationTarget(Entity handle, Entity target,
                                           float speed) {
        BRIDGE.setNavigationTarget(handle, target, speed);
    }

    public static void setProfile(SkullMeta meta, GameProfile profile) {
        BRIDGE.setProfile(meta, profile);
    }

    public static void setShouldJump(Entity entity) {
        BRIDGE.setShouldJump(entity);
    }

    public static void setShulkerPeek(Shulker shulker, int peek) {
        BRIDGE.setShulkerPeek(shulker, peek);
    }

    public static void setSitting(Tameable tameable, boolean sitting) {
        BRIDGE.setSitting(tameable, sitting);
    }

    public static void setStepHeight(Entity entity, float height) {
        BRIDGE.setStepHeight(entity, height);
    }

    public static void setVerticalMovement(Entity bukkitEntity, double d) {
        BRIDGE.setVerticalMovement(bukkitEntity, d);
    }

    public static void setWitherCharged(Wither wither, boolean charged) {
        BRIDGE.setWitherCharged(wither, charged);
    }

    public static boolean shouldJump(Entity entity) {
        return BRIDGE.shouldJump(entity);
    }

    public static void shutdown() {
        BRIDGE.shutdown();
    }

    public static boolean tick(Entity next) {
        return BRIDGE.tick(next);
    }

    public static void trySwim(Entity entity) {
        BRIDGE.trySwim(entity);
    }

    public static void trySwim(Entity entity, float power) {
        BRIDGE.trySwim(entity, power);
    }

    public static void updateNavigationWorld(Entity entity, org.bukkit.World world) {
        BRIDGE.updateNavigationWorld(entity, world);
    }

    public static void updatePathfindingRange(NPC npc, float pathfindingRange) {
        BRIDGE.updatePathfindingRange(npc, pathfindingRange);
    }
}
