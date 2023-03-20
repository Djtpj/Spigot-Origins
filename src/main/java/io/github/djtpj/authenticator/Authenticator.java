package io.github.djtpj.authenticator;

import io.github.djtpj.trait.Ability;
import lombok.Getter;
import org.bukkit.event.Event;

/** An Authenticator is a reusable way to check if an Ability should run on an event
 * @param <T> the Event to Authenticate
 * @see Event
 * @see Ability
 */
public abstract class Authenticator<T extends Event> {
    @Getter
    private final Ability associatedAbility;

    /**
     * @param associatedAbility the ability to associate with the Authenticator
     */
    protected Authenticator(Ability associatedAbility) {
        this.associatedAbility = associatedAbility;
    }

    /** Verifies weather a trait should run on an event
     * @param event The event to authenticate
     * @return whether a trait should run on this event
     */
    public abstract boolean authenticate(T event);
}
