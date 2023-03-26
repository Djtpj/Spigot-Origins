package io.github.djtpj.trait;

import io.github.djtpj.gui.ItemIcon;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;

import static io.github.djtpj.origin.Main.plugin;

/**
 * A Trait is a class that simply defines behavior for an {@link io.github.djtpj.origin.Origin Origin}
 * @see io.github.djtpj.origin.Origin
 */
@Getter
public abstract class Trait implements Listener {
    public final static HashMap<Player, ArrayList<Trait>> playerTraitMap = new HashMap<>();
    private final static ArrayList<Trait> registeredTraits = new ArrayList<>();

    protected final ItemIcon icon;
    protected final Type type;

    protected Trait(String name, String description, ChatColor color, Material material, Type type) {
        this.icon = new ItemIcon(name, description + "\n\n" + type, color, material);

        this.type = type;
    }

    protected void register() {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);

        plugin.getLogger().log(Level.INFO, getID() + " registered as listener.");
    }

    public String getID() {
        try {
            return (String) getClass().getField("ID").get(null);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            return getClass().getSimpleName().toLowerCase();
        }
    }

    public final void enable(Player player) {
        onEnable(player);
    }
    protected void onEnable(Player player) {}

    public final void disable(Player player) {
        onDisable(player);
    }
    protected void onDisable(Player player) {}

    /**
     * Shows whether a Trait is Positive, Negative, or Neutral
     */
    public enum Type {
        POSITIVE(ChatColor.GREEN),
        NEUTRAL(ChatColor.YELLOW),
        NEGATIVE(ChatColor.RED);

        private final ChatColor color;

        Type(ChatColor color) {
            this.color = color;
        }

        @Override
        public String toString() {
            return color + super.toString();
        }
    }
}
