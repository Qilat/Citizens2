package fr.poudlardrp.citizens.trait.waypoint.triggers;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import fr.poudlardrp.citizens.util.Messages;
import fr.poudlardrp.citizens.util.PlayerAnimation;
import fr.poudlardrp.citizens.util.Util;
import net.citizensnpcs.api.util.Messaging;
import org.bukkit.command.CommandSender;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;

import java.util.List;

public class AnimationTriggerPrompt extends StringPrompt implements WaypointTriggerPrompt {
    private final List<PlayerAnimation> animations = Lists.newArrayList();

    @Override
    public Prompt acceptInput(ConversationContext context, String input) {
        if (input.equalsIgnoreCase("back")) {
            return (Prompt) context.getSessionData("previous");
        }
        if (input.equalsIgnoreCase("finish")) {
            context.setSessionData(CREATED_TRIGGER_KEY, new AnimationTrigger(animations));
            return (Prompt) context.getSessionData(RETURN_PROMPT_KEY);
        }
        PlayerAnimation animation = Util.matchEnum(PlayerAnimation.values(), input);
        if (animation == null) {
            Messaging.sendErrorTr((CommandSender) context.getForWhom(), Messages.INVALID_ANIMATION, input,
                    getValidAnimations());
        }
        animations.add(animation);
        Messaging.sendTr((CommandSender) context.getForWhom(), Messages.ANIMATION_ADDED, input);
        return this;
    }

    @Override
    public String getPromptText(ConversationContext context) {
        Messaging.sendTr((CommandSender) context.getForWhom(), Messages.ANIMATION_TRIGGER_PROMPT, getValidAnimations());
        return "";
    }

    private String getValidAnimations() {
        return Joiner.on(", ").join(PlayerAnimation.values());
    }
}
