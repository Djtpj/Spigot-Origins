package io.github.djtpj;

import io.github.djtpj.origin.Origin;
import io.github.djtpj.origin.OriginRegistry;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.Objects;

import static io.github.djtpj.origin.Main.plugin;

public class PlayerManager {
    private final static NamespacedKey ORIGIN_META_KEY = new NamespacedKey(plugin, "playerOrigin");

    private static PlayerManager instance;

    private PlayerManager() {

    }

    public static PlayerManager getInstance() {
        if (instance == null) {
            instance = new PlayerManager();
        }
        return instance;
    }

    public void setOrigin(Player player, Origin origin) {
        Origin oldOrigin = getOrigin(player);
        if (oldOrigin != null) oldOrigin.disable(player);
        player.getPersistentDataContainer().set(ORIGIN_META_KEY, PersistentDataType.STRING, origin.getId());
        origin.enable(player);
    }

    public HashMap<Player, Origin> getPlayerOriginMap() {
        HashMap<Player, Origin> results = new HashMap<>();

        for (Player player : Bukkit.getOnlinePlayers()) {
            Origin origin = getOrigin(player);

            if (origin == null) continue;

            results.put(player, origin);
        }

        return results;
    }

    public Origin getOrigin(Player player) {
        try {
            String id = player.getPersistentDataContainer().get(ORIGIN_META_KEY, PersistentDataType.STRING);

            for (Origin origin : OriginRegistry.getInstance().registry()) {
                if (Objects.equals(origin.getId(), id)) {
                    return origin;
                }
            }

            return null;
        } catch (IndexOutOfBoundsException ignored) {
            // If the player does not have any attached meta, return null because they have no origin.
            return null;
        }
    }
}
