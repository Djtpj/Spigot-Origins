package io.github.djtpj.gui;

import io.github.djtpj.PlayerManager;
import io.github.djtpj.origin.Origin;
import io.github.djtpj.trait.Trait;
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

@Getter
public class OriginPane implements Listener {
    private final static int BACK_BUTTON_INDEX = 45;
    private final static int SELECT_BUTTON_INDEX = 53;
    private final static int ORIGIN_ICON_INDEX = 49;
    private final static HashMap<Player, OriginPane> playerMap = new HashMap<>();

    private final Origin origin;
    private Inventory inventory;

    public OriginPane(Origin origin, Player player) {
        this.origin = origin;

        if (player == null && origin == null) return;

        inventory = Bukkit.createInventory(player, 54, origin.getName());

        playerMap.put(player, this);

        placeTraits();
        backButton();
        impactIndicators();
        // Add Origin Icon
        inventory.setItem(ORIGIN_ICON_INDEX, origin.getIcon());
        selectButton();
        GuiUtils.populateEmptySlots(inventory);

        player.openInventory(inventory);
    }

    private void placeTraits() {
        for (int i = 0; i < origin.getTraits().length; i++) {
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

            Trait trait = origin.getTraits()[i];

            inventory.setItem(index, trait.getIcon());
        }
    }

    private void backButton() {
        ItemStack button = new ItemIcon("Back", null, ChatColor.RED, Material.ARROW);

        inventory.setItem(BACK_BUTTON_INDEX, button);
    }

    private void selectButton() {
        ItemStack button = new ItemIcon("Select", null, ChatColor.GREEN, Material.BEACON);

        inventory.setItem(SELECT_BUTTON_INDEX, button);
    }

    private void impactIndicators() {
        ItemStack indicator = origin.getImpact().getIcon();

        int[] paneLocations = {
                52,
                51,
                50,
                48,
                47,
                46,
        };

        for (int i : paneLocations) {
            inventory.setItem(i, origin.getImpact().getIcon());
        }
    }

    @EventHandler
    public void handleClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (!playerMap.containsKey(player)) return;

        event.setCancelled(true);

        if (event.getSlot() == BACK_BUTTON_INDEX) {
            new OriginPicker(player);
            return;
        }

        if (event.getSlot() == SELECT_BUTTON_INDEX) {
            Origin origin = playerMap.get(player).getOrigin();
            PlayerManager.getInstance().setOrigin(player, origin);
            player.closeInventory();
        }
    }
}
