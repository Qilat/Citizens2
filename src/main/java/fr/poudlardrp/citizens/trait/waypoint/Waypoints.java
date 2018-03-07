package fr.poudlardrp.citizens.trait.waypoint;

import com.google.common.collect.Maps;
import fr.poudlardrp.citizens.editor.Editor;
import fr.poudlardrp.citizens.util.Messages;
import fr.poudlardrp.citizens.util.StringHelper;
import fr.poudlardrp.citizens.api.command.CommandContext;
import fr.poudlardrp.citizens.api.exception.NPCLoadException;
import fr.poudlardrp.citizens.api.persistence.PersistenceLoader;
import fr.poudlardrp.citizens.api.trait.Trait;
import fr.poudlardrp.citizens.api.trait.TraitName;
import fr.poudlardrp.citizens.api.util.DataKey;
import fr.poudlardrp.citizens.api.util.Messaging;
import org.bukkit.command.CommandSender;

import java.util.Map;
import java.util.Map.Entry;

@TraitName("waypoints")
public class Waypoints extends Trait {
    private static final Map<String, Class<? extends WaypointProvider>> providers = Maps.newHashMap();

    static {
        providers.put("linear", LinearWaypointProvider.class);
        providers.put("wander", WanderWaypointProvider.class);
        providers.put("guided", GuidedWaypointProvider.class);
    }

    private WaypointProvider provider = new LinearWaypointProvider();
    private String providerName = "linear";

    public Waypoints() {
        super("waypoints");
    }

    /**
     * Registers a {@link WaypointProvider}, which can be subsequently used by NPCs.
     *
     * @param clazz The class of the waypoint provider
     * @param name  The name of the waypoint provider
     */
    public static void registerWaypointProvider(Class<? extends WaypointProvider> clazz, String name) {
        providers.put(name, clazz);
    }

    private WaypointProvider create(Class<? extends WaypointProvider> clazz) {
        try {
            return clazz.newInstance();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void describeProviders(CommandSender sender) {
        Messaging.sendTr(sender, Messages.AVAILABLE_WAYPOINT_PROVIDERS);
        for (String name : providers.keySet()) {
            Messaging.send(sender, "    - " + StringHelper.wrap(name));
        }
    }

    /**
     * Returns the current {@link WaypointProvider}. May be null during initialisation.
     *
     * @return The current provider
     */
    public WaypointProvider getCurrentProvider() {
        return provider;
    }

    /**
     * @return The current provider name
     */
    public String getCurrentProviderName() {
        return providerName;
    }

    public Editor getEditor(CommandSender player, CommandContext args) {
        return provider.createEditor(player, args);
    }

    @Override
    public void load(DataKey key) throws NPCLoadException {
        provider = null;
        providerName = key.getString("provider", "linear");
        for (Entry<String, Class<? extends WaypointProvider>> entry : providers.entrySet()) {
            if (entry.getKey().equals(providerName)) {
                provider = create(entry.getValue());
                break;
            }
        }
        if (provider == null)
            return;
        PersistenceLoader.load(provider, key.getRelative(providerName));
    }

    @Override
    public void onSpawn() {
        if (provider != null) {
            provider.onSpawn(getNPC());
        }
    }

    @Override
    public void save(DataKey key) {
        if (provider == null)
            return;
        PersistenceLoader.save(provider, key.getRelative(providerName));
        key.setString("provider", providerName);
    }

    /**
     * Sets the current {@link WaypointProvider} using the given name.
     *
     * @param name The name of the waypoint provider, registered using {@link #registerWaypointProvider(Class, String)}
     * @return Whether the operation succeeded
     */
    public boolean setWaypointProvider(String name) {
        name = name.toLowerCase();
        Class<? extends WaypointProvider> clazz = providers.get(name);
        if (provider != null) {
            provider.onRemove();
        }
        if (clazz == null || (provider = create(clazz)) == null)
            return false;
        providerName = name;
        if (npc != null && npc.isSpawned()) {
            provider.onSpawn(npc);
        }
        return true;
    }
}