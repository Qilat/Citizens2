package fr.poudlardrp.citizens.api.command;

import net.citizensnpcs.api.trait.Trait;
import org.bukkit.entity.EntityType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Requirements {
    EntityType[] excludedTypes() default {EntityType.UNKNOWN};

    boolean livingEntity() default false;

    boolean ownership() default false;

    boolean selected() default false;

    Class<? extends Trait>[] traits() default {};

    EntityType[] types() default {EntityType.UNKNOWN};
}