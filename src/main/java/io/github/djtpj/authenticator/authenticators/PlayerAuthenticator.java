package io.github.djtpj.authenticator.authenticators;

import io.github.djtpj.PlayerManager;
import io.github.djtpj.authenticator.Authenticator;
import io.github.djtpj.origin.Origin;
import io.github.djtpj.trait.Trait;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;

import java.util.Arrays;

public class PlayerAuthenticator <T extends PlayerEvent> extends Authenticator<T> {
    public PlayerAuthenticator(Trait trait) {
        super(trait);
    }

    @Override
    public boolean authenticate(T event) {
        return playerHasTrait(event.getPlayer(), getAssociatedTrait());
    }

    public static boolean playerHasTrait(Player player, Trait trait) {
        Origin origin = PlayerManager.getInstance().getOrigin(player);

        if (origin == null) return false;

        return Arrays.asList(origin.getAllTraits()).contains(trait);
    }
}
