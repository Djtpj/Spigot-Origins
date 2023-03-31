package io.github.djtpj.trait.traits;

import io.github.djtpj.authenticator.authenticators.EntityAuthenticator;
import io.github.djtpj.trait.SimpleTrait;
import io.github.djtpj.trait.UtilityTrait;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.function.Predicate;

/**
 * Changes death message if predicate is fulfilled and replaces it with a defined message.
 */
@UtilityTrait
public class DeathMessageModifier extends SimpleTrait {
    public static final String ID = "death-message-modifier";

    private final Predicate<PlayerDeathEvent> predicate;

    private final String message;

    public DeathMessageModifier(String name, String description, ChatColor color, Material material, Type type, Predicate<PlayerDeathEvent> predicate, String message) {
        super(name, description, color, material, type);

        this.predicate = predicate;
        this.message = message;
    }

    /**
     * @param predicate the method to check if the message should be changed
     * @param message the new message.
     * @apiNote To place the player's name in the message, use {@code {p}}. For example, as used by the {@link Hydrophobic} ability, the death message is passed in as {@code "{p} got soaked."}. This produces {@code "Djtpj got soaked."}.
     */
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
