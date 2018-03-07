package fr.poudlardrp.citizens.api.trait.trait;

import fr.poudlardrp.citizens.api.CitizensAPI;
import fr.poudlardrp.citizens.api.ai.speech.VocalChord;
import fr.poudlardrp.citizens.api.persistence.Persist;
import fr.poudlardrp.citizens.api.trait.Trait;
import fr.poudlardrp.citizens.api.trait.TraitName;

/**
 * Represents the default speech settings of an NPC.
 */
@TraitName("speech")
public class Speech extends Trait {
    public static final String DEFAULT_VOCAL_CHORD = "chat";
    @Persist("")
    private String defaultVocalChord = DEFAULT_VOCAL_CHORD;

    public Speech() {
        super("speech");
    }

    /**
     * Gets the name of the default {@link VocalChord} for this NPC.
     *
     * @return The name of the VocalChord
     */
    public String getDefaultVocalChord() {
        return defaultVocalChord;
    }

    /**
     * Sets the name of the default {@link VocalChord} for this NPC.
     *
     * @return The name of the VocalChord
     */
    public void setDefaultVocalChord(Class<VocalChord> clazz) {
        defaultVocalChord = CitizensAPI.getSpeechFactory().getVocalChordName(clazz);
    }

    @Override
    public String toString() {
        return "DefaultVocalChord{" + defaultVocalChord + "}";
    }
}