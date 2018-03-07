package fr.poudlardrp.citizens.api.ai.speech.event;

import fr.poudlardrp.citizens.api.CitizensAPI;
import fr.poudlardrp.citizens.api.ai.speech.SpeechContext;
import fr.poudlardrp.citizens.api.ai.speech.VocalChord;
import fr.poudlardrp.citizens.api.event.NPCEvent;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

/**
 * Represents an event where an NPC speaks, with {@link SpeechContext}. This event takes place before being sent to the
 * {@link VocalChord}.
 */
public class NPCSpeechEvent extends NPCEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled = false;
    private SpeechContext context;
    private String vocalChordName;

    public NPCSpeechEvent(SpeechContext context, String vocalChordName) {
        super(CitizensAPI.getNPCRegistry().getNPC(context.getTalker().getEntity()));
        this.vocalChordName = vocalChordName;
        this.context = context;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    /**
     * Returns the {@link SpeechContext} that will be sent to the VocalChord.
     *
     * @return the SpeechContext
     */
    public SpeechContext getContext() {
        return context;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    /**
     * @return the name of the VocalChord that will be used.
     */
    public String getVocalChordName() {
        return vocalChordName;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    /**
     * Sets the name of the {@link VocalChord} to be used.
     *
     * @param name A valid registered VocalChord name
     */
    public void setVocalChord(String name) {
        this.vocalChordName = name;
    }
}