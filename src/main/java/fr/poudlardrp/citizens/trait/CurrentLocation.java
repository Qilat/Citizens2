package fr.poudlardrp.citizens.trait;

import fr.poudlardrp.citizens.api.persistence.Persist;
import fr.poudlardrp.citizens.api.trait.Trait;
import fr.poudlardrp.citizens.api.trait.TraitName;
import org.bukkit.Location;

@TraitName("location")
public class CurrentLocation extends Trait {
    @Persist(value = "", required = true)
    private Location location = new Location(null, 0, 0, 0);

    public CurrentLocation() {
        super("location");
    }

    public Location getLocation() {
        return location.getWorld() == null ? null : location;
    }

    public void setLocation(Location loc) {
        this.location = loc.clone();
    }

    @Override
    public void run() {
        if (!npc.isSpawned())
            return;
        location = npc.getEntity().getLocation(location);
    }

    @Override
    public String toString() {
        return "CurrentLocation{" + location + "}";
    }
}