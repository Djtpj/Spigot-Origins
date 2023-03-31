package io.github.djtpj.authenticator.authenticators;

import io.github.djtpj.authenticator.Authenticator;
import io.github.djtpj.trait.SimpleTrait;
import io.github.djtpj.trait.Trait;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityEvent;

import java.util.function.Function;

public class EntityAuthenticator <T extends EntityEvent> extends Authenticator<T> {
    private final Function<T, Entity> retreiveEntity;

    public EntityAuthenticator(SimpleTrait trait) {
        super(trait);
        retreiveEntity = EntityEvent::getEntity;
    }

    public EntityAuthenticator(Trait associatedTrait, Function<T, Entity> retreiveEntity) {
        super(associatedTrait);
        this.retreiveEntity = retreiveEntity;
    }

    @Override
    public boolean authenticate(T event) {
        Entity entity = (retreiveEntity != null) ? retreiveEntity.apply(event) : event.getEntity();

        if (!(entity instanceof Player player)) return false;

        return PlayerAuthenticator.playerHasTrait(player, getAssociatedTrait());
    }
}
