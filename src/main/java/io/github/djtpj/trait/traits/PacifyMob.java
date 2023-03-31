package io.github.djtpj.trait.traits;

import io.github.djtpj.authenticator.authenticators.EntityTargetAuthenticator;
import io.github.djtpj.gui.ItemIcon;
import io.github.djtpj.trait.SimpleTrait;
import io.github.djtpj.trait.UtilityTrait;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityTargetEvent;
import org.json.simple.JSONArray;

import java.util.ArrayList;

/**
 * Makes a certain mob(s) unable to aggro on players.
 */
@UtilityTrait
public class PacifyMob extends SimpleTrait {
    public static final String ID = "pacify-mob";

    private final EntityType[] types;

    public PacifyMob(String name, String description, ChatColor color, Material material, Type type, EntityType[] types) {
        super(name, description, color, material, type);
        this.types = types;
    }

    public PacifyMob(ItemIcon icon, Type type, JSONArray types) {
        super(icon, type);

        this.types = (EntityType[]) types.stream().map(o -> ((String) o).toUpperCase()).map(s -> EntityType.valueOf((String) s)).toArray(EntityType[]::new);
    }

    private static EntityType[] readTypes(JSONArray array) {
        ArrayList<EntityType> results = new ArrayList<>();

        for (Object o : array) {
            results.add(EntityType.valueOf((String) o));
        }

        return results.toArray(new EntityType[0]);
    }

    @EventHandler
    public void preventAggro(EntityTargetEvent event) {
        if (!new EntityTargetAuthenticator(this, types).authenticate(event)) return;

        event.setCancelled(true);
    }
}
