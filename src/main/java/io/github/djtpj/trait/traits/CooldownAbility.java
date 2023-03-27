package io.github.djtpj.trait.traits;

import io.github.djtpj.trait.Ability;
import io.github.djtpj.trait.UtilityAbility;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.NoSuchElementException;

import static io.github.djtpj.origin.Main.plugin;

/**
 * A CooldownAbility is an Ability that provides the tools to program in a cooldown.
 */
@UtilityAbility
public abstract class CooldownAbility extends Ability {
    private final long cooldownTicks;

    protected CooldownAbility(String name, String description, ChatColor color, Material material, Type type, long cooldownTicks) {
        super(name, description, color, material, type);
        this.cooldownTicks = cooldownTicks;
    }

    protected CooldownAbility(long cooldownTicks) {
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
