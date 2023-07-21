package io.github.djtpj.trait.traits;

import io.github.djtpj.trait.SimpleTrait;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class Normal extends SimpleTrait {
    public static final String ID = "normal";

    public Normal() {
        super("Normal", "You are completely normal.", ChatColor.WHITE, Material.PLAYER_HEAD, Type.NEUTRAL);
    }
}
