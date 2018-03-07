package fr.poudlardrp.citizens.editor;

import fr.poudlardrp.citizens.util.Messages;
import fr.poudlardrp.citizens.api.npc.NPC;
import fr.poudlardrp.citizens.api.util.Messaging;
import fr.poudlardrp.citizens.trait.Saddle;
import org.bukkit.Material;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PigEquipper implements Equipper {
    @Override
    public void equip(Player equipper, NPC toEquip) {
        ItemStack hand = equipper.getInventory().getItemInMainHand();
        Pig pig = (Pig) toEquip.getEntity();
        if (hand.getType() == Material.SADDLE) {
            if (!pig.hasSaddle()) {
                toEquip.getTrait(Saddle.class).toggle();
                hand.setAmount(0);
                Messaging.sendTr(equipper, Messages.SADDLED_SET, toEquip.getName());
            }
        } else if (pig.hasSaddle()) {
            equipper.getWorld().dropItemNaturally(pig.getLocation(), new ItemStack(Material.SADDLE, 1));
            toEquip.getTrait(Saddle.class).toggle();
            Messaging.sendTr(equipper, Messages.SADDLED_STOPPED, toEquip.getName());
        }
        equipper.getInventory().setItemInMainHand(hand);
    }
}
