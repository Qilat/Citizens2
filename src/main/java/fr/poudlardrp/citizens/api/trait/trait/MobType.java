package fr.poudlardrp.citizens.api.trait.trait;

import fr.poudlardrp.citizens.api.trait.Trait;
import fr.poudlardrp.citizens.api.trait.TraitName;
import fr.poudlardrp.citizens.api.util.DataKey;
import org.bukkit.entity.EntityType;

/**
 * Represents an NPC's mob type.
 */
@TraitName("type")
public class MobType extends Trait {
    private EntityType type = EntityType.PLAYER;

    public MobType() {
        super("type");
    }

    /**
     * Gets the type of mob that an NPC is.
     *
     * @return The mob type
     */
    public EntityType getType() {
        return type;
    }

    /**
     * Sets the type of mob that an NPC is.
     *
     * @param type Mob type to set the NPC as
     */
    public void setType(EntityType type) {
        this.type = type;
    }

    @Override
    public void load(DataKey key) {
        try {
            type = EntityType.valueOf(key.getString(""));
        } catch (IllegalArgumentException ex) {
            type = EntityType.fromName(key.getString(""));
        }
        if (type == null) {
            type = EntityType.PLAYER;
        }
    }

    @Override
    public void onSpawn() {
        type = npc.getEntity().getType();
    }

    @Override
    public void save(DataKey key) {
        key.setString("", type.name());
    }

    @Override
    public String toString() {
        return "MobType{" + type + "}";
    }
}