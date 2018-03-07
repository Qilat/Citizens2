package fr.poudlardrp.citizens.api.ai.event;

import fr.poudlardrp.citizens.api.ai.Navigator;
import org.bukkit.event.HandlerList;

public class NavigationCompleteEvent extends NavigationEvent {
    private static final HandlerList handlers = new HandlerList();

    public NavigationCompleteEvent(Navigator navigator) {
        super(navigator);
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
