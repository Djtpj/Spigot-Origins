package io.github.djtpj.gui;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import io.github.djtpj.origin.Origin;
import io.github.djtpj.origin.OriginRegistry;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.logging.Level;

import static io.github.djtpj.origin.Main.plugin;

@Getter
public class OriginPicker implements Listener {
    private final static HashMap<Player, OriginPicker> playerMap = new HashMap<>();
    private final static int EXIT_BUTTON_INDEX = 49;

    private final Inventory inventory;

    public OriginPicker(Player player) {
        this.inventory = Bukkit.createInventory(player, 54, "Origin Picker");

        if (player == null) {
            plugin.getLogger().log(Level.INFO, "Origin Picker registered as listener.");
            return;
        }

        playerMap.put(player, this);

        originIcons();
        exitButton();
        GuiUtils.populateEmptySlots(inventory);

        player.openInventory(inventory);
    }

    private void originIcons() {
        Origin[] origins = OriginRegistry.getInstance().registry();

        final int FULL_ROW = 9;
        final int MARGIN = 2;
        final int STARTING_ROW = 2, START_POSITION = (STARTING_ROW - 1) * FULL_ROW + MARGIN, ROW = FULL_ROW - (MARGIN * 2);

        for (int i = 0; i < origins.length; i++) {
            // Calculate the position that the icon should be placed in.
            int index = i >= ROW ? START_POSITION + i + (MARGIN * 2) : START_POSITION + i;

            Origin origin = origins[i];

            ItemStack icon = NBTEditor.set(origin.getIcon(), origin.getId(), "origin");

            inventory.setItem(index, icon);
        }
    }

    private void exitButton() {
        ItemStack button = new ItemIcon("Exit", null, ChatColor.RED, Material.BARRIER);

        inventory.setItem(EXIT_BUTTON_INDEX, button);
    }

    @EventHandler
    public void handleClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (!playerMap.containsKey(player)) return;

        event.setCancelled(true);

        if (event.getSlot() == EXIT_BUTTON_INDEX) {
            player.closeInventory();
            return;
        }

        ItemStack item = event.getCurrentItem();

        if (GuiUtils.isFillerItem(item)) return;

        String originID = NBTEditor.getString(item, "origin");

        Origin origin = OriginRegistry.getInstance().getOrigin(originID);

        if (origin == null) return;

        new OriginPane(origin, player);

        playerMap.remove(player);
    }
}
