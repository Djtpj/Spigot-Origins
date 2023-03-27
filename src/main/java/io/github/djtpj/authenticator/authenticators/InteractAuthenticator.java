package io.github.djtpj.authenticator.authenticators;

import io.github.djtpj.trait.Trait;
import org.bukkit.Material;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Arrays;

/** Authenticates an Interact Event */
public class InteractAuthenticator extends PlayerAuthenticator<PlayerInteractEvent> {
    private final Action[] actions;
    private final boolean requiresSneaking;
    private final boolean requiresItem;

    /**
     * @param requiresSneaking whether the player needs to be sneaking
     * @param requiresItem     whether the item must be non-placeable, edible, or interactable to trigger the event
     * @param trait            the associated trait
     * @param actions          the acceptable actions
     */
    public InteractAuthenticator(boolean requiresSneaking, boolean requiresItem, Trait trait, Action... actions) {
        super(trait);
        this.requiresSneaking = requiresSneaking;
        this.requiresItem = requiresItem;
        this.actions = actions;
    }

    @Override
    public boolean authenticate(PlayerInteractEvent event) {
        if (!Arrays.asList(actions).contains(event.getAction())) return false;

        if (!playerHasTrait(event.getPlayer(), getAssociatedTrait())) return false;

        if (requiresItem && event.getItem() != null) {
            Material material = event.getItem().getType();

            if (material.isEdible() || material.isBlock()) {
                return false;
            }
        }

        return !(requiresSneaking && !event.getPlayer().isSneaking());
    }
}
