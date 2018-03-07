package fr.poudlardrp.citizens.trait;

import fr.poudlardrp.citizens.util.NMS;
import fr.poudlardrp.citizens.api.persistence.Persist;
import fr.poudlardrp.citizens.api.trait.Trait;
import fr.poudlardrp.citizens.api.trait.TraitName;
import org.bukkit.entity.Shulker;

@TraitName("shulkertrait")
public class ShulkerTrait extends Trait {
    @Persist("peek")
    private int peek = 0;

    public ShulkerTrait() {
        super("shulkertrait");
    }

    @Override
    public void onSpawn() {
        setPeek(peek);
    }

    public void setPeek(int peek) {
        this.peek = peek;
        if (npc.getEntity() instanceof Shulker) {
            NMS.setShulkerPeek((Shulker) npc.getEntity(), peek);
        }
    }
}
