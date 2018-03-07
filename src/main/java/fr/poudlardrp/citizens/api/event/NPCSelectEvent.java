package fr.poudlardrp.citizens.api.event;

import net.citizensnpcs.api.npc.NPC;
import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;

/**
 * Called when an NPC is selected by a player.
 */
public class NPCSelectEvent extends NPCEvent {
    private static final HandlerList handlers = new HandlerList();
    private final CommandSender sender;

    public NPCSelectEvent(NPC npc, CommandSender sender) {
        super(npc);
        this.sender = sender;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    /**
     * Gets the selector of the NPC.
     *
     * @return CommandSender that selected an NPC
     */
    public CommandSender getSelector() {
        return sender;
    }
}