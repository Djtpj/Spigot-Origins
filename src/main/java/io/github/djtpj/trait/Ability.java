package io.github.djtpj.trait;

import io.github.djtpj.authenticator.Authenticator;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

/** An Ability is a single Trait that performs one method on a specific event
 * @param <T> the Event to listen to and authenticate
 */
@Getter
public abstract class Ability<T extends Event> extends Trait {
    public final static String ID = "ability";

    protected Ability(String name, String description, ChatColor color, Material material, Trait.Type type) {
        super(name, description, color, material, type);

        register();
    }

    protected Ability() {
        this(null, null, null, null, null);
    }

    @Nullable
    protected abstract Authenticator<? super T> getAuthenticator();
}