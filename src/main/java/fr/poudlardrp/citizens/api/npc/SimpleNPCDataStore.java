package fr.poudlardrp.citizens.api.npc;

import fr.poudlardrp.citizens.api.util.DataKey;
import fr.poudlardrp.citizens.api.util.Messaging;
import fr.poudlardrp.citizens.api.util.Storage;
import org.bukkit.entity.EntityType;

import java.util.UUID;

public class SimpleNPCDataStore implements NPCDataStore {
    private static final String LOAD_NAME_NOT_FOUND = "citizens.notifications.npc-name-not-found";
    private static final String LOAD_UNKNOWN_NPC_TYPE = "citizens.notifications.unknown-npc-type";
    private final Storage root;

    protected SimpleNPCDataStore(Storage saves) {
        root = saves;
    }

    public static NPCDataStore create(Storage storage) {
        return new SimpleNPCDataStore(storage);
    }

    private static EntityType matchEntityType(String toMatch) {
        EntityType type;
        try {
            type = EntityType.valueOf(toMatch);
        } catch (IllegalArgumentException ex) {
            type = EntityType.fromName(toMatch);
        }
        if (type != null)
            return type;
        return matchEnum(EntityType.values(), toMatch);
    }

    private static <T extends Enum<?>> T matchEnum(T[] values, String toMatch) {
        T type = null;
        for (T check : values) {
            String name = check.name();
            if (name.matches(toMatch) || name.equalsIgnoreCase(toMatch)
                    || name.replace("_", "").equalsIgnoreCase(toMatch)
                    || name.replace('_', '-').equalsIgnoreCase(toMatch)
                    || name.replace('_', ' ').equalsIgnoreCase(toMatch) || name.startsWith(toMatch)) {
                type = check;
                break;
            }
        }
        return type;
    }

    @Override
    public void clearData(NPC npc) {
        root.getKey("npc").removeKey(Integer.toString(npc.getId()));
    }

    @Override
    public int createUniqueNPCId(NPCRegistry registry) {
        DataKey key = root.getKey("");
        int newId = key.getInt("last-created-npc-id", -1);
        if (newId == -1 || registry.getById(newId + 1) != null) {
            int maxId = Integer.MIN_VALUE;
            for (NPC npc : registry) {
                if (npc.getId() > maxId) {
                    maxId = npc.getId();
                }
            }
            newId = maxId == Integer.MIN_VALUE ? 0 : maxId + 1;
        } else {
            newId++;
        }
        key.setInt("last-created-npc-id", newId);
        return newId;
    }

    @Override
    public void loadInto(NPCRegistry registry) {
        for (DataKey key : root.getKey("npc").getIntegerSubKeys()) {
            int id = Integer.parseInt(key.name());
            if (!key.keyExists("name")) {
                Messaging.logTr(LOAD_NAME_NOT_FOUND, id);
                continue;
            }
            String unparsedEntityType = key.getString("traits.type", "PLAYER");
            EntityType type = matchEntityType(unparsedEntityType);
            if (type == null) {
                Messaging.logTr(LOAD_UNKNOWN_NPC_TYPE, unparsedEntityType);
                continue;
            }
            NPC npc = registry.createNPC(type,
                    !key.getString("uuid", "").isEmpty() ? UUID.fromString(key.getString("uuid")) : UUID.randomUUID(),
                    id, key.getString("name"));
            npc.load(key);
        }
    }

    @Override
    public void saveToDisk() {
        new Thread() {
            @Override
            public void run() {
                root.save();
            }
        }.start();
    }

    @Override
    public void saveToDiskImmediate() {
        root.save();
    }

    @Override
    public void store(NPC npc) {
        npc.save(root.getKey("npc." + npc.getId()));
    }

    @Override
    public void storeAll(NPCRegistry registry) {
        for (NPC npc : registry) {
            store(npc);
        }
    }
}
