package fr.poudlardrp.citizens.api.event;

import net.citizensnpcs.api.npc.NPC;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class CommandSenderCreateNPCEvent extends NPCCreateEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private final CommandSender creator;
    private boolean cancelled;
    private String reason;

    public CommandSenderCreateNPCEvent(CommandSender sender, NPC npc) {
        super(npc);
        creator = sender;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    /**
     * @return The reason for cancelling the event
     * @see #getCancelReason()
     */
    public String getCancelReason() {
        return reason;
    }

    /**
     * Sets the reason for cancelling the event. This will be sent to the {@link CommandSender} creator to explain why
     * the NPC cannot be created.
     *
     * @param reason The reason explaining the cancellation
     */
    public void setCancelReason(String reason) {
        this.reason = reason;
    }

    /**
     * @return The {@link CommandSender} creating the NPC.
     */
    public CommandSender getCreator() {
        return creator;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }
}
