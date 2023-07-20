package io.github.djtpj.trait.traits;

import io.github.djtpj.trait.CompoundTrait;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Objects;

public class Hydrophobic extends CompoundTrait {
    public final static String ID = "hydrophobic";

    public Hydrophobic() {
        super(
                "Hydrophobic",
                "You react poorly to touching water.",
                ChatColor.DARK_AQUA,
                Material.WATER_BUCKET,
                Type.NEGATIVE,

                new DeathMessageModifier (
                        e -> inRain(e.getEntity()) || e.getEntity().isInWater(),
                        "{p} got soaked"
                ),

                new RunnableSimpleTrait(20) {
                    @Override
                    protected void onTick(Player player) {
                        final int damage = 2;

                        if (player.isInWater() || inRain(player)) {
                            player.damage(damage);
                        }
                    }
                }
        );
    }

    private static boolean inRain(Player player) {
        boolean isRaining = player.getWorld().isThundering() || player.getWorld().hasStorm();

        int blockLocation = Objects.requireNonNull(player.getLocation().getWorld()).getHighestBlockYAt(player.getLocation());

        boolean isUnderBlock = blockLocation > player.getEyeLocation().getY();

        return isRaining && !isUnderBlock;
    }
}
