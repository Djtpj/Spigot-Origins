package io.github.djtpj.trait;

import io.github.djtpj.gui.ItemIcon;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Material;

/**
 * An SimpleTrait is a single Trait that performs one method on a specific event
 */
@Getter
public abstract class SimpleTrait extends Trait {
    protected SimpleTrait(String name, String description, ChatColor color, Material material, Trait.Type type) {
        super(name, description, color, material, type);

        register();
    }

    protected SimpleTrait(ItemIcon icon, Type type) {
        super(icon, type);

        register();
    }

    protected SimpleTrait() {
        super(null, null, null, null, null);
    }
}