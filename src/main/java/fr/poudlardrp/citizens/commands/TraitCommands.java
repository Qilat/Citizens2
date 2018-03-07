package fr.poudlardrp.citizens.commands;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import fr.poudlardrp.citizens.util.Messages;
import fr.poudlardrp.citizens.util.StringHelper;
import fr.poudlardrp.citizens.api.CitizensAPI;
import fr.poudlardrp.citizens.api.command.Command;
import fr.poudlardrp.citizens.api.command.CommandConfigurable;
import fr.poudlardrp.citizens.api.command.CommandContext;
import fr.poudlardrp.citizens.api.command.Requirements;
import fr.poudlardrp.citizens.api.command.exception.CommandException;
import fr.poudlardrp.citizens.api.command.exception.NoPermissionsException;
import fr.poudlardrp.citizens.api.event.NPCTraitCommandAttachEvent;
import fr.poudlardrp.citizens.api.npc.NPC;
import fr.poudlardrp.citizens.api.trait.Trait;
import fr.poudlardrp.citizens.api.util.Messaging;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.List;

@Requirements(selected = true, ownership = true)
public class TraitCommands {
    @Command(
            aliases = {"trait", "tr"},
            usage = "add [trait name]...",
            desc = "Adds traits to the NPC",
            modifiers = {"add", "a"},
            min = 2,
            permission = "citizens.npc.trait")
    public void add(CommandContext args, CommandSender sender, NPC npc) throws CommandException {
        List<String> added = Lists.newArrayList();
        List<String> failed = Lists.newArrayList();
        for (String traitName : Splitter.on(',').split(args.getJoinedStrings(1))) {
            if (!sender.hasPermission("citizens.npc.trait." + traitName)
                    && !sender.hasPermission("citizens.npc.trait.*")) {
                failed.add(String.format("%s: No permission", traitName));
                continue;
            }

            Class<? extends Trait> clazz = CitizensAPI.getTraitFactory().getTraitClass(traitName);
            if (clazz == null) {
                failed.add(String.format("%s: Trait not found", traitName));
                continue;
            }
            if (npc.hasTrait(clazz)) {
                failed.add(String.format("%s: Already added", traitName));
                continue;
            }
            addTrait(npc, clazz, sender);
            added.add(StringHelper.wrap(traitName));
        }
        if (added.size() > 0)
            Messaging.sendTr(sender, Messages.TRAITS_ADDED, Joiner.on(", ").join(added));
        if (failed.size() > 0)
            Messaging.sendTr(sender, Messages.TRAITS_FAILED_TO_ADD, Joiner.on(", ").join(failed));
    }

    private void addTrait(NPC npc, Class<? extends Trait> clazz, CommandSender sender) {
        npc.addTrait(clazz);
        Bukkit.getPluginManager().callEvent(new NPCTraitCommandAttachEvent(npc, clazz, sender));
    }

    @Command(
            aliases = {"traitc", "trc"},
            usage = "[trait name] (flags)",
            desc = "Configures a trait",
            modifiers = {"*"},
            min = 1,
            flags = "*",
            permission = "citizens.npc.trait-configure")
    public void configure(CommandContext args, CommandSender sender, NPC npc) throws CommandException {
        String traitName = args.getString(0);
        if (!sender.hasPermission("citizens.npc.trait-configure." + traitName)
                && !sender.hasPermission("citizens.npc.trait-configure.*"))
            throw new NoPermissionsException();
        Class<? extends Trait> clazz = CitizensAPI.getTraitFactory().getTraitClass(args.getString(0));
        if (clazz == null)
            throw new CommandException(Messages.TRAIT_NOT_FOUND);
        if (!CommandConfigurable.class.isAssignableFrom(clazz))
            throw new CommandException(Messages.TRAIT_NOT_CONFIGURABLE);
        if (!npc.hasTrait(clazz))
            throw new CommandException(Messages.TRAIT_NOT_FOUND_ON_NPC);
        CommandConfigurable trait = (CommandConfigurable) npc.getTrait(clazz);
        trait.configure(args);
    }

    @Command(
            aliases = {"trait", "tr"},
            usage = "remove [trait name]...",
            desc = "Removes traits on the NPC",
            modifiers = {"remove", "rem", "r"},
            min = 1,
            permission = "citizens.npc.trait")
    public void remove(CommandContext args, CommandSender sender, NPC npc) throws CommandException {
        List<String> removed = Lists.newArrayList();
        List<String> failed = Lists.newArrayList();
        for (String traitName : Splitter.on(',').split(args.getJoinedStrings(0))) {
            if (!sender.hasPermission("citizens.npc.trait." + traitName)
                    && !sender.hasPermission("citizens.npc.trait.*")) {
                failed.add(String.format("%s: No permission", traitName));
                continue;
            }

            Class<? extends Trait> clazz = CitizensAPI.getTraitFactory().getTraitClass(traitName);
            if (clazz == null) {
                failed.add(String.format("%s: Trait not found", traitName));
                continue;
            }
            boolean hasTrait = npc.hasTrait(clazz);
            if (!hasTrait) {
                failed.add(String.format("%s: Trait not attached", traitName));
                continue;
            }
            npc.removeTrait(clazz);
            removed.add(StringHelper.wrap(traitName));
        }
        if (removed.size() > 0)
            Messaging.sendTr(sender, Messages.TRAITS_REMOVED, Joiner.on(", ").join(removed));
        if (failed.size() > 0)
            Messaging.sendTr(sender, Messages.FAILED_TO_REMOVE, Joiner.on(", ").join(failed));
    }

    @Command(
            aliases = {"trait", "tr"},
            usage = "[trait name], [trait name]...",
            desc = "Toggles traits on the NPC",
            modifiers = {"*"},
            min = 1,
            permission = "citizens.npc.trait")
    public void toggle(CommandContext args, CommandSender sender, NPC npc) throws CommandException {
        List<String> added = Lists.newArrayList();
        List<String> removed = Lists.newArrayList();
        List<String> failed = Lists.newArrayList();
        for (String traitName : Splitter.on(',').split(args.getJoinedStrings(0))) {
            if (!sender.hasPermission("citizens.npc.trait." + traitName)
                    && !sender.hasPermission("citizens.npc.trait.*")) {
                failed.add(String.format("%s: No permission", traitName));
                continue;
            }

            Class<? extends Trait> clazz = CitizensAPI.getTraitFactory().getTraitClass(traitName);
            if (clazz == null) {
                failed.add(String.format("%s: Trait not found", traitName));
                continue;
            }
            boolean remove = npc.hasTrait(clazz);
            if (remove) {
                npc.removeTrait(clazz);
                removed.add(StringHelper.wrap(traitName));
                continue;
            }
            addTrait(npc, clazz, sender);
            added.add(StringHelper.wrap(traitName));
        }
        if (added.size() > 0)
            Messaging.sendTr(sender, Messages.TRAITS_ADDED, Joiner.on(", ").join(added));
        if (removed.size() > 0)
            Messaging.sendTr(sender, Messages.TRAITS_REMOVED, Joiner.on(", ").join(removed));
        if (failed.size() > 0)
            Messaging.sendTr(sender, Messages.TRAITS_FAILED_TO_CHANGE, Joiner.on(", ").join(failed));
    }
}