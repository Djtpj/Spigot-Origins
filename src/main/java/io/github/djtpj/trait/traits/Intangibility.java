package io.github.djtpj.trait.traits;

import io.github.djtpj.authenticator.authenticators.InteractAuthenticator;
import io.github.djtpj.authenticator.authenticators.PlayerAuthenticator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import java.util.Optional;

import static io.github.djtpj.origin.Main.plugin;

public class Intangibility extends CooldownSimpleTrait {
    public static final String ID = "intangibility";

    public Intangibility() {
        super("Intangibility", "You are able to phase through blocks by shift right clicking.", ChatColor.DARK_GRAY, Material.GLASS, Type.POSITIVE, 5 * 20);
    }

    @EventHandler
    public void phase(PlayerInteractEvent event) {
        if (!new InteractAuthenticator(true, true, this, Action.RIGHT_CLICK_BLOCK, Action.RIGHT_CLICK_AIR).authenticate(event)) return;

        Player player = event.getPlayer();

        if (!getCooldownReady(player)) {
            return;
        }

        player.setGameMode(GameMode.SPECTATOR);

        player.setMetadata("phantomPhased", new FixedMetadataValue(plugin, true));
        startCooldown(player);
    }

    @EventHandler
    public void unphase(PlayerMoveEvent event) {
        if (!new PlayerAuthenticator(this).authenticate(event)) return;

        Player player = event.getPlayer();

        if (player.hasMetadata("phantomPhased")) {
            Optional<MetadataValue> optional = player.getMetadata("phantomPhased").stream().filter(m -> m.getOwningPlugin().equals(plugin)).findFirst();
            if (optional.isEmpty()) return;

            boolean phased = optional.get().asBoolean();

            if (phased && notInBlock(player)) {
                player.setGameMode(Bukkit.getDefaultGameMode());
                player.setMetadata("phantomPhased", new FixedMetadataValue(plugin, false));
            }
        }
    }

    private static boolean notInBlock(Player player) {
        return player.getLocation().getBlock().getType().isAir() && player.getEyeLocation().getBlock().getType().isAir();
    }
}
