package io.github.djtpj.trait.traits;

import io.github.djtpj.PlayerManager;
import io.github.djtpj.gui.ItemIcon;
import io.github.djtpj.trait.SimpleTrait;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Map;

import static io.github.djtpj.origin.Main.plugin;

/** A RunnableSimpleTrait is an ability that runs over and over again every {@code x} ticks */
public abstract class RunnableSimpleTrait extends SimpleTrait {
    private int delayTicks = 0, loopTicks  = 10;

    public RunnableSimpleTrait(String name, String description, ChatColor color, Material material, Type type) {
        this(name, description, color, material, type, 0, 10);
    }

    public RunnableSimpleTrait(String name, String description, ChatColor color, Material material, Type type, int loopTicks) {
        this(name, description, color, material, type, 0, loopTicks);
    }

    public RunnableSimpleTrait(String name, String description, ChatColor color, Material material, Type type, int delayTicks, int loopTicks) {
        super(name, description, color, material, type);
        this.delayTicks = delayTicks;
        this.loopTicks = loopTicks;

        init();
    }

    public RunnableSimpleTrait() {
        init();
    }

    public RunnableSimpleTrait(int loopTicks) {
        this.loopTicks = loopTicks;

        init();
    }

    public RunnableSimpleTrait(int delayTicks, int loopTicks) {
        this.delayTicks = delayTicks;
        this.loopTicks = loopTicks;

        init();
    }

    public RunnableSimpleTrait(ItemIcon icon, Type type) {
        super(icon, type);

        init();
    }

    public RunnableSimpleTrait(ItemIcon icon, Type type, int loopTicks) {
        super(icon, type);

        this.loopTicks = loopTicks;

        init();
    }

    public RunnableSimpleTrait(ItemIcon icon, Type type, int delayTicks, int loopTicks) {
        super(icon, type);
        this.delayTicks = delayTicks;
        this.loopTicks = loopTicks;

        init();
    }

    private void init() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, this::tick, delayTicks, loopTicks);
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
