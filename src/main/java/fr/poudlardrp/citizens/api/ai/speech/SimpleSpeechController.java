package fr.poudlardrp.citizens.api.ai.speech;

import fr.poudlardrp.citizens.api.CitizensAPI;
import fr.poudlardrp.citizens.api.ai.speech.event.NPCSpeechEvent;
import fr.poudlardrp.citizens.api.npc.NPC;
import fr.poudlardrp.citizens.api.trait.trait.Speech;
import org.bukkit.Bukkit;

/**
 * Simple implementation of {@link SpeechController} which allows a NPC to speak with any registered {@link VocalChord}.
 */
public class SimpleSpeechController implements SpeechController {
    NPC npc;

    public SimpleSpeechController(NPC npc) {
        this.npc = npc;
    }

    @Override
    public void speak(SpeechContext context) {
        speak(context, npc.getTrait(Speech.class).getDefaultVocalChord());
    }

    @Override
    public void speak(SpeechContext context, String vocalChordName) {
        context.setTalker(npc.getEntity());
        NPCSpeechEvent event = new NPCSpeechEvent(context, vocalChordName);
        Bukkit.getServer().getPluginManager().callEvent(event);
        if (event.isCancelled())
            return;
        CitizensAPI.getSpeechFactory().getVocalChord(event.getVocalChordName()).talk(context);
    }

}