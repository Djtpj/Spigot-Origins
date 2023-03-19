package io.github.djtpj.authenticator.authenticators;

import io.github.djtpj.PlayerManager;
import io.github.djtpj.authenticator.Authenticator;
import io.github.djtpj.origins.Origin;
import io.github.djtpj.trait.Trait;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;

import java.util.Arrays;

public class PlayerAuthenticator extends Authenticator<PlayerEvent> {
    @Override
    public boolean authenticate(PlayerEvent event) {
        return playerHasTrait(event.getPlayer(), getAssociatedTrait());
    }

    public static boolean playerHasTrait(Player player, Trait trait) {
        Origin origin = PlayerManager.getInstance().getOrigin(player);

        return Arrays.asList(origin.getTraits()).contains(trait);
    }
}
