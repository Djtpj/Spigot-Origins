package io.github.djtpj.trait.traits;

import io.github.djtpj.PlayerManager;
import io.github.djtpj.authenticator.Authenticator;
import io.github.djtpj.trait.Ability;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;

import static io.github.djtpj.origin.Main.plugin;

public abstract class RunnableAbility <T extends Event> extends Ability<T> {
    public static final String ID = "runnable-ability";

    private final static HashSet<RunnableAbility> abilities = new HashSet<>();

    static {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, RunnableAbility::tick, 0, 10);
    }

    protected RunnableAbility(String name, String description, ChatColor color, Material material, Type type, Authenticator<T> authenticator) {
        super(name, description, color, material, type);

        abilities.add(this);
    }

    private static void tick() {
        for (RunnableAbility ability : abilities) {
            for (Player player : ability.getApplicablePlayers()) {
                ability.onTick(player);
            }
        }
    }

    protected abstract void onTick(Player player);

    protected Player[] getApplicablePlayers() {
        // Dear god java 8 is weird
        return PlayerManager.getInstance().getPlayerOriginMap().entrySet().stream()
                .filter((e) -> Arrays.stream(e.getValue().getAllTraits()).toList().contains(this))
                .map(Map.Entry::getKey).toArray(Player[]::new);
    }
}
