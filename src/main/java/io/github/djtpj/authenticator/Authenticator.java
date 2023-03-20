package io.github.djtpj.authenticator;

import io.github.djtpj.trait.Ability;
import lombok.Getter;
import org.bukkit.event.Event;

public abstract class Authenticator<T extends Event> {
    @Getter
    private final Ability associatedAbility;

    protected Authenticator(Ability associatedAbility) {
        this.associatedAbility = associatedAbility;
    }

    public abstract boolean authenticate(T event);
}
