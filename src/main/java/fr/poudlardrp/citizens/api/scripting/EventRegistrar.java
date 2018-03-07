package fr.poudlardrp.citizens.api.scripting;

import com.google.common.collect.Maps;
import org.bukkit.event.*;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.util.Map;

/**
 * A simple {@link ContextProvider} for scripts, allowing them to register and unregister events.
 */
public class EventRegistrar implements ContextProvider {
    private final Plugin plugin;

    public EventRegistrar(Plugin plugin) {
        if (plugin == null || !plugin.isEnabled())
            throw new IllegalArgumentException("Invalid plugin passed to EventRegistrar. Is it enabled?");
        this.plugin = plugin;
    }

    @Override
    public void provide(Script script) {
        script.setAttribute("events", new Events(plugin));
    }

    public static class Events {
        private final Map<EventHandler, Listener> anonymousListeners = Maps.newHashMap();
        private final Plugin plugin;

        public Events(Plugin plugin) {
            this.plugin = plugin;
        }

        public void deregister(EventHandler handler) {
            if (handler != null) {
                HandlerList.unregisterAll(anonymousListeners.remove(handler));
            }
        }

        public void on(Class<? extends Event> eventClass, EventHandler handler) {
            registerEvent(handler, eventClass);
        }

        private void registerEvent(final EventHandler handler, final Class<? extends Event> eventClass) {
            if (!plugin.isEnabled())
                throw new IllegalStateException("Plugin is no longer valid.");
            Listener bukkitListener = new Listener() {
            };
            anonymousListeners.put(handler, bukkitListener);

            PluginManager manager = plugin.getServer().getPluginManager();
            manager.registerEvent(eventClass, bukkitListener, EventPriority.NORMAL, new EventExecutor() {
                @Override
                public void execute(Listener bukkitListener, Event event) throws EventException {
                    try {
                        if (!eventClass.isAssignableFrom(event.getClass()))
                            return;
                        handler.handle(event);
                    } catch (Throwable t) {
                        throw new EventException(t);
                    }
                }
            }, plugin);
        }
    }
}
