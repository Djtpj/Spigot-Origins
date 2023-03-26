package io.github.djtpj.trait.traits;

import io.github.djtpj.authenticator.authenticators.PlayerAuthenticator;
import io.github.djtpj.trait.Ability;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerRespawnEvent;

public class DimensionSpawn extends Ability {
    public final static String ID = "dimension-spawn";

    private final Location spawnLocation;

    public DimensionSpawn(String name, String description, ChatColor color, Material material, Type type, String worldName) {
        super(name, description, color, material, type);

        spawnLocation = Bukkit.getWorld(worldName).getSpawnLocation();
    }

    public DimensionSpawn(String name, String description, String color, String material, String type, String worldName) {
        this(name, description, ChatColor.valueOf(color), Material.valueOf(material), Type.valueOf(type), worldName);
    }

    @EventHandler
    public void changeSpawn(PlayerRespawnEvent event) {
        if (!new PlayerAuthenticator(this).authenticate(event)) return;

        if (event.isBedSpawn() || event.isAnchorSpawn()) return;

        event.setRespawnLocation(spawnLocation);
    }

    @Override
    protected void onEnable(Player player) {
        player.teleport(spawnLocation);
    }

    @Override
    protected void onDisable(Player player) {
        if (player.getWorld().getName().equals(spawnLocation.getWorld().getName())) {
            player.teleport(Bukkit.getWorld("world").getSpawnLocation());
        }
    }
}
