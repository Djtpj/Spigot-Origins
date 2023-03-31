package io.github.djtpj.authenticator.authenticators;

import io.github.djtpj.trait.SimpleTrait;
import org.bukkit.Material;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Arrays;

public class MaterialInteractionAuthenticator extends InteractAuthenticator {
    private final Material[] requiredMaterials;

    /**
     * @param requiresSneaking  whether the player needs to be sneaking
     * @param requiresItem     whether the item must be non-placeable, edible, or interactable to trigger the event
     * @param simpleTrait           the associated simpleTrait
     * @param requiredMaterials one of the materials required for the player to be holding
     * @param actions           the acceptable actions
     */
    public MaterialInteractionAuthenticator(boolean requiresSneaking, boolean requiresItem, SimpleTrait simpleTrait, Material[] requiredMaterials, Action... actions) {
        super(requiresSneaking, requiresItem, simpleTrait, actions);
        this.requiredMaterials = requiredMaterials;
    }

    public MaterialInteractionAuthenticator(boolean requiresSneaking, boolean requiresItem, SimpleTrait simpleTrait, Material requiredMaterial, Action... actions) {
        this(requiresSneaking, requiresItem, simpleTrait, new Material[] {requiredMaterial}, actions);
    }

    @Override
    public boolean authenticate(PlayerInteractEvent event) {
        if (Arrays.stream(requiredMaterials).noneMatch(m -> event.getItem().getType() == m)) return false;

        return super.authenticate(event);
    }
}
