package io.github.djtpj.authenticator.authenticators;

import io.github.djtpj.ItemComparer;
import io.github.djtpj.trait.Ability;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

/** Authenticates an Item Interaction event if the player is holding a specific item */
public class ItemInteractionAuthenticator extends InteractAuthenticator {
    private final ItemStack[] items;

    /**
     * @param items            the list of items that the player must hold one of
     * @param requiresSneaking whether the player needs to be sneaking
     * @param requiresItem     whether the item must be non-placeable, edible, or interactable to trigger the event
     * @param ability          the associated ability
     * @param actions          the acceptable actions
     */
    public ItemInteractionAuthenticator(ItemStack[] items, boolean requiresSneaking, boolean requiresItem, Ability ability, Action... actions) {
        super(requiresSneaking, requiresItem, ability, actions);

        this.items = items;
    }

    /**
     * @param item the item the player must hold
     * @param requiresSneaking whether the player needs to be sneaking
     * @param requiresItem     whether the item must be non-placeable, edible, or interactable to trigger the event
     * @param ability the associated ability
     * @param actions the acceptable actions
     */
    public ItemInteractionAuthenticator(ItemStack item, boolean requiresSneaking, boolean requiresItem, Ability ability, Action... actions) {
        this(new ItemStack[] {item}, requiresSneaking, requiresItem, ability, actions);
    }

    @Override
    public boolean authenticate(PlayerInteractEvent event) {
        if (Arrays.stream(items).noneMatch(i -> ItemComparer.compareItems(i, event.getItem()))) return false;

        return super.authenticate(event);
    }
}
