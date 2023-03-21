package io.github.djtpj.authenticator.authenticators;

import io.github.djtpj.trait.Ability;
import org.bukkit.Material;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Arrays;

public class MaterialInteractionAuthenticator extends InteractAuthenticator {
    private final Material[] requiredMaterials;

    /**
     * @param requiresSneaking  whether the player needs to be sneaking
     * @param ability           the associated ability
     * @param requiredMaterials one of the materials required for the player to be holding
     * @param actions           the acceptable actions
     */
    public MaterialInteractionAuthenticator(boolean requiresSneaking, Ability ability, Material[] requiredMaterials, Action... actions) {
        super(requiresSneaking, ability, actions);
        this.requiredMaterials = requiredMaterials;
    }

    public MaterialInteractionAuthenticator(boolean requiresSneaking, Ability ability, Material requiredMaterial, Action... actions) {
        this(requiresSneaking, ability, new Material[] {requiredMaterial}, actions);
    }

    @Override
    public boolean authenticate(PlayerInteractEvent event) {
        if (Arrays.stream(requiredMaterials).noneMatch(m -> event.getItem().getType() == m)) return false;

        return super.authenticate(event);
    }
}
