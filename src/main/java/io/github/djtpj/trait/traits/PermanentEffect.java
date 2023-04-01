package io.github.djtpj.trait.traits;

import io.github.djtpj.gui.ItemIcon;
import io.github.djtpj.trait.UtilityTrait;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/** A PermanentEffect is an ability that constantly applies a potion effect */
@UtilityTrait
public class PermanentEffect extends RunnableSimpleTrait {
    public static final String ID = "perm-effect";

    private final PotionEffect effect;

    public PermanentEffect(PotionEffectType effectType, int amplifier) {
        this(null, null, null, null, effectType, amplifier);
    }

    public PermanentEffect(String name, String description, ChatColor color, Type type, PotionEffectType effectType, int amplifier) {
        super(name, description, color, Material.POTION, type, 60);

        this.effect = new PotionEffect(effectType, 200, amplifier, false, false, false);
    }

    public PermanentEffect(ItemIcon icon, Type type, PotionEffectType effectType, int amplifier) {
        super(icon, type, 60);

        this.effect = new PotionEffect(effectType, 200, amplifier, false, false, false);
    }

    public PermanentEffect(ItemIcon icon, Type type, String effectType, Long amplifier) {
        this(icon, type, PotionEffectType.getByName(effectType), (int) (long) amplifier);
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
