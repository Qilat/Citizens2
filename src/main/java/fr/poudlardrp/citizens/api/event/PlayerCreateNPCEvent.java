package fr.poudlardrp.citizens.api.event;

import fr.poudlardrp.citizens.api.npc.NPC;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class PlayerCreateNPCEvent extends CommandSenderCreateNPCEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    public PlayerCreateNPCEvent(Player player, NPC npc) {
        super(player, npc);
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    /**
     * @return The {@link Player} creating the NPC.
     */
    @Override
    public Player getCreator() {
        return (Player) super.getCreator();
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
