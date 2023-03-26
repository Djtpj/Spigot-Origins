package io.github.djtpj.trait.traits;

import io.github.djtpj.trait.Ability;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import static io.github.djtpj.origin.Main.plugin;

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

    protected void startCooldown(Player player) {
        setCooldownReady(player, false);

        player.sendMessage(getID());

        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            setCooldownReady(player, true);

            // Play sound to notify the player that the cooldown is over
            player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5f, 1f);
        }, cooldownTicks);
    }

    protected void sendCooldownMessage(Player player) {
        player.sendMessage(ChatColor.RED + "You cannot use that ability yet!");
    }

    private String getTag() {
        return getID() + "-cooldown";
    }

    protected boolean getCooldownReady(Player player) {
        try {
            return player.getMetadata(getTag()).get(0).asBoolean();
        } catch (IndexOutOfBoundsException ignored) {
            // This will be thrown if the cooldown hasn't been written yet, so return true
            return true;
        }
    }

    private void setCooldownReady(Player player, boolean b) {
        player.setMetadata(getTag(), new FixedMetadataValue(plugin, b));
    }
}
