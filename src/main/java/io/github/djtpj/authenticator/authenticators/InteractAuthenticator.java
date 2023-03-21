package io.github.djtpj.authenticator.authenticators;

import io.github.djtpj.trait.Ability;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Arrays;

/** Authenticates an Interact Event */
public class InteractAuthenticator extends PlayerAuthenticator<PlayerInteractEvent> {
    private final Action[] actions;
    private final boolean requiresSneaking;

    /**
     * @param requiresSneaking whether the player needs to be sneaking
     * @param ability the associated ability
     * @param actions the acceptable actions
     */
    public InteractAuthenticator(boolean requiresSneaking, Ability ability, Action... actions) {
        super(ability);
        this.requiresSneaking = requiresSneaking;
        this.actions = actions;
    }

    @Override
    public boolean authenticate(PlayerInteractEvent event) {
        if (!Arrays.asList(actions).contains(event.getAction())) return false;

        return !(requiresSneaking && !event.getPlayer().isSneaking());
    }
}
