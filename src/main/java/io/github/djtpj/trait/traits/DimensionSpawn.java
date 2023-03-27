package io.github.djtpj.trait.traits;

import io.github.djtpj.authenticator.authenticators.PlayerAuthenticator;
import io.github.djtpj.trait.Ability;
import io.github.djtpj.trait.UtilityAbility;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerRespawnEvent;

/**
 * Changes what dimension a player respawns in
 */
@UtilityAbility
public class DimensionSpawn extends Ability {
    public final static String ID = "dimension-spawn";

    private final Dimension dimension;

    public DimensionSpawn(String name, String description, ChatColor color, Material material, Type type, Dimension dimension) {
        super(name, description, color, material, type);
        this.dimension = dimension;
    }

    public DimensionSpawn(String name, String description, String color, String material, String type, String dimension) {
        super(name, description, color, material, type);

        this.dimension = Dimension.valueOf(dimension);
    }

    @EventHandler
    public void changeSpawn(PlayerRespawnEvent event) {
        if (!new PlayerAuthenticator(this).authenticate(event)) return;

        if (event.isBedSpawn() || event.isAnchorSpawn()) return;

        event.setRespawnLocation(dimension.spawnLocation);
    }

    @Override
    protected void onEnable(Player player) {
        dimension.teleport(player);
    }

    @Override
    protected void onDisable(Player player) {
        if (dimension.playerIn(player)) {
            // Teleport back to the Overworld, because that's the default dimension
            Dimension.OVERWORLD.teleport(player);
        }
    }

    public enum Dimension {
        OVERWORLD("world"),
        NETHER("world_nether"),
        END("world_end");

        public final String worldName;
        public final Location spawnLocation;

        Dimension(String worldName) {
            this.worldName = worldName;
            this.spawnLocation = Bukkit.getWorld(worldName).getSpawnLocation();
        }

        public void teleport(Player player) {
            player.teleport(spawnLocation);
        }

        public boolean playerIn(Player player) {
            return player.getWorld().getName().equals(spawnLocation.getWorld().getName());
        }
    }
}
