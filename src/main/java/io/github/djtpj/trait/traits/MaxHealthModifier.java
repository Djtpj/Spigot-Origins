package io.github.djtpj.trait.traits;

import io.github.djtpj.gui.ItemIcon;
import io.github.djtpj.trait.Ability;
import io.github.djtpj.trait.UtilityAbility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

/**
 * MaxHealthModifier sets a player's max health to a specified value.
 */
@UtilityAbility
public class MaxHealthModifier extends Ability {
    public static final String ID = "health-mod";

    private final double newHealth;

    public MaxHealthModifier(String name, String description, ChatColor color, Material material, Type type, Double newHealth) {
        super(name, description, color, material, type);
        this.newHealth = newHealth;
    }

    public MaxHealthModifier(ItemIcon icon, Type type, Double newHealth) {
        super(icon, type);
        this.newHealth = newHealth;
    }

    @Override
    protected void onEnable(Player player) {
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(newHealth);
    }

    @Override
    protected void onDisable(Player player) {
        final double normalMaxHealth = 20;

        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
    }
}
