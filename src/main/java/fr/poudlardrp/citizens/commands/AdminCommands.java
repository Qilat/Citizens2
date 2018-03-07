package fr.poudlardrp.citizens.commands;

import fr.poudlardrp.citizens.Citizens;
import fr.poudlardrp.citizens.util.Messages;
import fr.poudlardrp.citizens.util.StringHelper;
import fr.poudlardrp.citizens.api.command.Command;
import fr.poudlardrp.citizens.api.command.CommandContext;
import fr.poudlardrp.citizens.api.command.Requirements;
import fr.poudlardrp.citizens.api.command.exception.CommandException;
import fr.poudlardrp.citizens.api.exception.NPCLoadException;
import fr.poudlardrp.citizens.api.npc.NPC;
import fr.poudlardrp.citizens.api.util.Messaging;
import org.bukkit.command.CommandSender;

@Requirements
public class AdminCommands {
    private final Citizens plugin;

    public AdminCommands(Citizens plugin) {
        this.plugin = plugin;
    }

    @Command(aliases = {"citizens"}, desc = "Show basic plugin information", max = 0, permission = "citizens.admin")
    public void citizens(CommandContext args, CommandSender sender, NPC npc) throws CommandException {
        Messaging.send(sender,
                "          " + StringHelper.wrapHeader("<e>Citizens v" + plugin.getDescription().getVersion()));
        Messaging.send(sender, "     <7>-- <c>Written by fullwall and aPunch");
        Messaging.send(sender, "     <7>-- <c>Source Code: http://github.com/CitizensDev");
        Messaging.send(sender, "     <7>-- <c>Website: " + plugin.getDescription().getWebsite());
    }

    @Command(
            aliases = {"citizens"},
            usage = "reload",
            desc = "Reload Citizens",
            modifiers = {"reload"},
            min = 1,
            max = 1,
            permission = "citizens.admin")
    public void reload(CommandContext args, CommandSender sender, NPC npc) throws CommandException {
        Messaging.sendTr(sender, Messages.CITIZENS_RELOADING);
        try {
            plugin.reload();
            Messaging.sendTr(sender, Messages.CITIZENS_RELOADED);
        } catch (NPCLoadException ex) {
            ex.printStackTrace();
            throw new CommandException(Messages.CITIZENS_RELOAD_ERROR);
        }
    }

    @Command(
            aliases = {"citizens"},
            usage = "save (-a)",
            desc = "Save NPCs",
            help = Messages.COMMAND_SAVE_HELP,
            modifiers = {"save"},
            min = 1,
            max = 1,
            flags = "a",
            permission = "citizens.admin")
    public void save(CommandContext args, CommandSender sender, NPC npc) {
        Messaging.sendTr(sender, Messages.CITIZENS_SAVING);
        plugin.storeNPCs(args);
        Messaging.sendTr(sender, Messages.CITIZENS_SAVED);
    }
}