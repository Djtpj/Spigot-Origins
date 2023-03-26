package io.github.djtpj.authenticator.authenticators;

import io.github.djtpj.authenticator.Authenticator;
import io.github.djtpj.trait.Trait;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityTargetEvent;

import java.util.Arrays;

import static io.github.djtpj.authenticator.authenticators.PlayerAuthenticator.playerHasTrait;

/**
 * Checks that the target of an {@link EntityTargetEvent} is an applicable player, and that the {@code Entity} of the event is one of a specified types.
 */
public class EntityTargetAuthenticator extends Authenticator<EntityTargetEvent> {
    private final EntityType[] requiredType;

    /**
     * @param associatedTrait the ability to associate with the Authenticator
     * @param requiredType the type the entity (not the target) must be (if left empty, all {@code EntityTypes} will pass through
     */
    public EntityTargetAuthenticator(Trait associatedTrait, EntityType... requiredType) {
        super(associatedTrait);
        this.requiredType = requiredType;
    }

    @Override
    public boolean authenticate(EntityTargetEvent event) {
        if (!(event.getTarget() instanceof Player player)) return false;
        if (requiredType.length > 0 && !(Arrays.stream(requiredType).toList().contains(event.getEntityType()))) return false;

        return playerHasTrait(player, getAssociatedTrait());
    }
}
