package fr.poudlardrp.citizens.api.command;

import com.google.common.collect.Sets;
import fr.poudlardrp.citizens.api.CitizensAPI;
import fr.poudlardrp.citizens.api.command.exception.CommandException;
import fr.poudlardrp.citizens.api.command.exception.RequirementMissingException;
import fr.poudlardrp.citizens.api.npc.NPC;
import fr.poudlardrp.citizens.api.trait.Trait;
import fr.poudlardrp.citizens.api.trait.trait.MobType;
import fr.poudlardrp.citizens.api.trait.trait.Owner;
import fr.poudlardrp.citizens.api.util.Messaging;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;

public class RequirementsProcessor implements CommandAnnotationProcessor {
    @Override
    public Class<? extends Annotation> getAnnotationClass() {
        return Requirements.class;
    }

    @Override
    public void process(CommandSender sender, CommandContext context, Annotation instance, Object[] methodArgs)
            throws CommandException {
        Requirements requirements = (Requirements) instance;
        NPC npc = (methodArgs.length >= 3 && methodArgs[2] instanceof NPC) ? (NPC) methodArgs[2] : null;

        boolean canRedefineSelected = context.hasValueFlag("id") && sender.hasPermission("npc.select");
        String error = Messaging.tr(CommandMessages.MUST_HAVE_SELECTED);
        if (canRedefineSelected) {
            npc = CitizensAPI.getNPCRegistry().getById(context.getFlagInteger("id"));
            if (methodArgs.length >= 3) {
                methodArgs[2] = npc;
            }
            if (npc == null) {
                error += ' ' + Messaging.tr(CommandMessages.ID_NOT_FOUND, context.getFlagInteger("id"));
            }
        }
        if (requirements.selected() && npc == null) {
            throw new RequirementMissingException(error);
        }

        if (requirements.ownership() && npc != null && !sender.hasPermission("citizens.admin")
                && !npc.getTrait(Owner.class).isOwnedBy(sender)) {
            throw new RequirementMissingException(Messaging.tr(CommandMessages.MUST_BE_OWNER));
        }

        if (npc == null)
            return;

        for (Class<? extends Trait> clazz : requirements.traits()) {
            if (!npc.hasTrait(clazz)) {
                throw new RequirementMissingException(
                        Messaging.tr(CommandMessages.MISSING_TRAIT, clazz.getSimpleName()));
            }
        }

        Set<EntityType> types = Sets.newEnumSet(Arrays.asList(requirements.types()), EntityType.class);
        if (types.contains(EntityType.UNKNOWN)) {
            types = EnumSet.allOf(EntityType.class);
        }
        types.removeAll(Sets.newHashSet(requirements.excludedTypes()));

        EntityType type = npc.getTrait(MobType.class).getType();
        if (!types.contains(type)) {
            throw new RequirementMissingException(Messaging.tr(CommandMessages.REQUIREMENTS_INVALID_MOB_TYPE,
                    type.name().toLowerCase().replace('_', ' ')));
        }
        if (requirements.livingEntity() && !type.isAlive()) {
            throw new RequirementMissingException(
                    Messaging.tr(CommandMessages.REQUIREMENTS_MUST_BE_LIVING_ENTITY, methodArgs));
        }
    }
}