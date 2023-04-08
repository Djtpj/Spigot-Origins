package io.github.djtpj.trait.traits;

import io.github.djtpj.trait.CompoundTrait;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

public class Nyctophobia extends CompoundTrait {
    public final static String ID = "nyctophobia";
    private final static int REQUIRED_LEVEL = 5;

    public Nyctophobia() {
        super("Nyctophobia", "You are scared of the dark, and take damage when you are exposed to it.", ChatColor.BLACK, Material.TORCH, Type.NEGATIVE,
            new RunnableSimpleTrait(60) {
                /**
                 * The method to run on each player every ten ticks
                 *
                 * @param player The player to run the method on
                 */
                @Override
                protected void onTick(Player player) {
                    int level = player.getLocation().getBlock().getLightLevel();

                    if (level < REQUIRED_LEVEL) {
                        player.damage(2);
                    }
                }
            },

            new DeathMessageModifier(e -> {
                Player player = e.getEntity();

                if (player.getLastDamageCause().getCause() != EntityDamageEvent.DamageCause.CUSTOM) return false;

                return player.getLocation().getBlock().getLightLevel() < REQUIRED_LEVEL;
            }, "{p} got spooked")
        );
    }

}
