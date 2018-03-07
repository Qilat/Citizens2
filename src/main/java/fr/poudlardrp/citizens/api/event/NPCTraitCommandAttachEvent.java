package fr.poudlardrp.citizens.api.event;

import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.Trait;
import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;

public class NPCTraitCommandAttachEvent extends NPCEvent {
    private static final HandlerList handlers = new HandlerList();
    private final CommandSender sender;
    private final Class<? extends Trait> traitClass;

    public NPCTraitCommandAttachEvent(NPC npc, Class<? extends Trait> traitClass, CommandSender sender) {
        super(npc);
        this.traitClass = traitClass;
        this.sender = sender;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public CommandSender getCommandSender() {
        return sender;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public Class<? extends Trait> getTraitClass() {
        return traitClass;
    }
}
