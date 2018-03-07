package fr.poudlardrp.citizens.editor;

import fr.poudlardrp.citizens.api.npc.NPC;
import org.bukkit.entity.Player;

public interface Equipper {
    public void equip(Player equipper, NPC toEquip);
}