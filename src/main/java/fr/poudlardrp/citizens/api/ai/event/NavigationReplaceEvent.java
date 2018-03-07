package fr.poudlardrp.citizens.api.ai.event;

import net.citizensnpcs.api.ai.Navigator;
import org.bukkit.event.HandlerList;

public class NavigationReplaceEvent extends NavigationCancelEvent {
    private static final HandlerList handlers = new HandlerList();

    public NavigationReplaceEvent(Navigator navigator) {
        super(navigator, CancelReason.REPLACE);
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
