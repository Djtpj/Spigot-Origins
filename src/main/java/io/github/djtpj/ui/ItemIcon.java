package io.github.djtpj.ui;

import io.github.djtpj.gui.GuiUtils;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.json.simple.JSONObject;

import javax.annotation.Nullable;
import java.util.Arrays;

@Getter
public class ItemIcon extends ItemStack {
    private final String name, description;
    private final ChatColor color;

    public ItemIcon(String name, @Nullable String description, ChatColor color, Material material) {
        super(material);

        this.name = name;
        // Format the description as description text
        this.description =  description;
        this.color = color;

        ItemMeta meta = getItemMeta();
        meta.setDisplayName(color + name);
        if (description != null && !description.isEmpty())
            meta.setLore(GuiUtils.wrap(Arrays.asList("", ChatColor.RESET + "" + ChatColor.DARK_GRAY + description)));
        setItemMeta(meta);
    }

    public ItemIcon(JSONObject object) {
        this(
                (String) object.get("name"),
                (String) object.get("desc"),
                // Upper case to ensure that there is no capitalization error.
                ChatColor.valueOf(((String) object.get("color")).toUpperCase()),
                Material.valueOf(((String) object.get("material")).toUpperCase()));
    }

}
