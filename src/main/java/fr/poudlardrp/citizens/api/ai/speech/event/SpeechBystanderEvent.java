package fr.poudlardrp.citizens.api.ai.speech.event;

import fr.poudlardrp.citizens.api.ai.speech.SpeechContext;
import fr.poudlardrp.citizens.api.ai.speech.Talkable;
import fr.poudlardrp.citizens.api.ai.speech.VocalChord;

/**
 * Represents an event where a {@link Talkable} entity speaks by a {@link Talkable} bystander.
 */
public class SpeechBystanderEvent extends SpeechEvent {

    public SpeechBystanderEvent(Talkable target, SpeechContext context, String message, VocalChord vocalChord) {
        super(target, context, message, vocalChord);
    }

}