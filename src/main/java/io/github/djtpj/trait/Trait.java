package io.github.djtpj.trait;

import io.github.djtpj.authenticator.Authenticator;
import io.github.djtpj.ui.ItemIcon;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;

@Getter
public abstract class Trait <T extends Event> implements Listener {
    protected final ItemIcon icon;
    protected final Type type;
    protected final Authenticator<? super T> authenticator;

    protected Trait(String name, String description, ChatColor color, Material material, Type type, Authenticator<? super T> authenticator) {
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