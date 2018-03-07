package fr.poudlardrp.citizens.api.npc;

import fr.poudlardrp.citizens.api.ai.GoalController;
import fr.poudlardrp.citizens.api.ai.Navigator;
import fr.poudlardrp.citizens.api.ai.speech.SpeechController;
import fr.poudlardrp.citizens.api.astar.Agent;
import fr.poudlardrp.citizens.api.event.DespawnReason;
import fr.poudlardrp.citizens.api.event.NPCDespawnEvent;
import fr.poudlardrp.citizens.api.npc.BlockBreaker.BlockBreakerConfiguration;
import fr.poudlardrp.citizens.api.trait.Trait;
import fr.poudlardrp.citizens.api.trait.TraitFactory;
import fr.poudlardrp.citizens.api.util.DataKey;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import java.util.UUID;

/**
 * Represents an NPC with optional {@link Trait}s.
 */
public interface NPC extends Agent, Cloneable {
    String AMBIENT_SOUND_METADATA = "ambient-sound";
    String COLLIDABLE_METADATA = "collidable";
    String DAMAGE_OTHERS_METADATA = "damage-others";
    String DEATH_SOUND_METADATA = "death-sound";
    String DEFAULT_PROTECTED_METADATA = "protected";
    String DROPS_ITEMS_METADATA = "drops-items";
    String FLYABLE_METADATA = "flyable";
    String GLOWING_COLOR_METADATA = "glowing-color";
    String GLOWING_METADATA = "glowing";
    String HURT_SOUND_METADATA = "hurt-sound";
    String ITEM_DATA_METADATA = "item-type-data";
    String ITEM_ID_METADATA = "item-type-id";
    String LEASH_PROTECTED_METADATA = "protected-leash";
    String MINECART_ITEM_DATA_METADATA = "minecart-item-data";
    String MINECART_ITEM_METADATA = "minecart-item-name";
    String MINECART_OFFSET_METADATA = "minecart-item-offset";
    String NAMEPLATE_VISIBLE_METADATA = "nameplate-visible";
    String PLAYER_SKIN_TEXTURE_PROPERTIES_METADATA = "player-skin-textures";
    String PLAYER_SKIN_TEXTURE_PROPERTIES_SIGN_METADATA = "player-skin-signature";
    String PLAYER_SKIN_USE_LATEST = "player-skin-use-latest";
    String PLAYER_SKIN_UUID_METADATA = "player-skin-name";
    String RESPAWN_DELAY_METADATA = "respawn-delay";
    String SCOREBOARD_FAKE_TEAM_NAME_METADATA = "fake-scoreboard-team-name";
    String SHOULD_SAVE_METADATA = "should-save";
    String SILENT_METADATA = "silent-sounds";
    String SWIMMING_METADATA = "swim";
    String TARGETABLE_METADATA = "protected-target";

    /**
     * Adds a trait to this NPC. This will use the {@link TraitFactory} defined for this NPC to construct and attach a
     * trait using {@link #addTrait(Trait)}.
     *
     * @param trait The class of the trait to add
     */
    void addTrait(Class<? extends Trait> trait);

    /**
     * Adds a trait to this NPC.
     *
     * @param trait Trait to add
     */
    void addTrait(Trait trait);

    /**
     * @return A clone of the NPC. May not be an exact copy depending on the {@link Trait}s installed.
     */
    NPC clone();

    /**
     * @return The metadata store of this NPC.
     */
    MetadataStore data();

    /**
     * Despawns this NPC. This is equivalent to calling {@link #despawn(DespawnReason)} with
     * {@link DespawnReason#PLUGIN}.
     *
     * @return Whether this NPC was able to despawn
     */
    boolean despawn();

    /**
     * Despawns this NPC.
     *
     * @param reason The reason for despawning, for use in {@link NPCDespawnEvent}
     * @return Whether this NPC was able to despawn
     */
    boolean despawn(DespawnReason reason);

    /**
     * Permanently removes this NPC and all data about it from the registry it's attached to.
     */
    void destroy();

    /**
     * Faces a given {@link Location} if the NPC is spawned.
     */
    void faceLocation(Location location);

    BlockBreaker getBlockBreaker(Block targetBlock, BlockBreakerConfiguration config);

    /**
     * Gets the default {@link GoalController} of this NPC.
     *
     * @return Default goal controller
     */
    GoalController getDefaultGoalController();

    /**
     * Gets the default {@link SpeechController} of this NPC.
     *
     * @return Default speech controller
     */
    SpeechController getDefaultSpeechController();

