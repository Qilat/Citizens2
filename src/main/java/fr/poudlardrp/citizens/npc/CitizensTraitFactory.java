package fr.poudlardrp.citizens.npc;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import fr.poudlardrp.citizens.Metrics;
import fr.poudlardrp.citizens.trait.*;
import fr.poudlardrp.citizens.trait.text.Text;
import fr.poudlardrp.citizens.api.CitizensAPI;
import fr.poudlardrp.citizens.api.npc.NPC;
import fr.poudlardrp.citizens.api.trait.Trait;
import fr.poudlardrp.citizens.api.trait.TraitFactory;
import fr.poudlardrp.citizens.api.trait.TraitInfo;
import fr.poudlardrp.citizens.api.trait.trait.*;
import fr.poudlardrp.citizens.trait.waypoint.Waypoints;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class CitizensTraitFactory implements TraitFactory {
    private static final Set<String> INTERNAL_TRAITS = Sets.newHashSet();
    private final List<TraitInfo> defaultTraits = Lists.newArrayList();
    private final Map<String, TraitInfo> registered = Maps.newHashMap();

    public CitizensTraitFactory() {
        registerTrait(TraitInfo.create(Age.class));
        registerTrait(TraitInfo.create(ArmorStandTrait.class));
        registerTrait(TraitInfo.create(Anchors.class));
        registerTrait(TraitInfo.create(BossBarTrait.class));
        registerTrait(TraitInfo.create(Controllable.class));
        registerTrait(TraitInfo.create(Equipment.class));
        registerTrait(TraitInfo.create(Gravity.class));
        registerTrait(TraitInfo.create(HorseModifiers.class));
        registerTrait(TraitInfo.create(Inventory.class));
        registerTrait(TraitInfo.create(CurrentLocation.class));
        registerTrait(TraitInfo.create(LookClose.class));
        registerTrait(TraitInfo.create(OcelotModifiers.class));
        registerTrait(TraitInfo.create(Owner.class));
        registerTrait(TraitInfo.create(Poses.class));
        registerTrait(TraitInfo.create(Powered.class));
        registerTrait(TraitInfo.create(RabbitType.class));
        registerTrait(TraitInfo.create(Saddle.class));
        registerTrait(TraitInfo.create(ScriptTrait.class));
        registerTrait(TraitInfo.create(SheepTrait.class));
        registerTrait(TraitInfo.create(ShulkerTrait.class));
        registerTrait(TraitInfo.create(SkinLayers.class));
        registerTrait(TraitInfo.create(MountTrait.class));
        registerTrait(TraitInfo.create(SlimeSize.class));
        registerTrait(TraitInfo.create(Spawned.class));
        registerTrait(TraitInfo.create(Speech.class));
        registerTrait(TraitInfo.create(Text.class));
        registerTrait(TraitInfo.create(MobType.class).asDefaultTrait());
        registerTrait(TraitInfo.create(Waypoints.class));
        registerTrait(TraitInfo.create(WitherTrait.class));
        registerTrait(TraitInfo.create(WoolColor.class));
        registerTrait(TraitInfo.create(WolfModifiers.class));
        registerTrait(TraitInfo.create(VillagerProfession.class));

        for (String trait : registered.keySet()) {
            INTERNAL_TRAITS.add(trait);
        }
    }

    @Override
    public void addDefaultTraits(NPC npc) {
        for (TraitInfo info : defaultTraits) {
            npc.addTrait(create(info));
        }
    }

    public void addPlotters(Metrics.Graph graph) {
        for (Map.Entry<String, TraitInfo> entry : registered.entrySet()) {
            if (INTERNAL_TRAITS.contains(entry.getKey()))
                continue;
            final Class<? extends Trait> traitClass = entry.getValue().getTraitClass();
            graph.addPlotter(new Metrics.Plotter(entry.getKey()) {
                @Override
                public int getValue() {
                    int numberUsingTrait = 0;
                    for (NPC npc : CitizensAPI.getNPCRegistry()) {
                        if (npc.hasTrait(traitClass))
                            ++numberUsingTrait;
                    }
                    return numberUsingTrait;
                }
            });
        }
    }

    private <T extends Trait> T create(TraitInfo info) {
        return info.tryCreateInstance();
    }

    @Override
    public void deregisterTrait(TraitInfo info) {
        Preconditions.checkNotNull(info, "info cannot be null");
        registered.remove(info.getTraitName());
    }

    @Override
    public <T extends Trait> T getTrait(Class<T> clazz) {
        for (TraitInfo entry : registered.values()) {
            if (clazz == entry.getTraitClass()) {
                return create(entry);
            }
        }
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Trait> T getTrait(String name) {
        TraitInfo info = registered.get(name.toLowerCase());
        if (info == null)
            return null;
        return (T) create(info);
    }

    @Override
    public Class<? extends Trait> getTraitClass(String name) {
        TraitInfo info = registered.get(name.toLowerCase());
        return info == null ? null : info.getTraitClass();
    }

    @Override
    public boolean isInternalTrait(Trait trait) {
        return INTERNAL_TRAITS.contains(trait.getName());
    }

    @Override
    public void registerTrait(TraitInfo info) {
        Preconditions.checkNotNull(info, "info cannot be null");
        if (registered.containsKey(info.getTraitName())) {
            System.out.println(info.getTraitClass());
            throw new IllegalArgumentException("trait name already registered");
        }
        registered.put(info.getTraitName(), info);
        if (info.isDefaultTrait()) {
            defaultTraits.add(info);
        }
    }
}