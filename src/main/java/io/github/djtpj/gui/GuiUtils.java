package io.github.djtpj.gui;

import io.github.djtpj.ItemComparer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.ChatPaginator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GuiUtils {
    public static final int LINE_LENGTH = 45;

    public static void populateEmptySlots(Inventory inventory) {
        // Fill the empty slots with the filler item
        for (int i = 0; i < inventory.getSize(); i++) {
            ItemStack item = inventory.getItem(i);

            if (item != null) continue;

            inventory.setItem(i, fillerItem());
        }
    }

    public static ItemStack fillerItem() {
        ItemStack item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.RESET + "");

        item.setItemMeta(meta);

        return item;
    }

    public static boolean isFillerItem(ItemStack item) {
        return ItemComparer.compareItems(fillerItem(), item);
    }

    public static List<String> wrap(List<String> base) {
        ArrayList<String> results = new ArrayList<>();

        for (String s : base) {
            results.addAll(Arrays.asList(ChatPaginator.wordWrap(s, LINE_LENGTH)));
        }

        return results;
    }
}
