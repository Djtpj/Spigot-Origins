package io.github.djtpj.trait.traits;

import io.github.djtpj.authenticator.authenticators.InteractAuthenticator;
import io.github.djtpj.gui.ItemIcon;
import io.github.djtpj.trait.SimpleTrait;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.json.simple.JSONArray;

import java.util.stream.Stream;

public class InteractionBlocker extends SimpleTrait {
    public final static String ID = "interaction-blocker";

    private final Material blockedMaterial;
    private final Action[] actions;

    public InteractionBlocker(String name, String description, ChatColor color, Material material, Type type, Material blockedMaterial, Action[] actions) {
        super(name, description, color, material, type);
        this.blockedMaterial = blockedMaterial;
        this.actions = actions;
    }

    public InteractionBlocker(ItemIcon icon, Type type, String blockedMaterial, JSONArray actions) {
        super(icon, type);
        this.blockedMaterial = Material.valueOf(blockedMaterial);
        this.actions = ((Stream<String>) actions.stream()).map(Action::valueOf).toArray(Action[]::new);
    }

    public InteractionBlocker(Material blockedMaterial, Action[] actions) {
        super();
        this.blockedMaterial = blockedMaterial;
        this.actions = actions;
    }

    @EventHandler
    public void preventInteraction(PlayerInteractEvent event) {
        if (!new InteractAuthenticator(false, false, this, actions).authenticate(event)) return;

        if (blockedMaterial != null && event.getItem().getType() == blockedMaterial) {
            event.setCancelled(true);
        }
    }
}