    /**
     * Gets the Bukkit entity associated with this NPC. This may be <code>null</code> if {@link #isSpawned()} is false.
     *
     * @return Entity associated with this NPC
     */
    Entity getEntity();

    /**
     * Gets the full name of this NPC.
     *
     * @return Full name of this NPC
     */
    String getFullName();

    /**
     * Gets the unique ID of this NPC. This is not guaranteed to be globally unique across server sessions.
     *
     * @return ID of this NPC
     */
    int getId();

    /**
     * Gets the name of this NPC with color codes stripped.
     *
     * @return Stripped name of this NPC
     */
    String getName();

    /**
     * Sets the name of this NPC.
     *
     * @param name Name to give this NPC
     */
    void setName(String name);

    /**
     * @return The {@link Navigator} of this NPC.
     */
    Navigator getNavigator();

    /**
     * @return The {@link NPCRegistry} that created this NPC.
     */
    NPCRegistry getOwningRegistry();

    /**
     * If the NPC is not spawned, then this method will return the last known location, or null if it has never been
     * spawned. Otherwise, it is equivalent to calling <code>npc.getBukkitEntity().getLocation()</code>.
     *
     * @return The stored location, or <code>null</code> if none was found.
     */
    Location getStoredLocation();

    /**
     * Gets a trait from the given class. If the NPC does not currently have the trait then it will be created and
     * attached using {@link #addTrait(Class)} .
     *
     * @param trait Trait to get
     * @return Trait with the given name
     */
    <T extends Trait> T getTrait(Class<T> trait);

    /**
     * Returns the currently attached {@link Trait}s
     *
     * @return An Iterable of the current traits
     */
    Iterable<Trait> getTraits();

    /**
     * Gets the unique id of this NPC. This is guaranteed to be unique for all NPCs.
     *
     * @return The unique id
     */
    UUID getUniqueId();

    /**
     * Checks if this NPC has the given trait.
     *
     * @param trait Trait to check
     * @return Whether this NPC has the given trait
     */
    boolean hasTrait(Class<? extends Trait> trait);

    /**
     * Returns whether this NPC is flyable or not.
     *
     * @return Whether this NPC is flyable
     */
    boolean isFlyable();

    /**
     * Sets whether this NPC is <tt>flyable</tt> or not. Note that this is intended for normally <em>ground-based</em>
     * entities only - it will generally have no effect on mob types that were originally flyable.
     *
     * @param flyable
     */
    void setFlyable(boolean flyable);

    /**
     * Gets whether this NPC is protected from damage, movement and other events that players and mobs use to change the
     * entity state of the NPC.
     *
     * @return Whether this NPC is protected
     */
    boolean isProtected();

    /**
     * A helper method for using {@link #DEFAULT_PROTECTED_METADATA} to set the NPC as protected or not protected from
     * damage/entity target events. Equivalent to
     * <code>npc.data().set(NPC.DEFAULT_PROTECTED_METADATA, isProtected);</code>
     *
     * @param isProtected Whether the NPC should be protected
     */
    void setProtected(boolean isProtected);

    /**
     * Gets whether this NPC is currently spawned.
     *
     * @return Whether this NPC is spawned
     */
    boolean isSpawned();

    /**
     * Loads the {@link NPC} from the given {@link DataKey}. This reloads all traits, respawns the NPC and sets it up
     * for execution. Should not be called often.
     *
     * @param key The root data key
     */
    void load(DataKey key);

    /**
     * Removes a trait from this NPC.
     *
     * @param trait Trait to remove
     */
    void removeTrait(Class<? extends Trait> trait);

    /**
     * Saves the {@link NPC} to the given {@link DataKey}. This includes all metadata, traits, and spawn information
     * that will allow it to respawn at a later time via {@link #load(DataKey)}.
     *
     * @param key The root data key
     */
    void save(DataKey key);

    /**
     * Sets the {@link EntityType} of this NPC. Currently only accepts <em>living</em> entity types, with scope for
     * additional types in the future. The NPC will respawned if currently spawned, or will remain despawned otherwise.
     *
     * @param type The new mob type
     * @throws IllegalArgumentException If the type is not a living entity type
     */
    void setBukkitEntityType(EntityType type);

    /**
     * Attempts to spawn this NPC.
     *
     * @param location Location to spawn this NPC
     * @return Whether this NPC was able to spawn at the location
     */
    boolean spawn(Location location);

    /**
     * An alternative to bukkit method that teleports passengers as well.
     *
     * @param location The destination location
     * @param cause    The cause for teleporting
     */
    void teleport(Location location, TeleportCause cause);
}