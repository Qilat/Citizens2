package fr.poudlardrp.citizens.trait;

import fr.poudlardrp.citizens.npc.ai.NPCHolder;
import fr.poudlardrp.citizens.util.NMS;
import fr.poudlardrp.citizens.api.CitizensAPI;
import fr.poudlardrp.citizens.api.npc.NPC;
import fr.poudlardrp.citizens.api.persistence.Persist;
import fr.poudlardrp.citizens.api.trait.Trait;
import fr.poudlardrp.citizens.api.trait.TraitName;
import org.bukkit.entity.Entity;

import java.util.UUID;

@TraitName("mounttrait")
public class MountTrait extends Trait {
    private UUID mountedOn;
    private boolean triggered = false;
    @Persist("mountedon")
    private String uuid;

    public MountTrait() {
        super("mounttrait");
    }

    public void checkMount(Entity e) {
        if (mountedOn != null && (e == null || !e.getUniqueId().equals(mountedOn))) {
            NPC other = CitizensAPI.getNPCRegistry().getByUniqueId(mountedOn);
            if (other != null && other.isSpawned()) {
                NMS.mount(other.getEntity(), npc.getEntity());
                triggered = true;
            }
        }
    }

    @Override
    public void onDespawn() {
        Entity e = NMS.getVehicle(npc.getEntity());
        if (e != null) {
            npc.getEntity().leaveVehicle();
        }
    }

    @Override
    public void onSpawn() {
        checkMount(null);
    }

    @Override
    public void run() {
        if (!npc.isSpawned())
            return;
        if (!triggered && uuid != null) {
            try {
                mountedOn = UUID.fromString(uuid);
            } catch (IllegalArgumentException e) {
                mountedOn = null;
            }
            checkMount(null);
        }
        Entity e = NMS.getVehicle(npc.getEntity());
        if (e == null && !triggered) {
            mountedOn = null;
        } else if (e instanceof NPCHolder) {
            mountedOn = ((NPCHolder) e).getNPC().getUniqueId();
            uuid = mountedOn.toString();
        }
        checkMount(e);
    }
}
