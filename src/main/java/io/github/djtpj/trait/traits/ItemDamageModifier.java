package io.github.djtpj.trait.traits;

import io.github.djtpj.ItemComparer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Predicate;

public class ItemDamageModifier extends DamageModifier {
    public final static String ID = "item-damage-mod";

    public ItemDamageModifier(String name, String description, ChatColor color, Material material, Type type, Double damage, ItemStack item) {
        super(name, description, color, material, type, damage, generateShouldModify(item));
    }

    public ItemDamageModifier(String name, String description, ChatColor color, Material material, Type type, Double damage, Material item) {
        super(name, description, color, material, type, damage, generateShouldModify(new ItemStack(item)));
    }

    public ItemDamageModifier(Double damage, ItemStack itemStack) {
        super(damage, generateShouldModify(itemStack));
    }

    public ItemDamageModifier(Double damage, Material material) {
        super(damage, generateShouldModify(new ItemStack(material)));
    }

    public ItemDamageModifier(String name, String description, String color, String material, String type, Double damage, String itemType) {
        super(name, description, color, material, type, damage, generateShouldModify(new ItemStack(Material.valueOf(itemType))));
    }

    private static Predicate<EntityDamageByEntityEvent> generateShouldModify(ItemStack item) {
        return (e) -> {
            Player player = (Player) e.getDamager();

            return ItemComparer.compareItems(item, player.getItemInUse());
        };
    }
}
