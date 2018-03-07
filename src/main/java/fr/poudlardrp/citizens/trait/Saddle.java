package fr.poudlardrp.citizens.trait;

import fr.poudlardrp.citizens.api.CitizensAPI;
import fr.poudlardrp.citizens.api.persistence.Persist;
import fr.poudlardrp.citizens.api.trait.Trait;
import fr.poudlardrp.citizens.api.trait.TraitName;
import org.bukkit.entity.Pig;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;

@TraitName("saddle")
public class Saddle extends Trait implements Toggleable {
    private boolean pig;
    @Persist("")
    private boolean saddle;

    public Saddle() {
        super("saddle");
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        if (pig && npc.equals(CitizensAPI.getNPCRegistry().getNPC(event.getRightClicked())))
            event.setCancelled(true);
    }

    @Override
    public void onSpawn() {
        if (npc.getEntity() instanceof Pig) {
            ((Pig) npc.getEntity()).setSaddle(saddle);
            pig = true;
        } else
            pig = false;
    }

    @Override
    public boolean toggle() {
        saddle = !saddle;
        if (pig)
            ((Pig) npc.getEntity()).setSaddle(saddle);
        return saddle;
    }

    @Override
    public String toString() {
        return "Saddle{" + saddle + "}";
    }
}