package fr.poudlardrp.citizens.api.ai.event;

import fr.poudlardrp.citizens.api.ai.Navigator;
import fr.poudlardrp.citizens.api.ai.StuckAction;
import org.bukkit.event.HandlerList;

public class NavigationStuckEvent extends NavigationEvent {
    private static final HandlerList handlers = new HandlerList();
    private StuckAction action;

    public NavigationStuckEvent(Navigator navigator, StuckAction action) {
        super(navigator);
        this.action = action;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public StuckAction getAction() {
        return action;
    }

    public void setAction(StuckAction action) {
        this.action = action;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
