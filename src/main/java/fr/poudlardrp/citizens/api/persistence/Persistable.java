package fr.poudlardrp.citizens.api.persistence;

import fr.poudlardrp.citizens.api.util.DataKey;

public interface Persistable {
    public void load(DataKey root);

    public void save(DataKey root);
}
