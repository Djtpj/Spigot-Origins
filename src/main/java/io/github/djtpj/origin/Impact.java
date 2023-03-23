package io.github.djtpj.origin;

import io.github.djtpj.gui.GuiUtils;
import io.github.djtpj.gui.ItemIcon;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum Impact {
    NONE(GuiUtils.fillerItem().getType(), ChatColor.DARK_GRAY),
    LOW(Material.LIME_STAINED_GLASS_PANE, "Low", ChatColor.GREEN),
    MEDIUM(Material.YELLOW_STAINED_GLASS_PANE, "Medium", ChatColor.YELLOW),
    HIGH(Material.RED_STAINED_GLASS_PANE, "High", ChatColor.RED);

    private final Material icon;
    private final String name;
    private final ChatColor color;

    Impact(Material icon, ChatColor color) {
        // Default to having no name (like a filler item)
        this(icon, " ", color);
    }

    Impact(Material icon, String name, ChatColor color) {
        this.icon = icon;
        this.name = name;
        this.color = color;
    }

    public ItemStack getIcon() {
        return new ItemIcon(
                // If the pane has a name, then put impact after it, otherwise, just put the white space
                (!name.isBlank()) ? name + " Impact" : name,
                null, color, icon);
    }
}
