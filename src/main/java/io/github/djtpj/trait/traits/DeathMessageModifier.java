package io.github.djtpj.trait.traits;

import io.github.djtpj.authenticator.authenticators.EntityAuthenticator;
import io.github.djtpj.trait.Ability;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.function.Predicate;

public class DeathMessageModifier extends Ability {
    public static final String ID = "death-message-modifier";

    private final Predicate<PlayerDeathEvent> predicate;

    private final String message;

    public DeathMessageModifier(String name, String description, ChatColor color, Material material, Type type, Predicate<PlayerDeathEvent> predicate, String message) {
        super(name, description, color, material, type);

        this.predicate = predicate;
        this.message = message;
    }

    public DeathMessageModifier(Predicate<PlayerDeathEvent> predicate, String message) {
        super(null, null, null, null, null);

        this.predicate = predicate;
        this.message = message;
    }

    @EventHandler
    public void changeMessage(PlayerDeathEvent event) {
        if (!new EntityAuthenticator(this).authenticate(event)) return;

        event.setDeathMessage(message.replace("{p}", event.getEntity().getDisplayName()));
    }
}
