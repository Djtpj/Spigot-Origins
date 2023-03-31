package io.github.djtpj.trait.traits;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Objects;

public class Photosensitivity extends RunnableSimpleTrait {
    public final static String ID = "photosensitivity";

    public Photosensitivity() {
        super("Photosensitivity", "Your skin is highly sensitive to daylight, and you have a tendency to burst into flames when exposed to it.", ChatColor.GOLD, Material.FLINT_AND_STEEL, Type.NEGATIVE);
    }

    @Override
    protected void onTick(Player player) {
        long time = player.getWorld().getTime();

        final long DAY_TIME = 12300, NIGHT_TIME = 23850;
        if (time >= DAY_TIME && time <= NIGHT_TIME) return;

        if (player.getWorld().hasStorm() || player.getWorld().isThundering()) return;

        int blockLocation = Objects.requireNonNull(player.getLocation().getWorld()).getHighestBlockYAt(player.getLocation());

        if (blockLocation <= player.getLocation().getY() && !player.isInvisible()) {
            player.setFireTicks(60);
        }
    }
}
