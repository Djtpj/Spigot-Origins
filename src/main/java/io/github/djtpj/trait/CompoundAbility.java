package io.github.djtpj.trait;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import static io.github.djtpj.origin.Main.plugin;

/**
 * A CompoundAbility is a way to combine several {@link Ability Abilities} into one that is viewed as such in the {@link io.github.djtpj.gui.OriginPicker OriginPicker}
 */
public abstract class CompoundAbility extends Trait {
    public final static String ID = "compound-ability";

    protected final Trait[] traits;

    protected CompoundAbility(String name, String description, ChatColor color, Material material, Type type, Trait... traits) {
        super(name, description, color, material, type);

        this.traits = traits;

        register();
    }

    @Override
    protected void register() {
        super.register();

        for (Trait trait : traits) {
            plugin.getServer().getPluginManager().registerEvents(trait, plugin);
        }
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
