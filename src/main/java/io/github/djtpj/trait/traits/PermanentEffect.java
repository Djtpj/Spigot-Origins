package io.github.djtpj.trait.traits;

import io.github.djtpj.authenticator.Authenticator;
import io.github.djtpj.authenticator.authenticators.PlayerAuthenticator;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import static io.github.djtpj.origin.Main.plugin;

public class PermanentEffect extends RunnableAbility<PlayerEvent> {
    public static final String ID = "perm-effect";

    private final PotionEffect effect;

    protected PermanentEffect(PotionEffectType effectType, int amplifier) {
        this(null, null, null, null, effectType, amplifier);
    }

    protected PermanentEffect(String name, String description, ChatColor color, Type type, PotionEffectType effectType, int amplifier) {
        super(name, description, color, Material.POTION, type, new PlayerAuthenticator(null));

        this.effect = new PotionEffect(effectType, Integer.MAX_VALUE, amplifier, false, false);
    }

    private void effect(Player player) {
        player.addPotionEffect(effect);
        player.setMetadata(ID, new FixedMetadataValue(plugin, effect.getType()));
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

    @Override
    protected Authenticator<? super PlayerEvent> getAuthenticator() {
        return null;
    }
}
