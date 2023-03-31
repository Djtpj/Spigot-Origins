package io.github.djtpj.trait.traits;

import io.github.djtpj.authenticator.authenticators.BlockBreakAuthenticator;
import io.github.djtpj.trait.SimpleTrait;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SilkyHands extends SimpleTrait {
    public static final String ID = "silky-hands";

    public SilkyHands() {
        super("Silky Hands", "You are able to pick up any block like you had silk touch, as long as you use your bare hands.", ChatColor.AQUA, Material.DIAMOND_PICKAXE, Type.POSITIVE);
    }

    @EventHandler
    public void pickup(BlockBreakEvent event) {
        if (!new BlockBreakAuthenticator(this).authenticate(event)) return;

        // The player must be using their bare hands
        if (!event.getPlayer().getInventory().getItemInMainHand().getType().isAir()) return;

        ItemStack item = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(Enchantment.SILK_TOUCH, 1, false);
        item.setItemMeta(meta);

        // Break the block as if the player was using a silk touch pickaxe
        event.getBlock().breakNaturally(item);
    }
}