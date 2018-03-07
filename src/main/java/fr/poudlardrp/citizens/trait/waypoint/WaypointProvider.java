package fr.poudlardrp.citizens.trait.waypoint;

import fr.poudlardrp.citizens.api.command.CommandContext;
import fr.poudlardrp.citizens.api.npc.NPC;
import fr.poudlardrp.citizens.api.persistence.Persistable;
import org.bukkit.command.CommandSender;

public interface WaypointProvider extends Persistable {
    /**
     * Creates an {@link WaypointEditor} with the given {@link CommandSender}.
     *
     * @param sender The player to link the editor with
     * @param args
     * @return The editor
     */
    public WaypointEditor createEditor(CommandSender sender, CommandContext args);

    /**
     * Returns whether this provider has paused execution of waypoints.
     *
     * @return Whether the provider is paused.
     */
    public boolean isPaused();

    /**
     * Pauses waypoint execution.
     *
     * @param paused Whether to pause waypoint execution.
     */
    public void setPaused(boolean paused);

    /**
     * Called when the provider is removed from the NPC.
     */
    public void onRemove();

    /**
     * Called when the {@link NPC} attached to this provider is spawned.
     *
     * @param npc The attached NPC
     */
    public void onSpawn(NPC npc);

    public static interface EnumerableWaypointProvider extends WaypointProvider {
        public Iterable<Waypoint> waypoints();
    }
}