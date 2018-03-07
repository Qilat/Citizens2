package fr.poudlardrp.citizens.api.npc;

import org.bukkit.command.CommandSender;

public interface NPCSelector {
    NPC getSelected(CommandSender sender);
}
