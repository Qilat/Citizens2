package fr.poudlardrp.citizens.trait;

import fr.poudlardrp.citizens.api.persistence.Persist;
import fr.poudlardrp.citizens.api.trait.Trait;
import fr.poudlardrp.citizens.api.trait.TraitName;
import org.bukkit.entity.Rabbit;

@TraitName("rabbittype")
public class RabbitType extends Trait {
    private Rabbit rabbit;
    @Persist
    private Rabbit.Type type = Rabbit.Type.BROWN;

    public RabbitType() {
        super("rabbittype");
    }

    @Override
    public void onSpawn() {
        rabbit = npc.getEntity() instanceof Rabbit ? (Rabbit) npc.getEntity() : null;
        setType(type);
    }

    public void setType(Rabbit.Type type) {
        this.type = type;
        if (rabbit != null && rabbit.isValid()) {
            rabbit.setRabbitType(type);
        }
    }
}
