package io.github.djtpj.trait;

import io.github.djtpj.ui.ItemIcon;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.HashMap;

@Getter
public abstract class Trait implements Listener {
    public final static HashMap<Player, ArrayList<Trait>> playerTraitMap = new HashMap<>();

    protected final ItemIcon icon;
    protected final Type type;

    protected Trait(String name, String description, ChatColor color, Material material, Type type) {
        this.icon = new ItemIcon(name, description, color, material);
        this.type = type;
    }

    public final void enable(Player player) {
        onEnable(player);
    }
    protected void onEnable(Player player) {}

    public final void disable(Player player) {
        onDisable(player);
    }
    protected void onDisable(Player player) {}

    public enum Type {
        POSITIVE,
        NEUTRAL,
        NEGATIVE
    }
}
