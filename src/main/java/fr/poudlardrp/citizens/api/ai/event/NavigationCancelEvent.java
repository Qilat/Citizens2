package fr.poudlardrp.citizens.api.ai.event;

import fr.poudlardrp.citizens.api.ai.Navigator;
import org.bukkit.event.HandlerList;

public class NavigationCancelEvent extends NavigationCompleteEvent {
    private static final HandlerList handlers = new HandlerList();
    private final CancelReason reason;

    public NavigationCancelEvent(Navigator navigator, CancelReason reason) {
        super(navigator);
        this.reason = reason;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    /**
     * @return The cancellation reason
     */
    public CancelReason getCancelReason() {
        return reason;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
