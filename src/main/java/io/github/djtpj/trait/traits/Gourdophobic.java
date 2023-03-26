package io.github.djtpj.trait.traits;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static io.github.djtpj.origin.Main.plugin;

public class Gourdophobic extends RunnableAbility {
    public static final String ID = "gourdophobic";

    public Gourdophobic() {
        super("Gourdophobic", "You unable to see people who are wearing a pumpkin on their head.", ChatColor.GOLD, Material.JACK_O_LANTERN, Type.NEGATIVE);
    }

    @Override
    protected void onTick(Player player) {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            ItemStack helmet = onlinePlayer.getInventory().getHelmet();

            if (helmet.getType() == Material.JACK_O_LANTERN) {
                player.hidePlayer(plugin, onlinePlayer);
            }

            else {
                player.showPlayer(plugin, onlinePlayer);
            }
        }
    }
}
