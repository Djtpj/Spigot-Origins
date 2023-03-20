package io.github.djtpj.gui;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import io.github.djtpj.origin.Origin;
import io.github.djtpj.origin.OriginRegistry;
import io.github.djtpj.ui.ItemIcon;
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

        for (int i = 0; i < origins.length; i++) {
            final int startPos = 11;
            final int rowLength = 7;
            final int fullRowLength = 9;
            final int marginSize = 2;

            // Calculate the position that the icon should be placed in.
            int index =
                    // Check if the origin is going to overflow into the margin
                    ((i + startPos) - rowLength) % fullRowLength == 0 ?
                            // If so go to new row
                            i + startPos + (marginSize * 2) :
                            // Otherwise just add one
                            i + startPos;

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
