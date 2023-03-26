package io.github.djtpj.authenticator.authenticators;

import io.github.djtpj.authenticator.Authenticator;
import io.github.djtpj.trait.Trait;
import org.bukkit.event.block.BlockBreakEvent;

import static io.github.djtpj.authenticator.authenticators.PlayerAuthenticator.playerHasTrait;

public class BlockBreakAuthenticator extends Authenticator<BlockBreakEvent> {
    /**
     * @param associatedTrait the ability to associate with the Authenticator
     */
    public BlockBreakAuthenticator(Trait associatedTrait) {
        super(associatedTrait);
    }

    @Override
    public boolean authenticate(BlockBreakEvent event) {
        return playerHasTrait(event.getPlayer(), getAssociatedTrait());
    }
}
