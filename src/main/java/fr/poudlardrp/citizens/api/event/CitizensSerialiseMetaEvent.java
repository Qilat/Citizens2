package fr.poudlardrp.citizens.api.event;

import fr.poudlardrp.citizens.api.util.DataKey;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.meta.ItemMeta;

public class CitizensSerialiseMetaEvent extends CitizensEvent {
    private static final HandlerList handlers = new HandlerList();
    private final DataKey key;
    private final ItemMeta meta;

    public CitizensSerialiseMetaEvent(DataKey key, ItemMeta meta) {
        this.key = key;
        this.meta = meta;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public DataKey getKey() {
        return key;
    }

    public ItemMeta getMeta() {
        return meta;
    }
}
