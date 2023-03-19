package io.github.djtpj.traits;

import io.github.djtpj.authenticator.authenticators.PlayerAuthenticator;
import io.github.djtpj.trait.Trait;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class Roost extends Trait {
    public static final String ID = "roost";

    public Roost() {
        super("Roost", "You can only sleep at high altitudes", ChatColor.GRAY, Material.FEATHER, Type.NEGATIVE, new PlayerAuthenticator());
    }
}
