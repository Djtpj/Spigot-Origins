package io.github.djtpj.trait.traits;

import io.github.djtpj.authenticator.authenticators.InteractAuthenticator;
import io.github.djtpj.authenticator.authenticators.InventoryInteractionAuthenticator;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.json.simple.JSONArray;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.bukkit.Material.*;

public class ArmorSetRestriction extends RunnableSimpleTrait {
    public static final String ID = "armor-restriction";

    private final ArmorSet[] allowedSets;

    public ArmorSetRestriction(ArmorSet... allowedSets) {
        super("Armor Restriction", generateDescription(allowedSets), ChatColor.GOLD, IRON_HELMET, Type.NEGATIVE);

        this.allowedSets = allowedSets;
    }

    public ArmorSetRestriction(JSONArray allowedSets) {
        this(((Stream<String>) allowedSets.stream()).map(ArmorSet::valueOf).toArray(ArmorSet[]::new));
    }

    private static String generateDescription(ArmorSet[] allowedSets) {
        return "You are only allowed to wear one of the following armor sets:\n\n" + Arrays.stream(allowedSets)
                .map(Enum::name)
                .map(s -> s.substring(0, 1).toUpperCase() + s.toLowerCase().substring(1))
                .map( s -> " " + s + " ")
                .collect(Collectors.joining("Set,\n", "", "Set"));
    }

    @EventHandler
    public void preventEquip(InventoryClickEvent event) {
        if (!new InventoryInteractionAuthenticator(this).authenticate(event)) return;

        if (event.getSlotType() == InventoryType.SlotType.ARMOR) {
            if (canEquip(event.getCurrentItem())) return;

            event.setCancelled(true);
        }
    }

    @EventHandler
    public void preventEquip(PlayerInteractEvent event) {
        if (!new InteractAuthenticator(false, false, this, Action.RIGHT_CLICK_AIR, Action.RIGHT_CLICK_BLOCK).authenticate(event)) return;

        if (canEquip(event.getItem())) return;
        if (!ArmorSet.isArmorItem(event.getItem())) return;

        event.setCancelled(true);
    }

    private boolean canEquip(ItemStack item) {
        return Arrays.stream(allowedSets).anyMatch(s -> s.isOfSet(item));
    }

    /**
     * The method to run on each player every x ticks
     *
     * @param player The player to run the method on
     */
    @Override
    protected void onTick(Player player) {
        ItemStack[] armorContents = player.getInventory().getArmorContents();

        ItemStack[] newContents = Arrays.stream(armorContents)
                .filter(this::isAllowed)
                .filter(Objects::nonNull)
                .toArray(ItemStack[]::new);

        ItemStack[] bannedContents = Arrays.stream(armorContents)
                .filter(i -> !isAllowed(i))
                .filter(Objects::nonNull)
                .toArray(ItemStack[]::new);

        player.getInventory().setArmorContents(newContents);

        // Take the old items and put them back in the players inventory (failing that, drop them into the world)
        HashMap<Integer, ItemStack> overflow = player.getInventory().addItem(bannedContents);
        overflow.values().forEach(i -> player.getWorld().dropItemNaturally(player.getLocation(), i));
    }

    private boolean isAllowed(ItemStack item) {
        return Arrays.stream(allowedSets).anyMatch(s -> s.isOfSet(item));
    }

    enum ArmorSet {
        LEATHER(
                LEATHER_BOOTS,
                LEATHER_LEGGINGS,
                LEATHER_CHESTPLATE,
                LEATHER_HELMET
        ),
        CHAINMAIL(
                CHAINMAIL_BOOTS,
                CHAINMAIL_LEGGINGS,
                CHAINMAIL_CHESTPLATE,
                CHAINMAIL_HELMET
        ),
        GOLDEN(
                GOLDEN_BOOTS,
                GOLDEN_LEGGINGS,
                GOLDEN_CHESTPLATE,
                GOLDEN_HELMET
        ),
        IRON(
                IRON_BOOTS,
                IRON_LEGGINGS,
                IRON_CHESTPLATE,
                IRON_HELMET
        ),
        DIAMOND(
                DIAMOND_BOOTS,
                DIAMOND_LEGGINGS,
                DIAMOND_CHESTPLATE,
                DIAMOND_HELMET
        ),
        NETHERITE(
                NETHERITE_BOOTS,
                NETHERITE_LEGGINGS,
                NETHERITE_CHESTPLATE,
                NETHERITE_HELMET
        );

        public final Material[] items;

        ArmorSet(Material... items) {
            this.items = items;
        }

        public boolean isOfSet(ItemStack item) {
            if (item == null) {
                return false;
            }

            return Arrays.stream(items)
                    .toList()
                    .contains(item.getType());
        }

        public static boolean isArmorItem(ItemStack item) {
            List<Material> materials = Arrays.stream(values())
                    .map(s -> s.items)
                    .flatMap(Arrays::stream)
                    .toList();

            return materials.contains(item.getType());
        }
    }
}
