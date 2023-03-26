package io.github.djtpj.trait.traits;

import io.github.djtpj.authenticator.authenticators.EntityTargetAuthenticator;
import io.github.djtpj.trait.Ability;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityTargetEvent;
import org.json.simple.JSONArray;

import java.util.ArrayList;

public class PacifyMob extends Ability {
    public static final String ID = "pacify-mob";

    private final EntityType[] types;

    public PacifyMob(String name, String description, ChatColor color, Material material, Type type, EntityType[] types) {
        super(name, description, color, material, type);
        this.types = types;
    }

    public PacifyMob(String name, String description, String color, String material, String type, JSONArray types) {
        this(
                name,
                description,
                ChatColor.valueOf(color),
                Material.valueOf(material),
                Type.valueOf(type),
                (EntityType[]) types.stream().map(o -> ((String) o).toUpperCase()).map(s -> EntityType.valueOf((String) s)).toArray(EntityType[]::new)
        );
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
