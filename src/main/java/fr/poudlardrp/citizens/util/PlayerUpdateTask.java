package fr.poudlardrp.citizens.util;

import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class PlayerUpdateTask extends BukkitRunnable {
    private static Map<UUID, Entity> TICKERS = new HashMap<UUID, Entity>();
    private static List<Entity> TICKERS_PENDING_ADD = new ArrayList<Entity>();
    private static List<Entity> TICKERS_PENDING_REMOVE = new ArrayList<Entity>();

    public static void addOrRemove(Entity entity, boolean remove) {
        boolean contains = TICKERS.containsKey(entity.getUniqueId());
        if (!remove) {
            if (contains) {
                TICKERS_PENDING_REMOVE.add(entity);
            }
        } else if (!contains) {
            TICKERS_PENDING_ADD.add(entity);
        }
    }

    @Override
    public void cancel() {
        super.cancel();
        TICKERS.clear();
        TICKERS_PENDING_ADD.clear();
        TICKERS_PENDING_REMOVE.clear();
    }

    @Override
    public void run() {
        for (int i = 0; i < TICKERS_PENDING_ADD.size(); i++) {
            Entity ent = TICKERS_PENDING_ADD.get(i);
            TICKERS.put(ent.getUniqueId(), ent);
        }
        for (int i = 0; i < TICKERS_PENDING_REMOVE.size(); i++) {
            TICKERS.remove(TICKERS_PENDING_REMOVE.get(i).getUniqueId());
        }
        TICKERS_PENDING_ADD.clear();
        TICKERS_PENDING_REMOVE.clear();
        Iterator<Entity> itr = TICKERS.values().iterator();
        while (itr.hasNext()) {
            Entity entity = itr.next();
            if (NMS.tick(entity)) {
                itr.remove();
            }
        }
    }
}
