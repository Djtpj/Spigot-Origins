package io.github.djtpj.trait.traits;

import io.github.djtpj.authenticator.authenticators.EntityAuthenticator;
import io.github.djtpj.gui.ItemIcon;
import io.github.djtpj.trait.Ability;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.function.Predicate;

public class DamageDealtModifier extends Ability {
    public static final String ID = "damage-mod";

    private final double damage;
    private final Predicate<EntityDamageByEntityEvent> shouldModify;

    public DamageDealtModifier(String name, String description, ChatColor color, Material material, Type type, Double damage, Predicate<EntityDamageByEntityEvent> shouldModify) {
        super(name, description, color, material, type);
        this.damage = damage;
        this.shouldModify = shouldModify;
    }

    public DamageDealtModifier(ItemIcon icon, Type type, Double damage, Predicate<EntityDamageByEntityEvent> shouldModify) {
        super(icon, type);
        this.damage = damage;
        this.shouldModify = shouldModify;
    }

    public DamageDealtModifier(Double damage, Predicate<EntityDamageByEntityEvent> shouldModify) {
        super();
        this.damage = damage;
        this.shouldModify = shouldModify;
    }

    @EventHandler
    public void modifyDamage(EntityDamageByEntityEvent event) {
        if (!new EntityAuthenticator<>(this, EntityDamageByEntityEvent::getDamager).authenticate(event)) return;

        if (shouldModify.test(event)) {
            event.setDamage(damage);
        }
    }
}
