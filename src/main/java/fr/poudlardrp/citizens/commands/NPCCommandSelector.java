package fr.poudlardrp.citizens.commands;

import com.google.common.collect.Lists;
import fr.poudlardrp.citizens.util.Messages;
import fr.poudlardrp.citizens.util.Util;
import fr.poudlardrp.citizens.api.CitizensAPI;
import fr.poudlardrp.citizens.api.command.CommandContext;
import fr.poudlardrp.citizens.api.command.CommandMessages;
import fr.poudlardrp.citizens.api.command.exception.*;
import fr.poudlardrp.citizens.api.npc.NPC;
import fr.poudlardrp.citizens.api.npc.NPCRegistry;
import fr.poudlardrp.citizens.api.util.Messaging;
import org.bukkit.command.CommandSender;
import org.bukkit.conversations.*;

import java.util.List;

public class NPCCommandSelector extends NumericPrompt {
    private final Callback callback;
    private final List<NPC> choices;

    public NPCCommandSelector(Callback callback, List<NPC> possible) {
        this.callback = callback;
        this.choices = possible;
    }

    public static void start(Callback callback, Conversable player, List<NPC> possible) {
        final Conversation conversation = new ConversationFactory(CitizensAPI.getPlugin()).withLocalEcho(false)
                .withEscapeSequence("exit").withModality(false)
                .withFirstPrompt(new NPCCommandSelector(callback, possible)).buildConversation(player);
        conversation.begin();
    }

    public static void startWithCallback(Callback callback, NPCRegistry npcRegistry, CommandSender sender,
                                         CommandContext args, String raw) throws CommandException {
        try {
            int id = Integer.parseInt(raw);
            callback.run(npcRegistry.getById(id));
            return;
        } catch (NumberFormatException ex) {
            String name = args.getString(1);
            List<NPC> possible = Lists.newArrayList();
            double range = -1;
            if (args.hasValueFlag("r")) {
                range = Math.abs(args.getFlagDouble("r"));
            }
            for (NPC test : npcRegistry) {
                if (test.getName().equalsIgnoreCase(name)) {
                    if (range > 0 && test.isSpawned() && !Util.locationWithinRange(args.getSenderLocation(),
                            test.getEntity().getLocation(), range))
                        continue;
                    possible.add(test);
                }
            }
            if (possible.size() == 1) {
                callback.run(possible.get(0));
            } else if (possible.size() > 1) {
                NPCCommandSelector.start(callback, (Conversable) sender, possible);
                return;
            }
        }
    }

    @Override
    protected Prompt acceptValidatedInput(ConversationContext context, Number input) {
        boolean found = false;
        for (NPC npc : choices) {
            if (input.intValue() == npc.getId()) {
                found = true;
                break;
            }
        }
        CommandSender sender = (CommandSender) context.getForWhom();
        if (!found) {
            Messaging.sendErrorTr(sender, Messages.SELECTION_PROMPT_INVALID_CHOICE, input);
            return this;
        }
        NPC toSelect = CitizensAPI.getNPCRegistry().getById(input.intValue());
        try {
            callback.run(toSelect);
        } catch (ServerCommandException ex) {
            Messaging.sendTr(sender, CommandMessages.MUST_BE_INGAME);
        } catch (CommandUsageException ex) {
            Messaging.sendError(sender, ex.getMessage());
            Messaging.sendError(sender, ex.getUsage());
        } catch (UnhandledCommandException ex) {
            ex.printStackTrace();
        } catch (WrappedCommandException ex) {
            ex.getCause().printStackTrace();
        } catch (CommandException ex) {
            Messaging.sendError(sender, ex.getMessage());
        } catch (NumberFormatException ex) {
            Messaging.sendErrorTr(sender, CommandMessages.INVALID_NUMBER);
        }
        return null;
    }

    @Override
    public String getPromptText(ConversationContext context) {
        String text = Messaging.tr(Messages.SELECTION_PROMPT);
        for (NPC npc : choices) {
            text += "\n    - " + npc.getId();
        }
        return text;
    }

    public static interface Callback {
        public void run(NPC npc) throws CommandException;
    }
}