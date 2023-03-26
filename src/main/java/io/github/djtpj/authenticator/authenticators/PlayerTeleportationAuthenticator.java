package io.github.djtpj.authenticator.authenticators;

import io.github.djtpj.trait.Trait;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.Arrays;

public class PlayerTeleportationAuthenticator extends PlayerAuthenticator<PlayerTeleportEvent> {
    private final PlayerTeleportEvent.TeleportCause[] causes;

    public PlayerTeleportationAuthenticator(Trait trait, PlayerTeleportEvent.TeleportCause... causes) {
        super(trait);
        this.causes = causes;
    }

    @Override
    public boolean authenticate(PlayerTeleportEvent event) {
        if (!Arrays.stream(causes).toList().contains(event.getCause())) return false;

        return super.authenticate(event);
    }
}
