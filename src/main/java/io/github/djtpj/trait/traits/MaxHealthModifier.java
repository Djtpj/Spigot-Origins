package io.github.djtpj.trait.traits;

import io.github.djtpj.trait.Ability;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

public class MaxHealthModifier extends Ability {
    public static final String ID = "health-mod";

    private final double newHealth;

    public MaxHealthModifier(String name, String description, ChatColor color, Material material, Type type, Double newHealth) {
        super(name, description, color, material, type);
        this.newHealth = newHealth;
    }

    public MaxHealthModifier(String name, String description, String color, String material, String type, Double newHealth) {
        super(name, description, color, material, type);
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
