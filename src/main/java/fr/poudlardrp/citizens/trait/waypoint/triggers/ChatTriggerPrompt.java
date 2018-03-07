package fr.poudlardrp.citizens.trait.waypoint.triggers;

import com.google.common.collect.Lists;
import fr.poudlardrp.citizens.util.Messages;
import fr.poudlardrp.citizens.api.util.Messaging;
import org.bukkit.command.CommandSender;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;

import java.util.List;

public class ChatTriggerPrompt extends StringPrompt implements WaypointTriggerPrompt {
    private final List<String> lines = Lists.newArrayList();
    private double radius = -1;

    @Override
    public Prompt acceptInput(ConversationContext context, String input) {
        if (input.equalsIgnoreCase("back"))
            return (Prompt) context.getSessionData("previous");
        if (input.startsWith("radius")) {
            try {
                radius = Double.parseDouble(input.split(" ")[1]);
            } catch (NumberFormatException e) {
                Messaging.sendErrorTr((CommandSender) context.getForWhom(),
                        Messages.WAYPOINT_TRIGGER_CHAT_INVALID_RADIUS);
            } catch (IndexOutOfBoundsException e) {
                Messaging.sendErrorTr((CommandSender) context.getForWhom(), Messages.WAYPOINT_TRIGGER_CHAT_NO_RADIUS);
            }
            return this;
        }
        if (input.equalsIgnoreCase("finish")) {
            context.setSessionData(CREATED_TRIGGER_KEY, new ChatTrigger(radius, lines));
            return (Prompt) context.getSessionData(RETURN_PROMPT_KEY);
        }
        lines.add(input);
        return this;
    }

    @Override
    public String getPromptText(ConversationContext context) {
        Messaging.sendTr((CommandSender) context.getForWhom(), Messages.CHAT_TRIGGER_PROMPT);
        return "";
    }
}
