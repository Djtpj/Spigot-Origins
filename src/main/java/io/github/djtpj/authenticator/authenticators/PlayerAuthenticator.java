package io.github.djtpj.authenticator.authenticators;

import io.github.djtpj.PlayerManager;
import io.github.djtpj.authenticator.Authenticator;
import io.github.djtpj.origin.Origin;
import io.github.djtpj.trait.Ability;
import io.github.djtpj.trait.Trait;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;

import java.util.Arrays;

public class PlayerAuthenticator extends Authenticator<PlayerEvent> {
    public PlayerAuthenticator(Ability ability) {
        super(ability);
    }

    @Override
    public boolean authenticate(PlayerEvent event) {
        return playerHasTrait(event.getPlayer(), getAssociatedAbility());
    }

    public static boolean playerHasTrait(Player player, Trait trait) {
        Origin origin = PlayerManager.getInstance().getOrigin(player);

        return Arrays.asList(origin.getAllTraits()).contains(trait);
    }
}
