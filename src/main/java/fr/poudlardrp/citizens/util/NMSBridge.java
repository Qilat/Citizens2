package fr.poudlardrp.citizens.util;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.GameProfileRepository;
import fr.poudlardrp.citizens.api.ai.NavigatorParameters;
import fr.poudlardrp.citizens.api.command.CommandManager;
import fr.poudlardrp.citizens.api.npc.BlockBreaker;
import fr.poudlardrp.citizens.api.npc.BlockBreaker.BlockBreakerConfiguration;
import fr.poudlardrp.citizens.api.npc.NPC;
import fr.poudlardrp.citizens.api.npc.NPCRegistry;
import fr.poudlardrp.citizens.npc.ai.MCNavigationStrategy;
import fr.poudlardrp.citizens.npc.ai.MCTargetStrategy;
import fr.poudlardrp.citizens.npc.skin.SkinnableEntity;
import net.minecraft.server.v1_9_R2.CommandException;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.*;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.util.Vector;

import java.util.Collection;
import java.util.List;

public interface NMSBridge {
    public boolean addEntityToWorld(Entity entity, SpawnReason custom);

    public void addOrRemoveFromPlayerList(Entity entity, boolean remove);

    public void attack(LivingEntity attacker, LivingEntity target);

    public GameProfile fillProfileProperties(GameProfile profile, boolean requireSecure) throws Exception;

    public BlockBreaker getBlockBreaker(Entity entity, Block targetBlock, BlockBreakerConfiguration config);

    public BossBar getBossBar(Entity entity);

    public BoundingBox getBoundingBox(Entity handle);

    public GameProfileRepository getGameProfileRepository();

    public float getHeadYaw(Entity entity);

    public float getHorizontalMovement(Entity entity);

    public NPC getNPC(Entity entity);

    public List<Entity> getPassengers(Entity entity);

    public GameProfile getProfile(SkullMeta meta);

    public String getSound(String flag) throws CommandException, CommandException;

    public float getSpeedFor(NPC npc);

    public float getStepHeight(Entity entity);

    public MCTargetStrategy.TargetNavigator getTargetNavigator(Entity handle, Entity target, NavigatorParameters parameters);

    public MCNavigationStrategy.MCNavigator getTargetNavigator(Entity entity, Iterable<Vector> dest, NavigatorParameters params);

    public MCNavigationStrategy.MCNavigator getTargetNavigator(Entity entity, Location dest, NavigatorParameters params);

    public Entity getVehicle(Entity entity);

    public float getVerticalMovement(Entity entity);

    public boolean isOnGround(Entity entity);

    public void load(CommandManager commands);

    public void loadPlugins();

    public void look(Entity from, Entity to);

    public void look(Entity entity, float yaw, float pitch);

    public void look(Entity entity, Location to, boolean headOnly, boolean immediate);

    public void mount(Entity entity, Entity passenger);

    public void openHorseScreen(Tameable horse, Player equipper);

    public void playAnimation(PlayerAnimation animation, Player player, int radius);

    public void registerEntityClass(Class<?> clazz);

    public void removeFromServerPlayerList(Player player);

    public void removeFromWorld(Entity entity);

    public void removeHookIfNecessary(NPCRegistry npcRegistry, FishHook entity);

    public void replaceTrackerEntry(Player player);

    public void sendPositionUpdate(Player excluding, Entity from, Location storedLocation);

    public void sendTabListAdd(Player recipient, Player listPlayer);

    public void sendTabListRemove(Player recipient, Collection<? extends SkinnableEntity> skinnableNPCs);

    public void sendTabListRemove(Player recipient, Player listPlayer);

    public void setDestination(Entity entity, double x, double y, double z, float speed);

    public void setDummyAdvancement(Player entity);

    public void setHeadYaw(Entity entity, float yaw);

    public void setKnockbackResistance(LivingEntity entity, double d);

    public void setNavigationTarget(Entity handle, Entity target, float speed);

    public void setProfile(SkullMeta meta, GameProfile profile);

    public void setShouldJump(Entity entity);

    public void setShulkerPeek(Shulker shulker, int peek);

    public void setSitting(Tameable tameable, boolean sitting);

    public void setStepHeight(Entity entity, float height);

    public void setVerticalMovement(Entity bukkitEntity, double d);

    public void setWitherCharged(Wither wither, boolean charged);

    public boolean shouldJump(Entity entity);

    public void shutdown();

    public boolean tick(Entity next);

    public void trySwim(Entity entity);

    public void trySwim(Entity entity, float power);

    public void updateNavigationWorld(Entity entity, World world);

    public void updatePathfindingRange(NPC npc, float pathfindingRange);
}