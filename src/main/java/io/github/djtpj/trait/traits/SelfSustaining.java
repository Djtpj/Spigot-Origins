package io.github.djtpj.trait.traits;

import io.github.djtpj.authenticator.authenticators.EntityAuthenticator;
import io.github.djtpj.trait.Ability;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class SelfSustaining extends Ability {
    public static final String ID = "self-sustaining";

    public SelfSustaining() {
        super("Self Sustaining", "You never need to eat.", ChatColor.DARK_GREEN, Material.GOLDEN_CARROT, Type.POSITIVE);
    }

    @EventHandler
    public void preventLoss(FoodLevelChangeEvent event) {
        if (!new EntityAuthenticator(this).authenticate(event)) return;

        event.setCancelled(true);
    }

    @Override
    protected void onEnable(Player player) {
        // Max out the player's food level so that they can't be stuck at a non-full food level
        player.setFoodLevel(20);
    }
}
