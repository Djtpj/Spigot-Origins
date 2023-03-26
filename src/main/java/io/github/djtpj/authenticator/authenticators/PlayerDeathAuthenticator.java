package io.github.djtpj.authenticator.authenticators;

import io.github.djtpj.authenticator.Authenticator;
import io.github.djtpj.trait.Ability;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathAuthenticator extends Authenticator<PlayerDeathEvent> {
    public PlayerDeathAuthenticator(Ability ability) {
        super(ability);
    }

    @Override
    public boolean authenticate(PlayerDeathEvent event) {
        return PlayerAuthenticator.playerHasTrait(event.getEntity(), getAssociatedAbility());
    }
}
