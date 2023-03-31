package io.github.djtpj.trait.traits;

import io.github.djtpj.authenticator.authenticators.PlayerAuthenticator;
import io.github.djtpj.gui.ItemIcon;
import io.github.djtpj.trait.SimpleTrait;
import io.github.djtpj.trait.UtilityTrait;
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
@UtilityTrait
public class DimensionSpawn extends SimpleTrait {
    public final static String ID = "dimension-spawn";

    private final Dimension dimension;

    public DimensionSpawn(String name, String description, ChatColor color, Material material, Type type, Dimension dimension) {
        super(name, description, color, material, type);
        this.dimension = dimension;
    }

    public DimensionSpawn(ItemIcon icon, Type type, String dimension) {
        super(icon, type);

        this.dimension = Dimension.valueOf(dimension);
    }

    @EventHandler
    public void changeSpawn(PlayerRespawnEvent event) {
        if (!new PlayerAuthenticator(this).authenticate(event)) return;

        if (event.isBedSpawn() || event.isAnchorSpawn()) return;

        event.setRespawnLocation(dimension.getSpawnLocation());
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
        END("world_the_end");

        public final String worldName;

        Dimension(String worldName) {
            this.worldName = worldName;
        }

        public Location getSpawnLocation() {
            return Bukkit.getWorld(worldName).getSpawnLocation();
        }

        public void teleport(Player player) {
            player.teleport(getSpawnLocation());
        }

        public boolean playerIn(Player player) {
            return player.getWorld().getName().equals(worldName);
        }
    }
}
