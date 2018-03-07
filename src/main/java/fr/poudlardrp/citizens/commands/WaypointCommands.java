package fr.poudlardrp.citizens.commands;

import fr.poudlardrp.citizens.Citizens;
import fr.poudlardrp.citizens.util.Messages;
import fr.poudlardrp.citizens.api.command.Command;
import fr.poudlardrp.citizens.api.command.CommandContext;
import fr.poudlardrp.citizens.api.command.Requirements;
import fr.poudlardrp.citizens.api.command.exception.CommandException;
import fr.poudlardrp.citizens.api.npc.NPC;
import fr.poudlardrp.citizens.api.util.Messaging;
import fr.poudlardrp.citizens.trait.waypoint.Waypoints;
import org.bukkit.command.CommandSender;

@Requirements(ownership = true, selected = true)
public class WaypointCommands {
    public WaypointCommands(Citizens plugin) {
    }

    // TODO: refactor into a policy style system
    @Command(
            aliases = {"waypoints", "waypoint", "wp"},
            usage = "disableteleport",
            desc = "Disables teleportation when stuck (temporary command)",
            modifiers = {"disableteleport"},
            min = 1,
            max = 1,
            permission = "citizens.waypoints.disableteleport")
    public void disableTeleporting(CommandContext args, CommandSender sender, NPC npc) throws CommandException {
        npc.getNavigator().getDefaultParameters().stuckAction(null);
        Messaging.sendTr(sender, Messages.WAYPOINT_TELEPORTING_DISABLED);
    }

    @Command(
            aliases = {"waypoints", "waypoint", "wp"},
            usage = "provider [provider name] (-d)",
            desc = "Sets the current waypoint provider",
            modifiers = {"provider"},
            min = 1,
            max = 2,
            flags = "d",
            permission = "citizens.waypoints.provider")
    public void provider(CommandContext args, CommandSender sender, NPC npc) throws CommandException {
        Waypoints waypoints = npc.getTrait(Waypoints.class);
        if (args.argsLength() == 1) {
            if (args.hasFlag('d')) {
                waypoints.describeProviders(sender);
            } else {
                Messaging.sendTr(sender, Messages.CURRENT_WAYPOINT_PROVIDER, waypoints.getCurrentProviderName());
            }
            return;
        }
        boolean success = waypoints.setWaypointProvider(args.getString(1));
        if (!success)
            throw new CommandException("Provider not found.");
        Messaging.sendTr(sender, Messages.WAYPOINT_PROVIDER_SET, args.getString(1));
    }
}
