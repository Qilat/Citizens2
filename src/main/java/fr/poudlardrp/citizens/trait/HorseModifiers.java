package fr.poudlardrp.citizens.trait;

import net.citizensnpcs.api.persistence.Persist;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.trait.TraitName;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Horse.Color;
import org.bukkit.entity.Horse.Style;
import org.bukkit.inventory.ItemStack;

@TraitName("horsemodifiers")
public class HorseModifiers extends Trait {
    @Persist("armor")
    private ItemStack armor = null;
    @Persist("carryingChest")
    private boolean carryingChest;
    @Persist("color")
    private Color color = Color.CREAMY;
    @Persist("saddle")
    private ItemStack saddle = null;
    @Persist("style")
    private Style style = Style.NONE;

    public HorseModifiers() {
        super("horsemodifiers");
    }

    public ItemStack getArmor() {
        return armor;
    }

    public void setArmor(ItemStack armor) {
        this.armor = armor;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
        updateModifiers();
    }

    public ItemStack getSaddle() {
        return saddle;
    }

    public void setSaddle(ItemStack saddle) {
        this.saddle = saddle;
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
        updateModifiers();
    }

    @Override
    public void onSpawn() {
        updateModifiers();
    }

    @Override
    public void run() {
        if (npc.getEntity() instanceof Horse) {
            Horse horse = (Horse) npc.getEntity();
            saddle = horse.getInventory().getSaddle();
            armor = horse.getInventory().getArmor();
        }
    }

    public void setCarryingChest(boolean carryingChest) {
        this.carryingChest = carryingChest;
        updateModifiers();
    }

    private void updateModifiers() {
        if (npc.getEntity() instanceof Horse) {
            Horse horse = (Horse) npc.getEntity();
            horse.setColor(color);
            horse.setStyle(style);
            horse.getInventory().setArmor(armor);
            horse.getInventory().setSaddle(saddle);
        }
    }
}
