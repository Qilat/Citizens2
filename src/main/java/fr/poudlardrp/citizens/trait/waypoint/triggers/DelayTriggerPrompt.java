package fr.poudlardrp.citizens.trait.waypoint.triggers;

import fr.poudlardrp.citizens.util.Messages;
import fr.poudlardrp.citizens.api.util.Messaging;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.NumericPrompt;
import org.bukkit.conversations.Prompt;

public class DelayTriggerPrompt extends NumericPrompt implements WaypointTriggerPrompt {
    @Override
    protected Prompt acceptValidatedInput(ConversationContext context, Number input) {
        int delay = Math.max(input.intValue(), 0);
        context.setSessionData(WaypointTriggerPrompt.CREATED_TRIGGER_KEY, new DelayTrigger(delay));
        return (Prompt) context.getSessionData(WaypointTriggerPrompt.RETURN_PROMPT_KEY);
    }

    @Override
    public String getPromptText(ConversationContext context) {
        return Messaging.tr(Messages.DELAY_TRIGGER_PROMPT);
    }
}
