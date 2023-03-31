package io.github.djtpj.trait.traits;

import io.github.djtpj.authenticator.authenticators.InteractAuthenticator;
import io.github.djtpj.authenticator.authenticators.PlayerTeleportationAuthenticator;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class SpatialJumper extends CooldownSimpleTrait {
    public final static String ID = "spatial-jumper";

    public SpatialJumper() {
        super(
                "Spatial Jumper",
                "You are able to teleport by shift-right clicking with an item in your hand.",
                ChatColor.DARK_PURPLE,
                Material.ENDER_PEARL,
                Type.POSITIVE,
                50
                );
    }

    @EventHandler
    public void teleport(PlayerInteractEvent event) {
        if (!new InteractAuthenticator(true, true, this, Action.RIGHT_CLICK_AIR, Action.RIGHT_CLICK_BLOCK).authenticate(event)) return;

        Player player = event.getPlayer();

        if (!getCooldownReady(player)) {
            sendCooldownMessage(player);
            return;
        }

        player.launchProjectile(EnderPearl.class);
        startCooldown(player);
        event.setCancelled(true);
    }

    @EventHandler
    public void preventDamage(PlayerTeleportEvent event) {
        if (!new PlayerTeleportationAuthenticator(this, PlayerTeleportEvent.TeleportCause.ENDER_PEARL).authenticate(event)) return;

        Player player = event.getPlayer();

        event.setCancelled(true);

        player.teleport(event.getTo());
        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 0.5f, 1);
    }
}
