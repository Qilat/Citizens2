package net.poudlardcitizens.editor;

import net.poudlardcitizens.util.NMS;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;

import net.citizensnpcs.api.npc.NPC;

public class HorseEquipper implements Equipper {
    @Override
    public void equip(Player equipper, NPC toEquip) {
        Horse horse = (Horse) toEquip.getEntity();
        NMS.openHorseScreen(horse, equipper);
    }
}
