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
     * @param ability          the associated ability
     * @param actions          the acceptable actions
     */
    public ItemInteractionAuthenticator(ItemStack[] items, boolean requiresSneaking, Ability ability, Action... actions) {
        super(requiresSneaking, ability, actions);

        this.items = items;
    }

    /**
     * @param item the item the player must hold
     * @param requiresSneaking whether the player needds to be sneaking
     * @param ability the associated ability
     * @param actions the acceptable actions
     */
    public ItemInteractionAuthenticator(ItemStack item, boolean requiresSneaking, Ability ability, Action... actions) {
        this(new ItemStack[] {item}, requiresSneaking, ability, actions);
    }

    @Override
    public boolean authenticate(PlayerInteractEvent event) {
        if (Arrays.stream(items).anyMatch(i -> ItemComparer.compareItems(i, event.getItem()))) return true;

        return super.authenticate(event);
    }
}
