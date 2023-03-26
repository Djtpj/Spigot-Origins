package io.github.djtpj.trait;

import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Material;

/**
 * An Ability is a single Trait that performs one method on a specific event
 */
@Getter
public abstract class Ability extends Trait {
    protected Ability(String name, String description, ChatColor color, Material material, Trait.Type type) {
        super(name, description, color, material, type);

        register();
    }

    protected Ability(String name, String description, String color, String material, String type) {
        super(name, description, color, material, type);

        register();
    }

    protected Ability() {
        this(null, null, (ChatColor) null, null, null);
    }
}