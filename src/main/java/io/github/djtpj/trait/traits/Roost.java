package io.github.djtpj.trait.traits;

import io.github.djtpj.authenticator.Authenticator;
import io.github.djtpj.authenticator.authenticators.PlayerAuthenticator;
import io.github.djtpj.trait.Ability;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerBedEnterEvent;

public class Roost extends Ability<PlayerBedEnterEvent> {
    private static final int MINIMUM_SLEEP_HEIGHT = 128;
    public static final String ID = "roost";

    public Roost() {
        super("Roost", "You can only sleep at high altitudes", ChatColor.GRAY, Material.RED_BED, Type.NEGATIVE);
    }

    @EventHandler
    public void preventSleep(PlayerBedEnterEvent event) {
        if (!getAuthenticator().authenticate(event)) return;

        Player player = event.getPlayer();


        if (player.getLocation().getY() < MINIMUM_SLEEP_HEIGHT) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("You cannot sleep below altitude " + MINIMUM_SLEEP_HEIGHT));
            event.setCancelled(true);
        }
    }

    @Override
    protected Authenticator<? super PlayerBedEnterEvent> getAuthenticator() {
        return new PlayerAuthenticator(this);
    }
}
