package fr.poudlardrp.citizens.trait;

import fr.poudlardrp.citizens.api.persistence.Persist;
import fr.poudlardrp.citizens.api.trait.Trait;
import fr.poudlardrp.citizens.api.trait.TraitName;
import org.bukkit.entity.Creeper;

@TraitName("powered")
public class Powered extends Trait implements Toggleable {
    @Persist("")
    private boolean powered;

    public Powered() {
        super("powered");
    }

    @Override
    public void onSpawn() {
        if (npc.getEntity() instanceof Creeper) {
            ((Creeper) npc.getEntity()).setPowered(powered);
        }
    }

    @Override
    public boolean toggle() {
        powered = !powered;
        if (npc.getEntity() instanceof Creeper) {
            ((Creeper) npc.getEntity()).setPowered(powered);
        }
        return powered;
    }

    @Override
    public String toString() {
        return "Powered{" + powered + "}";
    }
}