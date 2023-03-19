package io.github.djtpj;

import io.github.djtpj.origins.Origin;
import io.github.djtpj.origins.OriginRegistry;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Objects;

import static io.github.djtpj.origins.Main.plugin;

public class PlayerManager {
    private final static String ORIGIN_META_KEY = "playerOrigin";

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
        player.setMetadata(ORIGIN_META_KEY, new FixedMetadataValue(plugin, origin.getId()));
    }

    public Origin getOrigin(Player player) {
        try {
            String id = player.getMetadata(ORIGIN_META_KEY).get(0).asString();

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
