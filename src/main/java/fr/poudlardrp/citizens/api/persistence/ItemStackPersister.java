package fr.poudlardrp.citizens.api.persistence;

import fr.poudlardrp.citizens.api.util.DataKey;
import fr.poudlardrp.citizens.api.util.ItemStorage;
import org.bukkit.inventory.ItemStack;

public class ItemStackPersister implements Persister<ItemStack> {
    @Override
    public ItemStack create(DataKey root) {
        return ItemStorage.loadItemStack(root);
    }

    @Override
    public void save(ItemStack instance, DataKey root) {
        ItemStorage.saveItem(root, instance);
    }
}
