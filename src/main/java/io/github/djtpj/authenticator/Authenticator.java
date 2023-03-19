package io.github.djtpj.authenticator;

import io.github.djtpj.trait.Trait;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Event;

public abstract class Authenticator<T extends Event> {
    @Getter
    @Setter
    private Trait associatedTrait;

    public abstract boolean authenticate(T event);
}
