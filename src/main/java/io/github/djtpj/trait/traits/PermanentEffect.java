package io.github.djtpj.trait.traits;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/** A PermanentEffect is an ability that constantly applies a potion effect */
public class PermanentEffect extends RunnableAbility<PlayerEvent> {
    public static final String ID = "perm-effect";

    private final PotionEffect effect;

    protected PermanentEffect(PotionEffectType effectType, int amplifier) {
        this(null, null, null, null, effectType, amplifier);
    }

    protected PermanentEffect(String name, String description, ChatColor color, Type type, PotionEffectType effectType, int amplifier) {
        super(name, description, color, Material.POTION, type, 60);

        this.effect = new PotionEffect(effectType, 200, amplifier, false, false);
    }

    private void effect(Player player) {
        player.addPotionEffect(effect);
    }

    private void removeEffect(Player player) {
        player.removePotionEffect(effect.getType());
    }

    @Override
    protected void onTick(Player player) {
        effect(player);
    }

    @Override
    protected void onDisable(Player player) {
        removeEffect(player);
    }
}
