package io.github.djtpj.authenticator;

import io.github.djtpj.trait.SimpleTrait;
import io.github.djtpj.trait.Trait;
import lombok.Getter;
import org.bukkit.event.Event;

/** An Authenticator is a reusable way to check if an SimpleTrait should run on an event
 * @param <T> the Event to Authenticate
 * @see Event
 * @see SimpleTrait
 */
public abstract class Authenticator<T extends Event> {
    @Getter
    private final Trait associatedTrait;

    /**
     * @param associatedTrait the ability to associate with the Authenticator
     */
    protected Authenticator(Trait associatedTrait) {
        this.associatedTrait = associatedTrait;
    }

    /** Verifies weather a trait should run on an event
     * @param event The event to authenticate
     * @return whether a trait should run on this event
     */
    public abstract boolean authenticate(T event);
}
