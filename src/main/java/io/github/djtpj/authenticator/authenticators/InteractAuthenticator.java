package io.github.djtpj.authenticator.authenticators;

import io.github.djtpj.ItemComparer;
import io.github.djtpj.authenticator.Authenticator;
import io.github.djtpj.trait.Ability;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class InteractAuthenticator extends Authenticator<PlayerInteractEvent> {
    private final ItemStack item;
    private final Action[] actions;
    private final boolean requiresSneaking;

    public InteractAuthenticator(ItemStack item, boolean requiresSneaking, Ability ability, Action... actions) {
        super(ability);
        this.item = item;
        this.requiresSneaking = requiresSneaking;
        this.actions = actions;
    }

    @Override
    public boolean authenticate(PlayerInteractEvent event) {
        if (!Arrays.asList(actions).contains(event.getAction())) return false;
        if (requiresSneaking && !event.getPlayer().isSneaking()) return false;

        return ItemComparer.compareItems(item, event.getItem());
    }
}
