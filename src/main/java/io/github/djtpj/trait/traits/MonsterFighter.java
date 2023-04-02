package io.github.djtpj.trait.traits;

import io.github.djtpj.authenticator.authenticators.EntityAuthenticator;
import io.github.djtpj.trait.SimpleTrait;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Arrays;

import static org.bukkit.entity.EntityType.*;

public class MonsterFighter extends SimpleTrait {
    private final static EntityType[] types = {
            ZOMBIE,
            SPIDER,
            CAVE_SPIDER,
            SKELETON,
    };

    public final static String ID = "monster-fighter";

    public MonsterFighter() {
        super("Monster Fighter", "You are able to destroy mobs monsters in one hit, but they can do the same to you.", ChatColor.GREEN, Material.IRON_SWORD, Type.NEUTRAL);
    }

    @EventHandler
    public void mobs(EntityDamageByEntityEvent event) {
        if (!new EntityAuthenticator<>(this).authenticate(event)) return;

        Player player = (Player) event.getEntity();

        // If the damager is one of the types damage
        if (Arrays.asList(types).contains(event.getDamager().getType())) {
            player.setHealth(0);
        }
    }

    @EventHandler
    public void player(EntityDamageByEntityEvent event) {
        if (!new EntityAuthenticator<>(this, EntityDamageByEntityEvent::getDamager).authenticate(event)) return;

        Player player = (Player) event.getDamager();

        if (Arrays.asList(types).contains(event.getEntityType())) {
            Damageable entity = (Damageable) event.getEntity();

            entity.setHealth(0);
        }
    }
}
