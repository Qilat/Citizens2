package fr.poudlardrp.citizens.api.event;

import fr.poudlardrp.citizens.api.npc.NPC;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityCombustEvent;

public class NPCCombustEvent extends NPCEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private final EntityCombustEvent event;
    private boolean cancelled;

    public NPCCombustEvent(EntityCombustEvent event, NPC npc) {
        super(npc);
        this.event = event;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    /**
     * @return the amount of time (in seconds) the combustee should be alight for
     */
    public int getDuration() {
        return event.getDuration();
    }

    /**
     * The number of seconds the combustee should be alight for.
     * <p/>
     * This value will only ever increase the combustion time, not decrease existing combustion times.
     *
     * @param duration the time in seconds to be alight for.
     */
    public void setDuration(int duration) {
        event.setDuration(duration);
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
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
