package io.github.djtpj.authenticator.authenticators;

import io.github.djtpj.authenticator.Authenticator;
import io.github.djtpj.trait.Ability;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityEvent;

public class EntityAuthenticator extends Authenticator<EntityEvent> {
    public EntityAuthenticator(Ability ability) {
        super(ability);
    }

    @Override
    public boolean authenticate(EntityEvent event) {
        if (!(event.getEntity() instanceof Player player)) return false;

        return PlayerAuthenticator.playerHasTrait(player, getAssociatedTrait());
    }
}
