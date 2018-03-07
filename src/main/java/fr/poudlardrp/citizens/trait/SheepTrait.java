package fr.poudlardrp.citizens.trait;

import fr.poudlardrp.citizens.api.CitizensAPI;
import fr.poudlardrp.citizens.api.persistence.Persist;
import fr.poudlardrp.citizens.api.trait.Trait;
import fr.poudlardrp.citizens.api.trait.TraitName;
import org.bukkit.DyeColor;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerShearEntityEvent;

@TraitName("sheeptrait")
public class SheepTrait extends Trait {
    @Persist("color")
    private DyeColor color = DyeColor.WHITE;
    @Persist("sheared")
    private boolean sheared = false;

    public SheepTrait() {
        super("sheeptrait");
    }

    @EventHandler
    public void onPlayerShearEntityEvent(PlayerShearEntityEvent event) {
        if (npc != null && npc.equals(CitizensAPI.getNPCRegistry().getNPC(event.getEntity()))) {
            event.setCancelled(true);
        }
    }

    @Override
    public void onSpawn() {
    }

    @Override
    public void run() {
        if (npc.getEntity() instanceof Sheep) {
            Sheep sheep = (Sheep) npc.getEntity();
            sheep.setSheared(sheared);
            sheep.setColor(color);
        }
    }

    public void setColor(DyeColor color) {
        this.color = color;
    }

    public void setSheared(boolean sheared) {
        this.sheared = sheared;
    }

    public boolean toggleSheared() {
        return sheared = !sheared;
    }
}