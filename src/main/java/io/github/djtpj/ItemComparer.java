package io.github.djtpj;

import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

public class ItemComparer {
    public static boolean compareItems(@Nullable ItemStack itemOne, @Nullable ItemStack itemTwo) {
        if (itemOne == null || itemTwo == null) return false;

        if (itemTwo.getItemMeta() != null) {
            if (!itemTwo.getItemMeta().equals(itemOne.getItemMeta())) return false;

            if (!itemTwo.getEnchantments().equals(itemOne.getEnchantments())) return false;

            if (!itemTwo.getType().equals(itemOne.getType())) return false;

            if (!itemTwo.getItemMeta().isUnbreakable() == itemOne.getItemMeta().isUnbreakable()) return false;

            return true;
        }

        return false;
    }
}
