package fr.poudlardrp.citizens.trait;

import fr.poudlardrp.citizens.api.persistence.Persist;
import fr.poudlardrp.citizens.api.trait.Trait;
import fr.poudlardrp.citizens.api.trait.TraitName;
import org.bukkit.util.Vector;

@TraitName("gravity")
public class Gravity extends Trait implements Toggleable {
    @Persist
    private boolean enabled;

    public Gravity() {
        super("gravity");
    }

    public void gravitate(boolean gravitate) {
        enabled = gravitate;
    }

    public boolean hasGravity() {
        return !enabled;
    }

    @Override
    public void run() {
        if (!npc.isSpawned())
            return;
        if (!enabled || npc.getNavigator().isNavigating())
            return;
        Vector vector = npc.getEntity().getVelocity();
        vector.setY(Math.max(0, vector.getY()));
        npc.getEntity().setVelocity(vector);
    }

    @Override
    public boolean toggle() {
        return enabled = !enabled;
    }
}
