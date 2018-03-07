package fr.poudlardrp.citizens.api;

import fr.poudlardrp.citizens.api.ai.speech.SpeechFactory;
import fr.poudlardrp.citizens.api.npc.NPC;
import fr.poudlardrp.citizens.api.npc.NPCDataStore;
import fr.poudlardrp.citizens.api.npc.NPCRegistry;
import fr.poudlardrp.citizens.api.npc.NPCSelector;
import fr.poudlardrp.citizens.api.scripting.ScriptCompiler;
import fr.poudlardrp.citizens.api.trait.TraitFactory;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.io.File;

/**
 * Contains methods used in order to utilize the Citizens API.
 */
public final class CitizensAPI {
    private static CitizensPlugin instance = null;
    private static ScriptCompiler scriptCompiler;

    private CitizensAPI() {
    }

    /**
     * Creates a new <em>anonymous</em> {@link NPCRegistry} with its own set of {@link NPC}s. This is not stored by the
     * Citizens plugin.
     *
     * @param store The {@link NPCDataStore} to use with the registry
     * @return A new anonymous NPCRegistry
     * @since 2.0.8
     */
    public static NPCRegistry createAnonymousNPCRegistry(NPCDataStore store) {
        return getImplementation().createAnonymousNPCRegistry(store);
    }

    /**
     * Creates a new {@link NPCRegistry} with its own set of {@link NPC}s. This is stored in memory with the Citizens
     * plugin, and can be accessed via {@link #getNamedNPCRegistry(String)}.
     *
     * @param name  The plugin name
     * @param store The {@link NPCDataStore} to use with the registry
     * @return A new NPCRegistry
     * @since 2.0.8
     */
    public static NPCRegistry createNamedNPCRegistry(String name, NPCDataStore store) {
        return getImplementation().createNamedNPCRegistry(name, store);
    }

    /**
     * @return The data folder of the current implementation
     */
    public static File getDataFolder() {
        return getImplementation().getDataFolder();
    }

    public static NPCSelector getDefaultNPCSelector() {
        return getImplementation().getDefaultNPCSelector();
    }

    private static CitizensPlugin getImplementation() {
        if (instance == null)
            throw new IllegalStateException("no implementation set");
        return instance;
    }

    /**
     * Sets the current Citizens implementation.
     *
     * @param implementation The new implementation
     */
    public static void setImplementation(CitizensPlugin implementation) {
        if (implementation != null && hasImplementation()) {
            getImplementation().onImplementationChanged();
        }
        instance = implementation;
    }

    private static ClassLoader getImplementationClassLoader() {
        return getImplementation().getOwningClassLoader();
    }

    /**
     * Retrieves the {@link NPCRegistry} previously created via {@link #createNamedNPCRegistry(String, NPCDataStore)} with the given
     * name, or null if not found.
     *
     * @param name The registry name
     * @return A NPCRegistry previously created via {@link #createNamedNPCRegistry(String, NPCDataStore)}, or null if not found
     * @since 2.0.8
     */
    public static NPCRegistry getNamedNPCRegistry(String name) {
        return getImplementation().getNamedNPCRegistry(name);
    }

    public static Iterable<NPCRegistry> getNPCRegistries() {
        return getImplementation().getNPCRegistries();
    }

    /**
     * Gets the current implementation's <em>default</em> {@link NPCRegistry}.
     *
     * @return The NPC registry
     */
    public static NPCRegistry getNPCRegistry() {
        return getImplementation().getNPCRegistry();
    }

    /**
     * @return The current {@link Plugin} providing an implementation
     */
    public static Plugin getPlugin() {
        return getImplementation();
    }

    /**
     * @return The current {@link ScriptCompiler}
     */
    public static ScriptCompiler getScriptCompiler() {
        if (scriptCompiler == null && getImplementation() != null) {
            scriptCompiler = new ScriptCompiler(getImplementationClassLoader());
        }
        return scriptCompiler;
    }

    /**
     * @return The folder used for storing scripts
     */
    public static File getScriptFolder() {
        return getImplementation().getScriptFolder();
    }

    /**
     * Gets the current implementation's {@link SpeechFactory}.
     *
     * @return Citizens speech factory
     * @see CitizensPlugin
     */
    public static SpeechFactory getSpeechFactory() {
        return getImplementation().getSpeechFactory();
    }

    /**
     * Gets the current implementation's {@link TraitFactory}.
     *
     * @return Citizens trait factory
     * @see CitizensPlugin
     */
    public static TraitFactory getTraitFactory() {
        return getImplementation().getTraitFactory();
    }

    /**
     * @return Whether a Citizens implementation is currently present
     */
    public static boolean hasImplementation() {
        return instance != null;
    }

    /**
     * A helper method for registering events using the current implementation's {@link Plugin}.
     *
     * @param listener The listener to register events for
     * @see #getPlugin()
     */
    public static void registerEvents(Listener listener) {
        if (Bukkit.getServer() != null && getPlugin() != null)
            Bukkit.getPluginManager().registerEvents(listener, getPlugin());
    }

    /**
     * Removes any previously created {@link NPCRegistry} stored under the given name.
     *
     * @param name The name previously given to {@link #createNamedNPCRegistry(String, NPCDataStore)}
     * @since 2.0.8
     */
    public static void removeNamedNPCRegistry(String name) {
        getImplementation().removeNamedNPCRegistry(name);
    }

    /**
     * Shuts down any resources currently being held.
     */
    public static void shutdown() {
        if (scriptCompiler == null)
            return;
        instance = null;
        scriptCompiler.interrupt();
        scriptCompiler = null;
    }
}