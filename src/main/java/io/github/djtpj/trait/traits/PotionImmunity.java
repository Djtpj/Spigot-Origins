package io.github.djtpj.trait.traits;

import io.github.djtpj.authenticator.authenticators.EntityAuthenticator;
import io.github.djtpj.trait.Ability;
import io.github.djtpj.trait.UtilityAbility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.potion.PotionEffectType;
import org.json.simple.JSONArray;

import java.util.Arrays;

/**
 * Makes player completely unable to be affected by potions (although potions can be whitelisted from this effect).
 */
@UtilityAbility
public class PotionImmunity extends Ability {
    public static final String ID = "potion-immune";

    private final PotionEffectType[] whitelisted;

    public PotionImmunity(String name, String description, ChatColor color, Material material, Type type, PotionEffectType... whitelisted) {
        super(name, description, color, material, type);
        this.whitelisted = whitelisted;
    }

    public PotionImmunity(String name, String description, String color, String material, String type, JSONArray whitelisted) {
        super(name, description, color, material, type);
        this.whitelisted = (PotionEffectType[]) whitelisted.stream().map(s -> PotionEffectType.getByName((String) s)).toArray(PotionEffectType[]::new);
    }

    @EventHandler
    public void preventEffect(EntityPotionEffectEvent event) {
        if (!new EntityAuthenticator(this).authenticate(event)) return;

        // Don't block the whitelisted potion effect types
        if (Arrays.stream(whitelisted).toList().contains(event.getNewEffect().getType())) return;

        event.setCancelled(true);
    }
}
