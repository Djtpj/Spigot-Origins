package io.github.djtpj.trait.traits;

import io.github.djtpj.authenticator.authenticators.EntityAuthenticator;
import io.github.djtpj.gui.ItemIcon;
import io.github.djtpj.trait.Ability;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageTakenModifier extends Ability {
    public static final String ID = "damage-taken-mod";

    private final Double newDamage;

    private final EntityDamageEvent.DamageCause cause;

    public DamageTakenModifier(String name, String description, ChatColor color, Material material, Type type, Double newDamage, EntityDamageEvent.DamageCause cause) {
        super(name, description, color, material, type);
        this.newDamage = newDamage;
        this.cause = cause;
    }

    public DamageTakenModifier(ItemIcon icon, Type type, Double newDamage, String cause) {
        super(icon, type);
        this.newDamage = newDamage;
        this.cause = EntityDamageEvent.DamageCause.valueOf(cause);
    }

    public DamageTakenModifier(Double newDamage, EntityDamageEvent.DamageCause cause) {
        super();
        this.newDamage = newDamage;
        this.cause = cause;
    }

    @EventHandler
    public void changeDamage(EntityDamageEvent event) {
        if (!new EntityAuthenticator(this).authenticate(event)) return;

        if (event.getCause() == cause) {
            event.setDamage(newDamage);
        }
    }
}