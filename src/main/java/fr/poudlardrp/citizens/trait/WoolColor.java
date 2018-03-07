package fr.poudlardrp.citizens.trait;

import fr.poudlardrp.citizens.api.CitizensAPI;
import fr.poudlardrp.citizens.api.exception.NPCLoadException;
import fr.poudlardrp.citizens.api.trait.Trait;
import fr.poudlardrp.citizens.api.trait.TraitName;
import fr.poudlardrp.citizens.api.util.DataKey;
import org.bukkit.DyeColor;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.SheepDyeWoolEvent;

@TraitName("woolcolor")
public class WoolColor extends Trait {
    boolean sheep = false;
    private DyeColor color = DyeColor.WHITE;

    public WoolColor() {
        super("woolcolor");
    }

    @Override
    public void load(DataKey key) throws NPCLoadException {
        try {
            color = DyeColor.valueOf(key.getString(""));
        } catch (Exception ex) {
            color = DyeColor.WHITE;
        }
    }

    @EventHandler
    public void onSheepDyeWool(SheepDyeWoolEvent event) {
        if (npc.equals(CitizensAPI.getNPCRegistry().getNPC(event.getEntity())))
            event.setCancelled(true);
    }

    @Override
    public void onSpawn() {
        if (npc.getEntity() instanceof Sheep) {
            ((Sheep) npc.getEntity()).setColor(color);
            sheep = true;
        } else {
            sheep = false;
        }
    }

    @Override
    public void save(DataKey key) {
        key.setString("", color.name());
    }

    public void setColor(DyeColor color) {
        this.color = color;
        if (sheep) {
            ((Sheep) npc.getEntity()).setColor(color);
        }
    }

    @Override
    public String toString() {
        return "WoolColor{" + color.name() + "}";
    }
}