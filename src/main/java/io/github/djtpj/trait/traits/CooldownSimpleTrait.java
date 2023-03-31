package io.github.djtpj.trait.traits;

import io.github.djtpj.trait.SimpleTrait;
import io.github.djtpj.trait.UtilityTrait;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.NoSuchElementException;

import static io.github.djtpj.origin.Main.plugin;

/**
 * A CooldownSimpleTrait is an SimpleTrait that provides the tools to program in a cooldown.
 */
@UtilityTrait
public abstract class CooldownSimpleTrait extends SimpleTrait {
    private final long cooldownTicks;

    protected CooldownSimpleTrait(String name, String description, ChatColor color, Material material, Type type, long cooldownTicks) {
        super(name, description, color, material, type);
        this.cooldownTicks = cooldownTicks;
    }

    protected CooldownSimpleTrait(long cooldownTicks) {
        super();
        this.cooldownTicks = cooldownTicks;
    }

    /** Begin the cooldown for a specific player
     * @param player the player to initiate the cooldown for
     */
    protected void startCooldown(Player player) {
        setCooldownReady(player, false);

        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            setCooldownReady(player, true);

            // Play sound to notify the player that the cooldown is over
            player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5f, 1f);
        }, cooldownTicks);
    }

    /** Sends a standardized message to the player telling them that the cooldown is not finished.
     * @param player the player to send the message to
     */
    protected void sendCooldownMessage(Player player) {
        player.sendMessage(ChatColor.RED + "You cannot use that ability yet!");
    }

    private String getTag() {
        return getID() + "-cooldown";
    }

    /** Check if the player's cooldown is over
     * @param player the player to check
     * @return whether the cooldown has ended
     */
    protected boolean getCooldownReady(Player player) {
        try {
            return player.getMetadata(getTag()).stream().filter(m -> m.getOwningPlugin().equals(plugin)).findFirst().get().asBoolean();
        } catch (NoSuchElementException ignored) {
            // This will be thrown if the cooldown hasn't been written yet, so return true
            return true;
        }
    }

    private void setCooldownReady(Player player, boolean b) {
        player.setMetadata(getTag(), new FixedMetadataValue(plugin, b));
    }
}
