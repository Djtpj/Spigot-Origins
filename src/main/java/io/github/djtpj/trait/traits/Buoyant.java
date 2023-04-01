package io.github.djtpj.trait.traits;

import io.github.djtpj.authenticator.authenticators.PlayerAuthenticator;
import io.github.djtpj.trait.SimpleTrait;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

public class Buoyant extends SimpleTrait {
    public static final String ID = "buoyant";

    public Buoyant() {
        super("Buoyant", "You do not sink in water.", ChatColor.AQUA, Material.WATER_BUCKET, Type.POSITIVE);
    }

    @EventHandler
    public void preventSink(PlayerMoveEvent event) {
        if (!new PlayerAuthenticator(this).authenticate(event)) return;

        Player player = event.getPlayer();

        if (!player.isInWater()) return;

        if (player.isSneaking() || player.isSwimming()) return;

        player.setFlying(true);
    }

    @EventHandler
    public void cancelSinkPrevention(PlayerMoveEvent event) {
        if (!new PlayerAuthenticator(this).authenticate(event)) return;

        Player player = event.getPlayer();

        Location to = event.getTo();
        Location from = event.getFrom();

        if (player.isInWater()) {
            if (player.isSneaking() || player.isSwimming()) {
                player.setFlying(false);
            }
        }

        else {
            if (player.getGameMode() == GameMode.SURVIVAL) {
                player.setFlying(false);
            }
        }
    }

    @Override
    protected void onEnable(Player player) {
        player.setAllowFlight(true);
    }

    @Override
    protected void onDisable(Player player) {
        player.setAllowFlight(false);
    }
}
