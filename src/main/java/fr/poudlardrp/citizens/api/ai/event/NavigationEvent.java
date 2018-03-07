package fr.poudlardrp.citizens.api.ai.event;

import fr.poudlardrp.citizens.api.ai.Navigator;
import fr.poudlardrp.citizens.api.npc.NPC;
import org.bukkit.event.Event;

public abstract class NavigationEvent extends Event {
    private final Navigator navigator;

    protected NavigationEvent(Navigator navigator) {
        this.navigator = navigator;
    }

    /**
     * @return The {@link Navigator} involved in this event
     */
    public Navigator getNavigator() {
        return navigator;
    }

    /**
     * @return The {@link NPC} involved in this event
     */
    public NPC getNPC() {
        return navigator.getNPC();
    }
}
