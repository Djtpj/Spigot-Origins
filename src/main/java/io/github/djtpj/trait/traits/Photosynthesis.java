package io.github.djtpj.trait.traits;

import io.github.djtpj.authenticator.authenticators.PlayerAuthenticator;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import java.util.Objects;

public class Photosynthesis extends RunnableSimpleTrait {
    public static final String ID = "photosynthesis";

    public Photosynthesis() {
        super("Photosynthesis", "You are only able to sustain yourself off of the sunlight alone by raising your head towards the sky.", ChatColor.YELLOW, Material.COOKED_BEEF, Type.POSITIVE);
    }

    @Override
    protected void onTick(Player player) {
        float pitch = player.getLocation().getPitch();

        final float REQUIRED_ANGLE = -45;
        if (pitch < REQUIRED_ANGLE && isSunny(player) && DimensionSpawn.Dimension.OVERWORLD.playerIn(player)) {
            int blockY = Objects.requireNonNull(player.getLocation().getWorld()).getHighestBlockYAt(player.getLocation());

            if (blockY <= player.getLocation().getY()) {
                // Do not let the food level exceed the ceiling
                final int FOOD_LEVEL_CEILING = 20, FOOD_INCREMENT = 2;
                int newFood = Math.min(FOOD_LEVEL_CEILING, player.getFoodLevel() + FOOD_INCREMENT);

                final float SATURATION_LEVEL_CEILING = 5.0f, SATURATION_INCREMENT = 0.5f;
                float newSaturation = Math.min(SATURATION_LEVEL_CEILING, player.getSaturation() + FOOD_INCREMENT);

                player.setFoodLevel(newFood);
                player.setSaturation(newSaturation);
            }
        }
    }

    @EventHandler
    public void preventEat(PlayerItemConsumeEvent event) {
        if (!new PlayerAuthenticator(this).authenticate(event)) return;

        if (event.getItem().getType().isEdible()) {
            event.setCancelled(true);
        }
    }

    private static boolean isSunny(Player player) {
        final long DAY_TIME = 12300, NIGHT_TIME = 23850;
        long time = player.getWorld().getTime();

        boolean isDay = time < DAY_TIME || time > NIGHT_TIME;
        if (isDay) {
            return !player.getWorld().hasStorm() && !player.getWorld().isThundering();
        }

        return false;
    }
}
