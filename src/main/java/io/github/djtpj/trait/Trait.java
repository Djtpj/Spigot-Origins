package io.github.djtpj.trait;

import io.github.djtpj.authenticator.Authenticator;
import io.github.djtpj.ui.ItemIcon;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

@Getter
public abstract class Trait {
    private final ItemIcon icon;
    private final Type type;
    private final Authenticator<?> authenticator;

    protected Trait(String name, String description, ChatColor color, Material material, Type type, Authenticator authenticator) {
        this.authenticator = authenticator;
        authenticator.setAssociatedTrait(this);

        this.icon = new ItemIcon(name, description, color, material);
        this.type = type;
    }

    public final void enable(Player player) {}
    protected void onEnable(Player player) {}

    public final void disable(Player player) {}
    protected void onDisable(Player player) {}

    public enum Type {
        POSITIVE,
        NEUTRAL,
        NEGATIVE
    }
}