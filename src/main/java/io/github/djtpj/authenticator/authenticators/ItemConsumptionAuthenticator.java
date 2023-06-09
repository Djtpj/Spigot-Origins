package io.github.djtpj.authenticator.authenticators;

import io.github.djtpj.trait.SimpleTrait;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import java.util.Arrays;

public class ItemConsumptionAuthenticator extends PlayerAuthenticator<PlayerItemConsumeEvent> {
    private final Material[] materials;

    /**
     * @param associatedTrait the ability to associate with the Authenticator
     * @param materials one of the food materials the player must be eating to authenticate (leave empty for all to trigger)
     */
    public ItemConsumptionAuthenticator(SimpleTrait associatedTrait, Material... materials) {
        super(associatedTrait);
        this.materials = materials;
    }

    @Override
    public boolean authenticate(PlayerItemConsumeEvent event) {
        if (materials.length > 0) {
            if (Arrays.stream(materials).noneMatch(m -> m == event.getItem().getType())) return false;
        }

        return super.authenticate(event);
    }
}
