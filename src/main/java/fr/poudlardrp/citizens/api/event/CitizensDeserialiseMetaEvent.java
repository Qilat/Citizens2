package fr.poudlardrp.citizens.api.event;

import fr.poudlardrp.citizens.api.util.DataKey;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class CitizensDeserialiseMetaEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final ItemStack itemstack;
    private final DataKey key;

    public CitizensDeserialiseMetaEvent(DataKey key, ItemStack itemstack) {
        this.key = key;
        this.itemstack = itemstack;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public ItemStack getItemStack() {
        return itemstack;
    }

    public DataKey getKey() {
        return key;
    }
}
