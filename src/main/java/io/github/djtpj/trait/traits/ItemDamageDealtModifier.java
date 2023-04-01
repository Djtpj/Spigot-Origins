package io.github.djtpj.trait.traits;

import io.github.djtpj.gui.ItemIcon;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Predicate;

public class ItemDamageDealtModifier extends DamageDealtModifier {
    public final static String ID = "item-damage-mod";

    public ItemDamageDealtModifier(String name, String description, ChatColor color, Material material, Type type, Double damage, ItemStack item) {
        super(name, description, color, material, type, damage, generateShouldModify(item));
    }

    public ItemDamageDealtModifier(String name, String description, ChatColor color, Material material, Type type, Double damage, Material item) {
        super(name, description, color, material, type, damage, generateShouldModify(new ItemStack(item)));
    }

    public ItemDamageDealtModifier(Double damage, ItemStack itemStack) {
        super(damage, generateShouldModify(itemStack));
    }

    public ItemDamageDealtModifier(Double damage, Material material) {
        super(damage, generateShouldModify(new ItemStack(material)));
    }

    public ItemDamageDealtModifier(ItemIcon icon, Type type, Double damage, String itemType) {
        super(icon, type, damage, generateShouldModify(new ItemStack(Material.valueOf(itemType))));
    }

    private static Predicate<EntityDamageByEntityEvent> generateShouldModify(ItemStack item) {
        return (e) -> {
            Player player = (Player) e.getDamager();

            return player.getInventory().getItemInMainHand().getType() == item.getType();
        };
    }
}
