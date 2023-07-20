package io.github.djtpj.trait.traits;

import io.github.djtpj.authenticator.authenticators.EntityAuthenticator;
import io.github.djtpj.gui.ItemIcon;
import io.github.djtpj.trait.SimpleTrait;
import io.github.djtpj.trait.UtilityTrait;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.json.simple.JSONArray;

import java.util.Arrays;

/**
 * Makes player completely unable to be affected by potions (although potions can be whitelisted from this effect).
 */
@UtilityTrait
public class PotionImmunity extends SimpleTrait {
    public static final String ID = "potion-immune";

    private final PotionEffectType[] types;
    private final boolean whitelist;

    public PotionImmunity(String name, String description, ChatColor color, Material material, Type type, boolean whitelist, PotionEffectType... types) {
        super(name, description, color, material, type);
        this.whitelist = whitelist;
        this.types = types;
    }

    public PotionImmunity(ItemIcon icon, Type type, JSONArray types, Boolean whitelist) {
        super(icon, type);
        this.types = (PotionEffectType[]) types.stream().map(s -> PotionEffectType.getByName((String) s)).toArray(PotionEffectType[]::new);
        this.whitelist = whitelist;
    }

    @EventHandler
    public void preventEffect(EntityPotionEffectEvent event) {
        if (!new EntityAuthenticator(this).authenticate(event)) return;
        PotionEffect newEffect = event.getNewEffect();

        if (newEffect == null) return;

        PotionEffectType type = event.getNewEffect().getType();

        // Don't block the whitelisted potion effect types
        if (type == null || matches(type)) return;

        event.setCancelled(true);
    }

    private boolean matches(PotionEffectType type) {
        boolean b = Arrays.stream(types).toList().contains(type);

        return whitelist == b;
    }
}
