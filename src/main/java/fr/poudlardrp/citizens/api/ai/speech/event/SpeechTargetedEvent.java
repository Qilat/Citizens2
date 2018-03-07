package fr.poudlardrp.citizens.api.ai.speech.event;

import fr.poudlardrp.citizens.api.ai.speech.SpeechContext;
import fr.poudlardrp.citizens.api.ai.speech.Talkable;
import fr.poudlardrp.citizens.api.ai.speech.VocalChord;

/**
 * Represents an event where a Talkable entity speaks to another Talkable entity.
 */
public class SpeechTargetedEvent extends SpeechEvent {

    public SpeechTargetedEvent(Talkable target, SpeechContext context, String message, VocalChord vocalChord) {
        super(target, context, message, vocalChord);
    }

}