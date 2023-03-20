package io.github.djtpj.trait.traits;

import io.github.djtpj.trait.CompoundAbility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.potion.PotionEffectType;

public class FrailBody extends CompoundAbility {
    public final static String ID = "frail-body";

    public FrailBody() {
        super(
                "Frail Body",
                "Your body is frail and light, and therefore you move quickly, and fall slowly, however, you inept when it comes to combat.",
                ChatColor.GOLD,
                Material.EGG,
                Type.NEUTRAL,

                new PermanentEffect(PotionEffectType.SPEED, 0),
                new PermanentEffect(PotionEffectType.SLOW_FALLING, 0),
                new PermanentEffect(PotionEffectType.WEAKNESS, 0)
                );
    }
}
