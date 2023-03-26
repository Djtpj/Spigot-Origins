package io.github.djtpj.trait.traits;

import io.github.djtpj.authenticator.authenticators.EntityAuthenticator;
import io.github.djtpj.trait.Ability;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Arrays;

public class DamageCanceller extends Ability {
    public static final String ID = "damage-canceller";

    private final EntityDamageEvent.DamageCause[] ignoredCauses;

    protected DamageCanceller(String name, String description, ChatColor color, Material material, Type type, EntityDamageEvent.DamageCause... ignoredCauses) {
        super(name, description, color, material, type);
        this.ignoredCauses = ignoredCauses;
    }

    protected DamageCanceller(EntityDamageEvent.DamageCause... ignoredCauses) {
        super();
        this.ignoredCauses = ignoredCauses;
    }

    @EventHandler
    public void cancelDamage(EntityDamageEvent event) {
        if (!new EntityAuthenticator(this).authenticate(event)) return;

        if (Arrays.stream(ignoredCauses).toList().contains(event.getCause())) {
            event.setCancelled(true);
        }
    }
}
