package io.github.djtpj.trait;

import io.github.djtpj.authenticator.Authenticator;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

@Getter
public abstract class Ability<T extends Event> extends Trait {
    public final static String ID = "ability";

    protected Ability(String name, String description, ChatColor color, Material material, Trait.Type type) {
        super(name, description, color, material, type);
    }

    protected Ability(CompoundAbility ability) {
        this(ability.icon.getName(), ability.icon.getDescription(), ability.icon.getColor(), ability.icon.getType(), ability.type);
    }

    @Nullable
    protected abstract Authenticator<? super T> getAuthenticator();
}