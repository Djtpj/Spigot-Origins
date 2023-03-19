package io.github.djtpj.items;

import io.github.djtpj.authenticator.authenticators.InteractAuthenticator;
import io.github.djtpj.gui.OriginPicker;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

import static io.github.djtpj.origins.Main.plugin;

public class OriginOrb extends ItemStack implements Listener {
    private final static String NAMESPACE_KEY = "orb_of_origin";

    static {
        // Register this as a listener
        plugin.getServer().getPluginManager().registerEvents(new OriginOrb(), plugin);

    }

    public OriginOrb() {
        super(Material.FIRE_CHARGE);

        ItemMeta meta = getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Orb Of Origin");
        meta.setLore(Arrays.asList("", ChatColor.RESET + "" + ChatColor.DARK_GRAY + "Right click this item to change your origin."));
        meta.addEnchant(Enchantment.DAMAGE_ALL, 0, true);
        meta.addItemFlags(ItemFlag.values());
        setItemMeta(meta);
    }

    @EventHandler
    public void changeOrigin(PlayerInteractEvent event) {
        InteractAuthenticator authenticator = new InteractAuthenticator(this, false, Action.RIGHT_CLICK_AIR, Action.RIGHT_CLICK_BLOCK);

        if (!authenticator.authenticate(event)) {
            return;
        }

        new OriginPicker(event.getPlayer());

        event.setCancelled(true);
    }

    public static Recipe recipe() {
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin, NAMESPACE_KEY), new OriginOrb());

        recipe.shape("GDG", "DED", "GDG");

        recipe.setIngredient('G', Material.GOLD_INGOT);
        recipe.setIngredient('D', Material.DIAMOND);
        recipe.setIngredient('E', Material.ENDER_PEARL);

        return recipe;
    }
}
