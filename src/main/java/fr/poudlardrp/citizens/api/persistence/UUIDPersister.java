package fr.poudlardrp.citizens.api.persistence;

import fr.poudlardrp.citizens.api.util.DataKey;

import java.util.UUID;

public class UUIDPersister implements Persister<UUID> {
    @Override
    public UUID create(DataKey root) {
        try {
            return UUID.fromString(root.getString(""));
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @Override
    public void save(UUID instance, DataKey root) {
        root.setString("", instance.toString());
    }
}
