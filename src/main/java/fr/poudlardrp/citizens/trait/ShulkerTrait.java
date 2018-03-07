package fr.poudlardrp.citizens.trait;

import fr.poudlardrp.citizens.util.NMS;
import net.citizensnpcs.api.persistence.Persist;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.trait.TraitName;
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
