package io.github.djtpj.trait;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public abstract class CompoundAbility extends Trait {
    public final static String ID = "compound-ability";

    protected final Trait[] traits;

    protected CompoundAbility(String name, String description, ChatColor color, Material material, Type type, Trait... traits) {
        super(name, description, color, material, type);

        this.traits = traits;
    }

    public Trait[] getTraits() {
        return traits;
    }

    @Override
    protected void onDisable(Player player) {
        for (Trait trait : traits) {
            trait.disable(player);
        }
    }

    @Override
    protected void onEnable(Player player) {
        for (Trait trait : traits) {
            trait.enable(player);
        }
    }
}
