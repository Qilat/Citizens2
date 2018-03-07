package fr.poudlardrp.citizens.trait.waypoint.triggers;

import fr.poudlardrp.citizens.trait.waypoint.WaypointEditor;
import fr.poudlardrp.citizens.util.Messages;
import fr.poudlardrp.citizens.api.CitizensAPI;
import fr.poudlardrp.citizens.api.util.Messaging;
import fr.poudlardrp.citizens.trait.waypoint.Waypoint;
import org.bukkit.command.CommandSender;
import org.bukkit.conversations.*;
import org.bukkit.entity.Player;

public class TriggerEditPrompt extends StringPrompt {
    private final WaypointEditor editor;

    public TriggerEditPrompt(WaypointEditor editor) {
        this.editor = editor;
    }

    public static Conversation start(Player player, WaypointEditor editor) {
        final Conversation conversation = new ConversationFactory(CitizensAPI.getPlugin()).withLocalEcho(false)
                .withEscapeSequence("exit").withEscapeSequence("triggers").withEscapeSequence("/npc path")
                .withModality(false).withFirstPrompt(new TriggerEditPrompt(editor)).buildConversation(player);
        conversation.begin();
        return conversation;
    }

    @Override
    public Prompt acceptInput(ConversationContext context, String input) {
        input = input.toLowerCase().trim();
        if (input.contains("add")) {
            context.setSessionData("said", false);
            return new TriggerAddPrompt(editor);
        }
        if (input.contains("remove")) {
            context.setSessionData("said", false);
            return new TriggerRemovePrompt(editor);
        }
        return this;
    }

    @Override
    public String getPromptText(ConversationContext context) {
        context.setSessionData("previous", this);
        if (context.getSessionData("said") == Boolean.TRUE)
            return "";
        context.setSessionData("said", true);
        String base = Messaging.tr(Messages.WAYPOINT_TRIGGER_EDITOR_PROMPT);
        if (editor.getCurrentWaypoint() != null) {
            Waypoint waypoint = editor.getCurrentWaypoint();
            for (WaypointTrigger trigger : waypoint.getTriggers()) {
                base += "\n    - " + trigger.description();
            }
        }
        Messaging.send((CommandSender) context.getForWhom(), base);
        return "";
    }
}
