package fr.poudlardrp.citizens.trait;

import fr.poudlardrp.citizens.api.exception.NPCLoadException;
import fr.poudlardrp.citizens.api.trait.Trait;
import fr.poudlardrp.citizens.api.trait.TraitName;
import fr.poudlardrp.citizens.api.util.DataKey;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Profession;

@TraitName("profession")
public class VillagerProfession extends Trait {
    private Profession profession = Profession.FARMER;

    public VillagerProfession() {
        super("profession");
    }

    @Override
    public void load(DataKey key) throws NPCLoadException {
        try {
            profession = Profession.valueOf(key.getString(""));
            if (profession == Profession.NORMAL) {
                profession = Profession.FARMER;
            }
        } catch (IllegalArgumentException ex) {
            throw new NPCLoadException("Invalid profession.");
        }
    }

    @Override
    public void onSpawn() {
        if (npc.getEntity() instanceof Villager) {
            ((Villager) npc.getEntity()).setProfession(profession);
        }
    }

    @Override
    public void save(DataKey key) {
        key.setString("", profession.name());
    }

    public void setProfession(Profession profession) {
        if (profession == Profession.NORMAL) {
            profession = Profession.FARMER;
        }
        this.profession = profession;
        if (npc.getEntity() instanceof Villager) {
            ((Villager) npc.getEntity()).setProfession(profession);
        }
    }

    @Override
    public String toString() {
        return "Profession{" + profession + "}";
    }
}