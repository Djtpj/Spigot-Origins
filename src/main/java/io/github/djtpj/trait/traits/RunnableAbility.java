package io.github.djtpj.trait.traits;

import io.github.djtpj.PlayerManager;
import io.github.djtpj.trait.Ability;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Map;

import static io.github.djtpj.origin.Main.plugin;

/** A RunnableAbility is an ability that runs over and over again every ten ticks */
public abstract class RunnableAbility extends Ability {
    public static final String ID = "runnable-ability";

    public RunnableAbility(String name, String description, ChatColor color, Material material, Type type) {
        this(name, description, color, material, type, 0, 10);
    }

    public RunnableAbility(String name, String description, ChatColor color, Material material, Type type,  int loopTicks) {
        this(name, description, color, material, type, 0, loopTicks);
    }

    public RunnableAbility(String name, String description, ChatColor color, Material material, Type type, int delayTicks, int loopTicks) {
        super(name, description, color, material, type);

        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, this::tick, delayTicks, loopTicks);
    }

    public RunnableAbility() {
        this(null, null, null, null, null);
    }

    public RunnableAbility(int loopTicks) {
        this(null, null, null, null, null, loopTicks);
    }

    public RunnableAbility(int delayTicks, int loopTicks) {
        this(null, null, null, null, null, delayTicks, loopTicks);
    }

    private void tick() {
        for (Player player : getApplicablePlayers()) {
            onTick(player);
        }
    }

    /** The method to run on each player every ten ticks
     * @param player The player to run the method on
     */
    protected abstract void onTick(Player player);

    /** Returns the players with an origin that posses this ability
     * @return the players that have this ability
     */
    protected Player[] getApplicablePlayers() {
        // Dear god java 8 is weird
        return PlayerManager.getInstance().getPlayerOriginMap().entrySet().stream()
                .filter((e) -> Arrays.stream(e.getValue().getAllTraits()).toList().contains(this))
                .map(Map.Entry::getKey).toArray(Player[]::new);
    }
}
