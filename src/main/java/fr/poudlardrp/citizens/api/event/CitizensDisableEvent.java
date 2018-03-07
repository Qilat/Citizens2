package fr.poudlardrp.citizens.api.event;

import org.bukkit.event.HandlerList;

public class CitizensDisableEvent extends CitizensEvent {
    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
