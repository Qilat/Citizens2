package fr.poudlardrp.citizens.editor;

import fr.poudlardrp.citizens.util.NMS;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;

public class HorseEquipper implements Equipper {
    @Override
    public void equip(Player equipper, NPC toEquip) {
        Horse horse = (Horse) toEquip.getEntity();
        NMS.openHorseScreen(horse, equipper);
    }
}
