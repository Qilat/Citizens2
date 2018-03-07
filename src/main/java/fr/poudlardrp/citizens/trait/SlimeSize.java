package fr.poudlardrp.citizens.trait;

import fr.poudlardrp.citizens.util.Messages;
import fr.poudlardrp.citizens.api.persistence.Persist;
import fr.poudlardrp.citizens.api.trait.Trait;
import fr.poudlardrp.citizens.api.trait.TraitName;
import fr.poudlardrp.citizens.api.util.Messaging;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Slime;

@TraitName("slimesize")
public class SlimeSize extends Trait {
    @Persist
    private int size = 3;
    private boolean slime;

    public SlimeSize() {
        super("slimesize");
    }

    public void describe(CommandSender sender) {
        Messaging.sendTr(sender, Messages.SIZE_DESCRIPTION, npc.getName(), size);
    }

    @Override
    public void onSpawn() {
        if (!(npc.getEntity() instanceof Slime)) {
            slime = false;
            return;
        }
        ((Slime) npc.getEntity()).setSize(size);
        slime = true;
    }

    public void setSize(int size) {
        this.size = size;
        if (slime)
            ((Slime) npc.getEntity()).setSize(size);
    }
}
