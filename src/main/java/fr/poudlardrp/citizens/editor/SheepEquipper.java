package fr.poudlardrp.citizens.editor;

import fr.poudlardrp.citizens.util.Messages;
import fr.poudlardrp.citizens.api.npc.NPC;
import fr.poudlardrp.citizens.api.util.Messaging;
import fr.poudlardrp.citizens.trait.SheepTrait;
import fr.poudlardrp.citizens.trait.WoolColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Dye;

public class SheepEquipper implements Equipper {
    @Override
    public void equip(Player equipper, NPC toEquip) {
        ItemStack hand = equipper.getInventory().getItemInMainHand();
        Sheep sheep = (Sheep) toEquip.getEntity();
        if (hand.getType() == Material.SHEARS) {
            Messaging.sendTr(equipper, toEquip.getTrait(SheepTrait.class).toggleSheared() ? Messages.SHEARED_SET
                    : Messages.SHEARED_STOPPED, toEquip.getName());
        } else if (hand.getType() == Material.INK_SACK) {
            Dye dye = (Dye) hand.getData();
            if (sheep.getColor() == dye.getColor())
                return;
            DyeColor color = dye.getColor();
            toEquip.getTrait(WoolColor.class).setColor(color);
            Messaging.sendTr(equipper, Messages.EQUIPMENT_EDITOR_SHEEP_COLOURED, toEquip.getName(),
                    color.name().toLowerCase().replace("_", " "));

            hand.setAmount(hand.getAmount() - 1);
        } else {
            toEquip.getTrait(WoolColor.class).setColor(DyeColor.WHITE);
            Messaging.sendTr(equipper, Messages.EQUIPMENT_EDITOR_SHEEP_COLOURED, toEquip.getName(), "white");
        }
        equipper.getInventory().setItemInMainHand(hand);
    }
}