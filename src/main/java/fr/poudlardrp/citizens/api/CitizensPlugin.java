package fr.poudlardrp.citizens.api;

import fr.poudlardrp.citizens.api.ai.speech.SpeechFactory;
import fr.poudlardrp.citizens.api.npc.NPCDataStore;
import fr.poudlardrp.citizens.api.npc.NPCRegistry;
import fr.poudlardrp.citizens.api.trait.TraitFactory;
import fr.poudlardrp.citizens.npc.NPCSelector;
import org.bukkit.plugin.Plugin;

import java.io.File;

public interface CitizensPlugin extends Plugin {
    /**
     * @param store The data store of the registry
     * @return A new anonymous NPCRegistry that is not accessible via {@link #getNamedNPCRegistry(String)}
     */
    NPCRegistry createAnonymousNPCRegistry(NPCDataStore store);

    /**
     * @param pluginName The plugin name
     * @param store      The data store for the registry
     * @return A new NPCRegistry, that can also be retrieved via {@link #getNamedNPCRegistry(String)}
     */
    NPCRegistry createNamedNPCRegistry(String pluginName, NPCDataStore store);

    NPCSelector getDefaultNPCSelector();

    /**
     * @param pluginName The plugin name
     * @return A NPCRegistry previously created via {@link #createNamedNPCRegistry(String, NPCDataStore)}, or null if not found
     */
    NPCRegistry getNamedNPCRegistry(String pluginName);

    Iterable<NPCRegistry> getNPCRegistries();

    /**
     * Gets the <em>default</em> {@link NPCRegistry}.
     *
     * @return The NPC registry
     */
    NPCRegistry getNPCRegistry();

    ClassLoader getOwningClassLoader();

    /**
     * @return The folder for storing scripts
     */
    File getScriptFolder();

    /**
     * Gets the SpeechFactory.
     *
     * @return Citizens speech factory
     */
    SpeechFactory getSpeechFactory();

    /**
     * Gets the TraitFactory.
     *
     * @return Citizens trait factory
     */
    TraitFactory getTraitFactory();

    /**
     * Called when the current Citizens implementation is changed
     */
    void onImplementationChanged();

    /**
     * Removes the named NPCRegistry with the given name.
     */
    void removeNamedNPCRegistry(String name);
}
