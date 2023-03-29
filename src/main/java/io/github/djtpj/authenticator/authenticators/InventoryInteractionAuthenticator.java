package io.github.djtpj.authenticator.authenticators;

import io.github.djtpj.authenticator.Authenticator;
import io.github.djtpj.trait.Trait;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryInteractEvent;

public class InventoryInteractionAuthenticator extends Authenticator<InventoryInteractEvent> {
    public InventoryInteractionAuthenticator(Trait trait) {
        super(trait);
    }

    /**
     * Verifies weather a trait should run on an event
     *
     * @param event The event to authenticate
     * @return whether a trait should run on this event
     */
    @Override
    public boolean authenticate(InventoryInteractEvent event) {
        return PlayerAuthenticator.playerHasTrait((Player) event.getWhoClicked(), getAssociatedTrait());
    }
}
